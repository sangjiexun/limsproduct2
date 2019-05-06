/****************************************************************************
 * 命名规范：
 * 本控制层调整必须是按照如下的路径调整：@RequestMapping("/device/system/sample")，全部小写。
 * 列表信息listxxxx，獲取信息：getxxxx 编辑信息：editxxxx 删除信息：detelexxxx 新增信息 newxxxx
 * 导出信息exportxxxx 保存信息：savexxxx 
 ****************************************************************************/

package net.zjcclims.web.chose;

import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.chose.ChoseProfessorService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabCenterService;
import net.zjcclims.web.common.PConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/****************************************************************************
 * 功能：导师互选模块 作者：孙虎时间：2017-12-1
 ****************************************************************************/
@Controller("ChoseProfessorController")
@SessionAttributes("selected_academy")
@RequestMapping("/nwuChose")
public class ChoseProfessorController<JsonResult> {

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
    private UserDAO userDAO;
	@Autowired
    private ChoseAttentionDAO choseAttentionDAO;
	@Autowired
    private ChoseProfessorDAO choseProfessorDAO;
	@Autowired
    private ChoseProfessorBatchDAO choseProfessorBatchDAO;
	@Autowired
    private ChoseProfessorRecordDAO choseProfessorRecordDAO;
	@Autowired
    private ChoseProfessorService choseProfessorService;
	@Autowired
    private ChoseDessitationForYearDAO choseDessitationForYearDAO;
	@Autowired
    private ChoseAttentionRecordDAO choseAttentionRecordDAO;
	@Autowired
	private ChoseUserDAO choseUserDAO;
	@Autowired
	private PConfig pConfig;

	@RequestMapping("/ChoseRedirect")
	public ModelAndView ChoseRedirect(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		// 当前登录人
				User user = shareService.getUser();
				mav.addObject("user", user);
//		int auth = 0;
//		for(Authority authority:user.getAuthorities()){
//			if(authority.getId()==11) {
//				auth = authority.getId();
//				break;
//			}else if(authority.getId()==2) {
//				auth = authority.getId();
//				break;
//			}else if(authority.getId()==1) {
//				auth = authority.getId();
//				break;
//			}
//		}
//		if(auth==11) {
//			mav.setViewName("redirect:/nwuChose/ChoseThemeList?currpage=1");
//		}else if(auth==2) {
//			mav.setViewName("redirect:/nwuChose/myStudentForProfessor");
//		}else {
//			mav.setViewName("redirect:/nwuChose/myProfessorForStudent");
//		}
		Integer labCenterId = 13;
		mav.addObject("selected_academy", labCenterId);
		mav.setViewName("chose/chose_professor/testList.jsp");
		return mav;
	}

