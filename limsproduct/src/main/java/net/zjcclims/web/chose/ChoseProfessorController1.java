/****************************************************************************
 * 命名规范：
 * 本控制层调整必须是按照如下的路径调整：@RequestMapping("/device/system/sample")，全部小写。
 * 列表信息listxxxx，獲取信息：getxxxx 编辑信息：editxxxx 删除信息：detelexxxx 新增信息 newxxxx
 * 导出信息exportxxxx 保存信息：savexxxx 
 ****************************************************************************/

package net.zjcclims.web.chose;

import com.google.inject.servlet.RequestParameters;
import net.zjcclims.dao.*;
import net.zjcclims.domain.ChoseAttention;
import net.zjcclims.domain.ChoseTheme;
import net.zjcclims.service.chose.ChoseProfessorService;
import net.zjcclims.service.chose.ChoseProfessorService1;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/****************************************************************************
 * 功能：导师互选模块 作者：孙虎时间：2017-12-1
 ****************************************************************************/
@Controller("ChoseProfessorController1")
@SessionAttributes("selected_academy")
@RequestMapping("/nwuChose")
public class ChoseProfessorController1<JsonResult> {
	
	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register
																				// static
																				// property
																				// editors.
		binder.registerCustomEditor(java.util.Calendar.class,
				new org.skyway.spring.util.databinding.CustomCalendarEditor());
		binder.registerCustomEditor(byte[].class,
				new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
		binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
		binder.registerCustomEditor(java.math.BigDecimal.class,
				new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
		binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
				Integer.class, true));
		binder.registerCustomEditor(java.util.Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
		binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
		binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
				Long.class, true));
		binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
				Double.class, true));
	}

	@Autowired
    private ShareService shareService;
	@Autowired
    private LabCenterService labCenterService;
	@Autowired
    private ChoseThemeDAO choseThemeDAO;
	@Autowired
    private ChoseAttentionDAO choseAttentionDAO;
	@Autowired
    private ChoseProfessorDAO choseProfessorDAO;
	@Autowired
    private ChoseProfessorBatchDAO choseProfessorBatchDAO;
	@Autowired
    private ChoseProfessorRecordDAO choseProfessorRecordDAO;
	@Autowired
    private ChoseProfessorService1 choseProfessorService1;
	@Autowired
    private ChoseProfessorService choseProfessorService;

	/**
	 * 导师互选大纲列表-管理员
	 * @author 孙虎
	 * 2017.12.1
	 
	@RequestMapping("/ChoseThemeList")
	public ModelAndView ChoseThemeList(HttpServletRequest request,@RequestParam int currpage,@ModelAttribute ChoseTheme choseTheme, @ModelAttribute("selected_academy") String acno){
		ModelAndView mav = new ModelAndView();
		int pageSize = 10;
		int totalRecords = choseProfessorService.findChoseThemesCount(choseTheme);
		List<ChoseTheme> choseThemes = choseProfessorService.findChoseThemes(choseTheme, currpage, pageSize);
		mav.addObject("choseThemes", choseThemes);
		mav.addObject("choseTheme", choseTheme);
		mav.addObject("pageSize", pageSize);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.setViewName("chose/chose_professor/ChoseThemeList.jsp");
		return mav;
	}*/
	
	/**
	 * 导师互选-管理员——注意事项
	 * @author 屈晓瑞
	 * 2017.12.7
	 */
	@RequestMapping("/ChoseAttentionList")
	public ModelAndView ChoseThemeList(HttpServletRequest request, @RequestParam int currpage, @ModelAttribute ChoseAttention choseAttention, @ModelAttribute("selected_academy") String acno){
		ModelAndView mav = new ModelAndView();
		int pageSize = 10;
		int totalRecords = choseProfessorService1.findChoseAttentionsCount(choseAttention);
		List<ChoseAttention> choseAttentions = choseProfessorService1.findChoseAttentions(choseAttention, currpage, pageSize);
		mav.addObject("choseAttentions", choseAttentions);
		mav.addObject("choseThemes",choseProfessorService1.findAllChoseTheme());
		mav.addObject("choseAttention", choseAttention);
		mav.addObject("pageSize", pageSize);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.setViewName("chose/chose_professor/ChoseAttentionList.jsp");
		return mav;
	}
	/**
	 * 删除
	 * @author 屈晓瑞
	 * 2017.12.7
	 */
	@RequestMapping("/deleteChoseAttention")
	public String deleteChoseAttention(@RequestParam int choseAttentionId){
		//ChoseAttention choseAttention=choseProfessorService1.findChoseAttentionByPrimaryKey(choseAttentionId);
		//choseAttention.setIsUsed(0);//假删  
		//choseProfessorService1.saveChoseAttention(choseAttention);
		//labRoomService.deleteLabRoom(labRoomId);  真删  由于和实验项目的外键关系，会报错删不掉
		choseProfessorService1.deleteChoseAttention(choseAttentionId);
		
		return "redirect:/nwuChose/ChoseAttentionList?currpage=1";
	}
	/*******************************
	 * @功能  编辑 导师互选-管理员——注意事项
	 * @author 屈晓瑞
	 * 2017.12.7
	 *****************************/
	@RequestMapping("/editChoseAttention")
	public ModelAndView editChoseAttention(@RequestParam int choseAttentionId){
		ModelAndView mav = new ModelAndView();
		ChoseAttention choseAttention = choseProfessorService1.findChoseAttentionByPrimaryKey(choseAttentionId);
		ChoseTheme choseTheme=choseAttention.getChoseTheme();
		mav.addObject("choseTheme", choseTheme);
	//	mav.addObject("choseThemeTittle", choseTheme.getTittle());
		List<ChoseAttention> choseAttentions = choseProfessorService1.findAllChoseAttention();
		mav.addObject("choseAttentions", choseAttentions);
		List<ChoseTheme> choseThemes = choseProfessorService.findAllChoseThemes();
		mav.addObject("choseThemes", choseThemes);
		mav.addObject("choseAttention",choseAttention);
		mav.addObject("isEdit", false);     //isEdit标记，新建true 编辑 false
		mav.setViewName("chose/chose_professor/editChoseAttention.jsp");
		return mav;
		
	}
	/**
	 * 查看 导师互选-管理员——注意事项
	 * @author 屈晓瑞
	 * 2017.12.7
	 */
		@RequestMapping("/veiwChoseAttention")
		public ModelAndView veiwChoseAttention(@RequestParameters Integer choseAttentionId){
			ModelAndView mav=new ModelAndView();
			List<ChoseTheme> choseThemes = choseProfessorService.findAllChoseThemes();
			mav.addObject("choseThemes", choseThemes);

			ChoseAttention choseAttention=choseProfessorService1.findChoseAttentionByPrimaryKey(choseAttentionId);
			ChoseTheme choseTheme=choseAttention.getChoseTheme();
			mav.addObject("choseTheme", choseTheme);
			
			mav.addObject("choseAttention", choseAttention);
			mav.setViewName("chose/chose_professor/veiwChoseAttention.jsp");
			return mav;
		}
		
		/**
		 * 保存 导师互选-管理员——注意事项
		 * @author 屈晓瑞
		 * 2017.12.7
		 */
		@RequestMapping("/saveChoseAttention")
		public String saveChoseAttention(@ModelAttribute ChoseAttention choseAttention
				){
			
			/*ChoseTheme choseTheme = choseProfessorService.findChoseThemeByPrimaryKey(choseAttention.getChoseTheme().getId());
			choseAttention.setChoseTheme(choseTheme);*/
			
			choseAttention=choseProfessorService1.saveChoseAttention(choseAttention);
			
			return "redirect:/nwuChose/ChoseAttentionList?currpage=1";
		}
		/********************************
		 * 新建 导师互选-管理员——注意事项
		 * @author 屈晓瑞
		 * 2017.12.7
		 *********************************/
		@RequestMapping("/newChoseAttention")
		public ModelAndView newChoseAttention(){
			ModelAndView mav = new ModelAndView();
			List<ChoseTheme> choseThemes = choseProfessorService.findAllChoseThemes();
			mav.addObject("choseThemes", choseThemes);
			ChoseAttention choseAttention=new ChoseAttention();
			mav.addObject("choseAttention", choseAttention);
			ChoseTheme choseTheme=choseAttention.getChoseTheme();
			mav.addObject("choseTheme", choseTheme);
			mav.addObject("isEdit", true);
			
			mav.setViewName("chose/chose_professor/editChoseAttention.jsp");
			return mav;
		}
		/********************************
		 *  检查是否存在该类型的注意事项-管理员
		 * @author 赵晶
		 * 2018.1.24
		 *********************************/
		@RequestMapping("/checkIfRepeatType")
		public @ResponseBody
        String checkIfRepeatType(Integer type){
			List<ChoseAttention> choseAttentionList=choseProfessorService.findChoseAttentionByType(type);
			if(choseAttentionList!=null&&choseAttentionList.size()!=0){
				return "error";
			}
			return "success";
		}

	
}