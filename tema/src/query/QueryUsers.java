package query;

import commands.Movie;
import commands.Serial;
import commands.User;

import java.util.ArrayList;

public class QueryUsers {
    public final String numofratings(final ArrayList<User> users, final ArrayList<Movie> movies,
                               final ArrayList<Serial> serials, final int ok) {
        int rates = 0;
        ArrayList<User> usersout = new ArrayList<>();
        ArrayList<Integer> ratesout = new ArrayList<>();
        for (User user : users) {
            rates = 0;
            for (Movie movie : movies) {
                ArrayList<String> record = movie.getUsersRecord();
//                System.out.println(record);
                for (String recw : record) {
                    if (recw.compareTo(user.getUsername()) == 0) {
//                        System.out.println("AICIII " + movie.getTitle() + "\n" + user.getUsername() + "\n");
//                        System.out.println("\n + aDSadsadasdsa" + rates);
                        rates++;
                    }
                }
            }
            for (Serial serial : serials) {
                ArrayList<String> record = serial.getUsersRecord();
//                System.out.println(record);
                for (String recw : record) {
                    if (recw.compareTo(user.getUsername()) == 0) {
//                        System.out.println("\nASDSADSAD  " + serial.getTitle() + "\n" + user.getUsername());
//                        System.out.println("\n + BAAAAAAAAAAAAAAAAAAAAAA" + rates);
                        rates++;
                    }
                }
            }

            if (rates != 0) {
                usersout.add(user);
                ratesout.add(rates);
            }
        }

        for (User user : usersout)
//            System.out.println("USER:  " + user.getUsername());

//        System.out.println("  \n\nRATES:  " + ratesout);
        if (ok == 0) {

        } else {

        }

        String nume = "Query result: [";
        for (int i = 0; i < usersout.size(); i++) {
            nume += usersout.get(i).getUsername() + ", ";
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
