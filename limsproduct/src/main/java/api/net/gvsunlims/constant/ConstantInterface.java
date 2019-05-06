package api.net.gvsunlims.constant;


/****************************************************************************
 * Description: 统一实验室项目标志位
 *
 * @author:孟金城
 * @date : 2018-08-14
 ****************************************************************************/

public interface ConstantInterface{
        /****************************************************************************
         * Description: 实验室（Lab）模块{1.预约发布；2.预约保存}
         *
         * @author:魏诚
         * @date : 2018-09-06
         ****************************************************************************/
        public final static int COMMON_SELECT_LIMIT = 100;

        /****************************************************************************
         * Description: 功能模块定义
         *
         * @author:魏诚
         * @date : 2018-08-14
         ****************************************************************************/
        public String FUNCTION_MODEL_ACTION_TIMETABLE = "TIMETABLE";
        public String FUNCTION_MODEL_ACTION_DEVICEREPAIR = "DEVICEREPAIR";

        /****************************************************************************
         * Description: 教学排课（teaching process）模块
         * {排课状态 1：已发布 10未发布 2：待发布（待审核） 3:审核通过 4：审核拒绝 5：审核中}
         *
         * @author:魏诚
         * @date : 2018-08-14
         ****************************************************************************/
        public int TIMETABLE_STATUS_NO_APPOINTMENT = 0;
        public int TIMETABLE_STATUS_NO_PUBLIC = 10;
        public int TIMETABLE_STATUS_TOBE_PUBLIC = 2;
        public int TIMETABLE_STATUS_PUBLIC = 1;
        public final static int TIMETABLE_STATUS_NO_CONFIRM = 11;
        public final static int TIMETABLE_STATUS_AUDIT_ACCESS = 3;
        public final static int TIMETABLE_STATUS_AUDIT_REFUSE = 4;
        public final static int TIMETABLE_STATUS_AUDIT_ONGOING = 5;

        public int TIMETABLE_BATCH_IS_SELECT = 1;
        public int TIMETABLE_BATCH_IS_NOT_SELECT = 0;
        /****************************************************************************
         * Description: 权限定义{ROLE_STUDENT 学生：TEACHER 教师 ；PREEXTEACHING 实训部主任；EXCENTERDIRECTOR：中心主任}
         *
         * @author:魏诚
         * @date : 2018-08-14
         ****************************************************************************/

        public String AUTHORITY_RANGE_ACADEMY = "ACADEMY";      //学院限定有效
        public String AUTHORITY_RANGE_FIELD = "FIELD";           //字段限定有效
        public String AUTHORITY_RANGE_NONE = "NONE" ;            //无效
        //本实验中心主任/院系级系统管理员 只能查看和操作本学院的课程
        //排课教师只能查看和操作自己的课程
        public String AUTHORITY_TIMETABLE_BY_ACADEMY = "BY_ACADEMY" ;
        public String AUTHORITY_TIMETABLE_BY_TEACHER = "BY_TEACHER" ; //教师排自己的课，中心主任可用调整
        public String AUTHORITY_TIMETABLE_BY_DIRECTOR = "BY_DIRECTOR" ;//教师不排课，中心主任排所有课

        public String AUTHORITY_ROLE_STUDENT = "ROLE_STUDENT";
        public String AUTHORITY_ROLE_TEACHER = "ROLE_TEACHER";
        public String AUTHORITY_ROLE_PREEXTEACHING = "ROLE_PREEXTEACHING";
        public String AUTHORITY_ROLE_EXCENTERDIRECTOR = "ROLE_EXCENTERDIRECTOR";
        public String AUTHORITY_ROLE_ACADEMYLEVELM = "ROLE_ACADEMYLEVELM";
        public String AUTHORITY_ROLE_SUPERADMIN= "ROLE_SUPERADMIN";
        public String AUTHORITY_ROLE_ASSISTANT = "ROLE_ASSISTANT";// 系教学秘书
        public String AUTHORITY_ROLE_LABMANAGER= "ROLE_LABMANAGER";// 实验室管理员
        public String AUTHORITY_ROLE_DEPARTMENTHEADER= "ROLE_DEPARTMENTHEADER";// 教研室主任

