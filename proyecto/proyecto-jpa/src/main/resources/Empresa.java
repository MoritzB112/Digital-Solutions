import java.util.List;
import javax.persistence.*;

public class Empresa extends Cliente {
	
	@Column(name = "razon_social", nullable = false)
	private String razon_social;
	
	public Empresa() {
	
	}
}
