package tools.bookTxt2Excel.service;

import tools.bookTxt2Excel.bean.Book;
import tools.bookTxt2Excel.bean.BookWithStingFields;
import tools.bookTxt2Excel.bean.Copy;
import tools.bookTxt2Excel.bean.Version;
import tools.bookTxt2Excel.config.ConvertConfig;
import tools.bookTxt2Excel.config.YamlConfigParser;
import tools.bookTxt2Excel.service.enums.CurrentHandlePart;
import tools.bookTxt2Excel.service.enums.TxtLineType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 文本处理，将文本转换为javabean
 */
public class TxtToJavaBean {

    private ConvertConfig convertConfig;
    private int debugLine=700248;   //用于Debug的行

    public TxtToJavaBean(ConvertConfig convertConfig){
        this.convertConfig = convertConfig;
    }

    /**
     * 输入txt文件地址，将txt转换为Book对象
     * @return Book对象列表
     */
    public List<BookWithStingFields> txt2Books() throws IOException, ClassNotFoundException {
        Context context = new Context();
        //保存<行号:原文> map到context，然后处理原文。
        Map<Integer, String> rawContentMap = originBookContentMap();
        context.setOriginBookContentMap(rawContentMap);
        for (int i = 1; i < rawContentMap.size(); i++) {
            handleTxtSingleLine(context, rawContentMap.get(i), i);  //处理原文的每一行
        }
        //context.getBookData().forEach(x -> System.out.println(x));   //for debug
        //Book转BookWithStingFields
        List<Book> books = context.getBookData();
        List<BookWithStingFields> res = new ArrayList<>();
        ConvertConfig convertConfig = YamlConfigParser.parseConfig("tools/BookTxt2ExcelConfig.yaml");
        String delimiter = convertConfig.getCombineMultipleValuesToOneValueDelimiter();
        books.forEach(book -> {
            try {
                BookWithStingFields bookWithStingFields = new BookWithStingFields(book, delimiter);
                res.add(bookWithStingFields);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        //res.forEach(x -> System.out.println(x));   //for debug
        return res;
    }

    // 单独处理一行文本
    private void handleTxtSingleLine(Context context, String line, int lineNo){
        if(isInvalidText(line))
            return;
        if(lineNo == debugLine){  //for debug
            System.out.println("");
        }
        TxtLineType currentTxtLineType = getCurrentTxtLineType(context, line, lineNo);
//        System.out.println(lineNo + ":" + line + ": " + currentTxtLineType);  //for debug
        switch (currentTxtLineType){
            case BOOK_STARTER:
                handleBookStart(context, line, lineNo);
                break;
            case VERSION_START:
                handleVersionStart(context, line, lineNo);
                break;
            case COPY_START:
                handleCopyStart(context, line, lineNo);
                break;
            case NORMAL:
                handleNormalText(context, line, lineNo);
                break;
        }
    }

    //txt原文
    private Map<Integer, String> originBookContentMap(){
        Map<Integer, String> res = new HashMap<>();
        Scanner sc = null;
        int lineNo = 1;
        try {
            sc = new Scanner(new File(convertConfig.sourceTxtFilePath));
        } catch (FileNotFoundException e) {
            System.err.println("错误！文件不存在：" + convertConfig.sourceTxtFilePath);
            System.err.println("程序异常退出");
            System.exit(0);
        }
        while (sc.hasNextLine()){
            String line = sc.nextLine().trim();
            res.put(lineNo++, line);
        }
        return res;
    }

    //过滤掉无效文本
    private boolean isInvalidText(String text){
        if(text == null || text.isEmpty()){
            return true;
        }
        if(text.equals("书目")){
            return true;
        }
        if(text.equals("Item List")){
            return true;
        }
        if(text.equals("文献清单")){
            return true;
        }
        if(text.startsWith("产生的 星期")){
            return  true;
        }
        if(text.startsWith("产生的 周")){
            return  true;
        }
        return false;
    }

    // 判断当前文本行的类型：TxtLineType
    private TxtLineType getCurrentTxtLineType(Context context, String line, int lineNo){
        if(line.startsWith("ISBN:")){   //'ISBN:'开头的，是book行
            return TxtLineType.BOOK_STARTER;
        }
        else if(line.startsWith("复本号:")){   //'复本号:'开头的，是拷贝行
            return TxtLineType.COPY_START;
        } else if ((line.startsWith("题名:") || line.startsWith("个人名称－等同知识责任:"))
                && (context.getCurrentHandlePart() == CurrentHandlePart.COPY || context.getCurrentHandlePart() == CurrentHandlePart.NONE)) {
            /**
             * Book ISBN丢失的错误情况。
             * 注意这里有个假定逻辑：一本书的开头部分，如果ISBN丢了，则以【题名】或者【个人名称－等同知识责任】开头。
             * ！！！这里每加一种属性，记得需要对应修改handleBookStart方法！！！！ 比较ugly的逻辑！！！！
             * TODO: 如果后续有有更多其他情况，这里还是会处理错误。需要更健壮的写法。
             * 这里的错误，指的是类似下面的报错：把本属于Book的行，归纳成属于copy了。
             * '异常 - 未知的copy属性！ 行1172: 个人名称－等同知识责任: Mao, Tse-Tung, 1893-1976.'
             */
            return TxtLineType.BOOK_STARTER;
        } else {    //版本号
            /** 以下正则逻辑有问题 231113，改成判断下一行是不是'复本号:'
            String regex = "^[a-zA-Z0-9\\-./:\\s()=]+/[a-zA-Z0-9\\-./:\\s()=]+$";     //只包含字母/数字/./斜杠/冒号的，则是版本行
            Pattern pattern = Pattern.compile(regex);
            if(pattern.matcher(line).matches()){
//                System.out.println(line);
                return TxtLineType.VERSION_START;
            }
            */
            String nextLine = getNextValidLine(context, lineNo);
            if(nextLine.trim().startsWith("复本号:")) {    //假设'副本号:x'的前一行，都是版本号。
//                System.out.println(line);
                return TxtLineType.VERSION_START;
            }
        }
        return TxtLineType.NORMAL;  //默认返回normal
    }

    //获取原文里有效的下一行文本。如果没有，返回空字符串。
    private String getNextValidLine(Context context, int lineNo){
        Map<Integer, String> originBookContentMap = context.getOriginBookContentMap();
        while (lineNo < originBookContentMap.size()) {
            lineNo++;
            String nextLine = originBookContentMap.get(lineNo).trim();
            if (!isInvalidText(nextLine)) {
                return nextLine;
            }
        }
        return "";
    }

    //处理图书START文本
    private void handleBookStart(Context context, String line, int lineNo){
        Book book = null;
        if(context.getCurrentHandlePart() != CurrentHandlePart.BOOK){   //当前处理的不是book，但是当前行是book类型，说明是一本新书的开始
            context.resetContext();
            book = new Book();
            context.setCurrentBook(book);
            context.getBookData().add(book);
        }else{
            book = context.getCurrentBook();
        }

        //处理一行书开始的ISBN,或者题名。 TODO: 代码比较ugly和定制化，后续如果还有特殊情况，需要优化
        if(line.startsWith("ISBN:")){
            line = line.replace("ISBN:", "").trim();
            book.getISBN().add(line);
            context.setLastHandleBookFiled("ISBN");
        }else if(line.startsWith("题名:")){
            line = line.replace("题名:", "").trim();
            book.get题名().add(line);
            context.setLastHandleBookFiled("题名");
        }else if(line.startsWith("个人名称－等同知识责任:")){
            line = line.replace("个人名称－等同知识责任:", "").trim();
            book.get个人名称等同知识责任().add(line);
            context.setLastHandleBookFiled("个人名称等同知识责任");
        }
        context.setCurrentHandlePart(CurrentHandlePart.BOOK);
    }

    //处理版本START
    private void handleVersionStart(Context context, String line, int lineNo){
        if(!(context.getCurrentHandlePart() == CurrentHandlePart.BOOK
                || context.getCurrentHandlePart() == CurrentHandlePart.COPY)){
            System.err.println("异常 - 在处理版本之前，应当在处理book部分，或者copy部分!");
            System.err.println("行" + lineNo + ": " + line + "\n");
        }
        Book book = context.getCurrentBook();
        Version version = new Version();
        version.setVersionNumber(line);
        book.getVersions().add(version);
        context.setCurrentHandlePart(CurrentHandlePart.VERSION);
        context.setCurrentVersion(version);
    }

    //处理拷贝Start
    private void handleCopyStart(Context context, String line, int lineNo){
        if(!(context.getCurrentHandlePart() == CurrentHandlePart.VERSION
                || context.getCurrentHandlePart() == CurrentHandlePart.COPY)){
            System.err.println("异常 - 在处理拷贝之前，应当在处理版本部分，或者copy部分!");
            System.err.println("行" + lineNo + ": " + line + "\n");
        }
        Version version = context.getCurrentVersion();

        //version号没有识别出来的特殊情况！！！
        if(version == null){
            version = new Version();
            version.setVersionNumber(convertConfig.versionNumberWhenFailedToParse);     //用配置文件里的默认值设置
            Book book = context.getCurrentBook();
            book.getVersions().add(version);
            context.setCurrentHandlePart(CurrentHandlePart.VERSION);
            context.setCurrentVersion(version);
        }

        Copy copy = new Copy();
        version.getCopies().add(copy);
        context.setCurrentHandlePart(CurrentHandlePart.COPY);
        context.setCurrentCopy(copy);
        String[] lines = line.split(" ");
        for(String l: lines){
            if(l.startsWith("复本号:")) {
                l = l.replace("复本号:", "").trim();
                copy.set复本号(l);
            }else if(l.startsWith("标识:")) {
                l = l.replace("标识:", "").trim();
                copy.set标识(l);
            }else if(l.startsWith("馆别:")) {
                l = l.replace("馆别:", "").trim();
                copy.set馆别(l);
            }
        }
    }

    // 处理正常文本 TODO: 大量相似代码片段，如果频繁需求变更需要优化
    private void handleNormalText(Context context, String line, int lineNo){
        switch (context.getCurrentHandlePart()){
            case VERSION:
                break;
            case BOOK:
                Book book = context.getCurrentBook();
                if(line.startsWith("ISBN:")){
                    line = line.replace("ISBN:", "").trim();
                    book.getISBN().add(line);
                    context.setLastHandleBookFiled("ISBN");
                }else if(line.startsWith("作品语种:")){
                    line = line.replace("作品语种:", "").trim();
                    book.get作品语种().add(line);
                    context.setLastHandleBookFiled("作品语种");
                }else if(line.startsWith("题名:")){
                    line = line.replace("题名:", "").trim();
                    book.get题名().add(line);
                    context.setLastHandleBookFiled("题名");
                }else if(line.startsWith("版本:")){
                    line = line.replace("版本:", "").trim();
                    book.get版本().add(line);
                    context.setLastHandleBookFiled("版本");
                }else if(line.startsWith("出版信息:")){
                    line = line.replace("出版信息:", "").trim();
                    book.get出版信息().add(line);
                    context.setLastHandleBookFiled("出版信息");
                }else if(line.startsWith("稽核项:")){
                    line = line.replace("稽核项:", "").trim();
                    book.get稽核项().add(line);
                    context.setLastHandleBookFiled("稽核项");
                }else if(line.startsWith("一般注:")){
                    line = line.replace("一般注:", "").trim();
                    book.get一般注().add(line);
                    context.setLastHandleBookFiled("一般注");
                }else if(line.startsWith("论题主题:")){
                    line = line.replace("论题主题:", "").trim();
                    book.get论题主题().add(line);
                    context.setLastHandleBookFiled("论题主题");
                }else if(line.startsWith("个人名称－等同知识责任:")){
                    line = line.replace("个人名称－等同知识责任:", "").trim();
                    book.get个人名称等同知识责任().add(line);
                    context.setLastHandleBookFiled("个人名称等同知识责任");
                }else if(line.startsWith("丛书:")){
                    line = line.replace("丛书:", "").trim();
                    book.get丛书().add(line);
                    context.setLastHandleBookFiled("丛书");
                }else if(line.startsWith("目次备注:")){
                    line = line.replace("目次备注:", "").trim();
                    book.get目次备注().add(line);
                    context.setLastHandleBookFiled("目次备注");
                }else if(line.startsWith("个人主题:")){
                    line = line.replace("个人主题:", "").trim();
                    book.get个人主题().add(line);
                    context.setLastHandleBookFiled("个人主题");
                }else if(line.startsWith("概要:")){
                    line = line.replace("概要:", "").trim();
                    book.get概要().add(line);
                    context.setLastHandleBookFiled("概要");
                }else if(line.startsWith("概要:")){
                    line = line.replace("概要:", "").trim();
                    book.get概要().add(line);
                    context.setLastHandleBookFiled("概要");
                }else if(line.startsWith("变异题名:")){
                    line = line.replace("变异题名:", "").trim();
                    book.get变异题名().add(line);
                    context.setLastHandleBookFiled("变异题名");
                }else if(line.startsWith("题名主题:")){
                    line = line.replace("题名主题:", "").trim();
                    book.get题名主题().add(line);
                    context.setLastHandleBookFiled("题名主题");
                }else {
                    if(context.getLastHandleBookFiled() != null){   //处理txt里book属性被换行的情况
                        Book currentBook = context.getCurrentBook();
                        line = " " + line;  //前面补一个空格，模拟换行前实际有个空格
                        switch (context.getLastHandleBookFiled()){
                            case "ISBN":
                                currentBook.getISBN().set(currentBook.getISBN().size() - 1, currentBook.getISBN().get((currentBook.getISBN().size() - 1)) + line);
                                break;
                            case "作品语种":
                                currentBook.get作品语种().set(currentBook.get作品语种().size() - 1, currentBook.get作品语种().get((currentBook.get作品语种().size() - 1)) + line);
                                break;
                            case "题名":
                                currentBook.get题名().set(currentBook.get题名().size() - 1, currentBook.get题名().get((currentBook.get题名().size() - 1)) + line);
                                break;
                            case "版本":
                                currentBook.get版本().set(currentBook.get版本().size() - 1, currentBook.get版本().get((currentBook.get版本().size() - 1)) + line);
                                break;
                            case "出版信息":
                                currentBook.get出版信息().set(currentBook.get出版信息().size() - 1, currentBook.get出版信息().get((currentBook.get出版信息().size() - 1)) + line);
                                break;
                            case "稽核项":
                                currentBook.get稽核项().set(currentBook.get稽核项().size() - 1, currentBook.get稽核项().get((currentBook.get稽核项().size() - 1)) + line);
                                break;
                            case "一般注":
                                currentBook.get一般注().set(currentBook.get一般注().size() - 1, currentBook.get一般注().get((currentBook.get一般注().size() - 1)) + line);
                                break;
                            case "论题主题":
                                currentBook.get论题主题().set(currentBook.get论题主题().size() - 1, currentBook.get论题主题().get((currentBook.get论题主题().size() - 1)) + line);
                                break;
                            case "个人名称等同知识责任":
                                currentBook.get个人名称等同知识责任().set(currentBook.get个人名称等同知识责任().size() - 1, currentBook.get个人名称等同知识责任().get((currentBook.get个人名称等同知识责任().size() - 1)) + line);
                                break;
                            case "丛书":
                                currentBook.get丛书().set(currentBook.get丛书().size() - 1, currentBook.get丛书().get((currentBook.get丛书().size() - 1)) + line);
                                break;
                            case "目次备注":
                                currentBook.get目次备注().set(currentBook.get目次备注().size() - 1, currentBook.get目次备注().get((currentBook.get目次备注().size() - 1)) + line);
                                break;
                            case "概要":
                                currentBook.get概要().set(currentBook.get概要().size() - 1, currentBook.get概要().get((currentBook.get概要().size() - 1)) + line);
                                break;
                            case "个人主题":
                                currentBook.get个人主题().set(currentBook.get个人主题().size() - 1, currentBook.get个人主题().get((currentBook.get个人主题().size() - 1)) + line);
                                break;
                            case "变异题名":
                                currentBook.get变异题名().set(currentBook.get变异题名().size() - 1, currentBook.get变异题名().get((currentBook.get变异题名().size() - 1)) + line);
                                break;
                            case "题名主题":
                                currentBook.get题名主题().set(currentBook.get题名主题().size() - 1, currentBook.get题名主题().get((currentBook.get题名主题().size() - 1)) + line);
                                break;
                            default:
                                System.err.println("异常 - 未知的book属性！");
                                System.err.println("行" + lineNo + ": " + line + "\n");
                        }
                    }else {
                        System.err.println("异常 - 未知的book属性！");
                        System.err.println("行" + lineNo + ": " + line + "\n");
                    }
                }
                break;
            case COPY:
                String[] lines = line.split(" ");
                Copy copy = context.getCurrentCopy();
                for(String l: lines){
                    l = l.trim();
                    if(l.isEmpty())
                        continue;
                    if(l.startsWith("价格:")) {
                        l = l.replace("价格:", "").trim();
                        copy.set价格(l);
                    }else if(l.startsWith("文献类别1:")) {
                        l = l.replace("文献类别1:", "").trim();
                        copy.set文献类别1(l);
                    }else if(l.startsWith("文献类别2:")) {
                        l = l.replace("文献类别2:", "").trim();
                        copy.set文献类别2(l);
                    }else if(l.startsWith("类型:")) {
                        l = l.replace("类型:", "").trim();
                        copy.set类型(l);
                    }else if(l.startsWith("永久馆藏位置:")) {
                        l = l.replace("永久馆藏位置:", "").trim();
                        copy.set永久馆藏位置(l);
                    }else if(l.startsWith("已新增:")) {
                        l = l.replace("已新增:", "").trim();
                        copy.set已新增(l);
                    }else if(l.startsWith("目前馆址:")) {
                        l = l.replace("目前馆址:", "").trim();
                        copy.set目前馆址(l);
                    }else if(l.startsWith("件:")) {
                        l = l.replace("件:", "").trim();
                        copy.set件(l);
                    }else if(l.startsWith("cat3:")) {
                        l = l.replace("cat3:", "").trim();
                        copy.setCat3(l);
                    }else if(l.startsWith("cat4:")) {
                        l = l.replace("cat4:", "").trim();
                        copy.setCat4(l);
                    }else if(l.startsWith("cat5:")) {
                        l = l.replace("cat5:", "").trim();
                        copy.setCat5(l);
                    }else if(l.startsWith("永久的")) {
                        copy.set馆藏期限("永久的");
                    }else if(l.startsWith("临时馆藏")) {
                        copy.set馆藏期限("临时馆藏");
                    }else {
                        System.err.println("异常 - 未知的copy属性！");
                        System.err.println("行" + lineNo + ": " + line + "\n");
                    }
                }
        }
    }
}
