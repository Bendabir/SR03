//package utils;
//
//import javax.ws.rs.*;
//import javax.ws.rs.core.*;
//
//@Path("/*")
//public class CORSFilter extends Application {
//	@OPTIONS
//	@Path("{path : .*}")
//	public Response options() {
//	    return Response.ok("")
//	            .header("Access-Control-Allow-Origin", "*")
//	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
//	            .header("Access-Control-Allow-Credentials", "true")
//	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
//	            .header("Access-Control-Max-Age", "1209600")
//	            .build();
//	}
//}