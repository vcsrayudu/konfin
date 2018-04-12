package com.konfin.util;

/**
 * Created by cvidudal on 4/10/2018.
 */
import java.util.HashMap;

public class FundhouseList {
    int fundhouseNumber;
    String fundhouseName;
    HashMap<Integer,String> funds;
    public int getFundhouseNumber() {
        return fundhouseNumber;
    }
    public void setFundhouseNumber(int fundhouseNumber) {
        this.fundhouseNumber = fundhouseNumber;
    }
    public String getFundhouseName() {
        return fundhouseName;
    }
    public void setFundhouseName(String fundhouseName) {
        this.fundhouseName = fundhouseName;
    }
    public HashMap<Integer, String> getFunds() {
        return funds;
    }
    public void setFunds(HashMap<Integer, String> funds) {
        this.funds = funds;
    }


}
