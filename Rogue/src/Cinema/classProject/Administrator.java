package Cinema.classProject;


import java.io.Serializable;
import java.util.ArrayList;

public class Administrator extends Person implements Serializable {

    private static final long serialVersionUID = 5241726003763248055L;
    private ArrayList<Card> cards = new ArrayList<Card>();
    private int code;
    private String idNumber;

    public Administrator(String name, String surname, String birthday, Card card, int code) {
        super(name, surname, birthday);
        cards.add(card);
        this.code = code;
        createRandomeIDNumber();
    }

    public String toString() {
        return "idNumber = " + idNumber;
    }

    public int getRandomNumber(int min, int max) {
        int ans = (int) (Math.random() * (max - min + 1)) + min;
        return ans;
    }

    public void createRandomeIDNumber() {
        idNumber = this.getName();
        idNumber += this.getSurname();
        idNumber += String.valueOf(getRandomNumber(0, 9));
        idNumber += String.valueOf(getRandomNumber(0, 9));
        idNumber += String.valueOf(getRandomNumber(0, 9));
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}