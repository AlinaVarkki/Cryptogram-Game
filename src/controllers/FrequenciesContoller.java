package controllers;

import cryptogram.Cryptogram;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class FrequenciesContoller {
    HashMap<Character, Double> englishAlphabetMap = new HashMap<>();
    TreeMap<String, Double> cryptogramAlphabetMap = new TreeMap<>();

    @FXML
    private GridPane alphabetFrequencies;

    @FXML
    private GridPane cryptogramFrequencies;

    @FXML
    private void initialize(){
        fillAplhabetGrid();
        fillCryptogramGrid();
    }

    public void fillAplhabetGrid() {
    fillEnglishAplhabet();
    ArrayList keys = new ArrayList(englishAlphabetMap.keySet());
    //loop through set size and allocate text to every row
    for(int i = 0; i < englishAlphabetMap.keySet().size(); i++) {
        Text e = new Text();
        e.setText(String.valueOf(keys.get(i)));
        alphabetFrequencies.add(e, 0, i);
        Text a = new Text();
        a.setText(String.valueOf(englishAlphabetMap.get(keys.get(i))) + " %");
        alphabetFrequencies.add(a, 1, i);

    }
    }

    public void fillEnglishAplhabet() {
        englishAlphabetMap.put('E',11.1607);
        englishAlphabetMap.put('A',8.4966);
        englishAlphabetMap.put('R',7.5809);
        englishAlphabetMap.put('I',7.5448);
        englishAlphabetMap.put('O',7.1635);
        englishAlphabetMap.put('T',6.9509);
        englishAlphabetMap.put('N',6.6544);
        englishAlphabetMap.put('S',5.7351);
        englishAlphabetMap.put('L',5.4893);
        englishAlphabetMap.put('C',4.5388);
        englishAlphabetMap.put('U',3.6308);
        englishAlphabetMap.put('D',3.3844);
        englishAlphabetMap.put('P',3.1671);
        englishAlphabetMap.put('M',3.0129);
        englishAlphabetMap.put('H',3.0034);
        englishAlphabetMap.put('G',2.4705);
        englishAlphabetMap.put('B',2.0720);
        englishAlphabetMap.put('F',1.8121);
        englishAlphabetMap.put('Y',1.7779);
        englishAlphabetMap.put('W',1.2899);
        englishAlphabetMap.put('K',1.1016);
        englishAlphabetMap.put('V',1.0074);
        englishAlphabetMap.put('X',0.2902);
        englishAlphabetMap.put('Z',0.2722);
        englishAlphabetMap.put('J',0.1965);
        englishAlphabetMap.put('Q',0.1962);
    }

    public HashMap returnEnglishAlphabetMap(){
        return englishAlphabetMap;
    }

    public void fillCryptogramGrid() {
        fillCryptogramAlphabet();
        List<String> keys = new ArrayList(cryptogramAlphabetMap.keySet());

        for(int i = 0; i < cryptogramAlphabetMap.keySet().size(); i++) {
            Text e = new Text();
            e.setText(String.valueOf(keys.get(i)));
            cryptogramFrequencies.add(e, 0, i);
            Text a = new Text();
            a.setText(String.valueOf(cryptogramAlphabetMap.get(keys.get(i))) + " %");
            cryptogramFrequencies.add(a, 1, i);

        }
    }


    public void fillCryptogramAlphabet() {
        int counter = 0;
        String s = GameViewController.phrase;
        for(int i = 0; i < s.length(); i++){
            counter++;
            String curr = Character.toString(s.charAt(i));
            if(cryptogramAlphabetMap.get(curr) == null){
                cryptogramAlphabetMap.put(curr, 1.00);
            }else{
                cryptogramAlphabetMap.put(curr, cryptogramAlphabetMap.get(curr) +1);
            }
        }
//this is all here because some map is String to char
        List<String> keys = new ArrayList(cryptogramAlphabetMap.keySet());
        List<Character>keys1 = new ArrayList<>();
        for(int i = 0; i<keys.size(); i++){
            keys1.add(keys.get(i).charAt(0));
        }

        //replace actual values with mapped ones
        for(int i = 0; i<keys.size();i++){
            cryptogramAlphabetMap.put(Cryptogram.usedMapping.get(keys1.get(i)), cryptogramAlphabetMap.get(keys.get(i)));
            cryptogramAlphabetMap.remove(keys.get(i));
        }

        if(cryptogramAlphabetMap.containsKey(" ")){
            cryptogramAlphabetMap.remove(" ");
        }

        List<String> keys2 = new ArrayList(cryptogramAlphabetMap.keySet());

        for(int i = 0; i < keys2.size(); i++){
            Double a = cryptogramAlphabetMap.get(keys2.get(i))/counter * 100;
            DecimalFormat df = new DecimalFormat("#.##");
        cryptogramAlphabetMap.put(keys2.get(i), Double.parseDouble(df.format(a)));
        }
    }






}
