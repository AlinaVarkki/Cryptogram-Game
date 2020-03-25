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

    @FXML
    public void saveGame(ActionEvent actionEvent) {
        change = true;
        Stage stage = (Stage) enterNameLabel.getScene().getWindow();
        stage.close();
    }

    public Boolean getAnswer(){
        return change;
    }

    @FXML
    public void dontSave(ActionEvent actionEvent) {
        Stage stage = (Stage) enterNameLabel.getScene().getWindow();
        change = false;
        stage.close();
    }


}
