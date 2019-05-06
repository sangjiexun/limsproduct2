package net.zjcclims.dao;


import net.zjcclims.domain.VirtualImage;
import net.zjcclims.domain.VirtualImageReservation;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

/**
 * DAO to manage VirtualLabConstruction entities.
 * 
 */
public interface VirtualImageReservationDAO extends
		JpaDao<VirtualImageReservation> {
	public VirtualImageReservation findVirtualImageReservationByPrimaryKey(Integer id) throws DataAccessException;

	public VirtualImageReservation findVirtualImageReservationByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;
}