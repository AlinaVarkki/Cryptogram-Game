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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        //array from keys
        List<String> keys = new ArrayList<>(hiScore.keySet());
//        //order array by score
//        for(int i = 1; i < keys.size(); i++){
//            for(int j = i; j > 0; j--){
//                if(hiScore.get(keys.get(j)) < hiScore.get(keys.get(j-1))){
//                    String temp = keys.get(j);
//                    keys.add(j, keys.get(j-1));
//                    keys.add(j-1, temp);
//                }
//            }
//        }
        int j = 0;
        for (int i = 0; i < hiScore.keySet().size(); i++) {
            j++;
            Text e = new Text();
            e.setText(keys.get(i) + " " + hiScore.get(keys.get(i)));
            playersTop.add(e, 1, i);
            if(j>10){
                break;
            }
        }

    }

    public void addNumbers(){
        for(int i = 0; i < playersTop.getRowCount(); i++){
            Text e = new Text();
            e.setText(String.valueOf(i+1));
            playersTop.add(e, 0, i);
        }
    }
}
