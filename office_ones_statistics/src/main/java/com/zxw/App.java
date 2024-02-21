package com.zxw;

import com.zxw.bean.Emp;
import com.zxw.bean.OnesReq;
import com.zxw.bean.OnesReqAnalyzed;
import com.zxw.bean.OrgLevelStatistics;
import com.zxw.service.AnalyzeReq;
import com.zxw.service.ExcelCreator;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 */
public class App 
{
    public void process(String onesReqExcel, String empExcel) throws IOException {
        //1、读取需求和员工信息
        System.out.println("Getting data...");
        List<Emp> emps = readEmpExcel(empExcel);
        System.out.println("读取" + emps.size() + "条员工信息。");
        List<OnesReq> reqs = readOnesReqExcel(onesReqExcel);
        System.out.println("读取" + reqs.size() + "条需求信息。");
        System.out.println("Data received!");

        //2、分析需求完整性
        AnalyzeReq analyzeReq = new AnalyzeReq(emps, reqs);

        //2.1、需求级分析
        //对每条需求的关键字段，判断数据完整性信息。然后返回填充了完整度信息的需求列表。
        List<OnesReqAnalyzed> reqListWithFiledCompletenessInfo = analyzeReq.getReqListWithFiledCompletenessInfo();
        //需求粒度的完整度信息写excel
        ExcelCreator excelCreator = new ExcelCreator();
        String excelPath = excelCreator.saveReqListWithFiledCompletenessInfoToExcel(reqListWithFiledCompletenessInfo);
        System.out.println("需求完整度信息已生成excel，路径为：" + excelPath);

        //2.2 统计维度的分析
        Map<String, Map<String, OrgLevelStatistics>> orgLevelStatistics = analyzeReq.getOrgLevelStatistics(reqListWithFiledCompletenessInfo);
        //统计维度的完整度信息写excel
        excelPath = excelCreator.saveOrgLevelStatisticsToExcel(orgLevelStatistics);
        System.out.println("统计维度完整度信息已生成excel，路径为：" + excelPath);
    }

    private List<Emp> readEmpExcel(String empExcel) throws IOException {
        List<Emp> empList = new ArrayList<>();
        FileInputStream inputStream = new FileInputStream(new File(empExcel));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            // 如果是表头，跳过
            if (row.getRowNum() == 0) {
                continue;
            }
            // 读取每个单元格的数据
            String misId = row.getCell(0).toString();
            String orgFullPath = row.getCell(1).toString();
            String orgName = row.getCell(2).toString();
            Emp emp = new Emp(misId, orgFullPath, orgName);
            empList.add(emp);
        }
        return empList;
    }

    private List<OnesReq> readOnesReqExcel(String onesReqExcel) throws IOException {
        List<OnesReq> empList = new ArrayList<>();
        FileInputStream inputStream = new FileInputStream(new File(onesReqExcel));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表
        Iterator<Row> rowIterator = sheet.iterator();
        DataFormatter formatter = new DataFormatter(); // 创建一个DataFormatter来格式化和读取每个单元格的值
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            // 如果是表头，跳过
            if (row.getRowNum() == 0) {
                continue;
            }
            // 读取每个单元格的数据
            String reqId = formatter.formatCellValue(row.getCell(0)) + "";
            String reqName = row.getCell(1) + "";
            String state = row.getCell(2) + "";
            String subtypeName = row.getCell(3) + "";
            String spaceId = formatter.formatCellValue(row.getCell(4)) + "";
            String spaceName = row.getCell(5) + "";
            String createdBy = row.getCell(6) + "";
            String assigned = row.getCell(7) + "";
            String orgFullPath = row.getCell(8) + "";
            String createdAt = row.getCell(9) + "";
            String 提出时间 = row.getCell(10) + "";
            String 需求澄清日期 = row.getCell(11) + "";
            String PRD终审通过时间 = row.getCell(12) + "";
            String 技术评审结束时间 = row.getCell(13) + "";
            String 预计上线时间 = row.getCell(14) + "";
            String 实际提测时间 = row.getCell(15) + "";
            String 实际测试结束时间 = row.getCell(16) + "";
            String 实际上线时间 = row.getCell(17) + "";
            String 技术主R = row.getCell(18) + "";
            String 测试主R = row.getCell(19) + "";
            String 产品主R = row.getCell(20) + "";
            String 是否QA测试 = row.getCell(21) + "";
            String 开发人员多选 = row.getCell(22) + "";
            OnesReq onesReq = new OnesReq();
            onesReq.setReqId(reqId);
            onesReq.setReqName(reqName);
            onesReq.setState(state);
            onesReq.setSubtypeName(subtypeName);
            onesReq.setSpaceId(spaceId);
            onesReq.setSpaceName(spaceName);
            onesReq.setCreatedBy(createdBy);
            onesReq.setAssigned(assigned);
            onesReq.setOrgFullPath(orgFullPath);
            onesReq.setCreatedAt(createdAt);
            onesReq.set提出时间(提出时间);
            onesReq.set需求澄清日期(需求澄清日期);
            onesReq.setPRD终审通过时间(PRD终审通过时间);
            onesReq.set技术评审结束时间(技术评审结束时间);
            onesReq.set预计上线时间(预计上线时间);
            onesReq.set实际提测时间(实际提测时间);
            onesReq.set实际测试结束时间(实际测试结束时间);
            onesReq.set实际上线时间(实际上线时间);
            onesReq.set技术主R(技术主R);
            onesReq.set测试主R(测试主R);
            onesReq.set产品主R(产品主R);
            onesReq.set是否QA测试(是否QA测试);
            onesReq.set开发人员多选(开发人员多选);
            empList.add(onesReq);
        }
        return empList;
    }

    public static void main(String[] args) throws IOException {
//        String onesReqExcel = "/Volumes/文枢工作空间/需求列表-22的副本.xlsx";
        String onesReqExcel = "/Volumes/文枢工作空间/需求列表-810313196-1707121285799.xlsx";
        String empExcel = "/Volumes/文枢工作空间/组织信息-810305350-1707120774763.xlsx";
        App app = new App();
        app.process(onesReqExcel, empExcel);
    }
}
