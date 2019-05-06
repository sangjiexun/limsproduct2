/**
 * Description 评教模块方法
 * @author 陈乐为 2018-6-11
 */

package net.zjcclims.service.evaluation;


import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service("EvaluationService")
@Transactional
public class EvaluationServiceImpl implements EvaluationService {
	
	@PersistenceContext
    private EntityManager entityManager;
	@Autowired
    private EvaluationSetDAO evaluationSetDAO;
	@Autowired
    private EvaluationContentDAO evaluationContentDAO;
	@Autowired
    private EvaluationSourceDAO evaluationSourceDAO;
	@Autowired
    private EvaluationResultDAO evaluationResultDAO;
	@Autowired
    private SchoolTermDAO schoolTermDAO;
	
	/**
	 * Description 根据条件查询评教时间设置列表
	 * @param evaluationSet 查询参数
	 * @param currpage 页码
	 * @param pageSize 行数
	 * @return List<EvaluationSet>
	 * @author 陈乐为 2018-6-11
	 */
	@Override
	public List<EvaluationSet> findEvaluationSetByQuery(EvaluationSet evaluationSet, int currpage, int pageSize) {
		String sql = "select e from EvaluationSet e where 1=1";
		if(evaluationSet!=null && evaluationSet.getTermId()!=null) {
			sql += " and e.termId = " + evaluationSet.getTermId();
		}
		List<EvaluationSet> evaluationSets = evaluationSetDAO.executeQuery(sql, (currpage-1)*pageSize, pageSize);
		
		return evaluationSets;
	}
	
	/**
	 * Description 根据条件查询评教设置项列表
	 * @param evaluationContent 查询参数
	 * @param currpage 页码
	 * @param pageSize 行数
	 * @return List<EvaluationContent>
	 * @author 陈乐为 2018-6-11
	 */
	@Override
	public List<EvaluationContent> findEvaluationContentByQuery(EvaluationContent evaluationContent, int currpage, int pageSize) {
		String sql = "select e from EvaluationContent e where 1=1";
		if(evaluationContent!=null && evaluationContent.getSchoolTerm()!=null && evaluationContent.getSchoolTerm().getId()!=null) {
			sql += " and e.schoolTerm.id = " + evaluationContent.getSchoolTerm().getId();
		}
		List<EvaluationContent> evaluationContents = evaluationContentDAO.executeQuery(sql, (currpage-1)*pageSize, pageSize);
		
		return evaluationContents;
	}
	
	/**
	 * Description 根据主键查找对象
	 * @param idKey
	 * @return
	 * @author 陈乐为 2018-6-11
	 */
	@Override
	public EvaluationContent findEvaluationContentByPrimaryKey(Integer idKey) {
		
		return evaluationContentDAO.findEvaluationContentByPrimaryKey(idKey);
	}
	
	/**
	 * Description 保存
	 * @param evaluationContent
	 * @author 陈乐为 2018-6-11
	 */
	@Override
	public void saveEvaluationContent(EvaluationContent evaluationContent) {
		evaluationContentDAO.store(evaluationContent);
	}
	
	/**
	 * Description 删除评教项
	 * @param evaluationContent
	 * @author 陈乐为 2018-6-11
	 */
	@Override
	public void deleteEvaluationContent(EvaluationContent evaluationContent) {
		evaluationContentDAO.remove(evaluationContent);
		evaluationContentDAO.flush();
	}
	
	/**
	 * Description 根据条件查找评教列表
	 * @param evaluationSource
	 * @param currpage
	 * @param pageSize
	 * @param type 0未评 1已评
	 * @return
	 */
	@Override
	public List<EvaluationSource> findEvaluationSourceByQuery(EvaluationSource evaluationSource, int currpage, int pageSize) {
		String sql = "select s from EvaluationSource s where 1=1";
		sql += " and s.termId = " + evaluationSource.getTermId();
		sql += " and s.student = '"+ evaluationSource.getStudent() +"'";
		sql += " and s.status = " + evaluationSource.getStatus();
		List<EvaluationSource> evaluationSources = evaluationSourceDAO.executeQuery(sql, (currpage-1)*pageSize, pageSize);
		
		return evaluationSources;
	}
	
	/**
	 * Description 根据条件查询所有已提交评教内容
	 * @param evaluationContent
	 * @return
	 */
	@Override
	public List<EvaluationContent> findEvaluationContentSubmit(EvaluationContent evaluationContent) {
		String sql = "select e from EvaluationContent e where 1=1";
		if(evaluationContent!=null && evaluationContent.getSchoolTerm()!=null && evaluationContent.getSchoolTerm().getId()!=null) {
			sql += " and e.schoolTerm.id = " + evaluationContent.getSchoolTerm().getId();
		}
		sql += " and e.status = 1";
		List<EvaluationContent> evaluationContents = evaluationContentDAO.executeQuery(sql, 0, -1);
		
		return evaluationContents;
	}
	
