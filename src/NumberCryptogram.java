import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class NumberCryptogram extends Cryptogram<String> {

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
    public String EncryptedCryptogram() throws FileNotFoundException {
        String a = returnPhrase();
        StringBuilder encrypted_cryptogram = new StringBuilder();
        for(int i = 0; i < a.length(); i++){
            if(a.charAt(i) == ' '){
                encrypted_cryptogram.append("   ");
            }
            else{
                encrypted_cryptogram.append(number_key_map.get(a.charAt(i)));
                encrypted_cryptogram.append(' ');
            }
        }
        return encrypted_cryptogram.toString();

    }

    @Override
    public void enterLetter(String a, Character b, String cryptogram, HashMap<Character, String> user_solution) {
        //not letting user add letter to the solution if cryptogram doesn't contain this letter
        if(!cryptogram.contains(a)){
            System.out.println("Cryptogram does not contain this letter");
        }
        //if user has already set this letter, loop through values and remove it and it's key. Then put new value to the map
        else{
            if(user_solution.containsValue(a)){
                for (Map.Entry<Character, String> entry : user_solution.entrySet()) {
                    if (entry.getValue().equals(a)){
                        user_solution.remove(entry.getKey());
                    }
                }
                user_solution.remove(b);
            }
            user_solution.put(b, a);
        }
    }

    //method to display current user solution
    @Override
    public String showCurrentState(String cryptogram, HashMap<Character, String> user_solution){
        String[] tokens1 = cryptogram.split(" ");

        StringBuilder currentState = new StringBuilder();
        for(int e = 0; e < tokens1.length; e++){
            //if the character in the cryptogram is the value of a key in the map solution, it prints it out at the correct place
            if(user_solution.containsValue(tokens1[e])){
                for (Map.Entry<Character, String> entry : user_solution.entrySet()) {
                    if (entry.getValue().equals(tokens1[e])) {
                        currentState.append(entry.getKey());
                        currentState.append(" ");
                    }
                }
            }
            else{
                if(tokens1[e].equals("   "))
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
    public HashMap<Character, String> getMap(){
        return number_key_map;
    }

    public char getPlainLetter(char cryptoLetter){

        return 'a';
    }


}