	/**
	 * 导师互选大纲列表-管理员
	 * @author 孙虎
	 * 2017.12.1
	 */
	@RequestMapping("/ChoseThemeList")
	public ModelAndView ChoseThemeList(HttpServletRequest request, @RequestParam int currpage, @ModelAttribute ChoseTheme choseTheme, @ModelAttribute("selected_academy") String acno){
		//清除session保存的choseProfessorList和choseTheme
		HttpSession session = request.getSession();
		session.removeAttribute("choseProfessorList");
		session.removeAttribute("choseTheme");
		ModelAndView mav = new ModelAndView();
		//分页
		int pageSize = 10;
		int totalRecords = choseProfessorService.findChoseThemesCount(choseTheme);
		List<ChoseTheme> choseThemes = choseProfessorService.findChoseThemes(choseTheme, currpage, pageSize);
		if(choseThemes!=null){
			//存放页面显示的数据
			List<ChoseThemeVo> choseThemeVos=new ArrayList<ChoseThemeVo>();
			for(ChoseTheme ct:choseThemes){
				//获得每个大纲下最后一个志愿的结束时间
				Set<ChoseProfessorBatch> batchs = ct.getChoseProfessorBatchs();
				//存放最后一个志愿的结束时间
				Calendar lastBatchEndTime=null;
				if(batchs!=null){
					//map:key-第几志愿 value-结束时间
					Map<Integer,Calendar> map=new HashMap<Integer,Calendar>();
					for(ChoseProfessorBatch cpb:batchs){
						map.put(cpb.getBatchNumber(), cpb.getEndTime());
					}
					if(map!=null){
						//set-存放所有的志愿number
						Set<Integer> set = map.keySet();
						Object[] obj = set.toArray();
						//排序志愿number
						Arrays.sort(obj);
						if(obj!=null&&obj.length!=0){
							//获得最后一个志愿number-即最大的
							Object o= obj[obj.length-1];
							//获取每个大纲下最后一个志愿的结束时间
							lastBatchEndTime=map.get(Integer.parseInt(String.valueOf(o)));
						}
					}
				}
				//页面显示的数据
				ChoseThemeVo ctv=new ChoseThemeVo();
				//设置参数开始
				ctv.setId(ct.getId());
				ctv.setAdvanceDay(ct.getAdvanceDay());
				ctv.setBatchNumber(ct.getBatchNumber());
				ctv.setStartTime(ct.getStartTime());
				ctv.setEndTime(ct.getEndTime());
				ctv.setExpectDeadline(ct.getExpectDeadline());
				ctv.setExpectStartline(ct.getExpectStartline());
				ctv.setTheYear(ct.getTheYear());
				ctv.setState(ct.getState());
				ctv.setMaxStudent(ct.getMaxStudent());
				ctv.setStudentNumber(ct.getStudentNumber());
				ctv.setTeacherNumber(ct.getTeacherNumber());
				ctv.setLastBatchEndTime(lastBatchEndTime);
				//判断最后一个志愿的结束时间是否超过现在使时间
				Date date=new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String dateStr = sdf.format(date);
				Calendar calendar = shareService.toCalendar(dateStr);
				long currLong=calendar.getTimeInMillis();
				if(lastBatchEndTime!=null){
					long EndLong=lastBatchEndTime.getTimeInMillis();
					//当前时间超过最后一个志愿的结束时间
					if(currLong>EndLong){
						ctv.setIsOverCurrTime(1);//可以进行补选
					}
					else{
						ctv.setIsOverCurrTime(0);//不可以进行补选
					}
				}
				choseThemeVos.add(ctv);
			}
			mav.addObject("choseThemeVos", choseThemeVos);
		}
		mav.addObject("choseThemes", choseThemes);
		mav.addObject("choseTheme", choseTheme);
		mav.addObject("pageSize", pageSize);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		//获得当前时间
		Calendar currTime=Calendar.getInstance();
		//获得属于当前时间的志愿
		ChoseProfessorBatch choseProfessorBatch=choseProfessorService.belongCurrentTimeChoseProfessorBath(currTime);
		if(choseProfessorBatch!=null){
			ChoseTheme theme = choseProfessorBatch.getChoseTheme();
			//根据choseThemeId找到参选人数
			int participantNumber= choseProfessorService.findParticipantNumberBythemeId(theme.getTheYear());
			//根据choseThemeId找到未被选人数
			int noSelectedNumber= choseProfessorService.findNoSelectedNumberBythemeId(theme.getTheYear(),theme.getId());
			mav.addObject("participantNumber", participantNumber);
			mav.addObject("noSelectedNumber", noSelectedNumber);
			mav.addObject("choseProfessorBatch", choseProfessorBatch);
		}
		mav.setViewName("chose/chose_professor/ChoseThemeList.jsp");;
		return mav;
	}
	/**
	 * 导师互选结果列表--管理员
	 * @author 孙虎
	 * 2017.12.4
	 */
	@RequestMapping("/ChoseResultList")
	public ModelAndView ChoseResultList(HttpServletRequest request, @RequestParam int currpage, @RequestParam int themeId, @ModelAttribute ChoseTheme choseTheme, @ModelAttribute("selected_academy") String acno){
		ModelAndView mav = new ModelAndView();
		ChoseTheme theme = choseThemeDAO.findChoseThemeByPrimaryKey(themeId);
		mav.addObject("theme", theme);
		int pageSize = 10;
		int totalRecords = choseProfessorService.findChoseResultCount(choseTheme,themeId);
		List<ChoseProfessor> choseProfessors = choseProfessorService.findChoseResultList(choseTheme, currpage, pageSize,themeId);
		mav.addObject("choseProfessors", choseProfessors);
		mav.addObject("choseTheme", choseTheme);
		mav.addObject("pageSize", pageSize);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.setViewName("chose/chose_professor/ChoseResultList.jsp");
		return mav;
	}
	/**
	 * 导师互选大纲列表-导师
	 * @author 孙虎
	 * 2017.12.1
	 */
	/*@RequestMapping("/ChoseThemeListForProfessor")
	public ModelAndView ChoseThemeListForProfessor(HttpServletRequest request,@RequestParam int currpage,@ModelAttribute ChoseTheme choseTheme, @ModelAttribute("selected_academy") String acno){
		ModelAndView mav = new ModelAndView();
		int pageSize = 10;
		int totalRecords = choseProfessorService.findChoseThemesCount(choseTheme);
		List<ChoseTheme> choseThemes = choseProfessorService.findChoseThemes(choseTheme, currpage, pageSize);
		mav.addObject("choseThemes", choseThemes);
		mav.addObject("choseTheme", choseTheme);
		mav.addObject("pageSize", pageSize);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.setViewName("chose/chose_professor/ChoseThemeListForProfessor.jsp");
		return mav;
	}*/
	/**
	 * 导师互选学生列表-导师
	 * @author 孙虎
	 * 2017.12.1
	 */
	@RequestMapping("/ChoseStudentList")
	public ModelAndView ChoseStudentList(HttpServletRequest request, @RequestParam int currpage, @RequestParam int themeId, @ModelAttribute ChoseTheme choseTheme, @ModelAttribute("selected_academy") String acno){
		ModelAndView mav = new ModelAndView();
		ChoseTheme theme = choseThemeDAO.findChoseThemeByPrimaryKey(themeId);
		mav.addObject("theme", theme);
		int pageSize = 10;
		int totalRecords = choseProfessorService.findChoseStudentsCount(choseTheme,themeId);
		List<ChoseProfessorRecord> choseProfessorRecords = choseProfessorService.findChoseStudents(choseTheme, currpage, pageSize,themeId);
		mav.addObject("choseProfessorRecords", choseProfessorRecords);
		mav.addObject("choseTheme", choseTheme);
		mav.addObject("pageSize", pageSize);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.setViewName("chose/chose_professor/ChoseStudentList.jsp");
		return mav;
	}
	/**
	 * 导师互选大纲列表-学生
	 * @author 孙虎
	 * 2017.12.4
	 */
	@RequestMapping("/ChoseThemeListForStudent")
	public ModelAndView ChoseThemeListForStudent(HttpServletRequest request, @RequestParam int currpage, @ModelAttribute ChoseTheme choseTheme, @ModelAttribute("selected_academy") String acno){
		ModelAndView mav = new ModelAndView();
		int pageSize = 10;
		int totalRecords = choseProfessorService.findChoseThemesCount(choseTheme);
		List<ChoseTheme> choseThemes = choseProfessorService.findChoseThemes(choseTheme, currpage, pageSize);
		mav.addObject("choseThemes", choseThemes);
		mav.addObject("choseTheme", choseTheme);
		mav.addObject("pageSize", pageSize);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.setViewName("chose/chose_professor/ChoseThemeListForStudent.jsp");
		return mav;
	}
	/**
	 * 功能: 新建导师互选大纲第一步-管理员
	 * @author 赵晶
	 * @date 2017.12.08
	 */
	@RequestMapping("/newChoseThemeOneStep")
	public ModelAndView newChoseTheme(@ModelAttribute ChoseTheme choseTheme ){
		ModelAndView mav=new ModelAndView();
		//找到所有学生所属的届
		List<Object> attendanceTimeList= choseProfessorService.findAllAttendanceTime();
		mav.addObject("attendanceTimeList", attendanceTimeList);
		mav.setViewName("chose/chose_professor/NextAddChoseProfessor.jsp");
		return mav;
	}
	/**
	 * 功能: 从user表里查出所有的导师
	 * @author 赵晶
	 * @date 2017.12.08
	 */
	@RequestMapping("/findChoseProfessorByUser")
	public @ResponseBody
    String findChoseProfessorByUser(Integer currpage, Integer themeId){
		int pageSize=10;
		 List<User> users =choseProfessorService.findChoseProfessorByUser(currpage,pageSize);
		 //去除已经选择的导师
		 List<ChoseProfessor> list = choseProfessorService.findProfessorListByChemeId(themeId);
		 if(list!=null){
			 for(ChoseProfessor choseProfessor:list){
				 if(users.contains(choseProfessor.getUser())){
					 users.remove(choseProfessor.getUser());
				 }
			 }
		 }
		 //分页开始
		 int totalRecords=choseProfessorService.findCountChoseProfessorByUser();
		 Map<String,Integer> pageModel =shareService.getPage(currpage,pageSize,totalRecords);
		 String s="";
		 for(User u:users){
			 String academy="";
		    	if(u.getSchoolAcademy()!=null){
		    		academy=u.getSchoolAcademy().getAcademyName();
		    	}
		    	s+="<tr>"+
		    			"<td><input type='checkbox' name='CK_name' value='"+u.getUsername()+"'/></td>"+
		    	    	"<td>"+u.getCname()+"</td>"+
		    			"<td>"+u.getUsername()+"</td>"+
		    			"<td>"+academy+"</td>"+
		    			"</tr>";
		 	}
			 s+="<tr><td colspan='6'>"+
			    	    "<a href='javascript:void(0)' onclick='firstPage(1);'>"+"首页"+"</a>&nbsp;"+
			    	    "<a href='javascript:void(0)' onclick='previousPage("+currpage+");'>"+"上一页"+"</a>&nbsp;"+
			    	    "<a href='javascript:void(0)' onclick='nextPage("+currpage+","+pageModel.get("totalPage")+");'>"+"下一页"+"</a>&nbsp;"+
			    	    "<a href='javascript:void(0)' onclick='lastPage("+pageModel.get("totalPage")+");'>"+"末页"+"</a>&nbsp;"+
			    	    "当前第"+currpage+"页&nbsp; 共"+pageModel.get("totalPage")+"页  "+totalRecords+"条记录"+
			    	    		"</td></tr>";
			 return htmlEncode(s);
	}
	/**
	 * 功能: 保存备选导师
	 * @author zhaojing
	 * @date 2017.12.09
	 */
	@RequestMapping("/saveChoseProfessor")
	public ModelAndView saveChoseProfessor(String array[], Integer termId, HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("choseProfessorList");
		List<ChoseProfessor> choseProfessorList=null;
		if(obj!=null){
			choseProfessorList=(List<ChoseProfessor>)obj;
		}
		else{
			choseProfessorList=new ArrayList<ChoseProfessor>();
		}
		for(String username:array){
			User user = userDAO.findUserByPrimaryKey(username);
			ChoseProfessor choseProfessor =new ChoseProfessor();
			choseProfessor.setUser(user);
			choseProfessor.setType(1);
			ChoseTheme choseTheme=new ChoseTheme();
			choseTheme.setId(termId);
			choseProfessor.setChoseTheme(choseTheme);
			choseProfessor.setFinalNumber(0);
			choseProfessor.setIsAudit(0);//默认0代表未审核
			choseProfessorList.add(choseProfessor);
		}
		session.setAttribute("choseProfessorList", choseProfessorList);
		mav.setViewName("redirect:/nwuChose/findSelectChoseProfessorBySession?currpage=1");
		return mav;
	}
	/**
	 * 功能: 根据termId找到所有的备选导师
	 * @author zhaojing
	 * @date 2017.12.09
	 */
	@RequestMapping("findAllChoseProfessorByTermId")
	public ModelAndView findAllChoseProfessorByTermId(Integer currpage, Integer termId){
		ModelAndView mav=new ModelAndView();
		//找出备选导师名单
		Integer pageSize=10;
		List<ChoseProfessor> choseProfessorList = choseProfessorService.findChoseProfessorByThemeId(termId, currpage, pageSize,null);
		int totalRecords = choseProfessorService.findCountChoseProfessorByThemeId(termId,null);
		mav.addObject("choseProfessorList", choseProfessorList);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("user", new User());
		ChoseTheme choseTheme =new ChoseTheme();
		choseTheme.setId(termId);
		mav.addObject("choseTheme", choseTheme);
		mav.setViewName("chose/chose_professor/ChoseProfessorList.jsp");
		return mav;
	}
	/**
	 * 功能: 根据工号和姓名找到user
	 * @author 赵晶
	 * @date 2017.12.09
	 */
	@RequestMapping("/findUserByCnameAndUsername")
	public @ResponseBody
    String findUserByCnameAndUsername(HttpServletRequest request, String cname, String username, Integer currpage, Integer themeId){
		String[] usernameArray=null;
		//获得已经选择的导师列表
		 HttpSession session = request.getSession();
		 Object obj = session.getAttribute("choseProfessorList");
		 if(obj!=null){
			 List<ChoseProfessor> choseProfessorList= (List<ChoseProfessor>)obj;
			 usernameArray=new String[choseProfessorList.size()];
			 for(int i=0;i<choseProfessorList.size();i++){
				 usernameArray[i]=choseProfessorList.get(i).getUser().getUsername();
			 }
		 }
		int pageSize=10;
		User user=new User();
		user.setCname(cname);
		user.setUsername(username);
		//获得还未选的导师
		List<User> users =choseProfessorService.findUserByCnameAndUsername(user,currpage,pageSize,usernameArray);
		 //分页开始
		 int totalRecords=choseProfessorService.findCountUserByCnameAndUsername(user,usernameArray);
		 Map<String,Integer> pageModel =shareService.getPage(currpage,pageSize,totalRecords);
		 String s="";
		 for(User u:users){
			 String academy="";
		    	if(u.getSchoolAcademy()!=null){
		    		academy=u.getSchoolAcademy().getAcademyName();
		    	}
		    	s+="<tr>"+
		    			"<td><input type='checkbox' name='CK_name' value='"+u.getUsername()+"'/></td>"+
		    	    	"<td>"+u.getCname()+"</td>"+
		    			"<td>"+u.getUsername()+"</td>"+
		    			"<td>"+academy+"</td>"+
		    			"</tr>";
		 	}
			 s+="<tr><td colspan='6'>"+
			    	    "<a href='javascript:void(0)' onclick='firstPage(1);'>"+"首页"+"</a>&nbsp;"+
			    	    "<a href='javascript:void(0)' onclick='previousPage("+currpage+");'>"+"上一页"+"</a>&nbsp;"+
			    	    "<a href='javascript:void(0)' onclick='nextPage("+currpage+","+pageModel.get("totalPage")+");'>"+"下一页"+"</a>&nbsp;"+
			    	    "<a href='javascript:void(0)' onclick='lastPage("+pageModel.get("totalPage")+");'>"+"末页"+"</a>&nbsp;"+
			    	    "当前第"+currpage+"页&nbsp; 共"+pageModel.get("totalPage")+"页  "+totalRecords+"条记录"+
			    	    		"</td></tr>";
			 return htmlEncode(s);
	}
	/**
	 * 功能: 根据username删除存入session中的备选导师
	 * @author 赵晶
	 * @date 2017.1.15
	 */
	@RequestMapping("/deleteChoseProfessorByUsername")
	public String deleteChoseProfessorById(String username, HttpServletRequest request){
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("choseProfessorList");
		List<ChoseProfessor> choseProfessorList=(List<ChoseProfessor>)obj;
		//根据id找到user
		Iterator<ChoseProfessor> it = choseProfessorList.iterator();
		while(it.hasNext()){
			 if(username.equals(it.next().getUser().getUsername())){
				 it.remove();
			 }
		 }
		session.setAttribute("choseProfessorList", choseProfessorList);
		return "redirect:/nwuChose/findSelectChoseProfessorBySession?currpage=1";
	}
	/**
	 * 功能: 统计备选导师里的导师数量--管理员新建大纲第三步
	 * @author 赵晶
	 * @date 2017.12.10
	 */
	@RequestMapping("/listCountChoseProfessor")
	public ModelAndView listCountChoseProfessor(@ModelAttribute ChoseTheme choseTheme){
		ModelAndView mav=new ModelAndView();
		mav.setViewName("chose/chose_professor/NextAddChoseProfessor.jsp");
		return mav;
	}
	/**
	 * 功能: 保存备选大纲--管理员保存新建大纲时第三步
	 * @author 赵晶
	 * @date 2017.12.10
	 */
	@RequestMapping("/savaThemeChoseOneStep")
	public String savaThemeChoseOneStep(HttpServletRequest request, @ModelAttribute ChoseTheme choseTheme){
		HttpSession session = request.getSession();
		//清除session中保存的choseTheme和choseProfessorList
		session.removeAttribute("choseTheme");
		session.removeAttribute("choseProfessorList");
		ModelAndView mav=new ModelAndView();
		//获取互选大开始和结束时间
		String expectDeadlineStr = request.getParameter("expectDeadline");
		String expectStartlineStr = request.getParameter("expectStartline");
		//Object choseThemeObj = session.getAttribute("choseTheme");
		//从session中获得choseProfessorList
		Object choseProfessorListObj = session.getAttribute("choseProfessorList");
		//ChoseTheme choseTheme=(ChoseTheme)choseThemeObj;
		choseTheme.setExpectDeadline(shareService.toCalendar(expectDeadlineStr));
		choseTheme.setExpectStartline(shareService.toCalendar(expectStartlineStr));
		choseTheme.setType(1);//导师互选
		/*ChoseTheme theme=null;
		if(choseProfessorListObj==null){
			choseTheme.setTeacherNumber(0);
			theme = choseThemeDAO.store(choseTheme);
		}
		else{
			List<ChoseProfessor> choseProfessorList= (List<ChoseProfessor>)choseProfessorListObj;
			choseTheme.setTeacherNumber(choseProfessorList.size());
			theme = choseThemeDAO.store(choseTheme);
			for(ChoseProfessor choseProfessor:choseProfessorList){
				choseProfessor.setChoseTheme(theme);
				choseProfessorDAO.store(choseProfessor);
			}
		}
		//从session中获取choseProfessorBatchList
		Object choseProfessorBatchListObj = session.getAttribute("choseProfessorBatchList");
		List<ChoseProfessorBatch> choseProfessorBatchList= (List<ChoseProfessorBatch>)choseProfessorBatchListObj;
		for(ChoseProfessorBatch choseProfessorBatch:choseProfessorBatchList){
			choseProfessorBatch.setChoseTheme(theme);
			choseProfessorBatchDAO.store(choseProfessorBatch);
		}*/
		//将choseTheme保存到sesion中
		session.setAttribute("choseTheme", choseTheme);
		//return "redirect:ChoseThemeList?currpage=1";
		return "redirect:/nwuChose/findSelectChoseProfessorBySession?currpage=1";

	}