	/**
	 * Description 根据主键查找评教主体
	 * @param idKey
	 * @return
	 * @author 陈乐为 2018-6-12
	 */
	@Override
	public EvaluationSource findEvaluationSourceByPrimaryKey(Integer idKey) {
		return evaluationSourceDAO.findEvaluationSourceByPrimaryKey(idKey);
	}
	
	/**
	 * Description 保存评教结果
	 * @param evaluationResult
	 * @return
	 * @author 陈乐为 2018-6-12
	 */
	@Override
	public void saveEvaluationResult(EvaluationResult evaluationResult) {
		evaluationResultDAO.store(evaluationResult);
		evaluationResultDAO.flush();
	}
	
	/**
	 * Description 保存评教源数据
	 * @param evaluationSource
	 * @author 陈乐为 2018-6-12
	 */
	@Override
	public void saveEvaluationSource(EvaluationSource evaluationSource) {
		evaluationSourceDAO.store(evaluationSource);
		evaluationSourceDAO.flush();
	}
	
	/**
	 * Description 根据学期、课程、教师、学生查找评教结果
	 * @param evaluationSource
	 * @return
	 * @author 陈乐为 2018-6-12
	 */
	@Override
	public List<EvaluationResult> findEvaluationResultByQuery(EvaluationSource evaluationSource) {
		String sql = "select r from EvaluationResult r where 1=1";
		sql += " and r.termId = " + evaluationSource.getTermId();
		sql += " and r.schoolCourseInfo.courseNumber = '"+ evaluationSource.getSchoolCourseInfo().getCourseNumber() +"'";
		sql += " and r.teacherNo = '"+ evaluationSource.getUser().getUsername() +"'";
		sql += " and r.studentNo = '"+ evaluationSource.getStudent() +"'";
		
		List<EvaluationResult> evaluationResults = evaluationResultDAO.executeQuery(sql);
		return evaluationResults;
	}
	
	/**
	 * Descriiption 获取页面显示的评教结果
	 * @param evaluationResult
	 * @return
	 * @author 陈乐为 2018-6-12
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findEvaluationResultObjects(EvaluationResult evaluationResult, int currpage, int pageSize) {
		Query query = entityManager.createNativeQuery("call proc_evaluation_total_stu("+evaluationResult.getTermId()+","+currpage+","+pageSize+")");
		// 获取list对象
		List<Object[]> list = query.getResultList();
		return list;
	}
	
	/**
	 * Description 查询单项得分值
	 * @param evaluationResult
	 * @return
	 * @author 陈乐为 2018-6-12
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findEvaluationContentScores(EvaluationResult evaluationResult) {
		Query query = entityManager.createNativeQuery("call proc_evaluation_content_score("+evaluationResult.getTermId()+")");
		// 获取list对象
		List<Object[]> list = query.getResultList();
		return list;
	}
	
	/**
	 * Description 查找学期
	 * @param idKey
	 * @return
	 * @author 陈乐为 2018-6-12
	 */
	@Override
	public SchoolTerm findSchoolTermByPrimaryKey(Integer idKey) {
		return schoolTermDAO.findSchoolTermByPrimaryKey(idKey);
	}
	
	/**
	 * Description 保存评教时间设置
	 * @param evaluationSet
	 * @author 陈乐为 2018-6-13
	 */
	@Override
	public void saveEvaluationSet(EvaluationSet evaluationSet) {
		evaluationSetDAO.store(evaluationSet);
		evaluationSetDAO.flush();
	}
	
	/**
	 * Description 查找学期对应评教时间设置对象
	 * @param idKey 学期主键
	 * @return
	 * @author 陈乐为 2018-6-13
	 */
	@Override
	public EvaluationSet findEvaluationSetByTerm(Integer idKey) {
		String sql = "select s from EvaluationSet s where s.termId = " + idKey;
		List<EvaluationSet> evaluationSets = evaluationSetDAO.executeQuery(sql);
		if(evaluationSets!=null && evaluationSets.size()>0) {
			return evaluationSets.get(0);
		}else {
			return null;
		}
	}
	
	/**
	 * Description 根据主键查找评教时间设置对象
	 * @param idKey
	 * @return
	 * @author 陈乐为 2018-6-13
	 */
	@Override
	public EvaluationSet findEvaluationSetByPrimaryKey(Integer idKey) {
		return evaluationSetDAO.findEvaluationSetByPrimaryKey(idKey);
	}
	
