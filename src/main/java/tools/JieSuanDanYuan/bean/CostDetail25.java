package tools.JieSuanDanYuan.bean;

/**
 * 开销详情-25年做26规划
 */
public class CostDetail25 {
    private String prod;    //产品，如blade、Celler等
    private double cost;    //计费
    private String rdTeam;  //开发团队
    private String appKey; //appKey

    public CostDetail25(String prod, double cost, String rdTeam, String appKey) {
        this.prod = prod;
        this.cost = cost;
        this.rdTeam = rdTeam;
        this.appKey = appKey;
    }

    public String getProd() {
        return prod;
    }

    public void setProd(String prod) {
        this.prod = prod;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getRdTeam() {
        return rdTeam;
    }

    public void setRdTeam(String rdTeam) {
        this.rdTeam = rdTeam;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    @Override
    public String toString() {
        return "CostDetail25{" +
                "prod='" + prod + '\'' +
                ", cost=" + cost +
                ", rdTeam='" + rdTeam + '\'' +
                ", appKey='" + appKey + '\'' +
                '}';
    }
}
