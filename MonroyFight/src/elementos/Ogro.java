package elementos;

import logica.Const;

public class Ogro extends Jugador {

    public Ogro(char simbolo) {
        super(simbolo, Const.VELOCIDAD_OGRO, Const.MAGIA_OGRO, Const.FUERZA_OGRO);
    }
}