	/**
	 * Description 根据学期、课程、教师、学生查询已存在的评教数据量
	 * @param evaluationSource
	 * @return
	 * @author 陈乐为 2018-6-13
	 */
	@Override
	public int getEvaluationSourceExist(EvaluationSource evaluationSource) {
		String sql = "select count(s) from EvaluationSource s where 1=1";
		sql += " and s.termId = " + evaluationSource.getTermId();
		sql += " and s.schoolCourseInfo.courseNumber = '"+ evaluationSource.getSchoolCourseInfo().getCourseNumber() +"'";
		sql += " and s.user.username = '"+ evaluationSource.getUser().getUsername() +"'";
		sql += " and s.student = '"+ evaluationSource.getStudent() +"'";
		
		return ((Long) evaluationSourceDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	
	/**
	 * Description 插入评教原始数据
	 * @param timetableAppointments
	 * @author 陈乐为 2018-6-13
	 */
	@Override
	public void insertIntoEvaluationSource(List<TimetableAppointment> timetableAppointments) {
		/**
		 * 1.如果是教务排课（school_course_detail关联school_course关联school_course_info），根据学期、school_course_info、教师、学生判重（evaluation_source）
		 * 2.如果是自主排课（timetable_self_course关联school_course_info），根据
		 */
		for (TimetableAppointment timetableAppointment : timetableAppointments) {
			if(timetableAppointment.getSchoolCourseDetail()!=null && timetableAppointment.getSchoolCourseDetail().getCourseDetailNo()!=null) {// 教务排课
				// 遍历上课教师
				for(TimetableTeacherRelated teacher : timetableAppointment.getTimetableTeacherRelateds()) {
					// 遍历课程学生
					for(SchoolCourseStudent courseStudent : timetableAppointment.getSchoolCourseDetail().getSchoolCourseStudents()) {
						// 根据学期、课程、教师、学生判断是否已经存在评教数据
						// 参数传递
						EvaluationSource evaluationSource = new EvaluationSource();
						evaluationSource.setSchoolCourseInfo(timetableAppointment.getSchoolCourseInfo());
						evaluationSource.setTermId(timetableAppointment.getSchoolCourseDetail().getSchoolTerm().getId());
						evaluationSource.setUser(teacher.getUser());
						evaluationSource.setStudent(courseStudent.getUserByStudentNumber().getUsername());
						
						int existCount = this.getEvaluationSourceExist(evaluationSource);
						if(existCount>0) {// 评教数据已存在
							// do nothing
						}else {
							// 插入数据
							String insertSQL = "insert into evaluation_source (term_id,course_number,teacher,student,status) values " +
									"("+evaluationSource.getTermId()+",'"+evaluationSource.getSchoolCourseInfo().getCourseNumber()+"'," +
											"'"+evaluationSource.getUser().getUsername()+"','"+evaluationSource.getStudent()+"',0)";
							entityManager.createNativeQuery(insertSQL).executeUpdate();
						}
					}
				}
			}else if(timetableAppointment.getTimetableSelfCourse()!=null && timetableAppointment.getTimetableSelfCourse().getId()!=null) {// 自主排课
				// 遍历上课教师
				for(TimetableTeacherRelated teacher : timetableAppointment.getTimetableTeacherRelateds()) {
					// 遍历课程学生
					for(TimetableCourseStudent courseStudent : timetableAppointment.getTimetableSelfCourse().getTimetableCourseStudents()) {
						// 根据学期、课程、教师、学生判断是否已经存在评教数据
						// 参数传递
						EvaluationSource evaluationSource = new EvaluationSource();
						evaluationSource.setSchoolCourseInfo(timetableAppointment.getSchoolCourseInfo());
						evaluationSource.setTermId(timetableAppointment.getTimetableSelfCourse().getSchoolTerm().getId());
						evaluationSource.setUser(teacher.getUser());
						evaluationSource.setStudent(courseStudent.getUser().getUsername());
						
						int existCount = this.getEvaluationSourceExist(evaluationSource);
						if(existCount>0) {// 评教数据已存在
							// do nothing
						}else {
							// 插入数据
							String insertSQL = "insert into evaluation_source (term_id,course_number,teacher,student,status) values " +
									"("+evaluationSource.getTermId()+",'"+evaluationSource.getSchoolCourseInfo().getCourseNumber()+"'," +
											"'"+evaluationSource.getUser().getUsername()+"','"+evaluationSource.getStudent()+"',0)";
							entityManager.createNativeQuery(insertSQL).executeUpdate();
						}
					}
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}