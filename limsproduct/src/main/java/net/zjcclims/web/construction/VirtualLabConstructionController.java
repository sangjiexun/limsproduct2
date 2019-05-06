package net.zjcclims.web.construction;


import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.construction.VirtualLabConstructionService;
import net.zjcclims.service.lab.LabAnnexService;
import net.zjcclims.service.lab.LabCenterService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.service.operation.OperationService;
import net.zjcclims.service.system.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.BindException;
import java.util.*;

/**
 * Spring MVC controller that handles CRUD requests for VirtualLabConstruction entities
 * 
 */

@Controller("VirtualLabConstructionController")
@SessionAttributes("selected_academy")
public class VirtualLabConstructionController {

	/**
	 * DAO injected by Spring that manages LabRoomDevice entities
	 * 
	 */
	@Autowired
	private LabRoomDeviceDAO labRoomDeviceDAO;

	/**
	 * DAO injected by Spring that manages LabRoomProject entities
	 * 
	 */
	@Autowired
	private LabRoomProjectDAO labRoomProjectDAO;

	/**
	 * DAO injected by Spring that manages SchoolAcademy entities
	 * 
	 */
	@Autowired
	private SchoolAcademyDAO schoolAcademyDAO;

	/**
	 * DAO injected by Spring that manages SystemRoom entities
	 * 
	 */
	@Autowired
	private SystemRoomDAO systemRoomDAO;

	/**
	 * DAO injected by Spring that manages VirtualLabConstruction entities
	 * 
	 */
	@Autowired
	private VirtualLabConstructionDAO virtualLabConstructionDAO;

	/**
	 * Service injected by Spring that provides CRUD operations for VirtualLabConstruction entities
	 * 
	 */
	@Autowired
	private VirtualLabConstructionService virtualLabConstructionService;
	
	@Autowired
	AuthorityService authorityService;
	
	@Autowired
	ShareService shareService;
	@Autowired
	LabRoomService labRoomService;
	@Autowired
	private LabCenterService labCenterService;
	@Autowired
	private SchoolMajorDAO schoolMajorDAO;
	@Autowired
	private OperationService operationService;
	@Autowired
	private OperationItemDAO operationItemDAO;
	@Autowired CommonDocumentDAO commonDocumentDAO;
	@Autowired
	LabAnnexService labAnnexService;

