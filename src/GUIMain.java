import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GUIMain extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/menuView.fxml"));
        primaryStage.setTitle("Encryption Game");

        primaryStage.setScene(new Scene(root, 800, 530));
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
