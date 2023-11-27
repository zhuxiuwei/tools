package tools.workLogAnalysis.analysis;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tools.workLogAnalysis.bean.DurationStatistics;
import tools.workLogAnalysis.bean.WorkOTStatistics;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class BaseExcelWriter {
    /**
     * 写excel。
     * @param statisticsCollection
     * @return
     */
    public void writeStatisticsToExcel(Set<DurationStatistics> statisticsCollection, String excelFileName) {
        Workbook workbook = new XSSFWorkbook();

        //数据按照日期维度，先分组
        LinkedHashMap<String, Set<DurationStatistics>> dateDimensionAndDataSetMap = new LinkedHashMap<>();
        statisticsCollection.stream().forEach(
                statistics -> {
                    String dateDimension = dateDimensionGenerator(statistics);
                    if (dateDimensionAndDataSetMap.containsKey(dateDimension)) {
                        dateDimensionAndDataSetMap.get(dateDimension).add(statistics);
                    } else {
                        Set<DurationStatistics> dataCollection = new LinkedHashSet<>();
                        dataCollection.add(statistics);
                        dateDimensionAndDataSetMap.put(dateDimension, dataCollection);
                    }
                }
        );

        /********************* 1. 时长数据。注意不包含节假日。 *********************/
        // 1.1 创建sheet
        Sheet sheet = workbook.createSheet("时长");
        // 1.2 创建表头
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("日期");
        header.createCell(1).setCellValue("项目时间");
        header.createCell(2).setCellValue("技术时间");
        header.createCell(3).setCellValue("团队时间");
        header.createCell(4).setCellValue("产品时间");
        header.createCell(5).setCellValue("自我提升时间");
        header.createCell(6).setCellValue("总时间");
        header.createCell(7).setCellValue("节假日");
        // 1.3 分组数据进行聚合计算
        Set<DurationStatistics> groupedStatisticsSet = new LinkedHashSet<>();
        dateDimensionAndDataSetMap.keySet().stream().forEach(dateDimension ->{
            DurationStatistics groupedStatistics = new DurationStatistics();
            groupedStatistics.date = dateDimension;
            Set<DurationStatistics> statisticsSet = dateDimensionAndDataSetMap.get(dateDimension);
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
        // 1.4 写入excel
        groupedStatisticsSet.stream().forEach(statistics -> {
            if(statistics.isEmpty()){
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

        /********************* 2. 加班数据。 *********************/
        // 2.1 创建sheet
        Sheet otSheet = workbook.createSheet("加班");
        // 2.2 创建表头        // 创建表头
        Row otHeader = otSheet.createRow(0);
        otHeader.createCell(0).setCellValue("时间");
        otHeader.createCell(1).setCellValue("加班次数");
        otHeader.createCell(2).setCellValue("总加班时长");
        // 2.3 分组数据进行聚合计算
        Set<WorkOTStatistics> groupedWorkOTSet = new LinkedHashSet<>();
        dateDimensionAndDataSetMap.keySet().stream().forEach(dateDimension ->{
            Set<DurationStatistics> statisticsSet = dateDimensionAndDataSetMap.get(dateDimension);
            long otCount = statisticsSet.stream().filter(x -> x.isHoliday==true).count();
            double otTime = statisticsSet.stream().filter(x -> x.isHoliday == true).mapToDouble(x -> x.totalTime).sum();
            WorkOTStatistics otStatistics = new WorkOTStatistics(dateDimension, otCount, otTime);
            groupedWorkOTSet.add(otStatistics);
        });
        // 2.4 写入excel
        groupedWorkOTSet.stream().forEach(statistics -> {
            Row row = otSheet.createRow(otSheet.getLastRowNum() + 1);
            row.createCell(0).setCellValue(statistics.date);
            row.createCell(1).setCellValue(statistics.otCount);
            row.createCell(2).setCellValue(statistics.otTime);
        });

        // 3. 写入文件
        try (FileOutputStream fileOut = new FileOutputStream(excelFileName)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //生成日期维度，用于分组。
    public abstract String dateDimensionGenerator(DurationStatistics statistics);
}
