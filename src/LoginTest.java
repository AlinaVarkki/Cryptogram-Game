import org.junit.jupiter.api.Test;
import java.util.*;
import java.io.FileNotFoundException;
import static org.junit.jupiter.api.Assertions.*;

class LoginTest {
    @Test
    public void usernameNotExist() throws FileNotFoundException {
        String testUsername = "testNameNotInFile";
        String filename = "resources/Logins.txt";
        Boolean found = false;
        Scanner s = new Scanner(filename);
        s.useDelimiter("[,\n]");
        while (s.hasNext()) {
            if (s.next().equals(testUsername)) {
                found = true;
            }
        }
        assertFalse(found);
    }

    @Test
    public void passwordNotMatch() throws FileNotFoundException {
        String testPass = "123";
        String filename = "resources/Logins.txt";
        Scanner s = new Scanner(filename);
        s.useDelimiter("[,\n]");
        Boolean found = false;
        Boolean match = false;
        while (s.hasNext() && !found) {
            if (s.next().equals("test")) {
                found = true;
                if (s.next().equals(testPass)) {
                    match = true;
                }
            }
        }
        assertFalse(match);
    }
}