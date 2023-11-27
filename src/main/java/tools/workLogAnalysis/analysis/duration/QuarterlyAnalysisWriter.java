package tools.workLogAnalysis.analysis.duration;

import tools.workLogAnalysis.analysis.BaseExcelWriter;
import tools.workLogAnalysis.bean.Statistics;

public class QuarterlyAnalysisWriter extends BaseExcelWriter {

    @Override
    public String dateDimensionGenerator(Statistics statistics) {
        String year = statistics.date.substring(0, 4);
        String month = statistics.date.substring(5, 7);
        switch (month){
            case "01":
            case "02":
            case "03":
                return year + "Q1";
            case "04":
            case "05":
            case "06":
                return year + "Q2";
            case "07":
            case "08":
            case "09":
                return year + "Q3";
            case "10":
            case "11":
            case "12":
                return year + "Q4";
            default:
                return "ERROR!!!";
        }
    }
}
