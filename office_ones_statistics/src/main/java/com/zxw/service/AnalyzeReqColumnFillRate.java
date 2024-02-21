package com.zxw.service;

import com.zxw.bean.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 分析需求字段填写率情况主逻辑
 */
public class AnalyzeReqColumnFillRate {
    private List<Emp> emps;
    private List<OnesReq> originalReqs;

    public AnalyzeReqColumnFillRate(List<Emp> emps, List<OnesReq> reqs) {
        this.emps = emps;
        this.originalReqs = reqs;
    }

    /**
     * 需求维度的分析： 对每条需求的关键字段，判断数据完整性信息。然后返回填充了完整度信息的需求列表。
     * 对于需求某个字段，填写如下填写完整性信息：
     * - 如果需要填写，背景浅绿色。
     * - 如果需要填写但没有填写，值红色。
     * @return 填写了需求填写情况信息的ones需求列表
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

        //根据规则去判断
        Rules rules = new Rules();
        backendReqs.forEach(onesReqAnalyzed -> {
            onesReqAnalyzed.cellValues.forEach((field, cellValue) -> {
               if(!rules.shouldFiledBeEmptyForState(onesReqAnalyzed.subtypeName, field, onesReqAnalyzed.state)){
                   cellValue.setShouldBeFilled(true);
                   cellValue.setShouldNotBeEmptyBackground();   //该有值，设置绿色背景色
                   if(cellValue.isEmptyCellValue()){
                       cellValue.setShouldNotBeEmptyButEmptyFont(); //该有值，但是值为空，设置字体红色
                   }
               }
            });
        });

        // 后续处理： 无需QA测试的，则QA主R是否填了都没关系，统一设置成默认字体色。
        // TODO: 目前只有一个后续处理，多了做成接口实现类。
        backendReqs.forEach(onesReqAnalyzed -> {
            if(onesReqAnalyzed.cellValues.containsKey("是否QA测试")
                    && onesReqAnalyzed.cellValues.get("是否QA测试").isShouldBeFilled()
                    && onesReqAnalyzed.cellValues.get("是否QA测试").getCellValue().equals("否")){
                onesReqAnalyzed.cellValues.get("测试主R").setNormalFont();
                onesReqAnalyzed.cellValues.get("测试主R").setCellValue("NULL(但无需填写)");
            }
        });

        return backendReqs;
    }

    /**
     * 获取组织维度的字段填写完整率统计信息列表
     * @param reqs 填写了需求填写情况信息的ones需求列表
     * @return 组织维度的字段填写完整率统计信息。为2层map：第一层map(k=组织名，v=第二层map)；第二场map k=字段名，v=OrgLevelStatistics
     */
    public Map<String, Map<String, OrgLevelStatistics>> getOrgLevelStatistics(List<OnesReqAnalyzed> reqs){
        Map<String, Map<String, OrgLevelStatistics>> orgLevelStatisticsMap = new HashMap<>();
        //计算[办公效率后端-1]每个组织每个字段填写率
        reqs.forEach(onesReq -> {
            String orgName = onesReq.getBackendInvolvedOrgName();
            if(!orgLevelStatisticsMap.containsKey(orgName)){
                orgLevelStatisticsMap.put(orgName, new HashMap<>());
            }
            Map<String, OrgLevelStatistics> filedLevelStatisticsMap = orgLevelStatisticsMap.get(orgName);
            onesReq.cellValues.forEach((fieldName, cellValue) -> {
                if (!filedLevelStatisticsMap.containsKey(fieldName)) {
                    OrgLevelStatistics orgLevelStatistics = new OrgLevelStatistics();
                    orgLevelStatistics.orgName = orgName;
                    orgLevelStatistics.fieldName = fieldName;
                    filedLevelStatisticsMap.put(fieldName, orgLevelStatistics);
                }
                OrgLevelStatistics orgLevelStatistic = filedLevelStatisticsMap.get(fieldName);
                if(cellValue.isShouldBeFilled()){   //应该填写数统计
                    orgLevelStatistic.shouldFillCount++;
                    if(!cellValue.isEmptyCellValue()) {
                        orgLevelStatistic.actualFillCount++;    //实际填写数统计
                    }
                }
                orgLevelStatistic.fillRate = (double) orgLevelStatistic.actualFillCount / (double) orgLevelStatistic.shouldFillCount; //填写率
            });
        });

        //计算[办公效率后端]整个组织每个字段的整体填写率
        String banGongOrgName = "总体";
        Map<String, OrgLevelStatistics> filedLevelStatisticsMap_BanGong = new HashMap<>();
        orgLevelStatisticsMap.forEach((orgName, filedLevelStatisticsMap) -> {
            filedLevelStatisticsMap.forEach((fieldName, filedLevelStatistics) -> {
                OrgLevelStatistics orgLevelStatistics_BanGong = filedLevelStatisticsMap_BanGong.getOrDefault(fieldName, new OrgLevelStatistics());
                orgLevelStatistics_BanGong.orgName = banGongOrgName;
                orgLevelStatistics_BanGong.fieldName = fieldName;
                orgLevelStatistics_BanGong.shouldFillCount += filedLevelStatistics.shouldFillCount;
                orgLevelStatistics_BanGong.actualFillCount += filedLevelStatistics.actualFillCount;
                orgLevelStatistics_BanGong.fillRate = (double) orgLevelStatistics_BanGong.actualFillCount / (double) orgLevelStatistics_BanGong.shouldFillCount;
                filedLevelStatisticsMap_BanGong.put(fieldName, orgLevelStatistics_BanGong);
            });
        });
        orgLevelStatisticsMap.put(banGongOrgName, filedLevelStatisticsMap_BanGong);

        //计算组织整体字段填写率
        orgLevelStatisticsMap.forEach((orgName, filedLevelStatisticsMap) -> {
            OrgLevelStatistics totalOrgLevelStatistics = new OrgLevelStatistics();
            totalOrgLevelStatistics.orgName = orgName;
            totalOrgLevelStatistics.fieldName = "整体填写率";
            filedLevelStatisticsMap.forEach((fieldName, filedLevelStatistics) -> {
                totalOrgLevelStatistics.shouldFillCount += filedLevelStatistics.shouldFillCount;
                totalOrgLevelStatistics.actualFillCount += filedLevelStatistics.actualFillCount;
            });
            totalOrgLevelStatistics.fillRate = (double) totalOrgLevelStatistics.actualFillCount / (double) totalOrgLevelStatistics.shouldFillCount;
            filedLevelStatisticsMap.put("整体填写率", totalOrgLevelStatistics);
        });

        //对结果进线排序。排序规则：整体填写率高的组织放前面，「总体」永远放最后面。
        return sortOrgLevelStatisticsMap(orgLevelStatisticsMap);
    }

    /**
     *  对结果进线排序。排序规则：整体填写率高的组织放前面，「总体」永远放最后面。
     */
    private Map<String, Map<String, OrgLevelStatistics>> sortOrgLevelStatisticsMap(Map<String, Map<String, OrgLevelStatistics>> orgLevelStatisticsMap) {
        List<OrgTotalFillRateTuple> orgTotalFillRateTupleList = new ArrayList<>();
        orgLevelStatisticsMap.forEach((orgName, filedLevelStatisticsMap) -> {
            OrgLevelStatistics totalOrgLevelStatistics = filedLevelStatisticsMap.get("整体填写率");
            orgTotalFillRateTupleList.add(new OrgTotalFillRateTuple(orgName, totalOrgLevelStatistics.fillRate));
        });
        Collections.sort(orgTotalFillRateTupleList);
        Map<String, Map<String, OrgLevelStatistics>> res = new LinkedHashMap<>();
        orgTotalFillRateTupleList.forEach(orgTotalFillRateTuple -> {
            res.put(orgTotalFillRateTuple.getOrgName(), orgLevelStatisticsMap.get(orgTotalFillRateTuple.getOrgName()));
        });
        return res;
    }
}
