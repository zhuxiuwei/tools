package tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tools.workLogAnalysis.WorkLogAnalysis;


public class WorkLogAnalysisTest {
    @Test
    public void testGetTimeDuration() {
        Assertions.assertTrue(WorkLogAnalysis.getTimeDuration("0.3") == 0.3);
        Assertions.assertTrue(WorkLogAnalysis.getTimeDuration("1.3h") == 1.3);
        Assertions.assertTrue(WorkLogAnalysis.getTimeDuration("15min") == 0.25);
        Assertions.assertTrue(WorkLogAnalysis.getTimeDuration("1h15min") == 1.25);
    }
}
