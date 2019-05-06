package net.zjcclims.service.timetable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zjcclims.domain.LabRoom;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.dao.LabRoomDeviceDAO;
import net.zjcclims.dao.OperationItemDAO;
import net.zjcclims.dao.SchoolCourseDAO;
import net.zjcclims.dao.SchoolCourseStudentDAO;
import net.zjcclims.dao.SoftwareDAO;
import net.zjcclims.dao.TimetableAppointmentDAO;
import net.zjcclims.dao.TimetableBatchDAO;
import net.zjcclims.dao.TimetableBatchItemsDAO;
import net.zjcclims.dao.TimetableCourseStudentDAO;
import net.zjcclims.dao.TimetableGroupDAO;
import net.zjcclims.dao.TimetableGroupStudentsDAO;
import net.zjcclims.domain.LabRoomDevice;
import net.zjcclims.domain.OperationItem;
import net.zjcclims.domain.SchoolCourse;
import net.zjcclims.domain.SchoolCourseDetail;
import net.zjcclims.domain.SchoolCourseStudent;
import net.zjcclims.domain.Software;
import net.zjcclims.domain.SoftwareRoomRelated;
import net.zjcclims.domain.TimetableAppointment;
import net.zjcclims.domain.TimetableBatch;
import net.zjcclims.domain.TimetableBatchItems;
import net.zjcclims.domain.TimetableCourseStudent;
import net.zjcclims.domain.TimetableGroup;
import net.zjcclims.domain.TimetableGroupStudents;
import net.zjcclims.domain.TimetableSoftwareRelated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("TimetableReSchedulingService")
public class TimetableReSchedulingServiceImpl implements TimetableReSchedulingService {
	@Autowired
	private OperationItemDAO operationItemDAO;
	@Autowired
	private TimetableGroupDAO timetableGroupDAO;
	@Autowired
	private TimetableBatchDAO timetableBatchDAO;
	@Autowired
	private TimetableBatchItemsDAO timetableBatchItemsDAO;
	@Autowired
	private SchoolCourseStudentDAO schoolCourseStudentDAO;
	@Autowired
	private ShareService shareService;
	@Autowired
	private TimetableCourseStudentDAO timetableCourseStudentDAO;
	@Autowired
	private OuterApplicationService outerApplicationService;
	@Autowired
	private TimetableGroupStudentsDAO timetableGroupStudentsDAO;
	@Autowired
	private TimetableAppointmentDAO timetableAppointmentDAO;
	@Autowired
	private LabRoomDeviceDAO labRoomDeviceDAO;
	@Autowired
	private TimetableAppointmentService timetableAppointmentService;
	@Autowired
	private SoftwareDAO softwareDAO;
	@Autowired
	private SchoolCourseDAO schoolCourseDAO;
	@PersistenceContext
	private EntityManager entityManager;
	
	/*************************************************************************************
	 * @內容：保存分组信息
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public void saveTimetableGroup(HttpServletRequest request) {
		// 获取选课组
		String courseCode = request.getParameter("courseCode");

		// 分组计数
		int countGroup = Integer.parseInt(request.getParameter("countGroup"));
		/**
		 * 新建批次
		 **/

