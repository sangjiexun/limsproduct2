package net.zjcclims.service.device;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.ShareService;

import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service("SchoolDeviceService")
public class SchoolDeviceServiceImpl implements SchoolDeviceService{
	
	@Autowired
	SchoolDeviceDAO schoolDeviceDAO;
	@Autowired
	private LabCenterDAO labCenterDAO;
	@Autowired
	private ShareService shareService;
	@Autowired
	private SchoolDeviceUseDAO schoolDeviceUseDAO;
	@Autowired
	private SchoolAcademyDAO schoolAcademyDAO;
	@Autowired
	private UserDAO userDAO;
	@PersistenceContext
	private EntityManager entityManager;
	/****************************************************************************
	 * 功能：根据学院编号和schoolDevice对象查询设备数量
	 * 作者：贺子龙
	 * 时间：2015-09-08
	 ****************************************************************************/
	@Override
	public int findSchoolDeviceByAcademyNumberAndSchoolDevice(
			String academyNumber, SchoolDevice schoolDevice) {
		// TODO Auto-generated method stub
		String sql="select count(*) from SchoolDevice d where d.deviceNumber not in(select ld.schoolDevice.deviceNumber from LabRoomDevice ld)";
		/*if(academyNumber!=null&&!academyNumber.equals("")){
			sql+=" and d.schoolAcademy.academyNumber like '%"+academyNumber+"%'";
		}*/
		if(schoolDevice!=null){
			if(schoolDevice.getDeviceName()!=null&&!schoolDevice.getDeviceName().equals("")){//设备名称
				sql+=" and d.deviceName like '%"+schoolDevice.getDeviceName()+"%'";
			}
//			if(schoolDevice.getDeviceNumber()!=null&&!schoolDevice.getDeviceNumber().equals("")){//设备编号
//				sql+=" and d.deviceNumber ="+schoolDevice.getDeviceNumber();
//			}
			if(schoolDevice.getDeviceNumber()!=null&&!schoolDevice.getDeviceNumber().equals("")){//设备编号
				sql+=" and d.deviceNumber like '%"+schoolDevice.getDeviceNumber()+"%'";
			}
			if(schoolDevice.getDeviceAddress()!=null&&!schoolDevice.getDeviceAddress().equals("")){//设备编号
				sql+=" and d.deviceAddress like '%"+schoolDevice.getDeviceAddress()+"%'";
			}
			/*//设备保管员
			if(schoolDevice.getUserByKeepUser()!=null&&schoolDevice.getUserByKeepUser().getCname()!=null&&!schoolDevice.getUserByKeepUser().getCname().equals("")){
				sql+=" and d.userByKeepUser.cname like '%"+schoolDevice.getUserByKeepUser().getCname()+"%'";
			}*/
		}
		/*if(maxDeviceNumber!=null&&!maxDeviceNumber.equals("")){
			sql+=" and d.deviceNumber <="+maxDeviceNumber;
		}*/
//		System.out.println(sql);
		return ((Long) schoolDeviceDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	
	/****************************************************************************
	 * 功能：根据学院编号和schoolDevice对象查询设备并分页
	 * 作者：贺子龙
	 * 时间：2015-09-08
	 ****************************************************************************/
	@Override
	public List<SchoolDevice> findSchoolDeviceByAcademyNumberAndSchoolDevice(
			String academyNumber, SchoolDevice schoolDevice, Integer page,
			int pageSize) {
		// TODO Auto-generated method stub
		String sql="select d from SchoolDevice d where d.deviceNumber not in (select ld.schoolDevice.deviceNumber from LabRoomDevice ld)";
		/*if(academyNumber!=null&&!academyNumber.equals("")){
			sql+=" and d.schoolAcademy.academyNumber like '%"+academyNumber+"%'";
		}*/
		if(schoolDevice!=null){
			if(schoolDevice.getDeviceName()!=null&&!schoolDevice.getDeviceName().equals("")){//设备名称
				sql+=" and d.deviceName like '%"+schoolDevice.getDeviceName()+"%'";
			}
//			if(schoolDevice.getDeviceNumber()!=null&&!schoolDevice.getDeviceNumber().equals("")){//设备编号
//				sql+=" and d.deviceNumber ="+schoolDevice.getDeviceNumber();
//			}
			if(schoolDevice.getDeviceNumber()!=null&&!schoolDevice.getDeviceNumber().equals("")){//设备编号
				sql+=" and d.deviceNumber like '%"+schoolDevice.getDeviceNumber()+"%'";
			}
			if(schoolDevice.getDeviceAddress()!=null&&!schoolDevice.getDeviceAddress().equals("")){//设备编号
				sql+=" and d.deviceAddress like '%"+schoolDevice.getDeviceAddress()+"%'";
			}
			/*//设备保管员
			if(schoolDevice.getUserByKeepUser()!=null&&schoolDevice.getUserByKeepUser().getCname()!=null&&!schoolDevice.getUserByKeepUser().getCname().equals("")){
				sql+=" and d.userByKeepUser.cname like '%"+schoolDevice.getUserByKeepUser().getCname()+"%'";
			}*/
		}
		/*if(maxDeviceNumber!=null&&!maxDeviceNumber.equals("")){
			sql+=" and d.deviceNumber <="+maxDeviceNumber;
		}*/
//		System.out.println(sql);
		return schoolDeviceDAO.executeQuery(sql, (page-1)*pageSize,pageSize);
	}
	
	/****************************************************************************
	 * 功能：根据设备编号查询设备
	 * 作者：贺子龙
	 * 时间：2015-09-08
	 ****************************************************************************/
	@Override
	public SchoolDevice findSchoolDeviceByPrimaryKey(String deviceNumber) {
		// TODO Auto-generated method stub
		return schoolDeviceDAO.findSchoolDeviceByPrimaryKey(deviceNumber);
	}
	
	/****************************************************************************
	 * 功能：查询出所有的设备
	 * 作者：李小龙
	 * 时间：2014-07-30
	 ****************************************************************************/
	@Override
	public Set<SchoolDevice> findAllSchoolDevice() {
		// TODO Auto-generated method stub
		return schoolDeviceDAO.findAllSchoolDevices();
	}
	/****************************************************************************
	 * 功能：根据学院编号查询设备
	 * 作者：李小龙
	 ****************************************************************************/
	@Override
	public List<SchoolDevice> findSchoolDeviceByAcademyNumber(
			String academyNumber) {
		// TODO Auto-generated method stub
		String sql="select d from SchoolDevice d where d.schoolAcademy.academyNumber like '%"+academyNumber+"%'";
		return schoolDeviceDAO.executeQuery(sql, 0,-1);
	}
	/****************************************************************************
	 * 功能：根据学院编号和schoolDevice对象查询设备数量
	 * 作者：李小龙
	 ****************************************************************************/
	@Override
	public int findSchoolDeviceByAcademyNumberAndSchoolDevice(
			String academyNumber, SchoolDevice schoolDevice,String maxDeviceNumber) {
		// TODO Auto-generated method stub
		String sql="select count(*) from SchoolDevice d where d.deviceNumber not in(select ld.schoolDevice.deviceNumber from LabRoomDevice ld)";
		if(academyNumber!=null&&!academyNumber.equals("")){
			sql+=" and d.schoolAcademy.academyNumber like '%"+academyNumber+"%'";
		}
		if(schoolDevice!=null){
			if(schoolDevice.getDeviceName()!=null&&!schoolDevice.getDeviceName().equals("")){//设备名称
				sql+=" and d.deviceName like '%"+schoolDevice.getDeviceName()+"%'";
			}
			if(schoolDevice.getDeviceNumber()!=null&&!schoolDevice.getDeviceNumber().equals("")){//设备编号
				sql+=" and d.deviceNumber >='"+schoolDevice.getDeviceNumber()+"'";
			}
			//设备保管员
			if(schoolDevice.getUserByKeepUser()!=null&&schoolDevice.getUserByKeepUser().getCname()!=null&&!schoolDevice.getUserByKeepUser().getCname().equals("")){
				sql+=" and d.userByKeepUser.cname like '%"+schoolDevice.getUserByKeepUser().getCname()+"%'";
			}
		}
		if(maxDeviceNumber!=null&&!maxDeviceNumber.equals("")){
			sql+=" and d.deviceNumber <='"+maxDeviceNumber+"'";
		}
		return ((Long) schoolDeviceDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	
	
	/****************************************************************************
	 * 功能：根据学院编号和schoolDevice对象查询已添加设备数量
	 * 作者：贺子龙
	 * 时间：2015-09-22 20:57:44
	 ****************************************************************************/
	@Override
	public int findSchoolDeviceByAcademyNumberAndSchoolDeviceNew(
			String academyNumber, SchoolDevice schoolDevice,String maxDeviceNumber) {
		// TODO Auto-generated method stub
		String sql="select count(*) from SchoolDevice d where d.deviceNumber  in(select ld.schoolDevice.deviceNumber from LabRoomDevice ld)";
		if(academyNumber!=null&&!academyNumber.equals("")){
			sql+=" and d.schoolAcademy.academyNumber like '%"+academyNumber+"%'";
		}
		if(schoolDevice!=null){
			if(schoolDevice.getDeviceName()!=null&&!schoolDevice.getDeviceName().equals("")){//设备名称
				sql+=" and d.deviceName like '%"+schoolDevice.getDeviceName()+"%'";
			}
			if(schoolDevice.getDeviceNumber()!=null&&!schoolDevice.getDeviceNumber().equals("")){//设备编号
				sql+=" and d.deviceNumber >='"+schoolDevice.getDeviceNumber()+"'";
			}
			//设备保管员
			if(schoolDevice.getUserByKeepUser()!=null&&schoolDevice.getUserByKeepUser().getCname()!=null&&!schoolDevice.getUserByKeepUser().getCname().equals("")){
				sql+=" and d.userByKeepUser.cname like '%"+schoolDevice.getUserByKeepUser().getCname()+"%'";
			}
		}
		if(maxDeviceNumber!=null&&!maxDeviceNumber.equals("")){
			sql+=" and d.deviceNumber <='"+maxDeviceNumber+"'";
		}
		return ((Long) schoolDeviceDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	
	/****************************************************************************
	 * 功能：根据学院编号和schoolDevice对象查询设备并分页
	 * 作者：李小龙
	 ****************************************************************************/
	@Override
	public List<SchoolDevice> findSchoolDeviceByAcademyNumberAndSchoolDevice(
			String academyNumber, SchoolDevice schoolDevice,String maxDeviceNumber, Integer page,
			int pageSize) {
		// TODO Auto-generated method stub
		String sql="select d from SchoolDevice d where d.deviceNumber not in(select ld.schoolDevice.deviceNumber from LabRoomDevice ld)";
		if(academyNumber!=null&&!academyNumber.equals("")){
			sql+=" and d.schoolAcademy.academyNumber like '%"+academyNumber+"%'";
		}
		if(schoolDevice!=null){
			if(schoolDevice.getDeviceName()!=null&&!schoolDevice.getDeviceName().equals("")){//设备名称
				sql+=" and d.deviceName like '%"+schoolDevice.getDeviceName()+"%'";
			}
			if(schoolDevice.getDeviceNumber()!=null&&!schoolDevice.getDeviceNumber().equals("")){//设备编号
				sql+=" and d.deviceNumber >='"+schoolDevice.getDeviceNumber()+"'";
			}
			//设备保管员
			if(schoolDevice.getUserByKeepUser()!=null&&schoolDevice.getUserByKeepUser().getCname()!=null&&!schoolDevice.getUserByKeepUser().getCname().equals("")){
				sql+=" and d.userByKeepUser.cname like '%"+schoolDevice.getUserByKeepUser().getCname()+"%'";
			}
		}
		if(maxDeviceNumber!=null&&!maxDeviceNumber.equals("")){
			sql+=" and d.deviceNumber <='"+maxDeviceNumber+"'";
		}
		return schoolDeviceDAO.executeQuery(sql, (page-1)*pageSize,pageSize);
	}
	
	/****************************************************************************
	 * 功能：根据学院编号和schoolDevice对象查询已添加设备
	 * 作者：贺子龙
	 * 时间：2015-09-22 20:52:08
	 ****************************************************************************/
	@Override
	public List<SchoolDevice> findSchoolDeviceByAcademyNumberAndSchoolDeviceNew(
			String academyNumber, SchoolDevice schoolDevice,String maxDeviceNumber, Integer page,
			int pageSize) {
		// TODO Auto-generated method stub
		String sql="select d from SchoolDevice d where d.deviceNumber  in(select ld.schoolDevice.deviceNumber from LabRoomDevice ld)";
		if(academyNumber!=null&&!academyNumber.equals("")){
			sql+=" and d.schoolAcademy.academyNumber like '%"+academyNumber+"%'";
		}
		if(schoolDevice!=null){
			if(schoolDevice.getDeviceName()!=null&&!schoolDevice.getDeviceName().equals("")){//设备名称
				sql+=" and d.deviceName like '%"+schoolDevice.getDeviceName()+"%'";
			}
			if(schoolDevice.getDeviceNumber()!=null&&!schoolDevice.getDeviceNumber().equals("")){//设备编号
				sql+=" and d.deviceNumber >='"+schoolDevice.getDeviceNumber()+"'";
			}
			//设备保管员
			if(schoolDevice.getUserByKeepUser()!=null&&schoolDevice.getUserByKeepUser().getCname()!=null&&!schoolDevice.getUserByKeepUser().getCname().equals("")){
				sql+=" and d.userByKeepUser.cname like '%"+schoolDevice.getUserByKeepUser().getCname()+"%'";
			}
		}
		if(maxDeviceNumber!=null&&!maxDeviceNumber.equals("")){
			sql+=" and d.deviceNumber <='"+maxDeviceNumber+"'";
		}
		return schoolDeviceDAO.executeQuery(sql, (page-1)*pageSize,pageSize);
	}
	
	/****************************************************************************
	 * 功能：找出当前设备的关联设备
	 * 作者：贺子龙
	 ****************************************************************************/
	public List<SchoolDevice> findInnerSameDevice(String deviceNumber){
		SchoolDevice device = findSchoolDeviceByPrimaryKey(deviceNumber);
		Integer innerSame = device.getInnerSame();
		if (innerSame==null||innerSame.equals("")) {
			return null;
		}else {
			String sql = "select d from SchoolDevice d where 1=1";
			sql+=" and d.deviceNumber <> '"+ deviceNumber+"'";
			sql+=" and d.innerSame = "+ innerSame;
			List<SchoolDevice> deviceList = schoolDeviceDAO.executeQuery(sql, 0, -1);
			return deviceList;
		}
	}
	
	/****************************************************************************
	 * 功能：根据设备名称和编号查询学校设备并分页
	 * 作者：贺子龙
	 ****************************************************************************/
	public List<SchoolDevice> findSchoolDeviceByNameAndNumber(String acno, int labRoomId, String deviceName, String deviceNumber, int page, int pageSize){
		String sql="select distinct d from SchoolDevice d, LabRoomDevice ld where 1=1";
		sql+=" and ld.schoolDevice.deviceNumber = d.deviceNumber";
		// 中心
		String academyNumber="";
		SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
		if(academy!=null && academy.getAcademyNumber()!=null) {
			academyNumber = academy.getAcademyNumber();
		}else {
			academyNumber = shareService.getUser().getSchoolAcademy().getAcademyNumber();
		}
		if(academyNumber!=null&&!academyNumber.equals("")){
			sql+=" and d.schoolAcademy.academyNumber like '%"+academyNumber+"%'";
		}
		// 实验室
		if (labRoomId!=0) {
			sql+=" and ld.labRoom.id="+labRoomId;
		}
		// 设备名称
		if (deviceName!=null&&!deviceName.equals("")) {
			sql+=" and d.deviceName like '%"+deviceName+"%'";
		}
		// 设备编号
		if (deviceNumber!=null&&!deviceNumber.equals("")) {
			sql+=" and d.deviceNumber like '%"+deviceNumber+"%'";
		}
		//已经存在于某个组合中的设备不在备选设备中
		sql+=" and d.innerDeviceName is null";
		return schoolDeviceDAO.executeQuery(sql, (page-1)*pageSize,pageSize);
	}
	
	/****************************************************************************
	 * 功能：根据设备名称和编号查询学校设备数量
	 * 作者：贺子龙
	 ****************************************************************************/
	public int countSchoolDeviceByNameAndNumber(String acno, int labRoomId, String deviceName, String deviceNumber){
		String sql="select distinct count(d) from SchoolDevice d, LabRoomDevice ld where 1=1";
		sql+=" and ld.schoolDevice.deviceNumber = d.deviceNumber";
		// 中心
		String academyNumber="";
		SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
		if (academy!=null && academy.getAcademyNumber()!=null) {
			academyNumber = academy.getAcademyNumber();
		}else {
			academyNumber = shareService.getUser().getSchoolAcademy().getAcademyNumber();
		}
		if(academyNumber!=null&&!academyNumber.equals("")){
			sql+=" and d.schoolAcademy.academyNumber like '%"+academyNumber+"%'";
		}
		// 实验室
		if (labRoomId!=0) {
			sql+=" and ld.labRoom.id="+labRoomId;
		}
		// 设备名称
		if (deviceName!=null&&!deviceName.equals("")) {
			sql+=" and d.deviceName like '%"+deviceName+"%'";
		}
		// 设备编号
		if (deviceNumber!=null&&!deviceNumber.equals("")) {
			sql+=" and d.deviceNumber like '%"+deviceNumber+"%'";
		}
		//已经存在于某个组合中的设备不在备选设备中
		sql+=" and d.innerDeviceName is null";
		return ((Long) schoolDeviceDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	
	/****************************************************************************
	 * 功能：查询设备套数
	 * 作者：贺子龙
	 ****************************************************************************/
	public int maxSchoolDeviceSet(){
		String sql="select max(innerSame) from SchoolDevice d where 1=1";
		sql+=" and d.innerSame is not null";
		int maxInnerSame=0;
		if (schoolDeviceDAO.createQuerySingleResult(sql).getSingleResult()!=null) {
			maxInnerSame = ((Integer) schoolDeviceDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
		}
		return maxInnerSame;
	}
	
	/****************************************************************************
	 * 功能：保存设备
	 * 作者：贺子龙
	 ****************************************************************************/
	public void saveSchoolDevice(SchoolDevice device){
		schoolDeviceDAO.store(device);
		schoolDeviceDAO.flush();
	}
	
	/****************************************************************************
	 * 功能：根据关联设备编号查询的设备组合
	 * 作者：贺子龙
	 ****************************************************************************/
	public List<SchoolDevice> findSchoolDeviceSet(int innerSame){
		String sql="select d from SchoolDevice d where 1=1";
		sql+=" and d.innerSame ="+innerSame;
		return schoolDeviceDAO.executeQuery(sql, 0, -1);
	}
	
	/****************************************************************************
	 * 设备管理--设备使用情况报表--将设备学期使用记录set到主表中的相应字段（方便页面展示）
	 * @author 贺子龙
	 * 2016-07-21
	 ****************************************************************************/
	public void setUseToSchoolDevice(SchoolDevice device, Integer term, String termMulti){
		String deviceNumber = device.getDeviceNumber();
		// 根据设备编号和学期查询出使用情况
		// 单学期情况
		// SchoolDeviceUse deviceUse = this.findSchoolDeviceUseByNumberAndTerm(deviceNumber, term);
		// 学期多选情况
		if (!EmptyUtil.isStringEmpty(termMulti)) {
			// 初始化使用时间、次数、计费
			BigDecimal userHours =new BigDecimal(0.0);
			int userCount = 0;
			BigDecimal price =new BigDecimal(0.0);
			// 转化成数组
			String[] termArray = termMulti.split(",");
			// 遍历学期数组
			for (String termString : termArray) {
				// 获取当前学期的用量，并累加
				SchoolDeviceUse deviceUse = this.findSchoolDeviceUseByNumberAndTerm(deviceNumber, Integer.parseInt(termString));
				// 将使用情况赋值给主表，方便前台展示
				if (!EmptyUtil.isObjectEmpty(deviceUse)) {// 判空。
					userHours = userHours.add(deviceUse.getUseHours());
					userCount += deviceUse.getUseCount();
					price = price.add(deviceUse.getPrice());
				}
			}
			device.setUseHours(userHours);
			device.setUseCount(userCount);
			device.setDevicePrice(price);
			schoolDeviceDAO.store(device);
		}
	}
	/****************************************************************************
	 * 设备管理--设备使用情况报表--根据设备编号和学期唯一确定一条设备使用情况记录
	 * @author 贺子龙
	 * 2016-07-21
	 ****************************************************************************/
	public SchoolDeviceUse findSchoolDeviceUseByNumberAndTerm(String deviceNumber, int term){
		String sql = "select s from SchoolDeviceUse s where 1=1";
		sql+=" and s.term="+term;
		sql+=" and s.schoolDevice.deviceNumber like '"+deviceNumber+"'";
		List<SchoolDeviceUse> devices = schoolDeviceUseDAO.executeQuery(sql, 0, -1);
		if (devices!=null && devices.size()>0) {
			return devices.get(0);
		}else {
			return null;
		}
	}
	/****************************************************************************
	 * 功能：根据学院编号和schoolDevice对象查询设备并分页
	 * 作者：贺子龙
	 * 时间：2015-09-08
	 ****************************************************************************/
	@Override
	public List<SchoolDevice> findSchoolDeviceByAcademyNumberAndSchoolDeviceInLabRoomDevice(
			String academyNumber, SchoolDevice schoolDevice, Integer page,
			int pageSize) {
		// TODO Auto-generated method stub
		String sql="select d from SchoolDevice d where d.deviceNumber not in (select ld.schoolDevice.deviceNumber from LabRoomDevice ld)";
		/*if(academyNumber!=null&&!academyNumber.equals("")){
			sql+=" and d.schoolAcademy.academyNumber like '%"+academyNumber+"%'";
		}*/
		if(schoolDevice!=null){
			if(schoolDevice.getDeviceName()!=null&&!schoolDevice.getDeviceName().equals("")){//设备名称
				sql+=" and d.deviceName like '%"+schoolDevice.getDeviceName()+"%'";
			}
//			if(schoolDevice.getDeviceNumber()!=null&&!schoolDevice.getDeviceNumber().equals("")){//设备编号
//				sql+=" and d.deviceNumber ="+schoolDevice.getDeviceNumber();
//			}
			if(schoolDevice.getDeviceNumber()!=null&&!schoolDevice.getDeviceNumber().equals("")){//设备编号
				sql+=" and d.deviceNumber like '%"+schoolDevice.getDeviceNumber()+"%'";
			}
			if(schoolDevice.getDeviceAddress()!=null&&!schoolDevice.getDeviceAddress().equals("")){//设备编号
				sql+=" and d.deviceAddress like '%"+schoolDevice.getDeviceAddress()+"%'";
			}
			/*//设备保管员
			if(schoolDevice.getUserByKeepUser()!=null&&schoolDevice.getUserByKeepUser().getCname()!=null&&!schoolDevice.getUserByKeepUser().getCname().equals("")){
				sql+=" and d.userByKeepUser.cname like '%"+schoolDevice.getUserByKeepUser().getCname()+"%'";
			}*/
		}
		/*if(maxDeviceNumber!=null&&!maxDeviceNumber.equals("")){
			sql+=" and d.deviceNumber <="+maxDeviceNumber;
		}*/
//		System.out.println(sql);
		return schoolDeviceDAO.executeQuery(sql, (page-1)*pageSize,pageSize);
	}
	/****************************************************************************
	 * 功能：根据学院编号和schoolDevice对象查询设备数量
	 * 作者：贺子龙
	 * 时间：2015-09-08
	 ****************************************************************************/
	@Override
	public int findSchoolDeviceByAcademyNumberAndSchoolDeviceInLabRoomDevice(
			String academyNumber, SchoolDevice schoolDevice) {
		// TODO Auto-generated method stub
		String sql="select count(*) from SchoolDevice d where d.deviceNumber in(select ld.schoolDevice.deviceNumber from LabRoomDevice ld)";
		/*if(academyNumber!=null&&!academyNumber.equals("")){
			sql+=" and d.schoolAcademy.academyNumber like '%"+academyNumber+"%'";
		}*/
		if(schoolDevice!=null){
			if(schoolDevice.getDeviceName()!=null&&!schoolDevice.getDeviceName().equals("")){//设备名称
				sql+=" and d.deviceName like '%"+schoolDevice.getDeviceName()+"%'";
			}
//			if(schoolDevice.getDeviceNumber()!=null&&!schoolDevice.getDeviceNumber().equals("")){//设备编号
//				sql+=" and d.deviceNumber ="+schoolDevice.getDeviceNumber();
//			}
			if(schoolDevice.getDeviceNumber()!=null&&!schoolDevice.getDeviceNumber().equals("")){//设备编号
				sql+=" and d.deviceNumber like '%"+schoolDevice.getDeviceNumber()+"%'";
			}
			if(schoolDevice.getDeviceAddress()!=null&&!schoolDevice.getDeviceAddress().equals("")){//设备编号
				sql+=" and d.deviceAddress like '%"+schoolDevice.getDeviceAddress()+"%'";
			}
			/*//设备保管员
			if(schoolDevice.getUserByKeepUser()!=null&&schoolDevice.getUserByKeepUser().getCname()!=null&&!schoolDevice.getUserByKeepUser().getCname().equals("")){
				sql+=" and d.userByKeepUser.cname like '%"+schoolDevice.getUserByKeepUser().getCname()+"%'";
			}*/
		}
		/*if(maxDeviceNumber!=null&&!maxDeviceNumber.equals("")){
			sql+=" and d.deviceNumber <="+maxDeviceNumber;
		}*/
//		System.out.println(sql);
		return ((Long) schoolDeviceDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}

	/**
	 * Description 查询不不属于当前实验室的设备
	 * @param schoolDevice
	 * @param page
	 * @param pageSize
	 * @return
	 * @author 陈乐为 2018-9-2
	 */
	@Override
	public List<SchoolDevice> findSchoolDeviceForLab(SchoolDevice schoolDevice, Integer roomId, Integer page, int pageSize) {
		// TODO Auto-generated method stub
		String sql="select d from SchoolDevice d where d.deviceNumber not in (select ld.schoolDevice.deviceNumber from LabRoomDevice ld where ld.labRoom.id="+roomId+")";
		if(schoolDevice!=null){
			if(schoolDevice.getDeviceName()!=null&&!schoolDevice.getDeviceName().equals("")){//设备名称
				sql+=" and d.deviceName like '%"+schoolDevice.getDeviceName()+"%'";
			}
			if(schoolDevice.getDeviceNumber()!=null&&!schoolDevice.getDeviceNumber().equals("")){//设备编号
				sql+=" and d.deviceNumber like '%"+schoolDevice.getDeviceNumber()+"%'";
			}
			if(schoolDevice.getDeviceAddress()!=null&&!schoolDevice.getDeviceAddress().equals("")){//设备编号
				sql+=" and d.deviceAddress like '%"+schoolDevice.getDeviceAddress()+"%'";
			}
		}
		return schoolDeviceDAO.executeQuery(sql, (page-1)*pageSize,pageSize);
	}

	/**
	 * Description 不属于当前实验分室的所有设备数
	 * @param schoolDevice
	 * @param roomId
	 * @return
	 * @author 陈乐为 2018-9-2
	 */
	@Override
	public int findSchoolDeviceForLabCount(SchoolDevice schoolDevice, Integer roomId) {
		String sql="select count(*) from SchoolDevice d where d.deviceNumber not in(select ld.schoolDevice.deviceNumber from LabRoomDevice ld where ld.labRoom.id="+roomId+")";
		if(schoolDevice!=null){
			if(schoolDevice.getDeviceName()!=null&&!schoolDevice.getDeviceName().equals("")){//设备名称
				sql+=" and d.deviceName like '%"+schoolDevice.getDeviceName()+"%'";
			}
			if(schoolDevice.getDeviceNumber()!=null&&!schoolDevice.getDeviceNumber().equals("")){//设备编号
				sql+=" and d.deviceNumber like '%"+schoolDevice.getDeviceNumber()+"%'";
			}
			if(schoolDevice.getSystemRoom()!=null&&!schoolDevice.getSystemRoom().equals("")){//设备编号
				sql+=" and d.systemRoom like '%"+schoolDevice.getSystemRoom()+"%'";
			}
		}
		return ((Long) schoolDeviceDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	/**
	 * Description 设备列表
	 * @param schoolDevice
	 * @param page
	 * @param pageSize
	 * @return
	 * @author 黄浩 2019年3月7日
	 */
	@Override
	public List<SchoolDevice> listSchoolDevice(SchoolDevice schoolDevice, Integer page, int pageSize) {
		// TODO Auto-generated method stub
//		String sql="select d from LabRoomDevice d join d.schoolDevice u where 1=1 ";
		String sql="select d from SchoolDevice d where 1=1 ";
		if(schoolDevice!=null){
			if(schoolDevice.getDeviceName()!=null&&!schoolDevice.getDeviceName().equals("")){//设备名称
//				sql+=" and u.deviceName like '%"+schoolDevice.getDeviceName()+"%'";
				sql+=" and d.deviceName like '%"+schoolDevice.getDeviceName()+"%'";
			}
			if(schoolDevice.getDeviceNumber()!=null&&!schoolDevice.getDeviceNumber().equals("")){//设备编号
//				sql+=" and u.deviceNumber like '%"+schoolDevice.getDeviceNumber()+"%'";
				sql+=" and d.deviceNumber like '%"+schoolDevice.getDeviceNumber()+"%'";
			}
			if(schoolDevice.getDeviceAddress()!=null&&!schoolDevice.getDeviceAddress().equals("")){//设备编号
//				sql+=" and u.deviceAddress like '%"+schoolDevice.getDeviceAddress()+"%'";
				sql+=" and d.deviceAddress like '%"+schoolDevice.getDeviceAddress()+"%'";
			}
		}
		return schoolDeviceDAO.executeQuery(sql, (page-1)*pageSize,pageSize);
	}

	/**
	 * Description 设备总数
	 * @param schoolDevice
	 * @return
	 * @author 黄浩 2019年3月7日
	 */
	@Override
	public int getSchoolDeviceRecords(SchoolDevice schoolDevice) {
		// TODO Auto-generated method stub
		String sql="select count(d) from SchoolDevice d where 1=1 ";
		if(schoolDevice!=null){
			if(schoolDevice.getDeviceName()!=null&&!schoolDevice.getDeviceName().equals("")){//设备名称
				sql+=" and d.deviceName like '%"+schoolDevice.getDeviceName()+"%'";
			}
			if(schoolDevice.getDeviceNumber()!=null&&!schoolDevice.getDeviceNumber().equals("")){//设备编号
				sql+=" and d.deviceNumber like '%"+schoolDevice.getDeviceNumber()+"%'";
			}
			if(schoolDevice.getDeviceAddress()!=null&&!schoolDevice.getDeviceAddress().equals("")){//设备编号
				sql+=" and d.deviceAddress like '%"+schoolDevice.getDeviceAddress()+"%'";
			}
		}
		return  ((Long) schoolDeviceDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	/**************************************************************************************
	 * description：导入设备
	 * @author：黄浩
	 * @date：2019年3月8日
	 **************************************************************************************/
	public void importSchoolDevice(String File){
		Boolean isE2007=false;
		if(File.endsWith("xlsx")){
			isE2007=true;
		}
		//建立输入流
		try {
			//建立输入流
			InputStream input = new FileInputStream(File);
			Workbook wb =null;
			if(isE2007){
				wb=new XSSFWorkbook(input);
			}else{
				wb=new HSSFWorkbook(input);
			}
			//获取第一个表单数据
			Sheet sheet= wb.getSheetAt(0);
			//获取第一个表单迭代器
			Iterator<Row> rows=sheet.rowIterator();
			Row rowContent=null;// 表头
			String deviceNumber ="";//仪器编号
			String deviceName="";//仪器名称
			String devicePattern="";//仪器型号
			String deviceFormat ="";//仪器规格
			String deviceCountry="";//国别
			String devicePrice="";//设备价格
			String deviceSupplier="";//设备供应商
			String cname="";//领用人
			String projectSource="";//项目来源
			String keepUser ="";//保管人
			String deviceUseDirection ="";//使用方向
			String deviceBuyDate = "";//购买日期
			String deviceAddress = "";//存放地点
			String systemRoom="";//所属实训室
			String createDate="";//入账日期
			String manufacturer="";//生产厂家
			String sn="";//序列号
			String categoryMain="";//资产类别
			String categoryType="";//资产类型

			int a=0;
			while(rows.hasNext()){
				deviceNumber ="";//仪器编号
				deviceName="";//仪器名称
				devicePattern="";//仪器型号
				deviceFormat ="";//仪器规格
				deviceCountry="";//国别
				devicePrice="";//设备价格
				deviceSupplier="";//设备供应商
				cname="";//领用人
				projectSource="";//项目来源
				keepUser ="";//保管人
				deviceUseDirection ="";//使用方向
				deviceBuyDate = "";//购买日期
				deviceAddress = "";//存放地点
				systemRoom="";//所属实训室
				createDate="";//入账日期
				manufacturer="";//生产厂家
				sn="";//序列号
				categoryMain="";//资产类别
				categoryType="";//资产类型
				if(a==0){
					rowContent=rows.next();
					a=1;
				}
				Row row =rows.next();
				int column=sheet.getRow(0).getPhysicalNumberOfCells();
				//chName ="";//品名
				for(int k=0;k<column;k++){
					if(row.getCell(k)!=null){
						row.getCell(k).setCellType(Cell.CELL_TYPE_STRING);
						String columnName = rowContent.getCell(k).getStringCellValue();
						String content = row.getCell(k).getStringCellValue();
						if(columnName.contains("设备编号")){
							deviceNumber = content;
						}
						if(columnName.contains("设备名称")){
							deviceName = content;
						}
						if(columnName.equals("设备型号")){
							devicePattern = content;
						}
						if (columnName.equals("设备规格")) {
							deviceFormat = content;
						}
						if (columnName.equals("使用方向")) {
							deviceUseDirection = content;
						}
						if (columnName.equals("购买日期")) {
							deviceBuyDate = content;
						}
						if (columnName.equals("存放地点")) {
							deviceAddress = content;
						}
						if (columnName.contains("所属实训室")) {
							systemRoom = content;
						}
						if (columnName.equals("国别")) {
							deviceCountry = content;
						}
						if (columnName.contains("价格")) {
							devicePrice = content;
						}
						if (columnName.equals("设备入账日期*")) {
							deviceBuyDate = content;
						}
						if(columnName.equals("设备供应商")){
							deviceSupplier = content;
						}
						if(columnName.contains("领用人")){
							cname = content;
						}
						if (columnName.contains("保管人")) {
							keepUser = content;
						}
						if (columnName.equals("项目来源")) {
							projectSource = content;
						}
						if (columnName.equals("生产厂家")) {
							manufacturer = content;
						}
						if (columnName.equals("序列号")) {
							sn = content;
						}
						if (columnName.equals("资产类别")) {
							categoryMain = content;
						}
						if (columnName.equals("资产类型")) {
							categoryType = content;
						}
					}
				}
				if(!deviceNumber.equals("")){
					Calendar c = new GregorianCalendar(1900,0,-1);
					SchoolDevice schoolDevice = schoolDeviceDAO.findSchoolDeviceByDeviceNumber(deviceNumber);
					//不存在该设备时才导入
					if(schoolDevice == null){
						SchoolDevice s = new SchoolDevice();
						s.setDeviceNumber(deviceNumber);
						if(!deviceName.equals("")){
							s.setDeviceName(deviceName);
						}
						if(!devicePattern.equals("") ){
							s.setDevicePattern(devicePattern);
						}
						if(!deviceFormat.equals("") ){
							s.setDeviceFormat(deviceFormat);
						}
						if(!deviceUseDirection.equals("") ){
							s.setDeviceUseDirection(deviceUseDirection);
						}
						if(!deviceBuyDate.equals("") ){
							Date d = c.getTime();
							Date _d = DateUtils.addDays(d, Integer.valueOf(deviceBuyDate)); //距离1900年1月1日的天数
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(_d);
							s.setDeviceBuyDate(calendar);
						}
						if(!deviceAddress.equals("") ){
							s.setDeviceAddress(deviceAddress);
						}
						if(!systemRoom.equals("") ){
							s.setSystemRoom(systemRoom);
						}
						if(!createDate.equals("") ){
							Date date = c.getTime();
							Date _date = DateUtils.addDays(date, Integer.valueOf(createDate)); //距离1900年1月1日的天数
							Calendar calendar1 = Calendar.getInstance();
							calendar1.setTime(_date);
							s.setCreatedAt(calendar1);
						}
						if(!deviceCountry.equals("")){
							s.setDeviceCountry(deviceCountry);
						}
						if(!devicePrice.equals("") ){
							Float price = Float.valueOf(devicePrice);
							s.setDevicePrice(new BigDecimal(price));
						}
						if(!deviceSupplier.equals("")){
							s.setDeviceSupplier(deviceSupplier);
						}
						if(!cname.equals("")){
							String sql = "select u from User u where u.cname = '"+cname+"'";
							List<User> user = userDAO.executeQuery(sql);
							if (user.size()>0){
								s.setUserByUserNumber(user.get(0));
							}
						}
						if(!projectSource.equals("")){
							s.setProjectSource(projectSource);
						}
						if(!keepUser.equals("")){
							String sql1 = "select u from User u where u.cname = '"+keepUser+"'";
							List<User> keepU = userDAO.executeQuery(sql1);
							if (keepU.size()>0){
								s.setUserByKeepUser(keepU.get(0));
							}
						}
						if(!manufacturer.equals("")){
							s.setManufacturer(manufacturer);
						}
						if(!sn.equals("")){
							s.setSn(sn);
						}
						if(!categoryMain.equals("")){
							s.setCategoryMain(categoryMain);
						}
						if(!categoryType.equals("")){
							s.setCategoryType(categoryType);
						}
						//获取id最大值
						String  sql = "select max(id) from school_device ";
						Integer id = Integer.valueOf(entityManager.createNativeQuery(sql).getSingleResult().toString());
						s.setId(id+1);
						schoolDeviceDAO.store(s);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
