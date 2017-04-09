package api;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

@Provider
public class RequestsExceptionHandler implements ExceptionMapper<WebApplicationException> {
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(WebApplicationException exception){
    	// Bad request by default if an error occurred
    	// Returning application/json put could be text instead
        return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).header("Content-Type", "application/json").build();
    }
}