        /****************************************************************************
         * Description: 教学排课（teaching process）模块{排课方式：1直接排课2调整排课3二次
         *              不分组排课4二次分组排课5.自主不分批排课 6.自主分批排课7.实验室预约
         *          *              8.教务推送（浙江建设、中医药）9.不分批循环排课 10.分批循环排课}
         *
         * @author:魏诚
         * @date : 2018-08-14
         ****************************************************************************/
        public int TIMETABLE_STYLE_DIRECT_COURSE = 1;
        public int TIMETABLE_STYLE_ADJUST_COURSE = 2;
        public int TIMETABLE_STYLE_SECOND_NOGROUP_COURSE = 3;
        public int TIMETABLE_STYLE_SECOND_GROUP_COURSE = 4;
        public int TIMETABLE_STYLE_INDEPENDENT_No_GOURP = 5;
        public int TIMETABLE_STYLE_INDEPENDENT_GROUP = 6;
        public int TIMETABLE_STYLE_LAB_RESERVATION = 7;
        public int TIMETABLE_STYLE_NO_APPOINTMENT = 0;

        /****************************************************************************
         * Description: 教学排课（teaching process）模块{排课对象：1设备2实验室
         *
         * @author:魏诚
         * @date : 2018-08-14
         ****************************************************************************/
        public int TIMETABLE_OBJECT_DEVICE = 1;
        public int TIMETABLE_OBJECT_LAB = 2;
        /****************************************************************************
         * Description: 通用常量定义
         *
         * @author:魏诚
         * @date : 2018-08-19
         ****************************************************************************/
        public String MESSAGE_SYNCHRONIZE_FINISH = "同步完成";
        public String MESSAGE_OK = "ok";

        /****************************************************************************
         * Description: 通用常量定义
         *
         * @author:魏诚
         * @date : 2018-08-19
         ****************************************************************************/
        public String STRING_COMMON_TRUE = "TRUE";
        public String STRING_COMMON_FALSE = "FALSE";
        public String STRING_COMMON_SESSION_SITE_ID = "cid";

        /****************************************************************************
         * Description: 学校模块（School Module）{课程标记为：1.自建课程；0.基础数据}
         *
         * @author:孟金城
         * @date : 2018-08-14
         ****************************************************************************/
        public int SCHOOL_MODULE_FLAG_SELF_COURSE = 1;
        public int SCHOOL_MODULE_FLAG_BASIS_COURSE = 0;

        /****************************************************************************
         * Description: 学校模块（School Module）{课程类型:1.两周实训课；2.独立设置实践课；
         *              3.课内实验课}
         *
         * @author:孟金城
         * @date : 2018-08-14
         ****************************************************************************/
        public String SCHOOL_MODULE_COURSE_TYPE_TWO_WEEK = "1";
        public String SCHOOL_MODULE_COURSE_TYPE_INDEPENDENT="2";
        public String SCHOOL_MODULE_COURSE_TYPE_TWO_INCLASS = "3";

        /****************************************************************************
         * Description: 学校模块（School Module）{是否是共享库推送的：1.推送的；0.老师维护的}
         *
         * @author:孟金城
         * @date : 2018-08-14
         ****************************************************************************/
        public int SCHOOL_MODULE_IS_SHARED_YES = 1;
        public int SCHOOL_MODULE_IS_SHARED_NO = 0;

        /****************************************************************************
         * Description: 学校模块（School Module）{现状码: 1．在用 2．多余 3．待修 4．待报废
         *              5.报废 8．降档 9．其它}
         *
         * @author:孟金城
         * @date : 2018-08-14
         ****************************************************************************/
        public int SCHOOL_MODULE_DEVICE_CONDITION_USE = 1;
        public int SCHOOL_MODULE_DEVICE_CONDITION_REDUNDANT = 2;
        public int SCHOOL_MODULE_DEVICE_CONDITION_UNATTENDED = 3;
        public int SCHOOL_MODULE_DEVICE_CONDITION_SCRAPPED = 4;
        public int SCHOOL_MODULE_DEVICE_CONDITION_SCRAP = 5;
        public int SCHOOL_MODULE_DEVICE_CONDITION_DOWNSHIFTS = 8;
        public int SCHOOL_MODULE_DEVICE_CONDITION_OTHER = 9;

        /****************************************************************************
         * Description: 学校模块（School Module）{仪器来源: 1．购置；2．捐赠；3．自制；4．校外调入}
         *
         * @author:孟金城
         * @date : 2018-08-14
         ****************************************************************************/
        public int SCHOOL_MODULE_DEVICE_ORIGIN_PURCHASE = 1;
        public int SCHOOL_MODULE_DEVICE_ORIGIN_DONATION = 2;
        public int SCHOOL_MODULE_DEVICE_ORIGIN_HOMEMADE = 3;
        public int SCHOOL_MODULE_DEVICE_ORIGIN_OUTSIDE = 4;

