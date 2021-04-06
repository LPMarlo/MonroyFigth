package elementos;

import java.util.Objects;

public abstract class Jugador extends Elemento {

    //Objetos que puede recoger.
    private int gemas;
    private int pociones;
    private int dinero;

    //Características de cada personaje.
    private int velocidad;
    private int magia;
    private int fuerza;

    //Constructor
    public Jugador(char simbolo, int velocidad, int magia, int fuerza) {
        super(simbolo);
        this.velocidad = velocidad;
        this.magia = magia;
        this.fuerza = fuerza;
        gemas = 0;
        pociones = 0;
        dinero = 0;
    }

    public void addGema() {
        gemas++;
    }

    public void addPocion() {
        pociones++;
    }

    public void addDinero() {
        dinero++;
    }

    public void removeGema() {
        gemas--;
    }

    public void removePocion() {
        pociones--;
    }

    public int getGemas() {
        return gemas;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public int getMagia() {
        return magia;
    }

    public int getFuerza() {
        return fuerza;
    }

    public int getDinero() {
        return dinero;
    }

    public void winDinero(int dinero) {
        this.dinero = dinero;
    }

    public void loseDinero (){
        this.dinero = 0;
    }
    public int getPociones() {
        return pociones;
    }

    public String getInfo() {
        return "Jugador " + super.toString() +
                ": Gemas:" + gemas +
                " Pociones:" + pociones +
                " Dinero:" + dinero;
    }

    public String toString() {
        return "" + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return gemas == jugador.gemas && pociones == jugador.pociones && dinero == jugador.dinero && velocidad == jugador.velocidad && magia == jugador.magia && fuerza == jugador.fuerza;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gemas, pociones, dinero, velocidad, magia, fuerza);
    }
}
