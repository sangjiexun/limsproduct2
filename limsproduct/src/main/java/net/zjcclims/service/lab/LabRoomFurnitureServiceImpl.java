package net.zjcclims.service.lab;
import java.util.List;

import net.zjcclims.dao.LabRoomFurnitureDAO;
import net.zjcclims.domain.LabRoomFurniture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring service that handles CRUD requests for LabRoomFurniture entities
 * 
 */

@Service("LabRoomFurnitureService")
@Transactional
public class LabRoomFurnitureServiceImpl implements LabRoomFurnitureService {
	
	@Autowired LabRoomFurnitureDAO labRoomFurnitureDAO;
	
	@Override
	public List<LabRoomFurniture> findLabRoomFurnitureByRooId(Integer id) {

		String sql="select r from LabRoomFurniture r where labRoom="+id;
		return labRoomFurnitureDAO.executeQuery(sql,0,-1);
	}
	@Transactional
	public void deleteLabRoomFurniture(LabRoomFurniture labRoomFurniture) {
		labRoomFurnitureDAO.remove(labRoomFurniture);
		labRoomFurnitureDAO.flush();
	}
	@Transactional
	public void saveLabRoomFurniture(LabRoomFurniture labRoomFurniture) {

		labRoomFurniture=labRoomFurnitureDAO.store(labRoomFurniture);
	}

}
