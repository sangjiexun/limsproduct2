package net.gvsun.lims.api.software;

import net.gvsun.lims.dto.common.JsonValueDTO;
import net.gvsun.lims.dto.common.SelectDTO;
import net.gvsun.lims.service.labroom.LabRoomService;
import net.gvsun.lims.service.school.SchoolCourseStudentService;
import net.gvsun.lims.service.software.SoftwareService;
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
@Controller("apiSoftwareController")
@SessionAttributes("selected_academy")
@RequestMapping("/lims/api/software")
public class ApiSoftwareController<JsonResult> {
    @Autowired
    private SchoolCourseStudentService schoolCourseStudentService;
    @Autowired
    private LabRoomService labRoomService;
    @Autowired
    private SoftwareService softwareService;

    /**************************************************************************
     * Description 教务排课-查看课程库列表-获取数据
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiSoftWareListBySelect")
    public SelectDTO apiSoftWareListBySelect(String academyNumber, String search) {
        //获取查询课程库列表
        List<JsonValueDTO> jsonValueDTOs = softwareService.getSoftwareListBySelect(academyNumber,search);
        SelectDTO selectDTO = new SelectDTO();
        selectDTO.setResults(jsonValueDTOs);
        return selectDTO;
    }
}
