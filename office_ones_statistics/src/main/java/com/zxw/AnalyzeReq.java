package com.zxw;

import com.zxw.bean.Emp;
import com.zxw.bean.OnesReq;
import com.zxw.bean.OnesReqAnalyzed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分析需求情况主逻辑
 */
public class AnalyzeReq {
    private List<Emp> emps;
    private List<OnesReq> reqs;

    public AnalyzeReq(List<Emp> emps, List<OnesReq> reqs) {
        this.emps = emps;
        this.reqs = reqs;
    }

    public List<OnesReqAnalyzed> analyze(){
        List<OnesReqAnalyzed> res = new ArrayList<>();

        //员工信息转map
        Map<String, Emp> empWithOrgFullPath = new HashMap<>();
        emps.forEach(emp -> empWithOrgFullPath.put(emp.getMisId(), emp));

        //原始ones需求转OnesReqAnalyzed
        reqs.forEach(onesReq -> res.add(new OnesReqAnalyzed(onesReq, empWithOrgFullPath)));
//        res.forEach(onesReqAnalyzed -> onesReqAnalyzed.printBackendInfo());
        return res;
    }
}
