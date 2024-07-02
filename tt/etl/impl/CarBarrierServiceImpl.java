
import com.richai.data.communityetl.bean.OriginData;
import com.richai.data.communityetl.bean.db.CommunityDevice;
import com.richai.data.communityetl.etl.BaseEtlService;
import com.richai.data.communityetl.repository.CommunityDeviceRepository;
import com.richai.data.communityetl.service.ProbeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service("carBarrier")
public class CarBarrierServiceImpl implements BaseEtlService {

    private static Logger logger = LoggerFactory.getLogger(CarBarrierServiceImpl.class);

    private static final String PROBE_TYPE = "车牌识别";

    private ProbeService probeService;
    private CommunityDeviceRepository communityDeviceRepository;

    @Autowired
    public void setProbeService(ProbeService probeService) {
        this.probeService = probeService;
    }

    @Autowired
    public void setCommunityDeviceRepository(CommunityDeviceRepository communityDeviceRepository) {
        this.communityDeviceRepository = communityDeviceRepository;
    }

    @Override
    public void process(OriginData data) {
        String communityId = data.getCommunityId();
        String dzwz = data.getData().get("DZWZ").toString();
        String dzbh = data.getData().get("DZBH").toString();
        if (!StringUtils.isEmpty(dzwz)){

            String id = UUID.nameUUIDFromBytes((communityId.trim() + PROBE_TYPE.trim() + dzbh.trim()).getBytes()).toString();
            CommunityDevice cd = CommunityDevice.newBuilder()
                    .id(id)
                    .communityId(communityId)
                    .deviceType(PROBE_TYPE)
                    .deviceNumber(dzbh)
                    .deviceName(dzwz)
                    .build();

            communityDeviceRepository.save(cd);
            probeService.updateProbe(communityId, dzbh, dzwz, PROBE_TYPE);
        } else {
            logger.info("道闸位置不存在");
        }
    }
}
