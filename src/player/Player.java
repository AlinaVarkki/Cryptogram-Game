package player;

public class Player {

    public int totalGuesses;
    public int correctGuesses;
    public int cryptogramsPlayed;
    public int cryptogramsCompleted;
    public String username;
    public String password;

    //constructor for newly registered user
    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        totalGuesses = 0;
        correctGuesses = 0;
        cryptogramsCompleted = 0;
        cryptogramsPlayed = 0;
    }

    public int updateAccuracy(){
        return correctGuesses/totalGuesses;
    }

    public void incrementCryptogramsCompleted(){
        cryptogramsCompleted++;
    }

    public void incrementCryptogramsPlayed(){
        cryptogramsPlayed++;
    }

    public void incrementTotalGuesses() {
        totalGuesses++;
    }

    public void incrementCorrectGuesses() {
        correctGuesses++;
    }

    public int getNumCryptogramsCompleted(){
        return cryptogramsCompleted;
    }

    public int getNumCryptogramsPlayed(){
        return cryptogramsPlayed;
    }

    public int getTotalGuesses() {
        return totalGuesses;
    }

    public int getCorrectGuesses() {
        return correctGuesses;
    }

    public void setGuesses(int n) {
        totalGuesses = n;
    }

    public void setCorrectGuesses(int n) {
        correctGuesses = n;
    }

    public void setPlayed(int n) {
        cryptogramsPlayed = n;
    }

    public void setCompleted(int n) {
        cryptogramsCompleted = n;
    }

}
