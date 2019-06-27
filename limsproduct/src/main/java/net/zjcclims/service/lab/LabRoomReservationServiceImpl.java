package net.zjcclims.service.lab;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.device.LabRoomDeviceService;
import net.zjcclims.service.timetable.OuterApplicationService;
import net.zjcclims.util.HttpClientUtil;
import net.zjcclims.web.common.PConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("LabRoomReservationService")
public class LabRoomReservationServiceImpl implements LabRoomReservationService {
	
	@Autowired
	private LabWorkRoomDAO labWorkRoomDAO;
	@Autowired
	private LabCenterDAO labCenterDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private ShareService shareService;
	@Autowired
	private LabRoomReservationCreditDAO labRoomReservationCreditDAO;
	@Autowired private LabRoomStationReservationDAO labRoomStationReservationDAO;
	@Autowired private LabRoomStationReservationStudentDAO labRoomStationReservationStudentDAO;
	@Autowired private LabRoomDAO labRoomDAO;
	@Autowired private LabRoomAdminDAO labRoomAdminDAO;
	@Autowired private LabRoomStationReservationResultDAO labRoomStationReservationResultDAO;
	@Autowired private OuterApplicationService outerApplicationService;
	@Autowired private LabRoomDeviceReservationDAO labRoomDeviceReservationDAO;
	@Autowired private LabRoomDeviceReservationResultDAO labRoomDeviceReservationResultDAO;
	@Autowired private LabRoomStationReservationCreditDAO labRoomStationReservationCreditDAO;
	@Autowired private MessageDAO messageDAO;
	@Autowired private SoftwareReserveDAO softwareReserveDAO;
	@Autowired private SoftwareReserveAuditDAO softwareReserveAuditDAO;
	@Autowired private SchoolDeviceDAO schoolDeviceDAO;
	@Autowired private LabReservationDAO labReservationDAO;
	@Autowired private LabReservationAuditDAO labReservationAuditDAO;
	@Autowired private LabRoomDeviceService labRoomDeviceService;
	@Autowired private LabRoomService labRoomService;
	@Autowired private SchoolWeekDAO schoolWeekDAO;
	@Autowired private SystemTimeDAO systemTimeDAO;
	@Autowired private LabRelevantConfigDAO labRelevantConfigDAO;
	@Autowired private PConfig pConfig;
	@PersistenceContext
	private EntityManager entityManager;

	/***************************** 
	*Description 根据学院找到用户信誉积分
	*
	*@author:余南新
	*@param:cid(实验中心id)
	*@date:2017.4.26
	*****************************/
	public List<User> findUserCreditWarningByCenter(int cid,int currpage,int pageSize){
		String academyNumber="";
		LabCenter center = labCenterDAO.findLabCenterByPrimaryKey(cid);
		if(center != null){
			academyNumber = center.getSchoolAcademy().getAcademyNumber();
		}
		//CStaticValue cStaticValue = shareService.findCStaticValueByCodeAndAcademy("device_credit_score", academyNumber);
		String sql = "select u from User u where 1=1";
		/*if(cStaticValue != null && cStaticValue.getDeviceScore() != null){
			sql += " and creditScore < "+cStaticValue.getDeviceScore();
		}*/
		sql += " and schoolAcademy.academyNumber ='"+academyNumber+"'";
		return userDAO.executeQuery(sql, pageSize*(currpage-1),pageSize);
	}
	
