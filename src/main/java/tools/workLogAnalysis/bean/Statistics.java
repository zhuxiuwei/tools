package tools.workLogAnalysis.bean;

public class Statistics {
    public String date;
    public double projectTime;  //项目时间
    public double techTime;  //技术时间
    public double teamTime;  //团队时间
    public double productTime;  //产品时间
    public double studyTime;  //自我时间
    public double totalTime;  //总计时间
    public double totalTime2;  //总计时间
    public boolean isHoliday;   //是否是休息日

    public Statistics(){}
    public Statistics(String date, double projectTime, double techTime, double teamTime, double productTime, double studyTime, double totalTime, boolean isHoliday) {
        this.date = date;
        this.projectTime = projectTime;
        this.techTime = techTime;
        this.teamTime = teamTime;
        this.productTime = productTime;
        this.studyTime = studyTime;
        this.totalTime = totalTime;
        this.isHoliday = isHoliday;
    }

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
                ", totalTime2=" + totalTime2 +
                ", isHoliday=" + isHoliday +
                '}';
    }
}
