package Cinema.classProject;


import java.io.Serializable;

public class Function implements Serializable {

    private static final long serialVersionUID = -3531191219672740190L;
    private Movie movie;
    private Cinema cinema;
    private String date;
    private int price;

    public Function(String date, Cinema cinema, Movie movie, int price) {
        this.date = date;
        this.cinema = cinema;
        this.movie = movie;
        this.price = price;
    }

    public String toString() {
        return "Function : " + movie + "\ndate = "
                + date + "\nprice " + price + "\ncinema = " + cinema;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}