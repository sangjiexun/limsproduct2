package net.zjcclims.service.schedule;

import net.zjcclims.domain.SchoolTerm;
import net.zjcclims.domain.User;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.vo.ScheduleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service("ScheduleService")
@Transactional
public class ScheduleServiceImpl implements ScheduleService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired private ShareService shareService;

    /**
     * Description 教师个人课表
     * @return
     * @author 陈乐为 2018-12-28
     */
    @Override
    public List<ScheduleVO> findScheduleByQuery(HttpServletRequest request) {
        SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
        User user = shareService.getUserDetail();
        int week = 0;
        if(request.getParameter("week") != null && !request.getParameter("week").equals("")) {
            week = Integer.valueOf(request.getParameter("week"));
        }

        Query query = entityManager.createNativeQuery("call proc_teacher_schedule("+schoolTerm.getId()+","+schoolTerm.getTermCode()+",null ,"+user.getUsername()+","+week+")");
        // 获取list对象
        List<Object[]> list = query.getResultList();
        List<ScheduleVO> scheduleVOS = new ArrayList<>();
        for(Object[] obj : list) {
            ScheduleVO scheduleVO = new ScheduleVO();
            scheduleVO.setSection(obj[7].toString());// 节次
            scheduleVO.setWeekday(obj[4].toString());// 星期
            scheduleVO.setWeeks("第"+obj[5].toString()+"-"+obj[6].toString()+"周");
            scheduleVO.setCourse(obj[2].toString());// 课程
            if(obj[3]!=null) {
                scheduleVO.setItem(obj[3].toString());// 项目
            }else {
                scheduleVO.setItem("无项目");
            }
            scheduleVO.setAddress(obj[13].toString());// 上课地点
            if(obj[9]!=null && obj[11]!=null && !obj[11].equals("")) {
                scheduleVO.setTeacher(obj[9].toString() + " " + obj[11].toString());// 教师
            }else if(obj[9]!=null){
                scheduleVO.setTeacher(obj[9].toString());// 教师
            }
            scheduleVOS.add(scheduleVO);
        }
        return scheduleVOS;
    }

}