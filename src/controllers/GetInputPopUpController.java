package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class GetInputPopUpController {

    public javafx.scene.control.TextField enterName;
    private String name;

    @FXML
    private Label enterNameLabel;

    @FXML
    public void saveGame(ActionEvent actionEvent) {
        name = enterName.getText();
        Stage stage = (Stage) enterNameLabel.getScene().getWindow();
        stage.close();
    }

    public String getName(){
        return name;
    }


}
