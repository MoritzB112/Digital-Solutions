package es.uma.proyecto.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.ws.rs.core.UriInfo;

import es.uma.proyecto.ejb.GestionClientes;
import es.uma.proyecto.ejb.GestionCuentas;
import es.uma.proyecto.ejb.GestionPersonas_Autorizadas;
import es.uma.proyecto.jpa.Autorizacion;
import es.uma.proyecto.jpa.Cliente;
import es.uma.proyecto.jpa.Cuenta_Fintech;
import es.uma.proyecto.jpa.Empresa;
import es.uma.proyecto.jpa.Individual;
import es.uma.proyecto.jpa.Persona_Autorizada;

@Path("")
public class ServicioREST {

	@EJB
	private GestionCuentas sc;
	@EJB
	private GestionClientes sci;
	@EJB
	private GestionPersonas_Autorizadas gpa;

	@Context
	private UriInfo uriInfo;

	@Path("/healthcheck")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response healthcheck() {
		return Response.ok("OK").build();
	}

	@Path("/clients")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response clients(ClientInput inputA) {
		Client input = inputA.getSearchParameters();

		JsonArrayBuilder ja = Json.createArrayBuilder();

		ja.addAll(parseIndividual(input));
		ja.addAll(parsePersAut(input));

		JsonObjectBuilder solJ = Json.createObjectBuilder();
		solJ.add("Individual", ja.build());

		return Response.ok(solJ.build()).build();
	}

	private JsonArrayBuilder parsePersAut(Client input) {
		List<Persona_Autorizada> x = new ArrayList<>();
		x.addAll(gpa.sacarPA());

		List<Persona_Autorizada> sol = new ArrayList<>();
		sol.addAll(x);

		for (Persona_Autorizada i : x) {
			if (input.getName() != null) {
				if (input.getName().getFirstName() != null) {
					if (!input.getName().getFirstName().equalsIgnoreCase(i.getNombre())) {
						sol.remove(i);
					}
				}
				if (input.getName().getLastName() != null) {
					if (!input.getName().getLastName().equalsIgnoreCase(i.getApellidos())) {
						sol.remove(i);
					}
				}
			}
			if (input.getStartPeriod() != null) {
				if (!input.sacarStartPeriodD().before(i.getFechaInicio())) {
					sol.remove(i);
				}
			}
			if (input.getEndPeriod() != null && i.getFechaFin() != null) {
				if (!input.sacarEndPeriodD().after(i.getFechaFin())) {
					sol.remove(i);
				}
			}
		}

		JsonArrayBuilder ja = Json.createArrayBuilder();
		for (Persona_Autorizada i : sol) {

			JsonObjectBuilder obj = Json.createObjectBuilder();
			JsonArrayBuilder productos = Json.createArrayBuilder();
			for (Autorizacion a : i.getAutorizaciones()) {
				for (Cuenta_Fintech c : a.getEm().getCf()) {
					JsonObjectBuilder producto = Json.createObjectBuilder();
					producto.add("productNumber", c.getIBAN());
					producto.add("status", c.getEstado());
					producto.add("relationship", "autorizada");
					productos.add(producto);
				}
			}
			obj.add("products", productos);
			obj.add("activeCustomer", i.getEstado().equalsIgnoreCase("ALTA"));
			obj.add("identificationNumber", i.getIdentificacion());
			if(i.getFecha_nacimiento()==null) {
				obj.add("dateOfBirth", "non-existent");
			}else {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				obj.add("dateOfBirth", formatter.format(i.getFecha_nacimiento()));
			}
			
			JsonObjectBuilder nombre = Json.createObjectBuilder();
			nombre.add("firstName", i.getNombre());
			nombre.add("lastName", i.getApellidos());
			obj.add("name", nombre);
			JsonObjectBuilder direccion = Json.createObjectBuilder();
			direccion.add("streetNumber", i.getDireccion());
			direccion.add("postalCode", i.getCodigoPostal());
			direccion.add("city", i.getCiudad());
			direccion.add("country", i.getPais());
			obj.add("address", direccion);
			ja.add(obj);

		}
		return ja;
	}

