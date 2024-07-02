package com.zxw.bean;

import com.zxw.service.Rules;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * ONES需求 -- 被分析过的
 */
public class OnesReqAnalyzed implements Comparable<OnesReqAnalyzed>{
    public String reqId;
    public String reqName;
    public String state;
    public String subtypeName;
    public String spaceId;
    public String spaceName;
    public String createdBy;
    public String assigned;
    public String createdAt;

    public String 开发人员多选;

    public Map<String, CellValueWithStyle> cellValues = new HashMap<>();
    public Map<String, Emp> emps = new HashMap<>();

    public OnesReqAnalyzed(OnesReq onesReq, Map<String, Emp> emps){
        this.reqId = onesReq.getReqId();
        this.reqName = onesReq.getReqName();
        this.state = onesReq.getState();
        this.subtypeName = onesReq.getSubtypeName();
        this.spaceId = onesReq.getSpaceId();
        this.spaceName = onesReq.getSpaceName();
        this.createdBy = onesReq.getCreatedBy();
        this.assigned = onesReq.getAssigned();
        this.createdAt = onesReq.getCreatedAt();
        this.开发人员多选 = onesReq.get开发人员多选();
        cellValues.put("提出时间", new CellValueWithStyle("提出时间", onesReq.get提出时间()));
        cellValues.put("需求澄清日期", new CellValueWithStyle("需求澄清日期", onesReq.get需求澄清日期()));
        cellValues.put("PRD终审通过时间", new CellValueWithStyle("PRD终审通过时间", onesReq.getPRD终审通过时间()));
        cellValues.put("技术评审结束时间", new CellValueWithStyle("技术评审结束时间", onesReq.get技术评审结束时间()));
        cellValues.put("预计上线时间", new CellValueWithStyle("预计上线时间", onesReq.get预计上线时间()));
        cellValues.put("实际提测时间", new CellValueWithStyle("实际提测时间", onesReq.get实际提测时间()));
        cellValues.put("实际测试结束时间", new CellValueWithStyle("实际测试结束时间", onesReq.get实际测试结束时间()));
        cellValues.put("实际上线时间", new CellValueWithStyle("实际上线时间", onesReq.get实际上线时间()));
        cellValues.put("技术主R", new CellValueWithStyle("技术主R", onesReq.get技术主R()));
        cellValues.put("测试主R", new CellValueWithStyle("测试主R", onesReq.get测试主R()));
        cellValues.put("产品主R", new CellValueWithStyle("产品主R", onesReq.get产品主R()));
        cellValues.put("是否QA测试", new CellValueWithStyle("是否QA测试", onesReq.get是否QA测试()));
        this.emps = emps;
        //后端参与情况
        this.setBackendInvolveInfo();
    }

    /** 后端参与情况 **/
    public boolean isBackendInvolved;  //是否是后端参与的需求
    public Set<String> backendInvolvedMisId = new HashSet<>();  //后端参与的需求的misId合集
    public Set<String> backendInvolvedReason = new HashSet<>();  //后端参与的原因合集
    public Set<String> backendInvolvedOrgPaths = new HashSet<>();  //后端参与的组织合集
    private String backendInvolvedOrgName;   //后端参与组织名，由backendInvolvedOrgPaths精简而来。
    public String getBackendInvolvedOrgName(){
        return backendInvolvedOrgPaths.toString().replace("[", "").replace("]", "")
                .replace("/美团点评/核心本地商业/基础研发平台/企业平台研发部/办公效率/办公效率后端/", "")
                .replace("/", "");
    }

