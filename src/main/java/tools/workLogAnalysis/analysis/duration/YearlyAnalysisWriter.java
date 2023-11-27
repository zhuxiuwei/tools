package tools.workLogAnalysis.analysis.duration;

import tools.workLogAnalysis.analysis.BaseExcelWriter;
import tools.workLogAnalysis.bean.Statistics;

public class YearlyAnalysisWriter extends BaseExcelWriter {

    @Override
    public String dateDimensionGenerator(Statistics statistics) {
        String year = statistics.date.substring(0, 4);
        return year;
    }
}
