package commands;


import fileio.ActorInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.UserInputData;
import fileio.SerialInputData;

import java.util.ArrayList;

public class Database {
    private final ArrayList<Actor>  actors  = new ArrayList<>();
    private final ArrayList<Movie>  movies  = new ArrayList<>();
    private final ArrayList<Serial> serials = new ArrayList<>();
    private final ArrayList<User> users = new ArrayList<>();

    public Database() { }

    /**
     * @param input - preluam datele de intrare
     */
    public final ArrayList<User> populateWithUsers(final Input input) {
        for (UserInputData user : input.getUsers()) {
            users.add(new User(user.getUsername(), user.getSubscriptionType(),
                    user.getHistory(), user.getFavoriteMovies()));
        }
        return users;
    }

    /**
     * @param input - preluam datele de intrare
     */
    public final ArrayList<Actor> populateWithActors(final Input input) {
        for (ActorInputData actor : input.getActors()) {
            actors.add(new Actor(actor.getName(), actor.getCareerDescription(),
                    actor.getFilmography(), actor.getAwards()));
        }
        return actors;
    }

    /**
     * @param input - preluam datele de intrare
     */
    public final ArrayList<Movie> populateWithMovies(final Input input) {
        for (MovieInputData movie : input.getMovies()) {
            movies.add(new Movie(movie.getTitle(), movie.getCast(),
                    movie.getGenres(), movie.getYear(), movie.getDuration(), 0));
        }
        return movies;
    }

    /**
     * @param input - preluam datele de intrare
     */
    public final ArrayList<Serial> populateWithSerials(final Input input) {
        for (SerialInputData serial : input.getSerials()) {
            serials.add(new Serial(serial.getTitle(), serial.getCast(),
                    serial.getGenres(), serial.getNumberSeason(), serial.getSeasons(),
                    serial.getYear()));
        }
        return serials;
    }

    public final ArrayList<Actor> getActors() {
        return actors;
    }

    public final ArrayList<Movie> getMovies() {
        return movies;
    }

    public final ArrayList<Serial> getSerials() {
        return serials;
    }

    public final ArrayList<User> getUsers() {
        return users;
    }
}
