//controller for pop up window
//used in different parts of the game with changed pop up message

package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PopUpController extends AnchorPane {

    public PopUpController(){

    }

    @FXML
    private Button closeButton;
    @FXML
    private Label label;

    @FXML
    public void buttonClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


    public void setText(String text){
        label.setText(text);
    }




}
