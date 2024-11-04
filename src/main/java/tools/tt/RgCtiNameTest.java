package tools.tt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RgCtiNameTest {

    //旧版表达式：只支持中英文（包括全角字符）、数字、下划线 ,+-（全角及汉字算两位）,长度为2-40位,中文按二位计数
    private static void oldRegTest(String str){
        /**
         * [\\w\\＿.【】\\[\\]+-[０-９]\\[\\]\u4e00-\u9fa5\uFF21-\uFF3A\uFF41-\uFF5A]：表示一个字符类，匹配以下任意一个字符：
         * \\w：字母、数字、下划线。
         * \\＿：全角下划线。
         * .【】：全角的点、方括号。
         * \\[\\]：匹配中括号。
         * +：匹配加号。
         * -：匹配减号。
         * [０-９]：匹配全角数字。
         * \u4e00-\u9fa5：匹配中文字符。
         * \uFF21-\uFF3A：匹配全角大写字母。
         * \uFF41-\uFF5A：匹配全角小写字母。
         * +：表示前面的字符类可以出现一次或多次。
         * $：表示字符串的结束。
         */
        String validatePatternOld = "^[\\w\\＿.【】\\[\\]+-[０-９]\u4e00-\u9fa5\uFF21-\uFF3A\uFF41-\uFF5A]+$";   //原版
        boolean rs;
        rs = matcher(validatePatternOld, str);
        if(!rs){
            System.out.println(str);
        }
    }

    private static void newRegTest(String str){
        //[ -~] ASCII码十进制32（代表空格）到126（~）的字符，包含英文标点符号、大小写、数字。
        String validatePatternNew = "^[\\＿【】[ -~][０-９]\u4e00-\u9fa5\uFF21-\uFF3A\uFF41-\uFF5A]+$";   //新版 https://km.sankuai.com/collabpage/2481554091
        boolean rs;
        rs = matcher(validatePatternNew, str);
        if(!rs){
            System.err.println(str);
        }
    }


    //测试旧版表达式
    public static void testOnlineRgCsv(String fileName) {
        List<String> lines = readFile(fileName);
        lines.add("zxw_rg_a1【_.[Ｂ]１２３４５６７８[]９+-.");
        lines.add("你好[]");
        for(String line : lines){
            oldRegTest(line);
        }
        System.out.println("old test done");
    }

    //测试新版表达式
    public static void testOnlineRgCsv_New(String fileName) {
        List<String> lines = readFile(fileName);
        lines.add("zxw_rg_a1【_.[Ｂ]１２３４５６７８９+-.");
        lines.add("新版_ _!__\"__#__$__%__&__'__(__)__*__,__/__:__;__<__=__>__?__@__\\__^__{__|__}__~__`new");
        lines.add("你好[]");
        for(String line : lines){
            newRegTest(line);
        }
        System.out.println("new test done");
    }

    public static List<String> readFile(String fileName) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
    private static boolean matcher(String reg, String string) {
        boolean temp;
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(string);
        temp = matcher.matches();
        return temp;
    }

    public static void main(String[] args) {
        //测试线上所有rg名称ok【截止0925】 -- 旧版表达式
        testOnlineRgCsv("/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/tt/allRgAndCti.csv");

        //新版表达式测试
        testOnlineRgCsv_New("/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/tt/allRgAndCti.csv");

    }
}
