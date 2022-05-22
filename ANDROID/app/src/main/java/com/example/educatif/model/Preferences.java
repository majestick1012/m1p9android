package com.example.educatif.model;

public class Preferences {

    private int foregroundColor;
    private int backgroundColor;
    private double textSizeWidth;
    private double textSizeHeight;

    public Preferences(int foregroundColor,int backgroundColor,double textSizeWidth,double textSizeHeight){
        this.foregroundColor = foregroundColor;
        this.backgroundColor = backgroundColor;
        this.textSizeWidth = textSizeWidth;
        this.textSizeHeight = textSizeHeight;
    }

    public int getForegroundColor() {
        return foregroundColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public double getTextSizeWidth() {
        return textSizeWidth;
    }

    public double getTextSizeHeight() {
        return textSizeHeight;
    }

    public void setForegroundColor(int foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setTextSizeWidth(double textSizeWidth) {
        this.textSizeWidth = textSizeWidth;
    }

    public void setTextSizeHeight(double textSizeHeight) {
        this.textSizeHeight = textSizeHeight;
    }
}
