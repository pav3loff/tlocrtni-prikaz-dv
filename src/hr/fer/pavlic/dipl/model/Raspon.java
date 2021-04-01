package hr.fer.pavlic.dipl.model;

public class Raspon {
	
	private int idStPoc;
	private int idStKraj;
	
	public Raspon(int idStPoc, int idStKraj) {
		super();
		this.idStPoc = idStPoc;
		this.idStKraj = idStKraj;
	}

	public int getIdStPoc() {
		return idStPoc;
	}
	
	public void setIdStPoc(int idStPoc) {
		this.idStPoc = idStPoc;
	}
	
	public int getIdStKraj() {
		return idStKraj;
	}
	
	public void setIdStKraj(int idStKraj) {
		this.idStKraj = idStKraj;
	}

}
