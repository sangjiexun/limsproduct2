package net.zjcclims.service.performance;

import net.zjcclims.domain.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public interface PerformanceService {
	/****************************************************************************
	 * 功能：查找个人绩效
	 * 作者：廖文辉
	 * 时间：2018/9/19
	 ****************************************************************************/
	public List<IndividualPerformance> findPerformanceCount(IndividualPerformance individualPerformance, int type);

	/****************************************************************************
	 * 功能：查找个人绩效(分页）
	 * 作者：廖文辉
	 * 时间：2018/9/19
	 ****************************************************************************/
	public List<IndividualPerformance> findPerformance(IndividualPerformance individualPerformance, int currpage,
													   int pageSize, int type);

	/****************************************************************************
	 * 功能：上传个人绩效文档
	 * 作者：廖文辉
	 ****************************************************************************/
	public void performanceDocumentUpload(HttpServletRequest request,
										  HttpServletResponse response, Integer id,int type);
	/****************************************************************************
	 * 功能：保存个人绩效的文档
	 * 作者：李小龙
	 * 时间：2014-07-29
	 ****************************************************************************/
	public void saveLabRoomDocument(String fileTrueName, Integer id,Integer type);
	/****************************************************************************
	 * 功能：查找课程
	 * 作者：廖文辉
	 * 时间：2018-09-26
	 ****************************************************************************/
	public   List<SchoolCourseInfo>  getSchoolCourseInfoList();
	/****************************************************************************
	 * 功能：查找专业
	 * 作者：廖文辉
	 * 时间：2018-09-26
	 ****************************************************************************/
	public List<SchoolMajor> getSchoolMajorList();
	/****************************************************************************
	 * 功能：查找属于所选学院的老师
	 * 作者：廖文辉
	 * 时间：2018-09-28
	 ****************************************************************************/
	public List<User> findTeacheresBySchoolAcademy(String acno);
}