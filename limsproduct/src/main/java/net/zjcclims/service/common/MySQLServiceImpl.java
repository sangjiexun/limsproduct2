package net.zjcclims.service.common;

import net.zjcclims.dao.LabRoomLimitTimeDAO;
import net.zjcclims.dao.SystemTimeDAO;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.*;


@Service("MySQLService")
public class MySQLServiceImpl implements MySQLService {
	@PersistenceContext  
    private EntityManager entityManager;
	@Autowired
	private ShareService shareService;
    @Autowired
    LabRoomLimitTimeDAO labRoomLimitTimeDAO;
    @Autowired
    SystemTimeDAO systemTimeDAO;
	/**************************************************************************************
	 * 读取视图的数据并分页
	 * 李小龙
	 **************************************************************************************/
	@Override
	public List getViewOfStudentCourse(int pageSize,int page) {
		// TODO Auto-generated method stub
		Query query=entityManager.createNativeQuery("select student_number,student_name,course_number,course_name,course_code from view_lxl ");
		List list=query.setMaxResults(pageSize).setFirstResult((page-1)*pageSize).getResultList();
		return list;
	}
	/**************************************************************************************
	 * 根据课程id读取微课的章节
	 * 李小龙
	 **************************************************************************************/
	@Override
	public List getWKCourseChapter(int id) {
		String sql="select * from wk_chapter where course_id="+id;
		Query query=entityManager.createNativeQuery(sql);
		List sectionList=query.getResultList();
		
		return sectionList;
	}
	/**************************************************************************************
	 * 根据课程id读取微课的课时
	 * 李小龙
	 **************************************************************************************/
	@Override
	public List getWKCourseLesson(int id) {
		String sql="select * from wk_lesson where chapter_id in(select c.id from wk_chapter as c where c.course_id="+id+")";
		Query query=entityManager.createNativeQuery(sql);
		List lessonList=query.getResultList();
		
		return lessonList;
	} 
	
	/****************************************************
	 * 功能：调用存储过程--将针对实验室的排课映射到实验室禁用时间（直接排课专用）
	 * 作者： 贺子龙
	 * 日期：2016-05-28
	 *****************************************************/
	@Transactional
	public void createLabLimitByDirectAppointment(Integer appointmentId){
		Query query = entityManager.createNativeQuery("{call setLimitTimeFromDirectAppointment("+appointmentId+")}");
		query.executeUpdate();
	}
	
	/****************************************************
	 * 功能：调用存储过程--将针对实验室的排课映射到实验室禁用时间
	 * 作者： 贺子龙
	 * 日期：2016-05-28
	 *****************************************************/
	@Transactional
	public void createLabLimitByAppointment(Integer appointmentId){
		Query query = entityManager.createNativeQuery("{call setLimitTimeFromAppoint("+appointmentId+")}");
		query.executeUpdate();
	}

	/********************************************************************************
	 * Description: 排课-排课首页{列出所有的选课组}
	 * @author: 戴昊宇
	 * @date: 2017-08-25
	 *********************************************************************************/
	@Transactional
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List listCourseDetails(HttpServletRequest request,String acno, int page, int pageSize){
		String sql = "select * from view_course_detail c where 1=1 ";

		// 查询条件
		// 学期
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (!"".equals(request.getParameter("term"))) {
			if (request.getParameter("term") != null) {
				term = Integer.parseInt(request.getParameter("term"));
			}
			sql+=" and c.term = "+term;
		}
		if (pageSize!=-1) {// 非查找全部时
			// 教师
			String cName = request.getParameter("teacher");
			if (!EmptyUtil.isStringEmpty(cName)) {
				sql+=" and c.teacherUsername like '"+cName+"'";
			}
			// 课程编号
			String courseNo = request.getParameter("courseNo");
			if (!EmptyUtil.isStringEmpty(courseNo)) {
				sql+=" and c.course_no like '"+courseNo+"'";
			}
		}
//		sql+=" group by c.cname";
		// 获取登录用户信息
        User user = shareService.getUserDetail();
		//判断当登录用户是且仅是老师时只查看自己的教学进度排课列表
		if (user.getAuthorities().size() < 2) {
			for (Authority a : user.getAuthorities()) {
				if (a.getId() == 2 ) {
					sql+=" and c.teacherUsername like '"+user.getUsername()+"'";
				}
			}
		}
        sql+=" order by c.course_name";

		Query query= entityManager.createNativeQuery(sql);
		// 以下两行是分页设置   pageSize为-1时，不做限制
		// setMaxResults:Set the maximum number of rows to retrieve.
		// If not set, there is no limit to the number of rows retrieved.
		if (pageSize!=-1) {
			query.setMaxResults(pageSize);
		}
		query.setFirstResult((page-1)*pageSize);
		// 获取list对象
		List<Object[]> list= query.getResultList();
		return list;
	}

