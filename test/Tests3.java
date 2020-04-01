import controllers.FrequenciesContoller;
import controllers.GameViewController;
import controllers.LogInController;
import cryptogram.CryptLoader;
import cryptogram.Cryptogram;
import cryptogram.LetterCryptogram;
import cryptogram.NumberCryptogram;
import javafx.scene.layout.GridPane;
import org.junit.Test;
import player.Player;

import java.io.File;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Tests3 {

//    o Given a cryptogram is being played and hasn’t been completed by the player
//    o When the player selects to show the solution
//    o Then the correct mapping is applied and the solution displayed to the player
    //using method that is called when we get solution to fill textboxes
    @Test
    public void showSolutionTest(){
    Cryptogram c = new Cryptogram() {

    @Override
    public HashMap getMap() {
        return null;
    }

    @Override
    public HashMap encryptionMap() {
        return null;
    }

    };

    String solution = c.returnSolution();
    assertEquals(solution, c.returnPhrase());
    }


//    Scenario player views the frequencies of letters in the cryptogram
//    o Given a cryptogram is being played
//    o When the player asks to view the frequencies
//    o Then the proportion of letter frequencies in the cryptogram is shown as well as the common proportions of letter frequencies in the English language

    @Test
    public void frequnciesTest(){

        FrequenciesContoller fc = new FrequenciesContoller();
        fc.fillEnglishAplhabet();
        HashMap h = fc.returnEnglishAlphabetMap();
        assertTrue(h.containsKey('Q') && h.containsValue(0.1962));

    }

//    Scenario
//    o Given a cryptogram is being played and there are still cryptogram values to be mapped
//    o When a player asks for a hint
//    o Then the letter for which the corresponding cryptogram value which has not been mapped is added to the mapping and displayed to the user


    @Test
    public void hintTest(){

        CryptLoader f = new CryptLoader();
        f.generatePhrase();
        Cryptogram c = new LetterCryptogram() {
            @Override
            public HashMap getMap() {
                return null;
            }

            @Override
            public HashMap encryptionMap() {
                return null;
            }
        };
        //c.fillUsedMap();
        Character ch = c.giveHint();
        assertTrue(CryptLoader.gamePhrase.contains(ch.toString()));
    }

//    o Given at least one player has successfully completed a cryptogram
//    o When a player selects to see the top 10 players by number of successfully completed cryptograms
//    o Then the top 10 players are shown, ordered by highest proportion of successfully completed cryptograms to least, with blank spaces where there is no player to fill that position
//       Scenario: no player stats have been stored


}
