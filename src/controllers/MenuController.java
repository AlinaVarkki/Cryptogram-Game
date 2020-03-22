package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Builder;
import javafx.util.BuilderFactory;

import java.io.IOException;
import java.util.ResourceBundle;

public class MenuController {


    @FXML
    private Button LetterCryptButton;

    @FXML
    private Button numberCryptButton;

    @FXML
    public void newLetterCrptButtonClicked(ActionEvent actionEvent){

        Scene scene = LetterCryptButton.getScene();
//        ScreenController contoller = new ScreenController(scene);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gameView.fxml"));
            loader.setController(new GameViewController(false));
//            contoller.addScreen("numberCryptogram", loader.load());
            scene.setRoot(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
   //     contoller.activate("letterCryptogram");
    }

    @FXML
    public void numberCryptogramButtonClicked(ActionEvent actionEvent) {
        Scene scene = numberCryptButton.getScene();
//        ScreenController contoller = new ScreenController(scene);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gameView.fxml"));
            loader.setController(new GameViewController(true));
//            contoller.addScreen("numberCryptogram", loader.load());
            scene.setRoot(loader.load());



        } catch (IOException e) {
            e.printStackTrace();
        }
    //    contoller.activate("numberCryptogram");
    }
}



