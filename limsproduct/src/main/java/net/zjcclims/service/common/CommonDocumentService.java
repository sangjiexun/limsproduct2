package net.zjcclims.service.common;

import java.util.List;

import net.zjcclims.domain.CommonDocument;
import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.SoftwareReserve;


public interface CommonDocumentService {
	/***********************************************************************************************
	 * 根据实验室id查询图片
	 * 作者：李小龙 
	 ***********************************************************************************************/
	public List<CommonDocument> findImageByLabAnnexId(Integer id);
	/***********************************************************************************************
	 * 根据主键查询图片
	 * 作者：李小龙 
	 ***********************************************************************************************/
	public CommonDocument findCommonDocumentByPrimaryKey(Integer id);
	/***********************************************************************************************
	 * 删除图片对象
	 * 作者：李小龙 
	 ***********************************************************************************************/
	public void deleteCommonDocument(CommonDocument doc);
	
	/***********************************************************************************************
	 * 根据实验室编号查询实验室图片
	 * 作者：裴继超
	 * 2016年3月14日
	 ***********************************************************************************************/
	public CommonDocument findCommonDocumentByLabRoom(LabRoom lr,int type) ;
	
	/***********************************************************************************************
	 * description:根据实验室编号查询实验室图片
	 * 
	 * author:于侃
	 * date:2016年9月21日 17:14:48
	 ***********************************************************************************************/
	public List<CommonDocument> findCommonDocumentsByLabRoom(LabRoom lr,int type);

	/****************************************************************************
	 * @Description： 通过软件安装申请查询文件
	 * @author： 张德冰
	 * @date: 2018-12-29
	 ****************************************************************************/
	public List<CommonDocument> findCommonDocumentsBySoftwareReserve(Integer softwareReserveId, int type);
	

}