		TimetableBatch timetableBatch = new TimetableBatch();
		/*// 获取相同选课组的最大批次
		int maxBatch = timetableBatchDAO.executeQuery(
				"select c from TimetableBatch c where c.courseCode like '" + courseCode + "'").size();
		maxBatch = maxBatch + 1;*/
		timetableBatch.setCountGroup(countGroup);
		timetableBatch.setCourseCode(courseCode);
		timetableBatch.setBatchName(request.getParameter("batchName"));
		timetableBatch.setIfselect(Integer.parseInt(request.getParameter("ifselect")));
		try {
		Calendar ccalendarStart = Calendar.getInstance();
		Calendar ccalendarEnd = Calendar.getInstance();
		SimpleDateFormat sstartDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat eendDate = new SimpleDateFormat("yyyy-MM-dd");
		Date ddate1 = sstartDate.parse(request.getParameter("startDate"));
		Date ddate2 = eendDate.parse(request.getParameter("endDate"));
		ccalendarStart.setTime(ddate1);
		ccalendarEnd.setTime(ddate2);
		timetableBatch.setStartDate(ccalendarStart);
		timetableBatch.setEndDate(ccalendarEnd);
		timetableBatch = timetableBatchDAO.store(timetableBatch);
		} catch (Exception e) {

		}
		// 保存批次相关的实验项目
		String[] items = request.getParameterValues("item");
		int length = items.length;
		if (items[length - 1].equals("")) {
			length = length - 1;
		}
		TimetableBatchItems timetableBatchItems = new TimetableBatchItems();
		try {
			if (items.length > 0) {
				for (int i = 0; i < length; i++) {
					// 将matchLabs添加到matchLabs中
					timetableBatchItems.setOperationItem((operationItemDAO.findOperationItemByPrimaryKey(Integer
							.parseInt(items[i]))));
					timetableBatchItems.setTimetableBatch(timetableBatch);
					timetableBatchItemsDAO.store(timetableBatchItems);
				}
			}
			/**
			 * 新建组
			 **/
			// 获取传入参数
			String[] numbers = request.getParameterValues("numbers");
			TimetableGroup timetableGroup = new TimetableGroup();
			for (int i = 0; i < countGroup; i++) {
				// 日期处理
				/*Calendar calendarStart = Calendar.getInstance();
				Calendar calendarEnd = Calendar.getInstance();
				SimpleDateFormat dStartDate = new SimpleDateFormat("yyyy-MM-dd");
				Date date1 = dStartDate.parse(startDate[i]);

				SimpleDateFormat dEndDate = new SimpleDateFormat("yyyy-MM-dd");
				Date date2 = dEndDate.parse(endDate[i]);
				calendarStart.setTime(date1);
				timetableGroup.setStartDate(calendarStart);
				calendarEnd.setTime(date2);
				timetableGroup.setEndDate(calendarEnd);*/
				timetableGroup.setGroupName("第" + String.valueOf(i + 1) + "组");
				timetableGroup.setNumbers(Integer.parseInt(numbers[i]));
				timetableGroup.setTimetableBatch(timetableBatch);
				timetableGroup.setTimetableAppointment(null);
				// TimetableGroup timetableGroupSave = new TimetableGroup();
				timetableGroupDAO.store(timetableGroup);

			}
		} catch (Exception e) {

		}
	}

	/*************************************************************************************
	 * @內容：根据选课组编号获取分组信息
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public List<TimetableGroup> getTimetableGroupByCourseCode(String courseCode) {
		List<TimetableGroup> timetableGroups = timetableGroupDAO
				.executeQuery("select c from TimetableGroup c where c.timetableBatch.courseCode like '" + courseCode
						+ "' order by c.timetableBatch.courseCode,c.timetableBatch.id desc,c.id asc");
		return timetableGroups;
	}

	/*************************************************************************************
	 * @內容：获取学生选课的列表
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public List<TimetableGroup> getTimetableStudentSelect() {
		// 获取排课信息
		List<TimetableBatch> timetableBatchs = new ArrayList<TimetableBatch>(
				timetableBatchDAO.executeQuery(" select distinct c from TimetableBatch c where 1=1 ",0,-1));
		// 非登陆学生的用户，从list中去掉
		List<TimetableGroup> timetableGroupReturn = new ArrayList<TimetableGroup>();
		for (TimetableBatch timetableBatch : timetableBatchs) {
			TimetableAppointment timetableAppointment = new TimetableAppointment();
			if (!timetableBatch.getTimetableGroups().isEmpty() && timetableBatch.getTimetableGroups().size() > 0
					&& timetableBatch.getTimetableGroups().iterator().next().getTimetableAppointments().size() > 0) {
				timetableAppointment = timetableBatch.getTimetableGroups().iterator().next().getTimetableAppointments()
						.iterator().next();
			}

			if (timetableBatch.getTimetableGroups().isEmpty() || timetableBatch.getTimetableGroups().size() == 0
					|| timetableBatch.getTimetableGroups().iterator().next().getTimetableAppointments().size() == 0
					|| timetableAppointment == null || timetableAppointment.getStatus() != 1) {
				continue;
			}
			// 如果为二次选课而非自主排课
			if (timetableAppointment.getTimetableStyle()!=5&&timetableAppointment.getTimetableStyle()!=6) {
				if (schoolCourseStudentDAO.executeQuery(
						"select c from SchoolCourseStudent c where c.userByStudentNumber like '"
								+ shareService.getUserDetail().getUsername()
								+ "' and c.schoolCourseDetail.courseDetailNo like '"
								+ timetableAppointment.getSchoolCourseDetail().getCourseDetailNo() + "'").size() > 0) {
					timetableGroupReturn.add(timetableBatch.getTimetableGroups().iterator().next());
				}

				/*
				 * for (SchoolCourseStudent schoolCourseStudent :
				 * timetableAppointment
				 * .getSchoolCourseDetail().getSchoolCourseStudents()) { if
				 * (schoolCourseStudent.getUserByStudentNumber() .getUsername()
				 * .equals(shareService.getUserDetail().getUsername())) {
				 * timetableGroupReturn.add(timetableBatch
				 * .getTimetableGroups().iterator().next());
				 * 
				 * } }
				 */
			} else {
				List<TimetableCourseStudent> timetableCourseStudents =timetableCourseStudentDAO.executeQuery(
						"select c from TimetableCourseStudent c where c.user.username like '"
								+ shareService.getUserDetail().getUsername() + "' and c.timetableSelfCourse.id = "
								+ timetableAppointment.getTimetableSelfCourse().getId()); 
				if (timetableCourseStudents.size() > 0) {
					timetableGroupReturn.add(timetableBatch.getTimetableGroups().iterator().next());
				}
				/*
				 * for (TimetableCourseStudent timetableCourseStudent :
				 * timetableAppointment
				 * .getTimetableSelfCourse().getTimetableCourseStudents()) { if
				 * (timetableCourseStudent.getUser().getUsername()
				 * .equals(shareService.getUserDetail().getUsername())) {
				 * timetableGroupReturn.add(timetableBatch
				 * .getTimetableGroups().iterator().next());
				 * 
				 * } }
				 */
			}
		}
		return timetableGroupReturn;
	}

	/*************************************************************************************
	 * @內容：返回实验项目相关可用的星期信息
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public String getValidItemMap(int itemid) {
		// 返回可用的星期信息
		OperationItem operationItem = operationItemDAO.findOperationItemById(itemid);
		String jsonWeek = "[";
		// 遍历实验分室
		if (operationItem != null) {
			/*jsonWeek = jsonWeek + "{\"groups\":\"" + operationItem.getOpenGroupNumber() + "\",\"groupCount\":\""
					+ operationItem.getEachGroupNumbers() + "\",\"labhours\":\""
					+ operationItem.getExperimentalClasses() + "\"}";*/
			Integer groupcount = operationItem.getLpStudentNumber()%(Integer.valueOf(operationItem.getLpStudentNumberGroup()))==0?operationItem.getLpStudentNumber()/(Integer.valueOf(operationItem.getLpStudentNumberGroup())):operationItem.getLpStudentNumber()/(Integer.valueOf(operationItem.getLpStudentNumberGroup()))+1;
			jsonWeek = jsonWeek + "{\"groups\":\"" + groupcount + "\",\"groupCount\":\""
					+ operationItem.getLpStudentNumberGroup() + "\",\"labhours\":\""
					+ operationItem.getLpDepartmentHours() + "\"}";
		}
		jsonWeek = jsonWeek + "]";

		return jsonWeek;
	}

	/*************************************************************************************
	 * @內容：返回可用的星期信息
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public String getValidWeeksMap(int term, int weekday, int[] labrooms, int[] classes) {
		// 返回可用的星期信息
		Integer[] intWeeks = outerApplicationService.getValidWeeks(term, classes, labrooms, weekday);
		String jsonWeek = "[";
		// 遍历实验分室
		if (intWeeks.length != 0 && intWeeks[0] != null) {
			for (int intWeek : intWeeks) {
				if (intWeeks[intWeeks.length - 1] == intWeek) {
					jsonWeek = jsonWeek + "{\"id\":\"" + intWeek + "\",\"value\":\"" + intWeek + "\",\"weekday\":\""
							+ weekday + "\"}";
				} else {
					jsonWeek = jsonWeek + "{\"id\":\"" + intWeek + "\",\"value\":\"" + intWeek + "\",\"weekday\":\""
							+ weekday + "\"},";
				}
			}
		}
		jsonWeek = jsonWeek + "]";
//		System.out.println(jsonWeek);
		return jsonWeek;

	}
	/*************************************************************************************
	 * @內容：返回可用的星期信息--编辑排课记录
	 * @作者：贺子龙
	 * @日期：2016-01-06
	 *************************************************************************************/
	public String getValidWeeksMap(int term, int weekday, int[] labrooms, int[] classes, int tableAppId) {
		// 返回可用的星期信息
		Integer[] intWeeks = outerApplicationService.getValidWeeks(term, classes, labrooms, weekday, tableAppId);
		String jsonWeek = "[";
		// 遍历实验分室
		if (intWeeks.length != 0 && intWeeks[0] != null) {
			for (int intWeek : intWeeks) {
				if (intWeeks[intWeeks.length - 1] == intWeek) {
					jsonWeek = jsonWeek + "{\"id\":\"" + intWeek + "\",\"value\":\"" + intWeek + "\",\"weekday\":\""
							+ weekday + "\"}";
					
				} else {
					jsonWeek = jsonWeek + "{\"id\":\"" + intWeek + "\",\"value\":\"" + intWeek + "\",\"weekday\":\""
							+ weekday + "\"},";
					
				}
				
			}
		}
		jsonWeek = jsonWeek + "]";
//		System.out.println(jsonWeek);
		return jsonWeek;
		
	}

	
	/*************************************************************************************
	 * @內容：返回可用的实验室设备
	 * @作者： 贺子龙
	 * @日期：2015-10-27
	 *************************************************************************************/
	
	public String getLabroomDeviceMap(int[] labrooms){
		StringBuffer sql = new StringBuffer("select c from LabRoomDevice c where 1=0 ");
		for (int labroom : labrooms) {
			sql.append(" or c.labRoom.id ="+labroom);
		}
		List<LabRoomDevice> devices = labRoomDeviceDAO.executeQuery(sql.toString());
		String jsonDevice = "[";
		if (devices.size()!=0 && devices.get(0)!=null) {
			for (LabRoomDevice labRoomDevice : devices) {
				jsonDevice=jsonDevice+"{\"id\":\"" + labRoomDevice.getId() + "\",\"value\":\"" + labRoomDevice.getSchoolDevice().getDeviceNumber() + "\",\"deviceName\":\""
						+labRoomDevice.getSchoolDevice().getDeviceNumber()+ labRoomDevice.getSchoolDevice().getDeviceName()+"("+labRoomDevice.getLabRoom().getLabRoomName()+")" + "\"},";
			}
		}
		jsonDevice = jsonDevice + "]";
//		System.out.println(jsonDevice);
		return jsonDevice;
	}
	/*************************************************************************************
	 * @內容：返回可用的实验室信息
	 * @作者：戴昊宇
	 * @日期：2017-08-25
	 *************************************************************************************/
	public String getValidLabRoomsForSkill(int term, int weeks, int weekday, int[] classes,Integer selfCourseId,String coursecode,Integer []softwares,Integer confinementTime) {
       
	    //获得所选软件的Id
		List<Integer> softid = new ArrayList<Integer>();
		Set<Software> softwareByCode = new HashSet<Software>();
		for (Integer software : softwares) {
			softwareByCode.add(softwareDAO.findSoftwareById(software));
		}
		for (Software soft : softwareByCode) {
			softid.add(soft.getId());
		}
	    Collections.sort(softid);
		// 返回可用的实验室信息
		List<LabRoom> labRooms = outerApplicationService.getValidLabRooms(term, weeks, weekday, classes, selfCourseId, confinementTime, coursecode);
		//获得选课组编号
		Set<SchoolCourse> findSchoolCourseByCourseCode = schoolCourseDAO.findSchoolCourseByCourseCode(coursecode);
		Set<SchoolCourseDetail> schoolcoursedetail= new HashSet<SchoolCourseDetail>();
		for(SchoolCourse schoolcourse :findSchoolCourseByCourseCode)
		{
			schoolcoursedetail.addAll(schoolcourse.getSchoolCourseDetails());
		}
		List<SchoolCourseDetail> schoolCourseDetail = new ArrayList<SchoolCourseDetail>();
		for(SchoolCourseDetail schoolcoursenum:schoolcoursedetail)
		{
			schoolCourseDetail.add(schoolcoursenum);
		}
		// 获得选课组学生人数
		String courseDetailNo = schoolCourseDetail.get(0).getCourseDetailNo();
		String sql = "select c from SchoolCourseStudent c where c.schoolCourseDetail.courseDetailNo like '"
				+ courseDetailNo + "'";
		List<SchoolCourseStudent> schoolCourseStudents = schoolCourseStudentDAO.executeQuery(sql, 0, -1);
		// 遍历实验分室
		String jsonLabRoom = "[";
		for(LabRoom labroomnub:labRooms){
			 //定义标志位
		    int status = 0;
			//判断实验室是否包含使用的软件
			List<Object[]> softwareView = this.softwareView();
  			for (Object[] obj : softwareView) {
  				Object[] a = new Object[obj.length - 1];
				System.arraycopy(obj, 1, a, 0, a.length);
				List<Integer> list = new ArrayList<Integer>();
				String[] split = a[0].toString().split(",");
				for (int i = 0; i < split.length; i++) {
					list.add(Integer.parseInt(String.valueOf(split[i])));
				}
				//判断实验室关联的软件是否符合
				if ((labroomnub.getId() == Integer.parseInt(String.valueOf(obj[0]))&&list.containsAll(softid))||softid.size()==0) {
					//判断实验室容量是否大于选课组学生人数
					if (schoolCourseStudents.size() <=labroomnub
							.getLabRoomCapacity()){
				      status = 1;	
				} else {
					status = 2;
				}
			  }
  			}
			jsonLabRoom = jsonLabRoom + "{\"id\":\"" + labroomnub.getId()
					+ "\",\"value\":\"" + labroomnub.getLabRoomName()
					+ labroomnub.getLabRoomAddress() + "\",\"status\":\""
					+ status + "\",\"number\":\""
					+ labroomnub.getLabRoomCapacity()+"\"},";	
		}
		jsonLabRoom = jsonLabRoom.substring(0, jsonLabRoom.length()-1);
		jsonLabRoom = jsonLabRoom + "]";
		return jsonLabRoom;
	}

	/*************************************************************************************
	 * @內容：删除id对应的批次的所有记录
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public void deleteBatch(int id, int term, String courseCode) {
		/*try {*/
			// 删除关联项目的记录
			for (TimetableBatchItems timetableBatchItems : timetableBatchItemsDAO
					.executeQuery("select c from TimetableBatchItems c where c.timetableBatch.id =" + id)) {
				timetableBatchItemsDAO.remove(timetableBatchItems);
			}
			TimetableBatch timetableBatch = timetableBatchDAO.findTimetableBatchById(id);
			try {
			for (TimetableGroup timetableGroup : timetableGroupDAO
					.executeQuery("select c from TimetableGroup c where c.timetableBatch.id =" + id)) {
				// 删除该分组下所有关联的排课记录
				for(TimetableAppointment timetableAppointment:timetableGroup.getTimetableAppointments() ){
					timetableAppointmentService.deleteAppointment(timetableAppointment.getId());
				}
				// 删除批次下的分组记录
				timetableGroupDAO.remove(timetableGroup);
			}
			}catch (Exception e) {
				
			}
			// 删除批次
			timetableBatchDAO.remove(timetableBatch);
		/*} catch (Exception e) {
		}*/
	}

	/*************************************************************************************
	 * @內容：确认二次分组排课是否完成
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public void doReGroupTimetableOk(String courseCode,int batchId, int term) {
		// 根据选课组编号，获取排课信息
		List<TimetableAppointment> timetableAppointments = new ArrayList<TimetableAppointment>(
				timetableAppointmentDAO.findTimetableAppointmentByCourseCode(courseCode));
		for (TimetableAppointment timetableAppointment : timetableAppointments) {
			// 设置代发布状态：2
			timetableAppointment.setStatus(2);
			timetableAppointmentDAO.store(timetableAppointment);
		}

		// 如果分组为自动选课，保存学生选课记录
		TimetableBatch timetableBatch = timetableBatchDAO.findTimetableBatchById(batchId);
		timetableBatch.setFlag(1);
		timetableBatchDAO.store(timetableBatch);
		timetableBatchDAO.flush();

		// 自动选课
		if (timetableBatch.getIfselect() == 0) {
			Iterator<SchoolCourseStudent> iterator = null;

			for (TimetableGroup timetableGroup : timetableBatch.getTimetableGroups()) {
				if (timetableGroup.getTimetableAppointments() == null) {
					continue;
				}
				if (iterator == null&&timetableGroup.getTimetableAppointments().size()>0) {
					iterator = timetableGroup.getTimetableAppointments().iterator().next().getSchoolCourseDetail()
							.getSchoolCourseStudents().iterator();
				}
				if(iterator!=null&&iterator.hasNext()){
				for (int i = 1; i <= timetableGroup.getNumbers(); i++) {
					TimetableGroupStudents timetableGroupStudents = new TimetableGroupStudents();
					timetableGroupStudents.setUser(iterator.next().getUserByStudentNumber());
					timetableGroupStudents.setTimetableGroup(timetableGroup);
					timetableGroupStudentsDAO.store(timetableGroupStudents);
					timetableGroupStudentsDAO.flush();
				}}
			}
		}
	}

	/*************************************************************************************
	 * @內容：返回冲突加密狗软件工位数
	 * @作者：戴昊宇
	 * @日期：2017-10-13
	 *************************************************************************************/
	public int getLicence(int term, int[] classes, Integer weeks,
			Integer weekday, Integer softwareid) {
		// String hqll="select a from Software a where a.name like '%licence%'";
		String hql = "select c from TimetableAppointment c where c.schoolCourse.schoolTerm.id ="
				+ term;
		hql += " and c.startWeek=" + weeks;
		hql += " and (c.startClass between " + classes[0] + " and "
				+ classes[classes.length - 1] + " OR c.endClass between "
				+ classes[0] + " and " + classes[classes.length - 1]+")";
		List<TimetableAppointment> list = timetableAppointmentDAO
				.executeQuery(hql);
		int softsize = 0;
		int dogle = 0;
		Set<TimetableSoftwareRelated> set2 = new HashSet<TimetableSoftwareRelated>();
		for (TimetableAppointment timetableAppointment : list) {
			set2.addAll(timetableAppointment.getTimetableSoftwareRelateds());
			for (TimetableSoftwareRelated timetablesoft : set2) {
				if (timetablesoft.getSoftware().getId() == softwareid) {
					dogle =1;
				}
			}
			if(dogle==1){
				//timetableAppointment.getSchoolCourseDetail().getCourseDetailNo();
				softsize+= Integer.parseInt(timetableAppointment.getSchoolCourseDetail().getPlanStudentNumber());
			}
		}
		return softsize;

	}

	/**************************************
	 * Description：视图呈现样例--list
	 * 
	 * @author 戴昊宇
	 * @date 2017-08-11
	 **************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Object[]> softwareView() {
		String sql = "select * from view_lab_software ";
		Query query = entityManager.createNativeQuery(sql);
		// 获取list对象
		List<Object[]> list = query.getResultList();
		return list;
	}
}