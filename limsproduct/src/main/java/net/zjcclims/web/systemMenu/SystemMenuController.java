package net.zjcclims.web.systemMenu;

import net.zjcclims.dao.AuthorityDAO;
import net.zjcclims.dao.AuthorityMenuDAO;
import net.zjcclims.dao.SchoolAcademyDAO;
import net.zjcclims.domain.Authority;
import net.zjcclims.domain.AuthorityMenu;
import net.zjcclims.domain.SchoolAcademy;
import net.zjcclims.domain.SystemMenu;
import net.zjcclims.service.system.SystemAuthorityService;
import net.zjcclims.service.system.showAcademyAuthority;
import net.zjcclims.service.systemMenu.SystemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller("SystemMenuController")
@SessionAttributes("selected_academy")
@RequestMapping("/systemMenu")
public class SystemMenuController<JsonResult> {

    @Autowired
    private SystemAuthorityService systemAuthorityService;

    @Autowired
    private SystemMenuService systemMenuService;

    @Autowired
    private AuthorityDAO authorityDAO;

    @Autowired
    private AuthorityMenuDAO authorityMenuDAO;

    @Autowired
    private SchoolAcademyDAO schoolAcademyDAO;

    /**
     * Description 列出所有可调整菜单的权限列表
     * @param currpage 页数
     * @return 列表界面
     * @author 黄保钱 2018-11-2
     */
    @RequestMapping("/listAuthorityMenus")
    public ModelAndView listAuthorityMenus(@RequestParam Integer currpage,  @ModelAttribute("selected_academy") String acno, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        //判断当前登录人是否为超级管理员
        int isaut=1;
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_SUPERADMIN") != -1 ) {
            isaut = 0;
        }
        mav.addObject("isaut", isaut);
        // 获取所有权限
        List<showAcademyAuthority> AllUserAuthority = systemAuthorityService.getUserTotalRecords(acno,request);
        mav.addObject("AllUserAuthority", AllUserAuthority);
        mav.setViewName("/systemMenu/listAuthorityMenus.jsp");
        return mav;
    }

    /**
     * Description 编辑权限下的左侧栏菜单
     * @param name 权限名称
     * @param acno 所在学院
     * @param request 请求（用于传参）
     * @return 编辑界面
     * @author 黄保钱 2018-11-2
     */
    @RequestMapping("/editAuthorityMenu")
    public ModelAndView editAuthorityMenu(@RequestParam String name, @ModelAttribute("selected_academy") String acno, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        // 获取权限
        Authority authority = authorityDAO.findAuthorityByName(name);
        mav.addObject("authority", authority);
        // 获取学院
        String acNumber = request.getParameter("academyNumber") == null ? acno : request.getParameter("academyNumber");
        // 当前学院
        mav.addObject("selAcno", acNumber);
        // 通过权限名和学院查找数据库中的顶级栏目和一级栏目
        List<SystemMenu> authorityMenuParent = systemMenuService.getSystemMenuByNoParent(name, acNumber);
        List<SystemMenu> authorityMenuChild = systemMenuService.getSystemMenuByParent(name, acNumber);
        // 转换成id
        List<Integer> ids = new ArrayList<>();
        for (SystemMenu s : authorityMenuParent) {
            ids.add(s.getId());
        }
        List<Integer> idd = new ArrayList<>();
        for (SystemMenu s : authorityMenuChild) {
            idd.add(s.getId());
        }
        // 传到页面上做显示用
        mav.addObject("authorityMenuParent", ids);
        mav.addObject("authorityMenuChild", idd);

        // 查找所有顶级栏目和一级栏目用于编辑
        List<SystemMenu> allSystemMenuParent = systemMenuService.getAllSystemMenuByNoParent();
        Map<String, List<SystemMenu>> allSystemMenuChild = systemMenuService.getAllSystemMenuByParent();
        mav.addObject("allSystemMenuParent", allSystemMenuParent);
        mav.addObject("allSystemMenuChild", allSystemMenuChild);

        // 获取所有学院
        List<SchoolAcademy> schoolAcademies = new ArrayList<>(schoolAcademyDAO.findAllSchoolAcademys());
        mav.addObject("schoolAcademies", schoolAcademies);

        mav.setViewName("/systemMenu/editAuthorityMenu.jsp");
        return mav;
    }

    /**
     * Description 保存左侧栏菜单
     * @param request 请求（用于传参）
     * @return 重定向回权限列表
     * @author 黄保钱 2018-11-2
     */
    @RequestMapping("/saveSystemMenu")
    public String saveSystemMenu(HttpServletRequest request) {
        // 获取选择的顶级栏目
        String[] parents = request.getParameterValues("allSelected");
        // 获取学院编号
        String acNumber = request.getParameter("academyNumber");
        List<Integer> saves = new ArrayList<>();
        if (parents != null) {
            // 遍历顶级栏目列表获取子栏目
            for (String parent : parents) {
                // 将顶级栏目加入要保存栏目的列表
                saves.add(Integer.parseInt(parent));
                String[] children = request.getParameterValues("warningType_" + parent);
                if (children != null) {
                    // 遍历一级栏目列表加入要保存栏目的列表
                    for (String child : children) {
                        saves.add(Integer.parseInt(child));
                    }
                }
            }
        }
        // 获取权限名
        String authName = request.getParameter("authName");
        // 获取数据库的菜单列表做对比
        List<AuthorityMenu> authorityMenus = systemMenuService.getAuthorityMenuByAuth(authName, acNumber);
        // 遍历数据库的菜单列表与要保存的列表做对比
        for (AuthorityMenu authorityMenu : authorityMenus) {
            // 如果数据库有而且要保存的有，则从要保存的列表中移去，不做操作
            if (saves.contains(authorityMenu.getSystemMenu().getId())) {
                saves.remove(authorityMenu.getSystemMenu().getId());
            }
            // 如果数据库有，而要保存的没有，则从数据库中删除
            else {
                authorityMenuDAO.remove(authorityMenu);
            }
        }
        // 剩下的则是需要新建保存的
        for (Integer i : saves) {
            AuthorityMenu authorityMenu = new AuthorityMenu();
            SystemMenu systemMenu = systemMenuService.getSystemMenuById(i);
            authorityMenu.setSystemMenu(systemMenu);
            authorityMenu.setAuthority(authorityDAO.findAuthorityByName(authName));
            authorityMenu.setAcademyNumber(acNumber);
            authorityMenuDAO.store(authorityMenu);
        }
        authorityMenuDAO.flush();
        return "redirect:listAuthorityMenus?currpage=1";
    }

}
