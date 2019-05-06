package net.zjcclims.web.lab;

import java.net.BindException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.LabCenterDAO;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.CommonDocumentService;
import net.zjcclims.service.common.CommonVideoService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabAnnexService;
import net.zjcclims.service.lab.LabCenterService;
import net.zjcclims.service.system.SystemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller("LabAnnexController")
@SessionAttributes("selected_academy")
@RequestMapping("/labAnnex")
public class LabAnnexController<JsonResult> {

	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register // static // property // editors.
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
	@Autowired
	private ShareService shareService;
	@Autowired
	private LabAnnexService labAnnexService;
	@Autowired
	private LabCenterService labCenterService;
	@Autowired
	private SystemService systemService;
	@Autowired
	LabCenterDAO labCenterDAO;
	@Autowired
	CommonDocumentService commonDocumentService;
	@Autowired
	CommonVideoService commonVideoService;

	/****************************************************************************
	 * 功能：获取labannex列表
	 * 作者：徐文
	 * 日期：2016-4-26
	 ****************************************************************************/
	@RequestMapping("/listLabAnnex")
	public ModelAndView listLabAnnex(@RequestParam int currpage, @ModelAttribute LabAnnex labAnnex,
									 @ModelAttribute("selected_academy") String acno, HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		int pageSize = CommonConstantInterface.INT_PAGESIZE;
		int totalRecords = labAnnexService.findAllLabAnnexByQuery(1, -1, labAnnex,acno,request).size();
		List<LabAnnex> listLabAnnex=labAnnexService.findAllLabAnnexByQuery(currpage, pageSize, labAnnex,acno,request);
		for (LabAnnex labAnnex2 : listLabAnnex) {
			Set<LabRoom> labRooms=labAnnex2.getLabRooms();
			int labNumber=0;
			if (labRooms != null && labRooms.size() > 0) {
				for (LabRoom labRoom : labRooms) {
					if(labRoom.getLabCategory()==1) {
						labNumber = labNumber + 1;
					}
				}
			}
			labAnnex2.setLabNumber(labNumber+"");
			labAnnexService.saveLabAnnex(labAnnex2);
		}
		mav.addObject("listLabAnnex", listLabAnnex);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("page",currpage);
		mav.setViewName("lab/lab_annex/listLabAnnex.jsp");
		return mav;
	}
	/**********************************************************************************************
	 * 功能：新建实验室
	 * 姓名：徐文
	 * 日期：2016-4-27
	 *************************************************************************************************/
	@RequestMapping("/newLabAnnex")
	public ModelAndView newLabAnnex(HttpServletRequest request,int page){
		ModelAndView mav = new ModelAndView();
		//实验室类别
		LabAnnex labAnnex = new LabAnnex();
		labAnnex.setCreatedAt(Calendar.getInstance());
		
		mav.addObject("isNew", 1);
		mav.addObject("CDictionary", shareService.getLabType("c_lab_annex_type"));
		mav.addObject("labAnnex", labAnnex);
		mav.addObject("subject12s", systemService.getAllSystemSubject12(1, -1));  //学科数据(12版)
		mav.addObject("listLabCenter", labCenterService.findAllLabCenterByQuery(request,new LabCenter(), 1, -1));
		mav.addObject("page",page);
		mav.setViewName("lab/lab_annex/newLabAnnex.jsp");
		return mav;
	}
	/**************************************************************************************
	 * 功能：保存实验室数据
	 * 姓名：徐文
	 * 日期：2016-4-27
	 ***************************************************************************************/
	@RequestMapping("/saveLabAnnex")
	public String saveLabAnnex(@ModelAttribute LabAnnex labAnnex,int page){
		labAnnex.setLabNumber(String.valueOf(0));
		labAnnexService.saveLabAnnex(labAnnex);
		
		return "redirect:/labAnnex/listLabAnnex?currpage="+page;
	}
	/*******************************************************************************************
	 * 功能：删除实验室数据
	 * 姓名：徐文
	 * 日期：2016-4-27
	 ******************************************************************************************/
	@RequestMapping("/deleteLabAnnex")
	public String deleteLabAnnex(@RequestParam int labAnnexId,int page){
		labAnnexService.deleteLabAnnex(labAnnexId);  
		
		return "redirect:/labAnnex/listLabAnnex?currpage="+page;
	}
	/*****************************************************************************************
	 * 功能：编辑实验室
	 * 姓名：徐文
	 * 日期：2016-4-28
	 *****************************************************************************************/
	@RequestMapping("/editLabAnnex")
	public ModelAndView editLabAnnex(@RequestParam int labAnnexId, HttpServletRequest request,int page){
		ModelAndView mav = new ModelAndView();
		mav.addObject("isNew", 0);
		mav.addObject("CDictionary", shareService.getCDictionaryData("c_lab_annex_type"));
		mav.addObject("labAnnex", labAnnexService.findLabAnnexByPrimaryKey(labAnnexId));
		mav.addObject("subject12s", systemService.getAllSystemSubject12(1, -1));  //学科数据(12版)
		mav.addObject("listLabCenter", labCenterService.findAllLabCenterByQuery(request, new LabCenter(), 1, -1));
		mav.addObject("page",page);
		mav.setViewName("lab/lab_annex/newLabAnnex.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：查看实验室详细信息
	 * 作者：徐文
	 * 日期：2016-4-28
	 ****************************************************************************/
	@RequestMapping("/getLabAnnex")
	public ModelAndView getLabAnnex(@RequestParam int id){
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室
		LabAnnex labAnnex=labAnnexService.findLabAnnexByPrimaryKey(id);
		mav.addObject("labAnnex", labAnnex);
		
		mav.setViewName("lab/lab_annex/getLabAnnex.jsp");
		return mav;
	}

	/****************************************************************************
	 * 功能：修改实验室
	 * 作者：李小龙
	 * 时间：2014-07-29
	 ****************************************************************************/
	@RequestMapping("/appointment/updateLabAnnex")
	public ModelAndView updateLabAnnex(@RequestParam Integer id){
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室
		LabAnnex labAnnex=labAnnexService.findLabAnnexByPrimaryKey(id);
		mav.addObject("labAnnex", labAnnex);
		//实验室类别
		mav.addObject("CLabAnnexTypes", shareService.getCDictionaryData("c_lab_annex_type"));
		//所属中心
		Set<LabCenter> labCenters=new HashSet<LabCenter>();
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") != -1){
			labCenters=labCenterDAO.findAllLabCenters();
		}else{
			User user=shareService.getUser();
			labCenters=user.getSchoolAcademy().getLabCenters();
		}
		mav.addObject("labCenters", labCenters);
		//实验室的图片
		List<CommonDocument> Images=commonDocumentService.findImageByLabAnnexId(id);
		mav.addObject("Images", Images);
		//实验室的视频
		List<CommonVideo> videos=commonVideoService.findVideoByLabAnnexId(id);
		mav.addObject("videos", videos);


		mav.setViewName("lab/lab_annex/updateLabAnnex.jsp");
		return mav;
	}

	/****************************************************************************
	 * 功能：给实验室上传图片
	 * 作者：李小龙
	 * 时间：2014-07-28
	 ****************************************************************************/
	@RequestMapping("/appointment/imageUpload")
	public @ResponseBody
	String imageUpload(HttpServletRequest request, HttpServletResponse response, BindException errors, Integer id) throws Exception {
		labAnnexService.imageUpload(request, response,id);
		return "ok";
	}

	/****************************************************************************
	 * 功能：给实验室上传视频
	 * 作者：李小龙
	 * 时间：2014-07-28
	 ****************************************************************************/
	@RequestMapping("/appointment/videoUpload")
	public @ResponseBody String videoUpload(HttpServletRequest request, HttpServletResponse response, BindException errors,Integer id) throws Exception {
		labAnnexService.videoUpload(request, response,id);
		return "ok";
	}

	/****************************************************************************
	 * 功能：删除实验室图片
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/appointment/deleteLabAnnexVideo")
	public ModelAndView deleteLabAnnexVideo(@RequestParam Integer id){
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室视频
		CommonVideo video=commonVideoService.findVideoByPrimaryKey(id);
		//视频所属的实验室
		LabAnnex annex=video.getLabAnnex();
		int idkey=annex.getId();
		commonVideoService.deleteCommonVideo(video);
		//删除服务器上的文件

		mav.setViewName("redirect:/labAnnex/appointment/updateLabAnnex?id="+idkey);
		return mav;
	}

	/****************************************************************************
	 * 功能：删除实验室图片
	 * 作者：李小龙
	 ****************************************************************************/
	@RequestMapping("/appointment/deleteLabAnnexDocument")
	public ModelAndView deleteLabAnnexDocument(@RequestParam Integer id){
		//新建ModelAndView对象；
		ModelAndView mav=new ModelAndView();
		//id对应的实验室图片
		CommonDocument doc=commonDocumentService.findCommonDocumentByPrimaryKey(id);
		//图片所属的实验室
		LabAnnex annex=doc.getLabAnnex();
		int idkey=annex.getId();
		commonDocumentService.deleteCommonDocument(doc);
		//删除服务器上的文件
		mav.setViewName("redirect:/labAnnex/appointment/updateLabAnnex?id="+idkey);
		return mav;
	}
}