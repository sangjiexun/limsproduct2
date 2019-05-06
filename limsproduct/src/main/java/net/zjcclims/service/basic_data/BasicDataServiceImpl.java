package net.zjcclims.service.basic_data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*import net.zjcclims.dao.AssetManageDAO;*/
import net.zjcclims.dao.SchoolDeviceChangeReportDAO;
import net.zjcclims.dao.SchoolYearDAO;
/*import net.zjcclims.domain.AssetManage;*/
import net.zjcclims.domain.SchoolDeviceChangeReport;
import net.zjcclims.domain.SchoolYear;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.ShareService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import excelTools.ExcelUtils;
import excelTools.JsGridReportBase;
import excelTools.TableData;
import flex.messaging.io.ArrayList;
import flex.messaging.util.URLEncoder;

@Service("BasicDataService")
public class BasicDataServiceImpl implements BasicDataService {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private SchoolYearDAO schoolYearDAO;
	@Autowired
	private ShareService shareService;
	@Autowired
	private SchoolDeviceChangeReportDAO schoolDeviceChangeReportDAO;
	/*@Autowired
	private AssetManageDAO assetManageDAO;*/
	/*************************************************************************************
	 * Description：报表导出txt{下载文件}
	 * 
	 * @author：岳茜
	 * @date：2016-8-2
	 *************************************************************************************/
	public void downLoad(File tempFile, String fileName,HttpServletResponse response) {
		response.setContentType("application/txt;charset=UTF-8");
		
                FileInputStream in = null;
		OutputStream o = null;
		try {
			byte b[] = new byte[1024];
			in = new FileInputStream(tempFile);
			o = response.getOutputStream();
			response.setContentType("application/x-doc");
                        // 指定下载的文件名
			/*response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName + ".txt", "UTF-8"));*/
			String as = fileName+".txt";
			response.setHeader("Content-disposition", "attachment; filename=" + as);
            response.setHeader("Content_Length", String.valueOf(tempFile.length())); // 设置头文件的长度为指定文件的长度

            int n = 0;
			while ((n = in.read(b)) != -1) {
                o.write(b, 0, n); // 将数组 b 中的  n 个字节按顺序写入输出流
			}
        } catch (Exception e) { // 当try语句中出现异常是时，会执行catch中的语句
            e.printStackTrace(); // 在命令行打印异常信息在程序中出错的位置及原因
        } finally { // 执行顺序：try--Exception--finally   一般finally写的代码就是流的关闭语句
			try {
                in.close(); // 关闭此文件输入流并释放与该流相关的所有系统资源。如果该流具有相关联的信道，则信道被关闭为好。
                
                o.flush(); // 刷新此输出流
                o.close(); // 关闭此输出流
            } catch (IOException e) { // 捕捉的是输入输出异常
                e.printStackTrace(); // 在命令行打印异常信息在程序中出错的位置及原因
			}
		}
	}
	/*************************************************************************************
	 * Description：报表导出txt{生成文件}
	 * 
	 * @author：岳茜
	 * @date：2016-8-2
	 *************************************************************************************/
	public void contentToTxt(File tempFile, String content) {
		try {
			if (!tempFile.exists()) {
				tempFile.createNewFile();// 不存在则创建
			}
			OutputStreamWriter write = new OutputStreamWriter(
					new FileOutputStream(tempFile), "gbk");
			BufferedWriter writer = new BufferedWriter(write);
			writer.write(content);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*************************************************************************************
	 * Description：基表模块-学年列表{获取学年的总记录数}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 *************************************************************************************/
	@Transactional
	public int getYearTotalRecords() {
		Set<SchoolYear> years = schoolYearDAO.findAllSchoolYears();
		return years.size();
	}

	/************************************************************
	 * Description：基表模块-学年列表{获取所有学年}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 ************************************************************/
	public Map<Integer, String> findAllSchoolYearMap() {
		String sql = "select y from SchoolYear y order by id desc";
		List<SchoolYear> yearList = schoolYearDAO.executeQuery(sql, -1, 0);
		Map<Integer, String> schoolYearMap = new HashMap<Integer, String>();
		for (SchoolYear schoolYear : yearList) {
			schoolYearMap.put(schoolYear.getCode(), schoolYear.getYearName());
		}
		return schoolYearMap;
	}

	/*************************************************************************************
	 * Description：基表模块-学年列表{查找所有的学年信息}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 *************************************************************************************/
	public Set<SchoolYear> findAllYears(int curr, int size) {
		return schoolYearDAO.findAllSchoolYears(curr * size, size);
	}

	/*************************************************************************************
	 * Description：基表模块-学年列表{根据学年的id查找学年}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 *************************************************************************************/
	public SchoolYear findYearById(Integer id) {
		SchoolYear schoolYear = schoolYearDAO.findSchoolYearById(id);
		return schoolYear;
	}

	/************************************************************
	 * Description：基表模块-学年列表{删除学年}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 ************************************************************/
	public void deleteYear(SchoolYear sy) {
		schoolYearDAO.remove(sy);
		schoolYearDAO.flush();
	}

	/************************************************************
	 * Description：基表模块-学年列表{保存学年}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 ************************************************************/
	public void saveYear(SchoolYear sy) {
		schoolYearDAO.store(sy);
		schoolYearDAO.flush();
	}

	/*************************************************************************************
	 * Description：基表模块-学年列表{根据学年的名称查找学年}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 *************************************************************************************/
	public Set<SchoolYear> findYearsByYearName(String yearName) {
		return schoolYearDAO.findSchoolYearByYearName(yearName);
	}

	/*********************************************************************************
	 * Description：基表模块-{查出教学科研仪器设备表里面的所有设备 school_device_report}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 ************************************************************************************/
	@SuppressWarnings({ "unchecked" })
	public List<Object[]> schoolDeviceReport(HttpServletRequest request, int page,
			int pageSize) {
		String sql = "select * from basic_data_one s where 1=1";
		// 学年查询
		if (request.getParameter("yearCodeForTxt") != null && request.getParameter("yearCodeForTxt") != "") {
			sql += " and s.year_code like '" + request.getParameter("yearCodeForTxt")+ "' ";
		}
		javax.persistence.Query query = entityManager.createNativeQuery(sql);
		// 以下两行是分页设置
		query.setMaxResults(pageSize);
		query.setFirstResult((page - 1) * pageSize);
		// 获取list对象
		List<Object[]> list = query.getResultList();
		return list;
	}

	
	
	/*********************************************************************************
	 * Description：基表模块-{导出excel，教学科研仪器设备表里面的所有设备 school_device_report}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 ************************************************************************************/
	public void exportReport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Map> list = new ArrayList();
		List<Object[]> reports = schoolDeviceReport(request, 1, 1000000);

		for (Object[] s : reports) {
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("schoolNumber", s[0]);// 学校编号
			map.put("deviceNumber", s[1]);// 仪器编号
			map.put("deviceUdc", s[2]);// 分类号
			map.put("deviceName", s[3]);// 仪器名称
			map.put("devicePattern", s[4]);// 型号
			map.put("deviceFormat", s[5]);// 规格
			map.put("deviceOrigin", s[6]);// 仪器来源
			map.put("deviceCountry", s[7]);// 国别码
			map.put("devicePrice", s[8]);// 单价
			map.put("deviceBuyDate", s[9]);// 购置日期
			map.put("deviceCondition", s[10]);// 现状码
			map.put("useDirection", s[11]);// 使用方向
			map.put("academyNumber", s[12]);// 单位编号
			map.put("academyName", s[13]);// 单位名称
			map.put("scrapTime", s[14]);// 报废日期

			list.add(map);
		}
		String title = "sj112712";
		String[] hearders = new String[] { "学校编号", "仪器编号", "分类号", "仪器名称", "型号",
				"规格", "仪器来源", "国别码", "单价", "购置日期", "现状码", "使用方向", "单位编号",
				"单位名称", "报废日期" };// 表头数组
		String[] fields = new String[] { "schoolNumber", "deviceNumber",
				"deviceUdc", "deviceName", "devicePattern", "deviceFormat",
				"deviceOrigin", "deviceCountry", "devicePrice",
				"deviceBuyDate", "deviceCondition", "useDirection",
				"academyNumber", "academyName", "scrapTime" };// Financialresources对象属性数组

		TableData td = ExcelUtils.createTableData(list,
				ExcelUtils.createTableHeader(hearders), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportToExcel(title, shareService.getUser().getCname(), td);// title未表头名，中间的参数为获取当前登陆用户的用户名后传入创建人，td即当前表的生成时间。效果如下

	}

	/*********************************************************************************
	 * Description：基表模块-{导出txt，教学科研仪器设备表里面的所有设备 school_device_report}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 ************************************************************************************/
	public void newExportTecEquipAVE(List<Object[]> listEquipments,
			File tempFile, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 设定输出字节流内容
		String content = "";
		// 设定时间格式
		@SuppressWarnings("unused")
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		List<Object[]> reports = schoolDeviceReport(request, 1, 50);
		for (Object[] s : reports) {

			content += "12712"// 学校代码
					+ formatStr(s[1].toString().trim(), 8)// 仪器编号
					+ formatStr(s[2].toString().trim(), 8)// 分类号
					+ formatStr(s[3].toString().trim(), 30) // 仪器名称
					+ formatStr(s[4].toString().trim(), 20)// 型号
					+ formatStr(s[5].toString().trim(), 30)// 规格
					+ s[6]// 仪器来源
					+ s[7]// 国别码
					+ formatStr(s[8].toString().trim(), 12)// 单价
					+ formatStr(s[9].toString().trim(), 6)// 购置日期
					+ formatStr(s[10].toString().trim(), 2)// 现状码
					+ s[11]// 使用方向
					+ formatStr(s[12].toString().trim(), 10)// 单位编号
					+ formatStr(s[13].toString().trim(), 50)// 单位名称
					+ formatStr(s[14].toString().trim(), 6)// 报废日期
					+ "\r\n";
		}
		contentToTxt(tempFile, content);
		downLoad(tempFile, "SJ112712", response);
	}

	/*********************************************************************************
	 * Description：基表模块-{导出txt，格式化txt报表字符串输出}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 ************************************************************************************/
	public String formatStr(String str, int length) {
		// 获取需要格式化的字符串字节长度
		int strLength = str.getBytes().length;
		// 比较长度是否符合要求
		if (strLength < length) {
			int temp = length - strLength;
			for (int i = 0; i < temp; i++) {
				// 长度不够时往后加空格填充值规定长度
				str += " ";
			}
		}
		return str;
	}

	/********************************************************************************
	 * Description：基表模块-{查出教学科研仪器设备--count}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 *********************************************************************************/
	public int schoolDeviceReportCount(HttpServletRequest request) {
		String sql = "select count(*) from basic_data_one ";
		// 以下两行是分页设置
		javax.persistence.Query query = entityManager.createNativeQuery(sql);
		// 获取对象条数
		int count = ((BigInteger) ((javax.persistence.Query) query)
				.getSingleResult()).intValue();
		return count;
	}

	/*********************************************************************************
	 * Description：基表模块-{查出教学科研仪器设备表里面的贵重仪器设备 school_device_value 单价大于400000}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 ************************************************************************************/
	@SuppressWarnings({ "unchecked" })
	public List<Object[]> schoolDeviceValue(HttpServletRequest request, int page,
			int pageSize) {
		String sql = "select * from basic_data_three v  where 1=1";
		// 学年查询
		if (request.getParameter("yearCodeForTxt") != null
				&& request.getParameter("yearCodeForTxt") != "") {
			sql += " and v.year_code like '" + request.getParameter("yearCodeForTxt")
					+ "' ";
		}
		javax.persistence.Query query = entityManager.createNativeQuery(sql);
		// 以下两行是分页设置
		query.setMaxResults(pageSize);
		query.setFirstResult((page - 1) * pageSize);
		// 获取list对象
		List<Object[]> list = query.getResultList();
		return list;
	}

	/**************************************
	 * Description：基表模块-{查出教学科研仪器设备中的贵重仪器设备--count}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 **************************************/
	public int schoolDeviceValueCount(HttpServletRequest request) {
		String sql = "select count(*) from basic_data_three ";
		// 以下两行是分页设置
		javax.persistence.Query query = entityManager.createNativeQuery(sql);
		// 获取对象条数
		int count = ((BigInteger) ((javax.persistence.Query) query)
				.getSingleResult()).intValue();
		return count;
	}
	
	/*********************************************************************************
	 * Description：基表模块-{导出excel，教学科研仪器设备表贵重仪器设备 school_device_value}
	 * 
	 * @author：魏来
	 * @date：2016-8-24
	 ************************************************************************************/
	@SuppressWarnings("unchecked")
	public void exportSchoolDeviceValue(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		@SuppressWarnings("rawtypes")
		List<Map> list = new ArrayList();
		List<Object[]> reports = schoolDeviceValue(request, 1, 50);
		for (Object[]s : reports) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("schoolNumber", s[0]);
			map.put("deviceNumber", s[1]);
			map.put("deviceUdc", s[2]);
			map.put("deviceName", s[3]);
			map.put("devicePrice", s[4]);
			map.put("devicePattern", s[5]);
			map.put("deviceFormat", s[6]); 
			map.put("useHoursTeaching", s[7]);
			map.put("useHoursResearch", s[8]);
			map.put("useHoursService", s[9]);
			map.put("useHoursOpen", s[10]);
			map.put("sampleCount", s[11]);
			map.put("trainNumberTeacher", s[12]);
			map.put("trainNumberStudent", s[13]);
			map.put("trainNumberOther", s[14]);
			map.put("itemTeaching", s[15]);
			map.put("itemResearch", s[16]);
			map.put("itemSocial", s[17]);
			map.put("prizeNation", s[18]); 
			map.put("prizeProvince", s[19]);
			map.put("patentTeacher", s[20]);
			map.put("paperSci", s[21]);
			map.put("paperKey", s[22]);
			map.put("manager", s[23]);
			map.put("manager", s[24]);
			list.add(map);
		}
		String title = "sj312712";
		String[] hearders = new String[] { "学校代码", "设备编号", "分类号", "仪器名称", "单价",
				"型号", "规格", "使用机时教学", "使用机时科研", "使用机时社会服务", "使用机时开放使用", "测样数", "培训学生数",
				"培训教师数", "培训人员数" , "教学实验项目数", "科研项目数"
				, "社会服务项目数", "获奖情况国家级", "获奖情况省部级", "发明专利教师"
				, "发明专利教师", "论文情况三大检索", "论文情况核心刊物", 
				"负责人姓名"};// 表头数组
		String[] fields = new String[] { "schoolNumber", "deviceNumber",
				"deviceUdc", "deviceName", "devicePrice", "devicePattern",
				"deviceFormat", "useHoursTeaching", "useHoursResearch",
				"useHoursService", "useHoursOpen", "sampleCount",
				"trainNumberTeacher", "trainNumberStudent", "trainNumberOther" , 
				"itemTeaching" , "itemResearch" 
				, "itemSocial" , "prizeNation" , "prizeProvince" 
				, "patentTeacher" , "patentStudent" , "paperSci", "paperKey" 
				, "manager" };//对象属性数组

		TableData td = ExcelUtils.createTableData(list,
				ExcelUtils.createTableHeader(hearders), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportToExcel(title, shareService.getUser().getCname(), td);// title未表头名，中间的参数为获取当前登陆用户的用户名后传入创建人，td即当前表的生成时间。效果如下
	}
	
	/*********************************************************************************
	 * Description：基表模块-{导出txt，教学科研仪器设备表贵重仪器设备 school_device_value}
	 * 
	 * @author：魏来
	 * @date：2016-8-24
	 ************************************************************************************/
	public void newSchoolDeviceValueTecEquipAVE(List<Object[]> listEquipments,
			File tempFile, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 设定输出字节流内容
		String content = "";
		// 设定时间格式
		@SuppressWarnings("unused")
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		List<Object[]> reports = schoolDeviceValue(request, 1, 50);
		for (Object[] s : reports) {
			String cell1,cell2,cell3,cell4,cell5,cell6,cell7,cell8,cell9,cell10,cell11,cell12,cell13,cell14,cell15,cell16,cell17,cell18,cell19,cell20,cell21,cell22,cell23,cell24;
			if(s[1]!=null ){
				cell1 = formatStr(s[1].toString().trim(), 8);
			}else{
				cell1 = formatStr("*", 8);
			}
			if(s[2]!=null ){
				cell2 = formatStr(s[2].toString().trim(),8);
			}else{
				cell2 = formatStr("*",8);
			}
			if(s[3]!=null ){
				cell3 = formatStr(s[3].toString().trim(), 30);
			}else{
				cell3 = formatStr("*", 30);
			}
			if(s[4]!=null ){
				cell4 = formatStr(s[4].toString().trim(), 12);
			}else{
				cell4 = formatStr("*", 12);
			}
			if(s[5]!=null ){
				cell5 = formatStr(s[5].toString().trim(), 20);
			}else{
				cell5 = formatStr("*", 20);
			}
			if(s[6]!=null ){
				cell6 = formatStr(s[6].toString().trim(), 200);
			}else{
				cell6 = formatStr("*", 200);
			}
			if(s[7]!=null ){
				cell7 = formatStr(s[7].toString().trim(), 4);
			}else{
				cell7 = formatStr("0", 4);
			}
			if(s[8]!=null ){
				cell8 = formatStr(s[8].toString().trim(), 4);
			}else{
				cell8 = formatStr("0", 4);
			}
			if(s[9]!=null ){
				cell9 = formatStr(s[9].toString().trim(), 4);
			}else{
				cell9 = formatStr("0", 4);
			}
			if(s[10]!=null ){
				cell10 = formatStr(s[10].toString().trim(), 4);
			}else{
				cell10 = formatStr("*", 4);
			}
			if(s[11]!=null ){
				cell11 = formatStr(s[11].toString().trim(),6);
			}else{
				cell11 = formatStr("*", 6);
			}
			if(s[12]!=null ){
				cell12 = formatStr(s[12].toString().trim(), 4);
			}else{
				cell12 = formatStr("*", 4);
			}
			if(s[13]!=null ){
				cell13 = formatStr(s[13].toString().trim(), 4);
			}else{
				cell13 = formatStr("*", 4);
			}
			if(s[14]!=null ){
				cell14 = formatStr(s[14].toString().trim(), 4);
			}else{
				cell14 = formatStr("*", 4);
			}
			if(s[15]!=null ){
				cell15 = formatStr(s[15].toString().trim(), 3);
			}else{
				cell15 = formatStr("*", 3);
			}
			if(s[16]!=null ){
				cell16 = formatStr(s[16].toString().trim(), 3);
			}else{
				cell16 = formatStr("*", 3);
			}
			if(s[17]!=null ){
				cell17 = formatStr(s[17].toString().trim(), 4);
			}else{
				cell17 = formatStr("*", 4);
			}
			if(s[18]!=null ){
				cell18 = formatStr(s[18].toString().trim(), 2);
			}else{
				cell18 = formatStr("*", 2);
			}
			if(s[19]!=null ){
				cell19 = formatStr(s[19].toString().trim(), 2);
			}else{
				cell19 = formatStr("*", 2);
			}
			if(s[20]!=null ){
				cell20 = formatStr(s[20].toString().trim(), 2);
			}else{
				cell20 = formatStr("*", 2);
			}
			if(s[21]!=null ){
				cell21 = formatStr(s[21].toString().trim(), 2);
			}else{
				cell21 = formatStr("*", 2);
			}
			if(s[22]!=null ){
				cell22 = formatStr(s[22].toString().trim(), 3);
			}else{
				cell22 = formatStr("*", 3);
			}
			if(s[23]!=null ){
				cell23 = formatStr(s[23].toString().trim(), 3);
			}else{
				cell23 = formatStr("*", 3);
			}
			if(s[24]!=null ){
				cell24 = formatStr(s[24].toString().trim(), 8);
			}else{
				cell24 = formatStr("无", 8);
			}
			content += "12712"// 学校代码
					+cell1+cell2+cell3+cell4+cell5+cell6+cell7+cell8+cell9+cell10+cell11
					+cell12+cell13+cell14+cell15+cell16+cell17+cell18+cell19+cell20+cell21+cell22+cell23+cell24
					+ "\r\n";
		}
		contentToTxt(tempFile, content);
		downLoad(tempFile, "sj312712", response);
	}	
	
	/*********************************************************************************
	 * Description：基表模块-{教学科研仪器设备增减变动情况表 school_device_change}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 ************************************************************************************/
	@SuppressWarnings({ "unchecked" })
	public SchoolDeviceChangeReport schoolDeviceChange(HttpServletRequest request) {
		// 查询条件
		String yearCode = request.getParameter("yearCode");
		// 转换成int
		int yearCodeInt = 2016;		
		//  判断yearCode是否为null，如果是null获得当前的年份
		if (EmptyUtil.isStringEmpty(yearCode)) {
			yearCodeInt=Calendar.getInstance().get(Calendar.YEAR);
		}else {
			yearCodeInt = Integer.parseInt(yearCode);
		}		
		// 查询的上一年的yearcode
		int yearCodeLast = yearCodeInt - 1;		
		// 根据yearCodeLast找到表中的数据
		String sqlData = "select s from SchoolDeviceChangeReport s where 1=1 and s.yearCode = "+ yearCodeLast;
		// 执行查询
		List<SchoolDeviceChangeReport> reports = schoolDeviceChangeReportDAO.executeQuery(sqlData);
		SchoolDeviceChangeReport reportLast = new SchoolDeviceChangeReport();
		// 判断是否为空，若为空，则证明没有上一年数据，即查询的当前年是头一年；若不为空，则一定只有一条数据，将这条数据取出来
		boolean isFirstYear = false;
		if (reports!=null && reports.size()>0) {
			reportLast = reports.get(0);
		}else {
			isFirstYear = true;
		}				
		String sql = "select * from basic_data_two  c";
//		"  where  yearId="+ request.getParameter("yearCode") + " or yearScrap="+ request.getParameter("yearCode")  ;
		javax.persistence.Query query = entityManager.createNativeQuery(sql);
		// 获取list对象
		List<Object[]> list = query.getResultList();
		Object[] schoolDeviceChange;
		SchoolDeviceChangeReport report = new SchoolDeviceChangeReport();
        if (list!=null && list.size()>0) {// list is not all            
            schoolDeviceChange = list.get(0);
            Integer  devicenumberAdd =Integer.valueOf(schoolDeviceChange[4].toString());
            BigDecimal priceAdd = new BigDecimal(schoolDeviceChange[5].toString());
            Integer  devicenumberReduce =Integer.valueOf(schoolDeviceChange[6].toString());
            BigDecimal priceReduce = new BigDecimal(schoolDeviceChange[7].toString());                 
            Integer  deviceNumberChange = Integer.valueOf(schoolDeviceChange[12].toString());
            BigDecimal devicePriceChange =new BigDecimal(schoolDeviceChange[13].toString());
            Integer  deviceNumberValueChange =Integer.valueOf(schoolDeviceChange[14].toString());
            BigDecimal devicePriceValueChange =new BigDecimal(schoolDeviceChange[15].toString());                                       
            report.setYearCode(yearCodeInt);
            report.setDeviceNumberLast(reportLast.getDeviceNumberThis());
            report.setDevicePriceLast(reportLast.getDevicePriceThis());
            report.setDeviceNumberValueLast(reportLast.getDeviceNumberValueThis());
            report.setDevicePriceValueLast(reportLast.getDevicePriceValueThis());            
            report.setDeviceNumberAdd(devicenumberAdd);
            report.setDevicePriceAdd(priceAdd);
            report.setDeviceNumberReduce(devicenumberReduce);
            report.setDevicePriceReduce(priceReduce);             
            if (isFirstYear) {// 直接是变化量
            	report.setDeviceNumberThis(deviceNumberChange);
    		    report.setDevicePriceThis(devicePriceChange);
    		    report.setDeviceNumberValueThis(deviceNumberValueChange);
    		    report.setDevicePriceValueThis(devicePriceValueChange);
			}else {// 变化量加上上一年末的数据
			    report.setDeviceNumberThis(deviceNumberChange+reportLast.getDeviceNumberThis());
			    report.setDevicePriceThis(devicePriceChange.add(reportLast.getDevicePriceThis()));
			    report.setDeviceNumberValueThis(deviceNumberValueChange+reportLast.getDeviceNumberValueThis());
			    report.setDevicePriceValueThis(devicePriceValueChange.add(reportLast.getDevicePriceValueThis()));
			}  
            schoolDeviceChangeReportDAO.store(report);
        }		    	
		return report;
		}
	/*********************************************************************************
	 * Description：基表模块-{查出school_device_change_report表里面的数据}
	 * 
	 * @author：岳茜
	 * @date：2016-9-6
	 ************************************************************************************/
	public List<SchoolDeviceChangeReport> schoolDeviceChangeReport(int yearcode) {
		String sql = "select distinct  n  from SchoolDeviceChangeReport n where 1=1 and  n.yearCode ="+ yearcode;
		sql += " group by n.deviceNumberAdd";
		List<SchoolDeviceChangeReport> list = schoolDeviceChangeReportDAO.executeQuery(sql);	
		return list;
	}

	/********************************************************************************
	 * Description：基表模块-{查出教学科研仪器设备--count school_device_change_report}
	 * 
	 * @author：岳茜
	 * @date：2016-8-3
	 *********************************************************************************/
	public int schoolDeviceChangeCount(HttpServletRequest request) {
		String sql = "select distinct count(*) from school_device_change_report ";
		// 以下两行是分页设置
		javax.persistence.Query query = entityManager.createNativeQuery(sql);
		// 获取对象条数
		int count = ((BigInteger) ((javax.persistence.Query) query)
				.getSingleResult()).intValue();
		return count;
	}
	
	/*********************************************************************************
	 * Description：基表模块-{导出excel，教学科研仪器设备表 school_device_change}
	 * 
	 * @author：魏来
	 * @date：2016-8-1
	 ************************************************************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void exportSchoolDeviceChangeReport(HttpServletRequest request,
			HttpServletResponse response,int yearcode) throws Exception {
		List<Map> list = new ArrayList();
		List<SchoolDeviceChangeReport> reports = schoolDeviceChangeReport(yearcode);
		for (SchoolDeviceChangeReport s : reports) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("schoolNumber","12712");// 学校代码
			if(s.getDeviceNumberLast()!=null && !s.getDeviceNumberLast().equals("")){
			map.put("deviceNumberLast", s.getDeviceNumberLast().toString());// 上学年末实有数 台件
			}else
			{
				map.put("deviceNumberLast", null);
			}
			if(s.getDevicePriceLast()!=null && !s.getDevicePriceLast().equals("")){
			map.put("devicePriceLast", s.getDevicePriceLast().toString());// 上学年末实有数 金额
			}else
			{
				map.put("devicePriceLast", null);
			}
			if(s.getDeviceNumberValueLast()!=null && !s.getDeviceNumberValueLast().equals("")){
			map.put("deviceNumberValueLast", s.getDeviceNumberValueLast().toString());// 上学年末实有数 台件（其中10万元（含）以上）
			}else
			{
				map.put("deviceNumberValueLast", null);
			}
			if(s.getDevicePriceValueLast()!=null && !s.getDevicePriceValueLast().equals("")){
			map.put("devicePriceValueLast", s.getDevicePriceValueLast().toString());// 上学年末实有数 金额（其中10万元（含）以上）
			}else
			{
				map.put("devicePriceValueLast", null);
			}
			if(s.getDeviceNumberAdd()!=null && !s.getDeviceNumberAdd().equals("")){
			map.put("deviceNumberAdd", s.getDeviceNumberAdd().toString());//本学年增加数 台件
			}else
			{
				map.put("deviceNumberAdd", null);
			}
			if(s.getDevicePriceAdd()!=null && !s.getDevicePriceAdd().equals("")){
			map.put("devicePriceAdd", s.getDevicePriceAdd().toString());//本学年增加数 金额
			}else
			{
				map.put("getDevicePriceAdd", null);
			}
			if(s.getDeviceNumberReduce()!=null && !s.getDeviceNumberReduce().equals("")){
			map.put("deviceNumberReduce", s.getDeviceNumberReduce().toString());// 本学年减少数 台件
			}else
			{
				map.put("deviceNumberReduce", null);
			}
			if(s.getDevicePriceReduce()!=null && !s.getDevicePriceReduce().equals("")){
			map.put("devicePriceReduce", s.getDevicePriceReduce().toString());// 本学年减少数 金额
			}else
			{
				map.put("devicePriceReduce", null);
			}
			if(s.getDeviceNumberThis()!=null && !s.getDeviceNumberThis().equals("")){
			map.put("deviceNumberThis", s.getDeviceNumberThis().toString());// 本学年末实有数  台件
			}else
			{
				map.put("deviceNumberThis", null);
			}
			if(s.getDevicePriceThis()!=null && !s.getDevicePriceThis().equals("")){
			map.put("devicePriceThis", s.getDevicePriceThis().toString());//本学年末实有数  金额
			}else
			{
				map.put("devicePriceThis", null);
			}
			if(s.getDeviceNumberValueThis()!=null && !s.getDeviceNumberValueThis().equals("")){
			map.put("deviceNumberValueThis", s.getDeviceNumberValueThis().toString());// 本学年末实有数 台件（其中10万元（含）以上）
			}else
			{
				map.put("deviceNumberValueThis", null);
			}
			if(s.getDevicePriceValueThis()!=null && !s.getDevicePriceValueThis().equals("")){
			map.put("devicePriceValueThis", s.getDevicePriceValueThis().toString());// 本学年末实有数 金额（其中10万元（含）以上）
			}else
			{
				map.put("devicePriceValueThis", null);
			}
     		list.add(map);
		}
		String title = "sj212712";
		String[] hearders = new String[] { "学校代码", "上学年末实有台件数", "上学年末实有金额数", "上学年末实有 台件数（其中10万元（含）以上）", "上学年末实有金额数 （其中10万元（含）以上）",
				"本学年增加台件数", "本学年增加金额数",  "本学年减少台件数 ", "本学年减少金额数 ", " 本学年末实有台件数  ", "本学年末实有金额数  ", " 本学年末实有台件数（其中10万元（含）以上）",
				"本学年末实有金额数（其中10万元（含）以上）" };// 表头数组
		String[] fields = new String[] { "schoolNumber", "deviceNumberLast","devicePriceLast", "deviceNumberValueLast", "devicePriceValueLast",
				                                                                           "deviceNumberAdd",	"devicePriceAdd", "deviceNumberReduce", "devicePriceReduce",
				                                                                           "deviceNumberThis", "devicePriceThis", "deviceNumberValueThis","devicePriceValueThis" };//对象属性数组

		TableData td = ExcelUtils.createTableData(list,
				ExcelUtils.createTableHeader(hearders), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportToExcel(title, shareService.getUser().getCname(), td);// title未表头名，中间的参数为获取当前登陆用户的用户名后传入创建人，td即当前表的生成时间。效果如下
	}
	/*********************************************************************************
	 * Description：基表模块-{导出txt，教学科研仪器设备表 school_device_change}
	 * 
	 * @author：魏来
	 * @date：2016-8-5
	 ************************************************************************************/
	public void newSchoolDeviceChangeTecEquipAVE(List<SchoolDeviceChangeReport> listEquipments,
			File tempFile, HttpServletRequest request,
			HttpServletResponse response,int yearcode) throws Exception {
		// 设定输出字节流内容
		String content = "";
		// 设定时间格式
		@SuppressWarnings("unused")
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		List<SchoolDeviceChangeReport> reports = schoolDeviceChangeReport( yearcode);
		for (SchoolDeviceChangeReport s : reports) {
			String cell1,cell2,cell3,cell4,cell5,cell6,cell7,cell8,cell9,cell10,cell11,cell12;
			if(s.getDeviceNumberLast()!=null&&!s.getDeviceNumberLast().equals("") ){
				cell1 = formatStr(s.getDeviceNumberLast().toString().trim(), 8);
			}else{
				cell1 = formatStr("*", 8);
			}
			if(s.getDevicePriceLast()!=null && !s.getDevicePriceLast().equals("")){
				cell2 = formatStr(s.getDevicePriceLast().toString().trim(), 11);
			}else{
				cell2 = formatStr("*", 11);
			}
			if(s.getDeviceNumberValueLast()!=null &&!s.getDeviceNumberValueLast().equals("")){
				cell3=formatStr(s.getDeviceNumberValueLast().toString().toString(),6);// 上学年末实有数 台件（其中10万元（含）以上）
			}else
				{
					cell3 = formatStr("*", 6);
				}
			if(s.getDevicePriceValueLast()!=null && !s.getDevicePriceValueLast().equals("")){
				cell4=formatStr(s.getDevicePriceValueLast().toString(), 9);// 上学年末实有数 金额（其中10万元（含）以上）
			}else
				{
				cell4 = formatStr("*", 9);
				}
			if(s.getDeviceNumberAdd()!=null && !s.getDeviceNumberAdd().equals("")){
				cell5=formatStr(s.getDeviceNumberAdd().toString(), 6);//本学年增加数 台件
			}else
				{
				    cell5 = formatStr("*", 6);
				}
				if(s.getDevicePriceAdd()!=null && !s.getDevicePriceAdd().equals("")){
				    cell6=formatStr(s.getDevicePriceAdd().toString(),9);//本学年增加数 金额
				}else
				{
					cell6 = formatStr("*", 9);
				}
				if(s.getDeviceNumberReduce()!=null && !s.getDeviceNumberReduce().equals("")){
				    cell7=formatStr(s.getDeviceNumberReduce().toString(), 6);// 本学年减少数 台件
				}else
				{
					cell7 = formatStr("*", 6);
				}
				if(s.getDevicePriceReduce()!=null && !s.getDevicePriceReduce().equals("")){
				    cell8=formatStr(s.getDevicePriceReduce().toString(), 9);// 本学年减少数 金额
				}else
				{
					cell8 = formatStr("*", 9);
				}
				if(s.getDeviceNumberThis()!=null && !s.getDeviceNumberThis().equals("")){
				    cell9=formatStr(s.getDeviceNumberThis().toString(), 8);// 本学年末实有数  台件
				}else
				{
					cell9 = formatStr("*", 8);
				}
				if(s.getDevicePriceThis()!=null && !s.getDevicePriceThis().equals("")){
				    cell10=formatStr(s.getDevicePriceThis().toString(), 11);//本学年末实有数  金额
				}else
				{
					cell10 = formatStr("*", 11);
				}
				if(s.getDeviceNumberValueThis()!=null && !s.getDeviceNumberValueThis().equals("")){
				    cell11=formatStr(s.getDeviceNumberValueThis().toString(), 6);// 本学年末实有数 台件（其中10万元（含）以上）
				}else
				{
					cell11 = formatStr("*", 6);
				}
				if(s.getDevicePriceValueThis()!=null && !s.getDevicePriceValueThis().equals("")){
				    cell12=formatStr( s.getDevicePriceValueThis().toString(), 9);// 本学年末实有数 金额（其中10万元（含）以上）
				}else
				{
					cell12 = formatStr("*", 9);
				}
			content += "12712"// 学校代码
					+cell1+cell2+cell3+cell4+cell5+cell6+cell7+cell8+cell9+cell10+cell11
					+cell12
					+ "\r\n";
		}
		contentToTxt(tempFile, content);
		downLoad(tempFile, "sj212712", response);
	}	

	/*********************************************************************************
	 * Description：基表模块-{查出专任实验室人员列表 lab_team}
	 * 
	 * @author：魏来
	 * @date：2016-8-1
	 ************************************************************************************/
	@SuppressWarnings({ "unchecked" })
	public List<Object[]> labTeam(HttpServletRequest request, int page, int pageSize) {
		String sql = "select * from lab_team n where 1=1";
		javax.persistence.Query query = entityManager.createNativeQuery(sql);
		// 以下两行是分页设置
		query.setMaxResults(pageSize);
		query.setFirstResult((page - 1) * pageSize);
		// 获取list对象
		List<Object[]> list = query.getResultList();
		return list;
	}
	
	/*********************************************************************************
	 * Description：基表模块-{导出excel，专任实验室人员表 lab_team}
	 * 
	 * @author：魏来
	 * @date：2016-8-1
	 ************************************************************************************/
	@SuppressWarnings("unchecked")
	public void exportLabTeam(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		@SuppressWarnings("rawtypes")
		List<Map> list = new ArrayList();
		List<Object[]> reports = labTeam(request, 1, 100000);
		for (Object[]s : reports) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("schoolNumber", s[0]);// 学校代码
			map.put("lwCodeCustom", s[1]);// 人员编号
			map.put("labRoomNumber", s[2]);// 实验室编号
			map.put("labRoomName", s[3]);// 实验室名称
			map.put("cname", s[4]);// 姓名
			map.put("useSexy", s[5]);// 性别
			map.put("birthday", s[6]);// 出生年月
			map.put("subject", s[7]);// 所属学科
			map.put("lwProfessionSpecialty", s[8]);// 专业技术职务
			map.put("degree", s[9]);// 文化程度
			map.put("categoryExpert", s[10]);// 专家类别
			map.put("trainFormalInlandTime", s[11]);// 国内培训 学历教育时间
			map.put("trainInformalInlandTime", s[12]);// 国内培训 非学历教育时间
			map.put("trainFormalAbroadTime", s[13]);// 国外培训 学历教育时间
			map.put("trainInformalAbroadTime", s[14]);// 国外培训 非学历教育时间
			list.add(map);
		}
		String title = "sj512712";
		String[] hearders = new String[] { "学校代码", "人员编号", "实验室编号", "实验室名称", "姓名",
				"性别", "出生年月", "所属学科", "专业技术职务", "文化程度", "专家类别", "国内培训 学历教育时间", "国内培训 非学历教育时间",
				"国外培训 学历教育时间", " 国外培训 非学历教育时间" };// 表头数组
		String[] fields = new String[] { "schoolNumber", "lwCodeCustom",
				"labRoomNumber", "labRoomName", "cname", "useSexy",
				"birthday", "subject", "lwProfessionSpecialty",
				"degree", "categoryExpert", "trainFormalInlandTime",
				"trainInformalInlandTime", "trainFormalAbroadTime", "trainInformalAbroadTime" };//对象属性数组

		TableData td = ExcelUtils.createTableData(list,
				ExcelUtils.createTableHeader(hearders), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportToExcel(title, shareService.getUser().getCname(), td);// title未表头名，中间的参数为获取当前登陆用户的用户名后传入创建人，td即当前表的生成时间。效果如下
	}
	
	/*********************************************************************************
	 * Description：基表模块-{导出txt，专任实验室人员基表 lab_team}
	 * 
	 * @author：魏来
	 * @date：2016-8-5
	 ************************************************************************************/
	public void newLabTeamTecEquipAVE(List<Object[]> listEquipments,
			File tempFile, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 设定输出字节流内容
		String content = "";
		// 设定时间格式
		@SuppressWarnings("unused")
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		List<Object[]> reports = labTeam(request, 1, 50);
		for (Object[] s : reports) {

			content += "12712";// 学校代码
			if(s[1] != null)
			{
				content += formatStr(s[1].toString().trim(), 7);// 人员编号
			}else{
				content += formatStr("*", 7);// 规格
			}
			if(s[2] != null)
			{
				content+= formatStr(s[2].toString().trim(), 10);// 实验室编号
			}else{
				content += formatStr("*", 10);// 规格
			}
			if(s[3] != null)
			{
				content += formatStr(s[3].toString().trim(), 50); // 实验室名称
			}else{
				content += formatStr("*", 50);// 规格
			}
			if(s[4] != null)
			{
				content +=formatStr(s[4].toString().trim(), 8);// 姓名
			}else{
				content += formatStr("*", 8);// 规格
			}
			if(s[5] != null)
			{
				content +=formatStr(s[5].toString().trim(), 4);// 性别
			}else{
				content += formatStr("*", 4);// 规格
			}
			if(s[6] != null)
			{
				content += formatStr(s[6].toString().trim(), 6);// 出生年月
			}else{
				content += formatStr("*", 6);// 规格
			}
			if(s[7] != null)
			{
				content += formatStr(s[7].toString().trim(), 4);// 所属学科
			}else{
				content += formatStr("*", 4);// 规格
			}
			if(s[8] != null)
			{
				content += formatStr(s[8].toString().trim(), 3);// 专业技术职务
			}else{
				content += formatStr("*", 3);// 规格
			}
			if(s[9] != null)
			{
				content +=formatStr(s[9].toString().trim(),  2);// 文化程度
			}else{
				content += formatStr("*", 2);// 规格
			}
			if(s[10] != null)
			{
				content += formatStr(s[10].toString().trim(), 2);// 专家类别
			}else{
				content += formatStr("*", 2);// 规格
			}
			if(s[11] != null)
			{
				content += formatStr(s[11].toString().trim(), 3);// 国内培训 学历教育时间
			}else{
				content += formatStr("*", 3);// 规格
			}
			if(s[12] != null)
			{
				content += formatStr(s[12].toString().trim(), 3);// 国内培训 非学历教育时间
			}else{
				content += formatStr("*", 3);// 规格
			}
			if(s[13] != null)
			{
				content += formatStr(s[13].toString().trim(), 3);// 国外培训 学历教育时间
			}else{
				content += formatStr("*", 3);// 规格
			}
			if(s[14] != null)
			{
				content += formatStr(s[14].toString().trim(), 3);// 国外培训 非学历教育时间
			}else{
				content += formatStr("*", 3);// 规格
			}
			content += "\r\n";
		}
		contentToTxt(tempFile, content);
		downLoad(tempFile, "sj512712", response);
	}	

	/*********************************************************************************
	 * Description：基表模块-{查出教学实验项目表 operation_item_teaching}
	 * 
	 * @author：魏来
	 * @date：2016-8-8
	 ************************************************************************************/
	@SuppressWarnings({ "unchecked" })
	public List<Object[]> operationItemTeaching(HttpServletRequest request, int page, int pageSize) {
		String sql = "select * from operation_item_teaching n where 1=1";
		if( request.getParameter("yearCode") != null){
			sql += " and n.yearId = "+ request.getParameter("yearCode");
		}
		//sql += " and status = 545";
		javax.persistence.Query query = entityManager.createNativeQuery(sql);
		// 以下两行是分页设置
		query.setMaxResults(pageSize);
		query.setFirstResult((page - 1) * pageSize);
		// 获取list对象
		List<Object[]> list = query.getResultList();
		return list;
	}
	/**************************************
	 * Description：基表模块-{查出教学实验项目--count}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 **************************************/
	public int operationItemTeachingCount(HttpServletRequest request) {
		String sql = "select count(*) from operation_item_teaching ";
		// 以下两行是分页设置
		javax.persistence.Query query = entityManager.createNativeQuery(sql);
		// 获取对象条数
		int count = ((BigInteger) ((javax.persistence.Query) query)
				.getSingleResult()).intValue();
		return count;
	}
	
	/*********************************************************************************
	 * Description：基表模块-{导出excel，教学实验项目表 operation_item_teaching}
	 * 
	 * @author：魏来
	 * @date：2016-8-8
	 ************************************************************************************/
	@SuppressWarnings("unchecked")
	public void exportOperationItemTeaching(HttpServletRequest request,
			HttpServletResponse response,int yearCode) throws Exception {
		@SuppressWarnings("rawtypes")
		List<Map> list = new ArrayList();
		List<Object[]> reports = operationItemTeaching(request, 1, 50);
		for (Object[] s : reports) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("schoolNumber", s[0]);// 学校代码
			map.put("lpCodeCustom", s[1]);// 实验编号
			map.put("lpName", s[2]);// 实验名称
			map.put("lpCategoryMain", s[3]);// 实验类别
			map.put("lpCategoryApp", s[4]);// 实验类型
			map.put("lpSubject", s[5]);// 实验所属学科
			map.put("lpCategoryRequire", s[6]);// 实验要求
			map.put("lpCategoryStudent", s[7]);// 实验者类别
			map.put("lpStudentNumber", s[8]);// 实验者人数
			map.put("lpStudentNumberGroup", s[9]);// 每组人数
			map.put("lpDepartmentHours", s[10]);// 实验学时数
			map.put("labRoomNumber", s[11]);// 实验室编号
			map.put("labRoomName", s[12]);// 实验室名称
			map.put("yearCode", s[13]);// 学年
			list.add(map);
		}
		String title = "sj412712";
		String[] hearders = new String[] { "学校代码", "试验编号", "实验名称", "实验类别", "实验类型",
				"实验所属学科", "实验要求", "实验者类别", "实验者人数", "每组人数", "实验学时数", "实验室编号", "实验室名称" };// 表头数组
		String[] fields = new String[] { "schoolNumber", "lpCodeCustom",
				"lpName", "lpCategoryMain", "lpCategoryApp", "lpSubject",
				"lpCategoryRequire", "lpCategoryStudent", "lpStudentNumber",
				"lpStudentNumberGroup", "lpDepartmentHours", "labRoomNumber",
				"labRoomName" };//对象属性数组

		TableData td = ExcelUtils.createTableData(list,
				ExcelUtils.createTableHeader(hearders), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportToExcel(title, shareService.getUser().getCname(), td);// title未表头名，中间的参数为获取当前登陆用户的用户名后传入创建人，td即当前表的生成时间。效果如下
	}
	
	/*********************************************************************************
	 * Description：基表模块-{导出txt，教学实验项目表 operation_item_teaching}
	 * 
	 * @author：魏来
	 * @date：2016-8-8
	 ************************************************************************************/
	public void newOperationItemTeachingTecEquipAVE(List<Object[]> listEquipments,
			File tempFile, HttpServletRequest request,
			HttpServletResponse response,int yearCode) throws Exception {
		// 设定输出字节流内容
		String content = "";
		// 设定时间格式
		@SuppressWarnings("unused")
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		List<Object[]> reports = operationItemTeaching(request, 1, 50);
		for (Object[] s : reports) {
			String cell1,cell2,cell3,cell4,cell5,cell6,cell7,cell8,cell9,cell10,cell11,cell12;
			if(s[1]!=null ){
				cell1 = formatStr(s[1].toString().trim(), 13);
			}else{
				cell1 = formatStr("*", 13);
			}
			if(s[2]!=null ){
				cell2 = formatStr(s[2].toString().trim(),50);
			}else{
				cell2 = formatStr("*",50);
			}
			if(s[3]!=null ){
				cell3 = formatStr(s[3].toString().trim(), 1);
			}else{
				cell3 = formatStr("*", 1);
			}
			if(s[4]!=null ){
				cell4 = formatStr(s[4].toString().trim(), 2);
			}else{
				cell4 = formatStr("*", 2);
			}
			if(s[5]!=null ){
				cell5 = formatStr(s[5].toString().trim(), 4);
			}else{
				cell5 = formatStr("*", 4);
			}
			if(s[6]!=null ){
				cell6 = formatStr(s[6].toString().trim(), 1);
			}else{
				cell6 = formatStr("*", 1);
			}
			if(s[7]!=null ){
				cell7 = formatStr(s[7].toString().trim(), 1);
			}else{
				cell7 = formatStr("0", 1);
			}
			if(s[8]!=null ){
				cell8 = formatStr(s[8].toString().trim(), 6);
			}else{
				cell8 = formatStr("0", 6);
			}
			if(s[9]!=null ){
				cell9 = formatStr(s[9].toString().trim(), 2);
			}else{
				cell9 = formatStr("0", 2);
			}
			if(s[10]!=null ){
				cell10 = formatStr(s[10].toString().trim(), 4);
			}else{
				cell10 = formatStr("*", 4);
			}
			if(s[11]!=null ){
				cell11 = formatStr(s[11].toString().trim(),10);
			}else{
				cell11 = formatStr("*", 10);
			}
			if(s[12]!=null ){
				cell12 = formatStr(s[12].toString().trim(), 50);
			}else{
				cell12 = formatStr("*", 50);
			}
			content += "12712"// 学校代码
					+cell1+cell2+cell3+cell4+cell5+cell6+cell7+cell8+cell9+cell10+cell11
					+cell12
					+ "\r\n";
		}
		contentToTxt(tempFile, content);
		downLoad(tempFile, "sj412712", response);
	}	
	
	/*********************************************************************************
	 * Description：基表模块-{实验室基本情况表 view_lab_basic}
	 * 
	 * @author：魏来
	 * @date：2016-8-10
	 ************************************************************************************/
	@SuppressWarnings({ "unchecked" })
	public List<Object[]> labBasic(HttpServletRequest request, int page, int pageSize) {
		String sql = "select * from view_lab_basic n where 1=1";
		javax.persistence.Query query = entityManager.createNativeQuery(sql);
		// 以下两行是分页设置
		query.setMaxResults(pageSize);
		query.setFirstResult((page - 1) * pageSize);
		// 获取list对象
		List<Object[]> list = query.getResultList();
		return list;
	}
	
	/*********************************************************************************
	 * Description：基表模块-{导出excel，实验室基本情况表 lab_basic}
	 * 
	 * @author：魏来
	 * @date：2016-8-10
	 ************************************************************************************/
	@SuppressWarnings("unchecked")
	public void exportLabBasic(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		@SuppressWarnings("rawtypes")
		List<Map> list = new ArrayList();
		List<Object[]> reports = labBasic(request, 1,100000);
		for (Object[] s : reports) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("schoolNumber", s[0]);// 学校代码
			map.put("labRoomNumber", s[1]);// 实验室编号
			map.put("labRoomName", s[2]);// 实验室名称
			map.put("labRoomCategory", s[3]);//实验室类别
			map.put("labRoomTimeCreate", s[4]);// 建立年份
			map.put("labRoomArea", s[5]);// 房屋使用面积
			map.put("labRoomType", s[6]);// 实验室类型
			map.put("subject", s[7]);// 所属学科
			map.put("labPrizeNation", s[8]);// 教师获奖与成果 （国家级）
			map.put("labPrizeProvince", s[9]);// 教师获奖与成果 （省部级）
			map.put("labPrizePatent", s[10]);// 教师获奖与成果 （发明专利）
			map.put("labPrizeStudent", s[11]);// 学生获奖情况
			map.put("labPaperSciTeaching", s[12]);// 教学方面论文和教材情况（三大检索收录）
			map.put("labPaperSciResearch", s[13]);// 科研方面论文和教材情况（三大检索收录）
			map.put("labPaperKeyTeaching", s[14]);// 教学方面论文和教材情况（核心刊物）
			map.put("labPaperKeyResearch", s[15]);//科研方面论文和教材情况（核心刊物）
			map.put("labPaperBook", s[16]);// 论文和教材情况（实验教材）
			map.put("labResearchNation", s[17]);// 科研及社会服务情况中科研项目数（省部级以上）
			map.put("labResearchOther", s[18]);// 科研及社会服务情况中科研项目数（其它）
			map.put("labService", s[19]);// 科研及社会服务情况中社会服务项目数
			map.put("labTeachingNation", s[20]);// 科研及社会服务情况中教研项目数（省部级以上）
			map.put("labTeachingOther", s[21]);// 科研及社会服务情况中教研项目数（其它）
			map.put("labGraduateMaster", s[22]);// 毕业设计和论文人数（专科生人数）
			map.put("labGraduateBachelor", s[23]);// 毕业设计和论文人数（本科生人数）
			map.put("labGraduateOther", s[24]);// 毕业设计和论文人数（研究生人数）
			map.put("labOpenItemCountInner", s[25]);// 开放实验个数（校内）
			map.put("labOpenItemCountOutter", s[26]);// 开放实验个数（校外）
			map.put("labOpenItemStudentInner", s[27]);// 开放实验人数（校内）
			map.put("labOpenItemStudentOutter", s[28]);// 开放实验人数（校外）
			map.put("labOpenItemHourInner", s[29]);// 开放实验人时数（校内）
			map.put("labOpenItemHourOutter", s[30]);// 开放实验人时数（校外）
			map.put("labPartTime", s[31]);// 兼任人员数
			map.put("labCostTotal", s[32]);//实验教学运行经费小计（万元,保留两位小数）
			map.put("labCostTeaching", s[33]);//实验教学运行经费（其中教学实验 年材料消耗费）
			list.add(map);
		}
		String title = "sj612712";
		String[] hearders = new String[] { "学校代码", "实验室编号", "实验室名称", "实验室类别", "建立年份",	"房屋使用面积", 
				                                                   "实验室类型", "所属学科", "教师获奖与成果 （国家级）", "教师获奖与成果 （省部级）", " 教师获奖与成果 （发明专利）", "学生获奖情况",
				                                                   " 教学方面论文和教材情况（三大检索收录）", " 科研方面论文和教材情况（三大检索收录）", "科研方面论文和教材情况（核心刊物）", 
				                                                   " 论文和教材情况（实验教材）", "科研及社会服务情况中科研项目数（省部级以上）", "科研及社会服务情况中科研项目数（其它）",
				                                                   "科研及社会服务情况中社会服务项目数", "科研及社会服务情况中教研项目数（省部级以上）", " 科研及社会服务情况中教研项目数（其它）",
				                                                   "毕业设计和论文人数（专科生人数）", "毕业设计和论文人数（本科生人数）", "毕业设计和论文人数（研究生人数）",
				                                                   "开放实验个数（校内）", "开放实验个数（校外）", "开放实验人数（校内）", "开放实验人数（校外）", " 开放实验人时数（校内）", "开放实验人时数（校外）",
				                                                   "兼任人员数", "实验教学运行经费小计", "实验教学运行经费","其中教学实验 年材料消耗费"
		};// 表头数组
		String[] fields = new String[] { "schoolNumber", "labRoomNumber","labRoomName", "labRoomCategory", "labRoomTimeCreate", "labRoomArea",
				                                              "labRoomType", "subject","labPrizeNation", "labPrizeProvince", "labPrizePatent", "labPrizeStudent",
				                                              "labPaperSciTeaching", "labPaperSciResearch","labPaperKeyTeaching", "labPaperKeyResearch", "labPaperBook", "labResearchNation",
				                                              "labResearchOther", "labService", "labTeachingNation","labTeachingOther", "labGraduateMaster", "labGraduateBachelor",
				                                              "labGraduateOther","labOpenItemCountInner","labOpenItemCountOutter","labOpenItemStudentInner", "labOpenItemStudentOutter", 
	                                                          "labOpenItemHourInner",  "labOpenItemHourOutter", "labPartTime", "labCostTotal","labCostTeaching" };//对象属性数组

		TableData td = ExcelUtils.createTableData(list,
				ExcelUtils.createTableHeader(hearders), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportToExcel(title, shareService.getUser().getCname(), td);// title未表头名，中间的参数为获取当前登陆用户的用户名后传入创建人，td即当前表的生成时间。效果如下
	}

	/*********************************************************************************
	 * @Description：基表模块-{导出txt，实验室基本情况表 lab_basic}
	 * 
	 * @author：魏来
	 * @date：2016-8-8
	 ************************************************************************************/
	public void newLabBasicTecEquipAVE(List<Object[]> listEquipments,
			File tempFile, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 设定输出字节流内容
		String content = "";
		// 设定时间格式
		@SuppressWarnings("unused")
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		List<Object[]> reports = labBasic(request, 1, 50);
		for (Object[] s : reports) {
			String cell1,cell2,cell3,cell4,cell5,cell6,cell7,cell8,cell9,cell10,cell11,cell12,cell13,cell14,cell15,cell16,cell17,cell18,
			cell19,cell20,cell21,cell22,cell23,cell24,cell25,cell26,cell27,cell28,cell29,cell30,cell31,cell32,cell33;
			if(s[1]!=null ){
				cell1 = formatStr(s[1].toString().trim(), 10);
			}else{
				cell1 = formatStr("*", 10);
			}
			if(s[2]!=null ){
				cell2 = formatStr(s[2].toString().trim(),50);
			}else{
				cell2 = formatStr("*",50);
			}
			if(s[3]!=null ){
				cell3 = formatStr(s[3].toString().trim(), 1);
			}else{
				cell3 = formatStr("*", 1);
			}
			if(s[4]!=null ){
				cell4 = formatStr(s[4].toString().trim(), 4);
			}else{
				cell4 = formatStr("*", 4);
			}
			if(s[5]!=null ){
				cell5 = formatStr(s[5].toString().trim(), 6);
			}else{
				cell5 = formatStr("*", 6);
			}
			if(s[6]!=null ){
				cell6 = formatStr(s[6].toString().trim(), 1);
			}else{
				cell6 = formatStr("*", 1);
			}
			if(s[7]!=null ){
				cell7 = formatStr(s[7].toString().trim(), 4);
			}else{
				cell7 = formatStr("0", 4);
			}
			if(s[8]!=null ){
				cell8 = formatStr(s[8].toString().trim(), 2);
			}else{
				cell8 = formatStr("0", 2);
			}
			if(s[9]!=null ){
				cell9 = formatStr(s[9].toString().trim(), 2);
			}else{
				cell9 = formatStr("0", 2);
			}
			if(s[10]!=null ){
				cell10 = formatStr(s[10].toString().trim(), 2);
			}else{
				cell10 = formatStr("*", 2);
			}
			if(s[11]!=null ){
				cell11 = formatStr(s[11].toString().trim(),2);
			}else{
				cell11 = formatStr("*", 2);
			}
			if(s[12]!=null ){
				cell12 = formatStr(s[12].toString().trim(), 3);
			}else{
				cell12 = formatStr("*", 3);
			}
			if(s[13]!=null ){
				cell13 = formatStr(s[13].toString().trim(), 3);
			}else{
				cell13 = formatStr("*", 3);
			}
			if(s[14]!=null ){
				cell14 = formatStr(s[14].toString().trim(), 3);
			}else{
				cell14 = formatStr("*", 3);
			}
			if(s[15]!=null ){
				cell15 = formatStr(s[15].toString().trim(), 3);
			}else{
				cell15 = formatStr("*", 3);
			}
			if(s[16]!=null ){
				cell16 = formatStr(s[16].toString().trim(), 2);
			}else{
				cell16 = formatStr("*", 2);
			}
			if(s[17]!=null ){
				cell17 = formatStr(s[17].toString().trim(), 2);
			}else{
				cell17 = formatStr("*", 2);
			}
			if(s[18]!=null ){
				cell18 = formatStr(s[18].toString().trim(), 3);
			}else{
				cell18 = formatStr("*", 3);
			}
			if(s[19]!=null ){
				cell19 = formatStr(s[19].toString().trim(), 3);
			}else{
				cell19 = formatStr("*", 3);
			}
			if(s[20]!=null ){
				cell20 = formatStr(s[20].toString().trim(), 3);
			}else{
				cell20 = formatStr("*", 3);
			}
			if(s[21]!=null ){
				cell21 = formatStr(s[21].toString().trim(), 3);
			}else{
				cell21 = formatStr("*", 3);
			}
			if(s[22]!=null ){
				cell22 = formatStr(s[22].toString().trim(), 4);
			}else{
				cell22 = formatStr("*", 4);
			}
			if(s[23]!=null ){
				cell23 = formatStr(s[23].toString().trim(), 4);
			}else{
				cell23 = formatStr("*", 4);
			}
			if(s[24]!=null ){
				cell24 = formatStr(s[24].toString().trim(), 4);
			}else{
				cell24 = formatStr("*", 4);
			}
			if(s[25]!=null ){
				cell25 = formatStr(s[25].toString().trim(), 6);
			}else{
				cell25 = formatStr("*", 6);
			}
			if(s[26]!=null ){
				cell26 = formatStr(s[26].toString().trim(),6);
			}else{
				cell26 = formatStr("*",6);
			}
			if(s[27]!=null ){
				cell27 = formatStr(s[27].toString().trim(), 6);
			}else{
				cell27= formatStr("*", 6);
			}
			if(s[28]!=null ){
				cell28 = formatStr(s[28].toString().trim(), 6);
			}else{
				cell28 = formatStr("*", 6);
			}
			if(s[29]!=null ){
				cell29 = formatStr(s[29].toString().trim(), 8);
			}else{
				cell29 = formatStr("*", 8);
			}
			if(s[30]!=null ){
				cell30 = formatStr(s[30].toString().trim(), 6);
			}else{
				cell30 = formatStr("*", 6);
			}
			if(s[31]!=null ){
				cell31 = formatStr(s[31].toString().trim(), 3);
			}else{
				cell31 = formatStr("0", 3);
			}
			if(s[32]!=null ){
				cell32 = formatStr(s[32].toString().trim(), 8);
			}else{
				cell32 = formatStr("0", 8);
			}
			if(s[33]!=null ){
				cell33 = formatStr(s[33].toString().trim(), 8);
			}else{
				cell33 = formatStr("0", 8);
			}
			content += "12712"// 学校代码
					+cell1+cell2+cell3+cell4+cell5+cell6+cell7+cell8+cell9+cell10+cell11
					+cell12+cell13+cell14+cell15+cell16+cell17+cell18+cell19+cell20+cell21+cell22+cell23+cell24
					+cell25+cell26+cell27+cell28+cell29+cell30+cell31+cell32+cell33
					+ "\r\n";
		}
		contentToTxt(tempFile, content);
		downLoad(tempFile, "sj612712", response);
	}	
	
	/*********************************************************************************
	 * Description：基表模块-{实验室经费情况表 lab_room_report_fund}
	 * 
	 * @author：魏来
	 * @date：2016-8-17
	 ************************************************************************************/
	@SuppressWarnings({ "unchecked" })
	public List<Object[]> labRoomReportFund(HttpServletRequest request, int page, int pageSize) {
		String sql = "select * from lab_room_report_fund n where 1=1";
		javax.persistence.Query query = entityManager.createNativeQuery(sql);
		// 以下两行是分页设置
		query.setMaxResults(pageSize);
		query.setFirstResult((page - 1) * pageSize);
		// 获取list对象
		List<Object[]> list = query.getResultList();
		return list;
	}
	
	/*********************************************************************************
	 * Description：基表模块-{导出excel，实验室经费情况表 lab_room_report_fund}
	 * 
	 * @author：魏来
	 * @date：2016-8-17
	 ************************************************************************************/
	@SuppressWarnings("unchecked")
	public void exportLabRoomReportFund(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		@SuppressWarnings("rawtypes")
		List<Map> list = new ArrayList();
		List<Object[]> reports = labRoomReportFund(request, 1, 100000);
		for (Object[]s : reports) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("schoolNumber",12712 );// 学校代码
			map.put("labCount", s[2]);// 实验室个数
			map.put("labRoomArea", s[3]);// 实验室面积
			map.put("labFundTotal", s[4]);// 经费投入（总计）
			map.put("labDeviceFundTotal", s[5]);//仪器设备购置经费(总计)
			map.put("labDeviceFundTeaching", s[6]);//仪器设备购置经费(教学)
			map.put("labDeviceRepairTotal", s[7]);//仪器设备维护经费(总计)
			map.put("labDeviceRepairTeaching", s[8]);// 仪器设备维护费(教学)
			map.put("labTeachFundTotal", s[9]);// 实验教学运行经费(总计)
			map.put("labTeachFundExpendable", s[10]);// 实验教学运行经费(材料消耗)
			map.put("labConstructionFund", s[11]);// 实验室建设经费
			map.put("labResearchFund", s[12]);// 实验教学研究与改革经费
			map.put("other_fund", s[13]);//其他经费
			list.add(map);
		}
		String title = "sj712712";
		String[] hearders = new String[] { "学校代码", "实验室个数"," 实验室面积", "经费投入（总计）","仪器设备购置经费(总计)", "仪器设备购置经费(教学)",
				                                                   "仪器设备维护经费(总计)", "仪器设备维护费(教学)","实验教学运行经费(总计)", "实验教学运行经费(材料消耗)","实验室建设经费", "实验教学研究与改革经费",
				                                                   "其他经费"};// 表头数组
		String[] fields = new String[] { "schoolNumber", "labCount","labRoomArea","labFundTotal", "labDeviceFundTotal","labDeviceFundTeaching",
				                                             "labDeviceRepairTotal", "labDeviceRepairTeaching","labTeachFundTotal", "labTeachFundExpendable","labConstructionFund", 
				                                             "labResearchFund", "other_fund"};//对象属性数组
		TableData td = ExcelUtils.createTableData(list,
				ExcelUtils.createTableHeader(hearders), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportToExcel(title, shareService.getUser().getCname(), td);// title未表头名，中间的参数为获取当前登陆用户的用户名后传入创建人，td即当前表的生成时间。效果如下
	}
	
	/*********************************************************************************
	 * @Description：基表模块-{导出txt，实验室经费情况表 lab_room_report_fund}
	 * 
	 * @author：魏来
	 * @date：2016-8-8
	 ************************************************************************************/
	public void newLabRoomReportFundTecEquipAVE(List<Object[]> listEquipments,
			File tempFile, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 设定输出字节流内容
		String content = "";
		// 设定时间格式
		@SuppressWarnings("unused")
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		List<Object[]> reports = labRoomReportFund(request, 1, 50);
		for (Object[] s : reports) {
			String cell2,cell3,cell4,cell5,cell6,cell7,cell8,cell9,cell10,cell11,cell12,cell13;
			if(s[2]!=null ){
				cell2 = formatStr(s[2].toString().trim(),6);
			}else{
				cell2 = formatStr("0",6);
			}
			if(s[3]!=null ){
				cell3 = formatStr(s[3].toString().trim(), 8);
			}else{
				cell3 = formatStr("0", 8);
			}
			if(s[4]!=null ){
				cell4 = formatStr(s[4].toString().trim(), 10);
			}else{
				cell4 = formatStr("0", 10);
			}
			if(s[5]!=null ){
				cell5 = formatStr(s[5].toString().trim(), 8);
			}else{
				cell5 = formatStr("0", 8);
			}
			if(s[6]!=null ){
				cell6 = formatStr(s[6].toString().trim(), 8);
			}else{
				cell6 = formatStr("0", 8);
			}
			if(s[7]!=null ){
				cell7 = formatStr(s[7].toString().trim(), 8);
			}else{
				cell7 = formatStr("0", 8);
			}
			if(s[8]!=null ){
				cell8 = formatStr(s[8].toString().trim(), 8);
			}else{
				cell8 = formatStr("0", 8);
			}
			if(s[9]!=null ){
				cell9 = formatStr(s[9].toString().trim(), 8);
			}else{
				cell9 = formatStr("0", 8);
			}
			if(s[10]!=null ){
				cell10 = formatStr(s[10].toString().trim(), 8);
			}else{
				cell10 = formatStr("0", 8);
			}
			if(s[11]!=null ){
				cell11 = formatStr(s[11].toString().trim(),8);
			}else{
				cell11 = formatStr("0", 8);
			}
			if(s[12]!=null ){
				cell12 = formatStr(s[12].toString().trim(), 8);
			}else{
				cell12 = formatStr("0", 8);
			}
			if(s[13]!=null ){
				cell13 = formatStr(s[13].toString().trim(), 8);
			}else{
				cell13 = formatStr("0", 8);
			}
			content += "12712"// 学校代码
					+cell2+cell3+cell4+cell5+cell6+cell7+cell8+cell9+cell10+cell11+cell12+cell13
					+ "\r\n";
		}
		contentToTxt(tempFile, content);
		downLoad(tempFile, "sj712712", response);
	}	
	
	/*************************************************************************************
	 * Description：基表模块-资产管理{获取资产管理的总记录数}
	 * 
	 * @author：郑昕茹
	 * @date：2016-11-21
	 *************************************************************************************/
	@Transactional
	public int getAllAssetManage() {
		/*String sql ="select count(*) from AssetManage a where 1=1"; 
		return ((Long)assetManageDAO.createQuerySingleResult(sql).getSingleResult()).intValue();*/
		return 0;
	}
	
	/*************************************************************************************
	 * Description：基表模块-资产管理{获取资产管理的分页记录数}
	 * 
	 * @author：郑昕茹
	 * @date：2016-11-21
	 *************************************************************************************/
/*	@Transactional
	public List<AssetManage> getAssetManage(AssetManage assetManage, int currpage, int pageSize) {
		String sql ="select a from AssetManage a where 1=1"; 
		return assetManageDAO.executeQuery(sql, (currpage-1)*pageSize, pageSize);
        return null;
	}*/

	
	/*********************************************************************************
	 * Description：基表模块-{导出txt，学年}
	 * 
	 * @author：郑昕茹
	 * @date：2016-11-21
	 ************************************************************************************/
	public void newExportTxtYear(File tempFile, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 设定输出字节流内容
		String content = "";
		// 设定时间格式 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		Set<SchoolYear> years= this.findAllYears(0,-1);
		Integer count = 0;
		for (SchoolYear s : years) {
			count++;
			s.getYearStart();
			content +=formatStr(count.toString(),8);// 仪器编号
			if(s.getYearName() != null)
			{
				content += formatStr(s.getYearName(), 8);// 分类号
			}else{
				content += formatStr("*", 8);
			}
			if(s.getYearStart() != null)
			{
				content += formatStr(sdf.format(s.getYearStart().getTime()), 30); // 仪器名称
			}else{
				content += formatStr("*", 30);
			}
			if(s.getYearEnd() != null)
			{
				content += formatStr(sdf.format(s.getYearEnd().getTime()), 30);// 型号
			}else{
				content += formatStr("*", 30);
			}
			if(s.getCode() != null)
			{
				content += formatStr(s.getCode().toString(), 30);// 规格
			}else{
				content += formatStr("*", 30);
			}
			content +=  "\r\n";
		}
		contentToTxt(tempFile, content);
		downLoad(tempFile, "SJ112712", response);
	}
	
	
	/*********************************************************************************
	 * Description：基表模块-{导出excel，教学科研仪器设备表里面的所有设备 school_device_report，本学年}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 ************************************************************************************/
	public void exportReportCurrYear(HttpServletRequest request,
			HttpServletResponse response,int yearcode) throws Exception {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<TableData> dataList = new LinkedList<TableData>();
		List<Map> list1 = new ArrayList();
		List<Object[]> reports1 = schoolDeviceReportCurrYear(yearcode, 1, 1000000);

		for (Object[] s : reports1) {
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("schoolNumber", s[0]);// 学校编号
			map.put("deviceNumber", s[1]);// 仪器编号
			map.put("deviceUdc", s[2]);// 分类号
			map.put("deviceName", s[3]);// 仪器名称
			map.put("devicePattern", s[4]);// 型号
			map.put("deviceFormat", s[5]);// 规格
			map.put("deviceOrigin", s[6]);// 仪器来源
			map.put("deviceCountry", s[7]);// 国别码
			map.put("devicePrice", s[8]);// 单价
			map.put("deviceBuyDate", s[9]);// 购置日期
			map.put("deviceCondition", s[10]);// 现状码
			map.put("useDirection", s[11]);// 使用方向
			map.put("academyNumber", s[12]);// 单位编号
			map.put("academyName", s[13]);// 单位名称
			map.put("scrapTime", s[14]);// 报废日期

			list1.add(map);
		}
		String title1 = "sj112712";
		String[] hearders1 = new String[] { "学校编号", "仪器编号", "分类号", "仪器名称", "型号",
				"规格", "仪器来源", "国别码", "单价", "购置日期", "现状码", "使用方向", "单位编号",
				"单位名称", "报废日期" };// 表头数组
		String[] fields1 = new String[] { "schoolNumber", "deviceNumber",
				"deviceUdc", "deviceName", "devicePattern", "deviceFormat",
				"deviceOrigin", "deviceCountry", "devicePrice",
				"deviceBuyDate", "deviceCondition", "useDirection",
				"academyNumber", "academyName", "scrapTime" };// Financialresources对象属性数组

		TableData td1 = ExcelUtils.createTableData(list1,
				ExcelUtils.createTableHeader(hearders1), fields1);
		td1.setSheetTitle("title1");
		dataList.add(td1);
		
		
		List<Map> list2 = new ArrayList();
		List<SchoolDeviceChangeReport> reports2 = schoolDeviceChangeReport(yearcode);
		for (SchoolDeviceChangeReport s : reports2) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("schoolNumber","12712");// 学校代码
			if(s.getDeviceNumberLast()!=null && !s.getDeviceNumberLast().equals("")){
			map.put("deviceNumberLast", s.getDeviceNumberLast().toString());// 上学年末实有数 台件
			}else
			{
				map.put("deviceNumberLast", null);
			}
			if(s.getDevicePriceLast()!=null && !s.getDevicePriceLast().equals("")){
			map.put("devicePriceLast", s.getDevicePriceLast().toString());// 上学年末实有数 金额
			}else
			{
				map.put("devicePriceLast", null);
			}
			if(s.getDeviceNumberValueLast()!=null && !s.getDeviceNumberValueLast().equals("")){
			map.put("deviceNumberValueLast", s.getDeviceNumberValueLast().toString());// 上学年末实有数 台件（其中10万元（含）以上）
			}else
			{
				map.put("deviceNumberValueLast", null);
			}
			if(s.getDevicePriceValueLast()!=null && !s.getDevicePriceValueLast().equals("")){
			map.put("devicePriceValueLast", s.getDevicePriceValueLast().toString());// 上学年末实有数 金额（其中10万元（含）以上）
			}else
			{
				map.put("devicePriceValueLast", null);
			}
			if(s.getDeviceNumberAdd()!=null && !s.getDeviceNumberAdd().equals("")){
			map.put("deviceNumberAdd", s.getDeviceNumberAdd().toString());//本学年增加数 台件
			}else
			{
				map.put("deviceNumberAdd", null);
			}
			if(s.getDevicePriceAdd()!=null && !s.getDevicePriceAdd().equals("")){
			map.put("devicePriceAdd", s.getDevicePriceAdd().toString());//本学年增加数 金额
			}else
			{
				map.put("getDevicePriceAdd", null);
			}
			if(s.getDeviceNumberReduce()!=null && !s.getDeviceNumberReduce().equals("")){
			map.put("deviceNumberReduce", s.getDeviceNumberReduce().toString());// 本学年减少数 台件
			}else
			{
				map.put("deviceNumberReduce", null);
			}
			if(s.getDevicePriceReduce()!=null && !s.getDevicePriceReduce().equals("")){
			map.put("devicePriceReduce", s.getDevicePriceReduce().toString());// 本学年减少数 金额
			}else
			{
				map.put("devicePriceReduce", null);
			}
			if(s.getDeviceNumberThis()!=null && !s.getDeviceNumberThis().equals("")){
			map.put("deviceNumberThis", s.getDeviceNumberThis().toString());// 本学年末实有数  台件
			}else
			{
				map.put("deviceNumberThis", null);
			}
			if(s.getDevicePriceThis()!=null && !s.getDevicePriceThis().equals("")){
			map.put("devicePriceThis", s.getDevicePriceThis().toString());//本学年末实有数  金额
			}else
			{
				map.put("devicePriceThis", null);
			}
			if(s.getDeviceNumberValueThis()!=null && !s.getDeviceNumberValueThis().equals("")){
			map.put("deviceNumberValueThis", s.getDeviceNumberValueThis().toString());// 本学年末实有数 台件（其中10万元（含）以上）
			}else
			{
				map.put("deviceNumberValueThis", null);
			}
			if(s.getDevicePriceValueThis()!=null && !s.getDevicePriceValueThis().equals("")){
			map.put("devicePriceValueThis", s.getDevicePriceValueThis().toString());// 本学年末实有数 金额（其中10万元（含）以上）
			}else
			{
				map.put("devicePriceValueThis", null);
			}
     		list2.add(map);
		}
		String title2 = "sj212712";
		String[] hearders2 = new String[] { "学校代码", "上学年末实有台件数", "上学年末实有金额数", "上学年末实有 台件数（其中10万元（含）以上）", "上学年末实有金额数 （其中10万元（含）以上）",
				"本学年增加台件数", "本学年增加金额数",  "本学年减少台件数 ", "本学年减少金额数 ", " 本学年末实有台件数  ", "本学年末实有金额数  ", " 本学年末实有台件数（其中10万元（含）以上）",
				"本学年末实有金额数（其中10万元（含）以上）" };// 表头数组
		String[] fields2 = new String[] { "schoolNumber", "deviceNumberLast","devicePriceLast", "deviceNumberValueLast", "devicePriceValueLast",
				                                                                           "deviceNumberAdd",	"devicePriceAdd", "deviceNumberReduce", "devicePriceReduce",
				                                                                           "deviceNumberThis", "devicePriceThis", "deviceNumberValueThis","devicePriceValueThis" };//对象属性数组
		
		TableData td2 = ExcelUtils.createTableData(list2,
				ExcelUtils.createTableHeader(hearders2), fields2);
		td2.setSheetTitle("title2");
		dataList.add(td2);
		
		
		List<Map> list3 = new ArrayList();
		List<Object[]> reports3 = schoolDeviceValueCurrYear(yearcode, 1, 100000);
		for (Object[]s : reports3) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("schoolNumber", s[0]);
			map.put("deviceNumber", s[1]);
			map.put("deviceUdc", s[2]);
			map.put("deviceName", s[3]);
			map.put("devicePrice", s[4]);
			map.put("devicePattern", s[5]);
			map.put("deviceFormat", s[6]); 
			map.put("useHoursTeaching", s[7]);
			map.put("useHoursResearch", s[8]);
			map.put("useHoursService", s[9]);
			map.put("useHoursOpen", s[10]);
			map.put("sampleCount", s[11]);
			map.put("trainNumberTeacher", s[12]);
			map.put("trainNumberStudent", s[13]);
			map.put("trainNumberOther", s[14]);
			map.put("itemTeaching", s[15]);
			map.put("itemResearch", s[16]);
			map.put("itemSocial", s[17]);
			map.put("prizeNation", s[18]); 
			map.put("prizeProvince", s[19]);
			map.put("patentTeacher", s[20]);
			map.put("paperSci", s[21]);
			map.put("paperKey", s[22]);
			map.put("manager", s[23]);
			map.put("manager", s[24]);
			list3.add(map);
		}
		String title3 = "sj312712";
		String[] hearders3 = new String[] { "学校代码", "设备编号", "分类号", "仪器名称", "单价",
				"型号", "规格", "使用机时教学", "使用机时科研", "使用机时社会服务", "使用机时开放使用", "测样数", "培训学生数",
				"培训教师数", "培训人员数" , "教学实验项目数", "科研项目数"
				, "社会服务项目数", "获奖情况国家级", "获奖情况省部级", "发明专利教师"
				, "发明专利教师", "论文情况三大检索", "论文情况核心刊物", 
				"负责人姓名"};// 表头数组
		String[] fields3 = new String[] { "schoolNumber", "deviceNumber",
				"deviceUdc", "deviceName", "devicePrice", "devicePattern",
				"deviceFormat", "useHoursTeaching", "useHoursResearch",
				"useHoursService", "useHoursOpen", "sampleCount",
				"trainNumberTeacher", "trainNumberStudent", "trainNumberOther" , 
				"itemTeaching" , "itemResearch" 
				, "itemSocial" , "prizeNation" , "prizeProvince" 
				, "patentTeacher" , "patentStudent" , "paperSci", "paperKey" 
				, "manager" };//对象属性数组

		TableData td3 = ExcelUtils.createTableData(list3,
				ExcelUtils.createTableHeader(hearders3), fields3);
		dataList.add(td3);
		td3.setSheetTitle("title3");
		
		List<Map> list4 = new ArrayList();
		List<Object[]> reports4 = operationItemTeachingByYear(yearcode, 1, 50);
		for (Object[] s : reports4) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("schoolNumber", s[0]);// 学校代码
			map.put("lpCodeCustom", s[1]);// 实验编号
			map.put("lpName", s[2]);// 实验名称
			map.put("lpCategoryMain", s[3]);// 实验类别
			map.put("lpCategoryApp", s[4]);// 实验类型
			map.put("lpSubject", s[5]);// 实验所属学科
			map.put("lpCategoryRequire", s[6]);// 实验要求
			map.put("lpCategoryStudent", s[7]);// 实验者类别
			map.put("lpStudentNumber", s[8]);// 实验者人数
			map.put("lpStudentNumberGroup", s[9]);// 每组人数
			map.put("lpDepartmentHours", s[10]);// 实验学时数
			map.put("labRoomNumber", s[11]);// 实验室编号
			map.put("labRoomName", s[12]);// 实验室名称
			map.put("yearCode", s[13]);// 学年
			list4.add(map);
		}
		String title4 = "sj412712";
		String[] hearders4 = new String[] { "学校代码", "试验编号", "实验名称", "实验类别", "实验类型",
				"实验所属学科", "实验要求", "实验者类别", "实验者人数", "每组人数", "实验学时数", "实验室编号", "实验室名称" };// 表头数组
		String[] fields4 = new String[] { "schoolNumber", "lpCodeCustom",
				"lpName", "lpCategoryMain", "lpCategoryApp", "lpSubject",
				"lpCategoryRequire", "lpCategoryStudent", "lpStudentNumber",
				"lpStudentNumberGroup", "lpDepartmentHours", "labRoomNumber",
				"labRoomName" };//对象属性数组

