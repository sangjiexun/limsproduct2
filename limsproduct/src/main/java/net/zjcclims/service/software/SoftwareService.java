package net.zjcclims.service.software;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zjcclims.domain.CommonDocument;
import net.zjcclims.domain.LabRoomDevice;
import net.zjcclims.domain.LabRoomDeviceRepair;
import net.zjcclims.domain.SchoolDevice;
import net.zjcclims.domain.Software;
import net.zjcclims.domain.SoftwareItemRelated;
import net.zjcclims.domain.SoftwareReserve;
import net.zjcclims.domain.SoftwareRoomRelated;

import com.google.inject.persist.Transactional;

public interface SoftwareService {
	/****************************************************************************
	 * Description：获取软件数量
	 * 
	 * @author： 邵志峰
	 * @date: 2017-06-02
	 ****************************************************************************/
	@Transactional
	public Integer countSoftwares();
	
	/****************************************************************************
	 * Description：查找所有软件信息
	 * 
	 * @param：startResult 当前页数
	 * @param： maxRows 总页数
	 * @author: 邵志峰
	 * @date: 2017-06-02
	 ****************************************************************************/
	@Transactional
	public List<Software> findAllSoftwares(Integer startResult, Integer maxRows);
	
	/****************************************************************************
	 * description：查询Software
	 * 
	 * @author：杨礼杰
	 * @date：2017-6-23
	 ****************************************************************************/
	public List<Software> findAllSoftwareByQuery(Integer currPage, Integer pageSize, Software software);
	
	/****************************************************************************
	 * Description:根据主键查找软件信息
	 *
	 * @param： idKey 软件编号
	 * @author 邵志峰
	 * @date: 2017-06-02
	 ****************************************************************************/
	@Transactional
	public Software findSoftwareByPrimaryKey(Integer id);
	
	/****************************************************************************
	 * Description:软件管理--保存
	 * 
	 * @author 杨礼杰
	 * @date: 2017-06-23
	 ****************************************************************************/
	public Software saveSoftware(Software software);
	
	/****************************************************************************
	 * Description:软件管理--删除
	 *
	 * @author 杨礼杰
	 * @date: 2017-06-23
	 ****************************************************************************/
	public void deleteSoftware(Software software);
	/****************************************************************************
	 * Description:实训中心列表——添加软件
	 *
	 * @author 周志辉
	 * @date: 2017-08-01
	 ****************************************************************************/
	public List<Software> findSoftwareByRoomId(Integer id) ;
	/****************************************************************************
	 * 功能：根据学院编号和Software对象查询软件数量
	 * 作者：周志辉
	 * 时间：2018-08-01
	 ****************************************************************************/
	public int findSoftwareByAcademyNumberAndSoftware(
			String academyNumber, Software software,Integer roomId);
	/****************************************************************************
	 * 功能：根据学院编号和Software对象查询软件并分页
	 * 作者：周志辉
	 * 时间：2017-08-01
	 ****************************************************************************/
	public List<Software> findSoftwareByAcademyNumberAndSoftware(
			String academyNumber, Software software, Integer roomId,Integer page,
			int pageSize);
	/****************************************************************************
	 * 功能：根据id查询软件
	 * 作者：周志辉
	 * 时间：2017-08-01
	 ****************************************************************************/
	public Software findSoftwareByPrimaryKey1(Integer id);
	
	/****************************************************************************
	 * Description 根據軟件&項目查詢關係記錄
	 * 
	 * @param softwareId 軟件主鍵
	 * @param itemId 項目主鍵
	 * @return 對象
	 * @author 陳樂為
	 * @date 2017年9月9日
	 ****************************************************************************/
	public SoftwareItemRelated getItemRelatedByQuery(Integer softwareId, Integer itemId);
	
	/****************************************************************************
	 * Description 根據軟件&實訓室查詢關係記錄
	 * 
	 * @param softwareId 軟件
	 * @param roomId 實訓室主鍵
	 * @return 對象
	 * @author 陳樂為
	 * @date 2017年9月9日
	 ****************************************************************************/
	public SoftwareRoomRelated getRoomRelatedByQuery(Integer softwareId, Integer roomId);
	
	/****************************************************************************
	 * 功能：上传附件
	 * 作者：周志辉
	 ****************************************************************************/
	public void softwareDocumentUpload(HttpServletRequest request,
			HttpServletResponse response, Integer id,int type);
	
	/****************************************************************************
	 * 功能：保存实验室软件安装说明的文档
	 * 作者：周志辉
	 * 时间：2017-09-18
	 ****************************************************************************/
	public void saveSoftwareDocument(String fileTrueName, Integer softwareId,Integer type);
	
	/****************************************************************************
	 * 功能：下载软件文档
	 * 作者：周志辉
	 ****************************************************************************/
	public void downloadFile(CommonDocument doc, HttpServletRequest request,
			HttpServletResponse response);
	
	/****************************************************************************
	 * Description:查看软件安装申请
	 *
	 * @author 张愉
	 * @date: 2017-09-30
	 ****************************************************************************/
	public SoftwareReserve findSoftwareReserveById(Integer id);
	
	/****************************************************************************
	 * Description:查找实验室的软件
	 *
	 * @author 张愉
	 * @date: 2017-10-30
	 ****************************************************************************/
	public List<SoftwareRoomRelated> findSoftwareRoomRelatedByRoomId(Integer roomId);
	
	/****************************************************************************
	 * @功能：导出查询到的所有软件(分sheet导出)
	 * @作者：张愉
	 * @日期：2018-1-3
	 ****************************************************************************/
	public void exportSoftList(List<Software> listSoftware,HttpServletRequest request, HttpServletResponse response)throws Exception;
	/**************************************************************************************
     * description：导入软件
     * @author：张愉
     * @date：2018-1-3
     **************************************************************************************/
	public void importSoft(String File);
    /****************************************************************************
     * description：软件总记录
     *
     * @author：廖文辉
     * @date：2018-11-30
     ****************************************************************************/
    public List<Software> findSoftwareByQuery(Integer currPage,
                                                 Integer pageSize, Software software,HttpServletRequest request);

	/******************************************************************
	 * Description:保存软件申请表
	 * @param：softwarereserve 软件申请表
	 * @author：邵志峰
	 * @date:2017-05-31
	 *****************************************************************/
	@org.springframework.transaction.annotation.Transactional
	public SoftwareReserve saveSoftwareReserve(SoftwareReserve softwarereserve);
}