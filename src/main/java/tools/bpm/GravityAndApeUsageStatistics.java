package tools.bpm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * https://km.sankuai.com/collabpage/2644961405
 * 流程工厂和gravity用户分布统计
 */
public class GravityAndApeUsageStatistics {

    public static void analysis(String csvPath) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(csvPath));
        Map<String, Map<String, Long>> appMap = new HashMap<>();
        while (sc.hasNextLine()){
            String[] line = sc.nextLine().split(",");
            String app = line[0];
            long count = Long.parseLong(line[1]);
            String clientOrg = line[2];
            String[] orgParts = clientOrg.split("/");
            clientOrg = orgParts[2] + "/" + orgParts[3];
//            if(orgParts.length >=5 ){
//                clientOrg = clientOrg + "/" + orgParts[4];
//            }
//            if(orgParts.length >=6 ){
//                clientOrg = clientOrg + "/" + orgParts[5];
//            }
//            if(orgParts.length >=7 ){
//                clientOrg = clientOrg + "/" + orgParts[6];
//            }
//            System.out.println(app + "-" + count + "-" + clientOrg);

            Map<String, Long> orgCountMap = appMap.getOrDefault(app, new HashMap<String, Long>());

            long sum = orgCountMap.getOrDefault(clientOrg, 0L);
            sum += count;
            orgCountMap.put(clientOrg, sum);

            appMap.put(app, orgCountMap);
        }
        //打印
        appMap.forEach((app, kv) -> {
            kv.forEach((org, sum) ->{
                System.out.println(app + "," + org + "," + sum);
            });
        });
    }
    public static void main(String[] args) throws FileNotFoundException {
        String csvPath = "/Users/zhuxiuwei/dev/workplace/tools/src/main/resources/mt/gravity&流程用量.csv";
        GravityAndApeUsageStatistics.analysis(csvPath);
    }
}
