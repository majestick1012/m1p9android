package com.example.educatif.model;

public class Preferences {

    private int foregroundColor;
    private int backgroundColor;
    private double imageWidth;
    private double imageHeight;
    private double textParamsWidth;
    private double textParamsHeight;
    private double textTableWidth;
    private double textTableHeight;
    private int rows;

    public Preferences(int foregroundColor,
                       int backgroundColor,
                       double imageWidth,
                       double imageHeight,
                       double textParamsWidth,
                       double textParamsHeight,
                       double textTableWidth,
                       double textTableHeight,
                       int rows){
        this.foregroundColor = foregroundColor;
        this.backgroundColor = backgroundColor;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.textParamsWidth = textParamsWidth;
        this.textParamsHeight = textParamsHeight;
        this.textTableWidth = textTableWidth;
        this.textTableHeight = textTableHeight;
        this.rows = rows;
    }

    public int getForegroundColor() {
        return foregroundColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }


    public void setForegroundColor(int foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public double getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(double imageWidth) {
        this.imageWidth = imageWidth;
    }

    public double getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(double imageHeight) {
        this.imageHeight = imageHeight;
    }

    public double getTextParamsWidth() {
        return textParamsWidth;
    }

    public void setTextParamsWidth(double textParamsWidth) {
        this.textParamsWidth = textParamsWidth;
    }

    public double getTextParamsHeight() {
        return textParamsHeight;
    }

    public void setTextParamsHeight(double textParamsHeight) {
        this.textParamsHeight = textParamsHeight;
    }

    public double getTextTableWidth() {
        return textTableWidth;
    }

    public void setTextTableWidth(double textTableWidth) {
        this.textTableWidth = textTableWidth;
    }

    public double getTextTableHeight() {
        return textTableHeight;
    }

    public void setTextTableHeight(double textTableHeight) {
        this.textTableHeight = textTableHeight;
    }
}
