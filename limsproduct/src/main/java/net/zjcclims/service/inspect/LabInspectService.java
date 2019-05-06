package net.zjcclims.service.inspect;

import net.zjcclims.domain.*;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface LabInspectService {
    /**
     * Description 获取所有设置项目
     * @return
     * @author 陈乐为 2018-11-1
     */
    public List<LabInspect> findAllLabInspects();

    /**
     * Description 根据批次主键查找评价项目
     * @param idKey
     * @return
     * @author 陈乐为 2018-11-1
     */
    public List<LabInspect> findLabInspectByBatchId(Integer idKey);

    /**
     * Description 根据批次查找批次下的项目
     * @param idKey
     * @return
     * @author 陈乐为 2018-11-1
     */
    public List<MInspectSetting> findMInspectSettingByBatchId(Integer idKey);

    /**
     * Description 删除
     * @param mInspectSetting
     * @author 陈乐为 2018-11-1
     */
    @Transactional
    public void deleteMInspectSetting(MInspectSetting mInspectSetting);

    /**
     * Description 保存批次项目设置
     * @param mInspectSetting
     * @author 陈乐为 2018-11-1
     */
    @Transactional
    public void saveMInspectSetting(MInspectSetting mInspectSetting);

    /**
     * Description 评价管理-查询评价总数
     * @param commonDocument
     * @param request
     * @return
     * @author 陈乐为 2018-11-1
     */
    public int findAlldocumentByQueryCount(CommonDocument commonDocument, HttpServletRequest request);

    /**
     * Description 实验室评价模块-评价管理-学生查询自己的评价
     * @param username
     * @param currpage
     * @param pageSize
     * @param commonDocument
     * @param request
     * @return
     * @author 陈乐为 2018-11-1
     */
    public List<Object[]> findMyLabRoomCommentByQuery(String username,int currpage,
                                                      int pageSize, CommonDocument commonDocument,HttpServletRequest request);

    /**
     * Description 根据查到LinkedHashSet的数据库表，判断当前时间是否属于可上传时间（可以则返回true，不可以则返回false）
     * @param date
     * @param labTimes
     * @return
     * @author 陈乐为 2018-11-1
     */
    public boolean isTimeForEvaluation(Date date, Set<LabInspectSetting> labTimes);

    /**
     * Description 根据当前时间查询评价批次
     * @param date
     * @param batchs
     * @return
     * @throws DataAccessException
     * @author 陈乐为 2018-11-1
     */
    public LabInspectSetting findBatchByNow(Date date, Set<LabInspectSetting> batchs)throws DataAccessException;

    /**
     * Description 保存评价结果
     * @param standardId
     * @param point
     * @param batchId
     * @param labRoomId
     * @param documentId
     * @return
     * @author 陈乐为 2018-11-1
     */
    public LabInspectGrading saveGrading(Integer standardId, Integer point,
                                         Integer batchId, Integer labRoomId, Integer documentId);

    /**
     * Description 查询根数列表
     * @param documentId
     * @return
     * @author 陈乐为 2018-11-1
     */
    public List<Object> findGradings(Integer documentId);

}