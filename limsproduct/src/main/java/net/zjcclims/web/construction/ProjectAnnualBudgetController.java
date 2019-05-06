package net.zjcclims.web.construction;


import net.zjcclims.dao.CommonDocumentDAO;
import net.zjcclims.domain.CommonDocument;
import net.zjcclims.domain.ProjectAnnualBudget;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.construction.ProjectAnnualBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.BindException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Spring MVC controller that handles CRUD requests for ProjectBudget entities
 * 
 */

@Controller("ProjectAnnualBudgetController")
@RequestMapping("annualBudget")
public class ProjectAnnualBudgetController {

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
	
	@Autowired private ProjectAnnualBudgetService projectAnnualBudgetService;
	@Autowired private ShareService shareService;
	@Autowired private CommonDocumentDAO commonDocumentDAO;
	
	/**
	 * Description 实验室历年建设项目
	 * @param annualBudget
	 * @param currpage
	 * @return
	 * @author 陈乐为 2017-12-19
	 */
	@RequestMapping("/listAnnualBudget")
	public ModelAndView listAnnualBudget(@ModelAttribute ProjectAnnualBudget annualBudget, @RequestParam int currpage){
		ModelAndView mav=new ModelAndView();
		//查询表单的对象
		mav.addObject("annualBudget", annualBudget);
		int pageSize=20;//每页20条记录
		List<ProjectAnnualBudget> projectAnnualBudgets = projectAnnualBudgetService.findAnnualBudgetByQuery(annualBudget, currpage, pageSize);
		mav.addObject("projectAnnualBudgets", projectAnnualBudgets);
		//查询出来的总记录条数
		int totalRecords = projectAnnualBudgetService.findAnnualBudgetByQuery(annualBudget, 1, -1).size();
		//分页信息
		Map<String,Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
		mav.addObject("pageModel",pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("currpage", currpage);
		mav.addObject("pageSize", pageSize);
		
		mav.setViewName("annualBudget/listAnnualBudget.jsp");
		return mav;
	}

	/**
	 * Description 编辑
	 * @param idKey
	 * @return
	 * @author 陈乐为 2017-12-19
	 */
	@RequestMapping("/editAnnualBudget")
	public ModelAndView editAnnualBudget(@RequestParam Integer idKey){
		ModelAndView mav = new ModelAndView();
		ProjectAnnualBudget projectAnnualBudget = projectAnnualBudgetService.findAnnualBudgetById(idKey);
		mav.addObject("annualBudget", projectAnnualBudget);
		//日期
		String deadline = "";
		if(projectAnnualBudget.getDeadLines()!=null){
			deadline = shareService.format(projectAnnualBudget.getDeadLines());
		}
		mav.addObject("deadline",deadline);
		
		mav.setViewName("annualBudget/editAnnualBudget.jsp");
		return mav;
	}
	
	/**
	 * Description 相关文档
	 * @param idKey
	 * @return
	 * @author 陈乐为 2017-12-19
	 */
	@RequestMapping("/docAnnualBudget")
	public ModelAndView docAnnualBudget(@RequestParam Integer idKey){
		ModelAndView mav = new ModelAndView();
		ProjectAnnualBudget projectAnnualBudget = projectAnnualBudgetService.findAnnualBudgetById(idKey);
		mav.addObject("annualBudget", projectAnnualBudget);
		
		mav.setViewName("annualBudget/docAnnualBudget.jsp");
		return mav;
	}
	
	/**
	 * Description 保存对象
	 * @param annualBudget
	 * @return
	 * @author 陈乐为 2017-12-19
	 * @throws ParseException
	 */
	@RequestMapping("/saveAnnualBudget")
	public String saveAnnualBudget(HttpServletRequest request,
                                   @ModelAttribute ProjectAnnualBudget annualBudget, int[] documentIdArray) throws ParseException {
		String deadlineTime = request.getParameter("deadLines");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = sdf.parse(deadlineTime);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
//		annualBudget.setDeadLines(calendar.setTime(date));
		
		projectAnnualBudgetService.saveAnnualBudget(annualBudget);
		//申请附件
//		for(int i:documentIdArray){
//			//文档id对应的文档
//			CommonDocument document = commonDocumentDAO.findCommonDocumentByPrimaryKey(i);
//			document.setProjectAnnualBudget(annualBudget);
//			commonDocumentDAO.store(document);
//		}
		
		return "redirect:/annualBudget/listAnnualBudget?currpage=1";
	}
	
	/**
	 * Description 保存相关文档
	 * @param documentIdArray
	 * @return
	 * @author 李志宇 2017-12-26
	 */
	@RequestMapping("/saveDocAnnualBudget")
	public String saveDocAnnualBudget(@ModelAttribute ProjectAnnualBudget annualBudget, int[] documentIdArray){
		//projectAnnualBudgetService.saveAnnualBudget(annualBudget);
		//申请附件
		for(int i:documentIdArray){
			//文档id对应的文档
			CommonDocument document = commonDocumentDAO.findCommonDocumentByPrimaryKey(i);
			document.setProjectAnnualBudget(annualBudget);
			commonDocumentDAO.store(document);
		}
		
		return "redirect:/annualBudget/listAnnualBudget?currpage=1";
	}
	
	/**
	 * Description 上传项目附件
	 * @param request
	 * @param response
	 * @param errors
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadAnnualBudgetFile")
	public @ResponseBody
    String uploadAnnualBudgetFile(HttpServletRequest request, HttpServletResponse response, BindException errors) throws Exception {
		String s = projectAnnualBudgetService.uploadAnnualBudgetFile(request,response);
		return shareService.htmlEncode(s);
	}
	
	/**
	 * Description 删除附件
	 * @param id
	 * @param annualId
	 * @return
	 * @author 陈乐为 2017-12-19
	 */
	@RequestMapping("/deleteCommonDocument")
	public ModelAndView deleteCommonDocument(@RequestParam Integer id, Integer annualId)  {
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室建设申请的文档
		CommonDocument doc=commonDocumentDAO.findCommonDocumentByPrimaryKey(id);
		//文档保存路径
		String documentUrl=doc.getDocumentUrl();
		commonDocumentDAO.remove(doc);
		//***************************
		//此处还是没有删除掉服务器上upload目录下的文件，需要删掉服务器上的文件
		//*****************************
		shareService.deleteDocument(documentUrl);
		mav.setViewName("redirect:/annualBudget/docAnnualBudget?idKey="+annualId);
		return mav;
	}
	
	/*********************************************************************************
	 * Description 实验室历年建设项目-导出
	 * @author 李志宇  2017-12-20
	 ************************************************************************************/
	@RequestMapping("/exportAnnualBudget")
	public void exportProjectStartedReport(@ModelAttribute ProjectAnnualBudget annualBudget,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.setContentType("application/excel;charset=UTF-8");
		// 获取所有的客户信息赋值给集合alls；
		List<ProjectAnnualBudget> annualBudgets = projectAnnualBudgetService.findAllAnnualBudgetByannualBudget(annualBudget, 1, projectAnnualBudgetService.findAllAnnualBudgetByannualBudget(annualBudget).size());
		projectAnnualBudgetService.exportExcelProjectAnnualBudget(annualBudgets,request,response);
		
	}
	
	/*********************************************************************************
	 * Description 实验室历年建设项目-下载附件
	 * @author 李志宇  2017-12-20
	 ************************************************************************************/
	@RequestMapping("/downloadFile")
	public void downloadFile(int idkey, HttpServletRequest request,HttpServletResponse response) {
		shareService.downloadFileByDocumentId(idkey,request,response);
	}

	/**
	 * Description 新建
	 * @return
	 * @author 廖文辉 2018-1-5
	 */
	@RequestMapping("/newAnnualBudget")
	public ModelAndView newAnnualBudget(){
		ModelAndView mav = new ModelAndView();
		ProjectAnnualBudget projectAnnualBudget = new ProjectAnnualBudget();
		mav.addObject("annualBudget", new ProjectAnnualBudget());
		//日期
		String deadline = "";
		if(projectAnnualBudget.getDeadLines()!=null){
			deadline = shareService.format(projectAnnualBudget.getDeadLines());
		}
		mav.addObject("deadline",deadline);

		mav.setViewName("annualBudget/editAnnualBudget.jsp");
		return mav;
	}

	/*********************************************************************************
	 * Description 实验室历年建设项目-导入
	 * @author 廖文辉  2018-1-3
	 ************************************************************************************/
	@RequestMapping("/importAnnualBudget")
	public ModelAndView importAnnualBudget(HttpServletRequest request)throws ParseException{
		ModelAndView mav = new ModelAndView();
		// 获取文件地址
		String fileName = shareService.getUpdateFilePath(request);
		// 获取服务器地址
		String logoRealPathDir = request.getSession().getServletContext()
				.getRealPath("/");
		// 获取文件全部地址
		String filePath = logoRealPathDir + fileName;
		System.out.println(filePath);
		if (filePath.endsWith("xls") || filePath.endsWith("xlsx")) {
			//进行导入操作
			projectAnnualBudgetService.importAnnualBudget(filePath);
		}
		mav.setViewName("redirect:/annualBudget/listAnnualBudget?currpage=1");
		return mav;
	}
}