import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

abstract class Cryptogram {
    public abstract HashMap getMap();
    public abstract HashMap encryptionMap();
    //stores real phrase for the cryptogram

    private String phrase;
    List<Player> allPlayers = new ArrayList<>();
    private List<String> cryptograms = new ArrayList<>();
    private Scanner s;
    private Random rand = new Random();
    private Scanner scanner = new Scanner(System.in);
    private String[] tokens = {""};
    private int numGuesses = 0;
    private int numCorrectGuesses = 0;


    //method to populate array with all the cryptograms
    private void populateCryptogramsArray() throws FileNotFoundException {
            s = new Scanner(new File("resources\\Cryptograms.txt"));

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


    //adding user input to the solution map
    public void enterLetter(String a, Character b, String cryptogram, HashMap<Character, String> user_solution){
        //not letting user add letter to the solution if cryptogram doesn't contain this letter
        if(!cryptogram.contains(a)){
            System.out.println("Cryptogram does not contain this letter");
        }
        //if user has already set this letter, loop through values and remove it and it's key. Then put new value to the map
        else{
            if(user_solution.containsKey(b)) {
                System.out.println("You already mapped this letter to another encrypted char, you need to remove it first");
            }
            else if (user_solution.containsValue(a)) {
                //ask user if they want to change their solution
                System.out.println("You have already mapped this letter, do you want to change the mapping?");
                System.out.println("Enter 'Y' for yes and 'N' for no");
                String input = scanner.nextLine();
                tokens = input.split(" ");

                if (tokens[0].equals("y")) {
                    for (Map.Entry<Character, String> entry : user_solution.entrySet()) {
                        if (entry.getValue().equals(a)) {
                            user_solution.remove(entry.getKey());
                        }
                    }
                    // user_solution.remove(b);
                    user_solution.put(b, a);
                    updateStats(a, b);
                } else {
                    return;
                }
            } else {
                user_solution.put(b, a);
                updateStats(a, b);
            }
        }
    }


    //method to display current user solution
    public String showCurrentState(String cryptogram, HashMap<Character, String> user_solution){

        String[] tokens1 = cryptogram.split(" ");

        StringBuilder currentState = new StringBuilder();
        for (String s : tokens1) {
            //if the character in the cryptogram is the value of a key in the map solution, it prints it out at the correct place
            if (user_solution.containsValue(s)) {
                for (Map.Entry<Character, String> entry : user_solution.entrySet()) {
                    if (entry.getValue().equals(s)) {
                        currentState.append(entry.getKey());
                        currentState.append(" ");
                    }
                }
            } else {
                if (getMap().containsValue(s)){
                    currentState.append("_");
                    currentState.append(" ");
                }
                else {
                    currentState.append(" ");
                }

            }

        }
        return currentState.toString();
    }


    public String EncryptedCryptogram() throws FileNotFoundException {
        String a = returnPhrase();
        StringBuilder encrypted_cryptogram = new StringBuilder();
        for(int i = 0; i < a.length(); i++){
            if(a.charAt(i) == ' '){
                encrypted_cryptogram.append("   ");
            }
            else{
                encrypted_cryptogram.append(getMap().get(a.charAt(i)));
                encrypted_cryptogram.append(' ');
            }
        }
        return encrypted_cryptogram.toString();

    }


    public void updateStats(String setTo, Character key) {
        numGuesses = numGuesses + 1;
        if(getMap().get(key).equals(setTo.charAt(0))){
            numCorrectGuesses= numCorrectGuesses + 1;
        }
    }

    //removing user input from the map
    public void undoLetter(Character c, HashMap<Character, String> user_solution){
        //if user solution doesn't contain the key to be removed, nothing happens
        if(!user_solution.containsKey(c)){
            System.out.println("You do not have character '" + c + "' set up");
        }
        else{
            user_solution.remove(c);
        }
    }

    public Integer getNumGuesses(){
        return numGuesses;
    }


    public Integer getNumCorrectGuesses() {
        return numCorrectGuesses;
    }

    //returns letter and it's frequency
    public String getFreqeuncies(){
        return "";
    }


}
