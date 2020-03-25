package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import player.Player;

import java.io.*;


public class LogInController {
    private static final String playerDirectory = "resources\\players\\";
    private Player currentPlayer;



    @FXML
    private TextField usernameLogIn;

    @FXML
    private PasswordField passwordLogIn;

    @FXML
    private TextField usernameSignIn;

    @FXML
    private PasswordField passwordSignIn;

    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Label label;
    @FXML
    private Label label1;



    @FXML
    public void initialize(){
        usernameLogIn.setPromptText("username");
        passwordLogIn.setPromptText("password");
        usernameSignIn.setPromptText("username");
        passwordSignIn.setPromptText("password");

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    String existingUsername = usernameLogIn.getText();
                    String existingPassword = passwordLogIn.getText();
                    String tempname = playerDirectory + existingUsername + ".sav";
                    FileInputStream saveFile = new FileInputStream(tempname);
                    ObjectInputStream restore = new ObjectInputStream(saveFile);
                    currentPlayer = (Player)restore.readObject();

                    //after logging in go to menu
                    goToMenu();
                } catch (IOException | ClassNotFoundException e) {
                    label1.setText("User with this username doesn't exist");
                }




            }
        });
//if user wants to register, new file is created for them
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    String newUsername = usernameSignIn.getText();
                    String newPassword = passwordSignIn.getText();
                    String tempname = playerDirectory + newUsername + ".sav";
                    File file = new File(tempname);
                    //if user name already exists, don't register
                    boolean exists = file.exists();
                    if(!exists) {
                        Player player = new Player(newUsername);
                        currentPlayer = player;
                        System.out.println(newUsername + newPassword);
                        //later add pop up if username exists


                        FileOutputStream saveFile = new FileOutputStream(tempname);
                        ObjectOutputStream save = new ObjectOutputStream(saveFile);
                        System.out.println("gets here");

                        save.writeObject(player);
                        System.out.println("gets here2");

                        save.close();
                        //after user is signed in, move to the menu
                        goToMenu();
                    }
                    else{
                        label.setText("This username is taken");
                    }

                }
                catch(IOException f){

                }

            }
        });
    }

    public void goToMenu() throws IOException {
        Scene scene = usernameLogIn.getScene();
        ScreenController screenController = new ScreenController(scene);
        screenController.addScreen("menu", FXMLLoader.load(getClass().getResource("/view/menuView.fxml")));
        screenController.activate("menu");
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }
}
