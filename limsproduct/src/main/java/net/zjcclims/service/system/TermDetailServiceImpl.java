package net.zjcclims.service.system;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import net.zjcclims.dao.SchoolTermDAO;
import net.zjcclims.dao.SchoolWeekDAO;
import net.zjcclims.domain.SchoolTerm;
import net.zjcclims.domain.SchoolWeek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("TermDetailService")
public class TermDetailServiceImpl implements TermDetailService {
	@Autowired
	private SchoolTermDAO schoolTermDAO;
	
	@Autowired
	private SchoolWeekDAO schoolWeekDAO;
	
	/*************************************************************************************
	 * @內容：获取学期的总记录数
	 * @作者： 叶明盾
	 * @日期：2014-07-28
	 *************************************************************************************/
	@Transactional
	public int getTermTotalRecords(){
		Set<SchoolTerm> terms = schoolTermDAO.findAllSchoolTerms();
		return terms.size();
	}
	
	/*************************************************************************************
	 * @內容：查找所有的学期信息
	 * @作者： 叶明盾
	 * @日期：2014-07-28
	 *************************************************************************************/
	public Set<SchoolTerm> findAllTerms(int curr, int size){
		return schoolTermDAO.findAllSchoolTerms(curr*size, size);
	}
	
	/*************************************************************************************
	 * @內容：根据学期生成周次
	 * @作者： 叶明盾
	 * @日期：2014-07-29
	 *************************************************************************************/
	@Transactional
	public void createWeek(SchoolTerm schoolTerm){
		//判断学期创建时间是否为空，如果为空则设置当前时间
		if(schoolTerm.getCreatedAt() == null )
			schoolTerm.setCreatedAt(Calendar.getInstance());
		//设置更新时间为当前时间
			schoolTerm.setUpdatedAt(Calendar.getInstance());
		//保存schoolTerm
			 SchoolTerm term = schoolTermDAO.store(schoolTerm);
		//获取term的schoolWeek的集合
			 Set<SchoolWeek> schoolWeeks = term.getSchoolWeeks();
		//新建一个SchoolWeek的list对象
			 List<SchoolWeek> weeks=new ArrayList<SchoolWeek>();
		//将schoolWeeks添加到weeks中
			 weeks.addAll(schoolWeeks);
		//如果schoolWeeks的大小不为0则将这记录删除；
			 if(weeks.size() > 0) {
				 //for循环weeks删除schoolWeek
				 for(SchoolWeek schoolWeek:weeks){
					 schoolWeekDAO.remove(schoolWeek);
				 }
			 }
		//得到学期的开始时间
			 Calendar termStart = term.getTermStart();
		//得到学期的结束时间
			 Calendar termEnd = term.getTermEnd();
		//获取当天周内天数
			 int current = termStart.get(Calendar.DAY_OF_WEEK);
		//获取周开始基准
			 int min = termStart.getActualMinimum(Calendar.DAY_OF_WEEK);
		//将termStart转成Date型
			 Date date2 =  termStart.getTime();
		//设置一个日期变量
			 Calendar now = termStart;
		//当天-基准，获取周开始日期
			 now.add(Calendar.DAY_OF_WEEK, min-current+1);
		//得到两个日期相隔的时间数；
			 long betTimes=termEnd.getTime().getTime()-now.getTime().getTime();
		//得到两个日期相隔的天数；
			 int betDays = (int)(betTimes/86400000);
		//算出该学期有多少周；
			 int week = betDays/7+1;
		//循环保存该学期的周次；
			 for(int i=1; i<week+1; i++){
				 for(int j=1; j<8; j++){
					//获取所需要加的天数；
					 int weekSum=(i-1)*7+(j-1);
					//新建一个week对象；
					SchoolWeek schoolWeek=new SchoolWeek();					
					 //设置schoolWeek的schoolTerm为term；
					schoolWeek.setSchoolTerm(term);
					//设置schoolWeek的week为i；
					schoolWeek.setWeek(i);
					Calendar cal= Calendar.getInstance();
					cal.setTime(date2);
					cal.add(Calendar.DATE, weekSum);
					 //设置schoolWeek的Date；
					schoolWeek.setDate(cal);
					//获取cal在周内的天数；
					int dayWeek=cal.get(Calendar.DAY_OF_WEEK);
					//如果dayWeek不等于1;则设置schoolWeek的weekDay为dayWeek减1；
					if(dayWeek!=1){
			        	   schoolWeek.setWeekday(dayWeek-1);
			           }else{
			        	   schoolWeek.setWeekday(7);
			           }				  
					   //如果创建时间为空，则设置为当前时间；
							if(schoolWeek.getCreatedAt()==null)
								schoolWeek.setCreatedAt(Calendar.getInstance());
							//设置更新时间为当前时间；
								schoolWeek.setUpdatedAt(Calendar.getInstance());
						schoolWeekDAO.store(schoolWeek);						 
				 }			 
			 }
	}
	
	/*************************************************************************************
	 * @內容：根据学期的名称查找学期
	 * @作者： 叶明盾
	 * @日期：2014-07-29
	 *************************************************************************************/
	public Set<SchoolTerm> findTermsByTermName(String termName)
	{
		return schoolTermDAO.findSchoolTermByTermName(termName);
	}
	
	/*************************************************************************************
	 * @內容：根据学期的id查找学期
	 * @作者： 叶明盾
	 * @日期：2014-08-06
	 *************************************************************************************/
	public SchoolTerm findTermById(Integer id)
	{
		SchoolTerm schoolTerm = schoolTermDAO.findSchoolTermById(id);
		return schoolTerm;		
	}

	/************************************************************ 
	 * @内容：删除学期
	 * @作者: 李鹏翔
	 ************************************************************/
	public void deleteTerm(SchoolTerm st){
		schoolTermDAO.remove(st);
		schoolTermDAO.flush();
	}
	/************************************************************ 
	 * @内容：删除学期
	 * @作者: 李鹏翔
	 ************************************************************/
	public void saveTerm(SchoolTerm st){
		if(st.getCreatedAt()==null){
			st.setCreatedAt(Calendar.getInstance());
		}
		st.setUpdatedAt(Calendar.getInstance());
		schoolTermDAO.store(st);
		schoolTermDAO.flush();
	}

}