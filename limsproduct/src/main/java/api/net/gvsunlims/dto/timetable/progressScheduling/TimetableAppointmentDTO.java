package api.net.gvsunlims.dto.timetable.progressScheduling;

import java.io.Serializable;

/**************************************************************************
 * Description:教学进度排课-按课次顺序显示教学进度排课
 *
 * @author:杨新蔚
 * @date:2018/8/24
 **************************************************************************/
public class TimetableAppointmentDTO implements Serializable, Comparable<TimetableAppointmentDTO>{
    /*
     *周次
     */
    private int dATWeek;
    /*
     *星期
     */
    private int dATWeekday;
    /*
     *开始节次
     */
    private int dATStartClass;
    /*
     *结束节次
     */
    private int dATEndClass;


    public int getdATWeek() {
        return dATWeek;
    }

    public void setdATWeek(int dATWeek) {
        this.dATWeek = dATWeek;
    }

    public int getdATWeekday() {
        return dATWeekday;
    }

    public void setdATWeekday(int dATWeekday) {
        this.dATWeekday = dATWeekday;
    }

    public int getdATStartClass() {
        return dATStartClass;
    }

    public void setdATStartClass(int dATStartClass) {
        this.dATStartClass = dATStartClass;
    }

    public int getdATEndClass() {
        return dATEndClass;
    }

    public void setdATEndClass(int dATEndClass) {
        this.dATEndClass = dATEndClass;
    }

    @Override
    public int compareTo(TimetableAppointmentDTO o) {
        //先按照周次排序
        int i = this.getdATWeek() - o.getdATWeek();
        if(i == 0){
            //如果周次相等了再用星期进行排序
            int j= this.getdATWeekday() - o.getdATWeekday();
            //如果星期相等了再用节次进行排序
            if(j==0){
                return this.getdATStartClass()-o.getdATStartClass();
            }
            return j;
        }
        return i;
    }
}