package tools.workLogAnalysis.analysis.duration;

import tools.workLogAnalysis.analysis.BaseExcelWriter;
import tools.workLogAnalysis.bean.Statistics;

public class DailyAnalysisWriter extends BaseExcelWriter {

    @Override
    public String dateDimensionGenerator(Statistics statistics) {
        return statistics.date;
    }
}
