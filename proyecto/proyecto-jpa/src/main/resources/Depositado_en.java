import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Depositado_en {

	@Column(nullable=false)
	private Long saldo;
	
	public Depositado_en() {
	}
}

