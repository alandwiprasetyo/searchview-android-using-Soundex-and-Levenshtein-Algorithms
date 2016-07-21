package com.alandwiprasetyo.searchviewusingsoundex.model;

import com.alandwiprasetyo.searchviewusingsoundex.algoritmn.Soundex;

/**
 * Created by root on 19/07/16.
 */
public class Word {
    public String name;
    public String value = "";
    public Word(String name){
        this.name = name;
//        this.value = Soundex.soundex(name);
    }
}
