package tools.bookTxt2Excel.config;

import tools.bookTxt2Excel.bean.ConvertableToExcel;

import java.util.List;
import java.util.Map;

//承载配置文件
public class ConvertConfig {
    public Map<String, List<String>> convertClassAndFields; //待转换excel的类和字段
    public Class<ConvertableToExcel> bottomConvertClass;    //最底层待转换excel的java类
    public String targetExcelPath;  //目标excel生成地址
    public String sourceTxtFilePath;  //源txt文件地址
    public Map<String, List<String>> excludedClassFieldsInExcel; //解析txt过程中用到的，但是在excel表头里不应该出现的字段。

    public String combineMultipleValuesToOneValueDelimiter; //一个字段有多个值，合并成一个值时的分隔符

    public ConvertConfig(Map<String, List<String>> convertClassAndFields, Class<ConvertableToExcel> bottomConvertClass) {
        this.convertClassAndFields = convertClassAndFields;
        this.bottomConvertClass = bottomConvertClass;
    }

    public Map<String, List<String>> getConvertClassAndFields() {
        return convertClassAndFields;
    }

    public void setConvertClassAndFields(Map<String, List<String>> convertClassAndFields) {
        this.convertClassAndFields = convertClassAndFields;
    }

    public Class<ConvertableToExcel> getBottomConvertClass() {
        return bottomConvertClass;
    }

    public void setBottomConvertClass(Class<ConvertableToExcel> bottomConvertClass) {
        this.bottomConvertClass = bottomConvertClass;
    }

    public String getTargetExcelPath() {
        return targetExcelPath;
    }

    public void setTargetExcelPath(String targetExcelPath) {
        this.targetExcelPath = targetExcelPath;
    }

    public String getSourceTxtFilePath() {
        return sourceTxtFilePath;
    }

    public void setSourceTxtFilePath(String sourceTxtFilePath) {
        this.sourceTxtFilePath = sourceTxtFilePath;
    }

    public Map<String, List<String>> getExcludedClassFieldsInExcel() {
        return excludedClassFieldsInExcel;
    }

    public void setExcludedClassFieldsInExcel(Map<String, List<String>> excludedClassFieldsInExcel) {
        this.excludedClassFieldsInExcel = excludedClassFieldsInExcel;
    }

    public String getCombineMultipleValuesToOneValueDelimiter() {
        return combineMultipleValuesToOneValueDelimiter;
    }

    public void setCombineMultipleValuesToOneValueDelimiter(String combineMultipleValuesToOneValueDelimiter) {
        this.combineMultipleValuesToOneValueDelimiter = combineMultipleValuesToOneValueDelimiter;
    }

    public ConvertConfig(Map<String, List<String>> convertClassAndFields, Class<ConvertableToExcel> bottomConvertClass, String targetExcelPath, String sourceTxtFilePath, Map<String, List<String>> excludedClassFieldsInExcel, String combineMultipleValuesToOneValueDelimiter) {
        this.convertClassAndFields = convertClassAndFields;
        this.bottomConvertClass = bottomConvertClass;
        this.targetExcelPath = targetExcelPath;
        this.sourceTxtFilePath = sourceTxtFilePath;
        this.excludedClassFieldsInExcel = excludedClassFieldsInExcel;
        this.combineMultipleValuesToOneValueDelimiter = combineMultipleValuesToOneValueDelimiter;
    }

    @Override
    public String toString() {
        return "ConvertConfig{" +
                "convertClassAndFields=" + convertClassAndFields +
                ", bottomConvertClass=" + bottomConvertClass +
                ", targetExcelPath='" + targetExcelPath + '\'' +
                ", sourceTxtFilePath='" + sourceTxtFilePath + '\'' +
                ", excludedClassFieldsInExcel=" + excludedClassFieldsInExcel +
                ", combineMultipleValuesToOneValueDelimiter='" + combineMultipleValuesToOneValueDelimiter + '\'' +
                '}';
    }
}