package tools.JieSuanDanYuan.bean;

/**
 * 开销详情
 */
public class CostDetail {
    private String prod;    //产品，如blade、Celler等
    private String costItem;    //计费项，如【存储资源/高性能通用H21】
    private double cost;    //计费
    private String rdTeam;  //开发团队

    public CostDetail(String prod, String costItem, double cost, String rdTeam) {
        this.prod = prod;
        this.costItem = costItem;
        this.cost = cost;
        this.rdTeam = rdTeam;
    }

    public String getProd() {
        return prod;
    }

    public void setProd(String prod) {
        this.prod = prod;
    }

    public String getCostItem() {
        return costItem;
    }

    public void setCostItem(String costItem) {
        this.costItem = costItem;
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

    @Override
    public String toString() {
        return "CostDetail{" +
                "prod='" + prod + '\'' +
                ", costItem='" + costItem + '\'' +
                ", cost=" + cost +
                ", rdTeam='" + rdTeam + '\'' +
                '}';
    }
}
