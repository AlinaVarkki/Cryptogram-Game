package controllers;

import cryptogram.Cryptogram;
import cryptogram.LetterCryptogram;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;

public class GameViewController {


    private String phrase;
    private TextField[] fields = new TextField[100];
    private int maxChars = 1;
    private String restictTo = "[a-z\\s]*";


    @FXML
    private GridPane enterLetters;


    //dynamically creating amount of textboxes
    @FXML
    private void initialize() throws FileNotFoundException {
        getCryptogram();
        createTextBoxes();
        addLetters();
        //setLimitToLetters();
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
            e.setText(String.valueOf(phrase.charAt(i)));
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

    public void getCryptogram() throws FileNotFoundException {
        Cryptogram cryptogram = new LetterCryptogram<>();
        phrase = cryptogram.returnPhrase();
    }



//    public void setLimitToLetters(){
//        for(TextField tx: fields){
//
//        }
//    }
//
//    public static void addTextLimiter(final TextField tf, final int maxLength) {
//        tf.textProperty().addListener((ov, oldValue, newValue) -> {
//            if (tf.getText().length() > maxLength) {
//                String s = tf.getText().substring(0, maxLength);
//                tf.setText(s);
//            }
//        });
//    }

}
