package api;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.WebApplicationException;

@Provider
public class RequestsExceptionHandler implements ExceptionMapper<WebApplicationException> {
    @Override
    public Response toResponse(WebApplicationException exception){
    	// Bad request by default if an error occured
        return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();  
    }
}