package net.zjcclims.service.tcoursesite;
import net.zjcclims.dao.WkFolderDAO;
import net.zjcclims.domain.User;
import net.zjcclims.domain.WkFolder;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("WkFolderService")
public class WkFolderServiceImpl implements  WkFolderService {
	@Autowired
	private WkFolderDAO wkFolderDAO;
	@Autowired
	private ShareService shareService;
	@Autowired
	private WkUploadService wkUploadService;


	/**************************************************************************
	 * Description:根据主键查询文件夹
	 * 
	 * @author：裴继超
	 * @date ：2016年4月29日9:50:57 
	 **************************************************************************/
	@Override
	public WkFolder findWkFolderByPrimaryKey(Integer wkFolderId) {
		//根据主键查询文件夹
		return wkFolderDAO.findWkFolderByPrimaryKey(wkFolderId);
	}
	/**************************************************************************
	 * Description:删除文件夹
	 * 
	 * @author：裴继超
	 * @date ：2016年4月29日9:50:57
	 **************************************************************************/
	@Override
	public void deleteWkFolder(WkFolder wkFolder) {
		//删除文件夹
		wkFolderDAO.remove(wkFolder);
		wkFolderDAO.flush();
	}
	

	/**************************************************************************
	 * Description:查找资源文件夹个数
	 * 
	 * @author：裴继超
	 * @date ：2016年7月9日20:26:34
	 **************************************************************************/
	@Override
	public int findFileFolderSize(Integer tCourseSiteId) {
		//章节下文件夹个数
		String sql1 = "select count(*) from WkFolder f where f.WkLesson.wkChapter.TCourseSite.id = " + tCourseSiteId  ;
		int size1 =  ((Long) wkFolderDAO.createQuerySingleResult(sql1.toString()).getSingleResult()).intValue();
		//学习单元下文件夹个数
		String sql2 = "select count(*) from WkFolder f where f.WkChapter.TCourseSite.id = " + tCourseSiteId ;
		int size2 = ((Long) wkFolderDAO.createQuerySingleResult(sql2.toString()).getSingleResult()).intValue();
		return size1 + size2;
	}
	
	/**************************************************************************
	 * Description:知识技能体验-ajax根据课时id和获取文件夹列表map
	 * 
	 * @author：裴继超
	 * @date ：2016年8月23日13:45:10
	 **************************************************************************/
	@Override
	public Map findFolderMapByLessonId(Integer lessonId){
		Map<Integer, String> map = new HashMap<Integer, String>();
		//根据课时id获取对应文件夹列表
		String sql = "select c from WkFolder c where c.wkLesson.id = " + lessonId +
				" or c.wkFolder.wkLesson.id = " + lessonId +
				" or c.wkFolder.wkFolder.wkLesson.id = " + lessonId ;
		List<WkFolder> folders = wkFolderDAO.executeQuery(sql, 0,-1);
		//获取每个文件夹的id和名称
		for(WkFolder c:folders){
			map.put(c.getId(), c.getName());
		}
		return map;
	}


	/**************************************************************************
	 * Description:可视化-根据类型获取文件夹
	 * 
	 * @author：裴继超
	 * @date ：2016-11-9
	 **************************************************************************/
	@Override
	public List<WkFolder> findFoldersByType(Integer type) {
		String sql = "from WkFolder f where f.type = " + type;
		List<WkFolder> wkFolders = wkFolderDAO.executeQuery(sql, 0,-1);
		return wkFolders;
	}
	
	
	
	
}