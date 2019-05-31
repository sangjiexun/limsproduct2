package net.zjcclims.web.virtual;

import net.zjcclims.service.virtual.VirtualService;
import net.zjcclims.vo.CourseSchedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/***********************************************************
 * Description: 根据课表启动虚拟镜像的自动计划，每天执行一次
 *
 * @author 陈敬2019年3月15日
 ***********************************************************/
public class StartVirtualImageByCourseSchedules extends Thread {
    private VirtualService virtualService;

    public StartVirtualImageByCourseSchedules(VirtualService virtualService) {
        this.virtualService = virtualService;
    }

    @Override
    public void run() {
        super.run();

        Timer timer = new Timer(true);//把与timer关联的线程设为后台线程
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    System.out.println("=============获取" + new Date().toString() + "课程启动计划表============");
                    List<CourseSchedule> todayCourseSchedules = virtualService.getTodayCourseSchedules();
                    System.out.println("=============调用接口启动虚拟镜像=======================================");
                    try {
                        virtualService.startVirtualImageByCourseSchedules(todayCourseSchedules);
                        System.out.println("=============今天的启动计划完成=========================================");
                    } catch (Exception e) {//catch代码块中的语句可能无法执行，因为post接口即使发生异常也不会抛出它
                        System.out.println("=============调用远程接口发生异常=======================================");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
       /*try {
            String stringToday = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            Date today = new SimpleDateFormat("yyyy-MM-dd").parse(stringToday);
            //明天凌晨1点
            Date tomorrow = new Date(today.getTime() + 25 * 3600 * 1000);
            //循环执行，间隔时间为一天
            timer.schedule(timerTask, tomorrow.getTime() - new Date().getTime(), 24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        //timer.schedule(timerTask, 0, 24 * 60 * 60 * 1000);
        //timer.schedule(timerTask, 0, 10 * 1000);
    }
}
