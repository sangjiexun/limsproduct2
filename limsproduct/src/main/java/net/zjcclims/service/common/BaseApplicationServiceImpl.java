package net.zjcclims.service.common;

import excelTools.ExcelUtils;
import excelTools.JsGridReportBase;
import excelTools.TableData;
import net.zjcclims.dao.UserDAO;
import net.zjcclims.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("BaseApplicationService")
@Transactional
public class BaseApplicationServiceImpl implements BaseApplicationService {
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private ShareService shareService;


	/*
	 * 分页显示模块
	 */
	@Transactional
	public Map<String, Integer> getPage(int pageSize, int currPage,
			int totalRecords) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		// 分页的bean模块，设置分页的具体参数结构
		PageModel pageModel = new PageModel();
		// 设置总的记录数
		pageModel.setTotalRecords(totalRecords);
		// 设置每页最大显示的记录数
		pageModel.setPageSize(pageSize);
		// 设置当前记录
		pageModel.setCurrpage(currPage);
		// 赋值总的页数到map中
		map.put("totalPage", pageModel.getTotalPage());
		// 设置下一页到map中
		map.put("nextPage", pageModel.getNextPage());
		map.put("previousPage", pageModel.getPreiviousPage());
		// 设置上一页到map中
		map.put("firstPage", pageModel.getFisrtPage());
		// 设置最后一页到map中
		map.put("lastPage", pageModel.getLastPage());
		return map;
	}

	/**
	 * 导出excel 
	 */
	public void exportExcel(List<Map> list, String title, String[] hearders,
			String[] fields, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 封装表格数据
		TableData td = ExcelUtils.createTableData(list,
				ExcelUtils.createTableHeader(hearders), fields);
		// 导出工具类
		JsGridReportBase report = new JsGridReportBase(request, response);
		// 将TableData数据对象导出到excel
		// String name = this.getUser().getCname();
		String name = shareService.getUser().getCname();
		report.exportToExcel(title, name, td);
	}

	/**
	 * 登录人 
	 */
	@Transactional
	public Map getLogMap() {
		Map logMap = new HashMap();
		User user = getUser();
		logMap.put(user.getUsername(), user.getCname());
		return logMap;
	}

	/**
	 * 获取登录人的所有信息 
	 */
	@Transactional
	public User getUser() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null
				&& (!AnonymousAuthenticationToken.class.isAssignableFrom(auth
						.getClass()))) {
			Set<User> set = userDAO.findUserByCname(SecurityContextHolder
					.getContext().getAuthentication().getName());
			List<User> list = new ArrayList<User>();
			list.addAll(set);
			if (list.iterator().hasNext()) {
				return list.iterator().next();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}



	/***********************************************************************************************
	 * @public File createDir(String code,String fileName) throws Exception{
	 * @param:User user
	 * @description:根据登录用户返回逗号分隔的权限字符串
	 * @author:魏诚 2014/06/11
	 * @version:V1.0.1
	 * @modify:
	 ***********************************************************************************************/
	@Transactional
	public File createDir(String code, String fileName) throws Exception {
		String root = System.getProperty("property.root");
		String upLoad = root + fileName + File.separator;
		File sendPath = new File(upLoad + code);
		if (!sendPath.exists()) {
			sendPath.mkdirs();
		}
		return sendPath;
	}

	/***********************************************************************************************
	 * @public void upLoad(MultipartFile file, Integer invoiceid,Integer
	 *         documentTypeId)
	 * @param:
	 * @description:发票文件上传
	 * @author:魏诚 2014/05/10
	 * @version:V1.0.1
	 * @modify:
	 ***********************************************************************************************/
	@Transactional
	public void upLoad(MultipartFile file, String code, Integer documentTypeId) {
		try {
			String filename = file.getOriginalFilename();
			String newFileName = null;
			InputStream is = file.getInputStream();
			File sendPath = this.createDir(code, "upload");
			File tempFile = new File(sendPath + File.separator + filename);
			if (filename != null && filename != "") {
				if (tempFile.exists()) {
					int end = filename.lastIndexOf(".");
					Date date = new Date();
					String beforeName = filename.substring(0, end);
					beforeName = beforeName + "_" + this.convert(date);
					String backName = filename
							.substring(end, filename.length());
					newFileName = beforeName + "." + backName;

				} else {
					newFileName = filename;
				}
				// saveDocument(newFileName,invoiceid,documentTypeId);
				FileOutputStream fos = new FileOutputStream(sendPath
						+ File.separator + newFileName);
				byte[] buffer = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/***********************************************************************************************
	 * @public String convert(Date date){
	 * @param:Date
	 * @description:date转换为string
	 * @author:魏诚 2014/06/18
	 * @version:V1.0.1
	 * @modify:
	 ***********************************************************************************************/
	public String convert(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String convertDate = sdf.format(date);
		return convertDate;
	}


	/***********************************************************************************************
	 * @public Map<String, File> getListFile(String path) throws Exception
	 * @param:
	 * @description:获取指定文件夹文件目录列表
	 * @author:魏诚 2014/06/18
	 * @version:V1.0.1
	 * @modify:
	 ***********************************************************************************************/
	public Map<String, File> getListFile(String path) {
		//System.out.println(path);
		File file = new File(path);
		Map<String, File> fileMap = new HashMap<String, File>();
		if (file.isDirectory()) {
			File[] list = file.listFiles();
			for (int i = 0; i < list.length; i++) {
				list = file.listFiles();
				fileMap.put(list[i].getName().toString(), list[i]);
			}

		}
        
		return fileMap;

	}

	/** 
	 * 删除单个文件 
	 * @param   sPath    被删除文件的文件名 
	 * @return 单个文件删除成功返回true，否则返回false 
	 */  
	public boolean deleteFile(String sPath) {  
	   boolean flag = false;  
	    File file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}  
	
	/***********************************************************************************************
	 * @public downLoad(File tempFile,String fileName,HttpServletResponse response)
	 * @param:
	 * @description:下载文件
	 * @author:李德 2015/01/06
	 * @version:
	 * @modify:
	 ***********************************************************************************************/	
	public void downLoad(File tempFile,String fileName,HttpServletResponse response){
		response.setContentType("application/txt;charset=UTF-8");
		
    	FileInputStream in = null;
    	OutputStream o = null;
    	try {
    		byte b[] = new byte[1024]; 
       	  	in = new FileInputStream(tempFile);  
       	  	o = response.getOutputStream();  
       	  	response.setContentType("application/x-doc"); 
       	  	response.setHeader("Content-disposition", "attachment; filename="+ URLEncoder.encode(fileName+".txt", "UTF-8"));
       	  	// 指定下载的文件名 
       	  	response.setHeader("Content_Length",String.valueOf(tempFile.length())); // download the file.
   	        int n = 0;       
   	        while ((n = in.read (b))!= -1){        
   	        	o.write(b, 0, n);   
   	        } 
    	} catch (Exception e) {
       	   e.printStackTrace();
    	}finally{
    		try {
       	  		in.close();
       	  		o.flush();
       	  		o.close();
       	   	} catch (IOException e) {
       	   		e.printStackTrace();
       	   	}
    	}
	}
	/***********************************************************************************************
	 * @public contentToTxt(File tempFile, String content)
	 * @param:
	 * @description:生成文件
	 * @author:李德 2015/01/06
	 * @version:
	 * @modify:
	 ***********************************************************************************************/	
	public  void contentToTxt(File tempFile, String content) {  
        try {   
            if (!tempFile.exists()) {    
                tempFile.createNewFile();// 不存在则创建  
            }  
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(tempFile),"gbk");      
            BufferedWriter writer=new BufferedWriter(write);          
            writer.write(content);      
            writer.close();     
        } catch (Exception e) {  
            e.printStackTrace();  
  
        }  
    }  	
	/***********************************************************************************************
	 * @public String covStr(String str,int length)
	 * @param:
	 * @description:截取数据长度
	 * @author:李德 2015/01/06
	 * @version:
	 * @modify:
	 ***********************************************************************************************/
	public String covStr(String str,int length){
		str=(str==null)?"":str;//判空
		int l=str.length();
		if(l>=length){
			return str.substring(0,length);
		}else{
			for (int i = 1; i < length-l; i++) {
				str+=" ";
			}
			return str+"";
		}
		
	}
	
	
	
}
