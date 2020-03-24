package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class GetInputPopUpController {

    public javafx.scene.control.TextField enterName;
    private String name;

    @FXML
    public void saveGame(ActionEvent actionEvent) {
        name = enterName.getText();
        System.out.println(name);
    }
    
}
