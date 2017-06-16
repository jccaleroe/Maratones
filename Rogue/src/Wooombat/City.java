package Wooombat;


public class City implements Comparable<City>{

    private int id;
    private String name;

    @Override
    public int hashCode(){
        return id;
    }

    @Override
    public int compareTo(City city){
        return id - city.id;
    }
}
