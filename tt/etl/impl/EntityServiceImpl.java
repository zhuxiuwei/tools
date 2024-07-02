
import com.richai.data.communityetl.bean.db.Base64;
import com.richai.data.communityetl.bean.db.EntityLog;
import com.richai.data.communityetl.bean.db.EntityProperty;
import com.richai.data.communityetl.bean.db.Picture;
import com.richai.data.communityetl.etl.EntityService;
import com.richai.data.communityetl.repository.Base64Repository;
import com.richai.data.communityetl.repository.EntityLogRepository;
import com.richai.data.communityetl.repository.EntityPropertyRepository;
import com.richai.data.communityetl.repository.PictureRepository;
import com.richai.data.communityetl.util.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.File;
import java.time.Instant;
import java.util.Collection;

@Service
public class EntityServiceImpl implements EntityService {


    private static Logger logger = LoggerFactory.getLogger(EntityServiceImpl.class);


    private EntityLogRepository entityLogRepository;
    private EntityPropertyRepository entityPropertyRepository;
    private Base64Repository base64Repository;
    private PictureRepository pictureRepository;

    @Autowired
    public void setEntityLogRepository(EntityLogRepository entityLogRepository) {
        this.entityLogRepository = entityLogRepository;
    }

    @Autowired
    public void setEntityPropertyRepository(EntityPropertyRepository entityPropertyRepository) {
        this.entityPropertyRepository = entityPropertyRepository;
    }

    @Autowired
    public void setBase64Repository(Base64Repository base64Repository) {
        this.base64Repository = base64Repository;
    }

    @Autowired
    public void setPictureRepository(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Value("${file.path.prefix}")
    private String pathPrefix;

    @PostConstruct
    public void init() {
        if (!new File(pathPrefix).exists()) {
            if (new File(pathPrefix).mkdirs()) {
                logger.info("path prefix not exist, create it");
            } else {
                logger.warn("path prefix not exist, create failed");
            }
        }
    }

    @Transactional
    public void saveDB(EntityLog log, Collection<EntityProperty> properties) {
        if (log != null) {
            entityLogRepository.insertEntityLog(log.getId(), log.getCommunityId(), log.getProbeId(), log.getCountTime(), Instant.now(), log.getType());
        }
        if (!CollectionUtils.isEmpty(properties)) {
            properties.stream().forEach(entityProperty -> {
                entityPropertyRepository.insertEntityLog(entityProperty.getId(), entityProperty.getEntityId(), entityProperty.getPropertyKey(), entityProperty.getPropertyValue(), Instant.now());
            });
        }
    }


    @Override
    @Async("asyncExecutor")
    public void saveDB(EntityLog log, Collection<EntityProperty> properties, Collection<Base64> b64s, Collection<Picture> images) {
        if (log != null) {
            entityLogRepository.insertEntityLog(log.getId(), log.getCommunityId(), log.getProbeId(), log.getCountTime(), Instant.now(), log.getType());
        }

        if (!CollectionUtils.isEmpty(b64s)) {

            b64s.forEach(x -> x.setBase64(x.getBase64().replace("\r", "").replace("\n", "")));
            base64Repository.saveAll(b64s);
        }

        if (!CollectionUtils.isEmpty(images)) {
            pictureRepository.saveAll(images);
        }

        if (!CollectionUtils.isEmpty(properties)) {
            properties.stream().forEach(entityProperty -> {
                entityPropertyRepository.insertEntityLog(entityProperty.getId(), entityProperty.getEntityId(), entityProperty.getPropertyKey(), entityProperty.getPropertyValue(), Instant.now());
            });
            //entityPropertyRepository.saveAll(properties);
        }

        logger.info("数据保存成功");
    }

    @Override
    @Async("asyncExecutor")
    public void saveFile(String base64, String path) {
        base64 = base64.replace("\r", "").replace("\n", "");
        boolean b = ImageUtils.toImage(base64, pathPrefix + path);
        if (b) {
            logger.info("file {}, 保存成功", path);
        } else {
            logger.warn("file {}, 保存失败", path);
        }
    }
}
