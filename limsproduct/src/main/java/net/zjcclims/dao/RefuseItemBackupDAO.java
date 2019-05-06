package net.zjcclims.dao;

import net.zjcclims.domain.RefuseItemBackup;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface RefuseItemBackupDAO extends JpaDao<RefuseItemBackup> {

    @Transactional
    public Set<RefuseItemBackup> findAllRefuseItemBackups() throws DataAccessException;

    @Transactional
    public Set<RefuseItemBackup> findAllRefuseItemBackups(int startResult, int maxRows) throws DataAccessException;

    @Transactional
    public RefuseItemBackup findRefuseItemBackupById(Integer id) throws DataAccessException;

    @Transactional
    public RefuseItemBackup findRefuseItemBackupByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

    @Transactional
    public Set<RefuseItemBackup> findRefuseItemBackupByBusinessId(String businessId) throws DataAccessException;

    @Transactional
    public Set<RefuseItemBackup> findRefuseItemBackupByBusinessId(String businessId, int startResult, int maxRows) throws DataAccessException;

    @Transactional
    public Set<RefuseItemBackup> findRefuseItemBackupByType(String type) throws DataAccessException;

    @Transactional
    public Set<RefuseItemBackup> findRefuseItemBackupByType(String type, int startResult, int maxRows) throws DataAccessException;

    @Transactional
    public Set<RefuseItemBackup> findRefuseItemBackupByTerm(Integer term) throws DataAccessException;

    @Transactional
    public Set<RefuseItemBackup> findRefuseItemBackupByTerm(Integer term, int startResult, int maxRows) throws DataAccessException;
}
