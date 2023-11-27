package tools.workLogAnalysis.analysis.duration;

import tools.workLogAnalysis.analysis.BaseExcelWriter;
import tools.workLogAnalysis.bean.DurationStatistics;

public class YearlyAnalysisWriter extends BaseExcelWriter {

    @Override
    public String dateDimensionGenerator(DurationStatistics durationStatistics) {
        String year = durationStatistics.date.substring(0, 4);
        return year;
    }
}
