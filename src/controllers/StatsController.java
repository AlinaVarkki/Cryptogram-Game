package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;
import java.text.DecimalFormat;

public class StatsController {

    @FXML
    private Label totalGuesses;
    @FXML
    private Label correctGuesses;
    @FXML
    private Label correctPercentage;
    @FXML
    private Label attemptedCrypt;
    @FXML
    private Label solvedCrypt;

    @FXML
    private void initialize() {
        totalGuesses.setText("      Total guesses: " + LogInController.currentPlayer.totalGuesses);
        correctGuesses.setText("      Correct guesses: " + LogInController.currentPlayer.correctGuesses);
        DecimalFormat df = new DecimalFormat("#.##");
        correctPercentage.setText("      Accuracy: " +Double.parseDouble(df.format( LogInController.currentPlayer.returnAccuracy())) +" %");
        attemptedCrypt.setText("      Cryptograms played: " + LogInController.currentPlayer.cryptogramsPlayed);
        solvedCrypt.setText("      Cryptograms completed: " + LogInController.currentPlayer.cryptogramsCompleted);

    }

    public void goToMenu(ActionEvent actionEvent) {
        Scene scene = totalGuesses.getScene();
        ScreenController cont = new ScreenController(scene);
        try {
            cont.addScreen("menu", FXMLLoader.load(getClass().getResource("/view/menuView.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        cont.activate("menu");
    }
}
