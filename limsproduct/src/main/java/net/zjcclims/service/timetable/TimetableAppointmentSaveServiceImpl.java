package net.zjcclims.service.timetable;

import api.net.gvsunlims.constant.ConstantInterface;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**********************************************
 * Description: 排课模块{各种形式排课的保存实现}
 *
 * @author 贺子龙
 * @date 2016-08-31
 ***********************************************/
@Service
public class TimetableAppointmentSaveServiceImpl implements TimetableAppointmentSaveService {

    @Autowired
    private TimetableAppointmentDAO timetableAppointmentDAO;
    @Autowired
    private TimetableLabRelatedDAO timetableLabRelatedDAO;
    @Autowired
    private LabRoomDAO labRoomDAO;
    @Autowired
    private OperationItemDAO operationItemDAO;
    @Autowired
    private TimetableItemRelatedDAO timetableItemRelatedDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private TimetableTeacherRelatedDAO timetableTeacherRelatedDAO;
    @Autowired
    private TimetableGroupDAO timetableGroupDAO;
    @Autowired
    private TimetableAppointmentSameNumberDAO timetableAppointmentSameNumberDAO;
    @Autowired
    private OuterApplicationServiceImpl outerApplicationServiceImpl;
    @Autowired
    private SchoolCourseDAO schoolCourseDAO;
    @Autowired
    private TimetableAppointmentCycleDAO timetableAppointmentCycleDAO;
    @Autowired
    private TimetableAppointmentService timetableAppointmentService;
    @Autowired
    private TimetableTutorRelatedDAO timetableTutorRelatedDAO;
    @Autowired
    private SchoolTermDAO schoolTermDAO;

    /***********************************************************************************************
     * Description：排课模块{保存教务实验课}
     *
     * @author：贺子龙
     * @Date：2016-08-31
     ***********************************************************************************************/
    public TimetableAppointment saveSchoolTimetable(int term, int[] classes, int[] labrooms, int[] weekArray,
                                                    int weekday, int[] items, String teachers, String courseNo, Integer isOther) {
        // 定义布尔变量，表征是否满足判冲条件
        boolean timeOK = true;
        // 把已选的周次和可用周次比对
        // 通过接口来查出可用的周次
        Integer[] validWeeks = outerApplicationServiceImpl.getValidWeeks(term, classes, labrooms, weekday, 0);
        for (int chosenWeek : weekArray) {
            if (!EmptyUtil.isIntegerArray(validWeeks, chosenWeek)) {// 所选的周次有不包含在可用周次范围内的
                timeOK = false;
            }
        }
        if (timeOK) {// 时间、地点合法，允许排课
            // 周次进行排序
            String[] sWeek = this.getTimetableWeekClass(weekArray);
            // 节次进行排序
            String[] sClasses = this.getTimetableWeekClass(classes);
            // 判断有没有跳节、跳周
            boolean jumpTime = false; // 默认没有
            if (sWeek.length > 1 || sClasses.length > 1) {
                jumpTime = true;
            }
            // 根据编号找到对应教务课程
            SchoolCourse course = schoolCourseDAO.findSchoolCourseByPrimaryKey(courseNo);
            // 保存排课主表
            TimetableAppointment timetableAppointment = new TimetableAppointment();
            // 保存操作时间
            timetableAppointment.setCreatedDate(Calendar.getInstance());
            timetableAppointment.setUpdatedDate(Calendar.getInstance());
            // 设置排课的选课组编号
            timetableAppointment.setCourseCode(course.getCourseCode());
            timetableAppointment.setSchoolCourse(course);
            //保存学期
            timetableAppointment.setSchoolTerm(course.getSchoolTerm());
            timetableAppointment.setSchoolCourseInfo(course.getSchoolCourseInfo());
            if (course != null && course.getSchoolCourseDetails() != null && course.getSchoolCourseDetails().size() > 0) {
                for (SchoolCourseDetail s : course.getSchoolCourseDetails()) {
                    timetableAppointment.setSchoolCourseDetail(s);
                }

            }
            // 生成主表公用数据
            timetableAppointment = this.saveTimetableMain(timetableAppointment, sWeek, sClasses, weekday);
            // 保存排课类型为教务不分批
            if (!EmptyUtil.isIntegerEmpty(isOther) && isOther.equals(1)) {// 其他时间段
                timetableAppointment.setTimetableStyle(3);
            } else {// 教务安排时间段
                timetableAppointment.setTimetableStyle(2);
            }
            // 保存主表
            timetableAppointment = timetableAppointmentDAO.store(timetableAppointment);
            // 保存排课关联表
            // 1 保存排课跳周表（如果有的话）
            if (jumpTime) {
                this.saveTimetableAppointmentSameNumber(timetableAppointment, sWeek, sClasses);
            }
            // 2 保存教师
            this.saveTimetableTeacherRelated(timetableAppointment, teachers);
            // 3 保存项目
            this.saveTimetableItemRelated(timetableAppointment, items);
            // 4 保存实验室
            this.saveTimetableLabRelated(timetableAppointment, labrooms);
            // 返回成功
            return timetableAppointment;
        } else {// 该时间、该实验室已经被占用，不能排课
            // 返回失败
            return new TimetableAppointment();
        }

    }

