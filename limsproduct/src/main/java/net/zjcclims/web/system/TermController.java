/****************************************************************************
 * 命名规范：
 * 本控制层调整必须是按照如下的路径调整：@RequestMapping("/device/system/sample")，全部小写。
 * 列表信息listxxxx，獲取信息：getxxxx 编辑信息：editxxxx 删除信息：detelexxxx 新增信息 newxxxx
 * 导出信息exportxxxx 保存信息：savexxxx 
 ****************************************************************************/

package net.zjcclims.web.system;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.system.TermDetailService;
import net.zjcclims.dao.SchoolTermDAO;
import net.zjcclims.dao.SystemPreDayDAO;
import net.zjcclims.domain.SchoolTerm;
import net.zjcclims.domain.SchoolTermActive;
import net.zjcclims.domain.SystemPreDay;
import net.zjcclims.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import excelTools.ExcelUtils;
import excelTools.JsGridReportBase;
import excelTools.TableData;
import flex.messaging.io.ArrayList;

/****************************************************************************
 * 功能：系统后台管理模块
 * 作者：魏诚
 * 时间：2014-07-14
 ****************************************************************************/
@Controller("TermController")
@SessionAttributes("selected_academy")
public class TermController<JsonResult>{
	/************************************************************ 
	 * @初始化WebDataBinder，这个WebDataBinder用于填充被@InitBinder注释的处理
	 * 方法的command和form对象
	 *
	 ************************************************************/
	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register static property editors.
		binder.registerCustomEditor(java.util.Calendar.class, new org.skyway.spring.util.databinding.CustomCalendarEditor());
		binder.registerCustomEditor(byte[].class, new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
		binder.registerCustomEditor(boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(false));
		binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
		binder.registerCustomEditor(java.math.BigDecimal.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
		binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Integer.class, true));
		binder.registerCustomEditor(java.util.Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
		binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
		binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Long.class, true));
		binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Double.class, true));
	}
	
	@Autowired
	TermDetailService termDetailService;
	@Autowired
	ShareService shareService;
	@Autowired
	private SchoolTermDAO schoolTermDAO;
	@Autowired
	private SystemPreDayDAO systemPreDayDAO;
	
	/************************************************************ 
	 * @内容：学期列表
	 * @作者：叶明盾
	 * @日期：2014-07-28
	 ************************************************************/
	@RequestMapping("/system/listTerm")
	public ModelAndView listTerm(@RequestParam int currpage,@ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();		
		// 设置分页变量并赋值为20；
		int pageSize = CommonConstantInterface.INT_PAGESIZE;
		//设置学期表的总记录数并赋值
		int totalRecords = termDetailService.getTermTotalRecords();
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
		mav.addObject("newFlag", true);
		mav.addObject("pageModel", pageModel);
		mav.addObject("page", currpage);
		mav.addObject("totalRecords", totalRecords);
//		mav.addObject("cid", cid);
		mav.addObject("terms", termDetailService.findAllTerms(currpage - 1, pageSize));
		// 将该Model映射到listTerm.jsp;
		mav.setViewName("system/term/listTerm.jsp");
		return mav;
	}
	
	/************************************************************ 
	 * @内容：新增学期
	 * @作者：叶明盾
	 * @日期：2014-07-28
	 ************************************************************/
	@RequestMapping("/system/newTerm")
	public ModelAndView newTerm(HttpServletRequest request)
	{
		ModelAndView mav = new ModelAndView();
		mav.addObject("newFlag", true);
		mav.addObject("schoolTerm", new SchoolTerm());
//		mav.addObject("departments", shareService.getDepartmentsMap());
//		mav.addObject("educations", shareService.getEducationsMap());
//		mav.addObject("degrees", shareService.getDegreesMap());
//		mav.addObject("genders", shareService.getGendersMap());
		mav.addObject("page", shareService.getCurrpage(request));
		mav.setViewName("system/term/newTerm.jsp");
		return mav;
	}
	
	/************************************************************ 
	 * @内容：修改学期
	 * @作者: 李鹏翔
	 ************************************************************/
	@RequestMapping("/system/editTerm")
	public ModelAndView editTerm(@RequestParam Integer idKey)
	{
		ModelAndView mav = new ModelAndView();
		mav.addObject("newFlag", true);
		mav.addObject("idKey", idKey);
		SchoolTerm schoolTerm=schoolTermDAO.findSchoolTermByPrimaryKey(idKey);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		mav.addObject("termStart",sdf.format(schoolTerm.getTermStart().getTime()));
		mav.addObject("termEnd",sdf.format(schoolTerm.getTermEnd().getTime()));
		mav.addObject("schoolTerm",schoolTerm);
		mav.setViewName("system/term/editTerm.jsp");
		return mav;
	}
	
	/************************************************************ 
	 * @内容：删除学期
	 * @作者: 李鹏翔
	 ************************************************************/
	@RequestMapping ("/system/deleteTerm")
	public String deleteTerm(@RequestParam Integer idKey){
		SchoolTerm schoolTerm = termDetailService.findTermById(idKey);
		termDetailService.deleteTerm(schoolTerm);
		return "redirect:/system/listTerm?currpage=1";
	}
	/************************************************************ 
	 * @内容：根据学期生成周次
	 * @param：id为学期的主键id
	 * @作者：叶明盾
	 * @日期：2014-07-28
	 ************************************************************/
	@RequestMapping("/system/createWeek")
	public String createWeek(@RequestParam int id)
	{
		SchoolTerm schoolTerm = termDetailService.findTermById(id);	
		termDetailService.createWeek(schoolTerm);
		return "redirect:/system/listTerm?currpage=1";		
	}
	
	/************************************************************ 
	 * @内容：保存新建的学期
	 * @作者：李鹏翔
	 ************************************************************/
	@RequestMapping("/system/saveNewTerm")
	public String saveTerm(@ModelAttribute SchoolTerm schoolTerm){
		termDetailService.saveTerm(schoolTerm);
		return "redirect:/system/listTerm?currpage=1";
	}
	/************************************************************ 
	 * @内容：判断学期名称是否重复
	 * @作者：叶明盾
	 * @日期：2014-07-29
	 ************************************************************/
	@RequestMapping("/system/checkTerm")
	public @ResponseBody	String checkTerm(@RequestParam String termName)
	{
		Set<SchoolTerm> terms = termDetailService.findTermsByTermName(termName);
		if (terms.size() == 0)
		{
			return "no";
		}
		else
		{
			return "ok";
		}
	}
	
	/************************************************************
	 * @description:申请递交时间设置页面
	 * @author：黄烁
	 * @date：2017-11-05
	 ************************************************************/
	@RequestMapping("/system/listApplySetting")
	public ModelAndView applySetting(@RequestParam int currpage)
	{
		ModelAndView mav = new ModelAndView();
		Set<SystemPreDay> systemPreDay = systemPreDayDAO.findAllSystemPreDays();
		mav.addObject("systemPreDay", systemPreDay);
		// 设置分页变量并赋值为20；
		int pageSize = CommonConstantInterface.INT_PAGESIZE;
		int totalRecords = systemPreDayDAO.findAllSystemPreDays().size();
		mav.addObject("totalRecords", totalRecords);
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
		mav.addObject("pageModel", pageModel);
		// 将currpage映射到page，用来获取当前页的页码
		mav.addObject("page", currpage);
		mav.setViewName("system/time/listApplySetting.jsp");
		return mav;
	}
	
	/************************************************************
	 * @description:新建申请递交时间数据
	 * @author：黄烁
	 * @date：2017-11-05
	 ************************************************************/
	@RequestMapping("/system/newApplySetting")
	public ModelAndView newApplySetting(@ModelAttribute SystemPreDay systemPreDay)
	{
		ModelAndView mav = new ModelAndView();
		// 获取学期列表
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerms", schoolTerms);
		mav.addObject("systemPreDay", systemPreDay);
		mav.setViewName("system/time/newApplySetting.jsp");
		return mav;
	}
	
	/************************************************************
	 * @description:保存申请递交时间数据
	 * @author：黄烁
	 * @date：2017-11-05
	 ************************************************************/
	@RequestMapping("/system/saveApplySetting")
	public String saveApplySetting(@ModelAttribute SystemPreDay systemPreDay){
		if(systemPreDay.getUser() == null){
			User user = shareService.getUser();
			systemPreDay.setUser(user);
		}
		if(systemPreDay.getCreateTime() == null){
			Calendar c = Calendar.getInstance();
			systemPreDay.setCreateTime(c);
		}
		systemPreDayDAO.store(systemPreDay);
		systemPreDayDAO.flush();
		return "redirect:/system/listApplySetting?currpage=1";
	}
	
	/************************************************************
	 * @description:删除申请递交时间记录
	 * @author：黄烁
	 * @date：2017-11-06
	 ************************************************************/
	@RequestMapping("/system/deleteApplySetting")
	public String deleteTimetableSetting(@RequestParam int id){
		SystemPreDay sta =systemPreDayDAO.findSystemPreDayById(id);
		systemPreDayDAO.remove(sta);
		return "redirect:/system/listApplySetting?currpage=1";
	}
	
	/************************************************************
	 * @description:导出排课申请时间设置页面
	 * @author：黄烁
	 * @date：2017-11-05
	 ************************************************************/
	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	@RequestMapping("/system/exportApplySetting")
	public void exportApplySetting(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<Map> list = new ArrayList();
		Set<SystemPreDay> systemPreDays = systemPreDayDAO.findAllSystemPreDays();
		int i = 1;
		for(SystemPreDay s : systemPreDays){
			Map map = new HashMap();
			map.put("i", i);
			i++;
			map.put("term", s.getSchoolTerm().getTermName());
			map.put("day", s.getDayNum());
			DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			if(s.getCreateTime() != null){
				Date sdate = s.getCreateTime().getTime();
				String createTime = fmt.format(sdate);
				map.put("createTime", createTime);
			}else{
				map.put("createTime", "");
			}
			map.put("creater", s.getUser().getCname());
			list.add(map);
		}
		String title = "申请递交时间设置表";
		String[] headers = new String[]{"序号", "学期", "提前递交天数", "创建时间", "创建人"};
		String[] fields = new String[]{"i", "term", "day", "createTime", "creater"};
		TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(headers), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportExcel(title, shareService.getUserDetail().getCname(), "", td);
	}
	
	/************************************************************
	 * @description:编辑申请递交时间数据
	 * @author：黄烁
	 * @date：2017-11-06
	 ************************************************************/
	@RequestMapping("/system/editApplySetting")
	public ModelAndView editApplySetting(@RequestParam int id)
	{
		ModelAndView mav = new ModelAndView();
		// 获取学期列表
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		SystemPreDay systemPreDay = systemPreDayDAO.findSystemPreDayById(id);
		mav.addObject("schoolTerms", schoolTerms);
		mav.addObject("systemPreDay", systemPreDay);
		mav.setViewName("system/time/editApplySetting.jsp");
		return mav;
	}
	
	
	
	
	
	
	
	
	
}