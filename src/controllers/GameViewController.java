package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class GameViewController {


    String crypt = "DECRYPT THIS SENTENCE PLEASE LETS SEE HOW IT ALL WORKS";


    @FXML
    private GridPane enterLetters;


    //dynamically creating amount of textboxes
    @FXML
    private void initialize() {
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
        for (int i = 0; i < crypt.length(); i++) {
            TextField e = new TextField();
            if (crypt.charAt(i) != ' ') {
                enterLetters.add(e, column, row);
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
        for (int i = 0; i < crypt.length(); i++) {
            Text e = new Text();
            e.setText(String.valueOf(crypt.charAt(i)));
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

    public void getCryptogram(){
        
    }


}
