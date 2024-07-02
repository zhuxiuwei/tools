
import com.richai.data.communityetl.bean.db.EntityLog;
import com.richai.data.communityetl.bean.db.EntityProperty;
import com.richai.data.communityetl.etl.impl.EntityServiceImpl;
import com.richai.data.communityetl.repository.EntityLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api")
public class TestController {

    @Autowired
    private EntityServiceImpl entityService;

    @ResponseBody
    @GetMapping("testSave")
    public void testSave() {
        EntityLog entityLog = new EntityLog();
        entityLog.setCommunityId("123");
        entityLog.setCountTime(Instant.now());
        entityLog.setId(UUID.randomUUID().toString());
        entityLog.setProbeId("213");
        entityLog.setType("人脸");
        EntityProperty entityProperty = new EntityProperty(UUID.randomUUID().toString(), "123", "123", "123");
        entityService.saveDB(entityLog, Arrays.asList(entityProperty));
    }

}