        /****************************************************************************
         * Description: 学校模块（School Module）{使用方向 1．教学为主；2．科研为主}
         *
         * @author:孟金城
         * @date : 2018-08-14
         ****************************************************************************/
        public int SCHOOL_MODULE_USE_DIRECTION_TEACH = 1;
        public int SCHOOL_MODULE_USE_DIRECTION_SCIENCE = 2;


        /****************************************************************************
         * Description: 课程模块（Course Module）{此次排课针对的是设备还是实验室: 1.设备  2.实验室}
         *
         * @author:孟金城
         * @date : 2018-08-14
         ****************************************************************************/
        public int COURSE_MODULE_DEVICE_OR_LAB_ISDEVICE = 1;
        public int COURSE_MODULE_DEVICE_OR_LAB_ISLAB = 2;

        /****************************************************************************
         * Description: 课程模块（Course Module）{执行方式：1.刷卡执行 2.任课教师确认执行
         *              3.实训室管理员确认执行 4.实训室管理员登记执行}
         *
         * @author:孟金城
         * @date : 2018-08-14
         ****************************************************************************/
        public int COURSE_MODULE_CONFIRM_TYPE_USE_CARD = 1;
        public int COURSE_MODULE_CONFIRM_TYPE_TEACHER = 2;
        public int COURSE_MODULE_CONFIRM_TYPE_LAB_ADMINISTRATOR = 3;
        public int COURSE_MODULE_CONFIRM_TYPE_LAB_ADMINISTRATOR_REGISTRATION= 4;

        /****************************************************************************
         * Description: 课程模块（Course Module）{使用对象: 1、校内师生   2、校外师生
         *              3、校外其他人员（外键关联c_dictionary的id字段）}
         *
         * @author:孟金城
         * @date : 2018-08-14
         ****************************************************************************/
        public int COURSE_MODULE_OBJECT_SCHOOL_TEACHERS_AND_STUDENTS = 1;
        public int COURSE_MODULE_OBJECT_OUTSCHOOL_TEACHERS_AND_STUDENTS = 2;
        public int COURSE_MODULE_OBJECT_OUTSCHOOL_OTHER_PERSONS = 3;

        /****************************************************************************
         * Description: 课程模块（Course Module）{实训室用途: 1.教学 2.科研 3.考试 4.鉴定
         *              5.培训 6.会议 7.其他（外键关联c_dictionary的id字段）}
         *
         * @author:孟金城
         * @date : 2018-08-14
         ****************************************************************************/
        public int COURSE_MODULE_APPLICATION_TEACH = 1;
        public int COURSE_MODULE_APPLICATION_SCIENCE = 2;
        public int COURSE_MODULE_APPLICATION_TEST = 3;
        public int COURSE_MODULE_APPLICATION_IDENTIFICATION = 4;
        public int COURSE_MODULE_APPLICATION_TRAINING = 5;
        public int COURSE_MODULE_APPLICATION_MEETING = 6;
        public int COURSE_MODULE_APPLICATION_OTHER = 7;

        /****************************************************************************
         * Description: 课程模块（Course Module）{使用设备故障情: 1.有故障 2.无故障（外键关
         *              联c_dictionary的id字段）}
         *
         * @author:孟金城
         * @date : 2018-08-14
         ****************************************************************************/
        public int COURSE_MODULE_EQUIPMENT_SITUATION_FAULT = 1;
        public int COURSE_MODULE_EQUIPMENT_SITUATION_NOFAULT = 2;

        /****************************************************************************
         * Description: 课程模块（Course Module）{使用后整理归位情况: 1.已整理 2.未整理
         *             （外键关联c_dictionary的id字段）}
         *
         * @author:孟金城
         * @date : 2018-08-14
         ****************************************************************************/
        public int COURSE_MODULE_TIDY_SITUATION_YES = 1;
        public int COURSE_MODULE_TIDY_SITUATION_NO = 2;

        /****************************************************************************
         * Description: 课程模块（Course Module）{页面按钮标志位: 0.查看 2.系主任审核 3.实
         *              训室管理员审核 4.实训部秘书审核}
         *
         * @author:孟金城
         * @date : 2018-08-14
         ****************************************************************************/
        public int COURSE_MODULE_BUTTON_MARK_VIEW = 0;
        public int COURSE_MODULE_BUTTON_MARK_DEPARTMENT_HEAD_AUDIT = 2;
        public int COURSE_MODULE_BUTTON_MARK_LAB_ADMINISTRATOR_AUDIT = 3;
        public int COURSE_MODULE_BUTTON_MARK_LAB_SECRETARY_AUDIT = 4;

