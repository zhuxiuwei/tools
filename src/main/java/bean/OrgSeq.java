package bean;

/**
 * 组织序列，如 基础研发平台-研发质量及效率部-PMIS研发组
 */
public class OrgSeq {
    private int totalLevel; //总层数
    private String l1;  //第1层，BG
    private String l2;  //第2层，BU
    private String l3;  //第3层
    private String l4;
    private String l5;

    public OrgSeq(String l1, String l2, String l3, String l4, String l5) {
        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
        this.l4 = l4;
        this.l5 = l5;
    }


    public OrgSeq(int totalLevel, String ... levels) {
        this.totalLevel = totalLevel;
        for (int i = 1; i <= totalLevel; i++) {
            switch (i){
                case 1:
                    this.l1 = levels[i-1];
                    break;
                case 2:
                    this.l2 = levels[i-1];
                    break;
                case 3:
                    this.l3 = levels[i-1];
                    break;
                case 4:
                    this.l4 = levels[i-1];
                    break;
                case 5:
                    this.l5 = levels[i-1];
                    break;
                default:
                    break;
            }
        }
    }

    public int getTotalLevel() {
        return totalLevel;
    }

    public void setTotalLevel(int totalLevel) {
        this.totalLevel = totalLevel;
    }

    public String getL1() {
        return l1;
    }

    public void setL1(String l1) {
        this.l1 = l1;
    }

    public String getL2() {
        return l2;
    }

    public void setL2(String l2) {
        this.l2 = l2;
    }

    public String getL3() {
        return l3;
    }

    public void setL3(String l3) {
        this.l3 = l3;
    }

    public String getL4() {
        return l4;
    }

    public void setL4(String l4) {
        this.l4 = l4;
    }

    public String getL5() {
        return l5;
    }

    public void setL5(String l5) {
        this.l5 = l5;
    }
    public String toString() {
        if(totalLevel <= 0)
            return "NULL";
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= totalLevel; i++) {
            switch (i){
                case 1:
                    sb.append(l1);
                    sb.append("-");
                    break;
                case 2:
                    sb.append(l2);
                    sb.append("-");
                    break;
                case 3:
                    sb.append(l3);
                    sb.append("-");
                    break;
                case 4:
                    sb.append(l4);
                    sb.append("-");
                    break;
                case 5:
                    sb.append(l5);
                    sb.append("-");
                    break;
                default:
                    break;
            }
        }
        String res = sb.toString();
        return res.substring(0, res.length() - 1);
    }

}