	/**
	 *功能: 根据termId、cname找到所有的备选导
	 *@author 赵晶
	 *@date 2017.12.11
	 */
	@RequestMapping("/choseProfessorList")
	public ModelAndView choseProfessorList(@ModelAttribute ChoseProfessor choseProfessor, @RequestParam Integer currpage){
		ModelAndView mav=new ModelAndView();
		ChoseTheme choseTheme = choseProfessor.getChoseTheme();
		Integer pageSize=10;
		//分页
		int totalRecords=choseProfessorService.countProfessorList(choseProfessor);
		 Map<String,Integer> pageModel =shareService.getPage(currpage,pageSize,totalRecords);
		//找出所有记录
		List<ChoseProfessor> choseProfessorList=choseProfessorService.choseProfessorList(choseProfessor,currpage,pageSize);
		mav.addObject("choseProfessorList", choseProfessorList);

		/*mav.addObject("choseProfessor",choseProfessorList.get(0));*/
		mav.addObject("user", new User());
		mav.addObject("pageModel", pageModel);
		/*mav.addObject("msg", msg);
		mav.addObject("flag", flag);*/
		mav.addObject("choseTheme", choseTheme);
		mav.setViewName("chose/chose_professor/ChoseProfessorList.jsp");
		return mav;
	}
	/**
	 * 填写期望的学生数量-导师
	 * @author 赵晶
	 * @date 2017.12.15
	 */
	@RequestMapping("/addExpectNumber")
	public ModelAndView addExpectNumber(@RequestParam Integer themeId){
		ModelAndView mav=new ModelAndView();
		//获取当前登陆人的信息
		ChoseProfessor choseProfessor=new ChoseProfessor();
		User user = shareService.getUserDetail();
		choseProfessor.setUser(user);
		ChoseTheme choseTheme =new ChoseTheme();
		choseTheme.setId(themeId);
		choseProfessor.setChoseTheme(choseTheme);
		ChoseProfessor professor = choseProfessorService.findChoseProfessor(choseProfessor);
		mav.addObject("professor", professor);
		Integer maxStudent = professor.getChoseTheme().getMaxStudent();
		mav.addObject("maxStudent", maxStudent);
		mav.setViewName("chose/chose_professor/AddExpectNumber.jsp?themeId="+themeId);
		return mav;

	}
	/**
	 * 功能:导师填写期望学生数
	 * @author zhaojing
	 * @date 2017.12.15
	 */
	@RequestMapping("/saveExpectNumber")
	public ModelAndView saveExpectNumber(@ModelAttribute ChoseProfessor professor, Integer expectNumber){
		ModelAndView mav=new ModelAndView();
		//获取当前登陆人的信息
		User user = shareService.getUserDetail();
		ChoseProfessor choseProfessor = choseProfessorService.findChoseProfessor(professor);
		choseProfessor.setExpectNumber(professor.getExpectNumber());
		choseProfessorDAO.store(choseProfessor);
		mav.setViewName("redirect:ChoseThemeListForProfessor?currpage=1");
		return mav;

	}
	/****************************************************************************
	 * 功能：拼接批次
	 * 作者：赵晶
	 * 时间：2017-12-25
	 ****************************************************************************/
	@RequestMapping("/addBatch")
	public  @ResponseBody
    String addBatch(Integer batchNumber){
		//拼接每个批次
		String str="";
	   for(int i=0;i<batchNumber;i++){
		   str+="<fieldset><label>第"+(i+1)+"志愿开始-结束时间: </label>";
		   str+="<input id='startTime"+i+"' name='startTime' required='true' class='Wdate datepicker' autocomplete='off' onfocus='WdatePicker({skin:\"whyGreen\",dateFmt:\"yyyy-MM-dd HH:mm:ss\"})'  type='text'/>";
		   str+="<input id='endTime"+i+"' name='endTime' required='true' class='Wdate datepicker' autocomplete='off' onfocus='WdatePicker({skin:\"whyGreen\",dateFmt:\"yyyy-MM-dd HH:mm:ss\"})'   type='text'/></fieldset>";
		}
		return htmlEncode(str);
	}
	/****************************************************************************
	 * 功能：保存大纲-第一步
	 * 作者：赵晶
	 * 时间：2017-12-25
	 ****************************************************************************/
	@RequestMapping("/saveChoseThemeFirst")
	public String saveChoseThemeFirst(@ModelAttribute ChoseTheme choseTheme, HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		//获得互选大纲的开始和结束时间
		String startStr = request.getParameter("start");
		String endStr = request.getParameter("end");
		//获得批次的开始和结束时间数组
		String[] startTime = request.getParameterValues("startTime");
		String[] endTime = request.getParameterValues("endTime");
		List<ChoseProfessorBatch> choseProfessorBatchs=new ArrayList<ChoseProfessorBatch>();
		//遍历，获取每个志愿的开始和结束时间
		for(int i=0; i<startTime.length;i++){
			ChoseProfessorBatch choseProfessorBatch=new ChoseProfessorBatch();
			choseProfessorBatch.setStartTime(shareService.toCalendarDetil(startTime[i]));
			choseProfessorBatch.setEndTime(shareService.toCalendarDetil(endTime[i]));
			choseProfessorBatch.setBatchNumber(i+1);
			choseProfessorBatch.setChoseTheme(choseTheme);
			//将志愿存入list集合中
			choseProfessorBatchs.add(choseProfessorBatch);
		}
		//保存互选开始和结束时间
		choseTheme.setBatchNumber(choseTheme.getBatchNumber());
		choseTheme.setEndTime(shareService.toCalendar(endStr));
		choseTheme.setState(0);
		//获取session，将choseTheme存入session中
		HttpSession session = request.getSession();
		session.setAttribute("choseTheme", choseTheme);
		session.setAttribute("choseProfessorBatchList", choseProfessorBatchs);
		//跳转到备选导师列表页面
		return "redirect:nwuChose/findSelectChoseProfessorBySession?currpage=1";
	}
	/**
	 * 功能: 列出session中已经选择的备选导师
	 * @author 赵晶
	 * @date 2018.1.15
	 */
	@RequestMapping("/findSelectChoseProfessorBySession")
	public ModelAndView findSelectChoseProfessorBySession(@RequestParam("currpage") Integer currpage, HttpServletRequest request, @ModelAttribute User user){
		ModelAndView mav=new ModelAndView();
		//从session找到备选导师
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("choseProfessorList");
		List<ChoseProfessor> choseProfessorList=null;
		if(obj!=null){
			//所有的学生
			List<ChoseProfessor> choseProfessorListAll=(List<ChoseProfessor>)obj;
			//根据user里的参数筛选choseProfessorListAll中的
			Iterator<ChoseProfessor> it = choseProfessorListAll.iterator();
			if(user!=null){
				if(!"".equals(user.getCname())&&user.getCname()!=null){
					while(it.hasNext()){
						int index=it.next().getUser().getCname().indexOf(user.getCname());
						if(index<=0){
							it.remove();
						}
					}
				}
			}
			//分页
			 int pageSize=10;
			 int totalRecords=choseProfessorListAll.size();
			 Map<String,Integer> pageModel =shareService.getPage(currpage,pageSize,totalRecords);
			 //获得总共的页数
			 Integer totalPage = pageModel.get("totalPage");
			 if(currpage==totalPage){
				//每页显示的学生列表
				 choseProfessorList=choseProfessorListAll.subList((currpage-1)*pageSize,totalRecords);
			 }
			 else{
				//每页显示的学生列表
				 if(choseProfessorListAll.size()!=0){
					 choseProfessorList=choseProfessorListAll.subList((currpage-1)*pageSize,pageSize);
				 }
				 choseProfessorList=choseProfessorListAll;
			 }
			 mav.addObject("pageModel", pageModel);
		}
		mav.addObject("choseProfessorList", choseProfessorList);
		//mav.addObject("themeId", themeId);
		mav.setViewName("chose/chose_professor/ChoseProfessorList.jsp");
		return mav;

	}
	/****************************************************************************
	 * 功能：找到本次大纲下非本届的所有学生(没有导师的)
	 * 作者：赵晶
	 * 时间：2017-12-28
	 ****************************************************************************/
	@RequestMapping("/findSelectStudentByUser")
	public ModelAndView findSelectStudentByUser(int currpage, HttpServletRequest request, @ModelAttribute User user, Integer themeId, Integer flag){
		ModelAndView mav=new ModelAndView();
		boolean flagObj = true;
		List<User> list = new ArrayList<>();
		//找到属于本届的学生列表
		if(user.getAttendanceTime() != null && !"".equals(user.getAttendanceTime()))
			list = choseProfessorService.findStudentListByAttendanceTime(Integer.parseInt(user.getAttendanceTime()));
		//找到选中非本届的所有学生
//		HttpSession session = request.getSession();
//		Object obj = session.getAttribute("studentList");
//		//每页显示的记录
//		List<User> studentList=new ArrayList<User>();
//		//总的记录
//		List<User> studentListAll=new ArrayList<User>();
//		//第一次进来，需要查以前realAttendanceTime为本届的学生
//		if(flag != null && flag==1){
//			//找到上次没有导师的其他批次的学生
//			List<User> otherlist =choseProfessorService.findOhterStudentListByRealAttendanceTime(Integer.parseInt(user.getAttendanceTime()));
//			if(otherlist!=null&&otherlist.size()!=0){
//				if(obj!=null){
//					otherlist.addAll((List<User>)obj);
//					flagObj = false;
//				}
//				session.setAttribute("studentList", otherlist);
//				studentListAll.addAll(otherlist);
//			}
//		}
//		if(obj!=null && flagObj){
//			//所有的学生
//			studentListAll.addAll((List<User>)obj);
//
//		}
		List<User> otherlist = choseProfessorService.findOhterStudentListByRealAttendanceTime(Integer.parseInt(user.getAttendanceTime()));
		//每页显示的记录
		List<User> studentList=new ArrayList<User>();
		//总的记录
		List<User> studentListAll=new ArrayList<User>();
		studentListAll.addAll(list);
		studentListAll.addAll(otherlist);
        if(user.getCname()!=null&& !"".equals(user.getCname())) {
            Iterator<User> it = studentListAll.iterator();
            while (it.hasNext()) {
                if (!it.next().getCname().equals(user.getCname()))
                    it.remove();
            }
        }
		//分页
		 int pageSize=10;
		 int totalRecords=studentListAll.size();
		 Map<String,Integer> pageModel =shareService.getPage(currpage,pageSize,totalRecords);
		 //获得总共的页数
		 Integer totalPage = pageModel.get("totalPage");
		 if(currpage==totalPage){
			//每页显示的学生列表
			 studentList=studentListAll.subList((currpage-1)*pageSize,totalRecords);
		 }
		 else{
			//每页显示的学生列表
			 if(studentListAll!=null&&studentListAll.size()!=0){
			 	//杨新蔚修改（此处sublist两个参数均指下标，左闭右开）
				 studentList=studentListAll.subList((currpage-1)*pageSize,currpage*pageSize);
			 }
		 }
		 mav.addObject("pageSize",pageSize);
		 mav.addObject("pageModel", pageModel);
		 mav.addObject("studentList", studentList);
		 mav.addObject("attendanceTime", user.getAttendanceTime());
		 mav.addObject("themeId", themeId);
		 mav.addObject("flag", ++flag);
		mav.setViewName("chose/chose_professor/OtherBatchStudentList.jsp");
		return mav;

	}
	/****************************************************************************
	 * 功能：查询非本届的所有学生
	 * 作者：赵晶
	 * 时间：2017-12-25
	 ****************************************************************************/
	@RequestMapping("/queryOtherBatchStudent")
	public ModelAndView addOtherBatchStudent(@ModelAttribute User user, Integer currpage){
		 ModelAndView mav=new ModelAndView();
		 //找到非本届的所有学生
		 int pageSize=10;
		 List<User> studentList =choseProfessorService.findStudentByUser(currpage,pageSize,user);
		 //分页开始
		 int totalRecords=choseProfessorService.findCountStudentByUser(user);
		 Map<String,Integer> pageModel =shareService.getPage(currpage,pageSize,totalRecords);
		 mav.addObject("studentList", studentList);
		 mav.addObject("pageModel", pageModel);
		 //保存realAttendanceTime
		 if(user!=null){
			 mav.addObject("realAttendanceTime", user.getAttendanceTime());
		 }
		 mav.setViewName("chose/chose_professor/addOtherBatchStudent.jsp");
		 return mav;
	}
	/****************************************************************************
	 * 功能：保存非本届的所有学生(实际修改每个user的real_attendance_time)
	 * 作者：赵晶
	 * 时间：2017-12-28
	 ****************************************************************************/
	@RequestMapping("/saveOtherBatchStudent")
	public ModelAndView saveOtherBatchStudent(String array[], String attendanceTime, Integer themeId, HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
//		HttpSession session = request.getSession();
//		Object obj = session.getAttribute("studentList");
//		if(obj!=null){
//			List<User> studentList=(List<User>)obj;
//			for(String username:array){
//			  //找到每个user对象
//			  User user = userDAO.findUserByPrimaryKey(username);
//			  user.getChoseUser().setRealAttendanceTime(attendanceTime);
//			  studentList.add(user);
//			}
//			session.setAttribute("studentList", studentList);
//		}
//		else{
//			List<User> studentList=new ArrayList<User>();
		for (String username : array) {
			//找到每个user对象
			User user = userDAO.findUserByPrimaryKey(username);
			ChoseUser cu;
			if(user.getChoseUser() == null)
			{
				cu = new ChoseUser();
				cu.setUser(user);
			}else{
				cu = user.getChoseUser();
			}
			cu.setRealAttendanceTime(attendanceTime);
			choseUserDAO.store(cu);
//				  studentList.add(user);
		}
//			session.setAttribute("studentList", studentList);
//		}
		mav.setViewName("redirect:/nwuChose/findSelectStudentByUser?currpage=1&flag=2&attendanceTime="+attendanceTime+"&themeId="+themeId);
		return mav;

	}
	/****************************************************************************
	 * 功能：查询非本届的所有学生(排除session中已经选择的非本届学生)
	 * 作者：赵晶
	 * 时间：2017-12-28
	 ****************************************************************************/
	@RequestMapping("/findOtherBatchStudentByuser")
	public @ResponseBody
    String findOtherBatchStudentByuser(Integer currpage, User user, String attendanceTime, HttpServletRequest request){
		int pageSize=10;
		String[] usernameArray=null;
		//获得已经选择的学生列表
		List<User> list = choseProfessorService.findStudentListByAttendanceTime(Integer.parseInt(user.getAttendanceTime()));
		List<User> otherlist =choseProfessorService.findOhterStudentListByRealAttendanceTime(Integer.parseInt(user.getAttendanceTime()));
		List<User> studentListAll = new ArrayList<User>(list);
		studentListAll.addAll(otherlist);
//		 HttpSession session = request.getSession();
//		 Object obj = session.getAttribute("studentList");
		if(studentListAll !=null){
			usernameArray=new String[studentListAll.size()];
			 for(int i = 0; i< studentListAll.size(); i++){
				 usernameArray[i]= studentListAll.get(i).getUsername();
			 }
		 }
		 //去掉已经选的学生
		 List<User> users =choseProfessorService.findOtherBatchStudentByuser(currpage,pageSize,user, usernameArray);
		 //分页开始
		 int totalRecords=choseProfessorService.findCountOtherBatchStudentByuser(user,usernameArray);
		 Map<String,Integer> pageModel =shareService.getPage(currpage,pageSize,totalRecords);
		 String s="";
		 for(User u:users){
			 String academy="";
		    	if(u.getSchoolAcademy()!=null){
		    		academy=u.getSchoolAcademy().getAcademyName();
		    	}
		    	s+="<tr>"+
		    			"<td><input type='checkbox' name='CK_name' value='"+u.getUsername()+"'/></td>"+
		    	    	"<td>"+u.getCname()+"</td>"+
		    			"<td>"+u.getUsername()+"</td>"+
		    			"<td>"+academy+"</td>"+
		    			"</tr>";
		 	}
			 s+="<tr><td colspan='6'>"+
			    	    "<a href='javascript:void(0)' onclick='firstPage(1);'>"+"首页"+"</a>&nbsp;"+
			    	    "<a href='javascript:void(0)' onclick='previousPage("+currpage+");'>"+"上一页"+"</a>&nbsp;"+
			    	    "<a href='javascript:void(0)' onclick='nextPage("+currpage+","+pageModel.get("totalPage")+");'>"+"下一页"+"</a>&nbsp;"+
			    	    "<a href='javascript:void(0)' onclick='lastPage("+pageModel.get("totalPage")+");'>"+"末页"+"</a>&nbsp;"+
			    	    "当前第"+currpage+"页&nbsp; 共"+pageModel.get("totalPage")+"页  "+totalRecords+"条记录"+
			    	    		"</td></tr>";
			 return htmlEncode(s);
	}