	/**
	 * Register custom, context-specific property editors
	 * 
	 */
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

	
	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目申请列表
	 * 作者：李德
	 * 时间：2015-03-10
	 ****************************************************************************/
	/*@RequestMapping("/labconstruction/listVirtualLabConstruction")
	public ModelAndView listVirtualLabConstruction(@ModelAttribute VirtualLabConstruction virtualLabConstruction,
			@RequestParam int page,int modelId,
			@ModelAttribute("selected_academy") String acno){
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//System.out.println(authorityService.getAuthorityResourses(modelId).getIsActive());
		//System.out.println("aaa");
		// 权限控制
*//*		if (authorityService.getAuthorityResourses(modelId).getIsActive() == 0) {
			mav.setViewName("system/authorityManage/accessDenied.jsp");
			return mav;
		}else{*//*
			//实验室
			List<LabRoom> rooms=labRoomService.findLabRoomByLabCenterid(cid);
			mav.addObject("rooms", rooms);
			//查询表单的对象
			mav.addObject("virtualLabConstruction", virtualLabConstruction);
			int pageSize=10;//每页20条记录
			//查询出来的总记录条数
			int totalRecords=virtualLabConstructionService.findAllVirtualLabByVirtualLab(virtualLabConstruction).size();
			//System.out.println(totalRecords);
			//分页信息
			Map<String,Integer> pageModel =shareService.getPage(pageSize, page,totalRecords);
			//根据分页信息查询出来的记录
			List<VirtualLabConstruction> listVirtualLabConstruction=virtualLabConstructionService.findAllVirtualLabByVirtualLab(virtualLabConstruction,page,pageSize);
			mav.addObject("listVirtualLabConstruction",listVirtualLabConstruction);
			mav.addObject("pageModel",pageModel);
			mav.addObject("totalRecords", totalRecords);
			mav.addObject("page", page);
			mav.addObject("pageSize", pageSize);
			mav.addObject("cid", cid);
			
			Map map=new HashMap();
			for(VirtualLabConstruction virtualLabConstructions:listVirtualLabConstruction)
			{
			  //System.out.println("11111="+virtualLabConstructions.getMajorName());
			  if(!(virtualLabConstructions.getMajorName()==null))
			  {
			    Set<SchoolMajor> cstaffs=new HashSet<SchoolMajor>();
			    String[] arraycstaff=virtualLabConstructions.getMajorName().split(",");
			    //
		        for(String strAuth:arraycstaff)
		        {		
		        	
			      SchoolMajor cstaff=schoolMajorDAO.findSchoolMajorByPrimaryKey(strAuth);
			      
			      //System.out.println(cstaff.getMajorName());
			      //cstaff.setMajorName(cstaff.getMajorName()+',');
			      cstaffs.add(cstaff);
		
		          //System.out.println("2222222="+cstaff);
		          map.put(virtualLabConstructions.getId(), cstaffs);
	
		         }
			   }
			}
			mav.addObject("map",map);
			
			//查询
			mav.addObject("searchVirtualLab",authorityService.getAuthorityResourses(921));
			//查看
			mav.addObject("viewVirtualLab",authorityService.getAuthorityResourses(926));
			//编辑
			mav.addObject("editVirtualLab",authorityService.getAuthorityResourses(922));
			//删除
			mav.addObject("deleteVirtualLab",authorityService.getAuthorityResourses(923));
			//新增
			mav.addObject("newVirtualLab",authorityService.getAuthorityResourses(924));
			//导出
			mav.addObject("exportVirtualLab",authorityService.getAuthorityResourses(925));
			//
			mav.setViewName("labconstruction/listVirtualLabConstruction.jsp");
			return mav;
		//}
		
	}*/
	
	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目申请列表
	 * 作者：李德
	 * 时间：2015-03-10
	 ****************************************************************************/
	@RequestMapping("/labconstruction/newVirtualLabConstruction")
	public ModelAndView newVirtualLabConstruction(
			@ModelAttribute VirtualLabConstruction virtualLabConstruction,
			@RequestParam int modelId,
			@ModelAttribute("selected_academy") String acno) {
		// 新建ModelAndView对象；
		ModelAndView mav = new ModelAndView();



/*		if (authorityService.getAuthorityResourses(modelId).getIsActive() == 0) {
			mav.setViewName("system/authorityManage/accessDenied.jsp");
			return mav;
		} else {
			mav.addObject("virtualLabConstruction", new VirtualLabConstruction());
			// 中心所属学院
			String academyNumber = labCenterService
					.findLabCenterByPrimaryKey(cid).getSchoolAcademy()
					.getAcademyNumber();
			mav.addObject("cid", cid);
			User user = shareService.getUser();
			mav.addObject("user", user);
			Set<Authority> as = user.getAuthorities();
			if (as.size() > 0) {
				for (Authority a : as) {
					if (a.getId() == 2 || a.getId() == 5 || a.getId() == 10) {
						mav.addObject("admin", true);
					} else {
						mav.addObject("admin", false);
					}
				}
			}
			mav.setViewName("labconstruction/newVirtualLabConstruction.jsp");
			return mav;
		}*/
		mav.addObject("virtualLabConstruction", new VirtualLabConstruction());
		// 中心所属学院
/*		String academyNumber = labCenterService
				.findLabCenterByPrimaryKey(cid).getSchoolAcademy()
				.getAcademyNumber();
		mav.addObject("cid", cid);
		User user = shareService.getUser();
		mav.addObject("user", user);
		Set<Authority> as = user.getAuthorities();
		if (as.size() > 0) {
			for (Authority a : as) {
				if (a.getId() == 2 || a.getId() == 5 || a.getId() == 10) {
					mav.addObject("admin", true);
				} else {
					mav.addObject("admin", false);
				}
			}
		}*/
		
		mav.addObject("ID1", virtualLabConstructionService.findAllSchoolMajor());
		
		mav.setViewName("labconstruction/newVirtualLabConstruction.jsp");
		return mav;
		
	}

	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目申请列表
	 * 作者：李德
	 * 时间：2015-03-10
	 ****************************************************************************/
	@RequestMapping("/labconstruction/editVirtualLabConstruction")
	public ModelAndView editVirtualLabConstruction(@RequestParam Integer idKey, int modelId) {
		ModelAndView mav = new ModelAndView();

		// id对应的实验队伍
		VirtualLabConstruction virtualLabConstruction = virtualLabConstructionService.findVirtualLabConstructionByPrimaryKey(idKey);
		mav.addObject("virtualLabConstruction", virtualLabConstructionDAO
				.findVirtualLabConstructionByPrimaryKey(idKey));

		System.out.println("11111="+virtualLabConstruction.getMajorName());
	    Set<SchoolMajor> cstaffs=new HashSet<SchoolMajor>();
	    String[] arraycstaff=virtualLabConstruction.getMajorName().split(",");
	    //读取本条数据的面向专业
        for(String strAuth:arraycstaff)
        {		
	      SchoolMajor cstaff=schoolMajorDAO.findSchoolMajorByPrimaryKey(strAuth);
	      cstaffs.add(cstaff);

          System.out.println("2222222="+cstaff);
         }
        mav.addObject("cstaffs",cstaffs);
        //获取所有专业，并对本条数据的面向专业设置为灰色
        Set<SchoolMajor> schoolMajorEdit=virtualLabConstructionService.findAllSchoolMajor();
        schoolMajorEdit.remove(cstaffs);
		mav.addObject("ID1", schoolMajorEdit);
		
		mav.setViewName("labconstruction/editVirtualLabConstruction.jsp");
		return mav;
	}

	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目申请列表
	 * 作者：李德
	 * 时间：2015-03-10
	 ****************************************************************************/
	@RequestMapping("/saveVirtualLabConstruction")
	public String saveVirtualLabConstruction(
			@ModelAttribute VirtualLabConstruction virtualLabConstruction) {
		if (virtualLabConstruction.getId() != null) {
			VirtualLabConstruction team = virtualLabConstructionService
					.findVirtualLabConstructionByPrimaryKey(virtualLabConstruction
							.getId());
			//labRoomTeamManage.setUser(team.getUser());
		}
		System.out.println("virtualLabConstruction.getId="+virtualLabConstruction.getId());
		System.out.println("virtualLabConstruction.getMajorName="+virtualLabConstruction.getMajorName());
		//virtualLabConstructionService.saveVirtualLabConstruction(virtualLabConstruction);
		virtualLabConstructionService.save(virtualLabConstruction);
		return "redirect:/labconstruction/listVirtualLabConstruction?page=1&modelId=811";
	}

	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目申请列表
	 * 作者：李德
	 * 时间：2015-03-10
	 ****************************************************************************/
	@RequestMapping("/labconstruction/deleteVirtualLabConstruction")
	public String deleteVirtualLabConstruction(@RequestParam Integer idKey, int modelId) {
		VirtualLabConstruction virtualLabConstruction = virtualLabConstructionService
				.findVirtualLabConstructionByPrimaryKey(idKey);

		virtualLabConstructionService.deleteVirtualLabConstruction(virtualLabConstruction);

		return "redirect:/labconstruction/listVirtualLabConstruction?page=1&modelId=911";
	}

