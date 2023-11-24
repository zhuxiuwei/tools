package tools.workLogAnalysis;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class WorkLogAnalysis {

    private static String processYear;
    public static String readWord(String filePath){
        try {
            FileInputStream fis = new FileInputStream(filePath);
            XWPFDocument document = new XWPFDocument(fis);
            Set<Statistics> result = new LinkedHashSet<>();
            Statistics statistics = null;

            // 遍历所有体元素
            for (IBodyElement element : document.getBodyElements()) {
                // 如果体元素是段落 -- 是日期标题
                if (element instanceof XWPFParagraph) {
                    if(statistics != null)
                        result.add(statistics);
                    XWPFParagraph paragraph = (XWPFParagraph) element;
                    String content  = paragraph.getText();
                    if ("1".equals(paragraph.getStyle())) {
                        statistics = new Statistics();
                        statistics.date=getDateFromFileNameAndHeading1(filePath, paragraph.getText());
                    }
                }
                // 如果体元素是表格 -- 是当天的具体日志
                else if (element instanceof XWPFTable) {
                    XWPFTable table = (XWPFTable) element;
                    for (XWPFTableRow row : table.getRows()) {
                        List<XWPFTableCell> tableCells = row.getTableCells();
                        XWPFTableCell cell = tableCells.get(0);
                        double duration = 0;
                        if(cell.getText().trim().equals("分类")){
                            continue;
                        }else{
                            if(tableCells.size() == 3) {
                                duration = getTimeDuration(tableCells.get(2).getText());
                            }
                        }
                        if(cell.getText().trim().equals("项目")){
                            statistics.projectTime += duration;
                        } else if (cell.getText().trim().equals("技术")) {
                            statistics.techTime += duration;
                        } else if (cell.getText().trim().equals("团队")) {
                            statistics.teamTime += duration;
                        } else if (cell.getText().trim().equals("产品")) {
                            statistics.productTime += duration;
                        } else if (cell.getText().trim().equals("自我提升")) {
                            statistics.studyTime += duration;
                        } else if (cell.getText().trim().equals("总计")) {
                            statistics.totalTime += duration;
                        }
                    }
                    statistics.validateTotalTime();
                }
            }
            return writeStatisticsToExcel(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    //获取日志日期
    private static String getDateFromFileNameAndHeading1(String filePath, String heading1Content){
        if(processYear == null) {
            File file = new File(filePath);
            String fileName = file.getName();
            processYear = fileName.substring(0, 4); //文件名前四个字符是年份
        }
        String month = heading1Content.substring(0, 2);
        String day = heading1Content.substring(2,4);
        return processYear+"-"+month+"-"+day;
    }

    //抽取时长
    public static double getTimeDuration(String timeStr) {
        double hours = 0.0;
        if(timeStr == null || timeStr.isEmpty())
            return hours;
        if (timeStr.contains("h")) {
            String[] parts = timeStr.split("h");
            hours += Double.parseDouble(parts[0]);
            if (parts.length > 1 && parts[1].contains("min")) {
                String minStr = parts[1].replace("min", "").trim();
                hours += Double.parseDouble(minStr) / 60;
            }
        } else if (timeStr.contains("min")) {
            String minStr = timeStr.replace("min", "").trim();
            hours = Double.parseDouble(minStr) / 60;
        } else {
            hours = Double.parseDouble(timeStr);
        }
        return hours;
    }

    public static String writeStatisticsToExcel(Collection<Statistics> statisticsCollection) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Statistics");

        // 创建表头
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("日期");
        header.createCell(1).setCellValue("项目时间");
        header.createCell(2).setCellValue("技术时间");
        header.createCell(3).setCellValue("团队时间");
        header.createCell(4).setCellValue("产品时间");
        header.createCell(5).setCellValue("自我提升时间");
        header.createCell(6).setCellValue("总时间");

        // 写入数据
        statisticsCollection.stream().forEach(statistics -> {
            if(statistics.totalTime == 0){
                System.out.println("总时长为0，跳过: " + statistics);
            }else {
                Row row = sheet.createRow(sheet.getLastRowNum() + 1);
                row.createCell(0).setCellValue(statistics.date);
                row.createCell(1).setCellValue(statistics.projectTime);
                row.createCell(2).setCellValue(statistics.techTime);
                row.createCell(3).setCellValue(statistics.teamTime);
                row.createCell(4).setCellValue(statistics.projectTime);
                row.createCell(5).setCellValue(statistics.studyTime);
                row.createCell(6).setCellValue(statistics.totalTime);
            }
        });

        // 写入文件
        String excelPath = "日志分析" + processYear + "-" + System.currentTimeMillis() + ".xlsx";
        try (FileOutputStream fileOut = new FileOutputStream(excelPath)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return excelPath;
    }

    public static void main(String[] args) {
        System.out.println("开始生成");
        String excelPath = WorkLogAnalysis.readWord("/Users/zhuxiuwei/Documents/个人/历年工作日志/2020工作日志的副本.docx");
        System.out.println("生成excel地址： " + excelPath);
    }
}
