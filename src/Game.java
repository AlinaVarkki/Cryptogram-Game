import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class Game {


    Player currentPlayer;
    private HashMap<Character, Character> solution = new HashMap<>();

    public Game(Player p, String cryptType){

    }

    public Game(Player p){

    }



    public static void main(String[] argc){
        HashMap<Character, Character> solution = new HashMap<>();
        LetterCryptogram newCryptogram = new LetterCryptogram();
        System.out.println("To set encrypted letter to the solution enter 'set [encrypted letter] [real letter]'");
        System.out.println("To remove set letter enter 'remove [encrypted letter]'");
        System.out.println("To check your solution, enter 'check'");
        System.out.println("To exit enter 'exit'");
        String cr = newCryptogram.alphEncryptedCryptogram();;
        System.out.println(cr);

        //take in input and put it in the map to compare if the input is correct

        String[] tokens = {""};
        boolean shouldcontinue = true;
        while(shouldcontinue == true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            tokens = input.split(" ");


            if(tokens[0].equals("set") && tokens[1] != null && tokens[1].length() == 1 && tokens[2] != null && tokens[2].length() == 1 && tokens.length < 4){
                solution.put(tokens[1].charAt(0), tokens[2].charAt(0));
            }
            else if(tokens[0].equals("remove") && solution.containsKey(tokens[1].charAt(0)) && tokens.length < 3){
                solution.remove(tokens[1].charAt(0));
            }
            else if(tokens[0].equals("exit")){
                shouldcontinue = false;
            }
            else if(tokens[0].equals("check")){
                //LetterCryptogram a = new LetterCryptogram();
                HashMap<Character, Character> answer =  newCryptogram.getMap();
                Set<Character> keys = solution.keySet();
                boolean correct = true;
                    for(Character b: keys){
                        if(answer.get(b).equals(solution.get(b)) == false){
                            correct = false;
                        }
                    }
                if(correct == false){
                    System.out.println("your solution is wrong");
                }
                else{
                    System.out.println("good job");
                }

            }
            else{
                System.out.println("Sorry syntax is wrong");
            }

            System.out.println("________________");
        }
        System.exit(1);

    }

    public char getHint(){
        return 'a';
    }

    public void loadPlayer(){

    }

    public void playGame(){

    }

//    private static String generateCryptogram(){
//        LetterCryptogram newCryptogram = new LetterCryptogram();
//        return newCryptogram.alphEncryptedCryptogram();
//    }

    public void enterLetter(char a){

    }

    public void undoLetter(){}

    public void viewFrequencies(){}

    public void saveGame(){}

    public void loadGame(){}

    public void showSolution(){}


}
