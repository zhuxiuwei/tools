package mt;

import bean.OrgSeq;
import bean.Ticket;
import bean.TicketArchive;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.PrintTable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * 问渠计划
 */
public class TTAnalysis2 {

    private Set<String> validModules = new HashSet<>();
    private Map<String, String> moduleMapper = new HashMap<>();

    public TTAnalysis2(){
        String[] validModulesStr = "平台介绍,工作台,项目集,项目,权限,配置,工作项,迭代,应用,提测,用例,工时,搜索,甘特图,报表,其他".split(",");  //现存模块，主要是资讯类的 三级目录
        String[] newAddModulesStr = "过滤器,OpenAPI,配置需求".split(",");  //新增应该合法的模块

        for (String validModule: validModulesStr){
            validModules.add(validModule);
        }
        for (String newAddModulesSt: newAddModulesStr){
            validModules.add(newAddModulesSt);
        }

        String[] mapperStrs = ("API,OpenAPI\n" +
                "app,应用\n" +
                "Issue,工作项\n" +
                "issue tab,工作项\n" +
                "issueEngine,工作项\n" +
                "open api,OpenAPI\n" +
                "openapi,OpenAPI\n" +
                "tapd迁移,其他\n" +
                "token,配置需求\n" +
                "user,权限\n" +
                "web hook,工作项\n" +
                "webhook,工作项\n" +
                "版本,配置\n" +
                "标签,配置\n" +
                "操作日志,工作项\n" +
                "草稿,工作项\n" +
                "测试,用例\n" +
                "测试计划,用例\n" +
                "测试用例,用例\n" +
                "测试用例分类,用例\n" +
                "导出,工作项\n" +
                "第三方系统,提测\n" +
                "对外API,OpenAPI\n" +
                "对外接口,OpenAPI\n" +
                "分支,应用\n" +
                "服务,应用\n" +
                "附件,工作项\n" +
                "附件上传,工作项\n" +
                "工作流,配置\n" +
                "工作流配置,配置\n" +
                "工作项（线下环境）,工作项\n" +
                "工作项联动,配置\n" +
                "工作项列表,工作项\n" +
                "关联,工作项\n" +
                "关联关系,工作项\n" +
                "关联关系对外接口,OpenAPI\n" +
                "管理后台,配置需求\n" +
                "过滤,过滤器\n" +
                "过滤查询,过滤器\n" +
                "过滤器/header展示,过滤器\n" +
                "回收站,工作项\n" +
                "接口,OpenAPI\n" +
                "界面,配置\n" +
                "界面显示,配置\n" +
                "看板,工作项\n" +
                "可选值,配置\n" +
                "可选值配置,配置\n" +
                "快捷拆分,配置\n" +
                "列表,工作项\n" +
                "列表页,工作项\n" +
                "模版,配置\n" +
                "模板,配置\n" +
                "模块,配置\n" +
                "评论,工作项\n" +
                "评论提醒,配置\n" +
                "全局搜索,搜索\n" +
                "缺陷,工作项\n" +
                "缺陷原因,工作项\n" +
                "人力资源规划,配置\n" +
                "人员搜索,搜索\n" +
                "任务,工作项\n" +
                "视图,配置\n" +
                "详情TAB,工作项\n" +
                "详情页,工作项\n" +
                "项目概述,项目\n" +
                "项目模板,配置\n" +
                "消息,配置\n" +
                "消息提示,配置\n" +
                "消息提醒,配置\n" +
                "消息通知,配置\n" +
                "消息推送,配置\n" +
                "需求,工作项\n" +
                "需求分类,配置\n" +
                "应用/服务,应用\n" +
                "用户,权限\n" +
                "用例分类,用例\n" +
                "用例集,用例\n" +
                "账号,权限\n" +
                "状态,配置\n" +
                "状态流,配置\n" +
                "状态流转,配置\n" +
                "子类型,配置\n" +
                "自测,提测\n" +
                "自定义字段,配置需求\n" +
                "自动化,配置\n" +
                "自动化配置,配置\n" +
                "自动化设置,配置\n" +
                "字段,工作项\n" +
                "字段配置,配置\n" +
                "组织结构,权限").split("\n");
        for (String mapper: mapperStrs){
            String[] kv = mapper.split(",");
            if(!validModules.contains(kv[1])) {
                System.out.println("!!!!!!!!!!!!!!!!!!  invalid mapper：" + mapper);
                System.exit(1);
            }
            moduleMapper.put(kv[0].toLowerCase().trim(), kv[1].trim());
        }
    }

