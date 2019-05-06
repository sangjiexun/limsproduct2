package net.zjcclims.service.construction;



import net.zjcclims.domain.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for VirtualLabConstruction entities
 * 
 */
public interface VirtualLabConstructionService {

	/**
	 * Load an existing VirtualLabConstruction entity
	 * 
	 */
	public Set<VirtualLabConstruction> loadVirtualLabConstructions();

	/**
	 * Save an existing LabRoomDevice entity
	 * 
	 */
	public VirtualLabConstruction saveVirtualLabConstructionLabRoomDevice(Integer id, LabRoomDevice related_labroomdevice);

	/**
	 * Save an existing VirtualLabConstruction entity
	 * 
	 */
	public void saveVirtualLabConstruction(VirtualLabConstruction virtuallabconstruction);

	/**
	 * Delete an existing SchoolAcademy entity
	 * 
	 */
	public VirtualLabConstruction deleteVirtualLabConstructionSchoolAcademy(Integer virtuallabconstruction_id, String related_schoolacademy_academyNumber);

	/**
	 * Save an existing LabRoomProject entity
	 * 
	 */
	public VirtualLabConstruction saveVirtualLabConstructionLabRoomProject(Integer id_1, LabRoomProject related_labroomproject);

	/**
	 * Return all VirtualLabConstruction entity
	 * 
	 */
	public List<VirtualLabConstruction> findAllVirtualLabConstructions(Integer startResult, Integer maxRows);

	/**
	 * Delete an existing SystemRoom entity
	 * 
	 */
	public VirtualLabConstruction deleteVirtualLabConstructionSystemRoom(Integer virtuallabconstruction_id_1, String related_systemroom_roomNumber);

	/**
	 * Delete an existing VirtualLabConstruction entity
	 * 
	 */
	public void deleteVirtualLabConstruction(VirtualLabConstruction virtuallabconstruction_1);

	/**
	 * Return a count of all VirtualLabConstruction entity
	 * 
	 */
	public Integer countVirtualLabConstructions();

	/**
	 * Save an existing SchoolAcademy entity
	 * 
	 */
	public VirtualLabConstruction saveVirtualLabConstructionSchoolAcademy(Integer id_2, SchoolAcademy related_schoolacademy);

	/**
	 * Save an existing SystemRoom entity
	 * 
	 */
	public VirtualLabConstruction saveVirtualLabConstructionSystemRoom(Integer id_3, SystemRoom related_systemroom);

	/**
	 */
	//public VirtualLabConstruction findVirtualLabConstructionByPrimaryKey(Integer id_4);

	/**
	 * Delete an existing LabRoomDevice entity
	 * 
	 */
	public VirtualLabConstruction deleteVirtualLabConstructionLabRoomDevice(Integer virtuallabconstruction_id_2, Integer related_labroomdevice_id);

	/**
	 * Delete an existing LabRoomProject entity
	 * 
	 */
	public VirtualLabConstruction deleteVirtualLabConstructionLabRoomProject(Integer virtuallabconstruction_id_3, Integer related_labroomproject_id);
	
	/****************************************************************************
	 * 功能：根据实验中心信息查询实验中心
	 * 作者：李德
	 ****************************************************************************/
	public List<VirtualLabConstruction> findAllVirtualLabByVirtualLab(VirtualLabConstruction virtualLabConstruction);
	/****************************************************************************
	 * 功能：根据实验中心信息查询实验中心并分页
	 * 作者：李德
	 ****************************************************************************/
	public List<VirtualLabConstruction> findAllVirtualLabByVirtualLab(VirtualLabConstruction virtualLabConstruction,
                                                                      int page, int pageSize);
	/****************************************************************************
	 * 功能：查询所有的实验中心
	 * 作者：李德
	 ****************************************************************************/
	public Set<VirtualLabConstruction> findAllVirtualLabConstruction();
	/****************************************************************************
	 * 功能：根据主键查询实验中心
	 * 作者：李德
	 ****************************************************************************/
	public VirtualLabConstruction findVirtualLabConstructionByPrimaryKey(Integer Id);

