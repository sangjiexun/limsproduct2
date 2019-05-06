package net.zjcclims.dao;

import org.skyway.spring.util.dao.JpaDao;
import net.zjcclims.domain.ReservationSetItem;
import org.springframework.dao.DataAccessException;

import java.util.Set;

public interface ReservationSetItemDAO extends JpaDao<ReservationSetItem> {
    /**
     * JPQL Query - findLabRoomByLabCenterId
     *
     */
    public ReservationSetItem findReservationSetItemById(Integer id_1) throws DataAccessException;
    /**
     * JPQL Query - findLabRoomByPrimaryKey
     *
     */
    public ReservationSetItem findReservationSetItemById(Integer id, int startResult, int maxRows) throws DataAccessException;
    /**
     * JPQL Query - findLabRoomByPrimaryKey
     *
     */
    public ReservationSetItem findReservationSetItemBylabRoomIdAndType(Integer id,Integer type) throws DataAccessException;

    /**
     * JPQL Query - findLabRoomByPrimaryKey
     *
     */
    public ReservationSetItem findReservationSetItemBylabRoomIdAndType(Integer id,Integer type, int startResult, int maxRows) throws DataAccessException;

}
