package SmartJacksonTest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhuxiuwei
 * @date 2025/5/3
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ChatRecordsRespPO {
    private Integer total;
    private List<ChatRecordPO> items;
}
