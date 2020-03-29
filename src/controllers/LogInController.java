//first, login view controller

package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import player.Player;

import java.io.*;


public class LogInController {
    private static final String playerDirectory = "resources\\players\\";

    //Player has initial value only for testing, otherwise it is equal to null in other classes and nothing can be tested
    private static Player a = new Player("test");
    public static Player currentPlayer = a;

    @FXML
    private TextField usernameLogIn;

    //password are actually there for looking better, they don't do anything, not actually needed for our user stories, usernames do all the needed job
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
    public void initialize(){
        usernameLogIn.setPromptText("username");
        passwordLogIn.setPromptText("password");
        usernameSignIn.setPromptText("username");
        passwordSignIn.setPromptText("password");

        loginButton.setOnAction(actionEvent -> {
            String existingUsername = usernameLogIn.getText();
            String existingPassword = passwordLogIn.getText();
            //when user wants to login, first try to load the file
            try {

                String tempname = playerDirectory + existingUsername + ".sav";
                FileInputStream saveFile = new FileInputStream(tempname);
                ObjectInputStream restore = new ObjectInputStream(saveFile);
                currentPlayer = (Player)restore.readObject();

                //after logging in go to menu
                goToMenu();
                //if file doesn't exist, create new one
            } catch (FileNotFoundException | ClassNotFoundException e) {

                try {

                    String tempname = playerDirectory + existingUsername + ".sav";
                    File file = new File(tempname);
                    //if user name already exists, don't register
                    boolean exists = file.exists();
                    if(!exists) {
                        Player player = new Player(existingUsername);
                        currentPlayer = player;
                        System.out.println(existingUsername + existingPassword);
                        //later add pop up if username exists


                        FileOutputStream saveFile = new FileOutputStream(tempname);
                        ObjectOutputStream save = new ObjectOutputStream(saveFile);

                        save.writeObject(player);

                        save.close();

                        //show popup that new user is created
                        newUserPopUp();
                        //after user is signed in, move to the menu
                        goToMenu();
                    }
                    else{
                        label.setText("This username is taken");
                    }

                     }catch(IOException f){}

                //if file is not readable, show user pop up saying something is wrong with the file
            } catch (IOException e) {
                somethingWrong();
            }


        });

        //if user wants to register, new file is created for them
        registerButton.setOnAction(actionEvent -> {
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

                    save.writeObject(player);

                    save.close();
                    //after user is signed in, move to the menu
                    goToMenu();
                }
                else{
                    label.setText("This username is taken");
                }

            }
            catch(IOException f){
            somethingWrong();
            }
        });
    }

    public void goToMenu() throws IOException {
        Scene scene = usernameLogIn.getScene();
        ScreenController screenController = new ScreenController(scene);
        screenController.addScreen("menu", FXMLLoader.load(getClass().getResource("/view/menuView.fxml")));
        screenController.activate("menu");
    }

    //if user tried to login, but this username does not exist, let them know new user was created
    public void newUserPopUp(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/finishPopUpView.fxml"));

        try {
            Parent parent = loader.load();
            PopUpController pop = loader.getController();
            pop.setText(" Account with this username not found, new account was created");
            Scene scene = new Scene(parent, 450, 270);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void somethingWrong(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/finishPopUpView.fxml"));

        try {
            Parent parent = loader.load();
            PopUpController pop = loader.getController();
            pop.setText(" File is corrupted");
            Scene scene = new Scene(parent, 450, 270);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}