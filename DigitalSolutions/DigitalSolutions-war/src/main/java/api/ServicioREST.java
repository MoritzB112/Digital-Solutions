package api;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import es.uma.proyecto.ejb.ClientesEJB;
import es.uma.proyecto.ejb.CuentasEJB;
import es.uma.proyecto.ejb.Excepciones.*;
import es.uma.proyecto.jpa.Cliente;
import es.uma.proyecto.jpa.Cuenta_Fintech;
import es.uma.proyecto.jpa.Empresa;
import es.uma.proyecto.jpa.Individual;
import es.uma.proyecto.jpa.Segregada;



public class ServicioREST {
	
	@EJB
	private ClientesEJB clientes;
	
	@EJB
	private CuentasEJB cuentas;
	
	@Context
	private UriInfo uriInfo;
	
	@Path("/healthcheck")
	@GET
	@Produces ("text/plain")
	public Response checkService () {
		return Response.ok("OK").build();
	}
	
	@Path("/clients")
	@POST
	@Consumes ({MediaType.APPLICATION_JSON})
	@Produces ({MediaType.APPLICATION_JSON})
	public Response filterClients (searchClients c) {
		JsonObjectBuilder result = Json.createObjectBuilder();
		
//		List<Cliente> l = clientes.sacarInformacionCliente(c.getNombre(), c.getNombre());
		
	
 /*		result.add(Json.createArrayBuilder()
				.add("products", Json.createArrayBuilder()
						.add("productNumber", )
						.add("status", )
						.add("relationship", ))
				.add("activeCustomer", )
				.add("identificationNumber", )
				.add("dateOfBirth", )
				.add("name", Json.createObjectBuilder()
						.add("firstName", )
						.add("lastName",  )
				.add("address", Json.createObjectBuilder()
						.add("streetNumber", )
						.add("postalCode", )
						.add("city", )
						.add("country", ));
						
 */	return null;}
	
	@Path("/products")
	@POST
	@Consumes ({MediaType.APPLICATION_JSON})
	@Produces ({MediaType.APPLICATION_JSON})
	public Response filterAccounts (searchProducts p) {
		JsonObjectBuilder result = Json.createObjectBuilder();
		
		List<Cuenta_Fintech> l = cuentas.sacarInformacionCuenta(p.getProductNumber(), p.getStatus());
		
		for (Cuenta_Fintech s : l) {
			String firstName; String lastName;
			String tipoCuenta = s.getCl().getTipo_cliente();
			if (tipoCuenta.toUpperCase() == "FISICA") {
				Individual ind = clientes.sacarIndividualConcreto(s.getCl().getId());
				firstName = ind.getNombre();
				lastName = ind.getApellido();
			} else { // tipoCuenta.toUpperCase() == "EMPRESA"
				Empresa emp = clientes.sacarEmpresaConcreta(s.getCl().getId());
				firstName = emp.getRazon_social();
				lastName = null;
			}
			
			String fechaCierre = s.getFecha_cierre().toString();
			if (fechaCierre == null) fechaCierre = "non-existent";
			
			result.add("products", Json.createArrayBuilder())
					.add("accountHolder", Json.createObjectBuilder()
							.add("activeCustomer", s.getCl().getEstado())
							.add("accounttype", tipoCuenta)
							.add("name", Json.createObjectBuilder()
									.add("firstName", firstName)
									.add("lastName", lastName))
							.add("address", Json.createObjectBuilder()
									.add("streetNumber", s.getCl().getDireccion())
									.add("postalCode", s.getCl().getCodigoPostal())
									.add("city", s.getCl().getCiudad())
									.add("country", s.getCl().getPais()))
							.add("productNumber", s.getIBAN())
							.add("status", s.getEstado())
							.add("startDate", s.getFecha_apertura().toString())
							.add("endDate", fechaCierre));
		}
		
		return Response.ok(result).build();
	}
	
}