package login;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import javax.ws.rs.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.InputSource;
import org.w3c.dom.Document;

import java.net.URI;
import java.net.URISyntaxException;

import beans.User;

@Path("/login")
public class Login extends Application {
	private Properties config;
	
	public Login(){
		this.config = new Properties();
		
		try {
			config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("conf.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    @GET
    @Produces("text/html; charset= UTF-8")
    public Response get(@QueryParam("ticket") String ticket, @Context UriInfo uriInfo, @Context Request request){
    	// Si on a déjà un cookie, on redirige
    	
    	// Si pas de parametres GET
    	// Redirection vers le CAS (header 301)
    	if(ticket == null || ticket.equals("")){
    		try {
    			return Response.temporaryRedirect(new URI(this.config.getProperty("casLoginEndPoint") + "?service=" + this.config.getProperty("endPoint") + "/server/login")).build();
    		}
    		catch(URISyntaxException e){
    			e.printStackTrace();
    			return Response.status(500).entity("Oops, something went wrong because we don't know how to code. Sorry pal !").build();
    		}
    	}
    	else {
        	// Si parametre ticket present
        	// Lecture du service validate
        	// Ajout de l'utilisateur si inconnu
        	// Déclaration d'un cookie (token de session)
        	// Redirection
    		
    		Response response = ClientBuilder.newClient()
			    								.target(this.config.getProperty("casServiceValidateEndPoint"))
			    								.queryParam("service", this.config.getProperty("endPoint") + "/server/login")
			                                    .queryParam("ticket", ticket)
			                                    .request()
			                                    .get();
    		
    		String casInfo = response.readEntity(String.class).replaceAll("cas:", "");
    		
    		response.close();
    		
    		try {
    			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    			DocumentBuilder builder = factory.newDocumentBuilder();
    			InputSource is = new InputSource(new StringReader(casInfo));
    			Document info = builder.parse(is);
    			
    			// Building user
    			User u = new User();
    			u.setUsername(info.getElementsByTagName("user").item(0).getTextContent());
    			u.setFirstName(info.getElementsByTagName("givenName").item(0).getTextContent());
    			u.setLastName(info.getElementsByTagName("sn").item(0).getTextContent());
    			u.setStatus("user");
    			
    			// Checking if user exists
    			User dummy = dao.Users.get(u.getUsername());
    			
    			if(dummy == null){
    				// Creating
    				dao.Users.add(u);
    			}
    			else {
    				u = dao.Users.get(u.getUsername());
    			}
    			
        		return Response.ok(u.toString()).cookie(new NewCookie("token", "fzeufvhaunfarzfuomazzouriaemo", "/", "localhost", "", 86400, false)).build();    			
    		}
    		catch(Exception e){
    			e.printStackTrace();
    			return Response.status(500).entity("Oops, something went wrong because we don't know how to code. Sorry pal !").build();
    		}
    	}
    }	
}