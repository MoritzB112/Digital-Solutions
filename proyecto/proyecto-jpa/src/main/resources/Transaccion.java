import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import javax.persistence.Id;

@Entity
public class Transaccion {
	@Id
	@GeneratedValue
	private Long ID_unico;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date fechaInstruccion;
	
	@Column(nullable=false)
	private Long cantidad;
	
	@Temporal(TemporalType.DATE)
	private Date fechaEjecucion;
	
	@Column(nullable=false)
	private String Tipo;
	private Integer comision;
	private Boolean internacional;
	
	public Transaccion() {	
	}

}
