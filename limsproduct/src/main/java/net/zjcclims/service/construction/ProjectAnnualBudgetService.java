package net.zjcclims.service.construction;



import net.zjcclims.domain.ProjectAnnualBudget;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;

/**
 * Spring service that handles CRUD requests for ProjectBudget entities
 * 
 */
public interface ProjectAnnualBudgetService {
	/**
	 * Description 列表
	 * @param labAnnex
	 * @param currpage
	 * @param pageSize
	 * @return
	 * @author 陈乐为 2017-12-19
	 */
	public List<ProjectAnnualBudget> findAnnualBudgetByQuery(ProjectAnnualBudget annualBudget, int currpage, int pageSize);
	
	/**
	 * Description 根据主键查找对象
	 * @param idKey
	 * @return
	 * @author 陈乐为 2017-12-19
	 */
	public ProjectAnnualBudget findAnnualBudgetById(Integer idKey);
	
	/**
	 * Description 保存
	 * @param annualBudget
	 * @author 陈乐为 2017-12-19
	 */
	public void saveAnnualBudget(ProjectAnnualBudget annualBudget);
	
	/**
	 * Description 附件上传
	 * @param request
	 * @param response
	 * @return
	 * @author 陈乐为 2017-12-19
	 */
	public String uploadAnnualBudgetFile(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * Description 查询所有annualBudget并分页
	 * @param request
	 * @param response
	 * @return
	 * @author 李志宇 2017-12-20
	 */
	public List<ProjectAnnualBudget> findAllAnnualBudgetByannualBudget(ProjectAnnualBudget annualBudget,
                                                                       int page, int pageSize);

	/**
	 * Description 查询所有annualBudget
	 * @return
	 * @author 李志宇 2017-12-20
	 */
	public List<ProjectAnnualBudget> findAllAnnualBudgetByannualBudget(ProjectAnnualBudget annualBudget);

	/**
	 * Description 导出
	 * @param request
	 * @param response
	 * @return
	 * @author 李志宇 2017-12-20
	 */
	public void exportExcelProjectAnnualBudget(List<ProjectAnnualBudget> annualBudgets, HttpServletRequest request,
                                               HttpServletResponse response) throws Exception;

	/**
	 * Description 导入
	 * @param File
	 * throws ParseException
	 * @author 廖文辉 2018-1-3
	 */
	public void importAnnualBudget(String File)throws ParseException;

}