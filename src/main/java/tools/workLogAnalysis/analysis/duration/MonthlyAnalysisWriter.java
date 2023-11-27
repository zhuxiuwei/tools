package tools.workLogAnalysis.analysis.duration;

import tools.workLogAnalysis.analysis.BaseExcelWriter;
import tools.workLogAnalysis.bean.DurationStatistics;

public class MonthlyAnalysisWriter extends BaseExcelWriter {

    @Override
    public String dateDimensionGenerator(DurationStatistics durationStatistics) {
        return durationStatistics.date.substring(0, 7);
    }
}
