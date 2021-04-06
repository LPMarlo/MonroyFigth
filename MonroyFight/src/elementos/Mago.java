package elementos;

import logica.Const;

public class Mago extends Jugador {

    public Mago(char simbolo) {
        super(simbolo, Const.VELOCIDAD_MAGO, Const.MAGIA_MAGO, Const.FUERZA_MAGO);
    }
}
