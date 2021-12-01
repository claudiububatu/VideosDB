package commands;

import entertainment.Season;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Rating {
    /**
     * @param id          id-ul comenzii
     * @param title       titlul video-ului
     * @param username    username-ul utilizatorului unde trebuie adaugat
     * @param usersData   datele de intrare pentru utilizatori
     * @param rating      rating-ul pe care il oferim unui film
     * @param moviesData  datele de intrare pentru filme
     * @param arrayResult rezultatul ce urmeaza sa fie scris
     * @param fileWriter  The file where the data will be written
     * @throws IOException in case of exceptions to reading / writing
     */
    public final void rateMovie(final int id, final String title, final String username,
                          final ArrayList<User> usersData, final double rating,
                          final ArrayList<Movie> moviesData,
                          final JSONArray arrayResult, final Writer fileWriter) throws IOException {
        JSONObject print = new JSONObject();

        int ok = 0;
        for (User user : usersData) {
            String getuser = user.getUsername();
            if (getuser.compareTo(username) == 0) {
                Map<String, Integer> gethistory = user.getHistory();
                if (gethistory.containsKey(title)) {
                    ok = 1;
                }
                break;
            }
        }
        if (ok == 0) {
            print = fileWriter.writeFile(id, "message",
                    "error -> " + title + " is not seen");
            arrayResult.add(print);
            return;
        }

        for (Movie movie : moviesData) {
            if (movie.getTitle().compareTo(title) == 0) {
                if (movie.getRatings().size() == 0) {
                    movie.getRatings().add(rating);
                    ArrayList<String> record = movie.getUsersRecord();
                    record.add(username);
                    movie.setUsersRecord(record);
                    print = fileWriter.writeFile(id, "message", "success -> "
                            + title + " was rated with "
                            + rating + " by " + username);
                } else {

                    for (int i = 0; i < movie.getUsersRecord().size(); i++) {
                        if (movie.getUsersRecord().get(i).compareTo(username) == 0) {
                            print = fileWriter.writeFile(id, "message",
                                    "error -> " + title
                                            + " has been already rated");
                            ok = 0;
                        }
                    }
                    if (ok == 1) {
                        movie.getRatings().add(rating);
                        ArrayList<String> record = movie.getUsersRecord();
                        record.add(username);
                        movie.setUsersRecord(record);
                        print = fileWriter.writeFile(id, "message",
                                "success -> " + title + " was rated with "
                                        + rating + " by " + username);
                    }
                }
                arrayResult.add(print);
            }
        }
    }

    /**
     * @param id          id-ul comenzii
     * @param title       titlul video-ului
     * @param username    username-ul utilizatorului unde trebuie adaugat
     * @param usersData   datele de intrare pentru utilizatori
     * @param rating      rating-ul pe care il oferim unui serial
     * @param seasnum     numarul sezonului
     * @param serialsData  datele de intrare pentru seriale
     * @param arrayResult rezultatul ce urmeaza sa fie scris
     * @param fileWriter  The file where the data will be written
     * @throws IOException in case of exceptions to reading / writing
     */
public final void rateSerial(final int id, final String title, final String username,
                       final ArrayList<User> usersData, final double rating, final int seasnum,
                       final ArrayList<Serial> serialsData, final JSONArray arrayResult,
                             final Writer fileWriter) throws IOException {
        JSONObject print = new JSONObject();
        final ArrayList<Double> ratings = new ArrayList<>();

        int ok = 0;
        for (User user : usersData) {
            final String getuser = user.getUsername();
            if (getuser.compareTo(username) == 0) {
                Map<String, Integer> gethistory = user.getHistory();
                if (gethistory.containsKey(title)) {
                    ok = 1;
                }
                break;
            }
        }
        if (ok == 0) {
            print = fileWriter.writeFile(id, "message",
                    "error -> " + title + " is not seen");
            arrayResult.add(print);
            return;
        }

        for (Serial serial : serialsData) {

            if (serial.getTitle().compareTo(title) == 0) {
                Season seas = serial.getSeasons().get(seasnum - 1);
                if (seas.getUsersRecord().contains(username)) {
                    print = fileWriter.writeFile(id, "message", "error -> "
                            + title + " has been already rated");
                } else {
                    List<Double> list = seas.getRatings();

                    list.add(rating);
                    seas.setRatings(list);
                    print = fileWriter.writeFile(id, "message", "success -> "
                            + title + " was rated with "
                            + rating + " by " + username);
                    ArrayList<String> record = seas.getUsersRecord();
                    record.add(username);
                    seas.setUsersRecord(record);
                }
            }
        }
        arrayResult.add(print);
    }

}

