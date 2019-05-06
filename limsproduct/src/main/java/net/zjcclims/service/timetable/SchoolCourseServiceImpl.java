package net.zjcclims.service.timetable;

import net.zjcclims.dao.*;
import net.zjcclims.domain.SchoolAcademy;
import net.zjcclims.domain.SchoolCourse;
import net.zjcclims.domain.SchoolTerm;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service("SchoolCourseService")
public class SchoolCourseServiceImpl implements SchoolCourseService {
	 
	@Autowired
	private SchoolCourseDAO schoolCourseDAO;
	@Autowired
	private ShareService shareService;
	@Autowired
	private LabCenterDAO labCenterDAO;
	@Autowired
	private SchoolTermDAO schoolTermDAO;
	@Autowired
	private SchoolAcademyDAO schoolAcademyDAO;
	@Autowired
	private LabRoomDAO labRoomDAO;
	@Autowired
	private UserDAO userDAO;
	
	/*************************************************************************************
	 * @內容：进行termid,获取教务选课组编号
	 * @作者： 魏誠
	 * @日期：2014-09-16
	 *************************************************************************************/
	public List<SchoolCourse> getCourseCodeListAll(int termId,String acno) {
		String sql="select c from SchoolCourse c where 1=1";
		sql+=" and c.schoolTerm.id ="+termId;
	/*	sql+=" and schoolAcademy.academyNumber like '"+academyNumber+"'";*/
		sql+=" and c.state=1";
		sql+=" and c.schoolCourseDetails.size>0";
		List<SchoolCourse> list = schoolCourseDAO.executeQuery(sql,0,-1);
		return list;
	}
	
	/*************************************************************************************
	 * @內容：进行termid,获取教务选课组编号
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public String getCourseCodeList(int term, String acno) {

        String academyNumber="";
        // 如果没有获取有效的实验分室列表-根据登录用户的所属学院
        if (acno!=null && !acno.equals("-1")) {
    		//获取选择的实验中心
        	academyNumber = shareService.findSchoolAcademyByPrimaryKey(acno).getAcademyNumber();
        }else{
        	academyNumber = shareService.getUserDetail().getSchoolAcademy().getAcademyNumber();
        }
		// 返回可用的星期信息
		List<SchoolCourse> list = schoolCourseDAO.executeQuery("select c from SchoolCourse c where c.schoolTerm.id =" + term + " and schoolAcademy.academyNumber like '" + academyNumber + "'and c.state=1 and c.schoolCourseDetails.size>0",0,-1);
		String jsonWeek = "[";
		// 遍历实验分室
		for (SchoolCourse schoolCourse : list) {
				jsonWeek = jsonWeek + "{\"courseNo\":\"" + schoolCourse.getCourseNo() + "\",\"value\":\"" + schoolCourse.getSchoolCourseInfo().getCourseNumber() + schoolCourse.getUserByTeacher().getCname() + schoolCourse.getCourseName()+"；选课组编号："+ schoolCourse.getCourseCode()+"\"},";
		}
		jsonWeek.substring(0,jsonWeek.length()-1);
		jsonWeek = jsonWeek + "]";
		return jsonWeek;

	}
	
	/*************************************************************************************
	 * @內容：进行termid,获取教务选课组编号
	 * @作者： 魏誠
	 * @日期：2014-09-16
	 *************************************************************************************/
	public List<SchoolCourse> getCourseCodeList(int termId) {
		String sql;
		String academyNumber = shareService.getUserDetail().getSchoolAcademy().getAcademyNumber();
		sql="select c from SchoolCourse c,SchoolCourseDetail d where 1=1 and d.schoolCourse.courseNo=c.courseNo" +
				" and c.schoolTerm.id =" + termId 
				+ " and c.state=1 and c.schoolCourseDetails.size<>0" +
				" and d.endClass>0 and d.state=1";
		//2015-11-27  所有选课组放开学院
	/*	+" and schoolAcademy.academyNumber like '" + 
		shareService.getUserDetail().getSchoolAcademy().getAcademyNumber()+"'" */
		sql+=" group by c.courseNo order by " + "case when c.schoolAcademy.academyNumber like '" + academyNumber + "' then 0 else 1 end, "+
				"c.courseCode";//group by c.courseNo  为了防止教务排课管理页面--选课组：查询条件中出现看似重复的两条记录
		List<SchoolCourse> list = schoolCourseDAO.executeQuery(sql,0,-1);
		return list;
	}
	/*************************************************************************************
	 * @內容：通过主键查询school_course
	 * @作者：贺子龙
	 * @日期：2016-07-16
	 *************************************************************************************/
	public SchoolCourse findSchoolCourseByPrimaryKey(String courseNo){
		
		return schoolCourseDAO.findSchoolCourseByPrimaryKey(courseNo);
		
	}

