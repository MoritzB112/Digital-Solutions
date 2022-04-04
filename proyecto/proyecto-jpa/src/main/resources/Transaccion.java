import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="TRANSACCION") 
public class Transaccion {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID Unico", nullable=false)
	private Long id;
	
	@Column(name="fechaInstruccion", nullable=false)
	@Temporal(TemporalType.DATE)
	private Date fechaInstruccion;
	
	@Column(name="cantidad", nullable=false)
	private double cantidad;
	
	@Column(name="fechaEjecucion")
	@Temporal(TemporalType.DATE)
	private Date fechaEjecucion;
	
	/* Tipo = {Ingreso, Cargo} */
	@Column(name="Tipo", nullable=false)
	private String tipo;
	
	@Column(name="comision")
	private double comision;
	
	@Column(name="Internacional")
	private boolean internacional;
	
	
	public Transaccion(Long id, Date fechaInstruccion, double cantidad, Date fechaEjecucion, 
						String tipo, double comision, boolean internacional) {
		super();
		this.id = id;
		this.fechaInstruccion = fechaInstruccion;
		this.cantidad = cantidad;
		this.fechaEjecucion = fechaEjecucion;
		this.tipo = tipo;
		this.comision = comision;
		this.internacional = internacional;
	}
	
	public Transaccion() {
		
	}
	
	/* Getters and Setter methods */
	
	public Date getFechaInstruccion() {
		return fechaInstruccion;
	}
	public void setFechaInstruccion(Date fechaInstruccion) {
		this.fechaInstruccion = fechaInstruccion;
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}
	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public double getComision() {
		return comision;
	}
	public void setComision(double comision) {
		this.comision = comision;
	}
	public boolean isInternacional() {
		return internacional;
	}
	public void setInternacional(boolean internacional) {
		this.internacional = internacional;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaccion other = (Transaccion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Transaccion [id=" + id + ", fechaInstruccion=" + fechaInstruccion 
				+ ", cantidad=" + cantidad + ", fechaEjecucion=" + fechaEjecucion 
				+ ", Tipo=" + tipo + ", comision=" + comision + ",Internacional=" + internacional + "]";
	}
	
}