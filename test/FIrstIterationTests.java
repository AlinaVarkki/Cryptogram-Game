import cryptogram.Cryptogram;
import cryptogram.LetterCryptogram;
import cryptogram.NumberCryptogram;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.HashMap;

import static org.junit.Assert.*;

public class FIrstIterationTests {

    //checking if cryptogram doesn't equal to null in case of the number cryptogram
    @Test
    public void generateNumberCryptogramTest() throws FileNotFoundException {
        Cryptogram a = new NumberCryptogram();
        String cryptogram = a.returnPhrase();
        Assert.assertNotEquals(null, cryptogram);
    }

    //checking if cryptogram doesn't equal to null in case of the letter cryptogram
    @Test
    public void generateLetterCryptogramTest() throws FileNotFoundException {
        Cryptogram a = new LetterCryptogram<>();
        String cryptogram = a.returnPhrase();
        Assert.assertNotEquals(null, cryptogram);
    }

    //checking if when letter entered in the letter cryptogram is added to the user solution map
    @Test
    public void enterLetterInLetterCryptogramTest(){
        Cryptogram a = new LetterCryptogram();
        HashMap<Character, String> user_solution_test = new HashMap<>();
        a.enterLetter("d", 'a', "hello world", user_solution_test);
        assertTrue(user_solution_test.containsKey('a') && user_solution_test.get('a').equals("d"));
    }

    //checking if when letter entered in the number cryptogram is added to the user solution map
    @Test
    public void enterLetterInNumberCryptogramTest(){
        Cryptogram a = new NumberCryptogram();
        HashMap<Character, String> user_solution_test = new HashMap<>();
        a.enterLetter("34", 'a', "22 1 34 8", user_solution_test);
        assertTrue(user_solution_test.containsKey('a') && user_solution_test.get('a').equals("34"));
    }

    //checking if the letter is removed from the user solution map
    @Test
    public void removeLetterInLetterCryptogramTest() throws FileNotFoundException {
        Cryptogram a = new LetterCryptogram();
        HashMap<Character, String> user_solution_test = new HashMap<>();
        a.enterLetter("d", 'a', "hello world", user_solution_test);
        assertTrue(user_solution_test.containsKey('a') && user_solution_test.get('a').equals("d"));
        a.undoLetter('a', user_solution_test);
        assertFalse(user_solution_test.containsKey('a'));
    }

    //checking if the letter is removed from the user solution map in case of number cryptogram
    @Test
    public void removeLetterInNumberCryptogramTest() throws FileNotFoundException {
        Cryptogram a = new NumberCryptogram();
        HashMap<Character, String> user_solution_test = new HashMap<>();
        a.enterLetter("23", 'a', "23 3 23", user_solution_test);
        assertTrue(user_solution_test.containsKey('a') && user_solution_test.get('a').equals("23"));
        a.undoLetter('a', user_solution_test);
        assertFalse(user_solution_test.containsKey('a'));
    }

}
