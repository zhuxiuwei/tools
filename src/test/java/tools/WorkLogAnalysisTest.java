package tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tools.workLogAnalysis.analysis.WorkLogWordReader;


public class WorkLogAnalysisTest {
    @Test
    public void testGetTimeDuration() {
        WorkLogWordReader workLogAnalysis = new WorkLogWordReader();
        Assertions.assertTrue(workLogAnalysis.getTimeDuration("0.3") == 0.3);
        Assertions.assertTrue(workLogAnalysis.getTimeDuration("1.3h") == 1.3);
        Assertions.assertTrue(workLogAnalysis.getTimeDuration("15min") == 0.25);
        Assertions.assertTrue(workLogAnalysis.getTimeDuration("1h15min") == 1.25);
    }
}
