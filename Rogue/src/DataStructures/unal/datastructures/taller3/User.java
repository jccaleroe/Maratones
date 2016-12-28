package DataStructures.unal.datastructures.taller3;

import java.io.Serializable;
import java.util.*;

public class User extends Human implements Serializable {

    private static final long serialVersionUID = 2177581310282967641L;
    private String carrera;
    private String correo;
    private String clave;
    private int type;
    private Calendar sancion;
    private Loan inLoan;
    private short s15min;
    private short sotherS;
    private int daysS;
    private boolean active;

    //	Constructor
    public User(int cedula, String nombre, String clave, String carrera, String correo, int type) {
        super(cedula, nombre);
        this.clave = clave;
        this.carrera = carrera;
        this.correo = correo;
        this.type = type;
        sancion = Calendar.getInstance();
        sancion.setTime(sancion.getTime());
        inLoan = null;
        daysS = 0;
        active = true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("User [Nombre: " + getNombre() + ", Cedula: " + getCedula() + ", carrera: " + carrera + ", correo: " + correo + ",  type: "
                + type + ",\nsancion: ");
        Calendar cal = Calendar.getInstance();
        if ((cal.compareTo(sancion)) > 0)
            builder.append("No tiene Sanciones");
        else
            builder.append("Sancion hasta : " + sancion.getTime());
        builder.append(", Numero de sanciones por mas de 15m de demora: " + s15min + ",\n Numero de sanciones por estaciones equivocadas: "
                + sotherS + ", Dias sancionado: " + daysS + ", activo: " + active + "]\n");
        return new String(builder);
    }

    // Getters and Setters
    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Calendar getSancion() {
        return sancion;
    }

    public void setSancion(int time) {
        sancion.add(Calendar.DATE, time);
        daysS += time;
        if (daysS >= 100)
            active = false;
    }

    public Loan getInLoan() {
        return inLoan;
    }

    public void setInLoan(Loan inLoan) {
        this.inLoan = inLoan;
    }

    public Short getS15min() {
        return s15min;
    }

    public void setS15min(short s15min) {
        this.s15min = s15min;
    }

    public short getSotherS() {
        return sotherS;
    }

    public void setSotherS(short sotherS) {
        this.sotherS = sotherS;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
