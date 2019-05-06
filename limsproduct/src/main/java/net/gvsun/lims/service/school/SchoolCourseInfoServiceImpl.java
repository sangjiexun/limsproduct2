package net.gvsun.lims.service.school;

import api.net.gvsunlims.constant.ConstantInterface;
import net.gvsun.lims.dto.common.BaseActionAuthDTO;
import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.common.JsonValueDTO;
import net.gvsun.lims.dto.school.SchoolCourseDetailDTO;
import net.gvsun.lims.dto.timetable.TimetableMergeDTO;
import net.gvsun.lims.service.timetable.TimetableCommonService;
import net.gvsun.lims.service.user.UserActionService;
import net.gvsun.lims.vo.timtable.engineer.EduCourseListVO;
import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.SchoolCourseDAO;
import net.zjcclims.dao.SchoolCourseInfoDAO;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service("schoolCourseInfoService")
public class SchoolCourseInfoServiceImpl implements SchoolCourseInfoService {
    @Autowired
    private SchoolCourseInfoDAO schoolCourseInfoDAO;
    @Autowired
    private ShareService shareService;

    /*************************************************************************************
     * Description:课程管理-获取课程查询列表
     *
     * @author： 魏誠
     * @date：2018-10-26
     *************************************************************************************/
    public List<JsonValueDTO> apiSchoolCourseInfoListBySelect(String search) {
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select c from SchoolCourseInfo c where 1=1 " );
        //查询条件
        sql.append(" and c.academyNumber like '"+ shareService.getUser().getSchoolAcademy().getAcademyNumber() +"'");
        if (search != null && !"".equals(search)) {
            sql.append(" and (c.courseNumber like '%" + search + "%' or");
            sql.append(" c.courseName like '%" + search + "%' ) ");
        }
        sql.append(" order by c.courseName asc limit " + ConstantInterface.COMMON_SELECT_LIMIT);
        // 执行sb语句
        List<SchoolCourseInfo> schoolCourseInfos = schoolCourseInfoDAO.executeQuery(sql.toString(),0,ConstantInterface.COMMON_SELECT_LIMIT);

        //获取列表主查询语句
        StringBuffer sql_1 = new StringBuffer("select c from SchoolCourseInfo c where 1=1 " );
        //查询条件
        sql_1.append(" and c.academyNumber not like '"+ shareService.getUser().getSchoolAcademy().getAcademyNumber() +"'");
        if (search != null && !"".equals(search)) {
            sql_1.append(" and (c.courseNumber like '%" + search + "%' or");
            sql_1.append(" c.courseName like '%" + search + "%' ) ");
        }
        sql_1.append(" order by c.courseName asc limit " + ConstantInterface.COMMON_SELECT_LIMIT);
        // 执行sb语句
        List<SchoolCourseInfo> schoolCourseInfo1s = schoolCourseInfoDAO.executeQuery(sql_1.toString(),0,ConstantInterface.COMMON_SELECT_LIMIT);
        schoolCourseInfos.addAll(schoolCourseInfo1s);
        int total =schoolCourseInfos.size();
        //封装VO
        List<JsonValueDTO> schoolCourseInfoDTOs = new ArrayList<JsonValueDTO>();
        for(SchoolCourseInfo schoolCourseInfo:schoolCourseInfos){
            JsonValueDTO jsonValueDTO = new JsonValueDTO();
            //设置学院
            jsonValueDTO.setId(schoolCourseInfo.getCourseNumber());
            jsonValueDTO.setText(schoolCourseInfo.getCourseName()+"(编号："+schoolCourseInfo.getCourseNumber()+")");
            schoolCourseInfoDTOs.add(jsonValueDTO);
        }
        return schoolCourseInfoDTOs;
    }
}