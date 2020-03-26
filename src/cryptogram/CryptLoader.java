package cryptogram;

import controllers.PopUpController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class CryptLoader {


    public static String gamePhrase;

    //return random cryptogram.Cryptogram(by now random)
    public void generatePhrase()  {
        Scanner s = null;
        Random rand = new Random();
        String phrase;
        List<String> cryptograms = new ArrayList<>();

        //method to populate array with all the cryptograms
            try {
                s = new Scanner(new File("C:\\Users\\USER\\Desktop\\cs207\\resources\\Cryptograms.txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        assert s != null;
        while (s.hasNextLine()) {
                cryptograms.add(s.nextLine());
            }
            s.close();
            
        
        int random = 0;
       try{
        random = rand.nextInt(cryptograms.size());
        }catch(Exception e){

           FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/finishPopUpView.fxml"));

           try {
               Parent parent = loader.load();
               PopUpController pop = loader.getController();
               pop.setText("Sorry, there are no phrases to play");
               Scene scene = new Scene(parent, 450, 270);
               Stage stage = new Stage();
               stage.initModality(Modality.APPLICATION_MODAL);
               stage.setScene(scene);
               stage.showAndWait();
               //stage.close();
           } catch (IOException f) {
               e.printStackTrace();
           }

           System.exit(1);
        }
        gamePhrase = cryptograms.get(random);
    }
    
    
}
