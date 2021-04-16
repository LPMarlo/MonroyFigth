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
	private boolean dead;
    
    //CONSTRUCTOR
    public Juego(int alto, int ancho, int numJugadores) throws JuegoException {
    	if (alto<=0 || ancho<=0) {
			throw new JuegoException("Error. El alto y el ancho deben ser mayor que 0.");
		}
    	
    	if (numJugadores<Const.MIN_JUGADORES || numJugadores>Const.MAX_JUGADORES) {
			throw new JuegoException("Error. El numero de jugadores debe ser mayor que " + Const.MIN_JUGADORES + " y menor que " + Const.MAX_JUGADORES + ".");
		}
    	
        tablero = new Elemento[alto][ancho];
        jugadores = new Jugador[numJugadores];
        this.numJugadores = numJugadores;
        this.alto = alto;
        this.ancho = ancho;
        this.turnoJugador = 0;
        terminado = false;
        this.dead = false;
        addObjetos();
    }
    
    /**
     * Crea un jugador segun el tipo seleccionado y se le asigna un turno y posicion aleatoria.
     * @param tipo
     */
    public void crearJugador(TipoJugador tipo) {
    	
    	//VARIABLES
        Jugador jugador = null;
        int posicion = 0;
        boolean salir = false;
        int fila, columna;

        //Calcular primera posicion nula en el array de jugadores
        for (int i = 0; i < jugadores.length && !salir; i++) {
			if (jugadores[i]==null) {
				posicion = i;
				salir = true;
			}
		}

        //Fila y columna donde se añadirá el jugador en el tablero
        do {
            Random r1 = new Random();
            fila = r1.nextInt(alto);

            Random r2 = new Random();
            columna = r2.nextInt(ancho);
        } while (tablero[fila][columna]!=null);

        //Crear Jugador dependiendo del tipo
        if (tipo == TipoJugador.ELFO)           jugador = new Elfo(JUGADORES.charAt(posicion));
        else if (tipo == TipoJugador.GUERRERO)  jugador = new Guerrero(JUGADORES.charAt(posicion));
        else if (tipo == TipoJugador.MAGO)      jugador = new Mago(JUGADORES.charAt(posicion));
        else if (tipo == TipoJugador.OGRO)      jugador = new Ogro(JUGADORES.charAt(posicion));
        
        //Se añade el jugador en el array de jugadores
        jugadores[posicion] = jugador;
        
        //Se añade el jugador al tablero
        tablero[fila][columna] = jugador;
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
     * Método que incluye los posibles casos donde se vaya a mover el jugador.
     * @param direccion
     * @return String con la información del movimiento
     */
    public String moverJugador(char direccion) {
    	
    	//VARIABLES
    	String info = null;
    	int nextFila = 0, nextColumna = 0;
        int filaActual = 0, columnaActual = 0;
        int jugadorAAtacar = -1;
        boolean salir = false;
        boolean mover = false;
        
        //Para mover a un jugador,  primero buscamos en que fila y columna se encuentra
        for (int i = 0; i < this.alto && !salir; i++) {
            for (int j = 0; j < this.ancho && !salir; j++) {
                if (tablero[i][j]!=null && tablero[i][j]==jugadores[turnoJugador]) {
                    filaActual = i;
                    columnaActual = j;
                    nextFila = i;
                    nextColumna = j;
                    salir = true;
                }
            }
        }
        //Segun la direccion selecionada la columna o la fila una casilla
        if (direccion == ESTE) {
          if (nextColumna < this.ancho-1) nextColumna = nextColumna+1;
          else nextColumna = 0;
        }
        else if (direccion == OESTE) {
            if (nextColumna > 0) nextColumna = nextColumna-1;
            else nextColumna = ancho-1;
        }
        else if (direccion == NORTE) {
            if (nextFila > 0) nextFila = nextFila-1;
            else nextFila = alto-1;
        }
        else if (direccion == SUR) {
            if (nextFila < this.alto-1) nextFila = nextFila+1;
            else nextFila = 0;
        }
        
        //Bucle para calcular la posicion del posible jugador en el array de jugadores
        for (int i = 0; i < jugadores.length; i++) {
            if (jugadores[i]!=null && jugadores[i]==tablero[nextFila][nextColumna]) {
            	jugadorAAtacar = i;
			}
        }

        if (tablero[nextFila][nextColumna] == null) { //Si la nueva casilla está vacia, el jugador solo se mueve
            info = "Movimiento realizado.";
            mover = true;
        }
        
        //Si hay un objeto, se añade al jugador
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
        
        //Si hay un obstaculo, se supera dependiendo de las condiciones
        else if (tablero[nextFila][nextColumna] instanceof Roca) { 
            if (jugadores[turnoJugador].getGemas()>0) { // En el caso de la roca, el jugador solo se puede mover si tiene al menos 1 gema
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

            if (magiaJugador > magiaPozo) { // En el caso de pozo, solo se supera si aleatoriamente la magia del jugador supera a la del pozo
                info = "El jugador " + jugadores[turnoJugador].toString() + " ha podido pasar sobre el pozo.";
                mover = true;
            } else {
                info = "El jugador " + jugadores[turnoJugador].toString() + " no ha podido pasar sobre el pozo.";
            }
        }

        //ATAQUE
        else if(jugadores[jugadorAAtacar]==tablero[nextFila][nextColumna]) {
            Random a = new Random();
            int fa = a.nextInt(jugadores[turnoJugador].getFuerza())+1;
            
            Random b = new Random();
            int fb = b.nextInt(jugadores[jugadorAAtacar].getFuerza())+1;

            if (fa>fb) { //Caso en el que el jugador del turno gana
                if (jugadores[jugadorAAtacar].getPociones()>0) {
                    jugadores[jugadorAAtacar].removePocion();
                    info = "El jugador " + tablero[nextFila][nextColumna].toString() + " ha utilizado una pocion.";
                }
                else if (jugadores[jugadorAAtacar].getDinero()>0) {
                    jugadores[turnoJugador].winDinero(jugadores[jugadorAAtacar].getDinero());
                    jugadores[jugadorAAtacar].loseDinero();
                    info = "El jugador " + jugadores[jugadorAAtacar].toString() + " ha perdido todo su dinero.";
                }
                else {
                    info = "El jugador " + jugadores[jugadorAAtacar].toString() + " ha sido eliminado.";
                    jugadores[jugadorAAtacar] = null;
                    tablero[nextFila][nextColumna] = null;
                    mover = true; // Si el jugador del turno elimina al otro, se mueve
                }
            } else if (fb>fa) { //Caso en el que el jugador que recibe el ataque gana
                if (jugadores[turnoJugador].getPociones()>0) {
                    ((Jugador) jugadores[turnoJugador]).removePocion();
                    info = "El jugador " + jugadores[turnoJugador].toString() + " ha utilizado una pocion.";
                }
                else if (jugadores[jugadorAAtacar].getDinero()>0) {
                    jugadores[jugadorAAtacar].winDinero(((Jugador) jugadores[turnoJugador]).getDinero());
                    ((Jugador) jugadores[turnoJugador]).loseDinero();
                    info = "El jugador " + jugadores[turnoJugador].toString() + " ha perdido todo su dinero.";
                }
                else {
                    info = "El jugador " + jugadores[turnoJugador].toString() + " ha sido eliminado.";
                    jugadores[turnoJugador] = null;
                    tablero[filaActual][columnaActual] = null;
                    this.dead = true; // Si el jugador que recibe el ataque elimina al del turno, no se mueve y muere el que ataca
                }
            }
            else info = "Empate.";
        }
        
        //Una vez pasados los filtros, el jugador en turno se mueve
        if (mover) {
            tablero[nextFila][nextColumna] = jugadores[turnoJugador];
            tablero[filaActual][columnaActual] = null;
        }
        
        //Casos en los que se termina el juego
        int jugadoresEnTablero = 0;
        for (int i = 0; i < jugadores.length; i++) {
			if (jugadores[i]!=null) jugadoresEnTablero++;
		}
        
        if (jugadoresEnTablero<=1) this.terminado = true;
        else if (jugadorAAtacar!=-1) {
        	if (jugadores[jugadorAAtacar]!=null) {
        	if (jugadores[jugadorAAtacar].getDinero()==Const.NUM_DINERO) this.terminado = true;				
        	}
    	}
        else if (jugadores[turnoJugador]!=null) {
        	if (jugadores[turnoJugador].getDinero()==Const.NUM_DINERO) {
        		turnoJugador--;
        		this.terminado = true;				
        	}
		}
        
        return info;
    }
    
    /**
     * Cuando un jugador se muere termina su turno con este metodo en true
     * @return boolean si ha muerto o no
     */
    public boolean isDead() {
    	return this.dead;
    }
    
    /**
     * String con el jugador en turno.
     * @return jugador en turno.
     */
    public String getJugadorTurno() {
        return jugadores[turnoJugador].toString();
    }
    
    /**
     * Cambia de turno al siguiente jugador.
     */
    public void proximoJugador() {
    	do {
    		turnoJugador++;
            if (turnoJugador==numJugadores) {
                turnoJugador = 0;
            }
		} while (jugadores[turnoJugador]==null);
    	this.dead = false;
    }
    
    /**
     * String con los objetos que tienen los jugadores.
     * @return String información de los jugadores.
     */
    public String datosJugadores() {
    	
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < jugadores.length; i++) {
            if (jugadores[i] != null) sb.append(jugadores[i].getInfo() + "\n");
        }
        
        return sb.toString();
    }

    /**
     * Una vez que termina el juego, se muestra el ganador.
     * @return simbolo del ganador.
     */
    public String getGanador() {
        return jugadores[turnoJugador].toString();
    }
    
    /**
     * Método para saber si ha terminado el juego o no.
     * @return boolean terminado
     */
    public boolean isTerminado() {
        return terminado;
    }
    
    /**
     * String con el tablero.
     */
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
    
    /**
     * Se ordena los jugadores de forma aleatoria.
     * @return nombre de los jugadores por orden de creación.
     */
	public String ordenJugadores() {
		
		StringBuilder sb = new StringBuilder();
		Jugador[] o = new Jugador[numJugadores];
		int numR = 0;
		
		for (int i = 0; i < jugadores.length; i++) {
			do {
				Random r = new Random();
				numR = r.nextInt(jugadores.length);
			} while (o[numR]!=null);
			
			o[numR] = jugadores[i];
			sb.append("Jugador " + (i+1) + ": " + jugadores[i] + "\n");
		}
		
		jugadores = o;
		
		return sb.toString();
	}
}
