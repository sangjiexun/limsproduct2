package net.zjcclims.service.timetable;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.zjcclims.domain.SchoolClasses;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.dao.LabCenterDAO;
import net.zjcclims.dao.SchoolCourseInfoDAO;
import net.zjcclims.dao.TimetableBatchDAO;
import net.zjcclims.dao.TimetableBatchItemsDAO;
import net.zjcclims.dao.TimetableCourseStudentDAO;
import net.zjcclims.dao.TimetableGroupDAO;
import net.zjcclims.dao.TimetableSelfCourseDAO;
import net.zjcclims.dao.UserDAO;
import net.zjcclims.domain.SchoolCourseInfo;
import net.zjcclims.domain.TimetableBatch;
import net.zjcclims.domain.TimetableBatchItems;
import net.zjcclims.domain.TimetableCourseStudent;
import net.zjcclims.domain.TimetableGroup;
import net.zjcclims.domain.TimetableSelfCourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("TimetableSelfCourseService")
public class TimetableSelfCourseServiceImpl implements TimetableSelfCourseService {
	@Autowired
	private TimetableSelfCourseDAO timetableSelfCourseDAO;
	@Autowired
	private ShareService shareService;
	@Autowired
	private SchoolCourseInfoDAO schoolCourseInfoDAO;
	@Autowired
	private TimetableCourseStudentDAO timetableCourseStudentDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private TimetableBatchItemsDAO timetableBatchItemsDAO;
	@Autowired
	private TimetableGroupDAO timetableGroupDAO;
	@Autowired
	private TimetableBatchDAO timetableBatchDAO;
	@Autowired
	private LabCenterDAO labCenterDAO;

	/*************************************************************************************
	 * @內容：查找所有的自建课程信息
	 * @作者：魏诚
	 * @日期：2014-08-25
	 *************************************************************************************/
	public List<TimetableSelfCourse> findAllTimetableSelfCourses(int curr, int size) {
		// 利用sql语句从用户表中查找出所有的用户，并赋给StringBuffer类型的sb变量
		StringBuffer sb = new StringBuffer(
				"select c from TimetableSelfCourse c where 1=1 and c.schoolAcademy.academyNumber like '"
						+ shareService.getUserDetail().getSchoolAcademy().getAcademyNumber() + "'");
		// 给语句添加分页机制
		List<TimetableSelfCourse> timetableSelfCourses = timetableSelfCourseDAO.executeQuery(sb.toString(),
				curr * size, size);
		return timetableSelfCourses;
	}

	/*************************************************************************************
	 * @內容：获取查找自建课程信息记录数
	 * @作者： 魏诚
	 * @日期：2014-08-25
	 *************************************************************************************/
	@Transactional
	public int getTimetableSelfCourseTotalRecords() {
		// 得出用户数量（由于用户的数据量比较多，不能够使用userDAO.findAllUsers()方法查找用户）
		return ((Long) timetableSelfCourseDAO.createQuerySingleResult(
				"select count(*) from TimetableSelfCourse c where 1=1 and c.schoolAcademy.academyNumber like '"
						+ shareService.getUserDetail().getSchoolAcademy().getAcademyNumber() + "'").getSingleResult())
				.intValue();
	}

	/*************************************************************************************
	 * @內容：获取最大自建课程信息id
	 * @作者： 魏诚
	 * @日期：2014-08-25
	 *************************************************************************************/
	@Transactional
	public int getMaxTimetableSelfCourseId() {
		// 得出用户数量（由于用户的数据量比较多，不能够使用userDAO.findAllUsers()方法查找用户）
		return ((Long) timetableSelfCourseDAO.createQuerySingleResult(
				"select max(c.id) from TimetableSelfCourse c where 1=1 and c.schoolAcademy.academyNumber like '"
						+ shareService.getUserDetail().getSchoolAcademy().getAcademyNumber() + "'").getSingleResult())
				.intValue();
	}

	/*************************************************************************************
	 * @內容：获取自建课程信息的分页列表信息
	 * @作者： 魏诚
	 * @日期：2014-08-25
	 *************************************************************************************/
	public List<TimetableSelfCourse> findAllTimetableSelfCourses(TimetableSelfCourse timetableSelfCourse, int curr,
			int size, String acno) {
		String query = "";
		// 判断获取的用户的信息是否为空
		if (timetableSelfCourse.getSchoolCourseInfo() != null
				&& timetableSelfCourse.getSchoolCourseInfo().getCourseName() != null&&!timetableSelfCourse.getSchoolCourseInfo().getCourseName().equals("")) {
			query = query + " and c.schoolCourseInfo.courseName like '%"
					+ timetableSelfCourse.getSchoolCourseInfo().getCourseName() + "%'";
		}

		// 判断获取的学期
		if (timetableSelfCourse.getSchoolTerm()!=null) {
			query = query + " and c.schoolTerm.id =" + timetableSelfCourse.getSchoolTerm().getId() + " " ;
		}
		// 查询表；
		/*StringBuffer sql = new StringBuffer(
				"select c from TimetableSelfCourse c where  1=1 and c.schoolAcademy.academyNumber like '"
						+ labCenterDAO.findLabCenterById(cid).getSchoolAcademy().getAcademyNumber() + "' ");*/
		StringBuffer sql = new StringBuffer(
				"select c from TimetableSelfCourse c where  1=1");
		// 将query添加到sb1后
		sql.append(query);
		// sql.append(" order by c.username");
		// 执行sb语句
		List<TimetableSelfCourse> timetableSelfCourses = timetableSelfCourseDAO.executeQuery(sql.toString(), curr
				* size, size);
		return timetableSelfCourses;
	}

