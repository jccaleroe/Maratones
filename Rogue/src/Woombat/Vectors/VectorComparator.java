package Woombat.Vectors;

class VectorComparator implements Comparable<VectorComparator> {

    private double distance;
    String value;

    VectorComparator(String value, double distance){
        this.value = value;
        this.distance = distance;
    }

    @Override
    public int compareTo(VectorComparator o){
        return Double.compare(distance, o.distance);
    }
}
