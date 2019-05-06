package net.zjcclims.service.common;

import java.util.List;

import net.zjcclims.dao.CommonVideoDAO;
import net.zjcclims.domain.CommonVideo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CommonVideoService")
public class CommonVideoServiceImpl implements CommonVideoService {

	@Autowired CommonVideoDAO commonVideoDAO;
	
	/***********************************************************************************************
	 * 根据实验室id查询视频
	 * 作者：李小龙 
	 ***********************************************************************************************/
	@Override
	public List<CommonVideo> findVideoByLabAnnexId(Integer id) {
		// TODO Auto-generated method stub
		String sql="select v from CommonVideo v where v.labRoom.id="+id;
		return commonVideoDAO.executeQuery(sql,0,-1);
	}
	/***********************************************************************************************
	 * 根据视频主键查询视频
	 * 作者：李小龙 
	 ***********************************************************************************************/
	@Override
	public CommonVideo findVideoByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return commonVideoDAO.findCommonVideoByPrimaryKey(id);
	}
	/***********************************************************************************************
	 * 删除视频
	 * 作者：李小龙 
	 ***********************************************************************************************/
	@Override
	public void deleteCommonVideo(CommonVideo video) {
		// TODO Auto-generated method stub
		commonVideoDAO.remove(video);
	}
	
	
	
}