        /****************************************************************************
         * Description: 课程模块（Course Module）{审核状态: 2.系主任审核阶段 3.实训室管理
         *              员审核阶段 5.实训部秘书审核阶段 6.全部都审核完成阶段}
         *
         * @author:孟金城
         * @date : 2018-08-14
         ****************************************************************************/
        public int COURSE_MODULE_STATE_DEPARTMENT_HEAD_AUDIT = 2;
        public int COURSE_MODULE_STATE_LAB_ADMINISTRATOR_AUDIT = 3;
        public int COURSE_MODULE_STATE_LAB_SECRETARY_AUDIT = 5;
        public int COURSE_MODULE_STATE_ALL_AUDIT = 6;

        /****************************************************************************
         * Description: 课程模块（Course Module）{标志位: 2.系主任 3.实训室管理员 4.实训教学秘书}
         *
         * @author:孟金城
         * @date : 2018-08-14
         ****************************************************************************/
        public int COURSE_MODULE_CHANGE_ADUIT_STATUS_DEPARTMENT_HEAD = 2;
        public int COURSE_MODULE_CHANGE_ADUIT_STATUS_LAB_ADMINISTRATOR = 3;
        public int COURSE_MODULE_CHANGE_ADUIT_STATUS_LAB_SECRETARY = 4;

        /****************************************************************************
         * Description: 课程模块（Course Module）{审核结果：0.不通过 1.通过}
         *
         * @author:孟金城
         * @date : 2018-08-14
         ****************************************************************************/
        public String COURSE_MODULE_RESULT_NOPASS = "0";
        public String COURSE_MODULE_RESULT_PASS ="1";

        /****************************************************************************
         * Description: 课程模块（Course Module）{是否学生选课：0.自动选课 1.学生选课 2.录
         *              入 3.循环排课分组 4.分批循环排课分组}
         *
         * @author:孟金城
         * @date : 2018-08-14
         ****************************************************************************/
        public int COURSE_MODULE_IFSELECT_AUTOMATIC_COURSE = 0;
        public int COURSE_MODULE_IFSELECT_STUDENT_COURSE = 1;
        public int COURSE_MODULE_IFSELECT_ENTRY = 2;
        public int COURSE_MODULE_IFSELECT_CYCLE_COURSE = 3;
        public int COURSE_MODULE_IFSELECT_PARTIAL_CYCLE_COURSE = 4;

        /****************************************************************************
         * Description: 课程模块（Course Module）{排课情况: 1.排课完成 0.未完成}
         *
         * @author:孟金城
         * @date : 2018-08-14
         ****************************************************************************/
        public int COURSE_MODULE_FLAG_YES = 1;
        public int COURSE_MODULE_FLAG_NO = 0;

        /****************************************************************************
         * Description 实验分室管理（lab_room）{1实验分室，2工作室，3会议室}
         * @author 陈乐为 2018-9-12
         ****************************************************************************/
        public final static int LAB_FOR_ROOM = 1;
        public final static int LAB_FOR_WORKSPACE = 2;
        public final static int LAB_FOR_MEETING = 3;
        public final static int LAB_FOR_CLASSROOM = 4;

        /****************************************************************************
         * Description 分页参数
         * @author 陈乐为 2018-9-14
         ****************************************************************************/
        public final static int PAGE_SIZE = 20;

        /****************************************************************************
         * Description 审核用前端参数{分辨业务类型}
         *
         * @author 陈乐为 2019-1-11
         ****************************************************************************/
        public String FIRM_TYPE_TIMETABLE = "TimetableAudit";// 教务排课审核
        public String FIRM_TYPE_LAB_RESERVATION = "LabRoomReservation";// 实验室预约
        public String FIRM_TYPE_ITEM = "OperationItem";// 项目立项
        public String FIRM_TYPE_DEVICE_REPAIR = "DeviceRepair";// 设备维修
        public String FIRM_TYPE_DEVICE_RESERVATION = "DeviceReservation";// 设备预约
        public String FIRM_TYPE_VIRTUAL_RESERVATION = "VirtualImageReservation";// 镜像预约
        public String FIRM_TYPE_SELF_TIMETABLE = "SelfTimetableAudit";// 自主排课审核
}