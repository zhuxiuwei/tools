package bean;

/**
 * TT
 */
public class Ticket {
    private String id;
    private String module;  //模块
    private String title;  //标题
    private String type;  //类型
    private String assignTo;  //指派人
    private OrgSeq createByOrg;  //发起人组织
    private String onesLike;  //Ones链接
    private String satisfaction;  //满意度
    private String solution;  //解决方案
    private String content;  //TT内容
    private TicketArchive archive;  //问题归档

    public Ticket(String id, String module, String title, String type, String assignTo, OrgSeq createByOrg, String onesLike, String satisfaction, String solution, String content, TicketArchive archive) {
        this.id = id;
        this.module = module;
        this.title = title;
        this.type = type;
        this.assignTo = assignTo;
        this.createByOrg = createByOrg;
        this.onesLike = onesLike;
        this.satisfaction = satisfaction;
        this.solution = solution;
        this.content = content;
        this.archive = archive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(String assignTo) {
        this.assignTo = assignTo;
    }

    public OrgSeq getCreateByOrg() {
        return createByOrg;
    }

    public void setCreateByOrg(OrgSeq createByOrg) {
        this.createByOrg = createByOrg;
    }

    public String getOnesLike() {
        return onesLike;
    }

    public void setOnesLike(String onesLike) {
        this.onesLike = onesLike;
    }

    public String getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(String satisfaction) {
        this.satisfaction = satisfaction;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TicketArchive getArchive() {
        return archive;
    }

    public void setArchive(TicketArchive archive) {
        this.archive = archive;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", module='" + module + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", assignTo='" + assignTo + '\'' +
                ", createByOrg='" + createByOrg + '\'' +
                ", onesLike='" + onesLike + '\'' +
                ", satisfaction='" + satisfaction + '\'' +
                ", solution='" + solution + '\'' +
                ", content='" + content + '\'' +
                ", archive=" + archive +
                '}';
    }
}
