
import com.richai.data.communityetl.bean.OriginData;
import com.richai.data.communityetl.bean.db.Base64;
import com.richai.data.communityetl.bean.db.EntityLog;
import com.richai.data.communityetl.bean.db.EntityProperty;
import com.richai.data.communityetl.bean.db.Picture;
import com.richai.data.communityetl.etl.BaseEtlService;
import com.richai.data.communityetl.etl.EntityService;
import com.richai.data.communityetl.service.ProbeService;
import com.richai.data.communityetl.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@Service("carEvent")
public class CarEventServiceImpl implements BaseEtlService {

    private Logger logger = LoggerFactory.getLogger(CarEventServiceImpl.class);

    private static final String ENTITY_TYPE = "汽车";
    private static final String PROBE_TYPE = "车牌识别";

    private EntityService entityService;
    private ProbeService probeService;

    @Autowired
    public void setEntityService(EntityService entityService) {
        this.entityService = entityService;
    }

    @Autowired
    public void setProbeService(ProbeService probeService) {
        this.probeService = probeService;
    }

    @Override
    public void process(OriginData originData) {

        Map<String,Object> data = originData.getData();

        List<EntityProperty> properties = new ArrayList<>();
        List<Base64> base64s = new ArrayList<>();
        List<Picture> pictures = new ArrayList<>();

        String communityId = originData.getCommunityId();
        String countTime = data.getOrDefault("DZSJSJ","").toString();
        String dzbh = data.getOrDefault("DZBH","").toString();
        String cph = null;
        if (data.containsKey("CPH") && !StringUtils.isEmpty(data.get("CPH"))){
            cph = data.get("CPH").toString();
        }
        String clzp = data.getOrDefault("CLZP","").toString();
        String cpzp = data.getOrDefault("CPZP","").toString();

        String probe = probeService.getProbeId(communityId, dzbh, PROBE_TYPE);
        logger.info("处理时间为:{},车牌号为{},道闸编号为{},probe为{}的数据",countTime, cph, dzbh,probe);
        String entityId = UUID.randomUUID().toString();
        EntityLog entityLog = EntityLog.newBuilder()
                .id(entityId)
                .communityId(communityId)
                .probeId(probe)
                .countTime(TimeUtils.toInstant2(countTime))
                .type(ENTITY_TYPE)
                .build();


        //车辆照片
        if (!StringUtils.isEmpty(clzp)){
            String clB64Id = UUID.randomUUID().toString();
            Base64 cl = new Base64(clB64Id, clzp,Instant.now(), 0);
            base64s.add(cl);

            EntityProperty clProperty = new EntityProperty(UUID.randomUUID().toString(), entityId, "base64",clB64Id);
            properties.add(clProperty);

            String path = "/community/" + LocalDate.now().toString() + "/" + communityId  + "/car/" + clB64Id + ".jpg";
            entityService.saveFile(clzp, path);
            Picture p = new Picture(clB64Id, path, Instant.now(),0);
            pictures.add(p);

            EntityProperty picProperty = new EntityProperty(UUID.randomUUID().toString(), entityId, "picture",clB64Id);
            properties.add(picProperty);
        }

        // 车牌照片
        if (!StringUtils.isEmpty(cpzp)){
            String cpB64Id = UUID.randomUUID().toString();
            Base64 cp = new Base64(cpB64Id, cpzp,Instant.now(), 0);
            base64s.add(cp);

            EntityProperty cpProperty = new EntityProperty(UUID.randomUUID().toString(), entityId, "base64_little",cpB64Id);
            properties.add(cpProperty);

            String path = "/community/" + LocalDate.now().toString() + "/" + communityId  + "/car/" + cpB64Id + ".jpg";
            entityService.saveFile(clzp, path);
            Picture p = new Picture(cpB64Id, path, Instant.now(),0);
            pictures.add(p);

            EntityProperty picProperty = new EntityProperty(UUID.randomUUID().toString(), entityId, "picture_little",cpB64Id);
            properties.add(picProperty);
        }

        //车牌号
        if (!StringUtils.isEmpty(cph)){
            EntityProperty carNoProperty = new EntityProperty(UUID.randomUUID().toString(), entityId, "plateNo",cph);
            properties.add(carNoProperty);
        }

        entityService.saveDB(entityLog, properties, base64s, pictures);
    }
}
