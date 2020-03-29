//view for the game itself, window where user solves the cryptogram

package controllers;

import cryptogram.Cryptogram;
import cryptogram.LetterCryptogram;
import cryptogram.NumberCryptogram;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import player.Player;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Map;

public class GameViewController implements Serializable {

    private Cryptogram cryptogram;

    private String phrase;
    private String encrypted_phrase;
    private TextField[] fields = new TextField[100];
    private int maxChars = 1;
    private String restictTo = "[a-z\\s]*";
    private GetInputPopUpController input = new GetInputPopUpController();
    private boolean done = false;
    private boolean change = true;

    //getting current player
    private Player player = LogInController.currentPlayer;


    @FXML
    private GridPane enterLetters;

    @FXML
    private Label deleteFirst;
    @FXML
    private Button saveButton;
    @FXML
    private Button menuButton;
    @FXML
    private Button showSolutionButton;
    @FXML
    private Button frequenciesButton;

    //constructor for restored cryptograms, passing in restored cryptogram
    public GameViewController(Cryptogram cryptogram){
        this.cryptogram = cryptogram;
        this.phrase = cryptogram.returnPhrase();
        this.encrypted_phrase = cryptogram.encryptedCryptogram();
    }

    //constructor for newly started games
    public GameViewController(Boolean isNumeric) {
        //user starts new game, amount of games updated
        player.incrementCryptogramsPlayed();

        //if passed boolean is true, numeric cryptogram starts, if it's false, letter one
        cryptogram = isNumeric ? new NumberCryptogram() : new LetterCryptogram();
        try {
            phrase = cryptogram.returnPhrase();
        }
        catch(Exception e){
            System.out.println("oops");
            System.exit(0);
        }
        encrypted_phrase = cryptogram.encryptedCryptogram();

        System.out.println(encrypted_phrase);
        System.out.println(phrase);
    }


    //dynamically creating amount of textboxes
    @FXML
    private void initialize() {
        //player.incrementCryptogramsPlayed();
        System.out.println(player.getNumCryptogramsPlayed());
        createTextBoxes();
        addLetters();
        saveButton.setOnAction(actionEvent -> saveGame());

        menuButton.setOnAction(actionEvent -> goToMenu());

        showSolutionButton.setOnAction(actionEvent -> showSolution());

        frequenciesButton.setOnAction(actionEvent -> showFrequncies());


        //fill boxes with current user solution (for restored cryptograms, in other case it's empty)
        fillBoxes();
    }


