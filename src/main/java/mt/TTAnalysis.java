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
 * TT分析。
 */
public class TTAnalysis {

    private PrintTable printTable = new PrintTable();

    //分析入口
    public void analyze(String excelFilePath){
        List<Ticket> tickets = getTicketFromExcel(excelFilePath);

        //分析归档类型数量
        analyzeArchiveCount(tickets);

        //按照维度分析
        analyzeByDimesions(tickets, "全部");
        String[] ranges = {"使用咨询", "配置需求", "缺陷", "产品需求", "其他"};
        for (String range: ranges) {
            analyzeByDimesions(tickets.stream().filter(x -> x.getArchive() != null
                    && x.getArchive().getL1() != null && x.getArchive().getL1().equals(range))
                    .collect(Collectors.toList()), range);
        }

        //分析TT满意度
        analyzeBySatisfaction(tickets);
    }

    //分析归档类型数量
    private void analyzeArchiveCount(List<Ticket> tickets){
        System.out.println("\n按照归档类型分类");
        List<String> nums = new ArrayList<>();
        nums.add(tickets.size() + "");
        nums.add(tickets.stream().filter(x -> x.getArchive() != null && x.getArchive().getL1() != null && x.getArchive().getL1().equals("使用咨询")).count() + "");
        nums.add(tickets.stream().filter(x -> x.getArchive() != null && x.getArchive().getL1() != null && x.getArchive().getL1().equals("配置需求")).count() + "");
        nums.add(tickets.stream().filter(x -> x.getArchive() != null && x.getArchive().getL1() != null && x.getArchive().getL1().equals("缺陷")).count() + "");
        nums.add(tickets.stream().filter(x -> x.getArchive() != null && x.getArchive().getL1() != null && x.getArchive().getL1().equals("产品需求")).count() + "");
        nums.add(tickets.stream().filter(x -> x.getArchive() != null && x.getArchive().getL1() != null && x.getArchive().getL1().equals("其他")).count() + "");
        nums.add(tickets.stream().filter(x -> x.getArchive() == null || x.getArchive().getL1() == null).count() + "");
        printTable.printTable(Arrays.asList(new String[] {"总数","使用咨询","配置需求","缺陷","产品需求","其他","未分类"}), nums);
    }

    //按照维度分析
    private void analyzeByDimesions(List<Ticket> tickets, String range){
        System.out.println("\n======================== 分析范围：" + range + " ========================");
        System.out.println("发起人组织架构分布情况-具体到BG");
        TreeMap<String, List<Ticket>> m = tickets.stream().collect(
                groupingBy(ticket -> ticket.getCreateByOrg().getL1()
                        , TreeMap::new, Collectors.toList()));
        for(String k: m.keySet()){
            System.out.println(k + ":" + m.get(k).size());
        }

        System.out.println("\n发起人组织架构分布情况-具体到BU");
        m = tickets.stream().collect(
                groupingBy(ticket -> ticket.getCreateByOrg().getL1() + "-" + ticket.getCreateByOrg().getL2()
                        , TreeMap::new, Collectors.toList()));
        for(String k: m.keySet()){
            System.out.println(k + ":" + m.get(k).size());
        }

        System.out.println("\n按照模块分类");
        m = tickets.stream().collect(
                groupingBy(ticket -> ticket.getModule(), TreeMap::new, Collectors.toList()));
        for(String k: m.keySet()){
            System.out.println(k + ":" + m.get(k).size());
        }
    }

    //分析TT满意度
    private void analyzeBySatisfaction(List<Ticket> tickets){
        System.out.println("\n======================== 分析满意度 ========================");
        List<String> nums = new ArrayList<>();
        nums.add(tickets.stream().filter(x -> x.getSatisfaction().equals("满意")).count() + "");
        nums.add(tickets.stream().filter(x -> x.getSatisfaction().equals("一般")).count() + "");
        nums.add(tickets.stream().filter(x -> x.getSatisfaction().equals("不满意")).count() + "");
        nums.add(tickets.stream().filter(x -> x.getSatisfaction().equals("NULL")).count() + "");
        printTable.printTable(Arrays.asList(new String[] {"满意","一般","不满意","未评价"}), nums);

        System.out.println("\n满意TT发起人组织架构分布情况-具体到BG");
        TreeMap<String, List<Ticket>> m = tickets.stream().filter(x -> x.getSatisfaction().equals("满意"))
            .collect(
                groupingBy(ticket -> ticket.getCreateByOrg().getL1()
                        , TreeMap::new, Collectors.toList()));
        for(String k: m.keySet()){
            System.out.println(k + ":" + m.get(k).size());
        }

        System.out.println("\n不满意TT发起人组织架构分布情况-具体到BG");
        m = tickets.stream().filter(x -> x.getSatisfaction().equals("一般") || x.getSatisfaction().equals("不满意") )
                .collect(
                        groupingBy(ticket -> ticket.getCreateByOrg().getL1()
                                , TreeMap::new, Collectors.toList()));
        for(String k: m.keySet()){
            System.out.println(k + ":" + m.get(k).size());
        }
    }


