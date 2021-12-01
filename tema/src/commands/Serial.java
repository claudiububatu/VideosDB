package commands;

import entertainment.Season;
import fileio.ShowInput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public final class Serial extends ShowInput {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> season;

    private final List<Double> ratings;
    private double rating;
    private final ArrayList<String> usersRecord = new ArrayList<>();
    private final int nrofseasons;
    public Serial(final String title, final ArrayList<String> cast,
                           final ArrayList<String> genres,
                           final int numberOfSeasons, final ArrayList<Season> season,
                           final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.season = season;
        this.nrofseasons = 0;
        this.rating = 0;
        ratings = null;
    }

    public int getNrofseasons() {
        return this.nrofseasons;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     */
    public void computeRating() {
        double totalratings = 0;
        for (Season seas : season) {
            double average = 0;
            List<Double> rates = seas.getRatings();
            for (Double elem : rates) {
                average += elem;
            }
            if (rates.size() > 0) {
                average /= rates.size();
            }
            if (average != 0) {
                totalratings += average;
            }

        }
        if (totalratings != 0) {
            this.rating = totalratings / numberOfSeasons;
        }
    }

    /**
     * @return int
     */
    public int computeDuration() {
        int dur = 0;
        for (Season seas : season) {
            int durlong = seas.getDuration();
            dur += durlong;
        }
        return dur;
    }

    public ArrayList<Season> getSeasons() {
        return season;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public ArrayList<String> getUsersRecord() {
        return usersRecord;
    }

    @Override
    public String toString() {
        return "SerialInputData{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + season + "\n\n" + '}';
    }
}