	/********************************************************************************
	 * Description: 排课-排课首页{查询下拉框列出所有选课组}
	 * @author: 杨新蔚
	 * @date: 2018-08-07
	 *********************************************************************************/
	@Transactional
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List listCourseDetailsQuery(){
		String sql = "select sc.courseName,sc.courseNo from SchoolCourse sc where 1=1 ";
		Query query= entityManager.createQuery(sql);
		// 获取list对象
		List<Object[]> list= query.getResultList();
		return list;
	}

	/********************************************************************************
	 * Description: 排课-排课首页{列出所有的选课组}
	 * @author: 戴昊宇
	 * @date: 2017-08-25
	 *********************************************************************************/
	@Transactional
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List listCourseDetailsFortearch(HttpServletRequest request, String acno, int page,int pageSize){
		String sql = "select sc.userByTeacher.username,sc.userByTeacher.cname from SchoolCourse sc where 1=1";
  		sql+=" group by sc.userByTeacher.username,sc.userByTeacher.cname order by sc.userByTeacher.username";
		Query query= entityManager.createQuery(sql);
		if (pageSize!=-1) {
			query.setMaxResults(pageSize);
		}
		query.setFirstResult((page-1)*pageSize);
		// 获取list对象
		List<Object[]> list= query.getResultList();
		return list;
	}
	/********************************************************************************
	 * Description: 排课-排课首页{所有选课组数量}
	 * @author: 戴昊宇
	 * @date: 2017-08-25
	 *********************************************************************************/
	@Transactional
	public int countCourseDetails(HttpServletRequest request, String acno){
		String sql = "select count(*) from view_course_detail c where 1=1";

		// 查询条件
		// 学期
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (!"".equals(request.getParameter("term"))) {
			if (request.getParameter("term") != null) {
				term = Integer.parseInt(request.getParameter("term"));
			}
			sql+=" and c.term = "+term;
		}
		// 教师
		String teacherUsername = request.getParameter("teacher");
		if (!EmptyUtil.isStringEmpty(teacherUsername)) {
			sql+=" and c.teacherUsername like '"+teacherUsername+"'";
		}
		// 课程编号
		String courseNo = request.getParameter("courseNo");
		if (!EmptyUtil.isStringEmpty(courseNo)) {
			sql+=" and c.course_no like '"+courseNo+"'";
		}
        // 获取登录用户信息
        User user = shareService.getUserDetail();
        //判断当登录用户是且仅是老师时返回自己的教学进度排课总数
        if (user.getAuthorities().size() < 2) {
            for (Authority a : user.getAuthorities()) {
                if (a.getId() == 2 ) {
                    sql+=" and c.teacherUsername like '"+user.getUsername()+"'";
                }
            }
        }

		// 以下两行是分页设置
		Query query = entityManager.createNativeQuery(sql);
		// 获取对象条数
		int count= ((BigInteger)query.getSingleResult()).intValue();
		return count;
	}

	/********************************************************************************
	 * Description: 我的排课选课组
	 * @author: 戴昊宇
	 * @date: 2017-08-25
	 *********************************************************************************/
	@Transactional
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List mylistCourseDetails(HttpServletRequest request,String acno, int page,int pageSize){
		String sql = "select * from view_course_detail c where 1=1";
		// 学期
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term") != null) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and c.term = "+term;
		if (pageSize!=-1) {// 非查找全部时
			// 教师
			String teacherUsername = shareService.getUserDetail().getUsername();
			sql+=" and c.teacherUsername like '"+teacherUsername+"'";

			// 课程编号
			String courseNo = request.getParameter("courseNo");
			if (!EmptyUtil.isStringEmpty(courseNo)) {
				sql+=" and c.course_no like '"+courseNo+"'";
			}
		}
		sql+=" and c.courseType = "+2;
		Query query= entityManager.createNativeQuery(sql);
		// 以下两行是分页设置   pageSize为-1时，不做限制
		// setMaxResults:Set the maximum number of rows to retrieve.
		// If not set, there is no limit to the number of rows retrieved.
		if (pageSize!=-1) {
			query.setMaxResults(pageSize);
		}
		query.setFirstResult((page-1)*pageSize);
		// 获取list对象
		List<Object[]> list= query.getResultList();
		return list;
	}

