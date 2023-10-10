package tools.bookTxt2Excel;

import tools.bookTxt2Excel.bean.BookWithStingFields;
import tools.bookTxt2Excel.service.TxtToJavaBean;
import tools.bookTxt2Excel.config.ConvertConfig;
import tools.bookTxt2Excel.service.ExcelCreator;
import tools.bookTxt2Excel.config.YamlConfigParser;

import java.io.IOException;
import java.util.List;

/**
 * 主程序入口
 */
public class App {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("生成excel中....");
        //配置文件路径，要么通过args传入，如果不传入用项目内的默认配置文件tools/BookTxt2ExcelConfig.yaml。
        String configPath = args.length == 1 ? args[0] : "tools/BookTxt2ExcelConfig.yaml";

        //读取配置文件
        System.out.println("读取配置文件：" + configPath);
        ConvertConfig convertConfig = YamlConfigParser.parseConfig(configPath);
        System.out.println("\n==============配置信息==============");
        System.out.println(convertConfig);
        System.out.println("==============配置信息==============\n");

        //1. 解析txt文本成结构化的javaBean
        List<BookWithStingFields> books = TxtToJavaBean.txt2Books(convertConfig.getSourceTxtFilePath());

        //2. javabean生成excel
        String excelPath = new ExcelCreator().crateExcelFromBook(books, convertConfig);
        System.out.println("生成excel成功，文件路径：" + excelPath);
    }
}