	/***********************************************************************************************
	 * public void exportExcelCar(String name,String servBegin) {
	 *
	 * @param:
	 * @description:根据条件导出到电子表格
	 * @author:李德
	 * @version:
	 * @modify:
	 ***********************************************************************************************/
	public void exportExcelVirtualLab(List<VirtualLabConstruction> virtualLabConstructions, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception;

	/****************************************************************************
	 * 功能：根据实验室id查询实验分室并分页
	 * 作者：李德
	 * 时间：2015-03-19
	 * @param labRoom
	 ****************************************************************************/
	public List<LabRoom> findLabRoomByAll(LabRoom labRoom, Integer page, int pageSize);

	/****************************************************************************
	 * 功能：根据实验室id查询实验分室
	 * 作者：李德
	 * 时间：2015-03-19
	 * @param labRoom
	 ****************************************************************************/
	public List<LabRoom> findLabRoomByVirtualLab(LabRoom labRoom);

	/****************************************************************************
	 * 功能：保存实验室对象
	 * 作者：李德
	 * 时间：2015-04-09
	 ****************************************************************************/
	public VirtualLabConstruction save(VirtualLabConstruction virtualLabConstruction);

	/****************************************************************************
	 * 功能：根据条件导出到电子表格
	 * 作者：李德
	 * 时间：2015-03-19
	 ****************************************************************************/
	public void exportExcelLabRoom(List<LabRoom> labRooms, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception;

	/***********************************************************************************************
	 * 功能:根据条件导出到电子表格
	 * 作者:李德
	 * 时间：2015-03-19
	 ***********************************************************************************************/
	public void exportExcelExperimentUser(List<User> user, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception;

	/****************************************************************************
	 * 功能：查询部门名称
	 * 作者：李德
	 * 时间：2015-4-9
	 ****************************************************************************/

	public Set<SchoolMajor> findAllSchoolMajor();

	/***********************************************************************************************
	 * 功能:根据条件导出到电子表格
	 * 作者:李德
	 * 时间：2015-03-19
	 ***********************************************************************************************/
	public void exportExcelVirtualLabTest(List<VirtualLabConstruction> virtualLabConstructions, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception;

	/***********************************************************************************************
	 * 功能：上传实验实训项目附件，并返回文档字符串信息
	 * 作者：李德
	 * 时间：2015-04-24
	 ***********************************************************************************************/
	public String uploadFile(Integer id, HttpServletRequest request,
                             HttpServletResponse response);

	/****************************************************************************
	 * 功能：根据验收申请ID查询实验实训项目附件
	 * 作者：李德
	 * 时间：2015-04-24
	 ****************************************************************************/
	public List<CommonDocument> findCommonDocumentByOperationItemId(int idKey);

	/***********************************************************************************************
	 * 功能：根据实验实训项目id删除文档(暂不删除服务器上的文件)
	 * 作者：李德
	 * 时间：2015-04-24
	 ***********************************************************************************************/
	public void deleteOperationItemCommonDocumentByOperationItemId(Integer idKey);

 	/***********************************************************************************************
	 * 功能：查找所有的实验实训项目卡数
	 * 作者：李德
	 * 时间：2015-04-27
	 ***********************************************************************************************/
	public int getOperationItemExperiment(OperationItem operationItem, String acno);

 	/***********************************************************************************************
	 * 功能：查找所有的实验实训项目卡数
	 * 作者：李德
	 * 时间：2015-04-27
	 ***********************************************************************************************/
	public List<OperationItem> getOperationItemExperimentpage(OperationItem operationItem, int currpage, int pagesize, String acno);

 	/***********************************************************************************************
	 * 功能：查找所有的实习项目卡数
	 * 作者：李德
	 * 时间：2015-04-27
	 ***********************************************************************************************/
	public int  getOperationItemPractice(OperationItem operationItem, String acno);

 	/***********************************************************************************************
	 * 功能：查找所有的实验实训项目卡数
	 * 作者：李德
	 * 时间：2015-04-27
	 ***********************************************************************************************/
	public List<OperationItem> getOperationItemPracticepage(OperationItem operationItem, int currpage, int pagesize, String acno);
	/***********************************************************************************************
	 * 功能:根据条件导出到电子表格
	 * 作者:李德
	 * 时间：2015-04-29
	 ***********************************************************************************************/
	public void exportExcelLabconstructAnnex(List<LabAnnex> labAnnexs, HttpServletRequest request,
                                             HttpServletResponse response) throws Exception;

	/****************************************************************************
	 * 功能：根据中心id和LabCenterid查询该中心的实验室
	 * 作者:李德
	 * 时间：2015-04-29
	 ****************************************************************************/
	public List<LabRoom> findLabRoomByLabAnnexId(Integer labAnnexid);
	
	/****************************************************************************
	 * 功能：根据中心id和labRoomId查询该中心的实验室
	 * 作者:李德
	 * 时间：2015-04-29
	 ****************************************************************************/
	public List<LabRoomDevice> findLabRoomDeviceByLabRoomId(Integer labRoomId);
	
	/****************************************************************************
	 * 功能：根据schoolAcademyNo查询专业
	 * 作者:李德
	 * 时间：2015-04-29
	 ****************************************************************************/
	public List<SchoolMajor> findSchoolMajorSchoolAcademyId(String schoolAcademyNo);
	/****************************************************************************
	 * 功能：根据labRoomId查询实验项目
	 * 作者:李德
	 * 时间：2015-09-17
	 ****************************************************************************/	
	public List<OperationItem> findOperationItem(int labRoomId);

}