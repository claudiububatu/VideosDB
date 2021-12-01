package query;

import commands.Actor;
import commands.Movie;
import commands.Serial;
import commands.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class QueryVideos {
    /**
     * @param actors - datele de intrare pentru actori
     * @param movies - datele de intrare pentru filme
     * @param ok - parametru ajutator pentru "asc" si "desc"
     * @param number - dimensiunea listei de afisat
     * @return String
     */
    public final String ratingMovie(final ArrayList<Actor> actors, final ArrayList<Movie> movies,
                              final int ok, final int number) {
        List<Actor> actorsout = new ArrayList<Actor>();
        for (Actor actor : actors) {
            double totalratings = 0;
            int nrfilms = 0;
            ArrayList<String> films = actor.getFilmography();
            for (String name : films) {
                for (Movie movie : movies) {
                    if (movie.getTitle().compareTo(name) == 0) {
                        movie.computeRating();

                        if (movie.getRating() != 0) {
                            totalratings += movie.getRating();
                            nrfilms++;
                        }
                        break;
                    }
                }
            }
            if (totalratings != 0) {
                actor.setAverageRating(totalratings / nrfilms);
                actorsout.add(actor);
            }
        }

        if (ok == 0) {
            Collections.sort(actorsout,
                    Comparator.comparing(Actor::getAverageRating).
                            thenComparing(Actor::getName));
        } else {
            Collections.sort(actorsout,
                    Comparator.comparing(Actor::getAverageRating).
                            thenComparing(Actor::getName).reversed());
        }
        String nume = "Query result: [";
        ArrayList<Actor> newlist = new ArrayList<Actor>();
        for (int i = 0; i < number && i < actorsout.size(); i++) {
            newlist.add(actorsout.get(i));
            nume += actorsout.get(i).getName() + ", ";
        }

        if (nume.compareTo("Query result: [") == 0) {
            nume += "]";
        } else {
            nume = nume.substring(0, nume.length() - 2);
            nume += "]";
        }

        return nume;
    }

    /**
     * @param actors - datele de intrare pentru actori
     * @param serials - datele de intrare pentru seriale
     * @param ok - parametru ajutator pentru "asc" si "desc"
     * @param number - dimensiunea listei de afisat
     * @return String
     */
    public final String ratingSerial(final ArrayList<Actor> actors,
                                     final ArrayList<Serial> serials,
                                     final int ok, final int number) {
        List<Actor> actorsout = new ArrayList<Actor>();
        for (Actor actor : actors) {
            double totalratings = 0;
            int nrfilms = 0;
            ArrayList<String> films = actor.getFilmography();
            for (String name : films) {
                for (Serial serial : serials) {
                    if (serial.getTitle().compareTo(name) == 0) {
                        serial.computeRating();
                        if (serial.getRating() != 0) {
                            totalratings += serial.getRating();
                            nrfilms++;
                        }
                        break;
                    }
                }
            }
            if (totalratings != 0) {
                actor.setAverageRating(totalratings / nrfilms);
                actorsout.add(actor);
            }
        }

        if (ok == 0) {
            Collections.sort(actorsout,
                    Comparator.comparing(Actor::getAverageRating).
                            thenComparing(Actor::getName));
        } else {
            Collections.sort(actorsout,
                    Comparator.comparing(Actor::getAverageRating).
                            thenComparing(Actor::getName).reversed());
        }
        String nume = "Query result: [";
        ArrayList<Actor> newlist = new ArrayList<Actor>();
        for (int i = 0; i < number && i < actorsout.size(); i++) {
            newlist.add(actorsout.get(i));
            nume += actorsout.get(i).getName() + ", ";
        }

        if (nume.compareTo("Query result: [") == 0) {
            nume += "]";
        } else {
            nume = nume.substring(0, nume.length() - 2);
            nume += "]";
        }

        return nume;
    }

    /**
     * @param movies - datele de intrare pentru filme
     * @param filters - datele de intrare pentru filtre
     * @param ok - parametru ajutator petnru "asc" si "desc"
     * @param number - dimensiunea listei de afisat
     * @return String
     */
    public final String longestmovie(final ArrayList<Movie> movies,
                                     final List<List<String>> filters,
                                     final int ok, final int number) {
        ArrayList<Movie> moviesout = new ArrayList<>();
        ArrayList<Integer> durationlist = new ArrayList<>();
        for (Movie movie : movies) {
            String year;
            year = String.valueOf(movie.getYear());
            List<String> filteryear = null;
            List<String> filtergenre = null;
            filteryear = filters.get(0); // am luat anul
            filtergenre = filters.get(1); // am luat genul

            // daca anul si genul filmului corespund cu filtrele
            if (filteryear.get(0) != null && filtergenre.get(0) != null) {
                if (filteryear.get(0).compareTo(year) == 0 && movie.
                        getGenres().contains(filtergenre.get(0))) {
                    int duration = movie.getDuration();
                    durationlist.add(duration);
                    moviesout.add(movie);
                }
            } else if (filteryear.get(0) != null) {
                if (filteryear.get(0).compareTo(year) == 0) {
                    // daca anul filmului corespunde cu filtrul
                    int duration = movie.getDuration();
                    durationlist.add(duration);
                    moviesout.add(movie);
                }
            } else if (filtergenre.get(0) != null) {
                if (movie.getGenres().contains(filtergenre.get(0))) {
                    // daca genul filmului crespunde cu filtrul
                    int duration = movie.getDuration();
                    durationlist.add(duration);
                    moviesout.add(movie);
                }
            }

        }

        if (ok == 0) {
            Collections.sort(moviesout,
                    Comparator.comparing(Movie::getDuration).
                            thenComparing(Movie::getTitle));
        } else {
            Collections.sort(moviesout,
                    Comparator.comparing(Movie::getDuration).
                            thenComparing(Movie::getTitle).reversed());
        }


        String nume = "Query result: [";
        for (int i = 0; i < number && i < moviesout.size(); i++) {
            nume += moviesout.get(i).getTitle() + ", ";
        }

        if (nume.compareTo("Query result: [") == 0) {
            nume += "]";
        } else {
            nume = nume.substring(0, nume.length() - 2);
            nume += "]";
        }

        return nume;
    }

    /**
     * @param serials - datele de intrare pentru seriale
     * @param filters - datele de intrare pentru filtre
     * @param ok - parametru ajutator pentru "asc" si "desc"
     * @param number - dimensiunea listei de afisat
     * @return String
     */
    public final String longestserial(final ArrayList<Serial> serials,
                                      final List<List<String>> filters,
                                      final int ok, final int number) {
        ArrayList<Serial> serialsout = new ArrayList<>();
        ArrayList<Integer> durationlist = new ArrayList<>();
        for (Serial serial : serials) {
            String year;
            year = String.valueOf(serial.getYear());
            List<String> filteryear = null;
            List<String> filtergenre = null;
            filteryear = filters.get(0); // am luat anul
            filtergenre = filters.get(1); // am luat genul

            // daca anul si genul filmului corespund cu filtrele
            if (filteryear.get(0) != null && filtergenre.get(0) != null) {
                if (filteryear.get(0).compareTo(year) == 0
                        && serial.getGenres().contains(filtergenre.get(0))) {
                    int duration = serial.computeDuration();
                    durationlist.add(duration);
                    serialsout.add(serial);
                }
            } else if (filteryear.get(0) != null) {
                if (filteryear.get(0).compareTo(year) == 0) {
                    // daca anul filmului corespunde cu filtrul
                    int duration = serial.computeDuration();
                    durationlist.add(duration);
                    serialsout.add(serial);
                }
            } else if (filtergenre.get(0) != null) {
                if (serial.getGenres().contains(filtergenre.get(0))) {
                    // daca genul filmului crespunde cu filtrul
                    int duration = serial.computeDuration();
                    durationlist.add(duration);
                    serialsout.add(serial);
                }
            }

        }

        if (ok == 0) {
            Collections.sort(serialsout,
                    Comparator.comparing(Serial::computeDuration).
                            thenComparing(Serial::getTitle));
        } else {
            Collections.sort(serialsout,
                    Comparator.comparing(Serial::computeDuration).
                            thenComparing(Serial::getTitle).reversed());
        }


        String nume = "Query result: [";
        for (int i = 0; i < number && i < serialsout.size(); i++) {
            nume += serialsout.get(i).getTitle() + ", ";
        }

        if (nume.compareTo("Query result: [") == 0) {
            nume += "]";
        } else {
            nume = nume.substring(0, nume.length() - 2);
            nume += "]";
        }

        return nume;
    }


    /**
     * @param users - datele de intrare pentru useri
     * @param movies - datele de intrare pentru filme
     * @param filters - datele de intrare pentru filtre
     * @param ok - parametru ajutator pentru "asc" si "desc"
     * @param number - dimensiunea listei de afisat
     * @return String
     */
    public final String mostviewedmovie(final ArrayList<User> users,
                                        final ArrayList<Movie> movies,
                                        final List<List<String>> filters,
                                        final int ok, final int number) {
        ArrayList<Movie> moviesout = new ArrayList<>();

        for (Movie movie : movies) {
            List<String> filteryear = null;
            List<String> filtergenre = null;
            String year = String.valueOf(movie.getYear());
            filteryear = filters.get(0); // am luat anul
            filtergenre = filters.get(1); // am luat genul
            if (filteryear.get(0) != null
                    && filtergenre.get(0) != null) {
                if (filteryear.get(0).compareTo(year) == 0
                        && movie.getGenres().contains(filtergenre.get(0))) {
                    for (User user : users) {
                        if (user.getHistory().containsKey(movie.getTitle())) {
                            if (!moviesout.contains(movie)) {
                                moviesout.add(movie);
                            }
                        }
                    }
                }
            } else if (filteryear.get(0) != null) {
                if (filteryear.get(0).compareTo(year) == 0) {
                    for (User user : users) {
                        if (user.getHistory().containsKey(movie.getTitle())) {
                            if (!moviesout.contains(movie)) {
                                moviesout.add(movie);
                            }
                        }
                    }
                }
            } else if (filtergenre.get(0) != null) {
                if (movie.getGenres().contains(filtergenre.get(0))) {
                    for (User user : users) {
                        if (user.getHistory().containsKey(movie.getTitle())) {
                            if (!moviesout.contains(movie)) {
                                moviesout.add(movie);
                            }
                        }
                    }
                }
            }
        }

        if (ok == 0) {
            Collections.sort(moviesout,
                    Comparator.comparing(Movie::getNumberofviews).
                            thenComparing(Movie::getTitle));
        } else {
            Collections.sort(moviesout,
                    Comparator.comparing(Movie::getNumberofviews).
                            thenComparing(Movie::getTitle).reversed());
        }

        String nume = "Query result: [";
        for (int i = 0; i < moviesout.size(); i++) {
            nume += moviesout.get(i).getTitle() + ", ";
        }

        if (nume.compareTo("Query result: [") == 0) {
            nume += "]";
        } else {
            nume = nume.substring(0, nume.length() - 2);
            nume += "]";
        }
        return nume;
    }

    /**
     * @param users - datele de intrare pentru useri
     * @param serials - datele de intrare pentru seriale
     * @param filters - datele de intrare pentru filtre
     * @param ok - parametru ajutator pentru "asc" si "desc"
     * @param number - dimensiunea listei de afisat
     * @return String
     */
    public final String mostviewedserial(final ArrayList<User> users,
                                         final ArrayList<Serial> serials,
                                         final List<List<String>> filters,
                                         final int ok, final int number) {
        ArrayList<Serial> serialsout = new ArrayList<>();

        for (Serial serial : serials) {
            List<String> filteryear = null;
            List<String> filtergenre = null;
            String year = String.valueOf(serial.getYear());
            filteryear = filters.get(0); // am luat anul
            filtergenre = filters.get(1); // am luat genul
            if (filteryear.get(0) != null && filtergenre.get(0) != null) {
                if (filteryear.get(0).compareTo(year) == 0
                        && serial.getGenres().contains(filtergenre.get(0))) {
                    for (User user : users) {
                        if (user.getHistory().containsKey(serial.getTitle())) {
                            if (!serialsout.contains(serial)) {
                                serialsout.add(serial);
                            }
                        }
                    }
                }
            } else if (filteryear.get(0) != null) {
                if (filteryear.get(0).compareTo(year) == 0) {
                    for (User user : users) {
                        if (user.getHistory().containsKey(serial.getTitle())) {
                            if (!serialsout.contains(serial)) {
                                serialsout.add(serial);
                            }
                        }
                    }
                }
            } else if (filtergenre.get(0) != null) {
                if (serial.getGenres().contains(filtergenre.get(0))) {
                    for (User user : users) {
                        if (user.getHistory().containsKey(serial.getTitle())) {
                            if (!serialsout.contains(serial)) {
                                serialsout.add(serial);
                            }
                        }
                    }
                }
            }
        }

        if (ok == 0) {
            Collections.sort(serialsout,
                    Comparator.comparing(Serial::getNrofseasons).
                            thenComparing(Serial::getTitle));
        } else {
            Collections.sort(serialsout,
                    Comparator.comparing(Serial::getNrofseasons).
                            thenComparing(Serial::getTitle).reversed());
        }

        String nume = "Query result: [";
        for (int i = 0; i < serialsout.size(); i++) {
            nume += serialsout.get(i).getTitle() + ", ";
        }

        if (nume.compareTo("Query result: [") == 0) {
            nume += "]";
        } else {
            nume = nume.substring(0, nume.length() - 2);
            nume += "]";
        }

        return nume;
    }

    /**
     * @param users - datele de intrare pentru useri
     * @param movies - datele de intrare pentru filme
     * @param filters - datele de intrare pentru filtre
     * @param ok - parametru ajutator pentru "asc" si "desc"
     * @param number - dimensiunea listei de afisat
     * @return String
     */
    public final String favoritemovie(final ArrayList<User> users,
                                      final ArrayList<Movie> movies,
                                      final List<List<String>> filters,
                                      final int ok, final int number) {
        ArrayList<String> favout = new ArrayList<>();
        for (Movie movie : movies) {
            List<String> filteryear = null;
            List<String> filtergenre = null;
            String year = String.valueOf(movie.getYear());
            filteryear = filters.get(0); // am luat anul
            filtergenre = filters.get(1); // am luat genul
            if (filteryear.get(0) != null && filtergenre.get(0) != null) {
                if (filteryear.get(0).compareTo(year) == 0
                        && movie.getGenres().contains(filtergenre.get(0))) {
                    for (User user : users) {
                        ArrayList<String> favorites = user.getFavoriteMovies();
                        for (String fav : favorites) {
                            if (fav.compareTo(movie.getTitle()) == 0) {
                                favout.add(fav);
                            }
                        }
                    }
                }
            }
        }


//        if (ok == 0)
//            Collections.sort(favout,
//            Comparator.comparing(Movie::).
//            thenComparing(Serial::getTitle));
//        else
//            Collections.sort(favout,
//            Comparator.comparing(Serial::getNr_of_seasons).
//            thenComparing(Serial::getTitle).reversed());


        String nume = "Query result: [";
        for (int i = 0; i < favout.size(); i++) {
            nume += favout.get(i) + ", ";
        }

        if (nume.compareTo("Query result: [") == 0) {
            nume += "]";
        } else {
            nume = nume.substring(0, nume.length() - 2);
            nume += "]";
        }

        return nume;
    }

    /**
     * @param users - datele de intrare pentru useri
     * @param serials - datele de intrare pentru seriale
     * @param filters - datele de intrare pentru filtre
     * @param ok - parametru ajutator pentru "asc" si "desc"
     * @param number - dimensiunea listei de afisat
     * @return String
     */
    public final String favoriteserial(final ArrayList<User> users,
                                 final ArrayList<Serial> serials,
                                 final List<List<String>> filters,
                                 final int ok, final int number) {
        ArrayList<String> favout = new ArrayList<>();
        for (Serial serial : serials) {
            List<String> filteryear = null;
            List<String> filtergenre = null;
            String year = String.valueOf(serial.getYear());
            filteryear = filters.get(0); // am luat anul
            filtergenre = filters.get(1); // am luat genul
            if (filteryear.get(0) != null && filtergenre.get(0) != null) {
                if (filteryear.get(0).compareTo(year) == 0
                        && serial.getGenres().contains(filtergenre.get(0))) {
                    for (User user : users) {
                        ArrayList<String> favorites = user.getFavoriteMovies();
                        for (String fav : favorites) {
                            if (fav.compareTo(serial.getTitle()) == 0) {
                                favout.add(fav);
                            }
                        }
                    }
                }
            } else if (filteryear.get(0) != null) {
                if (filteryear.get(0).compareTo(year) == 0) {
                    for (User user : users) {
                        ArrayList<String> favorites = user.getFavoriteMovies();
                        for (String fav : favorites) {
                            if (fav.compareTo(serial.getTitle()) == 0) {
                                favout.add(fav);
                            }
                        }
                    }
                }
            } else if (filtergenre.get(0) != null) {
                if (serial.getGenres().contains(filtergenre.get(0))) {
                    for (User user : users) {
                        ArrayList<String> favorites = user.getFavoriteMovies();
                        for (String fav : favorites) {
                            if (fav.compareTo(serial.getTitle()) == 0) {
                                favout.add(fav);
                            }
                        }
                    }
                }
            }
        }


//        if (ok == 0)
//            Collections.sort(favout,
//            Comparator.comparing(Movie::).
//            thenComparing(Serial::getTitle));
//        else
//            Collections.sort(favout,
//            Comparator.comparing(Serial::getNr_of_seasons).
//            thenComparing(Serial::getTitle).reversed());


        String nume = "Query result: [";
        for (int i = 0; i < favout.size(); i++) {
            nume += favout.get(i) + ", ";
        }

        if (nume.compareTo("Query result: [") == 0) {
            nume += "]";
        } else {
            nume = nume.substring(0, nume.length() - 2);
            nume += "]";
        }

        return nume;
    }
}
