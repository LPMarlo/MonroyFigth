package MonroyFight;

public abstract class Personaje {
	
	//ATRIBUTOS
	private int gemas;
	private int pociones;
	private int magia;
	private int fuerza;
	private int velocidad;
	
	//CONSTRUCTOR
	public Personaje(int magia, int fuerza, int velocidad) {
		this.magia = magia;
		this.fuerza = fuerza;
		this.velocidad = velocidad;
	}
	
	//GETTERS
	public int getGemas() {
		return gemas;
	}

	public int getPociones() {
		return pociones;
	}

	public int getMagia() {
		return magia;
	}

	public int getFuerza() {
		return fuerza;
	}

	public int getVelocidad() {
		return velocidad;
	}
	
	//SETTERS
	protected void setGemas(int gemas) {
		this.gemas = gemas;
	}

	protected void setPociones(int pociones) {
		this.pociones = pociones;
	}

	protected void setMagia(int magia) {
		this.magia = magia;
	}

	protected void setFuerza(int fuerza) {
		this.fuerza = fuerza;
	}

	protected void setVida(int velocidad) {
		this.velocidad = velocidad;
	}

	//TOSTRING
	public String toString() {
		return "Personaje [gemas=" + gemas + ", pociones=" + pociones + ", magia=" + magia + ", fuerza=" + fuerza
				+ ", velocidad=" + velocidad + "]";
	}
	
	
	
}
