package net.zjcclims.service.performance;

import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

//import net.zjcclims.domain.LabRoomDeviceReservation;
//import net.zjcclims.dao.LabRoomDeviceReservationDAO;

@Service("PerformanceService")
public class PerformanceServiceImpl implements PerformanceService {

	@Autowired OperationItemDAO operationItemDAO;
	@Autowired LabReservationDAO labReservationDAO;
	@Autowired
	IndividualPerformanceDAO individualPerformanceDAO;
	@Autowired
	ShareService shareService;
	@Autowired
	CommonDocumentDAO commonDocumentDAO;
	@Autowired
	SchoolCourseInfoDAO schoolCourseInfoDAO;
	@Autowired
	SchoolMajorDAO schoolMajorDAO;
	@Autowired
	UserDAO userDAO;
	/****************************************************************************
	 * 功能：查找个人绩效
	 * 作者：廖文辉
	 * 时间：2018/9/19
	 ****************************************************************************/

	@Override
	public List<IndividualPerformance> findPerformanceCount(IndividualPerformance individualPerformance,int type){
		StringBuffer sql = new StringBuffer(" select i from IndividualPerformance i where 1 = 1 ");
		sql.append( " and i.type="+type);
		if (individualPerformance.getSchoolTerm()!=null&&!"".equals(individualPerformance.getSchoolTerm())) {
			sql.append(" and i.schoolTerm='" + individualPerformance.getSchoolTerm() + "'");
		}
		if(individualPerformance.getYearCode()!=null&&!"".equals(individualPerformance.getYearCode())){
			sql.append(" and i.yearCode='"+individualPerformance.getYearCode()+"'");
		}
		return individualPerformanceDAO.executeQuery(sql.toString(),0,-1);
	}
	/****************************************************************************
	 * 功能：查找个人绩效(分页）
	 * 作者：廖文辉
	 * 时间：2018/9/19
	 ****************************************************************************/
	public List<IndividualPerformance> findPerformance(IndividualPerformance individualPerformance,int currpage,
													   int pageSize,int type){
		StringBuffer sql = new StringBuffer(" select i  from IndividualPerformance  i where 1 = 1 ");
		sql.append(" and i.type="+type);
		if (individualPerformance.getSchoolTerm()!=null&&!"".equals(individualPerformance.getSchoolTerm())) {
			sql.append(" and i.schoolTerm='" + individualPerformance.getSchoolTerm() + "'");
		}
		if(individualPerformance.getYearCode()!=null&&!"".equals(individualPerformance.getYearCode())){
			sql.append(" and i.yearCode='"+individualPerformance.getYearCode()+"'");
		}
		return individualPerformanceDAO.executeQuery(sql.toString(),(currpage-1)*pageSize, pageSize);
	}
	/****************************************************************************
	 * 功能：上传个人绩效文档
	 * 作者：廖文辉
	 ****************************************************************************/
	public void performanceDocumentUpload(HttpServletRequest request,
									  HttpServletResponse response, Integer id,int type) {
		String sep = System.getProperty("file.separator");
		String path = sep+ "upload"+ sep+"performance"+sep+id;
		shareService.uploadFiles(request, path,"savePerformanceDocument",id);
	}
	/****************************************************************************
	 * 功能：保存实验分室的文档
	 * 作者：廖文辉
	 * 时间：2018-9-21
	 ****************************************************************************/
	public void saveLabRoomDocument(String fileTrueName, Integer id,Integer type) {
		// TODO Auto-generated method stub
		//id对应的实验分室
		IndividualPerformance individualPerformance =individualPerformanceDAO.findIndividualPerformanceById(id);
		CommonDocument doc=new CommonDocument();
		doc.setType(type);
		doc.setDocumentName(fileTrueName);
		String imageUrl="upload/performance/"+id+"/"+fileTrueName;
		doc.setDocumentUrl(imageUrl);
		doc.setIndividualPerformance(individualPerformance);
		commonDocumentDAO.store(doc);
	}
	/****************************************************************************
	 * 功能：查找课程
	 * 作者：廖文辉
	 * 时间：2018-09-26
	 ****************************************************************************/
	public   List<SchoolCourseInfo>  getSchoolCourseInfoList(){
		String sql= "select  distinct s from SchoolCourseInfo s where 1=1 ";
		return	schoolCourseInfoDAO.executeQuery(sql,0,-1);
	}
	/****************************************************************************
	 * 功能：查找专业
	 * 作者：廖文辉
	 * 时间：2018-09-26
	 ****************************************************************************/
	public List<SchoolMajor> getSchoolMajorList(){
		String sql= "select s from SchoolMajor s where 1=1 ";
		return	schoolMajorDAO.executeQuery(sql,0,-1);
	}
	/****************************************************************************
	 * 功能：查找属于所选学院的老师
	 * 作者：廖文辉
	 * 时间：2018-09-28
	 ****************************************************************************/
	public List<User> findTeacheresBySchoolAcademy(String acno){
		String s = "select u from User u where u.userRole=1";
		s +=" and u.schoolAcademy.academyNumber='"+acno+"'";
		List<User> users = userDAO.executeQuery(s, 0, -1);
		return users;
	}
}