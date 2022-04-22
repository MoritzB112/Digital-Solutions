package es.uma.proyecto;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.net.ssl.HttpsURLConnection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DivisasEJB implements GestionDivisas {
	
	@PersistenceContext(name="proyecto-ejb")
	private EntityManager em;
	
	@Override
	public void insertarDivisa(Divisa div) {
		if(!em.contains(div)) {
			em.persist(div);
		}
	}
	
	@Override
	public void modificarDivisa(Divisa div) {
		Divisa realDiv=em.find(Divisa.class, div.getAbreviatura());
		if(realDiv!=null) {
			em.merge(div);
		}
		
	}
	
	@Override
	public void eliminarDivisa(Divisa div) {
		Divisa realDiv=em.find(Divisa.class, div.getAbreviatura());
		if(realDiv!=null) {
			em.remove(realDiv);
		}
	}
	
	@Override
	@Schedule(hour = "11")
	public void actualizarDivisas() {
		//acceder a api y acturalizar todas las divisas que hay
		try {
			URL url=new URL("https://api.currconv.com/api/v7/convert?q=USD_PHP,PHP_USD&compact=ultra&apiKey=582210348b40c517c856");
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "text/json");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    
	}
}
