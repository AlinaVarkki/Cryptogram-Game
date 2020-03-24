package game;

import cryptogram.Cryptogram;
import cryptogram.LetterCryptogram;
import cryptogram.NumberCryptogram;
import player.Player;

import java.io.FileNotFoundException;
import java.util.*;

public class Game <T>{

    Player currentPlayer;
    private Cryptogram crypt_object;
    //map to store the encryption used
    private Map solution_map;
    //initializing tokens of user input
    private String[] tokens = {""};
    //current cryptogram
   // private String cryptogram;
    private Scanner scanner = new Scanner(System.in);
    boolean complete = false;

    public Game(Player p, String cryptType){}

    public Game(Player p){}

    public Game() throws FileNotFoundException {
        chooseCryptogram();
        printRules();
        System.out.println(crypt_object.encryptedCryptogram().toUpperCase());
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
    private void takeInput()  {
        boolean shouldcontinue = true;

        while(shouldcontinue) {
            String input = scanner.nextLine();

            tokens = input.split(" ");

            try {
                if (tokens[0].equals("set") && tokens[2].length() == 1 && tokens.length == 3) {
                        crypt_object.enterLetter(tokens[1], tokens[2].charAt(0));
//                    System.out.println(crypt_object.getNumCorrectGuesses());
//                    System.out.println(crypt_object.getNumGuesses());
                    complete = crypt_object.checkSolution();
                    if(complete){
                        System.out.println("Good job, your solution is correct");
                        System.out.println(crypt_object.showCurrentState());
                        System.exit(1);
                    }


                }
                else if (tokens[0].equals("remove") &&  tokens.length == 2) {
                    crypt_object.undoLetter(tokens[1].charAt(0));
                }
                else if (tokens[0].equals("check")) {
                    boolean correct = crypt_object.checkSolution();
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
            String currentSolution = crypt_object.showCurrentState().toUpperCase();
            System.out.println(currentSolution);
            System.out.println("_________________________________________");
            if(!currentSolution.contains("_")){
                System.out.println("your solution is not right, try again");
            }

        }
    }


    //    private String generateCryptogram() throws FileNotFoundException {
//        cryptogram = crypt_object.EncryptedCryptogram();
//        return cryptogram;
//    }


    private void showSolution()  {
        System.out.println(crypt_object.returnPhrase());
    }

//    public void setCryptKeys(){
//        Set <Character> cryptogramKeys = new HashSet<>();
//        for(int i = 0; i<cryptogram.length(); i++){
//            cryptogramKeys
//        }
//    }




    public char getHint(){
        return 'a';}
    public void loadPlayer(){}

    public void playGame(){}

    public void viewFrequencies(){}

    public void saveGame(){}

    public void loadGame(){}


}
