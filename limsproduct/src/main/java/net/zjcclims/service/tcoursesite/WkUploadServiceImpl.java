package net.zjcclims.service.tcoursesite;

import net.zjcclims.dao.WkUploadDAO;
import net.zjcclims.domain.User;
import net.zjcclims.domain.WkFolder;
import net.zjcclims.domain.WkUpload;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;





@Service("WkUploadService")
public class WkUploadServiceImpl implements  WkUploadService {
	
	@Autowired
	private WkUploadDAO wkUploadDAO;
	@Autowired
	private WkFolderService wkFolderService;
	@Autowired
	private ShareService shareService;
	/**************************************************************************
	 * Description:保存文件 
	 * 
	 * @author：于侃
	 * @date ：2016-09-13
	 **************************************************************************/
	@Override
	public int saveUpload(WkUpload upload) {
		//设置上传时间为当前时间
		upload.setUpTime(Calendar.getInstance());
		//保存文件
		WkUpload up=wkUploadDAO.store(upload);
		//刷新数据库
		wkUploadDAO.flush();
		//返回上传文件的id
		return up.getId();
	}
	/**************************************************************************
	 * Description:根据主键查询上传的文件
	 * 
	 * @author：于侃
	 * @date ：2016-09-13
	 **************************************************************************/
	@Override
	public WkUpload findUploadByPrimaryKey(Integer valueOf) {
		//通过主键查找上传的文件
		return wkUploadDAO.findWkUploadByPrimaryKey(valueOf);
	}
	/**************************************************************************
	 * Description:删除文档
	 * 
	 * @author：于侃
	 * @date ：2016-09-13
	 **************************************************************************/
	@Override
	public void deleteWkUpload(WkUpload upload) {
		//删除上传文件
		wkUploadDAO.remove(upload);
	}
	

	
	/**************************************************************************
	 * Description:复制文件 
	 * 
	 * @author：裴继超
	 * @date ：2016年6月15日15:01:13
	 **************************************************************************/
	@Override
	public int copyUpload(WkUpload upload,WkFolder newWkFolder) {
		
		//当前登陆用户
		User nowUser = shareService.getUserDetail();
		//新建上传文件
		WkUpload newWkUpload = new WkUpload();
		newWkUpload.setName(upload.getName());
		newWkUpload.setUrl(upload.getUrl());
		newWkUpload.setUpTime(Calendar.getInstance());
		newWkUpload.setType(upload.getType());
		newWkUpload.setDescription(upload.getDescription());
		newWkUpload.setSize(upload.getSize());
		newWkUpload.setUser(nowUser);
		newWkUpload.setWkFolder(newWkFolder);
		//保存文件
		newWkUpload = wkUploadDAO.store(newWkUpload);
		wkUploadDAO.flush();
		return newWkUpload.getId();
	}
	
	/**************************************************************************
	 * Description:知识技能体验-用户所属资源列表分页
	 * 
	 * @author：裴继超
	 * @date ：2016年7月25日9:28:59
	 **************************************************************************/
 	public List<WkUpload> findWkUploadsByUser(User user, int currpage, int pagesize, int uploadType){
 		//声明文件列表
 		List<WkUpload> list = new ArrayList<WkUpload>();
 		//根据当前用户和资源类型获取资源列表，（分页）
 		String hql = new String("select w from WkUpload w where w.user.username like '" + user.getUsername() + "'" + " and ( " + "w.type = " + uploadType + " or w.type = 101 ) group by w.url");
 		list = wkUploadDAO.executeQuery(hql, (currpage-1)*pagesize, pagesize);
 		return list;
 	}
 	
 	/**************************************************************************
	 * Description:知识技能体验-用户所属资源总数
	 * 
	 * @author：裴继超
	 * @date ：2016年7月25日9:29:03
	 **************************************************************************/
 	public int countWkUploadsByUser(User user, int uploadType){
 		//声明文件列表
 		List<WkUpload> list = new ArrayList<WkUpload>();
 		//根据当前用户和资源类型获取资源列表
 		String hql = new String("select w from WkUpload w where w.user = '" + user.getUsername() + "'" + " and " + "w.type = " + uploadType + " group by w.url");
 		list = wkUploadDAO.executeQuery(hql, 0, -1);
 		//获取资源总数
 		int count = list.size();
 		return count;
 	}
 	
 	/**************************************************************************
	 * Description:知识技能体验-查看图片
	 * 
	 * @author：裴继超
	 * @date ：2016年8月11日17:28:10
	 **************************************************************************/
 	@Override
	public Map lookImageMap(Integer folderId,int currpage, int pagesize){
 		//根据id获取文件
 		WkFolder wkFolder = wkFolderService.findWkFolderByPrimaryKey(folderId);
 		Map<String, Object> map = new HashMap<String, Object>();
 		//根据文件id查找对应的上传的文件列表
 		String sql = "select u from WkUpload u where u.wkFolder.id = " + folderId;
 		//文件列表
 		List<WkUpload> uploads = wkUploadDAO.executeQuery(sql,(currpage-1)*pagesize, pagesize);
 		// 查询的图片存在
		if (uploads.size()!=0) {
			//因为每页显示一个，所有获取第一个上传的图片
			WkUpload upload = uploads.get(0);
			// 图片路径
			map.put("imageUrlLook", upload.getUrl());
			// 图片名称
			map.put("imageNameLook", upload.getName());
		}
 		return map;
		
	}
 	
