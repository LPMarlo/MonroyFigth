package main;

import java.util.Arrays;
import java.util.Scanner;

import logica.*;

/**
 * @link https://github.com/LPMarlo/MonroyFigth
 * @author Marlo Lebrón Pareja
 * @author Fco Javier Torrejón Aragón
 */
public class MainJuego {

    public static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
    	
        try {
        	
            Juego juego = crearJuego();
            System.out.println(juego);
            System.out.println(juego.ordenJugadores());
            System.out.println(juego.datosJugadores());

            while (!juego.isTerminado()) {
            	
                int dado = juego.getNumeroMovimientosJugador();
                System.out.println("Le toca al jugador " + juego.getJugadorTurno() + ". El dado saca " + dado + " movimientos.");

                for (int i = 1; i <= dado && !juego.isTerminado() && !juego.isDead(); i++) {
                	
                    System.out.println(juego.moverJugador(solicitarMovimiento()));
                    System.out.println(juego);
                    System.out.println(juego.datosJugadores());
                    
                }
                
                juego.proximoJugador();
            }
            
            System.out.println("Juego terminado. El ganador es: " + juego.getGanador());
        } catch (JuegoException e) {
            System.out.println(e.getMessage());
        }
    }

    private static char solicitarMovimiento() {
    	
        char direccion;
        
        do {
            System.out.println("Indique a donde desea moverse: N-Norte, S-Sur, E-Este u O-Oeste");
            direccion = teclado.nextLine().toUpperCase().charAt(0);
        } while (direccion != 'N' && direccion != 'S' && direccion != 'E' && direccion != 'O');
        
        return direccion;
    }

    private static Juego crearJuego() throws JuegoException {
    	
        int numJugadores;

        do {
            System.out.println("Introduzca número de jugadores entre "+ Const.MIN_JUGADORES +  "  y " + Const.MAX_JUGADORES);
            numJugadores = Integer.parseInt(teclado.nextLine());
        } while (numJugadores < Const.MIN_JUGADORES || numJugadores > Const.MAX_JUGADORES);

        Juego juego = new Juego(Const.FILAS_TABLERO, Const.COLUMNAS_TABLERO, numJugadores);

        for (int i = 1; i <= numJugadores; i++) {

            TipoJugador tipo = solicitarTipoJugador();
            juego.crearJugador(tipo);

        }
        
        return juego;
    }

    private static TipoJugador solicitarTipoJugador() {

        TipoJugador tipo=null;
        boolean hayError;

        do {
            try {
                System.out.println("Elija el tipo de jugador:" + Arrays.toString(TipoJugador.values()));
                tipo = TipoJugador.valueOf(teclado.nextLine().toUpperCase());
                hayError = false;
            } catch (IllegalArgumentException e) {
                System.out.println("Tipo incorrecto");
                hayError = true;
            }
        } while (hayError);

        return tipo;
    }

}