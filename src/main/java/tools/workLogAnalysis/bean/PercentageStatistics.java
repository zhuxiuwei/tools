package tools.workLogAnalysis.bean;

/**
 * 工作时长统计
 */
public class PercentageStatistics {
    public String date;     //时间维度
    public double projectTimePart;  //项目时间占比
    public double techTimePart;  //技术时间占比
    public double teamTimePart;  //团队时间占比
    public double productTimePart;  //产品时间占比
    public double studyTimePart;  //学习时间占比

    public PercentageStatistics(DurationStatistics durationStatistics){
        this.date = durationStatistics.date;
        this.projectTimePart = durationStatistics.projectTime/durationStatistics.getTotalTimeBySub()*100;
        this.techTimePart = durationStatistics.techTime/durationStatistics.getTotalTimeBySub()*100;
        this.teamTimePart = durationStatistics.teamTime/durationStatistics.getTotalTimeBySub()*100;
        this.productTimePart = durationStatistics.productTime/durationStatistics.getTotalTimeBySub()*100;
        this.studyTimePart = durationStatistics.studyTime/durationStatistics.getTotalTimeBySub()*100;
    }

    @Override
    public String toString() {
        return "PercentageStatistics{" +
                "date='" + date + '\'' +
                ", projectTimePart=" + projectTimePart +
                ", techTimePart=" + techTimePart +
                ", teamTimePart=" + teamTimePart +
                ", productTimePart=" + productTimePart +
                ", studyTimePart=" + studyTimePart +
                '}';
    }
}
