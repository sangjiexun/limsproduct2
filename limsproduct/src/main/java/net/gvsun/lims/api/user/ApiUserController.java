package net.gvsun.lims.api.user;

import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.common.JsonValueDTO;
import net.gvsun.lims.dto.common.SelectDTO;
import net.gvsun.lims.service.school.SchoolCourseStudentService;
import net.gvsun.lims.service.user.UserService;
import org.json.JSONObject;
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
@Controller("apiUserController")
@SessionAttributes("selected_academy")
@RequestMapping("/lims/api/user")
public class ApiUserController<JsonResult> {
    @Autowired
    private SchoolCourseStudentService schoolCourseStudentService;
    @Autowired
    private UserService userService;

    /**************************************************************************
     * Description 教务排课-查看课程库列表-获取数据
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiUserDetail")
    public BaseDTO apiUserDetail(String username) {
        //获取查询课程库列表
        BaseDTO baseVo = userService.getUserByUsername(username);
        JSONObject json = new JSONObject();
        try {
            json.put("rows", baseVo.getRows());
            json.put("total", baseVo.getTotal());
        } catch (Exception e) {
        }
        return baseVo;
    }

    /**************************************************************************
     * Description 教务排课-查看课程库列表-获取数据
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiUserListBySearch")
    public BaseDTO apiSchoolCourseListByPage(String userRole, String search) {
        //获取查询课程库列表
        BaseDTO baseVo = userService.getUserListBySearch(userRole,search);
        JSONObject json = new JSONObject();
        try {
            json.put("rows", baseVo.getRows());
            json.put("total", baseVo.getTotal());
        } catch (Exception e) {
        }
        return baseVo;
    }

    /**************************************************************************
     * Description 教务排课-查看课程库列表-获取数据
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiUserListBySelect")
    public SelectDTO apiUserListBySelect(String userRole, String search) {
        //获取查询课程库列表
        List<JsonValueDTO> jsonValueDTOs = userService.getUserListBySelect(userRole,search);
        SelectDTO SelectDTO = new SelectDTO();
        SelectDTO.setResults(jsonValueDTOs);
        return SelectDTO;
    }
}