	/********************************************************************************
	 * Description: 我的排课选课组数量
	 * @author: 戴昊宇
	 * @date: 2017-08-25
	 *********************************************************************************/
	@Transactional
	public int countmyCourseDetails(HttpServletRequest request,String acno){
		String sql = "select count(*) from view_course_detail c where 1=1";

		// 查询条件
		// 学院
		String academyNumber = shareService.getUserDetail().getSchoolAcademy().getAcademyNumber();
		sql+=" and c.academyNumber = "+academyNumber;
		// 学期
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term") != null) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and c.term = "+term;
		// 教师
		String teacherUsername = shareService.getUserDetail().getUsername();
		sql+=" and c.teacherUsername like '"+teacherUsername+"'";
		// 课程编号
		String courseNo = request.getParameter("courseNo");
		if (!EmptyUtil.isStringEmpty(courseNo)) {
			sql+=" and c.course_no like '"+courseNo+"'";
		}
		sql+=" and c.courseType = "+2;
		// 以下两行是分页设置
		Query query = entityManager.createNativeQuery(sql);
		// 获取对象条数
		int count= ((BigInteger)query.getSingleResult()).intValue();
		return count;
	}

	/********************************************************************************
	 * Description: 排课-排课首页{列出所有的选课组}
	 * @author: 戴昊宇
	 * @date: 2017-08-25
	 *********************************************************************************/
	@Transactional
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List listCourseDetails(HttpServletRequest request,String acno, int page,int pageSize,Integer flag){
		String sql = "select * from view_course_detail c where 1=1";

		// 查询条件
		// 学院
		//String academyNumber = labCenterDAO.findLabCenterByPrimaryKey(cid).getSchoolAcademy().getAcademyNumber();
		//if (!EmptyUtil.isStringEmpty(academyNumber)) {
		//}
		// 学期
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term") != null) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and c.term = "+term;
		if (pageSize!=-1) {// 非查找全部时
			// 教师
			String teacherUsername = request.getParameter("teacher");
			if (!EmptyUtil.isStringEmpty(teacherUsername)) {
				sql+=" and c.teacherUsername like '"+teacherUsername+"'";
			}
			// 课程编号
			String courseNo = request.getParameter("courseNo");
			if (!EmptyUtil.isStringEmpty(courseNo)) {
				sql+=" and c.course_no like '"+courseNo+"'";
			}
		}
		sql+=" and c.courseType = "+flag;
		Query query= entityManager.createNativeQuery(sql);
		// 以下两行是分页设置   pageSize为-1时，不做限制
		// setMaxResults:Set the maximum number of rows to retrieve.
		// If not set, there is no limit to the number of rows retrieved.
		if (pageSize!=-1) {
			query.setMaxResults(pageSize);
		}
		query.setFirstResult((page-1)*pageSize);
		// 获取list对象
		List<Object[]> list= query.getResultList();
		return list;
	}

	/********************************************************************************
	 * Description: 排课-排课首页{所有选课组数量}
	 * @author: 戴昊宇
	 * @date: 2017-08-25
	 *********************************************************************************/
	@Transactional
	public int countCourseDetails(HttpServletRequest request, Integer cid,int flag){
		String sql = "select count(*) from view_course_detail c where 1=1";

		// 查询条件
		// 学院
		String academyNumber = shareService.getUserDetail().getSchoolAcademy().getAcademyNumber();
		//if (!EmptyUtil.isStringEmpty(academyNumber)) {
		sql+=" and c.academyNumber = "+academyNumber;
		//}
		// 学期
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term") != null) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and c.term = "+term;
		// 教师
		String teacherUsername = request.getParameter("teacher");
		if (!EmptyUtil.isStringEmpty(teacherUsername)) {
			sql+=" and c.teacherUsername like '"+teacherUsername+"'";
		}
		// 课程编号
		String courseNo = request.getParameter("courseNo");
		if (!EmptyUtil.isStringEmpty(courseNo)) {
			sql+=" and c.course_no like '"+courseNo+"'";
		}
		sql+=" and c.courseType="+flag;
		// 以下两行是分页设置
		Query query = entityManager.createNativeQuery(sql);
		// 获取对象条数
		int count= ((BigInteger)query.getSingleResult()).intValue();
		return count;
	}

