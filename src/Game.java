import java.security.spec.RSAOtherPrimeInfo;
import java.util.*;

public class Game {


    Player currentPlayer;
    //map to store the encryption used
    private HashMap<Character, Character> answer;
    private static LetterCryptogram newCryptogram = new LetterCryptogram();
    //map that stores the mapping that user enteres
    private HashMap<Character, Character> solution = new HashMap<>();
    //initializing tokens of user input
    private String[] tokens = {""};
    private static String cryptogram;

    public Game(Player p, String cryptType){}

    public Game(Player p){}

    public Game(){
        printRules();
        System.out.println(generateCryptogram().toUpperCase());
        takeInput();
    }

    //prints rules in console
    private void printRules(){
        System.out.println("To set encrypted letter to the solution enter 'set [encrypted letter] [real letter]'");
        System.out.println("To remove set letter enter 'remove [encrypted letter]'");
        System.out.println("To check your solution, enter 'check'");
        System.out.println("To exit enter 'exit'");
        System.out.println();
        System.out.println();
    }

    //takes user input and calls methods accordingly
    private void takeInput(){
        boolean shouldcontinue = true;

        while(shouldcontinue) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            tokens = input.split(" ");

            try {
                if (tokens[0].equals("set") && tokens[1] != null && tokens[1].length() == 1 && tokens[2] != null && tokens[2].length() == 1 && tokens.length < 4) {
                    enterLetter(tokens[1].charAt(0), tokens[2].charAt(0));
                }
                else if (tokens[0].equals("remove") && tokens[1] != null && solution.containsKey(tokens[1].charAt(0)) && tokens.length < 3) {
                    undoLetter(tokens[1].charAt(0));
                }
                else if (tokens[0].equals("exit")) {
                    shouldcontinue = false;
                }
                else if (tokens[0].equals("check")) {
                    boolean correct = checkSolution();
                    if (!correct) {
                        System.out.println("your solution is wrong");
                    } else {
                        System.out.println("good job");
                    }
                }
                else{
                    System.out.println("Sorry syntax is wrong");
                }
            }
            catch(Exception e){
                System.out.println(" ");
            }

            System.out.println(showCurrentState().toUpperCase());
            System.out.println("_________________________________________");
        }
    }

    //adding user input to the solution map
    private void enterLetter(Character a, Character b){
        if(!cryptogram.contains(a.toString())){
            System.out.println("Cryptogram does not contain this letter");
        }
        else if(solution.containsValue(a)){
            for (Map.Entry<Character, Character> entry : solution.entrySet()) {
                if (entry.getValue().equals(a)){
                    solution.remove(entry.getKey());
                }
            }
            solution.remove(b);
            solution.put(b, a);
        }
        else {
            solution.put(b, a);
        }
    }

    //removing user input from the map
    private void undoLetter(Character c){
        solution.remove(c);
    }

    //works but doesn't check the solution properly, checks all the letters, not just the ones in the cryptogram
    //key set should be equal to the set of real characters in the cryptogram
    private boolean checkSolution(){
        answer =  newCryptogram.getMap();
        answer.remove( ' ');
        Set<Character> solutionkeys = newCryptogram.getCryptogramCharacters();
        //need to remove space from keys because user doesn't enter it but it is contained in the map
        solutionkeys.remove(' ');
        Set<Character> keys = solution.keySet();
        boolean correct = true;
        if(!solutionkeys.containsAll(keys)){
            correct = false;
        }
        if(!keys.containsAll(solutionkeys)){
            correct = false;
        }
        for(Character b: keys){
            if(!answer.get(b).equals(solution.get(b))){
                correct = false;
            }
        }
        return correct;
    }

    private static String generateCryptogram(){
        cryptogram = newCryptogram.alphEncryptedCryptogram();
        return cryptogram;
    }

    //method that displays current mapping user has
    //display the same amount of dashes as there are letters in the cryptogram
    //loop through the amount of letters that are in the ccrypt
    //if map has it, print the letter, if no, the dash
    private String showCurrentState(){
        StringBuilder currentState = new StringBuilder();
        for(int i = 0; i < cryptogram.length(); i++){
            //if the character in the cryptogram is the value of a key in the map solution, it prints it out at the correct place
            //if character in a map
            //user maps eencrypted letter to real letter
            if(solution.containsValue(cryptogram.charAt(i))){
                for (Map.Entry<Character, Character> entry : solution.entrySet()) {
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

    public void showSolution(){}

        public char getHint(){
        return 'a';
    }

    public void loadPlayer(){}

    public void playGame(){}

    public void viewFrequencies(){}

    public void saveGame(){}

    public void loadGame(){}


}