	/*************************************************************************************
	 * @內容：保存选课程
	 * @作者： 戴昊宇
	 * @日期：2018-01-11
	 *************************************************************************************/
	public SchoolCourse saveSchoolCourse(SchoolCourse schoolCourse,HttpServletRequest request){
		/*if(schoolCourse.getCourseNo()==null || schoolCourse.getCourseNo().equals("")){
			//增加
			//id规则 schoolCourseInfo里的courseNumber 老师的username 四位增长数
			String courseNumber = schoolCourse.getSchoolCourseInfo().getCourseNumber();
			String teacherUsername = schoolCourse.getUserByTeacher().getUsername();
			StringBuffer str = new StringBuffer(courseNumber+teacherUsername);
			String code = getMaxCourseNo(str.toString());
			code = (Integer.parseInt(code)+1)+"";
			int codeLength = code.length();
			for(int i=0;i<4-codeLength;i++){
				code = "0"+code;
			}
			str.append(code);
			schoolCourse.setCourseNo(str.toString());

		}else{
			//修改
		}
		//设置默认的
		schoolCourse.setCourseCode(schoolCourse.getCourseNo());*/
		int labRoom=0;
		if(request.getParameter("labRoomId")!=null&&request.getParameter("labRoomId")!=""){
			labRoom =Integer.parseInt(request.getParameter("labRoomId"));
		}
		String teacher="";
		if(request.getParameter("teacher")!=null&&request.getParameter("teacher")!=""){
			teacher =request.getParameter("teacher");
		}
		int schoolTerm=0;
		if(request.getParameter("schoolTermd")!=null&&request.getParameter("schoolTermd")!=""){
			schoolTerm =Integer.valueOf(request.getParameter("schoolTermd"));
		}
		SchoolTerm findSchoolTermById = schoolTermDAO.findSchoolTermById(schoolTerm);
		String getCourseNo="";
		getCourseNo=request.getParameter("courseNo");
		if(getCourseNo==null || getCourseNo.equals("")){
			//自动生成的编号
			String autocourseNo="";
			autocourseNo=findSchoolTermById.getYearCode()+"-"+findSchoolTermById.getTermCode()+"-"+schoolCourse.getSchoolCourseInfo().getCourseNumber()+"-"+schoolCourseDAO.findAllSchoolCourses().size();
			schoolCourse.setCourseNo(autocourseNo);
			schoolCourse.setCourseCode(autocourseNo);
			//获得当前登陆人的学院信息
			SchoolAcademy schoolAcademy = schoolAcademyDAO.findSchoolAcademyByAcademyNumber(shareService.getUserDetail().getSchoolAcademy().getAcademyNumber());
			schoolCourse.setSchoolAcademy(schoolAcademy);
			schoolCourse.setState(1);
			schoolCourse.setLabRoom(labRoomDAO.findLabRoomById(labRoom));
			schoolCourse.setUserByTeacher(userDAO.findUserByUsername(teacher));
			schoolCourse.setSchoolTerm(findSchoolTermById);
			schoolCourseDAO.store(schoolCourse);
			schoolCourseDAO.flush();
			return schoolCourse;
		}else
		{
			SchoolCourse getschoolCourse = schoolCourseDAO.findSchoolCourseByCourseNo(getCourseNo);
			getschoolCourse.setCourseName(schoolCourse.getCourseName());
			//获得当前登陆人的学院信息
			SchoolAcademy schoolAcademy = schoolAcademyDAO.findSchoolAcademyByAcademyNumber(shareService.getUserDetail().getSchoolAcademy().getAcademyNumber());
			getschoolCourse.setSchoolAcademy(schoolAcademy);
			getschoolCourse.setState(1);
			getschoolCourse.setLabRoom(labRoomDAO.findLabRoomById(labRoom));
			getschoolCourse.setUserByTeacher(userDAO.findUserByUsername(teacher));
			getschoolCourse.setSchoolTerm(findSchoolTermById);
			schoolCourseDAO.store(getschoolCourse);
			schoolCourseDAO.flush();
			return getschoolCourse;
		}


	}
}