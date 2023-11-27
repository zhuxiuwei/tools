package tools.workLogAnalysis.analysis;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tools.workLogAnalysis.bean.Statistics;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class BaseExcelWriter {
    /**
     * 日志信息写excel base class。注意不包含节假日。
     * @param statisticsCollection
     * @return
     */
    public String writeDetailStatisticsToExcel(Set<Statistics> statisticsCollection, String excelFileName) {
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
        header.createCell(7).setCellValue("节假日");

        //数据按照日期维度，先分组
        LinkedHashMap<String, Set<Statistics>> dateDimensionAndDataCollectionMap = new LinkedHashMap<>();
        statisticsCollection.stream().forEach(
            statistics -> {
                String dateDimension = dateDimensionGenerator(statistics);
                if (dateDimensionAndDataCollectionMap.containsKey(dateDimension)) {
                    dateDimensionAndDataCollectionMap.get(dateDimension).add(statistics);
                } else {
                    Set<Statistics> dataCollection = new LinkedHashSet<>();
                    dataCollection.add(statistics);
                    dateDimensionAndDataCollectionMap.put(dateDimension, dataCollection);
                }
            }
        );

        //分组数据进行聚合计算
        Set<Statistics> groupedStatisticsSet = new LinkedHashSet<>();
        dateDimensionAndDataCollectionMap.keySet().stream().forEach(dateDimension ->{
            Statistics groupedStatistics = new Statistics();
            groupedStatistics.date = dateDimension;
            Set<Statistics> statisticsSet = dateDimensionAndDataCollectionMap.get(dateDimension);
            double avgProjectTime = statisticsSet.stream().filter(x -> x.isHoliday==false).mapToDouble(x -> x.projectTime).average().orElse(0.0);
            double avgTechTime = statisticsSet.stream().filter(x -> x.isHoliday==false).mapToDouble(x -> x.techTime).average().orElse(0.0);
            double avgTeamTime = statisticsSet.stream().filter(x -> x.isHoliday==false).mapToDouble(x -> x.teamTime).average().orElse(0.0);
            double avgProductTime = statisticsSet.stream().filter(x -> x.isHoliday==false).mapToDouble(x -> x.productTime).average().orElse(0.0);
            double avgStudyTime = statisticsSet.stream().filter(x -> x.isHoliday==false).mapToDouble(x -> x.studyTime).average().orElse(0.0);
            double avgTotalTime = statisticsSet.stream().filter(x -> x.isHoliday==false).mapToDouble(x -> x.totalTime).average().orElse(0.0);
            groupedStatistics.projectTime = avgProjectTime;
            groupedStatistics.techTime = avgTechTime;
            groupedStatistics.teamTime = avgTeamTime;
            groupedStatistics.productTime = avgProductTime;
            groupedStatistics.studyTime = avgStudyTime;
            groupedStatistics.totalTime = avgTotalTime;
            groupedStatisticsSet.add(groupedStatistics);
        });

        // 写入数据
        groupedStatisticsSet.stream().forEach(statistics -> {
            if(statistics.totalTime == 0){
                System.out.println("总时长为0，跳过: " + statistics);
            }else {
                Row row = sheet.createRow(sheet.getLastRowNum() + 1);
                row.createCell(0).setCellValue(statistics.date);
                row.createCell(1).setCellValue(statistics.projectTime);
                row.createCell(2).setCellValue(statistics.techTime);
                row.createCell(3).setCellValue(statistics.teamTime);
                row.createCell(4).setCellValue(statistics.productTime);
                row.createCell(5).setCellValue(statistics.studyTime);
                row.createCell(6).setCellValue(statistics.totalTime);
                row.createCell(7).setCellValue(statistics.isHoliday);
            }
        });

        // 写入文件
        try (FileOutputStream fileOut = new FileOutputStream(excelFileName)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return excelFileName;
    }

    //生成日期维度，用于分组。
    public abstract String dateDimensionGenerator(Statistics statistics);
}
