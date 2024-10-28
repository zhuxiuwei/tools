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

    //看TT与流程团队，分产品的情况
    public static void processCostAtTTAndProcessTeam() throws FileNotFoundException {
        String file = "/Volumes/文枢工作空间/企业平台服务-成本明细(2024-01)/企业平台服务-成本明细(2024-01)-1.csv";
        Scanner sc = new Scanner(new File(file));
        Map<String, CostDetail> teamCost = new TreeMap();
        sc.nextLine();  //跳过第一行header
        while (sc.hasNextLine()){
            String[] lines = sc.nextLine().split(",");
            String prod = lines[0].trim();
            String costItem = lines[1].trim();
            String app = lines[5].trim();
            double price = Double.parseDouble(lines[3].trim());
            String rdTeam = lines[7].trim();
            if(rdTeam.contains("流程和TT研发组")) {
                CostDetail costDetail = new CostDetail(prod, costItem, price, rdTeam, app);
                String key = prod + app;
                CostDetail old = teamCost.get(key);
                if (old != null) {
                    costDetail.setCost(costDetail.getCost() + old.getCost());
                }
                teamCost.put(key, costDetail);
            }
        }
        teamCost.values().forEach(cost -> {
            System.out.println(cost.getApp() + ","
                    + cost.getProd() + ","
                    + cost.getCost());
        });
    }

    //基础服务团队，cdn s3 venus的情况
    public static void processCostAtJiChuFuWuTeam() throws FileNotFoundException {
        String file = "/Volumes/文枢工作空间/企业平台服务-成本明细(2024-01)/企业平台服务-成本明细(2024-06)-1.csv";
        Scanner sc = new Scanner(new File(file));
        Map<String, CostDetail> teamCost = new TreeMap();
        sc.nextLine();  //跳过第一行header
        while (sc.hasNextLine()){
            String[] lines = sc.nextLine().split(",");
            if(lines.length != 18) {
                System.err.println(lines.length);
            }
            String rdTeam = lines[14].trim();
            if(rdTeam.contains("办公效率后端/基础服务")) {
                String prod = lines[0].trim();
                String costItem = lines[1].trim();
//                String app = lines[12].trim();
                double price = Double.parseDouble(lines[7].trim());
                CostDetail costDetail = new CostDetail(prod, costItem, price, rdTeam);
//                String key = prod + app;
                String key = prod;
                CostDetail old = teamCost.get(key);
                if (old != null) {
                    costDetail.setCost(costDetail.getCost() + old.getCost());
                }
                if(prod.equalsIgnoreCase("S3")
                        || prod.equalsIgnoreCase("Venus")
                        || prod.equalsIgnoreCase("CDN"))
                    teamCost.put(key, costDetail);
            }
        }
        teamCost.values().forEach(cost -> {
            System.out.println(cost.getProd() + ","
                    + cost.getCost());
        });
    }

    //计算各产品或team，在各开销项的占比
    public static void calPercentByProdOrTeam() throws FileNotFoundException {
        String file = "/Volumes/文枢工作空间/未命名文件夹/办公整体-计算占比用.csv";
        Scanner sc = new Scanner(new File(file));

        //计算每项的总和
        Map<String, Double> costItemTotalPrice = new HashMap<>();
        sc.nextLine();  //跳过第一行header
        while (sc.hasNextLine()){
            String[] lines = sc.nextLine().split(",");
            String costItem = lines[1].trim();
            double price = Double.parseDouble(lines[2].trim());
            double oldPrice = costItemTotalPrice.getOrDefault(costItem, 0.0);
            oldPrice += price;
            costItemTotalPrice.put(costItem, oldPrice);
        }

        //计算每项每组的百分比
        sc = new Scanner(new File(file));
        //计算每项的总和
        sc.nextLine();  //跳过第一行header
        while (sc.hasNextLine()){
            String[] lines = sc.nextLine().split(",");
            String app = lines[0];
            String costItem = lines[1].trim();
            double price = Double.parseDouble(lines[2].trim());
            double percent = price/costItemTotalPrice.get(costItem);
            System.out.println(app+","+costItem+","+percent);
        }

    }

    //根据9月*12，算每个产品全年初始预估
    public static void calYearCostEstimateBySeptCost() throws FileNotFoundException {
        String file = "/Volumes/文枢工作空间/未命名文件夹/2222.csv";
        Scanner sc = new Scanner(new File(file));

        Map<String, Double> septCost = new HashMap<>();
        Map<String, Double> costItemTotalPrice = new HashMap<>();
        sc.nextLine();  //跳过第一行header
        while (sc.hasNextLine()){
            String[] lines = sc.nextLine().split(",");
            String costItem = lines[0].trim();
            double price = Double.parseDouble(lines[1].trim());
            double oldPrice = costItemTotalPrice.getOrDefault(costItem, 0.0);
            oldPrice += price;
            costItemTotalPrice.put(costItem, oldPrice);
        }
        costItemTotalPrice.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + ","
                    + entry.getValue()*12);
        });
    }

    public static void main(String[] args) throws FileNotFoundException {
//        ExcelProcess.processExcel2();
//        processCostByTeam();
//        processCostAtTTAndProcessTeam();
//        processCostAtJiChuFuWuTeam();
//        calPercentByProdOrTeam();
        calYearCostEstimateBySeptCost();
    }
}
