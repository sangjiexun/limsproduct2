package net.zjcclims.service.system;

import net.zjcclims.dao.User2DAO;
import net.zjcclims.dao.UserDAO;
import net.zjcclims.domain.User;
import net.zjcclims.domain.User2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;


public class User2DetailServiceImpl implements User2DetailService{
    @Autowired
    private User2DAO user2DAO;
    @PersistenceContext
    private EntityManager entityManager;
    /*************************************************************************************
     * @內容：获取用户的总记录数
     * @作者： 叶明盾
     * @日期：2014-08-14
     *************************************************************************************

    /*************************************************************************************
     * @內容：获取用户的总记录数
     * @作者： 叶明盾
     * @日期：2014-08-14
     *************************************************************************************/
    @Transactional
    public int getUserTotalRecords(String number){
        String sql="select count(u) from User2 u where 1=1";
        return  ((Long) user2DAO.createQuerySingleResult(sql).getSingleResult()).intValue();
    }

    /*************************************************************************************
     * @內容：查找所有的用户信息
     * @作者： 叶明盾
     * @日期：2014-08-14
     *************************************************************************************/
    public List<User2> findAllUser2s(int curr, int size){
        //利用sql语句从用户表中查找出所有的用户，并赋给StringBuffer类型的sb变量
        StringBuffer sb= new StringBuffer("select * from User2  ");
        //给语句添加分页机制
        List<User2> users=user2DAO.executeQuery(sb.toString(), curr*size, size);
        return users;
    }
    public List<User2> findAllUsers() {
        String sql = "select username from `user2` ";
        return user2DAO.executeQuery(sql,20,20);
    }
}
