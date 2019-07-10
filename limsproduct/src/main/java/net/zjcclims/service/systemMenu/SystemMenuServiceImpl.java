package net.zjcclims.service.systemMenu;

import net.gvsun.lims.dto.common.PConfigDTO;
import net.zjcclims.dao.AuthorityMenuDAO;
import net.zjcclims.dao.SystemMenuDAO;
import net.zjcclims.domain.AuthorityMenu;
import net.zjcclims.domain.SystemMenu;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("SystemMenuService")
public class SystemMenuServiceImpl implements SystemMenuService {

    @Autowired
    private SystemMenuDAO systemMenuDAO;

    @Autowired
    private AuthorityMenuDAO authorityMenuDAO;

    @Autowired
    private ShareService shareService;


    /**
     * Description 获取数据库中已有的顶级菜单权限列表
     * @param authName 权限名
     * @param acno 学院编号
     * @return 菜单权限列表
     * @author 黄保钱 2018-11-2
     */
    @Override
    public List<AuthorityMenu> getAuthorityMenuByNoParent(String authName, String acno){
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
        String sql = "select distinct am from AuthorityMenu am where 1=1 ";
        sql += " and am.authority.authorityName like '" + authName + "' ";
        sql += " and am.systemMenu.parentMenu is null ";
        sql += " and am.systemMenu.isUsed = 1 ";
        sql += " and am.systemMenu.projectName like '" + pConfigDTO.PROJECT_NAME + "'";
        sql += " and (am.academyNumber like '" + acno + "' or am.academyNumber like '-1')";
        List<AuthorityMenu> list = authorityMenuDAO.executeQuery(sql);
        return list;
    }

    /**
     * Description 获取数据库中已有的一级菜单权限列表
     * @param authName 权限名
     * @param acno 学院编号
     * @return 菜单权限列表
     * @author 黄保钱 2018-11-2
     */
    @Override
    public Map<String, List<AuthorityMenu>> getAuthorityMenuByParent(String authName, String acno){
        List<AuthorityMenu> authorityMenus = getAuthorityMenuByNoParent(authName, acno);
        Map<String, List<AuthorityMenu>> map = new LinkedHashMap<>();
        for(AuthorityMenu authorityMenu: authorityMenus){
            String sql = "select distinct ams from AuthorityMenu am  ";
            sql += " join am.systemMenu.childMenus ch ";
            sql += " join ch.authorityMenus ams ";
            sql += " where ams.authority.authorityName like '" + authName + "'";
            sql += " and ch.isUsed = 1";
            sql += " and am.systemMenu.id = " + authorityMenu.getSystemMenu().getIdentification() + "'";
            sql += " and (am.academyNumber like '" + acno + "' or am.academyNumber like '-1')";
            List<AuthorityMenu> list = authorityMenuDAO.executeQuery(sql);
            map.put(authorityMenu.getSystemMenu().getIdentification(), list);
        }
        return map;
    }

    /**
     * Description 获取数据库中已有的顶级菜单列表
     * @param authName 权限名
     * @param acno 学院编号
     * @return 菜单列表
     * @author 黄保钱 2018-11-2
     */
    @Override
    public List<SystemMenu> getSystemMenuByNoParent(String authName, String acno){
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
        String sql = "select distinct sm from SystemMenu sm " +
                " join sm.authorityMenus aus " +
                " where 1=1 ";
        sql += " and aus.authority.authorityName like '" + authName + "' ";
        sql += " and sm.parentMenu is null ";
        sql += " and sm.isUsed = 1 ";
        sql += " and sm.projectName like '" + pConfigDTO.PROJECT_NAME + "'";
        sql += " and (aus.academyNumber like '" + acno + "')";
        List<SystemMenu> list = systemMenuDAO.executeQuery(sql);
        return list;
    }

