package cryptogram;

import controllers.LogInController;
import game.Game;
import player.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;

public abstract class Cryptogram implements Serializable{
    public abstract HashMap getMap();
    public abstract HashMap encryptionMap();
    //stores real phrase for the cryptogram


    private String phrase = "sd lol";
    private String encrypted_puzzle;
    //List<player.Player> allPlayers = new ArrayList<>();
    private String[] tokens = {""};

    //map that stores the mapping that user enters
    private HashMap<Character, String> user_solution = new HashMap<>();
    private HashSet phraseLetters = new HashSet();
    //instance of login controller to get current user
    //getting current player
    private Player player = LogInController.currentPlayer;
    // private CryptLoader loader = new CryptLoader();


    public HashMap<Character, String> getUser_solution() {
        return user_solution;
    }

    public void setUser_solution(HashMap<Character, String> user_solution) {
        this.user_solution = user_solution;
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


    //method to display current user solution
    public String showCurrentState(){

        String[] tokens1 = encrypted_puzzle.split(" ");

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


    public String encryptedCryptogram() {
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



    //removing user input from the map
    public void undoLetter(Character c){
            user_solution.remove(c);
    }


    public String getFrequncies(){
        return "";
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
        //if keys in slution and answer are not the same, the answer is incorrect
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

    public boolean allPlacesFilled(){
        for(int i = 0; i<phrase.length(); i++){
            phraseLetters.add(phrase.charAt(i));
        }
        if(phraseLetters.contains(' ')){
            phraseLetters.remove(' ');
        }
        if(user_solution.keySet().size() == phraseLetters.size()){
            return true;
        }
        return false;
    }

    public String returnPhrase(){
        return phrase;
    }
}
