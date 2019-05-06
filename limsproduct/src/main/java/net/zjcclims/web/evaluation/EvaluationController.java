/**
 * Description 评教模块方法
 * @author 陈乐为 2018-6-11
 */

package net.zjcclims.web.evaluation;


import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.evaluation.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller("EvaluationController")
@RequestMapping("/evaluation")

public class EvaluationController<JsonResult> {
	
	@Autowired
    private EvaluationService evaluationService;
	@Autowired
    private ShareService shareService;
	
	/**
	 * Description 评教时间设置列表
	 * @param evaluationSet
	 * @param currpage
	 * @return
	 * @author 陈乐为 2018-6-11
	 */
	@RequestMapping("/listEvaluationSet")
	public ModelAndView listEvaluationSet(@ModelAttribute EvaluationSet evaluationSet, @RequestParam int currpage) {
		ModelAndView mav = new ModelAndView();
		// 分页参数
		int pageSize = 20;
		int totalRecords = evaluationService.findEvaluationSetByQuery(evaluationSet, 1, -1).size();
		// 评教设置列表
		List<EvaluationSet> evaluationSets = evaluationService.findEvaluationSetByQuery(evaluationSet, currpage, pageSize);
		mav.addObject("evaluationSets", evaluationSets);
		// 分页参数
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("currpage", currpage);
		mav.addObject("pageSize", pageSize);
		mav.addObject("totalRecords", totalRecords);
		
		mav.setViewName("/evaluation/listEvaluationSet.jsp");
		return mav;
	}
	
	/**
	 * Description 评教设置项列表
	 * @param evaluationContent
	 * @param currpage
	 * @return
	 * @author 陈乐为 2018-6-11
	 */
	@RequestMapping("/listEvaluationContent")
	public ModelAndView listEvaluationContent(@ModelAttribute EvaluationContent evaluationContent, @RequestParam int currpage) {
		ModelAndView mav = new ModelAndView();
		// 学期传参
		int termId = 0;
		if(evaluationContent!=null && evaluationContent.getSchoolTerm()!=null && evaluationContent.getSchoolTerm().getId()!=null) {
			termId = evaluationContent.getSchoolTerm().getId();
		}else {
			evaluationContent.setSchoolTerm(shareService.getBelongsSchoolTerm(Calendar.getInstance()));
			termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		}
		mav.addObject("termId", termId);
		// 学期评教时间设置
		EvaluationSet evaluationSet = evaluationService.findEvaluationSetByTerm(termId);
		mav.addObject("evaluationSet", evaluationSet);
		// 分页参数
		int pageSize = 20;
		int totalRecords = evaluationService.findEvaluationContentByQuery(evaluationContent, 1, -1).size();
		// 评教设置列表
		List<EvaluationContent> evaluationContents = evaluationService.findEvaluationContentByQuery(evaluationContent, currpage, pageSize);
		mav.addObject("evaluationContents", evaluationContents);
		// 用于计算查询列表的总分
    	List<EvaluationContent> findAllEvaluationScore = evaluationService.findEvaluationContentByQuery(evaluationContent, 1, -1);
 		int totalcount=0;
 		for(EvaluationContent e:findAllEvaluationScore){
 			totalcount += e.getScores();
 		}
		mav.addObject("totalcount",totalcount);
		// 提交状态
		if(evaluationContents.size() > 0) {
    		mav.addObject("status",evaluationContents.get(0).getStatus());
    	}else {
    		mav.addObject("status",0);
    	}
		mav.addObject("evaluationContent", evaluationContent);
		// 学期
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerms", schoolTerms);
		// 分页参数
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("currpage", currpage);
		mav.addObject("pageSize", pageSize);
		mav.addObject("totalRecords", totalRecords);
		
		mav.setViewName("/evaluation/listEvaluationContent.jsp");
		return mav;
	}
	
	/**
	 * Description 新建
	 * @return
	 * @author 陈乐为 2018-6-11
	 */
	@RequestMapping("/newEvaluationContent")
	public ModelAndView newEvaluationContent() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("evaluationContent", new EvaluationContent());
		// 学期
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerms", schoolTerms);
		
