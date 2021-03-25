package hr.fer.pavlic.dipl.stupovi;

public class SpojnaTockaIzolatora {
	
	private int idSti;
	private double x;
	private double y;
	private double z;
	
	public SpojnaTockaIzolatora(int idSti, double x, double y, double z) {
		super();
		this.idSti = idSti;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int getIdSti() {
		return idSti;
	}
	
	public void setIdSti(int idSti) {
		this.idSti = idSti;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof SpojnaTockaIzolatora)) {
			return false;
		}
		
		SpojnaTockaIzolatora other = (SpojnaTockaIzolatora) obj;
		
		return this.x == other.x && this.y == other.y && this.z == other.z;
	}

}
