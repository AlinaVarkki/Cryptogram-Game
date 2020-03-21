package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DonePopUpController extends AnchorPane {

    public DonePopUpController(){

    }

    @FXML
    private Button closeButton;

    @FXML
    public void buttonClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


    //    @FXML
//    void btnGoodJob(ActionEvent event){
//        closeStage(event);
//    }

    private void closeStage(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }


}
