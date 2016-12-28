package DataStructures.unal.datastructures.taller3;

import java.io.Serializable;

public class Moto implements Serializable {
    private static final long serialVersionUID = -1621379814286764147L;
    private long barCode;
    private String location;
    private String brand;
    private String plate;
    private String state;
    private int cost;

    //Constructor
    public Moto(long barCode, String location, String brand, String placa,
                String state, int cost) {
        this.barCode = barCode;
        this.location = location;
        int aux = MotoRUN.getStations().get(location).getMotos();
        MotoRUN.getStations().get(location).setMotos(++aux);
        this.brand = brand;
        plate = placa;
        this.state = state;
        this.cost = cost;
    }

    // To String
    @Override
    public String toString() {
        return "Moto [barCode=" + barCode + ", ubicacion=" + location
                + ", marca=" + brand + ", placa=" + plate + ", stado=" + state + "]";
    }

    // Setters and Getters
    public long getBarCode() {
        return barCode;
    }

    public void setBarCode(long barCode) {
        this.barCode = barCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        SpaceHashTable<String, Station> estaciones = MotoRUN.getStations();
        int aux = estaciones.get(this.location).getMotos();
        estaciones.get(this.location).setMotos(--aux);
        this.location = location;
        aux = estaciones.get(location).getMotos();
        estaciones.get(location).setMotos(++aux);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String placa) {
        plate = placa;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
