package DataStructures.unal.datastructures.taller3;

import java.io.Serializable;

public abstract class Human implements Serializable {

    private static final long serialVersionUID = -619827683405153375L;
    private int cedula;
    private String nombre;

    //Constructor
    public Human(int cedula, String nombre) {
        this.cedula = cedula;
        this.nombre = nombre;
    }

    //Getters and Setters
    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //To String
    @Override
    public String toString() {
        return "Person [cedula=" + cedula + ", nombre=" + nombre + "]";
    }
}