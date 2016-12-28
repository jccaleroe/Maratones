package DataStructures.unal.datastructures.taller3;

public class UserLoans implements Comparable<UserLoans> {
    int cedula;
    long numLoans;

    public UserLoans(int cedula, long numLoans) {
        this.cedula = cedula;
        this.numLoans = numLoans;
    }

    @Override
    public String toString() {
        return "UserLoans [cedula=" + cedula + ", numLoans=" + numLoans + "]";
    }

    @Override
    public int compareTo(UserLoans o) {
        return Long.compare(numLoans, o.numLoans);
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public long getNumLoans() {
        return numLoans;
    }

    public void setNumLoans(long numLoans) {
        this.numLoans = numLoans;
    }
}
