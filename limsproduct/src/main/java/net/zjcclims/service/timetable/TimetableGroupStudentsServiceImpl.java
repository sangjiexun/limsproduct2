package net.zjcclims.service.timetable;



import net.zjcclims.dao.TimetableGroupDAO;
import net.zjcclims.dao.TimetableGroupStudentsDAO;
import net.zjcclims.domain.TimetableGroup;
import net.zjcclims.domain.TimetableGroupStudents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("TimetableGroupStudentsService")
public class TimetableGroupStudentsServiceImpl implements TimetableGroupStudentsService {
    @Autowired
    private TimetableGroupStudentsDAO timetableGroupStudentsDAO;
    @Autowired
    private TimetableGroupDAO timetableGroupDAO;

    /*************************************************************************************
     * @內容：根据学生id查找相对应的组
     * @作者： 麦凯俊
     * @日期：2018-8-8
     *************************************************************************************/
    public TimetableGroupStudents findTimetableGroupStudentsByGroupId(String id){
        String sql ="select s from TimetableGroupStudents s where s.timetableGroup.id = "+id;
        TimetableGroupStudents stu = (TimetableGroupStudents) timetableGroupStudentsDAO.executeQuerySingleResult(sql);
        return stu;
    }

    /*************************************************************************************
     * @內容：查找其他组学生列表
     * @作者： 麦凯俊
     * @日期：2018-8-8
     *************************************************************************************/
    public List<TimetableGroupStudents> findElseGroupStudents(int groupId){
        /*String sql = "select s from TimetableGroupStudents s where s.timetableGroup.id != "+groupId
                +" and s.timetableGroup.id in( select g.id from TimetableGroup g where g.timetableBatch.id = (" +
                " select g1.timetableBatch.id from TimetableGroup g1 where g1.id = "+groupId+"))";
        List<TimetableGroupStudents> studentsList = timetableGroupStudentsDAO.executeQuery(sql,-1,-1);*/
        String sql ="select g from TimetableGroup g where g.id = "+groupId;
        TimetableGroup group  = (TimetableGroup) timetableGroupDAO.executeQuerySingleResult(sql);
        int batchId = group.getTimetableBatch().getId();
        String sql2 = "select s from TimetableGroupStudents s where s.timetableGroup.id != "+groupId
                +" and s.timetableGroup.timetableBatch.id = "+batchId;
        List<TimetableGroupStudents> studentsList = timetableGroupStudentsDAO.executeQuery(sql2,-1,-1);
        return studentsList;
    }
}
