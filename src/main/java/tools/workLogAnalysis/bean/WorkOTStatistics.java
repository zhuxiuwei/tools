package tools.workLogAnalysis.bean;

/**
 * 加班时长统计
 */
public class WorkOTStatistics {
    public String date; //时间维度
    public long otCount;  //加班次数
    public double otTime;  //总加班时长

    public WorkOTStatistics(String date, long otCount, double otTime) {
        this.date = date;
        this.otCount = otCount;
        this.otTime = otTime;
    }

    public WorkOTStatistics() {
    }

    @Override
    public String toString() {
        return "WorkOTStatistics{" +
                "date='" + date + '\'' +
                ", otCount=" + otCount +
                ", otTime=" + otTime +
                '}';
    }
}
