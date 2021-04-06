package elementos;

public abstract class Personaje extends Elemento {
	
	//ATRIBUTOS
	private int gemas;
	private int pociones;
	private int magia;
	private int fuerza;
	private int velocidad;
	
	//CONSTRUCTOR
	public Personaje(char simbolo, int magia, int fuerza, int velocidad) {
		super(simbolo);
		this.magia = magia;
		this.fuerza = fuerza;
		this.velocidad = velocidad;
		gemas = 0;
		pociones = 0;
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

	//TOSTRING
	public String toString() {
		return "Personaje [gemas=" + gemas + ", pociones=" + pociones + ", magia=" + magia + ", fuerza=" + fuerza
				+ ", velocidad=" + velocidad + "]";
	}
	
	
	
}