    //method called when user wants to save the game
    public void saveGame(){
        File tmpDir = new File("resources\\savedGames\\" + player.username + "savedGame.sav");
        boolean exists = tmpDir.exists();
        if(exists){
            //if there is a cryptogram saved, ask user if they want to override it
            overridePopUp();
        }
        else{
            //need empty catch block for testing, only in tests it is equal to null because Label is from fxml file and it is not seen in tests
            try {
                deleteFirst.setText("Cryptogram is saved");
            }
            catch (Exception e){}
        }

        //otherwise just save
        if(change = true) {
            try {
                String tempname = "resources\\savedGames\\"+ player.username +"savedGame.sav";
                FileOutputStream saveFile = new FileOutputStream(tempname);
                ObjectOutputStream save = new ObjectOutputStream(saveFile);
                save.writeObject(cryptogram);
                save.close();
                saveFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    //pop up to get save name from user
    public void overridePopUp(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/getInputPopUp.fxml"));

            try {
                Parent parent = loader.load();
                input = loader.getController();
                Scene scene = new Scene(parent, 440, 241);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();
                change = input.getAnswer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch(Exception e){

        }

    }



    //dynamically create textboxes for every letter
    public void createTextBoxes(){


        int row = 1;
        //variable that gets incremented with every pass to change the row after it's at max(6 in a row)
        int c = 0;
        int column = 0;
        //max of letters in a row
        int trashHold = enterLetters.getColumnCount();
        //adding textboxes to cells
        for (int i = 0; i < phrase.length(); i++) {
            //create new textbox and override methods to only accept1 letter (and only letters)
            fields[i] = new TextField(){
                @Override
                public void replaceText(int start, int end, String text) {
                    if (matchTest(text)) {
                        super.replaceText(start, end, text);
                    }
                }
                @Override
                public void replaceSelection(String text) {
                    if (matchTest(text)) {
                        super.replaceSelection(text);
                    }
                }
                private boolean matchTest(String text) {
                    return text.isEmpty() || (text.matches(restictTo) && getText().length() < maxChars);
                }

            };

            String s = (String) cryptogram.getMap().get(phrase.charAt(i));

            //listener for cells
            fields[i].textProperty().addListener((observable, oldValue, newValue) -> {

                System.out.println("textfield changed from " + oldValue + " to " + newValue);

                if(!newValue.equals("")) {
                    deleteFirst.setText("");

                    if(newValue.equals(" ")) {
                        ((StringProperty)observable).setValue("");
                    } else {

                        if (!cryptogram.getUser_solution().containsKey(newValue.charAt(0)) || cryptogram.getUser_solution().get(newValue.charAt(0)).equals(s)) {

                            cryptogram.enterLetter(s, newValue.charAt(0));
                            fillBoxes();


                            if (cryptogram.checkSolution() && !done) {
                                System.out.println("textfield changed from " + oldValue + " to " + newValue);
                                done = true;
                                showGoodJobPopUp();
                                //after game is correctly completed, go back to the menu

                                goToMenu();
                            }
                            if(cryptogram.allPlacesFilled()){
                                deleteFirst.setText("Your solution is not correct, try again");
                            }


                            if (!oldValue.equals("")) {
                                cryptogram.undoLetter(oldValue.charAt(0));
                                System.out.println("hehe");
                                fillBoxes();
                            }
                        } else {
                            ((StringProperty) observable).setValue(" ");
                            deleteFirst.setText("You should remove letter before setting it again");
                        }
                    }
                }
                else{
                    deleteFirst.setText("");

                    if (!oldValue.equals(" ")) {
                        cryptogram.undoLetter(oldValue.charAt(0));
                        fillBoxes();
                    }
                }

            });


            if (phrase.charAt(i) != ' ') {
                enterLetters.add(fields[i], column, row);
            }
            column = column + 1;
            c = c + 1;
            if (c >= trashHold) {
                row = row + 2;
                trashHold = trashHold + enterLetters.getColumnCount();
            }
            if (column > 9) {
                column = 0;
            }
        }
    }

    //fill all needed boxes with the letter when entered
    public void fillBoxes() {
        for (int i = 0; i < phrase.length(); i++) {
            //if the character or number in the cryptogram is the value of a key in the map solution, it prints it out at the correct place
            if (cryptogram.getUser_solution().containsValue(cryptogram.getMap().get(phrase.charAt(i)))) {
                for (Map.Entry<Character, String> entry : cryptogram.getUser_solution().entrySet()) {
                    if (entry.getValue().equals(cryptogram.getMap().get(phrase.charAt(i)))) {
                        fields[i].setText(String.valueOf(entry.getKey()));
                    }

                }
            }
            if(!cryptogram.getUser_solution().containsValue(cryptogram.getMap().get(phrase.charAt(i)))){
                fields[i].clear();
            }

        }
    }

            //dynamically allocate letters to the right cells
            public void addLetters(){
                int rowl = 0;
                //variable that gets incremented with every pass to change the row after it's at max(6 in a row)
                int cl = 0;
                int columnl = 0;
                //max of letters in a row
                int trashHoldl = enterLetters.getColumnCount();
                //adding textboxes to cells
                for (int i = 0; i < phrase.length(); i++) {
                    Text e = new Text();

                    if (phrase.charAt(i) == ' ') {
                        e.setText(" ");
                    }
                    else {
                        e.setText(String.valueOf(cryptogram.getMap().get(phrase.charAt(i))));
                    }
                    enterLetters.add(e, columnl, rowl);

                    columnl = columnl + 1;
                    cl = cl + 1;
                    if (cl >= trashHoldl) {
                        rowl = rowl + 2;
                        trashHoldl = trashHoldl + enterLetters.getColumnCount();
                    }
                    if (columnl > 9) {
                        columnl = 0;
                    }
                }
            }

            public void showGoodJobPopUp(){
            //if this appears, user completed cryptogram successfully
            player.incrementCryptogramsCompleted();
            try {
                System.out.println(player.getNumCryptogramsCompleted());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/finishPopUpView.fxml"));

                try {
                    Parent parent = loader.load();
                    PopUpController pop = loader.getController();
                    pop.setText("Good job, your solution is correct");
                    Scene scene = new Scene(parent, 450, 270);
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(scene);
                    stage.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e){}
            }



    public void goToMenu() {

        Scene scene = enterLetters.getScene();
        ScreenController cont = new ScreenController(scene);
        try {
            cont.addScreen("menu", FXMLLoader.load(getClass().getResource("/view/menuView.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        cont.activate("menu");
    }


    //when user wants to see solution
    //method creates new textfields on the same places (because if we fill old ones, listener will call certain methods and all statistics will get updated)
    //after filling textboxes, they get disabled and user cannot do anything with textboxes
    //save button is not longer working, you cannot save game that is solved
    public void showSolution() {

        int row = 1;
        //variable that gets incremented with every pass to change the row after it's at max(6 in a row)
        int c = 0;
        int column = 0;
        //max of letters in a row
        int trashHold = enterLetters.getColumnCount();
        //adding textboxes to cells
        for (int i = 0; i < phrase.length(); i++) {
            //create new textbox and override methods to only accept1 letter (and only letters)
            fields[i] = new TextField();

            String s = (String) cryptogram.getMap().get(phrase.charAt(i));

            if (phrase.charAt(i) != ' ') {
                enterLetters.add(fields[i], column, row);
            }

            column = column + 1;
            c = c + 1;
            if (c >= trashHold) {
                row = row + 2;
                trashHold = trashHold + enterLetters.getColumnCount();
            }
            if (column > 9) {
                column = 0;
            }
        }

        for(int i = 0; i < phrase.length(); i++){
            fields[i].clear();
            fields[i].setText(String.valueOf(phrase.charAt(i)));
        }


        for(int i = 0; i < phrase.length(); i++){
            fields[i].setEditable(false);
        }

        saveButton.setOnAction(actionEvent ->cantSavePopUp());

    }

    private void cantSavePopUp() {
        try {
            System.out.println(player.getNumCryptogramsCompleted());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/finishPopUpView.fxml"));

            try {
                Parent parent = loader.load();
                PopUpController pop = loader.getController();
                pop.setText("You can't save solved cryptogram");
                Scene scene = new Scene(parent, 450, 270);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e){}
    }

    private void showFrequncies() {
        try {
            System.out.println(player.getNumCryptogramsCompleted());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/frequenciesView.fxml"));

            try {
                Parent parent = loader.load();
                FrequenciesContoller controller = loader.getController();
                Scene scene = new Scene(parent, 565, 589);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e){}
    }

}

