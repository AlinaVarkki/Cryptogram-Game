package cryptogram;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class CryptLoader {






    //return random cryptogram.Cryptogram(by now random)
    public static String returnPhrase()  {
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

            while (s.hasNextLine()) {
                cryptograms.add(s.nextLine());
            }
            s.close();
            
        
        int random = 0;
//        try{
        random = rand.nextInt(cryptograms.size());
//        }catch(Exception e){
//            System.out.println("Sorry there are no phrases");
//            System.exit(1);
//        }
        phrase = cryptograms.get(random);
        return phrase;
    }
    
    
}
