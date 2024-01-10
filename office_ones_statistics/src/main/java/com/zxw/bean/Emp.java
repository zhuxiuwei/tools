package com.zxw.bean;

/**
 * 员工信息
 */
public class Emp {
    private String misId;
    private String orgFullPath;
    private String orgName;

    // 判断员工是否属于后端团队。通过组织结构判断
    public boolean isEmpBackendTeam(){
        boolean result = false;
        if(orgFullPath != null && orgFullPath.contains("办公效率后端")){
            result = true;
        }
//        System.out.println(this + " is 后端？ " + result);
        return result;
    }

    public Emp(String misId, String orgFullPath, String orgName) {
        this.misId = misId;
        this.orgFullPath = orgFullPath;
        this.orgName = orgName;
    }

    public String getMisId() {
        return misId;
    }

    public void setMisId(String misId) {
        this.misId = misId;
    }

    public String getOrgFullPath() {
        return orgFullPath;
    }

    public void setOrgFullPath(String orgFullPath) {
        this.orgFullPath = orgFullPath;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "misId='" + misId + '\'' +
                ", orgFullPath='" + orgFullPath + '\'' +
                ", orgName='" + orgName + '\'' +
                '}';
    }
}
