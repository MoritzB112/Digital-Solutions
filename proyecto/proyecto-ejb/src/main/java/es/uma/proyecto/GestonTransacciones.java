package es.uma.proyecto;

import java.util.List;

public interface GestonTransacciones {
	
	public void crearTransaccion(Cuenta orig, Cuenta dest);
	
	public List<Transaccion> sacarTransacciones();
	
}
