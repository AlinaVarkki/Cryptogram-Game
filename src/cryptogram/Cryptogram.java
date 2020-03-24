package cryptogram;

import game.Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public abstract class Cryptogram {
    public abstract HashMap getMap();
    public abstract HashMap encryptionMap();
    //stores real phrase for the cryptogram

    private String phrase;
    private String encrypted_puzzle;
    //List<player.Player> allPlayers = new ArrayList<>();
    private List<String> cryptograms = new ArrayList<>();
    private Scanner s;
    private Random rand = new Random();
    private Scanner scanner = new Scanner(System.in);
    private String[] tokens = {""};
    private int numGuesses = 0;
    private int numCorrectGuesses = 0;
    //map that stores the mapping that user enters
    private HashMap<Character, String> user_solution = new HashMap<>();
    private HashSet phraseLetters = new HashSet();



    public void setNumGuesses(int numGuesses) {
        this.numGuesses = numGuesses;
    }

    public void setNumCorrectGuesses(int numCorrectGuesses) {
        this.numCorrectGuesses = numCorrectGuesses;
    }

    public HashMap<Character, String> getUser_solution() {
        return user_solution;
    }

    public void setUser_solution(HashMap<Character, String> user_solution) {
        this.user_solution = user_solution;
    }

    //method to populate array with all the cryptograms
    private void populateCryptogramsArray()  {
        try {
            s = new Scanner(new File("C:\\Users\\USER\\Desktop\\cs207\\resources\\Cryptograms.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (s.hasNextLine()) {
                cryptograms.add(s.nextLine());
            }
            s.close();

    }

    //return random cryptogram.Cryptogram(by now random)
    public String returnPhrase()  {
        populateCryptogramsArray();
        int random = 0;
//        try{
        random = rand.nextInt(cryptograms.size());
//        }catch(Exception e){
//            System.out.println("Sorry there are no phrases");
//            System.exit(1);
//        }
        phrase = cryptograms.get(random);
        return phrase;
    }

    //method that returns letters in the cryptogram.Cryptogram as a set
    public Set<Character> getCryptogramCharacters() {
        //phrase = returnPhrase();
        Set<Character> cryptogramCharacters = new HashSet<>();
        for(int i = 0; i < phrase.length(); i++){
            cryptogramCharacters.add(phrase.charAt(i));
        }
        return cryptogramCharacters;
    }


    //adding user input to the solution map
    public void enterLetter(String a, Character b) {
        //encryptedCryptogram();
        //not letting user add letter to the solution if cryptogram doesn't contain this letter

//            if (!encrypted_puzzle.contains(a)) {
//                System.out.println("cryptogram.Cryptogram does not contain this letter");
//            }
            //if user has already set this letter, loop through values and remove it and it's key. Then put new value to the map
//            else {
//                if (user_solution.containsKey(b)) {
//                    System.out.println("You already mapped this letter to another encrypted char, you need to remove it first");
//                }
//                else if (user_solution.containsValue(a)) {
//                    //ask user if they want to change their solution
//                    System.out.println("You have already mapped this letter, do you want to change the mapping?");
//                    System.out.println("Enter 'Y' for yes and 'N' for no");
//                    String input = scanner.nextLine();
//                    tokens = input.split(" ");
//
//                    if (tokens[0].equals("y")) {
//                        for (Map.Entry<Character, String> entry : user_solution.entrySet()) {
//                            if (entry.getValue().equals(a)) {
//                                user_solution.remove(entry.getKey());
//                            }
//                        }
//                        // user_solution.remove(b);
//                        user_solution.put(b, a);
//                        updateStats(a, b);
//                    } else {
//                        return;
//                    }
//                }
//                else {
                    user_solution.put(b, a);
                    updateStats(a, b);
//                }
//            }


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
//            if(a.charAt(i) == ' '){
//                encrypted_cryptogram.append("   ");
//            }
//            else{
                encrypted_cryptogram.append(getMap().get(a.charAt(i)));
//                encrypted_cryptogram.append(' ');
//            }
        }
        encrypted_puzzle = encrypted_cryptogram.toString();
        return encrypted_puzzle;

    }


    public void updateStats(String setTo, Character key) {
        numGuesses = numGuesses + 1;
        if(getMap().get(key).equals(setTo.charAt(0))){
            numCorrectGuesses= numCorrectGuesses + 1;
        }
    }

    //removing user input from the map
    public void undoLetter(Character c){
        //if user solution doesn't contain the key to be removed, nothing happens
//        if(!user_solution.containsKey(c)){
//            System.out.println("You do not have character '" + c + "' set up");
//        }
//        else{
            user_solution.remove(c);
//        }
    }

    public Integer getNumGuesses(){
        return numGuesses;
    }


    public Integer getNumCorrectGuesses() {
        return numCorrectGuesses;
    }

    //returns letter and it's frequency
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
        if(user_solution.keySet().size() == phraseLetters.size()){
            return true;
        }
        return false;
    }

}
