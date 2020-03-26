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
import javafx.util.Builder;
import javafx.util.BuilderFactory;
import player.Player;

import java.io.*;
import java.util.ResourceBundle;

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

//    public MenuController(Player player){
//        this.player = player;
//    }


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

    @FXML
    public void continueGameButton(ActionEvent actionEvent) {
        Scene scene1 = numberCryptButton.getScene();

        try {
            FileInputStream saveFile = new FileInputStream("C:\\Users\\USER\\Desktop\\cs207\\resources\\savedGames\\savedGame.sav");
            ObjectInputStream restore = new ObjectInputStream(saveFile);
            Cryptogram cryptogram = (Cryptogram) restore.readObject();
            restore.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gameView.fxml"));
            loader.setController(new GameViewController(cryptogram));
//            contoller.addScreen("numberCryptogram", loader.load());
            scene1.setRoot(loader.load());

        } catch (FileNotFoundException | ClassNotFoundException e){

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/finishPopUpView.fxml"));

            try {
                Parent parent = loader.load();
                DonePopUpController pop = loader.<DonePopUpController>getController();
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
                DonePopUpController pop = loader.<DonePopUpController>getController();
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
}