    /***********************************************************************************************
     * Description：排课模块{保存分组排课}
     *
     * @author：贺子龙
     * @Date：2016-08-31
     ***********************************************************************************************/
    public TimetableAppointment saveGroupTimetable(int term, int[] classes, int[] labrooms, int[] weekArray,
                                                   int weekday, int[] items, String teachers, String courseNo, int groupId, Integer isAdmin, String teachers2) {
        // 创建对象
        TimetableAppointment timetableAppointment = new TimetableAppointment();
        // 分组与不分组之间的差别就在于分组信息的保存
        // 所以，首先利用已经写好的方法，将除分组信息之外的其他信息保存
        // 按isAdmin分两种情况：
        if (!EmptyUtil.isIntegerEmpty(isAdmin) && isAdmin.equals(1)) {// 教务分组
            timetableAppointment = timetableAppointmentService.saveSchoolTimetable(
                    term, classes, labrooms, weekArray, weekday, teachers, courseNo, 1, teachers2, items);
        }
        // 判断是否保存成功
        if (timetableAppointment.getId() != null) {// 保存成功
            if (!EmptyUtil.isIntegerEmpty(isAdmin) && isAdmin.equals(1)) {// 教务分组
                timetableAppointment.setTimetableStyle(4);
            } else {
                timetableAppointment.setTimetableStyle(6);
            }
            // 接着保存分组信息
            Set<TimetableGroup> timetableGroupSet = new HashSet<TimetableGroup>();
            timetableGroupSet.add(timetableGroupDAO.findTimetableGroupById(groupId));
            timetableAppointment.setTimetableGroups(timetableGroupSet);
            timetableAppointmentDAO.store(timetableAppointment);
            timetableAppointmentDAO.flush();
        } else {// 保存不成功
            // do nothing
        }
        return timetableAppointment;
    }

    /*************************************************************************************
     * Description：排课保存公用方法{生成主表字段，除去选课组关联（自主排课选课组和教务排课选课组不同）}
     * @author： 贺子龙
     * @Date：2016-09-01
     *************************************************************************************/
    public TimetableAppointment saveTimetableMain(TimetableAppointment timetableAppointment,
                                                  String[] sWeek, String[] sClasses, int weekday) {
        // 保存操作时间
        timetableAppointment.setCreatedDate(Calendar.getInstance());
        timetableAppointment.setUpdatedDate(Calendar.getInstance());
        // 设置时间
        // 设置星期
        timetableAppointment.setWeekday(weekday);
        // 没有跳周、跳节
        // 设置周次
        int startWeek = 0;
        int endWeek = 0;
        if (sWeek[0].indexOf(("-")) == -1) {// 判断是否只有一周
            startWeek = Integer.parseInt(sWeek[0]);
            endWeek = Integer.parseInt(sWeek[0]);
        } else {// 如果是一个区间
            startWeek = Integer.parseInt(sWeek[0].split("-")[0]);
            endWeek = Integer.parseInt(sWeek[0].split("-")[1]);
        }
        timetableAppointment.setTotalWeeks(String.valueOf((endWeek - startWeek + 1)));
        timetableAppointment.setStartWeek(startWeek);
        timetableAppointment.setEndWeek(endWeek);
        // 设置节次
        int startClass = 0;
        int endClass = 0;
        if (sClasses[0].indexOf(("-")) == -1) {// 判断是否只有一节
            startClass = Integer.parseInt(sClasses[0]);
            endClass = Integer.parseInt(sClasses[0]);
        } else {// 如果是一个区间
            startClass = Integer.parseInt(sClasses[0].split("-")[0]);
            endClass = Integer.parseInt(sClasses[0].split("-")[1]);
        }
        timetableAppointment.setTotalClasses(endClass - startClass + 1);
        timetableAppointment.setStartClass(startClass);
        timetableAppointment.setEndClass(endClass);
        // 保存排课状态为待提交
        timetableAppointment.setStatus(ConstantInterface.TIMETABLE_STATUS_NO_CONFIRM);
        return timetableAppointment;
    }

