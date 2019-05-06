package net.zjcclims.service.routineInspection;

import excelTools.ExcelUtils;
import excelTools.JsGridReportBase;
import excelTools.TableData;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("RoutineInspectionService")
@Transactional
public class RoutineInspectionServiceImpl implements RoutineInspectionService {
    @Autowired
    private ShareService shareService;
    @Autowired
    private LabRoomService labRoomService;
    @Autowired
    private RoutineInspectionDAO routineInspectionDAO;
    @Autowired
    private LabRoomDAO labRoomDAO;
    @Autowired
    private CommonDocumentDAO commonDocumentDAO;
    @Autowired
    private LabRoomAdminDAO labRoomAdminDAO;
    @Autowired
    private SpecialExaminationDAO specialExaminationDAO;
    @Autowired
    private LabCenterDAO labCenterDAO;

    /*********************************************************************
     * @内容：常规检查-查询{查询该学院的该管理员的所有常规检查记录总数}
     * @作者：赵昶
     * @日期：2017-9-4
     *********************************************************************/
    public int Count(HttpServletRequest request, RoutineInspection routineInspection, String acno) {
        String hql = "";
        //实验室管理员获取所属的实验室
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_LABMANAGER") != -1) {
            hql += " select a from RoutineInspection a,LabRoomAdmin la where la.labRoom.id=a.labRoom.id and la.user.username='" + shareService.getUser().getUsername() + "'";
        } else {
            //不是实验室管理员的
            hql += "select a from RoutineInspection a where 1=1";
            // 判断是否有超级管理员、运行与安全管理科身份
            if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_SUPERADMIN") == -1 &&
                    request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_OPEARTIONSECURITYMANAGEMENT") == -1) {
                String userId = shareService.getUser().getUsername();
                hql += " and a.user.username = '" + userId + "' ";
            }else {
                acno=null;
            }
        }
        //类型是常规检查表的
        hql += " and a.typeTable =1";

        //传来的对象week有值代表是查询，增加查询条件
        if (routineInspection.getWeek() != null && !"".equals(routineInspection.getWeek())) {
            hql += " and a.week ='" + routineInspection.getWeek() + "'";
        }
        //传来的对象labroom有值代表是查询，增加查询条件
        if (routineInspection.getLabRoom() != null && routineInspection.getLabRoom().getId() != null) {
            hql += " and a.labRoom.id ='" + routineInspection.getLabRoom().getId() + "'";
        }
        if (acno != null && !acno.equals("")) {
            hql += " and a.labRoom.labAnnex.labCenter.schoolAcademy.academyNumber ='" + acno + "'";
        }
        List<RoutineInspection> list = routineInspectionDAO.executeQuery(hql, 0, -1);
        int RecordCount = list.size();
        return RecordCount;
    }


    /*********************************************************************
     * @功能：常规检查-查询{查询该学院的该管理员的所有常规检查记录}
     * @作者：赵昶
     * @日期：2017-9-4
     *********************************************************************/
    @Override
    public List<RoutineInspection> findAllRoutineInspection(HttpServletRequest request, Integer currpage, Integer pageSize, RoutineInspection routineInspection, String acno) {
        String hql = "";
        //实验室管理员获取所属的实验室
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_LABMANAGER") != -1) {
            hql += " select a from RoutineInspection a,LabRoomAdmin la where la.labRoom.id=a.labRoom.id and la.user.username='" + shareService.getUser().getUsername() + "'";
        } else {
            //不是实验室管理员的
            hql += "select a from RoutineInspection a where 1=1";
            // 判断是否有超级管理员、运行与安全管理科身份
            if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_SUPERADMIN") == -1 &&
                    request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_OPEARTIONSECURITYMANAGEMENT") == -1) {
                String userId = shareService.getUser().getUsername();
                hql += " and a.user.username = '" + userId + "' ";
            }else {
                acno=null;
            }
        }
        //类型是常规检查表的
        hql += " and a.typeTable =1";
        //传来的对象week有值代表是查询，增加查询条件
        if (routineInspection.getWeek() != null && !"".equals(routineInspection.getWeek())) {
            hql += " and a.week ='" + routineInspection.getWeek() + "'";
        }
        //传来的对象labroom有值代表是查询，增加查询条件
        if (routineInspection.getLabRoom() != null && routineInspection.getLabRoom().getId() != null) {
            hql += " and a.labRoom.id ='" + routineInspection.getLabRoom().getId() + "'";
        }
        if (acno != null && !acno.equals("")) {
            hql += " and a.labRoom.labAnnex.labCenter.schoolAcademy.academyNumber ='" + acno + "'";
        }

        List<RoutineInspection> list = routineInspectionDAO.executeQuery(hql);
        return routineInspectionDAO.executeQuery(hql.toString(), (currpage - 1) * pageSize, pageSize);
    }

    /*********************************************************************
     * @功能：常规检查-查询{查询管理员所管辖的实验室(未实现)}
     * @作者：赵昶
     * @日期：2017-9-4
     *********************************************************************/
    @Override
    public List<LabRoom> getLabRooms() {
        String hql = "select a from LabRoomAdmin a where 1=1";
        String userId = shareService.getUser().getUsername();
        hql += "and a.user.username ='" + userId + "'";
        hql += "and a.typeId='1'";
        List<LabRoomAdmin> labRoomAdmins = labRoomAdminDAO.executeQuery(hql);
        List<LabRoom> labRooms = new ArrayList<LabRoom>();
        for (LabRoomAdmin l : labRoomAdmins) {
            labRooms.add(l.getLabRoom());
        }

        return labRooms;
    }

    /*********************************************************************
     * @功能：常规检查统计-获取实验室检查状态数据
     * @作者：李德
     * @日期：2017-11-15
     *********************************************************************/
    @Override
    public List<LabRoom> getInspectionLabRooms(HttpServletRequest request, Integer currpage, Integer pageSize, RoutineInspection routineInspection, int weekCount, String acno) {

        String query = "";
        //实验室管理员获取所属的实验室
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_LABMANAGER") != -1) {
            query += "select l from LabRoom l,LabRoomAdmin la where la.labRoom.id=l.id and la.user.username='" + shareService.getUser().getUsername() + "'";
        } else {
            query += "select l from LabRoom l where 1=1";
        }
        //增加查询条件-学院
        if (routineInspection.getAcademyNumber() != null && !"".equals(routineInspection.getAcademyNumber())) {
            query += " and l.labAnnex.labCenter.schoolAcademy.academyNumber ='" + routineInspection.getAcademyNumber() + "'";
        }else {
            if (acno != null && !acno.equals("")) {
                //当前登录中心的学院
                query += " and l.labAnnex.labCenter.schoolAcademy.academyNumber = '" + acno + "'";
            }
        }
        //实验室查询
        if (routineInspection.getLabRoom() != null && !"".equals(routineInspection.getLabRoom()) && routineInspection.getLabRoom().getId() != null) {
            query += " and l.id =" + routineInspection.getLabRoom().getId();
        }
        //学期
        int schoolTerm = 0;
        if (routineInspection.getSchoolTerm() != null && !"".equals(routineInspection.getSchoolTerm()) && routineInspection.getSchoolTerm().getId() != null) {
            schoolTerm = routineInspection.getSchoolTerm().getId();
        }
        //System.out.println(query);
        List<LabRoom> labRooms = labRoomDAO.executeQuery(query, (currpage - 1) * pageSize, pageSize);
        List<LabRoom> labRoomList = new ArrayList<LabRoom>();

        for (LabRoom lb : labRooms) {
            int[] typeAudit = getInspectionByLabRoom(lb, weekCount, schoolTerm);
            StringBuffer sb = new StringBuffer("%");
            for (int i = 0; i < typeAudit.length; i++) {
                sb.append(typeAudit[i]).append("%");
            }
            //System.out.println(sb);
            LabRoom labRoomTemp = new LabRoom();
            labRoomTemp.setLabAnnex(lb.getLabAnnex());
            labRoomTemp.setLabRoomName(lb.getLabRoomName() + "," + sb.toString());
            labRoomList.add(labRoomTemp);
        }

        return labRoomList;
    }

    /*********************************************************************
     * @功能：常规检查统计-获取实验室检查状态数据count
     * @作者：李德
     * @日期：2017-11-21
     *********************************************************************/
    @Override
    public Integer countInspectionLabRooms(HttpServletRequest request, RoutineInspection routineInspection, Integer cid) {
        String query = "";
        //实验室管理员获取所属的实验室
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_LABMANAGER") != -1) {
            query += "select count(l) from LabRoom l,LabRoomAdmin la where la.labRoom.id=l.id and la.user.username='" + shareService.getUser().getUsername() + "'";
        } else {
            query += "select count(l) from LabRoom l where 1=1";
        }
        /*String query="select count(l) from LabRoom l where 1=1";*/
        //String academyNumber=shareService.getUser().getSchoolAcademy().getAcademyNumber();
        String academyNumber = "";
        if (cid == -1) {
            academyNumber = shareService.getUser().getSchoolAcademy().getAcademyNumber();
        } else {
            academyNumber = labCenterDAO.findLabCenterById(cid).getSchoolAcademy().getAcademyNumber();
        }
        query += " and l.labAnnex.labCenter.schoolAcademy.academyNumber='" + academyNumber + "'";
        //增加查询条件-学院
        if (routineInspection.getAcademyNumber() != null && !"".equals(routineInspection.getAcademyNumber())) {
            query += " and l.labAnnex.labCenter.schoolAcademy.academyNumber ='" + routineInspection.getAcademyNumber() + "'";
        }
        //实验室查询
        if (routineInspection.getLabRoom() != null && !"".equals(routineInspection.getLabRoom()) && routineInspection.getLabRoom().getId() != null) {
            query += " and l.id =" + routineInspection.getLabRoom().getId();
        }
        if (cid != null) {
            query += " and l.labCenter.id=" + cid;
        }
        //学期
        int schoolTerm = 0;
        if (routineInspection.getSchoolTerm() != null && !"".equals(routineInspection.getSchoolTerm()) && routineInspection.getSchoolTerm().getId() != null) {
            schoolTerm = routineInspection.getSchoolTerm().getId();
        }

        //System.out.println(query);
        return ((Long) labRoomDAO.createQuerySingleResult(query).getSingleResult()).intValue();
    }


    /*********************************************************************
     * @功能：常规检查统计-处理各实验室各周次的检查状态数据
     * @作者：李德
     * @日期：2017-11-15
     *********************************************************************/
    public int[] getInspectionByLabRoom(LabRoom labRoom, int weekCount, int schoolTerm) {
        //定义各周次状态数据的数组，数组长度由基础数据中的周次决定
        int[] typeAudit = new int[weekCount];
        String query = "select r from RoutineInspection r where 1=1 and r.labRoom.id =" + labRoom.getId();
        //学期查询
        if (schoolTerm != 0) {
            query += " and r.schoolTerm.id =" + schoolTerm;
        }
        List<RoutineInspection> routineInspections = routineInspectionDAO.executeQuery(query, 0, -1);
        //System.out.println(query);
        int week = 1;
        int routineInspectionSize = routineInspections.size();
        if (routineInspectionSize > 0) {
            for (int i = 0; i < weekCount - 1; i++) {
                //各周次常规检查数据

                for (RoutineInspection r : routineInspections) {
                    //如果有该周次的常规检查数据，则继续；否则页面显示×
                    if (r.getWeek() != null && week == Integer.parseInt(r.getWeek())) {
                        //System.out.println(r.getTypeAuditing());
                        //审核通过、审核不通过、待审核状态，页面显示√
                        if (r.getTypeAuditing().equals("1") || r.getTypeAuditing().equals("2") || r.getTypeAuditing().equals("3")) {
                            //对应页面显示√
                            typeAudit[i] = 1;
                        }

                    }
                }
                week = week + 1;
            }
        }


        return typeAudit;
    }

    /*********************************************************************
     * @功能：常规检查-保存{保存常规检查记录}
     * @作者：赵昶
     * @日期：2017-9-5
     *********************************************************************/
    @Override
    public RoutineInspection saveRoutineInspection(RoutineInspection routineInspection) {
        return routineInspectionDAO.store(routineInspection);

    }

    /*********************************************************************
     * @功能：常规检查-附件上传{多文件上传}
     * @作者：赵昶
     * @日期：2017-9-5
     *********************************************************************/
    @SuppressWarnings({"rawtypes"})
    @Override
    public String uploaddnolinedocment(HttpServletRequest request, HttpServletResponse response, Integer id) {
        String listid = "";
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        String sep = System.getProperty("file.separator");
        Map files = multipartRequest.getFileMap();
        Iterator fileNames = multipartRequest.getFileNames();
        boolean flag = false;
        String suiji = UUID.randomUUID().toString();
        String fileDir = request.getSession().getServletContext().getRealPath("/") + "upload" + sep + "operation" + sep + suiji;
        //	 String fileDir =  "/upload"+ sep+"operation"+sep+suiji;
        //存放文件文件夹名称
        for (; fileNames.hasNext(); ) {

            String filename = (String) fileNames.next();
            CommonsMultipartFile file = (CommonsMultipartFile) files.get(filename);
            byte[] bytes = file.getBytes();
            if (bytes.length != 0) {
                // 说明申请有附件
                if (!flag) {
                    File dirPath = new File(fileDir);
                    if (!dirPath.exists()) {
                        flag = dirPath.mkdirs();
                    }
                }
                String fileTrueName = file.getOriginalFilename();
                //System.out.println("文件名称："+fileTrueName);
                File uploadedFile = new File(fileDir + sep + fileTrueName);
                //System.out.println("文件存放路径为："+fileDir + sep + fileTrueName);
                try {
                    FileCopyUtils.copy(bytes, uploadedFile);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                CommonDocument conn = new CommonDocument();
                conn.setDocumentName(fileTrueName);
                //
                fileDir = "/upload/operation/" + suiji + "/";
                // conn.setDocumentUrl(fileDir + sep + fileTrueName);
                conn.setDocumentUrl(fileDir + fileTrueName);
                if (id != null) {
                    conn.setRoutineInspection(routineInspectionDAO.findRoutineInspectionById(id));
                }
                CommonDocument ids = commonDocumentDAO.store(conn);
                listid = "<tr id='s_" + ids.getId() + "'><td>" + ids.getDocumentName() + "</td><td><input type='button' onclick='delectuploaddocment(" + ids.getId() + ")' value='删除'/> </td></tr>";

            }

        }

        return listid;
    }

    /*********************************************************************
     * @功能：常规检查-查询{查询登录人所属学院的实验室}
     * @作者：赵昶
     * @日期：2017-9-4
     *********************************************************************/
    @Override
    public List<LabRoom> getAcademyLabRooms(String acno) {
        String hql = "select a from LabRoom a where 1=1";
        if(acno != null && !acno.equals("")) {
            hql += " and a.labCenter.schoolAcademy.academyNumber='" + acno + "'";
        }
        return labRoomDAO.executeQuery(hql, 0,-1);
    }

    /*********************************************************************
     * @功能：查询登录人所属学院的实验中心
     * @作者：张德冰
     * @日期：2018.08.20
     *********************************************************************/
    @Override
    public List<LabCenter> getAcademyLabCenters(String acno) {
        String hql = "select a from LabCenter a where 1=1";
        if(acno != null && !acno.equals("")) {
            hql += " and a.schoolAcademy.academyNumber='" + acno + "' ";
        }
        return labCenterDAO.executeQuery(hql.toString());
    }

    /*********************************************************************
     * @功能：查询实验中心主任所属实验中心
     * @作者：张德冰
     * @日期：2018-12-11
     *********************************************************************/
    @Override
    public List<LabCenter> getLabCenterByCenterManager() {
        String hql = "select a from LabCenter a where 1=1";
        hql += " and a.userByCenterManager.username='" + shareService.getUser().getUsername() + "' ";
        return labCenterDAO.executeQuery(hql.toString());
    }

    /*********************************************************************
     * @功能：常规检查-查询{查询该学院的所有常规检查记录(专职管理员或学院领导登陆的情况下)}
     * @作者：赵昶
     * @日期：2017-9-4
     *********************************************************************/
    @Override
    public List<RoutineInspection> findAllAcademyRoutineInspection(
            int currpage, int pageSize, RoutineInspection routineInspection, String acno) {
        String hql = "select a from RoutineInspection a where 1=1";
        //类型是常规检查表的
        hql += " and a.typeTable =1";
        //除待提交状态的
        hql += " and a.typeAuditing !=4";
        //传来的对象week有值代表是查询，增加查询条件
        if (routineInspection.getWeek() != null && !"".equals(routineInspection.getWeek())) {
            hql += " and a.week ='" + routineInspection.getWeek() + "'";
        }
        //传来的对象labroom有值代表是查询，增加查询条件
        if (routineInspection.getLabRoom() != null && routineInspection.getLabRoom().getId() != null) {
            hql += " and a.labRoom.id ='" + routineInspection.getLabRoom().getId() + "'";
        }
        if (acno != null && !acno.equals("")) {
            //当前登录中心的学院
            hql += " and a.labRoom.labAnnex.labCenter.schoolAcademy.academyNumber = '" + acno + "'";
        }
        return routineInspectionDAO.executeQuery(hql.toString(), (currpage - 1) * pageSize, pageSize);
    }

    /*********************************************************************
     * @内容：常规检查-查询{查询该学院的所有常规检查记录总数(专职管理员或学院领导登陆的情况下)}
     * @作者：赵昶
     * @日期：2017-9-4
     *********************************************************************/
    @Override
    public int CountAcademy(RoutineInspection routineInspection, String acno) {
        String hql = "select a from RoutineInspection a where 1=1";
        //类型是常规检查表的
        hql += " and a.typeTable =1";

        //除待提交状态的
        hql += " and a.typeAuditing !=4";

        //传来的对象week有值代表是查询，增加查询条件
        if (routineInspection.getWeek() != null && !"".equals(routineInspection.getWeek())) {
            hql += " and a.week ='" + routineInspection.getWeek() + "'";
        }
        //传来的对象labroom有值代表是查询，增加查询条件
        if (routineInspection.getLabRoom() != null && routineInspection.getLabRoom().getId() != null) {
            hql += " and a.labRoom.id ='" + routineInspection.getLabRoom().getId() + "'";
        }
        if (acno != null && !acno.equals("")) {
            //当前登录中心的学院
            hql += " and a.labRoom.labAnnex.labCenter.schoolAcademy.academyNumber = '" + acno + "'";
        }

        List<RoutineInspection> list = routineInspectionDAO.executeQuery(hql, 0, -1);
        int RecordCount = list.size();
        return RecordCount;
    }

    /*********************************************************************
     * @功能：上报常规检查{查询该学院的所有上报常规检查记录(专职管理员或学院领导登陆的情况下)}
     * @作者：赵昶
     * @日期：2017-9-4
     *********************************************************************/
    @Override
    public List<RoutineInspection> findAllReportRoutineInspection(
            Integer currpage, Integer pageSize,
            RoutineInspection routineInspection, String acno) {
        String hql = "select a from RoutineInspection a where 1=1";
        //类型是上报常规检查表的
        hql += " and a.typeTable =2";

        //传来的对象week有值代表是查询，增加查询条件
        if (routineInspection.getWeek() != null && !"".equals(routineInspection.getWeek())) {
            hql += " and a.week ='" + routineInspection.getWeek() + "'";
        }
        //实验中心
        if (routineInspection.getLabCenter() != null && routineInspection.getLabCenter().getId() != null) {
            hql += " and a.labCenter.id =" + routineInspection.getLabCenter().getId();
        } else {
            if (acno != null && !acno.equals("")) {
                //当前登录中心的学院
                hql += " and a.labCenter.schoolAcademy.academyNumber = '" + acno + "'";
            }
        }


        return routineInspectionDAO.executeQuery(hql.toString(), (currpage - 1) * pageSize, pageSize);
    }

    /*********************************************************************
     * @内容：上报常规检查-查询{查询该学院的所有常规检查记录总数}
     * @作者：赵昶
     * @日期：2017-9-4
     *********************************************************************/
    @Override
    public int CountReportAcademy(RoutineInspection routineInspection, String acno) {
        String hql = "select a from RoutineInspection a where 1=1";
        //类型是上报常规检查表的
        hql += " and a.typeTable =2";

        //传来的对象week有值代表是查询，增加查询条件
        if (routineInspection.getWeek() != null && !"".equals(routineInspection.getWeek())) {
            hql += " and a.week ='" + routineInspection.getWeek() + "'";
        }
        //实验中心
        if (routineInspection.getLabCenter() != null && routineInspection.getLabCenter().getId() != null) {
            hql += " and a.labCenter.id =" + routineInspection.getLabCenter().getId();
        } else {
            if (acno != null && !acno.equals("")) {
                //当前登录中心的学院
                hql += " and a.labCenter.schoolAcademy.academyNumber = '" + acno + "'";
            }
        }


        List<RoutineInspection> list = routineInspectionDAO.executeQuery(hql, 0, -1);
        int RecordCount = list.size();
        return RecordCount;
    }

    /*********************************************************************
     * @内容：上报常规检查-查询{查询该学院的已提交的所有上报常规检查记录总数}
     * @作者：赵昶
     * @日期：2017-9-4
     *********************************************************************/
    @Override
    public int CountLeaderReport(RoutineInspection routineInspection, String acno) {
        String hql = "select a from RoutineInspection a where 1=1";
        //类型是上报常规检查表的
        hql += " and a.typeTable =2";

        //除待提交状态的
        hql += " and a.typeAuditing !=4";
        //传来的对象week有值代表是查询，增加查询条件
        if (routineInspection.getWeek() != null && !"".equals(routineInspection.getWeek())) {
            hql += " and a.week ='" + routineInspection.getWeek() + "'";
        }
        //实验中心
        if (routineInspection.getLabCenter() != null && routineInspection.getLabCenter().getId() != null) {
            hql += " and a.labCenter.id =" + routineInspection.getLabCenter().getId();
        } else {
            if (acno != null && !acno.equals("")) {
                //当前登录中心的学院
                hql += " and a.labCenter.schoolAcademy.academyNumber = '" + acno + "'";
            }
        }

        List<RoutineInspection> list = routineInspectionDAO.executeQuery(hql, 0, -1);
        int RecordCount = list.size();
        return RecordCount;
    }

    /*********************************************************************
     * @功能：上报常规检查{查询该学院的所有上报常规检查记录(专职管理员或学院领导登陆的情况下)}
     * @作者：赵昶
     * @日期：2017-9-4
     *********************************************************************/
    @Override
    public List<RoutineInspection> findLeaderReportRoutineInspection(
            Integer currpage, Integer pageSize,
            RoutineInspection routineInspection, String acno) {
        String hql = "select a from RoutineInspection a where 1=1";
        //类型是上报常规检查表的
        hql += " and a.typeTable =2";
        //除待提交状态的
        hql += " and a.typeAuditing !=4";
        //传来的对象week有值代表是查询，增加查询条件
        if (routineInspection.getWeek() != null && !"".equals(routineInspection.getWeek())) {
            hql += " and a.week ='" + routineInspection.getWeek() + "'";
        }
        //实验中心
        if (routineInspection.getLabCenter() != null && routineInspection.getLabCenter().getId() != null) {
            hql += " and a.labCenter.id =" + routineInspection.getLabCenter().getId();
        } else {
            if (acno != null && !acno.equals("")) {
                //当前登录中心的学院
                hql += " and a.labCenter.schoolAcademy.academyNumber = '" + acno + "'";
            }
        }
        return routineInspectionDAO.executeQuery(hql.toString(), (currpage - 1) * pageSize, pageSize);
    }

    /*********************************************************************
     * @内容：抽检(学生，督导){统计属于该人提交的检查表记录总数，通过types判断是学生（1）还是督导（2）}
     * @作者：赵昶
     * @日期：2017-9-14
     *********************************************************************/
    @Override
    public int countStudnetCasual(Integer types, RoutineInspection routineInspection, String acno) {
        String hql = "select a from RoutineInspection a where 1=1";

        if (types == 1) {
            //类型是学生抽检的
            hql += " and a.typeTable =3";
        } else {
            //类型是督导抽检的
            hql += " and a.typeTable =4";
        }

        //传来的对象academyNumber有值代表是查询，增加查询条件
        if (routineInspection.getAcademyNumber() != null && !"".equals(routineInspection.getAcademyNumber())) {
            hql += " and a.labRoom.labAnnex.labCenter.schoolAcademy.academyNumber ='" + routineInspection.getAcademyNumber() + "'";
        }else {
            if (acno != null && !acno.equals("")) {
                //当前登录中心的学院
                hql += " and a.labRoom.labAnnex.labCenter.schoolAcademy.academyNumber = '" + acno + "'";
            }
        }
        //传来的对象labroom有值代表是查询，增加查询条件
        if (routineInspection.getLabRoom() != null && routineInspection.getLabRoom().getId() != 999999) {
            hql += " and a.labRoom.id ='" + routineInspection.getLabRoom().getId() + "'";
        }
        //传来的对象SchoolTerm有值代表是查询，增加查询条件
        if (routineInspection.getSchoolTerm() != null && routineInspection.getSchoolTerm().getId() != null && routineInspection.getSchoolTerm().getId() != 999999) {
            hql += " and a.schoolTerm.id ='" + routineInspection.getSchoolTerm().getId() + "'";
        }

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") == -1 &&
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_OPEARTIONSECURITYMANAGEMENT") == -1&&
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_LABMANAGER") == -1&&
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_ASSETMANAGER") == -1&&
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERVISIONGROUP") == -1) {
            //是该登陆人的
            String userId = shareService.getUser().getUsername();
            hql += " and a.user.username = '" + userId + "'";
        }

        List<RoutineInspection> list = routineInspectionDAO.executeQuery(hql, 0, -1);
        int RecordCount = list.size();
        return RecordCount;
    }

    /*********************************************************************
     * @功能：抽检(学生，督导)-{返回所有属于该学生的抽检记录，通过types判断是学生（1）还是督导（2）}
     * @作者：赵昶
     * @日期：2017-9-14
     *********************************************************************/
    @Override
    public List<RoutineInspection> findStudnetCasual(Integer currpage,
                                                     Integer pageSize, RoutineInspection routineInspection, Integer types, String acno) {

        String hql = "select a from RoutineInspection a where 1=1";
        if (types == 1) {
            //类型是学生抽检的
            hql += " and a.typeTable = 3";
        } else {
            //类型是督导抽检的
            hql += " and a.typeTable = 4";

        }
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") == -1 &&
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_OPEARTIONSECURITYMANAGEMENT") == -1&&
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_LABMANAGER") == -1&&
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_ASSETMANAGER") == -1&&
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERVISIONGROUP") == -1) {
            //是该登录人的
            String userId = shareService.getUser().getUsername();
            hql += " and a.user.username = '" + userId + "'";
        }

        //传来的对象academyNumber有值代表是查询，增加查询条件
        if (routineInspection.getAcademyNumber() != null && !"".equals(routineInspection.getAcademyNumber())) {
            hql += " and a.labRoom.labAnnex.labCenter.schoolAcademy.academyNumber ='" + routineInspection.getAcademyNumber() + "'";
        }else {
            if (acno != null && !acno.equals("")) {
                //当前登录中心的学院
                hql += " and a.labRoom.labAnnex.labCenter.schoolAcademy.academyNumber = '" + acno + "'";
            }
        }
        //传来的对象labroom有值代表是查询，增加查询条件
        if (routineInspection.getLabRoom() != null && routineInspection.getLabRoom().getId() != 999999) {
            hql += " and a.labRoom.id ='" + routineInspection.getLabRoom().getId() + "'";
        }
        //传来的对象SchoolTerm有值代表是查询，增加查询条件
        if (routineInspection.getSchoolTerm() != null && routineInspection.getSchoolTerm().getId() != null && routineInspection.getSchoolTerm().getId() != 999999) {
            hql += " and a.schoolTerm.id ='" + routineInspection.getSchoolTerm().getId() + "'";
        }
        return routineInspectionDAO.executeQuery(hql.toString(), (currpage - 1) * pageSize, pageSize);
    }

    /*********************************************************************
     * @功能：学生抽检(学生)-{返回所有学生的抽检记录}
     * @作者：赵昶
     * @日期：2017-9-15
     *********************************************************************/
    @Override
    public List<RoutineInspection> findAllStudnetCasual(HttpServletRequest request, Integer currpage,
                                                        Integer pageSize, RoutineInspection routineInspection, Integer types,  String acno) {

        String hql = "";
        //实验室管理员获取所属的实验室
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_LABMANAGER") != -1) {
            hql += "select a from RoutineInspection a,LabRoomAdmin la where la.labRoom.id=a.labRoom.id and la.user.username='" + shareService.getUser().getUsername() + "'";
        } else {
            hql += "select a from RoutineInspection a where 1=1";
        }

        if (types == 1) {
            //类型是学生抽检记录的
            hql += " and a.typeTable = 3";
        } else {
            //类型是学生抽检记录的
            hql += " and a.typeTable = 4";
        }

        //除待提交状态的
        hql += " and a.typeAuditing != 4";

        //传来的对象academyNumber有值代表是查询，增加查询条件
        if (routineInspection.getAcademyNumber() != null && !"".equals(routineInspection.getAcademyNumber())) {
            hql += " and a.labRoom.labAnnex.labCenter.schoolAcademy.academyNumber ='" + routineInspection.getAcademyNumber() + "'";
        }else {
            if (acno != null && !acno.equals("")) {
                //当前登录中心的学院
                hql += " and a.labRoom.labAnnex.labCenter.schoolAcademy.academyNumber = '" + acno + "'";
            }
        }
        //传来的对象labroom有值代表是查询，增加查询条件
        if (routineInspection.getLabRoom() != null && routineInspection.getLabRoom().getId() != 999999) {
            hql += " and a.labRoom.id ='" + routineInspection.getLabRoom().getId() + "'";
        }
        //传来的对象SchoolTerm有值代表是查询，增加查询条件
        if (routineInspection.getSchoolTerm() != null && routineInspection.getSchoolTerm().getId() != null && routineInspection.getSchoolTerm().getId() != 999999) {
            hql += " and a.schoolTerm.id ='" + routineInspection.getSchoolTerm().getId() + "'";
        }
        return routineInspectionDAO.executeQuery(hql.toString(), (currpage - 1) * pageSize, pageSize);
    }

    /*********************************************************************
     * @内容：统计专项检查记录总数
     * @作者：赵昶
     * @日期：2017-10-9
     *********************************************************************/
    @Override
    public int countSpecialExamination(SpecialExamination specialExamination,String acno) {
        String hql = "select a from SpecialExamination a where 1=1";

        //传来的对象academyNumber有值代表是查询，增加查询条件
        if (specialExamination.getLabAnnex() != null && specialExamination.getLabAnnex().getId() != null && !"".equals(specialExamination.getLabAnnex().getId())) {
            hql += " and a.labAnnex.id =" + specialExamination.getLabAnnex().getId();
        }else {
            if (acno != null && !acno.equals("")) {
                //当前登录中心的学院
                hql += " and a.labAnnex.labCenter.schoolAcademy.academyNumber = '" + acno + "'";
            }
        }
        //传来的对象labroom有值代表是查询，增加查询条件
        if (specialExamination.getLabRoom() != null && specialExamination.getLabRoom().getId() != null) {
            hql += " and a.labRoom.id ='" + specialExamination.getLabRoom().getId() + "'";
        }
        //传来的对象SchoolTerm有值代表是查询，增加查询条件
        if (specialExamination.getSchoolTerm() != null && specialExamination.getSchoolTerm().getId() != null && specialExamination.getSchoolTerm().getId() != null) {
            hql += " and a.schoolTerm.id ='" + specialExamination.getSchoolTerm().getId() + "'";
        }

        List<SpecialExamination> list = specialExaminationDAO.executeQuery(hql, 0, -1);
        int RecordCount = list.size();
        return RecordCount;
    }

    /*********************************************************************
     * @功能：查找专项检查记录
     * @作者：赵昶
     * @日期：2017-10-10
     *********************************************************************/
    @Override
    public List<SpecialExamination> findSpecialExamination(HttpServletRequest request, Integer currpage, Integer pageSize,
                                                           SpecialExamination specialExamination, String acno) {

        String hql = "";
        //实验室管理员获取所属的实验室
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_LABMANAGER") != -1) {
            hql += "select a from SpecialExamination a,LabRoomAdmin la where la.labRoom.id=a.labRoom.id and la.user.username='" + shareService.getUser().getUsername() + "'";
        } else {
            hql += "select a from SpecialExamination a where 1=1";
        }

        //传来的对象academyNumber有值代表是查询，增加查询条件
        if (specialExamination.getLabAnnex() != null && specialExamination.getLabAnnex().getId() != null && !"".equals(specialExamination.getLabAnnex().getId())) {
            hql += " and a.labAnnex.id =" + specialExamination.getLabAnnex().getId();
        }else {
            if (acno != null && !acno.equals("")) {
                //当前登录中心的学院
                hql += " and a.labAnnex.labCenter.schoolAcademy.academyNumber = '" + acno + "'";
            }
        }

        //传来的对象labroom有值代表是查询，增加查询条件
        if (specialExamination.getLabRoom() != null && specialExamination.getLabRoom().getId() != null) {
            hql += " and a.labRoom.id ='" + specialExamination.getLabRoom().getId() + "'";
        }

        //传来的对象SchoolTerm有值代表是查询，增加查询条件
        if (specialExamination.getSchoolTerm() != null && specialExamination.getSchoolTerm().getId() != null && specialExamination.getSchoolTerm().getId() != null) {
            hql += " and a.schoolTerm.id ='" + specialExamination.getSchoolTerm().getId() + "'";
        }
        return specialExaminationDAO.executeQuery(hql.toString(), (currpage - 1) * pageSize, pageSize);
    }

    /*********************************************************************
     * @功能：常规检查统计导出
     * @作者：李德
     * @日期：2017-11-22
     *********************************************************************/
    @Override
    public void exportGeneralCheckLabRooms(@ModelAttribute List<LabRoom> generalCheckLabRooms, HttpServletRequest request, HttpServletResponse response) throws Exception {


        List<Map> list = new ArrayList<Map>();
        //System.out.println("yyyyyyyyyyy");
        //设置打印的信息

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (LabRoom l : generalCheckLabRooms) {
            Map map = new HashMap();
            //学院
            String academyName = "";
            if (l.getLabAnnex() != null && l.getLabAnnex().getLabCenter() != null && l.getLabAnnex().getLabCenter().getSchoolAcademy() != null) {
                academyName = l.getLabAnnex().getLabCenter().getSchoolAcademy().getAcademyName();
            }
            map.put("academyName", academyName);
            String[] labRoomName = l.getLabRoomName().split(",");

            //实验室名称
            map.put("labRoomName", labRoomName[0]);

            String[] weeks = l.getLabRoomName().split("%");
            //System.out.println("9:"+months[9]);
            //System.out.println("11:"+months[11]);
            //第一周
            String checkWeekStatus1 = "";
            if (weeks[1].equals("1")) {
                checkWeekStatus1 = "√";
            } else {
                checkWeekStatus1 = "×";
            }
            map.put("checkWeekStatus1", checkWeekStatus1);
            //第二周
            String checkWeekStatus2 = "";
            if (weeks[2].equals("1")) {
                checkWeekStatus2 = "√";
            } else {
                checkWeekStatus2 = "×";
            }
            map.put("checkWeekStatus2", checkWeekStatus2);
            //第三周
            String checkWeekStatus3 = "";
            if (weeks[3].equals("1")) {
                checkWeekStatus3 = "√";
            } else {
                checkWeekStatus3 = "×";
            }
            map.put("checkWeekStatus3", checkWeekStatus3);
            //第四周
            String checkWeekStatus4 = "";
            if (weeks[4].equals("1")) {
                checkWeekStatus4 = "√";
            } else {
                checkWeekStatus4 = "×";
            }
            map.put("checkWeekStatus4", checkWeekStatus4);
            //第五周
            String checkWeekStatus5 = "";
            if (weeks[5].equals("1")) {
                checkWeekStatus5 = "√";
            } else {
                checkWeekStatus5 = "×";
            }
            map.put("checkWeekStatus5", checkWeekStatus5);
            //第六周
            String checkWeekStatus6 = "";
            if (weeks[6].equals("1")) {
                checkWeekStatus6 = "√";
            } else {
                checkWeekStatus6 = "×";
            }
            map.put("checkWeekStatus6", checkWeekStatus6);
            //第七周
            String checkWeekStatus7 = "";
            if (weeks[7].equals("1")) {
                checkWeekStatus7 = "√";
            } else {
                checkWeekStatus7 = "×";
            }
            map.put("checkWeekStatus7", checkWeekStatus7);
            //第8周
            String checkWeekStatus8 = "";
            if (weeks[8].equals("1")) {
                checkWeekStatus8 = "√";
            } else {
                checkWeekStatus8 = "×";
            }
            map.put("checkWeekStatus8", checkWeekStatus8);
            //第9周
            String checkWeekStatus9 = "";
            if (weeks[9].equals("1")) {
                checkWeekStatus9 = "√";
            } else {
                checkWeekStatus9 = "×";
            }
            map.put("checkWeekStatus9", checkWeekStatus9);
            //第10周
            String checkWeekStatus10 = "";
            if (weeks[10].equals("1")) {
                checkWeekStatus10 = "√";
            } else {
                checkWeekStatus10 = "×";
            }
            map.put("checkWeekStatus10", checkWeekStatus10);
            //第11周
            String checkWeekStatus11 = "";
            if (weeks[11].equals("1")) {
                checkWeekStatus11 = "√";
            } else {
                checkWeekStatus11 = "×";
            }
            map.put("checkWeekStatus11", checkWeekStatus11);
            //第12周
            String checkWeekStatus12 = "";
            if (weeks[12].equals("1")) {
                checkWeekStatus12 = "√";
            } else {
                checkWeekStatus12 = "×";
            }
            map.put("checkWeekStatus12", checkWeekStatus12);

            //第13周
            String checkWeekStatus13 = "";
            if (weeks[13].equals("1")) {
                checkWeekStatus13 = "√";
            } else {
                checkWeekStatus13 = "×";
            }
            map.put("checkWeekStatus13", checkWeekStatus13);

            //第14周
            String checkWeekStatus14 = "";
            if (weeks[14].equals("1")) {
                checkWeekStatus14 = "√";
            } else {
                checkWeekStatus14 = "×";
            }
            map.put("checkWeekStatus14", checkWeekStatus14);

            //第15周
            String checkWeekStatus15 = "";
            if (weeks[15].equals("1")) {
                checkWeekStatus15 = "√";
            } else {
                checkWeekStatus15 = "×";
            }
            map.put("checkWeekStatus15", checkWeekStatus15);

            //第16周
            String checkWeekStatus16 = "";
            if (weeks[16].equals("1")) {
                checkWeekStatus16 = "√";
            } else {
                checkWeekStatus16 = "×";
            }
            map.put("checkWeekStatus16", checkWeekStatus16);

            //第17周
            String checkWeekStatus17 = "";
            if (weeks[17].equals("1")) {
                checkWeekStatus17 = "√";
            } else {
                checkWeekStatus17 = "×";
            }
            map.put("checkWeekStatus17", checkWeekStatus17);

            //第18周
            String checkWeekStatus18 = "";
            if (weeks[18].equals("1")) {
                checkWeekStatus18 = "√";
            } else {
                checkWeekStatus18 = "×";
            }
            map.put("checkWeekStatus18", checkWeekStatus18);

            //第19周
            String checkWeekStatus19 = "";
            if (weeks[19].equals("1")) {
                checkWeekStatus19 = "√";
            } else {
                checkWeekStatus19 = "×";
            }
            map.put("checkWeekStatus19", checkWeekStatus19);

            //第20周
            String checkWeekStatus20 = "";
            if (weeks[20].equals("1")) {
                checkWeekStatus20 = "√";
            } else {
                checkWeekStatus20 = "×";
            }
            map.put("checkWeekStatus20", checkWeekStatus20);

            //第21周
            String checkWeekStatus21 = "";
            if (weeks[21].equals("1")) {
                checkWeekStatus21 = "√";
            } else {
                checkWeekStatus21 = "×";
            }
            map.put("checkWeekStatus21", checkWeekStatus21);

            //第22周
            String checkWeekStatus22 = "";
            if (weeks[22].equals("1")) {
                checkWeekStatus22 = "√";
            } else {
                checkWeekStatus22 = "×";
            }
            map.put("checkWeekStatus22", checkWeekStatus22);

            //第23周
            String checkWeekStatus23 = "";
            if (weeks[23].equals("1")) {
                checkWeekStatus23 = "√";
            } else {
                checkWeekStatus23 = "×";
            }
            map.put("checkWeekStatus23", checkWeekStatus23);


            list.add(map);
        }
        //设置表头
        String title = "常规检查记录统计表";
        String[] hearders = new String[]{"学院", "实验室名称", "第1周", "第2周", "第3周", "第4周", "第5周", "第6周", "第7周", "第8周", "第9周", "第10周", "第11周", "第12周", "第13周", "第14周",
                "第15周", "第16周", "第17周", "第18周", "第19周", "第20周", "第21周", "第22周", "第23周"};//表头数组
        String[] fields = new String[]{"academyName", "labRoomName", "checkWeekStatus1", "checkWeekStatus2", "checkWeekStatus3", "checkWeekStatus4", "checkWeekStatus5", "checkWeekStatus6", "checkWeekStatus7", "checkWeekStatus8", "checkWeekStatus9", "checkWeekStatus10", "checkWeekStatus11", "checkWeekStatus12"
                , "checkWeekStatus13", "checkWeekStatus14", "checkWeekStatus15", "checkWeekStatus16", "checkWeekStatus17", "checkWeekStatus18", "checkWeekStatus19", "checkWeekStatus20", "checkWeekStatus21", "checkWeekStatus22", "checkWeekStatus23"};//Financialresources对象属性数组
        TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders), fields);
        JsGridReportBase report = new JsGridReportBase(request, response);
        report.exportToExcel(title, shareService.getUser().getCname(), td);
    }

    /*********************************************************************
     * @功能：常规检查统计-获取实验室检查状态数据
     * @作者：李德
     * @日期：2017-11-15
     *********************************************************************/
    public List<LabRoom> getInspectionLabRoomsExport(HttpServletRequest request, RoutineInspection routineInspection, int weekCount) {

        String query = "select l from LabRoom l where 1=1";
        //增加查询条件-学院
        if (routineInspection.getAcademyNumber() != null && !"".equals(routineInspection.getAcademyNumber())) {
            query += " and l.labAnnex.labCenter.schoolAcademy.academyNumber ='" + routineInspection.getAcademyNumber() + "'";
        }
        //实验室查询
        if (routineInspection.getLabRoom() != null && !"".equals(routineInspection.getLabRoom()) && routineInspection.getLabRoom().getId() != null) {
            query += " and l.id =" + routineInspection.getLabRoom().getId();
        }
        //学期
        int schoolTerm = 0;
        if (routineInspection.getSchoolTerm() != null && !"".equals(routineInspection.getSchoolTerm()) && routineInspection.getSchoolTerm().getId() != null) {
            schoolTerm = routineInspection.getSchoolTerm().getId();
        }
        //System.out.println(query);
        List<LabRoom> labRooms = labRoomDAO.executeQuery(query, 0, -1);
        List<LabRoom> labRoomList = new ArrayList<LabRoom>();

        for (LabRoom lb : labRooms) {
            int[] typeAudit = getInspectionByLabRoom(lb, weekCount, schoolTerm);
            StringBuffer sb = new StringBuffer("%");
            for (int i = 0; i < typeAudit.length; i++) {
                sb.append(typeAudit[i]).append("%");
            }
            //System.out.println(sb);
            LabRoom labRoomTemp = new LabRoom();
            labRoomTemp.setLabAnnex(lb.getLabAnnex());
            labRoomTemp.setLabRoomName(lb.getLabRoomName() + "," + sb.toString());
            labRoomList.add(labRoomTemp);
        }

        return labRoomList;
    }

}