package tools.xiaona.bookTxt2Excel.service;

import tools.xiaona.bookTxt2Excel.bean.Book;
import tools.xiaona.bookTxt2Excel.bean.Version;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * 文本处理
 */
public class TxtHandler {

    private List<String> errorLines = new ArrayList<>();    //处理过程中出错的行。最后会打印日志。

    /**
     * 输入txt文件地址，将txt转换为Book对象
     * @param txtFilePath txt文件地址
     * @return Book对象列表
     */
    public static List<Book> txt2Books(String txtFilePath) throws FileNotFoundException {
        Context context = new Context();

        Scanner sc = new Scanner(new File(txtFilePath));
        int lineNo = 0; //当前处理行
        while (sc.hasNextLine()){
            handleTxtSingleLine(context, sc.nextLine().trim(), ++lineNo);
        }

        return context.getBookData();
    }

    // 单独处理一行文本
    private static void handleTxtSingleLine(Context context, String line, int lineNo){
        TxtLineType currentTxtLineType = getCurrentTxtLineType(context, line, lineNo);
//        System.out.println(lineNo + ":" + line + ": " + currentTxtLineType);
        switch (currentTxtLineType){
            case BOOK_STARTER:
                handleBookPart(context, line, lineNo);
                break;
            case VERSION_START:
                handleVersionPart(context, line, lineNo);
                break;
            case COPY_START:
                handleVersionPart(context, line, lineNo);
                break;
        }
    }

    // 判断当前文本行的类型：TxtLineType
    private static TxtLineType getCurrentTxtLineType(Context context, String line, int lineNo){
        if(line.startsWith("ISBN:")){   //'ISBN:'开头的，是book行
            return TxtLineType.BOOK_STARTER;
        }
        else if(line.startsWith("复本号:")){   //'复本号:'开头的，是拷贝行
            return TxtLineType.COPY_START;
        }else {
            String regex = "^[a-zA-Z0-9\\-./:]+/[a-zA-Z0-9\\-./:]+$";     //只包含字母/数字/./斜杠/冒号的，则是版本行
            Pattern pattern = Pattern.compile(regex);
            if(pattern.matcher(line).matches()){
                return TxtLineType.VERSION_START;
            }
        }
        return TxtLineType.NORMAL;  //默认返回normal
    }

    //处理图书部分的信息
    private static void handleBookPart(Context context, String line, int lineNo){
        Book book = null;
        if(context.getCurrentHandlePart() != CurrentHandlePart.BOOK){   //当前处理的不是book，但是当前行是book类型，说明是一本新书的开始
            context.resetContext();
            book = new Book();
            context.setCurrentBook(book);
            context.addNewBookToBookData(book);
        }else{
            book = context.getCurrentBook();
        }
        if(line.startsWith("ISBN:")){
            line = line.replace("ISBN:", "").trim();
            book.getISBN().add(line);
        }else if(line.startsWith("作品语种:")){
            line = line.replace("作品语种:", "").trim();
            book.get作品语种().add(line);
        }else if(line.startsWith("题名:")){
            line = line.replace("题名:", "").trim();
            book.get题名().add(line);
        }else if(line.startsWith("版本:")){
            line = line.replace("版本:", "").trim();
            book.get版本().add(line);
        }else if(line.startsWith("出版信息:")){
            line = line.replace("出版信息:", "").trim();
            book.get出版信息().add(line);
        }else if(line.startsWith("稽核项:")){
            line = line.replace("稽核项:", "").trim();
            book.get稽核项().add(line);
        }else if(line.startsWith("一般注:")){
            line = line.replace("一般注:", "").trim();
            book.get一般注().add(line);
        }else if(line.startsWith("论题主题:")){
            line = line.replace("论题主题:", "").trim();
            book.get论题主题().add(line);
        }else if(line.startsWith("个人名称－等同知识责任:")){
            line = line.replace("个人名称－等同知识责任:", "").trim();
            book.get个人名称等同知识责任().add(line);
        }else {
            System.out.println("异常 - 未知的book属性！");
            System.out.println(lineNo + ": " + line);
        }
    }

    //处理版本部分的信息
    private static void handleVersionPart(Context context, String line, int lineNo){
        if(context.getCurrentHandlePart() != CurrentHandlePart.BOOK
                || context.getCurrentHandlePart() != CurrentHandlePart.COPY){
            System.out.println("异常 - 在处理版本之前，应当在处理book部分，或者copy部分!");
            System.out.println(lineNo + ": " + line);
        }
        Book book = context.getCurrentBook();
        Version version = new Version();
        version.setVersionNumber(line);
        book.getVersions().add(version);
        context.setCurrentHandlePart(CurrentHandlePart.VERSION);
    }

    //处理拷贝部分的信息
    private static void handleCopyPart(Context context, String line, int lineNo){
        if(context.getCurrentHandlePart() != CurrentHandlePart.VERSION
                || context.getCurrentHandlePart() != CurrentHandlePart.COPY){
            System.out.println("异常 - 在处理拷贝之前，应当在处理版本部分，或者copy部分!");
            System.out.println(lineNo + ": " + line);
        }
        Version version = context.getCurrentVersion();

        context.setCurrentHandlePart(CurrentHandlePart.COPY);
    }

    public static void main(String[] args) throws FileNotFoundException {
        List<Book> books = txt2Books("e:\\需要屏蔽的图书(1).txt");
    }
}
