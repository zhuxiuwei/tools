package SmartJacksonTest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhuxiuwei
 * @date 2025/5/3
 * 聊天记录详情，例：
 * {
 * 	"role": "assistant",
 * 	"time": "2025-04-29 20:22:25",
 * 	"message": "很抱歉，文档中没有你想要的答案。",
 * 	"like": 0,
 * 	"debugInfo": {},
 * 	"recallCount": null,
 * 	"recallInfo": null,
 * 	"messageId": "1917192709328625733",
 * 	"conversationId": "20250425201530_1915477741172473917_8220967",
 * 	"isTransferredToManual": false,
 * 	"feedbackOfLike": 0,
 * 	"feedbackOfDislike": 0,
 * 	"welcomeMessage": false,
 * 	"isLabeled": false,
 * 	"audioEnabled": false,
 * 	"additionalMessage": null,
 * 	"extendInfo": {
 * 		"clickTextList": null
 *        },
 * 	"reasoningContent": "",
 * 	"eventMessages": null
 * }
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRecordPO {
    private String role;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String time;

    private String message;
    private Integer like;
    private Object debugInfo;
    private Integer recallCount;
    private Object recallInfo;
    private String messageId;
    private String conversationId;
    private Boolean isTransferredToManual;
    private Integer feedbackOfLike;
    private Integer feedbackOfDislike;
    private Boolean welcomeMessage;
    private Boolean isLabeled;
    private Boolean audioEnabled;
    private JsonNode additionalMessage;
    private ExtendInfo extendInfo;
    private String reasoningContent;
    private List<Object> eventMessages;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExtendInfo {
        private List<String> clickTextList;
    }
}