	/***************************** 
	*Description 根据学院找到用户信誉积分
	*
	*@author:余南新
	*@param:cid(实验中心id)
	*@date:2017.4.26
	*****************************/
	public int getCountUserCreditWarningByCenter(int cid){
		String academyNumber="";
		LabCenter center = labCenterDAO.findLabCenterByPrimaryKey(cid);
		if(center != null){
			academyNumber = center.getSchoolAcademy().getAcademyNumber();
		}
		//CStaticValue cStaticValue = shareService.findCStaticValueByCodeAndAcademy("device_credit_score", academyNumber);
		String sql = "select count(*) from User u where 1=1";
		/*if(cStaticValue != null && cStaticValue.getDeviceScore() != null){
			sql += " and creditScore < "+cStaticValue.getDeviceScore();
		}*/
		sql += " and schoolAcademy.academyNumber ='"+academyNumber+"'";
		return ((Long) userDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	/***************************** 
	*Description 找到教师信誉积分
	*
	*@author:周志辉
	*@param:
	*@date:2017.8.14
	*****************************/
	public int getCountTeacherCreditWarningByQuery(User user){
		String sql = "select count(*) from User u where 1=1 and userRole=1";
		if(user!=null&&user.getUsername()!=null&&!user.getUsername().equals(""))
		{
			sql+=" and u.username like '%"+user.getUsername()+"%'";
		}
		if(user!=null&&user.getCname()!=null&&!user.getCname().equals(""))
		{
			sql+=" and u.cname like '%"+user.getCname()+"%'";
		}
		return ((Long) userDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	/***************************** 
	*Description 找到教师信誉积分并分页
	*
	*@author:周志辉
	*@param:
	*@date:2017.8.14
	*****************************/
	public List<User> findTeacherCreditWarningByQuery(int currpage,int pageSize,User user){
		String sql = "select u from User u where 1=1 and userRole=1";
		if(user!=null&&user.getUsername()!=null&&!user.getUsername().equals(""))
		{
			sql+=" and u.username like '%"+user.getUsername()+"%'";
		}
		if(user!=null&&user.getCname()!=null&&!user.getCname().equals(""))
		{
			sql+=" and u.cname like '%"+user.getCname()+"%'";
		}
		return userDAO.executeQuery(sql, pageSize*(currpage-1),pageSize);
	}
	/***************************** 
	*Description 找到学生信誉积分
	*
	*@author:周志辉
	*@param:
	*@date:2018.8.14
	*****************************/
	@Override
	public int getCountStudentCreditWarningByQuery(User user) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from User u where 1=1 and userRole=0";
		if(user!=null&&user.getUsername()!=null&&!user.getUsername().equals(""))
		{
			sql+=" and u.username like '%"+user.getUsername()+"%'";
		}
		if(user!=null&&user.getCname()!=null&&!user.getCname().equals(""))
		{
			sql+=" and u.cname like '%"+user.getCname()+"%'";
		}
		return ((Long) userDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	/***************************** 
	*Description 找到学生信誉积分并分页
	*
	*@author:周志辉
	*@param:
	*@date:2017.8.14
	*****************************/
	@Override
	public List<User> findStudentCreditWarningByQuery(int currpage, int pageSize,User user) {
		// TODO Auto-generated method stub
		String sql = "select u from User u where 1=1 and userRole=0";
		if(user!=null&&user.getUsername()!=null&&!user.getUsername().equals(""))
		{
			sql+=" and u.username like '%"+user.getUsername()+"%'";
		}
		if(user!=null&&user.getCname()!=null&&!user.getCname().equals(""))
		{
			sql+=" and u.cname like '%"+user.getCname()+"%'";
		}
		return userDAO.executeQuery(sql, pageSize*(currpage-1),pageSize);
	}
	/*************************************************************************************
	 * Description 根据实验室预约的id查找所有扣分项
	 * 
	 * @param id
	 * @author 孙虎
	 * @date 2017-08-16
	 *************************************************************************************/
	@Override
	public List<LabRoomReservationCredit> findlabRoomReservationCreditOptionById(Integer id) {
		// TODO Auto-generated method stub
		String hql="select c from LabRoomReservationCredit c where 1=1 and c.labReservation.id="+id;	 
		return labRoomReservationCreditDAO.executeQuery(hql, 0,-1);
	}
	/*************************************************************************************
	 * Description 分页查询实验室列表（实验室预约）
	 * 
	 * @author 孙虎
	 * @date 2017-09-20
	 *************************************************************************************/
	public List<LabRoom> findLabRoompage(LabRoom labRoom, int currpage, int pageSize, String acno,HttpServletRequest request) {
		String hql = "select distinct l from LabRoom l where 1=1 and l.labRoomReservation=1 and l.labRoomActive=1";
		if (labRoom.getLabRoomName() != null && !labRoom.getLabRoomName().equals("")) {
			hql += " and  (l.labRoomName like '%" + labRoom.getLabRoomName() + "%'  or l.labRoomNumber like '%"
					+ labRoom.getLabRoomName() + "%')";
		}
		String worker = request.getParameter("worker");
		String searchflg = request.getParameter("searchflg");
		if(searchflg != null && searchflg != ""){
			if(worker != null && worker != ""){
				
				if(searchflg.equals("1")){
					hql+=" and l.labRoomWorker = " + worker;
				}else if(searchflg.equals("2")){
					hql+=" and l.labRoomWorker > " + worker;
				}else if(searchflg.equals("3")){
					hql+=" and l.labRoomWorker < " + worker;
				}
			}
		}
		if (labRoom.getLabRoomWorker() != null) {
			hql += " and  l.labRoomWorker = " + labRoom.getLabRoomWorker();
		}
		if (labRoom.getLabRoomEnName() != null && !labRoom.getLabRoomEnName().equals("")){
			hql += " and l in (select l from Software s,LabRoom l,SoftwareRoomRelated srr where s.id=srr.software.id and l.id=srr.labRoom.id and s.name like'%"+labRoom.getLabRoomEnName()+"%')" ;
		}
		if (labRoom.getLabRoonAbbreviation() != null && !labRoom.getLabRoonAbbreviation().equals("")){
			hql += " and l in (select l from LabRoomDevice d,LabRoom l where d.id not in (select ld.labRoomDevice.id from OperationItemDevice ld) and d.labRoom.id=l.id and d.schoolDevice.deviceName like'%"+labRoom.getLabRoonAbbreviation()+"%')" ;
		}
		if(acno!=null && !acno.equals("-1")){// 20190506全校
            // 开放范围
            hql += " and l in (select l from LabRoom l join l.openSchoolAcademies openSAs where (openSAs.academyNumber = '" + acno + "' or openSAs.academyNumber='20190506'))";
			hql += " order by case when l.labCenter.schoolAcademy.academyNumber='" + acno + "' then 0 else 1 end ";
		}
		return labRoomDAO.executeQuery(hql,(currpage-1)*pageSize,pageSize);
	}

	/*************************************************************************************
	 * Description 分页查询实验室列表（工位预约）
	 *
	 * @author 孙虎
	 * @date 2017-09-20
	 * update Hezhaoyi 2019-6-12
	 *************************************************************************************/
	public List<LabRoom> findLabRoomStationBypage(LabRoom labRoom, int currpage, int pageSize, String acno,HttpServletRequest request) {
        StringBuffer sql = new StringBuffer("select distinct l from LabRoom l,LabOpenUpAcademy loua,LabRelevantConfig lrc");
        sql.append(" where l.labRoomActive=1 and l.id = loua.labRoomId and lrc.labRoomId = l.id");
        if (pConfig.PROJECT_NAME.equals("zjcclims")) {
            sql = new StringBuffer("select l from LabRoom l,LabOpenUpAcademy loua,LabRelevantConfig lrc");
            sql.append(" where l.labRoomLevel != 0 and l.id = loua.labRoomId and lrc.labRoomId = l.id");
        }
        sql.append(" and lrc.configCategory = 'lab_station_is_appointment' and lrc.setItem = 1");

		if (labRoom.getLabRoomName() != null && !labRoom.getLabRoomName().equals("")) {
            sql.append(" and  (l.labRoomName like '%" + labRoom.getLabRoomName() + "%'  or l.labRoomNumber like '%"
					+ labRoom.getLabRoomName() + "%')");
		}
		String worker = request.getParameter("worker");
		String searchflg = request.getParameter("searchflg");
		if(searchflg != null && searchflg != ""){
			if(worker != null && worker != ""){

				if(searchflg.equals("1")){
                    sql.append(" and l.labRoomWorker = " + worker);
				}else if(searchflg.equals("2")){
                    sql.append(" and l.labRoomWorker > " + worker);
				}else if(searchflg.equals("3")){
                    sql.append(" and l.labRoomWorker < " + worker);
				}
			}
		}
		if (labRoom.getLabRoomWorker() != null) {
            sql.append(" and  l.labRoomWorker = " + labRoom.getLabRoomWorker());
		}
		if (labRoom.getLabRoomEnName() != null && !labRoom.getLabRoomEnName().equals("")){
			sql.append(" and l in (select l from Software s,LabRoom l,SoftwareRoomRelated srr where s.id=srr.software.id and l.id=srr.labRoom.id and s.name like'%"+labRoom.getLabRoomEnName()+"%')");
		}
		if (labRoom.getLabRoonAbbreviation() != null && !labRoom.getLabRoonAbbreviation().equals("")){
//            sql.append(" and l in (select l from LabRoomDevice d,LabRoom l where d.id not in (select ld.labRoomDevice.id from OperationItemDevice ld) and d.labRoom.id=l.id and d.schoolDevice.deviceName like'%"+labRoom.getLabRoonAbbreviation()+"%')" );
            sql.append(" and l in (select l from LabRoomDevice d,LabRoom l where d.labRoom.id=l.id and d.schoolDevice.deviceName like'%"+labRoom.getLabRoonAbbreviation()+"%')" );

        }
		User user = shareService.getUser();
		Set<Authority> authorities = user.getAuthorities();
		if(acno!=null && !acno.equals("-1")){// 20190506全校
			// 开放范围/开放对象
            sql.append(" and (");
            sql.append(" ((loua.academyNumber = '" + acno + "' or loua.academyNumber='20190506') and loua.type = 2 and loua.authorityId = -1)");
            for(Authority authority : authorities){
                sql.append(" or ((loua.academyNumber = '" + acno + "' or loua.academyNumber='20190506') and loua.type = 2 and loua.authorityId = '" + authority.getId() + "')");
            }
            sql.append(")");
		}


		return labRoomDAO.executeQuery(sql.toString(),(currpage-1)*pageSize,pageSize);
	}

	/*************************************************************************************
	 * Description 分页查询实验室列表（实验室预约）,不包括软件
	 *
	 * @author 廖文辉
	 * @date 2018-08-28
	 *************************************************************************************/
	public List<LabRoom> findLabRoom(LabRoom labRoom, int currpage, int pageSize, String acno,HttpServletRequest request) {
		String hql = "select distinct l from LabRoom l,LabOpenUpAcademy loua where l.id = loua.labRoomId  and l.labRoomReservation=1 and l.labRoomActive=1";
		if (labRoom.getLabRoomName() != null && !labRoom.getLabRoomName().equals("")) {
			hql += " and  (l.labRoomName like '%" + labRoom.getLabRoomName() + "%'  or l.labRoomNumber like '%"
					+ labRoom.getLabRoomName() + "%')";
		}
		if (labRoom.getLabRoomWorker() != null) {
			hql += " and  l.labRoomWorker = " + labRoom.getLabRoomWorker();
		}
		if(request.getSession().getAttribute("selected_role").equals("ROLE_LABMANAGER") || request.getSession().getAttribute("selected_role").equals("ROLE_CABINETADMIN")){
			hql += " and l not in (select l from LabRoomAdmin lra, LabRoom l where lra.labRoom = l and lra.user.username = '" + shareService.getUserDetail().getUsername() + "')";
		}
		User user = shareService.getUser();
		Set<Authority> authorities = user.getAuthorities();
		if(acno!=null && !acno.equals("-1")){// 20190506全校
			// 开放范围/开放对象
			hql += " and (";
			hql += " ((loua.academyNumber = '" + acno + "' or loua.academyNumber='20190506') and loua.type = 1 and loua.authorityId = -1)";
			for(Authority authority : authorities){
				hql += " or ((loua.academyNumber = '" + acno + "' or loua.academyNumber='20190506') and loua.type = 1 and loua.authorityId = " + authority.getId() + ")";
			}
			hql += ")";
			hql +=" order by case when l.labCenter.schoolAcademy.academyNumber='" + acno + "' then 0 else 1 end ";
		}
		return labRoomDAO.executeQuery(hql, (currpage - 1) * pageSize, pageSize);
	}
	/*************************************************************************************
	 * 判断工位预约时间与实验室预约时间是否冲突
	 * 顾延钊
	 * 2019-5-5
	 *************************************************************************************/
	@Override
	public int findReservationEnableOrNot(Integer labRoomId,Calendar reservationTime,Calendar startTime,Calendar endTime) {
		int reservationStatus = 1;
		LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(labRoomId);
		//实训室对应预约列表
		Set<LabRoomStationReservation> labRoomStationReservations = labRoom.getLabRoomStationReservations();
		//和本身的预约逻辑做判断
		/*if (labRoomStationReservations != null) {
			for (LabRoomStationReservation labRoomStationReservation : labRoomStationReservations) {//遍历该实验室已有的预约信息
				if (labRoomStationReservation.getResult() != 4) {//筛去审核拒绝的
					if (labRoomStationReservation.getReservation().equals(reservationTime)) {//预约日期相同
						if (labRoomStationReservation.getStartTime().after(endTime) ||
								labRoomStationReservation.getEndTime().before(startTime) ||
								labRoomStationReservation.getStartTime().equals(endTime) ||
								labRoomStationReservation.getEndTime().equals(startTime)) {//未和所选时间冲突
							//do nothing
						} else {//和所选时间冲突
							reservationStatus = 2;
							return reservationStatus;
						}

					}
				}
			}
		}*/
		//实训室对应借用列表
		Set<LabReservation> labReservations = labRoom.getLabReservations();
		//demo
		Map<String, String> params = new HashMap<>();
		Iterator<LabReservation> it = labReservations.iterator();
		while (it.hasNext()) {
			LabReservation l = it.next();
			String businessType = "LabRoomReservation" + l.getLabRoom().getLabCenter().getSchoolAcademy().getAcademyNumber();
			params.put("businessAppUid", l.getId().toString());
			params.put("businessType", pConfig.PROJECT_NAME + businessType);
			String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", params);
			JSONObject jsonObject = JSON.parseObject(s);
			String status = jsonObject.getString("status");
			if ("success".equals(status)) {
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				if (l.getId().toString().equals(jsonArray.getJSONObject(0).getString("businessAppId"))
						&& "0".equals(jsonArray.getJSONObject(0).getString("level"))) {
					it.remove();
				}
			}
		}
		Set<SchoolWeek> schoolWeeks = schoolWeekDAO.findSchoolWeekByDate(reservationTime);
		SchoolWeek lendingDate = schoolWeeks.size() == 0 ? null : schoolWeeks.iterator().next();
		if (labReservations != null && labReservations.size() > 0 && lendingDate != null) {
			for (LabReservation labReservation : labReservations) {
				for (LabReservationTimeTable lrtt : labReservation.getLabReservationTimeTables()) {
					if(labReservation.getLendingTime() != null){
                        if (labReservation.getLendingTime().equals(reservationTime)) {//和借用日期在同一天的
                            if (lrtt.getStartTime().after(endTime) ||
                                    lrtt.getEndTime().before(startTime) ||
                                    lrtt.getStartTime().equals(endTime) ||
                                    lrtt.getEndTime().equals(startTime)) {//未和所选时间冲突
                                //do nothing
                            } else {
                                reservationStatus = 3;
                                return reservationStatus;
                            }
                        }
                    }
				}
			}
		}
		Set<TimetableLabRelated> timetableLabRelateds = labRoom.getTimetableLabRelateds();
		if (schoolWeeks.size() != 0) {
			SchoolWeek schoolWeek = schoolWeeks.iterator().next();
			for (TimetableLabRelated t : timetableLabRelateds) {
				Set<TimetableAppointmentSameNumber> timetableAppointmentSameNumbers =
						t.getTimetableAppointment().getTimetableAppointmentSameNumbers();
				for (TimetableAppointmentSameNumber tas : timetableAppointmentSameNumbers) {
					if (tas.getStartWeek() <= schoolWeek.getWeek() && tas.getEndWeek() >= schoolWeek.getWeek() && schoolWeek.getWeekday().equals(t.getTimetableAppointment().getWeekday())) {
						SystemTime start = systemTimeDAO.findSystemTimeBySection(tas.getStartClass()).iterator().next();
						SystemTime end = systemTimeDAO.findSystemTimeBySection(tas.getEndClass()).iterator().next();
						if (start.getStartDate().after(endTime) ||
								end.getEndDate().before(startTime) ||
								start.getStartDate().equals(endTime) ||
								end.getEndDate().equals(startTime)) {//未和所选时间冲突
							//do nothing
						} else {
							reservationStatus = 3;
							return reservationStatus;
						}
					}
				}
			}
		}
		return reservationStatus;
	}
	/*************************************************************************************
	 * Description 根据所选时间段查询剩余工位数（实验室预约-预约）
	 * 
	 * @author 孙虎
	 * @date 2017-09-21
	 *************************************************************************************/
	public int findRestReservationStations(Integer labRoomId, Calendar reservationTime,  Calendar startTime,  Calendar endTime) {
		
		LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(labRoomId);
		List<LabRoomStationReservation> labRoomStationReservations = this.getLabRoomStationReversationList(labRoomId, reservationTime);
		//已预约的工位数
		int reservationStationNum = 0;
		//可预约工位数
		Integer labRoomWorker = 0;
		if(labRoom.getLabRoomWorker() != null){
			labRoomWorker =labRoom.getLabRoomWorker();
		}
		SchoolTerm currTerm = shareService.getCurTerm();
		/*long start=System.currentTimeMillis();
		outerApplicationService.getValidLabRoomsStation(reservationTime,labRoomId,startTime,endTime,currTerm.getId());
		long end=System.currentTimeMillis();
		System.out.println("程序运行时间： "+(end-start)+"ms");*/
		//if(outerApplicationService.getValidLabRoomsStation(reservationTime,labRoomId,startTime,endTime,currTerm.getId()) != 0){//和排课判冲
			//和本身的预约逻辑做判断
			if(labRoomStationReservations != null){
				for (LabRoomStationReservation labRoomStationReservation : labRoomStationReservations) {
					if(labRoomStationReservation.getStartTime().after(endTime) || 
							labRoomStationReservation.getEndTime().before(startTime) || 
							labRoomStationReservation.getStartTime().equals(endTime) || 
							labRoomStationReservation.getEndTime().equals(startTime)){//未和所选时间冲突
					}else{//和所选时间冲突
						reservationStationNum +=labRoomStationReservation.getStationCount();
					}
				}
			}
		//}
		Set<SchoolWeek> schoolWeeks = schoolWeekDAO.findSchoolWeekByDate(reservationTime);
		Set<TimetableLabRelated> timetableLabRelateds = labRoom.getTimetableLabRelateds();
		if (schoolWeeks.size() != 0) {
			SchoolWeek schoolWeek = schoolWeeks.iterator().next();
			for (TimetableLabRelated t : timetableLabRelateds) {
				Set<TimetableAppointmentSameNumber> timetableAppointmentSameNumbers =
						t.getTimetableAppointment().getTimetableAppointmentSameNumbers();
				for (TimetableAppointmentSameNumber tas : timetableAppointmentSameNumbers) {
					if (tas.getStartWeek() <= schoolWeek.getWeek() && tas.getEndWeek() >= schoolWeek.getWeek() && schoolWeek.getWeekday().equals(t.getTimetableAppointment().getWeekday())) {
						SystemTime start = systemTimeDAO.findSystemTimeBySection(tas.getStartClass()).iterator().next();
						SystemTime end = systemTimeDAO.findSystemTimeBySection(tas.getEndClass()).iterator().next();
						if (start.getStartDate().after(endTime) ||
								end.getEndDate().before(startTime) ||
								start.getStartDate().equals(endTime) ||
								end.getEndDate().equals(startTime)) {//未和所选时间冲突
							//do nothing
						} else {
							return 0;
						}
					}
				}
			}
		}
		if(reservationStationNum >labRoomWorker){
			return 0;
		}else{
			return labRoomWorker-reservationStationNum;
		}
	}
	
	/*************************************************************************************
	 * Description 保存实验室预约（实验室预约-预约）
	 * 
	 * @author 孙虎
	 * @throws NoSuchAlgorithmException 
	 * @date 2017-09-21
	 *************************************************************************************/
	@Transactional
	public void saveReservationStations(Integer labRoomId, Calendar reservationTime,  Calendar startTime,  Calendar endTime,String[] array,String reason,String teacher,String daen,String userRole) throws NoSuchAlgorithmException {
		LabRoom labRoom = labRoomDAO.findLabRoomById(labRoomId);
		//先保存实验室预约
		LabRoomStationReservation labRoomStationReservation = new LabRoomStationReservation();
		//先保存一次获取数据库分配的id
		labRoomStationReservation = labRoomStationReservationDAO.store(labRoomStationReservation);
		labRoomStationReservation.setLabRoom(labRoom);
		labRoomStationReservation.setReason(reason);
		labRoomStationReservation.setReservation(reservationTime);
		labRoomStationReservation.setStartTime(startTime);
		labRoomStationReservation.setEndTime(endTime);
		SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(reservationTime);
		labRoomStationReservation.setSchoolTerm(schoolTerm);
		labRoomStationReservation.setUser(shareService.getUserDetail());
		labRoomStationReservation.setStationCount(array.length);
		
		CDictionary status = shareService.getCDictionaryByCategory("lab_room_station_reservation_user_role", userRole);
		labRoomStationReservation.setCDictionary(status);
		//判断实验室等级
		if(pConfig.PROJECT_NAME.equals("zjcclims") && labRoom.getLabRoomLevel() == 1){
			//是否需要审核
			if(labRoom.getCDictionaryByIsAudit() != null ){
				if(labRoom.getCDictionaryByIsAudit().getCName().equals("是")){
					labRoomStationReservation.setResult(3);
					labRoomStationReservation.setRemark("");
				}else{
					labRoomStationReservation.setResult(1);
					labRoomStationReservation.setRemark("该实验室预约不需要审核");
				}
			}else{
				labRoomStationReservation.setResult(1);
				labRoomStationReservation.setRemark("该实验室预约不需要审核");
			}
		}else if(pConfig.PROJECT_NAME.equals("zjcclims")){//二级实验室不需要审核
			labRoomStationReservation.setResult(1);
			labRoomStationReservation.setRemark("该实验室预约不需要审核");
		}else {
			//是否需要审核
			if(labRoom.getCDictionaryByIsAudit() != null ){
				if(labRoom.getCDictionaryByIsAudit().getCName().equals("是")){
					labRoomStationReservation.setResult(3);
					labRoomStationReservation.setRemark("");
				} else {
					labRoomStationReservation.setResult(1);
					labRoomStationReservation.setRemark("该实验室预约不需要审核");
				}
			} else {
				labRoomStationReservation.setResult(1);
				labRoomStationReservation.setRemark("该实验室预约不需要审核");
			}
		}
		if (!daen.equals("")) {
			labRoomStationReservation.setUserByDean(userDAO.findUserByPrimaryKey(daen));
		}
		if (!teacher.equals("")) {
			labRoomStationReservation.setUserByTeacher(userDAO.findUserByPrimaryKey(teacher));
		}

		labRoomStationReservation.setState(3);

		//消息
		if(labRoomStationReservation.getResult() != 1) {//需要审核就发信息
			Message message = new Message();
			Calendar date1 = Calendar.getInstance();
			message.setSendUser(shareService.getUserDetail().getCname());
			message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
			message.setCond(0);
			message.setTitle("实验室工位预约审核");
			String content = "<a onclick='changeMessage(this)' href=\"../LabRoomReservation/checkButton?id=" + labRoomStationReservation.getId() + "&tage=0&state=" + labRoomStationReservation.getState() + "&currpage=1\">审核</a>";
			message.setContent(content);
			message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
			message.setCreateTime(date1);
			//是否是一级实验室
			//if (labRoom.getLabRoomLevel() == 1) {
			//产生实验室管理员的消息
			if (labRoom.getLabRoomAdmins() != null) {
				for (LabRoomAdmin labRoomAdmin : labRoom.getLabRoomAdmins()) {
					message.setTage(2);
					shareService.sendMsg(labRoomAdmin.getUser(), message);
				}
			}
			//}
		}

		labRoomStationReservation = labRoomStationReservationDAO.store(labRoomStationReservation);
		labRoomStationReservationDAO.flush();
		
		//在保存对应的多表
		if(array != null && !array.equals("")){
		Set<LabRoomStationReservationStudent> labRoomStationReservationStudents = new HashSet<LabRoomStationReservationStudent>();
			for (String username : array) {
				if(!"".equals(username)){
					LabRoomStationReservationStudent labRoomStationReservationStudent = new LabRoomStationReservationStudent();
					labRoomStationReservationStudent.setLabRoomStationReservation(labRoomStationReservation);
					labRoomStationReservationStudent.setUsername(userDAO.findUserByUsername(username).getUsername());
					labRoomStationReservationStudent.setCname(userDAO.findUserByUsername(username).getCname());
					//labRoomStationReservation.setLabRoomStationReservationStudents(labRoomStationReservationStudents);
					labRoomStationReservationStudentDAO.store(labRoomStationReservationStudent);
					labRoomStationReservationStudentDAO.flush();
				}
				
			}
		}
		
	}
	/*************************************************************************************
	 * Description 判断所传数组是否是正确学生编号 返回错误的学号组
	 * 
	 * @author 孙虎
	 * @date 2017-09-22
	 *************************************************************************************/
	public String isAllStudent(String[] array) {
		//错误的学号
		String sudents = "";
		for (String username : array) {
			if(userDAO.findUserByPrimaryKey(username) == null){
				sudents += username + ",";
			}
		}
		return sudents;
	}
	

	/*************************************************************************************
	 * Description 根据登陆人权限得到实验室预约列表
	 * 
	 * @author 孙虎
	 * @date 2017-09-25
	 *************************************************************************************/
	public List<LabRoomStationReservation> findLabRoomreservatioList(LabRoomStationReservation labRoomStationReservation, int tage, int currpage,int pageSize, String acno,int isAudit) {
		String sql = "select distinct l from LabRoomStationReservation l where 1=1 ";
		if(labRoomStationReservation.getResult()!=null){
		    if(labRoomStationReservation.getResult()==2){   //审核中包括未审核状态
                sql += " and (l.result = 2 or l.result = 3)";
            }else if(labRoomStationReservation.getResult()==5){    //所有
		        //do nothing
		    }else {
                sql += " and l.result ="+ labRoomStationReservation.getResult();
            }
        }else{
		    if(isAudit == 2){        //我的预约页面默认所有
		        //do nothing
            }else {               //我的审核页面默认审核中
                sql += " and (l.result = 2 or l.result = 3)";
            }
        }
		if(labRoomStationReservation.getLabRoom() != null){
			if (labRoomStationReservation.getLabRoom().getLabRoomName() != null && !labRoomStationReservation.getLabRoom().getLabRoomName().equals("")) {
				sql += " and (l.labRoom.labRoomName like '%" + labRoomStationReservation.getLabRoom().getLabRoomName()+"%'"+ "or  l.labRoom.labRoomNumber like '%"  + labRoomStationReservation.getLabRoom().getLabRoomName() + "%' )";
			}
		}
		//新加需求，是我的审核还是我的预约，isAudit  1审核2预约
		if(isAudit == 1){
			//实验部主任权限可以看到所有
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_PREEXTEACHING") != -1){
				
			}else{
				// qun 判断 登陆者是不是超级管理员，实验教务 是的话下边权限不能进入
				int qun = 0;
				// shareService.getUser().getAuthorities().toString().indexOf(str)
				// 实验室超级管理员，实验教务,选择实验室中心的所有
				if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") != -1
						|| SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
								.indexOf("ROLE_EXPERIMENTALTEACHING") != -1) {
					qun++;
					String num = "";
					for (LabRoom iterable_element : labRoomDAO.findAllLabRooms()) {
							num += iterable_element.getId() + ",";
					}
					if (num != "") {
						sql += " and (l.labRoom.id in (" + num.substring(0, num.length() - 1) + " ) ";
						sql += " or  l.userByTeacher.username='" + shareService.getUser().getUsername() + "' ";
						sql += " or  l.userByDean.username='" + shareService.getUser().getUsername() + "' ";
						sql += ")";
					}
				}
				// shi 判断登陆者 不是超级管理员，实验教务， 是不是实验室中心主任， 是的话下边权限不能进入
				int shi = 0;
				// 实验室中心主任，看到该中心下 自己实验室下边的实验室预约
				if (qun == 0
						&& (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
								.indexOf("ROLE_EXCENTERDIRECTOR") != -1||SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
								.indexOf("ROLE_DEPARTMENTHEADER") != -1||SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
								.indexOf("ROLE_COLLEGELEADER") != -1||SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
								.indexOf("ROLE_ASSETMANAGEMENT") != -1
								)) {
					//ROLE_DEPARTMENTHEAD,ROLE_COLLEGELEADER,ROLE_ASSETMANAGEMENT为贺子龙  2015-11-28  新增
					shi++;
					String wq = "";
					for (LabCenter iter : shareService.getUser().getLabCentersForCenterManager()) {
							for (LabRoom ite : iter.getLabRooms()) {
								wq += ite.getId() + ",";
							}
					}
					if (wq != "") {
						sql += " and (l.labRoom.id in (" + wq.substring(0, wq.length() - 1) + " ) ";
						sql += " or  l.userByTeacher.username='" + shareService.getUser().getUsername() + "' ";
						sql += " or  l.userByDean.username='" + shareService.getUser().getUsername() + "' ";
						sql += ")";
					}
					;

				}
				// guan 判断登陆者 不是超级管理员，实验教务 不是实验室中心主任，是不是实验室管理员 是的话下边权限不能进入
				int guan = 0;
				// 实验室管理员
				if (qun == 0
						&& shi == 0
						&& SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
								.indexOf("ROLE_LABMANAGER") != -1) {
					guan++;
					String sql1 = "select r from LabRoomAdmin r where r.user.username like '"
							+ shareService.getUser().getUsername() + "'";
					List<LabRoomAdmin> labRoomAdmin = labRoomAdminDAO.executeQuery(sql1, 0, 3);
					if (labRoomAdmin.size() > 0) {
						sql += " and  (l.labRoom.id in (select r.labRoom.id from LabRoomAdmin r where r.user.username like '"
								+ shareService.getUser().getUsername() + "')";
						sql += " or  l.userByTeacher.username='" + shareService.getUser().getUsername() + "' ";
						sql += " or  l.userByDean.username='" + shareService.getUser().getUsername() + "' ";
						sql += ")";
					}
				}
				// 判断登陆者 不是超级管理员，实验教务, 不是实验室中心主任，是不是实验室管理员 是的话下边权限不能进入
				// System.out.println("---qun--"+qun+"---shi---"+shi+"---guan---"+guan);

				// 老师和学生SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_STUDENT")
				// != -1 ||
				// SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_TEACHER")
				// != -1
				if (qun == 0 && shi == 0 && guan == 0) {
					//sql += " and  (l.user.username='" + shareService.getUser().getUsername() + "' ";预约审核拆分为两部分时隐掉
					sql += " and (l.userByTeacher.username='" + shareService.getUser().getUsername() + "' ";
					sql += " or  l.userByDean.username='" + shareService.getUser().getUsername() + "' ";
					sql += ")";
					if(shareService.getUser().getSchoolAcademy() != null){
						sql += " and l.user.schoolAcademy.academyNumber='" + shareService.getUser().getSchoolAcademy().getAcademyNumber() + "' ";
					}else{
						sql += " and l.id = -1";
					}
				}
			}
		}else{
			sql += " and l.user.username='" + shareService.getUser().getUsername() + "' ";
		}
		
		// 通过
		if (tage == 1) {
			sql += " and l.result=1";
		}
		// 2审核中
		if (tage == 2) {
			sql += " and l.result=2";
		}
		// 3未审核
		if (tage == 3) {
			sql += " and l.result=3";
		}
		// 4审核未通过
		if (tage == 4) {
			sql += " and l.result=4";
		}
		sql += "   order by l.id desc";
		List<LabRoomStationReservation> lll = labRoomStationReservationDAO.executeQuery(sql, (currpage - 1) * pageSize, pageSize);
		
		return lll;
	}
	/*************************************************************************************
	 * Description 根据登陆人权限得到实验室预约列表
	 * 
	 * @author 孙虎
	 * @date 2017-09-25
	 *************************************************************************************/
	public List<LabRoomDeviceReservation> findLabRoomDeviceReservation(LabRoomDevice labRoomDevice, int tage, int currpage,
			int pageSize, String acno,int isAudit,HttpServletRequest request) {
		
		String status = String.valueOf(request.getParameter("status"));
		//String begintime = String.valueOf(request.getParameter("begintime"));
		//String endtime = String.valueOf(request.getParameter("endtime"));
		String sql = "select l from LabRoomDeviceReservation l where 1=1 ";
		if(labRoomDevice.getSchoolDevice() != null){
			if (labRoomDevice.getSchoolDevice().getDeviceNumber() != null && !"".equals(labRoomDevice.getSchoolDevice().getDeviceNumber())) {
				sql += " and (l.labRoomDevice.schoolDevice.deviceNumber like '%" + labRoomDevice.getSchoolDevice().getDeviceNumber()+"%')";
			}
		}
		//预约审核拆分为我的预约和我的审核 ,isAudit 1审核2预约
		if(isAudit == 1){
			//实验部主任权限可以看到所有
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_PREEXTEACHING") != -1){
				
			}else{
				// qun 判断 登陆者是不是超级管理员，实验教务 是的话下边权限不能进入
				int qun = 0;
				// shareService.getUser().getAuthorities().toString().indexOf(str)
				// 实验室超级管理员，实验教务,选择实验室中心的所有
				if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") != -1
						|| SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
								.indexOf("ROLE_EXPERIMENTALTEACHING") != -1) {
					qun++;
					String num = "";
					for (LabRoom iterable_element : labRoomDAO.findAllLabRooms()) {
							num += iterable_element.getId() + ",";
					}
					if (num != "") {
						sql += " and (l.labRoomDevice.labRoom.id in (" + num.substring(0, num.length() - 1) + " ) ";
						sql += " or  l.userByTeacher.username='" + shareService.getUser().getUsername() + "' ";
						sql += " or  l.userByDean.username='" + shareService.getUser().getUsername() + "' ";
						sql += ")";
					}
				}
				// shi 判断登陆者 不是超级管理员，实验教务， 是不是实验室中心主任， 是的话下边权限不能进入
				int shi = 0;
				// 实验室中心主任，看到该中心下 自己实验室下边的实验室预约
				if (qun == 0
						&& (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
								.indexOf("ROLE_EXCENTERDIRECTOR") != -1||SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
								.indexOf("ROLE_DEPARTMENTHEADER") != -1||SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
								.indexOf("ROLE_COLLEGELEADER") != -1||SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
								.indexOf("ROLE_ASSETMANAGEMENT") != -1
								)) {
					//ROLE_DEPARTMENTHEAD,ROLE_COLLEGELEADER,ROLE_ASSETMANAGEMENT为贺子龙  2015-11-28  新增
					shi++;
					String wq = "";
					for (LabCenter iter : shareService.getUser().getLabCentersForCenterManager()) {
							for (LabRoom ite : iter.getLabRooms()) {
								wq += ite.getId() + ",";
							}
					}
					if (wq != "") {
						sql += " and (l.labRoomDevice.labRoom.id in (" + wq.substring(0, wq.length() - 1) + " ) ";
						sql += " or  l.userByTeacher.username='" + shareService.getUser().getUsername() + "' ";
						sql += " or  l.userByDean.username='" + shareService.getUser().getUsername() + "' ";
						sql += ")";
					}
					;

				}
				// guan 判断登陆者 不是超级管理员，实验教务 不是实验室中心主任，是不是实验室管理员 是的话下边权限不能进入
				int guan = 0;
				// 实验室管理员
				if (qun == 0
						&& shi == 0
						&& SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
								.indexOf("ROLE_LABMANAGER") != -1) {
					guan++;
					String sql1 = "select r from LabRoomAdmin r where r.user.username like '"
							+ shareService.getUser().getUsername() + "'";
					List<LabRoomAdmin> labRoomAdmin = labRoomAdminDAO.executeQuery(sql1, 0, 3);
					if (labRoomAdmin.size() > 0) {
						sql += " and  (l.labRoomDevice.labRoom.id in (select r.labRoom.id from LabRoomAdmin r where r.user.username like '"
								+ shareService.getUser().getUsername() + "')";
						sql += " or  l.userByTeacher.username='" + shareService.getUser().getUsername() + "' ";
						sql += " or  l.userByDean.username='" + shareService.getUser().getUsername() + "' ";
						sql += ")";
					}
				}
				// 判断登陆者 不是超级管理员，实验教务, 不是实验室中心主任，是不是实验室管理员 是的话下边权限不能进入
				// System.out.println("---qun--"+qun+"---shi---"+shi+"---guan---"+guan);

				// 老师和学生SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_STUDENT")
				// != -1 ||
				// SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_TEACHER")
				// != -1
				if (qun == 0 && shi == 0 && guan == 0) {
					//sql += " and  (l.userByReserveUser.username='" + shareService.getUser().getUsername() + "' ";预约审核拆分为两部分时隐掉
					sql += " and (l.userByTeacher.username='" + shareService.getUser().getUsername() + "' ";
					sql += " or  l.userByDean.username='" + shareService.getUser().getUsername() + "' ";
					sql += ")";
				}
			}
		}else{
			sql += " and l.userByReserveUser.username='" + shareService.getUser().getUsername() + "' ";
		}
		
		// 1通过
		if (tage == 1) {
			sql += " and l.CDictionaryByCAuditResult.CNumber='2'";
		}
		// 2审核中
		if (tage == 2) {
			sql += " and l.CDictionaryByCAuditResult.CNumber='1'";
		}
		// 4审核未通过
		if (tage == 4) {
			sql += " and l.CDictionaryByCAuditResult.CNumber='3'";
		}
		//实验室
		if(labRoomDevice.getLabRoom()!=null){
			if(labRoomDevice.getLabRoom().getId()!=null&&!labRoomDevice.getLabRoom().getId().equals("")){
				sql+=" and l.labRoomDevice.labRoom.id="+labRoomDevice.getLabRoom().getId();
			}
		}
		//审核状态
		if(!status.equals("null") && !status.equals("")){
			if(status.equals("2")){
				sql+= " and l.CDictionaryByCAuditResult.CNumber between '2' and'3'";
			}
			if(status.equals("1")){
				sql+= " and l.CDictionaryByCAuditResult.CNumber ='1'";
			}
		}
		//学期

		String schoolTermId = request.getParameter("schoolTerm_id");
		if(schoolTermId != null ){
			sql+= " and l.schoolTerm = "+schoolTermId;
		}

		//申请时间
		String begin = request.getParameter("begintime");
		String begin1 = request.getParameter("begintime1");
		if(begin!=null && begin.length()>0 && begin1!=null&& begin1.length()>0){
			
				/*String begintime=sdf1.format(sdf2.parse(request.getParameter("begintime")));
				String begintime1=sdf1.format(sdf2.parse(request.getParameter("begintime1")));*/
				sql+= " and l.begintime between '"+begin+"' and '"+begin1+"'";
			
		}
		/*if(!begintime.equals("null") && !begintime.equals("")){
			if(!endtime.equals("null") && !endtime.equals("")){
				sql+= " and l.begintime between '"+begintime+"' and '"+endtime+"'";
			}
		}*/
		sql += "   order by l.id desc";
		List<LabRoomDeviceReservation> lll = labRoomDeviceReservationDAO.executeQuery(sql, (currpage - 1) * pageSize, pageSize);
		
		return lll;
	}
	/*************************************************************************************
	 * Description 获取所有我的申请信息
	 * 
	 * @author 张德冰
	 * @date 2018.03.07
	 *************************************************************************************/
	public List<LabRoomDeviceReservation> findLabRoomDeviceReservationAll(){
		String sql = "select l from LabRoomDeviceReservation l where 1=1 order by l.begintime asc";
		return labRoomDeviceReservationDAO.executeQuery(sql,0,-1);
	}

	/*************************************************************************************
	 * @Description 获取所有设备预约表中所有设备
	 * @author 张德冰
	 * @date 2018-12-05
	 *************************************************************************************/
	public List<SchoolDevice> findSchoolDeviceByLabRoomDeviceReservation(){
		String sql = "select distinct(s) from SchoolDevice s,LabRoomDeviceReservation l where 1=1 and l.labRoomDevice.schoolDevice.deviceNumber = s.deviceNumber";
		return schoolDeviceDAO.executeQuery(sql,0,-1);
	}

	/*************************************************************************************
	 * Description 保存不同身份的审核结果
	 * @author 孙虎
	 * @throws NoSuchAlgorithmException 
	 * @date 2017-09-27
	 *************************************************************************************/
	@Transactional
	public LabRoomStationReservation saveAuditResult(LabRoomStationReservation labRoomStationReservation, LabRoomStationReservationResult labRoomStationReservationResult) throws NoSuchAlgorithmException {
		LabRoom labRoom = labRoomStationReservation.getLabRoom();
		//审核结果
		Integer auditResult = labRoomStationReservationResult.getAuditResult();
		User user = shareService.getUserDetail();
		//消息
		Message message=new Message();
		message.setSendUser(user.getCname());
		message.setMessageState(0);//设置未读
		message.setCreateTime(Calendar.getInstance());
		message.setSendCparty(labRoomStationReservation.getUser().getSchoolAcademy().getAcademyName());

		Integer auditNumber = labRoomStationReservation.getState();
		if (labRoomStationReservation.getState() == 3) {//实验室管理员审核结果保存
			if (auditResult == 0) {//不通过
				labRoomStationReservation.setResult(4);
				labRoomStationReservation.setState(6);
				labRoomStationReservation.setRemark(labRoomStationReservation.getRemark() + user.getCname() + "审核拒绝，");
				labRoomStationReservationResult.setTag(3);
				message.setTitle("工位预约实验室管理员审核不通过");
				message.setContent("<a onclick='changeMessage(this)' href='../LabRoomReservation/checkButton?id=" + labRoomStationReservation.getId() + "&tage=0&state=" + labRoomStationReservation.getState() + "&currpage=1'>点击查看</a>");//消息内容
				message.setTage(3);
				shareService.sendMsg(labRoomStationReservation.getUser(), message);
			} else {//通过
				labRoomStationReservation.setResult(2);
				labRoomStationReservation.setRemark(labRoomStationReservation.getRemark() + user.getCname() + "审核通过，");
				labRoomStationReservationResult.setTag(3);
				labRoomStationReservation.setState(4);
				//给下一级预约人产生消息
				if (labRoom.getLabCenter().getUserByCenterManager() != null) {
					//消息内容
					message.setTitle("工位预约审核");
					message.setContent("<a onclick='changeMessage(this)' href=\"../LabRoomReservation/checkButton?id=" + labRoomStationReservation.getId() + "&tage=0&state=" + labRoomStationReservation.getState() + "&currpage=1\">审核</a>");
					message.setUsername(labRoom.getLabCenter().getUserByCenterManager().getUsername());
					message.setTage(2);
					shareService.sendMsg(labRoom.getLabCenter().getUserByCenterManager(),message);
				}

				//给预约人发消息
				message.setTitle("工位预约实验室管理员审核通过");
				message.setContent("<a onclick='changeMessage(this)' href=\"../LabRoomReservation/checkButton?id="+labRoomStationReservation.getId()+"&tage=0&state="+labRoomStationReservation.getState()+"&currpage=1\">查看</a>");//消息内容
				message.setTage(1);
				shareService.sendMsg(labRoomStationReservation.getUser(), message);
			}
		}else if(labRoomStationReservation.getState() == 4){//实验室中心主任审核结果保存
			if(auditResult == 0){//不通过
				labRoomStationReservation.setResult(4);
				labRoomStationReservation.setState(6);
				labRoomStationReservation.setRemark(labRoomStationReservation.getRemark()+user.getCname()+"审核拒绝，");
				labRoomStationReservationResult.setTag(4);
				message.setTitle("工位预约实验中心主任审核不通过");
				message.setContent("<a onclick='changeMessage(this)' href='../LabRoomReservation/checkButton?id="+labRoomStationReservation.getId()+"&tage=0&state="+labRoomStationReservation.getState()+"&currpage=1'>点击查看</a>");//消息内容
				message.setTage(3);
				shareService.sendMsg(labRoomStationReservation.getUser(), message);
			}else{//通过
				labRoomStationReservation.setResult(2);
				labRoomStationReservation.setRemark(labRoomStationReservation.getRemark()+user.getCname()+"审核通过，");
				labRoomStationReservationResult.setTag(4);
				//审核通过后判断下一级的审核状态
				labRoomStationReservation.setState(6);
				labRoomStationReservation.setResult(1);

				//给预约人发消息
				message.setTitle("工位预约实验中心主任审核通过");
				message.setContent("<a onclick='changeMessage(this)' href=\"../LabRoomReservation/checkButton?id="+labRoomStationReservation.getId()+"&tage=0&state="+labRoomStationReservation.getState()+"&currpage=1\">查看</a>");//消息内容
				message.setTage(1);
				shareService.sendMsg(labRoomStationReservation.getUser(), message);
			}
		}
		
		labRoomStationReservation = labRoomStationReservationDAO.store(labRoomStationReservation);
		labRoomStationReservationDAO.flush();
		labRoomStationReservationResult.setLabRoomStationReservation(labRoomStationReservation);
		labRoomStationReservationResult.setUser(user);
		labRoomStationReservationResult.setAuditTime(Calendar.getInstance());
		labRoomStationReservationResultDAO.store(labRoomStationReservationResult);
		labRoomStationReservationResultDAO.flush();
		return labRoomStationReservation;
	}
	/*************************************************************************************
	 * Description 保存不同身份的审核结果
	 * @author 孙虎
	 * @throws NoSuchAlgorithmException 
	 * @date 2017-10-19
	 *************************************************************************************/
	@Transactional
	public LabRoomDeviceReservation saveAuditResultDevice(LabRoomDeviceReservation labRoomDeviceReservation, LabRoomDeviceReservationResult labRoomDeviceReservationResult) throws NoSuchAlgorithmException {
		LabRoomDevice labRoomDevice = labRoomDeviceReservation.getLabRoomDevice();
		SchoolDevice schoolDevice = labRoomDevice.getSchoolDevice();
		//审核结果
		String auditResult = labRoomDeviceReservationResult.getCDictionaryByCTrainingResult().getCNumber();
		User user = shareService.getUserDetail();
		//消息
		Message message=new Message();
		message.setSendUser(user.getCname());
		message.setMessageState(0);//设置未读
		message.setCreateTime(Calendar.getInstance());
		message.setSendCparty(user.getSchoolAcademy().getAcademyName());
		
		if(labRoomDeviceReservation.getState() == 1){//导师审核结果保存
			if("2".equals(auditResult)){//不通过
				labRoomDeviceReservation.setCAuditResult(shareService.getCDictionaryByCategory("c_audit_result", "3"));
				labRoomDeviceReservation.setState(6);
				labRoomDeviceReservation.setRemark(user.getCname()+"审核拒绝，");
				labRoomDeviceReservationResult.setTag(1);
				message.setTitle("设备预约审核不通过");
				message.setContent("<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id="+labRoomDeviceReservation.getId()+"&tage=0&state="+labRoomDeviceReservation.getState()+"&currpage=1\">查看</a>");//消息内容
				message.setTage(3);
			}else{//通过
				labRoomDeviceReservation.setCAuditResult(shareService.getCDictionaryByCategory("c_audit_result", "1"));
				labRoomDeviceReservation.setRemark(user.getCname()+"审核通过，");
				labRoomDeviceReservationResult.setTag(1);
				//发给下一级的消息通用设置
				message.setTage(2);
				message.setTitle("设备预约");
				//审核通过后判断下一级的审核状态
				if(labRoomDevice.getDean() == 1){
					labRoomDeviceReservation.setState(2);
					//产生系主任的消息
		            String content = "<a onclick='changeMessage(this)'  href=\"../LabRoomDeviceReservation/checkButton?id=" + labRoomDeviceReservation.getId() + "&tage=0&state=" + labRoomDeviceReservation.getState() + "&currpage=1\">审核</a>";
		            message.setContent(content);
		            message.setUsername(labRoomDeviceReservation.getUserByDean().getUsername());
		            messageDAO.store(message);
		            messageDAO.flush();
				}else if(labRoomDevice.getCDictionaryByLabManagerAudit().getCName().equals("是")){
					labRoomDeviceReservation.setState(3);
					//产生实验室管理员的消息
		            message.setContent("<a onclick='changeMessage(this)'  href=\"../LabRoomDeviceReservation/checkButton?id=" + labRoomDeviceReservation.getId() + "&tage=0&state=" + labRoomDeviceReservation.getState() + "&currpage=1\">审核</a>");
		            if (labRoomDevice.getLabRoom().getLabRoomAdmins() != null) {
		                for (LabRoomAdmin labRoomAdmin : labRoomDevice.getLabRoom().getLabRoomAdmins()) {
		                    message.setUsername(labRoomAdmin.getUser().getUsername());
		                    messageDAO.store(message);
		                    messageDAO.flush();
		                }
		            }
				}else if(labRoomDevice.getTrainingCenterDirector() == 1){
					labRoomDeviceReservation.setState(4);
					//产生实验中心主任的消息
		            message.setContent("<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id=" + labRoomDeviceReservation.getId() + "&tage=0&state=" + labRoomDeviceReservation.getState() + "&currpage=1\">审核</a>");
		            if (labRoomDevice.getLabRoom().getLabCenter().getUserByCenterManager() != null) {
		                message.setUsername(labRoomDevice.getLabRoom().getLabCenter().getUserByCenterManager().getUsername());
		                messageDAO.store(message);
		                messageDAO.flush();
		            }
				}else if(labRoomDevice.getTrainingDepartmentDirrector() == 1){
					labRoomDeviceReservation.setState(5);
					//产生实验部主任的消息
		            message.setContent("<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id=" + labRoomDeviceReservation.getId() + "&tage=0&state=" + labRoomDeviceReservation.getState() + "&currpage=1\">审核</a>");
		            if (shareService.findUsersByAuthorityId(3) != null) {
		                for (User trainingDepartmentDirrector : shareService.findUsersByAuthorityId(3)) {
		                    message.setUsername(trainingDepartmentDirrector.getUsername());
		                    messageDAO.store(message);
		                    messageDAO.flush();
		                }
		            }
				}else{
					//预约成功刷新设备使用机时和次数
					if(schoolDevice.getUseHours() != null){
						schoolDevice.setUseHours(schoolDevice.getUseHours().add(labRoomDeviceReservation.getReserveHours()));
					}else{
						schoolDevice.setUseHours(labRoomDeviceReservation.getReserveHours());
					}
					if(schoolDevice.getUseCount() != null){
						schoolDevice.setUseCount(schoolDevice.getUseCount()+1);
					}else{
						schoolDevice.setUseCount(1);
					}
					schoolDeviceDAO.store(schoolDevice);
					schoolDeviceDAO.flush();
					labRoomDeviceReservation.setState(6);
					labRoomDeviceReservation.setCAuditResult(shareService.getCDictionaryByCategory("c_audit_result", "2"));
					message.setTage(4);
				}
				message.setTitle("设备预约导师审核通过");
				message.setContent("<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id="+labRoomDeviceReservation.getId()+"&tage=0&state="+labRoomDeviceReservation.getState()+"&currpage=1\">查看</a>");//消息内容
				message.setTage(1);
			}
			if(labRoomDeviceReservation.getUserByReserveUser().getTelephone() != null){
				try {
					String result = shareService.sendMessage(labRoomDeviceReservation.getUserByReserveUser().getTelephone(), message.getTitle());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			message.setUsername(labRoomDeviceReservation.getUserByReserveUser().getUsername());
			messageDAO.store(message);
			messageDAO.flush();
		}else if(labRoomDeviceReservation.getState() == 2){//系主任审核结果保存
			if("2".equals(auditResult)){//不通过
				labRoomDeviceReservation.setCAuditResult(shareService.getCDictionaryByCategory("c_audit_result", "3"));
				labRoomDeviceReservation.setState(6);
				labRoomDeviceReservation.setRemark(labRoomDeviceReservation.getRemark()+user.getCname()+"审核拒绝，");
				labRoomDeviceReservationResult.setTag(2);
				message.setTitle("设备预约审核不通过");
				message.setContent("<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id="+labRoomDeviceReservation.getId()+"&tage=0&state="+labRoomDeviceReservation.getState()+"&currpage=1\">查看</a>");//消息内容
				message.setTage(3);
			}else{//通过
				labRoomDeviceReservation.setCAuditResult(shareService.getCDictionaryByCategory("c_audit_result", "1"));
				labRoomDeviceReservation.setRemark(labRoomDeviceReservation.getRemark()+user.getCname()+"审核通过，");
				labRoomDeviceReservationResult.setTag(2);
				//发给下一级的消息通用设置
				message.setTage(2);
				message.setTitle("设备预约");
				//审核通过后判断下一级的审核状态
				if(labRoomDevice.getCDictionaryByLabManagerAudit().getCName().equals("是")){
					labRoomDeviceReservation.setState(3);
					//产生实验室管理员的消息
		            message.setContent("<a onclick='changeMessage(this)'  href=\"../LabRoomDeviceReservation/checkButton?id=" + labRoomDeviceReservation.getId() + "&tage=0&state=" + labRoomDeviceReservation.getState() + "&currpage=1\">审核</a>");
		            if (labRoomDevice.getLabRoom().getLabRoomAdmins() != null) {
		                for (LabRoomAdmin labRoomAdmin : labRoomDevice.getLabRoom().getLabRoomAdmins()) {
		                    message.setUsername(labRoomAdmin.getUser().getUsername());
		                    messageDAO.store(message);
		                    messageDAO.flush();
		                }
		            }
				}else if(labRoomDevice.getTrainingCenterDirector() == 1){
					labRoomDeviceReservation.setState(4);
					//产生实验中心主任的消息
		            message.setContent("<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id=" + labRoomDeviceReservation.getId() + "&tage=0&state=" + labRoomDeviceReservation.getState() + "&currpage=1\">审核</a>");
		            if (labRoomDevice.getLabRoom().getLabCenter().getUserByCenterManager() != null) {
		                message.setUsername(labRoomDevice.getLabRoom().getLabCenter().getUserByCenterManager().getUsername());
		                messageDAO.store(message);
		                messageDAO.flush();
		            }
				}else if(labRoomDevice.getTrainingDepartmentDirrector() == 1){
					labRoomDeviceReservation.setState(5);
					//产生实验部主任的消息
		            message.setContent("<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id=" + labRoomDeviceReservation.getId() + "&tage=0&state=" + labRoomDeviceReservation.getState() + "&currpage=1\">审核</a>");
		            if (shareService.findUsersByAuthorityId(3) != null) {
		                for (User trainingDepartmentDirrector : shareService.findUsersByAuthorityId(3)) {
		                    message.setUsername(trainingDepartmentDirrector.getUsername());
		                    messageDAO.store(message);
		                    messageDAO.flush();
		                }
		            }
				}else{
					//预约成功刷新设备使用机时和次数
					if(schoolDevice.getUseHours() != null){
						schoolDevice.setUseHours(schoolDevice.getUseHours().add(labRoomDeviceReservation.getReserveHours()));
					}else{
						schoolDevice.setUseHours(labRoomDeviceReservation.getReserveHours());
					}
					if(schoolDevice.getUseCount() != null){
						schoolDevice.setUseCount(schoolDevice.getUseCount()+1);
					}else{
						schoolDevice.setUseCount(1);
					}
					schoolDeviceDAO.store(schoolDevice);
					schoolDeviceDAO.flush();
					labRoomDeviceReservation.setState(6);
					labRoomDeviceReservation.setCAuditResult(shareService.getCDictionaryByCategory("c_audit_result", "2"));
					message.setTage(4);
				}
				message.setTitle("设备预约系主任审核通过");
				message.setContent("<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id="+labRoomDeviceReservation.getId()+"&tage=0&state="+labRoomDeviceReservation.getState()+"&currpage=1\">查看</a>");//消息内容
				message.setTage(1);
			}
			if(labRoomDeviceReservation.getUserByReserveUser().getTelephone() != null){
				try {
					String result = shareService.sendMessage(labRoomDeviceReservation.getUserByReserveUser().getTelephone(), message.getTitle());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			message.setUsername(labRoomDeviceReservation.getUserByReserveUser().getUsername());
			messageDAO.store(message);
			messageDAO.flush();
		}else if(labRoomDeviceReservation.getState() == 3){//实验室管理员审核结果保存
			if("2".equals(auditResult)){//不通过
				labRoomDeviceReservation.setCAuditResult(shareService.getCDictionaryByCategory("c_audit_result", "3"));
				labRoomDeviceReservation.setState(6);
				labRoomDeviceReservation.setRemark(labRoomDeviceReservation.getRemark()+user.getCname()+"审核拒绝，");
				labRoomDeviceReservation.setTag(3);
				message.setTitle("设备预约审核不通过");
				message.setContent("<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id="+labRoomDeviceReservation.getId()+"&tage=0&state="+labRoomDeviceReservation.getState()+"&currpage=1\">查看</a>");//消息内容
				message.setTage(3);
			}else{//通过
				labRoomDeviceReservation.setCAuditResult(shareService.getCDictionaryByCategory("c_audit_result", "1"));
				labRoomDeviceReservation.setRemark(labRoomDeviceReservation.getRemark()+user.getCname()+"审核通过，");
				labRoomDeviceReservationResult.setTag(3);
				//发给下一级的消息通用设置
				message.setTage(2);
				message.setTitle("设备预约");
				//审核通过后判断下一级的审核状态
				if(labRoomDevice.getTrainingCenterDirector() == 1){
					labRoomDeviceReservation.setState(4);
					//产生实验中心主任的消息
		            message.setContent("<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id=" + labRoomDeviceReservation.getId() + "&tage=0&state=" + labRoomDeviceReservation.getState() + "&currpage=1\">审核</a>");
		            if (labRoomDevice.getLabRoom().getLabCenter().getUserByCenterManager() != null) {
		                message.setUsername(labRoomDevice.getLabRoom().getLabCenter().getUserByCenterManager().getUsername());
		                messageDAO.store(message);
		                messageDAO.flush();
		            }
				}else if(labRoomDevice.getTrainingDepartmentDirrector() == 1){
					labRoomDeviceReservation.setState(5);
					//产生实验部主任的消息
		            message.setContent("<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id=" + labRoomDeviceReservation.getId() + "&tage=0&state=" + labRoomDeviceReservation.getState() + "&currpage=1\">审核</a>");
		            if (shareService.findUsersByAuthorityId(3) != null) {
		                for (User trainingDepartmentDirrector : shareService.findUsersByAuthorityId(3)) {
		                    message.setUsername(trainingDepartmentDirrector.getUsername());
		                    messageDAO.store(message);
		                    messageDAO.flush();
		                }
		            }
				}else{
					//预约成功刷新设备使用机时和次数
					if(schoolDevice.getUseHours() != null){
						schoolDevice.setUseHours(schoolDevice.getUseHours().add(labRoomDeviceReservation.getReserveHours()));
					}else{
						schoolDevice.setUseHours(labRoomDeviceReservation.getReserveHours());
					}
					if(schoolDevice.getUseCount() != null){
						schoolDevice.setUseCount(schoolDevice.getUseCount()+1);
					}else{
						schoolDevice.setUseCount(1);
					}
					schoolDeviceDAO.store(schoolDevice);
					schoolDeviceDAO.flush();
					labRoomDeviceReservation.setState(6);
					labRoomDeviceReservation.setCAuditResult(shareService.getCDictionaryByCategory("c_audit_result", "2"));
					message.setTage(4);
				}
				message.setTitle("设备预约实验室管理员审核通过");
				message.setContent("<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id="+labRoomDeviceReservation.getId()+"&tage=0&state="+labRoomDeviceReservation.getState()+"&currpage=1\">查看</a>");//消息内容
				message.setTage(1);
			}
			if(labRoomDeviceReservation.getUserByReserveUser().getTelephone() != null){
				try {
					String result = shareService.sendMessage(labRoomDeviceReservation.getUserByReserveUser().getTelephone(), message.getTitle());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
			message.setUsername(labRoomDeviceReservation.getUserByReserveUser().getUsername());
			messageDAO.store(message);
			messageDAO.flush();
		}else if(labRoomDeviceReservation.getState() == 4){//实验室中心主任审核结果保存
			if("2".equals(auditResult)){//不通过
				labRoomDeviceReservation.setCAuditResult(shareService.getCDictionaryByCategory("c_audit_result", "3"));
				labRoomDeviceReservation.setState(6);
				labRoomDeviceReservation.setRemark(labRoomDeviceReservation.getRemark()+user.getCname()+"审核拒绝，");
				labRoomDeviceReservationResult.setTag(4);
				message.setTitle("设备预约审核不通过");
				message.setContent("<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id="+labRoomDeviceReservation.getId()+"&tage=0&state="+labRoomDeviceReservation.getState()+"&currpage=1\">查看</a>");//消息内容
				message.setTage(3);
			}else{//通过
				labRoomDeviceReservation.setCAuditResult(shareService.getCDictionaryByCategory("c_audit_result", "1"));
				labRoomDeviceReservation.setRemark(labRoomDeviceReservation.getRemark()+user.getCname()+"审核通过，");
				labRoomDeviceReservationResult.setTag(4);
				//发给下一级的消息通用设置
				message.setTage(2);
				message.setTitle("设备预约");
				//审核通过后判断下一级的审核状态
				if(labRoomDevice.getTrainingDepartmentDirrector() == 1){
					labRoomDeviceReservation.setState(5);
					//产生实验部主任的消息
		            message.setContent("<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id=" + labRoomDeviceReservation.getId() + "&tage=0&state=" + labRoomDeviceReservation.getState() + "&currpage=1\">审核</a>");
		            if (shareService.findUsersByAuthorityId(3) != null) {
		                for (User trainingDepartmentDirrector : shareService.findUsersByAuthorityId(3)) {
		                    message.setUsername(trainingDepartmentDirrector.getUsername());
		                    messageDAO.store(message);
		                    messageDAO.flush();
		                }
		            }
				}else{
					//预约成功刷新设备使用机时和次数
					if(schoolDevice.getUseHours() != null){
						schoolDevice.setUseHours(schoolDevice.getUseHours().add(labRoomDeviceReservation.getReserveHours()));
					}else{
						schoolDevice.setUseHours(labRoomDeviceReservation.getReserveHours());
					}
					if(schoolDevice.getUseCount() != null){
						schoolDevice.setUseCount(schoolDevice.getUseCount()+1);
					}else{
						schoolDevice.setUseCount(1);
					}
					schoolDeviceDAO.store(schoolDevice);
					schoolDeviceDAO.flush();
					labRoomDeviceReservation.setState(6);
					labRoomDeviceReservation.setCAuditResult(shareService.getCDictionaryByCategory("c_audit_result", "2"));
					message.setTage(4);
				}
				message.setTitle("设备预约实验中心主任审核通过");
				message.setContent("<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id="+labRoomDeviceReservation.getId()+"&tage=0&state="+labRoomDeviceReservation.getState()+"&currpage=1\">查看</a>");//消息内容
				message.setTage(1);
			}
			if(labRoomDeviceReservation.getUserByReserveUser().getTelephone() != null){
				try {
					String result = shareService.sendMessage(labRoomDeviceReservation.getUserByReserveUser().getTelephone(), message.getTitle());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			message.setUsername(labRoomDeviceReservation.getUserByReserveUser().getUsername());
			messageDAO.store(message);
			messageDAO.flush();
		}else if(labRoomDeviceReservation.getState() == 5){//实验室部主任审核结果保存
			if("2".equals(auditResult)){//不通过
				labRoomDeviceReservation.setCAuditResult(shareService.getCDictionaryByCategory("c_audit_result", "3"));
				labRoomDeviceReservation.setState(6);
				labRoomDeviceReservation.setRemark(labRoomDeviceReservation.getRemark()+user.getCname()+"审核拒绝，");
				labRoomDeviceReservationResult.setTag(5);
				message.setTitle("设备预约审核不通过");
				message.setContent("<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id="+labRoomDeviceReservation.getId()+"&tage=0&state="+labRoomDeviceReservation.getState()+"&currpage=1\">查看</a>");//消息内容
				message.setTage(3);
			}else{//通过
				//预约成功刷新设备使用机时和次数
				if(schoolDevice.getUseHours() != null){
					schoolDevice.setUseHours(schoolDevice.getUseHours().add(labRoomDeviceReservation.getReserveHours()));
				}else{
					schoolDevice.setUseHours(labRoomDeviceReservation.getReserveHours());
				}
				if(schoolDevice.getUseCount() != null){
					schoolDevice.setUseCount(schoolDevice.getUseCount()+1);
				}else{
					schoolDevice.setUseCount(1);
				}
				schoolDeviceDAO.store(schoolDevice);
				schoolDeviceDAO.flush();
				labRoomDeviceReservation.setCAuditResult(shareService.getCDictionaryByCategory("c_audit_result", "2"));
				labRoomDeviceReservation.setRemark(labRoomDeviceReservation.getRemark()+user.getCname()+"审核通过，");
				labRoomDeviceReservationResult.setTag(5);
				labRoomDeviceReservation.setState(6);
				message.setTitle("设备预约实验部主任审核通过");
				message.setContent("<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id="+labRoomDeviceReservation.getId()+"&tage=0&state="+labRoomDeviceReservation.getState()+"&currpage=1\">查看</a>");//消息内容
				message.setTage(4);
			}
			if(labRoomDeviceReservation.getUserByReserveUser().getTelephone() != null){
				try {
					String result = shareService.sendMessage(labRoomDeviceReservation.getUserByReserveUser().getTelephone(), message.getTitle());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			message.setUsername(labRoomDeviceReservation.getUserByReserveUser().getUsername());
			messageDAO.store(message);
			messageDAO.flush();
		}
		
		labRoomDeviceReservation = labRoomDeviceReservationDAO.store(labRoomDeviceReservation);
		labRoomStationReservationDAO.flush();
		labRoomDeviceReservationResult.setLabRoomDeviceReservation(labRoomDeviceReservation);
		labRoomDeviceReservationResult.setUser(user);
		labRoomDeviceReservationResult.setAuditTime(Calendar.getInstance());
		labRoomDeviceReservationResultDAO.store(labRoomDeviceReservationResult);
		labRoomDeviceReservationResultDAO.flush();
		return labRoomDeviceReservation;
	}
	@Override
	public void initializeTeacherCredit() {
		// TODO Auto-generated method stub
		String sql="update user set credit_score=100";
		Query query=entityManager.createQuery(sql);
		query.executeUpdate();
		
	}

	@Override
	public void initializeStudentCredit() {
		// TODO Auto-generated method stub
		
	}
	/***************************** 
	*Description 找到教师
	*
	*@author:周志辉
	*@param:
	*@date:2017.9.28
	*****************************/
	public List<User> findTeacherByQuery(){
		String sql = "select u from User u where 1=1 and u.userRole=1";
		return userDAO.executeQuery(sql,0,-1);
	}
	/***************************** 
	*Description 找到学生
	*
	*@author:周志辉
	*@param:
	*@date:2017.9.28
	*****************************/
	public List<User> findStudentByQuery(){
		String sql = "select u from User u where 1=1 and userRole=0";
		return userDAO.executeQuery(sql,0,-1);
	}
	/***************************** 
	*Description 筛选实验室预约
	*@author:孙虎
	*@param:
	*@date:2017.10.16
	*****************************/
	public List<LabRoomStationReservation> getLabRoomStationReversationList(Integer labRoomId, Calendar reservationTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.format(reservationTime.getTime());
		String sql = "select l from LabRoomStationReservation l where 1=1 and l.reservation ='"+sdf.format(reservationTime.getTime())+"' and l.labRoom.id="+labRoomId;
		return labRoomStationReservationDAO.executeQuery(sql,0,-1);
	}
	/****************************************************************************
	 * 功能：查询信誉登记纪录by username
	 * 作者：周志辉
	 * @return 
	 ****************************************************************************/
	@Override
	public List<LabRoomStationReservationCredit> findCreditByUsername(
			String username) {
		String sql="Select l from LabRoomStationReservationCredit l where 1=1";
		sql+=" and l.labRoomStationReservation.user.username like'"+username+"'";
		return labRoomStationReservationCreditDAO.executeQuery(sql);
	}
	/*************************************************************************************
	 * Description 保存不同身份的审核结果
	 * @author 孙虎
	 * @throws NoSuchAlgorithmException 
	 * @throws InterruptedException 
	 * @date 2017-11-5
	 *************************************************************************************/
	public SoftwareReserve saveAuditResultForSoftware(SoftwareReserve softwareReserve, SoftwareReserveAudit softwareReserveAudit) throws NoSuchAlgorithmException, InterruptedException {
		LabRoom labRoom = softwareReserve.getLabRoom();
		User user = shareService.getUserDetail();
		//消息
		Message message=new Message();
		message.setSendUser(user.getCname());
		message.setMessageState(0);//设置未读
		message.setCreateTime(Calendar.getInstance());
		message.setUsername(softwareReserve.getUser().getUsername());
		//给下一级的消息
        Message nextMessage = new Message();
        nextMessage.setSendUser(shareService.getUserDetail().getCname());
        nextMessage.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
        nextMessage.setCond(0);
        nextMessage.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
        nextMessage.setCreateTime(Calendar.getInstance());
        nextMessage.setTage(2);
		//审核结果
		Integer auditResult = Integer.valueOf(softwareReserveAudit.getResult());
		
		if(softwareReserve.getState() == 2){//系主任审核结果保存
			if(auditResult == 0){//不通过
				softwareReserve.setApproveState(3);
				softwareReserve.setState(6);
				message.setTitle("软件安装申请审核未通过");
				message.setContent("<a onclick='changeMessage(this)' href=\"../SoftwareReservation/checkButton?id="+softwareReserve.getId()+"&tage=0&state="+softwareReserve.getState()+"&page=1\">查看</a>");//消息内容
				message.setTage(3);
			}else{//通过
				softwareReserve.setApproveState(2);
				softwareReserve.setState(3);
				nextMessage.setTitle("软件安装申请审核");

				//给下一级发消息
				nextMessage.setContent("<a onclick='changeMessage(this)' href=\"../SoftwareReservation/checkButton?id="+softwareReserve.getId()+"&tage=0&state="+softwareReserve.getState()+"&page=1\">审核</a>");//消息内容
				//实验室管理员
				if(labRoomDeviceService.findAdminByLrid(softwareReserve.getLabRoom().getId()) != null){
					for(User labRoomAdmin:labRoomDeviceService.findAdminByLrid(softwareReserve.getLabRoom().getId())){
						shareService.sendMsg(labRoomAdmin, nextMessage);
					}
				}
				message.setTitle("软件安装申请系主任通过");
				message.setContent("<a onclick='changeMessage(this)' href=\"../SoftwareReservation/checkButton?id="+softwareReserve.getId()+"&tage=0&state="+softwareReserve.getState()+"&page=1\">查看</a>");//消息内容
				message.setTage(1);
			}
			softwareReserveAudit.setStatus(2);
			if(shareService.getUserDetail().getTelephone() != null){
				try {
					String result = shareService.sendMessage(shareService.getUserDetail().getTelephone(), message.getTitle());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			shareService.sendMsg(softwareReserve.getUser(), message);
		}else if(softwareReserve.getState() == 3){//实验室管理员审核结果保存
			if(auditResult == 0){//不通过
				softwareReserve.setApproveState(3);
				softwareReserve.setState(6);
				message.setTitle("软件安装申请审核未通过");
				message.setContent("<a onclick='changeMessage(this)' href=\"../SoftwareReservation/checkButton?id="+softwareReserve.getId()+"&tage=0&state="+softwareReserve.getState()+"&page=1\">查看</a>");//消息内容
				message.setTage(3);
			}else{//通过
				softwareReserve.setApproveState(2);
				softwareReserve.setState(4);
				nextMessage.setTitle("软件安装申请审核");
				//给下一级发消息
				nextMessage.setContent("<a onclick='changeMessage(this)' href=\"../SoftwareReservation/checkButton?id="+softwareReserve.getId()+"&tage=0&state="+softwareReserve.getState()+"&page=1\">审核</a>");//消息内容
				//实验中心主任
				if(softwareReserve.getLabRoom().getLabCenter() != null){
					if(softwareReserve.getLabRoom().getLabCenter().getUserByCenterManager() != null){
						shareService.sendMsg(softwareReserve.getLabRoom().getLabCenter().getUserByCenterManager(), nextMessage);
					}
				}
				
				message.setTitle("软件安装申请实验室管理员通过");
				message.setContent("<a onclick='changeMessage(this)' href=\"../SoftwareReservation/checkButton?id="+softwareReserve.getId()+"&tage=0&state="+softwareReserve.getState()+"&page=1\">查看</a>");//消息内容
				message.setTage(1);
			}
			softwareReserveAudit.setStatus(3);
			shareService.sendMsg(softwareReserve.getUser(), message);
		}else if(softwareReserve.getState() == 4){//实验室中心主任审核结果保存
			if(auditResult == 0){//不通过
				softwareReserve.setApproveState(3);
				softwareReserve.setState(6);
				message.setTitle("软件安装申请审核未通过");
				message.setContent("<a onclick='changeMessage(this)' href=\"../SoftwareReservation/checkButton?id="+softwareReserve.getId()+"&tage=0&state="+softwareReserve.getState()+"&page=1\">查看</a>");//消息内容
				message.setTage(3);
			}else{//通过
				softwareReserve.setApproveState(2);
				softwareReserve.setState(5);
				nextMessage.setTitle("软件安装申请审核");
				//给下一级发消息
				nextMessage.setContent("<a onclick='changeMessage(this)' href=\"../SoftwareReservation/checkButton?id="+softwareReserve.getId()+"&tage=0&state="+softwareReserve.getState()+"&page=1\">审核</a>");//消息内容
				//实验部主任
				if(shareService.findUsersByAuthorityId(22) != null){
					for (User trainingDepartmentDirrector : shareService.findUsersByAuthorityId(22)) {
						shareService.sendMsg(trainingDepartmentDirrector, nextMessage);
					}
				}
				
				message.setTitle("软件安装申请实验中心主任通过");
				message.setContent("<a onclick='changeMessage(this)' href=\"../SoftwareReservation/checkButton?id="+softwareReserve.getId()+"&tage=0&state="+softwareReserve.getState()+"&page=1\">查看</a>");//消息内容
				message.setTage(1);
			}
			softwareReserveAudit.setStatus(4);
			shareService.sendMsg(softwareReserve.getUser(), message);
		}else if(softwareReserve.getState() == 5){//实验室部主任审核结果保存
			if(auditResult == 0){//不通过
				softwareReserve.setApproveState(3);
				softwareReserve.setState(6);
				message.setTitle("软件安装申请审核未通过");
				message.setContent("<a onclick='changeMessage(this)' href=\"../SoftwareReservation/checkButton?id="+softwareReserve.getId()+"&tage=0&state="+softwareReserve.getState()+"&page=1\">查看</a>");//消息内容
				message.setTage(3);
			}else{//通过
				softwareReserve.setApproveState(4);
				softwareReserve.setState(6);
				message.setTitle("软件安装申请审核通过");
				message.setContent("<a onclick='changeMessage(this)' href=\"../SoftwareReservation/checkButton?id="+softwareReserve.getId()+"&tage=0&state="+softwareReserve.getState()+"&page=1\">查看</a>");//消息内容
				message.setTage(4);
			}
			softwareReserveAudit.setStatus(5);
			shareService.sendMsg(softwareReserve.getUser(), message);
		}
		
		softwareReserve = softwareReserveDAO.store(softwareReserve);
		softwareReserveDAO.flush();
		softwareReserveAudit.setSoftwareReserve(softwareReserve);
		softwareReserveAudit.setUser(user);
		softwareReserveAudit.setCreateDate(Calendar.getInstance());
		softwareReserveAuditDAO.store(softwareReserveAudit);
		softwareReserveAuditDAO.flush();
		return softwareReserve;
	}
	/*************************************************************************************
	 * Description 分页查询实验室预约的实验室
	 *
	 * @author 廖文辉
	 * @date 2018.11.28
	 *************************************************************************************/
	public List<LabRoom> findLabRoomReservation(LabRoom labRoom, int currpage, int pageSize, String acno,HttpServletRequest request){
		String hql = "select distinct l from LabRoom l,LabOpenUpAcademy loua where l.id = loua.labRoomId and l.labRoomReservation=1 and l.labRoomActive=1";
		if (labRoom.getLabRoomName() != null && !labRoom.getLabRoomName().equals("")) {
			hql += " and  (l.labRoomName like '%" + labRoom.getLabRoomName() + "%'  or l.labRoomNumber like '%"
					+ labRoom.getLabRoomName() + "%')";
		}
		String worker = request.getParameter("worker");
		String searchflg = request.getParameter("searchflg");
		if(searchflg != null && searchflg != ""){
			if(worker != null && worker != ""){
				if(searchflg.equals("1")){
					hql+=" and l.labRoomWorker = " + worker;
				}else if(searchflg.equals("2")){
					hql+=" and l.labRoomWorker > " + worker;
				}else if(searchflg.equals("3")){
					hql+=" and l.labRoomWorker < " + worker;
				}
			}
		}
		if (labRoom.getLabRoomWorker() != null) {
			hql += " and  l.labRoomWorker = " + labRoom.getLabRoomWorker();
		}
		if (labRoom.getLabRoomEnName() != null && !labRoom.getLabRoomEnName().equals("")){
			hql += " and l in (select l from Software s,LabRoom l,SoftwareRoomRelated srr where s.id=srr.software.id and l.id=srr.labRoom.id and s.name like'%"+labRoom.getLabRoomEnName()+"%')" ;
		}
		if (labRoom.getLabRoonAbbreviation() != null && !labRoom.getLabRoonAbbreviation().equals("")){
			hql += " and l in (select l from LabRoomDevice d,LabRoom l where d.id not in (select ld.labRoomDevice.id from OperationItemDevice ld) and d.labRoom.id=l.id and d.schoolDevice.deviceName like'%"+labRoom.getLabRoonAbbreviation()+"%')" ;
		}
		//浙江建设，实验室管理员预约列表只能看自己管理的
		if(pConfig.PROJECT_NAME.equals("zjcclims") && (request.getSession().getAttribute("selected_role").equals("ROLE_LABMANAGER") || request.getSession().getAttribute("selected_role").equals("ROLE_CABINETADMIN"))){
			hql += " and l not in (select l from LabRoomAdmin lra, LabRoom l where lra.labRoom = l and lra.user.username = '" + shareService.getUserDetail().getUsername() + "')";
		}
//		if(acno!=null && !acno.equals("-1")){
//
//            // 开放范围
//            hql += " and l in (select l from LabRoom l join l.openSchoolAcademies openSAs where (openSAs.academyNumber = '" + acno + "' or openSAs.academyNumber='20190506'))";
//			hql +=" order by case when l.labCenter.schoolAcademy.academyNumber='" + acno + "' then 0 else 1 end ";
//		}
		User user = shareService.getUser();
		Set<Authority> authorities = user.getAuthorities();
		if(acno!=null && !acno.equals("-1")){// 20190506全校
			// 开放范围/开放对象
			hql += " and (";
			hql += " ((loua.academyNumber = '" + acno + "' or loua.academyNumber='20190506') and loua.type = 1 and loua.authorityId = -1)";
			for(Authority authority : authorities){
				hql += " or ((loua.academyNumber = '" + acno + "' or loua.academyNumber='20190506') and loua.type = 1 and loua.authorityId = " + authority.getId() + ")";
			}
			hql += ")";
            hql +=" order by case when l.labCenter.schoolAcademy.academyNumber='" + acno + "' then 0 else 1 end ";
		}
		return labRoomDAO.executeQuery(hql,(currpage-1)*pageSize,pageSize);
	}
	/*************************************************************************************
	 * Description 保存不同身份的审核结果
	 * @author 孙虎
	 * @throws NoSuchAlgorithmException
	 * @date 2017-10-19
	 *************************************************************************************/
	@Override
	@Transactional
	public LabRoomDeviceReservation saveAuditResultDevice(LabRoomDeviceReservation labRoomDeviceReservation,
                                                          Integer audit, String remark) throws NoSuchAlgorithmException {
		LabRoomDevice labRoomDevice = labRoomDeviceReservation.getLabRoomDevice();
		SchoolDevice schoolDevice = labRoomDevice.getSchoolDevice();
		//审核结果
		String auditResult = audit.toString();
		User user = shareService.getUserDetail();
		//消息
		Message message=new Message();
		message.setSendUser(user.getCname());
		message.setMessageState(0);//设置未读
		message.setCreateTime(Calendar.getInstance());
		message.setSendCparty(user.getSchoolAcademy().getAcademyName());
		message.setTitle("设备预约审核");

		// 保存审核结果到审核服务
		Map<String, String> params = new HashMap<>();
		String businessType = "DeviceReservation" + labRoomDeviceReservation.getLabRoomDevice().getLabRoom().getLabCenter().getSchoolAcademy().getAcademyNumber();
		params.put("businessType", pConfig.PROJECT_NAME + businessType);
		params.put("businessAppUid", labRoomDeviceReservation.getId().toString());
		params.put("businessUid", labRoomDeviceReservation.getLabRoomDevice().getId().toString());
		params.put("result", "1".equals(auditResult) ? "pass" : "fail");
		params.put("info", remark);
		params.put("username", user.getUsername());
		String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/saveBusinessLevelAudit", params);
		JSONObject jsonObject = JSON.parseObject(s);
		if(!"success".equals(jsonObject.getString("status"))){
			return null;
		}
		JSONObject resultJSONObject = jsonObject.getJSONObject("data");
		String nextAuthName = (String) resultJSONObject.values().iterator().next();
		//下一个审核人是教师权限，当前登录人不是学生
		if("TEACHER".equals(nextAuthName) && !"0".equals(user.getUserRole())){
			params.put("businessType", pConfig.PROJECT_NAME + businessType);
			params.put("businessAppUid", labRoomDeviceReservation.getId().toString());
			params.put("businessUid", labRoomDeviceReservation.getLabRoomDevice().getId().toString());
			params.put("result", "1".equals(auditResult) ? "pass" : "fail");
			params.put("info", remark);
			params.put("username", user.getUsername());
			String saveStr = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/saveBusinessLevelAudit", params);
			JSONObject saveJSONObject = JSONObject.parseObject(saveStr);
			JSONObject saveResult = saveJSONObject.getJSONObject("data");
			nextAuthName = (String) saveResult.values().iterator().next();
		}

		//判断预约产生时处于第几级审核状态
		if ("TEACHER".equals(nextAuthName)) {
			//产生导师的消息
			String content = "<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id=" + labRoomDeviceReservation.getId() + "&tage=0&state=" + 1 + "&currpage=1\">审核</a>";
			message.setContent(content);
			message.setTage(2);
			shareService.sendMsg(labRoomDeviceReservation.getUserByTeacher(), message);
		} else if ("CFO".equals(nextAuthName)) {
			//产生系主任的消息
			String content = "<a onclick='changeMessage(this)'  href=\"../LabRoomDeviceReservation/checkButton?id=" + labRoomDeviceReservation.getId() + "&tage=0&state=" + 1 + "&currpage=1\">审核</a>";
			message.setContent(content);
			List<User> deans = shareService.findDeansByAcademyNumber(labRoomDeviceReservation.getUserByReserveUser().getSchoolAcademy());
			if(deans != null && deans.size() > 0) {
				for(User dean: deans) {
					message.setTage(2);
					shareService.sendMsg(dean, message);
				}
			}
		} else if ("EQUIPMENTADMIN".equals(nextAuthName)) {
            //产生设备管理员的消息
            String content = "<a onclick='changeMessage(this)'  href=\"../LabRoomDeviceReservation/checkButton?id=" + labRoomDeviceReservation.getId() + "&tage=0&state=" + 1 + "&currpage=1\">审核</a>";
            message.setContent(content);
            if (labRoomDevice.getUser() != null){
                message.setTage(2);
                shareService.sendMsg(labRoomDevice.getUser(), message);
            }
        } else if ("LABMANAGER".equals(nextAuthName)) {
			//产生实验室管理员的消息
			String content = "<a onclick='changeMessage(this)'  href=\"../LabRoomDeviceReservation/checkButton?id=" + labRoomDeviceReservation.getId() + "&tage=0&state=" + 1 + "&currpage=1\">审核</a>";
			message.setContent(content);
			if (labRoomDevice.getLabRoom().getLabRoomAdmins() != null) {
				for (LabRoomAdmin labRoomAdmin : labRoomDevice.getLabRoom().getLabRoomAdmins()) {
					message.setTage(2);
					shareService.sendMsg(labRoomAdmin.getUser(), message);
				}
			}
		} else if ("EXCENTERDIRECTOR".equals(nextAuthName)) {
			//产生实验中心主任的消息
			String content = "<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id=" + labRoomDeviceReservation.getId() + "&tage=0&state=" + 1 + "&currpage=1\">审核</a>";
			message.setContent(content);
			if (labRoomDevice.getLabRoom().getLabCenter().getUserByCenterManager() != null) {
				message.setTage(2);
				shareService.sendMsg(labRoomDevice.getLabRoom().getLabCenter().getUserByCenterManager(), message);
			}
		} else if (!"pass".equals(nextAuthName) && !"fail".equals(nextAuthName)) {
			//产生实验部主任的消息
			String content = "<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id=" + labRoomDeviceReservation.getId() + "&tage=0&state=" + 1 + "&currpage=1\">审核</a>";
			message.setContent(content);
			if (shareService.findUsersByAuthorityName(nextAuthName) != null) {
				for (User authUser : shareService.findUsersByAuthorityName(nextAuthName)) {
					message.setTage(2);
					shareService.sendMsg(authUser, message);
				}
			}
		} else if("pass".equals(nextAuthName)) {
			labRoomDeviceReservation.setCAuditResult(shareService.getCDictionaryByCategory("c_audit_result", "2"));
		}else if("fail".equals(nextAuthName)) {
			labRoomDeviceReservation.setCAuditResult(shareService.getCDictionaryByCategory("c_audit_result", "3"));
		}

		//消息(发送到申请人)
		Message messageSendSelf = new Message();
		messageSendSelf.setSendUser(shareService.getUserDetail().getCname());
		messageSendSelf.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
		messageSendSelf.setCond(0);
		messageSendSelf.setTitle("设备预约" + shareService.getUserDetail().getCname() + "审核" + ("1".equals(auditResult) ? "通过" : "拒绝"));
		messageSendSelf.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
		messageSendSelf.setCreateTime(Calendar.getInstance());
		String contents = "<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id=" + labRoomDeviceReservation.getId() + "&tage=0&state=0&currpage=1\">查看</a>";
		messageSendSelf.setContent(contents);
		messageSendSelf.setTage(1);
		shareService.sendMsg(labRoomDeviceReservation.getUserByReserveUser(), messageSendSelf);

		labRoomDeviceReservation.setRemark(labRoomDeviceReservation.getRemark() + shareService.getUserDetail().getCname() + "审核" + ("1".equals(auditResult) ? "通过" : "拒绝") + ",");
		labRoomDeviceReservation = labRoomDeviceReservationDAO.store(labRoomDeviceReservation);
		labRoomStationReservationDAO.flush();
		return labRoomDeviceReservation;
	}

	/*************************************************************************************
	 * Description 保存实验室预约（工位预约-预约）
	 *
	 * @author 孙虎
	 * @throws NoSuchAlgorithmException
	 * @date 2017-09-21
	 *************************************************************************************/
	@Transactional
	public void saveReservationStations(Integer labRoomId, Calendar reservationTime,  Calendar startTime,  Calendar endTime,String[] array,String reason,String teacher,String userRole) throws NoSuchAlgorithmException {
		LabRoom labRoom = labRoomDAO.findLabRoomById(labRoomId);
		//先保存实验室预约
		LabRoomStationReservation labRoomStationReservation = new LabRoomStationReservation();
		//先保存一次获取数据库分配的id
		labRoomStationReservation = labRoomStationReservationDAO.store(labRoomStationReservation);
		labRoomStationReservation.setLabRoom(labRoom);
		labRoomStationReservation.setReason(reason);
		labRoomStationReservation.setReservation(reservationTime);
		labRoomStationReservation.setStartTime(startTime);
		labRoomStationReservation.setEndTime(endTime);
		SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(reservationTime);
		labRoomStationReservation.setSchoolTerm(schoolTerm);
		User user = shareService.getUserDetail();
		labRoomStationReservation.setUser(user);
		labRoomStationReservation.setStationCount(array.length);

		CDictionary status = shareService.getCDictionaryByCategory("lab_room_station_reservation_user_role", userRole);
		labRoomStationReservation.setCDictionary(status);
		if (!teacher.equals("")) {
			labRoomStationReservation.setUserByTeacher(userDAO.findUserByPrimaryKey(teacher));
		}


//		boolean flag = shareService.getAuditOrNot("LabRoomStationGradedOrNot");
//		String grade = "";
//		if(flag){
//			grade = labRoomStationReservation.getLabRoom().getLabRoomLevel().toString();
//			boolean audit = shareService.getExtendItem(grade + "LabRoomStationGradedOrNot");
//			if(audit){
//				labRoomStationReservation.setState(3);
//				labRoomStationReservation.setResult(3);
//			}else{
//				labRoomStationReservation.setResult(1);
//				labRoomStationReservation.setState(6);
//			}
//		}else{
//			labRoomStationReservation.setState(3);
//			labRoomStationReservation.setResult(3);
//		}
		//该实验室下的工位预约是否需要审核
		LabRelevantConfig labRelevantConfig = labRelevantConfigDAO.findLabRelevantConfigBylabRoomIdAndCategory(labRoomId,"lab_station_is_appointment_audit");
        if(labRelevantConfig == null ||(labRelevantConfig != null && labRelevantConfig.getSetItem()==0) ){   //实验室设置工位预约不需要审核
		    labRoomStationReservation.setResult(1);//设置该预约记录审核通过
        }else {
			labRoomStationReservation.setResult(3);
        }

		labRoomStationReservation = labRoomStationReservationDAO.store(labRoomStationReservation);
		labRoomStationReservationDAO.flush();

		//在保存对应的多表
		if(array != null && !array.equals("")){
			Set<LabRoomStationReservationStudent> labRoomStationReservationStudents = new HashSet<LabRoomStationReservationStudent>();
			for (String username : array) {
				if(!"".equals(username)){
					LabRoomStationReservationStudent labRoomStationReservationStudent = new LabRoomStationReservationStudent();
					labRoomStationReservationStudent.setLabRoomStationReservation(labRoomStationReservation);
					labRoomStationReservationStudent.setUsername(userDAO.findUserByUsername(username).getUsername());
					labRoomStationReservationStudent.setCname(userDAO.findUserByUsername(username).getCname());
					//labRoomStationReservation.setLabRoomStationReservationStudents(labRoomStationReservationStudents);
					labRoomStationReservationStudentDAO.store(labRoomStationReservationStudent);
					labRoomStationReservationStudentDAO.flush();
				}

			}
		}

		//消息
        String businessType = pConfig.PROJECT_NAME + "StationReservation" + (labRoom.getLabCenter() == null ? "-1" : labRoom.getLabCenter().getSchoolAcademy().getAcademyNumber());
        String businessAppUid = shareService.saveAuditSerialNumbers(labRoomStationReservation.getId().toString(), businessType);
        String businessUid = labRoom.getId().toString();
        if(labRoomStationReservation.getResult() != 1 ) {//需要审核就发信息
			// 审核微服务
			Map<String, String> params = new HashMap<>();
			//默认教务排课，type=1
//			String businessType = grade + "StationReservation";
            params.put("businessUid", businessUid);
			params.put("businessType", businessType);
            // 业务流水号，保存并返回流水号
            params.put("businessAppUid", businessAppUid);
//			params.put("businessAppUid", labRoomStationReservation.getId().toString());
			String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/saveInitBusinessAuditStatus", params);
			JSONObject jsonObject = JSON.parseObject(s);
			String statusStr = jsonObject.getString("status");
			if(!statusStr.equals("success")){             //审核服务没有查到审核层级意味着未配置全局审核设置项
                labRoomStationReservation.setResult(1);   //设置该预约记录审核通过
			}
			s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", params);
			jsonObject = JSON.parseObject(s);
			statusStr = jsonObject.getString("status");
			if(!statusStr.equals("success")){
				return;
			}
			JSONArray currArray = jsonObject.getJSONArray("data");
			Integer curStage = -2;
			String curAuthName = "";
			if (currArray != null && currArray.size() > 0) {
				JSONObject o = currArray.getJSONObject(0);
				curStage = o.getIntValue("level");
				curAuthName = o.getString("result");
                //不需要教师审核的部分，创建一条审核数据，更新审核等级
                if (curStage == 1 && "TEACHER".equals(curAuthName)) {
                    //如果第一级是教师审核则判断当前预约人是否有教师权限，有则教师权限审核通过
                    Set<Authority> authorities = user.getAuthorities();
                    boolean flag = false;
                    for(Authority authority:authorities){
                        if(authority.getAuthorityName().equals("TEACHER")){
                            flag = true;
                        }
                    }
                    if(flag == true){
                        Map<String, String> params1 = new HashMap<>();
                        params1.put("businessType", businessType);
                        params1.put("businessAppUid", businessAppUid);
                        params1.put("businessUid", labRoom.getId().toString());
                        params1.put("result", "pass");
                        params1.put("info", "不是学生不需要导师审核");
                        params1.put("username", user.getUsername());
                        String s3 = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/saveBusinessLevelAudit", params1);
                        JSONObject jsonObject4 = JSON.parseObject(s3);
                        String status3 = jsonObject4.getString("status");
                        if (status3.equals("fail")) {
                            return;
                        }
                        //重新查询审核状态
						s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", params);
						jsonObject = JSON.parseObject(s);
						statusStr = jsonObject.getString("status");
						if(!statusStr.equals("success")){
							return;
						}
						JSONArray currArray1 = jsonObject.getJSONArray("data");
						if (currArray1 != null && currArray1.size() > 0) {
							JSONObject o1 = currArray1.getJSONObject(0);
							curAuthName = o1.getString("result");
						}
                    }
                }
            }
            Message message = new Message();
            String content = "";
            if(curAuthName.equals("pass")){             //审核通过
                labRoomStationReservation.setResult(1);   //设置该预约记录审核通过
            }else {
                List<User> nextUsers = this.getNextAuditUser(curAuthName, businessAppUid,businessType);
                Calendar date1 = Calendar.getInstance();
                message.setSendUser(shareService.getUserDetail().getCname());
                message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
                message.setCond(0);
                message.setTitle("实验室工位预约审核");
                content = "<a onclick='changeMessage(this)' href=\"../auditing/auditList?businessType=" + businessType + "&businessUid=" + businessUid + "&businessAppUid=" + businessAppUid + "\">审核</a>";
                message.setContent(content);
                message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
                message.setCreateTime(date1);
                for(User user1: nextUsers){
                    message.setTage(2);
                    shareService.sendMsg(user1, message);
                }
            }


			//是否是一级实验室
			//if (labRoom.getLabRoomLevel() == 1) {
			//产生实验室管理员的消息
//			if (labRoom.getLabRoomAdmins() != null) {
//				for (LabRoomAdmin labRoomAdmin : labRoom.getLabRoomAdmins()) {
//					message.setTage(2);
//					shareService.sendMsg(labRoomAdmin.getUser(), message);
//				}
//			}
			// 给预约人发消息
			message.setTitle("实验室工位预约提交成功");
			content = "<a onclick='changeMessage(this)' href=\"../auditing/auditList?businessType=" + businessType + "&businessUid=" + businessUid + "&businessAppUid=" + businessAppUid + "\">点击查看</a>";
			message.setContent(content);
			message.setTage(1);
			shareService.sendMsg(labRoomStationReservation.getUser(), message);
			//}
		}else{
			Message message = new Message();
			message.setSendUser(shareService.getUserDetail().getCname());
			message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
			message.setCond(0);
			// 给预约人发消息
			message.setTitle("实验室工位预约不需要审核");
//			String businessType = grade + "StationReservation";

			String content = "<a onclick='changeMessage(this)' href=\"../auditing/auditList?businessType=" + businessType + "&businessUid=" + businessUid + "&businessAppUid=" + businessAppUid + "\">点击查看</a>";
			message.setContent(content);
			message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
			message.setCreateTime(Calendar.getInstance());
			message.setTage(1);
			shareService.sendMsg(labRoomStationReservation.getUser(), message);
			// 给预约的实验室管理员发送消息
			for (LabRoomAdmin labRoomAdmin : labRoom.getLabRoomAdmins()) {
				message.setTitle("无需审核的工位预约申请");
				message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
				message.setCreateTime(Calendar.getInstance());
				message.setTage(2);
				shareService.sendMsg(labRoomAdmin.getUser(), message);
			}
		}
		if(labRoomStationReservation.getResult()==1){
            Message message = new Message();
            message.setSendUser(shareService.getUserDetail().getCname());
            message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
            message.setCond(0);
            // 给预约人发消息
            message.setTitle("实验室工位预约不需要审核");
//			String businessType = grade + "StationReservation";

            String content = "<a onclick='changeMessage(this)' href=\"../auditing/auditList?businessType=" + businessType + "&businessUid=" + businessUid + "&businessAppUid=" + businessAppUid + "\">点击查看</a>";
            message.setContent(content);
            message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
            message.setCreateTime(Calendar.getInstance());
            message.setTage(1);
            shareService.sendMsg(labRoomStationReservation.getUser(), message);
            // 给预约的实验室管理员发送消息
            for (LabRoomAdmin labRoomAdmin : labRoom.getLabRoomAdmins()) {
                message.setTitle("无需审核的工位预约申请");
                message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
                message.setCreateTime(Calendar.getInstance());
                message.setTage(2);
                shareService.sendMsg(labRoomAdmin.getUser(), message);
            }
			// 判断当天预约--下发权限
			Boolean bln = shareService.theSameDay(labRoomStationReservation.getReservation().getTime());
			// 如果当前日期和预约日期相同即同一天，则向物联发送刷新权限请求
			if(bln) {
				labRoomService.sendAgentInfoTodayToIOT(labRoomStationReservation.getLabRoom().getId());
			}
        }
	}

	/**
	 * 获取下一级审核人
	 * @param nextAuth 下一级审核权限
	 * @return 审核人列表
	 * @author 黄保钱 2019-5-7
	 */
	@Override
	@Transactional
	public List<User> getNextAuditUser(String nextAuth, String businessAppUid,String businessType) {
        if(shareService.getReservationIdBySerialNumber(businessAppUid,businessType)=="fail"){
            //没有流水单号
            //do nothing
        }else {
            //有流水单号反查预约id
            businessAppUid = shareService.getReservationIdBySerialNumber(businessAppUid,businessType);
        }
		List<User> auditUsers = new ArrayList<>();
		Integer id = Integer.parseInt(businessAppUid);
		LabRoomStationReservation stationReservation = labRoomStationReservationDAO.findLabRoomStationReservationById(id);
		switch (nextAuth){
			case "LABMANAGER":
				for(LabRoomAdmin labRoomAdmin: stationReservation.getLabRoom().getLabRoomAdmins()){
					if(labRoomAdmin.getTypeId() == 1){
						auditUsers.add(labRoomAdmin.getUser());
					}
				}
				break;
			case "TEACHER":
				auditUsers.add(stationReservation.getUserByTeacher());
				break;
			case "EXCENTERDIRECTOR":
				auditUsers.add(stationReservation.getLabRoom().getLabCenter().getUserByCenterManager());
				break;
			default:
				auditUsers.addAll(shareService.findUsersByAuthorityNameAndAcno(nextAuth, stationReservation.getLabRoom().getLabCenter().getSchoolAcademy().getAcademyNumber()));
		}
		return auditUsers;
	}
}
