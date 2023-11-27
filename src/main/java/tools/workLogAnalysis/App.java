package tools.workLogAnalysis;

import tools.workLogAnalysis.analysis.duration.DailyAnalysisWriter;
import tools.workLogAnalysis.analysis.duration.MonthlyAnalysisWriter;
import tools.workLogAnalysis.analysis.duration.QuarterlyAnalysisWriter;
import tools.workLogAnalysis.analysis.duration.YearlyAnalysisWriter;
import tools.workLogAnalysis.bean.DurationStatistics;

import java.util.LinkedHashSet;
import java.util.Set;

public class App {

    public static void main(String[] args) {
        System.out.println("开始生成");

        //读取word
        WorkLogWordReader workLogWordReader = new WorkLogWordReader();
        Set<DurationStatistics> rawData = new LinkedHashSet<>();
        for (int i = 2020; i <= 2023; i++) {
            rawData.addAll(workLogWordReader.readWord("/Users/zhuxiuwei/Documents/个人/历年工作日志/" + i + "工作日志的副本.docx"));
        }

        //生成结果
        DailyAnalysisWriter dailyAnalysisWriter = new DailyAnalysisWriter();
        MonthlyAnalysisWriter monthlyAnalysisWriter = new MonthlyAnalysisWriter();
        QuarterlyAnalysisWriter quarterlyAnalysisWriter = new QuarterlyAnalysisWriter();
        YearlyAnalysisWriter yearlyAnalysisWriter = new YearlyAnalysisWriter();
        String t = System.currentTimeMillis() + "";
//        dailyAnalysisWriter.writeStatisticsToExcel(rawData,"日志分析-" + t + "-day.xlsx");
        monthlyAnalysisWriter.writeStatisticsToExcel(rawData,"日志分析-" + t + "-month.xlsx");
        quarterlyAnalysisWriter.writeStatisticsToExcel(rawData, "日志分析-" + t + "-quarter.xlsx");
        yearlyAnalysisWriter.writeStatisticsToExcel(rawData, "日志分析-" + t + "-year.xlsx");
    }
}
