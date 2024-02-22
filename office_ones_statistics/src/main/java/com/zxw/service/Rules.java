package com.zxw.service;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 规则数据，如子类型工作流状态顺序
 * 某字段在哪个状态后就应该有值了
 */
public class Rules {

    //不同子类型，状态位置索引
    private String[] prdReqStates = {"待规划","需求调研中","产品方案设计中","产品宣讲","待技术方案设计","技术方案设计中",
            "开发联调","开发完成","待测试","测试中","测试完成","待上线","灰度发布","业务全量","已上线","效果评估完成","已取消"};
    private String[] devReqStates = {"待技术方案设计","技术方案设计中","开发联调","开发完成","待测试","测试中",
            "测试完成","待上线","灰度发布","业务全量","已上线","效果评估完成","已取消"};
    private Map<String, Integer> prdReqStatesIdx = new HashMap<>();
    private Map<String, Integer> devReqStatesIdx = new HashMap<>();

    //合法状态名，用于输入数据校验
    private Set<String> legalStates = new HashSet<>();


    //一个字段，在某个状态后，就应该有值了。这个信息在这里处理。
    private Map<String, String> fieldFirstShouldNotEmptyStateMap_ProdReq = new HashMap<>(); //产品需求
    private Map<String, String> fieldFirstShouldNotEmptyStateMap_DevReq = new HashMap<>();  //技术需求
    private Map<String, String> fieldFirstShouldNotEmptyStateMap_Common = new HashMap<>(); //需求通用


    public Rules(){
        //初始化prdReqStatesIdx & devReqStatesIdx
        for (int i = 0; i < prdReqStates.length; i++) {
            prdReqStatesIdx.put(prdReqStates[i], i);
            legalStates.add(prdReqStates[i]);
        }
        for (int i = 0; i < devReqStates.length; i++) {
            devReqStatesIdx.put(devReqStates[i], i);
            legalStates.add(devReqStates[i]);
        }

        //初始化 fieldFirstShouldNotEmptyStateMap
        fieldFirstShouldNotEmptyStateMap_Common.put("技术评审结束时间", "开发联调");
        fieldFirstShouldNotEmptyStateMap_Common.put("预计上线时间", "开发联调");
        fieldFirstShouldNotEmptyStateMap_Common.put("实际提测时间", "待测试");
        fieldFirstShouldNotEmptyStateMap_Common.put("实际测试结束时间", "测试完成");
        fieldFirstShouldNotEmptyStateMap_Common.put("实际上线时间", "灰度发布");
        fieldFirstShouldNotEmptyStateMap_Common.put("技术主R", "待技术方案设计");

        //产品需求特有
        fieldFirstShouldNotEmptyStateMap_ProdReq.put("提出时间", "待规划"); 
        fieldFirstShouldNotEmptyStateMap_ProdReq.put("需求澄清日期", "产品方案设计中");
        fieldFirstShouldNotEmptyStateMap_ProdReq.put("PRD终审通过时间", "待技术方案设计");
        fieldFirstShouldNotEmptyStateMap_ProdReq.put("产品主R", "待规划");
        fieldFirstShouldNotEmptyStateMap_ProdReq.put("测试主R", "待技术方案设计");
        fieldFirstShouldNotEmptyStateMap_ProdReq.put("是否QA测试", "待技术方案设计");

        //技术需求特有
        fieldFirstShouldNotEmptyStateMap_DevReq.put("提出时间", "待技术方案设计");
        fieldFirstShouldNotEmptyStateMap_DevReq.put("测试主R", "开发联调");
        fieldFirstShouldNotEmptyStateMap_DevReq.put("是否QA测试", "开发联调");
    }

    /**
     * 判断给定的需求子类型，在到达一个state后，给定的字段是否应该为空
     * @param reqSubtype    需求子类型
     * @param filed 需求字段
     * @param state 需求状态
     */
    public boolean shouldFiledBeEmptyForState(String reqSubtype, String filed, String state){

        if(!legalStates.contains(state)){
            System.err.println("状态不合法。程序退出。" + state);
            System.exit(0);
        }

        //查询字段第一个不应该为空的状态
        String firstShouldNotEmptyState = fieldFirstShouldNotEmptyStateMap_Common.get(filed);
        if(firstShouldNotEmptyState == null){
            if (reqSubtype.equals("产品需求通用-办公新")) {
                firstShouldNotEmptyState = fieldFirstShouldNotEmptyStateMap_ProdReq.get(filed);
            } else if (reqSubtype.equals("技术需求通用-办公新")) {
                firstShouldNotEmptyState = fieldFirstShouldNotEmptyStateMap_DevReq.get(filed);
            }
        }

        //如果当前实际状态晚于字段第一个不应该为空的状态，则当前字段不应该为空。
        int firstShouldNotEmptyStateIdx = -1, currentStateIdx = -1;
        if (reqSubtype.equals("产品需求通用-办公新")) {
            firstShouldNotEmptyStateIdx = prdReqStatesIdx.getOrDefault(firstShouldNotEmptyState, Integer.MAX_VALUE);
            currentStateIdx = prdReqStatesIdx.getOrDefault(state, -1);
        } else if (reqSubtype.equals("技术需求通用-办公新")) {
            firstShouldNotEmptyStateIdx = devReqStatesIdx.getOrDefault(firstShouldNotEmptyState, Integer.MAX_VALUE);
            currentStateIdx = devReqStatesIdx.getOrDefault(state, -1);
        }
        if(currentStateIdx == -1){
            System.err.println("Error: 找不到状态'" + state + "'对应的index。程序退出。");
            System.exit(0);
        }
        return currentStateIdx < firstShouldNotEmptyStateIdx;
    }

    public int getStateIdx(String reqSubtype,String state){
        if (reqSubtype.equals("产品需求通用-办公新")) {
            return prdReqStatesIdx.get(state);
        } else if (reqSubtype.equals("技术需求通用-办公新")) {
            return devReqStatesIdx.get(state);
        } else {
            return -1;
        }
    }
}
