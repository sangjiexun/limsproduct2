package net.zjcclims.service.construction;


import net.zjcclims.dao.CommonDocumentDAO;
import net.zjcclims.dao.ProjectAnnualBudgetDAO;
import net.zjcclims.domain.CommonDocument;
import net.zjcclims.domain.ProjectAnnualBudget;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.BaseApplicationService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Spring service that handles CRUD requests for ProjectBudget entities
 * 
 */

@Service("ProjectAnnualBudgetService")
@Transactional
public class ProjectAnnualBudgetServiceImpl implements ProjectAnnualBudgetService {
	
	@Autowired private ProjectAnnualBudgetDAO projectAnnualBudgetDAO;
	@Autowired private CommonDocumentDAO commonDocumentDAO;
	@Autowired private BaseApplicationService baseApplicationService;
	
	/**
	 * Description 列表
	 * @param labAnnex
	 * @param currpage
	 * @param pageSize
	 * @return
	 * @author 陈乐为 2017-12-19
	 */
	public List<ProjectAnnualBudget> findAnnualBudgetByQuery(ProjectAnnualBudget annualBudget, int currpage, int pageSize){
		StringBuffer hql = new StringBuffer("select c from ProjectAnnualBudget c where 1=1");
		if(annualBudget != null){
			if(!EmptyUtil.isStringEmpty(annualBudget.getAcademyName())){
				hql.append(" and c.academyName like '%"+ annualBudget.getAcademyName() +"%'");
			}
			if(!EmptyUtil.isStringEmpty(annualBudget.getProjectName())){
				hql.append(" and c.projectName like '%"+ annualBudget.getProjectName() +"%'");
			}
			if(!EmptyUtil.isStringEmpty(annualBudget.getConstructYear())){
				hql.append(" and c.constructYear like '%"+ annualBudget.getConstructYear() +"%'");
			}
			if(!EmptyUtil.isStringEmpty(annualBudget.getStatus())) {
				hql.append(" and c.status like '"+ annualBudget.getStatus() +"'");
			}
			if(!EmptyUtil.isStringEmpty(annualBudget.getManager())) {
				hql.append(" and c.manager like '"+ annualBudget.getManager() +"'");
			}
		}
		
		List<ProjectAnnualBudget> projectAnnualBudgets = projectAnnualBudgetDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
		
		return projectAnnualBudgets;
	}
	
	/**
	 * Description 根据主键查找对象
	 * @param idKey
	 * @return
	 * @author 陈乐为 2017-12-19
	 */
	public ProjectAnnualBudget findAnnualBudgetById(Integer idKey) {
		return projectAnnualBudgetDAO.findProjectAnnualBudgetByPrimaryKey(idKey);
	}
	
	/**
	 * Description 保存
	 * @param annualBudget
	 * @author 陈乐为 2017-12-19
	 */
	public void saveAnnualBudget(ProjectAnnualBudget annualBudget) {
		projectAnnualBudgetDAO.store(annualBudget);
		projectAnnualBudgetDAO.flush();
	}
	
