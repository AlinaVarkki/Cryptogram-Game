import java.util.*;

public class Game {


    Player currentPlayer;
    private LetterCryptogram cryptogram_object = new LetterCryptogram();
    //map to store the encryption used
    private HashMap<Character, Character> soution_map;
    //map that stores the mapping that user enters
    private HashMap<Character, Character> user_solution = new HashMap<>();
    //initializing tokens of user input
    private String[] tokens = {""};
    //current cryptogram
    private String cryptogram;

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
        System.out.println("To see the solution, type 'solution'");
        System.out.println("To exit enter 'exit'");
        System.out.println();
        System.out.println();
    }

    //takes user input and calls methods accordingly
   //loops until user enters exit
    private void takeInput(){
        boolean shouldcontinue = true;

        while(shouldcontinue) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            tokens = input.split(" ");

            try {
                if (tokens[0].equals("set") && tokens[1].length() == 1 && tokens[2].length() == 1 && tokens.length == 3) {
                    enterLetter(tokens[1].charAt(0), tokens[2].charAt(0));
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
            System.out.println(showCurrentState().toUpperCase());
            System.out.println("_________________________________________");
        }
    }

    //adding user input to the solution map
    private void enterLetter(Character a, Character b){
        //not letting user add letter to the solution if cryptogram doesn't contain this letter
        if(!cryptogram.contains(a.toString())){
            System.out.println("Cryptogram does not contain this letter");
        }
        //if user has already set this letter, loop through values and remove it and it's key. Then put new value to the map
        else{
            if(user_solution.containsValue(a)){
            for (Map.Entry<Character, Character> entry : user_solution.entrySet()) {
                if (entry.getValue().equals(a)){
                    user_solution.remove(entry.getKey());
                }
            }
            user_solution.remove(b);
            }

            user_solution.put(b, a);
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
    private boolean checkSolution(){
        soution_map =  cryptogram_object.getMap();
        soution_map.remove( ' ');
        Set<Character> solutionkeys = cryptogram_object.getCryptogramCharacters();
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
            if(!soution_map.get(b).equals(user_solution.get(b))){
                correct = false;
            }
        }
        return correct;
    }

    private String generateCryptogram(){
        cryptogram = cryptogram_object.EncryptedCryptogram();
        return cryptogram;
    }

//method to display current user solution
    private String showCurrentState(){
        StringBuilder currentState = new StringBuilder();
        for(int i = 0; i < cryptogram.length(); i++){
            //if the character in the cryptogram is the value of a key in the map solution, it prints it out at the correct place

            if(user_solution.containsValue(cryptogram.charAt(i))){
                for (Map.Entry<Character, Character> entry : user_solution.entrySet()) {
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

    public void showSolution(){
        System.out.println(cryptogram_object.returnPhrase());
    }

    public char getHint(){
        return 'a';}
    public void loadPlayer(){}

    public void playGame(){}

    public void viewFrequencies(){}

    public void saveGame(){}

    public void loadGame(){}


}
