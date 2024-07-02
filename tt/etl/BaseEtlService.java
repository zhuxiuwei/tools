
import com.richai.data.communityetl.bean.OriginData;

import java.util.Map;

/**
 *
 */
public interface BaseEtlService {

    void process(OriginData data);

    default void process(Map<String, Object> map){}

}
