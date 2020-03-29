//user stats are stored in this class

package player;

import java.io.Serializable;

public class Player implements Serializable {

    public int totalGuesses;
    public int correctGuesses;
    public int cryptogramsPlayed;
    public int cryptogramsCompleted;
    public String username;
    public String password;

    //constructor for newly registered user
    public Player(String username) {
        this.username = username;
        totalGuesses = 0;
        correctGuesses = 0;
        cryptogramsCompleted = 0;
        cryptogramsPlayed = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    //percentage that shows how accurate you are
    public double returnAccuracy(){
        if(correctGuesses == 0){
            return 0.00;
        }
        return ((double) correctGuesses/(double) totalGuesses)*100.00;
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

    //decrementing when user used hint. When something is entered to textboxes, It is automatically incremented because of the listener
    public void decrementTotalGuesses() {
        totalGuesses--;
    }

    public void decrementCorrectGuesses() {
        correctGuesses--;
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
