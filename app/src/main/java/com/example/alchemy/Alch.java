package com.example.alchemy;

import java.util.ArrayList;

public class Alch {
    private String Comp1;
    private String Comp2;
    private String Result;

    public Alch(String Comp1, String Comp2, String Result){
        this.Comp1 = Comp1;
        this.Comp2 = Comp2;
        this.Result = Result;
    }

    /*public String getComp1(){
        return Comp1;
    }
    public String getComp2(){
        return Comp2;
    }
    public String getResult(){
        return Result;
    }*/

    public String getResult(String comp1, String comp2){
        if (this.Comp1.equals(comp1) & this.Comp2.equals(comp2))
            return this.Result;
        else
            return "NoResult";
    }

}
