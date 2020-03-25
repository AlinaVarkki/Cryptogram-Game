package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class LogInController {

    @FXML
    private TextField usernameLogIn;

    @FXML
    private TextField passwordLogIn;

    @FXML
    private TextField usernameSignIn;

    @FXML
    private TextField passwordSignIn;

    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;



    @FXML
    public void initialize(){
        usernameLogIn.setPromptText("username");
        passwordLogIn.setPromptText("password");
        usernameSignIn.setPromptText("username");
        passwordSignIn.setPromptText("password");

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
//if user wants to register, new file is created for them
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
    }
}
