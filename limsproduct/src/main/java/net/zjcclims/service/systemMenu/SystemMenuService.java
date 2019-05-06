package net.zjcclims.service.systemMenu;

import net.zjcclims.domain.AuthorityMenu;
import net.zjcclims.domain.SystemMenu;

import java.util.List;
import java.util.Map;

public interface SystemMenuService {

    /**
     * Description 获取数据库中已有的顶级菜单权限列表
     * @param authName 权限名
     * @param acno 学院编号
     * @return 菜单权限列表
     * @author 黄保钱 2018-11-2
     */
    List<AuthorityMenu> getAuthorityMenuByNoParent(String authName, String acno);

    /**
     * Description 获取数据库中已有的一级菜单权限列表
     * @param authName 权限名
     * @param acno 学院编号
     * @return 菜单权限列表
     * @author 黄保钱 2018-11-2
     */
    Map<String, List<AuthorityMenu>> getAuthorityMenuByParent(String authName, String acno);

    /**
     * Description 获取数据库中已有的顶级菜单列表
     * @param authName 权限名
     * @param acno 学院编号
     * @return 菜单列表
     * @author 黄保钱 2018-11-2
     */
    List<SystemMenu> getSystemMenuByNoParent(String authName, String acno);

    /**
     * Description 获取数据库中已有的一级菜单列表
     * @param authName 权限名
     * @param acno 学院编号
     * @return 菜单列表
     * @author 黄保钱 2018-11-2
     */
    Map<String, List<SystemMenu>> getSystemMenuByParent1(String authName, String acno);

    /**
     * Description 获取数据库中已有的一级菜单列表
     * @param authName 权限名
     * @param acno 学院编号
     * @return 菜单列表
     * @author 黄保钱 2018-11-2
     */
    List<SystemMenu> getSystemMenuByParent(String authName, String acno);

    /**
     * Description 获取数据库中全部的顶级菜单列表
     * @return 菜单列表
     * @author 黄保钱 2018-11-2
     */
    List<SystemMenu> getAllSystemMenuByNoParent();

    /**
     * Description 获取数据库中全部的一级菜单列表
     * @return 菜单列表
     * @author 黄保钱 2018-11-2
     */
    Map<String, List<SystemMenu>> getAllSystemMenuByParent();

    /**
     * Description 通过权限和学院获取已有的菜单权限
     * @param authName 权限名
     * @param acno 学院编号
     * @return 菜单权限列表
     * @author 黄保钱 2018-11-2
     */
    List<AuthorityMenu> getAuthorityMenuByAuth(String authName, String acno);

    /**
     * Description 通过id获得菜单
     * @param id id
     * @return 菜单
     * @author 黄保钱 2018-11-2
     */
    SystemMenu getSystemMenuById(Integer id);
}
