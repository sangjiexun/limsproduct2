package net.zjcclims.service.routineInspection;

import excelTools.ExcelUtils;
import excelTools.JsGridReportBase;
import excelTools.TableData;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.service.system.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("LabSecurityCheckService")
@Transactional
public class LabSecurityCheckServiceImpl implements LabSecurityCheckService {
    @Autowired
    private ShareService shareService;
    @Autowired
    private LabRoomService labRoomService;
    @Autowired
    private LabRoomDAO labRoomDAO;
    @Autowired
    private LabSecurityCheckDAO labSecurityCheckDAO;
    @Autowired
    private SDictionaryDAO sDictionaryDAO;
    @Autowired
    private LabSecurityCheckDetailsDAO labSecurityCheckDetailsDAO;
    @Autowired
    private SystemService systemService;
    @Autowired
    private LabCenterDAO labCenterDAO;


    /*********************************************************************
     * @内容：安全责任体系检查{统计符合条件的安全检查记录的总数}
     * @作者：赵昶
     * @日期：2017-9-20
     *********************************************************************/
    public int count(HttpServletRequest request, LabSecurityCheck labSecurityCheck, String acno) {
        String hql = "";

        //实验室管理员获取所属的实验室
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_LABMANAGER") != -1) {
            hql += "select a from LabSecurityCheck a,LabRoomAdmin la where la.labRoom.id=a.labRoom.id and la.user.username='" + shareService.getUser().getUsername() + "'";
        } else {
            hql += "select a from LabSecurityCheck a where 1=1";
            // 判断是否有超级管理员、运行与安全管理科身份
            if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_SUPERADMIN") == -1 &&
                    request.getSession().getAttribute("selected_role").toString().indexOf("OPEARTIONSECURITYMANAGEMENT") == -1) {
                String userId = shareService.getUser().getUsername();
                hql += " and a.user.username = '" + userId + "' ";
            }
        }
        //是当前学期的
        String term = "";
        term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getTermName();
        hql += " and a.schoolTerm.termName ='" + term + "'";

        //是否有查询
        //传来的对象week有值代表是查询，增加查询条件
        if (labSecurityCheck.getMonth() != null && !"".equals(labSecurityCheck.getMonth())) {
            hql += " and a.month ='" + labSecurityCheck.getMonth() + "'";
        }
        //传来的对象labCenter有值代表是查询，增加查询条件
        if (labSecurityCheck.getLabCenter() != null && labSecurityCheck.getLabCenter().getId() != null) {
            hql += " and a.labCenter.id ="+ labSecurityCheck.getLabCenter().getId();
        }
        if (acno != null && !acno.equals("")) {
            hql += " and a.labCenter.schoolAcademy.academyNumber =" + acno;
        }
        List<LabSecurityCheck> list = labSecurityCheckDAO.executeQuery(hql, 0, -1);
        int RecordCount = list.size();
        return RecordCount;
    }

    /*********************************************************************
     * @内容：安全责任体系检查{统计符合条件的安全检查记录}
     * @作者：赵昶
     * @日期：2017-9-20
     *********************************************************************/
    @Override
    public List<LabSecurityCheck> findlabSecurityChecks(HttpServletRequest request, Integer currpage,
                                                        Integer pageSize, LabSecurityCheck labSecurityCheck, String acno) {
        String hql = "";

        //实验室管理员获取所属的实验室
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_LABMANAGER") != -1) {
            hql += "select a from LabSecurityCheck a,LabRoomAdmin la where la.labRoom.id=a.labRoom.id and la.user.username='" + shareService.getUser().getUsername() + "'";
        } else {
            hql += "select a from LabSecurityCheck a where 1=1";
            // 判断是否有超级管理员、运行与安全管理科身份
            if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_SUPERADMIN") == -1 &&
                    request.getSession().getAttribute("selected_role").toString().indexOf("OPEARTIONSECURITYMANAGEMENT") == -1) {
                String userId = shareService.getUser().getUsername();
                hql += " and a.user.username = '" + userId + "' ";
            }
        }

        //是当前学期的
        String term = "";
        term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getTermName();
        hql += " and a.schoolTerm.termName =" + "'" + term + "'";


        //是否有查询
        //传来的对象week有值代表是查询，增加查询条件
        if (labSecurityCheck.getMonth() != null && !"".equals(labSecurityCheck.getMonth())) {
            hql += " and a.month =" + "'" + labSecurityCheck.getMonth() + "'";
        }
        //传来的对象labCenter有值代表是查询，增加查询条件
        if (labSecurityCheck.getLabCenter() != null && labSecurityCheck.getLabCenter().getId() != null) {
            hql += " and a.labCenter.id ="+ labSecurityCheck.getLabCenter().getId();
        }
        if (acno != null && !acno.equals("")) {
            hql += " and a.labCenter.schoolAcademy.academyNumber =" + acno;
        }
        List<LabSecurityCheck> labSecurityChecks = labSecurityCheckDAO.executeQuery(hql, (currpage - 1) * pageSize, pageSize);
        System.out.println(hql);
        return labSecurityChecks;
    }

    /*********************************************************************
     * @内容：安全责任体系检查-新建-保存
     * @作者：赵昶
     * @日期：2017-9-21
     *********************************************************************/
    @Override
    public LabSecurityCheck saveLabSecurityCheck(
            LabSecurityCheck labSecurityCheck) {
        return labSecurityCheckDAO.store(labSecurityCheck);
    }

    /*********************************************************************
     * @内容：查询字典表哪一类的哪一项的所有记录{types判断取是否取二级标题和三级标题（1），还是只取三级标题（2）}
     * @作者：赵昶
     * @日期：2017-9-21
     *********************************************************************/
    @Override
    public List<SDictionary> findChecskdictionaries(Integer category, Integer noOne, Integer types) {
        String hql = "select a from SDictionary a where 1=1";
        //筛选类别
        hql += "and a.category like" + "'" + category + "'";
        //筛选第几项
        hql += "and a.noOne like" + "'" + noOne + "'";

        if (types == 2) {
            //筛选出只有3级标题的，做结果保存用
            hql += "and a.typeDictionary like" + "'" + 3 + "'";
        }
        hql += "order by a.noTwo,a.noThree";
        return sDictionaryDAO.executeQuery(hql);
    }

    /*********************************************************************
     * @内容：查询字典表哪一类的所有三级标题
     * @作者：赵昶
     * @日期：2017-9-26
     *********************************************************************/
    @Override
    public List<SDictionary> findChecskdictionariesOnlyThree(Integer category) {
        String hql = "select a from SDictionary a where 1=1";
        //筛选类别
        hql += "and a.category like" + "'" + category + "'";
        hql += "and a.typeDictionary like" + "'" + 3 + "'";
        hql += "order by a.noTwo,a.noThree";
        return sDictionaryDAO.executeQuery(hql);
    }

    /*********************************************************************
     * @内容：查询检查表对应的填写结果
     * @作者：赵昶
     * @日期：2017-9-27
     *********************************************************************/
    @Override
    public List<LabSecurityCheckDetails> findChecskDetails(Integer checkId) {
        String hql = "select a from LabSecurityCheckDetails a where 1=1 ";
        //筛选类别
        hql += "and a.labSecurityCheck.id like " + "'" + checkId + "'";
        return labSecurityCheckDetailsDAO.executeQuery(hql, 0, -1);
    }

    /*********************************************************************
     * @内容：删除传来LabSecurityCheckDetails的对象集合
     * @作者：赵昶
     * @日期：2017-9-27
     *********************************************************************/
    public boolean deleteListLabSecurityCheckDetails(List<LabSecurityCheckDetails> labSecurityCheckDetails) {
        if (labSecurityCheckDetails.size() > 0) {
            for (LabSecurityCheckDetails l : labSecurityCheckDetails) {
                labSecurityCheckDetailsDAO.remove(l);
            }
            return true;
        }
        return false;
    }

    /*********************************************************************
     * @内容：安全责任体系检查-统计专职管理员看到的安全检查记录的总数
     * @作者：赵昶
     * @日期：2017-9-28
     *********************************************************************/
    public int countLeaderLabSecurityCheck(LabSecurityCheck labSecurityCheck, String acno) {
        String hql = "select a from LabSecurityCheck a where 1=1";

        //是当前学期的
        String term = "";
        term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getTermName();
        //}
        hql += " and a.schoolTerm.termName =" + "'" + term + "'";

        //排除未提交的，专职管理员不显示
        hql += "and a.typeAuditing !='4'";

        //是否有查询
        //传来的对象week有值代表是查询，增加查询条件
        if (labSecurityCheck.getMonth() != null && !"".equals(labSecurityCheck.getMonth())) {
            hql += " and a.month =" + "'" + labSecurityCheck.getMonth() + "'";
        }
        //传来的对象labCenter有值代表是查询，增加查询条件
        if (labSecurityCheck.getLabCenter() != null && labSecurityCheck.getLabCenter().getId() != null) {
            hql += " and a.labCenter.id ="+ labSecurityCheck.getLabCenter().getId();
        }
        if (acno != null && !acno.equals("")) {
            hql += " and a.labCenter.schoolAcademy.academyNumber =" + acno;
        }
        List<LabSecurityCheck> list = labSecurityCheckDAO.executeQuery(hql, 0, -1);
        int RecordCount = list.size();
        return RecordCount;
    }

    /*********************************************************************
     * @内容：安全责任体系检查-筛选专职管理员看到的安全检查记录
     * @作者：赵昶
     * @日期：2017-9-28
     *********************************************************************/
    @Override
    public List<LabSecurityCheck> findLeaderLabSecurityChecks(Integer currpage,
                                                              Integer pageSize, LabSecurityCheck labSecurityCheck, String acno) {
        String hql = "select a from LabSecurityCheck a where 1=1";

        //是当前学期的
        String term = "";
        term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getTermName();
        hql += " and a.schoolTerm.termName =" + "'" + term + "'";
        //排除未提交的，专职管理员不显示
        hql += "and a.typeAuditing !='4'";

        //是否有查询
        //传来的对象week有值代表是查询，增加查询条件
        if (labSecurityCheck.getMonth() != null && !"".equals(labSecurityCheck.getMonth())) {
            hql += " and a.month =" + "'" + labSecurityCheck.getMonth() + "'";
        }
        //传来的对象labCenter有值代表是查询，增加查询条件
        if (labSecurityCheck.getLabCenter() != null && labSecurityCheck.getLabCenter().getId() != null) {
            hql += " and a.labCenter.id ="+ labSecurityCheck.getLabCenter().getId();
        }
        if (acno != null && !acno.equals("")) {
            hql += " and a.labCenter.schoolAcademy.academyNumber =" + acno;
        }
        List<LabSecurityCheck> labSecurityChecks = labSecurityCheckDAO.executeQuery(hql, (currpage - 1) * pageSize, pageSize);

        return labSecurityChecks;
    }

    /*********************************************************************
     * @内容：查询字典表的所有检查类型
     * @作者：赵昶
     * @日期：2017-10-9
     *********************************************************************/
    public List<String> findDictionariesCheckTypes() {
        String hql = "select a from SDictionary a where 1=1";
        hql += "and a.typeDictionary like" + "'" + 4 + "'";
        hql += "order by a.typeDictionary";
        List<SDictionary> sDictionarys = sDictionaryDAO.executeQuery(hql);
        List<String> checkTypes = new ArrayList<String>();
        for (SDictionary s : sDictionarys) {
            checkTypes.add(s.getName());
        }
        return checkTypes;
    }

    /*********************************************************************
     * @内容：查询字典表该检查类型下所有检查项
     * @作者：赵昶
     * @日期：2017-10-9
     *********************************************************************/
    public List<String> findDictionariesCheckItems(Integer checkType) {
        String hql = "select a from SDictionary a where 1=1";
        hql += "and a.category like" + "'" + checkType + "'";

        hql += "and a.typeDictionary like" + "'" + 1 + "'";
        hql += "order by a.noOne";
        List<SDictionary> sDictionarys = sDictionaryDAO.executeQuery(hql);
        List<String> checkItems = new ArrayList<String>();
        for (SDictionary s : sDictionarys) {
            checkItems.add(s.getName());
        }
        return checkItems;
    }

    /*********************************************************************
     * @功能：常规检查统计-获取实验室检查状态数据
     * @作者：李德
     * @日期：2017-11-15
     *********************************************************************/
    public List<LabCenter> getSecurityCheckLabRooms(HttpServletRequest request, Integer currpage, Integer pageSize, LabSecurityCheck labSecurityCheck, String acno) {

        String query = "";
        query += "select l from LabCenter l where 1=1";
        //增加查询条件-学院
        if (labSecurityCheck.getAcademyNumber() != null && !"".equals(labSecurityCheck.getAcademyNumber())) {
            query += " and l.schoolAcademy.academyNumber ='" + labSecurityCheck.getAcademyNumber() + "'";
        }else {
            if (acno != null && !acno.equals("")) {
                //当前登录中心的学院
                query += " and l.schoolAcademy.academyNumber = '" + acno + "'";
            }
        }
        //实验室查询
        if (labSecurityCheck.getLabCenter() != null && !"".equals(labSecurityCheck.getLabCenter()) && labSecurityCheck.getLabCenter().getId() != null) {
            query += " and l.id =" + labSecurityCheck.getLabCenter().getId();
        }
        //学期
        int schoolTerm = 0;
        if (labSecurityCheck.getSchoolTerm() != null && !"".equals(labSecurityCheck.getSchoolTerm()) && labSecurityCheck.getSchoolTerm().getId() != null) {
            schoolTerm = labSecurityCheck.getSchoolTerm().getId();
        }
        List<LabCenter> labCenters = labCenterDAO.executeQuery(query, (currpage - 1) * pageSize, pageSize);
        List<LabCenter> labCenterList = new ArrayList<LabCenter>();

        for (LabCenter lb : labCenters) {
            int[] typeAudit = getSecurityCheckByLabRoom(lb, schoolTerm);
            StringBuffer sb = new StringBuffer("%");
            for (int i = 0; i < typeAudit.length; i++) {
                sb.append(typeAudit[i]).append("%");
            }
            LabCenter labCenterTemp = new LabCenter();
            labCenterTemp.setSchoolAcademy(lb.getSchoolAcademy());
            labCenterTemp.setCenterName(lb.getCenterName() + "," + sb.toString());
            labCenterList.add(labCenterTemp);
        }

        return labCenterList;
    }

    /*********************************************************************
     * @功能：安全检查统计-获取实验室检查状态数据count
     * @作者：李德
     * @日期：2017-11-21
     *********************************************************************/
    @Override
    public Integer countSecurityCheckLabRooms(HttpServletRequest request, LabSecurityCheck labSecurityCheck, Integer cid) {
        String query = "select count(l) from LabRoom l where 1=1";
        String academyNumber = shareService.getUser().getSchoolAcademy().getAcademyNumber();
        query += " and l.labAnnex.labCenter.schoolAcademy.academyNumber='" + academyNumber + "'";
        //增加查询条件-学院
        if (labSecurityCheck.getAcademyNumber() != null && !"".equals(labSecurityCheck.getAcademyNumber())) {
            query += " and l.academyNumber =" + "'" + labSecurityCheck.getAcademyNumber() + "'";
        }
        //实验室查询
        if (labSecurityCheck.getLabRoom() != null && !"".equals(labSecurityCheck.getLabRoom()) && labSecurityCheck.getLabRoom().getId() != null) {
            query += " and l.id =" + labSecurityCheck.getLabRoom().getId();
        }
        if (cid != null) {
            query += " and l.labCenter.id=" + cid;
        }
        //学期
        int schoolTerm = 0;
        if (labSecurityCheck.getSchoolTerm() != null && !"".equals(labSecurityCheck.getSchoolTerm()) && labSecurityCheck.getSchoolTerm().getId() != null) {
            schoolTerm = labSecurityCheck.getSchoolTerm().getId();
        }

        //System.out.println(query);
        return ((Long) labRoomDAO.createQuerySingleResult(query).getSingleResult()).intValue();
    }


    /*********************************************************************
     * @功能：安全责任体系检查统计-处理各实验室各周次的检查状态数据
     * @作者：李德
     * @日期：2017-11-15
     *********************************************************************/
    public int[] getSecurityCheckByLabRoom(LabCenter labCenter, int schoolTerm) {
        //定义各周次状态数据的数组，数组长度由基础数据中的周次决定
        int[] typeAudit = new int[12];
        String query = "select l from LabSecurityCheck l where 1=1 and l.labCenter.id =" + labCenter.getId();
        //学期查询
        if (schoolTerm != 0) {
            query += " and l.schoolTerm.id =" + schoolTerm;
        }
        List<LabSecurityCheck> labSecurityChecks = labSecurityCheckDAO.executeQuery(query, 0, -1);
        //System.out.println(query);

        int month = 1;
        int labSecurityCheckSize = labSecurityChecks.size();
        if (labSecurityCheckSize > 0) {
            for (int i = 0; i < 12; i++) {
                //各周次常规检查数据

                for (LabSecurityCheck l : labSecurityChecks) {
                    //如果有该周次的常规检查数据，则继续；否则页面显示×
                    if (l.getMonth() != null && month == Integer.parseInt(l.getMonth())) {
                        //System.out.println(l.getTypeAuditing());
                        //审核通过、审核不通过、待审核状态，页面显示√
                        if (l.getTypeAuditing().equals("1") | l.getTypeAuditing().equals("2") || l.getTypeAuditing().equals("3")) {
                            //对应页面显示√
                            typeAudit[i] = 1;
                        }

                    }
                }
                month = month + 1;
            }
        }


        return typeAudit;
    }

    /*********************************************************************
     * @功能：常规检查统计-获取实验室检查状态数据
     * @作者：李德
     * @日期：2017-11-21
     *********************************************************************/
    public List<LabCenter> getSecurityCheckLabRoomsExport(HttpServletRequest request, LabSecurityCheck labSecurityCheck) {

        String query = "select l from LabCenter l where 1=1";
        //增加查询条件-学院
        if (labSecurityCheck.getAcademyNumber() != null && !"".equals(labSecurityCheck.getAcademyNumber())) {
            query += " and l.schoolAcademy.academyNumber ='" + labSecurityCheck.getAcademyNumber() + "'";
        }
        //实验中心查询
        if (labSecurityCheck.getLabCenter() != null && !"".equals(labSecurityCheck.getLabCenter()) && labSecurityCheck.getLabCenter().getId() != null) {
            query += " and l.id =" + labSecurityCheck.getLabCenter().getId();
        }
        //学期
        int schoolTerm = 0;
        if (labSecurityCheck.getSchoolTerm() != null && !"".equals(labSecurityCheck.getSchoolTerm()) && labSecurityCheck.getSchoolTerm().getId() != null) {
            schoolTerm = labSecurityCheck.getSchoolTerm().getId();
        }
        //System.out.println(query);
        List<LabCenter> labCenters = labCenterDAO.executeQuery(query, 0, -1);
        List<LabCenter> labCenterList = new ArrayList<LabCenter>();

        for (LabCenter lb : labCenters) {
            int[] typeAudit = getSecurityCheckByLabRoom(lb, schoolTerm);
            StringBuffer sb = new StringBuffer("%");
            for (int i = 0; i < typeAudit.length; i++) {
                sb.append(typeAudit[i]).append("%");
            }
            LabCenter labCenterTemp = new LabCenter();
            labCenterTemp.setSchoolAcademy(lb.getSchoolAcademy());
            labCenterTemp.setCenterName(lb.getCenterName() + "," + sb.toString());
            labCenterList.add(labCenterTemp);
        }

        return labCenterList;
    }

    /*********************************************************************
     * @功能：安全检查统计导出
     * @作者：李德
     * @日期：2017-11-21
     *********************************************************************/
    @Override
    public void exportSecurityCheckLabRooms(@ModelAttribute List<LabCenter> securityCheckLabCenters, HttpServletRequest request, HttpServletResponse response) throws Exception {


        List<Map> list = new ArrayList<Map>();
        //System.out.println("yyyyyyyyyyy");
        //设置打印的信息

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (LabCenter l : securityCheckLabCenters) {
            Map map = new HashMap();
            //学院
            String academyName = "";
            if (l.getSchoolAcademy() != null) {
                academyName = l.getSchoolAcademy().getAcademyName();
            }
            map.put("academyName", academyName);
            String[] centerName = l.getCenterName().split(",");

            //实验室名称
            map.put("centerName", centerName[0]);

            String[] months = l.getCenterName().split("%");
            //System.out.println("9:"+months[9]);
            //System.out.println("11:"+months[11]);
            //一月
            String checkJanStatus = "";
            if (months[1].equals("1")) {
                checkJanStatus = "√";
            } else {
                checkJanStatus = "×";
            }
            map.put("checkJanStatus", checkJanStatus);
            //二月
            String checkFebStatus = "";
            if (months[2].equals("1")) {
                checkFebStatus = "√";
            } else {
                checkFebStatus = "×";
            }
            map.put("checkFebStatus", checkFebStatus);
            //三月
            String checkMarStatus = "";
            if (months[3].equals("1")) {
                checkMarStatus = "√";
            } else {
                checkMarStatus = "×";
            }
            map.put("checkMarStatus", checkMarStatus);
            //四月
            String checkAprStatus = "";
            if (months[4].equals("1")) {
                checkAprStatus = "√";
            } else {
                checkAprStatus = "×";
            }
            map.put("checkAprStatus", checkAprStatus);
            //五月
            String checkMayStatus = "";
            if (months[5].equals("1")) {
                checkMayStatus = "√";
            } else {
                checkMayStatus = "×";
            }
            map.put("checkMayStatus", checkMayStatus);
            //六月
            String checkJuneStatus = "";
            if (months[6].equals("1")) {
                checkJuneStatus = "√";
            } else {
                checkJuneStatus = "×";
            }
            map.put("checkJuneStatus", checkJuneStatus);
            //七月
            String checkJulyStatus = "";
            if (months[7].equals("1")) {
                checkJulyStatus = "√";
            } else {
                checkJulyStatus = "×";
            }
            map.put("checkJulyStatus", checkJulyStatus);
            //八月
            String checkAugStatus = "";
            if (months[8].equals("1")) {
                checkAugStatus = "√";
            } else {
                checkAugStatus = "×";
            }
            map.put("checkAugStatus", checkAugStatus);
            //九月
            String checkSeptStatus = "";
            if (months[9].equals("1")) {
                checkSeptStatus = "√";
            } else {
                checkSeptStatus = "×";
            }
            map.put("checkSeptStatus", checkSeptStatus);
            //十月
            String checkOctStatus = "";
            if (months[10].equals("1")) {
                checkOctStatus = "√";
            } else {
                checkOctStatus = "×";
            }
            map.put("checkOctStatus", checkOctStatus);
            //十一月
            String checkNovStatus = "";
            if (months[11].equals("1")) {
                checkNovStatus = "√";
            } else {
                checkNovStatus = "×";
            }
            map.put("checkNovStatus", checkNovStatus);
            //十二月
            String checkDectStatus = "";
            if (months[12].equals("1")) {
                checkDectStatus = "√";
            } else {
                checkDectStatus = "×";
            }
            map.put("checkDectStatus", checkDectStatus);

            list.add(map);
        }
        //设置表头
        String title = "安全责任体系检查记录统计表";
        String[] hearders = new String[]{"学院", "实验中心名称", "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};//表头数组
        String[] fields = new String[]{"academyName", "centerName", "checkJanStatus", "checkFebStatus", "checkMarStatus", "checkAprStatus", "checkMayStatus", "checkJuneStatus", "checkJulyStatus", "checkAugStatus", "checkSeptStatus", "checkOctStatus", "checkNovStatus", "checkDectStatus"};//Financialresources对象属性数组
        TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders), fields);
        JsGridReportBase report = new JsGridReportBase(request, response);
        report.exportToExcel(title, shareService.getUser().getCname(), td);
    }


}