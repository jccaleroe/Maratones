package DataStructures.unal.applications;

import java.io.*;

public class Cliente implements Comparable<Cliente>, Serializable {

    private static final long serialVersionUID = -1154868106875518025L;
    private String nombre;
    private int id;
    private char state;
    private int saldo_cuenta_corriente;
    private int saldo_cuenta_ahorros;
    private int estrato;
    private char tipo;
    private int saldo_total;
    protected static int ids = 1000000;

    public Cliente() {
        this("NN", 'L', 0, 0, 0, 'Z');
    }

    public Cliente(String nombre, char state, int saldo_cuenta_corriente,
                   int saldo_cuenta_ahorros, int estrato, char tipo) {
        this.nombre = nombre;
        this.state = state;
        this.saldo_cuenta_corriente = saldo_cuenta_corriente;
        this.saldo_cuenta_ahorros = saldo_cuenta_ahorros;
        this.estrato = estrato;
        this.tipo = tipo;
        saldo_total = saldo_cuenta_corriente + saldo_cuenta_ahorros;
        id = (ids -= 1);
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder("Nombre: " + nombre + ", id: " + id + ", Estado: " + state
                + ", tipo: " + tipo);
        if (state != 'L') {
            s.append(", saldo_cuenta_corriente: " + saldo_cuenta_corriente + ", saldo_cuenta_ahorros: "
                    + saldo_cuenta_corriente + "\n");
        } else {
            s.append("\n");
        }
        return new String(s);

    }

    @Override
    public int compareTo(Cliente otra) {
        return id - otra.getId();
    }

    @Override
    public boolean equals(Object otra) {
        if (otra == null)
            return false;
        if (!(otra instanceof Cliente))
            return false;
        if (otra == this)
            return true;
        return id == ((Cliente) otra).getId();
    }

    @Override
    public int hashCode() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
    }

    public int getSaldo_cuenta_corriente() {
        return saldo_cuenta_corriente;
    }

    public void setSaldo_cuenta_corriente(int saldo_cuenta_corriente) {
        saldo_total -= this.saldo_cuenta_corriente;
        saldo_total += saldo_cuenta_corriente;
        this.saldo_cuenta_corriente = saldo_cuenta_corriente;
    }

    public int getSaldo_cuenta_ahorros() {
        return saldo_cuenta_ahorros;
    }

    public void setSaldo_cuenta_ahorros(int saldo_cuenta_ahorros) {
        saldo_total -= this.saldo_cuenta_ahorros;
        saldo_total += saldo_cuenta_ahorros;
        this.saldo_cuenta_ahorros = saldo_cuenta_ahorros;
    }

    public int getEstrato() {
        return estrato;
    }

    public void setEstrato(int estrato) {
        this.estrato = estrato;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public int getSaldo_total() {
        return saldo_total;
    }
}
