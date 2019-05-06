/****************************************************************************
 * 命名规范：
 * 本控制层调整必须是按照如下的路径调整：@RequestMapping("/device/system/sample")，全部小写。
 * 列表信息listxxxx，獲取信息：getxxxx 编辑信息：editxxxx 删除信息：detelexxxx 新增信息 newxxxx
 * 导出信息exportxxxx 保存信息：savexxxx 
 ****************************************************************************/

package net.zjcclims.web.chose;

import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.chose.ChoseDissertationService;
import net.zjcclims.service.chose.ChoseProfessorService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabCenterService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/****************************************************************************
 * 功能：导师互选模块 作者：孙虎时间：2017-12-1
 ****************************************************************************/

/**
 * @author Administrator
 *
 * @param <JsonResult>
 */
@Controller("ChoseDissertationController")
@RequestMapping("/choseDissertation")
@SessionAttributes("selected_academy")
public class ChoseDissertationController<JsonResult> {
	
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
    private ChoseDissertationDAO choseDissertationDAO;
	@Autowired
    private ChoseDissertationService choseDissertationService;
	@Autowired
    private ChoseProfessorService choseProfessorService;
	@Autowired
    private ChoseDissertationRecordDAO choseDissertationRecordDAO;
	@Autowired
    private CommonDocumentDAO commonDocumentDAO;
	@Autowired
	private ChoseUserDAO choseUserDAO;
	/**************************************************************************
	 * @Description 导师互选大纲列表
	 * @return ModelAndView
	 * @author 孙虎
	 * @date 2017-12-22
	 **************************************************************************/
	@RequestMapping("/ChoseDissertationThemeList")
	public ModelAndView ChoseDisserationThemeList(HttpServletRequest request, @ModelAttribute ChoseTheme choseTheme, @RequestParam int currpage){
		ModelAndView mav = new ModelAndView();
		int pageSize = 10;
		int totalRecords = choseDissertationService.findChoseThemesForCDCount(choseTheme);
		List<ChoseTheme> choseThemes = choseDissertationService.findChoseThemesForCD(choseTheme, currpage, pageSize);
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
					if(currLong>EndLong){
						ctv.setIsOverCurrTime(1);
					}
					else{
						ctv.setIsOverCurrTime(0);//设置参数结束
					}
				}
				
