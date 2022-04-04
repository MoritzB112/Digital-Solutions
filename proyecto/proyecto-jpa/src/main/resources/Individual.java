import java.util.List;
import javax.persistence.*;


public class Individual extends Cliente {
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "apellido", nullable = false)
	private String apellido;
	
	@Column(name = "fecha_nacimiento") @Temporal(TemporalType.DATE)
	private Date fecha_nacimiento;
	
	public Individual() {
		
	}
}
