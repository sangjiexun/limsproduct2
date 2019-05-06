package net.zjcclims.dao;


import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.VirtualImage;
import net.zjcclims.domain.VirtualLabConstruction;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.Set;

/**
 * DAO to manage VirtualLabConstruction entities.
 * 
 */
public interface VirtualImageDAO extends
		JpaDao<VirtualImage> {
	public VirtualImage findVirtualImageByPrimaryKey(Integer id) throws DataAccessException;

	public VirtualImage findVirtualImageByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;
}