package com.example.alchemy;

public class MyColor {
    private String Name;
    private Integer textColor;
    private Integer bgColor;

    public MyColor(String Name, Integer textColor,Integer bgColor){
        this.Name = Name;
        this.textColor = textColor;
        this.bgColor = bgColor;
    }

    public String getName(){
        return Name;
    }

    public Integer getTextColor(){
        return textColor;
    }

    public Integer getBgColor(){
        return bgColor;
    }
}
