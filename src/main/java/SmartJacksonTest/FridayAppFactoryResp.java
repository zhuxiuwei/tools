package SmartJacksonTest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhuxiuwei
 * @date 2025/4/22
 * Friday应用工厂resp
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FridayAppFactoryResp<T> {
    private Integer code;
    private Integer status;
    private String message;
    private T data;
}
