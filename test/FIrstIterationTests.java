import controllers.LogInController;
import cryptogram.CryptLoader;
import cryptogram.Cryptogram;
import cryptogram.LetterCryptogram;
import cryptogram.NumberCryptogram;
import org.junit.Assert;
import org.junit.Test;
import player.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import static controllers.LogInController.currentPlayer;
import static org.junit.Assert.*;

public class FIrstIterationTests {

    //checking if cryptogram doesn't equal to null in case of the number cryptogram
    @Test
    public void generateNumberCryptogramTest(){
        CryptLoader b = new CryptLoader();
        b.generatePhrase();
        Cryptogram a = new NumberCryptogram();
        String cryptogram = a.returnPhrase();
        Assert.assertNotEquals(null, cryptogram);
    }

    //checking if cryptogram doesn't equal to null in case of the letter cryptogram
    @Test
    public void generateLetterCryptogramTest(){
        CryptLoader b = new CryptLoader();
        b.generatePhrase();
        Cryptogram a = new LetterCryptogram();
        String cryptogram = a.returnPhrase();
        Assert.assertNotEquals(null, cryptogram);
    }

    //if there are no phrases stored, phrase is not assigned
    @Test
    public void noCryptogramsStoredTest(){
        File newFile = new File("resources/Cryptograms.txt");
        if (newFile.length() == 0) {
            CryptLoader b = new CryptLoader();
            b.generatePhrase();
            Cryptogram a = new LetterCryptogram();
            String cryptogram = a.returnPhrase();
            Assert.assertEquals(null, cryptogram);
        }
    }

    //Letter gets entered to the user solution map in case on Number cryptogram
    @Test
    public void letterEnteredNumberCryptTest(){
        CryptLoader b = new CryptLoader();
        b.generatePhrase();
        Cryptogram a = new NumberCryptogram();
        a.enterLetter("34", 'a');
        HashMap<Character, String> user_solution_test = a.getUser_solution();
        assertTrue(user_solution_test.containsKey('a') && user_solution_test.get('a').equals("34"));
    }

    //Letter gets entered to the user solution map in case on Letter cryptogram
    @Test
    public void letterEnteredLetterCryptTest(){
        CryptLoader b = new CryptLoader();
        b.generatePhrase();
        Cryptogram a = new LetterCryptogram();
        a.enterLetter("b", 'a');
        HashMap<Character, String> user_solution_test = a.getUser_solution();
        assertTrue(user_solution_test.containsKey('a') && user_solution_test.get('a').equals("b"));
    }

    //Players statistics(number of guesses is updated)
    @Test
    public void numberOfGuessesUpdated(){
        int initialGuesses = LogInController.currentPlayer.getTotalGuesses();
        CryptLoader b = new CryptLoader();
        b.generatePhrase();
        Cryptogram a = new LetterCryptogram();
        a.enterLetter("b", 'a');
        assertNotEquals(initialGuesses, LogInController.currentPlayer.getTotalGuesses());
    }

    @Test
    public void removeLetterInLetterCryptogramTest()  {
        CryptLoader b = new CryptLoader();
        b.generatePhrase();
        Cryptogram a = new LetterCryptogram();
        HashMap<Character, String> user_solution_test = a.getUser_solution();
        a.enterLetter("d", 'a');
        assertTrue(user_solution_test.containsKey('a') && user_solution_test.get('a').equals("d"));
        a.undoLetter('a');
        assertFalse(user_solution_test.containsKey('a'));
    }

    @Test
    public void removeLetterInNumberCryptogramTest()  {
        CryptLoader b = new CryptLoader();
        b.generatePhrase();
        Cryptogram a = new NumberCryptogram();
        HashMap<Character, String> user_solution_test = a.getUser_solution();
        a.enterLetter("d", 'a');
        assertTrue(user_solution_test.containsKey('a') && user_solution_test.get('a').equals("d"));
        a.undoLetter('a');
        assertFalse(user_solution_test.containsKey('a'));
    }

}
