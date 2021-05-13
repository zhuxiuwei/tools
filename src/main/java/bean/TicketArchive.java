package bean;

/**
 * 问题归档。最多3层。
 */
public class TicketArchive {
    private int totalLevel; //总层数
    private String l1;
    private String l2;
    private String l3;

    public TicketArchive(String l1, String l2, String l3) {
        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
    }

    public TicketArchive(int totalLevel, String ... levels) {
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

    @Override
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
                default:
                    break;
            }
        }
        String res = sb.toString();
        return res.substring(0, res.length() - 1);
    }
}