		mav.setViewName("/evaluation/editEvaluationContent.jsp");
		return mav;
	}
	
	/**
	 * Description 编辑页面
	 * @param idKey
	 * @return
	 * @author 陈乐为 2018-6-11
	 */
	@RequestMapping("/editEvaluationContent")
	public ModelAndView editEvaluationContent(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("evaluationContent", evaluationService.findEvaluationContentByPrimaryKey(idKey));
		// 学期
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerms", schoolTerms);
		
		mav.setViewName("/evaluation/editEvaluationContent.jsp");
		return mav;
	}
	
	/**
	 * Description 保存
	 * @param evaluationContent
	 * @return
	 * @author 陈乐为 2018-6-11
	 */
	@RequestMapping("/saveEvaluationContent")
	public String saveEvaluationContent(@ModelAttribute EvaluationContent evaluationContent) {
		evaluationContent.setStatus(0);
		evaluationService.saveEvaluationContent(evaluationContent);
		
		return "redirect:/evaluation/listEvaluationContent?currpage=1";
	}
	
	/**
	 * Description 删除评教项
	 * @param idKey
	 * @return
	 * @author 陈乐为 2018-6-11
	 */
	@RequestMapping("/deleteEvaluationContent")
	public String deleteLabWorker(@RequestParam Integer idKey) {
		EvaluationContent content = evaluationService.findEvaluationContentByPrimaryKey(idKey);
		evaluationService.deleteEvaluationContent(content);
		
		return "redirect:/evaluation/listEvaluationContent?currpage=1";
	}
	
	/**
	 * Description 提交各评教项
	 * @param idKey 学期主键
	 * @return
	 * @author 陈乐为 2018-6-11
	 */
	@RequestMapping("/submitEvaluationContent")
	public String submitEvaluationContent(@RequestParam Integer idKey) {
		EvaluationContent evaluationContent = new EvaluationContent();
		evaluationContent.setSchoolTerm(evaluationService.findSchoolTermByPrimaryKey(idKey));
		List<EvaluationContent> contents = evaluationService.findEvaluationContentByQuery(evaluationContent, 1, -1);
		for(EvaluationContent content : contents) {
			content.setStatus(1);
			evaluationService.saveEvaluationContent(content);
		}
		
		return "redirect:/evaluation/newEvaluationSet?idKey="+idKey;
	}
	
	/**
	 * Descripition 评教时间设置
	 * @param idKey 学期主键
	 * @return
	 */
	@RequestMapping("/newEvaluationSet")
	public ModelAndView newEvaluationSet(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();
		SchoolTerm schoolTerm = evaluationService.findSchoolTermByPrimaryKey(idKey);
		mav.addObject("schoolTerm", schoolTerm);
		
		mav.addObject("evaluationSet",new EvaluationSet());
		
		mav.setViewName("/evaluation/editEvaluationSet.jsp");
		return mav;
	}
	
	/**
	 * Description 保存评教时间设置
	 * @param evaluationSet
	 * @return
	 * @author 陈乐为 2018-6-13
	 * @throws ParseException 
	 */
	@RequestMapping("/saveEvaluationSet")
	public String saveEvaluationSet(@ModelAttribute EvaluationSet evaluationSet, HttpServletRequest request) throws ParseException {
		String startT = request.getParameter("startT");
		String endT = request.getParameter("endT");
		//字符串转日期
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date start = sdf.parse(startT);
		Date end = sdf.parse(endT);
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		startDate.setTime(start);
		endDate.setTime(end);
		
		evaluationSet.setStartTime(startDate);
		evaluationSet.setEndTime(endDate);
		
		evaluationSet.setStatus(0);// 保存
		evaluationService.saveEvaluationSet(evaluationSet);
		
		return "redirect:/evaluation/listEvaluationContent?currpage=1";
	}

	/**
	 * Description 编辑评教时间设置
	 * @param idKey
	 * @return
	 * @author 陈乐为 2018-6-13
	 */
	@RequestMapping("/editEvaluationSet")
	public ModelAndView editEvaluationSet(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();
		// 修改对象
		EvaluationSet evaluationSet = evaluationService.findEvaluationSetByPrimaryKey(idKey);
		mav.addObject("evaluationSet", evaluationSet);
		// 学期
		SchoolTerm schoolTerm = evaluationService.findSchoolTermByPrimaryKey(evaluationSet.getTermId());
		mav.addObject("schoolTerm", schoolTerm);
		
		mav.setViewName("/evaluation/editEvaluationSet.jsp");
		return mav;
	}
	
	/**
	 * Description 提交评教时间设置
	 * @param idKey 对象主键
	 * @return
	 * @author 陈乐为 2018-6-13
	 */
	@RequestMapping("/submitEvaluationSet")
	public String submitEvaluationSet(@RequestParam Integer idKey) {
		EvaluationSet evaluationSet = evaluationService.findEvaluationSetByPrimaryKey(idKey);
		evaluationSet.setStatus(1);
		evaluationService.saveEvaluationSet(evaluationSet);
		
		return "redirect:/evaluation/listEvaluationContent?currpage=1";
	}
	
	/**
	 * Description 评教课程、教师列表
	 * @param evaluationSource
	 * @param currpage
	 * @param type
	 * @return
	 * @author 陈乐为 2018-6-12
	 */
	@RequestMapping("/listStudentEvaluation")
	public ModelAndView listStudentEvaluation(@ModelAttribute EvaluationSource evaluationSource, @RequestParam int currpage, int type) {
		ModelAndView mav = new ModelAndView();
		SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		evaluationSource.setStatus(type);
		evaluationSource.setStudent(shareService.getUserDetail().getUsername());
		if(evaluationSource.getTermId()==null) {
			evaluationSource.setTermId(schoolTerm.getId());
		}
		mav.addObject("type", type);
		// 对应学期评教时间
		EvaluationSet evaluationSet = evaluationService.findEvaluationSetByTerm(evaluationSource.getTermId());
		mav.addObject("evaluationSet", evaluationSet);
		// 判断当前是否为可评教时间
		int oks = 0;
		if(evaluationSet!=null && evaluationSet.getStatus()==1) {// 时间设置已提交
			if(Calendar.getInstance().before(evaluationSet.getEndTime()) && Calendar.getInstance().after(evaluationSet.getStartTime())) {
				oks = 1;// 可以评教
			}else if(Calendar.getInstance().after(evaluationSet.getEndTime())) {
				oks = 2;// 评教时间已过
			}else if(Calendar.getInstance().before(evaluationSet.getStartTime())) {
				oks = 3;// 评教未开始
			}
		}
		mav.addObject("oks", oks);
		
		int pageSize = 20;
		int totalRecords = evaluationService.findEvaluationSourceByQuery(evaluationSource, 1, -1).size();
		
		List<EvaluationSource> evaluationSources = evaluationService.findEvaluationSourceByQuery(evaluationSource, currpage, pageSize);
		mav.addObject("evaluationSources", evaluationSources);
		mav.addObject("evaluationSource", evaluationSource);
		if(evaluationSource!=null && evaluationSource.getTermId()!=null) {
			mav.addObject("termId", evaluationSource.getTermId());
		}else {
			mav.addObject("termId", schoolTerm.getId());
		}
		// 学期
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerms", schoolTerms);
		// 是否设置评教内容
		EvaluationContent evaluationContent = new EvaluationContent();
		evaluationContent.setSchoolTerm(schoolTerm);
		// 获取学期对应评教内容
		int contents = evaluationService.findEvaluationContentSubmit(evaluationContent).size();
		mav.addObject("contents", contents);
		// 分页参数
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("currpage", currpage);
		mav.addObject("pageSize", pageSize);
		mav.addObject("totalRecords", totalRecords);
		
		mav.setViewName("/evaluation/listEvaluationSource.jsp");
		return mav;
	}
	
	/**
	 * Description 
	 * @param idKey
	 * @return
	 */
	@RequestMapping("/studentEvaluation")
	public ModelAndView studentEvaluation(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();
		// 当前学期
		SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		EvaluationContent evaluationContent = new EvaluationContent();
		evaluationContent.setSchoolTerm(schoolTerm);
		// 获取学期对应评教内容
		List<EvaluationContent> evaluationContents = evaluationService.findEvaluationContentSubmit(evaluationContent);
		mav.addObject("evaluationContents", evaluationContents);
		String ids="";
		for(EvaluationContent e : evaluationContents){
			ids += e.getId()+"*";
		}
		ids=ids.substring(0,ids.length()-1);
		mav.addObject("ids",ids);
		// 根据主键查找评教主体
		EvaluationSource evaluationSource = evaluationService.findEvaluationSourceByPrimaryKey(idKey);
		mav.addObject("evaluationSource", evaluationSource);
		
		mav.setViewName("/evaluation/studentEvaluation.jsp");
		return mav;
	}
	
	/**
	 * Description 保存评教结果
	 * @param infos
	 * @param idKey
	 * @return
	 * @author 陈乐为 2018-6-12
	 */
	@RequestMapping("/saveStudentEvaluation")
	public String saveStudentEvaluation(@RequestParam String infos, Integer idKey) {
		EvaluationSource evaluationSource = evaluationService.findEvaluationSourceByPrimaryKey(idKey);
		// 判断包含几组数据
		if(infos.contains("~")){
			String[] selValue = infos.split("~");
			for(int i=0;i<selValue.length;i++) {
				String idAndValue = selValue[i];
				//切割id和分数
				String[] split = idAndValue.split("-");
				//获得id
				Integer id = Integer.parseInt(split[0]);
				//获得分数
				Integer value =Integer.parseInt(split[1]);
				EvaluationContent evaluationContent = evaluationService.findEvaluationContentByPrimaryKey(id);
				EvaluationResult evaluationResult = new EvaluationResult();
				evaluationResult.setEvaluationContent(evaluationContent);
				evaluationResult.setScore(value);
				// 相同属性值
				evaluationResult.setTeacherNo(evaluationSource.getUser().getUsername());
				evaluationResult.setTeacherName(evaluationSource.getUser().getCname());
				evaluationResult.setSchoolCourseInfo(evaluationSource.getSchoolCourseInfo());
				evaluationResult.setTermId(evaluationSource.getTermId());
				evaluationResult.setStudentNo(evaluationSource.getStudent());
				evaluationResult.setStudentName(shareService.findUserByUsername(evaluationSource.getStudent()).getCname());
				evaluationResult.setCreateTime(Calendar.getInstance());
				evaluationService.saveEvaluationResult(evaluationResult);
			}
		}
		// 更新源数据评教状态为已评
		evaluationSource.setStatus(1);
		evaluationService.saveEvaluationSource(evaluationSource);
		return "redirect:/evaluation/listStudentEvaluation?currpage=1&type=0";
	}
	
	/**
	 * Description 学生查看自己的已评记录
	 * @param idKey
	 * @return
	 * @author 陈乐为 2018-6-12
	 */
	@RequestMapping("/viewStudentEvaluation")
	public ModelAndView viewStudentEvaluation(@RequestParam Integer idKey) {
		ModelAndView mav = new ModelAndView();
		EvaluationSource evaluationSource = evaluationService.findEvaluationSourceByPrimaryKey(idKey);
		mav.addObject("evaluationSource", evaluationSource);
		// 结果集合
		List<EvaluationResult> evaluationResults = evaluationService.findEvaluationResultByQuery(evaluationSource);
		mav.addObject("evaluationResults", evaluationResults);
		
		mav.setViewName("/evaluation/viewStudentEvaluation.jsp");
		return mav;
	}
	
	/**
	 * Description 评教结果
	 * @param evaluationResult
	 * @param currpage
	 * @return
	 * @author 陈乐为 2018-6-12
	 */
	@RequestMapping("/listEvaluationResult")
	public ModelAndView listEvaluationResult(@ModelAttribute EvaluationResult evaluationResult, @RequestParam int currpage) {
		ModelAndView mav = new ModelAndView();
		int pageSize = 20;
		// 学期
		List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerms", schoolTerms);
		// 列数
		int columns = 0;
		// 当前学期
		SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		EvaluationContent evaluationContent = new EvaluationContent();
		if(evaluationResult.getTermId()==null) {
			evaluationResult.setTermId(schoolTerm.getId());
			evaluationContent.setSchoolTerm(schoolTerm);
		}else {
			evaluationContent.setSchoolTerm(evaluationService.findSchoolTermByPrimaryKey(evaluationResult.getTermId()));
		}
		// 评教内容
		List<EvaluationContent> contentList = evaluationService.findEvaluationContentSubmit(evaluationContent);
		// 获取学期对应评教内容数量
		int contents = evaluationService.findEvaluationContentSubmit(evaluationContent).size();
		columns = contents+4;//各评教项+4列（课程、教师、参评人数、总分）
		// 结果列表
		List<Object[]> list = evaluationService.findEvaluationResultObjects(evaluationResult, (currpage-1)*pageSize, pageSize);
		// 单项得分值
		List<Object[]> conStr = evaluationService.findEvaluationContentScores(evaluationResult);
		
		List<Object[]> objects = new ArrayList<Object[]>();
		// 第一行
		Object[] str1 = new Object[columns];
		str1[0] = "课程";
		str1[1] = "上课教师";
		str1[2] = "参评人数";
		int col1 = 0;
		for(EvaluationContent content : contentList) {
			str1[col1+3] = content.getOptions();
			col1++;
		}
		str1[columns-1] = "总分";
		objects.add(str1);
		// 行数
		for(Object[] obj : list) {//遍历集合
			Object[] str = new Object[columns];
			str[0] = obj[1];
			str[1] = obj[3];
			str[2] = obj[4];
			int col = 0;//列
			BigDecimal totalScore = new BigDecimal(0.00);
			for(Object[] conS : conStr) {
				if(conS[0].equals(obj[0]) && conS[1].equals(obj[2])) {
					str[col+3] = conS[4];// 各项平均分{即各项得分}
					if(conS[4]!=null) {
						totalScore = totalScore.add(new BigDecimal(conS[4].toString()));
					}
					col++;
				}
			}
			str[columns-1] = totalScore;
			// 行数递增
			objects.add(str);
		}
		mav.addObject("objects", objects);
		mav.addObject("columns", columns-1);
		//分页参数
		int totalRecords = evaluationService.findEvaluationResultObjects(evaluationResult, 0, -1).size();
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("currpage", currpage);
		mav.addObject("pageSize", pageSize);
		mav.addObject("totalRecords", totalRecords);
		
		mav.setViewName("/evaluation/listEvaluationResult.jsp");
		return mav;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}