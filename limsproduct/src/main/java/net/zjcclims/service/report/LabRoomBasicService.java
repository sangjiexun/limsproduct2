package net.zjcclims.service.report;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.zjcclims.constant.MonthReport;
import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.LabWorker;
import net.zjcclims.domain.SchoolCourseDetail;
import net.zjcclims.domain.TimetableAppointment;
import net.zjcclims.domain.TimetableLabRelated;

import org.springframework.transaction.annotation.Transactional;

public interface LabRoomBasicService{
	/****************************************************************
	 * @功能：获取LabRoom列表
	 * @作者：陈乐为
	 * @日期：2016-01-06
	 ****************************************************************/
	public List<LabRoom> getList();
	
	/****************************************************************
	 * @功能：获取LabRoom列表记录数
	 * @作者：陈乐为
	 * @日期：2016-01-06
	 ****************************************************************/
	public int Count();
	
	/****************************************************************
	 * @功能：查看所有的实验室信息列表
	 * @作者：陈乐为
	 * @日期：2016-01-06
	 ****************************************************************/
	public List<LabRoom> findAllLabRoomBasic(Integer currpage, Integer pageSize, LabRoom labRoom);
	
	
	public List<LabRoom> getList(int startResult, int maxRows);
	
	/****************************************************************
	 * @内容：根据实验中心查询
	 * @作者：陈乐为
	 * @日期：2016-01-07
	 ****************************************************************/
	public List<LabRoom> findLabRoomByQuery(LabRoom labRoom, Integer labCenterid, Integer currpage, Integer pageSize);
	
	/*************************************************************************************
	 * @內容：根据实验中心查询所得数据条数
	 * @作者： 陈乐为
	 * @日期：2016-01-07
	 *************************************************************************************/
	public Integer findAllLabRoomByQuery(LabRoom labRoom,Integer labCenterId);
	
}



