package login;

import java.io.IOException;
import java.util.Properties;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;

@Path("")
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
    @Produces(MediaType.TEXT_HTML)
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
    		
    		return Response.ok("ticket = " + ticket).build();
    	}
    }	
}