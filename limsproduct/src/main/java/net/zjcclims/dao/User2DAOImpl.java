package net.zjcclims.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;


import net.zjcclims.domain.User2;
import org.skyway.spring.util.dao.AbstractJpaDao;

import org.springframework.dao.DataAccessException;

import org.springframework.transaction.annotation.Transactional;

public class User2DAOImpl extends AbstractJpaDao<User2> implements User2DAO {
    private EntityManager entityManager;
    private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { User2.class }));
    public EntityManager getEntityManager() {
        return entityManager;
    }
    public Set<Class<?>> getTypes() {
        return dataTypes;
    }
    public boolean canBeMerged(User2 entity) {
        return true;
    }
    @Transactional
    public Set<User2> findUserbyUsername(String username) throws DataAccessException {

        return findUserbyUsername(username);
    }
    public Set<User2> findAllUsers(int startResult, int maxRows)throws DataAccessException {
        Query query = createNamedQuery("findAllUser2s", startResult, maxRows);
        return new LinkedHashSet<User2>(query.getResultList());
    }
}