	/*************************************************************************************
	 * @內容：根据以选课组为单元的获取实验安排表分组
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public List<TimetableSelfCourse> getCourseCodeInTimetableSelfCourse(int term) {
		StringBuffer sql = new StringBuffer(
				"select c from TimetableSelfCourse c where 1=1 and c.schoolAcademy.academyNumber like '"
						+ shareService.getUserDetail().getSchoolAcademy().getAcademyNumber() + "' ");
		if (term != -1) {
			sql.append("and c.schoolTerm.id=" + term + "  order by c.courseCode ");
		}
		// 执行sb语句
		List<TimetableSelfCourse> timetableSelfCourses = timetableSelfCourseDAO.executeQuery(sql.toString(), 0, -1);
		return timetableSelfCourses;
	}

	/*************************************************************************************
	 * @內容：保存 新建自主排课
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public void saveSelfSchoolCourseInfo(SchoolCourseInfo schoolCourseInfo, String flagID) {
		// 设置标记位为自建课程
		schoolCourseInfo.setFlag(1);
		if (flagID.equals("-1")) {
			schoolCourseInfo.setCreatedAt(Calendar.getInstance());
			schoolCourseInfo.setUpdatedAt(Calendar.getInstance());
			schoolCourseInfo = schoolCourseInfoDAO.store(schoolCourseInfo);
			schoolCourseInfoDAO.flush();
		} else {
			SchoolCourseInfo schoolCourseInfoEdit = schoolCourseInfoDAO.findSchoolCourseInfoByCourseNumber(flagID);
			schoolCourseInfoEdit.setAcademyNumber(schoolCourseInfo.getAcademyNumber());
			schoolCourseInfoEdit.setCourseName(schoolCourseInfo.getCourseName());
			schoolCourseInfoEdit.setUpdatedAt(Calendar.getInstance());
			schoolCourseInfoDAO.store(schoolCourseInfoEdit);
			schoolCourseInfoDAO.flush();
		}
	}

	/*************************************************************************************
	 * @內容：保存 新建自主排课的选课组
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public String saveTimetableSelfCourse(HttpServletRequest request, TimetableSelfCourse timetableSelfCourse,
			int flagID) {
		timetableSelfCourse.setSchoolAcademy(shareService.getUserDetail().getSchoolAcademy());
		String returnString = "";
		if (flagID == -1) {
			timetableSelfCourse = timetableSelfCourseDAO.store(timetableSelfCourse);
			timetableSelfCourseDAO.flush();
			returnString = "redirect:/timetable/selfTimetable/newCourseCodeIframeDetail?id="
					+ timetableSelfCourse.getId();
		} else {
			returnString = "redirect:/timetable/selfTimetable/listCourseCodes?currpage=1";
			TimetableSelfCourse timetableSelfCourseEdit = timetableSelfCourseDAO.findTimetableSelfCourseById(flagID);
			timetableSelfCourseEdit.setCourseCode(timetableSelfCourse.getCourseCode());
			timetableSelfCourseEdit.setName(timetableSelfCourse.getName());
			timetableSelfCourseEdit.setSchoolAcademy(timetableSelfCourse.getSchoolAcademy());
			timetableSelfCourseEdit.setSchoolCourseInfo(timetableSelfCourse.getSchoolCourseInfo());
			timetableSelfCourseEdit.setUser(timetableSelfCourse.getUser());
			timetableSelfCourseEdit.setCourseCount(timetableSelfCourse.getCourseCount());
			timetableSelfCourseEdit.setSchoolTerm(timetableSelfCourse.getSchoolTerm());
			timetableSelfCourseDAO.store(timetableSelfCourseEdit);
			timetableSelfCourseDAO.flush();

			// 删除原有的课程学生名单
			for (TimetableCourseStudent student : timetableCourseStudentDAO
					.executeQuery("select c from TimetableCourseStudent c where c.timetableSelfCourse.id="
							+ timetableSelfCourseEdit.getId())) {
				timetableCourseStudentDAO.remove(student);
			}
			;

			// 保存学生名单
			String students = request.getParameter("students");
			students = students.replaceAll("\t", "");
			String[] sStudents = students.replaceAll("\r\n", ";").split(";");
			for (String student : sStudents) {
				if (student == null || student.equals("") || student.equals("\t")) {
					continue;
				}
				TimetableCourseStudent timetableCourseStudent = new TimetableCourseStudent();
				timetableCourseStudent.setTimetableSelfCourse(timetableSelfCourseEdit);
				timetableCourseStudent.setUser(userDAO.findUserByPrimaryKey(student));
				// 判断是否重复
				if (timetableCourseStudentDAO.executeQuery(
						"select c from TimetableCourseStudent c where c.user.username like '" + student
								+ "' and c.timetableSelfCourse =" + timetableSelfCourseEdit.getId()).size() == 0) {
					timetableCourseStudentDAO.store(timetableCourseStudent);
					timetableCourseStudentDAO.flush();
				}
			}

		}
		return returnString;
	}

	/**************************************************************************************
	 * @自主排课： 删除id对应的批次的所有记录
	 * @作者：魏诚
	 * @日期：2014-07-25
	 **************************************************************************************/
	public String deleteBatch(int id, int term, String courseCode) {
		String returnUrl = "";
		try {
			// 删除关联项目的记录
			for (TimetableBatchItems timetableBatchItems : timetableBatchItemsDAO
					.executeQuery("select c from TimetableBatchItems c where c.timetableBatch.id =" + id)) {
				timetableBatchItemsDAO.remove(timetableBatchItems);
			}
			TimetableBatch timetableBatch = timetableBatchDAO.findTimetableBatchById(id);
			// 删除批次下的分组记录
			// 删除关联项目的记录
			for (TimetableGroup timetableGroup : timetableGroupDAO
					.executeQuery("select c from TimetableGroup c where c.timetableBatch.id =" + id)) {
				timetableGroupDAO.remove(timetableGroup);
			}
			timetableGroupDAO.executeQuery("delete TimetableGroup c where c.timetableBatch.id =" + id);
			// 删除批次
			timetableBatchDAO.remove(timetableBatch);
		} catch (Exception e) {
		}
		return returnUrl;
	}

