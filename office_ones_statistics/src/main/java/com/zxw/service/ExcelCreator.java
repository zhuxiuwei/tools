package com.zxw.service;

import com.zxw.bean.CellValueWithStyle;
import com.zxw.bean.OnesReqAnalyzed;
import com.zxw.bean.OrgLevelStatistics;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * javaBeans转excel
 */
public class ExcelCreator {

    /**
     * 需求维度统计信息写excel
     * 将保存了每条需求的关键字段及其数据完整性信息的需求列表写入excel。
     * @return excel地址
     */
    public String saveReqListWithFiledCompletenessInfoToExcel(List<OnesReqAnalyzed> reqAnalyzedList){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        // 创建表头
        List<String> excelHeaders = Arrays.asList("需求","状态","子类型","空间名","创建时间","后端参与人","后端参与人角色","后端参与人组织",
                "提出时间","需求澄清日期","PRD终审通过时间","技术评审结束时间","预计上线时间","实际提测时间","实际测试结束时间","实际上线时间","技术主R","测试主R","产品主R","是否QA测试");
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < excelHeaders.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(excelHeaders.get(i));
        }

        // 填充数据
        for (int i = 0; i < reqAnalyzedList.size(); i++) {
            OnesReqAnalyzed analyzed = reqAnalyzedList.get(i);
            Row dataRow = sheet.createRow(i + 1);
            int j = 0;
            CellValueWithStyle cellValueWithStyle = null;
            for (String header : excelHeaders) {
                Cell cell = dataRow.createCell(j);
                switch (header){
                    case "需求":
                        CellStyle hlinkStyle = workbook.createCellStyle();
                        Font hlinkFont = workbook.createFont();
                        hlinkFont.setUnderline(Font.U_SINGLE);
                        hlinkFont.setColor(IndexedColors.BLUE.getIndex());
                        hlinkStyle.setFont(hlinkFont);
                        cell.setCellValue(analyzed.reqName);
                        // 创建一个超链接并设置它的地址
                        CreationHelper createHelper = workbook.getCreationHelper();
                        Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_URL);
                        link.setAddress(analyzed.genOnesReqLink());
                        // 应用超链接和样式到单元格
                        cell.setHyperlink(link);
                        cell.setCellStyle(hlinkStyle);
                        break;
                    case "状态":
                        cell.setCellValue(analyzed.state);
                        break;
                    case "子类型":
                        cell.setCellValue(analyzed.subtypeName);
                        break;
                    case "空间名":
                        cell.setCellValue(analyzed.spaceName);
                        break;
                    case "创建时间":
                        cell.setCellValue(analyzed.createdAt);
                        break;
                    case "后端参与人":
                        cell.setCellValue(analyzed.backendInvolvedMisId.toString().replace("[", "").replace("]", ""));
                        break;
                    case "后端参与人角色":
                        cell.setCellValue(analyzed.backendInvolvedReason.toString().replace("[", "").replace("]", ""));
                        break;
                    case "后端参与人组织":
                        cell.setCellValue(analyzed.getBackendInvolvedOrgName());
                        setCellStyle(workbook, cell, getColorForString(analyzed.backendInvolvedOrgPaths.toString()), IndexedColors.WHITE.getIndex());
                        break;
                    case "提出时间":
                        cellValueWithStyle = analyzed.cellValues.get("提出时间");
                        cell.setCellValue(cellValueWithStyle.getCellValue());
                        setCellStyle(workbook, cell, cellValueWithStyle.getCellBackgroundColor(), cellValueWithStyle.getFontColor());
                        break;
                    case "需求澄清日期":
                        cellValueWithStyle = analyzed.cellValues.get("需求澄清日期");
                        cell.setCellValue(cellValueWithStyle.getCellValue());
                        setCellStyle(workbook, cell, cellValueWithStyle.getCellBackgroundColor(), cellValueWithStyle.getFontColor());
                        break;
                    case "PRD终审通过时间":
                        cellValueWithStyle = analyzed.cellValues.get("PRD终审通过时间");
                        cell.setCellValue(cellValueWithStyle.getCellValue());
                        setCellStyle(workbook, cell, cellValueWithStyle.getCellBackgroundColor(), cellValueWithStyle.getFontColor());
                        break;
                    case "技术评审结束时间":
                        cellValueWithStyle = analyzed.cellValues.get("技术评审结束时间");
                        cell.setCellValue(cellValueWithStyle.getCellValue());
                        setCellStyle(workbook, cell, cellValueWithStyle.getCellBackgroundColor(), cellValueWithStyle.getFontColor());
                        break;
                    case "预计上线时间":
                        cellValueWithStyle = analyzed.cellValues.get("预计上线时间");
                        cell.setCellValue(cellValueWithStyle.getCellValue());
                        setCellStyle(workbook, cell, cellValueWithStyle.getCellBackgroundColor(), cellValueWithStyle.getFontColor());
                        break;
                    case "实际提测时间":
                        cellValueWithStyle = analyzed.cellValues.get("实际提测时间");
                        cell.setCellValue(cellValueWithStyle.getCellValue());
                        setCellStyle(workbook, cell, cellValueWithStyle.getCellBackgroundColor(), cellValueWithStyle.getFontColor());
                        break;
                    case "实际测试结束时间":
                        cellValueWithStyle = analyzed.cellValues.get("实际测试结束时间");
                        cell.setCellValue(cellValueWithStyle.getCellValue());
                        setCellStyle(workbook, cell, cellValueWithStyle.getCellBackgroundColor(), cellValueWithStyle.getFontColor());
                        break;
                    case "实际上线时间":
                        cellValueWithStyle = analyzed.cellValues.get("实际上线时间");
                        cell.setCellValue(cellValueWithStyle.getCellValue());
                        setCellStyle(workbook, cell, cellValueWithStyle.getCellBackgroundColor(), cellValueWithStyle.getFontColor());
                        break;
                    case "技术主R":
                        cellValueWithStyle = analyzed.cellValues.get("技术主R");
                        cell.setCellValue(cellValueWithStyle.getCellValue());
                        setCellStyle(workbook, cell, cellValueWithStyle.getCellBackgroundColor(), cellValueWithStyle.getFontColor());
                        break;
                    case "测试主R":
                        cellValueWithStyle = analyzed.cellValues.get("测试主R");
                        cell.setCellValue(cellValueWithStyle.getCellValue());
                        setCellStyle(workbook, cell, cellValueWithStyle.getCellBackgroundColor(), cellValueWithStyle.getFontColor());
                        break;
                    case "产品主R":
                        cellValueWithStyle = analyzed.cellValues.get("产品主R");
                        cell.setCellValue(cellValueWithStyle.getCellValue());
                        setCellStyle(workbook, cell, cellValueWithStyle.getCellBackgroundColor(), cellValueWithStyle.getFontColor());
                        break;
                    case "是否QA测试":
                        cellValueWithStyle = analyzed.cellValues.get("是否QA测试");
                        cell.setCellValue(cellValueWithStyle.getCellValue());
                        setCellStyle(workbook, cell, cellValueWithStyle.getCellBackgroundColor(), cellValueWithStyle.getFontColor());
                        break;
                    default:
                        break;
                }
                j++;
            }
        }

        // 保存Excel文件
        String excelPath = "/Users/zhuxiuwei/Documents/Ones填写分析-需求维度"+ "-" + System.currentTimeMillis() + ".xlsx";
        try (FileOutputStream outputStream = new FileOutputStream(excelPath)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("保存excel文件失败到路径失败：" + excelPath + ", 程序异常退出。");
            System.exit(0);
        }
        return excelPath;
    }

    //随机挑选一个背景色
    private short getColorForString(String input) {
        IndexedColors[] colors = {
                IndexedColors.AQUA,
                IndexedColors.BLACK,
                IndexedColors.BLUE,
                IndexedColors.BLUE_GREY,
                IndexedColors.BRIGHT_GREEN,
                IndexedColors.BLUE_GREY,
                IndexedColors.GREY_40_PERCENT,
                IndexedColors.DARK_TEAL,
                IndexedColors.SEA_GREEN,
                IndexedColors. DARK_GREEN,
                IndexedColors. OLIVE_GREEN,
                IndexedColors.BROWN,
                IndexedColors.PLUM,
                IndexedColors.INDIGO,
                // ... 添加更多颜色，如果需要
        };
        // 使用字符串的哈希码来选择颜色
        int index = Math.abs(input.hashCode()) % colors.length;
        return colors[index].getIndex();
    }

    private void setCellStyle(Workbook workbook, Cell cell, short backgroundColor, short fontColor){
        // 创建一个字体对象并设置字体颜色
        Font font = workbook.createFont();
        font.setColor(fontColor); // 设置字体颜色
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(backgroundColor); // 设置背景颜色
        style.setFillPattern(CellStyle.SOLID_FOREGROUND); // 设置填充模式
        style.setFont(font); // 将字体应用到样式中
        cell.setCellStyle(style); // 应用样式到单元格
    }

    /**
     * 组织维度的统计信息写excel
     * @param orgLevelStatistics
     * @return excel地址
     */
    public String saveOrgLevelStatisticsToExcel(Map<String, Map<String, OrgLevelStatistics>> orgLevelStatistics){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");
        // 创建表头
        Row headerRow = sheet.createRow(0);
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("组织");
        setCellStyle(workbook, headerCell, IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex(), IndexedColors.BLACK.getIndex());
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("类型");
        setCellStyle(workbook, headerCell, IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex(), IndexedColors.BLACK.getIndex());
        List<String> dataHeaders = Arrays.asList("提出时间","需求澄清日期","PRD终审通过时间","技术评审结束时间",
                "预计上线时间","实际提测时间","实际测试结束时间","实际上线时间","技术主R","测试主R","产品主R","是否QA测试","整体填写率");
        for (int i = 0; i < dataHeaders.size(); i++) {
            headerCell = headerRow.createCell(i + 2);
            headerCell.setCellValue(dataHeaders.get(i));
            setCellStyle(workbook, headerCell, IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex(), IndexedColors.BLACK.getIndex());
        }

        //填充数据
        AtomicInteger rowIdx = new AtomicInteger();
        orgLevelStatistics.forEach((orgName, fieldLevelStatistics) -> {
            int columnIdx = 0;
            Map<String, OrgLevelStatistics> filedLevelStatistic = orgLevelStatistics.get(orgName);
            Row dataRow = sheet.createRow(rowIdx.incrementAndGet());

            //「应该填写数」 行
            Cell cell = dataRow.createCell(columnIdx++);
            cell.setCellValue(orgName);
            setCellStyle(workbook, cell, getColorForString(orgName), IndexedColors.WHITE.getIndex());
            cell = dataRow.createCell(1);
            cell.setCellValue("应该填写数");
            for (String columnName: dataHeaders){
                cell = dataRow.createCell(++columnIdx);
                cell.setCellValue(fieldLevelStatistics.get(columnName).shouldFillCount);
            }

            //「实际填写数」 行
            columnIdx = 0;
            dataRow = sheet.createRow(rowIdx.incrementAndGet());
            cell = dataRow.createCell(columnIdx++);
            setCellStyle(workbook, cell, getColorForString(orgName), IndexedColors.WHITE.getIndex());
            cell.setCellValue(orgName);
            cell = dataRow.createCell(1);
            cell.setCellValue("实际填写数");
            for (String columnName: dataHeaders){
                cell = dataRow.createCell(++columnIdx);
                cell.setCellValue(fieldLevelStatistics.get(columnName).actualFillCount);
            }

            //「填写占比」 行
            columnIdx = 0;
            dataRow = sheet.createRow(rowIdx.incrementAndGet());
            cell = dataRow.createCell(columnIdx++);
            setCellStyle(workbook, cell, getColorForString(orgName), IndexedColors.WHITE.getIndex());
            cell.setCellValue(orgName);
            cell = dataRow.createCell(1);
            cell.setCellValue("填写占比");
            for (String columnName: dataHeaders){
                cell = dataRow.createCell(++columnIdx);
                // 创建样式。设置数据格式为百分比，保留一位小数。且为粗体字。
                Font font = workbook.createFont();
                font.setBoldweight(Font.BOLDWEIGHT_BOLD);
                CellStyle style = workbook.createCellStyle();
                style.setFont(font);
                DataFormat format = workbook.createDataFormat();
                style.setDataFormat(format.getFormat("0.0%"));
                cell.setCellStyle(style);
                cell.setCellValue(fieldLevelStatistics.get(columnName).fillRate);
            }
        });
        String excelPath = "/Users/zhuxiuwei/Documents/Ones填写分析-组织维度"+ "-" + System.currentTimeMillis() + ".xlsx";
        try (FileOutputStream outputStream = new FileOutputStream(excelPath)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("保存excel文件失败到路径失败：" + excelPath + ", 程序异常退出。");
            System.exit(0);
        }
        return excelPath;
    }
}