    /*************************************************************************************
     * Description：排课保存公用方法{保存跳周}
     * @author： 贺子龙
     * @Date：2016-09-01
     *************************************************************************************/
    public void saveTimetableAppointmentSameNumber(TimetableAppointment timetableAppointment,
                                                   String[] sWeek, String[] sClasses) {
        for (int i = 0; i < sWeek.length; i++) {
            for (int j = 0; j < sClasses.length; j++) {
                TimetableAppointmentSameNumber sameNumber = new TimetableAppointmentSameNumber();
                sameNumber.setCreatedDate(Calendar.getInstance());
                sameNumber.setUpdatedDate(Calendar.getInstance());
                // 设置跳周
                int startWeekSame = 0;
                int endWeekSame = 0;
                if (sWeek[i].indexOf(("-")) == -1) {// 判断是否只有一周
                    startWeekSame = Integer.parseInt(sWeek[i]);
                    endWeekSame = Integer.parseInt(sWeek[i]);
                } else {// 如果是一个区间
                    startWeekSame = Integer.parseInt(sWeek[i].split("-")[0]);
                    endWeekSame = Integer.parseInt(sWeek[i].split("-")[1]);
                }
                sameNumber.setTotalWeeks(String.valueOf(endWeekSame - startWeekSame + 1));
                sameNumber.setStartWeek(startWeekSame);
                sameNumber.setEndWeek(endWeekSame);
                // 设置跳节
                int startClassSame = 0;
                int endClassSame = 0;
                if (sClasses[j].indexOf(("-")) == -1) {// 判断是否只有一节
                    startClassSame = Integer.parseInt(sClasses[j]);
                    endClassSame = Integer.parseInt(sClasses[j]);
                } else {// 如果是一个区间
                    startClassSame = Integer.parseInt(sClasses[j].split("-")[0]);
                    endClassSame = Integer.parseInt(sClasses[j].split("-")[1]);
                }
                sameNumber.setTotalClasses(endClassSame - startClassSame + 1);
                sameNumber.setStartClass(startClassSame);
                sameNumber.setEndClass(endClassSame);
                sameNumber.setTimetableAppointment(timetableAppointment);
                timetableAppointmentSameNumberDAO.store(sameNumber);
                timetableAppointmentSameNumberDAO.flush();
            }
        }
    }

    /*************************************************************************************
     * Description：排课保存公用方法{保存教师}
     * @author： 贺子龙
     * @Date：2016-09-01
     *************************************************************************************/
    public void saveTimetableTeacherRelated(TimetableAppointment timetableAppointment, String teacherString) {
        // 转化为字符数组
        String[] teachers = teacherString.split(",");
        // 新建关联表
        TimetableTeacherRelated timetableTeacherRelated = new TimetableTeacherRelated();
        // 保存关联表
        if (teachers != null && teachers.length > 0) {
            for (int i = 0; i < teachers.length; i++) {
                timetableTeacherRelated.setUser(userDAO.findUserByUsername(teachers[i]));
                timetableTeacherRelated.setTimetableAppointment(timetableAppointment);
                timetableTeacherRelatedDAO.store(timetableTeacherRelated);
                timetableTeacherRelatedDAO.flush();
            }
        }
    }

