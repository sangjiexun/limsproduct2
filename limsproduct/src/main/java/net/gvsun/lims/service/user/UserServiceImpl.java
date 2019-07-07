package net.gvsun.lims.service.user;

import api.net.gvsunlims.constant.ConstantInterface;
import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.common.JsonValueDTO;
import net.gvsun.lims.dto.user.AuthorityDTO;
import net.gvsun.lims.dto.user.UserDTO;
import net.zjcclims.dao.UserDAO;
import net.zjcclims.domain.Authority;
import net.zjcclims.domain.User;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ShareService shareService;

    /*************************************************************************************
     * Description:用户管理-获取用户详细信息
     *
     * @author： 魏誠
     * @date：2018-09-06
     *************************************************************************************/
    public BaseDTO getUserByUsername(String username) {
        UserDTO userDTO = new UserDTO();
        User user = userDAO.findUserByPrimaryKey(username);
        //设置学院
        userDTO.setAcademyName(user.getSchoolAcademy().getAcademyName());
        userDTO.setAcademyNumber(user.getSchoolAcademy().getAcademyNumber());
        //设置卡号
        userDTO.setCardno(user.getCardno());
        //设置姓名
        userDTO.setCname(user.getCname());
        //设置学号
        userDTO.setUsername(user.getUsername());
        userDTO.setUserRole(user.getUserRole());
        //获取权限
        List<AuthorityDTO> authorityDTOs = new ArrayList<AuthorityDTO>();
        for (Authority authority : user.getAuthorities()) {
            AuthorityDTO authorityDTO = new AuthorityDTO();
            authorityDTO.setAuthorityName(authority.getAuthorityName());
            authorityDTO.setCname(authority.getCname());
            authorityDTO.setId(authority.getId());
            authorityDTO.setType(authority.getType());
            authorityDTOs.add(authorityDTO);
        }
        userDTO.setAuthorityDTOs(authorityDTOs);
        List<UserDTO> userDTOs = new ArrayList<UserDTO>();
        userDTOs.add(userDTO);
        BaseDTO baseVO = new BaseDTO();
        baseVO.setRows(userDTOs);
        baseVO.setTotal(1);
        return baseVO;
    }

    /*************************************************************************************
     * Description:用户管理-获取用户查询列表
     *
     * @author： 魏誠
     * @date：2018-09-06
     *************************************************************************************/
    public BaseDTO getUserListBySearch(String userRole, String search) {
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select c from User c where  c.userRole like '" + userRole +"'" );
        //查询条件
        if (search != null && !"".equals(search)) {
            sql.append(" and (c.username like '%" + search + "%' or");
            sql.append(" c.cname like '%" + search + "%')");
        }
        sql.append(" order by c.cname asc limit " + ConstantInterface.COMMON_SELECT_LIMIT);
        // 执行sb语句
        List<User> users = userDAO.executeQuery(sql.toString());
        int total =users.size();
        //封装VO
        List<UserDTO> userDTOs = new ArrayList<UserDTO>();
        for(User user:users){
            UserDTO userDTO = new UserDTO();
            //设置学院
            userDTO.setAcademyName(Objects.nonNull(user.getSchoolAcademy())?user.getSchoolAcademy().getAcademyName():null);
            userDTO.setAcademyNumber(Objects.nonNull(user.getSchoolAcademy())?user.getSchoolAcademy().getAcademyNumber():null);
            //设置卡号
            userDTO.setCardno(user.getCardno());
            //设置姓名
            userDTO.setCname(user.getCname());
            //设置学号
            userDTO.setUsername(user.getUsername());
            userDTO.setUserRole(user.getUserRole());
            //获取权限
            List<AuthorityDTO> authorityDTOs = new ArrayList<AuthorityDTO>();
            for (Authority authority : user.getAuthorities()) {
                AuthorityDTO authorityDTO = new AuthorityDTO();
                authorityDTO.setAuthorityName(authority.getAuthorityName());
                authorityDTO.setCname(authority.getCname());
                authorityDTO.setId(authority.getId());
                authorityDTO.setType(authority.getType());
                authorityDTOs.add(authorityDTO);
            }
            userDTO.setAuthorityDTOs(authorityDTOs);
            userDTOs.add(userDTO);
        }

        BaseDTO baseVO = new BaseDTO();
        baseVO.setRows(userDTOs);
        baseVO.setTotal(1);
        return baseVO;
    }

    /*************************************************************************************
     * Description:用户管理-获取用户查询列表
     *
     * @author： 魏誠
     * @date：2018-09-06
     *************************************************************************************/
    public List<JsonValueDTO> getUserListBySelect(String userRole, String search) {
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select c from User c where  c.userRole like '" + userRole +"'" );
        //查询条件
        sql.append(" and c.schoolAcademy.academyNumber like '"+ shareService.getUser().getSchoolAcademy().getAcademyNumber() +"'");
        if (search != null && !"".equals(search)) {
            sql.append(" and (c.username like '%" + search + "%' or");
            sql.append(" c.cname like '%" + search + "%' or");
            sql.append(" c.schoolAcademy.academyName like '%" + search + "%')");
        }
        sql.append(" order by c.cname asc limit " + ConstantInterface.COMMON_SELECT_LIMIT);
        // 执行sb语句
        List<User> users = userDAO.executeQuery(sql.toString(),0,ConstantInterface.COMMON_SELECT_LIMIT);

        //获取列表主查询语句
        StringBuffer sql_1 = new StringBuffer("select c from User c where  c.userRole like '" + userRole +"'" );
        //查询条件
        sql_1.append(" and c.schoolAcademy.academyNumber not like '"+ shareService.getUser().getSchoolAcademy().getAcademyNumber() +"'");
        if (search != null && !"".equals(search)) {
            sql_1.append(" and (c.username like '%" + search + "%' or");
            sql_1.append(" c.cname like '%" + search + "%' or");
            sql_1.append(" c.schoolAcademy.academyName like '%" + search + "%')");
        }
        sql_1.append(" order by c.cname asc limit " + ConstantInterface.COMMON_SELECT_LIMIT);
        // 执行sb语句
        List<User> user1s = userDAO.executeQuery(sql_1.toString(),0,ConstantInterface.COMMON_SELECT_LIMIT);
        users.addAll(user1s);
        int total =users.size();
        //封装VO
        List<JsonValueDTO> userDTOs = new ArrayList<JsonValueDTO>();
        for(User user:users){
            JsonValueDTO jsonValueDTO = new JsonValueDTO();
            //设置学院
            jsonValueDTO.setId(user.getUsername());
            String academyName = Objects.nonNull(user.getSchoolAcademy())?user.getSchoolAcademy().getAcademyName():null;
            jsonValueDTO.setText(user.getCname()+"(学号："+user.getUsername()+"；  学院："+academyName +")");
            userDTOs.add(jsonValueDTO);
        }
        return userDTOs;
    }


    /*************************************************************************************
     * Description:用户管理-获取指定学院的系主任
     *
     * @author： Hezhaoyi
     * @date：2019-7-7
     *************************************************************************************/
    public List<User> findDeansByAcademyNumber(String academyNumber) {
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("SELECT u FROM User u join u.authorities a WHERE (a.id=9 or a.id=17) and u.academy_number= "+academyNumber );

        sql.append(" order by u.cname asc");
        // 执行sb语句
        List<User> users = userDAO.executeQuery(sql.toString());

        return users;
    }

    /*************************************************************************************
     * Description:用户管理-根据权限名称查询用户
     *
     * @author： Hezhaoyi
     * @date：2019-7-7
     *************************************************************************************/
    public List<User> findUserByAuthorityName(String authorityName) {
        //获取列表主查询语句
        String sql = "select u from User u join u.authorities a where 1=1";
        sql += " and a.authorityName = '" + authorityName + "'";
        sql += " order by u.cname asc";
        // 执行sb语句
        List<User> users = userDAO.executeQuery(sql);

        return users;
    }



}