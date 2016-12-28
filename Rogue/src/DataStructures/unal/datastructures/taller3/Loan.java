package DataStructures.unal.datastructures.taller3;

import java.io.*;
import java.util.Calendar;

public class Loan implements Serializable {
    private static final long serialVersionUID = 193651429626730178L;
    private int user;
    private Long moto;
    private String stationA;
    private String stationB;
    private Long timeA;
    private Long timeB;
    private Boolean sancion;

    public Loan(int user, long moto, String stationA, String stationB,
                Long timeA) {
        this.user = user;
        this.moto = moto;
        this.stationA = stationA;
        this.stationB = stationB;
        this.timeA = timeA;
        sancion = false;
    }

    @Override
    public int hashCode() {
        int result = user + moto.hashCode() + timeA.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nLoan [user = " + user + ", moto = " + moto + ", stationA = "
                + stationA + ", stationB = " + stationB + ", timeA = ");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeA);
        builder.append(cal.getTime() + ", timeB = ");
        if (timeB == null)
            builder.append("No ha llegado" + ", sancionado = " + sancion + "]");
        else {
            cal.setTimeInMillis(timeB);
            builder.append(cal.getTime() + ", sancionado = " + sancion + "]\n");
        }
        return new String(builder);
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public long getMoto() {
        return moto;
    }

    public void setMoto(long moto) {
        this.moto = moto;
    }

    public String getStationA() {
        return stationA;
    }

    public void setStationA(String stationA) {
        this.stationA = stationA;
    }

    public String getStationB() {
        return stationB;
    }

    public void setStationB(String stationB) {
        this.stationB = stationB;
    }

    public Long getTimeA() {
        return timeA;
    }

    public void setTimeA(Long timeA) {
        this.timeA = timeA;
    }

    public Long getTimeB() {
        return timeB;
    }

    public void setTimeB(Long timeB) {
        this.timeB = timeB;
    }

    public Boolean getSancion() {
        return sancion;
    }

    public void setSancion(Boolean sancion) {
        this.sancion = sancion;
    }
}
