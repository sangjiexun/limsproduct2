package net.gvsun.lims.service.school;

import net.gvsun.lims.dto.common.BaseDTO;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

public interface SchoolCourseService {

	/*************************************************************************************
	 * Description:教务课程-获取课程库列表
	 *
	 * @author： 魏誠
	 * @date：2018-08-03
	 *************************************************************************************/
	public BaseDTO getSchoolCourseListByPage(int termId, String search,String status, int offset, int limit, String sort, String sortOrder,HttpServletRequest request);

	/**************************************************************************
	 * Description 教务排课-查看排课管理列表-获取数据
	 *
	 * @author 魏诚
	 * @date 2018年10月17日
	 **************************************************************************/
	@Transactional
	public BaseDTO apiEduSchoolCourseByPage(int termId, String search,String status, int offset, int limit, String sort, String sortOrder,HttpServletRequest request);

	/**
	 * Description 获取排课审核拒绝列表
	 * @param termId 学期id
	 * @param search 输入
	 * @param status 状态
	 * @param offset 偏移量
	 * @param limit 每页最大数量
	 * @param sort 排序字段
	 * @param sortOrder 排序方式
	 * @param request 请求（传参）
	 * @return 排课审核拒绝列表
	 * @author 黄保钱 2019-1-23
	 */
	BaseDTO getSchoolCourseListByPageForRefuse(int termId, String search, String status, int offset, int limit, String sort, String sortOrder, HttpServletRequest request);

	/**************************************************************************
	 * Description 排课审核-获取当前审核对象信息
	 * @param termId
	 * @param search
	 * @return
	 * @author 陈乐为 2019-1-16
	 **************************************************************************/
	public BaseDTO apiEduSchoolCourseForAudit(int termId, String search);
}