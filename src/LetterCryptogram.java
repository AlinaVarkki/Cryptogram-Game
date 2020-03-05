import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class LetterCryptogram extends Cryptogram {

    private List<Character> cryptogramAlphabet = new ArrayList<>();
    private HashMap<Character, Character> map = encryptionMap();


    public LetterCryptogram(File String){

    }

    public LetterCryptogram(){

    }

    //method that generates map for encyption
    //the output is a map that has real alphabet mapped to the letter for encryption
    private HashMap<Character, Character> encryptionMap(){

        HashMap<Character, Character> map = new HashMap<>();

        //arraylist to add alphabet to the map as a key without changing this array
        ArrayList<Character> alphabetNoChange = new ArrayList<>();
        //alphabet to randomly take letters from and remove them
        ArrayList<Character> alphabet = new ArrayList<>();
        int MAP_KEY_INDEX = 0;


        //populate arrays with alphabetical characters
        for (char c='a'; c <= 'z'; c++){
            alphabet.add(c);
            alphabetNoChange.add(c);
        }

        //add random characters into map with keys from a to z
        while(alphabet.size() != 0){
            //random number to take one of array characters and put into the map
            Random rand = new Random();
            int random = rand.nextInt(alphabet.size());
                map.put(alphabetNoChange.get(MAP_KEY_INDEX), alphabet.get(random));
            MAP_KEY_INDEX ++;
            alphabet.remove(random);
        }
        //space should be mapped to space
        map.put(' ', ' ');
        return map;
    }

    //changing letters in the cypher for the ones in the map
    public String alphEncryptedCryptogram() {
        String a = returnCryptogram();
        StringBuilder encrypted_cryptogram = new StringBuilder();
        for(int i = 0; i < a.length(); i++){
            encrypted_cryptogram.append(map.get(a.charAt(i)));
        }
        return encrypted_cryptogram.toString();
    }

    //return map for the answer
    public HashMap<Character, Character> getMap(){
        return map;
    }


    public char getPlainLetter(char cryptoLetter){

        return 'a';
    }


}