	/**************************************************************************************
	 * @自主排课： 根据学期和学院获取自主排课列表
	 * @作者：魏诚
	 * @日期：2014-07-25
	 **************************************************************************************/
	public List<TimetableSelfCourse> getTimetableSelfCourseByTermAcademy(HttpServletRequest request) {
		return timetableSelfCourseDAO.executeQuery("select c from TimetableSelfCourse c where c.schoolTerm.id ="
				+ request.getParameter("term") + " and c.schoolAcademy.academyNumber like '"
				+ shareService.getUserDetail().getSchoolAcademy().getAcademyNumber() + "'", 0, -1);
	}
	
	/*************************************************************************************
	 * @功能：根据selfCourseId获取与此选课组有相同班级的其他选课组
	 * @作者：贺子龙
	 * @日期：2016-06-05
	 *************************************************************************************/
	public List<TimetableSelfCourse> findTimetableSelfCourseWithSameClass(int selfCourseId){
		/**
		 * 1、找到原选课组；
		 * 2、找到原选课组对应的班级；
		 * 3、找到相同班级的选课组（包括原选课组本身）。
		 */
		String sql = "select t from TimetableSelfCourse t left join t.schoolClassess tcc where 1=0";
		TimetableSelfCourse orgSelfCourose = timetableSelfCourseDAO.findTimetableSelfCourseByPrimaryKey(selfCourseId);
		/*Set<SchoolClasses> classes = orgSelfCourose.getSchoolClassess();
		if (classes!=null && classes.size()>0) {
			for (SchoolClasses schoolClasses : classes) {
				sql+=" or tcc.classNumber like '"+schoolClasses.getClassNumber()+"'";
			}
			sql+=" or 1=0";
		}*/
		List<TimetableSelfCourse> selfCouroses = timetableSelfCourseDAO.executeQuery(sql, 0, -1);
		return selfCouroses;
	}
	/**************************************************************************************
	 * @自主排课： 根据course_code查询TimetableSelfCourse记录
	 * @作者：贺子龙
	 * @日期：2016-07-16
	 **************************************************************************************/
	public TimetableSelfCourse findTimetableSelfCourseByCourseCode(String courseCode){
		String sql = "select t from TimetableSelfCourse t where 1=1";
		sql+= " and t.courseCode like '"+courseCode+"'";
		List<TimetableSelfCourse> courseList = timetableSelfCourseDAO.executeQuery(sql, 0, -1);
		if (courseList!=null && courseList.size()>0) {
			return courseList.get(0);
		}else {
			return null;
		}
	}
	
}