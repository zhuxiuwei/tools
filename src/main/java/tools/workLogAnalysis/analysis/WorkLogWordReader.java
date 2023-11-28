package tools.workLogAnalysis.analysis;

import org.apache.poi.xwpf.usermodel.*;
import tools.workLogAnalysis.bean.DurationStatistics;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * 工作日志word读取解析
 */
public class WorkLogWordReader {

    private String processYear;
    private Set<DurationStatistics> result = new LinkedHashSet<>();
    public Set<DurationStatistics> readWord(String filePath){
        System.out.println("--------------处理文件：" + filePath);
        processYear = null;
        try {
            FileInputStream fis = new FileInputStream(filePath);
            XWPFDocument document = new XWPFDocument(fis);
            DurationStatistics statistics = null;

            // 遍历所有体元素
            for (IBodyElement element : document.getBodyElements()) {
                // 如果体元素是段落 -- 是日期标题
                if (element instanceof XWPFParagraph) {
                    if(statistics != null)
                        result.add(statistics);
                    XWPFParagraph paragraph = (XWPFParagraph) element;
                    String content  = paragraph.getText();
                    if ("1".equals(paragraph.getStyle())) {
                        statistics = new DurationStatistics();
                        statistics.date=getDateFromFileNameAndHeading1(filePath, content);
                        if(isHoliday(content)){
                            statistics.isHoliday = true;
                            statistics.heading = content;
                        }
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        //过滤掉empty的统计
        result.removeIf(statistics -> statistics.isEmpty());
        result.stream().filter(x -> x.isHoliday).forEach(x -> System.out.println("!!节假日:" + x));    //for debug
        return result;
    }

    //获取日志日期
    private String getDateFromFileNameAndHeading1(String filePath, String heading1Content){
        if(processYear == null) {
            File file = new File(filePath);
            String fileName = file.getName();
            processYear = fileName.substring(0, 4); //文件名前四个字符是年份
        }
        String month = heading1Content.substring(0, 2);
        String day = heading1Content.substring(2,4);
        return processYear+"-"+month+"-"+day;
    }

    //是否是休息日
    private boolean isHoliday(String dateStr){
        if(dateStr.contains("非假日") || dateStr.contains("非假期") || dateStr.contains("加班") )
            return false;
        if(dateStr.contains("假期") || dateStr.contains("国庆") || dateStr.contains("新年")
                || dateStr.contains("周末") || dateStr.contains("春节") || dateStr.contains("元旦")|| dateStr.contains("(日)") || dateStr.contains("(6)"))
            return true;
        return false;
    }

    //抽取时长
    public double getTimeDuration(String timeStr) {
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
}
