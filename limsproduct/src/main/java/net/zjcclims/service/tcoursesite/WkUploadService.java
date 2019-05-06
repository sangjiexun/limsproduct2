package net.zjcclims.service.tcoursesite;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.zjcclims.domain.User;
import net.zjcclims.domain.WkFolder;
import net.zjcclims.domain.WkUpload;



public interface WkUploadService {
	/**********************************************************************************
	 * 保存文件 
	 * 
	 **********************************************************************************/
	public int saveUpload(WkUpload upload);
	/**********************************************************************************
	 * 根据主键查询上传的文件 
	 * 
	 **********************************************************************************/
	public WkUpload findUploadByPrimaryKey(Integer valueOf);
	/**********************************************************************************
	 * 删除文档
	 * 
	 **********************************************************************************/
	public void deleteWkUpload(WkUpload upload);
	

	/**********************************************************************************
	 * 复制文件 
	 * 裴继超
	 * 2016年6月15日15:01:13
	 **********************************************************************************/
	public int copyUpload(WkUpload upload,WkFolder newWkFolder);
	
	/**********************************************************************************
	 * 知识技能体验-用户所属资源列表分页
	 * 裴继超
	 * 2016年7月25日9:28:59
	 **********************************************************************************/
	public List<WkUpload> findWkUploadsByUser(User user, int currpage, int pagesize, int uploadType);
	
	/**********************************************************************************
	 * 知识技能体验-用户所属资源总数
	 * 裴继超
	 * 2016年7月25日9:29:03
	 **********************************************************************************/
	public int countWkUploadsByUser(User user, int uploadType);
	
	/**********************************************************************************
	 * 知识技能体验-查看图片
	 * 裴继超
	 * 2016年8月11日17:28:10
	 **********************************************************************************/
	public Map lookImageMap(Integer folderId,int currpage, int pagesize);
	 	
	/**************************************************************************
	 * Description:pdf转swf
	 * @return 
	 * 
	 * @author：于侃
	 * @date ：2016年10月12日 16:46:52
	 **************************************************************************/
 	public void pdf2swf(String fileString,String rootPath) throws Exception;
	
 	/**************************************************************************
	 * Description:知识技能体验-根据章节查询图片数量
	 * 
	 * @author：于侃
	 * @date ：2016年10月14日 15:33:00
	 **************************************************************************/
	public Integer countImageMapByChapter(Integer chapterId,Integer lessonId);
	
	/**************************************************************************
	 * Description:知识技能体验-根据章节查看图片
	 * 
	 * @author：于侃
	 * @date ：2016年10月14日 15:33:35
	 **************************************************************************/
	public Map lookImageMapByChapter(Integer chapterId,Integer lessonId,int currpage, int pagesize);
	
 	/**************************************************************************
	 * Description:微课-查找章节下的图片
	 * 
	 * @author：裴继超
	 * @date ：2016-11-24
	 **************************************************************************/
	public List<WkUpload> findImagesByChapterId(Integer chapterId);
	
}