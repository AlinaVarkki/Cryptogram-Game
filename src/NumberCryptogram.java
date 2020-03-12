import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class NumberCryptogram extends Cryptogram {

    List<Character> cryptogramAlphabet = new ArrayList<>();
    private HashMap<Character, String> number_key_map;



    public NumberCryptogram(File String){

    }

    public NumberCryptogram(){
        encryptionMap();
    }


    //method that generates map for encryption
    //it maps every letter to a number
    @Override
    public HashMap<Character, String> encryptionMap(){

        number_key_map = new HashMap<>();

        //arraylist to add alphabet to the map as a key without changing this array
        //those values will be used as keys
        ArrayList<Character> alphabetNoChange = new ArrayList<>();
        //arraylist to hold numbers from 1-26, they'll be randomly assigned to the alphabet
        ArrayList<Integer> numbers = new ArrayList<>();
        int MAP_KEY_INDEX = 0;


        //populate array with alphabetical characters
        for (char c='a'; c <= 'z'; c++){
            alphabetNoChange.add(c);
        }

        //populate array with numbers
        for (int d=1; d <= 26; d++){
            numbers.add(d);
        }

        //add random letters into map with keys from a to z
        while(numbers.size() != 0){
            //random number to take one of array number and put into the map
            Random rand = new Random();
            int random = rand.nextInt(numbers.size());
            number_key_map.put(alphabetNoChange.get(MAP_KEY_INDEX), numbers.get(random).toString());
            MAP_KEY_INDEX ++;
            numbers.remove(random);
        }
        //space should be mapped to space
        number_key_map.put(' '," ");
        return number_key_map;
    }

    @Override
    public HashMap<Character, String> getMap(){
        return number_key_map;
    }

    public char getPlainLetter(char cryptoLetter){

        return 'a';
    }


}
