import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.HashMap;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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

    //checking if when letter is entered, it is added to the user solution map
    @Test
    public void enterLetterTest(){
        Cryptogram a = new LetterCryptogram<>();
        HashMap<Character, String> user_solution_test = new HashMap<>();
        a.enterLetter("d", 'a', "hello world", user_solution_test);
        assertTrue(user_solution_test.containsKey('a') && user_solution_test.get('a').equals("d"));
    }





}
