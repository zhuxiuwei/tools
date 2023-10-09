package tools.bookTxt2Excel.bean;

import tools.bookTxt2Excel.utils.ConvertConfig;
import tools.bookTxt2Excel.utils.YamlConfigParser;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * BookWithStingFields也是book对象，和Book类的区别是，其属性不是List，而是字符串
 * 此类字段属性必须和Book类保持一样。
 */
public class BookWithStingFields implements ConvertableToExcel{
    private String ISBN;
    private String 作品语种;
    private String 题名;
    private String 版本;
    private String 出版信息;
    private String 稽核项;
    private String 一般注;
    private String 论题主题;
    private String 个人名称等同知识责任;
    private String 丛书;
    private String 目次备注;
    private String 概要;
    private String 个人主题;
    private String 变异题名;
    private String 题名主题;

    private List<Version> versions = new ArrayList<>();

    //Book 转 BookWithStingFields时，List多元素转string的分隔符
    private String delimiter;

    //利用反射，将Book里的list值，转成字符串，用delimiter分隔
    public BookWithStingFields(Book book) throws IllegalAccessException, NoSuchFieldException, IOException, ClassNotFoundException {
        ConvertConfig convertConfig = YamlConfigParser.parseConfig("tools/BookTxt2ExcelConfig.yaml");
        delimiter = convertConfig.getCombineMultipleValuesToOneValueDelimiter();

        Field[] listFields = book.getClass().getDeclaredFields();
        for(Field listField: listFields){
            listField.setAccessible(true);
            List listValues = (List) listField.get(book);
            if(listValues.size() > 0){
                Object firstVal = listValues.get(0);
                Field stringField = this.getClass().getDeclaredField(listField.getName());
                stringField.setAccessible(true);
                if(firstVal.getClass().equals(String.class)){   //如果List里保存的是字符串，则合并list为单一的字符串
                    String combinedStringValue = (String) listValues.stream().collect(Collectors.joining(delimiter));
                    stringField.set(this, combinedStringValue);
                }else { //其他类型，则不做处理，直接赋值
                    stringField.set(this, listValues);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "BookWithStingFields{" +
                "ISBN=" + ISBN +
                ", 作品语种=" + 作品语种 +
                ", 题名=" + 题名 +
                ", 版本=" + 版本 +
                ", 出版信息=" + 出版信息 +
                ", 稽核项=" + 稽核项 +
                ", 一般注=" + 一般注 +
                ", 论题主题=" + 论题主题 +
                ", 个人名称等同知识责任=" + 个人名称等同知识责任 +
                ", 丛书=" + 丛书 +
                ", 目次备注=" + 目次备注 +
                ", 概要=" + 概要 +
                ", 个人主题=" + 个人主题 +
                ", 变异题名=" + 变异题名 +
                ", 题名主题=" + 题名主题 +
                ", versions=" + versions +
                '}';
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String get作品语种() {
        return 作品语种;
    }

    public void set作品语种(String 作品语种) {
        this.作品语种 = 作品语种;
    }

    public String get题名() {
        return 题名;
    }

    public void set题名(String 题名) {
        this.题名 = 题名;
    }

    public String get版本() {
        return 版本;
    }

    public void set版本(String 版本) {
        this.版本 = 版本;
    }

    public String get出版信息() {
        return 出版信息;
    }

    public void set出版信息(String 出版信息) {
        this.出版信息 = 出版信息;
    }

    public String get稽核项() {
        return 稽核项;
    }

    public void set稽核项(String 稽核项) {
        this.稽核项 = 稽核项;
    }

    public String get一般注() {
        return 一般注;
    }

    public void set一般注(String 一般注) {
        this.一般注 = 一般注;
    }

    public String get论题主题() {
        return 论题主题;
    }

    public void set论题主题(String 论题主题) {
        this.论题主题 = 论题主题;
    }

    public String get个人名称等同知识责任() {
        return 个人名称等同知识责任;
    }

    public void set个人名称等同知识责任(String 个人名称等同知识责任) {
        this.个人名称等同知识责任 = 个人名称等同知识责任;
    }

    public String get丛书() {
        return 丛书;
    }

    public void set丛书(String 丛书) {
        this.丛书 = 丛书;
    }

    public String get目次备注() {
        return 目次备注;
    }

    public void set目次备注(String 目次备注) {
        this.目次备注 = 目次备注;
    }

    public String get概要() {
        return 概要;
    }

    public void set概要(String 概要) {
        this.概要 = 概要;
    }

    public String get个人主题() {
        return 个人主题;
    }

    public void set个人主题(String 个人主题) {
        this.个人主题 = 个人主题;
    }

    public String get变异题名() {
        return 变异题名;
    }

    public void set变异题名(String 变异题名) {
        this.变异题名 = 变异题名;
    }

    public String get题名主题() {
        return 题名主题;
    }

    public void set题名主题(String 题名主题) {
        this.题名主题 = 题名主题;
    }

    public List<Version> getVersions() {
        return versions;
    }

    public void setVersions(List<Version> versions) {
        this.versions = versions;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }
}
