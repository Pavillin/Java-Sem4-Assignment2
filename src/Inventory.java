import java.util.ArrayList;
import java.util.Arrays;

public class Inventory {
    public static ArrayList getGenres(){
        String validGenres[] = {"Action", "Action-Adventure", "Adventure", "Role-Playing", "Simulation",
                                "Strategy", "Sports", "MMO"};
        ArrayList<String> genres = new ArrayList<>(Arrays.asList(validGenres));
        return genres;
    }
}
