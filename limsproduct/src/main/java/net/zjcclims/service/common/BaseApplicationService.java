package net.zjcclims.service.common;

import net.zjcclims.domain.User;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.List;

import java.util.Map;
public interface BaseApplicationService {

	/*
	 * 分页显示
	 */
	public Map<String, Integer> getPage(int pageSize, int currPage,
                                        int totalRecords);

	/*
	 * Load all existing Task entities
	 */

	/*
	 * 导出电子表格
	 */
	public void exportExcel(List<Map> list, String title, String[] hearders,
                            String[] fields, HttpServletRequest request,
                            HttpServletResponse response) throws Exception;

	/**
	 * 登录人 作者：魏诚 2014.04.28
	 */
	public Map getLogMap();

	/**
	 * 获取登录人的所有信息 作者：魏诚 2014.04.28
	 */

	public User getUser();

	/***********************************************************************************************
	 * @public File createDir(String code,String fileName) throws Exception{
	 * @param:User user
	 * @description:根据登录用户返回逗号分隔的权限字符串
	 * @author:魏诚 2014/06/11
	 * @version:V1.0.1
	 * @modify:
	 ***********************************************************************************************/
	@Transactional
	public File createDir(String code, String fileName) throws Exception;


	/***********************************************************************************************
	 * @public String convert(Date date){
	 * @param:Date
	 * @description:date转换为string
	 * @author:魏诚 2014/06/18
	 * @version:V1.0.1
	 * @modify:
	 ***********************************************************************************************/
	public String convert(Date date);

	/***********************************************************************************************
	 * @public Map<String, File> getListFile(String path) throws Exception
	 * @param:
	 * @description:获取指定文件夹文件目录列表
	 * @author:魏诚 2014/06/18
	 * @version:V1.0.1
	 * @modify:
	 ***********************************************************************************************/
	public Map<String, File> getListFile(String path);

	/**
	 * 删除单个文件
	 * @param   sPath    被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public boolean deleteFile(String sPath);
	/***********************************************************************************************
	 * @public downLoad(File tempFile,String fileName,HttpServletResponse response)
	 * @param:
	 * @description:下载文件
	 * @author:李德 2015/01/06
	 * @version:
	 * @modify:
	 ***********************************************************************************************/
	public void downLoad(File tempFile, String fileName, HttpServletResponse response);
	/***********************************************************************************************
	 * @public contentToTxt(File tempFile, String content)
	 * @param:
	 * @description:生成文件
	 * @author:李德 2015/01/06
	 * @version:
	 * @modify:
	 ***********************************************************************************************/
	public  void contentToTxt(File tempFile, String content);
	/***********************************************************************************************
	 * @public String covStr(String str,int length)
	 * @param:
	 * @description:截取数据长度
	 * @author:李德 2015/01/06
	 * @version:
	 * @modify:
	 ***********************************************************************************************/
	public String covStr(String str, int length);
}
