package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {


    @FXML
    private Button LetterCryptButton;

    @FXML
    private Button numberCryptButton;

    @FXML
    public void newLetterCrptButtonClicked(ActionEvent actionEvent){

        Scene scene = LetterCryptButton.getScene();
        ScreenController contoller = new ScreenController(scene);

        try {
            contoller.addScreen("letterCryptogram", FXMLLoader.load(getClass().getResource("/view/gameView.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        contoller.activate("letterCryptogram");
    }

    @FXML
    public void numberCryptogramButtonClicked(ActionEvent actionEvent) {
        Scene scene = numberCryptButton.getScene();
        ScreenController contoller = new ScreenController(scene);

        try {
            contoller.addScreen("numberCryptogram", FXMLLoader.load(getClass().getResource("/view/gameView.fxml")));



        } catch (IOException e) {
            e.printStackTrace();
        }
        contoller.activate("numberCryptogram");
    }
}