    //添加需求后端参与情况
    private void setBackendInvolveInfo(){
        //先看指派给
        Emp assigned = emps.get(this.assigned);
        if(assigned != null && assigned.isEmpBackendTeam()) {
            isBackendInvolved = true;
            backendInvolvedMisId.add(assigned.getMisId());
            backendInvolvedReason.add("指派给");
            backendInvolvedOrgPaths.add(assigned.getOrgFullPath());
        }
        //再看技术主R
//        System.out.println("技术主R");
        Emp 技术主R = emps.get(cellValues.get("技术主R").getCellValue());
        if(技术主R != null && 技术主R.isEmpBackendTeam()) {
            isBackendInvolved =  true;
            backendInvolvedMisId.add(技术主R.getMisId());
            backendInvolvedReason.add("技术主R");
            backendInvolvedOrgPaths.add(技术主R.getOrgFullPath());
        }
        String[] 开发人员多选s = 开发人员多选.split(",");
//        System.out.println("开发人员多选s");
        for(String 开发人员多选: 开发人员多选s){
            Emp 开发人员多选Emp = emps.get(开发人员多选);
            if (开发人员多选Emp != null && 开发人员多选Emp.isEmpBackendTeam()) {
                isBackendInvolved = true;
                backendInvolvedMisId.add(开发人员多选Emp.getMisId());
                backendInvolvedReason.add("开发人员多选");
                backendInvolvedOrgPaths.add(开发人员多选Emp.getOrgFullPath());
            }
        }
    }

    public void printBackendInfo(){
        System.out.println("reqId: " + this.reqId);
        System.out.println("===Backend Involved: " + isBackendInvolved);
        System.out.println("===Backend Involved MisId: " + backendInvolvedMisId);
        System.out.println("===Backend Involved Reason: " + backendInvolvedReason);
        System.out.println("===Backend Involved Org FullPath: " + backendInvolvedOrgPaths);
    }

    public void printBackendWarnInfo(){
        if(backendInvolvedOrgPaths.size() > 1) {
            System.err.println("WARING: 以下需求有多个后端组参加：reqId: " + this.reqId);
            System.err.println("===Backend Involved: " + isBackendInvolved);
            System.err.println("===Backend Involved MisId: " + backendInvolvedMisId);
            System.err.println("===Backend Involved Reason: " + backendInvolvedReason);
            System.err.println("===Backend Involved Org FullPath: " + backendInvolvedOrgPaths);
        }
    }

    public String genOnesReqLink(){
        return "https://ones.sankuai.com/ones/product/" + this.spaceId +  "/workItem/requirement/detail/" + reqId;
    }

    @Override
    public String toString() {
        return "OnesReqAnalyzed{" +
                "reqId='" + reqId + '\'' +
                ", reqName='" + reqName + '\'' +
                ", state='" + state + '\'' +
                ", subtypeName='" + subtypeName + '\'' +
                ", spaceId='" + spaceId + '\'' +
                ", spaceName='" + spaceName + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", assigned='" + assigned + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", cellValues=" + cellValues +
                ", 开发人员多选=" + 开发人员多选 +
                ", isBackendInvolved=" + isBackendInvolved +
                ", backendInvolvedMisId='" + backendInvolvedMisId + '\'' +
                ", backendInvolvedReason='" + backendInvolvedReason + '\'' +
                '}';
    }

    @Override
    public int compareTo(OnesReqAnalyzed o) {
//        int result = this.backendInvolvedOrgPaths.toString().compareTo(o.backendInvolvedOrgPaths.toString());
//        if (result != 0) {
//            return result;
//        }else {
//            result = this.subtypeName.compareTo(o.subtypeName);
//        } if (result != 0) {
//            return result;
//        }else {
//            Rules rules = new Rules();
//            return rules.getStateIdx(this.subtypeName, this.state) - rules.getStateIdx(o.subtypeName, o.state);
//        }
        int result = this.backendInvolvedOrgPaths.toString().compareTo(o.backendInvolvedOrgPaths.toString());
        if (result != 0) {
            return result;
        }else {
            Rules rules = new Rules();
            return rules.getStateIdx("产品需求通用-办公新", this.state) - rules.getStateIdx("产品需求通用-办公新", o.state);
        }
    }
}
