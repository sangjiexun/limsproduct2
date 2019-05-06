package net.zjcclims.dao;

import net.zjcclims.domain.AuditRefuseBackup;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.util.Set;

public interface AuditRefuseBackupDAO extends JpaDao<AuditRefuseBackup> {

    public Set<AuditRefuseBackup> findAllAuditRefuseBackups() throws DataAccessException;

    public Set<AuditRefuseBackup> findAllAuditRefuseBackups(int startResult, int maxRows) throws DataAccessException;

    public AuditRefuseBackup findAuditRefuseBackupById(Integer id) throws DataAccessException;

    public AuditRefuseBackup findAuditRefuseBackupById(Integer id, int startResult, int maxRows) throws DataAccessException;

    public AuditRefuseBackup findAuditRefuseBackupByPrimaryKey(Integer id) throws DataAccessException;

    public AuditRefuseBackup findAuditRefuseBackupByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException;

    public Set<AuditRefuseBackup> findAuditRefuseBackupByAuditInfo(String auditInfo) throws DataAccessException;

    public Set<AuditRefuseBackup> findAuditRefuseBackupByAuditInfo(String auditInfo, int startResult, int maxRows) throws DataAccessException;

    public Set<AuditRefuseBackup> findAuditRefuseBackupByAuditInfoContaining(String auditInfo) throws DataAccessException;

    public Set<AuditRefuseBackup> findAuditRefuseBackupByAuditInfoContaining(String auditInfo, int startResult, int maxRows) throws DataAccessException;

    public Set<AuditRefuseBackup> findAuditRefuseBackupByAuditContent(String auditContent) throws DataAccessException;

    public Set<AuditRefuseBackup> findAuditRefuseBackupByAuditContent(String auditContent, int startResult, int maxRows) throws DataAccessException;

    public Set<AuditRefuseBackup> findAuditRefuseBackupByAuditContentContaining(String auditContent) throws DataAccessException;

    public Set<AuditRefuseBackup> findAuditRefuseBackupByAuditContentContaining(String auditContent, int startResult, int maxRows) throws DataAccessException;


}
