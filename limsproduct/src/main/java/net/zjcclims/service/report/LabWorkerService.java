package net.zjcclims.service.report;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zjcclims.constant.MonthReport;
import net.zjcclims.domain.LabWorker;
import net.zjcclims.domain.SchoolCourseDetail;
import net.zjcclims.domain.TimetableAppointment;
import net.zjcclims.domain.TimetableLabRelated;

import org.springframework.transaction.annotation.Transactional;

public interface LabWorkerService{
	/****************************************************************
	 * @功能：获取LabWorker列表
	 * @作者：陈乐为
	 * @日期：2016-01-06
	 ****************************************************************/
	public List<LabWorker> getList();
	
	/****************************************************************
	 * @功能：获取LabWorker列表记录数
	 * @作者：陈乐为
	 * @日期：2016-01-06
	 ****************************************************************/
	public int Count();
	
	/****************************************************************
	 * @功能：查看所有的人员信息列表
	 * @作者：陈乐为
	 * @日期：2016-01-06
	 ****************************************************************/
	public List<LabWorker> findAllLabWorker(Integer currpage, Integer pageSize, LabWorker labWorker);
	
	
	public List<LabWorker> getList(int startResult, int maxRows);
	
	 /*************************************************************************************
	   * @內容：专任实验室人员列表导出Excel
	   * @作者：陈乐为
	   * @日期：2016.03.23
	   *************************************************************************************/
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	  public void exportLabWorker(HttpServletRequest request, HttpServletResponse response) throws Exception; 
	
}