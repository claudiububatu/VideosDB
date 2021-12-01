package recommendation;

import commands.Movie;
import commands.Serial;
import commands.User;
import fileio.Input;
import fileio.UserInputData;

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


    public final String fav(final ArrayList<Movie> movies, final ArrayList<Serial> serials,
                            final ArrayList<User> users, final User usr) {

        ArrayList<Integer> nrviewsmovies = new ArrayList<>();
        ArrayList<Integer> nrviewsserials = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            int nrfavs = 0;
            for (User user : users) {
                if (!user.getFavoriteMovies().contains(movies.get(i).getTitle())) {
                    nrfavs++;
                }
            }
            nrviewsmovies.add(nrfavs);

        }

        for (int i = 0; i < serials.size(); i++) {
            int nrfavs = 0;
            for (User user : users) {
                if (!user.getFavoriteMovies().contains(serials.get(i).getTitle())) {
                    nrfavs++;
                }
            }
            nrviewsserials.add(nrfavs);
        }
        ArrayList<Movie> copym = new ArrayList<>(movies);
        ArrayList<Serial> copys = new ArrayList<>(serials);

        int ok = 0;
        while (ok == 0) {
            int max = 0;
            int index = 0;
            for (int i = 0; i < copym.size(); i++) {
                if (nrviewsmovies.get(i) > max) {
                    max = nrviewsmovies.get(i);
                    index = i;
                }
            }
            if (!usr.getHistory().containsKey(copym.get(index).getTitle())) {
                return copym.get(index).getTitle();
            }
            nrviewsmovies.remove(index);
            copym.remove(copym.get(index));
            if (copym.size() == 0) {
                ok = 1;
            }
        }

        ok = 0;
        while (ok == 0) {
            int max = 0;
            int index = 0;
            for (int i = 0; i < copys.size(); i++) {
                if (nrviewsserials.get(i) > max) {
                    max = nrviewsserials.get(i);
                    index = i;
                }
            }
            if (!usr.getHistory().containsKey(copys.get(index).getTitle())) {
                return copys.get(index).getTitle();
            }
            nrviewsserials.remove(index);
            copys.remove(copys.get(index));
            if (copys.size() == 0) {
                ok = 1;
            }
        }
        return null;
    }
}
