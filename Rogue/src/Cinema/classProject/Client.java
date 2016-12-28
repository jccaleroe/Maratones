package Cinema.classProject;

import java.io.Serializable;

public class Client extends Person implements Serializable {

    private static final long serialVersionUID = -5882966990893098682L;
    private Card card;
    private String idNumber;

    public Client(String name, String surname, String birthday, Card card) {
        super(name, surname, birthday);
        this.card = card;
        createRandomeIDNumber();
    }

    public Client(String name, String surname, String birthday, String email, String id, Card card) {
        super(name, surname, birthday, email);
        this.card = card;
        this.idNumber = id;
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

    public String toString() {
        return this.getName() + " " + this.getSurname() + "\nbirthday = " + this.getBirthday()
                + "\nemail = " + this.getEmail() + "\nidNumber = " + idNumber;
    }

    public Card getCard() {
        return card;
    }

    public void setCards(Card card) {
        this.card = card;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}