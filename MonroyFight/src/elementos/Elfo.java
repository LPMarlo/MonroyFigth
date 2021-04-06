package elementos;

import logica.Const;

public class Elfo extends Jugador {

    public Elfo(char simbolo) {
        super(simbolo, Const.VELOCIDAD_ELFO, Const.MAGIA_ELFO, Const.FUERZA_ELFO);
    }
}
