package login;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.InputSource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.w3c.dom.Document;

import java.net.URI;
import java.net.URISyntaxException;

import beans.User;

@Path("/login")
public class Login extends Application {
	private Gson gson; // Gson builder
	private Properties config;
	
	public Login(){
		this.gson = new GsonBuilder().setPrettyPrinting().create(); // Human readable		
		this.config = new Properties();
		
		try {
			config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("conf.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response get(@QueryParam("ticket") String ticket, @Context Request request, @Context HttpServletRequest baseRequest){
    	HttpSession session = baseRequest.getSession(false);
    	
    	// If no session, log in
    	if(session == null){
        	// If no get parameters, redirect to CAS login (301
        	if(ticket == null || ticket.equals("")){
        		try {
        			return Response.temporaryRedirect(new URI(this.config.getProperty("casLoginEndPoint") + "?service=" + this.config.getProperty("endPoint") + "/server/login")).build();
        		}
        		catch(URISyntaxException e){
        			e.printStackTrace();

            		JsonObject jsonError = new JsonObject();
            		jsonError.addProperty("message", "Oops, something went wrong because we don't know how to code. Sorry pal !");
        			
        			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(this.gson.toJson(jsonError)).build();
        		}
        	}
        	else {
        		// If we have the "ticket" parameter
        		// Validate the service through CAS
        		// Read XML response with user information
        		Response response = ClientBuilder.newClient()
    			    								.target(this.config.getProperty("casServiceValidateEndPoint"))
    			    								.queryParam("service", this.config.getProperty("endPoint") + "/server/login")
    			                                    .queryParam("ticket", ticket)
    			                                    .request()
    			                                    .get();
        		
        		String casInfo = response.readEntity(String.class).replaceAll("cas:", ""); // XML namespace is annoying
        		
        		response.close();
        		
        		try {
        			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        			DocumentBuilder builder = factory.newDocumentBuilder();
        			InputSource is = new InputSource(new StringReader(casInfo));
        			Document info = builder.parse(is);
        			
        			// Need to add a check on authenticationSuccess or authenticatonFailure ?
        			
        			// Building user
        			User u = new User();
        			u.setUsername(info.getElementsByTagName("user").item(0).getTextContent());
        			u.setFirstName(info.getElementsByTagName("givenName").item(0).getTextContent());
        			u.setLastName(info.getElementsByTagName("sn").item(0).getTextContent());
        			u.setStatus("user");
        			
        			// Checking if user exists
        			User dummy = dao.Users.get(u.getUsername());
        			
        			// If not, creating, otherwise updating information
        			if(dummy == null){
        				// Creating
        				dao.Users.add(u);
        			}
        			else {
        				u = dummy;
        			}
        			
        			// Declaring new session       			
        			HttpSession s = baseRequest.getSession(true);
        			
        			s.setAttribute("username", u.getUsername());
        			s.setAttribute("firstname", u.getFirstName());
        			s.setAttribute("lastname", u.getLastName());
        			s.setAttribute("status", u.getStatus());

        			return Response.ok(this.gson.toJson(u)).build(); // Returning user information
//            		return Response.ok(this.gson.toJson(u)).cookie(new NewCookie("token", "fzeufvhaunfarzfuomazzouriaemo", "/", "localhost", "", 24 * 60 * 60, false)).build();    			
        		}
        		catch(Exception e){
        			e.printStackTrace();
        			
            		JsonObject jsonError = new JsonObject();
            		jsonError.addProperty("message", "Oops, something went wrong because we don't know how to code. Sorry pal !");
        			
        			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(this.gson.toJson(jsonError)).build();
        		}
        	}
    	}
    	else {
    		// User already logged in, giving information
    		JsonObject user = new JsonObject();
    		user.addProperty("username", session.getAttribute("username").toString());
    		user.addProperty("firstname", session.getAttribute("firstname").toString());
    		user.addProperty("lastname", session.getAttribute("lastname").toString());
    		user.addProperty("status", session.getAttribute("status").toString());
    		
    		JsonObject jsonResponse = new JsonObject();
    		jsonResponse.addProperty("message", "User already logged in !");
    		jsonResponse.add("user", user);
    		
    		return Response.ok(this.gson.toJson(jsonResponse)).build();
    	}
    }	
}