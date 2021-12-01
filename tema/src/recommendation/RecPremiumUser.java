package recommendation;

import commands.Movie;
import commands.Serial;
import commands.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RecPremiumUser {

    public final static ArrayList<String> search(final ArrayList<Movie> movies, final ArrayList<Serial> serials,
                                          final User user, final String genre) {
        String recommmendation = null;
        ArrayList<Movie> moviesout = new ArrayList<>();
        ArrayList<Serial> serialsout = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getGenres().contains(genre)) {
                if (!user.getHistory().containsKey(movie.getTitle())) {
                    moviesout.add(movie);
                }
            }
        }
        for (Serial serial : serials) {
            if (serial.getGenres().contains(genre)) {
                if (!user.getHistory().containsKey(serial.getTitle())) {
                    serialsout.add(serial);
                }
            }
        }

        Collections.sort(moviesout, Comparator.comparing(Movie::getRating));
        Collections.sort(serialsout, Comparator.comparing(Serial::getRating));
        ArrayList<String> stringsout = new ArrayList<>();

        if (moviesout.size() != 0 && serialsout.size() != 0) {
            for (Movie movie : moviesout) {
                for (Serial serial : serialsout) {
                    if (movie.getRating() >= serial.getRating()) {
                        stringsout.add (movie.getTitle());
                    } else {
                        stringsout.add (serial.getTitle());
                    }
                }
            }
        } else if (moviesout.size() != 0) {
            for (Movie movie : moviesout) {
                stringsout.add(movie.getTitle());
            }
        } else if (serialsout.size() != 0) {
            for (Serial serial : serialsout) {
                stringsout.add (serial.getTitle());
            }
        }

        System.out.println(stringsout);
        return stringsout;
//        return recommmendation;
    }
}
