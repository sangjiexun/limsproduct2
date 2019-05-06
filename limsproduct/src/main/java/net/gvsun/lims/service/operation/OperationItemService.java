package net.gvsun.lims.service.operation;

import net.gvsun.lims.dto.common.JsonValueDTO;

import java.util.List;

public interface OperationItemService {

	/*************************************************************************************
	 * Description:用户管理-获取实验项目查询列表
	 *
	 * @author： 魏誠
	 * @date：2018-09-06
	 *************************************************************************************/
	public List<JsonValueDTO> getOperationItemListBySelect(String academyNumber,String courseNumber, String search) ;
}