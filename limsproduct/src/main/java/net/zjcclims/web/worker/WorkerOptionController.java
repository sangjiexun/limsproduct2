package net.zjcclims.web.worker;


import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.CDictionaryDAO;
import net.zjcclims.dao.WorkerOptionDAO;
import net.zjcclims.domain.CDictionary;
import net.zjcclims.domain.User;
import net.zjcclims.domain.WorkerOption;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.worker.WorkerOptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller("WorkerOptionController")
@SessionAttributes("selected_academy")
public class WorkerOptionController<JsonResult> {

	@Autowired
	WorkerOptionService workerOptionService;
	@Autowired
	ShareService shareService;
	@Autowired
	private WorkerOptionDAO workerOptionDAO;
	@Autowired
	private CDictionaryDAO cDictionaryDAO;

	/************************************************** 
	*@功能:根据查询条件获取可预约工位数
	*@作者:张德冰
	*@时间:2018.03.28
	***************************************************/
	@RequestMapping("/worker/listWorkerOption")
	public ModelAndView listWorkerOption(@RequestParam int currpage,
			@ModelAttribute WorkerOption workerOption) {
		ModelAndView mav = new ModelAndView();
		CDictionary cDictionary = shareService.getCDictionaryByCategory("max_reservation_count","1");
		mav.addObject("cDictionary",cDictionary);
		mav.setViewName("worker/cDictionaryCount.jsp");
		/*一下是按教师设置单次可预约最大工位数方案
		// 设置分页变量并赋值为20；
		int pageSize = CommonConstantInterface.INT_PAGESIZE;
		//根据查询条件获取可预约工位数
		List< WorkerOption> workerOptions=workerOptionService.workerOptions(workerOption,currpage - 1, pageSize);
		// 获取可预约工位数的总记录数
		int totalRecords = workerOptions.size();
		Map<String, Integer> pageModel = shareService.getPage(currpage, 
				pageSize, totalRecords);
		//获取教师信息
		List<User> teachers = workerOptionService.findAllTeacher();
		mav.addObject("teachers",teachers);
		mav.addObject("pageModel", pageModel);
		mav.addObject("currpage", currpage);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("workerOptions",workerOptions);
		mav.addObject("workerOption", new WorkerOption());
		mav.setViewName("worker/listWorkerOption.jsp");*/
		return mav;
	}
	/************************************************************************
	 *@Description:保存实训室预约最大可预约工位数
	 *@Author:孙虎
	 *@Date:2018/5/29
	 ************************************************************************/
	@RequestMapping("/worker/saveReservationCount")
	public ModelAndView saveReservationCount(@RequestParam String count){
		ModelAndView mav = new ModelAndView("redirect:/worker/listWorkerOption?currpage=1");
		CDictionary cDictionary = shareService.getCDictionaryByCategory("max_reservation_count","1");
		try {
			cDictionary.setCName(count);
		}catch (NullPointerException e){
			System.out.println("cDictionary表中少记录，去看数据库日志");
			e.printStackTrace();
		}
		cDictionaryDAO.store(cDictionary);
		cDictionaryDAO.flush();
		return mav;
	}
	/************************************************** 
	*@功能:新建可预约工位数
	*@作者:张德冰
	*@时间:2018.03.28
	***************************************************/
	@RequestMapping("/worker/newWorkerOption")
	public ModelAndView newworkerOption() {
		ModelAndView mav = new ModelAndView();
		//用来判断是修改还是新建:0、新建
		int flag=0;
		mav.addObject("flag", flag);
		//获取教师信息
		List<User> teachers = workerOptionService.findAllTeacher();
		mav.addObject("teachers",teachers);
		mav.addObject("workerOption", new WorkerOption());
		mav.setViewName("worker/newWorkerOption.jsp");
		return mav;
	}
	
	/************************************************** 
	*@功能:保存可预约工位数
	*@作者:张德冰
	*@时间:2018.03.28
	***************************************************/
	@RequestMapping("/worker/saveWorkerOption")
	public String saveworkerOption(@ModelAttribute WorkerOption workerOption) {
		workerOptionService.saveWorkerOption(workerOption);
		return "redirect:/worker/listWorkerOption?currpage=1";
	}
	
	/************************************************** 
	*@功能:删除可预约工位数
	*@作者:张德冰
	*@时间:2018.03.28
	***************************************************/
	@RequestMapping("/worker/deleteWorkerOption")
	public String deleteWorkerOption(@RequestParam Integer id) {
		WorkerOption workerOption = workerOptionDAO.findWorkerOptionByPrimaryKey(id);
		workerOptionService.deleteWorkerOption(workerOption);
		return "redirect:/worker/listWorkerOption?currpage=1";
	}
	
	/************************************************** 
	*@功能:修改可预约工位数
	*@作者:张德冰
	*@时间:2018.03.28
	***************************************************/
	@RequestMapping("/worker/editWorkerOption")
	public ModelAndView editWorkerOption(@RequestParam Integer id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", id);
		//用来判断是修改还是新建:1、修改
		int flag=1;
		mav.addObject("flag", flag);
		//获取教师信息
		List<User> teachers = workerOptionService.findAllTeacher();
		mav.addObject("teachers",teachers);
		//获取需要修改的可预约工位数信息
		WorkerOption workerOption = workerOptionDAO.findWorkerOptionByPrimaryKey(id);
		mav.addObject("workerOption", workerOption);
		mav.setViewName("worker/newWorkerOption.jsp");
		return mav;
	}

  }