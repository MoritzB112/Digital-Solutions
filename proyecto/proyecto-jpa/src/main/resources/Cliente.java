import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "CLIENTE")
@Inheritance(strategy = InheritanceType.JOINED)
public class Cliente {
	@Id @GeneratedValue @Column(name = "id")
	private Long id;
	
	@Column(name = "identificacion",unique = true, nullable = false)
	private String identificacion;
	
	@Column(name = "tipo_cliente", nullable = false)
	private String tipo_cliente;
	
	@Column(name = "estado", nullable = false)
	private String estado;
	
	@Column(name = "fecha_alta", nullable = false) @Temporal(TemporalType.DATE)
	private Date fecha_alta;
	
	@Column(name = "fecha_baja") @Temporal(TemporalType.DATE)
	private Date fecha_baja;
	
	@Column(name = "direccion", nullable = false)
	private String direccion;
	
	@Column(name = "ciudad", nullable = false)
	private String ciudad;
	
	@Column(name = "codigoPostal", nullable = false)
	private int codigoPostal;
	
	@Column(name = "pais", nullable = false)
	private String pais;
	
	public Cliente() {
		
	}
	
	
}