	private JsonArrayBuilder parseIndividual(Client input) {
		List<Individual> x = new ArrayList<>();
		x.addAll(sci.sacarIndividual());

		List<Individual> sol = new ArrayList<>();
		sol.addAll(x);

		for (Individual i : x) {
			if (input.getName() != null) {
				if (input.getName().getFirstName() != null) {
					if (!input.getName().getFirstName().equalsIgnoreCase(i.getNombre())) {
						sol.remove(i);
					}
				}
				if (input.getName().getLastName() != null) {
					if (!input.getName().getLastName().equalsIgnoreCase(i.getApellido())) {
						sol.remove(i);
					}
				}
			}
			if (input.getStartPeriod() != null) {
				if (!input.sacarStartPeriodD().before(i.getFecha_alta())) {
					sol.remove(i);
				}
			}
			if (input.getEndPeriod() != null && i.getFecha_baja() != null) {
				if (!input.sacarEndPeriodD().after(i.getFecha_baja())) {
					sol.remove(i);
				}
			}
		}

		JsonArrayBuilder ja = Json.createArrayBuilder();
		for (Individual i : sol) {
			JsonObjectBuilder obj = Json.createObjectBuilder();
			JsonArrayBuilder productos = Json.createArrayBuilder();
			for (Cuenta_Fintech c : i.getCf()) {
				JsonObjectBuilder producto = Json.createObjectBuilder();
				producto.add("productNumber", c.getIBAN());
				producto.add("status", c.getEstado());
				producto.add("relationship", "propietaria");
				productos.add(producto);
			}
			obj.add("products", productos);
			obj.add("activeCustomer", i.getEstado().equalsIgnoreCase("ALTA"));
			obj.add("identificationNumber", i.getIdentificacion());
			if(i.getFecha_nacimiento()==null) {
				obj.add("dateOfBirth", "non-existent");
			}else {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				obj.add("dateOfBirth", formatter.format(i.getFecha_nacimiento()));
			}
			JsonObjectBuilder nombre = Json.createObjectBuilder();
			nombre.add("firstName", i.getNombre());
			nombre.add("lastName", i.getApellido());
			obj.add("name", nombre);
			JsonObjectBuilder direccion = Json.createObjectBuilder();
			direccion.add("streetNumber", i.getDireccion());
			direccion.add("postalCode", i.getCodigoPostal());
			direccion.add("city", i.getCiudad());
			direccion.add("country", i.getPais());
			obj.add("address", direccion);
			ja.add(obj);
		}
		return ja;
	}

	@Path("/products")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response products(ProductsInput input) {
		List<Cuenta_Fintech> x = new ArrayList<>();
		x.addAll(sc.sacarSegregadas());
		x.addAll(sc.sacarPooledAccount());

		List<Cuenta_Fintech> sol = new ArrayList<>();
		sol.addAll(x);

		String a = input.getSearchParameters().getStatus();
		String b = input.getSearchParameters().getProductNumber();

		for (Cuenta_Fintech c : x) {
			if (a != null) {
				if (!c.getEstado().equalsIgnoreCase(a)) {
					sol.remove(c);
				}
			}
			if (b != null) {
				if (!c.getIBAN().equals(b)) {
					sol.remove(c);
				}
			}

		}

		JsonArrayBuilder ja = Json.createArrayBuilder();

		for (Cuenta_Fintech c : sol) {
			Cliente cl = c.getCl();
			JsonObjectBuilder obj = Json.createObjectBuilder();
			JsonObjectBuilder cuenta = Json.createObjectBuilder();
			cuenta.add("activeCustomer", cl.getEstado().equalsIgnoreCase("ALTA"));
			cuenta.add("accounttype", cl.getTipo_cliente());

			if (cl.getTipo_cliente().equalsIgnoreCase("FISICA")) {
				JsonObjectBuilder nombre = Json.createObjectBuilder();
				Individual i = sci.gtIndividual(cl.getId());
				nombre.add("firstName", i.getNombre());
				nombre.add("lastName", i.getApellido());
				cuenta.add("name", nombre);

			} else if (cl.getTipo_cliente().equalsIgnoreCase("JURIDICA")) {
				Empresa i = sci.gtEmpresa(cl.getId());
				cuenta.add("razon_social", i.getRazon_social());
			}

			JsonObjectBuilder direccion = Json.createObjectBuilder();
			direccion.add("streetNumber", cl.getDireccion());
			direccion.add("postalCode", cl.getCodigoPostal());
			direccion.add("city", cl.getCiudad());
			direccion.add("country", cl.getPais());
			cuenta.add("address", direccion);
			obj.add("accountHolder", cuenta);
			obj.add("productNumber", c.getIBAN());
			obj.add("status", c.getEstado());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			obj.add("startDate", format.format(c.getFecha_apertura()));
			if (c.getFecha_cierre() == null) {
				obj.add("endDate", "non-existent");
			} else {
				obj.add("endDate", format.format(c.getFecha_cierre()));
			}

			ja.add(obj);
		}

		JsonObjectBuilder obj = Json.createObjectBuilder();
		obj.add("products", ja.build());

		return Response.ok(obj.build()).build();
	}
}
