package Principal;

import MonroyFight.*;

public class Juego {
	
	//ATRIBUTOS
	private Elemento[][] tablero;
	private Personaje[][] personajes;
	private int numJugadores;
	private boolean finished;
	
	//CONSTRUCTOR
	public Juego(int numJugadores, Personaje[][] personajes) {
		this.tablero = new Elemento[Const.FILAS_TABLERO][Const.COLUMNAS_TABLERO];
		this.personajes = personajes;
		this.numJugadores = numJugadores;
		this.finished = false;
	}
	
	
	
}