	/**
	 * Description 获取直播链接
	 * @author 黄保钱 2018-11-21
	 */
	@Transactional
	public Map<Integer, String> getLivePathByApp(List<TimetableAppointment> timetableAppointments){
		if(timetableAppointments == null || timetableAppointments.size() == 0){
			return new HashMap<>();
		}
		StringBuilder timetableIds = new StringBuilder();
		for(TimetableAppointment l: timetableAppointments){
			timetableIds.append(l.getId().toString()).append(",");
		}
		String sql = "select distinct app_id, live_path from course_resource where app_id in (" +
				timetableIds.toString().substring(0, timetableIds.length() - 1) +
				")";
		Query query = entityManager.createNativeQuery(sql);
		List<Object[]> list= query.getResultList();
		Map<Integer, String> map = new HashMap<>();
		if(list.size() == 0){
			return map;
		}
		for(Object[] o: list){
			Integer i = Integer.parseInt((String) o[0]);
			map.put(i, (String) o[1]);
		}
		return map;
	}

	/**
	 * Description 获取录播链接
	 * @author 黄保钱 2018-11-21
	 */
	@Transactional
	public Map<Integer, List<String>> getHttpPathByApp(List<TimetableAppointment> timetableAppointments){
		if(timetableAppointments == null || timetableAppointments.size() == 0){
			return new HashMap<>();
		}
		StringBuilder timetableIds = new StringBuilder();
		for(TimetableAppointment l: timetableAppointments){
			timetableIds.append(l.getId().toString()).append(",");
		}
		String sql = "select distinct app_id, httppath from course_resource where app_id in (" +
				timetableIds.toString().substring(0, timetableIds.length() - 1) +
				")";
		Query query = entityManager.createNativeQuery(sql);
		List<Object[]> list= query.getResultList();
		Map<Integer, List<String>> map = new HashMap<>();
		if(list.size() == 0){
			return map;
		}
		for(Object[] o: list){
			Integer i = Integer.parseInt((String) o[0]);
			List<String > temp = map.get(i);
			if(temp == null){
				temp = new ArrayList<>();
			}
			if(o[1] != null && !"".equals(o[1])) {
				temp.add((String) o[1]);
			}
			map.put(i, temp);
		}
		return map;
	}

