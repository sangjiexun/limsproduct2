package net.zjcclims.dao;

import net.zjcclims.domain.LabOpenUpAcademy;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;
import java.util.Set;

public interface LabOpenUpAcademyDAO extends JpaDao<LabOpenUpAcademy> {

    public Set<LabOpenUpAcademy> findLabOpenUpAcademyBylabRoomIdAndType(Integer id,Integer type) throws DataAccessException;

    /**
     * JPQL Query - findLabRoomByPrimaryKey
     *
     */
    public Set<LabOpenUpAcademy> findLabOpenUpAcademyBylabRoomIdAndType(Integer id,Integer type, int startResult, int maxRows) throws DataAccessException;

}
