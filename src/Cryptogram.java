import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

abstract class Cryptogram <T> {
    public abstract HashMap getMap();
    public abstract HashMap encryptionMap();
    public abstract String EncryptedCryptogram();
    public abstract void enterLetter(String charAt, Character charAt1, String cryptogram, HashMap<Character, T> user_solution);
    public abstract String showCurrentState(String cryptogram, HashMap<Character, T> user_solution);


    //stores real phrase for the cryptogram

    String phrase;
    List<Player> allPlayers = new ArrayList<>();
    private List<String> cryptograms = new ArrayList<>();
    private Scanner s;
    private Random rand = new Random();

    //method to populate array with all the cryptograms
    private void populateCryptogramsArray() {
        try {
            s = new Scanner(new File("C:\\Users\\USER\\Desktop\\cs207\\resources\\Cryptograms.txt"));

            while (s.hasNextLine()) {
                cryptograms.add(s.nextLine());
            }
            s.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //return random Cryptogram(by now random)
    public String returnPhrase(){
        populateCryptogramsArray();
        int random = rand.nextInt(cryptograms.size());
        return cryptograms.get(random);
    }

    //method that returns letters in the Cryptogram as a set
    public Set<Character> getCryptogramCharacters(){
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
