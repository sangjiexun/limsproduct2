package net.zjcclims.web.credit;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.CreditOptionDAO;
import net.zjcclims.domain.CreditOption;
import net.zjcclims.domain.LabCenter;
import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.User;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.credit.CreditOptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller("CreditOptionController")
@SessionAttributes("selected_academy")
public class CreditOptionController<JsonResult> {

	@Autowired
	CreditOptionService creditOptionService;
	@Autowired
	ShareService shareService;
	@Autowired
	private CreditOptionDAO creditOptionDAO;
	
	/***************************** 
	*Description 实训室开放预约-信誉积分设置
	*
	*@param:cid(实训中心id)
	*@author:黄阿娜
	*@date:2017-07-12
	*****************************/
	@RequestMapping("/credit/listCreditOption")
	public ModelAndView listCreditOption(@RequestParam int currpage,
			@ModelAttribute("selected_academy") String acno,
			@ModelAttribute CreditOption creditOption) {
		ModelAndView mav = new ModelAndView();
		// 设置分页变量并赋值为20；
		int pageSize = CommonConstantInterface.INT_PAGESIZE;
		// 设置学期表的总记录数并赋值
		int totalRecords = creditOptionService.getCreditOptionTotalRecords();
		Map<String, Integer> pageModel = shareService.getPage(currpage, 
				pageSize, totalRecords);
		mav.addObject("newFlag", true);
		mav.addObject("pageModel", pageModel);
		mav.addObject("page", currpage);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("name",creditOption.getName());
		/*mav.addObject("allAPractice", aPracticeDetailService.searchAPractices());*/
		mav.addObject("creditOptions",creditOptionService.creditOptions(creditOption));
		mav.addObject("creditOption", new CreditOption());
		// 将该Model映射到listCreditOption.jsp;
		mav.setViewName("credit/listCreditOption.jsp");
		return mav;
	}
	/************************************************************
	 * Description 实训室开放预约-新增扣分项
	 * 
	 * @param request
	 * @author 黄阿娜
	 * @date 2017-07-13
	 ************************************************************/
	@RequestMapping("/credit/newCreditOption")
	public ModelAndView newCreditOption(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("newFlag", true);
		mav.addObject("CreditOption", new CreditOption());
		// mav.addObject("departments", shareService.getDepartmentsMap());
		// mav.addObject("educations", shareService.getEducationsMap());
		// mav.addObject("degrees", shareService.getDegreesMap());
		// mav.addObject("genders", shareService.getGendersMap());
		mav.addObject("page", shareService.getCurrpage(request));
		mav.setViewName("credit/newCreditOption.jsp");
		return mav;
	}

	/************************************************************
	 * Description 实训室开放预约-修改扣分项
	 * 
	 * @param idKey(主键)
	 * @author 黄阿娜
	 * @date 2017-07-13
	 ************************************************************/
	@RequestMapping("/credit/editCreditOption")
	public ModelAndView editCreditOption(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("newFlag", true);
		mav.addObject("idKey", idKey);
		CreditOption creditOption = creditOptionDAO.findCreditOptionByPrimaryKey(idKey);
		mav.addObject("creditOption", creditOption);
		mav.setViewName("credit/editCreditOption.jsp");
		return mav;
	}

	/************************************************************
	 * Description 实训室开放预约-删除扣分项
	 * 
	 * @param idKey(主键)
	 * @author 黄阿娜
	 * @date 2017-07-13
	 ************************************************************/
	@RequestMapping("/credit/deleteCreditOption")
	public String deleteCreditOption(@RequestParam Integer idKey) {
		CreditOption creditOption = creditOptionService.findCreditOptionById(idKey);
		creditOptionService.deleteCreditOption(creditOption);
		return "redirect:/credit/listCreditOption?currpage=1";
	}

	/************************************************************
	 * Description 实训室开放预约-保存新建的扣分项
	 * 
	 * @param creditOption
	 * @author 黄阿娜
	 * @date 2017-07-13
	 ************************************************************/
	@RequestMapping("/credit/saveNewCreditOption")
	public String saveCreditOption(@ModelAttribute CreditOption creditOption) {
		creditOptionService.saveCreditOption(creditOption);
		return "redirect:/credit/listCreditOption?currpage=1";
	}
	/************************************************************
	 * Description 实训室开放预约-提交新建的扣分项
	 * 
	 * @param idKey(主键)
	 * @author 黄阿娜
	 * @date 2017-07-13
	 ************************************************************/
	@RequestMapping("/credit/submitNewCreditOption")
	public String submitCreditOption(@RequestParam Integer idKey) {
		CreditOption creditOption = creditOptionDAO.findCreditOptionByPrimaryKey(idKey);
		creditOptionService.submitCreditOption(creditOption);
		return "redirect:/credit/listCreditOption?currpage=1";
	}
  }