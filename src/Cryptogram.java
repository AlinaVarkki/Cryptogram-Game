import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

abstract class Cryptogram <T> {
    public abstract HashMap getMap();
    public abstract HashMap encryptionMap();
    public abstract String EncryptedCryptogram() throws FileNotFoundException;
    public abstract void enterLetter(String charAt, Character charAt1, String cryptogram, HashMap<Character, T> user_solution);
    public abstract String showCurrentState(String cryptogram, HashMap<Character, T> user_solution);
    public abstract void updateStats(String setTo, Character key);
    public abstract Integer getNumGuesses();
    public abstract Integer getNumCorrectGuesses();
    //stores real phrase for the cryptogram

    private String phrase;
    List<Player> allPlayers = new ArrayList<>();
    private List<String> cryptograms = new ArrayList<>();
    private Scanner s;
    private Random rand = new Random();

    //method to populate array with all the cryptograms
    private void populateCryptogramsArray() throws FileNotFoundException {
            s = new Scanner(new File("C:\\Users\\USER\\Desktop\\cs207\\resources\\Cryptograms.txt"));

            while (s.hasNextLine()) {
                cryptograms.add(s.nextLine());
            }
            s.close();

    }

    //return random Cryptogram(by now random)
    public String returnPhrase() throws FileNotFoundException {
        populateCryptogramsArray();
        int random = 0;
        try{
        random = rand.nextInt(cryptograms.size());
        }catch(Exception e){
            System.out.println("Sorry there are no phrases");
            System.exit(1);
        }
        return cryptograms.get(random);
    }

    //method that returns letters in the Cryptogram as a set
    public Set<Character> getCryptogramCharacters() throws FileNotFoundException {
        phrase = returnPhrase();
        Set<Character> cryptogramCharacters = new HashSet<>();
        for(int i = 0; i < phrase.length(); i++){
            cryptogramCharacters.add(phrase.charAt(i));
        }
        return cryptogramCharacters;
    }




    //returns letter and it's frequency
    public String getFrequncies(){
        return "";
    }


}
