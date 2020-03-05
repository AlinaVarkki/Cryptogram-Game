public class Game {

    //what data type is this
    //playerGameMapping

    Player currentPlayer;

    public Game(Player p, String cryptType){

    }

    public Game(Player p){

    }

    public static void main(String[] argc){
        System.out.println(generateCryptogram());
    }

    public char getHint(){
        return 'a';
    }

    public void loadPlayer(){

    }

    public void playGame(){

    }

    private static String generateCryptogram(){
        LetterCryptogram newCryptogram = new LetterCryptogram();
        return newCryptogram.alphEncryptedCryptogram();
    }

    public void enterLetter(char a){

    }

    public void undoLetter(){}

    public void viewFrequencies(){}

    public void saveGame(){}

    public void loadGame(){}

    public void showSolution(){}


}
