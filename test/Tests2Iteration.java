import controllers.GameViewController;
import controllers.GetInputPopUpController;
import controllers.LogInController;
import cryptogram.CryptLoader;
import cryptogram.Cryptogram;
import cryptogram.LetterCryptogram;
import cryptogram.NumberCryptogram;
import org.junit.Test;
import player.Player;

import javax.swing.*;
import java.io.*;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class Tests2Iteration {

//    o Given a player has generated a cryptogram and is currently playing it
//    o When the player indicates they wish to save
//    o Then the cryptogram and state of play should be saved to a file for that player

    @Test
    public void saveNewCryptogramTest(){
        Player player = LogInController.currentPlayer;
        CryptLoader b = new CryptLoader();
        b.generatePhrase();
        Cryptogram a = new NumberCryptogram();
        GameViewController c = new GameViewController(true);
        c.saveGame();
        File tmpDir = new File("resources\\savedGames\\" + player.username + "savedGame.sav");
        boolean exists = tmpDir.exists();
        assertTrue(exists);
        tmpDir.delete();
    }
//    o Given a player has a saved cryptogram already, and are currently playing a generated cryptogram
//    o When the player indicates they wish to save
//    o Then they are asked if they want to overwrite the save file, if so it’s overwritten if not the original saved game is kept.
        @Test
        public void userAskedIfWantsSavingTest(){
            Player player = LogInController.currentPlayer;
            CryptLoader b = new CryptLoader();
            b.generatePhrase();
            Cryptogram a = new NumberCryptogram();
            GameViewController c = new GameViewController(true);
            c.saveGame();
            File tmpDir = new File("resources\\savedGames\\" + player.username + "savedGame.sav");
            boolean exists = tmpDir.exists();
            assertTrue(exists);
            tmpDir.delete();
        }

//    o Given a player has stored a saved cryptogram game
//    o When the player asks to load the saved game
//    o Then the game is loaded ready to resume play
        @Test
        public void cryptogramCanBePlayed() throws IOException, ClassNotFoundException {
            FileInputStream saveFile = new FileInputStream("C:\\Users\\USER\\Desktop\\cs207\\resources\\savedGames\\savedGame.sav");
            ObjectInputStream restore = new ObjectInputStream(saveFile);
            Cryptogram cryptogram = (Cryptogram) restore.readObject();
            restore.close();
             assertTrue(cryptogram != null);
    }

//    o Given a player has been created
//    o When the player asks to exit the game
//    o Then their details are saved to a file
    @Test
    public void userDetailsInTheFile() throws IOException, ClassNotFoundException{
        FileInputStream saveFile = new FileInputStream("C:\\Users\\USER\\Desktop\\cs207\\resources\\players\\test.sav");
        ObjectInputStream restore = new ObjectInputStream(saveFile);
        Player player = (Player) restore.readObject();
        restore.close();
        assertTrue(player != null);
    }

//    user statistics get updated
    @Test
    public void playerStatisticUpdated(){
        int initialGuesses = LogInController.currentPlayer.getTotalGuesses();
        CryptLoader b = new CryptLoader();
        b.generatePhrase();
        Cryptogram a = new LetterCryptogram();
        a.enterLetter("b", 'a');
        assertNotEquals(initialGuesses, LogInController.currentPlayer.getTotalGuesses());
    }




}
