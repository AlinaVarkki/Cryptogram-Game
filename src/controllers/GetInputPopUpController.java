//pop up to ask if user wants to override a cryptogram

package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class GetInputPopUpController {

    public javafx.scene.control.TextField enterName;
    private String name;
    private Boolean change;


    @FXML
    private Label enterNameLabel;

    //if user chooses to override saved cryptogram, boolean is changed to true
    @FXML
    public void saveGame(ActionEvent actionEvent) {
        change = true;
        Stage stage = (Stage) enterNameLabel.getScene().getWindow();
        stage.close();
    }

    //if user chooses to not override saved cryptogram, boolean is false
    @FXML
    public void dontSave(ActionEvent actionEvent) {
        Stage stage = (Stage) enterNameLabel.getScene().getWindow();
        change = false;
        stage.close();
    }

    public Boolean getAnswer(){
        return change;
    }

}
