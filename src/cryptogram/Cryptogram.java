package cryptogram;

import controllers.LogInController;
import player.Player;
import java.io.Serializable;
import java.util.*;

import static cryptogram.CryptLoader.gamePhrase;

public abstract class Cryptogram implements Serializable{
    public abstract HashMap getMap();
    public abstract HashMap encryptionMap();

    //stores real phrase for the cryptogram
    public static HashMap<Character, String> usedMapping;

    private String phrase = gamePhrase;
    private String encrypted_puzzle;
    //List<player.Player> allPlayers = new ArrayList<>();
    private String[] tokens = {""};

    //map that stores the mapping that user enters
    private HashMap<Character, String> user_solution = new HashMap<>();
    private HashSet phraseLetters = new HashSet();
    //instance of login controller to get current user
    //getting current player
    private Player player = LogInController.currentPlayer;


    public HashMap<Character, String> getUser_solution() {
        return user_solution;
    }


    //method that returns letters in the cryptogram.Cryptogram as a set
    public Set<Character> getCryptogramCharacters() {
//        phrase = CryptLoader.returnPhrase();
        Set<Character> cryptogramCharacters = new HashSet<>();
        for(int i = 0; i < phrase.length(); i++){
            cryptogramCharacters.add(phrase.charAt(i));
        }
        return cryptogramCharacters;
    }


    //adding user input to the solution map
    public void enterLetter(String a, Character b) {
        int mapSize = user_solution.size();
        user_solution.put(b, a);
        HashMap solution_map = getMap();
        if(mapSize != user_solution.size()) {
            player.incrementTotalGuesses();
            System.out.println("total guesses " + player.getTotalGuesses());
            if (solution_map.get(b).equals(a)) {
                player.incrementCorrectGuesses();
                System.out.println("correct " + player.getCorrectGuesses());
            }
        }
    }

    //ecrypting current played phrase using the map generated in lettercryptogram and numbercryptogram
    public String encryptedCryptogram() {
        fillUsedMap();

        String a;
        a = phrase;
        StringBuilder encrypted_cryptogram = new StringBuilder();
        for(int i = 0; i < a.length(); i++){
            if(a.charAt(i) == ' '){
                encrypted_cryptogram.append(" ");
            }
            else{
                encrypted_cryptogram.append(getMap().get(a.charAt(i)));
            }
        }
        encrypted_puzzle = encrypted_cryptogram.toString();
        return encrypted_puzzle;
    }

    //removing user input from the map if they delete letter
    public void undoLetter(Character c){
            user_solution.remove(c);
    }


    //method checks if current solution is correct
    public boolean checkSolution() {
        HashMap solution_map = getMap();
        solution_map.remove( ' ');
        Set<Character> solutionkeys = getCryptogramCharacters();
        //need to remove space from keys because user doesn't enter it but it is contained in the map
        solutionkeys.remove(' ');
        Set<Character> input_keys = getUser_solution().keySet();
        boolean correct = true;
        //if keys in solution and answer are not the same, the answer is incorrect
        if(!solutionkeys.containsAll(input_keys)){
            correct = false;
        }
        if(!input_keys.containsAll(solutionkeys)){
            correct = false;
        }
        //if every mapping of user is the same as the correct mapping, the answer if correct
        for(Character b: input_keys){
            if(!solution_map.get(b).equals(getUser_solution().get(b))){
                correct = false;
            }
        }
        return correct;
    }

    //checks if user doesn't have any free input fields
    public boolean allPlacesFilled(){
        for(int i = 0; i<phrase.length(); i++){
            phraseLetters.add(phrase.charAt(i));
        }
        phraseLetters.remove(' ');
        return user_solution.keySet().size() == phraseLetters.size();
    }

    public String returnPhrase(){
        return phrase;
    }

    public String returnSolution(){
        return phrase;
    }

    //methods just for testing
    public void setPhrase(String a){
        phrase = a;
    }
    public void setEncyptedPhrase(String a){
        encrypted_puzzle = a;
    }

    //returns letters in cryptogram and mapping
    public void fillUsedMap(){
        usedMapping = new HashMap<>();
        for(int i = 0; i < phrase.length(); i++){
            usedMapping.put(phrase.charAt(i), (String) getMap().get(phrase.charAt(i)));
        }
    }

    public Character giveHint(){
        ArrayList keys = new ArrayList();
        for(int i = 0; i<phrase.length(); i++){
            keys.add(String.valueOf(phrase.charAt(i)));
        }
        Random rand = new Random();
        int random;
        random = rand.nextInt(phrase.length());


    return phrase.charAt(random);
    }
}