    /*************************************************************************************
     * Description：排课保存公用方法{保存项目}
     * @author： 贺子龙
     * @Date：2016-09-01
     *************************************************************************************/
    public void saveTimetableItemRelated(TimetableAppointment timetableAppointment, int[] items) {
        // 新建关联表
        TimetableItemRelated timetableItemRelated = new TimetableItemRelated();
        // 保存关联表
        if (items != null && items.length > 0) {
            for (int i = 0; i < items.length; i++) {
                timetableItemRelated.setOperationItem(operationItemDAO.findOperationItemById(items[i]));
                timetableItemRelated.setTimetableAppointment(timetableAppointment);
                timetableItemRelatedDAO.store(timetableItemRelated);
                timetableItemRelatedDAO.flush();
            }
        }
    }

    /*************************************************************************************
     * Description：排课保存公用方法{保存实验室}
     * @author： 贺子龙
     * @Date：2016-09-01
     *************************************************************************************/
    public void saveTimetableLabRelated(TimetableAppointment timetableAppointment, int[] labRooms) {
        // 新建关联表
        TimetableLabRelated timetableLabRelated = new TimetableLabRelated();
        if (labRooms != null && labRooms.length > 0) {
            for (int labroomId : labRooms) {
                // 获取实验室
                LabRoom labRoom = labRoomDAO.findLabRoomById(labroomId);
                // 设置实验室
                timetableLabRelated.setLabRoom(labRoom);
                timetableLabRelated.setTimetableAppointment(timetableAppointment);
                timetableLabRelatedDAO.store(timetableLabRelated);
                timetableLabRelatedDAO.flush();
            }
        }
    }

    /*************************************************************************************
     * Description：排课保存公用方法{返回排课节次或者周次}
     * eg：[234,235,236]--->[234-236] [234,235,237,238]--->[234-235,237-238]
     * @author： 魏誠
     * @Date：2014-08-5
     *************************************************************************************/
    public String[] getTimetableWeekClass(int[] intWeeks) {
        String startWeek = "1";
        String endWeek = "1";
        String sWeek = "";
        Arrays.sort(intWeeks);
        // 创建根据学期来查询课程；
        for (int i = 0; i < intWeeks.length; i++) {
            if (i == 0) {
                startWeek = String.valueOf(intWeeks[i]);
                if (intWeeks.length == 1) {
                    sWeek = startWeek + ";";
                }
            } else {
                if (intWeeks[i] - intWeeks[i - 1] == 1) {
                    if (i == intWeeks.length - 1) {
                        endWeek = String.valueOf(intWeeks[i]);
                        sWeek = sWeek + startWeek + "-" + endWeek + ";";
                    } else {
                        continue;
                    }
                } else if (intWeeks[i] - intWeeks[i - 1] > 1 && intWeeks.length > i + 1) {
                    endWeek = String.valueOf(intWeeks[i - 1]);
                    sWeek = sWeek + startWeek + "-" + endWeek + ";";
                    startWeek = String.valueOf(intWeeks[i]);

                } else if (intWeeks[i] - intWeeks[i - 1] > 1 && intWeeks.length == i + 1) {
                    endWeek = String.valueOf(intWeeks[i - 1]);
                    sWeek = sWeek + startWeek + "-" + endWeek + ";";
                    sWeek = sWeek + String.valueOf(intWeeks[i]) + "-" + String.valueOf(intWeeks[i]);
                }
            }
        }
        return sWeek.split(";");
    }

