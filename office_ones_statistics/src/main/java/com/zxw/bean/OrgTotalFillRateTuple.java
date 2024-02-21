package com.zxw.bean;

/**
 * 组织总体填写率
 */
public class OrgTotalFillRateTuple implements Comparable<OrgTotalFillRateTuple>{
    private String orgName;    //组织名称
    private double fillRate; //整体填写率

    public OrgTotalFillRateTuple(String orgName, double fillRate) {
        this.orgName = orgName;
        this.fillRate = fillRate;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public double getFillRate() {
        return fillRate;
    }

    public void setFillRate(double fillRate) {
        this.fillRate = fillRate;
    }

    @Override
    public int compareTo(OrgTotalFillRateTuple o) {
        if(this.orgName.equals("总体"))
            return 1;
        if (this.fillRate > o.fillRate) {
            return -1;
        } else if (this.fillRate < o.fillRate) {
            return 1;
        } else {
            return 0;
        }
    }
}
