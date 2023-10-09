package tools.bookTxt2Excel;

import tools.bookTxt2Excel.bean.BookWithStingFields;
import tools.bookTxt2Excel.service.TxtToJavaBean;
import tools.bookTxt2Excel.utils.ConvertConfig;
import tools.bookTxt2Excel.utils.ExcelCreator;
import tools.bookTxt2Excel.utils.YamlConfigParser;

import java.io.IOException;
import java.util.List;

/**
 * 主程序入口
 */
public class App {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //读取配置文件
        ConvertConfig convertConfig = YamlConfigParser.parseConfig("tools/BookTxt2ExcelConfig.yaml");

        //1. 解析txt文本成结构化的javaBean
        List<BookWithStingFields> books = TxtToJavaBean.txt2Books(convertConfig.getSourceTxtFilePath());

        //2. javabean生成excel
        new ExcelCreator().crateExcelFromBook(books, convertConfig);
    }
}
