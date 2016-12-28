package Cinema.classProject;


import java.io.Serializable;

public class Person implements Serializable {

    private static final long serialVersionUID = -5060250176084895749L;
    private String name;
    private String surname;
    private String birthday;
    private String email;

    public Person(String name, String surname, String birthday, String email) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.email = email;
    }

    public Person(String name, String surname, String birthday) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        createRandomEmail();
    }

    public void createRandomEmail() {
        String aux = "@gamil.com";
        email = name;
        email += "_";
        email += surname;
        email += aux;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return name + " " + surname + "\nbirthday = " + birthday + "\nemail = " + email;
    }
}