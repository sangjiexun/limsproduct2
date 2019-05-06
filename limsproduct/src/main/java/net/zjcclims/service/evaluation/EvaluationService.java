/**
 * Description 评教模块方法
 * @author 陈乐为 2018-6-11
 */

package net.zjcclims.service.evaluation;

import net.zjcclims.domain.*;

import java.util.List;

public interface EvaluationService {
	/**
	 * Description 根据条件查询评教时间设置列表
	 * @param evaluationSet 查询参数
	 * @param currpage 页码
	 * @param pageSize 行数
	 * @return List<EvaluationSet>
	 * @author 陈乐为 2018-6-11
	 */
	public List<EvaluationSet> findEvaluationSetByQuery(EvaluationSet evaluationSet, int currpage, int pageSize);
	
	/**
	 * Description 根据条件查询评教设置项列表
	 * @param evaluationSet 查询参数
	 * @param currpage 页码
	 * @param pageSize 行数
	 * @return List<EvaluationContent>
	 * @author 陈乐为 2018-6-11
	 */
	public List<EvaluationContent> findEvaluationContentByQuery(EvaluationContent evaluationContent, int currpage, int pageSize);
	
	/**
	 * Description 根据主键查找对象
	 * @param idKey
	 * @return
	 * @author 陈乐为 2018-6-11
	 */
	public EvaluationContent findEvaluationContentByPrimaryKey(Integer idKey);
	
	/**
	 * Description 保存
	 * @param evaluationContent
	 * @author 陈乐为 2018-6-11
	 */
	public void saveEvaluationContent(EvaluationContent evaluationContent);
	
	/**
	 * Description 删除评教项
	 * @param evaluationContent
	 * @author 陈乐为 2018-6-11
	 */
	public void deleteEvaluationContent(EvaluationContent evaluationContent);
	
	/**
	 * Description 根据条件查找评教列表
	 * @param evaluationSource
	 * @param currpage
	 * @param pageSize
	 * @param type 0未评 1已评
	 * @return
	 */
	public List<EvaluationSource> findEvaluationSourceByQuery(EvaluationSource evaluationSource, int currpage, int pageSize);
	
	/**
	 * Description 根据条件查询所有已提交评教内容
	 * @param evaluationContent
	 * @return
	 */
	public List<EvaluationContent> findEvaluationContentSubmit(EvaluationContent evaluationContent);
	
	/**
	 * Description 根据主键查找评教主体
	 * @param idKey
	 * @return
	 * @author 陈乐为 2018-6-12
	 */
	public EvaluationSource findEvaluationSourceByPrimaryKey(Integer idKey);
	
	/**
	 * Description 保存评教结果
	 * @param evaluationResult
	 * @return
	 * @author 陈乐为 2018-6-12
	 */
	public void saveEvaluationResult(EvaluationResult evaluationResult);
	
	/**
	 * Description 保存评教源数据
	 * @param evaluationSource
	 * @author 陈乐为 2018-6-12
	 */
	public void saveEvaluationSource(EvaluationSource evaluationSource);
	
	/**
	 * Description 根据学期、课程、教师、学生查找评教结果
	 * @param evaluationSource
	 * @return
	 * @author 陈乐为 2018-6-12
	 */
	public List<EvaluationResult> findEvaluationResultByQuery(EvaluationSource evaluationSource);
	
	/**
	 * Descriiption 获取页面显示的评教结果
	 * @param evaluationResult
	 * @return
	 * @author 陈乐为 2018-6-12
	 */
	public List<Object[]> findEvaluationResultObjects(EvaluationResult evaluationResult, int currpage, int pageSize);
	
	/**
	 * Description 查询单项得分值
	 * @param evaluationResult
	 * @return
	 * @author 陈乐为 2018-6-12
	 */
	public List<Object[]> findEvaluationContentScores(EvaluationResult evaluationResult);
	
	/**
	 * Description 查找学期
	 * @param idKey
	 * @return
	 * @author 陈乐为 2018-6-12
	 */
	public SchoolTerm findSchoolTermByPrimaryKey(Integer idKey);
	
	/**
	 * Description 保存评教时间设置
	 * @param evaluationSet
	 * @author 陈乐为 2018-6-13
	 */
	public void saveEvaluationSet(EvaluationSet evaluationSet);
	
	/**
	 * Description 查找学期对应评教时间设置对象
	 * @param idKey 学期主键
	 * @return
	 * @author 陈乐为 2018-6-13
	 */
	public EvaluationSet findEvaluationSetByTerm(Integer idKey);
	
	/**
	 * Description 根据主键查找评教时间设置对象
	 * @param idKey
	 * @return
	 * @author 陈乐为 2018-6-13
	 */
	public EvaluationSet findEvaluationSetByPrimaryKey(Integer idKey);
	
	/**
	 * Description 根据学期、课程、教师、学生查询已存在的评教数据量
	 * @param evaluationSource
	 * @return
	 * @author 陈乐为 2018-6-13
	 */
	public int getEvaluationSourceExist(EvaluationSource evaluationSource);
	
	/**
	 * Description 插入评教原始数据
	 * @param timetableAppointments
	 * @author 陈乐为 2018-6-13
	 */
	public void insertIntoEvaluationSource(List<TimetableAppointment> timetableAppointments);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}