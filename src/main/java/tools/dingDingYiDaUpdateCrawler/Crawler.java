package tools.dingDingYiDaUpdateCrawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {
    public static void crawl(String path) {
        String[] pathList = path.split("\n");
        String host = "https://docs.aliwork.com";

        String regex = "href=\"([^\"]*)\"";
        Pattern pattern = Pattern.compile(regex);
        for(String rawUrl: pathList){
//            System.out.println(rawUrl);
            Matcher matcher = pattern.matcher(rawUrl);
            if (matcher.find()) {
                String href = host + matcher.group(1);
//                System.out.println("提取到的href值为：" + href);
                doCrawl(href);
            }
        }
    }

    public static void doCrawl(String path){
        try {
            // 连接到指定的 URL 并获取文档对象
            Document doc = Jsoup.connect(path).get();

            //标题：
            String title = doc.title();
            System.out.println("更新时间:" + title);

            //内容：
            Element paragraphs = doc.select("article").first();
            System.out.println("更新内容:" + paragraphs.text());
            System.out.println("");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String paths = "\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/gslzl3trmm7hgvss\">2024.12.03 版本更新 - 应用分发能力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/qyfp7rbmmdsip1mf\">2024.12.02 版本更新 - 国际化能力发布</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/aws16vcqrbqgtb1s\">2024.11.28 版本更新 - 精品应用能力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/qp9cs6imvuy32miu\">2024.11.12 版本更新 - 精品应用能力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/vfokboz2flkpob4s\">2024.11.11 版本更新 - 专属宜搭升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/ysg1gw8gp371hk1w\">2024.11.08 版本更新 - 全新表单组件发布</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/yb0cagc5eg8c2g0m\">2024.11.05 版本更新 - 全新聚合表上线</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/ttkak7s4ubbgdg3u\">2024.11.01 版本更新 - 基础产品能力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/bsgnylgis2wvnh33\">2024.10.30 版本更新 - 新增JS API</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/au4nqntxttt30ad3\">2024.10.29 版本更新 - 全新预算费控组件发布</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/adqfg9eohxlpbqsa\">2024.10.17 版本更新 - 专属宜搭能力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/zzwx3h3uvf1pv9c2\">2024.09.20 版本更新 - 应用分发能力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/ltqv7hgrswn7ip0k\">2024.09.05 版本更新 - 宜搭精品应用体验优化</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/edwi9222ghl12kns\">2024.09.02 版本更新 - 宜搭产品力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/nxafqqr2qgtvagyi\">2024.08.29 版本更新 - 新增银企支付组件</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/rwhuhgerc6aekftn\">2024.08.28 版本更新 - 宜搭 AI 能力优化</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/czprp050d9vl08qx\">2024.08.22 版本更新 - 专属宜搭能力优化</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/xizzf5c87rkwy2qb\">2024.08.21 版本更新 - 宜搭智能服务助手升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/rshi4panh5vx5eww\">2024.08.20 版本更新 - 精选应用能力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/szsht2ylm8ieue6l\">2024.08.15 版本更新 - 全新钉钉伴侣发布</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/arb61377ywvwxt77\">2024.07.16 版本更新 - 应用发布到钉钉日历插件中心</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/davyas34umd05z0t\">2024.07.04 版本更新 - 专属数字工作空间</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/dxddprynec8q3iav\">2024.07.01 版本更新 - 宜搭 AI 新增主动播报功能</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/egnsogi3gmy0ntsb\">2024.06.20 版本更新 - 基础能力优化</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/ivkloxggy716qbnv\">2024.06.15 版本更新 - 新增智能发票组件</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/cggx22gwzfbhy27b\">2024.05.30 版本更新 - 专属宜搭能力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/ghmghckhktwybwy2\">2024.05.20 版本更新 - 审批流程能力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/lwigyw5bwcab0q61\">2024.05.10 版本更新-专属宜搭能力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/gnfkdmsrwl6a96vw\">2024.04.29 版本更新-基础产品力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/gtxg8u464yevyts0\">2024.04.25 版本更新-基础产品力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/alog0rgplo9ueodt\">2024.04.07 版本更新-基础产品力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/oiv8y4ngqsgbaxvx\">2024.03.28 版本更新-专属宜搭产品升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/crlnoiaznqv9zdgh\">2024.03.15 版本更新-数据管理页支持多视图</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/dfvonuhhw16s3vr5\">2024.01.22 版本更新-自定义按钮及详情页</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/nw4cvxkx05hyrqvk\">2024.01.18 版本更新-精选应用产品力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/yt6gs09cgcbz5ip2\">2024.01.15 版本更新-专属宜搭能力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/lzgun7xg71sq0tkk\">2024.01.09版本更新-AI助理全新发布</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/de4b24qxllyt2u8m\">2023.12.19 版本更新-基础产品力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/sd4haut6ppytlsoz\">2023.12.12 版本更新-专属宜搭升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/xh6pkqn0k4ii35w1\">2023.11.23 版本更新-智能表单/智能数据分析全新发布</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/augw2gdkfco8glqq\">2023.10.30 版本更新-基础产品力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/zz0gly218tifghop\">2023.10.12 版本更新-专属宜搭升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/ndx3savt46ggol8o\">2023.09.22 版本更新-应用搭建体验升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/hzwz951cb23botmv\">2023.09.05 版本更新-可播报的门户30天免费体验</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/oktphy8pqlg3p412\">2023.08.24 版本更新-宜搭AI Copilot全新发布</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/qzq1pbm2plwbgixr\">2023.08.17 版本更新-增值权益上架</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/afssdrzw11aohbks\">2023.08.15 版本更新-基础产品力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/qzwebreoh6tvlk38\">2023.08.03 版本更新-专属宜搭能力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/ge9kmszlmc9q90zm\">2023.07.27 版本更新-宜搭流程能力强化</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/to825fq074qk9k33\">2023.07.11 版本更新-专属宜搭升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/fcdh6cszpmv1r4vi\">2023.07.03 版本更新-宜搭全新版本发布</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/lnx2sdgma23xld4o\">2023.06.27 版本更新-宜搭平台能力优化及SaaS能力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/hz3mfltpdu5z0mgn\">2023.05.30 版本更新-专属版能力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/iayo22ueh8vn37r3\">2023.05.25 版本更新-宜搭SaaS工作台能力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/qmvq6d0nvr9tzag1\">2023.05.11 版本更新-流程能力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/shwcya8d24w1o9yh\">2023.04.20 版本更新-平台管理能力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/uvao23cydm1sz488\">2023.03.27 版本更新-报表编辑器体验升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/vegg7oaa21fbt60y\">2023.02.23 版本更新-表单流水号组件全新发布</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/yvhg4bx5wkiugnsv\">2023.01.10 版本更新-宜搭打通钉钉企业存储</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/wvh80a0nh19lkl4l\">2022.12.28 版本更新-支持发布宜搭应用到钉钉上下游</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/af00zhzc5eixk4xo\">2022.12.19 版本更新-循环容器和关联查询组件全新上线</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/ep49ewigwfgkrfq0\">2022.11.07 版本更新-集成自动化能力增强</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/alvlp0hlghv1qsc8\">2022.09.08 版本更新-宜搭支持增值购买数据大屏</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/lxttkz7d83gs2hco\">2022.09.07 版本更新-钉钉酷应用工厂全新发布</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/wb0at2\">2022.08.31 版本更新-支持钉钉官方应用数据互联</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/imbcr5\">2022.08.22 版本更新-应用菜单支持图标自定义</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/gnpfzt\">2022.08.16 版本更新-宜搭酷看板（数据服务+酷数据卡片）</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/zyprdy\">2022.08.16 版本更新-宜搭卡片设计器全新升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/uh9f38\">2022.08.10 版本更新-宜搭页面链接默认转卡片</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/ll6ffe\">2022.08.08 版本更新-定时触发支持选择表单内日期字段</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/vi2re81wfbvo98m4\">2022.06.30 版本更新-宜搭二级域名的升级公告</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/gddpov\">2022.06.21 版本更新-宜搭产品6月更新概览</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/rxtewo\">2022.06.21 版本更新-宜搭应用具备酷应用能力 重磅来袭</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/dvch4g\">2022.06.20 版本更新 - 全新宜搭数据工厂</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/toco9i\">2022.06.14 版本更新-新增钉钉OA审批连接器</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/xzdtwr\">2022.06.13 版本更新-数据节点支持跨应用</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/gwkpg4\">2022.05.17 版本更新-宜搭产品5月更新概览</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/ibef96\">2022.05.11 版本更新-宜搭AI组件</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/av2510\">2022.05.10 版本更新-流程专题更新</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/ywfxxn\">2022.04.27 版本更新-数据权限计算逻辑支持“属于”“或”</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/xsaez9\">2022.04.27 版本更新-平台管理权限升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/ggas2s\">2022.04.27 版本更新-连接器工厂全新升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/msz3zl\">2022.04.27 版本更新-数据管理页能力增强</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/hldb7o\">2022.04.27 版本更新-跨应用读写表单数据</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/fedev8\">2022.04.27 版本更新-跨应用表单数据查询 全新发布</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/xe1y54\">2022.04.21 版本更新-OpenAPI 全新升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/lp6bft\">2022.04.15 版本更新-数据报表能力增强</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/mkzmy9\">2022.04.14 版本更新-平台体验优化</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/sth73n\">2022.04.13 版本更新-连接器配置面板优化</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/ihghgn\">2022.04.07 版本更新-审批支持跳转下一条</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/rxzhoa\">2022.04.07 版本更新-流程版本管理功能</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/qait2k\">2022.04.02 版本更新-电子签章插件 全新发布</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/tcat6d\">2022.03.25 版本更新-流程提交预览功能</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/ogxmor\">2022.03.24 版本更新-部门自动授权管理</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/oer5fy\">2022.03.16 版本更新-跨组织数据权限升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/igor7t\">2022.03.16 版本更新-宜搭平台管理体验升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/nbfmeu\">2022.03.16 版本更新-流程超时处理规则全新升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/me29gm\">2022.03.15 版本更新-富文本/图文组件全新升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/itubig\">2022.03.03 版本更新-审批流程分析（BPA）</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/bxefi9\">2022.02.16 版本更新-宜搭搜索能力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/yz8phc\">2022.02.16 版本更新-页面快捷消息</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/pg2703\">2022.02.15 版本更新-自定义组件发布</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/ozopio\">2022.02.10 版本更新-“宜搭码”全新发布</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/eefa43\">2022.01.25 版本更新-行业通讯录数据权限升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/iddpa7\">2022.01.18 版本更新-宜搭酷应用工厂全新发布</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/os8ddx\">2022.01.18 版本更新-公式体验全新升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/ccsoe9\">2022.01.06 版本更新-数据源能力提升</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/tgbp90\">2021.12.29 版本更新-宜搭工作台首页升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/tmssbl\">2021.12.22 版本更新-定时触发、发送邮件</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/yll7yv\">2021.12.08 版本更新-12月流程功能专题</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/bddksu\">2021.12.07 版本更新-集成自动化增加审批事件触发&amp;应用支持自定义图标&amp;角色成员支持管理范围</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/mgygbx\">2021.12.07 版本更新-教育组件支持数据展示及分析</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/xultel\">2021.12.07 版本更新-打印插件升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/qnxqcf\">2021.12.01 版本更新-交叉透视表、词云等新组件发布</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/zb952c\">2021.11.24 版本更新-打通钉钉统一登录态</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/dhe99y\">2021.11.03 版本更新-平台授权管理升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/wlciu4\">2021.11.03 版本更新-宜搭3.0信息架构升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/lo70s8\">2021.11.08 版本更新-宜搭教育版组件升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/grurgo\">2021.10.25 版本更新-宜搭大屏全新发布</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/psyvkv\">2021.09.29 版本更新-报表的分享与图片显示</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/zhuppn\">2021.09.27 版本更新-自定义企业域名</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/dq4g9m\">2021.09.15 版本更新-新报表与应用数据管理</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/vrt14s\">2021.09.14 版本更新-集成&amp;自动化-连接器</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/xdbhpz\">2021.09.13 版本更新-群插件能力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/iglusc\">2021.08.30 版本更新-数据管理页能力升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/mfskvv\">2021.07.26 版本更新-审批流全新升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/sqdqti\">2021.06.22 版本更新-支持历史应用升级新版数据权限功能</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/rm78v6\">2021.06.18 版本更新-子表单升级及新增关联列表功能</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/aexgmo\">2021.06.17 版本更新-从Excel创建应用</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/for1o3\">2021.06.15 版本更新-表单组件升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/np6hzp\">2021.06.12 版本更新-宜搭支持关联组织</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/sxx01b\">2021.05.31 版本更新-宜搭官网、数据管理页功能等更新</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/fpan1y\">2021.05.19 版本更新-从Excel创建宜搭应用</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/gve70l\">2021.05.18 版本更新-数据权限升级</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/ngvy22\">2021.05.12 版本更新-高级流程功能上新&amp;服务商应用分发优化&amp;子表功能优化</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/akb894\">2021.04.21 版本更新-钉钉端宜搭页面功能新增&amp;数值组件优化&amp;审批优化</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/tdvzr2\">2021.04.15 版本更新-尊享版权益上新&amp;解决方案馆上线&amp;版本权益及价目表更新&amp;同步钉钉角色&amp;新增平台公告</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/nliux8\">2021.04.06 版本更新-关联表单组件上新</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/kqrk7r\">2021.02.08 版本更新-单行文本组件功能上新&amp;批量导出功能上新等</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/eegfbo\">2021.01.31 版本更新-宜搭移动端相关功能上新&amp;帮助文档、进阶视频上线等</a></li>\n" +
                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/hcvyrg\">2021年01月 大版本更新</a></li>";
//        paths = "\n" +
//                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/gslzl3trmm7hgvss\">2024.12.03 版本更新 - 应用分发能力升级</a></li>\n" +
//                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/kqrk7r\">2021.02.08 版本更新-单行文本组件功能上新&amp;批量导出功能上新等</a></li>\n" +
//                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/eegfbo\">2021.01.31 版本更新-宜搭移动端相关功能上新&amp;帮助文档、进阶视频上线等</a></li>\n" +
//                "  <a class=\"menu__link_mdV_ menu__link\" href=\"/docs/yida_updates/hcvyrg\">2021年01月 大版本更新</a></li>";
        crawl(paths);
    }
}
