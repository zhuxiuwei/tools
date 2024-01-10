package com.zxw.bean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * ONES需求 -- 被分析过的
 */
public class OnesReqAnalyzed {
    public String reqId;
    public String reqName;
    public String state;
    public String subtypeName;
    public String spaceId;
    public String spaceName;
    public String createdBy;
    public String assigned;
    public String createdAt;

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
        cellValues.put("开发人员多选", new CellValueWithStyle("开发人员多选", onesReq.get开发人员多选()));

        this.emps = emps;

        //后端参与情况
        this.setBackendInvolveInfo();
    }

    public boolean isBackendInvolved;  //是否是后端参与的需求
    public Set<String> backendInvolvedMisId = new HashSet<>();  //后端参与的需求的misId
    public Set<String> backendInvolvedReason = new HashSet<>();  //后端参与的原因

    //添加需求后端参与情况
    private void setBackendInvolveInfo(){
        //先看指派给
//        System.out.println("指派给");
        if(this.reqId.equals("83000058")){
            System.out.println(1);
        }
        Emp assigned = emps.get(this.assigned);
        if(assigned != null && assigned.isEmpBackendTeam()) {
            isBackendInvolved = true;
            backendInvolvedMisId.add(assigned.getMisId());
            backendInvolvedReason.add("指派给");
        }
        //再看技术主R
//        System.out.println("技术主R");
        Emp 技术主R = emps.get(cellValues.get("技术主R").getCellValue());
        if(技术主R != null && 技术主R.isEmpBackendTeam()) {
            isBackendInvolved =  true;
            backendInvolvedMisId.add(assigned.getMisId());
            backendInvolvedReason.add("技术主R");
        }
        String[] 开发人员多选s = cellValues.get("开发人员多选").getCellValue().split(",");
//        System.out.println("开发人员多选s");
        for(String 开发人员多选: 开发人员多选s){
            Emp 开发人员多选Emp = emps.get(开发人员多选);
            if (开发人员多选Emp != null && 开发人员多选Emp.isEmpBackendTeam()) {
                isBackendInvolved = true;
                backendInvolvedMisId.add(assigned.getMisId());
                backendInvolvedReason.add("开发人员多选");
            }
        }
    }

    public void printBackendInfo(){
        System.out.println("reqId: " + this.reqId);
        System.out.println("===Backend Involved: " + isBackendInvolved);
        System.out.println("===Backend Involved MisId: " + backendInvolvedMisId);
        System.out.println("===Backend Involved Reason: " + backendInvolvedReason);
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
                ", emps=" + emps +
                ", isBackendInvolved=" + isBackendInvolved +
                ", backendInvolvedMisId='" + backendInvolvedMisId + '\'' +
                ", backendInvolvedReason='" + backendInvolvedReason + '\'' +
                '}';
    }
}
