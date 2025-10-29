package tools.JieSuanDanYuan;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author zhuxiuwei
 * @date 2025/10/23
 */
public class ExcelProcess25 {

    /**
     * 1、计算企平各团队25年各产品开销金额
     * @return
     */
    public void calQiPingCost() throws FileNotFoundException {
        String file = "/Volumes/WenshuSpace/办公25整体开销-计算占比用.csv";
        Scanner sc = new Scanner(new File(file));

        Map<String, Double> teamProdTotalPrice = new HashMap<>();
        sc.nextLine();  //跳过第一行header
        while (sc.hasNextLine()){
            String[] lines = sc.nextLine().split(",");
            String prod = lines[2].trim();
            String team = getTeamName(lines[3].trim());
            String key = prod  + "," + team;
            double price = Double.parseDouble(lines[1].trim());
            double oldPrice = teamProdTotalPrice.getOrDefault(key, 0.0);
            oldPrice += price;
            teamProdTotalPrice.put(key, oldPrice);
        }


        for (Map.Entry<String, Double> entry : teamProdTotalPrice.entrySet()) {
            System.out.println(entry.getKey() + "," + entry.getValue());
        }
    }

    /**
     * 2、计算企平各团队25年各产品开销金额占比
     * (基于1生成的结果计算)
     * @return
     */
    public void calQiPingZhanBi() throws FileNotFoundException {
        String file = "/Users/zhuxiuwei/Documents/办公25产品团队金额.csv";
        Scanner sc = new Scanner(new File(file));

        //计算每个产品总开销
        Map<String, Double> prodTotalCost = new HashMap<>();
        sc.nextLine();  //跳过第一行header
        while (sc.hasNextLine()){
            String[] lines = sc.nextLine().split(",");
            String prod = lines[0].trim();
            double price = Double.parseDouble(lines[2].trim());
            double oldPrice = prodTotalCost.getOrDefault(prod, 0.0);
            oldPrice += price;
            prodTotalCost.put(prod, oldPrice);
        }

        //计算每个团队开销占比
        StringBuilder sb = new StringBuilder();
        sc = new Scanner(new File(file));
        sc.nextLine();  //跳过第一行header
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            String[] lines = line.split(",");
            double price = Double.parseDouble(lines[2].trim());
            double totalPrice = prodTotalCost.get(lines[0].trim());
            double zhanBi = price / totalPrice;
            sb.append(line).append(",").append(zhanBi).append("\n");
        }
        System.out.println(sb.toString());
    }

    /**
     * 根据是否是办公效率的团队，返回团队名。不是的返回其他。
     * @param team
     * @return
     */
    private String getTeamName(String team){
        Set<String> knownTeams = new HashSet<>();
        knownTeams.add("企业应用研发");
        for (String s : knownTeams) {
            if (team.contains(s)){
                return team.replace("核心本地商业/基础研发平台/企业平台研发部/企业应用研发/", "");
            }
        }
        return "其他";
    }

    public static void main(String[] args) throws FileNotFoundException {
        ExcelProcess25 excelProcess25 = new ExcelProcess25();
//        excelProcess25.calQiPingCost();
        excelProcess25.calQiPingZhanBi();
    }
}