    /****************************************************************************
     * Description：保存实验室禁用时间段
     *
     * @作者：魏诚
     * @时间：2016-03-05
     * update Hezhaoyi 2019-3-21
     ****************************************************************************/
    public void saveLabRoomLimitTime(HttpServletRequest request, LabRoomLimitTime labRoomLimitTime,Integer type){
        // 星期可多选
        String[] weekdays = request.getParameterValues("weekday1");

        boolean allSelected = false;
        // 如果选择的是全部
        if (weekdays != null && weekdays.length > 0) {
            for (String weekday : weekdays) {
                if (weekday.equals("0")) {// 选择的是全部
                    allSelected = true;
                    break;
                }
            }
        }
        if (allSelected) {
            String[] allWeekdays = { "1", "2", "3", "4", "5", "6", "7" };
            weekdays = allWeekdays;
        }

        // 起始和结束周
        String startDate = request.getParameter("startDate");
        int startweek = shareService.getSchoolWeekByString(startDate).getWeek();
        int startWeekday = shareService.getSchoolWeekByString(startDate).getWeekday();
        String endDate = request.getParameter("endDate");
        int endweek = shareService.getSchoolWeekByString(endDate).getWeek();
        int endWeekday = shareService.getSchoolWeekByString(startDate).getWeekday();
        // 起始和结束节
        String startTime = request.getParameter("startTime");
        int startclass = shareService.getSystemTimeByString(startTime);
        //int startclass = systemTimeDAO.findSystemTimeById(startclassId).getSection();
        String endTime = request.getParameter("endTime");
        int endclass = shareService.getSystemTimeByString(endTime);
        //int endclass = systemTimeDAO.findSystemTimeById(endclassId).getSection();
        // 学期
        SchoolTerm term = shareService.getSchoolWeekByString(endDate).getSchoolTerm();

        if (weekdays != null && weekdays.length > 0) {

            if (endweek - startweek == 0) {// 在同一周
                for (String weekday : weekdays) {
                    for (int i = startWeekday; i <= 7; i++) {
                        if (weekday.equals("" + i)) {
                            // 设置星期
                            labRoomLimitTime.setWeekday(Integer.parseInt(weekday));
                            // 设置起始节次
                            labRoomLimitTime.setStartclass(startclass);
                            // 设置结束节次
                            labRoomLimitTime.setEndclass(endclass);
                            // 设置起始周次
                            labRoomLimitTime.setStartweek(startweek);
                            // 设置结束周次
                            labRoomLimitTime.setEndweek(startweek);
                            // 设置学期
                            labRoomLimitTime.setSchoolTerm(term);
                            // 设置标志位
                            labRoomLimitTime.setFlag(0);// 0-手动添加
							labRoomLimitTime.setType(type); //0-实验室预约配置项 1-设备预约配置项
                            labRoomLimitTimeDAO.store(labRoomLimitTime);
                        }
                    }
                }
            }

            if (endweek - startweek >= 1) {// 有隔周情况

                // 起始周
                for (String weekday : weekdays) {
                    for (int i = startWeekday; i <= 7; i++) {
                        if (weekday.equals("" + i)) {
                            // 设置星期
                            labRoomLimitTime.setWeekday(Integer.parseInt(weekday));
                            // 设置起始节次
                            labRoomLimitTime.setStartclass(startclass);
                            // 设置结束节次
                            labRoomLimitTime.setEndclass(endclass);
                            // 设置起始周次
                            labRoomLimitTime.setStartweek(startweek);
                            // 设置结束周次
                            labRoomLimitTime.setEndweek(startweek);
                            // 设置学期
                            labRoomLimitTime.setSchoolTerm(term);
                            // 设置标志位
                            labRoomLimitTime.setFlag(0);// 0-手动添加
							labRoomLimitTime.setType(type); //0-实验室预约配置项 1-设备预约配置项
                            labRoomLimitTimeDAO.store(labRoomLimitTime);
                        }
                    }
                }

                // 终止周
                for (String weekday : weekdays) {
                    for (int i = 1; i < endWeekday; i++) {
                        if (weekday.equals("" + i)) {
                            // 设置星期
                            labRoomLimitTime.setWeekday(Integer.parseInt(weekday));
                            // 设置起始节次
                            labRoomLimitTime.setStartclass(startclass);
                            // 设置结束节次
                            labRoomLimitTime.setEndclass(endclass);
                            // 设置起始周次
                            labRoomLimitTime.setStartweek(endweek);
                            // 设置结束周次
                            labRoomLimitTime.setEndweek(endweek);
                            // 设置学期
                            labRoomLimitTime.setSchoolTerm(term);
                            // 设置标志位
                            labRoomLimitTime.setFlag(0);// 0-手动添加
							labRoomLimitTime.setType(type); //0-实验室预约配置项 1-设备预约配置项
                            labRoomLimitTimeDAO.store(labRoomLimitTime);
                        }
                    }
                }

                if (endweek - startweek >= 2) {
                    for (String weekday : weekdays) {
                        // 设置星期
                        labRoomLimitTime.setWeekday(Integer.parseInt(weekday));
                        // 设置起始节次
                        labRoomLimitTime.setStartclass(startclass);
                        // 设置结束节次
                        labRoomLimitTime.setEndclass(endclass);
                        // 设置起始周次
                        labRoomLimitTime.setStartweek(startweek + 1);
                        // 设置结束周次
                        labRoomLimitTime.setEndweek(endweek - 1);
                        // 设置学期
                        labRoomLimitTime.setSchoolTerm(term);
                        // 设置标志位
                        labRoomLimitTime.setFlag(0);// 0-手动添加
						labRoomLimitTime.setType(type); //0-实验室预约配置项 1-设备预约配置项
                        labRoomLimitTimeDAO.store(labRoomLimitTime);
                    }
                }
            }
        }

    }
}
