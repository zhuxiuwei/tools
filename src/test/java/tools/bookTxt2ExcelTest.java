package tools;

import com.sun.tools.javac.util.List;
import org.junit.jupiter.api.Test;
import tools.xiaona.bookTxt2Excel.bean.*;
import tools.xiaona.bookTxt2Excel.utils.ConvertConfig;
import tools.xiaona.bookTxt2Excel.utils.ExcelCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class bookTxt2ExcelTest {

    @Test
    public void testPrintCertainFields() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
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
    public void testBookWithStingFields() throws IllegalAccessException, NoSuchFieldException {
        BookWithStingFields book = mockBook();
        System.out.println(book);
    }

    @Test
    public void test子类反射判断(){
        //java子类与父类的判断(instanceof与isAssignableFrom) https://blog.csdn.net/weixin_44728369/article/details/120564227
        com.sun.tools.javac.util.List l1 = List.of("a", "b");
        java.util.List l2 = new ArrayList();

        //用isAssignableFrom判断类是其他类的同类或父类
        assertTrue(java.util.List.class.isAssignableFrom(l1.getClass()) == true);
        assertTrue(java.util.List.class.isAssignableFrom(l2.getClass()) == true);

        //用instanceOf判断类是其他类的子类或父类
        assertTrue((l1 instanceof java.util.List) == true);
        assertTrue((l2 instanceof java.util.List) == true);
    }

    private ConvertConfig mockConfig() throws ClassNotFoundException {
        Map<String, java.util.List<String>> convertClassAndFieldsMap = new HashMap<>();
        convertClassAndFieldsMap.put("tools.xiaona.bookTxt2Excel.bean.BookWithStingFields", List.of("ISBN", "题名", "versions"));
        convertClassAndFieldsMap.put("tools.xiaona.bookTxt2Excel.bean.Version", List.of("versionNumber", "copies"));
        convertClassAndFieldsMap.put("tools.xiaona.bookTxt2Excel.bean.Copy", List.of("复本号","标识","价格"));
        return new ConvertConfig(convertClassAndFieldsMap,
                (Class<ConvertableToExcel>) Class.forName("tools.xiaona.bookTxt2Excel.bean.Copy"));
    }

    private BookWithStingFields mockBook() throws NoSuchFieldException, IllegalAccessException {
        Book book = new Book();
        book.setISBN(List.of("isbn1","isbn2"));
        book.set题名(List.of("西游记"));
        book.set个人主题(List.of("名著","神话"));

        Version v1 = new Version();
        v1.setVersionNumber("v1");

        Version v2 = new Version();
        v2.setVersionNumber("v2");

        book.setVersions(List.of(v1, v2));

        Copy copy1_1 = new Copy();
        copy1_1.set复本号("副本1_1");
        copy1_1.set价格("20");
        copy1_1.set馆藏期限("永久");

        v1.setCopies(List.of(copy1_1));

        Copy copy2_1 = new Copy();
        copy2_1.set复本号("副本2_1");
        copy2_1.set价格("20");
        copy2_1.set馆藏期限("永久");

        Copy copy2_2 = new Copy();
        copy2_2.set复本号("副本2_2");
        copy2_2.set价格("40");
        copy2_2.set馆藏期限("永久");

        v2.setCopies(List.of(copy2_1, copy2_2));

        return new BookWithStingFields(book);
    }

}
