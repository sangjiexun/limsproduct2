package net.zjcclims.service.softwareRoomRelated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.zjcclims.dao.LabRoomDeviceDAO;
import net.zjcclims.dao.SoftwareRoomRelatedDAO;
import net.zjcclims.domain.SoftwareRoomRelated;
@Service("SoftwareRoomRelatedService")
public class SoftwareRoomRelatedServiceImpl implements
		SoftwareRoomRelatedService {
	@Autowired  SoftwareRoomRelatedDAO softwareRoomRelatedDAO;
	@Override
	public SoftwareRoomRelated save(SoftwareRoomRelated softwareRoomRelated) {
		// TODO Auto-generated method stub
		return softwareRoomRelatedDAO.store(softwareRoomRelated);
	}

}
