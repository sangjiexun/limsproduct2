package net.zjcclims.dao;

import org.skyway.spring.util.dao.JpaDao;
import net.zjcclims.domain.LabRelevantConfig;
import org.springframework.dao.DataAccessException;

        import java.util.Set;

public interface LabRelevantConfigDAO extends JpaDao<LabRelevantConfig> {

    public LabRelevantConfig findLabRelevantConfigBylabRoomIdAndCategory(Integer id,String category) throws DataAccessException;

    /**
     * JPQL Query - findLabRoomByPrimaryKey
     *
     */
    public LabRelevantConfig findLabRelevantConfigBylabRoomIdAndCategory(Integer id,String category, int startResult, int maxRows) throws DataAccessException;

}
