
import com.richai.data.communityetl.bean.db.Base64;
import com.richai.data.communityetl.bean.db.EntityLog;
import com.richai.data.communityetl.bean.db.EntityProperty;
import com.richai.data.communityetl.bean.db.Picture;

import java.util.Collection;

public interface EntityService {
    void saveDB(EntityLog log, Collection<EntityProperty> properties, Collection<Base64> b64s, Collection<Picture> images);
    void saveFile(String base64, String path);
}
