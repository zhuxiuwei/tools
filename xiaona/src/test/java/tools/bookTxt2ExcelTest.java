package tools;

import org.junit.jupiter.api.Assertions;
import tools.bookTxt2Excel.bean.*;
import tools.bookTxt2Excel.config.ConvertConfig;
import tools.bookTxt2Excel.service.ExcelCreator;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;

public class bookTxt2ExcelTest {
    @Test
    public void testPrintCertainFields() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, IOException {
        ExcelCreator excelCreator = new ExcelCreator();
        java.util.List<Map<String, String>> resultList =  new ArrayList<>();
        excelCreator.createResultMapList(mockBook(), mockConfig(), resultList, new HashMap<>());
        System.out.println(resultList);
        /**
         * [{标识=, 复本号=副本1_1, ISBN=isbn1,isbn2, 价格=20, versionNumber=v1, 题名=西游记},
         *  {标识=, 复本号=副本2_1, ISBN=isbn1,isbn2, 价格=20, versionNumber=v2, 题名=西游记},
         *  {标识=, 复本号=副本2_2, ISBN=isbn1,isbn2, 价格=40, versionNumber=v2, 题名=西游记}]
         */
    }

    @Test
    public void testBookWithStingFields() throws IllegalAccessException, NoSuchFieldException, IOException, ClassNotFoundException {
        BookWithStingFields book = mockBook();
        System.out.println(book);
        /**
         * BookWithStingFields{ISBN=isbn1,isbn2, 作品语种=null, 题名=西游记, 版本=null, 出版信息=null, 稽核项=null, 一般注=null, 论题主题=null, 个人名称等同知识责任=null, 丛书=null, 目次备注=null, 概要=null, 个人主题=名著,神话, 变异题名=null, 题名主题=null, versions=Version{versionNumber='v1', copies=Copy{复本号='副本1_1', 标识='null', 馆别='null', 价格='20', 文献类别1='null', 文献类别2='null', 类型='null', 永久馆藏位置='null', 已新增='null', 目前馆址='null', 件='null', cat3='null', cat4='null', cat5='null', 馆藏期限='永久'}},Version{versionNumber='v2', copies=Copy{复本号='副本2_1', 标识='null', 馆别='null', 价格='20', 文献类别1='null', 文献类别2='null', 类型='null', 永久馆藏位置='null', 已新增='null', 目前馆址='null', 件='null', cat3='null', cat4='null', cat5='null', 馆藏期限='永久'},Copy{复本号='副本2_2', 标识='null', 馆别='null', 价格='40', 文献类别1='null', 文献类别2='null', 类型='null', 永久馆藏位置='null', 已新增='null', 目前馆址='null', 件='null', cat3='null', cat4='null', cat5='null', 馆藏期限='永久'}}}
         */
    }

    @Test
    public void test子类反射判断(){
        //java子类与父类的判断(instanceof与isAssignableFrom) https://blog.csdn.net/weixin_44728369/article/details/120564227
        java.util.List<String> l1 = asList("a", "b");
        java.util.List<String> l2 = new ArrayList();

        //用isAssignableFrom判断类是其他类的同类或父类
        Assertions.assertTrue(java.util.List.class.isAssignableFrom(l1.getClass()) == true);
        Assertions.assertTrue(java.util.List.class.isAssignableFrom(l2.getClass()) == true);

        //用instanceOf判断类是其他类的子类或父类
        Assertions.assertTrue((l1 instanceof java.util.List) == true);
        Assertions.assertTrue((l2 instanceof java.util.List) == true);
    }

    private ConvertConfig mockConfig() throws ClassNotFoundException {
        Map<String, java.util.List<String>> convertClassAndFieldsMap = new HashMap<>();
        convertClassAndFieldsMap.put("tools.bookTxt2Excel.bean.BookWithStingFields", asList("ISBN", "题名", "versions"));
        convertClassAndFieldsMap.put("tools.bookTxt2Excel.bean.Version", asList("versionNumber", "copies"));
        convertClassAndFieldsMap.put("tools.bookTxt2Excel.bean.Copy", asList("复本号","标识","价格"));
        return new ConvertConfig(convertClassAndFieldsMap,
                (Class<ConvertableToExcel>) Class.forName("tools.bookTxt2Excel.bean.Copy"));
    }

    private BookWithStingFields mockBook() throws NoSuchFieldException, IllegalAccessException, IOException, ClassNotFoundException {
        Book book = new Book();
        book.setISBN(asList("isbn1","isbn2"));
        book.set题名(asList("西游记"));
        book.set个人主题(asList("名著","神话"));

        Version v1 = new Version();
        v1.setVersionNumber("v1");

        Version v2 = new Version();
        v2.setVersionNumber("v2");

        book.setVersions(asList(v1, v2));

        Copy copy1_1 = new Copy();
        copy1_1.set复本号("副本1_1");
        copy1_1.set价格("20");
        copy1_1.set馆藏期限("永久");

        v1.setCopies(asList(copy1_1));

        Copy copy2_1 = new Copy();
        copy2_1.set复本号("副本2_1");
        copy2_1.set价格("20");
        copy2_1.set馆藏期限("永久");

        Copy copy2_2 = new Copy();
        copy2_2.set复本号("副本2_2");
        copy2_2.set价格("40");
        copy2_2.set馆藏期限("永久");

        v2.setCopies(asList(copy2_1, copy2_2));

        return new BookWithStingFields(book);
    }

    //版本号正则测试。231113：用正则判断版本号，已弃用。
    @Test
    public void isRegexMatch(){
        //匹配的
        List<String> matches = Arrays.asList(
                "(HT)F I546.22/6",
                "(HT)F O115.45/1:2",
                "(HT)F K126/9",
                " 10.(8)1-311./1",
                "K820.7/133:1");
        String regex = "^[a-zA-Z0-9\\-./:\\s()=]+/[a-zA-Z0-9\\-./:\\s()=]+$";     //只包含字母/数字/./斜杠/冒号的，则是版本行
        for(String str: matches) {
            boolean isMatch = Pattern.matches(regex, str.trim());
            System.out.println(isMatch + ": " + str);
        }

        System.out.println("----------------");

        //不匹配的
        List<String> notMatches = Arrays.asList("Veneris / translated by J. W. Mackail.",
                "Compendium of Roman history / Velleius Paterculus. Res",
                "gestae divi Augusti / with an English translation by",
                "Frederick W. Shipley.",
                "printing)",
                "translation by Earnest Cary ; on the basis of the",
                "version of Herbert Baldwin Foster.",
                "XX(1594601.1)");
        for(String str: notMatches) {
            boolean isMatch = Pattern.matches(regex, str.trim());
            System.out.println(isMatch + ": " + str);
        }
    }
}
