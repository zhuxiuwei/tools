package tools.bookTxt2Excel.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tools.bookTxt2Excel.bean.BookWithStingFields;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * javaBeans转excel
 */
public class ExcelCreator {

    /**
     * 将图书信息生成excel
     * @param books 待生成excel的图书信息
     * @param convertConfig 转换配置
     */
    public void crateExcelFromBook(java.util.List<BookWithStingFields> books, ConvertConfig convertConfig){
        //1. 根据要打印的Book对象，生成Map
        ArrayList<Map<String, String>> resultList =  new ArrayList<>();
        for(BookWithStingFields book: books) {
            createResultMapList(book, convertConfig, resultList, new HashMap<>());
        }
//        System.out.println(resultList);  //for debug

        //2. 根据map，生成excel
        java.util.List<String> excelHeaders = new ArrayList<>();    //生成headers
        for(String className: convertConfig.convertClassAndFields.keySet()) {
            List<String> fields = new ArrayList<>(convertConfig.convertClassAndFields.get(className));
            if(convertConfig.excludedClassFieldsInExcel.get(className) != null)
                fields.removeAll(convertConfig.excludedClassFieldsInExcel.get(className));  //将「excludedClassFieldsInExcel」配置项里的header都移除
            excelHeaders.addAll(fields);
        }
        saveMapToExcel(excelHeaders, resultList, convertConfig.getTargetExcelPath());   //写excel
    }

    /**
     * 根据要打印的Book对象，生成Map
     * @param objectToPrint 待转换的对象，可以是Book，Copy，Version
     * @param convertConfig 转换配置
     * @param resultList 最终生成的结果List，里面包含的都是map<字段名，字段值>
     * @param currentMap 用于保存中间递归结果
     */
    public void createResultMapList(Object objectToPrint,
                                    ConvertConfig convertConfig,
                                    java.util.List<Map<String, String>> resultList,
                                    Map<String, String> currentMap){
        if(convertConfig.convertClassAndFields.containsKey(objectToPrint.getClass().getName())){
            java.util.List<String> fieldsToPrint = convertConfig.convertClassAndFields.get(objectToPrint.getClass().getName());
            Map<String, String> currentMapCopy = new HashMap<>(currentMap);
            for (String fieldName: fieldsToPrint){
                try {
                    Field field = objectToPrint.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    try {
                        Object fieldValue = field.get(objectToPrint);
                        if(fieldValue == null){  //值为null
                            currentMapCopy.put(fieldName, "");  //放入空字符串
                        }else if(fieldValue instanceof java.util.List){  //值是List
                            for(Object singleValue: (java.util.List)fieldValue){
                                if(!convertConfig.convertClassAndFields.containsKey(singleValue.getClass().getName())){
                                    System.err.println("不支持的数据类型： " + objectToPrint.getClass());
                                    System.exit(0);
                                }else {
                                    //递归打印子对象
                                    createResultMapList(singleValue, convertConfig, resultList, currentMapCopy);
                                }
                            }
                        }else if(fieldValue.getClass().equals(String.class)){    //值是字符串
                            currentMapCopy.put(fieldName, fieldValue.toString());
                        }else { //对于其他数据类型，简单粗暴直接toString。算是个兜底逻辑。
                            currentMapCopy.put(fieldName, fieldValue.toString());
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                } catch (NoSuchFieldException e) {
                    System.err.println("异常：field不存在！");
                    System.exit(0);
                }
            }
            if(convertConfig.bottomConvertClass == objectToPrint.getClass()){   //转化到了最底层，则需要把结果加到resultList里。
                resultList.add(currentMapCopy);
            }
        }else {
            System.err.println("异常：打印对象不在配置文件中！");
            System.exit(0);
        }
    }

    /**
     * 将数据写入excel
     * @param excelHeaders excel表头
     * @param data 数据，是个list。list里每条数据是个map，其Key是表头，value是值。
     * @param excelPath excel保存目录
     */
    public void saveMapToExcel(java.util.List<String> excelHeaders, java.util.List<Map<String, String>> data, String excelPath){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < excelHeaders.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(excelHeaders.get(i));
        }

        // 填充数据
        for (int i = 0; i < data.size(); i++) {
            Map<String, String> rowData = data.get(i);
            Row dataRow = sheet.createRow(i + 1);
            int j = 0;
            for (String header : excelHeaders) {
                Cell cell = dataRow.createCell(j);
                cell.setCellValue(rowData.get(header));
                j++;
            }
        }

        // 保存Excel文件
        try (FileOutputStream outputStream = new FileOutputStream(excelPath + File.separator
                + "图书txt信息转excel" + System.currentTimeMillis() + ".xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
