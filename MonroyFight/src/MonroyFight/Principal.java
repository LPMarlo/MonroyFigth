package MonroyFight;

import MonroyFight.Personaje;

public class Principal {
    public static void main(String[] args) {
        Personaje a = new Elfo();
        Personaje b = new Ogro();
        Tablero tablero1= new Tablero(a, b);

        for (int i = 0; i < Const.FILAS_TABLERO; i++) {
            for (int j = 0; j < Const.COLUMNAS_TABLERO; j++) {
                if (tablero1.tablero[i][j]==tablero1.gema) {
                    System.out.print("G");
                } else {
                    if (tablero1.tablero[i][j]==tablero1.pozo){
                        System.out.print("P");
                    }else {
                        System.out.print(" ");
                    }
                }
            }
            System.out.println();
        }

    }
}
