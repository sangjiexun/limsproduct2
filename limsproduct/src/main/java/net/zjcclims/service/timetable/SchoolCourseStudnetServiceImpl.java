package net.zjcclims.service.timetable;


import net.zjcclims.dao.SchoolCourseStudentDAO;
import net.zjcclims.domain.SchoolCourseStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("SchoolCourseStudnetService")
public class SchoolCourseStudnetServiceImpl implements SchoolCourseStudentService {
    @Autowired
    private SchoolCourseStudentDAO schoolCourseStudentDAO;
    @Override
    public List<SchoolCourseStudent> getSchoolCousrseStudnetByCourseNo(String courseNo) {
        String sql = "select c from SchoolCourseStudent c where c.schoolCourseDetail.schoolCourse = '"
                + courseNo + "' and c.state=1 group by c.userByStudentNumber.username";
        List<SchoolCourseStudent> schoolCourseStudentList = schoolCourseStudentDAO.executeQuery(sql,-1,-1);
        return schoolCourseStudentList;
    }
}
