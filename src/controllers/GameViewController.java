package controllers;

import cryptogram.Cryptogram;
import cryptogram.LetterCryptogram;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class GameViewController {

    private Cryptogram cryptogram = new LetterCryptogram<>();
    private String phrase;
    private String encrypted_phrase;
    private TextField[] fields = new TextField[100];
    private int maxChars = 1;
    private String restictTo = "[a-z\\s]*";




    @FXML
    private GridPane enterLetters;

    public GameViewController() {
        encrypted_phrase = cryptogram.encryptedCryptogram();

        phrase = cryptogram.returnPhrase();
        System.out.println(encrypted_phrase);
        System.out.println(phrase);
    }


    //dynamically creating amount of textboxes
    @FXML
    private void initialize() throws FileNotFoundException {
        createTextBoxes();
        addLetters();
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

            Character letter = encrypted_phrase.charAt(i);
            String s = letter.toString();
            int finalI = i;

            fields[i].textProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("textfield changed from " + oldValue + " to " + newValue);
               if(!newValue.equals("")){
                   cryptogram.enterLetter(s, newValue.charAt(0));
                   fillBoxes();
                   System.out.println("here");
                    if(cryptogram.checkSolution()){
                      showGoodJobPopUp();
                    }
                }
                if(!oldValue.equals("")) {
                 cryptogram.undoLetter(oldValue.charAt(0));
                 fillBoxes();
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
        for (int i = 0; i < encrypted_phrase.length(); i++) {
            //if the character in the cryptogram is the value of a key in the map solution, it prints it out at the correct place
            if (cryptogram.getUser_solution().containsValue(Character.toString(encrypted_phrase.charAt(i)))) {
                for (Map.Entry<Character, String> entry : cryptogram.getUser_solution().entrySet()) {
                    if (entry.getValue().equals(Character.toString(encrypted_phrase.charAt(i)))) {
                        fields[i].setText(String.valueOf(entry.getKey()));
                    }

                }
            }
            if(!cryptogram.getUser_solution().containsValue(Character.toString(encrypted_phrase.charAt(i)))){
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
                for (int i = 0; i < encrypted_phrase.length(); i++) {
                    Text e = new Text();

                    e.setText(String.valueOf(encrypted_phrase.charAt(i)).toUpperCase());
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/finishPopUpView.fxml"));

                try {
                    Parent parent = loader.load();
                    DonePopUpController pop = loader.<DonePopUpController>getController();
                    Scene scene = new Scene(parent, 300, 200);
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(scene);
                    stage.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

}

