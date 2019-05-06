package net.zjcclims.service.inspect;

import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("LabInspectService")
@Transactional
public class LabInspectServiceImpl implements LabInspectService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired private LabInspectDAO labInspectDAO;
    @Autowired private MInspectSettingDAO mInspectSettingDAO;
    @Autowired private CommonDocumentDAO commonDocumentDAO;
    @Autowired private LabInspectSettingDAO labInspectSettingDAO;
    @Autowired private LabInspectGradingDAO labInspectGradingDAO;

    /**
     * Description 获取所有设置项目
     * @return
     * @author 陈乐为 2018-11-1
     */
    @Override
    public List<LabInspect> findAllLabInspects() {
        StringBuffer hql = new StringBuffer("select p from LabInspect p");
        return labInspectDAO.executeQuery(hql.toString(), 0,-1);
    }

    /**
     * Description 根据批次主键查找评价项目
     * @param idKey
     * @return
     * @author 陈乐为 2018-11-1
     */
    @Override
    public List<LabInspect> findLabInspectByBatchId(Integer idKey) {
        // 根据批次id查询对应评价项目
        StringBuffer hql = new StringBuffer("select p from LabInspect p , MInspectSetting lrs" +
                " where lrs.labInspect.id=p.id and lrs.batchId=").append(idKey);
        return labInspectDAO.executeQuery(hql.toString(),0,-1);
    }

    /**
     * Description 根据批次查找批次下的项目
     * @param idKey
     * @return
     * @author 陈乐为 2018-11-1
     */
    @Override
    public List<MInspectSetting> findMInspectSettingByBatchId(Integer idKey) {
        StringBuffer hql=new StringBuffer("select p from MInspectSetting p where p.batchId=").append(idKey);
        return  mInspectSettingDAO.executeQuery(hql.toString(), 0,-1);
    }

    /**
     * Description 删除
     * @param mInspectSetting
     * @author 陈乐为 2018-11-1
     */
    @Transactional
    public void deleteMInspectSetting(MInspectSetting mInspectSetting) {
        mInspectSettingDAO.remove(mInspectSetting);
        mInspectSettingDAO.flush();
    }

    /**
     * Description 保存批次项目设置
     * @param mInspectSetting
     * @author 陈乐为 2018-11-1
     */
    @Transactional
    public void saveMInspectSetting(MInspectSetting mInspectSetting) {
        MInspectSetting existingMInspectSetting = mInspectSettingDAO.findMInspectSettingByPrimaryKey(mInspectSetting.getId());

        if (existingMInspectSetting != null) {
            if (existingMInspectSetting != mInspectSetting) {
                existingMInspectSetting.setId(mInspectSetting.getId());
                existingMInspectSetting.setBatchId(mInspectSetting.getBatchId());
            }
            mInspectSettingDAO.store(existingMInspectSetting);
        } else {
            mInspectSettingDAO.store(mInspectSetting);
        }
        mInspectSettingDAO.flush();
    }

    /**
     * Description 评价管理-查询评价总数
     * @param commonDocument
     * @param request
     * @return
     * @author 陈乐为 2018-11-1
     */
    @Override
    public int findAlldocumentByQueryCount(CommonDocument commonDocument, HttpServletRequest request) {
        //查询时间的范围
        String starttime= request.getParameter("starttime");
        String endtime=	request.getParameter("endtime");
        StringBuffer hql = new StringBuffer("select count(i) from CommonDocument i where type='5' ");
        //实验名称条件
        if(commonDocument.getLabRoom()!=null && !"".equals(commonDocument.getLabRoom().getLabRoomName()))
        {
            hql.append(" and i.labRoom.labRoomName like '%"+commonDocument.getLabRoom().getLabRoomName()+"%'");
        }
        //上传时间条件查询
        if(starttime!=null && starttime.length()>0 && endtime!=null&& endtime.length()>0){
            hql.append(" and i.createdAt between '"+starttime +"' and '"+endtime+"' ");
        }

        return ((Long) commonDocumentDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
    }

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
    @Override
    public List<Object[]> findMyLabRoomCommentByQuery(String username,int currpage,
                                                      int pageSize, CommonDocument commonDocument,HttpServletRequest request) {
        //上传时间的开始结束时间范围
        String starttime= request.getParameter("starttime");
        String endtime=	request.getParameter("endtime");
        StringBuffer hql = new StringBuffer("select i from CommonDocument i where type='5'");
        if(username!=null) {
            hql.append(" and created_by='"+ username +"'");
        }
        //实验室名称条件
        if(commonDocument.getLabRoom()!=null && !"".equals(commonDocument.getLabRoom().getLabRoomName()))
        {
            hql.append(" and i.labRoom.labRoomName like '%"+commonDocument.getLabRoom().getLabRoomName()+"%'");
        }
        //上传时间条件
        if(starttime!=null && starttime.length()>0 && endtime!=null&& endtime.length()>0){
            hql.append(" and i.createdAt between '"+starttime +"' and '"+endtime+"' ");
        }
        List<Object[]> list1 = new ArrayList<Object[]>();
        List<CommonDocument> list =  commonDocumentDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
        for(CommonDocument c : list){
            Object[] o = new Object[5000];
            o[0] = c.getId();
            o[1] = c.getLabRoom().getLabRoomName();
            LabInspectSetting la = labInspectSettingDAO.findLabInspectSettingByPrimaryKey(c.getSettingId());
            o[3] = c.getUser().getCname();
            o[4] = c.getUser().getUsername();
            o[5] = c.getUser().getSchoolAcademy().getAcademyName();
            o[6] = c.getCreatedAt();
            list1.add(o);
        }
        return list1;
    }

    /**
     * Description 根据查到LinkedHashSet的数据库表，判断当前时间是否属于可上传时间（可以则返回true，不可以则返回false）
     * @param date
     * @param labTimes
     * @return
     * @author 陈乐为 2018-11-1
     */
    public boolean isTimeForEvaluation(Date date, Set<LabInspectSetting> labTimes){
        for (LabInspectSetting labEvaluationTimeSetting : labTimes) {
            if(labEvaluationTimeSetting.getIsRegular()){
                int week=labEvaluationTimeSetting.getWeek();
                int ss = date.getDay();
                if(date.getDay()==1&&(week/1000000)==1){
                    return true;
                }
                if(date.getDay()==2&&(week/100000)%10==1){
                    return true;
                }
                if(date.getDay()==3&&(week/10000)%10==1){
                    return true;
                }
                if(date.getDay()==4&&(week/1000)%10==1){
                    return true;
                }
                if(date.getDay()==5&&(week/100)%10==1){
                    return true;
                }
                if(date.getDay()==6&&(week/10)%10==1){
                    return true;
                }
                if(date.getDay()==0&&(week%10==1)){
                    return true;
                }
            }else{
                if(date.after(labEvaluationTimeSetting.getStartTime().getTime())&&date.before(labEvaluationTimeSetting.getEndTime().getTime())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Description 根据当前时间查询评价批次
     * @param date
     * @param batchs
     * @return
     * @throws DataAccessException
     * @author 陈乐为 2018-11-1
     */
    @Override
    public LabInspectSetting findBatchByNow(Date date, Set<LabInspectSetting> batchs)throws DataAccessException {
        for (LabInspectSetting batch : batchs) {
            if(batch.getIsRegular()){
                int week=batch.getWeek();
                if(date.getDay()==1&&(week/1000000)==1){
                    return batch;
                }
                if(date.getDay()==2&&(week/100000)%10==1){
                    return batch;
                }
                if(date.getDay()==3&&(week/10000)%10==1){
                    return batch;
                }
                if(date.getDay()==4&&(week/1000)%10==1){
                    return batch;
                }
                if(date.getDay()==5&&(week/100)%10==1){
                    return batch;
                }
                if(date.getDay()==6&&(week/10)%10==1){
                    return batch;
                }
                if(date.getDay()==0&&(week%10==1)){
                    return batch;
                }
            }else{
                if(date.after(batch.getStartTime().getTime())&&date.before(batch.getEndTime().getTime())){
                    return batch;
                }
            }
        }
        return new LabInspectSetting();
    }

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
    @Override
    public LabInspectGrading saveGrading(Integer standardId, Integer point,
                                         Integer batchId, Integer labRoomId, Integer documentId){

        LabInspectGrading grading = new LabInspectGrading();
        grading.setBatchId(batchId);
        grading.setStandardId(standardId);
        grading.setPoint(point);
        grading.setLabRoomId(labRoomId);
        grading.setDocumentId(documentId);
        grading.setCreateTime(Calendar.getInstance());
        return labInspectGradingDAO.store(grading);
    }

    /**
     * Description 查询根数列表
     * @param documentId
     * @return
     * @author 陈乐为 2018-11-1
     */
    @Override
    public List<Object> findGradings(Integer documentId){

        String sql = "select g.point,s.standard_name from lab_inspect_grading g";
        sql += " left join lab_inspect as s on s.id = g.standard_id ";
        sql += " where g.document_id = " + documentId;
        sql += " group by s.id ";
        sql += " order by s.id";
        System.out.println(sql);
        List<Object> objects = entityManager.createNativeQuery(sql).getResultList();
        return objects;
    }

}