				choseThemeVos.add(ctv);
			}
			mav.addObject("choseThemeVos", choseThemeVos);
		}
		//获得当前时间
		Calendar currTime=Calendar.getInstance();
		//获得属于当前时间的志愿
		ChoseProfessorBatch choseProfessorBatch=choseDissertationService.belongCurrentTimeChoseProfessorBath(currTime);
		if(choseProfessorBatch!=null){
			ChoseTheme theme = choseProfessorBatch.getChoseTheme();
			//根据choseThemeId找到参选人数
			int participantNumber= choseProfessorService.findParticipantNumberBythemeId(theme.getTheYear());
			//根据choseThemeId找到未被选人数
			int noSelectedNumber= choseDissertationService.findNoSelectedNumberBythemeId(theme.getTheYear(),theme.getId());
			mav.addObject("participantNumber", participantNumber);
			mav.addObject("noSelectedNumber", noSelectedNumber);
			mav.addObject("choseProfessorBatch", choseProfessorBatch);
		}
		mav.addObject("choseThemes", choseThemes);
		mav.addObject("choseTheme", choseTheme);
		mav.addObject("pageSize", pageSize);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.setViewName("chose/chose_dissertation/ChoseDisserationThemeList.jsp");
		return mav;
	}
	
	/**************************************************************************
	 * @Description 新建毕业论文互选大纲
	 * @return ModelAndView
	 * @author 孙虎
	 * @date 2017-12-22
	 **************************************************************************/
	@RequestMapping("/newChoseThemeForCD")
	public ModelAndView newChoseThemeForCD(HttpServletRequest request, @ModelAttribute ChoseTheme choseTheme){
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		session.removeAttribute("choseTheme");
		session.removeAttribute("choseProfessorList");
		//找到所有学生所属的届
		List<Object> attendanceTimeList= choseProfessorService.findAllAttendanceTime();
		mav.addObject("attendanceTimeList", attendanceTimeList);
		mav.setViewName("chose/chose_dissertation/newChoseThemeForCD.jsp");
		return mav;
	}
	/**************************************************************************
	 * @Description 新建毕业论文互选大纲保存第一步
	 * @return ModelAndView
	 * @author 孙虎
	 * @throws ParseException
	 * @date 2017-12-22
	 **************************************************************************/
	@RequestMapping("/fristSaveChoseThemeForCD")
	public String fristSaveChoseThemeForCD(HttpServletRequest request, @ModelAttribute ChoseTheme choseTheme){
		HttpSession session = request.getSession();
		/*//获得完成时间
		String finishTimeStr=request.getParameter("finishTime");*/
		//开始时间结束时间
		String startLine = request.getParameter("startline");
		String endLine = request.getParameter("endline");
		choseTheme.setExpectStartline(shareService.toCalendar(startLine));
		//set设置结束时间
		choseTheme.setExpectDeadline(shareService.toCalendar(endLine));
		choseTheme.setType(2);
		//choseTheme.setFinishTime(shareService.toCalendar(finishTimeStr));
		//保存ChoseTheme到sesoion中
		session.setAttribute("choseTheme", choseTheme);
		return "redirect:/choseDissertation/ChoseProfessorListForCD?currpage=1";
	}
	/** *************************************************************************
	 * @Description 毕业论文互选大纲下的所有导师(从session中获取)
	 * @return ModelAndView
	 * @author 孙虎
	 * @date 2017-12-24
	 **************************************************************************/
	@RequestMapping("/ChoseProfessorListForCD")
	public ModelAndView ChoseProfessorListForCD(HttpServletRequest request, @RequestParam int currpage, @ModelAttribute User user){
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("choseProfessorList");
		List<ChoseProfessor> choseProfessorList=null;
		 int pageSize=10;
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
			
			 int totalRecords=choseProfessorListAll.size();
			 Map<String,Integer> pageModel =shareService.getPage(currpage,pageSize,totalRecords);
			 //获得总共的页数
			 int totalPage = pageModel.get("totalPage");
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
			 mav.addObject("choseProfessorList", choseProfessorList);
		}
		else{
			Map<String,Integer> pageModel =shareService.getPage(currpage,pageSize,0);
			mav.addObject("pageModel", pageModel);
		}
		mav.setViewName("chose/chose_dissertation/ChoseProfessorListForCD.jsp");
		return mav;
	}
	/** *************************************************************************
	 * @Description 备选导师列表
	 * @return ModelAndView
	 * @author 孙虎
	 * @date 2017-12-24
	 **************************************************************************/
	@RequestMapping("/showUsersForChoseProfessor")
	public ModelAndView showUsersForChoseProfessor(HttpServletRequest request, @RequestParam int currpage, @ModelAttribute User user){
		ModelAndView mav=new ModelAndView();
		int pageSize=10;
		String[] usernameArray=null;
		//获得已经选择的学生列表
		 HttpSession session = request.getSession();
		 Object obj = session.getAttribute("choseProfessorList");
		 if(obj!=null){
			 List<ChoseProfessor> choseProfessorList= (List<ChoseProfessor>)obj;
			 usernameArray=new String[choseProfessorList.size()];
			 for(int i=0;i<choseProfessorList.size();i++){
				 usernameArray[i]=choseProfessorList.get(i).getUser().getUsername();
			 }
		 }
		 List<User> userForProfessor =choseProfessorService.findUserByCnameAndUsername(user,currpage,pageSize,usernameArray);
		 //分页开始
		 int totalRecords=choseProfessorService.findCountUserByCnameAndUsername(user,usernameArray);
		 Map<String,Integer> pageModel =shareService.getPage(currpage,pageSize,totalRecords);
		 mav.addObject("userForProfessor", userForProfessor);
		 mav.addObject("pageModel", pageModel);
		 mav.setViewName("chose/chose_dissertation/showUsersForChoseProfessor.jsp");
		return mav;
	}
	/***************************************************************************
	 * @Description 向大纲批量添加导师---我要去忙教评模块了，添加导师页面的分页和查询也未做
	 * @return String
	 * @author 孙虎
	 * @date 2017-12-25
	 **************************************************************************/
	
	@RequestMapping("/addToProfessor")
	public @ResponseBody
    String addToProfessor(HttpServletRequest request, String[] array){
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("choseProfessorList");
		ChoseTheme choseTheme=(ChoseTheme)session.getAttribute("choseTheme");
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
			choseProfessor.setType(2);//论文制
			choseProfessor.setChoseTheme(choseTheme);
			choseProfessor.setFinalNumber(0);
			choseProfessor.setIsAudit(0);//默认0代表未审核
			choseProfessorList.add(choseProfessor);
		}
		session.setAttribute("choseProfessorList", choseProfessorList);
		return "success";
	}
	/***************************************************************************
	 * @Description 检查是否有同届的大纲
	 * @return String
	 * @author 赵晶
	 * @date 2018-1-17
	 **************************************************************************/
	@RequestMapping("/checkSameTheYearChoseTheme")
	public @ResponseBody
    String checkSameTheYearChoseTheme(Integer theYear){
		ModelAndView mav=new ModelAndView();
		//根据届找到同届的的大纲列表
		List<ChoseTheme> choseThemeList=choseDissertationService.sameTheYearChoseThemeList(theYear);
		if(choseThemeList!=null&&choseThemeList.size()!=0){
			return shareService.htmlEncode("0");//有同届的，需要管理员手动关闭
		}
		else{
			return shareService.htmlEncode("1");//没有同届的，进入新建大纲的下一步
		}
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
			List<ChoseDissertationRecord> cdr=choseDissertationService.passStudentListForProfessor(batchIds,professorId);
			if(cdr!=null){
				for(ChoseDissertationRecord c:cdr){
					str+="<tr>"+
							"<td>"+c.getChoseDissertation().getTittle()+"</td>"+
		    	    	"<td>"+c.getUser().getUsername()+"</td>"+
		    			"<td>"+c.getUser().getCname()+"</td>"+
		    			"<td>"+c.getUser().getSchoolAcademy().getAcademyName()+"</td>"+
		    			"</tr>";
				}
				
			}
		}
		return shareService.htmlEncode(str);
	}
	/****************************************************************************
	 * 功能：删除大纲-管理员
	 * 作者：赵晶
	 * 时间：2018-1-16
	 ****************************************************************************/
	@RequestMapping("/deleteChoseTheme")
	public String deleteChoseTheme(@ModelAttribute("selected_academy") String acno, Integer themeId){
		ChoseTheme choseTheme = choseThemeDAO.findChoseThemeByPrimaryKey(themeId);
//		Set<ChoseProfessor> professors = choseTheme.getChoseProfessors();
//		if(professors!=null&&professors.size()!=0){
//			for(ChoseProfessor professor:professors){
//				if(professor.getChoseDissertations()!=null&&professor.getChoseDissertations().size()!=0){
//					Set<ChoseDissertation> choseDissertationsprofessors=professor.getChoseDissertations();
//					for(ChoseDissertation choseDisseration:choseDissertationsprofessors){
//						ChoseDissertation dissertation = choseDissertationDAO.findChoseDissertationByPrimaryKey(choseDisseration.getId());
//						choseDissertationDAO.remove(dissertation);
//					}
//				}
//				ChoseProfessor choseProfessor = choseProfessorDAO.findChoseProfessorById(professor.getId());
//				choseProfessorDAO.remove(choseProfessor);
//				choseProfessorDAO.flush();
//			}
//		}
		choseThemeDAO.remove(choseThemeDAO.merge(choseTheme));
		choseThemeDAO.flush();
		return "redirect:/choseDissertation/ChoseDissertationThemeList?currpage=1";
	}
	/****************************************************************************
	 * 功能：关闭大纲-管理员
	 * 作者：赵晶
	 * 时间：2018-01-16
	 ****************************************************************************/
	@RequestMapping("/closeChoseTheme")
	public String closeChoseTheme(@ModelAttribute("selected_academy") String acno, Integer themeId){
		ChoseTheme choseTheme = choseThemeDAO.findChoseThemeByPrimaryKey(themeId);
		choseTheme.setState(2);//设置状态-已关闭
		choseThemeDAO.store(choseTheme);
		//找到关闭大纲下的没有导师学生的列表
		List<User> users=choseProfessorService.findNoProfessorUserByAttendancTime(choseTheme.getTheYear().toString());
		if(users!=null&users.size()!=0){
			//修改是否提交志愿
			for(User user:users){
				User u = userDAO.findUserByPrimaryKey(user.getUsername());
				//修改学生是否提交志愿得标记
				//u.setIsChoseProfessor(0);
				u.getChoseUser().setIsChoseDissertation(0);
				userDAO.store(u);
			}
		}
		return "redirect:/choseDissertation/ChoseDissertationThemeList?currpage=1";
	}
	/****************************************************************************
	 * 功能：删除备选导师-管理员
	 * 作者：赵晶
	 * 时间：2018-01-17
	 ****************************************************************************/
	@RequestMapping("/deleteChoseProfessor")
	public String deleteChoseProfessor(HttpServletRequest request, String username){
		//获取session中的choseProfessorList
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("choseProfessorList");
		List<ChoseProfessor> choseProfessorList=(List<ChoseProfessor>)obj;
		Iterator<ChoseProfessor> it = choseProfessorList.iterator();
		while(it.hasNext()){
			int index=it.next().getUser().getUsername().indexOf(username);
			if(index>=0){
				it.remove();
			}
		}
		session.setAttribute("choseProfessorList", choseProfessorList);
		return "redirect:ChoseProfessorListForCD?currpage=1";
	}
	/****************************************************************************
	 * 功能：立题-导师
	 * 作者：赵晶
	 * 时间：2018.1.17
	 ****************************************************************************/
	@RequestMapping("/addDissertationForAdmin")
	public ModelAndView addDissertationForAdmin(@ModelAttribute("selected_academy") String acno, @ModelAttribute ChoseDissertation choseDissertation, Integer professorId){
		ModelAndView mav=new ModelAndView();
		//找到所有的学部
		List<ChoseDissertationDirection> directionList =choseDissertationService.findAllDirection();
		mav.addObject("directionList", directionList);
		mav.addObject("professorId", professorId);
		mav.setViewName("chose/chose_dissertation/AddChoseDissertationForAdmin.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：立题-导师
	 * 作者：赵晶
	 * 时间：2018.1.17
	 ****************************************************************************/
	@RequestMapping("/saveDissertationForAdmin")
	public String saveDissertationForAdmin(@ModelAttribute("selected_academy") String acno, @ModelAttribute ChoseDissertation choseDissertation, HttpServletRequest request, Integer professorId){
		//获得完成时间
		//String finishTimeStr=request.getParameter("finishTime");
		//找到导师所属的学部
		User user = shareService.getUserDetail();
		String department = user.getTeachingDepartment();
		if(department!=null){
			choseDissertation.setDepartment(department);
		}
		//根据导师username找到导师id
		ChoseProfessor choseProfessor=choseProfessorDAO.findChoseProfessorByPrimaryKey(professorId);
		//ChoseProfessor choseProfessor=choseDissertationService.findChoseProfessorByProfessorUsername(user.getUsername(),choseDissertation.getChoseProfessor().getChoseTheme().getId());
		//设置导师在所属大纲下的立题数量加1
		Integer exceptNumber=choseProfessor.getExpectNumber();
		if(exceptNumber==null||"".equals(exceptNumber)){
			choseProfessor.setExpectNumber(1);
		}
		else{
			choseProfessor.setExpectNumber(exceptNumber+1);
		}
		choseProfessorDAO.store(choseProfessor);
		//当前大纲的立题数量加1
		ChoseTheme choseTheme =choseProfessor.getChoseTheme();
		//ChoseTheme choseTheme = choseThemeDAO.findChoseThemeByPrimaryKey(themeId);
		//获得大纲下的立题数量
		Integer studentNumber = choseTheme.getStudentNumber();
		if(studentNumber==null||studentNumber==0){
			choseTheme.setStudentNumber(1);
		}
		else{
			choseTheme.setStudentNumber(studentNumber+1);
		}
		//保存立题数量
		choseThemeDAO.store(choseTheme);
		choseDissertation.setChoseProfessor(choseProfessor);
		//choseDissertation.setFinishTime(shareService.toCalendar(finishTimeStr));//
		choseDissertationDAO.store(choseDissertation);
		return "redirect:dissertationList?currpage=1&professorId="+professorId;
	}
	/****************************************************************************
	 * 功能：查询当前大纲下剩余可选立题列表-管理员
	 * 作者：赵晶
	 * 时间：2017-01-21
	 ****************************************************************************/
	@RequestMapping("/havePositionDissertationList")
	public @ResponseBody
    String havePositionDissertationList(String tittle, String realUsername, Integer currpage, Integer themeId, Integer attendanceTime){
		int pageSize=10;
		/*User user=new User();
		user.setCname(cname);
		user.setUsername(username);*/
		 //List<Object[]> lists =choseProfessorService.havePositionProfessorList(user,choseThemeId,currpage,pageSize);
		List<Object[]> lists =choseDissertationService.havePositionDissertationList(tittle,themeId,currpage,pageSize);
		 //分页开始
		// int totalRecords=choseProfessorService.findhavePositionProfessorListCount(tittle,choseThemeId);
		int totalRecords=choseDissertationService.havePositionDissertationListCount(tittle,themeId);
		 Map<String,Integer> pageModel =shareService.getPage(currpage,pageSize,totalRecords);
		 String s="";
		 for(Object[] obj:lists){
		    	s+="<tr>"+
		    	    	"<td>"+obj[3]+"</td>"+
		    			"<td>"+obj[4]+"</td>"+
		    			/*"<td>"+obj[3]+"</td>"+
		    			"<td>"+obj[4]+"</td>"+*/
		    			"<td><a href='addDissertationForRestStudent?realUsername="+realUsername+"&dissertationId="+obj[1]+"&professorId="+obj[2]+"&attendanceTime="+attendanceTime+"&themeId="+themeId+"'>添加1</a></td>"+
		    			"</tr>";
		 }
		 s+="<tr><td colspan='5'>"+
		    	    "<a href='javascript:void(0)' onclick='firstPage(1,"+realUsername+");'>"+"首页"+"</a>&nbsp;"+
		    	    "<a href='javascript:void(0)' onclick='previousPage("+currpage+","+realUsername+");'>"+"上一页"+"</a>&nbsp;"+
		    	    "<a href='javascript:void(0)' onclick='nextPage("+currpage+","+pageModel.get("totalPage")+","+realUsername+");'>"+"下一页"+"</a>&nbsp;"+
		    	    "<a href='javascript:void(0)' onclick='lastPage("+pageModel.get("totalPage")+","+realUsername+");'>"+"末页"+"</a>&nbsp;"+
		    	    "当前第"+currpage+"页&nbsp; 共"+pageModel.get("totalPage")+"页  "+totalRecords+"条记录"+
		    	    		"</td></tr>";
		 return shareService.htmlEncode(s);
	
     }
	/****************************************************************************
	 * 功能：没有论题的学生列表-导师
	 * 作者：赵晶
	 * 时间：2017-01-21
	 ****************************************************************************/
	@RequestMapping("/noDissertationStudentList")
	public ModelAndView noDissertationStudentList(@ModelAttribute("selected_academy") String acno, Integer attendanceTime, Integer currpage, Integer themeId){
		ModelAndView mav=new ModelAndView();
		//分页
		int pageSize=10;
		int totalRecords =choseDissertationService.noDissertationStudentListCount(attendanceTime,currpage,pageSize);
		//int totalRecords = choseProfessorService.noProfessorStudentListCount(attendanceTime,currpage,pageSize);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		//List<User> users=choseProfessorService.noProfessorStudentList(attendanceTime,currpage,pageSize);
		List<User> users=choseDissertationService.noDissertationStudentList(attendanceTime,currpage,pageSize);
		mav.addObject("users", users);
		mav.addObject("themeId", themeId);
		mav.addObject("attendanceTime", attendanceTime);
		mav.setViewName("chose/chose_dissertation/NoProfessorStudentList.jsp");
		return mav;
	}
	
	/****************************************************************************
	 * 功能：删除导师-管理员
	 * 作者：赵晶
	 * 时间：2017-01-28
	 ****************************************************************************/
	@RequestMapping("/deleteChoseProfessorForAdmin")
	public String deleteChoseProfessorForAdmin(@ModelAttribute("selected_academy") String acno, Integer professorId){
		ChoseProfessor choseProfessor = choseProfessorDAO.findChoseProfessorByPrimaryKey(professorId);
		Set<ChoseDissertation> choseDissertations = choseProfessor.getChoseDissertations();
		if(choseDissertations!=null&&choseDissertations.size()!=0){
			Iterator<ChoseDissertation> it = choseDissertations.iterator();
			while(it.hasNext()){
				choseDissertationDAO.remove(it.next());
				it.remove();
			}
		}
		//删除导师
		choseProfessorDAO.remove(choseProfessor);
		ChoseTheme choseTheme = choseProfessor.getChoseTheme();
		choseTheme.setTeacherNumber(choseTheme.getTeacherNumber()-1);
		return "redirect:editChoseTheme?currpage=1&themeId="+choseTheme.getId();
	}
	/****************************************************************************
	 * 功能：添加立题功能(补选)-管理员
	 * 作者：赵晶
	 * 时间：2018-1-22
	 ****************************************************************************/
	@RequestMapping("/addDissertationForRestStudent")
	public ModelAndView addDissertationForRestStudent(@ModelAttribute("selected_academy") String acno, String realUsername, Integer dissertationId, Integer professorId, Integer attendanceTime, Integer themeId){
		ModelAndView mav=new ModelAndView();
		//导师的最终学生数量加1
		ChoseProfessor choseProfessor = choseProfessorDAO.findChoseProfessorByPrimaryKey(professorId);
		Integer finalNumber;
		if(choseProfessor!=null){
			finalNumber=choseProfessor.getFinalNumber();//获得导师的最终学生
			if(finalNumber==null||finalNumber==0){
				choseProfessor.setFinalNumber(1);
			}
			else{
				choseProfessor.setFinalNumber(finalNumber+1);
			}
		}
		//添加学生的所属论文
		User student = userDAO.findUserByPrimaryKey(realUsername);
		ChoseDissertation choseDissertation = choseDissertationDAO.findChoseDissertationByPrimaryKey(dissertationId);
		student.getChoseUser().setChoseDissertation(choseDissertation);
		userDAO.store(student);
		//设置论文所属的学生
		choseDissertation.setStudent(realUsername);
		choseDissertationDAO.store(choseDissertation);
		mav.setViewName("redirect:noDissertationStudentList?currpage=1&attendanceTime="+attendanceTime+"&themeId="+themeId);
		return mav;
	}
	/****************************************************************************
	 * 功能：完成保存互选大纲-管理员
	 * 作者：赵晶
	 * 时间：2018-01-17
	 ****************************************************************************/
	@RequestMapping("/finalSaveChoseTheme")
	public String deleteChoseProfessor(HttpServletRequest request){
		HttpSession session = request.getSession();
		//从sesson中获取choseTheme
		Object choseThemeObj = session.getAttribute("choseTheme");
		//从session中获得choseProfessorList
		Object choseProfessorListObj = session.getAttribute("choseProfessorList");
		ChoseTheme choseTheme=(ChoseTheme)choseThemeObj;
		choseTheme.setState(0);//社长大纲的状态为未发布
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
		session.removeAttribute("choseTheme");
		session.removeAttribute("choseProfessorList");
		return "redirect:/choseDissertation/ChoseDissertationThemeList?currpage=1";
	}
	//----------------------------导师操作开始------------------------------------
	
	/****************************************************************************
	 * 功能：找到属于导师的所有的大纲-导师
	 * 作者：赵晶
	 * 时间：2018.1.17
	 ****************************************************************************/
	@RequestMapping("/choseThemeListForProfessor")
	public ModelAndView ChoseThemeListForProfessor(@ModelAttribute("selected_academy") String acno, Integer currpage, @ModelAttribute ChoseTheme choseTheme){
		ModelAndView mav=new ModelAndView();
		//获得当前登陆人的信息
		User user = shareService.getUserDetail();
		//用于所有存放论文大纲
		//List<ChoseTheme> choseThemeListAll=new ArrayList<ChoseTheme>();
		//用于存放当前页论文大纲
		//List<ChoseTheme> choseThemeList=new ArrayList<ChoseTheme>();
		//找到当前导师下的所有毕业论文互选大纲
		List<ChoseProfessor> choseProfessors=choseDissertationService.findchoseProfessorList(user.getUsername());
		/*if(choseProfessors!=null&&choseProfessors.size()!=0){
			//Integer[] themeIds=new Integer[choseProfessors.size()];
			for(int i=0;i<choseProfessors.size();i++){
				if(choseProfessors.get(i).getType()==2){
					//choseThemeListAll.add(choseProfessors.get(i).getChoseTheme());
				}
			}
		}*/
		List<ChoseProfessor> choseProfessorList=new ArrayList<ChoseProfessor>();
		//分页
		int pageSize=10;
		//int totalRecords=choseThemeListAll.size();
		int totalRecords=choseProfessors.size();
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
		int totalPage=pageModel.get("totalPage");
		if(choseProfessors!=null && choseProfessors.size()!=0){
			if(currpage==totalPage){
				choseProfessorList=choseProfessors.subList((currpage-1)*pageSize, totalRecords);
				//choseThemeList=choseThemeListAll.subList((currpage-1)*pageSize, totalRecords);
			}
			else{
				choseProfessorList=choseProfessors.subList((currpage-1)*pageSize, pageSize);
				//choseThemeList=choseThemeListAll.subList((currpage-1)*pageSize, pageSize);
			}
			choseProfessorList.get(0).getChoseTheme();
		}
		
		
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		//mav.addObject("choseThemeList", choseThemeList);
		mav.addObject("choseProfessorList", choseProfessorList);
		mav.setViewName("chose/chose_dissertation/ChoseThemeListForProfessor.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：找到大纲下的立题列表-导师
	 * 作者：赵晶
	 * 时间：2018.1.22
	 ****************************************************************************/
	@RequestMapping("/findDissertationList")
	public ModelAndView findDissertationList(@ModelAttribute("selected_academy") String acno, Integer currpage, Integer id){
		ModelAndView mav=new ModelAndView();
		int pageSize=10;
		/*ChoseProfessor choseProfessor = choseProfessorDAO.findChoseProfessorByPrimaryKey(id);
		Set<ChoseDissertation> choseDissertationList = choseProfessor.getChoseDissertations();*/
		List<ChoseDissertation> choseDissertationList =choseDissertationService.findChoseDissertationByProfessorId(id,currpage,pageSize);
		int totalRecords=choseDissertationService.findChoseDissertationByProfessorIdCount(id);
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
		mav.addObject("pageModel", pageModel);
		mav.addObject("choseDissertationList", choseDissertationList);
		mav.addObject("professorId", id);
		mav.setViewName("chose/chose_dissertation/DissertationList.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：立题-删除立题
	 * 作者：邵志峰
	 * 时间：2018.5.17
	 ****************************************************************************/
	@RequestMapping("/deleteDissertation")
	public ModelAndView deleteDissertation(Integer dissertationId, Integer id){
		ModelAndView mav=new ModelAndView();
		//找到所有的学部
		ChoseDissertation choseDissertation =choseDissertationDAO.findChoseDissertationById(dissertationId);
		choseDissertationDAO.remove(choseDissertation);
		mav.setViewName("redirect:findDissertationList?currpage=1&id="+id);
		return mav;
	}
	/****************************************************************************
	 * 功能：立题-导师
	 * 作者：赵晶
	 * 时间：2018.1.17
	 ****************************************************************************/
	@RequestMapping("/addDissertation")
	public ModelAndView addDissertation(@ModelAttribute("selected_academy") String acno, @ModelAttribute ChoseDissertation choseDissertation, Integer professorId){
		ModelAndView mav=new ModelAndView();
		//找到所有的学部
		List<ChoseDissertationDirection> directionList =choseDissertationService.findAllDirection();
		mav.addObject("directionList", directionList);
		mav.addObject("professorId", professorId);
		mav.setViewName("chose/chose_dissertation/AddChoseDissertation.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：立题-导师
	 * 作者：赵晶
	 * 时间：2018.1.17
	 ****************************************************************************/
	@RequestMapping("/saveDissertation")
	public String saveDissertation(@ModelAttribute("selected_academy") String acno, @ModelAttribute ChoseDissertation choseDissertation, HttpServletRequest request, Integer professorId){
		//获得完成时间
		//String finishTimeStr=request.getParameter("finishTime");
		//找到导师所属的学部
		User user = shareService.getUserDetail();
		String department = user.getTeachingDepartment();
		if(department!=null){
			choseDissertation.setDepartment(department);
		}
		//根据导师username找到导师id
		ChoseProfessor choseProfessor=choseProfessorDAO.findChoseProfessorByPrimaryKey(professorId);
		//ChoseProfessor choseProfessor=choseDissertationService.findChoseProfessorByProfessorUsername(user.getUsername(),choseDissertation.getChoseProfessor().getChoseTheme().getId());
		//设置导师在所属大纲下的立题数量加1
		Integer exceptNumber=choseProfessor.getExpectNumber();
		if(exceptNumber==null||"".equals(exceptNumber)){
			choseProfessor.setExpectNumber(1);
		}
		else{
			choseProfessor.setExpectNumber(exceptNumber+1);
		}
		choseProfessorDAO.store(choseProfessor);
		//当前大纲的立题数量加1
		ChoseTheme choseTheme =choseProfessor.getChoseTheme();
		//ChoseTheme choseTheme = choseThemeDAO.findChoseThemeByPrimaryKey(themeId);
		//获得大纲下的立题数量
		Integer studentNumber = choseTheme.getStudentNumber();
		if(studentNumber==null||studentNumber==0){
			choseTheme.setStudentNumber(1);
		}
		else{
			choseTheme.setStudentNumber(studentNumber+1);
		}
		//保存立题数量
		choseThemeDAO.store(choseTheme);
		choseDissertation.setChoseProfessor(choseProfessor);
		//choseDissertation.setFinishTime(shareService.toCalendar(finishTimeStr));//
		choseDissertationDAO.store(choseDissertation);
		return "redirect:findDissertationList?currpage=1&id="+professorId ;
	}
	/****************************************************************************
	 * 功能：导师审核的批次列表-导师
	 * 作者：赵晶
	 * 时间：2018-01-19
	 ****************************************************************************/
	@RequestMapping("/choseProfessorBatchListForProfessor")
	public ModelAndView ChoseProfessorBatchListForProfessor(@ModelAttribute("selected_academy") String acno, Integer currpage, Integer themeId){
		int pageSize=10;
		ModelAndView mav=new ModelAndView();
		//根据当前登陆人找到所有的大纲
		User user = shareService.getUserDetail();
		int totalRecords = choseDissertationService.choseProfessorBatchListForProfessorCount(themeId,user.getUsername());
		List<Object[]> lists=choseDissertationService.choseProfessorBatchListForProfessor(themeId,user.getUsername(),currpage,pageSize);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("lists", lists);
		mav.addObject("themeId", themeId);
		mav.setViewName("chose/chose_dissertation/ChoseProfessorBatchListForProfessor.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：查询每个的批审核通过的学生列表-导师
	 * 作者：赵晶
	 * 时间：2017-01-22
	 ****************************************************************************/
	@RequestMapping("/studentList")
	public ModelAndView studentList(@ModelAttribute("selected_academy") String acno, Integer batchId, Integer currpage, Integer themeId){
		ModelAndView mav=new ModelAndView();
		User user = shareService.getUserDetail();
		int pageSize=10;
		List<ChoseDissertationRecord> records=choseDissertationService.studentList(user.getUsername(),batchId,pageSize,currpage);
		int totalRecords= choseDissertationService.studentListCount(user.getUsername(),batchId);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("records", records);
		mav.addObject("batchId", batchId);
		mav.addObject("themeId", themeId);
		mav.setViewName("chose/chose_dissertation/BatchAduitPassStudentList.jsp");
		return mav;
	}	
	/****************************************************************************
	 * 功能：导师审核的列表-导师
	 * 作者：赵晶
	 * 时间：2018-01-19
	 ****************************************************************************/
	@RequestMapping("/disserationListForProfessor")
	public ModelAndView disserationListForProfessor(@ModelAttribute("selected_academy") String acno, Integer currpage, Integer batchId, Integer themeId){
		ModelAndView mav=new ModelAndView();
		//获得登陆人信息
		User user = shareService.getUserDetail();
		//分页
		int pageSize=10;
		List<Object[]> list=choseDissertationService.findAduitDissertationList(currpage,pageSize,batchId,user.getUsername());
		int totalRecords=choseDissertationService.findAduitDissertationListCount(batchId,user.getUsername());
		
		//List<ChoseDissertation> choseDissertations=choseDissertationService.findDissertationListByProUsername(user.getUsername(),themeId,currpage,pageSize);
	    //int totalRecords=choseDissertationService.findDissertationListByProUsernameCount(user.getUsername(),batchId);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("list", list);
		mav.addObject("batchId", batchId);
		mav.addObject("themeId", themeId);
		mav.setViewName("chose/chose_dissertation/ChoseDissertationListForProfessor.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：导师审核的的学生列表-导师
	 * 作者：赵晶
	 * 时间：2018-01-21
	 ****************************************************************************/
	@RequestMapping("/professorAduitListForCD")
	public ModelAndView professorAduitListForCD(@ModelAttribute("selected_academy") String acno, Integer currpage, Integer batchId, Integer dissertationId, Integer themeId){
		ModelAndView mav=new ModelAndView();
		User user = shareService.getUserDetail();
		//分页
		int pageSize=10;
		List<ChoseDissertationRecord> choseDissertationRecordList=choseDissertationService.findAduitStudentList(currpage,pageSize,batchId,user.getUsername(),dissertationId);
		int totalRecords=choseDissertationService.findAduitStudentListCount(batchId,user.getUsername(),dissertationId);
		mav.addObject("choseDissertationRecordList", choseDissertationRecordList);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("batchId", batchId);
		mav.addObject("themeId", themeId);
		mav.setViewName("chose/chose_dissertation/AduitStudentListForProfessor.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：保存导师审核结果-导师
	 * 作者：赵晶
	 * 时间：2017-01-04
	 ****************************************************************************/
	@RequestMapping("/saveAduitResult")
	public ModelAndView saveAduitResult(@ModelAttribute("selected_academy") String acno, Integer recordId, String username, Integer batchId){
		ModelAndView mav=new ModelAndView();
		User user = shareService.getUserDetail();
		ChoseDissertationRecord choseDissertationRecord = choseDissertationRecordDAO.findChoseDissertationRecordByPrimaryKey(recordId);
		
		Integer finalNumber = choseDissertationRecord.getChoseDissertation().getChoseProfessor().getFinalNumber();
		//设置导师最终的学生数量
		ChoseProfessor choseProfessor = choseDissertationRecord.getChoseDissertation().getChoseProfessor();
		if(finalNumber==null||finalNumber==0){
			choseProfessor.setFinalNumber(1);
		}
		else{
			choseProfessor.setFinalNumber(finalNumber+1);
		}
		choseProfessorDAO.store(choseProfessor);
		//设置论文所属的学生
		ChoseDissertation choseDissertation = choseDissertationRecord.getChoseDissertation();
		choseDissertation.setStudent(username);
		choseDissertationDAO.store(choseDissertation);
		//设置user表的
		User u = choseDissertationRecord.getUser();
		u.getChoseUser().setChoseDissertation(choseDissertation);
		choseDissertationDAO.store(choseDissertation);
		//设置记录表的
		choseDissertationRecord.setCurrAduit(2);
		choseDissertationRecord.setAduitResult(1);
		choseDissertationRecordDAO.store(choseDissertationRecord);
		//设置没被选上的学生的记录信息
		List<ChoseDissertationRecord> findNoAduitStudentList = choseDissertationService.findNoAduitStudentList(batchId,user.getUsername(),username) ;
		for(ChoseDissertationRecord  record:findNoAduitStudentList){
			record.setCurrAduit(2);
			record.setAduitResult(2);
			choseDissertationRecordDAO.store(record);
		}
		mav.setViewName("redirect:disserationListForProfessor?currpage=1&batchId="+batchId);
		return mav;
	}
	
	/****************************************************************************
	 * 功能：查看立题对应的学生信息-导师
	 * 作者：赵晶
	 * 时间：2017-01-21
	 ****************************************************************************/
	@RequestMapping("/findDissertationToStudent")
	public ModelAndView findDissertationToStudent(@ModelAttribute("selected_academy") String acno, Integer dissertationId, Integer batchId, Integer themeId){
		ModelAndView mav=new ModelAndView();
		ChoseDissertation choseDissertation = choseDissertationDAO.findChoseDissertationByPrimaryKey(dissertationId);
		String student = choseDissertation.getStudent();
		if(student!=null){
			User user = userDAO.findUserByPrimaryKey(student);
			mav.addObject("user", user);
		}
		mav.addObject("themeId", themeId);
		mav.addObject("batchId", batchId);
		mav.setViewName("chose/chose_dissertation/DissertationUderStudent.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：查看我的学生-导师
	 * 作者：赵晶
	 * 时间：2017-01-24
	 ****************************************************************************/
	@RequestMapping("/myStudentForProfessor")
	public ModelAndView myStudentForProfessor(@ModelAttribute("selected_academy") String acno, Integer currpage){
		ModelAndView mav=new ModelAndView();
		//获得登陆人信息
		User user = shareService.getUserDetail();
		int pageSize=10;
		List<ChoseDissertationRecord> choseDissertationRecords= choseDissertationService.findMyAduitPassDissertationRecords(user.getUsername(),currpage,pageSize);
		int totalRecords=choseDissertationService.findMyAduitPassDissertationRecordCount(user.getUsername());
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("choseDissertationRecords", choseDissertationRecords);
		mav.setViewName("chose/chose_dissertation/MyStudentForProfessor.jsp");
		return mav;
	}
	/***************************************************************************
	 * @Description 下载学生毕业论文--老师
	 * @return ModelAndView
	 * @author 赵晶
	 * @date 2018-1-24
	 **************************************************************************/
	@RequestMapping("/downStudentloadFile")
	    public ResponseEntity<byte[]> downStudentloadFile(String username) throws IOException {
	        //获得登陆人信息
	    	User user =userDAO.findUserByPrimaryKey(username);
	    	Integer documentId = user.getChoseUser().getDocumentId();
	    	CommonDocument document = commonDocumentDAO.findCommonDocumentByPrimaryKey(documentId);
	        File file=new File(document.getDocumentUrl());
	        HttpHeaders headers = new HttpHeaders();
	        String fileName=new String(document.getDocumentName().getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题
	        headers.setContentDispositionFormData("attachment", fileName);
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
	                                          headers, HttpStatus.OK);
	        
	}  
	//---------------------------管理员的操作--------------
	/**
	 * 论文互选结果列表--管理员
	 * @author 赵晶
	 * 2018.1.22
	 */
	@RequestMapping("/choseResultList")
	public ModelAndView ChoseResultList(HttpServletRequest request, @RequestParam int currpage, @RequestParam int themeId, @ModelAttribute ChoseTheme choseTheme, @ModelAttribute("selected_academy") String acno){
		ModelAndView mav = new ModelAndView();
		ChoseTheme theme = choseThemeDAO.findChoseThemeByPrimaryKey(themeId);
		mav.addObject("theme", theme);
		int pageSize = 10;
		int totalRecords = choseDissertationService.findChoseResultCount(choseTheme,themeId);
		List<ChoseProfessor> choseProfessors = choseDissertationService.findChoseResultList(choseTheme, currpage, pageSize,themeId);
		mav.addObject("choseProfessors", choseProfessors);
		mav.addObject("choseTheme", choseTheme);
		mav.addObject("pageSize", pageSize);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.setViewName("chose/chose_dissertation/ChoseResultList.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：立题-导师
	 * 作者：赵晶
	 * 时间：2018.1.17
	 ****************************************************************************/
	@RequestMapping("/editChoseTheme")
	public ModelAndView editChoseTheme(@ModelAttribute("selected_academy") String acno, Integer themeId, Integer currpage){
		ModelAndView mav=new ModelAndView();
		int pageSize=10;
		//根据themeId找到所有的导师列表
		List<ChoseProfessor> choseProfessorList=choseDissertationService.findChoseProfessorListByThemeId(themeId,currpage,pageSize);
		int totalRecords=choseDissertationService.findChoseProfessorListByThemeIdCount(themeId);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("choseProfessorList", choseProfessorList);
		mav.addObject("themeId", themeId);
		mav.setViewName("chose/chose_dissertation/ChoseProfessorList.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：题目列表-管理员
	 * 作者：赵晶
	 * 时间：2018.1.18
	 ****************************************************************************/
	@RequestMapping("/dissertationList")
	public ModelAndView dissertationList(@ModelAttribute("selected_academy") String acno, Integer professorId, Integer currpage){
		ModelAndView mav=new ModelAndView();
		int pageSize=10;
		//根据professorId找到所有的论文列表
		List<ChoseDissertation> choseDissertationList=choseDissertationService.findChoseDissertationListByProfessorId(professorId,currpage,pageSize);
		int totalRecords=choseDissertationService.findChoseDissertationListByProfessorIdCount(professorId);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("choseDissertationList", choseDissertationList);
		mav.addObject("professorId", professorId);
		ChoseProfessor choseProfessor = choseProfessorDAO.findChoseProfessorByPrimaryKey(professorId);
		mav.addObject("themeId", choseProfessor.getChoseTheme().getId());
		mav.setViewName("chose/chose_dissertation/ChoseDissertationList.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：论文详情-管理员
	 * 作者：赵晶
	 * 时间：2018.1.18
	 ****************************************************************************/
	@RequestMapping("/dissertationInfo")
	public ModelAndView dissertationInfo(@ModelAttribute("selected_academy") String acno, Integer id){
		ModelAndView mav=new ModelAndView();
		ChoseDissertation choseDissertation = choseDissertationDAO.findChoseDissertationByPrimaryKey(id);
		mav.addObject("choseDissertation", choseDissertation);
		mav.setViewName("chose/chose_dissertation/ChoseDissertationInfo.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：编辑论文详情-管理员
	 * 作者：赵晶
	 * 时间：2018.1.18
	 ****************************************************************************/
	@RequestMapping("/editDissertationInfo")
	public ModelAndView editDissertationInfo(@ModelAttribute("selected_academy") String acno, Integer id, @ModelAttribute ChoseDissertation choseDissertation){
		ModelAndView mav=new ModelAndView();
		choseDissertation = choseDissertationDAO.findChoseDissertationByPrimaryKey(id);
		//找到所有的学部
		List<String> teachingDepartment = choseProfessorService.findAllTeachingDepartment();
		//找到所有的方向
		List<ChoseDissertationDirection> directions=choseDissertationService.findAllDirection();
		mav.addObject("teachingDepartment", teachingDepartment);
		mav.addObject("choseDissertation", choseDissertation);
		mav.addObject("directions", directions);
		mav.setViewName("chose/chose_dissertation/EditChoseDissertationInfo.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：编辑论文(保存)-管理员
	 * 作者：赵晶
	 * 时间：2018.1.18
	 ****************************************************************************/
	@RequestMapping("/savaChoseDissertation")
	public String savaChoseDissertation(@ModelAttribute("selected_academy") String acno, ChoseDissertation choseDissertation){
		ChoseDissertation dissertation = choseDissertationDAO.findChoseDissertationById(choseDissertation.getId());
		dissertation.setRemark(choseDissertation.getRemark());
		dissertation.setContent(choseDissertation.getContent());
		dissertation.setTittle(choseDissertation.getTittle());
		dissertation.setDirection(choseDissertation.getDirection());
		dissertation.setDepartment(choseDissertation.getDepartment());
		dissertation.setState(1);//修改的标志
		choseDissertationDAO.store(dissertation);
		return "redirect:dissertationList?currpage=1&professorId="+dissertation.getChoseProfessor().getId();
	}
	/****************************************************************************
	 * 功能：新建大纲第三步(创建志愿)-管理员
	 * 作者：赵晶
	 * 时间：2018.1.18
	 ****************************************************************************/
	@RequestMapping("/newChoseThemecreateBatch")
	public ModelAndView editChoseTheme(@ModelAttribute("selected_academy") String acno, @ModelAttribute ChoseTheme choseTheme){
		ModelAndView mav=new ModelAndView();
		ChoseTheme chose = choseThemeDAO.findChoseThemeByPrimaryKey(choseTheme.getId());
		mav.addObject("teacherNum", chose.getStudentNumber());
		mav.setViewName("chose/chose_dissertation/NextAddChoseTheme.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：保存大纲(保存创建的志愿)-管理员
	 * 作者：赵晶
	 * 时间：2018.1.18
	 ****************************************************************************/
	@RequestMapping("/saveChoseThemecreateBatch")
	public String saveChoseThemeFour(@ModelAttribute("selected_academy") String acno, @ModelAttribute ChoseTheme choseTheme, HttpServletRequest request){
		ChoseTheme theme = choseThemeDAO.findChoseThemeByPrimaryKey(choseTheme.getId());
		//获得互选大纲的开始和结束时间
		String startStr = request.getParameter("start");
		String endStr = request.getParameter("end");
		//获得批次的开始和结束时间数组
		String[] startTime = request.getParameterValues("startTime");
		String[] endTime = request.getParameterValues("endTime");
		List<ChoseProfessorBatch> choseProfessorBatchs=new ArrayList<ChoseProfessorBatch>();
		//Set<ChoseProfessorBatch> choseProfessorBatchSet=new HashSet<ChoseProfessorBatch>();
		//遍历，获取每个志愿的开始和结束时间
		for(int i=0; i<startTime.length;i++){
			ChoseProfessorBatch choseProfessorBatch=new ChoseProfessorBatch();
			choseProfessorBatch.setStartTime(shareService.toCalendarDetil(startTime[i]));
			choseProfessorBatch.setEndTime(shareService.toCalendarDetil(endTime[i]));
			choseProfessorBatch.setBatchNumber(i+1);
			choseProfessorBatch.setChoseTheme(theme);
			//将志愿存入list集合中
			choseProfessorBatchs.add(choseProfessorBatch);
			//choseProfessorBatchSet.add(choseProfessorBatch);
		}
		//保存互选开始和结束时间
		theme.setBatchNumber(choseTheme.getBatchNumber());
		theme.setEndTime(shareService.toCalendar(endStr));
		theme.setStartTime(shareService.toCalendar(startStr));
		//choseProfessorBatchSet
		//theme.setChoseProfessorBatchs(choseProfessorBatchs);
		//choseThemeDAO.store(theme);
		//获取session，将choseThemechoseProfessorBatchList和存入session中
		HttpSession session = request.getSession();
		session.setAttribute("choseTheme", theme);
		session.setAttribute("choseProfessorBatchList", choseProfessorBatchs);
		//跳转到其他届学生列表页面
		return "redirect:findSelectStudentByUser?currpage=1&flag=1&attendanceTime="+theme.getTheYear()+"&themeId="+theme.getId();
	}
	/****************************************************************************
	 * 功能：找到本次大纲下非本届的所有学生(没有导师的)
	 * 作者：赵晶
	 * 时间：2018-1-18
	 ****************************************************************************/
	@RequestMapping("/findSelectStudentByUser")
	public ModelAndView findSelectStudentByUser(int currpage, HttpServletRequest request, @ModelAttribute User user, Integer themeId, Integer flag){
		ModelAndView mav=new ModelAndView();
		//找到属于本届的学生列表
		List<User> list = choseProfessorService.findStudentListByAttendanceTime(Integer.parseInt(user.getAttendanceTime()));
		//找到选中非本届的所有学生
//		HttpSession session = request.getSession();
//		Object obj = session.getAttribute("studentList");
		//每页显示的记录
		List<User> studentList=new ArrayList<User>();
		//总的记录
		//		//第一次进来，需要查以前realAttendanceTime为本届的学生
//		if(flag==1){
//			//找到上次没有导师的其他批次的学生
			List<User> otherlist =choseProfessorService.findOhterStudentListByRealAttendanceTime(Integer.parseInt(user.getAttendanceTime()));
//			if(otherlist!=null&&otherlist.size()!=0){
//				if(obj!=null){
//					otherlist.addAll((List<User>)obj);
//				}
//				session.setAttribute("studentList", otherlist);
//				studentListAll.addAll(otherlist);
//			}
//		}
//		if(obj!=null){
//			//所有的学生
//			studentListAll.addAll((List<User>)obj);
//
//		}
		List<User> studentListAll = new ArrayList<User>(list);
		studentListAll.addAll(otherlist);
		//分页
		if(user.getCname()!=null&& !"".equals(user.getCname())) {
			Iterator<User> it = studentListAll.iterator();
			while (it.hasNext()) {
				if (!it.next().getCname().equals(user.getCname()))
					it.remove();
			}
		}
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
				 studentList=studentListAll.subList((currpage-1)*pageSize,pageSize);
			 }
		 }
		 mav.addObject("pageModel", pageModel);
		 mav.addObject("studentList", studentList);
		 mav.addObject("attendanceTime", user.getAttendanceTime());
		 mav.addObject("themeId", themeId);
		 mav.setViewName("chose/chose_dissertation/OtherBatchStudentList.jsp");
		return mav;
		
	}
	/*public ModelAndView findSelectStudentByUser(int currpage,HttpServletRequest request,@ModelAttribute User user,Integer themeId,String attendanceTime,Integer flag){
		ModelAndView mav=new ModelAndView();
		//找到选中非本届的所有学生
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("studentList");
		List<User> userAll=new ArrayList<User>();
		if(flag==1){
			//找到大纲中非本届的没有导师学生
			List<User> users=choseDissertationService.findNoProfessorStudentList(attendanceTime);
			if(obj!=null){
				//所有的学生
				List<User> studentListAll=(List<User>)obj;
				studentListAll.addAll(users);
				userAll.addAll(studentListAll);
			}
			else{
				userAll.addAll(users);
			}
		}
		else{
			if(obj!=null){
				List<User> studentListAll=(List<User>)obj;
				userAll.addAll(studentListAll);
			}
		}
		//存放每页显示的记录
		List<User> userList=new ArrayList<User>();
		//分页
		 int pageSize=10;
		 int totalRecords=userAll.size();
		 Map<String,Integer> pageModel =shareService.getPage(currpage,pageSize,totalRecords);
		 //获得总共的页数
		 Integer totalPage = pageModel.get("totalPage");
		 if(currpage==totalPage){
			//每页显示的学生列表 
			 userList=userAll.subList((currpage-1)*pageSize,totalRecords);
		 }
		 else{
			//每页显示的学生列表
			 if(userAll!=null&&userAll.size()!=0){
				 userList=userAll.subList((currpage-1)*pageSize,pageSize);
			 }
		 }
		 mav.addObject("pageModel", pageModel);
		 session.setAttribute("studentList", userAll);
		 mav.addObject("studentList", userAll);
		 mav.addObject("attendanceTime", user.getAttendanceTime());
		 mav.addObject("themeId", themeId);
		mav.setViewName("chose/chose_dissertation/OtherBatchStudentList.jsp");
		return mav;
		
	}*/
	/****************************************************************************
	 * 功能：查询非本届的所有学生(排除session中已经选择的非本届学生)
	 * 作者：赵晶
	 * 时间：2018-1-18
	 ****************************************************************************/
	@RequestMapping("/findOtherBatchStudentByuser")
	public @ResponseBody
    String findOtherBatchStudentByuser(Integer currpage, User user, String attendanceTime, HttpServletRequest request){
		int pageSize=10;
		String[] usernameArray=null;
//		//获得已经选择的学生列表
//		 HttpSession session = request.getSession();
//		 Object obj = session.getAttribute("studentList");
				List<User> list = choseProfessorService.findStudentListByAttendanceTime(Integer.parseInt(user.getAttendanceTime()));
		List<User> otherlist =choseProfessorService.findOhterStudentListByRealAttendanceTime(Integer.parseInt(user.getAttendanceTime()));
				List<User> studentListAll = new ArrayList<User>(list);
		studentListAll.addAll(otherlist);
		if(studentListAll !=null){
			 List<User> studentList= (List<User>) studentListAll;
			 if(studentList.size()!=0){
				 usernameArray=new String[studentList.size()];
				 for(int i=0;i<studentList.size();i++){
					 usernameArray[i]=studentList.get(i).getUsername();
				 }
			 }
		 }
		 //去掉已经选的学生
		 List<User> users =choseDissertationService.findOtherBatchStudentByuser(currpage,pageSize,user, usernameArray);
		 //分页开始
		 int totalRecords=choseDissertationService.findCountOtherBatchStudentByuser(user,usernameArray);
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
			 return shareService.htmlEncode(s);
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
//			for(String username:array){
//				  //找到每个user对象
//				  User user = userDAO.findUserByPrimaryKey(username);
//				  user.getChoseUser().setRealAttendanceTime(attendanceTime);
//				  studentList.add(user);
//			}
//			session.setAttribute("studentList", studentList);
//		}
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
		mav.setViewName("redirect:findSelectStudentByUser?currpage=1&flag=0&attendanceTime="+attendanceTime+"&themeId="+themeId);
		return mav;
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
	 * 功能：发布大纲-管理员
	 * 作者：赵晶
	 * 时间：2018-1-18
	 ****************************************************************************/
	@RequestMapping("/releaseChoseTheme")
	public String releaseChoseTheme(@ModelAttribute("selected_academy") String acno, Integer themeId, HttpServletRequest request, Integer studentNumber){
		HttpSession session = request.getSession();
//		Object studentListObj = session.getAttribute("studentList");
		Object choseProfessorBatchListObj = session.getAttribute("choseProfessorBatchList");
		Object choseThemeObj=session.getAttribute("choseTheme");
		ChoseTheme choseTheme=null;
		if(choseThemeObj!=null){
			choseTheme=(ChoseTheme)choseThemeObj;
		}
		//ChoseTheme choseTheme = choseThemeDAO.findChoseThemeByPrimaryKey(themeId);
		//查询本次大纲下attendanceTime等于所选届的学生（没有论文）数量
		/*int count=choseDissertationService.findStudentByAttendanceTimeCount(choseTheme.getTheYear());
		if(studentListObj!=null){
			List<User> users=(List<User>)studentListObj;
			count+=users.size();
		}*/
		if(choseProfessorBatchListObj!=null){
			List<ChoseProfessorBatch> ChoseProfessorBatchList=(List<ChoseProfessorBatch>)choseProfessorBatchListObj;
			for(ChoseProfessorBatch choseProfessorBatch:ChoseProfessorBatchList){
				choseProfessorBatchDAO.store(choseProfessorBatch);
			}
		}
		choseTheme.setStudentNumber(studentNumber);
		choseTheme.setState(1);//已发布
		choseThemeDAO.store(choseTheme);
		//清除存入session里的studentList
//		session.removeAttribute("studentList");
		session.removeAttribute("choseProfessorBatchList");
		session.removeAttribute("choseTheme");
		return "redirect:ChoseDissertationThemeList?currpage=1";
    }
	//************************************************************************************
	//-------------------------------------学生填报志愿开始-----------------------------------*
	//************************************************************************************
	/****************************************************************************
	 * 功能：找到所属的备选大纲列表-学生
	 * 作者：赵晶
	 * 时间：2018-1-18
	 ****************************************************************************/
	@RequestMapping("/belongChoseThemeList")
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
		int totalRecords = choseDissertationService.findBelongChoseThemeListCount(choseTheme,user);
		//根据条件找到所属的备选大纲-学生
		List<ChoseTheme> choseThemeList = choseDissertationService.BelongChoseThemeList(choseTheme,user, currpage, pageSize);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("choseThemeList", choseThemeList);
		//判断该学生是否已经填过志愿
		Integer isChoseDissertation = user.getChoseUser()!=null?user.getChoseUser().getIsChoseDissertation():null;
		mav.addObject("isChoseDissertation", isChoseDissertation);
		//分页结束
		mav.setViewName("chose/chose_dissertation/BelongChoseThemeList.jsp");
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
		mav.setViewName("chose/chose_dissertation/BatchStateForStudent.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：到学生填写论文志愿的页面-学生
	 * 作者：赵晶
	 * 时间：2018-1-19
	 * 参数：i 代表是第几志愿
	 ***************************************************************************/
	@RequestMapping("/toAddBatch")
	public ModelAndView toAddBatch(HttpServletRequest request, @RequestParam int i, Integer themeId, @ModelAttribute("selected_academy") String acno, @ModelAttribute ChoseDissertation choseDissertation, Integer currpage){
		ModelAndView mav = new ModelAndView();
		//查询当前登陆人的信息-学生
		User userInfo = shareService.getUserDetail();
		//查询所有的学部列表
		List<String> teachingDepartments=choseProfessorService.findAllTeachingDepartment();
		mav.addObject("teachingDepartments", teachingDepartments);
		//查询所有的方向列表
		List<ChoseDissertationDirection> directions = choseDissertationService.findAllDirection();
		mav.addObject("directions", directions);
		//根据大纲id找到所有的志愿
		List<ChoseProfessorBatch> batchList = choseProfessorService.belongChoseProfessorBatchList(themeId);
		//用于存放已经选的立题的id
		List<Integer> choseDissertationIds=new ArrayList<Integer>();
		HttpSession session = request.getSession();
		//获得session中已经选论文信息
		Object obj = session.getAttribute("batchInfo");
		if(obj!=null){
			List<String[]> list=(List<String[]>)obj;
			for(String[] s:list){
				choseDissertationIds.add(Integer.parseInt(s[3]));
			}
		}
		//分页开始
		int pageSize=10;
		if(batchList!=null&batchList.size()!=0){
			//判断当前的志愿属于大纲下的某志愿
			if(i<=batchList.size()){
				//找到属于这个大纲的所有导师列表
				List<ChoseProfessor> choseProfessorList = choseProfessorService.findProfessorListByChemeId(themeId);
				//
				if(choseProfessorList!=null&&choseProfessorList.size()!=0){
					List<Integer> professorIds=new ArrayList<Integer>();
					for(ChoseProfessor choseProfessor:choseProfessorList){
						professorIds.add(choseProfessor.getId());
					}
					//找到登陆人未被选的立题列表
					List<ChoseDissertation> ChoseDissertationList=choseDissertationService.findNoSelectedDissertationList(professorIds,currpage,choseDissertation,choseDissertationIds,pageSize);
					//int totalRecords= choseProfessorService.findNoSelectedProfessorListQueryCount(themeId,choseProfessor,professorIds);
					int totalRecords= choseDissertationService.findNoSelectedProfessorListQueryCount(professorIds,choseDissertation,choseDissertationIds);
					mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
					//设置批次的id
					mav.addObject("batchId",batchList.get(i-1).getId());
					mav.addObject("ChoseDissertationList", ChoseDissertationList);
					mav.addObject("themeId", themeId);
					//第几志愿
					mav.addObject("i",i);
					mav.setViewName("chose/chose_dissertation/AddBatch.jsp");
				}
				
			}
			else{
				//到批次的列表
				mav.setViewName("chose/chose_dissertation/BatchList.jsp");
			}
		}
		return mav;
	}
	/** *************************************************************************
	 * @Description 详情弹框页面-填志愿页面
	 * @return ModelAndView
	 * @author 赵晶
	 * @date 2018-1-19
	 **************************************************************************/
	@RequestMapping("/showChoseDissertation")
	public ModelAndView showChoseDissertation(@RequestParam Integer choseDissertationId){
		ModelAndView mav=new ModelAndView();
		ChoseDissertation choseDissertation = choseDissertationDAO.findChoseDissertationByPrimaryKey(choseDissertationId);
		 mav.addObject("choseDissertation", choseDissertation);
		 mav.setViewName("chose/chose_dissertation/ShowChoseDissertation.jsp");
		return mav;
	}
	/****************************************************************************
	 * 功能：保存学生填写的志愿到session中-学生
	 * 作者：赵晶
	 * 时间：2017-1-19
	 ****************************************************************************/
	@RequestMapping("/saveBatchInfoToSession")
	public String saveBatchInfoToSession(HttpServletRequest request, String themeId, String batchId, String i, String choseDissertationId){
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("batchInfo");
		if(obj!=null){
			List<String[]> batchInfo=(List<String[]>)obj;//保存志愿信息
			String[] b=new String[5];
			b[0]=themeId;//大纲的id
			b[1]=batchId;//批次的id
			b[2]=i;//第几志愿
			b[3]=choseDissertationId;//论文id
			ChoseDissertation choseDissertaion=choseDissertationDAO.findChoseDissertationByPrimaryKey(Integer.parseInt(choseDissertationId));
			b[4]=choseDissertaion.getTittle();
			//b[5]=professor.getUser().getCname();
			batchInfo.add(b);
			session.setAttribute("batchInfo", batchInfo);
		}
		else{
			List<String[]> batchInfo=new ArrayList<String[]>();
			String[] b=new String[5];
			b[0]=themeId;
			b[1]=batchId;
			b[2]=i;
			b[3]=choseDissertationId;
			ChoseDissertation choseDissertaion=choseDissertationDAO.findChoseDissertationByPrimaryKey(Integer.parseInt(choseDissertationId));
			b[4]=choseDissertaion.getTittle();
			/*ChoseProfessor professor=choseProfessorDAO.findChoseProfessorByPrimaryKey(Integer.parseInt(choseProfessorId));
			b[4]=professor.getUser().getUsername();
			b[5]=professor.getUser().getCname();*/
			batchInfo.add(b);
			session.setAttribute("batchInfo", batchInfo);
		}
		
		return "redirect:toAddBatch?i="+(Integer.parseInt(i)+1)+"&themeId="+themeId+"&currpage=1";
	}
	/****************************************************************************
	 * 功能：保存志愿-学生
	 * 作者：赵晶
	 * 时间：2017-1-19
	 ****************************************************************************/
	@RequestMapping("/saveBatch")
	public String saveBatch(HttpServletRequest request, @ModelAttribute("selected_academy") String acno){
		//获得学生填写的志愿信息
		HttpSession session=request.getSession();
		Object obj = session.getAttribute("batchInfo");
        User user = shareService.getUserDetail();
		if(obj!=null){
			List<String[]> list=(List<String[]>)obj;
			for(String[] b:list){
				//大纲的id
				Integer themeId=Integer.parseInt(b[0]);
				//批次的id 
				Integer batchId=Integer.valueOf(b[1]);
				//第几志愿
				Integer i=Integer.parseInt(b[2]);
				//立题的id
				Integer choseDissertationId=Integer.parseInt(b[3]);
				ChoseDissertationRecord choseDissertationRecord=new ChoseDissertationRecord();
				//根据立题的id获得立题信息
				ChoseDissertation dissertation = choseDissertationDAO.findChoseDissertationByPrimaryKey(choseDissertationId);
				ChoseProfessor choseProfessor = dissertation.getChoseProfessor();
				if(choseProfessor!=null){
					choseDissertationRecord.setTUsername(choseProfessor.getUser().getUsername());
				}
				//根据导师id获取导师
				/*ChoseProfessorRecord choseProfessorRecord=new ChoseProfessorRecord();
				ChoseProfessor professor=choseProfessorDAO.findChoseProfessorByPrimaryKey(choseProfessorId);
				choseProfessorRecord.setTUsername(professor.getUser().getUsername());*/
				
				ChoseDissertation choseDissertation = choseDissertationDAO.findChoseDissertationByPrimaryKey(choseDissertationId);
				//设置论题
				choseDissertationRecord.setChoseDissertation(choseDissertation);
				//设置填写志愿的学生信息
				choseDissertationRecord.setUser(user);
				//设置批次信息
				ChoseProfessorBatch c=new ChoseProfessorBatch();
				c.setId(batchId);
				choseDissertationRecord.setChoseProfessorBatch(c);
				//设置审核状态
				choseDissertationRecord.setCurrAduit(0);//未审核
				//设置是否提交论文志愿的标记
				//设置是否提交论文志愿的标记
				choseDissertationRecordDAO.store(choseDissertationRecord);
			}
            ChoseUser cu;
            if(user.getChoseUser()==null){
                cu = new ChoseUser();
                cu.setUser(user);
            }else{
                cu = user.getChoseUser();
            }
            cu.setIsChoseDissertation(1);//提交
            choseUserDAO.store(cu);
			
		}
		return "redirect:/choseDissertation/belongChoseThemeList?currpage=1";
	}
	/****************************************************************************
	 * 功能：我都毕业-学生
	 * 作者：赵晶
	 * 时间：2017-1-19
	 ****************************************************************************/
	@RequestMapping("/myDissertationForStudent")
	public ModelAndView myDissertationForStudent(HttpServletRequest request, @ModelAttribute("selected_academy") String acno){
		ModelAndView mav=new ModelAndView();
		//获得登陆人信息
		User user = shareService.getUserDetail();
		mav.addObject("user", user);
		mav.setViewName("chose/chose_dissertation/MyDissertationForStudent.jsp");
		return mav;
	}
	/***************************************************************************
	 * @Description 上传毕业论文--学生
	 * @return ModelAndView
	 * @author 赵晶
	 * @date 2018-1-23
	 **************************************************************************/
	@RequestMapping("/uploadDessistation")
	public String uploadDessistationForYear(@RequestParam(value = "file", required = false) MultipartFile upFile, HttpServletRequest request, @RequestParam("id") String id){
		String originalFilename = upFile.getOriginalFilename();
		String fileDir = request.getSession().getServletContext().getRealPath( "/") +  "upload"+ File.separator+"choseDissertation"+File.separator+id;
		File files=new File(fileDir);
		if(!files.exists()){
			if(files.mkdirs())
				System.out.println("成功创建文件夹");
			else
				System.out.println("失败");
		}
		File file=new File(fileDir,originalFilename);
		ArrayList<String> addressAndName = new ArrayList<String>();
		addressAndName.add(fileDir+File.separator+originalFilename);
		addressAndName.add(originalFilename);
		try {
			upFile.transferTo(file);
			CommonDocument document = choseDissertationService.saveDessitationDocument(addressAndName);

			//获得登陆人信息
			User user = shareService.getUserDetail();
			user.getChoseUser().setDocumentId(document.getId());
			userDAO.store(user);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:myDissertationForStudent";
	}
	/***************************************************************************
	 * @Description 下载毕业论文--学生
	 * @return ModelAndView
	 * @author 赵晶
	 * @date 2018-1-23
	 **************************************************************************/
	@RequestMapping("/downloadFile")
	    public ResponseEntity<byte[]> download() throws IOException {
	        //获得登陆人信息
	    	User user = shareService.getUserDetail();
	    	Integer documentId = user.getChoseUser().getDocumentId();
	    	CommonDocument document = commonDocumentDAO.findCommonDocumentByPrimaryKey(documentId);
	        File file=new File(document.getDocumentUrl());
	        HttpHeaders headers = new HttpHeaders();
	        String fileName=new String(document.getDocumentName().getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题
	        headers.setContentDispositionFormData("attachment", fileName);   
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
	                                          headers, HttpStatus.OK);
	    }    
	}  
