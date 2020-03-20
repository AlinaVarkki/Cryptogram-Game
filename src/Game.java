import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Game <T>{

    Player currentPlayer;
    private Cryptogram crypt_object;
    //map to store the encryption used
    private Map solution_map;
    //map that stores the mapping that user enters
    private HashMap<Character, String> user_solution = new HashMap<>();
    //initializing tokens of user input
    private String[] tokens = {""};
    //current cryptogram
    private String cryptogram;
    private Scanner scanner = new Scanner(System.in);
    boolean complete = false;
    private String directory = "resources\\saves\\";
    private String name;

    public Game(Player p, String cryptType){}

    public Game(Player p){}

    public Game() throws FileNotFoundException {
        chooseCryptogram();
        chooseName();
        printRules();
        System.out.println(generateCryptogram().toUpperCase());
        takeInput();
    }

    private void chooseCryptogram(){
        System.out.println("To play letter cryptogram, type 'l', to play number cryptogram, type'n' or to load type 'load [savename]' ");

        boolean shouldContinueAsking = true;
        while(shouldContinueAsking) {
            String cryptChoice = scanner.nextLine();
            tokens = cryptChoice.split(" ");
                if (tokens[0].equals("l")) {
                    crypt_object = new LetterCryptogram();
                    shouldContinueAsking = false;
                } else if (tokens[0].equals("n")) {
                    crypt_object = new NumberCryptogram();
                    shouldContinueAsking = false;
                } else if (tokens[0].equals("load")) {
                    loadGame(tokens[1]);
                    printRules();
                    System.out.println(cryptogram.toUpperCase());
                    takeInput();
                    shouldContinueAsking = false;
                }
                else System.out.println("Please enter correct syntax");
        }
    }

    private void chooseName() {
        System.out.println("Please enter a save name: ");
        name = scanner.nextLine();
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
//                    System.out.println(crypt_object.getNumCorrectGuesses());
//                    System.out.println(crypt_object.getNumGuesses());
                    complete = checkSolution();
                    if(complete){
                        System.out.println("Good job, your solution is correct");
                        System.out.println(crypt_object.showCurrentState(cryptogram, user_solution).toUpperCase());
                        saveGame();
                        System.exit(1);
                    }


                }
                else if (tokens[0].equals("remove") &&  tokens.length == 2) {
                    crypt_object.undoLetter(tokens[1].charAt(0), user_solution);
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
                    saveGame();
                    shouldcontinue = false;
                }
                else if (tokens[0].equals("save")) {
                    saveGame();
                }
                else{
                    System.out.println("Sorry syntax is wrong");
                }
            }
            //catching exception just in case, don't think anything throws it at this point
            catch(Exception e){
                System.out.println(" ");
            }
            String currentSolution = crypt_object.showCurrentState(cryptogram, user_solution).toUpperCase();
            System.out.println(currentSolution);
            System.out.println("_________________________________________");
            if(!currentSolution.contains("_")){
                System.out.println("your solution is not right, try again");
            }

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

    public void saveGame(){
        try {
            String tempname = directory + name + ".sav";
            FileOutputStream saveFile = new FileOutputStream(tempname);
            ObjectOutputStream save = new ObjectOutputStream(saveFile);
            save.writeObject(crypt_object);
            save.writeObject(solution_map);
            save.writeObject(user_solution);
            save.writeObject(cryptogram);
            save.close();
            System.out.println("You have successfully saved the game as: " + tempname);
        }
        catch(IOException e) {
            System.out.println(e);
        }
    }

    public void loadGame(String n){
        try {
            name = n;
            String tempname = directory + n + ".sav";
            FileInputStream saveFile = new FileInputStream(tempname);
            ObjectInputStream restore = new ObjectInputStream(saveFile);
            crypt_object = (LetterCryptogram) restore.readObject();
            solution_map = (Map) restore.readObject();
            user_solution = (HashMap<Character, String>) restore.readObject();
            cryptogram = (String) restore.readObject();
            restore.close();
        }
        catch(IOException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }


}
