package net.zjcclims.service.timetable;

import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.GregorianCalendar;
import java.util.List;

@Service("TimetableBatchService")
public class TimetableBatchServiceImpl implements TimetableBatchService {

	@Autowired
	private ShareService shareService;
	@Autowired
	private TimetableBatchDAO timetableBatchDAO;
	@Autowired
	private LabCenterDAO labCenterDAO;
	@Autowired
	private SchoolCourseDAO schoolCourseDAO;
	@Autowired
	private TimetableSelfCourseDAO timetableSelfCourseDAO;
	@Autowired
	private TimetableGroupDAO timetableGroupDAO;
	@Autowired
	private SchoolCourseStudentDAO schoolCourseStudentDAO;
	@Autowired
	private TimetableCourseStudentDAO timetableCourseStudentDAO;
	/*************************************************************************************
	 * @內容：查看所有的预约的列表安排
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	@SuppressWarnings("unused")
	public List<TimetableBatch> getTimetableBatchByQuery(int termId, int status, int curr, int size) {
		// 创建根据学期来查询课程；
		StringBuffer sql = new StringBuffer("select c from TimetableBatch  c where 1=1 ");

		List<TimetableBatch> timetableBatchList = timetableBatchDAO.executeQuery(sql.toString(), curr * size, size);
		
		//返回值
		List<TimetableBatch> returnTimetableBatchList = timetableBatchDAO.executeQuery(sql.toString(),0, -1);
		
		return returnTimetableBatchList;
	}

	/*************************************************************************************
	 * @內容：查看计数的所有时间列表安排
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public int getCountTimetableBatchByQuery(int termId,int status, String acno) {
		String academyNumber = "";
		// 如果没有获取有效的实验分室列表-根据登录用户的所属学院
		SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
		if (academy!=null && academy.getAcademyNumber()!=null) {
			// 获取选择的实验中心
			academyNumber = academy.getAcademyNumber();
		} else {
			academyNumber = shareService.getUserDetail().getSchoolAcademy().getAcademyNumber();
		}
		// 创建根据学期来查询课程；
		StringBuffer sql = new StringBuffer("select c from TimetableBatch  c where 1=1 ");

		List<TimetableBatch> timetableBatchList = timetableBatchDAO.executeQuery(sql.toString(), 0, -1);
		//返回值
		List<TimetableBatch> returnTimetableBatchList = timetableBatchDAO.executeQuery(sql.toString(), 0, -1);
		// 判断批次所在的部门
		for (TimetableBatch timetableBatch : timetableBatchList) {
			// 如果为教务类排课
			if (!schoolCourseDAO.findSchoolCourseByCourseCode(timetableBatch.getCourseCode()).isEmpty()) {
				if (!schoolCourseDAO.findSchoolCourseByCourseCode(timetableBatch.getCourseCode()).iterator().next()
						.getSchoolAcademy().getAcademyNumber().equals(academyNumber)) {
					returnTimetableBatchList.remove(timetableBatch);
				}
			}
			// 如果为自主类排课
			if (!timetableSelfCourseDAO.findTimetableSelfCourseByCourseCode(timetableBatch.getCourseCode()).isEmpty()) {
				if (!timetableSelfCourseDAO.findTimetableSelfCourseByCourseCode(timetableBatch.getCourseCode())
						.iterator().next().getSchoolAcademy().getAcademyNumber().equals(academyNumber)) {
					returnTimetableBatchList.remove(timetableBatch);
				}
			}
		}

		return returnTimetableBatchList.size();
	}


	/***********************************************************************************************
	 * Description：排课模块通用{通过选课组编号查询分组}
	 *
	 * @author：贺子龙
	 * @Date：2016-08-27
	 ***********************************************************************************************/
	public List<TimetableGroup> findTimetableGroupByCourseCode(String courseCode){
		// 建立查询
		String sql = "select t from TimetableGroup t, TimetableBatch tb where 1=1";
		// 联合查询
		sql+=" and t.timetableBatch.id = tb.id";
		// 限制条件（选课组编号）
		sql+=" and tb.courseCode = '"+courseCode+"'";
		sql+=" and tb.ifselect in (0,1) ";
		// 排序
		sql+=" order by tb.id, t.id";
		// 执行查询
		List<TimetableGroup> timetableGroups = timetableGroupDAO.executeQuery(sql);
		// 结果返回
		return timetableGroups;
	}

