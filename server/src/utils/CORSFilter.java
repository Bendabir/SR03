//package utils;
//
//import java.io.IOException;
//
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerResponseContext;
//import javax.ws.rs.container.ContainerResponseFilter;
//import javax.ws.rs.container.PreMatching;
//import javax.ws.rs.ext.Provider;
//
//// Small filter applied to all requests to enable CORS
//@Provider
//@PreMatching
//public class CORSFilter implements ContainerResponseFilter {
// 
//    @Override
//    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
//    	System.out.println("Filter called !");
//    	
//        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
//        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
//        responseContext.getHeaders().add("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization");
//        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
//    }
//}