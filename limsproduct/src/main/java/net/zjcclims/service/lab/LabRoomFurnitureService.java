package net.zjcclims.service.lab;

import java.util.List;

import net.zjcclims.domain.LabRoomFurniture;

/**
 * Spring service that handles CRUD requests for LabRoomFurniture entities
 * 
 */
public interface LabRoomFurnitureService {
		
	
	
	public List<LabRoomFurniture> findLabRoomFurnitureByRooId(Integer id);
	
	public void deleteLabRoomFurniture(LabRoomFurniture labRoomFurniture);
	
	public void saveLabRoomFurniture(LabRoomFurniture labRoomFurniture);
}