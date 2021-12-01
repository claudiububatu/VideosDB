package commands;

import fileio.ShowInput;

import java.util.ArrayList;
import java.util.List;


public final class Movie extends ShowInput {
    private final int duration;
    private double rating;
    private List<Double> ratings;
    private ArrayList<String> usersRecord;
    private final int numberofviews;
    private final ArrayList<Integer> arrayofviews;

    public Movie(final String title, final ArrayList<String> cast,
                 final ArrayList<String> genre, final int year,
                 final int duration, final double rating) {
        super(title, year, cast, genre);
        this.duration = duration;
        this.rating = rating;
        this.ratings = new ArrayList<>();
        this.usersRecord = new ArrayList<>();
        this.numberofviews = 0;
        this.arrayofviews = null;
    }

    public ArrayList<Integer> getArrayofviews() {
        return arrayofviews;
    }


    public int getNumberofviews() {
        return numberofviews;
    }


    public void setRating(final double rating) {
        this.rating = rating;
    }

    /**
     */
    public void computeRating() {
        double totalrating = 0;
        for (Double ratingg : ratings) {
            totalrating += ratingg;
        }
        if (totalrating != 0) {
            this.rating = totalrating / ratings.size();
        }
    }

    public double getRating() {
        return rating;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(final List<Double> ratings) {
        this.ratings = ratings;
    }

    public ArrayList<String> getUsersRecord() {
        return usersRecord;
    }

    public void setUsersRecord(final ArrayList<String> usersRecord) {
        this.usersRecord = usersRecord;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "MovieInputData{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }
}
