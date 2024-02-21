package com.zxw.bean;

/**
 * 组织维度的需求填写完整度信息
 */
public class OrgLevelStatistics {
    public String orgName;    //组织名称
    public String fieldName;    //字段名
    public int shouldFillCount;  //应该填写数
    public int actualFillCount;  //实际填写数
    public double fillRate;  //填写率，实际填写数除以应该填写数

    @Override
    public String toString() {
        return "OrgLevelStatistics{" +
                "orgName='" + orgName + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", shouldFillCount=" + shouldFillCount +
                ", actualFillCount=" + actualFillCount +
                ", fillRate=" + fillRate +
                '}';
    }
}