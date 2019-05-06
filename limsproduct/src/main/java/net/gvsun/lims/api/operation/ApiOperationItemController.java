package net.gvsun.lims.api.operation;

import net.gvsun.lims.dto.common.JsonValueDTO;
import net.gvsun.lims.dto.common.SelectDTO;
import net.gvsun.lims.service.labroom.LabRoomService;
import net.gvsun.lims.service.operation.OperationItemService;
import net.gvsun.lims.service.school.SchoolCourseStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

/****************************************************************************
 * Descriptions：教务排课管理模块
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ****************************************************************************/
@Controller("apiOperationItemController")
@SessionAttributes("selected_academy")
@RequestMapping("/lims/api/operation")
public class ApiOperationItemController<JsonResult> {
    @Autowired
    private OperationItemService operationItemService;

    /**************************************************************************
     * Description 教务排课-查看课程库列表-获取数据
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiOperationItemListBySelect")
    public SelectDTO apiOperationItemListBySelect(String academyNumber,String courseNumber, String search) {
        //获取查询课程库列表
        List<JsonValueDTO> jsonValueDTOs = operationItemService.getOperationItemListBySelect(academyNumber,courseNumber,search);
        SelectDTO selectDTO = new SelectDTO();
        selectDTO.setResults(jsonValueDTOs);
        return selectDTO;
    }
}
