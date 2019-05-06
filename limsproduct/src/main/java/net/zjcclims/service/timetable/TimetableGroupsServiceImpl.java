package net.zjcclims.service.timetable;


import net.zjcclims.dao.TimetableGroupDAO;
import net.zjcclims.dao.TimetableGroupStudentsDAO;
import net.zjcclims.domain.TimetableGroup;
import net.zjcclims.domain.TimetableGroupStudents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;

@Service("TimetableGroupService")
public class TimetableGroupsServiceImpl implements TimetableGroupsService {
    @Autowired
    private TimetableGroupDAO timetableGroupDAO;
    @Autowired
    private TimetableGroupStudentsService timetableGroupStudentsService;
    @Autowired
    private TimetableGroupStudentsDAO timetableGroupStudentsDAO;


    /*************************************************************************************
     * @內容：根据组别查找分组
     * @作者： 麦凯俊
     * @日期：2018-8-8
     *************************************************************************************/
    @Transient
    public TimetableGroup findTimetableGroupByGroupNum(Integer groupId){
        String sql = "select s from TimetableGroup s where s.id = "+groupId;
        TimetableGroup group = (TimetableGroup) timetableGroupDAO.executeQuerySingleResult(sql);
        return group;
    }

    /*************************************************************************************
     * @內容：添加分组学生名单
     * @作者： 麦凯俊
     * @日期：2018-8-8
     *************************************************************************************/
    @Transactional
    public void addGroupStudent(String[] groupIds,Integer groupId){
        for(String s:groupIds){
            TimetableGroupStudents stu = timetableGroupStudentsService.findTimetableGroupStudentsByGroupId(s);
            //减去组的人数
            TimetableGroup group_sub = stu.getTimetableGroup();
            group_sub.setNumbers(group_sub.getNumbers()-1);
            timetableGroupDAO.store(group_sub);

            //增加组的人数
            TimetableGroup group_add = this.findTimetableGroupByGroupNum(groupId);
            group_add.setNumbers(group_add.getNumbers()+1);
            timetableGroupDAO.store(group_add);

            stu.setTimetableGroup(group_add);
            timetableGroupStudentsDAO.store(stu);
        }
    }
}