	/*********************************************************************************
	 * 功能：实验室建设项目-导出
	 * 作者：李德
	 * 时间：2015-03-10
	 ************************************************************************************/
	@RequestMapping("/labconstruction/exportVirtualLabConstruction")
	public void exportExcelVirtualLab(@ModelAttribute VirtualLabConstruction virtualLabConstruction,@RequestParam int modelId,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.setContentType("application/excel;charset=UTF-8");
		// 获取所有的客户信息赋值给集合alls；
		List<VirtualLabConstruction> virtualLabConstructions = virtualLabConstructionService.findAllVirtualLabByVirtualLab(virtualLabConstruction, 1, virtualLabConstructionService.findAllVirtualLabByVirtualLab(virtualLabConstruction).size());
		virtualLabConstructionService.exportExcelVirtualLabTest(virtualLabConstructions,request,response);
		
	}
	
	/****************************************************************************
	 * 功能：实验室建设项目-实验室建设项目申请列表
	 * 作者：李德
	 * 时间：2015-03-10
	 ****************************************************************************/
	@RequestMapping("/labconstruction/viewVirtualLabConstruction")
	public ModelAndView viewVirtualLabConstruction(@RequestParam Integer idKey, int modelId,
                                                   @ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();

/*		// 权限控制
		if (authorityService.getAuthorityResourses(modelId).getIsActive() == 0) {
			mav.setViewName("system/authorityManage/accessDenied.jsp");
			return mav;
		} else {

			// id对应的实验队伍
			mav.addObject("labRoomTeamManage", virtualLabConstructionDAO
					.findVirtualLabConstructionByPrimaryKey(idKey));
			// 中心所属学院
			String academyNumber = labCenterService
					.findLabCenterByPrimaryKey(cid).getSchoolAcademy()
					.getAcademyNumber();
			mav.setViewName("labconstruction/editVirtualLabConstruction.jsp");
			return mav;
		}*/
		
		// id对应的实验队伍
		VirtualLabConstruction virtualLabConstruction =  virtualLabConstructionDAO.findVirtualLabConstructionByPrimaryKey(idKey);
		mav.addObject("virtualLabConstruction", virtualLabConstruction);
		// 中心所属学院
/*		String academyNumber = labCenterService
				.findLabCenterByPrimaryKey(cid).getSchoolAcademy()
				.getAcademyNumber();*/
		
		Map map=new HashMap();
		
		System.out.println("11111="+virtualLabConstruction.getMajorName());
		if(!(virtualLabConstruction.getMajorName()==null))
		{
		    Set<SchoolMajor> cstaffs=new HashSet<SchoolMajor>();
		    String[] arraycstaff=virtualLabConstruction.getMajorName().split(",");
		    //
	        for(String strAuth:arraycstaff)
	        {		
		
		      SchoolMajor cstaff=schoolMajorDAO.findSchoolMajorByPrimaryKey(strAuth);
		      cstaffs.add(cstaff);
	
	          System.out.println("2222222="+cstaff);
	          map.put(virtualLabConstruction.getId(), cstaffs);

	         }
		}
		
		mav.addObject("map",map);
		
		mav.setViewName("labconstruction/viewVirtualLabConstruction.jsp");
		return mav;
	}
	
