package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import player.Player;

import java.io.*;
import java.text.DecimalFormat;
import java.util.HashMap;

public class StatsController {

    private static final String playerDirectory = "resources\\players\\";
    HashMap<String,Integer> hiScore = new HashMap<>();


    @FXML
    private GridPane playersTop;
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
        addNumbers();
        fillTop();
        fillTopGrid();
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

    //reading all players and adding their scores to the map
    public void fillTop(){
        File[] files = new File(playerDirectory).listFiles();
        for (File file : files) {
            try {
                FileInputStream saveFile = new FileInputStream(file);
                ObjectInputStream restore = new ObjectInputStream(saveFile);
                Player user = (Player) restore.readObject();
                hiScore.put(user.username, user.cryptogramsCompleted);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public void fillTopGrid(){
        
    }

    public void addNumbers(){
        for(int i = 0; i < playersTop.getRowCount(); i++){
            Text e = new Text();
            e.setText(String.valueOf(i+1));
            playersTop.add(e, 0, i);
        }
    }
}
