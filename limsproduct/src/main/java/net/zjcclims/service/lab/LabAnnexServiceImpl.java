package net.zjcclims.service.lab;

import net.zjcclims.dao.*;
import net.zjcclims.domain.CommonDocument;
import net.zjcclims.domain.CommonVideo;
import net.zjcclims.domain.LabAnnex;
import net.zjcclims.domain.LabRoom;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service("LabAnnexService")
public class LabAnnexServiceImpl implements  LabAnnexService {
	
	@Autowired LabAnnexDAO labAnnexDAO;
	@Autowired LabRoomDAO labRoomDAO;
	@Autowired CommonDocumentDAO documentDAO;
	@Autowired CommonVideoDAO videoDAO;
	@Autowired ShareService shareService;
	@Autowired LabCenterDAO labCenterDAO;

	/****************************************************************************
	 * 功能：查询出所有的实验室
	 * 作者：李小龙
	 * 时间：2014-07-24
	 ****************************************************************************/
	@Override
	public Set<LabAnnex> findAllLabAnnex() {
		// TODO Auto-generated method stub
		return labAnnexDAO.findAllLabAnnexs();
	}
	
	/****************************************************************************
	 * 功能：查询出某一中心下的所有的实验室
	 * 作者：贺子龙
	 * 时间：2015-11-30
	 ****************************************************************************/
	@Override
	public List<LabAnnex> findAllLabAnnexBySchoolAcademy(String acno){
		StringBuffer sb= new StringBuffer("select l from LabAnnex l where 1=1");
		if(acno != null && !acno.equals("")) {
			sb.append(" and l.labCenter.schoolAcademy.academyNumber like '%" + acno + "%'");
		}
		 List<LabAnnex> labAnnexs=labAnnexDAO.executeQuery(sb.toString(), 0,-1);
		 return labAnnexs;
	}
	