	/***********************************************************************************************
	 * Description：排课模块通用{教务排课：选课组编号查询选课组下的学生}
	 *
	 * @author：戴昊宇
	 * @Date：2017-08-26
	 ***********************************************************************************************/
	public List<SchoolCourseStudent> findSchoolCourseStudentByCourseCode(String courseCode){
		// 确定主表
		String sql = "select distinct s from SchoolCourseStudent s, SchoolCourseDetail scd where 1=1";
		// 联合查询
		sql+=" and s.schoolCourseDetail.courseDetailNo = scd.courseDetailNo";
		// 限制条件
		sql+=" and scd.schoolCourse.courseNo like '"+courseCode+"'";
		// 执行查询
		List<SchoolCourseStudent> schoolCourseStudentList = schoolCourseStudentDAO.executeQuery(sql, 0, -1);
		// 返回结果
		return schoolCourseStudentList;
	}

	/***********************************************************************************************
	 * Description：排课模块通用{保存分批信息}
	 *
	 * @author：贺子龙
	 * @Date：2016-08-27
	 ***********************************************************************************************/
	public Integer saveTimetableBatch(TimetableBatch timetableBatch, int isSelf){
		// 编辑和新建标志位
		boolean isEdit = false;
		if (timetableBatch.getId()!=null) {// 如果id为空，新建；否则，编辑
			isEdit = true;
		}
		if(timetableBatch.getStartDate() == null){
			timetableBatch.setStartDate(new GregorianCalendar());
		}
		if(timetableBatch.getEndDate() == null){
			timetableBatch.setEndDate(new GregorianCalendar());
		}
		// 保存分批
		timetableBatch = timetableBatchDAO.store(timetableBatch);
		timetableBatchDAO.flush();
		if (!isEdit) {
			// 生成分组
			// 确定选课组
			String courseCode = timetableBatch.getCourseCode();
			// 选课下的学生人数
			int studentNumber = 0;
			// 找到选课组下的所有学生
			if (isSelf == 0||isSelf==3) {// 非自主排课
				List<SchoolCourseStudent> students = this.findSchoolCourseStudentByCourseCode(courseCode);
				studentNumber =  students.size();
			}else {// 自主排课
				List<TimetableCourseStudent> students = this.findTimetableCourseStudentByCourseCode(courseCode);
				studentNumber =  students.size();
			}
			// 批次下面的分组数量
			int groupNumber = timetableBatch.getCountGroup();
			//平均数
			int average = studentNumber/groupNumber;
			// 循环建立分组
			TimetableGroup group = new TimetableGroup();
			for (int i = 1; i < groupNumber+1; i++) {
				// 名称
				group.setGroupName("第"+i+"组");
				// 分批外键
				group.setTimetableBatch(timetableBatch);
				// 设置每个分组里面学生的数量
				if (i!=groupNumber) {
					group.setNumbers(average);
				}else {
					//最后一组放剩下的学生
					group.setNumbers(studentNumber-average*(groupNumber-1));
				}
				// 保存分组
				timetableGroupDAO.store(group);
				timetableGroupDAO.flush();
			}
		}
		return timetableBatch.getId();
	}

	/***********************************************************************************************
	 * Description：排课模块通用{自主排课：选课组编号查询选课组下的学生}
	 *
	 * @author：贺子龙
	 * @Date：2016-08-27
	 ***********************************************************************************************/
	public List<TimetableCourseStudent> findTimetableCourseStudentByCourseCode(String courseCode){
		// 确定主表
		String sql = "select distinct s from TimetableCourseStudent s, TimetableSelfCourse scd where 1=1";
		// 联合查询
		sql+=" and s.timetableSelfCourse.id = scd.id";
		// 限制条件
		sql+=" and scd.courseCode like '"+courseCode+"'";
		// 执行查询
		List<TimetableCourseStudent> timetableCourseStudentList = timetableCourseStudentDAO.executeQuery(sql, 0, -1);
		// 返回结果
		return timetableCourseStudentList;
	}

	public List<TimetableBatch> getTimetableBatchByCourseCode(String courseNo){
		String sql = "select t from TimetableBatch t where t.courseCode = '" + courseNo +"'";
		List<TimetableBatch> batches = timetableBatchDAO.executeQuery(sql,-1,-1);
		return batches;
	}

	/***********************************************************************************************
	 * Description：排课--分批排课{根据主键查找分组}
	 *
	 * @author：贺子龙
	 * @Date：2016-08-27
	 ***********************************************************************************************/
	public List<TimetableGroup> findTimetableGroupsByBatchId(int batchId){
		// 建立查询
		String sql="select g from TimetableGroup g where g.timetableBatch.id ="+batchId;
		// 执行查询
		List<TimetableGroup> groups=timetableGroupDAO.executeQuery(sql);
		// 返回结果
		return groups;
	}
}