 	/**************************************************************************
	 * Description:知识技能体验-根据章节查询图片数量
	 * 
	 * @author：于侃
	 * @date ：2016年10月14日 15:33:00
	 **************************************************************************/
 	@Override
	public Integer countImageMapByChapter(Integer chapterId,Integer lessonId){
 		//根据章节查找对应的上传的图片数量
 		String sql = "";
 		if(lessonId == -1){
 			sql = "select count(u) from WkUpload u join u.wkFolder f where f.WkChapter.id = " + chapterId + " and f.WkLesson is null and u.type = 1";
 		}else{
 			sql = "select count(u) from WkUpload u join u.wkFolder f where f.WkChapter.id = " + chapterId + " and f.WkLesson.id = " + lessonId + " and u.type = 1 ";
 		}
 		int result = ((Long)wkUploadDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
 		return result;
 	}
 	
 	/**************************************************************************
	 * Description:知识技能体验-根据章节查看图片
	 * 
	 * @author：于侃
	 * @date ：2016年10月14日 15:33:35
	 **************************************************************************/
 	@Override
	public Map lookImageMapByChapter(Integer chapterId,Integer lessonId,int currpage, int pagesize){
 		Map<String, Object> map = new HashMap<String, Object>();
 		//根据章节查找对应的上传的图片列表
 		String sql = "";
 		if(lessonId == -1){
	 		sql = "select u from WkUpload u join u.wkFolder f where f.WkChapter.id = " + chapterId + " and f.WkLesson is null and u.type = 1 order by f.id";
 		}else{
 			sql = "select u from WkUpload u join u.wkFolder f where f.WkChapter.id = " + chapterId + " and f.WkLesson.id = " + lessonId
	 				+ " and u.type = 1 order by f.id";
 		}
 		//图片列表
 		List<WkUpload> uploads = wkUploadDAO.executeQuery(sql,(currpage-1)*pagesize, pagesize);
 		// 查询的图片存在
		if (uploads.size()!=0) {
			//因为每页显示一个，所有获取第一个上传的图片
			WkUpload upload = uploads.get(0);
			// 图片路径
			map.put("imageUrlLook", upload.getUrl());
			// 图片名称
			map.put("imageNameLook", upload.getName());
		}
 		return map;
	}
 	
 	
 	/**************************************************************************
	 * Description:pdf转swf
	 * 
	 * @author：于侃
	 * @date ：2016年10月12日 16:46:52
	 **************************************************************************/
 	public void pdf2swf(String fileString,String rootPath) throws Exception
    {
 		//环境1：windows 2:linux(涉及pdf2swf路径问题)
 		int environment=2;
        String fileName=fileString.substring(0,fileString.lastIndexOf("."));
        File pdfFile=new File(fileName+".pdf");
        File swfFile=new File(fileName+".swf");
        Runtime r=Runtime.getRuntime();
        if(!swfFile.exists())
        {
            if(pdfFile.exists())
            {
                if(environment==1)//windows环境处理
                {
                    try {
                        Process p=r.exec("zjcclims/SWFTools/pdf2swf.exe "+pdfFile.getPath()+" -o "+swfFile.getPath()+" -T 9");
                        System.out.print(loadStream(p.getInputStream()));
                        System.err.print(loadStream(p.getErrorStream()));
                        System.out.print(loadStream(p.getInputStream()));
                        //System.err.println("****swf转换成功，文件输出："+swfFile.getPath()+"****");
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }
                else if(environment==2)//linux环境处理
                {
                    try {
                        Process p=r.exec(rootPath+"SWFTools/pdf2swf.exe "+pdfFile.getPath()+" -o "+swfFile.getPath()+" -T 9");
//                        System.out.print(loadStream(p.getInputStream()));
//                        System.err.print(loadStream(p.getErrorStream()));
//                        System.err.println("****swf转换成功，文件输出："+swfFile.getPath()+"****");
                        loadStream(p.getInputStream());
                        loadStream(p.getErrorStream());
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
            else {
                System.out.println("****pdf不存在，无法转换****");
            }
        }
        else {
            System.out.println("****swf已存在不需要转换****");
        }
    }
 	
 	public String loadStream(InputStream in) throws IOException
    {
        int ptr=0;
        in=new BufferedInputStream(in);
        StringBuffer buffer=new StringBuffer();
        
        while((ptr=in.read())!=-1)
        {
            buffer.append((char)ptr);
        }
        return buffer.toString();
    }
 	
 	/**************************************************************************
	 * Description:微课-查找章节下的图片
	 * 
	 * @author：裴继超
	 * @date ：2016-11-24
	 **************************************************************************/
 	@Override
	public List<WkUpload> findImagesByChapterId(Integer chapterId){
		
		List<WkUpload> images = new ArrayList(); 
		String sql = "from WkUpload u where u.wkFolder.WkChapter.id = " + chapterId;
		sql += " and u.wkFolder.type = 2";
		images = wkUploadDAO.executeQuery(sql, 0,-1);
		return images;
		
	}
}