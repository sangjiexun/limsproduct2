package net.zjcclims.service.report;

import java.math.BigDecimal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zjcclims.domain.LabCenter;
import net.zjcclims.dao.LabRoomDAO;
import net.zjcclims.dao.LabWorkerDAO;
import net.zjcclims.dao.OperationItemDAO;
import net.zjcclims.domain.LabWorker;
import net.zjcclims.domain.OperationItem;
import net.zjcclims.domain.SchoolTerm;
import net.zjcclims.domain.LabRoom;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.service.project.ProjectSummaryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import excelTools.ExcelUtils;
import excelTools.JsGridReportBase;
import excelTools.TableData;

@Service("LabWorkerService")
@Transactional
public class LabWorkerServiceImpl implements LabWorkerService{
	@Autowired private ShareService shareService;
	@Autowired private LabRoomService labRoomService;
	@Autowired private LabWorkerDAO labWorkerDAO;
	
	/****************************************************************
	 * @功能：获取LabWorker列表记录数
	 * @作者：陈乐为
	 * @日期：2016-01-06
	 ****************************************************************/
	@Override
	public int Count() {
		int RecordCount=this.getList().size();		
		return RecordCount;
	}
	
	/****************************************************************
	 * @功能：获取LabWorker列表
	 * @作者：陈乐为
	 * @日期：2016-01-06
	 ****************************************************************/
	public List<LabWorker> getList() {
		List<LabWorker> list = getList(0,-1);
		return list;
	}
	
	/*********************************************************************
	 * @功能：查看所有的人员信息列表
	 * @作者：陈乐为
	 * @日期：2016-01-06
	 *********************************************************************/
	@Override
	public List<LabWorker> findAllLabWorker(Integer currpage, Integer pageSize, LabWorker labWorker) {
		StringBuffer hql = new StringBuffer("select i from LabWorker i where 1=1");
		return labWorkerDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}
	
	//返回指定分页的记录列表
	public List<LabWorker> getList(int startResult, int maxRows){
		String hql="select a from LabWorker a where 1=1";                                                                   //my,新增
		List<LabWorker> list=labWorkerDAO.executeQuery(hql,startResult,maxRows);           //my（-1，-1）
		return list;
	}
	
	  /*************************************************************************************
	   * @內容：专任实验室人员列表导出Excel
	   * @作者：陈乐为
	   * @日期：2016.03.23
	   *************************************************************************************/
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	  @Override
	  public void exportLabWorker(HttpServletRequest request, HttpServletResponse response) throws Exception 
	  {
	    List<Map> list = new ArrayList<Map>();
	    List<LabWorker> reportLabWorkers = getLabWorker(); //
	    //实验中心所在学院的实验室项目
	    int i=1;
	      for (LabWorker reportLabWorker : reportLabWorkers) 
	      {
	        Map map = new HashMap();
	        
	        map.put("serial number",i);//序号
	        i++;
//	        if(reportLabWorker.getUser() != null && reportLabWorker.getUser().getCname() != null){
//	        	map.put("name",reportLabWorker.getUser().getCname());//姓名
//	        }
			if(reportLabWorker.getLwName() != null) {
				map.put("name", reportLabWorker.getLwName());//姓名
			}
	        if(reportLabWorker.getLwSex() != null){
	        	if(reportLabWorker.getLwSex().equals("1")){
	        		map.put("sex","男");//性别
	        	}else{
	        		map.put("sex","女");//性别
	        	}
	        }
	        if(reportLabWorker.getLwBirthday() != null){
	        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	        	map.put("birth", sdf.format(reportLabWorker.getLwBirthday().getTime()));
	        }
	        if(reportLabWorker.getCDictionaryByLwAcademicDegree() != null){
	        	map.put("academicDegree", reportLabWorker.getCDictionaryByLwAcademicDegree().getCName());
	        }
	        if(reportLabWorker.getCDictionaryByLwDegree() != null){
	        	map.put("degree", reportLabWorker.getCDictionaryByLwDegree().getCName());
	        }
	        if(reportLabWorker.getLwProfessionSpecialty() != null){
	        	map.put("specialty", reportLabWorker.getLwProfessionSpecialty());
	        }
	        if(reportLabWorker.getLwDuty() != null){
	        	map.put("duty", reportLabWorker.getLwDuty());
	        }
	        if(reportLabWorker.getCDictionaryByLwCategoryStaff() != null){
	        	map.put("categoryStaff", reportLabWorker.getCDictionaryByLwCategoryStaff().getCName());
	        }
	        if(reportLabWorker.getEmployer() != null){
	        	map.put("employer", reportLabWorker.getEmployer());
	        }
	        list.add(map);
	      }  //实验室遍历
	      String title = "专任实验室人员表";
	        String[] hearders = new String[] {"序号","姓名","性别","出生年月","学历","学位","专业技术职务","承担任务","专职/兼职","单位"};//表头数组
	        String[] fields = new String[] {"serial number","name", "sex",  "birth","academicDegree", "degree", "specialty", "duty", 
	            "categoryStaff", "employer"};
	        TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders),fields);
	        JsGridReportBase report = new JsGridReportBase(request, response);
	        report.exportExcel(title,  shareService.getUserDetail().getCname(), "专任实验室人员表", td);  
	  }
	  
	  /*********************************************************************
	   * @内容：获取所有的项目信息列表
	   * @作者：陈乐为
	   * @日期：2016-01-05
	   *********************************************************************/
	  public List<LabWorker> getLabWorker() {
	    String sql = "select i from LabWorker i where 1=1";
	    return labWorkerDAO.executeQuery(sql.toString(), 0, -1);
	  }
}
