//package es.uma.proyecto.api;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//import javax.ws.rs.HeaderParam;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.UriInfo;
//
//@Path(value = "/test")
//public class ServicioREST {
//	
//	@Context
//	private UriInfo uriInfo;
//	
//	@HeaderParam("User-auth")
//	private String autorizacion;
//	
//	@Path("/healthcheck")
//	@GET
//	@Produces (MediaType.APPLICATION_JSON)
//	public Response healthcheck() {
//		return Response.ok("OK").build();
//	}
//	
//	
//	@Path("/clients")
//	@POST
//	@Consumes (MediaType.APPLICATION_JSON)
//	@Produces (MediaType.APPLICATION_JSON)
//	public Response aniadirContacto(ClientInput ci) {
//		return Response.ok(ci).build();
//	}
//	
////	@Path("/contacto/{id}")
////	@GET
////	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
////	public Response getContacto(@PathParam("id") String id) {
////		
////	}
//	
//	
//}
