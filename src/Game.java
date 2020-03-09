import java.io.FileNotFoundException;
import java.util.*;

public class Game <T>{


    Player currentPlayer;
    private Cryptogram crypt_object;
    //map to store the encryption used
    private Map solution_map;
    //map that stores the mapping that user enters
    private HashMap<Character, T> user_solution = new HashMap<>();
    //initializing tokens of user input
    private String[] tokens = {""};
    //current cryptogram
    private String cryptogram;
    private Scanner scanner = new Scanner(System.in);


    public Game(Player p, String cryptType){}

    public Game(Player p){}

    public Game() throws FileNotFoundException {
        chooseCryptogram();
        printRules();
        System.out.println(generateCryptogram().toUpperCase());
        takeInput();
    }

    private void chooseCryptogram(){
        System.out.println("To play letter cryptogram, type 'l', to play number cryptogram, type'n'");

        boolean shouldContinueAsking = true;
        while(shouldContinueAsking) {
            String cryptChoice = scanner.nextLine();
            tokens = cryptChoice.split(" ");
            try {
                if (tokens[0].equals("l")) {
                    crypt_object = new LetterCryptogram();
                    shouldContinueAsking = false;
                } else if (tokens[0].equals("n")) {
                    crypt_object = new NumberCryptogram();
                    shouldContinueAsking = false;
                }
                else{
                    System.out.println("Wrong syntax, type 'l' or 'n'");
                }
            }
            catch(Exception e) {
                System.out.println("Wrong syntax, type 'l' or 'n'");
            }
        }
    }

    //prints rules in console
    private void printRules(){
        System.out.println("To set encrypted letter to the solution enter 'set [encrypted letter] [real letter]'");
        System.out.println("To remove set letter enter 'remove [encrypted letter]'");
        System.out.println("To check your solution, enter 'check'");
        System.out.println("To see the solution, type 'solution'");
        System.out.println("To exit, enter 'exit'");
        System.out.println();
        System.out.println();
    }

    //takes user input and calls methods accordingly
   //loops until user enters exit
    private void takeInput(){
        boolean shouldcontinue = true;

        while(shouldcontinue) {
            String input = scanner.nextLine();

            tokens = input.split(" ");

            try {
                if (tokens[0].equals("set") && tokens[2].length() == 1 && tokens.length == 3) {
                        crypt_object.enterLetter(tokens[1], tokens[2].charAt(0), cryptogram, user_solution);
                }
                else if (tokens[0].equals("remove") &&  tokens.length == 2) {
                    undoLetter(tokens[1].charAt(0));
                }
                else if (tokens[0].equals("check")) {
                    boolean correct = checkSolution();
                    if (!correct) {
                        System.out.println("your solution is wrong");
                    } else {
                        System.out.println("good job");
                    }
                }
                else if (tokens[0].equals("solution")) {
                    showSolution();
                }
                else if (tokens[0].equals("exit")) {
                    shouldcontinue = false;
                }
                else{
                    System.out.println("Sorry syntax is wrong");
                }
            }
            //catching exception just in case, don't think anything throws it at this point
            catch(Exception e){
                System.out.println(" ");
            }
            System.out.println(crypt_object.showCurrentState(cryptogram, user_solution).toUpperCase());
            System.out.println("_________________________________________");
        }
    }



    //removing user input from the map
    private void undoLetter(Character c){
        //if user solution doesn't contain the key to be removed, nothing happens
        if(!user_solution.containsKey(tokens[1].charAt(0))){
            System.out.println("You do not have character '" + c + "' set up");
        }
        else{
        user_solution.remove(c);
        }
    }

    //method checks if current solution is correct
    private boolean checkSolution() throws FileNotFoundException {
        solution_map =  crypt_object.getMap();
        solution_map.remove( ' ');
        Set<Character> solutionkeys = crypt_object.getCryptogramCharacters();
        //need to remove space from keys because user doesn't enter it but it is contained in the map
        solutionkeys.remove(' ');
        Set<Character> input_keys = user_solution.keySet();
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
            if(!solution_map.get(b).equals(user_solution.get(b))){
                correct = false;
            }
        }
        return correct;
    }

    private String generateCryptogram() throws FileNotFoundException {
        cryptogram = crypt_object.EncryptedCryptogram();
        return cryptogram;
    }



    private void showSolution() throws FileNotFoundException {
        System.out.println(crypt_object.returnPhrase());
    }

    public char getHint(){
        return 'a';}
    public void loadPlayer(){}

    public void playGame(){}

    public void viewFrequencies(){}

    public void saveGame(){}

    public void loadGame(){}


}
