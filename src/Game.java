import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class Game {


    Player currentPlayer;
    //map to store the encryption used
    private HashMap<Character, Character> answer;
    private static LetterCryptogram newCryptogram = new LetterCryptogram();
    //map that stores the mapping that user enteres
    private HashMap<Character, Character> solution = new HashMap<>();
    //initializing tokens of user input
    private String[] tokens = {""};

    public Game(Player p, String cryptType){

    }

    public Game(Player p){

    }

    public Game(){
        printRules();
        System.out.println(generateCryptogram());
        takeInput();
    }

    //prints rules in console
    private void printRules(){
        System.out.println("To set encrypted letter to the solution enter 'set [encrypted letter] [real letter]'");
        System.out.println("To remove set letter enter 'remove [encrypted letter]'");
        System.out.println("To check your solution, enter 'check'");
        System.out.println("To exit enter 'exit'");
    }

    //takes user input and calls methods accordingly
    private void takeInput(){
        boolean shouldcontinue = true;
        while(shouldcontinue) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            tokens = input.split(" ");


            if(tokens[0].equals("set") && tokens[1] != null && tokens[1].length() == 1 && tokens[2] != null && tokens[2].length() == 1 && tokens.length < 4){
                enterLetter(tokens[1].charAt(0), tokens[2].charAt(0));
            }
            else if(tokens[0].equals("remove") && solution.containsKey(tokens[1].charAt(0)) && tokens.length < 3){
                undoLetter(tokens[1].charAt(0));
            }
            else if(tokens[0].equals("exit")){
                shouldcontinue = false;
            }
            else if(tokens[0].equals("check")){

                boolean correct = checkSolution();

                if(!correct){
                    System.out.println("your solution is wrong");
                }
                else{
                    System.out.println("good job");
                }

            }
            else{
                System.out.println("Sorry syntax is wrong");
            }

            System.out.println("_________________________________________");
        }
    }

    //adding user input to the solution map
    private void enterLetter(Character a, Character b){
        solution.put(a, b);
    }

    //removing user input from the map
    private void undoLetter(Character c){
        solution.remove(c);
    }

    //works but doesn't check the solution properly, checks all the letters, not just the ones in the cryptogram
    private boolean checkSolution(){
        answer =  newCryptogram.getMap();
        Set<Character> keys = solution.keySet();
        boolean correct = true;
        for(Character b: keys){
            if(!answer.get(b).equals(solution.get(b))){
                correct = false;
            }
        }
        return correct;
    }

    private static String generateCryptogram(){
        return newCryptogram.alphEncryptedCryptogram();
    }

    public char getHint(){
        return 'a';
    }

    public void loadPlayer(){ }

    public void playGame(){}

    public void viewFrequencies(){}

    public void saveGame(){}

    public void loadGame(){}

    public void showSolution(){}


}
