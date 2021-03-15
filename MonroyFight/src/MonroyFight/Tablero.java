package MonroyFight;

import java.util.Random;

public class Tablero {

	//ATRIBUTOS
	public Object[][] tablero;
	public  Object gema;
	public Object pozo;
	private Object roca;

	//CONSTRUCTOR
	public Tablero(Personaje a, Personaje b) {
		this.tablero = new Object[Const.FILAS_TABLERO][Const.COLUMNAS_TABLERO];
		addObjetos(gema, Const.NUM_GEMAS);
		addObjetos(pozo, Const.NUM_POZOS);
		addObjetos(roca, Const.NUM_ROCAS);
	}

	//GENERAR OBJETOS EN TABLERO
	private void addObjetos(Object objeto, int cantidad) {
		for (int i = 0; i < cantidad; i++) {
			Random fr = new Random();
			int fila = fr.nextInt(Const.FILAS_TABLERO-1);

			Random cr = new Random();
			int columna = cr.nextInt(Const.COLUMNAS_TABLERO-1);

			if (tablero[fila][columna]==null) {
				tablero[fila][columna] = objeto;
			} else {
				i--;
			}
		}
	}

	private void addJugador(Personaje p){
		int columna;
		int fila;

		do {
			Random fr = new Random();
			fila = fr.nextInt(Const.FILAS_TABLERO-1);

			Random cr = new Random();
			columna = cr.nextInt(Const.COLUMNAS_TABLERO-1);

			if (tablero[fila][columna]==null) {
				tablero[fila][columna] = p;
			}
		} while (tablero[fila][columna]!=null);


	}
}
