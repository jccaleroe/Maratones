package DataStructures.unal.applications;

public class MyStudent implements Comparable<MyStudent> {
    private String name;
    private String surname;
    private int document;

    public MyStudent(String NewName, String NewSurname, int NewDocument) {
        name = NewName;
        surname = NewSurname;
        document = NewDocument;
    }


    @Override
    public String toString() {
        return name + " " + surname + " " + document;
    }

    @Override
    public int compareTo(MyStudent otra) {
        return document - otra.getDocument();
    }

    @Override
    public boolean equals(Object otra) {
        if (otra == null)
            return false;
        if (!(otra instanceof MyStudent))
            return false;
        if (otra == this)
            return true;
        return document == ((MyStudent) otra).getDocument();
    }

    @Override
    public int hashCode() {
        return (int) document;
    }

    public void setName(String NewName) {
        name = NewName;
    }

    public void setSurname(String NewSurname) {
        surname = NewSurname;
    }

    public void setDocument(int NewDocument) {
        document = NewDocument;
    }


    public String getName() {
        return name;
    }


    public String getSurname() {
        return surname;
    }


    public int getDocument() {
        return document;
    }
}