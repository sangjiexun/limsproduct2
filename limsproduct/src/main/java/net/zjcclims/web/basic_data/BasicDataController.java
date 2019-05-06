package net.zjcclims.web.basic_data;

import java.io.File;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Map;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zjcclims.constant.CommonConstantInterface;
/*import net.zjcclims.domain.AssetManage;*/
import net.zjcclims.dao.SchoolYearDAO;
import net.zjcclims.domain.SchoolDeviceChangeReport;
import net.zjcclims.domain.SchoolYear;
import net.zjcclims.service.basic_data.BasicDataService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.system.ShareDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller("BasicDataController")
public class BasicDataController {
	@Autowired private ShareService shareService;
	@Autowired private BasicDataService basicDataService;
	@Autowired private ShareDataService shareDataService;
	@Autowired private SchoolYearDAO schoolYearDao;
	
	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register static property editors.
		binder.registerCustomEditor(java.util.Calendar.class,new org.skyway.spring.util.databinding.CustomCalendarEditor());
		binder.registerCustomEditor(byte[].class,new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
		binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
		binder.registerCustomEditor(java.math.BigDecimal.class,new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
		binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Integer.class, true));
		binder.registerCustomEditor(java.util.Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
		binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
		binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Long.class, true));
		binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Double.class, true));
	}
	
	/**
	 * @Description:教育部基表
	 * @author 张愉
	 * @date:2017-6-30
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/basic_data/allsheet")
	  public ModelAndView allsheet(HttpServletRequest request,@RequestParam Integer currpage){
		ModelAndView mav = new  ModelAndView();
		mav.setViewName("basic_data/allsheet.jsp");
		return mav;	
	}
	
	/*********************************************************************************
     * Description：基表模块-{查看教学科研仪器设备表里面的所有设备表   school_device_report}
     * 
     * @author：岳茜
     * @date：2016-7-28
     ************************************************************************************/
	@SuppressWarnings("unchecked")
	@RequestMapping("/basic_data/schoolDeviceReport")
		public ModelAndView schoolDeviceReport(HttpServletRequest request,@RequestParam Integer currpage){
		ModelAndView mav = new  ModelAndView();
	    //学年 (获取学年列表)
	    Map<Integer,String> schoolYearMap=basicDataService.findAllSchoolYearMap();
	    mav.addObject("schoolYearMap",schoolYearMap);
	    //按学年查询
	    Integer yearId = 0;
	    if (request.getParameter("yearCode") != null && request.getParameter("yearCode") != "") {
	    	yearId =  Integer.valueOf(request.getParameter("yearCode"));
		}
	    mav.addObject("yearId", yearId);
		    
		int pageSize =50;
		List<Object[]>schoolDeviceReportLists = basicDataService.schoolDeviceReport(request, currpage, pageSize);
		mav.addObject("schoolDeviceReportLists",schoolDeviceReportLists);
		int totalRecords=basicDataService.schoolDeviceReportCount(request);
        mav.addObject("pageModel",shareService.getPage(pageSize,currpage,totalRecords));
		mav.addObject("currpage",currpage);
		mav.addObject("pageSize",pageSize);
		mav.addObject("totalRecords",totalRecords);
		mav.setViewName("basic_data/schoolDeviceReport.jsp");
		return mav;		
		}
	/*********************************************************************************
     *  Description：基表模块-{导出excel，教学科研仪器设备表里面的所有设备   school_device_report}
     *  
     * @author：岳茜
     * @date：2016-8-2
     ************************************************************************************/
	@RequestMapping("/basic_data/exportReport")
	 public void exportReport (HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		basicDataService.exportReport(request, response);
	}
	/*********************************************************************************
     *  Description：基表模块-{导出txt，教学科研仪器设备表里面的所有设备   school_device_report}
     *  
     * @author：岳茜
     * @date：2016-8-2
     ************************************************************************************/
	@RequestMapping("/basic_data/exportReportTxt")
	public void exportReportTxt(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
	    /** 得到文件保存目录的真实路径* */
	    String logoRealPathDir = this.getClass().getClassLoader().getResource("/").getPath(); 
	    
	    /** 临时文件放在webapps/property/WEB-INF/classes/temp.txt* */
	    File tempFile = new File(logoRealPathDir+File.separator+"temp.txt");
	    if (!tempFile.exists()){
	    	tempFile.createNewFile();  
	    }

		//查询出所有的专用设备设备
	    @SuppressWarnings("unchecked")
		List<Object[]> listEquipments= basicDataService.schoolDeviceReport(request,1,50);
	    basicDataService.newExportTecEquipAVE(listEquipments,tempFile,request,response);
	}	
	 /*********************************************************************************
     * Description：基表模块-{教学科研仪器设备增减变动情况表   School_device_change}
     * 
     * @author：岳茜
     * @date：2016-8-1
     ************************************************************************************/
	@RequestMapping("/basic_data/schoolDeviceChange")
    public ModelAndView schoolDeviceChange(HttpServletRequest request){
    ModelAndView mav = new  ModelAndView();
   //学年 (获取学年列表)
    Map<Integer,String> schoolYearMap=basicDataService.findAllSchoolYearMap();
    mav.addObject("schoolYearMap",schoolYearMap);
    //按学年查询
    Integer yearId = 0;
    if (request.getParameter("yearCode") != null && request.getParameter("yearCode") != "") {
    	mav.addObject("yearCode", request.getParameter("yearCode"));
        yearId =  Integer.valueOf(request.getParameter("yearCode"));
    }
    mav.addObject("yearId", yearId);
    
    SchoolDeviceChangeReport report = basicDataService.schoolDeviceChange(request);
    mav.addObject("report",report);
    mav.setViewName("basic_data/schoolDeviceChange.jsp");
    return mav;     
    }
	/*********************************************************************************
     *  Description：基表模块-{导出excel，教学科研仪器设备增减变动情况表   School_device_change}
     *  
     * @author：魏来
     * @date：2016-8-23
     ************************************************************************************/
	@RequestMapping("/basic_data/exportSchoolDeviceChange")
	 public void exportSchoolDeviceChange(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer yearCode = 0;
		if(request.getParameter("yearCodeForTxt") != null && !request.getParameter("yearCodeForTxt").equals("")) {
			yearCode = Integer.valueOf(request.getParameter("yearCodeForTxt"));
		}
		basicDataService.exportSchoolDeviceChangeReport(request, response, yearCode);
	}
	
	/*********************************************************************************
     *  Description：基表模块-{导出txt，教学科研仪器设备增减变动情况表   School_device_change}
     *  
     * @author：魏来
     * @date：2016-8-23
     ************************************************************************************/
    @RequestMapping("/basic_data/exportSchoolDeviceChangeTxt")
	public void exportSchoolDeviceChangeTxt(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
	   /** 得到文件保存目录的真实路径* */
	    String logoRealPathDir = this.getClass().getClassLoader().getResource("/").getPath(); 
	    
	  /** 临时文件放在webapps/property/WEB-INF/classes/temp.txt* */
	    File tempFile = new File(logoRealPathDir+File.separator+"temp.txt");
	    if (!tempFile.exists()){
	    	tempFile.createNewFile();  
	    }
		Integer yearCode = 0;
		if(request.getParameter("yearCodeForTxt") != null && !request.getParameter("yearCodeForTxt").equals("")) {
			yearCode = Integer.valueOf(request.getParameter("yearCodeForTxt"));
		}
		//查询出所有的专用设备设备
		List<SchoolDeviceChangeReport> listEquipments= basicDataService.schoolDeviceChangeReport(yearCode);
	    basicDataService.newSchoolDeviceChangeTecEquipAVE(listEquipments,tempFile,request,response,yearCode);
	}
	 /*********************************************************************************
     *  Description：基表模块-{查出教学科研仪器设备表里面的贵重仪器设备   school_device_value 单价大于400000}
	 * @param yearName 
     *  
     * @author：岳茜
     * @date：2016-7-29
     ************************************************************************************/
	@SuppressWarnings("unchecked")
	@RequestMapping("/basic_data/schoolDeviceValue")
		public ModelAndView schoolDeviceValue(HttpServletRequest request,@RequestParam Integer currpage, String yearName){
		ModelAndView mav = new  ModelAndView();
		int pageSize =50;
		List<Object[]>schoolDeviceValueLists = basicDataService.schoolDeviceValue(request, currpage, pageSize);
		mav.addObject("schoolDeviceValueLists",schoolDeviceValueLists);
		int totalRecords=basicDataService.schoolDeviceValueCount(request);
		List<SchoolYear> schoolYearList = schoolYearDao.executeQuery("select s from SchoolYear s",-1,-1);
		mav.addObject("yearCodeMap",schoolYearList);
        mav.addObject("pageModel",shareService.getPage(pageSize,currpage,totalRecords));
		mav.addObject("currpage",currpage);
		mav.addObject("pageSize",pageSize);
		mav.addObject("totalRecords",totalRecords);
		mav.setViewName("basic_data/schoolDeviceValue.jsp");
		return mav;		
		}
	
	/*********************************************************************************
     *  Description：基表模块-{导出excel，教学科研仪器设备表贵重仪器   school_device_value}
     *  
     * @author：岳茜
     * @date：2016-8-2
     ************************************************************************************/
	@RequestMapping("/basic_data/exportSchoolDeviceValue")
	 public void exportSchoolDeviceValue (HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		basicDataService.exportSchoolDeviceValue(request, response);
	}
	/*********************************************************************************
     *  Description：基表模块-{导出txt，教学科研仪器设备表里面的贵重仪器   school_device_value}
     *  
     * @author：岳茜
     * @date：2016-8-2
     ************************************************************************************/
	@RequestMapping("/basic_data/exportSchoolDeviceValueTxt")
	public void exportSchoolDeviceValueTxt(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
	    /** 得到文件保存目录的真实路径* */
	    String logoRealPathDir = this.getClass().getClassLoader().getResource("/").getPath(); 
	    
	    /** 临时文件放在webapps/property/WEB-INF/classes/temp.txt* */
	    File tempFile = new File(logoRealPathDir+File.separator+"temp.txt");
	    if (!tempFile.exists()){
	    	tempFile.createNewFile();  
	    }

		//查询出所有的专用设备设备
	    @SuppressWarnings("unchecked")
		List<Object[]> listEquipments= basicDataService.schoolDeviceValue(request,1,50);
	    basicDataService.newSchoolDeviceValueTecEquipAVE(listEquipments,tempFile,request,response);
	}
	/*********************************************************************************
     * Description：基表模块-{教学实验项目表   operation_item_teaching}
     * 
     * @author：魏来
     * @date：2016-8-8
     ************************************************************************************/
	 @SuppressWarnings("unchecked")
		@RequestMapping("/basic_data/operationItemTeaching")
	    public ModelAndView operationItemTeaching(HttpServletRequest request, @RequestParam Integer currpage){
	        ModelAndView mav = new ModelAndView();
	      //学年 (获取学年列表)
		    Map<Integer,String> schoolYearMap=basicDataService.findAllSchoolYearMap();
		    mav.addObject("schoolYearMap",schoolYearMap);
		    //按学年查询
		    Integer yearId = 0;
		    if (request.getParameter("yearCode") != null && request.getParameter("yearCode") != "") {
		    	yearId =  Integer.valueOf(request.getParameter("yearCode"));
			}
		    mav.addObject("yearId", yearId);
		    
	        int pageSize =50;
	        List<Object[]> operationItemTeachingLists = basicDataService.operationItemTeaching(request, currpage, pageSize);
	        mav.addObject("operationItemTeachingLists",operationItemTeachingLists);
	        mav.addObject("currpage",currpage);
	        mav.addObject("pageSize",pageSize); int totalRecords=basicDataService.operationItemTeachingCount(request);
	        mav.addObject("pageModel",shareService.getPage(pageSize,currpage,totalRecords));
	        mav.addObject("currpage",currpage);
	        mav.addObject("pageSize",pageSize);
	        mav.addObject("totalRecords",totalRecords);
	        mav.setViewName("/basic_data/operationItemTeaching.jsp");
	        return mav;     
	    }
	 
	 /*********************************************************************************
	  * Description：基表模块-{导出excel，教学实验项目表   operation_item_teaching}
      *  
	  * @author：魏来
	  * @date：2016-8-4
	     ************************************************************************************/
		@RequestMapping("/basic_data/exportOperationItemTeaching")
		 public void exportOperationItemTeaching (HttpServletRequest request, HttpServletResponse response, Integer cid,int yearCode) throws Exception{
			basicDataService.exportOperationItemTeaching(request,response,yearCode);
		}
		
		/*********************************************************************************
	     * Description：基表模块-{导出txt，教学实验项目表   operation_item_teaching}
	     *  
	     * @author：魏来
	     * @date：2016-8-5
	     ************************************************************************************/
		@RequestMapping("/basic_data/exportOperationItemTeachingTxt")
		public void exportOperationItemTeachingTxt(HttpServletRequest request,HttpServletResponse response,int yearCode) throws Exception{
			
		    /** 得到文件保存目录的真实路径* */
		    String logoRealPathDir = this.getClass().getClassLoader().getResource("/").getPath(); 
		    
		    /** 临时文件放在webapps/property/WEB-INF/classes/temp.txt* */
		    File tempFile = new File(logoRealPathDir+File.separator+"temp.txt");
		    if (!tempFile.exists()){
		    tempFile.createNewFile();  
		    }
			//查询出所有的专用设备设备
		    @SuppressWarnings("unchecked")
			List<Object[]> listEquipments= basicDataService.operationItemTeaching(request,1,50);
		    basicDataService.newOperationItemTeachingTecEquipAVE(listEquipments,tempFile,request,response,yearCode);
		}	
		
	/*********************************************************************************
     * Description：基表模块-{所有专任实验室人员表   lab_team}
     * 
     * @author：魏来
     * @date：2016-8-2
     ************************************************************************************/
    @SuppressWarnings("unchecked")
	@RequestMapping("/basic_data/labTeam")
    public ModelAndView labTeam(HttpServletRequest request, @RequestParam Integer currpage){
        ModelAndView mav = new  ModelAndView();
        int pageSize =50;
        List<Object[]> labTeamLists = basicDataService.labTeam(request, currpage, pageSize);
        mav.addObject("labTeamLists",labTeamLists);
        mav.addObject("currpage",currpage);
        mav.addObject("pageSize",pageSize);
        mav.setViewName("basic_data/labTeam.jsp");
        return mav;     
    }
    
    /*********************************************************************************
     *  Description：基表模块-{导出excel，专任实验室人员   lab_team}
     *  
     * @author：魏来
     * @date：2016-8-4
     ************************************************************************************/
	@RequestMapping("/basic_data/exportLabTeam")
	 public void exportLabTeam (HttpServletRequest request, HttpServletResponse response, Integer cid) throws Exception{
		basicDataService.exportLabTeam(request, response);
	}
	
	/*********************************************************************************
     *  Description：基表模块-{导出txt，专任实验室人员   lab_team}
     *  
     * @author：魏来
     * @date：2016-8-5
     ************************************************************************************/
	@RequestMapping("/basic_data/exportLabTeamTxt")
	public void exportLabTeamTxt(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
	    /** 得到文件保存目录的真实路径* */
	    String logoRealPathDir = this.getClass().getClassLoader().getResource("/").getPath(); 
	    
	    /** 临时文件放在webapps/property/WEB-INF/classes/temp.txt* */
	    File tempFile = new File(logoRealPathDir+File.separator+"temp.txt");
	    if (!tempFile.exists()){
	    	tempFile.createNewFile();  
	    }
		//查询出所有的专用设备设备
	    @SuppressWarnings("unchecked")
		List<Object[]> listEquipments= basicDataService.labTeam(request,1,50);
	    basicDataService.newLabTeamTecEquipAVE(listEquipments,tempFile,request,response);
	}	
	
	
		/*********************************************************************************
	     * Description：基表模块-{实验室基本情况表  lab_basic}
	     * 
	     * @author：魏来
	     * @date：2016-8-10
	     ************************************************************************************/
		@SuppressWarnings("unchecked")
		@RequestMapping("/basic_data/labBasic")
	    public ModelAndView labBasic(HttpServletRequest request, @RequestParam Integer currpage){
	        ModelAndView mav = new ModelAndView();
	        int pageSize =50;
	        List<Object[]> labBasicLists = basicDataService.labBasic(request, currpage, pageSize);
	        mav.addObject("labBasicLists",labBasicLists);
	        mav.addObject("currpage",currpage);
	        mav.addObject("pageSize",pageSize);
	        mav.setViewName("/basic_data/labBasic.jsp");
	        return mav;     
	    }
		
		/*********************************************************************************
		  * Description：基表模块-{导出excel，实验室基本情况表  lab_basic}
	      *  
		  * @author：魏来
		  * @date：2016-8-4
		 ************************************************************************************/
			@RequestMapping("/basic_data/exportLabBasic")
			 public void exportLabBasic (HttpServletRequest request, HttpServletResponse response, Integer cid) throws Exception{
				basicDataService.exportLabBasic(request, response);
			}
			
			/*********************************************************************************
		     * Description：基表模块-{导出txt，实验室基本情况表  lab_basic}
		     *  
		     * @author：魏来
		     * @date：2016-8-5
		     ************************************************************************************/
			@RequestMapping("/basic_data/exportLabBasicTxt")
			public void exportLabBasicTxt(HttpServletRequest request,HttpServletResponse response) throws Exception{
				
			    /** 得到文件保存目录的真实路径* */
			    String logoRealPathDir = this.getClass().getClassLoader().getResource("/").getPath(); 
			    
			    /** 临时文件放在webapps/property/WEB-INF/classes/temp.txt* */
			    File tempFile = new File(logoRealPathDir+File.separator+"temp.txt");
			    if (!tempFile.exists()){
			    	tempFile.createNewFile();  
			    }
				//查询出所有的专用设备设备
			    @SuppressWarnings("unchecked")
				List<Object[]> listEquipments= basicDataService.labBasic(request,1,50);
			    basicDataService.newLabBasicTecEquipAVE(listEquipments,tempFile,request,response);
			}	
			
			/*********************************************************************************
		     * Description：基表模块-{实验室经费情况表 lab_room_report_fund}
		     * 
		     * @author：魏来
		     * @date：2016-8-10
		     ************************************************************************************/
			@SuppressWarnings("unchecked")
			@RequestMapping("/basic_data/labRoomReportFund")
		    public ModelAndView labRoomReportFund(HttpServletRequest request, @RequestParam Integer currpage){
		        ModelAndView mav = new ModelAndView();
		        int pageSize =50;
		        List<Object[]> labRoomReportFundLists = basicDataService.labRoomReportFund(request, currpage, pageSize);
		        mav.addObject("labRoomReportFundLists",labRoomReportFundLists);
		        mav.addObject("currpage",currpage);
		        mav.addObject("pageSize",pageSize);
		        mav.setViewName("/basic_data/labRoomReportFund.jsp");
		        return mav;     
		    }
			
			/*********************************************************************************
			  * Description：基表模块-{导出excel，实验室经费情况表  lab_room_report_fund}
		      *  
			  * @author：魏来
			  * @date：2016-8-17
			 ************************************************************************************/
				@RequestMapping("/basic_data/exportLabRoomReportFund")
				 public void exportLabRoomReportFund (HttpServletRequest request, HttpServletResponse response, Integer cid) throws Exception{
					basicDataService.exportLabRoomReportFund(request, response);
				}
				
				/*********************************************************************************
			     * Description：基表模块-{导出txt，实验室经费情况表  lab_room_report_fund}
			     *  
			     * @author：魏来
			     * @date：2016-8-17
			     ************************************************************************************/
				@RequestMapping("/basic_data/exportLabRoomReportFundTxt")
				public void exportLabRoomReportFundTxt(HttpServletRequest request,HttpServletResponse response) throws Exception{
					
				    /** 得到文件保存目录的真实路径* */
				    String logoRealPathDir = this.getClass().getClassLoader().getResource("/").getPath(); 
				    
				    /** 临时文件放在webapps/property/WEB-INF/classes/temp.txt* */
				    File tempFile = new File(logoRealPathDir+File.separator+"temp.txt");
				    if (!tempFile.exists()){
				    	tempFile.createNewFile();  
				    }
					//查询出所有的专用设备设备
				    @SuppressWarnings("unchecked")
					List<Object[]> listEquipments= basicDataService.labRoomReportFund(request,1,50);
				    basicDataService.newLabRoomReportFundTecEquipAVE(listEquipments,tempFile,request,response);
				}	
				
				/************************************************************ 
				 *  Description：基表模块-{资产管理}
				 *  
				 * @author：郑昕茹
			     * @date：2016-11-21
				 ************************************************************/
				/*@RequestMapping("/basic_data/assetManage")
				public ModelAndView assetManage(@RequestParam int currpage,  @ModelAttribute AssetManage assetManage) {
					ModelAndView mav = new ModelAndView();		
					// 设置分页变量并赋值为20；
					int pageSize = CommonConstantInterface.INT_PAGESIZE;
					//设置学期表的总记录数并赋值
					int totalRecords = basicDataService.getAllAssetManage();
					Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords); 
					mav.addObject("pageModel", pageModel);
					mav.addObject("page", currpage);
					mav.addObject("totalRecords", totalRecords);
					mav.addObject("pageSize", pageSize); 
					mav.addObject("assetManages", basicDataService.getAssetManage(assetManage, currpage, pageSize));
					// 将该Model映射到listTerm.jsp;
					mav.setViewName("/basic_data/listAssetManages.jsp");
					return mav;
				}*/
				
				 /**************************************************************************************
			     * description：消耗品记录列表导入
			     * @author:郑昕茹
				 * @throws ParseException 
			     * @date：2016-11-21
			     **************************************************************************************/
				/*@RequestMapping("/basic_data/importAssetManage")
				public ModelAndView importAssetManage(HttpServletRequest request) throws ParseException{
					ModelAndView mav = new ModelAndView();
					String fileName = shareService.getUpdateFilePath(request);
					String logoRealPathDir = request.getSession().getServletContext().getRealPath("/");
					String filePath = logoRealPathDir + fileName;
					System.out.println(filePath);
					if(filePath.endsWith("xls")||filePath.endsWith("xlsx")){
						shareDataService.importAssetManage(filePath);
					}
					mav.setViewName("redirect:/basic_data/assetManage?currpage=1");
					return mav;
				}*/
				/*********************************************************************************
			     *  Description：基表模块-{导出txt，学年管理}
			     *  
			     * @author：岳茜
			     * @date：2016-8-2
			     ************************************************************************************/
				@RequestMapping("/basic_data/exportYearTxt")
				public void exportYearTxt(HttpServletRequest request,HttpServletResponse response) throws Exception{
					
				    /** 得到文件保存目录的真实路径* */
				    String logoRealPathDir = this.getClass().getClassLoader().getResource("/").getPath(); 
				    
				    /** 临时文件放在webapps/property/WEB-INF/classes/temp.txt* */
				    System.out.print(new String((logoRealPathDir+"temp.txt").getBytes("UTF-8")));
				    File tempFile = new File(new String((logoRealPathDir+"temp.txt").getBytes("UTF-8"), "ISO8859-1") );
				    if (!tempFile.exists()){
				    	tempFile.createNewFile();  
				    }
 
				    basicDataService.newExportTxtYear(tempFile,request,response);
				}	
				
	/*********************************************************************************
	 *  Description：基表模块-{一次性导出本学年的7张报表}
	 *  
	 * @author：岳茜
	* @date：2016-8-2
	************************************************************************************/
	@RequestMapping("/basic_data/exportYearReport")
	 public @ResponseBody String exportYearReport (@RequestParam Integer tag, HttpServletRequest request, HttpServletResponse response, Integer cid) throws Exception{
		SchoolYear schoolYear = shareService.getBelongsSchoolYear(Calendar.getInstance());
		int yearCode = 0;
		if(schoolYear != null){
			yearCode = schoolYear.getCode();
		}	
		if(tag == 1)
		{
			basicDataService.exportReportCurrYear(request, response,yearCode);
			return "/basic_data/exportReportCurrYear";
		}
		if(tag == 2)
		{
			//basicDataService.exportSchoolDeviceChangeReport(request, response, yearCode);
			return "/basic_data/exportReportCurrYear";
		}
		if(tag == 3)
		{
			//basicDataService.exportCurrYearSchoolDeviceValue(request, response);
			return "success";
		}
		if(tag == 4)
		{
			//basicDataService.exportOperationItemTeaching(request,response,yearCode);
			return "success";
		}
		if(tag == 5)
		{
			//basicDataService.exportLabTeam(request, response);
			return "success";
		}
		if(tag == 6)
		{
			//basicDataService.exportLabBasic(request, response);
			return "success";
		}
		if(tag == 7)
		{
			//basicDataService.exportLabRoomReportFund(request, response);
			return "success";
		}
		if(tag == 8){
			return "end";
		}
		return "end";	
			
	}
	
	
	/*********************************************************************************
     *  Description：基表模块-{导出本学年7张基表}
	 * @throws Exception 
     *  
     * @author：郑昕茹
     * @date：2016-12-15
     ************************************************************************************/
	@RequestMapping("/basic_data/exportReportCurrYear")
	 public void exportReportCurrYear (@RequestParam Integer yearCode,HttpServletRequest request, HttpServletResponse response) throws Exception{
		basicDataService.exportReportCurrYear(request, response,yearCode);
	}
	
	/*********************************************************************************
     *  Description：基表模块-{导出本学年7张 基表的txt压缩包}
	 * @throws Exception 
     *  
     * @author：郑昕茹
     * @date：2016-12-15
     ************************************************************************************/
	@RequestMapping("/basic_data/exportReportTxtCurrYear")
	 public void exportReportTxtCurrYear (@RequestParam Integer yearCode, HttpServletRequest request, HttpServletResponse response) throws Exception{
	    basicDataService.exportReportTxtCurrYear(request, response, yearCode);
	}
}