	/****************************************************************************
	 * 功能：根据分页信息查询出所有的实验室
	 * 作者：李小龙
	 * 时间：2014-07-25
	 ****************************************************************************/
	@Override
	public List<LabAnnex> getAllLabAnnexs(int currpage, int pageSize) {
		// TODO Auto-generated method stub
		StringBuffer sb= new StringBuffer("select l from LabAnnex l where 1=1 order by l.createdAt desc");
		 //给语句添加分页机制；
		 List<LabAnnex> labAnnexs=labAnnexDAO.executeQuery(sb.toString(), (currpage-1)*pageSize, pageSize);
		 return labAnnexs;
	}
	@Override
	public List<LabAnnex> findLabAnnexByLabAnnex(LabAnnex labAnnex) {
		// TODO Auto-generated method stub
		String sql="select l from LabAnnex l where 1=1";
		if(labAnnex.getLabName()!=null&&!labAnnex.getLabName().equalsIgnoreCase("")){
			sql+=" and l.labName like '%"+labAnnex.getLabName()+"%'";
		}
		sql+=" order by l.createdAt desc";
		//给语句添加分页机制；
		List<LabAnnex> labAnnexs=labAnnexDAO.executeQuery(sql,0,-1);
		return labAnnexs;
	}
	@Override
	public List<LabAnnex> findLabAnnexByLabAnnex(LabAnnex labAnnex,int page,
			int pageSize) {
		// TODO Auto-generated method stub
		String sql="select l from LabAnnex l where 1=1";
		if(labAnnex.getLabName()!=null&&!labAnnex.getLabName().equalsIgnoreCase("")){
			sql+=" and l.labName like '%"+labAnnex.getLabName()+"%'";
		}
		sql+=" order by l.createdAt desc";
		//给语句添加分页机制；
		List<LabAnnex> labAnnexs=labAnnexDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
		return labAnnexs;
	}
	/****************************************************************************
	 * 功能：删除实验室对象
	 * 作者：李小龙
	 * 时间：2014-07-25
	 ****************************************************************************/
	@Override
	public void deleteLabAnnex(LabAnnex annex) {
		// TODO Auto-generated method stub
		labAnnexDAO.remove(annex);
	}
	/****************************************************************************
	 * 功能：保存实验室对象
	 * 作者：李小龙
	 * 时间：2014-07-27
	 ****************************************************************************/
	@Override
	public LabAnnex save(LabAnnex labAnnex) {
		// TODO Auto-generated method stub
		return labAnnexDAO.store(labAnnex);
	}
	/****************************************************************************
	 * 功能：上传实验室图片
	 * 作者：李小龙
	 * 时间：2014-07-28
	 ****************************************************************************/
	@Override
	public void imageUpload(HttpServletRequest request,
			HttpServletResponse response, Integer id) {
		String sep = System.getProperty("file.separator"); 
		String path = sep+ "upload"+ sep+"labannex"+sep+id;
		shareService.uploadFiles(request, path,"saveDocument",id);
	}
	/****************************************************************************
	 * 功能：保存实验室的文档
	 * 作者：李小龙
	 * 时间：2014-07-28
	 ****************************************************************************/
	public void saveDocument(String fileTrueName, Integer labAnnexid) {
		// TODO Auto-generated method stub
		//id对应的实验室
		LabAnnex annex=labAnnexDAO.findLabAnnexByPrimaryKey(labAnnexid);
		CommonDocument doc = new CommonDocument( );
		doc.setDocumentName(fileTrueName);
		String imageUrl="upload/labannex/"+labAnnexid+"/"+fileTrueName;
		doc.setDocumentUrl(imageUrl);
		doc.setLabAnnex(annex);
		
		documentDAO.store(doc);
	}
	/****************************************************************************
	 * 功能：上传实验室视频
	 * 作者：李小龙
	 * 时间：2014-07-28
	 ****************************************************************************/
	@Override
	public void videoUpload(HttpServletRequest request,
			HttpServletResponse response, Integer id) {
		String sep = System.getProperty("file.separator"); 
		String path = sep+ "upload"+ sep+"labannex"+sep+id;
		shareService.uploadFiles(request, path,"saveVideo",id);
	}
	/****************************************************************************
	 * 功能：保存实验室的视频
	 * 作者：李小龙
	 * 时间：2014-07-28
	 ****************************************************************************/
	@Override
	public void saveVideo(String fileTrueName, Integer labAnnexid) {
		//id对应的实验室
		LabAnnex annex=labAnnexDAO.findLabAnnexByPrimaryKey(labAnnexid);
		CommonVideo video=new CommonVideo();
		video.setVideoName(fileTrueName);
		String videoUrl="upload/labannex/"+labAnnexid+"/"+fileTrueName;
		video.setVideoUrl(videoUrl);
		video.setLabAnnex(annex);
		
		videoDAO.store(video);
	}
	/****************************************************************************
	 * 功能：根据实验室id查询实验分室
	 * 作者：李小龙
	 * 时间：2014-07-29
	 ****************************************************************************/
	@Override
	public List<LabRoom> findLabRoomByLabAnnexId(LabRoom labRoom,Integer id) {
		// TODO Auto-generated method stub
		String sql="select r from LabRoom r where r.labAnnex.id="+id+" and r.isUsed=1";
		if(labRoom.getLabRoomName()!=null&&!labRoom.getLabRoomName().equalsIgnoreCase("")){
			sql+=" and r.labRoomName like '%"+labRoom.getLabRoomName()+"%'";
		}
		sql+=" order by r.id desc";
		return labRoomDAO.executeQuery(sql,0,-1);
	}
	/****************************************************************************
	 * 功能：根据实验室id查询实验分室并分页
	 * 作者：李小龙
	 * 时间：2014-07-29
	 ****************************************************************************/
	@Override
	public List<LabRoom> findLabRoomByLabAnnexId(LabRoom labRoom, Integer id,
			Integer page, int pageSize) {
		String sql="select r from LabRoom r where r.labAnnex.id="+id+" and r.isUsed=1";
		if(labRoom.getLabRoomNumber()!=null&&!labRoom.getLabRoomNumber().equalsIgnoreCase("")){
			sql+=" and r.labRoomNumber like '%"+labRoom.getLabRoomNumber()+"%'";
		}
		if(labRoom.getId()!=null&&!labRoom.getId().equals("")){
			sql+=" and r.id = "+labRoom.getId();
		}
		sql+=" order by r.id desc";
		System.out.println(sql);
		return labRoomDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
	}
	/****************************************************************************
	 * 功能：联动查询-学院&实验室
	 * 作者：徐文
	 * 日期：2016-5-10
	 ****************************************************************************/
	public String LinkSchoolAcademyAndLabAnnex(String schoolAcademy){
		//查询当前中心下的实验室
		String sql="select distinct x from LabAnnex x where 1=1";
		sql+=" and x.labCenter.schoolAcademy.academyNumber like '"+schoolAcademy + "'";
		Map<Integer,String> labAnnexMap=new HashMap<Integer, String>();
		//遍历实验室放到map集合中
		for(LabAnnex labAnnexes:labAnnexDAO.executeQuery(sql, -1,0)){
			labAnnexMap.put(Integer.valueOf(labAnnexes.getId()),labAnnexes.getLabName());
		}
		String labAnnex="<option  value=''>全部实验室</option>";
		//获取map集合的迭代器
	    Iterator<Map.Entry<Integer, String>> it = labAnnexMap.entrySet().iterator();
	    while (it.hasNext()) {
		    Map.Entry<Integer, String> entry = it.next();
		    labAnnex+="<option  value='"+ entry.getKey() +"'>"+entry.getValue()+"</option>";
		  }
		String labAnnexValue= shareService.htmlEncode(labAnnex);
		return labAnnexValue;
	}
	
