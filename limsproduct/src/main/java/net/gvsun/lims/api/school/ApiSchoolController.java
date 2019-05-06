package net.gvsun.lims.api.school;

import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.common.JsonValueDTO;
import net.gvsun.lims.dto.common.SelectDTO;
import net.gvsun.lims.service.school.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/****************************************************************************
 * Descriptions：教务排课管理模块
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ****************************************************************************/
@Controller("apiSchoolCourseStudentController")
@SessionAttributes("selected_academy")
@RequestMapping("/lims/api/school")
public class ApiSchoolController<JsonResult> {
    @Autowired
    private SchoolCourseStudentService schoolCourseStudentService;
    @Autowired
    private SchoolCourseService schoolCourseService;
    @Autowired
    private SchoolCourseInfoService schoolCourseInfoService;
    @Autowired
    private SchoolAcademyService schoolAcademyService;
    @Autowired
    private SchoolTermService schoolTermService;


    /**************************************************************************
     * Description 教务排课-查看课程库列表-获取数据
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiSchoolCourseStudentList")
    public BaseDTO apiSchoolCourseStudentList(int termId, String courseNo,String sort, String order) {
        //获取查询课程库列表
        BaseDTO baseVo = schoolCourseStudentService.apiSchoolCourseStudentList(termId, courseNo,sort,order);
        return baseVo;
    }

    /**************************************************************************
     * Description 教务排课-查看课程库列表-获取数据
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiSchoolCourseListByPage")
    public BaseDTO apiSchoolCourseListByPage(int offset, int limit, int termId, String search,String status, String sort, String order, HttpServletRequest request) {
        //获取查询课程库列表
        BaseDTO baseVo = schoolCourseService.getSchoolCourseListByPage(termId,search, status,offset , limit, sort, order,request);
        return baseVo;
    }

    /**************************************************************************
     * Description 教务排课-查看排课管理列表-获取数据
     *
     * @author 魏诚
     * @date 2018年10月17日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiEduSchoolCourseByPage")
    public BaseDTO apiEduSchoolCourseByPage(int offset, int limit, int termId, String search,String status, String sort, String order, HttpServletRequest request) {
        //获取查询课程库列表
        BaseDTO baseVo = schoolCourseService.apiEduSchoolCourseByPage(termId,search,status, offset , limit, sort, order,request);
        return baseVo;
    }

    /**************************************************************************
     * Description 教务排课-查看课程库列表-获取数据
     *
     * @author 魏诚
     * @date 2018年10月26日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiSchoolCourseInfoListBySelect")
    public SelectDTO apiSchoolCourseInfoListBySelect(String search) {
        //获取查询课程库列表
        List<JsonValueDTO> jsonValueDTOs = schoolCourseInfoService.apiSchoolCourseInfoListBySelect(search);
        SelectDTO SelectDTO = new SelectDTO();
        SelectDTO.setResults(jsonValueDTOs);
        return SelectDTO;
    }

    /**************************************************************************
     * Description 教务排课-查看学院列表-获取数据
     *
     * @author 魏诚
     * @date 2018年10月26日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiSchoolAcademyListBySelect")
    public SelectDTO apiSchoolAcademyListBySelect(String search,@ModelAttribute("selected_academy") String acno,HttpServletRequest request) {
        //获取查询课程库列表
        List<JsonValueDTO> jsonValueDTOs = schoolAcademyService.apiSchoolAcademyListBySelect(search,acno,request);
        SelectDTO SelectDTO = new SelectDTO();
        SelectDTO.setResults(jsonValueDTOs);
        return SelectDTO;
    }

    /**************************************************************************
     * Description 教务排课-查看学期列表-获取数据
     *
     * @author 魏诚
     * @date 2018年10月26日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiSchoolTermListBySelect")
    public SelectDTO apiSchoolTermListBySelect(String search) {
        //获取查询课程库列表
        List<JsonValueDTO> jsonValueDTOs = schoolTermService.apiSchoolTermListBySelect(search);
        SelectDTO SelectDTO = new SelectDTO();
        SelectDTO.setResults(jsonValueDTOs);
        return SelectDTO;
    }

    /**************************************************************************
     * Description 教务排课-查看课程库列表-获取数据
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiSchoolCourseListByPageForRefuse")
    public BaseDTO apiSchoolCourseListByPageForRefuse(int offset, int limit, int termId, String search,String status, String sort, String order, HttpServletRequest request) {
        //获取查询课程库列表
        BaseDTO baseVo = schoolCourseService.getSchoolCourseListByPageForRefuse(termId,search, status,offset , limit, sort, order,request);
        return baseVo;
    }

    /**************************************************************************
     * Description 排课审核-获取当前审核的课程信息
     * @param termId
     * @param search
     * @return
     * @author 陈乐为 2019-1-16
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiEduSchoolCourseForAudit")
    public BaseDTO apiEduSchoolCourseForAudit(int termId, String search) {
        //获取查询课程库列表
        BaseDTO baseVo = schoolCourseService.apiEduSchoolCourseForAudit(termId,search);
        return baseVo;
    }
}