    //补全TT的Module.
    List<TT2> addModuleUpdated(String filePath){
        List<TT2> tickets = readExcel(filePath);
        System.out.println(tickets.stream().filter(x -> (x.getModule() == null || x.getModule().equals("NULL"))).count());
        tickets.stream().forEach(
                x->{
                    String module = x.getModule().toLowerCase().trim();
                    if(module.equals("null") || module == null){    //module为空，从标题猜测。
                        x.setModuleUpdated(getByTitle(x.getTitle().toLowerCase().trim()));
                    }else {
                        if(validModules.contains(module)){
                            x.setModuleUpdated(module);
                        }else {
                            String mapperModule = moduleMapper.get(module);
                            if(mapperModule == null){
                                x.setModuleUpdated(getByTitle(x.getTitle().toLowerCase().trim()));
                            }else {
                                x.setModuleUpdated(mapperModule);
                            }
                        }
                    }
                }
        );
//        tickets.forEach(x -> System.out.println(x));
        return tickets;
    }

    String getByTitle(String title){
        String res = "其他";
        boolean match = false;
        for(String validModule: validModules){
            if(title.contains(validModule)){
                match = true;
                res = validModule;
                break;
            }
        }
        if(!match){
            for(String k: moduleMapper.keySet()){
                if(title.contains(k)){
                    return moduleMapper.get(k);
                }
            }
        }
        return res;
    }
    //header: TT-ID	标题	类型（修订）	模块（修订）	TODO	详细	是否高频问题	日期	计数	模块
    List<TT2> readExcel(String filePath){
        List<TT2> res = new ArrayList<>();
        InputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            Workbook workbook = null;
            if (filePath.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (filePath.endsWith(".xls") || filePath.endsWith(".et")) {
                workbook = new HSSFWorkbook(fis);
            }
            fis.close();
            /* 读EXCEL文字内容 */
            // 获取第一个sheet表，也可使用sheet表名获取
            Sheet sheet = workbook.getSheetAt(0);
            // 获取行
            Iterator<Row> rows = sheet.rowIterator();
            Row row;

            while (rows.hasNext()) {
                row = rows.next();
                String title = getCellStringVal(row, 1);
                if(title.equals("标题"))  //跳过第一行
                    continue;
                String type = row.getCell(2) == null ? "NULL": getCellStringVal(row, 2);
                String module = row.getCell(9) == null ? "NULL": getCellStringVal(row, 9);
                String date = row.getCell(7) == null ? "NULL": getCellStringVal(row, 7);
                TT2 ticket = new TT2(title, type, module, date);
                res.add(ticket);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }

    String getCellStringVal(Row row, int idx){
        String res = "";
        try {
            res = row.getCell(idx) == null ? "NULL" : row.getCell(idx).getStringCellValue();
        }catch (java.lang.IllegalStateException exp){
            res = row.getCell(idx) == null ? "NULL" : row.getCell(idx).getNumericCellValue() + "";
        }
        return res;
    }

    void fill问渠产出汇总表(String path, List<TT2> list){
        Map<String, String> map = new HashMap<>();
        for(TT2 tt2: list){
            map.put(tt2.getTitle(), tt2.getModuleUpdated());
        }
        List<TT2> ticketsWithoutUpdateModule = readExcel(path);
        for(TT2 tt2: ticketsWithoutUpdateModule){
            String newModule = map.getOrDefault(tt2.getTitle(), "其他");
            tt2.setModuleUpdated(newModule);
            System.out.println(tt2);
        }
    }

    public static void main(String[] args) {
        TTAnalysis2 t = new TTAnalysis2();
        String filePath = "/Users/zhuxiuwei/Downloads/原始.xlsx";
        List<TT2> tickets = t.addModuleUpdated(filePath);
        t.fill问渠产出汇总表("/Users/zhuxiuwei/Downloads/问渠产出汇总表-210512.xlsx", tickets);
    }
}

//示例见TTAnalysis2.zip
class TT2{
    private String title;   //标题
    private String type;    //类型
    private String module;  //模块
    private String moduleUpdated;   //模块-修订
    private String date;    //日期

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getModuleUpdated() {
        return moduleUpdated;
    }

    public void setModuleUpdated(String moduleUpdated) {
        this.moduleUpdated = moduleUpdated;
    }

    public TT2(String title, String type, String module, String date) {
        this.title = title;
        this.type = type;
        this.module = module;
        this.date = date;
    }

    @Override
    public String toString() {
        title = title.replace(",", "，");
        return title+","+type+","+module+","+moduleUpdated+","+date;
    }
}
