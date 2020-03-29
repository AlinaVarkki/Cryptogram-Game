package controllers;

import cryptogram.CryptLoader;
import cryptogram.Cryptogram;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import player.Player;

import java.io.*;

public class MenuController {


    //current player
    private Player player = LogInController.currentPlayer;
    private static final String playerDirectory = "resources\\players\\";

    @FXML
    private Button LetterCryptButton;

    @FXML
    private Button numberCryptButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button statsButton;

    //difference between choosing number vs letter cryptogram is that they pass different boolean values into constructor
    @FXML
    public void newLetterCrptButtonClicked(ActionEvent actionEvent){
        CryptLoader newLoader = new CryptLoader();
        newLoader.generatePhrase();
        Scene scene = LetterCryptButton.getScene();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gameView.fxml"));
            loader.setController(new GameViewController(false));
            scene.setRoot(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void numberCryptogramButtonClicked(ActionEvent actionEvent) {
        CryptLoader newLoader = new CryptLoader();
        newLoader.generatePhrase();

        Scene scene = numberCryptButton.getScene();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gameView.fxml"));
            loader.setController(new GameViewController(true));
            scene.setRoot(loader.load());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //restoring Cryptogram object from the file if user wants to continue unfinished game
    @FXML
    public void continueGameButton(ActionEvent actionEvent) {
        Scene scene1 = numberCryptButton.getScene();

        try {
            FileInputStream saveFile = new FileInputStream("C:\\Users\\USER\\Desktop\\cs207\\resources\\savedGames\\"+ player.username +"savedGame.sav");
            ObjectInputStream restore = new ObjectInputStream(saveFile);
            Cryptogram cryptogram = (Cryptogram) restore.readObject();
            restore.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gameView.fxml"));
            loader.setController(new GameViewController(cryptogram));
            scene1.setRoot(loader.load());

        } catch (FileNotFoundException | ClassNotFoundException e){

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/finishPopUpView.fxml"));

            try {
                Parent parent = loader.load();
                PopUpController pop = loader.getController();
                pop.setText("You don't have any cryptograms stored");
                Scene scene = new Scene(parent, 450, 270);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();
            } catch (IOException a) {
                e.printStackTrace();
            }
        } catch (IOException e) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/finishPopUpView.fxml"));

            try {
                Parent parent = loader.load();
                PopUpController pop = loader.getController();
                pop.setText("Something is wrong with the file");
                Scene scene = new Scene(parent, 450, 270);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();
            } catch (IOException a) {
                e.printStackTrace();
            }

        }


    }

    //when user logs out, user object file is overridden
    public void logOutButtonClicked(ActionEvent actionEvent) {
        try {
        String username = player.getUsername();
        String tempname = playerDirectory + username + ".sav";
        FileOutputStream saveFile = new FileOutputStream(tempname);
        ObjectOutputStream save = new ObjectOutputStream(saveFile);
        save.writeObject(player);
        save.close();
        System.out.println("Successfully saved player data");

        //go back to login screen
        Scene scene = logOutButton.getScene();
        ScreenController screenController = new ScreenController(scene);
        screenController.addScreen("login", FXMLLoader.load(getClass().getResource("/view/loginView.fxml")));
        screenController.activate("login");
        }
        catch(IOException f) {
            System.out.println("Player loading error");
        }


    }

    @FXML
    public void goToStats(ActionEvent actionEvent) {
        Scene scene = statsButton.getScene();
        ScreenController cont = new ScreenController(scene);
        try {
            cont.addScreen("stats", FXMLLoader.load(getClass().getResource("/view/statsView.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        cont.activate("stats");
    }
}



