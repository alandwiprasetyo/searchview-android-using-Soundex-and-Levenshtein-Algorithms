package com.alandwiprasetyo.searchviewusingsoundex.model;

import com.alandwiprasetyo.searchviewusingsoundex.algoritmn.Soundex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 19/07/16.
 */
public class DummyData {
    static List<Word> wordList = new ArrayList<>();
    public static List<Word> dummy(){
        wordList.add(new Word("Alan"));
        wordList.add(new Word("Alin"));
        wordList.add(new Word("Desy"));
        wordList.add(new Word("Desi"));
        wordList.add(new Word("Rochmat"));
        wordList.add(new Word("Rohmat"));
        return wordList;
    }

}
