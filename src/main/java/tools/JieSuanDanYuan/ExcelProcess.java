package tools.JieSuanDanYuan;

import tools.JieSuanDanYuan.bean.CostDetail;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ExcelProcess {
    /**
     * 读excel，多行的值，取唯一一个
     * @param originalText
     */
    public static void getSingleValeFromOneColumn(String originalText){
        String[] split = originalText.split("\n");
//        System.out.println(Arrays.toString(split));
        Set<String> dedup = new TreeSet<>();
        for (String app: split){
            dedup.add(app);
        }
        for (String app: dedup){
            System.out.println(app);
        }
    }

    //处理财务同事给的excel
    public  static void processExcel1() throws FileNotFoundException {

        String bangongApp = "123门户,DOCS文档服务,electron更新服务,OA,Office文档模板服务,url跳转大象小程序映射,WPS文档,办公开放平台,大蒜,大象,大象企业服务,大象搜索,大象应用,大象预览,大象桌面端,动态,工卡系统,话题,即时通讯,结衣,快搭 一体化审批流程搭建平台,快搭零代码应用搭建,流程平台,美团问卷,内容中台,企业管理后台,视讯,微安,无线访客接入服务,项目管理,学城,一体化卡片,云盘,制度系统";
        Set<String> bangongs = new HashSet<>();
        String[] bangongApps = bangongApp.split(",");
        for (String app: bangongApps)
            bangongs.add(app);
//        System.out.println(bangongs.size());    //34


        String file = "/Volumes/文枢工作空间/未命名文件夹/企业平台服务-成本明细9月 2.csv";
        Scanner sc = new Scanner(new File(file));
        Map<String, Double> appPrice = new TreeMap();
        String not = "";
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            String[] split = line.split(",");
            String app = split[0].trim();

            if(bangongs.contains(app)) {
                double price = Double.parseDouble(split[5]);
                double oldPrice = appPrice.getOrDefault(app, 0.0);
                price += oldPrice;
                appPrice.put(app, price);
            }else {
//                System.out.println(app + " 00" + line);
            }
        }
        appPrice.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + "," + entry.getValue() );
        });
    }

    //处理我自己导出的excel
    public  static void processExcel2() throws FileNotFoundException {
        String file = "/Volumes/文枢工作空间/未命名文件夹/企业平台服务-办公效率账单-appkey(2024-10).csv";
        Scanner sc = new Scanner(new File(file));
        Map<String, Double> appPrice = new TreeMap();
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            String[] split = line.split(",");
            String app = split[2].trim();
            double price = 0.0;
            try {
                String priceStr = split[1].replace(",", "");
                price = Double.parseDouble(priceStr);
            }catch (Exception e){}
            double oldPrice = appPrice.getOrDefault(app, 0.0);
            price += oldPrice;
            appPrice.put(app, price);
        }
        appPrice.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + "," + entry.getValue() );
        });

    }

    //按团队，生成开销情况
    public static void processCostByTeam() throws FileNotFoundException {
        String file = "/Volumes/文枢工作空间/未命名文件夹/222-企业平台服务-成本明细9月.csv";
        Scanner sc = new Scanner(new File(file));
        Map<String, CostDetail> teamCost = new TreeMap();
        sc.nextLine();  //跳过第一行header
        while (sc.hasNextLine()){
            String[] lines = sc.nextLine().split(",");
            String prod = lines[0].trim();
            String costItem = lines[1].trim();
            double price = Double.parseDouble(lines[3].trim());
            String rdTeam = lines[7].trim();
            CostDetail costDetail = new CostDetail(prod, costItem, price, rdTeam);
            String key = rdTeam + prod;
            CostDetail old = teamCost.get(key);
            if(old != null){
                costDetail.setCost(costDetail.getCost() + old.getCost());
            }
            teamCost.put(key, costDetail);
        }
        teamCost.values().forEach(cost -> {
            System.out.println( cost.getRdTeam() + ","
                + cost.getProd() + ","
                + cost.getCostItem() + ","
                + cost.getCost());
        });
    }

    public static void main(String[] args) throws FileNotFoundException {
//        ExcelProcess.processExcel2();
//        processCostByTeam();
        String rr = "产品\n" +
                "\n" +
                "IM即时通讯\n" +
                "\n" +
                "Oceanus\n" +
                "\n" +
                "SecuritySrv-信息安全\n" +
                "\n" +
                "Hulk\n" +
                "\n" +
                "RDS\n" +
                "\n" +
                "Mafka\n" +
                "\n" +
                "Blade\n" +
                "\n" +
                "Raptor\n" +
                "\n" +
                "S3\n" +
                "\n" +
                "Cellar\n" +
                "\n" +
                "DevTools\n" +
                "\n" +
                "长传\n" +
                "\n" +
                "DTS\n" +
                "\n" +
                "Squirrel\n" +
                "\n" +
                "SCA\n" +
                "\n" +
                "块存储\n" +
                "\n" +
                "Shepherd\n" +
                "\n" +
                "Quake\n" +
                "\n" +
                "SecuritySrv-内容风控\n" +
                "\n" +
                "CDN\n" +
                "\n" +
                "保时洁-人审服务\n" +
                "\n" +
                "Shark\n" +
                "\n" +
                "SecuritySrv-业务安全\n" +
                "\n" +
                "Venus\n" +
                "\n" +
                "Eagle\n" +
                "\n" +
                "S3\n" +
                "\n" +
                "Oceanus\n" +
                "\n" +
                "Venus\n" +
                "\n" +
                "Hulk\n" +
                "\n" +
                "RDS\n" +
                "\n" +
                "物理机\n" +
                "\n" +
                "DTS\n" +
                "\n" +
                "搜索离线平台\n" +
                "\n" +
                "CDN\n" +
                "\n" +
                "VM\n" +
                "\n" +
                "SecuritySrv-信息安全\n" +
                "\n" +
                "Raptor\n" +
                "\n" +
                "Mafka\n" +
                "\n" +
                "Squirrel\n" +
                "\n" +
                "长传\n" +
                "\n" +
                "DevTools\n" +
                "\n" +
                "块存储\n" +
                "\n" +
                "Cellar\n" +
                "\n" +
                "SCA\n" +
                "\n" +
                "Quake\n" +
                "\n" +
                "SecuritySrv-内容风控\n" +
                "\n" +
                "Shepherd\n" +
                "\n" +
                "视觉AI\n" +
                "\n" +
                "Shark\n" +
                "\n" +
                "Oceanus\n" +
                "\n" +
                "Hulk\n" +
                "\n" +
                "RDS\n" +
                "\n" +
                "Squirrel\n" +
                "\n" +
                "SecuritySrv-信息安全\n" +
                "\n" +
                "Raptor\n" +
                "\n" +
                "块存储\n" +
                "\n" +
                "DTS\n" +
                "\n" +
                "DevTools\n" +
                "\n" +
                "SCA\n" +
                "\n" +
                "长传\n" +
                "\n" +
                "Cellar\n" +
                "\n" +
                "Mafka\n" +
                "\n" +
                "Shark\n" +
                "\n" +
                "S3\n" +
                "\n" +
                "Quake\n" +
                "\n" +
                "Venus\n" +
                "\n" +
                "Shepherd\n" +
                "\n" +
                "Eagle\n" +
                "\n" +
                "RDS\n" +
                "\n" +
                "Hulk\n" +
                "\n" +
                "S3\n" +
                "\n" +
                "Oceanus\n" +
                "\n" +
                "DTS\n" +
                "\n" +
                "Raptor\n" +
                "\n" +
                "SecuritySrv-信息安全\n" +
                "\n" +
                "Squirrel\n" +
                "\n" +
                "长传\n" +
                "\n" +
                "DevTools\n" +
                "\n" +
                "Mafka\n" +
                "\n" +
                "SCA\n" +
                "\n" +
                "CDN\n" +
                "\n" +
                "块存储\n" +
                "\n" +
                "Shark\n" +
                "\n" +
                "Shepherd\n" +
                "\n" +
                "Blade\n" +
                "\n" +
                "Oceanus\n" +
                "\n" +
                "Hulk\n" +
                "\n" +
                "Shark\n" +
                "\n" +
                "S3\n" +
                "\n" +
                "SecuritySrv-信息安全\n" +
                "\n" +
                "Raptor\n" +
                "\n" +
                "搜索离线平台\n" +
                "\n" +
                "DTS\n" +
                "\n" +
                "VM\n" +
                "\n" +
                "块存储\n" +
                "\n" +
                "Squirrel\n" +
                "\n" +
                "长传\n" +
                "\n" +
                "Eagle\n" +
                "\n" +
                "Quake\n" +
                "\n" +
                "DevTools\n" +
                "\n" +
                "SCA\n" +
                "\n" +
                "RDS\n" +
                "\n" +
                "Mafka\n" +
                "\n" +
                "Nest\n" +
                "\n" +
                "Cellar\n" +
                "\n" +
                "CDN\n" +
                "\n" +
                "Shepherd\n" +
                "\n" +
                "Hulk\n" +
                "\n" +
                "RDS\n" +
                "\n" +
                "Eagle\n" +
                "\n" +
                "Squirrel\n" +
                "\n" +
                "DTS\n" +
                "\n" +
                "Raptor\n" +
                "\n" +
                "SecuritySrv-信息安全\n" +
                "\n" +
                "块存储\n" +
                "\n" +
                "DevTools\n" +
                "\n" +
                "长传\n" +
                "\n" +
                "Mafka\n" +
                "\n" +
                "Oceanus\n" +
                "\n" +
                "产研协作与洞察工具\n" +
                "\n" +
                "Raptor\n" +
                "\n" +
                "Hulk\n" +
                "\n" +
                "Nest\n" +
                "\n" +
                "Oceanus\n" +
                "\n" +
                "DevTools\n" +
                "\n" +
                "S3\n" +
                "\n" +
                "Quake\n" +
                "\n" +
                "SecuritySrv-信息安全\n" +
                "\n" +
                "RDS\n" +
                "\n" +
                "CDN\n" +
                "\n" +
                "DTS\n" +
                "\n" +
                "块存储\n" +
                "\n" +
                "长传\n" +
                "\n" +
                "SCA\n" +
                "\n" +
                "Venus\n" +
                "\n" +
                "Squirrel\n" +
                "\n" +
                "Oceanus\n" +
                "\n" +
                "S3\n" +
                "\n" +
                "Hulk\n" +
                "\n" +
                "RDS\n" +
                "\n" +
                "Raptor\n" +
                "\n" +
                "Nest\n" +
                "\n" +
                "VM\n" +
                "\n" +
                "SecuritySrv-信息安全\n" +
                "\n" +
                "长传\n" +
                "\n" +
                "DevTools\n" +
                "\n" +
                "块存储\n" +
                "\n" +
                "CDN\n" +
                "\n" +
                "Cellar\n" +
                "\n" +
                "Nest\n" +
                "\n" +
                "Raptor\n" +
                "\n" +
                "SCA\n" +
                "\n" +
                "长传\n" +
                "\n" +
                "RDS\n" +
                "\n" +
                "S3\n" +
                "\n" +
                "Raptor\n" +
                "\n" +
                "S3\n" +
                "\n" +
                "DevTools\n" +
                "\n" +
                "Nest\n" +
                "\n" +
                "RDS\n" +
                "\n" +
                "Hulk\n" +
                "\n" +
                "CDN\n" +
                "\n" +
                "SCA\n" +
                "\n" +
                "块存储\n" +
                "\n" +
                "SecuritySrv-信息安全\n" +
                "\n" +
                "长传\n" +
                "\n" +
                "Venus";
        getSingleValeFromOneColumn(rr);
    }
}