    /***********************************************************************************************
     * Description：排课模块{保存修改的排课}
     *
     * @author：郑昕茹
     * @Date：2016-11-05
     ***********************************************************************************************/
    public TimetableAppointment saveChangeTimetable(int term, int[] classes, int[] labrooms, int[] weekArray,
                                                    int weekday, int[] items, int tableAppId) {
        TimetableAppointment oldAppointment = timetableAppointmentService.findTimetableAppointmentByPrimaryKey(tableAppId);
        // 定义布尔变量，表征是否满足判冲条件
        boolean timeOK = true;
        // 把已选的周次和可用周次比对
        // 通过接口来查出可用的周次
        Integer[] validWeeks = outerApplicationServiceImpl.getValidWeeks(term, classes, labrooms, weekday, tableAppId);
        for (int chosenWeek : weekArray) {
            if (!EmptyUtil.isIntegerArray(validWeeks, chosenWeek)) {// 所选的周次有不包含在可用周次范围内的
                timeOK = false;
            }
        }
        if (timeOK) {// 时间、地点合法，允许排课
            // 周次进行排序
            String[] sWeek = this.getTimetableWeekClass(weekArray);
            // 节次进行排序
            String[] sClasses = this.getTimetableWeekClass(classes);
            // 判断有没有跳节、跳周
            boolean jumpTime = false; // 默认没有
            if (sWeek.length > 1 || sClasses.length > 1) {
                jumpTime = true;
            }
            // 保存排课主表
            TimetableAppointment timetableAppointment = new TimetableAppointment();
            // 保存操作时间
            timetableAppointment.setCreatedDate(Calendar.getInstance());
            timetableAppointment.setUpdatedDate(Calendar.getInstance());
            // 设置排课的选课组编号
            timetableAppointment.setCourseCode(oldAppointment.getCourseCode());
            timetableAppointment.setSchoolCourse(oldAppointment.getSchoolCourse());
            //保存学期
            timetableAppointment.setSchoolTerm(schoolTermDAO.findSchoolTermById(term));
            timetableAppointment.setSchoolCourseInfo(oldAppointment.getSchoolCourseInfo());
            timetableAppointment.setSchoolCourseDetail(oldAppointment.getSchoolCourseDetail());
            timetableAppointment.setTimetableSelfCourse(oldAppointment.getTimetableSelfCourse());
            // 生成主表公用数据
            timetableAppointment = this.saveTimetableMain(timetableAppointment, sWeek, sClasses, weekday);
            timetableAppointment.setTimetableStyle(oldAppointment.getTimetableStyle());
            timetableAppointment.setTimetableGroups(oldAppointment.getTimetableGroups());
            timetableAppointment.setStatus(1);
            // 保存主表
            timetableAppointment = timetableAppointmentDAO.store(timetableAppointment);
            if (oldAppointment.getTimetableTeacherRelateds() != null && oldAppointment.getTimetableTeacherRelateds().size() > 0) {
                for (TimetableTeacherRelated t : oldAppointment.getTimetableTeacherRelateds()) {
                    t.setTimetableAppointment(timetableAppointment);
                    timetableTeacherRelatedDAO.store(t);
                    timetableTeacherRelatedDAO.flush();
                }
            }
            // 保存排课关联表
            // 1 保存排课跳周表（如果有的话）
            if (jumpTime) {
                this.saveTimetableAppointmentSameNumber(timetableAppointment, sWeek, sClasses);
            }
            // 3 保存项目
            this.saveTimetableItemRelated(timetableAppointment, items);
            // 4 保存实验室
            this.saveTimetableLabRelated(timetableAppointment, labrooms);
            oldAppointment.setTimetableTeacherRelateds(null);
            oldAppointment.setTimetableGroups(null);
            timetableAppointmentDAO.remove(oldAppointment);
            timetableAppointmentDAO.flush();
            // 返回成功
            return timetableAppointment;
        } else {// 该时间、该实验室已经被占用，不能排课
            // 返回失败
            return new TimetableAppointment();
        }

    }

    /***********************************************************************************************
     * Description：排课预存
     * @author：戴昊宇
     * @Date：2018-03-1
     ***********************************************************************************************/
    public TimetableAppointmentCycle saveCycleTimetable(int labroom, int item, String teachers, String teachers2, String courseNo) {
        TimetableAppointmentCycle cycle = new TimetableAppointmentCycle();
        cycle.setDetailNo(courseNo);
        cycle.setLabRoom(labRoomDAO.findLabRoomById(labroom));
        cycle.setUserByTeacher(userDAO.findUserByPrimaryKey(teachers));
        cycle.setUserByTutor(userDAO.findUserByPrimaryKey(teachers2));
        cycle.setOperationItem(operationItemDAO.findOperationItemById(item));
        timetableAppointmentCycleDAO.store(cycle);
        return cycle;
    }

