package net.zjcclims.service.timetable;



import net.zjcclims.domain.TimetableGroupStudents;

import java.util.List;

public interface TimetableGroupStudentsService {

    /*************************************************************************************
     * @內容：根据学生id查找相对应的组
     * @作者： 麦凯俊
     * @日期：2018-8-8
     *************************************************************************************/
    public TimetableGroupStudents findTimetableGroupStudentsByGroupId(String id);

    /*************************************************************************************
     * @內容：查找其他组学生列表
     * @作者： 麦凯俊
     * @日期：2018-8-8
     *************************************************************************************/
    public List<TimetableGroupStudents> findElseGroupStudents(int groupId);
}