    //从excel解析TT
    private List<Ticket> getTicketFromExcel(String filePath) {
        List<Ticket> res = new ArrayList<>();
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
                String id = row.getCell(0) == null ? "NULL": row.getCell(0).getStringCellValue();
                if(id.equals("ticket的id"))  //跳过第一行
                    continue;
                String module = row.getCell(1) == null ? "NULL": row.getCell(1).getStringCellValue();
                String title = row.getCell(2) == null ? "NULL": row.getCell(2).getStringCellValue();
                String type = row.getCell(3) == null ? "NULL": row.getCell(3).getStringCellValue();
                String assignTo = row.getCell(7) == null ? "NULL": row.getCell(7).getStringCellValue();
                String createByOrg = row.getCell(13) == null ? "NULL": row.getCell(13).getStringCellValue();
                String onesLike = row.getCell(22) == null ? "NULL": row.getCell(22).getStringCellValue();
                String satisfaction = row.getCell(31) == null ? "NULL": row.getCell(31).getStringCellValue();
                String solution = row.getCell(33) == null ? "NULL": row.getCell(33).getStringCellValue();
                String content = row.getCell(34) == null ? "NULL": row.getCell(34).getStringCellValue();
                String archive = row.getCell(41) == null ? "NULL": row.getCell(41).getStringCellValue();
                Ticket ticket = new Ticket(id, module, title, type, assignTo, createCreateByOrgFromStr(createByOrg),
                        onesLike, satisfaction, solution, content,  createArchiveFromStr(archive));
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
        System.out.println(res.size());
        return res;
    }

    //读取excel字符串，生成OrgSeq
    private OrgSeq createCreateByOrgFromStr(String str){
        if(str == null)
            return null;
        else {
            String[] orgs = str.split("-");
            if(orgs.length >= 3){
                for (int i = 2; i < orgs.length; i++) {
                    orgs[i - 2] = orgs[i];
                }
                return new OrgSeq(orgs.length - 2, orgs);
            }
            else
                return null;
        }
    }

    //读取excel字符串，生成TicketArchive
    private TicketArchive createArchiveFromStr(String str){
        if(str == null)
            return null;
        else {
            String[] orgs = str.split("-");
            if(orgs.length >= 2){
                for (int i = 1; i < orgs.length; i++) {
                    orgs[i - 1] = orgs[i];
                }
                return new TicketArchive(orgs.length - 1, orgs);
            }
            else
                return null;
        }
    }

    public static void main(String[] args) {
        //文件示例见TTAnalysis.zip
        String filePath = "/Users/zhuxiuwei/Downloads/TT-zhuxiuwei-0329_0411.xlsx";
        new TTAnalysis().analyze(filePath);


//        PrintTable printTable = new PrintTable();
//
//        List<List<String>> contents = new ArrayList<>();
//        List<String> header = new ArrayList<>();
//        header.add("12312312zz");
//        header.add("哈萨");
//        contents.add(header);
//
//        List<String> r1 = new ArrayList<>();
//        r1.add("12312312zzasd93933");
//        r1.add("哈萨啊实打实搭对撒哈萨啊实打实搭对撒多发顺丰的啊哈萨啊实打实搭对撒多发顺丰的啊哈萨啊实打实搭对撒多发顺丰的啊哈萨啊实打实搭对撒多发顺丰的啊多发顺丰的啊");
//        contents.add(r1);
//
//        List<String> r2 = new ArrayList<>();
//        r2.add("as");
//        r2.add("a安顿 ");
//        contents.add(r2);
//
//        printTable.printTable(contents);
    }
}
