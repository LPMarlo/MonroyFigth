package elementos;

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
    
    //Se añade un objeto
    public void addGema() {
        gemas++;
    }

    public void addPocion() {
        pociones++;
    }

    public void addDinero() {
        dinero++;
    }
    
    //Se elimina un objeto
    public void removeGema() {
        gemas--;
    }

    public void removePocion() {
        pociones--;
    }
    
    //Jugador gana o pierde todo el dinero
    public void winDinero(int dinero) {
        this.dinero = dinero;
    }

    public void loseDinero (){
        this.dinero = 0;
    }
    
    public int getGemas() {
        return gemas;
    }
    
    //GETTERS
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
}
