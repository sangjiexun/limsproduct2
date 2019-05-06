package net.zjcclims.service.schedule;

import net.zjcclims.vo.ScheduleVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ScheduleService {
    /**
     * Description 教师个人课表
     * @return
     * @author 陈乐为 2018-12-28
     */
    public List<ScheduleVO> findScheduleByQuery(HttpServletRequest request);
}