package tools.case_data_analysis_230728;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Process {

    /**
     * 处理case活跃用户原始文件：active_user_raw.txt
     * @param filePath 活跃用户原始文件路径
     * @return  去重后的活跃用户set
     * @throws FileNotFoundException
     */
    private static Set<String> processActiveUserRawTxt(String filePath) throws FileNotFoundException {
        Set res = new HashSet<String>();
        Scanner sc = new Scanner(new File(filePath));
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            if(line.contains(",")){
                String[] misIds = line.split(",");
                Arrays.stream(misIds).forEach(x -> res.add(x));
            }else {
                res.add(line.trim());
            }
        }
//        System.out.println(res.size());
        return res;
    }

    /**
     * 计算组织用户，有多少出现在活跃用户列表里的占比。
     * 活跃用户列表是processActiveUserRawTxt函数的返回结果。
     * @param activeUserRawTxtPath
     * @param orgUerListFilePath
     * @return
     * @throws FileNotFoundException
     */
    public static double calOrgUserInActiveUserListPercentage(String activeUserRawTxtPath, String orgUerListFilePath) throws FileNotFoundException {
        //组织用户
        Scanner sc = new Scanner(new File(orgUerListFilePath));
        Set<String> orgUserSet = new HashSet<String>();
        while (sc.hasNextLine()){
            orgUserSet.add(sc.nextLine());
        }
        System.out.println("组织PM数: " + orgUserSet.size());

        //活跃用户
        Set<String> activeUserSet = processActiveUserRawTxt(activeUserRawTxtPath);

        //计算两个set的交集
        Set<String> retainSet = new HashSet<String>();
        retainSet.addAll(orgUserSet);
        retainSet.retainAll(activeUserSet);

        System.out.println("组织下活跃PM数: " + retainSet.size());

        return (double)retainSet.size()/orgUserSet.size();
    }

    public static void main(String[] args) throws FileNotFoundException {
//        System.out.println("到综产+运");
//        System.out.println(calOrgUserInActiveUserListPercentage("/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/active_user_raw.txt",
//                "/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/daozong-pm-list.txt"));
//        System.out.println("骑行产+运");
//        System.out.println(calOrgUserInActiveUserListPercentage("/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/active_user_raw.txt",
//                "/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/qixing-pm-list.txt"));
//        System.out.println("餐饮SaaS产+运");
//        System.out.println(calOrgUserInActiveUserListPercentage("/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/active_user_raw.txt",
//                "/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/saas-pm-list.txt"));
//
//        System.out.println("到综产品only");
//        System.out.println(calOrgUserInActiveUserListPercentage("/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/active_user_raw.txt",
//                "/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/daozong-pm_only-list.txt"));
//        System.out.println("到综运营only");
//        System.out.println(calOrgUserInActiveUserListPercentage("/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/active_user_raw.txt",
//                "/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/daozong-operations_only-list.txt"));
//
//        System.out.println("骑行产品only");
//        System.out.println(calOrgUserInActiveUserListPercentage("/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/active_user_raw.txt",
//                "/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/qixing-pm_only-list.txt"));
//        System.out.println("骑行运营only");
//        System.out.println(calOrgUserInActiveUserListPercentage("/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/active_user_raw.txt",
//                "/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/qixing-operations_only-list.txt"));
//
//        System.out.println("餐饮SaaS产品only");
//        System.out.println(calOrgUserInActiveUserListPercentage("/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/active_user_raw.txt",
//                "/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/saas-pm_only-list.txt"));
//        System.out.println("餐饮SaaS运营only");
//        System.out.println(calOrgUserInActiveUserListPercentage("/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/active_user_raw.txt",
//                "/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/saas-operations_only-list.txt"));
//
//        System.out.println("到综事业部平台产品部 产品+运营");
//        System.out.println(calOrgUserInActiveUserListPercentage("/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/active_user_raw.txt",
//                "/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/到综事业部平台产品部-pm-list.txt"));
//        System.out.println("到综事业部平台产品部 产品only");
//        System.out.println(calOrgUserInActiveUserListPercentage("/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/active_user_raw.txt",
//                "/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/到综事业部平台产品部-pm_only-list.txt"));
//        System.out.println("到综事业部平台产品部 运营only");
//        System.out.println(calOrgUserInActiveUserListPercentage("/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/active_user_raw.txt",
//                "/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/到综事业部平台产品部-operations_only-list.txt"));
//
//        System.out.println("骑行产品部（只有产品，无运营）");
//        System.out.println(calOrgUserInActiveUserListPercentage("/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/active_user_raw.txt",
//                "/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/骑行产品部-pm-list.txt"));
//
//        System.out.println("骑行产品部（只有产品，无运营）");
//        System.out.println(calOrgUserInActiveUserListPercentage("/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/active_user_raw.txt",
//                "/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/骑行产品部-pm-list.txt"));
//        System.out.println("骑行产品部-城市运营产品中心（只有产品，无运营）");
//        System.out.println(calOrgUserInActiveUserListPercentage("/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/active_user_raw.txt",
//                "/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/骑行产品部城市运营产品中心-pm-list.txt"));

        System.out.println("门票度假和交通事业部");
        System.out.println(calOrgUserInActiveUserListPercentage("/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/active_user_raw.txt",
                "/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/门票度假和交通事业部-pm-list.txt"));
        System.out.println("门票度假和交通事业部-产品和运营部");
        System.out.println(calOrgUserInActiveUserListPercentage("/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/active_user_raw.txt",
                "/Users/zhuxiuwei/dev/workplace/demo/mt_tools/src/main/java/case_data_analysis_230728/门票度假和交通事业部-产品和运营部-pm-list.txt"));
    }
}