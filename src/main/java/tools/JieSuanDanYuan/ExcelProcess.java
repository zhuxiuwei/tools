package tools.JieSuanDanYuan;

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
        String file = "/Volumes/文枢工作空间/未命名文件夹/企业平台服务-成本明细9月 2.csv";
        Scanner sc = new Scanner(new File(file));
        while (sc.hasNextLine()){
            String s = sc.nextLine();
            System.out.println(s);
        }
    }

    //处理我自己导出的excel
    public  static void processExcel2(){
        String file = "/Volumes/文枢工作空间/未命名文件夹/企业平台服务-办公效率账单-appkey(2024-09).csv";

    }

    public static void main(String[] args) {
        String old = "ape\n" +
                "ape\n" +
                "ape\n" +
                "ape\n" +
                "ape\n" +
                "ape\n" +
                "ape\n" +
                "ape\n" +
                "ape\n" +
                "ape\n" +
                "ape\n" +
                "ape\n" +
                "ape\n" +
                "ape\n" +
                "ape\n" +
                "ape\n" +
                "ape\n" +
                "ape\n" +
                "ape\n" +
                "ape\n" +
                "ape\n" +
                "ape\n" +
                "ape\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "Citadel\n" +
                "diego\n" +
                "diego\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "DX\n" +
                "dxdesktop\n" +
                "dxenterprise\n" +
                "dxenterprise\n" +
                "dxenterprise\n" +
                "dxenterprise\n" +
                "dxenterprise\n" +
                "dxsearch\n" +
                "dxsearch\n" +
                "dxsearch\n" +
                "dxsearch\n" +
                "dxsearch\n" +
                "dxsearch\n" +
                "dxsearch\n" +
                "dxsearch\n" +
                "dxsearch\n" +
                "dxsearch\n" +
                "dxsearch\n" +
                "dxsearch\n" +
                "dxsearch\n" +
                "dxsearch\n" +
                "dxsearch\n" +
                "electronupdater\n" +
                "electronupdater\n" +
                "enterprisesaas\n" +
                "family\n" +
                "family\n" +
                "Gakki\n" +
                "garlic\n" +
                "guestwifi\n" +
                "http2mrn\n" +
                "IM\n" +
                "kuaida\n" +
                "kuaida\n" +
                "mbox\n" +
                "mbox\n" +
                "mbox\n" +
                "mbox\n" +
                "mbox\n" +
                "mbox\n" +
                "mbox\n" +
                "mbox\n" +
                "mbox\n" +
                "mbox\n" +
                "mbox\n" +
                "mbox\n" +
                "mbox\n" +
                "mbox\n" +
                "mbox\n" +
                "mbox\n" +
                "mbox\n" +
                "mbox\n" +
                "mbox\n" +
                "mbox\n" +
                "mbox\n" +
                "mbox\n" +
                "mbox\n" +
                "mbox\n" +
                "mbox\n" +
                "OA\n" +
                "OA\n" +
                "OA\n" +
                "OA\n" +
                "OA\n" +
                "OA\n" +
                "OA\n" +
                "OA\n" +
                "OA\n" +
                "OA\n" +
                "OA\n" +
                "OA\n" +
                "OA\n" +
                "OA\n" +
                "OA\n" +
                "OA\n" +
                "OA\n" +
                "OA\n" +
                "OA\n" +
                "OA\n" +
                "OA\n" +
                "OA\n" +
                "OA\n" +
                "OA\n" +
                "open\n" +
                "open\n" +
                "PRE\n" +
                "projectmanager\n" +
                "Reich\n" +
                "Rule\n" +
                "staticdocs\n" +
                "staticdocs\n" +
                "staticdocs\n" +
                "survey\n" +
                "survey\n" +
                "survey\n" +
                "survey\n" +
                "survey\n" +
                "survey\n" +
                "topic\n" +
                "universalCard\n" +
                "Vivian\n" +
                "Vivian\n" +
                "workcard\n" +
                "workcard\n" +
                "wpsoffice\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm\n" +
                "xm";

        ExcelProcess.getSingleValeFromOneColumn(old);
    }
}
