package es.uma.proyecto.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Client {
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	private Nombre name;
	
	private String startPeriod;
	
	private String endPeriod;
	
	public Client() {
		//TODO Auto-generated constructor stub
	}

	public Nombre getName() {
		return name;
	}

	public void setName(Nombre name) {
		this.name = name;
	}

	public String getStartPeriod() {
		return startPeriod;
	}

	public void setStartPeriod(String startPeriod) {
		this.startPeriod = startPeriod;
	}

	public String getEndPeriod() {
		return endPeriod;
	}

	public void setEndPeriod(String endPeriod) {
		this.endPeriod = endPeriod;
	}
	
	public Date sacarEndPeriodD() {
		try {
			return formatter.parse(endPeriod);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Date sacarStartPeriodD() {
		try {
			return formatter.parse(startPeriod);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(endPeriod, name, startPeriod);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(endPeriod, other.endPeriod) && Objects.equals(name, other.name)
				&& Objects.equals(startPeriod, other.startPeriod);
	}

	@Override
	public String toString() {
		return "Client [name=" + name + ", startPeriod=" + startPeriod + ", endPeriod=" + endPeriod + "]";
	}

}
