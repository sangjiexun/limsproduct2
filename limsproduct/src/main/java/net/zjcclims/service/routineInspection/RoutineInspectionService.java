package net.zjcclims.service.routineInspection;

import net.zjcclims.domain.*;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface RoutineInspectionService {
    /*********************************************************************
     * @内容：常规检查模块
     * @作者：赵昶
     * @日期：2017-9-4
     *********************************************************************/
    //返回记录总数
    public int Count(HttpServletRequest request, RoutineInspection routineInspection, String acno);

    //返回所有的"常规检查"记录
    public List<RoutineInspection> findAllRoutineInspection(HttpServletRequest request, Integer currpage, Integer pageSize, RoutineInspection routineInspection, String acno);

    //返回所登陆用户管理员所管辖的实验室
    public List<LabRoom> getLabRooms();

    //返回专职管理员或者学院领导所属院系的实验室
    public List<LabRoom> getAcademyLabRooms(String acno);

    /*********************************************************************
     * @功能：查询登录人所属学院的实验中心
     * @作者：张德冰
     * @日期：2018.08.20
     *********************************************************************/
    public List<LabCenter> getAcademyLabCenters(String acno);

    /*********************************************************************
     * @功能：查询实验中心主任所属实验中心
     * @作者：张德冰
     * @日期：2018-12-11
     *********************************************************************/
    public List<LabCenter> getLabCenterByCenterManager();


    //保存常规检查记录
    public RoutineInspection saveRoutineInspection(RoutineInspection routineInspection);

    //多文件上传
    public String uploaddnolinedocment(HttpServletRequest request, HttpServletResponse response, Integer id);

    //返回是登录人所属学院的所有记录
    public List<RoutineInspection> findAllAcademyRoutineInspection(int currpage, int pageSize,
                                                                   RoutineInspection routineInspection, String acno);

    //返回是登录人所属学院的所有记录总数
    public int CountAcademy(RoutineInspection routineInspection, String acno);

    //返回所有的"常规检查上报"记录
    public List<RoutineInspection> findAllReportRoutineInspection(Integer currpage, Integer pageSize, RoutineInspection routineInspection, String acno);

    //返回是登录人所属学院的所有记录总数
    public int CountReportAcademy(RoutineInspection routineInspection, String acno);

    //返回是登录人所属学院的所有已提交的上报常规检查记录总数
    public int CountLeaderReport(RoutineInspection routineInspection, String acno);

    //返回是登录人所属学院的已提交的上报常规检查所有记录
    List<RoutineInspection> findLeaderReportRoutineInspection(Integer currpage,
                                                              Integer pageSize, RoutineInspection routineInspection, String acno);

    //抽检(types：1学生，2督导)-统计属于该学生的抽检记录总数
    public int countStudnetCasual(Integer types, RoutineInspection routineInspection, String acno);

    //抽检(types：1学生，2督导)-返回所有属于该学生的抽检记录
    public List<RoutineInspection> findStudnetCasual(Integer currpage, Integer pageSize, RoutineInspection routineInspection, Integer types, String acno);

    //学生抽检(设备科)-返回所有学生或者督导的抽检记录
    public List<RoutineInspection> findAllStudnetCasual(HttpServletRequest request, Integer currpage, Integer pageSize, RoutineInspection routineInspection, Integer types,  String acno);

    //统计专项检查记录总数
    public int countSpecialExamination(SpecialExamination specialExamination, String acno);

    //查找专项检查记录
    public List<SpecialExamination> findSpecialExamination(HttpServletRequest request, Integer currpage, Integer pageSize, SpecialExamination specialExamination, String acno);

    /*********************************************************************
     * @功能：常规检查统计-获取实验室检查状态数据
     * @作者：李德
     * @日期：2017-11-15
     *********************************************************************/
    public List<LabRoom> getInspectionLabRooms(HttpServletRequest request, Integer currpage, Integer pageSize, RoutineInspection routineInspection, int weekCount, String acno);

    /*********************************************************************
     * @功能：常规检查统计-处理各实验室各周次的检查状态数据
     * @作者：李德
     * @日期：2017-11-15
     *********************************************************************/
    public int[] getInspectionByLabRoom(LabRoom labRoom, int weekCount, int schoolTerm);

    /*********************************************************************
     * @功能：常规检查统计-获取实验室检查状态数据count
     * @作者：李德
     * @日期：2017-11-21
     *********************************************************************/
    public Integer countInspectionLabRooms(HttpServletRequest request, RoutineInspection routineInspection, Integer cid);

    /*********************************************************************
     * @功能：常规检检查统计导出
     * @作者：李德
     * @日期：2017-11-22
     *********************************************************************/
    public void exportGeneralCheckLabRooms(@ModelAttribute List<LabRoom> generalCheckLabRooms, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /*********************************************************************
     * @功能：常规检查统计-获取实验室检查状态数据
     * @作者：李德
     * @日期：2017-11-22
     *********************************************************************/
    public List<LabRoom> getInspectionLabRoomsExport(HttpServletRequest request, RoutineInspection routineInspection, int weekCount);
}