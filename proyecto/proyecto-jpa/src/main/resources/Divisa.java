import javax.persistence.Entity;

@Entity
public class Divisa {
	@Id
	private String abreviatura;
	
	@Column(nullable=false)
	private String nombre;
	private String simbolo;
	
	@Column(nullable=false)
	private Double CambioEuro;
	
	public Divisa() {
	}

}
