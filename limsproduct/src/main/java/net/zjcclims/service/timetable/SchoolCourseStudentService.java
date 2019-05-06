package net.zjcclims.service.timetable;



import net.zjcclims.domain.SchoolCourseStudent;

import java.util.List;

public interface SchoolCourseStudentService {
    public List<SchoolCourseStudent> getSchoolCousrseStudnetByCourseNo(String courseNo);
}
