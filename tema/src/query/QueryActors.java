package query;

import commands.Actor;
import commands.Movie;
import commands.Serial;
import actor.ActorsAwards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class QueryActors {

    /**
     * @param actors - datele de intrare pentru actori
     * @param movies - datele de intrare pentru filme
     * @param serials - datele de intrare pentru seriale
     * @param ok - parametru ajutator pentru "asc" si "desc"
     * @param number - dimensiunea listei de afisat
     * @return String
     */
    public final String average(final ArrayList<Actor> actors,
                                final ArrayList<Movie> movies,
                                final ArrayList<Serial> serials,
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
     * @param actors - datele de intrare pentru actori
     * @param nameofawards - numele awardsurilor
     * @param ok - parametru ajutator pentru "asc" si "desc"
     * @param number - dimensiunea listei de afisat
     * @return String
     */
    public final String awards(final ArrayList<Actor> actors, final List<String> nameofawards,
                         final int ok, final int number) {
        ArrayList<Actor> actorsout = new ArrayList<Actor>();
        for (Actor actor : actors) {
            Map<ActorsAwards, Integer> awards = actor.getAwards();
            int totalawards = 0;
            for (Integer awardss : awards.values()) {
                totalawards += awardss;
            }
            for (String award : nameofawards) {
                ActorsAwards name = ActorsAwards.valueOf(award);
                if (!awards.containsKey(name)) {
                    totalawards = 0;
                    break;
                }
            }
            if (totalawards != 0) {
                actorsout.add(actor);
            }
        }
        if (ok == 0) {
            Collections.sort(actorsout,
                    Comparator.comparing(Actor::getNumberofawards).
                            thenComparing(Actor::getName));
        } else {
            Collections.sort(actorsout,
                    Comparator.comparing(Actor::getNumberofawards).
                            thenComparing(Actor::getName).reversed());
        }
        String nume = "Query result: [";
        for (int i = 0; i < number && i <  actorsout.size(); i++) {
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
     * @param filters - datele de intrare pentru filtre
     * @return String
     */
    public final String filterdescription(final ArrayList<Actor> actors,
                                     final List<List<String>> filters) {
        ArrayList<Actor> actorsout = new ArrayList<>();
        int ok = 0;
        int ok2 = 0;
        for (Actor actor : actors) {
            ok2 = 0;
            String[] description = actor.getCareerDescription().split("[-.,;()\\s]+");
            List<String> filterwords;
            if (filters.get(2) != null) {
                filterwords = filters.get(2);
                for (int i = 0; i < filterwords.size(); i++) {
                    if (filterwords.get(i) != null) {
                        ok = 0;
                        for (String word : description) {
                            if (filterwords.get(i).compareTo(word) == 0) {
                                ok = 1;
                            }
                        }
                        if (ok == 0) {
                            ok2 = 1;
                        }
                    }
                }
            }
            if (ok2 == 0) {
                actorsout.add(actor);
            }
        }

//        System.out.println(filtersout);
//        Collections.sort(filtersout);

        String nume = "Query result: [";
        for (int i = 0; i < actorsout.size(); i++) {
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
}
