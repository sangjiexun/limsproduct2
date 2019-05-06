package net.zjcclims.service.routineInspection;

import net.zjcclims.domain.*;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface LabSecurityCheckService {
    /*********************************************************************
     * @内容：安全责任体系检查-统计符合条件的安全检查记录的总数
     * @作者：赵昶
     * @日期：2017-9-20
     *********************************************************************/
    public int count(HttpServletRequest request, LabSecurityCheck labSecurityCheck, String acno);

    /*********************************************************************
     * @内容：安全责任体系检查-统计符合条件的安全检查记录
     * @作者：赵昶
     * @日期：2017-9-20
     *********************************************************************/
    public List<LabSecurityCheck> findlabSecurityChecks(HttpServletRequest request, Integer currpage, Integer pageSize, LabSecurityCheck labSecurityCheck, String acno);

    /*********************************************************************
     * @内容：安全责任体系检查-新建-保存
     * @作者：赵昶
     * @日期：2017-9-21
     *********************************************************************/
    public LabSecurityCheck saveLabSecurityCheck(LabSecurityCheck labSecurityCheck);

    /*********************************************************************
     * @内容：查询字典表哪一类的哪一项的所有记录{types判断取是否取二级标题和三级标题（1），还是只取三级标题（2）}
     * @作者：赵昶
     * @日期：2017-9-21
     *********************************************************************/
    public List<SDictionary> findChecskdictionaries(Integer category, Integer noOne, Integer types);

    /*********************************************************************
     * @内容：查询字典表哪一类的所有三级标题
     * @作者：赵昶
     * @日期：2017-9-21
     *********************************************************************/
    public List<SDictionary> findChecskdictionariesOnlyThree(Integer category);

    /*********************************************************************
     * @内容：查询检查表对应的填写结果
     * @作者：赵昶
     * @日期：2017-9-27
     *********************************************************************/
    public List<LabSecurityCheckDetails> findChecskDetails(Integer checkId);

    /*********************************************************************
     * @内容：删除传来LabSecurityCheckDetails的对象集合
     * @作者：赵昶
     * @日期：2017-9-27
     *********************************************************************/
    public boolean deleteListLabSecurityCheckDetails(List<LabSecurityCheckDetails> labSecurityCheckDetails);

    /*********************************************************************
     * @内容：安全责任体系检查-统计专职管理员看到的安全检查记录的总数
     * @作者：赵昶
     * @日期：2017-9-28
     *********************************************************************/
    public int countLeaderLabSecurityCheck(LabSecurityCheck labSecurityCheck, String acno);

    /*********************************************************************
     * @内容：安全责任体系检查-专职管理员看到的安全检查记录
     * @作者：赵昶
     * @日期：2017-9-28
     *********************************************************************/
    public List<LabSecurityCheck> findLeaderLabSecurityChecks(Integer currpage, Integer pageSize, LabSecurityCheck labSecurityCheck, String acno);

    /*********************************************************************
     * @内容：查询字典表的所有检查类型
     * @作者：赵昶
     * @日期：2017-10-9
     *********************************************************************/
    public List<String> findDictionariesCheckTypes();

    /*********************************************************************
     * @内容：查询字典表该检查类型下所有检查项
     * @作者：赵昶
     * @日期：2017-10-9
     *********************************************************************/
    public List<String> findDictionariesCheckItems(Integer checkType);

    /*********************************************************************
     * @功能：安全检查统计-获取实验室检查状态数据
     * @作者：李德
     * @日期：2017-11-17
     *********************************************************************/
    public List<LabCenter> getSecurityCheckLabRooms(HttpServletRequest request, Integer currpage, Integer pageSize, LabSecurityCheck labSecurityCheck, String acno);

    /*********************************************************************
     * @功能：安全检查统计-获取实验室检查状态数据count
     * @作者：李德
     * @日期：2017-11-21
     *********************************************************************/
    public Integer countSecurityCheckLabRooms(HttpServletRequest request, LabSecurityCheck labSecurityCheck, Integer cid);

    /*********************************************************************
     * @功能：安全检查统计-处理各实验室各周次的检查状态数据
     * @作者：李德
     * @日期：2017-11-17
     *********************************************************************/
    public int[] getSecurityCheckByLabRoom(LabCenter labCenter, int schoolTerm);

    /*********************************************************************
     * @功能：安全检查统计-获取实验室检查状态数据
     * @作者：李德
     * @日期：2017-11-21
     *********************************************************************/
    public List<LabCenter> getSecurityCheckLabRoomsExport(HttpServletRequest request, LabSecurityCheck labSecurityCheck);

    /*********************************************************************
     * @功能：安全检查统计导出
     * @作者：李德
     * @日期：2017-11-21
     *********************************************************************/
    public void exportSecurityCheckLabRooms(@ModelAttribute List<LabCenter> securityCheckLabCenters, HttpServletRequest request, HttpServletResponse response) throws Exception;

}