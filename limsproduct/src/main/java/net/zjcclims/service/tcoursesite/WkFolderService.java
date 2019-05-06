package net.zjcclims.service.tcoursesite;

import net.zjcclims.domain.WkFolder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface WkFolderService {

	/**********************************************************************************
	 * 根据主键查询文件夹 
	 * 裴继超
	 * 2016年4月29日9:50:57
	 **********************************************************************************/
	public WkFolder findWkFolderByPrimaryKey(Integer wkFolderId);
	/**********************************************************************************
	 * 删除文件夹 
	 * 裴继超
	 * 2016年4月29日9:50:57
	 **********************************************************************************/
	public void deleteWkFolder(WkFolder wkFolder);
	
	/**********************************************************************************
	 * 查找资源文件夹个数
	 * 裴继超
	 * 2016年7月9日20:26:34
	 **********************************************************************************/
	public int findFileFolderSize(Integer tCourseSiteId) ;
	
	/**************************************************************************
	 * Description:知识技能体验-ajax根据课时id和获取文件夹列表map
	 * 
	 * @author：裴继超
	 * @date ：2016年8月23日13:45:10
	 **************************************************************************/
	public Map findFolderMapByLessonId(Integer lessonId);


	/**************************************************************************
	 * Description:可视化-根据类型获取文件夹
	 * 
	 * @author：裴继超
	 * @date ：2016-11-9
	 **************************************************************************/
	public List<WkFolder> findFoldersByType(Integer type);
	

	
}