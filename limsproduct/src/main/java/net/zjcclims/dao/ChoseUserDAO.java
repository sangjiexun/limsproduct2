package net.zjcclims.dao;

import net.zjcclims.domain.ChoseUser;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

public interface ChoseUserDAO extends JpaDao<ChoseUser> {

    public Set<ChoseUser> findAllChoseUsers() throws DataAccessException;

    public Set<ChoseUser> findAllChoseUsers(int startResult, int maxRows) throws DataAccessException;

    public Set<ChoseUser> findChoseUserByUsername(String username) throws DataAccessException;

    public Set<ChoseUser> findChoseUserByUsername(String username, int startResult, int maxRows) throws DataAccessException;
}
