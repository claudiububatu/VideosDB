package recommendation;

import commands.Movie;
import commands.Serial;
import commands.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RecUser {

    public final String standard(final ArrayList<Movie> movies,
                           final ArrayList<Serial> serials, final User user) {
        String recommmendation = null;

        for (Movie movie : movies) {
            if (!user.getHistory().containsKey(movie.getTitle())) {
                recommmendation = movie.getTitle();
                break;
            }
        }
        if (recommmendation == null) {
            for (Serial serial : serials) {
                if (!user.getHistory().containsKey(serial.getTitle())) {
                    recommmendation = serial.getTitle();
                    break;
                }
            }
        }

        return recommmendation;
    }

    public static final String bestunseen(final User user, final ArrayList<Movie> movies,
                                          final ArrayList<Serial> serials) {
        String recommmendation = null;
        ArrayList<Movie> moviesout = new ArrayList<>();
        ArrayList<Serial> serialsout = new ArrayList<>();
        for (Movie movie : movies) {
            if (!user.getHistory().containsKey(movie.getTitle())) {
                moviesout.add(movie);
            }
        }

        for (Serial serial : serials) {
            if (!user.getHistory().containsKey(serial.getTitle())) {
                serialsout.add(serial);
            }
        }

        Collections.sort(moviesout, Comparator.comparing(Movie::getRating).reversed());
        Collections.sort(serialsout, Comparator.comparing(Serial::getRating).reversed());
        if (moviesout.size() != 0 && serialsout.size() != 0) {
            if (moviesout.get(0).getRating() >= serialsout.get(0).getRating()) {
                recommmendation = moviesout.get(0).getTitle();
            } else {
                recommmendation = serialsout.get(0).getTitle();
            }
        } else if (moviesout.size() != 0) {
            recommmendation = moviesout.get(0).getTitle();
        } else if (serialsout.size() != 0) {
            recommmendation = serialsout.get(0).getTitle();
        }
        return recommmendation;
    }
}
