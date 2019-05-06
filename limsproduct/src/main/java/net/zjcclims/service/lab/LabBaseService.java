package net.zjcclims.service.lab;

import java.util.List;
import java.util.Map;

import net.zjcclims.domain.LabAnnex;
import net.zjcclims.domain.LabRoom;

import javax.servlet.http.HttpServletRequest;

public interface LabBaseService {

	/***************************** 
	*Description 根据基地主键查找基地对象
	*
	*@author:余南新
	*@param:
	*@date:2017.4.25
	*****************************/
	public LabAnnex findLabAnnexByPrimaryKey(Integer labBaseId);

	/**
	 *Description 获取所有的基地
	 *@author 陈乐为 2018-8-6
	 */
	public List<LabAnnex> findAllLabBaseByQuery(LabAnnex labAnnex, Integer currpage, Integer pageSize, HttpServletRequest request);

	/**
	 * Description 实验基地总数
	 * @param labAnnex
	 * @return
	 * @author 陈乐为 2018-8-6
	 */
	public int getAllLabBaseByQueryCount(LabAnnex labAnnex, HttpServletRequest request);

	/***************************** 
	*Description 保存基地数据
	*
	*@author:余南新
	*@param:
	*@date:2017.4.25
	*****************************/
	public LabAnnex saveLabAnnex(LabAnnex labAnnex,HttpServletRequest request);

	/**
	 * Description 删除基地
	 * @author 陈乐为 2018-8-6
	 */
	public boolean deleteLabAnnex(Integer labAnnexId);
	/**
	 * Description 基地下实验室数
	 * @param labAnnex
	 * @return
	 * @author 廖文辉 2018-11-15
	 */
	public int findLabRoomByQueryCount(LabAnnex labAnnex);
	/**
	 * Description 基地下实验室
	 * @param labAnnex
	 * @param page
	 *  @param pageSize
	 * @return
	 * @author 廖文辉 2018-11-15
	 */
	public List<LabRoom> findLabRoomByQuery(LabAnnex labAnnex, int page,
											int pageSize);
}