	/**
	 * 功能: 根据学生的username删除已经选的学生
	 * @author 赵晶
	 * @date 2017.12.10
	 */
	@RequestMapping("/deleteOtherBatchStudent")
	public String deleteOtherBatchStudent(String username, String attendanceTime, Integer themeId, HttpServletRequest request){
//		HttpSession session = request.getSession();
//		Object obj = session.getAttribute("studentList");
//		List<User> studentList=(List<User>)obj;
		//根据id找到user
		 User user = userDAO.findUserByPrimaryKey(username);
//		 if(studentList.contains(user)){
//			 studentList.remove(user);
//		 }
//		 session.setAttribute("studentList", studentList);
		//设置user 的realAttendanceTime为null
		user.getChoseUser().setRealAttendanceTime(null);
		userDAO.store(user);
		return "redirect:findSelectStudentByUser?currpage=1&flag=0&attendanceTime="+attendanceTime+"&themeId="+themeId;
	}
	/****************************************************************************
	 * 功能：找到所属的备选大纲列表-学生填志愿
	 * 作者：赵晶
	 * 时间：2017-12-29
	 ****************************************************************************/
	@RequestMapping("/BelongChoseThemeList")
	public ModelAndView BelongChoseThemeList(@RequestParam int currpage, @ModelAttribute ChoseTheme choseTheme, @ModelAttribute("selected_academy") String acno, HttpServletRequest request){
		//清除session中的志愿信息
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("batchInfo");
		if(obj!=null){
			session.removeAttribute("batchInfo");
		}
		ModelAndView mav = new ModelAndView();
		//查询当前登陆人的信息-学生
		User user = shareService.getUserDetail();
		//分页开始
		int pageSize = 10;
		int totalRecords = choseProfessorService.findBelongChoseThemeListCount(choseTheme,user);
		//根据条件找到所属的备选大纲-学生
		List<ChoseTheme> choseThemeList = choseProfessorService.BelongChoseThemeList(choseTheme,user, currpage, pageSize);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("choseThemeList", choseThemeList);
		//判断该学生是否已经填过志愿
		Integer isChoseProfessor = user.getChoseUser()!=null?user.getChoseUser().getIsChoseProfessor():null;
		mav.addObject("isChoseProfessor", isChoseProfessor);
		//分页结束
		mav.setViewName("chose/chose_professor/BelongChoseThemeList.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：查看志愿的状态-学生
	 * 作者：赵晶
	 * 时间：2018-1-28
	 ***************************************************************************/
	@RequestMapping("/findBatchState")
	public ModelAndView findBatchState (@ModelAttribute("selected_academy") String acno, Integer themeId){
		ModelAndView mav=new ModelAndView();
		ChoseTheme choseTheme = choseThemeDAO.findChoseThemeByPrimaryKey(themeId);
		List<Integer[]> list=new ArrayList<Integer[]>();
		Set<ChoseProfessorBatch> choseProfessorBatchs = choseTheme.getChoseProfessorBatchs();
		//获得当前时间
		long currentTimeMillis = System.currentTimeMillis();
		for(ChoseProfessorBatch choseProfessorBatch:choseProfessorBatchs){
			Calendar startTime = choseProfessorBatch.getStartTime();
			Calendar endTime = choseProfessorBatch.getEndTime();
			Integer[] element=new Integer[2];
			if(currentTimeMillis<startTime.getTimeInMillis()){
				element[0]=choseProfessorBatch.getBatchNumber();
				element[1]=0;//未开始
				list.add(element);
			}
			if(currentTimeMillis>=startTime.getTimeInMillis()&&currentTimeMillis<=endTime.getTimeInMillis()){
				element[0]=choseProfessorBatch.getBatchNumber();
				element[1]=1;//正在进行
				list.add(element);
			}
			if(currentTimeMillis>endTime.getTimeInMillis()){
				element[0]=choseProfessorBatch.getBatchNumber();
				element[1]=2;//已结束
				list.add(element);
			}
		}
		mav.addObject("list", list);
		mav.setViewName("chose/chose_professor/BatchStateForStudent.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：到学生填写志愿的页面-学生
	 * 作者：赵晶
	 * 时间：2017-12-29
	 * 参数：i 代表是第几志愿
	 ***************************************************************************/
	@RequestMapping("/toAddBatch")
	public ModelAndView toAddBatch(HttpServletRequest request, @RequestParam int i, Integer themeId, @ModelAttribute("selected_academy") String acno, @ModelAttribute ChoseProfessor choseProfessor, Integer currpage){
		//找到需要填写的志愿批次
		//ChoseProfessorBatch choseProfessorBatch = choseProfessorBatchDAO.findChoseProfessorBatchByPrimaryKey(choseTheme.getId());

		ModelAndView mav = new ModelAndView();
		//查询当前登陆人的信息-学生
		User userInfo = shareService.getUserDetail();
		/*//找到大纲中的备选导师列表
		List<ChoseProfessor> choseProfessorList=choseProfessorService.findProfessorListByChemeId(choseTheme.getId());*/
		//List<ChoseProfessor> choseProfessorList=choseProfessorService.findProfessorListByChemeId(choseTheme.getId());
		//查询所有的学部列表
		List<String> teachingDepartments=choseProfessorService.findAllTeachingDepartment();
		mav.addObject("teachingDepartments", teachingDepartments);
		//根据大纲id找到所有的志愿
		List<ChoseProfessorBatch> batchList = choseProfessorService.belongChoseProfessorBatchList(themeId);
		List<Integer> professorIds=new ArrayList<Integer>();
		int pageSize=10;
		HttpSession session = request.getSession();
		/*if("1".equals(i)){
			session.removeAttribute("batchInfo");
		}*/
		Object obj = session.getAttribute("batchInfo");
		if(obj!=null){
			List<String[]> list=(List<String[]>)obj;
			for(String[] s:list){
				professorIds.add(Integer.parseInt(s[3]));
			}
		}
		mav.addObject("professorIntroductionUrl", pConfig.professorIntroductionUrl);
		if(batchList!=null&batchList.size()!=0){
			//当先的志愿属于某大纲下的某志愿
			if(i<=batchList.size()){
				//找到未被选的导师列表-学生填志愿
				List<ChoseProfessor> choseProfessorList=choseProfessorService.findNoSelectedProfessorListQuery(themeId,currpage,choseProfessor,professorIds,pageSize);
				int totalRecords= choseProfessorService.findNoSelectedProfessorListQueryCount(themeId,choseProfessor,professorIds);
				mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
				//设置批次的id
				mav.addObject("batchId",batchList.get(i-1).getId());
				mav.addObject("choseProfessorList", choseProfessorList);
				mav.addObject("themeId", themeId);
				//第几志愿
				mav.addObject("i",i);
				mav.setViewName("chose/chose_professor/AddBatch.jsp");
			}
			else{
				//到批次的列表

				mav.setViewName("chose/chose_professor/BatchList.jsp");
			}
		}
		return mav;
	}
	/****************************************************************************
	 * 功能：添加志愿-学生
	 * 作者：赵晶
	 * 时间：2017-1-2
	 ****************************************************************************/
	/*@RequestMapping("/saveBatch")
	public ModelAndView saveBatch(HttpServletRequest request,@ModelAttribute("selected_academy") String acno){
		Integer i=Integer.parseInt(request.getParameter("i"));
		//批次的id
		Integer batchId=Integer.parseInt(request.getParameter("batchId"));
		//导师的id
		Integer choseProfessorId=Integer.parseInt(request.getParameter("choseProfessorId"));
		//根据导师id获取导师
		ChoseProfessor professor=choseProfessorDAO.findChoseProfessorByPrimaryKey(choseProfessorId);
		Integer themeId=Integer.parseInt(request.getParameter("themeId"));
		ModelAndView mav = new ModelAndView();
		ChoseProfessor choseProfessor=new ChoseProfessor();
		choseProfessor.setId(choseProfessorId);
		ChoseProfessorRecord choseProfessorRecord=new ChoseProfessorRecord();
		//设置学生填志愿的导师信息
		choseProfessorRecord.setChoseProfessor(choseProfessor);
		User user = shareService.getUserDetail();
		//设置填写志愿的学生信息
		choseProfessorRecord.setUser(user);
		ChoseProfessorBatch c=new ChoseProfessorBatch();
		c.setId(batchId);
		choseProfessorRecord.setChoseProfessorBatch(c);
		choseProfessorRecord.setCurrAduit(0);//未审核
		//保存导师tusername
		choseProfessorRecord.setTUsername(professor.getUser().getUsername());
		choseProfessorRecordDAO.store(choseProfessorRecord);
		mav.setViewName("redirect:toAddBatch?currpage=1&i="+i+"&themeId="+themeId);
		return mav;
	}*/
	@RequestMapping("/saveBatch")
	public String saveBatch(HttpServletRequest request, @ModelAttribute("selected_academy") String acno){

		//获得学生填写的志愿信息
		HttpSession session=request.getSession();
		Object obj = session.getAttribute("batchInfo");
		if(obj!=null){
			List<String[]> list=(List<String[]>)obj;
			User user = shareService.getUserDetail();
			for(String[] b:list){
				//大纲的id
				Integer themeId=Integer.parseInt(b[0]);
				//批次的id
				Integer batchId=Integer.valueOf(b[1]);
				//第几志愿
				Integer i=Integer.parseInt(b[2]);
				//导师的id
				Integer choseProfessorId=Integer.parseInt(b[3]);
				//根据导师id获取导师
				ChoseProfessorRecord choseProfessorRecord=new ChoseProfessorRecord();
				ChoseProfessor professor=choseProfessorDAO.findChoseProfessorByPrimaryKey(choseProfessorId);
				choseProfessorRecord.setTUsername(professor.getUser().getUsername());
				//设置学生填志愿的导师信息
				choseProfessorRecord.setChoseProfessor(professor);
				//设置填写志愿的学生信息
				choseProfessorRecord.setUser(user);
				ChoseProfessorBatch c=new ChoseProfessorBatch();
				c.setId(batchId);
				choseProfessorRecord.setChoseProfessorBatch(c);
				choseProfessorRecord.setCurrAduit(0);//未审核
				//设置是否提交论文志愿的标记
//				ChoseUser cu;
//				if(user.getChoseUser()==null){
//					cu = new ChoseUser();
//					cu.setUser(user);
//				}else{
//					cu = user.getChoseUser();
//				}
				choseProfessorRecordDAO.store(choseProfessorRecord);
				choseProfessorRecordDAO.flush();
			}
			ChoseUser cu = user.getChoseUser();
			if(cu==null){
				cu = new ChoseUser();
			}
			cu.setUser(user);
			cu.setIsChoseProfessor(1);//设置是否提交志愿
			choseUserDAO.store(cu);
			choseUserDAO.flush();
		}
		return "redirect:/nwuChose/BelongChoseThemeList?currpage=1";
	}
	/****************************************************************************
	 * 功能：保存学生填写的志愿到session中-学生
	 * 作者：赵晶
	 * 时间：2017-1-11
	 ****************************************************************************/
	@RequestMapping("/saveBatchInfoToSession")
	public String saveBatchInfoToSession(HttpServletRequest request, String themeId, String batchId, String i, String choseProfessorId){
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("batchInfo");
		if(obj!=null){
			List<String[]> batchInfo=(List<String[]>)obj;
			String[] b=new String[6];
			b[0]=themeId;
			b[1]=batchId;
			b[2]=i;
			b[3]=choseProfessorId;
			ChoseProfessor professor=choseProfessorDAO.findChoseProfessorByPrimaryKey(Integer.parseInt(choseProfessorId));
			b[4]=professor.getUser().getUsername();
			b[5]=professor.getUser().getCname();
			batchInfo.add(b);
			session.setAttribute("batchInfo", batchInfo);
		}
		else{
			List<String[]> batchInfo=new ArrayList<String[]>();
			String[] b=new String[6];
			b[0]=themeId;
			b[1]=batchId;
			b[2]=i;
			b[3]=choseProfessorId;
			ChoseProfessor professor=choseProfessorDAO.findChoseProfessorByPrimaryKey(Integer.parseInt(choseProfessorId));
			b[4]=professor.getUser().getUsername();
			b[5]=professor.getUser().getCname();
			batchInfo.add(b);
			session.setAttribute("batchInfo", batchInfo);
		}

		return "redirect:toAddBatch?i="+(Integer.parseInt(i)+1)+"&themeId="+themeId+"&batchId="+batchId+"&currpage=1";
	}
	/****************************************************************************
	 * 功能：列表志愿-学生
	 * 作者：赵晶
	 * 时间：2017-01-02
	 ****************************************************************************/
	@RequestMapping("/batchList")
	public ModelAndView batchList(@ModelAttribute("selected_academy") String acno, Integer themeId){
		//Integer themeId=Integer.parseInt(request.getParameter("themeId"));
		User user = shareService.getUserDetail();
		ModelAndView mav = new ModelAndView();
		//找到所有备选的导师
		List<ChoseProfessorBatch> batchList = choseProfessorService.belongChoseProfessorBatchList(themeId);
		List<ChoseProfessorRecord> recordList=new ArrayList<ChoseProfessorRecord>();
		if(batchList!=null){
			for(ChoseProfessorBatch batch:batchList){
				ChoseProfessorRecord record=choseProfessorService.getChoseProfessorRecordByIdAndUsername(batch.getId(),user.getUsername());
				recordList.add(record);
			}
		}
		String[] usernames=new String[recordList.size()];
		mav.addObject("recordList", recordList);
		String usernamesStr="";

			for(int i=0;i<recordList.size();i++){
				if(recordList.get(i)!=null){
				usernames[i]=recordList.get(i).getChoseProfessor().getUser().getUsername();
				usernamesStr+="username="+usernames[i];
				if(i!=(recordList.size()-1)){
					usernamesStr+="&";
				}
				}

		}


		mav.addObject("usernames", usernamesStr);
		mav.setViewName("chose/chose_professor/BatchList.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：提交志愿-学生{修改学生的is_chose_professor}
	 * 作者：赵晶
	 * 时间：2017-01-02
	 ****************************************************************************/
	@RequestMapping("/updateUser")
	public String updateUser(@ModelAttribute("selected_academy") String acno, String[] username){
		/*ModelAndView mav=new ModelAndView();
		if(username!=null){
			for(String usern:username) {
				User user = userDAO.findUserByPrimaryKey(usern);
				user.setIsChoseProfessor(1);//设置提交志愿
				userDAO.store(user);
			}
		}*/
		//获得登陆的用户信息
		User user = shareService.getUserDetail();
		user.getChoseUser().setIsChoseProfessor(1);//设置是否提交志愿
		return "redirect:/nwuChose/BelongChoseThemeList?currpage=1";
	}
	/****************************************************************************
	 * 功能：导师审核的列表-导师
	 * 作者：赵晶
	 * 时间：2017-01-03
	 ****************************************************************************/
	@RequestMapping("/ChoseProfessorBatchListForProfessor")
	public ModelAndView ChoseProfessorBatchListForProfessor(@ModelAttribute("selected_academy") String acno, Integer currpage, Integer themeId){
		int pageSize=10;
		ModelAndView mav=new ModelAndView();
		//根据当前登陆人找到所有的大纲
		User user = shareService.getUserDetail();
		//分页
		int totalRecords = choseProfessorService.choseProfessorBatchListForProfessorCount(themeId,user.getUsername());
		List<Object[]> lists=choseProfessorService.choseProfessorBatchListForProfessor(themeId,user.getUsername(),currpage,pageSize);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("lists", lists);
		mav.addObject("themeId", themeId);
		mav.setViewName("chose/chose_professor/ChoseProfessorBatchListForProfessor.jsp");
		return mav;
	}

	/****************************************************************************
	 * 功能：查询每个的批次待审核的学生列表-导师
	 * 作者：赵晶
	 * 时间：2017-01-03
	 ****************************************************************************/
	@RequestMapping("/aduitStudentListForProfessor")
	public ModelAndView aduitStudentListForProfessor(@ModelAttribute("selected_academy") String acno, Integer batchId, Integer themeId, Integer flag){
		ModelAndView mav=new ModelAndView();
		User user = shareService.getUserDetail();
		List<ChoseProfessorRecord> records=choseProfessorService.aduitStudentList(user.getUsername(),batchId);

		//获取所属大纲的学生数-导师
		Object[] objects = choseProfessorService.choseThemeStudentCount(user.getUsername(),themeId);
		if(objects!=null){
			int count=Integer.parseInt(String.valueOf(objects[0]))-Integer.parseInt(String.valueOf(objects[1]));
			mav.addObject("count", count);
		}
		mav.addObject("records", records);
		mav.addObject("flag", flag);
		mav.addObject("themeId", themeId);
		mav.setViewName("chose/chose_professor/AduitStudentListForProfessor.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：保存导师审核结果-导师
	 * 作者：赵晶
	 * 时间：2017-01-04
	 ****************************************************************************/
	@RequestMapping("/saveAduitResult")
	public ModelAndView saveAduitResult(@ModelAttribute("selected_academy") String acno, Integer[] recordIds, Integer themeId){
		ModelAndView mav=new ModelAndView();
		User user = shareService.getUserDetail();
		if(recordIds!=null){
			ChoseProfessorRecord crp = choseProfessorRecordDAO.findChoseProfessorRecordByPrimaryKey(recordIds[0]);
			Integer finalNumber = crp.getChoseProfessor().getFinalNumber();
			//设置导师最终的学生数量
			crp.getChoseProfessor().setFinalNumber(recordIds.length+finalNumber);
			choseProfessorRecordDAO.store(crp);
		}
		for(Integer recordId:recordIds){
			ChoseProfessorRecord choseProfessorRecord = choseProfessorRecordDAO.findChoseProfessorRecordByPrimaryKey(recordId);
			if(choseProfessorRecord!=null){
				//设置审核结果
				choseProfessorRecord.setAduitResult(1);
				//设置审核状态--已结束
				choseProfessorRecord.setCurrAduit(2);
				//设置学生的导师信息
				choseProfessorRecord.getUser().getChoseUser().setProfessor(user.getUsername());
				//获得导师已经通过的学生数量
				//获取志愿
				int batchNumber=choseProfessorRecord.getChoseProfessorBatch().getBatchNumber();
				//设置标志
				choseProfessorRecord.getChoseProfessor().setIsAudit(batchNumber);
			}
			choseProfessorRecordDAO.store(choseProfessorRecord);
		}
		mav.setViewName("redirect:/nwuChose/ChoseProfessorBatchListForProfessor?currpage=1&themeId="+themeId);
		return mav;
	}
	/****************************************************************************
	 * 功能：查询每个的批审核通过的学生列表-导师
	 * 作者：赵晶
	 * 时间：2017-01-04
	 ****************************************************************************/
	@RequestMapping("/studentList")
	public ModelAndView studentList(@ModelAttribute("selected_academy") String acno, Integer batchId, Integer flag, Integer themeId){
		ModelAndView mav=new ModelAndView();
		User user = shareService.getUserDetail();
		List<ChoseProfessorRecord> records=choseProfessorService.studentList(user.getUsername(),batchId);
		mav.addObject("records", records);
		mav.addObject("flag", flag);
		mav.addObject("themeId", themeId);
		mav.setViewName("chose/chose_professor/AduitStudentListForProfessor.jsp");
		return mav;
	}

	/****************************************************************************
	 * 功能：查询没有导师的学生列表-导师
	 * 作者：赵晶
	 * 时间：2017-01-05
	 ****************************************************************************/
	@RequestMapping("/noProfessorStudentList")
	public ModelAndView noProfessorStudentList(@ModelAttribute("selected_academy") String acno, Integer attendanceTime, Integer currpage, Integer choseThemeId){
		ModelAndView mav=new ModelAndView();
		//分页
		int pageSize=10;
		int totalRecords = choseProfessorService.noProfessorStudentListCount(attendanceTime,currpage,pageSize);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		List<User> users=choseProfessorService.noProfessorStudentList(attendanceTime,currpage,pageSize);
		mav.addObject("users", users);
		mav.addObject("choseThemeId", choseThemeId);
		mav.addObject("attendanceTime", attendanceTime);
		mav.setViewName("chose/chose_professor/NoProfessorStudentList.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：查询没有选完学生的导师列表-管理员
	 * 作者：赵晶
	 * 时间：2017-01-06
	 ****************************************************************************/
	@RequestMapping("/havePositionProfessorList")
	public @ResponseBody
    String havePositionProfessorList(String cname, String realUsername, String username, Integer currpage, Integer choseThemeId, Integer attendanceTime){
		int pageSize=10;
		User user=new User();
		user.setCname(cname);
		user.setUsername(username);
		 List<Object[]> lists =choseProfessorService.havePositionProfessorList(user,choseThemeId,currpage,pageSize);
		 //分页开始
		 int totalRecords=choseProfessorService.findhavePositionProfessorListCount(user,choseThemeId);
		 Map<String,Integer> pageModel =shareService.getPage(currpage,pageSize,totalRecords);
		 String s="";
		 for(Object[] obj:lists){
		    	s+="<tr>"+
		    	    	"<td>"+obj[1]+"</td>"+
		    			"<td>"+obj[2]+"</td>"+
		    			"<td>"+obj[3]+"</td>"+
		    			"<td>"+obj[4]+"</td>"+
		    			"<td><a href='addChoseProfessorForRestStudent?realUsername="+realUsername+"&professor="+obj[1]+"&attendanceTime="+attendanceTime+"&choseThemeId="+choseThemeId+"'>添加</a></td>"+
		    			"</tr>";
		 	}
			 s+="<tr><td colspan='5'>"+
			    	    "<a href='javascript:void(0)' onclick='firstPage(1,"+realUsername+");'>"+"首页"+"</a>&nbsp;"+
			    	    "<a href='javascript:void(0)' onclick='previousPage("+currpage+","+realUsername+");'>"+"上一页"+"</a>&nbsp;"+
			    	    "<a href='javascript:void(0)' onclick='nextPage("+currpage+","+pageModel.get("totalPage")+","+realUsername+");'>"+"下一页"+"</a>&nbsp;"+
			    	    "<a href='javascript:void(0)' onclick='lastPage("+pageModel.get("totalPage")+","+realUsername+");'>"+"末页"+"</a>&nbsp;"+
			    	    "当前第"+currpage+"页&nbsp; 共"+pageModel.get("totalPage")+"页  "+totalRecords+"条记录"+
			    	    		"</td></tr>";
			 return htmlEncode(s);

}
/****************************************************************************
 * 功能：添加导师功能-管理员
 * 作者：赵晶
 * 时间：2018-1-08
 ****************************************************************************/
	@RequestMapping("/addChoseProfessorForRestStudent")
	public ModelAndView addChoseProfessorForRestStudent(@ModelAttribute("selected_academy") String acno, String realUsername, String professor, Integer attendanceTime, Integer choseThemeId){
		ModelAndView mav=new ModelAndView();
		User user = userDAO.findUserByPrimaryKey(realUsername);
		if(user!=null){
			if(user.getChoseUser() == null){
				ChoseUser cu = new ChoseUser();
				cu.setUser(user);
				cu.setProfessor(professor);
				choseUserDAO.store(cu);
			}else {
				user.getChoseUser().setProfessor(professor);
			}
		}
		//根据导师的username和所属的大纲choseThemeId找到导师
		ChoseProfessor choseProfessor=choseProfessorService.getChoseProfessorByUsernameAndThemeId(professor,choseThemeId);
		if(choseProfessor!=null){
			choseProfessor.setFinalNumber(choseProfessor.getFinalNumber()+1);
			choseProfessorDAO.store(choseProfessor);
		}
		userDAO.store(user);
		//新建choseprofessorbatch
		ChoseProfessorBatch choseProfessorBatch=new ChoseProfessorBatch();
		choseProfessorBatch.setChoseTheme(choseThemeDAO.findChoseThemeByPrimaryKey(choseThemeId));
		List<ChoseProfessorBatch> choseProfessorBatchList= choseProfessorService.belongChoseProfessorBatchList(choseThemeId);
		for(ChoseProfessorBatch cpfb:choseProfessorBatchList){
			//以其他批次的最后截止时间为补选批次的开始时间
			if((choseProfessorBatch.getStartTime())==null||"".equals(choseProfessorBatch.getStartTime())){
				choseProfessorBatch.setStartTime(cpfb.getEndTime());
			}else{
				if(cpfb.getEndTime().after(choseProfessorBatch.getStartTime())){
					choseProfessorBatch.setStartTime(cpfb.getEndTime());
				}
			}
		}
		choseProfessorBatch.setEndTime(choseThemeDAO.findChoseThemeByPrimaryKey(choseThemeId).getEndTime());
		choseProfessorBatch.setBatchNumber(choseProfessorService.belongChoseProfessorBatchList(choseThemeId).size()+1);
		choseProfessorBatchDAO.store(choseProfessorBatch);
		//修改chose_theme批数个数
		ChoseTheme choseTheme=choseThemeDAO.findChoseThemeByPrimaryKey(choseThemeId);
		choseTheme.setBatchNumber(choseTheme.getBatchNumber()+1);
		choseThemeDAO.store(choseTheme);
		//新建chose_professor_record记录，保存学生数据
		ChoseProfessorRecord choseProfessorRecord=new ChoseProfessorRecord();
		choseProfessorRecord.setChoseProfessor(choseProfessorService.getChoseProfessorByUsernameAndThemeId(professor,choseThemeId));
		choseProfessorRecord.setUser(user);
		List<ChoseProfessorBatch> cpfblist=choseProfessorService.belongChoseProfessorBatchList(choseThemeId);
		ChoseProfessorBatch cpfbmax=new ChoseProfessorBatch();
		Integer batchnumber=0;
		for(ChoseProfessorBatch cpfb:cpfblist){
			if(batchnumber==0){
				batchnumber=cpfb.getBatchNumber();
				cpfbmax=cpfb;
			}else{
				if(cpfb.getBatchNumber()>batchnumber){
					cpfbmax=cpfb;
				}
			}
		}
		//直接存上面的choseprofessorbatch会报回滚错误
		choseProfessorRecord.setChoseProfessorBatch(cpfbmax);
		choseProfessorRecord.setAduitResult(1);
		choseProfessorRecord.setCurrAduit(2);
		choseProfessorRecord.setTUsername(professor);
		choseProfessorRecordDAO.store(choseProfessorRecord);
		mav.setViewName("redirect:/nwuChose/noProfessorStudentList?currpage=1&attendanceTime="+attendanceTime+"&choseThemeId="+choseThemeId);
		return mav;
	}

	/****************************************************************************
	 * 功能：发布大纲-管理员
	 * 作者：赵晶
	 * 时间：2018-1-09
	 ****************************************************************************/
	@RequestMapping("/releaseChoseTheme")
	public String releaseChoseTheme(@ModelAttribute("selected_academy") String acno, Integer themeId, HttpServletRequest request, Integer studentNumber){
		HttpSession session = request.getSession();
		Object choseThemeObj = session.getAttribute("choseTheme");
//		Object studentListObj = session.getAttribute("studentList");
		Object choseProfessorBatchListObj = session.getAttribute("choseProfessorBatchList");
		ChoseTheme choseTheme = choseThemeDAO.findChoseThemeByPrimaryKey(themeId);
		//查询本次大纲下attendanceTime等于所选届的学生（没有导师）数量
		/*int count=choseProfessorService.findStudentByAttendanceTimeCount(choseTheme.getTheYear());
		if(studentListObj!=null){
			List<User> users=(List<User>)studentListObj;
			for(User user:users){
				user.setRealAttendanceTime(choseTheme.getTheYear().toString());
				userDAO.store(user);
			}
			count+=users.size();
		}*/
		if(choseProfessorBatchListObj!=null){
			List<ChoseProfessorBatch> ChoseProfessorBatchList=(List<ChoseProfessorBatch>)choseProfessorBatchListObj;
			for(ChoseProfessorBatch choseProfessorBatch:ChoseProfessorBatchList){
				choseProfessorBatchDAO.store(choseProfessorBatch);
			}
		}
		if(choseThemeObj!=null){
			ChoseTheme theme=(ChoseTheme)choseThemeObj;
			theme.setStudentNumber(studentNumber);
			theme.setState(1);//已发布
			choseThemeDAO.store(theme);
		}

		//清除存入session里的studentList
//		session.removeAttribute("studentList");
		return "redirect:/nwuChose/ChoseThemeList?currpage=1";
    }
	/****************************************************************************
	 * 功能：删除大纲-管理员
	 * 作者：赵晶
	 * 时间：2018-1-09
	 ****************************************************************************/
	@RequestMapping("/deleteChoseTheme")
	public String deleteChoseTheme(@ModelAttribute("selected_academy") String acno, Integer themeId){
		ChoseTheme choseTheme = choseThemeDAO.findChoseThemeByPrimaryKey(themeId);
		Set<ChoseProfessor> professors = choseTheme.getChoseProfessors();
		Iterator<ChoseProfessor> it = professors.iterator();
		if(professors!=null&&professors.size()!=0){
			while(it.hasNext()){
				choseProfessorDAO.remove(it.next());
				it.remove();
			}
		}
		choseThemeDAO.remove(choseTheme);
		return "redirect:/nwuChose/ChoseThemeList?currpage=1";
	}
	/****************************************************************************
	 * 功能：找到某大纲下导师审核通过的学生列表-管理员
	 * 作者：赵晶
	 * 时间：2018-1-09
	 ****************************************************************************/
	@RequestMapping("/passStudentListForProfessor")
	public @ResponseBody
    String passStudentListForProfessor(@ModelAttribute("selected_academy") String acno, Integer themeId, Integer professorId){
		String str="";
		//找到某大纲下的所有志愿
		List<ChoseProfessorBatch> batchList = choseProfessorService.belongChoseProfessorBatchList(themeId);
		if(batchList!=null){
			int [] batchIds=new int[batchList.size()];//存放批次
			for(int i=0;i<batchList.size();i++){
				batchIds[i]=batchList.get(i).getId();
			}
			//根据大纲id和导师id找到所有通过的学生
			List<ChoseProfessorRecord> cfr=choseProfessorService.passStudentListForProfessor(batchIds,professorId);
			if(cfr!=null){
				for(ChoseProfessorRecord c:cfr){
					str+="<tr>"+
		    	    	"<td>"+c.getUser().getUsername()+"</td>"+
		    			"<td>"+c.getUser().getCname()+"</td>"+
		    			"<td>"+c.getUser().getUserSexy()+"</td>"+
		    			"<td>"+(c.getUser().getSchoolClasses() == null ? "无" : c.getUser().getSchoolClasses().getClassName())+"</td>"+
		    			"<td>"+c.getUser().getAttendanceTime()+"</td>"+
		    			/*"<td>"+c.getUser().getSchoolAcademy().getAcademyName()+"</td>"+*/
		    			"</tr>";
				}

			}
		}
		return htmlEncode(str);
	}

	/****************************************************************************
	 * 功能：我的导师-学生
	 * 作者：赵晶
	 * 时间：2018-1-09
	 ****************************************************************************/
	@RequestMapping("/myProfessorForStudent")
	public ModelAndView myProfessorForStudent(){
		ModelAndView mav=new ModelAndView();
		//找到当前用户信息
		User userInfo = shareService.getUserDetail();
		User user = userDAO.findUserByPrimaryKey(userInfo.getUsername());
		if(user!=null && user.getChoseUser() != null){
			ChoseProfessor choseProfessor=choseProfessorService.findProfessorByProfessorUsername(user.getChoseUser().getProfessor());
			mav.addObject("choseProfessor", choseProfessor);
		}
		mav.setViewName("chose/chose_professor/MyProfessor.jsp");
		return mav;
	}

	/****************************************************************************
	 * 功能：我的导师-学生
	 * 作者：赵晶
	 * 时间：2018-1-09
	 ****************************************************************************/
	@RequestMapping("/myStudentForProfessor")
	public ModelAndView myStudentForProfessor(@ModelAttribute("selected_academy") String acno){
		ModelAndView mav=new ModelAndView();
		User user = shareService.getUserDetail();
		List<User> users=choseProfessorService.myStudentForProfessor(user.getUsername());
		mav.addObject("users", users);
		mav.setViewName("chose/chose_professor/MyStudent.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：编辑导师互选大纲-管理员{导师填写期望的学生数量}
	 * 作者：赵晶
	 * 时间：2018.1.10
	 ****************************************************************************/
	@RequestMapping("/editChoseTheme")
	public ModelAndView editChoseTheme(@RequestParam Integer currpage, Integer themeId, HttpServletRequest request, @ModelAttribute ChoseProfessor choseProfessor){
		//首先清楚session中的存放的对象
		HttpSession session = request.getSession();
		session.removeAttribute("studentList");
		ModelAndView mav=new ModelAndView();
		ChoseTheme choseTheme=new ChoseTheme();
		choseTheme.setId(themeId);
		Integer pageSize=10;
		//找出新查纪录的备选导师名单
		List<ChoseProfessor> choseProfessorList = choseProfessorService.findChoseProfessorByThemeId(choseTheme.getId(), currpage, pageSize,choseProfessor);
		int totalRecords = choseProfessorService.findCountChoseProfessorByThemeId(themeId,choseProfessor);
		mav.addObject("choseProfessorList", choseProfessorList);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("user", new User());
		mav.addObject("choseTheme", choseTheme);
		mav.addObject("themeId", themeId);
		mav.setViewName("chose/chose_professor/EditChoseThemeForAdmin.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：编辑导师互选大纲-管理员{导师填写期望的学生数量}
	 * 作者：赵晶
	 * 时间：2018.1.10
	 ****************************************************************************/
	@RequestMapping("/deleteChoseProfessorForAdmin")
	public String deleteChoseProfessorForAdmin(Integer professorId){
		ChoseProfessor choseProfessor = choseProfessorDAO.findChoseProfessorByPrimaryKey(professorId);
		ChoseTheme choseTheme = choseProfessor.getChoseTheme();
		choseProfessorDAO.remove(choseProfessor);
		Integer teacherNumber=choseTheme.getTeacherNumber();
		choseTheme.setTeacherNumber(teacherNumber-1);
		choseThemeDAO.store(choseTheme);
		return "redirect:/nwuChose/editChoseTheme?currpage=1&themeId="+choseTheme.getId();
	}
	/****************************************************************************
	 * 功能：新建导师互选大纲第四步-管理员
	 * 作者：赵晶
	 * 时间：2018.1.10
	 ****************************************************************************//*
	@RequestMapping("/newChoseThemFourStep")
	public String newChoseThemFourStep(){
		return "redirect:chose/chose_professor/NextAddChoseTheme.jsp";
	}*/

	/****************************************************************************
	 * 功能：新建大纲第三步-管理员
	 * 作者：赵晶
	 * 时间：2018.1.10
	 ****************************************************************************/
	@RequestMapping("/newChoseThemeFourStep")
	public ModelAndView editChoseTheme(@ModelAttribute("selected_academy") String acno, @ModelAttribute ChoseTheme choseTheme){
		ModelAndView mav=new ModelAndView();
		ChoseTheme chose = choseThemeDAO.findChoseThemeByPrimaryKey(choseTheme.getId());
		mav.addObject("teacherNum", chose.getChoseProfessors().size());
		mav.setViewName("chose/chose_professor/NextAddChoseTheme.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：保存大纲第三步-管理员
	 * 作者：赵晶
	 * 时间：2018.1.10
	 ****************************************************************************/
	@RequestMapping("/saveChoseThemeFour")
	public String saveChoseThemeFour(@ModelAttribute("selected_academy") String acno, @ModelAttribute ChoseTheme choseTheme, HttpServletRequest request){
		ChoseTheme theme = choseThemeDAO.findChoseThemeByPrimaryKey(choseTheme.getId());
		//获得互选大纲的开始和结束时间
		String startStr = request.getParameter("start");
		String endStr = request.getParameter("end");
		//获得批次的开始和结束时间数组
		String[] startTime = request.getParameterValues("startTime");
		String[] endTime = request.getParameterValues("endTime");
		List<ChoseProfessorBatch> choseProfessorBatchs=new ArrayList<ChoseProfessorBatch>();
		//遍历，获取每个志愿的开始和结束时间
		for(int i=0; i<startTime.length;i++){
			ChoseProfessorBatch choseProfessorBatch=new ChoseProfessorBatch();
			choseProfessorBatch.setStartTime(shareService.toCalendarDetil(startTime[i]));
			choseProfessorBatch.setEndTime(shareService.toCalendarDetil(endTime[i]));
			choseProfessorBatch.setBatchNumber(i+1);
			choseProfessorBatch.setChoseTheme(choseTheme);
			//将志愿存入list集合中
			choseProfessorBatchs.add(choseProfessorBatch);
		}
		//保存互选开始和结束时间
		theme.setStartTime(shareService.toCalendar(startStr));
		theme.setBatchNumber(choseTheme.getBatchNumber());
		theme.setEndTime(shareService.toCalendar(endStr));
		//获取session，将choseThemechoseProfessorBatchList和存入session中
		HttpSession session = request.getSession();
		session.setAttribute("choseTheme", theme);
		session.setAttribute("choseProfessorBatchList", choseProfessorBatchs);
		//跳转到其他届学生列表页面
		return "redirect:findSelectStudentByUser?currpage=1&flag=1&attendanceTime="+theme.getTheYear()+"&themeId="+theme.getId();
	}
	/****************************************************************************
	 * 功能：找到所有的大纲-导师
	 * 作者：赵晶
	 * 时间：2018.1.10
	 ****************************************************************************/
	@RequestMapping("/ChoseThemeListForProfessor")
	public ModelAndView ChoseThemeListForProfessor(@ModelAttribute("selected_academy") String acno, Integer currpage, @ModelAttribute ChoseTheme choseTheme){
		ModelAndView mav=new ModelAndView();
		//获得当前登陆人的信息
		User user = shareService.getUserDetail();
		List<ChoseProfessor> choseProfessors=choseProfessorService.findchoseProfessorList(user.getUsername());
		if(choseProfessors!=null&&choseProfessors.size()!=0){
			Integer[] themeIds=new Integer[choseProfessors.size()];
			for(int i=0;i<themeIds.length;i++){
				themeIds[i]=choseProfessors.get(i).getChoseTheme().getId();
			}
			//分页
			int pageSize=10;
			List<ChoseTheme> ChoseThemes=choseProfessorService.findChoseThemeListByThemeIds(themeIds,choseTheme,currpage,pageSize);
			int totalRecords = choseProfessorService.findChoseThemeListByThemeIdsCount(themeIds,choseTheme);
			mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
			mav.addObject("choseThemes", ChoseThemes);
		}
		mav.setViewName("chose/chose_professor/ChoseThemeListForProfessor.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：检查是否有同届的未关闭的大纲-管理员
	 * 作者：赵晶
	 * 时间：2018.1.15
	 ****************************************************************************/
	@RequestMapping("/checkUncloseSameOutline")
	public @ResponseBody
    String checkUncloseSameOutline(Integer theYear){
		//根据届找到同届的未关闭的大纲
		List<ChoseTheme> choseThemes=choseProfessorService.UncloseSameOutlineList(theYear);
		if(choseThemes!=null&&choseThemes.size()!=0){
			return shareService.htmlEncode("0");//有同届的，需要管理员手动关闭
		}
		else{
			return shareService.htmlEncode("1");//没有同届的，进入新建大纲的下一步
		}

	}
	/****************************************************************************
	 * 功能：关闭大纲-管理员
	 * 作者：赵晶
	 * 时间：2018-01-15
	 ****************************************************************************/
	@RequestMapping("/closeChoseTheme")
	public String closeChoseTheme(@ModelAttribute("selected_academy") String acno, Integer themeId){
		ChoseTheme choseTheme = choseThemeDAO.findChoseThemeByPrimaryKey(themeId);
		choseTheme.setState(2);//设置未已关闭
		choseThemeDAO.store(choseTheme);
		//找到关闭大纲下的没有导师学生的列表
		List<User> users=choseProfessorService.findNoProfessorUserByAttendancTime(choseTheme.getTheYear().toString());
		if(users!=null&users.size()!=0){
			//修改是否提交志愿
			for(User user:users){
				User u = userDAO.findUserByPrimaryKey(user.getUsername());
				u.getChoseUser().setIsChoseProfessor(0);
				userDAO.store(u);
			}
		}
		return "redirect:/nwuChose/ChoseThemeList?currpage=1";
	}
	/****************************************************************************
	 * 功能：新建大纲(保存)完成-管理员
	 * 作者：赵晶
	 * 时间：2018-01-15
	 ****************************************************************************/
	@RequestMapping("/savaThemeChoseTwoStep")
	public String savaThemeChoseTwoStep(HttpServletRequest request){
		HttpSession session = request.getSession();
		//从sesson中获取choseTheme
		Object choseThemeObj = session.getAttribute("choseTheme");
		//从session中获得choseProfessorList
		Object choseProfessorListObj = session.getAttribute("choseProfessorList");
		ChoseTheme choseTheme=(ChoseTheme)choseThemeObj;
		choseTheme.setState(0);
		ChoseTheme theme=null;
		if(choseProfessorListObj==null){
			choseTheme.setTeacherNumber(0);
			theme = choseThemeDAO.store(choseTheme);
		}
		else{
			List<ChoseProfessor> choseProfessorList= (List<ChoseProfessor>)choseProfessorListObj;
			choseTheme.setTeacherNumber(choseProfessorList.size());
			theme = choseThemeDAO.store(choseTheme);
			for(ChoseProfessor choseProfessor:choseProfessorList){
				choseProfessor.setChoseTheme(theme);
				choseProfessorDAO.store(choseProfessor);
			}
		}
		/*//从session中获取choseProfessorBatchList
		Object choseProfessorBatchListObj = session.getAttribute("choseProfessorBatchList");
		List<ChoseProfessorBatch> choseProfessorBatchList= (List<ChoseProfessorBatch>)choseProfessorBatchListObj;
		for(ChoseProfessorBatch choseProfessorBatch:choseProfessorBatchList){
			choseProfessorBatch.setChoseTheme(theme);
			choseProfessorBatchDAO.store(choseProfessorBatch);
		}*/
		//清除session中的choseTheme、choseProfessorLlist、choseProfessorBatchList
		session.removeAttribute("choseTheme");
		session.removeAttribute("choseProfessorList");
		//session.removeAttribute("choseProfessorBatchList");
		return "redirect:/nwuChose/ChoseThemeList?currpage=1";
	}


	/****************************************************************************
	 * 功能：处理ajax中文乱码
	 * 作者：贺子龙
	 * 时间：2015-09-08
	 ****************************************************************************/
	public static String htmlEncode(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			int c = (int) str.charAt(i);
			if (c > 127 && c != 160) {
				sb.append("&#").append(c).append(";");
			} else {
				sb.append((char) c);
			}
		}
		return sb.toString();
	}
	 /***************************************************************************
	  * @Description 学年论文列表--导师
	  * @return ModelAndView
	  * @author 孙虎
	  * @date 2018-1-19
	  **************************************************************************/
	@RequestMapping("/ChoseDisserationForYearList")
	public ModelAndView ChoseDisserationForYearList(HttpServletRequest request, @RequestParam int currpage){
		ModelAndView mav = new ModelAndView();
		User user = shareService.getUserDetail();
		int pageSize = 10;
		int totalRecords = choseProfessorService.findChoseDisserationForYearsCount(user.getUsername());
		List<ChoseDessitationForYear> dessitationForYears = choseProfessorService.findChoseDisserationForYears(user.getUsername(), currpage, pageSize,0);
		mav.addObject("dessitationForYears", dessitationForYears);
		//所有学期
		mav.addObject("pageSize", pageSize);
		mav.addObject("currpage", currpage);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.setViewName("chose/chose_professor/ChoseDisserationForYearList.jsp");
		return mav;
	}
	/***************************************************************************
	 * @Description 新建学年论文--导师
	 * @return ModelAndView
	 * @author 孙虎
	 * @date 2018-1-19
	 **************************************************************************/
	@RequestMapping("/NewChoseDisserationForYear")
	public ModelAndView NewChoseDisserationForYear(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		User user = shareService.getUserDetail();
		ChoseDessitationForYear dessitationForYear = new ChoseDessitationForYear();
		mav.addObject("dessitationForYear", dessitationForYear);
		mav.addObject("mystudentsForYear",choseProfessorService.myStudentForProfessor(user.getUsername()));
		mav.setViewName("chose/chose_professor/NewChoseDisserationForYear.jsp");
		return mav;
	}
	/***************************************************************************
	 * @Description 编辑学年论文--导师
	 * @return ModelAndView
	 * @author 孙虎
	 * @date 2018-1-19
	 **************************************************************************/
	@RequestMapping("/editChoseDisserationForYear")
	public ModelAndView editChoseDisserationForYear(HttpServletRequest request, @RequestParam int id, int currpage){
		ModelAndView mav = new ModelAndView();
		User user = shareService.getUserDetail();
		ChoseDessitationForYear dessitationForYear = choseDessitationForYearDAO.findChoseDessitationForYearByPrimaryKey(id);
		mav.addObject("dessitationForYear", dessitationForYear);
		mav.addObject("mystudentsForYear",choseProfessorService.myStudentForProfessor(user.getUsername()));
		mav.setViewName("chose/chose_professor/NewChoseDisserationForYear.jsp");
		return mav;
	}
	/***************************************************************************
	 * @Description 保存学年论文--导师
	 * @return ModelAndView
	 * @author 孙虎
	 * @date 2018-1-19
	 **************************************************************************/
	@RequestMapping("/SaveChoseDisserationForYear")
	public String SaveChoseDisserationForYear(HttpServletRequest request, @ModelAttribute ChoseDessitationForYear choseDessitationForYear, @RequestParam String finishTime){
		User user = shareService.getUserDetail();
		choseDessitationForYear.setTeacher(user.getUsername());
		choseDessitationForYear.setFinishTime(shareService.toCalendar(finishTime));
		choseDessitationForYear.setState(0);
		choseDessitationForYear.setStudentCname(userDAO.findUserByPrimaryKey(choseDessitationForYear.getStudent()).getCname());
		choseProfessorService.saveChoseDessitationForYear(choseDessitationForYear);
		return "redirect:/nwuChose/ChoseDisserationForYearList?currpage=1";
	}
	/***************************************************************************
	 * @Description 保存学年论文--导师
	 * @return ModelAndView
	 * @author 孙虎
	 * @date 2018-1-19
	 **************************************************************************/
	@RequestMapping("/deleteChoseDisserationForYear")
	public String deleteChoseDisserationForYear(HttpServletRequest request, @RequestParam int id, int currpage){
		User user = shareService.getUserDetail();
		ChoseDessitationForYear dessitationForYear = choseDessitationForYearDAO.findChoseDessitationForYearByPrimaryKey(id);
		choseDessitationForYearDAO.remove(dessitationForYear);
		choseDessitationForYearDAO.flush();
		return "redirect:/nwuChose/ChoseDisserationForYearList?currpage="+currpage;
	}
	/***************************************************************************
	 * @Description 发布学年论文--导师
	 * @return ModelAndView
	 * @author 孙虎
	 * @date 2018-1-19
	 **************************************************************************/
	@ResponseBody
	@RequestMapping("/releaseChoseDisserationForYear")
	public String releaseChoseDisserationForYear(HttpServletRequest request, @RequestParam int id){
		User user = shareService.getUserDetail();
		ChoseDessitationForYear dessitationForYear = choseDessitationForYearDAO.findChoseDessitationForYearByPrimaryKey(id);
		//是否已发布该学生的学年论文
		boolean isReleased = choseProfessorService.isAllreadyReleasedDessitationForYear(dessitationForYear.getTeacher(),dessitationForYear.getStudent(),dessitationForYear.getTheYear());
		if(isReleased){
			return "isReleased";
		}else{
			choseProfessorService.releasedDessitationForYear(dessitationForYear);
		}
		return "success";
	}
	 /***************************************************************************
	  * @Description 学年论文--学生
	  * @return ModelAndView
	  * @author 孙虎
	  * @date 2018-1-19
	  **************************************************************************/
	@RequestMapping("/myChoseDisserationForYear")
	public ModelAndView myChoseDisserationForYear(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		User user = shareService.getUserDetail();
		List<ChoseDessitationForYear> dessitationForYears = choseProfessorService.findChoseDisserationForYears(user.getUsername(), 0, 100,1);
		mav.addObject("dessitationForYears", dessitationForYears);
		mav.setViewName("chose/chose_professor/myChoseDisserationForYear.jsp");
		return mav;
	}
	/***************************************************************************
	 * @Description 上传学年论文--学生
	 * @return ModelAndView
	 * @author 孙虎
	 * @date 2018-1-19
	 **************************************************************************/
	@Transactional
	@RequestMapping("/uploadDessistationForYear")
	public @ResponseBody
    String uploadDessistationForYear(@RequestParam(value = "file", required = false) MultipartFile upFile, HttpServletRequest request, HttpServletResponse response, @RequestParam int id){
		//上传文件返回相对地址和文档名字
		List<String> saveFileAddressName = choseProfessorService.uploadDessistationForYear(request, response,id);
		//保存论文对应的document数据
		CommonDocument document = choseProfessorService.saveDessitationDocument(saveFileAddressName);
		//保存ChoseDessitationForYear和document的关联
		ChoseDessitationForYear choseDessitationForYear = choseDessitationForYearDAO.findChoseDessitationForYearByPrimaryKey(id);
		choseDessitationForYear.setDocumentId(document.getId());
		choseProfessorService.saveChoseDessitationForYear(choseDessitationForYear);
		return "ok";
	}
	/***************************************************************************
	 * @Description 下载学年论文
	 * @return String
	 * @author 赵晶
	 * @date 2018-1-24
	 **************************************************************************/
	@RequestMapping("/downloadDessistationForYear")
	public @ResponseBody
    String downloadDessistationForYear(HttpServletRequest request, HttpServletResponse response, Integer id) throws Exception {
		ChoseDessitationForYear dessitationForYear = choseDessitationForYearDAO.findChoseDessitationForYearByPrimaryKey(id);
		choseProfessorService.downloadDessistationForYear(request, response,dessitationForYear.getDocumentId(),id);
		return "ok";
	}
	/***************************************************************************
	 * @Description 检查是否可以发布(1:有注意事项 可以发布 2：没有 不可发布)
	 * @return String
	 * @author 赵晶
	 * @date 2018-1-24
	 **************************************************************************/
	@RequestMapping("/checkIFRelease")
	public @ResponseBody
    String checkIFRelease(HttpServletRequest request, HttpServletResponse response, Integer themeId) {
		ChoseTheme choseTheme = choseThemeDAO.findChoseThemeByPrimaryKey(themeId);
		Integer type = choseTheme.getType();
		if(type!=null &&type!=0){
			Integer types[]=new Integer[2];
			if(type==1){
				types[0]=1;
				types[1]=2;
			}
			else{
				types[0]=3;
				types[1]=4;
			}
			List<ChoseAttention> choseAttentions=choseProfessorService.findChoseAttentionByTypeArray(types);
			if(choseAttentions!=null&&choseAttentions.size()!=0){
				if(choseAttentions.size()==2){
					return "success";
				}
			}

		}
		return "error";
	}
	/***************************************************************************
	 * @Description 检查是否阅读注意事项
	 * @return String
	 * @author 赵晶
	 * @date 2018-1-4
	 **************************************************************************/
	@RequestMapping("/checkIfReadAttention")
	public @ResponseBody
    String checkIfReadAttention(Integer themeId) {
		//获得登陆人信息
		User user = shareService.getUserDetail();
		List<ChoseAttentionRecord> choseAttentionRecords=choseProfessorService.findChoseAttentinRecordByThemIdAndUsername(user.getUsername(),themeId);
		if(choseAttentionRecords!=null&&choseAttentionRecords.size()!=0){
			return "success";//表示已经读过了
		}
		else{
			return "error";//表示没有读过
		}
	}
	/***************************************************************************
	 * @Description 找到注意事项
	 * @return String
	 * @author 赵晶
	 * @date 2018-1-4
	 **************************************************************************/
	@RequestMapping("/findChoseAttentionByType")
	public ModelAndView findChoseAttentionByType(Integer type, Integer themeId) {
		ModelAndView mav=new ModelAndView();
		List<ChoseAttention> choseAttentions = choseProfessorService.findChoseAttentionByType(type);
		if(choseAttentions!=null&&choseAttentions.size()!=0){
			mav.addObject("choseDissertation", choseAttentions.get(0));
		}
		mav.addObject("themeId", themeId);
		mav.addObject("type", type);
		mav.setViewName("chose/chose_professor/ChoseAttention.jsp");
		return mav;
	}
	/***************************************************************************
	 * @Description 新建注意事项记录
	 * @return String
	 * @author 赵晶
	 * @date 2018-1-24
	 **************************************************************************/
	@RequestMapping("/newChoseAttentionRecord")
	public String newChoseAttentionRecord(Integer themeId, Integer type) {
		//获得登陆人
		User user = shareService.getUserDetail();
		ChoseAttentionRecord choseAttentionRecord =new ChoseAttentionRecord();
		choseAttentionRecord.setThemeId(themeId);
		choseAttentionRecord.setUsername(user.getUsername());
		choseAttentionRecordDAO.store(choseAttentionRecord);
		ModelAndView mav=new ModelAndView();
		//mav.setViewName("redirect:/toAddBatch?i=1&currpage=1&themeId="+themeId);
		if(type==1){
			return "forward:toAddBatch?i=1&currpage=1&themeId="+themeId;
		}
		else{
			if(type==2){
				return "redirect:nwuChose/ChoseProfessorBatchListForProfessor?currpage=1&themeId="+themeId;
			}
			else{
				if(type==3){
					return "redirect:/choseDissertation/toAddBatch?i=1&currpage=1&themeId="+themeId;
				}
				else{
					if(type==4){
						return "redirect:/choseDissertation/choseProfessorBatchListForProfessor?currpage=1&themeId="+themeId;
					}
				}
			}
		}
		return "";
	}
	/**
	 * 功能:管理员填写期望学生数
	 * @author 赵晶
	 * @date 2018.1.27
	 */
	@RequestMapping("/saveExpectNumberForAdmin")
	public @ResponseBody
    String saveExpectNumberForAdmin(ChoseProfessor professor){
		//获取当前登陆人的信息
		User user = shareService.getUserDetail();
		ChoseProfessor choseProfessor = choseProfessorDAO.findChoseProfessorByPrimaryKey(professor.getId());
		choseProfessor.setExpectNumber(professor.getExpectNumber());
		choseProfessorDAO.store(choseProfessor);
		return "success";
	}
	/**
	 * 功能:根据大纲id找到未填写期望学生数的导师
	 * @author 赵晶
	 * @date 2018.1.27
	 */
	@RequestMapping("/findNoExpectNumberBythemeId")
	public @ResponseBody
    String findNoExpectNumberBythemeId(Integer themeId){
		List<ChoseProfessor> choseProfessorList=choseProfessorService.findNoExpectNumberBythemeId(themeId);
		if(choseProfessorList!=null&&choseProfessorList.size()!=0){
			return "error";
		}
		else{
			return "success";
		}
	}
}