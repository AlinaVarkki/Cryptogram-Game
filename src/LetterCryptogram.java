import java.io.File;
import java.util.*;

public class LetterCryptogram extends Cryptogram <Character> {

    private List<Character> cryptogramAlphabet = new ArrayList<>();
    private HashMap<Character, Character> map;

    public LetterCryptogram(File String){

    }

    //map for cryptogram encryption generated every time when the object is created
    public LetterCryptogram(){
        encryptionMap();
    }

    //method that generates map for encyption
    //the output is a map that has real alphabet mapped to the letter for encryption
    @Override
    public HashMap<Character, Character> encryptionMap(){

        map = new HashMap<>();

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

    //adding user input to the solution map
    @Override
    public void enterLetter(Character a, Character b, String cryptogram, HashMap<Character, Character> user_solution){
        //not letting user add letter to the solution if cryptogram doesn't contain this letter
        if(!cryptogram.contains(a.toString())){
            System.out.println("Cryptogram does not contain this letter");
        }
        //if user has already set this letter, loop through values and remove it and it's key. Then put new value to the map
        else{
            if(user_solution.containsValue(a)){
                for (Map.Entry<Character, Character> entry : user_solution.entrySet()) {
                    if (entry.getValue().equals(a)){
                        user_solution.remove(entry.getKey());
                    }
                }
                user_solution.remove(b);
            }

            user_solution.put(b, a);
        }
    }

    //return map for the answer
    @Override
    public HashMap<Character, Character> getMap(){
        return map;
    }


    public char getPlainLetter(char cryptoLetter){

        return 'a';
    }


}