	/**
	 * Description 附件上传
	 * @param request
	 * @param response
	 * @return
	 * @author 陈乐为 2017-12-19
	 */
	@Override
	public String uploadAnnualBudgetFile(HttpServletRequest request, HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request; 
		 String sep = System.getProperty("file.separator");
		 Map files = multipartRequest.getFileMap();
		 Iterator fileNames = multipartRequest.getFileNames();
		 boolean flag =false; 
		 String fileDir = request.getSession().getServletContext().getRealPath( "/") +  "upload"+ sep+"annualbudget"+sep;
		 //返回到页面的文档信息
		 Set<CommonDocument> docSet=new HashSet<CommonDocument>();
		//存放文件文件夹名称
		for(; fileNames.hasNext();){
		  String filename = (String) fileNames.next();
		  CommonsMultipartFile file = (CommonsMultipartFile) files.get(filename); 
		  byte[] bytes = file.getBytes(); 
		  if(bytes.length != 0) {
			  // 说明申请有附件
			  if(!flag) { 
				  File dirPath = new File(fileDir);
				  if(!dirPath.exists()) { 
					  flag = dirPath.mkdirs();
		              } 
		      } 
			  String fileTrueName = file.getOriginalFilename();
			  //System.out.println("文件名称："+fileTrueName);
			  File uploadedFile = new File(fileDir + sep + fileTrueName);
			  //System.out.println("文件存放路径为："+fileDir + sep + fileTrueName);
			  try {
				FileCopyUtils.copy(bytes,uploadedFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			  CommonDocument doc = saveDocument(fileTrueName);
			  docSet.add(doc);
		  } 
		}
		String str="";
		for (CommonDocument d : docSet) {
			str+="<tr>"+
			    	"<td>"+d.getDocumentName()+"<input type='hidden' name='docId' value='"+d.getId()+"'>"+"</td>"+
					"<td><a href='javascript:void(0)' onclick='javascript:this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);' >删除</a></td>"+
					"</tr>";		
		}
		return str;
	}
	
	/**
	 * Description 保存文件
	 * @param fileTrueName
	 * @return
	 */
	public CommonDocument saveDocument(String fileTrueName) {
		CommonDocument doc = new CommonDocument( );
		doc.setDocumentName(fileTrueName);
		String url="upload/annualbudget/"+fileTrueName;
		doc.setDocumentUrl(url);
		
		return commonDocumentDAO.store(doc);
	}
	
	/**
	 * Description 查询所有annualBudget并分页
	 * @param request
	 * @param response
	 * @return
	 * @author 李志宇 2017-12-20
	 */
	public List<ProjectAnnualBudget> findAllAnnualBudgetByannualBudget(ProjectAnnualBudget annualBudget,
                                                                       int page, int pageSize) {
		// TODO Auto-generated method stub
		String sql="select c from ProjectAnnualBudget c where 1=1";
		if(annualBudget.getProjectName()!=null&&!annualBudget.getProjectName().equals("")){
			sql+=" and c.projectName like '%"+annualBudget.getProjectName()+"%'";
		}	
		return projectAnnualBudgetDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
	}
	
	/**
	 * Description 查询所有annualBudget
	 * @param request
	 * @param response
	 *@return
	 * @author 李志宇 2017-12-20
	 */
	public List<ProjectAnnualBudget> findAllAnnualBudgetByannualBudget(ProjectAnnualBudget annualBudget){
		// TODO Auto-generated method stub
				String sql="select c from ProjectAnnualBudget c where 1=1";
				if(annualBudget.getProjectName()!=null&&!annualBudget.getProjectName().equals("")){
					sql+=" and c.projectName like '%"+annualBudget.getProjectName()+"%'";
				}	
				return projectAnnualBudgetDAO.executeQuery(sql,0,-1);
	}
	
	/**
	 * Description 导出
	 * @param request
	 * @param response
	 * @return
	 * @author 李志宇 2017-12-20
	 */
	public void exportExcelProjectAnnualBudget(List<ProjectAnnualBudget> annualBudgets, HttpServletRequest request,
                                               HttpServletResponse response) throws Exception {
		// 新建一个map的list集合；
				List<Map> list1 = new ArrayList<Map>();
				// 循环alls；
				for (ProjectAnnualBudget annualBudget : annualBudgets) {
					// 新建一个HashMap对象；
					Map map = new HashMap();
					
					// 编号；
					map.put("id", annualBudget.getId());
					// 所在系部；
					map.put("academyName", annualBudget.getAcademyName());
					// 项目名称；
					map.put("projectName", annualBudget.getProjectName());
					// 项目来源；
					map.put("projectSource", annualBudget.getProjectSource());
					//建设年度；
					map.put("constructYear", annualBudget.getConstructYear());
					//项目经费
					map.put("projectFunds", annualBudget.getProjectFunds());
					//项目负责人
					map.put("manager", annualBudget.getManager());
					//项目进展
					map.put("projectProceed", annualBudget.getProjectProceed());
					// 最晚验收时间；
					if(annualBudget.getDeadLines()!=null){
						map.put("deadLine", annualBudget.getDeadLines().getTime());
					}else{
						map.put("deadLine", "");
					}
					//状态
					map.put("status", annualBudget.getStatus());
					list1.add(map);
				}
				// 给表设置名称；
				String title = "实验室历年建设项目  ";
				// 给表设置表名；
				String[] hearders = new String[] { "序号", "所在系部", "项目名称", "项目来源", "建设年度", "项目经费（万元）"
						, "项目负责人", "项目进程", "最晚验收时间" ,"状态"};// 表头数组
				// 属性数组，写数据到excel时的顺序定位
				String[] fields = new String[] { "id", "academyName", "projectName", "projectSource"
						, "constructYear", "projectFunds", "manager", "projectProceed", "deadLine", "status"};
				// 输出excel；
				baseApplicationService.exportExcel(list1, title, hearders, fields,
						request, response);
	}

	/**
	 * Description 导入
	 * @param File
	 * throws ParseException
	 * @author 廖文辉 2018-1-3
	 */
	public void importAnnualBudget(String File)throws ParseException {
		Boolean isE2007=false;
		if(File.endsWith("xlsx")){
			isE2007=true;
		}
		//建立输入流
		try {
			//建立输入流
			InputStream input = new FileInputStream(File);
			Workbook wb =null;
			if(isE2007){
				wb=new XSSFWorkbook(input);
			}else{
				wb=new HSSFWorkbook(input);
			}
			//获取第一个表单数据
			Sheet sheet= wb.getSheetAt(0);
			//获取第一个表单迭代器
			Iterator<Row>rows=sheet.rowIterator();
			Row rowContent=null;// 表头

			String academy_name="";//所属系部
			String project_name="";//项目名称
			String project_source="";//项目来源
			String construct_year="";//建设年度
			String project_funds="";//项目经费
			String manager="";//项目负责人
			String project_proceed="";//项目进展
			String dead_lines="";//最晚验收时间
			String status="";//状态

			rows.hasNext();
			rows.hasNext();

			int a=0;
			while(rows.hasNext()){
				Row row = rows.next();
				if(a==0){
					row = rows.next();
					a=1;
				}

				int column = sheet.getRow(0).getPhysicalNumberOfCells();

				academy_name="";//所属系部
				project_name="";//项目名称
				project_source="";//项目来源
				construct_year="";//建设年度
				project_funds="";//项目经费
				manager="";//项目负责人
				project_proceed="";//项目进展
				dead_lines="";//最晚验收时间
				status="";//状态

				for(int k=0;k<column;k++){
					if(row.getCell(k)!=null){
						row.getCell(k).setCellType(Cell.CELL_TYPE_STRING);
						String content = row.getCell(k).getStringCellValue();

						if(k == 0){
							academy_name=content;
						}
						if(k == 1){
							project_name = content;
						}
						if(k == 2){
							project_source = content;
						}
						if(k == 3){
							construct_year = content;
						}
						if(k == 4){
							project_funds = content;
						}
						if(k == 5){
							manager = content;
						}
						if(k == 6){
							project_proceed = content;
						}
						if(k == 7){
							dead_lines = content;
						}
						if(k == 8){
							status = content;
						}
					}
				}

				ProjectAnnualBudget projectAnnualBudget = new ProjectAnnualBudget();

				if(!dead_lines.equals("")){
					SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
					Date date = new Date();
					date=sdf.parse(dead_lines);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					projectAnnualBudget.setDeadLines(calendar);
				}

				projectAnnualBudget.setAcademyName(academy_name);
				projectAnnualBudget.setProjectName(project_name);
				projectAnnualBudget.setProjectSource(project_source);
				projectAnnualBudget.setConstructYear(construct_year);
				projectAnnualBudget.setProjectFunds(project_funds);
				projectAnnualBudget.setManager(manager);
				projectAnnualBudget.setProjectProceed(project_proceed);
				projectAnnualBudget.setStatus(status);

				projectAnnualBudgetDAO.store(projectAnnualBudget);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