    /**
     * Description 获取数据库中已有的一级菜单列表
     * @param authName 权限名
     * @param acno 学院编号
     * @return 菜单列表
     * @author 黄保钱 2018-11-2
     */
    @Override
    public Map<String, List<SystemMenu>> getSystemMenuByParent1(String authName, String acno){
        List<SystemMenu> systemMenus = getSystemMenuByNoParent(authName, acno);
        Map<String, List<SystemMenu>> map = new LinkedHashMap<>();
        for(SystemMenu systemMenu: systemMenus){
            String sql = "select distinct ch from AuthorityMenu am  ";
            sql += " join am.systemMenu.childMenus ch ";
            sql += " join ch.authorityMenus ams ";
            sql += " where ams.authority.authorityName like '" + authName + "'";
            sql += " and ch.isUsed = 1";
            sql += " and am.systemMenu.identification like '" + systemMenu.getIdentification() + "'";
            sql += " and (ams.academyNumber like '" + acno + "')";
            List<SystemMenu> list = systemMenuDAO.executeQuery(sql);
            map.put(systemMenu.getIdentification(), list);
        }
        return map;
    }

    /**
     * Description 获取数据库中已有的一级菜单列表
     * @param authName 权限名
     * @param acno 学院编号
     * @return 菜单列表
     * @author 黄保钱 2018-11-2
     */
    @Override
    public List<SystemMenu> getSystemMenuByParent(String authName, String acno){
        List<SystemMenu> systemMenus = getSystemMenuByNoParent(authName, acno);
        List<SystemMenu> childs = new ArrayList<>();
        for(SystemMenu systemMenu: systemMenus){
            String sql = "select distinct ch from AuthorityMenu am  ";
            sql += " join am.systemMenu.childMenus ch ";
            sql += " join ch.authorityMenus ams ";
            sql += " where ams.authority.authorityName like '" + authName + "'";
            sql += " and ch.isUsed = 1";
            sql += " and am.systemMenu.identification like '" + systemMenu.getIdentification() + "'";
            sql += " and (ams.academyNumber like '" + acno + "')";
            List<SystemMenu> list = systemMenuDAO.executeQuery(sql);
            childs.addAll(list);
        }
        return childs;
    }

    /**
     * Description 获取数据库中全部的顶级菜单列表
     * @return 菜单列表
     * @author 黄保钱 2018-11-2
     */
    @Override
    public List<SystemMenu> getAllSystemMenuByNoParent() {
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
        String sql = "select distinct sm from SystemMenu sm " +
                " where 1=1 ";
        sql += " and sm.parentMenu is null ";
        sql += " and sm.isUsed = 1 ";
        sql += " and sm.projectName like '" + pConfigDTO.PROJECT_NAME + "'";
        List<SystemMenu> systemMenuList = systemMenuDAO.executeQuery(sql);
        return systemMenuList;
    }

    /**
     * Description 获取数据库中全部的一级菜单列表
     * @return 菜单列表
     * @author 黄保钱 2018-11-2
     */
    @Override
    public Map<String, List<SystemMenu>> getAllSystemMenuByParent() {
        List<SystemMenu> systemMenus = getAllSystemMenuByNoParent();
        Map<String, List<SystemMenu>> map = new LinkedHashMap<>();
        for(SystemMenu systemMenu: systemMenus){
            String sql = "select distinct ch from SystemMenu sm  ";
            sql += " join sm.childMenus ch ";
            sql += " where 1=1 ";
            sql += " and ch.isUsed = 1";
            sql += " and sm.identification like '" + systemMenu.getIdentification() + "'";
            List<SystemMenu> list = systemMenuDAO.executeQuery(sql);
            map.put(systemMenu.getIdentification(), list);
        }
        return map;
    }

    /**
     * Description 通过权限和学院获取已有的菜单权限
     * @param authName 权限名
     * @param acno 学院编号
     * @return 菜单权限列表
     * @author 黄保钱 2018-11-2
     */
    @Override
    public List<AuthorityMenu> getAuthorityMenuByAuth(String authName, String acno){
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
        String sql = "select distinct am from AuthorityMenu am where 1=1 ";
        sql += " and am.authority.authorityName like '" + authName + "' ";
        sql += " and am.systemMenu.isUsed = 1 ";
        sql += " and am.systemMenu.projectName like '" + pConfigDTO.PROJECT_NAME + "'";
        sql += " and (am.academyNumber like '" + acno + "')";
        List<AuthorityMenu> list = authorityMenuDAO.executeQuery(sql);
        return list;
    }

    /**
     * Description 通过id获得菜单
     * @param id id
     * @return 菜单
     * @author 黄保钱 2018-11-2
     */
    @Override
    public SystemMenu getSystemMenuById(Integer id){
        return systemMenuDAO.findSystemMenuById(id);
    }
}