		TableData td4 = ExcelUtils.createTableData(list4,
				ExcelUtils.createTableHeader(hearders4), fields4);
		dataList.add(td4);
		td4.setSheetTitle("title4");
		
		List<Map> list5 = new ArrayList();
		List<Object[]> reports5 = labTeam(request, 1, 100000);
		for (Object[]s : reports5) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("schoolNumber", s[0]);// 学校代码
			map.put("lwCodeCustom", s[1]);// 人员编号
			map.put("labRoomNumber", s[2]);// 实验室编号
			map.put("labRoomName", s[3]);// 实验室名称
			map.put("cname", s[4]);// 姓名
			map.put("useSexy", s[5]);// 性别
			map.put("birthday", s[6]);// 出生年月
			map.put("subject", s[7]);// 所属学科
			map.put("lwProfessionSpecialty", s[8]);// 专业技术职务
			map.put("degree", s[9]);// 文化程度
			map.put("categoryExpert", s[10]);// 专家类别
			map.put("trainFormalInlandTime", s[11]);// 国内培训 学历教育时间
			map.put("trainInformalInlandTime", s[12]);// 国内培训 非学历教育时间
			map.put("trainFormalAbroadTime", s[13]);// 国外培训 学历教育时间
			map.put("trainInformalAbroadTime", s[14]);// 国外培训 非学历教育时间
			list5.add(map);
		}
		String title5 = "sj512712";
		String[] hearders5 = new String[] { "学校代码", "人员编号", "实验室编号", "实验室名称", "姓名",
				"性别", "出生年月", "所属学科", "专业技术职务", "文化程度", "专家类别", "国内培训 学历教育时间", "国内培训 非学历教育时间",
				"国外培训 学历教育时间", " 国外培训 非学历教育时间" };// 表头数组
		String[] fields5 = new String[] { "schoolNumber", "lwCodeCustom",
				"labRoomNumber", "labRoomName", "cname", "useSexy",
				"birthday", "subject", "lwProfessionSpecialty",
				"degree", "categoryExpert", "trainFormalInlandTime",
				"trainInformalInlandTime", "trainFormalAbroadTime", "trainInformalAbroadTime" };//对象属性数组

		TableData td5= ExcelUtils.createTableData(list5,
				ExcelUtils.createTableHeader(hearders5), fields5);
		td5.setSheetTitle("title5");
		dataList.add(td5);
		
		
		List<Map> list6 = new ArrayList();
		List<Object[]> reports6 = labBasic(request, 1,100000);
		for (Object[] s : reports6) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("schoolNumber", s[0]);// 学校代码
			map.put("labRoomNumber", s[1]);// 实验室编号
			map.put("labRoomName", s[2]);// 实验室名称
			map.put("labRoomCategory", s[3]);//实验室类别
			map.put("labRoomTimeCreate", s[4]);// 建立年份
			map.put("labRoomArea", s[5]);// 房屋使用面积
			map.put("labRoomType", s[6]);// 实验室类型
			map.put("subject", s[7]);// 所属学科
			map.put("labPrizeNation", s[8]);// 教师获奖与成果 （国家级）
			map.put("labPrizeProvince", s[9]);// 教师获奖与成果 （省部级）
			map.put("labPrizePatent", s[10]);// 教师获奖与成果 （发明专利）
			map.put("labPrizeStudent", s[11]);// 学生获奖情况
			map.put("labPaperSciTeaching", s[12]);// 教学方面论文和教材情况（三大检索收录）
			map.put("labPaperSciResearch", s[13]);// 科研方面论文和教材情况（三大检索收录）
			map.put("labPaperKeyTeaching", s[14]);// 教学方面论文和教材情况（核心刊物）
			map.put("labPaperKeyResearch", s[15]);//科研方面论文和教材情况（核心刊物）
			map.put("labPaperBook", s[16]);// 论文和教材情况（实验教材）
			map.put("labResearchNation", s[17]);// 科研及社会服务情况中科研项目数（省部级以上）
			map.put("labResearchOther", s[18]);// 科研及社会服务情况中科研项目数（其它）
			map.put("labService", s[19]);// 科研及社会服务情况中社会服务项目数
			map.put("labTeachingNation", s[20]);// 科研及社会服务情况中教研项目数（省部级以上）
			map.put("labTeachingOther", s[21]);// 科研及社会服务情况中教研项目数（其它）
			map.put("labGraduateMaster", s[22]);// 毕业设计和论文人数（专科生人数）
			map.put("labGraduateBachelor", s[23]);// 毕业设计和论文人数（本科生人数）
			map.put("labGraduateOther", s[24]);// 毕业设计和论文人数（研究生人数）
			map.put("labOpenItemCountInner", s[25]);// 开放实验个数（校内）
			map.put("labOpenItemCountOutter", s[26]);// 开放实验个数（校外）
			map.put("labOpenItemStudentInner", s[27]);// 开放实验人数（校内）
			map.put("labOpenItemStudentOutter", s[28]);// 开放实验人数（校外）
			map.put("labOpenItemHourInner", s[29]);// 开放实验人时数（校内）
			map.put("labOpenItemHourOutter", s[30]);// 开放实验人时数（校外）
			map.put("labPartTime", s[31]);// 兼任人员数
			map.put("labCostTotal", s[32]);//实验教学运行经费小计（万元,保留两位小数）
			map.put("labCostTeaching", s[33]);//实验教学运行经费（其中教学实验 年材料消耗费）
			list6.add(map);
		}
		String title6 = "sj612712";
		String[] hearders6 = new String[] { "学校代码", "实验室编号", "实验室名称", "实验室类别", "建立年份",	"房屋使用面积", 
				                                                   "实验室类型", "所属学科", "教师获奖与成果 （国家级）", "教师获奖与成果 （省部级）", " 教师获奖与成果 （发明专利）", "学生获奖情况",
				                                                   " 教学方面论文和教材情况（三大检索收录）", " 科研方面论文和教材情况（三大检索收录）", "科研方面论文和教材情况（核心刊物）", 
				                                                   " 论文和教材情况（实验教材）", "科研及社会服务情况中科研项目数（省部级以上）", "科研及社会服务情况中科研项目数（其它）",
				                                                   "科研及社会服务情况中社会服务项目数", "科研及社会服务情况中教研项目数（省部级以上）", " 科研及社会服务情况中教研项目数（其它）",
				                                                   "毕业设计和论文人数（专科生人数）", "毕业设计和论文人数（本科生人数）", "毕业设计和论文人数（研究生人数）",
				                                                   "开放实验个数（校内）", "开放实验个数（校外）", "开放实验人数（校内）", "开放实验人数（校外）", " 开放实验人时数（校内）", "开放实验人时数（校外）",
				                                                   "兼任人员数", "实验教学运行经费小计", "实验教学运行经费","其中教学实验 年材料消耗费"
		};// 表头数组
		String[] fields6 = new String[] { "schoolNumber", "labRoomNumber","labRoomName", "labRoomCategory", "labRoomTimeCreate", "labRoomArea",
				                                              "labRoomType", "subject","labPrizeNation", "labPrizeProvince", "labPrizePatent", "labPrizeStudent",
				                                              "labPaperSciTeaching", "labPaperSciResearch","labPaperKeyTeaching", "labPaperKeyResearch", "labPaperBook", "labResearchNation",
				                                              "labResearchOther", "labService", "labTeachingNation","labTeachingOther", "labGraduateMaster", "labGraduateBachelor",
				                                              "labGraduateOther","labOpenItemCountInner","labOpenItemCountOutter","labOpenItemStudentInner", "labOpenItemStudentOutter", 
	                                                          "labOpenItemHourInner",  "labOpenItemHourOutter", "labPartTime", "labCostTotal","labCostTeaching" };//对象属性数组

		TableData td6 = ExcelUtils.createTableData(list6,
				ExcelUtils.createTableHeader(hearders6), fields6);
		td6.setSheetTitle("title6");
		dataList.add(td6);
		
		List<Map> list7 = new ArrayList();
		List<Object[]> reports7 = labRoomReportFund(request, 1, 100000);
		for (Object[]s : reports7) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("schoolNumber",12712 );// 学校代码
			map.put("labCount", s[2]);// 实验室个数
			map.put("labRoomArea", s[3]);// 实验室面积
			map.put("labFundTotal", s[4]);// 经费投入（总计）
			map.put("labDeviceFundTotal", s[5]);//仪器设备购置经费(总计)
			map.put("labDeviceFundTeaching", s[6]);//仪器设备购置经费(教学)
			map.put("labDeviceRepairTotal", s[7]);//仪器设备维护经费(总计)
			map.put("labDeviceRepairTeaching", s[8]);// 仪器设备维护费(教学)
			map.put("labTeachFundTotal", s[9]);// 实验教学运行经费(总计)
			map.put("labTeachFundExpendable", s[10]);// 实验教学运行经费(材料消耗)
			map.put("labConstructionFund", s[11]);// 实验室建设经费
			map.put("labResearchFund", s[12]);// 实验教学研究与改革经费
			map.put("other_fund", s[13]);//其他经费
			list7.add(map);
		}
		String title7 = "sj712712";
		String[] hearders7 = new String[] { "学校代码", "实验室个数"," 实验室面积", "经费投入（总计）","仪器设备购置经费(总计)", "仪器设备购置经费(教学)",
				                                                   "仪器设备维护经费(总计)", "仪器设备维护费(教学)","实验教学运行经费(总计)", "实验教学运行经费(材料消耗)","实验室建设经费", "实验教学研究与改革经费",
				                                                   "其他经费"};// 表头数组
		String[] fields7 = new String[] { "schoolNumber", "labCount","labRoomArea","labFundTotal", "labDeviceFundTotal","labDeviceFundTeaching",
				                                             "labDeviceRepairTotal", "labDeviceRepairTeaching","labTeachFundTotal", "labTeachFundExpendable","labConstructionFund", 
				                                             "labResearchFund", "other_fund"};//对象属性数组
		TableData td7 = ExcelUtils.createTableData(list7,
				ExcelUtils.createTableHeader(hearders7), fields7);
		td7.setSheetTitle("title7");
		dataList.add(td7);
		
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportToExcel(title1, shareService.getUser().getCname(), dataList);// title未表头名，中间的参数为获取当前登陆用户的用户名后传入创建人，td即当前表的生成时间。效果如下
	}

	/*********************************************************************************
	 * Description：基表模块-{查出教学科研仪器设备表里面的所有设备 school_device_report}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 ************************************************************************************/
	@SuppressWarnings({ "unchecked" })
	public List<Object[]> schoolDeviceReportCurrYear(int yearCode,int page,
			int pageSize) {
		String sql = "select * from basic_data_one s where 1=1";
		sql += " and s.yearId = " + yearCode+ " ";
		javax.persistence.Query query = entityManager.createNativeQuery(sql);
		// 以下两行是分页设置
		query.setMaxResults(pageSize);
		query.setFirstResult((page - 1) * pageSize);
		// 获取list对象
		List<Object[]> list = query.getResultList();
		return list;
	}
	
	/*********************************************************************************
	 * Description：基表模块-{导出excel，教学科研仪器设备表贵重仪器设备 school_device_value}
	 * 
	 * @author：魏来
	 * @date：2016-8-24
	 ************************************************************************************/
	@SuppressWarnings("unchecked")
	public void exportCurrYearSchoolDeviceValue(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		@SuppressWarnings("rawtypes")
		List<Map> list = new ArrayList();
		List<Object[]> reports = schoolDeviceValue(request, 1, 100000);
		for (Object[]s : reports) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("schoolNumber", s[0]);
			map.put("deviceNumber", s[1]);
			map.put("deviceUdc", s[2]);
			map.put("deviceName", s[3]);
			map.put("devicePrice", s[4]);
			map.put("devicePattern", s[5]);
			map.put("deviceFormat", s[6]); 
			map.put("useHoursTeaching", s[7]);
			map.put("useHoursResearch", s[8]);
			map.put("useHoursService", s[9]);
			map.put("useHoursOpen", s[10]);
			map.put("sampleCount", s[11]);
			map.put("trainNumberTeacher", s[12]);
			map.put("trainNumberStudent", s[13]);
			map.put("trainNumberOther", s[14]);
			map.put("itemTeaching", s[15]);
			map.put("itemResearch", s[16]);
			map.put("itemSocial", s[17]);
			map.put("prizeNation", s[18]); 
			map.put("prizeProvince", s[19]);
			map.put("patentTeacher", s[20]);
			map.put("paperSci", s[21]);
			map.put("paperKey", s[22]);
			map.put("manager", s[23]);
			map.put("manager", s[24]);
			list.add(map);
		}
		String title = "sj312712";
		String[] hearders = new String[] { "学校代码", "设备编号", "分类号", "仪器名称", "单价",
				"型号", "规格", "使用机时教学", "使用机时科研", "使用机时社会服务", "使用机时开放使用", "测样数", "培训学生数",
				"培训教师数", "培训人员数" , "教学实验项目数", "科研项目数"
				, "社会服务项目数", "获奖情况国家级", "获奖情况省部级", "发明专利教师"
				, "发明专利教师", "论文情况三大检索", "论文情况核心刊物", 
				"负责人姓名"};// 表头数组
		String[] fields = new String[] { "schoolNumber", "deviceNumber",
				"deviceUdc", "deviceName", "devicePrice", "devicePattern",
				"deviceFormat", "useHoursTeaching", "useHoursResearch",
				"useHoursService", "useHoursOpen", "sampleCount",
				"trainNumberTeacher", "trainNumberStudent", "trainNumberOther" , 
				"itemTeaching" , "itemResearch" 
				, "itemSocial" , "prizeNation" , "prizeProvince" 
				, "patentTeacher" , "patentStudent" , "paperSci", "paperKey" 
				, "manager" };//对象属性数组

		TableData td = ExcelUtils.createTableData(list,
				ExcelUtils.createTableHeader(hearders), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportToExcel(title, shareService.getUser().getCname(), td);// title未表头名，中间的参数为获取当前登陆用户的用户名后传入创建人，td即当前表的生成时间。效果如下
	}
	
	/*********************************************************************************
	 * Description：基表模块-{查出教学科研仪器设备表里面的贵重仪器设备 school_device_value 单价大于400000}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 ************************************************************************************/
	@SuppressWarnings({ "unchecked" })
	public List<Object[]> schoolDeviceValueCurrYear(int yearCode, int page,
			int pageSize) {
		String sql = "select * from basic_data_three v where 1=1";
		sql += " and v.yearId = " + yearCode+ "";
		javax.persistence.Query query = entityManager.createNativeQuery(sql);
		// 以下两行是分页设置
		query.setMaxResults(pageSize);
		query.setFirstResult((page - 1) * pageSize);
		// 获取list对象
		List<Object[]> list = query.getResultList();
		return list;
	}
	
	/*********************************************************************************
	 * Description：基表模块-{导出excel，教学科研仪器设备表里面的所有设备 school_device_report，本学年}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 ************************************************************************************/
	public void exportReportTxtCurrYear(HttpServletRequest request,
			HttpServletResponse response,int yearcode) throws Exception {
		
		List<File> files = new LinkedList<File>();
		/** 得到文件保存目录的真实路径* */
	    String logoRealPathDir1 = this.getClass().getClassLoader().getResource("/").getPath(); 
	    
	    /** 临时文件放在webapps/property/WEB-INF/classes/temp.txt* */
	    File tempFile1 = new File(logoRealPathDir1+File.separator+"temp1.txt");
	    if (!tempFile1.exists()){
	    	tempFile1.createNewFile();  
	    }
		// 设定输出字节流内容
		String content1 = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		List<Object[]> reports = schoolDeviceReportCurrYear(yearcode, 1, 10000);
		for (Object[] s : reports) {
				content1 += "12712";// 学校代码
				if(s[1] != null)
				{
					content1 += formatStr(s[1].toString().trim(), 8);// 仪器编号
				}
				else{
					content1 += formatStr("*", 3);
				}
				if(s[2] != null)
				{
					content1 += formatStr(s[2].toString().trim(), 8);// 分类号
				}
				else{
					content1 += formatStr("*", 8);
				}
				if(s[3] != null)
				{
					content1 += formatStr(s[3].toString().trim(), 30); // 仪器名称
				}
				else{
					content1 += formatStr("*", 30);
				}
				if(s[4] != null)
				{
					content1 += formatStr(s[4].toString().trim(), 20);// 型号
				}else{
					content1 += formatStr("*", 20);
				}
				if(s[5] != null)
				{
					content1 += formatStr(s[5].toString().trim(), 30);// 规格
				}else{
					content1 += formatStr("*", 30);
				}
				if(s[6] != null)
				{
					content1 += formatStr(s[6].toString().trim(), 30);// 规格
				}else{
					content1 += formatStr("*", 30);
				}
				if(s[7] != null)
				{
					content1 += formatStr(s[5].toString().trim(), 20);// 规格
				}else{
					content1 += formatStr("*", 20);
				}
				if(s[8] != null)
				{
					content1 += formatStr(s[8].toString().trim(), 12);// 单价
				}else{
					content1 += formatStr("*", 12);
				}
				if(s[9] != null)
				{
					content1 += formatStr(s[9].toString().trim(), 6);// 购置日期
				}else{
					content1 += formatStr("*", 6);
				}
				if(s[10] != null)
				{
					content1 += formatStr(s[10].toString().trim(), 2);// 现状码
				}else{
					content1 += formatStr("*", 2);
				}
				if(s[11] != null)
				{
					content1 += formatStr(s[5].toString().trim(), 20);// 使用方向
				}else{
					content1 += formatStr("*", 20);
				}
				if(s[12] != null)
				{
					content1 += formatStr(s[12].toString().trim(), 10);// 单位编号
				}else{
					content1 += formatStr("*", 10);
				}
				if(s[13] != null)
				{
					content1 += formatStr(s[13].toString().trim(), 50);// 单位名称
				}else{
					content1 += formatStr("*", 50);
				}
				if(s[14] != null)
				{
					content1 += formatStr(s[14].toString().trim(), 6);// 报废日期
				}else{
					content1 += formatStr("*", 6);
				}
				content1 += "\r\n";
			}
			contentToTxt(tempFile1, content1);
			files.add(tempFile1);
			
			 /** 得到文件保存目录的真实路径* */
		    String logoRealPathDir2 = this.getClass().getClassLoader().getResource("/").getPath(); 
		    
		  /** 临时文件放在webapps/property/WEB-INF/classes/temp.txt* */
		    File tempFile2 = new File(logoRealPathDir2+File.separator+"temp2.txt");
		    if (!tempFile2.exists()){
		    	tempFile2.createNewFile();  
		    }
			Integer yearCode = 0;
			if(request.getParameter("yearCode") != null && !request.getParameter("yearCode").equals("")) {
				yearCode = Integer.valueOf(request.getParameter("yearCode"));
			}
			//查询出所有的专用设备设备
			List<SchoolDeviceChangeReport> listEquipments= this.schoolDeviceChangeReport(yearCode);
			// 设定输出字节流内容
			String content2 = "";
			// 设定时间格式
			@SuppressWarnings("unused") 
			List<SchoolDeviceChangeReport> reports2 = schoolDeviceChangeReport( yearcode);
			for (SchoolDeviceChangeReport s : reports2) {
				String cell1,cell2,cell3,cell4,cell5,cell6,cell7,cell8,cell9,cell10,cell11,cell12;
				if(s.getDeviceNumberLast()!=null&&!s.getDeviceNumberLast().equals("") ){
					cell1 = formatStr(s.getDeviceNumberLast().toString().trim(), 8);
				}else{
					cell1 = formatStr("*", 8);
				}
				if(s.getDevicePriceLast()!=null && !s.getDevicePriceLast().equals("")){
					cell2 = formatStr(s.getDevicePriceLast().toString().trim(), 11);
				}else{
					cell2 = formatStr("*", 11);
				}
				if(s.getDeviceNumberValueLast()!=null &&!s.getDeviceNumberValueLast().equals("")){
					cell3=formatStr(s.getDeviceNumberValueLast().toString().toString(),6);// 上学年末实有数 台件（其中10万元（含）以上）
				}else
					{
						cell3 = formatStr("*", 6);
					}
				if(s.getDevicePriceValueLast()!=null && !s.getDevicePriceValueLast().equals("")){
					cell4=formatStr(s.getDevicePriceValueLast().toString(), 9);// 上学年末实有数 金额（其中10万元（含）以上）
				}else
					{
					cell4 = formatStr("*", 9);
					}
				if(s.getDeviceNumberAdd()!=null && !s.getDeviceNumberAdd().equals("")){
					cell5=formatStr(s.getDeviceNumberAdd().toString(), 6);//本学年增加数 台件
				}else
					{
					    cell5 = formatStr("*", 6);
					}
					if(s.getDevicePriceAdd()!=null && !s.getDevicePriceAdd().equals("")){
					    cell6=formatStr(s.getDevicePriceAdd().toString(),9);//本学年增加数 金额
					}else
					{
						cell6 = formatStr("*", 9);
					}
					if(s.getDeviceNumberReduce()!=null && !s.getDeviceNumberReduce().equals("")){
					    cell7=formatStr(s.getDeviceNumberReduce().toString(), 6);// 本学年减少数 台件
					}else
					{
						cell7 = formatStr("*", 6);
					}
					if(s.getDevicePriceReduce()!=null && !s.getDevicePriceReduce().equals("")){
					    cell8=formatStr(s.getDevicePriceReduce().toString(), 9);// 本学年减少数 金额
					}else
					{
						cell8 = formatStr("*", 9);
					}
					if(s.getDeviceNumberThis()!=null && !s.getDeviceNumberThis().equals("")){
					    cell9=formatStr(s.getDeviceNumberThis().toString(), 8);// 本学年末实有数  台件
					}else
					{
						cell9 = formatStr("*", 8);
					}
					if(s.getDevicePriceThis()!=null && !s.getDevicePriceThis().equals("")){
					    cell10=formatStr(s.getDevicePriceThis().toString(), 11);//本学年末实有数  金额
					}else
					{
						cell10 = formatStr("*", 11);
					}
					if(s.getDeviceNumberValueThis()!=null && !s.getDeviceNumberValueThis().equals("")){
					    cell11=formatStr(s.getDeviceNumberValueThis().toString(), 6);// 本学年末实有数 台件（其中10万元（含）以上）
					}else
					{
						cell11 = formatStr("*", 6);
					}
					if(s.getDevicePriceValueThis()!=null && !s.getDevicePriceValueThis().equals("")){
					    cell12=formatStr( s.getDevicePriceValueThis().toString(), 9);// 本学年末实有数 金额（其中10万元（含）以上）
					}else
					{
						cell12 = formatStr("*", 9);
					}
				content2 += "12712"// 学校代码
						+cell1+cell2+cell3+cell4+cell5+cell6+cell7+cell8+cell9+cell10+cell11
						+cell12
						+ "\r\n";
			}
			contentToTxt(tempFile2, content2);
			files.add(tempFile2);
			
			
			 /** 得到文件保存目录的真实路径* */
		    String logoRealPathDir3 = this.getClass().getClassLoader().getResource("/").getPath(); 
		    
		    /** 临时文件放在webapps/property/WEB-INF/classes/temp.txt* */
		    File tempFile3 = new File(logoRealPathDir3+File.separator+"temp3.txt");
		    if (!tempFile3.exists()){
		    	tempFile3.createNewFile();  
		    }

			//查询出所有的专用设备设备 
			List<Object[]> listEquipments3= this.schoolDeviceValueCurrYear(yearcode,1,100000);
			String content3 = "";
			// 设定时间格式
			@SuppressWarnings("unused") 
			List<Object[]> reports3 = schoolDeviceValue(request, 1, 50);
			for (Object[] s : reports3) {
				String cell1,cell2,cell3,cell4,cell5,cell6,cell7,cell8,cell9,cell10,cell11,cell12,cell13,cell14,cell15,cell16,cell17,cell18,cell19,cell20,cell21,cell22,cell23,cell24;
				if(s[1]!=null ){
					cell1 = formatStr(s[1].toString().trim(), 8);
				}else{
					cell1 = formatStr("*", 8);
				}
				if(s[2]!=null ){
					cell2 = formatStr(s[2].toString().trim(),8);
				}else{
					cell2 = formatStr("*",8);
				}
				if(s[3]!=null ){
					cell3 = formatStr(s[3].toString().trim(), 30);
				}else{
					cell3 = formatStr("*", 30);
				}
				if(s[4]!=null ){
					cell4 = formatStr(s[4].toString().trim(), 12);
				}else{
					cell4 = formatStr("*", 12);
				}
				if(s[5]!=null ){
					cell5 = formatStr(s[5].toString().trim(), 20);
				}else{
					cell5 = formatStr("*", 20);
				}
				if(s[6]!=null ){
					cell6 = formatStr(s[6].toString().trim(), 200);
				}else{
					cell6 = formatStr("*", 200);
				}
				if(s[7]!=null ){
					cell7 = formatStr(s[7].toString().trim(), 4);
				}else{
					cell7 = formatStr("0", 4);
				}
				if(s[8]!=null ){
					cell8 = formatStr(s[8].toString().trim(), 4);
				}else{
					cell8 = formatStr("0", 4);
				}
				if(s[9]!=null ){
					cell9 = formatStr(s[9].toString().trim(), 4);
				}else{
					cell9 = formatStr("0", 4);
				}
				if(s[10]!=null ){
					cell10 = formatStr(s[10].toString().trim(), 4);
				}else{
					cell10 = formatStr("*", 4);
				}
				if(s[11]!=null ){
					cell11 = formatStr(s[11].toString().trim(),6);
				}else{
					cell11 = formatStr("*", 6);
				}
				if(s[12]!=null ){
					cell12 = formatStr(s[12].toString().trim(), 4);
				}else{
					cell12 = formatStr("*", 4);
				}
				if(s[13]!=null ){
					cell13 = formatStr(s[13].toString().trim(), 4);
				}else{
					cell13 = formatStr("*", 4);
				}
				if(s[14]!=null ){
					cell14 = formatStr(s[14].toString().trim(), 4);
				}else{
					cell14 = formatStr("*", 4);
				}
				if(s[15]!=null ){
					cell15 = formatStr(s[15].toString().trim(), 3);
				}else{
					cell15 = formatStr("*", 3);
				}
				if(s[16]!=null ){
					cell16 = formatStr(s[16].toString().trim(), 3);
				}else{
					cell16 = formatStr("*", 3);
				}
				if(s[17]!=null ){
					cell17 = formatStr(s[17].toString().trim(), 4);
				}else{
					cell17 = formatStr("*", 4);
				}
				if(s[18]!=null ){
					cell18 = formatStr(s[18].toString().trim(), 2);
				}else{
					cell18 = formatStr("*", 2);
				}
				if(s[19]!=null ){
					cell19 = formatStr(s[19].toString().trim(), 2);
				}else{
					cell19 = formatStr("*", 2);
				}
				if(s[20]!=null ){
					cell20 = formatStr(s[20].toString().trim(), 2);
				}else{
					cell20 = formatStr("*", 2);
				}
				if(s[21]!=null ){
					cell21 = formatStr(s[21].toString().trim(), 2);
				}else{
					cell21 = formatStr("*", 2);
				}
				if(s[22]!=null ){
					cell22 = formatStr(s[22].toString().trim(), 3);
				}else{
					cell22 = formatStr("*", 3);
				}
				if(s[23]!=null ){
					cell23 = formatStr(s[23].toString().trim(), 3);
				}else{
					cell23 = formatStr("*", 3);
				}
				if(s[24]!=null ){
					cell24 = formatStr(s[24].toString().trim(), 8);
				}else{
					cell24 = formatStr("无", 8);
				}
				content3 += "12712"// 学校代码
						+cell1+cell2+cell3+cell4+cell5+cell6+cell7+cell8+cell9+cell10+cell11
						+cell12+cell13+cell14+cell15+cell16+cell17+cell18+cell19+cell20+cell21+cell22+cell23+cell24
						+ "\r\n";
			}
			contentToTxt(tempFile3, content3);
			files.add(tempFile3);
			
			   /** 得到文件保存目录的真实路径* */
		    String logoRealPathDir4 = this.getClass().getClassLoader().getResource("/").getPath(); 
		    
		    /** 临时文件放在webapps/property/WEB-INF/classes/temp.txt* */
		    File tempFile4 = new File(logoRealPathDir4+File.separator+"temp4.txt");
		    if (!tempFile4.exists()){
		    tempFile4.createNewFile();  
		    }
			//查询出所有的专用设备设备
		    @SuppressWarnings("unchecked")
			List<Object[]> listEquipments4= this.operationItemTeaching(request,1,50);
			// 设定输出字节流内容
			String content4 = "";
			// 设定时间格式
			@SuppressWarnings("unused") 
			List<Object[]> reports4 = operationItemTeachingByYear(yearcode, 1, 50);
			for (Object[] s : reports4) {
				String cell1,cell2,cell3,cell4,cell5,cell6,cell7,cell8,cell9,cell10,cell11,cell12;
				if(s[1]!=null ){
					cell1 = formatStr(s[1].toString().trim(), 13);
				}else{
					cell1 = formatStr("*", 13);
				}
				if(s[2]!=null ){
					cell2 = formatStr(s[2].toString().trim(),50);
				}else{
					cell2 = formatStr("*",50);
				}
				if(s[3]!=null ){
					cell3 = formatStr(s[3].toString().trim(), 1);
				}else{
					cell3 = formatStr("*", 1);
				}
				if(s[4]!=null ){
					cell4 = formatStr(s[4].toString().trim(), 2);
				}else{
					cell4 = formatStr("*", 2);
				}
				if(s[5]!=null ){
					cell5 = formatStr(s[5].toString().trim(), 4);
				}else{
					cell5 = formatStr("*", 4);
				}
				if(s[6]!=null ){
					cell6 = formatStr(s[6].toString().trim(), 1);
				}else{
					cell6 = formatStr("*", 1);
				}
				if(s[7]!=null ){
					cell7 = formatStr(s[7].toString().trim(), 1);
				}else{
					cell7 = formatStr("0", 1);
				}
				if(s[8]!=null ){
					cell8 = formatStr(s[8].toString().trim(), 6);
				}else{
					cell8 = formatStr("0", 6);
				}
				if(s[9]!=null ){
					cell9 = formatStr(s[9].toString().trim(), 2);
				}else{
					cell9 = formatStr("0", 2);
				}
				if(s[10]!=null ){
					cell10 = formatStr(s[10].toString().trim(), 4);
				}else{
					cell10 = formatStr("*", 4);
				}
				if(s[11]!=null ){
					cell11 = formatStr(s[11].toString().trim(),10);
				}else{
					cell11 = formatStr("*", 10);
				}
				if(s[12]!=null ){
					cell12 = formatStr(s[12].toString().trim(), 50);
				}else{
					cell12 = formatStr("*", 50);
				}
				content4 += "12712"// 学校代码
						+cell1+cell2+cell3+cell4+cell5+cell6+cell7+cell8+cell9+cell10+cell11
						+cell12
						+ "\r\n";
			}
			contentToTxt(tempFile4, content4);
			files.add(tempFile4);
			
			/** 得到文件保存目录的真实路径* */
		    String logoRealPathDir5 = this.getClass().getClassLoader().getResource("/").getPath(); 
		    
		    /** 临时文件放在webapps/property/WEB-INF/classes/temp.txt* */
		    File tempFile5 = new File(logoRealPathDir5+File.separator+"temp5.txt");
		    if (!tempFile5.exists()){
		    	tempFile5.createNewFile();  
		    }
			//查询出所有的专用设备设备
		    @SuppressWarnings("unchecked")
			List<Object[]> listEquipments5= this.labTeam(request,1,50);
			// 设定输出字节流内容
			String content5 = "";
			// 设定时间格式
			@SuppressWarnings("unused") 
			List<Object[]> reports5 = labTeam(request, 1, 50);
			for (Object[] s : reports5) {

				content5 += "12712";// 学校代码
				if(s[1] != null)
				{
					content5 += formatStr(s[1].toString().trim(), 7);// 人员编号
				}else{
					content5 += formatStr("*", 7);// 规格
				}
				if(s[2] != null)
				{
					content5+= formatStr(s[2].toString().trim(), 10);// 实验室编号
				}else{
					content5 += formatStr("*", 10);// 规格
				}
				if(s[3] != null)
				{
					content5 += formatStr(s[3].toString().trim(), 50); // 实验室名称
				}else{
					content5 += formatStr("*", 50);// 规格
				}
				if(s[4] != null)
				{
					content5 +=formatStr(s[4].toString().trim(), 8);// 姓名
				}else{
					content5 += formatStr("*", 8);// 规格
				}
				if(s[5] != null)
				{
					content5 +=formatStr(s[5].toString().trim(), 4);// 性别
				}else{
					content5 += formatStr("*", 4);// 规格
				}
				if(s[6] != null)
				{
					content5 += formatStr(s[6].toString().trim(), 6);// 出生年月
				}else{
					content5 += formatStr("*", 6);// 规格
				}
				if(s[7] != null)
				{
					content5 += formatStr(s[7].toString().trim(), 4);// 所属学科
				}else{
					content5 += formatStr("*", 4);// 规格
				}
				if(s[8] != null)
				{
					content5 += formatStr(s[8].toString().trim(), 3);// 专业技术职务
				}else{
					content5 += formatStr("*", 3);// 规格
				}
				if(s[9] != null)
				{
					content5 +=formatStr(s[9].toString().trim(),  2);// 文化程度
				}else{
					content5 += formatStr("*", 2);// 规格
				}
				if(s[10] != null)
				{
					content5 += formatStr(s[10].toString().trim(), 2);// 专家类别
				}else{
					content5 += formatStr("*", 2);// 规格
				}
				if(s[11] != null)
				{
					content5 += formatStr(s[11].toString().trim(), 3);// 国内培训 学历教育时间
				}else{
					content5 += formatStr("*", 3);// 规格
				}
				if(s[12] != null)
				{
					content5 += formatStr(s[12].toString().trim(), 3);// 国内培训 非学历教育时间
				}else{
					content5 += formatStr("*", 3);// 规格
				}
				if(s[13] != null)
				{
					content5 += formatStr(s[13].toString().trim(), 3);// 国外培训 学历教育时间
				}else{
					content5 += formatStr("*", 3);// 规格
				}
				if(s[14] != null)
				{
					content5 += formatStr(s[14].toString().trim(), 3);// 国外培训 非学历教育时间
				}else{
					content5 += formatStr("*", 3);// 规格
				}
				content5 += "\r\n";
			}
			contentToTxt(tempFile5, content5); 
			files.add(tempFile5);
			
			 /** 得到文件保存目录的真实路径* */
		    String logoRealPathDir6 = this.getClass().getClassLoader().getResource("/").getPath(); 
		    
		    /** 临时文件放在webapps/property/WEB-INF/classes/temp.txt* */
		    File tempFile6 = new File(logoRealPathDir6+File.separator+"temp6.txt");
		    if (!tempFile6.exists()){
		    	tempFile6.createNewFile();  
		    }
			//查询出所有的专用设备设备
		    @SuppressWarnings("unchecked")
			List<Object[]> listEquipments6= this.labBasic(request,1,50);
			// 设定输出字节流内容
			String content6 = "";
			// 设定时间格式
			@SuppressWarnings("unused") 
			List<Object[]> reports6 = labBasic(request, 1, 50);
			for (Object[] s : reports6) {
				String cell1,cell2,cell3,cell4,cell5,cell6,cell7,cell8,cell9,cell10,cell11,cell12,cell13,cell14,cell15,cell16,cell17,cell18,
				cell19,cell20,cell21,cell22,cell23,cell24,cell25,cell26,cell27,cell28,cell29,cell30,cell31,cell32,cell33;
				if(s[1]!=null ){
					cell1 = formatStr(s[1].toString().trim(), 10);
				}else{
					cell1 = formatStr("*", 10);
				}
				if(s[2]!=null ){
					cell2 = formatStr(s[2].toString().trim(),50);
				}else{
					cell2 = formatStr("*",50);
				}
				if(s[3]!=null ){
					cell3 = formatStr(s[3].toString().trim(), 1);
				}else{
					cell3 = formatStr("*", 1);
				}
				if(s[4]!=null ){
					cell4 = formatStr(s[4].toString().trim(), 4);
				}else{
					cell4 = formatStr("*", 4);
				}
				if(s[5]!=null ){
					cell5 = formatStr(s[5].toString().trim(), 6);
				}else{
					cell5 = formatStr("*", 6);
				}
				if(s[6]!=null ){
					cell6 = formatStr(s[6].toString().trim(), 1);
				}else{
					cell6 = formatStr("*", 1);
				}
				if(s[7]!=null ){
					cell7 = formatStr(s[7].toString().trim(), 4);
				}else{
					cell7 = formatStr("0", 4);
				}
				if(s[8]!=null ){
					cell8 = formatStr(s[8].toString().trim(), 2);
				}else{
					cell8 = formatStr("0", 2);
				}
				if(s[9]!=null ){
					cell9 = formatStr(s[9].toString().trim(), 2);
				}else{
					cell9 = formatStr("0", 2);
				}
				if(s[10]!=null ){
					cell10 = formatStr(s[10].toString().trim(), 2);
				}else{
					cell10 = formatStr("*", 2);
				}
				if(s[11]!=null ){
					cell11 = formatStr(s[11].toString().trim(),2);
				}else{
					cell11 = formatStr("*", 2);
				}
				if(s[12]!=null ){
					cell12 = formatStr(s[12].toString().trim(), 3);
				}else{
					cell12 = formatStr("*", 3);
				}
				if(s[13]!=null ){
					cell13 = formatStr(s[13].toString().trim(), 3);
				}else{
					cell13 = formatStr("*", 3);
				}
				if(s[14]!=null ){
					cell14 = formatStr(s[14].toString().trim(), 3);
				}else{
					cell14 = formatStr("*", 3);
				}
				if(s[15]!=null ){
					cell15 = formatStr(s[15].toString().trim(), 3);
				}else{
					cell15 = formatStr("*", 3);
				}
				if(s[16]!=null ){
					cell16 = formatStr(s[16].toString().trim(), 2);
				}else{
					cell16 = formatStr("*", 2);
				}
				if(s[17]!=null ){
					cell17 = formatStr(s[17].toString().trim(), 2);
				}else{
					cell17 = formatStr("*", 2);
				}
				if(s[18]!=null ){
					cell18 = formatStr(s[18].toString().trim(), 3);
				}else{
					cell18 = formatStr("*", 3);
				}
				if(s[19]!=null ){
					cell19 = formatStr(s[19].toString().trim(), 3);
				}else{
					cell19 = formatStr("*", 3);
				}
				if(s[20]!=null ){
					cell20 = formatStr(s[20].toString().trim(), 3);
				}else{
					cell20 = formatStr("*", 3);
				}
				if(s[21]!=null ){
					cell21 = formatStr(s[21].toString().trim(), 3);
				}else{
					cell21 = formatStr("*", 3);
				}
				if(s[22]!=null ){
					cell22 = formatStr(s[22].toString().trim(), 4);
				}else{
					cell22 = formatStr("*", 4);
				}
				if(s[23]!=null ){
					cell23 = formatStr(s[23].toString().trim(), 4);
				}else{
					cell23 = formatStr("*", 4);
				}
				if(s[24]!=null ){
					cell24 = formatStr(s[24].toString().trim(), 4);
				}else{
					cell24 = formatStr("*", 4);
				}
				if(s[25]!=null ){
					cell25 = formatStr(s[25].toString().trim(), 6);
				}else{
					cell25 = formatStr("*", 6);
				}
				if(s[26]!=null ){
					cell26 = formatStr(s[26].toString().trim(),6);
				}else{
					cell26 = formatStr("*",6);
				}
				if(s[27]!=null ){
					cell27 = formatStr(s[27].toString().trim(), 6);
				}else{
					cell27= formatStr("*", 6);
				}
				if(s[28]!=null ){
					cell28 = formatStr(s[28].toString().trim(), 6);
				}else{
					cell28 = formatStr("*", 6);
				}
				if(s[29]!=null ){
					cell29 = formatStr(s[29].toString().trim(), 8);
				}else{
					cell29 = formatStr("*", 8);
				}
				if(s[30]!=null ){
					cell30 = formatStr(s[30].toString().trim(), 6);
				}else{
					cell30 = formatStr("*", 6);
				}
				if(s[31]!=null ){
					cell31 = formatStr(s[31].toString().trim(), 3);
				}else{
					cell31 = formatStr("0", 3);
				}
				if(s[32]!=null ){
					cell32 = formatStr(s[32].toString().trim(), 8);
				}else{
					cell32 = formatStr("0", 8);
				}
				if(s[33]!=null ){
					cell33 = formatStr(s[33].toString().trim(), 8);
				}else{
					cell33 = formatStr("0", 8);
				}
				content6 += "12712"// 学校代码
						+cell1+cell2+cell3+cell4+cell5+cell6+cell7+cell8+cell9+cell10+cell11
						+cell12+cell13+cell14+cell15+cell16+cell17+cell18+cell19+cell20+cell21+cell22+cell23+cell24
						+cell25+cell26+cell27+cell28+cell29+cell30+cell31+cell32+cell33
						+ "\r\n";
			}
			contentToTxt(tempFile6, content6);
			files.add(tempFile6);
			
			
			 /** 得到文件保存目录的真实路径* */
		    String logoRealPathDir7 = this.getClass().getClassLoader().getResource("/").getPath(); 
		    
		    /** 临时文件放在webapps/property/WEB-INF/classes/temp.txt* */
		    File tempFile7 = new File(logoRealPathDir7+File.separator+"temp7.txt");
		    if (!tempFile7.exists()){
		    	tempFile7.createNewFile();  
		    }
			//查询出所有的专用设备设备
		    @SuppressWarnings("unchecked")
			List<Object[]> listEquipments7= this.labRoomReportFund(request,1,50);
			// 设定输出字节流内容
			String content7 = "";
			// 设定时间格式
			@SuppressWarnings("unused") 
			List<Object[]> reports7 = labRoomReportFund(request, 1, 50);
			for (Object[] s : reports7) {
				String cell2,cell3,cell4,cell5,cell6,cell7,cell8,cell9,cell10,cell11,cell12,cell13;
				if(s[2]!=null ){
					cell2 = formatStr(s[2].toString().trim(),6);
				}else{
					cell2 = formatStr("0",6);
				}
				if(s[3]!=null ){
					cell3 = formatStr(s[3].toString().trim(), 8);
				}else{
					cell3 = formatStr("0", 8);
				}
				if(s[4]!=null ){
					cell4 = formatStr(s[4].toString().trim(), 10);
				}else{
					cell4 = formatStr("0", 10);
				}
				if(s[5]!=null ){
					cell5 = formatStr(s[5].toString().trim(), 8);
				}else{
					cell5 = formatStr("0", 8);
				}
				if(s[6]!=null ){
					cell6 = formatStr(s[6].toString().trim(), 8);
				}else{
					cell6 = formatStr("0", 8);
				}
				if(s[7]!=null ){
					cell7 = formatStr(s[7].toString().trim(), 8);
				}else{
					cell7 = formatStr("0", 8);
				}
				if(s[8]!=null ){
					cell8 = formatStr(s[8].toString().trim(), 8);
				}else{
					cell8 = formatStr("0", 8);
				}
				if(s[9]!=null ){
					cell9 = formatStr(s[9].toString().trim(), 8);
				}else{
					cell9 = formatStr("0", 8);
				}
				if(s[10]!=null ){
					cell10 = formatStr(s[10].toString().trim(), 8);
				}else{
					cell10 = formatStr("0", 8);
				}
				if(s[11]!=null ){
					cell11 = formatStr(s[11].toString().trim(),8);
				}else{
					cell11 = formatStr("0", 8);
				}
				if(s[12]!=null ){
					cell12 = formatStr(s[12].toString().trim(), 8);
				}else{
					cell12 = formatStr("0", 8);
				}
				if(s[13]!=null ){
					cell13 = formatStr(s[13].toString().trim(), 8);
				}else{
					cell13 = formatStr("0", 8);
				}
				content7 += "12712"// 学校代码
						+cell2+cell3+cell4+cell5+cell6+cell7+cell8+cell9+cell10+cell11+cell12+cell13
						+ "\r\n";
			}
			contentToTxt(tempFile7, content7); 
			files.add(tempFile7);
			 String fileName = "test.zip";
			 /** 得到文件保存目录的真实路径* */
			    String logoRealPathDir8 = this.getClass().getClassLoader().getResource("/").getPath(); 
			    
			    /** 临时文件放在webapps/property/WEB-INF/classes/temp.txt* */
			    File fileZip = new File(logoRealPathDir7+File.separator+"test.zip");

		       // File fileZip = new File(outFilePath + fileName);
		        // 文件输出流
		        FileOutputStream outStream = new FileOutputStream(fileZip);
		        // 压缩流
		        ZipOutputStream toClient = new ZipOutputStream(outStream);
		    //  toClient.setEncoding("gbk");
		        zipFile(files, toClient);
		        toClient.close();
		        outStream.close();
		        this.downloadFile(fileZip, response, true);
	}
	
	public static void zipFile(List<File> files, ZipOutputStream outputStream) throws IOException, ServletException {
        try {
            int size = files.size();
            // 压缩列表中的文件
            for (int i = 0; i < size; i++) {
                File file = (File) files.get(i);
                zipFile(file, outputStream);
            }
        } catch (IOException e) {
            throw e;
        }
    }
	
	public static void zipFile(File inputFile, ZipOutputStream outputstream) throws IOException, ServletException {
        try {
            if (inputFile.exists()) {
                if (inputFile.isFile()) {
                    FileInputStream inStream = new FileInputStream(inputFile);
                    BufferedInputStream bInStream = new BufferedInputStream(inStream);
                    ZipEntry entry = new ZipEntry(inputFile.getName());
                    outputstream.putNextEntry(entry);

                    final int MAX_BYTE = 10 * 1024 * 1024; // 最大的流为10M
                    long streamTotal = 0; // 接受流的容量
                    int streamNum = 0; // 流需要分开的数量
                    int leaveByte = 0; // 文件剩下的字符数
                    byte[] inOutbyte; // byte数组接受文件的数据

                    streamTotal = bInStream.available(); // 通过available方法取得流的最大字符数
                    streamNum = (int) Math.floor(streamTotal / MAX_BYTE); // 取得流文件需要分开的数量
                    leaveByte = (int) streamTotal % MAX_BYTE; // 分开文件之后,剩余的数量

                    if (streamNum > 0) {
                        for (int j = 0; j < streamNum; ++j) {
                            inOutbyte = new byte[MAX_BYTE];
                            // 读入流,保存在byte数组
                            bInStream.read(inOutbyte, 0, MAX_BYTE);
                            outputstream.write(inOutbyte, 0, MAX_BYTE); // 写出流
                        }
                    }
                    // 写出剩下的流数据
                    inOutbyte = new byte[leaveByte];
                    bInStream.read(inOutbyte, 0, leaveByte);
                    outputstream.write(inOutbyte);
                    outputstream.closeEntry(); // Closes the current ZIP entry
                    // and positions the stream for
                    // writing the next entry
                    bInStream.close(); // 关闭
                    inStream.close();
                }
            } else {
                throw new ServletException("文件不存在！");
            }
        } catch (IOException e) {
            throw e;
        }
    }
	
	public void downloadFile(File file,HttpServletResponse response,boolean isDelete) {
        try {
            // 以流的形式下载文件。
            BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes("UTF-8"),"ISO-8859-1"));
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            if(isDelete)
            {
                file.delete();        //是否将生成的服务器端文件删除
            }
         } 
         catch (IOException ex) {
             ex.printStackTrace();
         }
     }
	
	
	/*********************************************************************************
	 * Description：基表模块-{查出教学实验项目表 operation_item_teaching}
	 * 
	 * @author：魏来
	 * @date：2016-8-8
	 ************************************************************************************/
	@SuppressWarnings({ "unchecked" })
	public List<Object[]> operationItemTeachingByYear(int yearCode, int page, int pageSize) {
		String sql = "select * from operation_item_teaching n where 1=1";
		sql += " and n.yearId = "+ yearCode;
		//sql += " and status = 545";
		javax.persistence.Query query = entityManager.createNativeQuery(sql);
		// 以下两行是分页设置
		query.setMaxResults(pageSize);
		query.setFirstResult((page - 1) * pageSize);
		// 获取list对象
		List<Object[]> list = query.getResultList();
		return list;
	}
}