    /***********************************************************************************************
     * Description：查找排课预存条数
     * @author：戴昊宇
     * @Date：2018-03-2
     ***********************************************************************************************/
    public int findCycleTimetable(String courseNo) {
        String sql = "select count(*) from TimetableAppointmentCycle c where c.detailNo='" + courseNo + "'";
        int count = ((Long) timetableAppointmentCycleDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
        return count;

    }

    /***********************************************************************************************
     * Description：通过courseNo查找排课预存
     * @author：戴昊宇
     * @Date：2018-03-2
     ***********************************************************************************************/
    public List<TimetableAppointmentCycle> findCycleTimetableBycourseNo(String courseNo) {
        String sql = "select c from TimetableAppointmentCycle c where c.detailNo='" + courseNo + "'";
        List<TimetableAppointmentCycle> list = timetableAppointmentCycleDAO.executeQuery(sql);
        return list;

    }

    /***********************************************************************************************
     * Description：通过courseNo查找循环排课分组
     * @author：戴昊宇
     * @Date：2018-03-2
     ***********************************************************************************************/
    public List<TimetableGroup> findTimetableGroupBycourseNo(String courseCode) {
        // 建立查询
        String sql = "select t from TimetableGroup t, TimetableBatch tb where 1=1";
        // 联合查询
        sql += " and t.timetableBatch.id = tb.id";
        // 限制条件（选课组编号）
        sql += " and tb.courseCode = '" + courseCode + "'";
        // 限制条件（循环排课分批）
        sql += " and tb.ifselect = 3";
        // 排序
        sql += " order by tb.id, t.id";
        // 执行查询
        List<TimetableGroup> timetableGroups = timetableGroupDAO.executeQuery(sql);
        // 结果返回
        return timetableGroups;
    }

    /***********************************************************************************************
     * Description：循环排课 保存
     *
     * @author：戴昊宇
     * @Date：2018-03-05
     ***********************************************************************************************/
    public TimetableAppointment saveCycleTimetableAppointment(int term, int[] classes, int labroom, int week,
                                                              int weekday, int item, String teachers, String courseNo, int groupId, String tutor, int flag) {
        // 创建对象
        TimetableAppointment timetableAppointment = new TimetableAppointment();
        // 将除分组信息之外的其他信息保存
        timetableAppointment = this.saveCycleSchoolTimetable(
                term, classes, labroom, week, weekday, teachers, courseNo, tutor, item, flag);
        // 判断是否保存成功
        if (timetableAppointment.getId() != null) {// 保存成功
            // 保存分组信息
            Set<TimetableGroup> timetableGroupSet = new HashSet<TimetableGroup>();
            timetableGroupSet.add(timetableGroupDAO.findTimetableGroupById(groupId));
            timetableAppointment.setTimetableGroups(timetableGroupSet);
            timetableAppointment.setSchoolTerm(schoolTermDAO.findSchoolTermById(term));
            timetableAppointmentDAO.store(timetableAppointment);
            timetableAppointmentDAO.flush();
        } else {// 保存不成功
            // do nothing
        }
        return timetableAppointment;
    }

    /***********************************************************************************************
     * Description：通过courseNo查找分批循环排课分组
     * @author：戴昊宇
     * @Date：2018-03-2
     ***********************************************************************************************/
    public List<TimetableGroup> findTimetableBatchGroupBycourseNo(String courseCode) {
        // 建立查询
        String sql = "select t from TimetableGroup t, TimetableBatch tb where 1=1";
        // 联合查询
        sql += " and t.timetableBatch.id = tb.id";
        // 限制条件（选课组编号）
        sql += " and tb.courseCode = '" + courseCode + "'";
        // 限制条件（循环排课分批）
        sql += " and tb.ifselect = 4";
        // 排序
        sql += " order by tb.id, t.id";
        // 执行查询
        List<TimetableGroup> timetableGroups = timetableGroupDAO.executeQuery(sql);
        // 结果返回
        return timetableGroups;
    }

    /***********************************************************************************************
     * Description：循环排课保存方法
     *
     * @author：戴昊宇
     * @Date：2018-03-07
     ***********************************************************************************************/
    public TimetableAppointment saveCycleSchoolTimetable(int term, int[] classes, int labroom, int week,
                                                         int weekday, String teachers, String courseNo, String tutor, int item, int flag) {
        // 节次进行排序
        String[] sClasses = this.getTimetableWeekClass(classes);
        // 根据编号找到对应教务课程
        SchoolCourse course = schoolCourseDAO.findSchoolCourseByPrimaryKey(courseNo);
        String courseNumber = course.getSchoolCourseInfo().getCourseNumber();
        // 保存排课主表
        TimetableAppointment timetableAppointment = new TimetableAppointment();
        // 保存操作时间
        timetableAppointment.setCreatedDate(Calendar.getInstance());
        timetableAppointment.setUpdatedDate(Calendar.getInstance());
        // 设置排课的选课组编号
        timetableAppointment.setCourseCode(course.getCourseCode());
        timetableAppointment.setSchoolCourse(course);
        //保存学期
        timetableAppointment.setSchoolTerm(schoolTermDAO.findSchoolTermById(term));
        timetableAppointment.setSchoolCourseInfo(course.getSchoolCourseInfo());
        if (course != null && course.getSchoolCourseDetails() != null && course.getSchoolCourseDetails().size() > 0) {
            for (SchoolCourseDetail s : course.getSchoolCourseDetails()) {
                timetableAppointment.setSchoolCourseDetail(s);
                timetableAppointment.setAppointmentNo(s.getCourseDetailNo());
            }

        }
        String[] sWeek = new String[1];
        sWeek[0] = String.valueOf(week);
        // 生成主表公用数据
        timetableAppointment = this.saveTimetableMain(timetableAppointment, sWeek, sClasses, weekday);
        if (flag == 1) {
            // 循环排课状态位为9
            timetableAppointment.setTimetableStyle(9);
        }
        if (flag == 2) {
            // 循环分批排课状态位为10
            timetableAppointment.setTimetableStyle(10);
        }
        // 保存主表
        timetableAppointment = timetableAppointmentDAO.store(timetableAppointment);
        // 保存排课关联表
        // 保存教师
        if (!EmptyUtil.isStringEmpty(teachers)) {
            this.saveTimetableTeacherRelated(timetableAppointment, teachers);
        }
        // 保存研究生助教
        if (!EmptyUtil.isStringEmpty(tutor)) {
            this.saveTimetableTutorRelated(timetableAppointment, tutor);
        }
        // 3 保存项目
        int[] items = new int[1];
        items[0] = item;
        this.saveTimetableItemRelated(timetableAppointment, items);
        // 4 保存实验室
        int[] labrooms = new int[1];
        labrooms[0] = labroom;
        this.saveTimetableLabRelated(timetableAppointment, labrooms);
        // 返回成功
        return timetableAppointment;
    }

    /*************************************************************************************
     * Description：排课保存公用方法{保存研究生}
     * @author： 戴昊宇
     * @Date：2018-03-05
     *************************************************************************************/
    public void saveTimetableTutorRelated(TimetableAppointment timetableAppointment, String tutor) {
        // 转化为字符数组
        String[] tutors = tutor.split(",");
        // 新建关联表
        TimetableTutorRelated timetableTutorRelated = new TimetableTutorRelated();
        // 保存关联表
        if (tutors != null && tutors.length > 0) {
            for (int i = 0; i < tutors.length; i++) {
                timetableTutorRelated.setUser(userDAO.findUserByUsername(tutors[i]));
                timetableTutorRelated.setTimetableAppointment(timetableAppointment);
                timetableTutorRelatedDAO.store(timetableTutorRelated);
                timetableTutorRelatedDAO.flush();
            }
        }
    }

}
