package elementos;

import logica.Const;

public class Guerrero extends Jugador {

    public Guerrero(char simbolo) {
        super(simbolo, Const.VELOCIDAD_GUERRERO, Const.MAGIA_GUERRERO, Const.FUERZA_GUERRERO);
    }
}
