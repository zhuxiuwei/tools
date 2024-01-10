package com.zxw.service;

import com.zxw.bean.Emp;
import com.zxw.bean.OnesReq;
import com.zxw.bean.OnesReqAnalyzed;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 分析需求情况主逻辑
 */
public class AnalyzeReq {
    private List<Emp> emps;
    private List<OnesReq> originalReqs;

    public AnalyzeReq(List<Emp> emps, List<OnesReq> reqs) {
        this.emps = emps;
        this.originalReqs = reqs;
    }

    /**
     * 对每条需求的关键字段，判断数据完整性信息。然后返回填充了完整度信息的需求列表。
     * 如果需要填写，背景浅绿色。
     * 如果需要填写但没有填写，值红色。
     */
    public List<OnesReqAnalyzed> getReqListWithFiledCompletenessInfo(){
        List<OnesReqAnalyzed> analyzedReqs = new ArrayList<>();

        //员工信息转map
        Map<String, Emp> empWithOrgFullPath = new HashMap<>();
        emps.forEach(emp -> empWithOrgFullPath.put(emp.getMisId(), emp));

        //原始ones需求转OnesReqAnalyzed
        originalReqs.forEach(onesReq -> analyzedReqs.add(new OnesReqAnalyzed(onesReq, empWithOrgFullPath)));
//        analyzedReqs.forEach(onesReqAnalyzed -> onesReqAnalyzed.printBackendInfo());

        //梳理出后端参与的。进行逐条分析
        List<OnesReqAnalyzed> backendReqs  = analyzedReqs.stream().filter(onesReqAnalyzed -> onesReqAnalyzed.isBackendInvolved).collect(Collectors.toList());
        System.out.println("后端需求条数：" + backendReqs.size());
        backendReqs.forEach(onesReqAnalyzed -> onesReqAnalyzed.printBackendWarnInfo());

        //排除掉子类型用的不对的。理论上后面应该不会有了，除非有新ONES空间迁移过来。
        List<OnesReqAnalyzed> backendReqsWrongReqType  = backendReqs.stream().filter(onesReqAnalyzed ->
                !(onesReqAnalyzed.subtypeName.equals("产品需求通用-办公新") ||  onesReqAnalyzed.subtypeName.equals("技术需求通用-办公新"))).collect(Collectors.toList());
        backendReqsWrongReqType.forEach(onesReqAnalyzed -> System.err.println("排除子类型不对的需求: " + onesReqAnalyzed.reqId + "|" + onesReqAnalyzed.spaceName + "|" + onesReqAnalyzed.backendInvolvedMisId));
        backendReqs.removeAll(backendReqsWrongReqType);
        System.out.println("排除子类型不对的需求后，后端需求条数：" + backendReqs.size());

        Collections.sort(backendReqs);
//        backendReqs.forEach(onesReqAnalyzed -> {
//            System.out.println(onesReqAnalyzed.reqId + "|" + onesReqAnalyzed.backendInvolvedOrgPaths + "|" + onesReqAnalyzed.subtypeName + "|" +onesReqAnalyzed.state);
//        });


        //根据规则去判断
        Rules rules = new Rules();
        backendReqs.forEach(onesReqAnalyzed -> {
            onesReqAnalyzed.cellValues.forEach((field, cellValue) -> {
               if(!rules.shouldFiledBeEmptyForState(onesReqAnalyzed.subtypeName, field, onesReqAnalyzed.state)){
                   //该有值
                   cellValue.setShouldNotBeEmptyBackground();   //设置绿色背景色
                   if(cellValue.isEmptyCellValue()){    //该有值，但是值为空
                       cellValue.setShouldNotBeEmptyButEmptyFont(); //设置字体红色
                   }
               }
            });
        });
        return backendReqs;
    }
}
