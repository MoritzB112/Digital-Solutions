package es.uma.proyecto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.net.ssl.HttpsURLConnection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.proyecto.Excepciones.DivisaNoExisteException;
import es.uma.proyecto.Excepciones.DivisaYaExisteException;

@Stateless
public class DivisasEJB implements GestionDivisas {
	
	@PersistenceContext(name="proyecto-ejb")
	private EntityManager em;
	
	@Override
	public void insertarDivisa(Divisa div) throws DivisaYaExisteException {
		Divisa realDiv=em.find(Divisa.class, div.getAbreviatura());
		if(realDiv==null) {
			throw new DivisaYaExisteException();
		}
		
		em.persist(div);
	}
	
	@Override
	public void modificarDivisa(Divisa div) throws DivisaNoExisteException {
		Divisa realDiv=em.find(Divisa.class, div.getAbreviatura());
		if(realDiv==null) {
			throw new DivisaNoExisteException();
		}
		
		em.merge(div);
	}
	
	@Override
	@Schedule(hour = "11")
	public void actualizarDivisas() {
		try {			
			List<Divisa> l=em.createQuery("SELECT d FROM Divisa d", Divisa.class).getResultList();
			List<String> abr=new ArrayList<>();

			for(Divisa d:l) {
				abr.add(d.getAbreviatura());
			}
			
			String petition="";
			int count=abr.size();
			for(String s:abr) {
				if(count==1) {
					petition=petition+s+"_EUR";
				}else {
					petition+=petition+s+"_EUR,";
				}
				count--;
			}
			URL url=new URL("https://free.currconv.com/api/v7/convert?q="+petition+"&compact=ultra&apiKey=582210348b40c517c856");
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");
			
			 BufferedReader in = new BufferedReader(
		             new InputStreamReader(connection.getInputStream()));
		     String inputLine;
		     StringBuffer response = new StringBuffer();
		     
		     while ((inputLine = in.readLine()) != null) {
		     	response.append(inputLine);
		     }
//		     JsonObject convertedObject = new Gson().fromJson(response.toString(), JsonObject.class);
//		     for(String s:convertedObject.keySet()) {
//		    	 System.out.println(convertedObject.get(s).getAsDouble());
//		     }
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	}
}
