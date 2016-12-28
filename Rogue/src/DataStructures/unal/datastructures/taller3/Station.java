package DataStructures.unal.datastructures.taller3;

import java.io.Serializable;

public class Station implements Serializable {
    private static final long serialVersionUID = -7592232588621335612L;
    private String id;
    private String name;
    private String ubicacion;
    private int capacity;
    private int motos;

    public Station(String id, String name, String u, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        ubicacion = u;
        motos = 0;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", nombre = " + name + ", Ubicacion: " + ubicacion + ", capacidad = "
                + capacity + ", motos a disposicion = " + motos + "]";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getMotos() {
        return motos;
    }

    public void setMotos(int motos) {
        this.motos = motos;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
