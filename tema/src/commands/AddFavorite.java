package commands;

import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddFavorite {
    private JSONObject print = new JSONObject();
    /**
     * @param id          id-ul comenzii
     * @param title       titlul video-ului
     * @param username    username-ul utilizatorului unde trebuie adaugat
     * @param usersData   datele de intrare pentru utilizatori
     * @param arrayResult rezultatul ce urmeaza sa fie scris
     * @param fileWriter  The file where the data will be written
     * @throws IOException in case of exceptions to reading / writing
     */
    public void addfav(final int id, final String title, final String username,
                            final List<User> usersData, final JSONArray arrayResult,
                            final Writer fileWriter) throws IOException {
        for (User user : usersData) {
            String getuser = user.getUsername();
            if (getuser.compareTo(username) == 0) {
                ArrayList<String> favmovies = user.getFavoriteMovies();
                if (favmovies.contains(title)) {
                    print = fileWriter.writeFile(id, "message",
                            "error -> " + title + " is already in favourite list");
                    arrayResult.add(print);
                } else {
                    Map<String, Integer> history = user.getHistory();
                    if (history.containsKey(title)) {
                        user.getFavoriteMovies().add(title);
                        print = fileWriter.writeFile(id, "message",
                                "success -> " + title + " was added as favourite");
                        arrayResult.add(print);
                    } else {
                        print = fileWriter.writeFile(id, "message",
                                "error -> " + title + " is not seen");
                        arrayResult.add(print);
                    }
                }
            }
        }
    }
}
