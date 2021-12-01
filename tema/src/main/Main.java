package main;

import commands.Database;
import commands.Movie;
import commands.Serial;
import commands.User;
import commands.Actor;
import commands.AddFavorite;
import commands.View;
import commands.Rating;

import fileio.*;
import query.QueryActors;
import query.QueryUsers;
import query.QueryVideos;
import checker.Checkstyle;
import checker.Checker;

import common.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import recommendation.RecPremiumUser;
import recommendation.RecUser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation
        Database db = new Database();
        final ArrayList<Movie> movieData = db.populateWithMovies(input);
        final ArrayList<User> userData = db.populateWithUsers(input);
        final ArrayList<Serial> serialData = db.populateWithSerials(input);
        final ArrayList<Actor> actorData = db.populateWithActors(input);
        for (int i = 0; i < input.getCommands().size(); i++) {
            ActionInputData commandi = input.getCommands().get(i);
            String title = commandi.getTitle();
            String user = commandi.getUsername();
            int id = commandi.getActionId();
            QueryActors resact = new QueryActors();
            QueryVideos resolvevideos = new QueryVideos();
            String result = null;
            JSONObject print = new JSONObject();
            if (commandi.getActionType().compareTo("command") == 0) {
                if (commandi.getType().compareTo("favorite") == 0) {
                    AddFavorite addFavorite = new AddFavorite();
                    addFavorite.addfav(id, title, user, userData,
                            arrayResult, fileWriter);
                }  else if (commandi.getType().compareTo("view") == 0) {
                    View view = new View();
                    view.view(id, title, user, userData,
                            arrayResult, fileWriter);
                } else if (commandi.getType().compareTo("rating") == 0) {
                    if (commandi.getSeasonNumber() == 0) {
                        Rating rating = new Rating();
                        rating.rateMovie(id, title, user, userData, commandi.getGrade(),
                                movieData, arrayResult, fileWriter);
                    } else {
                        Rating rating = new Rating();
                        rating.rateSerial(id, title, user, userData, commandi.getGrade(),
                                commandi.getSeasonNumber(), serialData, arrayResult, fileWriter);
                    }
                }
            } else if (commandi.getActionType().compareTo("query") == 0) {
                if (commandi.getObjectType().compareTo("actors") == 0) {
                    if (commandi.getCriteria().compareTo("average") == 0) {
                        if (commandi.getSortType().compareTo("asc") == 0) {
                            result = resact.average(actorData, movieData,
                                    serialData, 0, commandi.getNumber());
                        } else {
                            result = resact.average(actorData, movieData,
                                    serialData, 1, commandi.getNumber());
                        }
                        print = fileWriter.writeFile(id, "message", result);
                        arrayResult.add(print);
                    } else if (commandi.getCriteria().compareTo("awards") == 0) {
                        List<String> a = commandi.getFilters().get(3);
                        if (commandi.getSortType().compareTo("asc") == 0) {
                            result = resact.awards(actorData, a, 0, commandi.getNumber());
                        } else {
                            result = resact.awards(actorData, a, 1, commandi.getNumber());
                        }
                        print = fileWriter.writeFile(id, "message", result);
                        arrayResult.add(print);
                    } else if (commandi.getCriteria().compareTo("filter_description") == 0) {
                        if (commandi.getSortType().compareTo("asc") == 0) {
                            result = resact.filterdescription(actorData, commandi.getFilters());
                        } else {
                            result = resact.filterdescription(actorData, commandi.getFilters());
                        }

                        print = fileWriter.writeFile(id, "message", result);
                        arrayResult.add(print);
                    }
                } else if (commandi.getObjectType().compareTo("movies") == 0) {
                    if (commandi.getCriteria().compareTo("ratings") == 0) {
                        if (commandi.getSortType().compareTo("asc") == 0) {
                            result = resolvevideos.ratingMovie(actorData,
                                    movieData, 0, commandi.getNumber());
                        } else {
                            result = resolvevideos.ratingMovie(actorData,
                                    movieData, 1, commandi.getNumber());
                        }
                        print = fileWriter.writeFile(id, "message", result);
                        arrayResult.add(print);
                    } else if (commandi.getCriteria().compareTo("most_viewed") == 0) {
                        if (commandi.getSortType().compareTo("asc") == 0) {
                            result = resolvevideos.mostviewedmovie(userData, movieData,
                                    commandi.getFilters(), 0, commandi.getNumber());
                        } else {
                            result = resolvevideos.mostviewedmovie(userData, movieData,
                                    commandi.getFilters(), 1, commandi.getNumber());
                        }
                        print = fileWriter.writeFile(id, "message", result);
                        arrayResult.add(print);
                    } else if (commandi.getCriteria().compareTo("longest") == 0) {
                        if (commandi.getSortType().compareTo("asc") == 0) {
                            result = resolvevideos.longestmovie(movieData, commandi.getFilters(),
                                      0, commandi.getNumber());
                        } else {
                            result = resolvevideos.longestmovie(movieData, commandi.getFilters(),
                                      1, commandi.getNumber());
                        }
                        print = fileWriter.writeFile(id, "message", result);
                        arrayResult.add(print);
                    } else if (commandi.getCriteria().compareTo("favorite") == 0) {
                        if (commandi.getSortType().compareTo("asc") == 0) {
                            result = resolvevideos.favoritemovie(userData, movieData,
                                    commandi.getFilters(), 0, commandi.getNumber());
                        } else {
                            result = resolvevideos.favoritemovie(userData, movieData,
                                    commandi.getFilters(), 1, commandi.getNumber());
                        }
                        print = fileWriter.writeFile(id, "message", result);
                        arrayResult.add(print);
                    }
                } else if (commandi.getObjectType().compareTo("shows") == 0) {
                    if (commandi.getCriteria().compareTo("ratings") == 0) {
                        if (commandi.getSortType().compareTo("asc") == 0) {
                            result = resolvevideos.ratingSerial(actorData,
                                    serialData, 0, commandi.getNumber());
                        } else {
                            result = resolvevideos.ratingSerial(actorData,
                                    serialData, 1, commandi.getNumber());
                        }
                        print = fileWriter.writeFile(id, "message", result);
                        arrayResult.add(print);
                    } else if (commandi.getCriteria().compareTo("longest") == 0) {
                        if (commandi.getSortType().compareTo("asc") == 0) {
                            result = resolvevideos.longestserial(serialData, commandi.getFilters(),
                                     0, commandi.getNumber());
                        } else {
                            result = resolvevideos.longestserial(serialData, commandi.getFilters(),
                                     1, commandi.getNumber());
                        }
                        print = fileWriter.writeFile(id, "message", result);
                        arrayResult.add(print);
                    } else if (commandi.getCriteria().compareTo("most_viewed") == 0) {
                        if (commandi.getSortType().compareTo("asc") == 0) {
                            result = resolvevideos.mostviewedserial(userData, serialData,
                                    commandi.getFilters(), 0, commandi.getNumber());
                        } else {
                            result = resolvevideos.mostviewedserial(userData, serialData,
                                    commandi.getFilters(), 1, commandi.getNumber());
                        }
                        print = fileWriter.writeFile(id, "message", result);
                        arrayResult.add(print);
                    } else if (commandi.getCriteria().compareTo("favorite") == 0) {
                        if (commandi.getSortType().compareTo("asc") == 0) {
                            result = resolvevideos.favoriteserial(userData, serialData,
                                    commandi.getFilters(), 0, commandi.getNumber());
                        } else {
                            result = resolvevideos.favoriteserial(userData, serialData,
                                    commandi.getFilters(), 1, commandi.getNumber());
                        }
                        print = fileWriter.writeFile(id, "message", result);
                        arrayResult.add(print);
                    }
                } else if (commandi.getObjectType().compareTo("users") == 0) {
                    QueryUsers resolve = new QueryUsers();
                    if (commandi.getSortType().compareTo("asc") == 0) {
                        result = resolve.numofratings(userData, movieData, serialData, 0);
                    } else {
                        result = resolve.numofratings(userData, movieData, serialData, 1);
                    }
                    print = fileWriter.writeFile(id, "message", result);
                    arrayResult.add(print);
                }
            } else if (commandi.getActionType().compareTo("recommendation") == 0) {
                if (commandi.getType().compareTo("standard") == 0) {
                    RecUser rec = new RecUser();
                    String res = null;
                    for (User us : userData) {
                        if (us.getUsername().compareTo(commandi.getUsername()) == 0) {
                            res = rec.standard(movieData, serialData, us);
                            break;
                        }
                    }
                    if (res != null) {
                        print = fileWriter.writeFile(id, "message",
                                "StandardRecommendation result: " + res);
                    } else {
                        print = fileWriter.writeFile(id, "message",
                                "StandardRecommendation cannot be applied!");
                    }
                    arrayResult.add(print);
                } else if (commandi.getType().compareTo("best_unseen") == 0) {
                    RecUser rec = new RecUser();
                    String res = null;
                    for (User us : userData) {
                        if (us.getUsername().compareTo(commandi.getUsername()) == 0) {
                            res = RecUser.bestunseen(us, movieData, serialData);
                            break;
                        }
                    }

                    if (res != null) {
                        print = fileWriter.writeFile(id, "message",
                                "BestRatedUnseenRecommendation result: " + res);
                    } else {
                        print = fileWriter.writeFile(id, "message",
                                "BestRatedUnseenRecommendation cannot be applied!");
                    }
                    arrayResult.add(print);
                } else if (commandi.getType().compareTo("search") == 0) {
                    RecUser rec = new RecUser();
                    ArrayList<String> res = null;
                    for (User us : userData) {
                        if (us.getUsername().compareTo(commandi.getUsername()) == 0) {
                            res = RecPremiumUser.search(movieData, serialData,
                                    us, commandi.getGenre());
                            break;
                        }
                    }

                    if (res != null) {
                        print = fileWriter.writeFile(id, "message",
                                "SearchRecommendation result: " + res);
                    } else {
                        print = fileWriter.writeFile(id, "message",
                                "SearchRecommendation cannot be applied!");
                    }

                    arrayResult.add(print);
                } else if (commandi.getType().compareTo("favorite") == 0) {
                    result = "FavoriteRecommendation cannot be applied!";
                    print = fileWriter.writeFile(id, "message", result);
                    arrayResult.add(print);
                } else if (commandi.getType().compareTo("popular") == 0) {
                    result = "PopularRecommendation cannot be applied!";
                    print = fileWriter.writeFile(id, "message", result);
                    arrayResult.add(print);
                }
            }
        }
        fileWriter.closeJSON(arrayResult);
    }
}
