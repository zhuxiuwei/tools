package com.zxw.bean;

import org.apache.poi.ss.usermodel.IndexedColors;

import java.util.Locale;

/**
 * 带格式的cell值
 */
public class CellValueWithStyle {
    private String cellName;
    private String cellValue;
    private short fontColor = IndexedColors.BLACK.getIndex(); //字体色，默认黑
    private short cellBackgroundColor = IndexedColors.WHITE.getIndex();     //单元格背景色，默认白

    public CellValueWithStyle(String cellName, String cellValue) {
        this.cellName = cellName;
        this.cellValue = cellValue;
    }

    //字段不应该为空，设置背景色为浅绿色
    public void setShouldNotBeEmptyBackground(){
        this.cellBackgroundColor = IndexedColors.LIGHT_GREEN.getIndex();
    }

    //字段不该为空但是值为空了，设置字体为红色
    public void setShouldNotBeEmptyButEmptyFont(){
        this.fontColor = IndexedColors.RED.getIndex();
    }

    public boolean isEmptyCellValue(){
        if(cellValue == null || cellValue.isEmpty() || cellValue.trim().toLowerCase().equals("null"))
            return true;
        return false;
    }

    public String getCellName() {
        return cellName;
    }

    public void setCellName(String cellName) {
        this.cellName = cellName;
    }

    public String getCellValue() {
        return cellValue;
    }

    public void setCellValue(String cellValue) {
        this.cellValue = cellValue;
    }

    public short getFontColor() {
        return fontColor;
    }

    public void setFontColor(short fontColor) {
        this.fontColor = fontColor;
    }

    public short getCellBackgroundColor() {
        return cellBackgroundColor;
    }

    public void setCellBackgroundColor(short cellBackgroundColor) {
        this.cellBackgroundColor = cellBackgroundColor;
    }

    @Override
    public String toString() {
        return "CellValueWithStyle{" +
                "cellName='" + cellName + '\'' +
                ", cellValue='" + cellValue + '\'' +
                ", fontColor=" + fontColor +
                ", cellBackgroundColor=" + cellBackgroundColor +
                '}';
    }
}
