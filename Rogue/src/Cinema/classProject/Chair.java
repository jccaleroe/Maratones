package Cinema.classProject;


import java.io.Serializable;

public class Chair implements Serializable {

    private static final long serialVersionUID = -6612517969834904177L;
    private int row;
    private int col;
    private boolean ocupated;
    private char symbol;
    private boolean reserved = false;

    public Chair(int r, int c, char s) {
        row = r;
        col = c;
        symbol = s;
        if (s == '+') {
            ocupated = true;
            reserved = false;
        } else if (s == '-') {
            ocupated = false;
            reserved = true;
        } else {
            ocupated = false;
            reserved = false;
        }
    }

    public String toString() {
        return "" + symbol;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean getOcupated() {
        return ocupated;
    }

    public void setOcupated(boolean ocupated) {
        if (ocupated)
            symbol = '+';
        else
            symbol = '*';
        this.ocupated = ocupated;
    }

    public boolean getReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        if (reserved)
            symbol = '-';
        else
            symbol = '*';
        this.reserved = reserved;
    }
}