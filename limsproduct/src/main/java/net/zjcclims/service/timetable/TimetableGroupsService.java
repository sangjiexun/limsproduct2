package net.zjcclims.service.timetable;


import net.zjcclims.domain.TimetableGroup;

public interface TimetableGroupsService {
    /*************************************************************************************
     * @內容：根据组别查找分组
     * @作者： 麦凯俊
     * @日期：2018-8-8
     *************************************************************************************/
    public TimetableGroup findTimetableGroupByGroupNum(Integer groupId);


    /*************************************************************************************
     * @內容：添加分组学生名单
     * @作者： 麦凯俊
     * @日期：2018-8-8
     *************************************************************************************/
    public void addGroupStudent(String[] groupIds, Integer gruoupIds);
}
