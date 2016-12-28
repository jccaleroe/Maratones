package Cinema.classProject;


import java.io.Serializable;

public class Card implements Serializable {

    private static final long serialVersionUID = 4349295124982166261L;
    private int balance;
    private int code;

    public Card(int balance, int code) {
        this.balance = balance;
        this.code = code;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String toString() {
        return "Card [balance = " + balance + " code " + code + "]";
    }
}