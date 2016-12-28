package Cinema.classProject;


import java.io.Serializable;

public class Movie implements Serializable {

    private static final long serialVersionUID = -8235858627073819610L;
    private String name;
    private String studio;
    private String budget;
    private String yearOfRealise;

    public Movie(String studio, String name, String yearOfRealise, String budget) {
        this.studio = studio;
        this.name = name;
        this.yearOfRealise = yearOfRealise;
        this.budget = budget;
    }

    public Movie(String allInOne) {
        String[] tmp = allInOne.split(",");
        name = tmp[0];
        studio = tmp[1];
        budget = tmp[2];
        yearOfRealise = tmp[3];
    }

    public String toString() {
        return name + "\nstudio = " + studio + ", budget = "
                + budget + ", yearOfRealise = " + yearOfRealise;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYearOfRealise() {
        return yearOfRealise;
    }

    public void setYearOfRealise(String yearOfRealise) {
        this.yearOfRealise = yearOfRealise;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }
}