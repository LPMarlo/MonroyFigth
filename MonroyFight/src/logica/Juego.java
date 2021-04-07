package logica;

import elementos.*;
import static logica.Const.*;
import java.util.Random;


public class Juego {

    //ATRIBUTOS
    private Elemento[][] tablero;
    private Jugador[] jugadores;
    private int numJugadores;
    private int turnoJugador;
    private int alto;
    private int ancho;
    private boolean terminado;

    public Juego(int alto, int ancho, int numJugadores) {
        tablero = new Elemento[alto][ancho];
        jugadores = new Jugador[numJugadores];
        this.numJugadores = numJugadores;
        this.alto = alto;
        this.ancho = ancho;
        terminado = false;
        addObjetos();
        this.turnoJugador = 0;
    }

    /**
     * Crea un jugador segun el tipo seleccionado y recibe un turno y posicion aleatoria.
     * @param tipo
     */
    public void crearJugador(TipoJugador tipo) {
        Jugador jugador = null;
        int posicion;
        int fila, columna;

        //Posicion en jugadores
        do {
            Random r = new Random();
            posicion = r.nextInt(numJugadores);
        } while (jugadores[posicion]!=null);

        //Posicion en tablero
        do {
            Random r1 = new Random();
            fila = r1.nextInt(alto);

            Random r2 = new Random();
            columna = r2.nextInt(ancho);
        } while (tablero[fila][columna]!=null);

        //Crear Jugador
        if (tipo == TipoJugador.ELFO) jugador = new Elfo(JUGADORES.charAt(posicion));
        else if (tipo == TipoJugador.GUERRERO) jugador = new Guerrero(JUGADORES.charAt(posicion));
        else if (tipo == TipoJugador.MAGO)  jugador = new Mago(JUGADORES.charAt(posicion));
        else if (tipo == TipoJugador.OGRO) jugador = new Ogro(JUGADORES.charAt(posicion));

        jugadores[posicion] = jugador;
        tablero[fila][columna] = jugador;
    }

    /**
     * @return si ha terminado
     */
    public boolean isTerminado() {
        return terminado;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append("-------------------------------------------------------------\n");
            for (int j = 0; j < 10; j++) {
                if (tablero[i][j] == null) {
                    sb.append("|     ");
                } else {
                    sb.append("|  " + (tablero[i][j]).toString() + "  ");
                }
            }
            sb.append("|\n");
        }
        sb.append("-------------------------------------------------------------");

