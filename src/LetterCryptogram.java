import java.io.File;
import java.io.FileNotFoundException;
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
    public void enterLetter(String a, Character b, String cryptogram, HashMap<Character, Character> user_solution){
        //not letting user add letter to the solution if cryptogram doesn't contain this letter
        if(!cryptogram.contains(a)){
            System.out.println("Cryptogram does not contain this letter");
        }
        //if user has already set this letter, loop through values and remove it and it's key. Then put new value to the map
        else{
            if(user_solution.containsValue(a.charAt(0))){
                for (Map.Entry<Character, Character> entry : user_solution.entrySet()) {
                    if (entry.getValue().equals(a.charAt(0))){
                        user_solution.remove(entry.getKey());
                    }
                }
                user_solution.remove(b);
            }

            user_solution.put(b, a.charAt(0));
        }
    }

    @Override
    //method to display current user solution
    public String showCurrentState(String cryptogram, HashMap<Character, Character> user_solution){

        StringBuilder currentState = new StringBuilder();
        int e = 0;
        for(int i = 0; i < cryptogram.length(); i++){
            //if the character in the cryptogram is the value of a key in the map solution, it prints it out at the correct place
            if(user_solution.containsValue(cryptogram.charAt(i))){
                for (Map.Entry<Character, Character> entry : user_solution.entrySet()) {
                    if (entry.getValue().equals(cryptogram.charAt(i))) {
                        currentState.append(entry.getKey());
                        currentState.append(" ");
                    }
                }
            }
            else{
                if(cryptogram.charAt(i) == ' ')
                    currentState.append("   ");
                else{
                    currentState.append("_");
                    currentState.append(" ");
                }

            }

        }
        return currentState.toString();
    }

    @Override
    //changing letters in the cypher for the ones in the map
    public String EncryptedCryptogram() throws FileNotFoundException {
        String a = returnPhrase();
        StringBuilder encrypted_cryptogram = new StringBuilder();
        for(int i = 0; i < a.length(); i++){
            if(a.charAt(i) == ' '){
                encrypted_cryptogram.append(' ');
            }
            else{
                encrypted_cryptogram.append(map.get(a.charAt(i)));
            }
        }
        return encrypted_cryptogram.toString();
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
