package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import player.Player;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

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
        playersTop.setStyle("-fx-font-size: 20px;");

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
                //e.printStackTrace();
                noPlayers();
            }

        }
    }

    public void fillTopGrid(){
        Map<String, Integer> sortedMap = sortByValue(hiScore);
        List<String> keys = new ArrayList<>(sortedMap.keySet());
        Collections.reverse(keys);
        if(hiScore.size() > 1) {
            addNumbers();
            int j = 0;
            for (int i = 0; i < hiScore.keySet().size(); i++) {
                j++;
                Text e = new Text();
                e.setFont(Font.font("Verdana", 20));
                e.setText(keys.get(i) + "     Score: " + sortedMap.get(keys.get(i)));
                playersTop.add(e, 1, i);
                if (j > 10) {
                    break;
                }
            }
        }
        else{
            noPlayers();
        }
    }

    //function to sort list map by value
    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
                new LinkedList<>(hm.entrySet());

        // Sort the list
        Collections.sort(list, Comparator.comparing(Map.Entry::getValue));

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public void addNumbers(){
        for(int i = 0; i < playersTop.getRowCount(); i++){
            Text e = new Text();
            e.setText(String.valueOf(i+1));
            playersTop.add(e, 0, i);
        }
    }

    public void noPlayers(){
        Text e = new Text();
        e.setFont(Font.font("Verdana", 20));
        e.setText("There are no players stored");
        playersTop.add(e, 1, 1);
    }
}