        return sb.toString();
    }

    /**
     * String con los datos de todos los jugadores.
     * @return inforrmacion
     */
    public String datosJugadores() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < jugadores.length; i++) {
            if (jugadores[i] != null) sb.append((i+1) + ". " + jugadores[i].getInfo() + "\n");
        }
        return sb.toString();
    }

    /**
     * Obtiene un numero aleatorio teniendo en cuenta la velocidad del jugador.
     * @return numero de movimientos
     */
    public int getNumeroMovimientosJugador() {
        int dado = 0;
        if (jugadores[turnoJugador] instanceof Elfo){
            Random num = new Random();
            dado = num.nextInt(((Elfo) jugadores[turnoJugador]).getVelocidad())+1;
        }
        else if (jugadores[turnoJugador] instanceof Guerrero){
            Random num = new Random();
            dado = num.nextInt(((Guerrero) jugadores[turnoJugador]).getVelocidad())+1;
        }
        else if (jugadores[turnoJugador] instanceof Mago){
            Random num = new Random();
            dado = num.nextInt(((Mago) jugadores[turnoJugador]).getVelocidad())+1;
        }
        else if (jugadores[turnoJugador] instanceof Ogro){
            Random num = new Random();
            dado = num.nextInt(((Ogro) jugadores[turnoJugador]).getVelocidad())+1;
        }
        return dado;
    }

    /**
     * @return informacion del jugador en el turno.
     */
    public String getJugadorTurno() {
        return jugadores[turnoJugador].toString();
    }

    /**
     * Cambio de turno.
     */
    public void proximoJugador() {
    	do {
    		turnoJugador++;
            if (turnoJugador>=jugadores.length) {
                turnoJugador = 0;
            }
		} while (jugadores[turnoJugador]==null);
    }

    /**
     * Lógica del movimiento en el tablero. Teniendo en cuenta todos los posibles casos.
     * @param direccion
     * @return
     */
    public String moverJugador(char direccion) {
        int nextFila = 0, nextColumna = 0;
        int filaActual = 0, columnaActual = 0;
        boolean salir = false;
        String info = null;
        boolean mover = false;
        int jugadorAAtacar = 0;

        for (int i = 0; i < this.alto && !salir; i++) {
            for (int j = 0; j < this.ancho && !salir; j++) {
                if (tablero[i][j]!=null && tablero[i][j].equals(jugadores[turnoJugador])) {
                    filaActual = i;
                    columnaActual = j;
                    salir = true;
                }
            }
        }
        
        if (direccion == ESTE) {
          if (columnaActual < this.ancho-1) nextColumna = columnaActual+1;
          else nextColumna = 0;
        }
        else if (direccion == OESTE) {
            if (columnaActual > 0) nextColumna = columnaActual-1;
            else nextColumna = ancho-1;
        }
        else if (direccion == NORTE) {
            if (filaActual > 0) nextFila = filaActual-1;
            else nextFila = alto-1;
        }
        else if (direccion == SUR) {
            if (filaActual < this.alto-1) nextFila = filaActual+1;
            else nextFila = 0;
        }
        
        for (int i = 0; i < jugadores.length; i++) {
            if (jugadores[i]!=null && jugadores[i].equals(tablero[nextFila][nextColumna])) {
            	jugadorAAtacar = i;
			}
        }

        if (tablero[nextFila][nextColumna] == null) {
            info = "Movimiento realizado.";
            mover = true;
        }
        else if (tablero[nextFila][nextColumna] instanceof Gema) {
            jugadores[turnoJugador].addGema();
            info = "El jugador " + jugadores[turnoJugador].toString() + " ha obtenido 1 gema.";
            mover = true;
        }
        else if (tablero[nextFila][nextColumna] instanceof Pocion) {
            jugadores[turnoJugador].addPocion();
            info = "El jugador " + jugadores[turnoJugador].toString() + " ha obtenido 1 pocion.";
            mover = true;
        }
        else if (tablero[nextFila][nextColumna] instanceof Dinero) {
            jugadores[turnoJugador].addDinero();
            info = "El jugador " + jugadores[turnoJugador].toString() + " ha obtenido 1 dinero.";
            mover = true;
        }
        else if (tablero[nextFila][nextColumna] instanceof Roca) {
            if (jugadores[turnoJugador].getGemas()>0) {
                jugadores[turnoJugador].removeGema();
                info = "El jugador " + jugadores[turnoJugador].toString() + " ha podido pasar sobre la roca.";
                mover = true;
            }
            else {
                info = "El jugador " + jugadores[turnoJugador].toString() + " no ha podido pasar sobre la roca.";
            }
        }
        else if (tablero[nextFila][nextColumna] instanceof Pozo) {
            Random jr = new Random();
            int magiaJugador = jr.nextInt(jugadores[turnoJugador].getMagia())+1;

            Random pr = new Random();
            int magiaPozo = pr.nextInt(MAGIA_POZO)+1;

            if (magiaJugador > magiaPozo) {
                info = "El jugador " + jugadores[turnoJugador].toString() + " ha podido pasar sobre el pozo.";
                mover = true;
            } else {
                info = "El jugador " + jugadores[turnoJugador].toString() + " no ha podido pasar sobre el pozo.";
            }
        }

        //BATALLA
        else if(jugadores[jugadorAAtacar].equals(tablero[nextFila][nextColumna])) {
            Random a = new Random();
            int fa = a.nextInt(jugadores[turnoJugador].getFuerza())+1;
            
            Random b = new Random();
            int fb = b.nextInt(jugadores[jugadorAAtacar].getFuerza())+1;

            if (fa>fb) {
                if (jugadores[jugadorAAtacar].getPociones()>0) {
                    jugadores[jugadorAAtacar].removePocion();
                    info = "El jugador " + tablero[nextFila][nextColumna].toString() + " ha utilizado una pocion.";
                }
                else if (jugadores[jugadorAAtacar].getDinero()>0) {
                    jugadores[turnoJugador].winDinero(jugadores[jugadorAAtacar].getDinero());
                    jugadores[jugadorAAtacar].loseDinero();
                }
                else {
                    info = "El jugador " + jugadores[jugadorAAtacar].toString() + " ha sido eliminado.";
                    jugadores[jugadorAAtacar] = null;
                    tablero[nextFila][nextColumna] = null;
                    mover = true;
                }
            } else if (fb>fa) {
                if (jugadores[turnoJugador].getPociones()>0) {
                    ((Jugador) jugadores[turnoJugador]).removePocion();
                    info = "El jugador " + jugadores[turnoJugador].toString() + " ha utilizado una pocion.";
                }
                else if (jugadores[jugadorAAtacar].getDinero()>0) {
                    jugadores[jugadorAAtacar].winDinero(((Jugador) jugadores[turnoJugador]).getDinero());
                    ((Jugador) jugadores[turnoJugador]).loseDinero();
                }
                else {
                    info = "El jugador " + jugadores[turnoJugador].toString() + " ha sido eliminado.";
                    jugadores[turnoJugador] = null;
                    tablero[filaActual][columnaActual] = null;
                }
            }
            int jugadoresEnTablero = 0;
            for (int i = 0; i < jugadores.length; i++) {
    			if (jugadores[i]!=null) {
    				jugadoresEnTablero++;
    			}
    		}
            if (jugadoresEnTablero<=1) {
    			this.terminado = true;
    		}
        }
        if (mover) {
            tablero[nextFila][nextColumna] = jugadores[turnoJugador];
            tablero[filaActual][columnaActual] = null;
        }
        return info;
    }

    /**
     * @return simbolo del ganador.
     */
    public String getGanador() {
        return jugadores[turnoJugador].toString();
    }


    //MÉTODOS INTERNOS

    /**
     * Añade los objetos al tablero de forma aleatoria
     * con la cantidad establecida en las constantes.
     */
    private void addObjetos() {
        //OBJETOS A AÑADIR
        Dinero dinero = new Dinero();
        Gema gema = new Gema();
        Pocion pocion = new Pocion();
        Pozo pozo = new Pozo();
        Roca roca = new Roca();

        for (int i = 0; i < Const.NUM_DINERO; i++) {
            Random fr = new Random();
            int fila = fr.nextInt(Const.COLUMNAS_TABLERO);

            Random cr = new Random();
            int columna = cr.nextInt(Const.COLUMNAS_TABLERO);

            if (tablero[fila][columna] == null) {
                tablero[fila][columna] = dinero;
            } else {
                i--;
            }
        }
        for (int i = 0; i < Const.NUM_GEMAS; i++) {
            Random fr = new Random();
            int fila = fr.nextInt(Const.COLUMNAS_TABLERO);

            Random cr = new Random();
            int columna = cr.nextInt(Const.COLUMNAS_TABLERO);

            if (tablero[fila][columna] == null) {
                tablero[fila][columna] = gema;
            } else {
                i--;
            }
        }
        for (int i = 0; i < Const.NUM_POCION; i++) {
            Random fr = new Random();
            int fila = fr.nextInt(Const.COLUMNAS_TABLERO);

            Random cr = new Random();
            int columna = cr.nextInt(Const.COLUMNAS_TABLERO);

            if (tablero[fila][columna] == null) {
                tablero[fila][columna] = pocion;
            } else {
                i--;
            }
        }
        for (int i = 0; i < Const.NUM_POZOS; i++) {
            Random fr = new Random();
            int fila = fr.nextInt(Const.COLUMNAS_TABLERO);

            Random cr = new Random();
            int columna = cr.nextInt(Const.COLUMNAS_TABLERO);

            if (tablero[fila][columna] == null) {
                tablero[fila][columna] = pozo;
            } else {
                i--;
            }
        }
        for (int i = 0; i < Const.NUM_ROCAS; i++) {
            Random fr = new Random();
            int fila = fr.nextInt(Const.COLUMNAS_TABLERO);

            Random cr = new Random();
            int columna = cr.nextInt(Const.COLUMNAS_TABLERO);

            if (tablero[fila][columna] == null) {
                tablero[fila][columna] = roca;
            } else {
                i--;
            }
        }
    }
}
