package net.zjcclims.service.operation;

import api.net.gvsunlims.constant.ConstantInterface;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabCenterService;
import net.zjcclims.service.system.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("OutlineService")
public class OutlineServiceImpl implements OutlineService {
    @Autowired private OperationOutlineDAO operationOutlineDAO;
    @Autowired private SchoolCourseInfoDAO schoolCourseInfoDAO;
    @Autowired private COperationOutlineCreditDAO cOperationOutlineCreditDAO;
    @Autowired private OperationItemDAO operationItemDAO;
    @Autowired private SchoolMajorDAO schoolMajorDAO;
    @Autowired private CDictionaryDAO cDictionaryDAO;

    @Autowired private LabCenterService labCenterService;
    @Autowired private OperationService operationService;
    @Autowired private ShareService shareService;

    /**
     * Description 实验大纲查询
     * @param outline
     * @param currpage
     * @return
     * @author 陈乐为 2018-9-14
     */
    @Override
    public List<OperationOutline> findOutlineByQuery(OperationOutline outline, int currpage, int pagesize) {
        StringBuffer hql = new StringBuffer("select c from OperationOutline c where 1=1");
        if(!EmptyUtil.isObjectEmpty(outline)) {
            if(!EmptyUtil.isStringEmpty(outline.getLabOutlineName())) {
                hql.append(" and c.labOutlineName like '%"+ outline.getLabOutlineName() +"%'");
            }
            if(!EmptyUtil.isObjectEmpty(outline.getSchoolAcademy()) && !EmptyUtil.isStringEmpty(outline.getSchoolAcademy().getAcademyNumber())) {
                hql.append(" and c.schoolAcademy.academyNumber = '"+ outline.getSchoolAcademy().getAcademyNumber() +"'");
            }
        }
        List<OperationOutline> outlines = operationOutlineDAO.executeQuery(hql.toString(), (currpage-1)*pagesize, pagesize);

        return outlines;
    }

    /**
     * Description 实验大纲查询数量
     * @param outline
     * @return
     * @author 陈乐为 2018-9-14
     */
    @Override
    public int findOutlineByQueryCount(OperationOutline outline) {
        StringBuffer hql = new StringBuffer("select count(*) from OperationOutline c where 1=1");
        if(!EmptyUtil.isObjectEmpty(outline)) {
            if(!EmptyUtil.isStringEmpty(outline.getLabOutlineName())) {
                hql.append(" and c.labOutlineName like '%"+ outline.getLabOutlineName() +"%'");
            }
            if(!EmptyUtil.isObjectEmpty(outline.getSchoolAcademy()) && !EmptyUtil.isStringEmpty(outline.getSchoolAcademy().getAcademyNumber())) {
                hql.append(" and c.schoolAcademy.academyNumber = '"+ outline.getSchoolAcademy().getAcademyNumber() +"'");
            }
        }

        return ((Long)operationOutlineDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
    }

    /**
     * Description 查找对象
     * @param idKey
     * @return
     * @author 陈乐为 2018-9-14
     */
    @Override
    public OperationOutline findOutlineByPrimaryKey(Integer idKey) {
        return operationOutlineDAO.findOperationOutlineByPrimaryKey(idKey);
    }

    /**
     * Description 获取学院课程
     * @param acno
     * @return
     * @author 陈乐为 2018-9-14
     */
    @Override
    public Map getSchoolCourseInfo(String acno) {
        Map maps = new HashMap();
        SchoolAcademy academy = null;
        if(!acno.equals("-1")) {
            academy = shareService.findSchoolAcademyByPrimaryKey(acno);
        }
        String sql = "select c from SchoolCourseInfo c where 1=1 and c.academyNumber = '"+ academy.getAcademyNumber() +"'";
        List<SchoolCourseInfo> courseInfos = schoolCourseInfoDAO.executeQuery(sql, 0, -1);
        for(SchoolCourseInfo courseInfo : courseInfos) {
            maps.put(courseInfo.getCourseNumber(), courseInfo.getCourseName()+" "+courseInfo.getCourseNumber());
        }
        return maps;
    }

    /**
     * Description 大纲课程学分
     * @return
     * @author 陈乐为 2018-9-14
     */
    @Override
    public Map getOutlineCreditMap() {
        Map outlineCredit=new HashMap();
        for (COperationOutlineCredit it : cOperationOutlineCreditDAO.findAllCOperationOutlineCredits()) {
            outlineCredit.put(it.getId(), it.getCredit());
        }
        return outlineCredit;
    }

    /**
     * Description 保存大纲
     * @param operationOutline
     * @param schoolMajors
     * @param properties 课程性质
     * @param items 项目
     * @return
     * @author 陈乐为 2018-9-17
     */
    @Override
    public OperationOutline saveOutline(OperationOutline operationOutline,
                                        String[] schoolMajors,String[] properties,String[] items, String[] teachers) {
        if(EmptyUtil.isObjectEmpty(operationOutline.getSchoolCourseInfoByFollowUpCourses()) ||
                (EmptyUtil.isStringEmpty(operationOutline.getSchoolCourseInfoByFollowUpCourses().getCourseNumber()))){
            operationOutline.setSchoolCourseInfoByFollowUpCourses(null);
        }
        if(EmptyUtil.isObjectEmpty(operationOutline.getSchoolCourseInfoByFirstCourses()) ||
                EmptyUtil.isStringEmpty(operationOutline.getSchoolCourseInfoByFirstCourses().getCourseNumber())){
            operationOutline.setSchoolCourseInfoByFirstCourses(null);
        }
        OperationOutline o = operationOutlineDAO.store(operationOutline);
        if(schoolMajors!=null ){
            operationService.saveSystemMajor(o.getId(),schoolMajors);
        }
        if(teachers!=null){
            operationService.saveOperationOuterlineTeacher(o.getId(),teachers);
        }
        if(properties!=null ){
            operationService.saveoperationoutlineproperty(o.getId(),properties);
        }
        if(items!=null && !items.equals("") && items.length>0  ){
            operationService.saveoperationoutlineitems(o.getId(),items);
        }

        return o;
    }

    /**
     * Description 查询项目
     * @param str
     * @return
     * @author 陈乐为 2018-9-17
     */
    @Override
    public List<OperationItem> getSaveGenerateperations(String str) {
        String sql="select s from OperationItem s where 1=1  and s.id!=0 ";
        if(!EmptyUtil.isStringEmpty(str)){
            sql+=" and (s.lpName  like '%"+str+"%' or s.lpCodeCustom like '%"+str+"%')";
        }

        return operationItemDAO.executeQuery(sql);
    }
    /**
     * Description 查询大纲（不分页）
     * @return
     * @author 廖文辉 2018-10-29
     */
    public List<OperationOutline> getOperationOutlineNoPage(){
        String sql =" select l from OperationOutline l where 1=1";
        List<OperationOutline> operationOutlines =operationOutlineDAO.executeQuery(sql,0,-1);
        return operationOutlines;
    }
}