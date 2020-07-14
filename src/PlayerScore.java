import java.util.ArrayList;

public class PlayerScore {
    private String name;
    private int score;

    public static ArrayList<PlayerScore> listPlayers = new ArrayList<>();

    public static boolean CheckAPlayerIsInList(PlayerScore p1){
        for (PlayerScore p2 : listPlayers){
            if (p1.getName().equalsIgnoreCase(p2.getName())){
                return true;
            }
        }
        return false;
    }

    public PlayerScore(){

    }

    public PlayerScore(String N, int S){
        this.name = N;
        this.score = S;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }
}
