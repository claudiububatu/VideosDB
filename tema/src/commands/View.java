package commands;

import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class View {
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
    public final void view(final int id, final String title, final String username,
                     final List<User> usersData, final JSONArray arrayResult,
                     final Writer fileWriter) throws IOException {
        for (User user : usersData) {
            if (user.getUsername().compareTo(username) == 0) {
                Map<String, Integer> gethistory = user.getHistory();
                if (!gethistory.containsKey(title)) {
                    gethistory.put(title, 1);
                    print = fileWriter.writeFile(id, "message",
                            "success -> " + title + " was viewed with total views of " + 1);
                } else {
                    gethistory.replace(title, gethistory.get(title) + 1);
                    print = fileWriter.writeFile(id, "message",
                            "success -> " + title + " was viewed with total views of "
                                    + gethistory.get(title));
                }
                arrayResult.add(print);
            }
        }
    }
}
