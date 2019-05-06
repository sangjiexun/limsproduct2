package net.gvsun.lims.service.labroom;

import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.common.JsonValueDTO;

import java.util.List;

public interface LabRoomService {

	/*************************************************************************************
	 * Description:用户管理-获取用户查询列表
	 *
	 * @author： 魏誠
	 * @date：2018-09-06
	 *************************************************************************************/
	public List<JsonValueDTO> getLabRoomListBySelect(String academyNumber, String search,String softId) ;

}