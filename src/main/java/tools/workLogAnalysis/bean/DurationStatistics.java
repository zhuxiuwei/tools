package tools.workLogAnalysis.bean;

/**
 * 工作时长统计
 */
public class DurationStatistics {
    public String date;     //时间维度
    public double projectTime;  //项目时间
    public double techTime;  //技术时间
    public double teamTime;  //团队时间
    public double productTime;  //产品时间
    public double studyTime;  //自我时间
    public double totalTime;  //总计时间
    public double totalTime2;  //总计时间
    public boolean isHoliday;   //是否是休息日
    public String heading;  //日志日期标题

    public DurationStatistics(){}

    public boolean validateTotalTime(){
        double diff = Math.abs(getTotalTimeBySub() - totalTime);
        if(diff > 0.00001) {
            System.out.println(diff + ":" + this);
        }
        return diff > 0.00001;
    }

    public double getTotalTimeBySub(){
        return (projectTime + techTime + teamTime + productTime + studyTime) ;
    }

    public boolean isEmpty(){
        return (this.totalTime == 0 || this.getTotalTimeBySub() == 0);
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "date='" + date + '\'' +
                ", projectTime=" + projectTime +
                ", techTime=" + techTime +
                ", teamTime=" + teamTime +
                ", productTime=" + productTime +
                ", studyTime=" + studyTime +
                ", totalTime=" + totalTime +
                ", totalTime2=" + getTotalTimeBySub() +
                ", isHoliday=" + isHoliday +
                ", heading=" + heading +
                '}';
    }
}