	/************************************************************************************
	 * 功能：查询labannex
	 * 姓名：徐文
	 * 日期：2016-4-26
	 **************************************************************************************/
	public List<LabAnnex> findAllLabAnnexByQuery(Integer currpage, Integer pageSize, LabAnnex labAnnex,
												 String acno, HttpServletRequest request){
		StringBuffer hql = new StringBuffer("select w from LabAnnex w where 1=1 ");
		if(labAnnex.getLabName()!=null && !"".equals(labAnnex.getLabName()))
		{
			hql.append(" and w.labName like '%"+labAnnex.getLabName()+"%'");
		}
		hql.append(" and w.CDictionaryByLabAnnex.CNumber <>4 and w.CDictionaryByLabAnnex.CCategory='c_lab_annex_type'");
		// 获取当前系统权限
		String auth = request.getSession().getAttribute("selected_role").toString();
		// 根据权限等级筛选
		int authLevel = shareService.getLevelByAuthName(auth);
		if(authLevel==5) {
			hql.append(" and w.labCenter.userByCenterManager.username='"+ shareService.getUserDetail().getUsername() +"'");
		}else if(authLevel==3 || authLevel==4 || authLevel==7 || authLevel==6) {
			hql.append(" and w.labCenter.schoolAcademy.academyNumber='"+ acno +"'");
		}

		hql.append(" order by w.createdAt desc");
		
		List<LabAnnex> labAnnexes = labAnnexDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
		return labAnnexes;
	}

	/**************************************************************************************
	 * 功能：根据实验室主键查找实验室对象
	 * 姓名：徐文
	 * 日期：2016-4-27
	 **************************************************************************************/
 	@Override
	public LabAnnex findLabAnnexByPrimaryKey(Integer labAnnexId){
		return labAnnexDAO.findLabAnnexByPrimaryKey(labAnnexId);
	}
	/**************************************************************************************
	 * 功能：保存实验室数据
	 * 姓名：徐文
	 * 日期：2016-4-27
	 ***************************************************************************************/
	public LabAnnex saveLabAnnex(LabAnnex labAnnex){
		return labAnnexDAO.store(labAnnex);
	}
	/*************************************************************************************
	 *功能： 删除实验室数据
	 *姓名：徐文
	 *日期：2016-4-27
	 ***********************************************************************************/
	public boolean deleteLabAnnex(Integer labAnnexId){
		LabAnnex labAnnex = labAnnexDAO.findLabAnnexByPrimaryKey(labAnnexId);
		if(labAnnex!=null)
		{
			labAnnexDAO.remove(labAnnex);
			labAnnexDAO.flush();
			return true;
		}
		
		return false;
 	}

	/*************************************************************************************
	 *功能： 根据c_lab_annex_type查询labAnnex
	 *姓名：麦凯俊
	 *日期：2018-8-28
	 ***********************************************************************************/
	public List<LabAnnex> findLabAnnexByLabType(int type, String acno){
		String sql = "";
		if(type == -1){ //查询除了实验基地的labannxe
			sql = "select la from LabAnnex la where la.CDictionaryByLabAnnex.CNumber != '4'"
					+ " and la.CDictionaryByLabAnnex.CCategory = 'c_lab_annex_type' ";
			if (acno != null && !acno.equals("-1")) {
				sql += " and la.labCenter.schoolAcademy.academyNumber='" + acno + "'";
			}
		} else {
			sql = "select la from LabAnnex la where la.CDictionaryByLabAnnex.CNumber = '" + type
					+ "' and la.CDictionaryByLabAnnex.CCategory = 'c_lab_annex_type' ";
			if (acno != null && !acno.equals("-1")) {
				sql += " and la.belongDepartment='" + acno + "'";
			}
		}
		return labAnnexDAO.executeQuery(sql);
	}
	/*************************************************************************************
	 *功能： 实验中心和实验室做联动
	 *姓名：廖文辉
	 *日期：2018-9-7
	 ***********************************************************************************/
	public String findLabAnnexByLabCenter(String labCenter) {
		String sql = "select l from LabAnnex l where 1=1";
		sql += " and l.labCenter.id='" + labCenter + "'";
		sql +=" and l.CDictionaryByLabAnnex.CNumber != '4'";
		sql +=" and l.CDictionaryByLabAnnex.CCategory = 'c_lab_annex_type' ";
		Map<String, String> labAnnexMap = new HashMap<String, String>();
		for (LabAnnex labAnnex : labAnnexDAO.executeQuery(sql, 0, -1)) {
			labAnnexMap.put(String.valueOf(labAnnex.getId()), labAnnex.getLabName());
		}
		String labAnnex = "<option value=''>全部实验室</option>";
		Iterator<Map.Entry<String, String>> it = labAnnexMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			labAnnex += "<option value='" + entry.getKey() + "'>" + entry.getValue() + "</option>";
		}
		String labAnnexValue = shareService.htmlEncode(labAnnex);
		return labAnnexValue;
	}
}