	/**************************************************************************
	 * 功能：教学计划管理-实排课管理首页
	 * 作者：李德
	 * 时间：2015-04-27
	 **************************************************************************/
	@RequestMapping("/labconstruction/operation/listOperationItemReport")
	public ModelAndView timetableAdmin() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("labconstruction/operation/listOperationItemReport.jsp");
		return mav;

	}
	
	/****************************************************************************
	 * 功能：教学计划管理-实践教学综合管理列表
	 * 作者：李德
	 * 时间：2015-04-24
	 ****************************************************************************/
	@RequestMapping("/labconstruction/operation/indexOperationExperiment")
	public ModelAndView indexLabExperiment(@ModelAttribute OperationItem operationItem, @RequestParam int currpage, @ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		int pageSize=20;
		int size=virtualLabConstructionService.getOperationItemExperiment(operationItem,acno);
	
		List<OperationItem> s=virtualLabConstructionService.getOperationItemExperimentpage(operationItem,currpage,pageSize,acno);
		Map<String,Integer> pageModel = shareService.getPage(pageSize, currpage,size);
	
	    mav.addObject("newFlag", true);
	    mav.addObject("pageModel",pageModel);
		mav.addObject("totalRecords", size);
		mav.addObject("currpage", currpage);
		mav.addObject("pageSize", pageSize);
		mav.addObject("operationItem", operationItem);
		mav.addObject("user", shareService.getUser());
		 int yse=0;//yes等于0不是所选择实验室的主任 1是
		  for (LabCenter labCenter : shareService.getUser().getLabCentersForCenterManager()) {
		  	// 中心修改学院模糊问题备注
//			  if(labCenter.getId().equals(sid)){
//			    yse=1;
//			   }
		  }
		 mav.addObject("yes", yse);
		//查找所有的实验大纲
		mav.addObject("Outlinelist",s );
		//mav.addObject("operationItemExperiment",authorityService.getAuthorityResourses(951));
		mav.setViewName("labconstruction/operation/indexOperationExperiment.jsp");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：教学计划管理-实践教学综合管理详细
	 * 作者：李德
	 * 时间：2015-04-24
	 ****************************************************************************/
	@RequestMapping("/labconstruction/operation/viewOperationitemExperiment")
	public ModelAndView viewOperationitem(@RequestParam int idkey) {
		ModelAndView mav = new ModelAndView();
        mav.addObject("infor", operationService.getopertioniteminfor(idkey));  
        mav.addObject("operationItem", operationService.getoperationItemlist());  
		//附件
		mav.addObject("CommonDocuments", virtualLabConstructionService.findCommonDocumentByOperationItemId(idkey));
		mav.setViewName("labconstruction/operation/viewOperationitemExperiment.jsp");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：教学计划管理-实践教学综合管理报告上传页面
	 * 作者：李德
	 * 时间：2015-04-24
	 ****************************************************************************/
	@RequestMapping("/labconstruction/operation/operationItemExperiment")
	public ModelAndView operationItemReport(@RequestParam int idkey,
			@ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
        mav.addObject("infor", operationService.getopertioniteminfor(idkey));  
        mav.addObject("operationItem", operationService.getoperationItemlist());
		//附件
		mav.addObject("CommonDocuments", virtualLabConstructionService.findCommonDocumentByOperationItemId(idkey));
		mav.setViewName("labconstruction/operation/operationItemExperiment.jsp");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：AJAX 上传实验室建设申请附件，并返回文档字符串信息
	 * 作者：李德
	 * 时间：2015-04-21
	 ****************************************************************************/
	@RequestMapping("/labconstruction/uploadFileOperationItem")
	public @ResponseBody
    String uploadFileOperationItem(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response, BindException errors) throws Exception {
		String s=virtualLabConstructionService.uploadFile(id,request,response);
		System.out.println(id);
		return shareService.htmlEncode(s);
	}
		
	/****************************************************************************
	 * 功能：删除实验室建设申请的文档
	 * 作者：李德
	 * 时间：2015-04-27
	 ****************************************************************************/
	@RequestMapping("/labconstruction/operation/deleteOperationItemCommonDocument")
	public ModelAndView deleteOperationItemCommonDocument(@RequestParam Integer id, Integer AppId, Integer flag)  {
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
		
		if(flag==1){
			System.out.println("flag"+flag);
			mav.setViewName("redirect:/labconstruction/operation/operationItemExperiment?idKey="+AppId);
		}else{
			mav.setViewName("redirect:/labconstruction/operation/operationItemPractice?idKey="+AppId);
		}
		return mav;
	}
	
	/****************************************************************************
	 * 功能：教学计划管理-实践教学综合管理列表
	 * 作者：李德
	 * 时间：2015-04-27
	 ****************************************************************************/
	@RequestMapping("/labconstruction/operation/indexOperationPractice")
	public ModelAndView indexLabExperimentPractice(@ModelAttribute OperationItem operationItem,@RequestParam int currpage,@ModelAttribute("selected_academy") String acno) {
		ModelAndView mav = new ModelAndView();
		int pageSize=20;
		int size=virtualLabConstructionService.getOperationItemPractice(operationItem,acno);
		
		List<OperationItem> s=virtualLabConstructionService.getOperationItemPracticepage(operationItem,currpage,pageSize,acno);
		Map<String,Integer> pageModel = shareService.getPage(pageSize, currpage,size);
	
	    mav.addObject("newFlag", true);
	    mav.addObject("pageModel",pageModel);
		mav.addObject("totalRecords", size);
		mav.addObject("currpage", currpage);
		mav.addObject("pageSize", pageSize);
		mav.addObject("operationItem", operationItem);
		mav.addObject("user", shareService.getUser());
		 int yse=0;//yes等于0不是所选择实验室的主任 1是
//		  for (LabCenter labCenter : shareService.getUser().getLabCentersForCenterManager()) {
//			  if(labCenter.getId().equals(sid)){
//			    yse=1;
//			   }
//		  }
		 mav.addObject("yes", yse);
		//查找所有的实验大纲
		mav.addObject("Outlinelist",s );
		mav.setViewName("labconstruction/operation/indexOperationPractice.jsp");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：教学计划管理-实践教学综合管理实习报告上传页面
	 * 作者：李德
	 * 时间：2015-04-27
	 ****************************************************************************/
	@RequestMapping("/labconstruction/operation/operationItemPractice")
	public ModelAndView operationItemPractice(@RequestParam int idkey) {
		ModelAndView mav = new ModelAndView();
        mav.addObject("infor", operationService.getopertioniteminfor(idkey));  
        mav.addObject("operationItem", operationService.getoperationItemlist());
		//附件
		mav.addObject("CommonDocuments", virtualLabConstructionService.findCommonDocumentByOperationItemId(idkey));
		mav.setViewName("labconstruction/operation/operationItemPractice.jsp");
		return mav;
	}
	

	
	/****************************************************************************
	 * 功能：教学计划管理-实践教学综合管理详细
	 * 作者：李德
	 * 时间：2015-04-27
	 ****************************************************************************/
	@RequestMapping("/labconstruction/operation/viewOperationitemPractice")
	public ModelAndView viewOperationitemPractice(@RequestParam int idkey) {
		ModelAndView mav = new ModelAndView();
        mav.addObject("infor", operationService.getopertioniteminfor(idkey));  
        mav.addObject("operationItem", operationService.getoperationItemlist()); 
		//附件
		mav.addObject("CommonDocuments", virtualLabConstructionService.findCommonDocumentByOperationItemId(idkey));
		mav.setViewName("labconstruction/operation/viewOperationitemPractice.jsp");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：下载文件 
	 * 作者：李德
	 * 时间：2015-04-27
	 ****************************************************************************/
	@RequestMapping("/labconstruction/operation/downloadFile")
	public void downloadFile(int idkey, HttpServletRequest request,HttpServletResponse response) {
		shareService.downloadFileByDocumentId(idkey,request,response);
	}
	
	/****************************************************************************
	 * 功能：虚拟实验室报表---LabAnnex
	 * 作者：李德
	 * 时间：2015-04-28
	 ****************************************************************************/
	@RequestMapping("/labconstruction/annex/listLabconstructAnnex")
	public ModelAndView LabList(@ModelAttribute LabAnnex labAnnex,@RequestParam int page,int modelId,@ModelAttribute("selected_academy") String acno){
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//查询表单的对象
		mav.addObject("labAnnex", labAnnex);
		int pageSize=10;//每页20条记录
		//查询出来的总记录条数
		int totalRecords=labAnnexService.findLabAnnexByLabAnnex(labAnnex).size();
		//分页信息
		Map<String,Integer> pageModel =shareService.getPage(pageSize, page,totalRecords);
		//根据分页信息查询出来的记录
		List<LabAnnex> listLabAnnex=labAnnexService.findLabAnnexByLabAnnex(labAnnex,page,pageSize);
		mav.addObject("listLabAnnex",listLabAnnex);
		mav.addObject("pageModel",pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("page", page);
		mav.addObject("pageSize", pageSize);

		mav.setViewName("labconstruction/annex/listLabconstructAnnex.jsp");
		return mav;

	}
	
	/*********************************************************************************
	 * 功能：虚拟实验室LabAnnex-导出
	 * 作者：李德
	 * 时间：2015-04-29
	 ************************************************************************************/
	@RequestMapping("/labconstruction/annex/exportLabconstructAnnex")
	public void exportLabconstructAnnex(@ModelAttribute LabAnnex labAnnex,@RequestParam int modelId,@ModelAttribute("selected_academy") String acno,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.setContentType("application/excel;charset=UTF-8");
		// 获取所有的信息赋值给集合alls；
		List<LabAnnex> listLabAnnex=labAnnexService.findLabAnnexByLabAnnex(labAnnex,1,20);
		virtualLabConstructionService.exportExcelLabconstructAnnex(listLabAnnex,request,response);
	}
	
}