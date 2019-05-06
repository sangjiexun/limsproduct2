package net.gvsun.lims.web.reservation.lab;

import net.gvsun.lims.dto.labConstruction.GrandSonProjectDTO;
import net.gvsun.lims.dto.labConstruction.ParentProjectDTO;
import net.gvsun.lims.dto.labConstruction.SonProjectDTO;
import net.gvsun.lims.service.labConstruction.LabConstructionProjectService;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/****************************************************************************
 * Descriptions：实验室建设
 *
 * @作者：魏好
 * @时间：2019-01-21
 ****************************************************************************/
@Controller("labConstructionController")
@RequestMapping("/lims/construction/lab")
public class LabConstructionController {
    /************************************************************
     * @初始化WebDataBinder，这个WebDataBinder用于填充被@InitBinder注释的处理 方法的command和form对象
     *
     ************************************************************/
    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register
        binder.registerCustomEditor(Calendar.class,
                new org.skyway.spring.util.databinding.CustomCalendarEditor());
        binder.registerCustomEditor(byte[].class,
                new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
        binder.registerCustomEditor(boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(false));
        binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
        binder.registerCustomEditor(java.math.BigDecimal.class,
                new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
        binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
                Integer.class, true));
        binder.registerCustomEditor(Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
        binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
        binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
                Long.class, true));
        binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
                Double.class, true));
    }

    private String CONSTRUCTION_URL = "lims/reservation/lab/construction/";

    @Autowired
    private LabConstructionProjectService labConstructionProjectService;

    @Autowired
    private ShareService shareService;

    /**
     * 父项目
     **/

    @RequestMapping("/parentProject")
    public ModelAndView parentProject(ModelAndView mav) {
        mav.setViewName(this.CONSTRUCTION_URL + "parentProject.jsp");
        return mav;
    }

    @RequestMapping("/editParentProject")
    public ModelAndView editParentProject(ModelAndView mav, HttpServletRequest request) {
        if(request.getParameter("id") != null && !"".equals(request.getParameter("id"))) {
            Integer id = Integer.valueOf(request.getParameter("id"));
            ParentProjectDTO parentProjectDTO = labConstructionProjectService.getParentProjectById(id);
            mav.addObject("parentProject", parentProjectDTO);
            mav.addObject("isEdit", 1);
        }

        mav.setViewName(this.CONSTRUCTION_URL + "editParentProject.jsp");
        return mav;
    }

    @RequestMapping("/detailParentProject")
    public ModelAndView detailParentProject(ModelAndView mav, HttpServletRequest request) {
        Integer id = Integer.valueOf(request.getParameter("id"));
        ParentProjectDTO parentProjectDTO = labConstructionProjectService.getParentProjectById(id);
        mav.addObject("parentProject", parentProjectDTO);


        mav.setViewName(this.CONSTRUCTION_URL + "detailParentProject.jsp");
        return mav;
    }

    /**
     * 子项目
     **/

    @RequestMapping("/sonProject")
    public ModelAndView sonProject(ModelAndView mav) {
        List<ParentProjectDTO> parentProjectDTOS = labConstructionProjectService.getParentProjectsForSonProject();
        mav.addObject("parentProjects", parentProjectDTOS);

        mav.setViewName(this.CONSTRUCTION_URL + "sonProject.jsp");
        return mav;
    }

    @RequestMapping("/editSonProject")
    public ModelAndView editSonProject(ModelAndView mav, HttpServletRequest request) {
        if(request.getParameter("id") != null && !"".equals(request.getParameter("id"))) {
            Integer id = Integer.valueOf(request.getParameter("id"));
            SonProjectDTO sonProjectDTO = labConstructionProjectService.getSonProjectById(id);
            mav.addObject("sonProject", sonProjectDTO);
            mav.addObject("isEdit", 1);
        }
        if(request.getParameter("parentId") != null) {
            Integer parentId = Integer.valueOf(request.getParameter("parentId"));
            mav.addObject("parentId", parentId);
        }
        List<ParentProjectDTO> parentProjectDTOS = labConstructionProjectService.getParentProjectsForSonProject();
        mav.addObject("parentProjects", parentProjectDTOS);
        mav.addObject("academies", shareService.findAllSchoolAcademys());

        mav.setViewName(this.CONSTRUCTION_URL + "editSonProject.jsp");
        return mav;
    }

    @RequestMapping("/detailSonProject")
    public ModelAndView detailSonProject(ModelAndView mav, HttpServletRequest request) {
        Integer id = Integer.valueOf(request.getParameter("id"));
        SonProjectDTO sonProjectDTO = labConstructionProjectService.getSonProjectById(id);
        mav.addObject("sonProject", sonProjectDTO);

        mav.setViewName(this.CONSTRUCTION_URL + "detailSonProject.jsp");
        return mav;
    }

    /**
     * 孙项目
     **/

    @RequestMapping("/grandSonProject")
    public ModelAndView grandSonProject(ModelAndView mav) {
        List<ParentProjectDTO> parentProjectDTOS = labConstructionProjectService.getParentProjectsForGrandSonProject();
        mav.addObject("parentProjects", parentProjectDTOS);

        // 未审核
        mav.addObject("notAudit", labConstructionProjectService.getGrandSonProjectsCount(0));
        // 审核中
        mav.addObject("auditing", labConstructionProjectService.getGrandSonProjectsCount(1));
        // 已经审核
        mav.addObject("audited", labConstructionProjectService.getGrandSonProjectsCount(2));

        mav.setViewName(this.CONSTRUCTION_URL + "grandSonProject.jsp");
        return mav;
    }

    @RequestMapping("/examineGrandsonProject")
    public ModelAndView examineGrandsonProject(ModelAndView mav, HttpServletRequest request) {
        Integer id = Integer.valueOf(request.getParameter("id"));
        GrandSonProjectDTO grandSonProjectDTO = labConstructionProjectService.getGrandSonProjectById(id);
        mav.addObject("grandSonProject", grandSonProjectDTO);


        mav.setViewName(this.CONSTRUCTION_URL + "examineGrandsonProject.jsp");
        return mav;
    }


    @RequestMapping("/editGrandsonProject")
    public ModelAndView editGrandsonProject(ModelAndView mav, HttpServletRequest request) {
        if(request.getParameter("id") != null && !"".equals(request.getParameter("id"))) {
            Integer id = Integer.valueOf(request.getParameter("id"));
            GrandSonProjectDTO grandSonProjectDTO = labConstructionProjectService.getGrandSonProjectById(id);
            mav.addObject("grandSonProject", grandSonProjectDTO);
            mav.addObject("isEdit", 1);
        }
        if(request.getParameter("sonId") != null) {
            Integer sonId = Integer.valueOf(request.getParameter("sonId"));
            mav.addObject("sonId", sonId);
            SonProjectDTO sonProjectDTO = labConstructionProjectService.getSonProjectById(sonId);
            mav.addObject("parentId", sonProjectDTO.getParentProjectId());
        }
        List<ParentProjectDTO> parentProjectDTOS = labConstructionProjectService.getParentProjectsForGrandSonProject();
        mav.addObject("parentProjects", parentProjectDTOS);


        mav.setViewName(this.CONSTRUCTION_URL + "editGrandsonProject.jsp");
        return mav;
    }

    @RequestMapping("/detailGrandsonProject")
    public ModelAndView detailGrandsonProject(ModelAndView mav, HttpServletRequest request) {
        Integer id = Integer.valueOf(request.getParameter("id"));
        GrandSonProjectDTO grandSonProject = labConstructionProjectService.getGrandSonProjectById(id);
        mav.addObject("grandSonProject", grandSonProject);

        mav.setViewName(this.CONSTRUCTION_URL + "detailGrandsonProject.jsp");
        return mav;
    }
}
