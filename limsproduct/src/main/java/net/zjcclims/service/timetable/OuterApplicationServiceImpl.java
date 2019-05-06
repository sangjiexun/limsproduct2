package net.zjcclims.service.timetable;

import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("OuterApplicationService")
public class OuterApplicationServiceImpl implements OuterApplicationService {

	@Autowired
	private ShareService shareService;
	@Autowired
	private LabRoomDAO labRoomDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private OperationItemDAO operationItemDAO;
	@Autowired
	private TimetableLabRelatedDAO timetableLabRelatedDAO;
	@Autowired
	private SchoolTermDAO schoolTermDAO;
	@Autowired
	private SchoolWeekDAO schoolWeekDAO;
	@Autowired
	private LabCenterDAO labCenterDAO;
	@Autowired
	private SoftwareDAO softwareDAO;
	@Autowired
	private TimetableAppointmentDAO timetableAppointmentDAO;
	@Autowired
	private TimetableSelfCourseService timetableSelfCourseService;
	@Autowired
	private SchoolCourseDAO schoolCourseDAO;
	/************************************************************
	 * @ 获取可排的实验分室列表 @ 作者：魏诚 @ 日期：2014-07-24
	 ************************************************************/
	public Map<Integer, String> getLabRoomMap(String acno) {
		Map<Integer, String> labRoomMap = new LinkedHashMap<Integer, String>(0);
		String academyNumber = "";
		// 如果没有获取有效的实验分室列表-根据登录用户的所属学院
		if (!acno.equals("-1")) {
			// 获取选择的实验中心
			academyNumber = shareService.findSchoolAcademyByPrimaryKey(acno).getAcademyNumber();
		} else {
			if(shareService.getUserDetail().getSchoolAcademy()!=null&&shareService.getUserDetail().getSchoolAcademy().getAcademyNumber()!=null) {
				academyNumber = shareService.getUserDetail().getSchoolAcademy().getAcademyNumber();
			}
		}

		// 如果没有获取有效的实验分室列表-根据登录用户的所属学院
		StringBuffer sql = new StringBuffer("select c from LabRoom c where 1=1"
				+ " and c.CDictionaryByLabRoom.CCategory='c_lab_room_type' and c.CDictionaryByLabRoom.CNumber = '1' "
				/*" and c.labAnnex.labCenter.schoolAcademy.academyNumber like '"
//				+ academyNumber.substring(0, SchoolConstantInterface.INT_SCHOOLACADEMY_NUMBER)
				+ academyNumber*/
				+ " and c.isUsed=1 "
		        + " and c.labCategory=1");

		List<LabRoom> list = labRoomDAO.executeQuery(sql.toString(), 0, -1);
		// 遍历实验分室
		for (LabRoom labRoom : list) {
			labRoomMap.put(labRoom.getId(),labRoom.getLabRoomName());
		}
		return labRoomMap;
	}

	/************************************************************
	 * @ 获取可排的实验项目列表 @ 作者：魏诚 @ 日期：2014-07-24
	 ************************************************************/
	public Map<Integer, String> getTimetableItemMap(String courseNo) {
		Map<Integer, String> timetableItemMap = new HashMap<Integer, String>();
		// 获取有效的实验分室列表-根据登录用户的所属学院
		String sql = "select c from OperationItem c "
				+ "where c.schoolCourseInfo.courseNumber like '%" + courseNo + "%'";
		sql += " and CDictionaryByLpStatusCheck.id ='545'";
		List<OperationItem> list = new ArrayList<OperationItem>(operationItemDAO.executeQuery(sql,0,-1));
		// 遍历实验分室
		for (OperationItem operationItem : list) {
			/*timetableItemMap.put(operationItem.getId(), operationItem.getItemName()*/
					timetableItemMap.put(operationItem.getId(), operationItem.getLpName()
			/*
			 * + ";" + operationItem.getItemNumber() + ";" +
			 * operationItem.getSchoolAcademyByCollege() .getAcademyName()
			 */);
		}
		return timetableItemMap;
	}

	/************************************************************
	 * @ 根据选课组编号获取可排的实验项目列表 @ 作者：魏诚 @ 日期：2014-07-24
	 ************************************************************/
	public List<OperationItem> getCourseCodeItemList(String courseCode) {
		// 获取有效的实验分室列表-根据登录用户的所属学院
		String sql = "select c from OperationItem c ";
		List<OperationItem> list = new ArrayList<OperationItem>(operationItemDAO.executeQuery(sql,0,-1));

		return list;
	}

	/************************************************************
	 * @根据选课组，获取可选的实验 列表
	 * @作者：魏诚
	 * @日期：2014-07-24
	 ************************************************************/
	public Map<String, String> getTimetableTearcherMap() {
		Map<String, String> timetableTearcherMap = new HashMap<String, String>();

		// 获取有效的实验分室列表-根据登录用户的所属学院
		String sql = "select c from User c " + "where c.userRole=1 ";
		List<User> list = new ArrayList<User>(userDAO.executeQuery(sql, 0, -1));
		// 遍历实验分室
		for (User user : list) {
			timetableTearcherMap.put(user.getUsername(), user.getCname() + "[" + user.getUsername() + "]");
		}
		return timetableTearcherMap;
	}

	/************************************************************
	 * @根据选课组，获取可选的实验 列表
	 * @作者：魏诚
	 * @日期：2014-07-24
	 ************************************************************/
	public Map<String, String> getTimetableTearcherMap(String acno) {
		Map<String, String> timetableTearcherMap = new HashMap<String, String>();
		String academyNumber = "";
		// 如果没有获取有效的实验分室列表-根据登录用户的所属学院
		if (!acno.equals("-1")) {
			// 获取选择的实验中心
			academyNumber = shareService.findSchoolAcademyByPrimaryKey(acno).getAcademyNumber();
		} else {
			academyNumber = shareService.getUserDetail().getSchoolAcademy().getAcademyNumber();
		}

		// 获取有效的实验分室列表-根据登录用户的所属学院
		String sql = "select c from User c " + "where c.userRole=1 and c.schoolAcademy.academyNumber ='"+academyNumber+"'";
		List<User> list = new ArrayList<User>(userDAO.executeQuery(sql, 0, -1));
		// 遍历实验分室
		for (User user : list) {
			timetableTearcherMap.put(user.getUsername(), user.getCname() /*+ ";" + user.getUsername()*/);
		}
		return timetableTearcherMap;
	}
	/************************************************************
	 * @根据选课组，获取可选的软件 列表
	 * @作者：戴昊宇
	 * @日期：2017-07-24
	 ************************************************************/
	public Map<Integer, String> getTimetableSoftwarerMap() {
		Map<Integer, String> timetableSoftwarerMap = new HashMap<Integer, String>();
		// 获取有效的实验分室列表-根据登录用户的所属学院
		String sql = "select c from Software c where 1=1";
		List<Software> list = new ArrayList<Software>(softwareDAO.executeQuery(sql, 0, -1));
		// 遍历实验分室
		for (Software ware : list) {
			timetableSoftwarerMap.put(ware.getId(),ware.getName());
		}
		return timetableSoftwarerMap;
	}
	/************************************************************
	 * @获取可选的教学周
	 * @作者：魏诚
	 * @日期：2014-07-24
	 ************************************************************/
	public Integer[] getValidWeeks(int term, int classids, int labroom, int weekday) {

		// 获取当前学期的可用周次数组
		List<SchoolWeek> weeklist = schoolWeekDAO.executeQuery("select c from SchoolWeek c where schoolTerm.id = "
				+ term + " group by c.week order by c.week asc");
		// 相关list转换的数组
		Integer[] allWeeks = new Integer[weeklist.size()];
		int iWeek = 0;
		for (SchoolWeek schoolWeek : weeklist) {
			allWeeks[iWeek] = schoolWeek.getWeek();
			iWeek++;
		}
		// 获取有效的实验分室列表-根据登录用户的所属学院
		StringBuffer sql = new StringBuffer(
				"select c from TimetableLabRelated c where 1=1 and c.timetableAppointment.schoolCourse.schoolTerm.id ="
						+ term + " and c.timetableAppointment.weekday =" + weekday);
		 sql.append(" and c.timetableAppointment.enabled is true");
		if (classids <= 4) {
			sql.append(" and c.labRoom.id =" + String.valueOf(labroom));
		} else {

		}
		List<TimetableLabRelated> list = timetableLabRelatedDAO.executeQuery(sql.toString());
		// String weeks ="";
		String weeks = "";
		for (TimetableLabRelated timetableLabRelated : list) {
			if (timetableLabRelated.getTimetableAppointment().getStartClass() >= classids * 2 - 1
					&& timetableLabRelated.getTimetableAppointment().getStartClass() <= classids * 2
					&& timetableLabRelated.getTimetableAppointment().getWeekday() >= weekday
					|| timetableLabRelated.getTimetableAppointment().getEndClass() >= classids * 2 - 1
					&& timetableLabRelated.getTimetableAppointment().getEndClass() <= classids * 2
					&& timetableLabRelated.getTimetableAppointment().getWeekday() >= weekday) {
				for (int i = timetableLabRelated.getTimetableAppointment().getStartWeek(); i <= timetableLabRelated
						.getTimetableAppointment().getEndWeek();) {
					weeks = weeks + i + ";";
					i++;
				}
			}
		}
		if (!weeks.equals("")) {
			String[] sWeeks = weeks.split(";");

			int[] intWeeks = new int[sWeeks.length];
			// 获取可用的教学
			for (int i = 0; i < intWeeks.length; i++) {
				intWeeks[i] = Integer.parseInt(sWeeks[i]);
			}

			// 数据排序去重
			Arrays.sort(intWeeks);

			String strWeek = "";
			Integer[] newIntWeeks1 = this.getDistinct(intWeeks);
			// int j = allWeeks.length;
			for (int i = 0; i < allWeeks.length; i++) {
				int a = Arrays.binarySearch(newIntWeeks1, allWeeks[i]);
				if (a < 0) {
					strWeek = strWeek + allWeeks[i] + ";";
				}
			}
			Integer[] newIntWeeks = new Integer[strWeek.split(";").length];
			if (newIntWeeks.length != 0 && newIntWeeks[0] != null) {

				for (int i = 0; i < strWeek.split(";").length; i++) {
					newIntWeeks[i] = Integer.parseInt(strWeek.split(";")[i]);

				}
			}
			return newIntWeeks;
		} else {
			return allWeeks;
		}

	}

	/************************************************************
	 * @获取可选的教学周
	 * @作者：魏诚
	 * @日期：2014-07-24
	 ************************************************************/
	public Integer[] getValidWeeks(int term, int[] classes, int[] labrooms, int weekday) {
		List<SchoolWeek> weeklist = schoolWeekDAO.executeQuery("select c from SchoolWeek c where schoolTerm.id = "
				+ term + " group by c.week order by c.week asc");
		// 相关list转换的数组
		Integer[] weeklists = new Integer[weeklist.size()];

		// 获取有效的实验分室列表-根据登录用户的所属学院
		StringBuffer selfSql = new StringBuffer("select c from TimetableLabRelated c where 1=0 ");
		StringBuffer sql = new StringBuffer("select c from TimetableLabRelated c where 1=0 ");
		sql.append(" and c.timetableAppointment.enabled is true");
		selfSql.append(" and c.timetableAppointment.enabled is true");
		if (labrooms!=null&&labrooms.length>0) {
			for (int labroom : labrooms) {
				if (shareService.isSpecialLabRoom(labroom)) {
					// 特殊实验室不参与判冲
				}else {
					sql.append(" or ( c.timetableAppointment.schoolCourse.schoolTerm.id =" + term
							+ " and c.timetableAppointment.weekday =" + weekday + " and c.labRoom.id =" + labroom + ") ");
					
					selfSql.append(" or ( c.timetableAppointment.timetableSelfCourse.schoolTerm.id =" + term
							+ " and c.timetableAppointment.weekday =" + weekday + " and c.labRoom.id =" + labroom + ") ");
				}
			}
		}
		// 教务课程排课数据
		List<TimetableLabRelated> list = timetableLabRelatedDAO.executeQuery(sql.toString());

		List<TimetableLabRelated> selfList = timetableLabRelatedDAO.executeQuery(selfSql.toString());
		list.addAll(selfList);
		// 自主排课数据
		// String weeks ="";
		String weeks = "";
		for (TimetableLabRelated timetableLabRelated : list) {
			boolean isIn = true;
			for (int intClass : classes) {
				if (timetableLabRelated.getTimetableAppointment().getTimetableAppointmentSameNumbers().size() == 0) {
					isIn = timetableLabRelated.getTimetableAppointment().getEndClass() >= intClass
							&& timetableLabRelated.getTimetableAppointment().getStartClass() <= intClass;

					if (!isIn) {
						break;
					} else {
						for (int i = timetableLabRelated.getTimetableAppointment().getStartWeek(); i <= timetableLabRelated
								.getTimetableAppointment().getEndWeek();) {
							weeks = weeks + i + ";";
							i++;
						}
					}
				} else {
					for (TimetableAppointmentSameNumber timetableAppointmentSameNumber : timetableLabRelated
							.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
						isIn = timetableAppointmentSameNumber.getEndClass() >= intClass
								&& timetableAppointmentSameNumber.getStartClass() <= intClass;

						if (!isIn) {
							break;
						} else {
							for (int i = timetableAppointmentSameNumber.getStartWeek(); i <= timetableAppointmentSameNumber
									.getEndWeek();) {
								weeks = weeks + i + ";";
								i++;
							}
						}
					}
				}
			}
		}
		if (!weeks.equals("")) {
			String[] sWeeks = weeks.split(";");

			int[] intWeeks = new int[sWeeks.length];
			// 获取可用的教学
			for (int i = 0; i < intWeeks.length; i++) {
				intWeeks[i] = Integer.parseInt(sWeeks[i]);
			}

			// 数据排序去重
			Arrays.sort(intWeeks);

			String strWeek = "";
			Integer[] newIntWeeks1 = this.getDistinct(intWeeks);

			for (int i = 0; i < weeklist.size(); i++) {
				int a = Arrays.binarySearch(newIntWeeks1, weeklist.get(i).getWeek());
				if (a < 0) {
					strWeek = strWeek + weeklist.get(i).getWeek() + ";";
				}
			}

			Integer[] newIntWeeks = new Integer[strWeek.split(";").length];
			if (strWeek.equals("")) {
				return newIntWeeks;
			}
			if (strWeek.split(";").length != 0 && strWeek.split(";")[0] != null) {

				for (int i = 0; i < strWeek.split(";").length; i++) {
					newIntWeeks[i] = Integer.parseInt(strWeek.split(";")[i]);
				}
			}
			return newIntWeeks;
		} else {
			// list转换为数组
			for (int i = 0; i < weeklist.size(); i++) {
				weeklists[i] = weeklist.get(i).getWeek();
			}
			return weeklists;
		}

	}
	/************************************************************
	 * @获取可选的教学周--编辑排课记录
	 * @作者：贺子龙
	 * @日期：2016-01-06
	 ************************************************************/
	public Integer[] getValidWeeks(int term, int[] classes, int[] labrooms, int weekday, int tableAppId) {
		List<SchoolWeek> weeklist = schoolWeekDAO.executeQuery("select c from SchoolWeek c where schoolTerm.id = "
				+ term + " group by c.week order by c.week asc");
		// 相关list转换的数组
		Integer[] weeklists = new Integer[weeklist.size()];
		
		// 获取有效的实验分室列表-根据登录用户的所属学院
		StringBuffer selfSql = new StringBuffer("select c from TimetableLabRelated c where 1=0 ");
		StringBuffer sql = new StringBuffer("select c from TimetableLabRelated c where 1=0 ");
		sql.append(" and c.timetableAppointment.enabled is true ");
		selfSql.append(" and c.timetableAppointment.enabled is true ");
		if (labrooms!=null&&labrooms.length>0) {
			for (int labroom : labrooms) {
				if (shareService.isSpecialLabRoom(labroom)) {
					// 特殊实验室不参与判冲
				}else {
					sql.append(" or ( c.timetableAppointment.schoolCourse.schoolTerm.id =" + term
							+ " and c.timetableAppointment.weekday =" + weekday + " and c.labRoom.id =" + labroom + ") ");
					
					selfSql.append(" or ( c.timetableAppointment.timetableSelfCourse.schoolTerm.id =" + term
							+ " and c.timetableAppointment.weekday =" + weekday + " and c.labRoom.id =" + labroom + ") ");
				}
			}
		}
		// 教务课程排课数据
		List<TimetableLabRelated> list = timetableLabRelatedDAO.executeQuery(sql.toString());
		
		List<TimetableLabRelated> selfList = timetableLabRelatedDAO.executeQuery(selfSql.toString());
		list.addAll(selfList);
		// 自主排课数据
		// String weeks ="";
		String weeks = "";
		for (TimetableLabRelated timetableLabRelated : list) {
			
			if (timetableLabRelated.getTimetableAppointment().getId()==tableAppId) {
				continue;//不与自身判冲
			}
			boolean isIn = true;
			for (int intClass : classes) {
				if (timetableLabRelated.getTimetableAppointment().getTimetableAppointmentSameNumbers().size() == 0) {
					isIn = timetableLabRelated.getTimetableAppointment().getEndClass() >= intClass
							&& timetableLabRelated.getTimetableAppointment().getStartClass() <= intClass;
					
					if (!isIn) {
						break;
					} else {
						for (int i = timetableLabRelated.getTimetableAppointment().getStartWeek(); i <= timetableLabRelated
								.getTimetableAppointment().getEndWeek();) {
							weeks = weeks + i + ";";
							i++;
						}
					}
				} else {
					for (TimetableAppointmentSameNumber timetableAppointmentSameNumber : timetableLabRelated
							.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
						isIn = timetableAppointmentSameNumber.getEndClass() >= intClass
								&& timetableAppointmentSameNumber.getStartClass() <= intClass;
						
						if (!isIn) {
							break;
						} else {
							for (int i = timetableAppointmentSameNumber.getStartWeek(); i <= timetableAppointmentSameNumber
									.getEndWeek();) {
								weeks = weeks + i + ";";
								i++;
							}
						}
						
					}
				}
				
			}
			
		}
		if (!weeks.equals("")) {
			String[] sWeeks = weeks.split(";");
			
			int[] intWeeks = new int[sWeeks.length];
			// 获取可用的教学
			for (int i = 0; i < intWeeks.length; i++) {
				intWeeks[i] = Integer.parseInt(sWeeks[i]);
			}
			
			// 数据排序去重
			Arrays.sort(intWeeks);
			
			String strWeek = "";
			Integer[] newIntWeeks1 = this.getDistinct(intWeeks);
			
			for (int i = 0; i < weeklist.size(); i++) {
				int a = Arrays.binarySearch(newIntWeeks1, weeklist.get(i).getWeek());
				if (a < 0) {
					strWeek = strWeek + weeklist.get(i).getWeek() + ";";
				}
			}
			
			Integer[] newIntWeeks = new Integer[strWeek.split(";").length];
			if (strWeek.equals("")) {
				return newIntWeeks;
			}
			if (strWeek.split(";").length != 0 && strWeek.split(";")[0] != null) {
				
				for (int i = 0; i < strWeek.split(";").length; i++) {
					newIntWeeks[i] = Integer.parseInt(strWeek.split(";")[i]);
					
				}
			}
			return newIntWeeks;
		} else {
			// list转换为数组
			for (int i = 0; i < weeklist.size(); i++) {
				weeklists[i] = weeklist.get(i).getWeek();
			}
			return weeklists;
		}
		
	}

	/************************************************************
     *  Description：获取可选的教学周-针对实验室预约
     *
     * @作者：魏诚
     * @日期：2018-06-13
     ************************************************************/
    public Integer[] getValidLabWeeks(int term, int[] classes, int[] labrooms, int weekday) {
        List<SchoolWeek> weeklist = schoolWeekDAO.executeQuery("select c from SchoolWeek c where schoolTerm.id = "
                + term + " group by c.week order by c.week asc");
        // 相关list转换的数组
        Integer[] weeklists = new Integer[weeklist.size()];
        String invalidtemp = "";
        // 获取有效的实验分室列表-根据登录用户的所属学院
        StringBuffer selfSql = new StringBuffer("select c from TimetableAppointment c  inner join c.timetableLabRelateds ts where 1=0 ");
        StringBuffer sql = new StringBuffer("select c from TimetableAppointment c  inner join c.timetableLabRelateds ts where 1=0 ");
        for (int labroom : labrooms) {
            sql.append("or ( c.schoolCourse.schoolTerm.id =" + term
                    + " and c.weekday =" + weekday + " and ts.labRoom.id =" + labroom + ") ");
            selfSql.append("or ( c.timetableSelfCourse.schoolTerm.id =" + term
                    + " and c.weekday =" + weekday + " and ts.labRoom.id =" + labroom + ") ");
        }
        // 教务课程排课数据
        List<TimetableAppointment> list = timetableAppointmentDAO.executeQuery(sql.toString(), 0, -1);
        List<TimetableAppointment> selfList = timetableAppointmentDAO.executeQuery(selfSql.toString(), 0, -1);
        list.addAll(selfList);
        // 自主排课数据
        // String weeks ="";
        String weeks = "";
        String invalidInt = "";
        //获取所有周次
        Map allWeeks = shareService.getWeeksMap(term);
        if (list != null && list.size() > 0) {
            //第一重循环，循环符合条件的实验室
            for (int labRoom : labrooms) {
                int maxReservation = labRoomDAO.findLabRoomById(labRoom).getReservationNumber();
                //第二重循环，循环节次
                for (int intClass : classes) {
                    //第三重循环，开始循环所有周次
                    //如果timetableAppointment周次累加次数，大于该实验室的容量则冲突
                    for (int currWeek = 1; currWeek <= allWeeks.size(); currWeek++) {
                        //第四重循环，开始所有符合初次筛选条件的排课列表timetableAppointments
                        int count = 0;
                        for (TimetableAppointment timetableAppointment : list) {
                            boolean isLabroom = false;
                            for (TimetableLabRelated timetableLabRelated : timetableAppointment.getTimetableLabRelateds()) {
                                if (timetableLabRelated.getLabRoom().getId() == labRoom) {
                                    isLabroom = true;
                                    break;
                                }
                            }
                            //如果实验室不是传参的实验室，continue
                            if (!isLabroom) {
                                continue;
                            }
                            //如果实验室是传参的实验室，开始处理
                            if (timetableAppointment.getTimetableAppointmentSameNumbers().size() == 0) {
                                if (timetableAppointment.getStartClass() <= intClass && timetableAppointment.getEndClass() >= intClass && timetableAppointment.getStartWeek() <= currWeek && timetableAppointment.getEndWeek() >= currWeek) {
                                    //如果是排课，则直接判冲
                                    if(timetableAppointment.getTimetableStyle()==1||timetableAppointment.getTimetableStyle()==2||timetableAppointment.getTimetableStyle()==3||timetableAppointment.getTimetableStyle()==4||timetableAppointment.getTimetableStyle()==5||timetableAppointment.getTimetableStyle()==6){
                                        count = count+maxReservation;
                                    }
                                    count++;
                                }
                            } else {
                                for (TimetableAppointmentSameNumber timetableAppointmentSameNumber : timetableAppointment.getTimetableAppointmentSameNumbers()) {
                                    if (timetableAppointment.getStartClass() <= intClass && timetableAppointment.getEndClass() >= intClass && timetableAppointment.getStartWeek() <= currWeek && timetableAppointment.getEndWeek() >= currWeek) {
                                        //如果是排课，则直接判冲
                                        if(timetableAppointment.getTimetableStyle()==1||timetableAppointment.getTimetableStyle()==2||timetableAppointment.getTimetableStyle()==3||timetableAppointment.getTimetableStyle()==4||timetableAppointment.getTimetableStyle()==5||timetableAppointment.getTimetableStyle()==6){
                                            count = count+maxReservation;
                                        }
                                        count++;
                                        break;
                                    }
                                }
                            }
                        }
                        //如果预约并发容量过限，则加入到week不可用列表
                        if (maxReservation <= count) {
                            weeks = weeks + currWeek + ":";
                        }
                    }
                    //该实验室最大预约数
                }
            }
        }
        // 字符串转换为数字数组并排序
        String[] strWeeks = weeks.split(":");
        if("".equals(weeks)){
            Integer[] intWeeks = new Integer[allWeeks.size()];
            for (int currWeek = 1; currWeek <= allWeeks.size(); currWeek++) {
                intWeeks[currWeek-1] = currWeek;
            }
            return intWeeks;
        }
        Integer[] iWeeks = new Integer[strWeeks.length];
        for(int i=0;i<strWeeks.length;i++){
            iWeeks[i] = Integer.parseInt(strWeeks[i]);
        }
        String invalidWeeks = "";
        for (int currWeek = 1; currWeek <= allWeeks.size(); currWeek++) {
            boolean isValid = false;
            for (int iweek : iWeeks) {
                if (iweek == currWeek) {
                    isValid = true;
                }
            }
            if (!isValid) {
                invalidWeeks = invalidWeeks + currWeek + ":";
            }
        }
        String[] sWeeks = invalidWeeks.split(":");
        Integer[] intWeeks = new Integer[sWeeks.length];
        // 获取可用的教学
        for (int i = 0; i < intWeeks.length; i++) {
            intWeeks[i] = Integer.parseInt(sWeeks[i]);
        }
        return intWeeks;

    }

	/*************************************************************************************
	 * @內容：判断选课资源是否冲突
	 * @作者： 魏誠
	 * @日期：2015-03-10
	 *************************************************************************************/
	@SuppressWarnings("unused")
	public boolean isTimetableConflict(int term, int weekday, int[] labrooms, int[] classes,int[] weeks) {
		List<SchoolWeek> weeklist = schoolWeekDAO.executeQuery("select c from SchoolWeek c where schoolTerm.id = "
				+ term + " group by c.week order by c.week asc");
		// 相关list转换的数组
		Integer[] weeklists = new Integer[weeklist.size()];

		// 获取有效的实验分室列表-根据登录用户的所属学院
		StringBuffer selfSql = new StringBuffer("select c from TimetableLabRelated c where 1=0 ");
		StringBuffer sql = new StringBuffer("select c from TimetableLabRelated c where 1=0 ");
		sql.append(" and c.timetableAppointment.enabled is true");
		selfSql.append(" and c.timetableAppointment.enabled is true");
		for (int labroom : labrooms) {
			if (shareService.isSpecialLabRoom(labroom)) {
				// 特殊实验室不参与判冲
			}else {
				sql.append("or ( c.timetableAppointment.schoolCourse.schoolTerm.id =" + term
						+ " and c.timetableAppointment.weekday =" + weekday + " and c.labRoom.id =" + labroom + ") ");
				
				selfSql.append("or ( c.timetableAppointment.timetableSelfCourse.schoolTerm.id =" + term
						+ " and c.timetableAppointment.weekday =" + weekday + " and c.labRoom.id =" + labroom + ") ");
			}
		}
		// 教务课程排课数据
		List<TimetableLabRelated> list = timetableLabRelatedDAO.executeQuery(sql.toString());

		List<TimetableLabRelated> selfList = timetableLabRelatedDAO.executeQuery(selfSql.toString());
		list.addAll(selfList);
		// 自主排课数据
		// String weeks ="";
		String sweek = "";
		for (TimetableLabRelated timetableLabRelated : list) {
			boolean isIn = true;
			for (int intClass : classes) {
				if (timetableLabRelated.getTimetableAppointment().getTimetableAppointmentSameNumbers().size() == 0) {
					isIn = timetableLabRelated.getTimetableAppointment().getEndClass() >= intClass
							&& timetableLabRelated.getTimetableAppointment().getStartClass() <= intClass
								&& (timetableLabRelated.getTimetableAppointment().getEndWeek() >= weeks[0]
									&& timetableLabRelated.getTimetableAppointment().getStartWeek() <= weeks[0]
										|| timetableLabRelated.getTimetableAppointment().getEndWeek() >= weeks[weeks.length-1]
									       && timetableLabRelated.getTimetableAppointment().getStartWeek() <= weeks[weeks.length-1]);

					if (isIn) {
						return false;
						//break;
					} else {
						for (int i = timetableLabRelated.getTimetableAppointment().getStartWeek(); i <= timetableLabRelated
								.getTimetableAppointment().getEndWeek();) {
							sweek = sweek + i + ";";
							i++;
						}
						//return true;
					}
				} else {
					for (TimetableAppointmentSameNumber timetableAppointmentSameNumber : timetableLabRelated
							.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
						isIn = timetableAppointmentSameNumber.getEndClass() >= intClass
								&& timetableAppointmentSameNumber.getStartClass() <= intClass;
						isIn = timetableAppointmentSameNumber.getEndClass() >= intClass
								&& timetableAppointmentSameNumber.getStartClass() <= intClass
									&& (timetableAppointmentSameNumber.getEndWeek() >= weeks[0]
										&& timetableAppointmentSameNumber.getStartWeek() <= weeks[0]
											|| timetableAppointmentSameNumber.getEndWeek() >= weeks[weeks.length-1]
										       && timetableAppointmentSameNumber.getStartWeek() <= weeks[weeks.length-1]);

						if (isIn) {
							//break;
							return false;
						} else {
							for (int i = timetableAppointmentSameNumber.getStartWeek(); i <= timetableAppointmentSameNumber
									.getEndWeek();) {
								sweek = sweek + i + ";";
								i++;
							}
						}

					}
				}

			}

		}
		return true;
	}

	/************************************************************
	 * @获取数组中去重的结果
	 * @作者：魏诚
	 * @日期：2014-07-24
	 ************************************************************/
	public Integer[] getDistinct(int num[]) {
		List<Integer> list = new java.util.ArrayList<Integer>();
		for (int i = 0; i < num.length; i++) {
			if (!list.contains(num[i])) {// 如果list数组不包括num[i]中的值的话，就返回true。
				list.add(num[i]); // 在list数组中加入num[i]的值。已经过滤过。
			}
		}
		return list.toArray(new Integer[0]);
	}

	/************************************************************
	 * @获取所有学期数据，按id倒序
	 * @作者：魏诚
	 * @日期：2014-08-17
	 ************************************************************/
	public List<SchoolTerm> getSchoolTermList() {
		String sql = "select c from SchoolTerm c order by id desc";
		List<SchoolTerm> schoolTerms = schoolTermDAO.executeQuery(sql,0,-1);
		return schoolTerms;
	}
	
	/************************************************************
	 * 功能：获取空闲的实验室（二次排课）
	 * 作者：戴昊宇
	 * 日期：2017-09-27
	 ************************************************************/
	public List<LabRoom> getValidLabRooms( int term,int weeks, int weekday, int[] classes,Integer selfCourseId,Integer confinementTime,String coursecode){
		String sql = "select r from LabRoom r where 1=1";
		 if(confinementTime!=null&&confinementTime==1){
			 String academyNumber = "";
			 Set<SchoolCourse> schoolCourseByCourseCode = schoolCourseDAO.findSchoolCourseByCourseCode(coursecode);
			 for(SchoolCourse schoolcourse:schoolCourseByCourseCode){
				 academyNumber =schoolcourse.getSchoolAcademy().getAcademyNumber();
			 }
			 sql += " and r.schoolAcademy.academyNumber="+academyNumber;
		 }
		// 获取所有实验室
		List<LabRoom> labRooms = labRoomDAO.executeQuery(sql, 0, -1);
	
			List<LabRoom> validLabRooms = new ArrayList<LabRoom>();
			if (labRooms.size()>0) {
				for (LabRoom labRoom : labRooms) {
					int[] labrooms = {labRoom.getId()};
					// 判断该实验室是否可用
					if (this.isLabRoomValid(weeks, term, classes, labrooms, weekday,selfCourseId)) {
						// 将可用的实验室添加进来
						validLabRooms.add(labRoom);
					}else {
						// do nothing
					}
				
			}
			// 判断是否有可用的实验室
			if (validLabRooms.size()>0) {
				return validLabRooms;
			}else {
				return null;
			}
			
		}
			return labRooms;
	}
	/*************************************************************************************
	 * @內容：根据周次、星期、节次和实验室id判断实验室是否可用
	 * @作者： 贺子龙
	 * @日期：2016-04-19
	 *************************************************************************************/
	public boolean isLabRoomValid(int week, int term, int[] classes, int[] labrooms, int weekday,Integer selfCourseId){
		boolean isLabRoomValid = false;
		Integer[] validWeeks = this.getValidWeeksByType(term, classes, labrooms, weekday, selfCourseId);
		if (validWeeks.length>0) {
			for (Integer validWeek : validWeeks) {
				if (validWeek.equals(week)) {
					isLabRoomValid = true;
					break;
				}
			}
		}
		return isLabRoomValid;
	}
	
	/************************************************************
	 * 功能：获取可选的教学周
	 * 作者：贺子龙
	 * 日期：2016-01-25
	 ************************************************************/
	public Integer[] getValidWeeksByType(int term, int[] classes, int[] labrooms, int weekday, Integer selfCourseId) {
		List<SchoolWeek> weeklist = schoolWeekDAO.executeQuery("select c from SchoolWeek c where schoolTerm.id = "
				+ term + " group by c.week order by c.week asc");
		// 相关list转换的数组
				Integer[] weeklists = new Integer[weeklist.size()];

				// 获取有效的实验分室列表-根据登录用户的所属学院
				StringBuffer selfSql = new StringBuffer("select c from TimetableLabRelated c  ");
			/*	
				if (selfCourseId!=null) {
					selfSql.append(" , TimetableTeacherRelated te, TimetableTutorRelated tt ");
				}*/
				selfSql.append("where 1=0");
				
		        	if (labrooms!=null&&labrooms.length>0) {
		    			for (int labroom : labrooms) {
		    				if (shareService.isSpecialLabRoom(labroom)) {
		    				
		    				
		    					}else {
		    						selfSql.append(" or ( c.timetableAppointment.schoolCourse.schoolTerm.id =" + term
		    								+ " and c.timetableAppointment.weekday =" + weekday + " and c.labRoom.id =" + labroom+")" );
		    					}
		    				}
		    			}
		    	/*	
		    		if (selfCourseId!=null) {// 班级判冲
		    			List<TimetableSelfCourse> timetableSelfCourses = timetableSelfCourseService.findTimetableSelfCourseWithSameClass(selfCourseId);
		    			if (timetableSelfCourses!=null && timetableSelfCourses.size()>0) {
		    				for (TimetableSelfCourse timetableSelfCourse : timetableSelfCourses) {
		    					if (type.equals(1)) {
		    						selfSql.append(" or ( c.timetableAppointment.timetableSelfCourse.schoolTerm.id =" + term
		    								+ " and c.timetableAppointment.weekday =" + weekday + " and c.timetableAppointment.timetableSelfCourse.id="+timetableSelfCourse.getId()+"" +
		    								" and (c.timetableAppointment.markBit=1 or c.timetableAppointment.markBit=6))");
		    					}else {
		    						selfSql.append(" or ( c.timetableAppointment.timetableSelfCourse.schoolTerm.id =" + term
		    								+ " and c.timetableAppointment.weekday =" + weekday + " and c.timetableAppointment.timetableSelfCourse.id="+timetableSelfCourse.getId()+"" +
		    								" and c.timetableAppointment.markBit<>1 and c.timetableAppointment.markBit<>6)");
		    					}
		    				}
		    			}
		    		}
		    		
*/

				// 自主排课数据
				List<TimetableLabRelated> selfList = timetableLabRelatedDAO.executeQuery(selfSql.toString());
				// String weeks ="";
				String weeks = "";
				for (TimetableLabRelated timetableLabRelated : selfList) {
					boolean isIn = true;
					for (int intClass : classes) {
						if (timetableLabRelated.getTimetableAppointment().getTimetableAppointmentSameNumbers().size() == 0) {
							isIn = timetableLabRelated.getTimetableAppointment().getEndClass() >= intClass
									&& timetableLabRelated.getTimetableAppointment().getStartClass() <= intClass;

							if (!isIn) {
								break;
							} else {
								for (int i = timetableLabRelated.getTimetableAppointment().getStartWeek(); i <= timetableLabRelated
										.getTimetableAppointment().getEndWeek();) {
									weeks = weeks + i + ";";
									i++;
								}
							}
						} else {
							for (TimetableAppointmentSameNumber timetableAppointmentSameNumber : timetableLabRelated
									.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
								isIn = timetableAppointmentSameNumber.getEndClass() >= intClass
										&& timetableAppointmentSameNumber.getStartClass() <= intClass;

								if (!isIn) {
									break;
								} else {
									for (int i = timetableAppointmentSameNumber.getStartWeek(); i <= timetableAppointmentSameNumber
											.getEndWeek();) {
										weeks = weeks + i + ";";
										i++;
									}
								}

							}
						}

					}

				}
				if (!weeks.equals("")) {
					String[] sWeeks = weeks.split(";");

					int[] intWeeks = new int[sWeeks.length];
					// 获取可用的教学
					for (int i = 0; i < intWeeks.length; i++) {
						intWeeks[i] = Integer.parseInt(sWeeks[i]);
					}

					// 数据排序去重
					Arrays.sort(intWeeks);

					String strWeek = "";
					Integer[] newIntWeeks1 = this.getDistinct(intWeeks);

					for (int i = 0; i < weeklist.size(); i++) {
						int a = Arrays.binarySearch(newIntWeeks1, weeklist.get(i).getWeek());
						if (a < 0) {
							strWeek = strWeek + weeklist.get(i).getWeek() + ";";
						}
					}

					Integer[] newIntWeeks = new Integer[strWeek.split(";").length];
					if (strWeek.equals("")) {
						return newIntWeeks;
					}
					if (strWeek.split(";").length != 0 && strWeek.split(";")[0] != null) {

						for (int i = 0; i < strWeek.split(";").length; i++) {
							newIntWeeks[i] = Integer.parseInt(strWeek.split(";")[i]);

						}
					}
					return newIntWeeks;
				} else {
					// list转换为数组
					for (int i = 0; i < weeklist.size(); i++) {
						weeklists[i] = weeklist.get(i).getWeek();
					}
					return weeklists;
				}
			}
	
	/************************************************************
	 * Description 根據實驗中心獲取所有教師列表
	 * 
	 * @param cid 中心主鍵
	 * @return map
	 * @author 陳樂為
	 * @date 2017年9月10日
	 ************************************************************/
	@Override
	public Map<String, String> getAllTearchersMap(String acno) {
		Map<String, String> timetableTearcherMap = new HashMap<String, String>();
		// 获取有效的用戶列表-根据所在中心的所属学院
		String sql = "select c from User c where  c.enabled = 1"
		+ "and c.userRole = 1 and c.schoolAcademy.academyNumber like '"
			+ shareService.getUserDetail().getSchoolAcademy().getAcademyNumber() + "'";
		if(acno != null && !acno.equals("-1")) {
			sql += " and c.schoolAcademy.academyNumber like '"+ shareService.findSchoolAcademyByPrimaryKey(acno).getAcademyNumber() +"'";
		}else {
			sql += " and c.schoolAcademy.academyNumber like '"+ shareService.getUserDetail().getSchoolAcademy().getAcademyNumber() +"'";
		}

		List<User> list = new ArrayList<User>(userDAO.executeQuery(sql, 0, -1));
		// 遍历实验分室
		for (User user : list) {
			if(user.getAuthorities().toString().contains("TEACHER")) {
				timetableTearcherMap.put(user.getUsername(), user.getCname() + "(" + user.getUsername() + ")");
			}
		}
		return timetableTearcherMap;
	}
	
	
	/************************************************************
	 * 功能：获取空闲的实验室（实验室工位预约）
	 * 作者：戴昊宇
	 * 日期：2017-09-27
	 ************************************************************/
	public Integer getValidLabRoomsStation(Calendar time,Integer labRoomId,Calendar startTime,Calendar endTime,Integer term){
		//根据时间获得周次星期与节次
		int weeks=shareService.getBelongsSchoolWeek(time);
		int weekday=shareService.getBelongsSchoolWeekday(time);
		int startclass =shareService.getCurClass(startTime);
		int endclass =shareService.getCurClass(endTime);
		int totalclass=endclass-startclass+1;
		int[] classes=new int[12];
		for(int i=0;i<=totalclass;i++){
			classes[i]=startclass+i;
		}
		 LabRoom vallabroom= new LabRoom();
		 LabRoom labRoomById = labRoomDAO.findLabRoomById(labRoomId);
					if (this.isLabRoomStationValid(weeks, term, classes, labRoomById, weekday,labRoomId)) {
						vallabroom=labRoomById;
					}else {
						// do nothing
					}
		
		int capacity = 0;
		if (vallabroom != null && !vallabroom.equals("")) {
			capacity = vallabroom.getLabRoomCapacity();
		}
			return capacity;
			
	}
	/*************************************************************************************
	 * @內容：根据周次、星期、节次和实验室id判断实验室是否可用
	 * @作者： 戴昊宇
	 * @日期：2017-09-27
	 *************************************************************************************/
	public boolean isLabRoomStationValid(int week, int term, int[] classes, LabRoom labRoomById, int weekday,int labRoomId){
		boolean isLabRoomValid = false;
		Integer[] validWeeks = this.getValidStationWeeksByType(term, classes, labRoomById, weekday,labRoomId);
		if (validWeeks.length>0) {
			for (Integer validWeek : validWeeks) {
				if (validWeek.equals(week)) {
					isLabRoomValid = true;
					break;
				}
			}
		}
		return isLabRoomValid;
	}
	/************************************************************
	 * 功能：获取可选的教学周
	 * 作者：戴昊宇
	 * 日期：2017-09-27
	 ************************************************************/
	public Integer[] getValidStationWeeksByType(int term, int[] classes, LabRoom labRoomById, int weekday,int labRoomId) {
		List<SchoolWeek> weeklist = schoolWeekDAO.executeQuery("select c from SchoolWeek c where schoolTerm.id = "
				+ term + " group by c.week order by c.week asc");
		      // 相关list转换的数组
				Integer[] weeklists = new Integer[weeklist.size()];
				// 获取有效的实验分室列表-根据登录用户的所属学院
				StringBuffer selfSql = new StringBuffer("select c from TimetableLabRelated c  ");
				selfSql.append("where 1=0");
		    				if (shareService.isSpecialLabRoom(labRoomId)) {
		    					}else {
		    						selfSql.append(" or ( c.timetableAppointment.schoolCourse.schoolTerm.id =" + term
		    								+ " and c.timetableAppointment.weekday =" + weekday + " and c.labRoom.id =" + labRoomId+")" );
		    					}
		    			
				// 自主排课数据
				List<TimetableLabRelated> selfList = timetableLabRelatedDAO.executeQuery(selfSql.toString());
				// String weeks ="";
				String weeks = "";
				for (TimetableLabRelated timetableLabRelated : selfList) {
					boolean isIn = true;
					for (int intClass : classes) {
						if (timetableLabRelated.getTimetableAppointment().getTimetableAppointmentSameNumbers().size() == 0) {
							isIn = timetableLabRelated.getTimetableAppointment().getEndClass() >= intClass
									&& timetableLabRelated.getTimetableAppointment().getStartClass() <= intClass;

							if (!isIn) {
								break;
							} else {
								for (int i = timetableLabRelated.getTimetableAppointment().getStartWeek(); i <= timetableLabRelated
										.getTimetableAppointment().getEndWeek();) {
									weeks = weeks + i + ";";
									i++;
								}
							}
						} else {
							for (TimetableAppointmentSameNumber timetableAppointmentSameNumber : timetableLabRelated
									.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
								isIn = timetableAppointmentSameNumber.getEndClass() >= intClass
										&& timetableAppointmentSameNumber.getStartClass() <= intClass;

								if (!isIn) {
									break;
								} else {
									for (int i = timetableAppointmentSameNumber.getStartWeek(); i <= timetableAppointmentSameNumber
											.getEndWeek();) {
										weeks = weeks + i + ";";
										i++;
									}
								}
							}
						}
					}
				}
				if (!weeks.equals("")) {
					String[] sWeeks = weeks.split(";");

					int[] intWeeks = new int[sWeeks.length];
					// 获取可用的教学
					for (int i = 0; i < intWeeks.length; i++) {
						intWeeks[i] = Integer.parseInt(sWeeks[i]);
					}
					// 数据排序去重
					Arrays.sort(intWeeks);
					String strWeek = "";
					Integer[] newIntWeeks1 = this.getDistinct(intWeeks);
					for (int i = 0; i < weeklist.size(); i++) {
						int a = Arrays.binarySearch(newIntWeeks1, weeklist.get(i).getWeek());
						if (a < 0) {
							strWeek = strWeek + weeklist.get(i).getWeek() + ";";
						}
					}
					Integer[] newIntWeeks = new Integer[strWeek.split(";").length];
					if (strWeek.equals("")) {
						return newIntWeeks;
					}
					if (strWeek.split(";").length != 0 && strWeek.split(";")[0] != null) {

						for (int i = 0; i < strWeek.split(";").length; i++) {
							newIntWeeks[i] = Integer.parseInt(strWeek.split(";")[i]);
						}
					}
					return newIntWeeks;
				} else {
					// list转换为数组
					for (int i = 0; i < weeklist.size(); i++) {
						weeklists[i] = weeklist.get(i).getWeek();
					}
					return weeklists;
				}
			}

	/************************************************************
	 * @ 获取可排的实验分室列表(key是实验室编号） @ 作者：廖文辉 @ 日期：2017-01-24
	 ************************************************************/
	public List<LabRoom>  getLabRoomNumberList(String acno){
		// 如果没有获取有效的实验分室列表-根据登录用户的所属学院
		StringBuffer sql = new StringBuffer("select c from LabRoom c where c.isUsed=1");
		SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
		if(academy!=null && academy.getAcademyNumber()!=null) {
			sql.append(" and c.labCenter.schoolAcademy.academyNumber = '"+ academy.getAcademyNumber() +"'");
		}

		List<LabRoom> list = labRoomDAO.executeQuery(sql.toString());
		return list;

	}

	/************************************************************
	 * @根据选课组，可选学生列表
	 * @作者：戴昊宇
	 * @日期：2018-06-25
	 ************************************************************/
	public Map<String, String> getStudentMap() {
		Map<String, String> getStudentMap = new HashMap<String, String>();
		// 获取有效的实验分室列表-根据登录用户的所属学院
		String sql = "select c from User c " + "where c.userRole='0' ";
		//and c.schoolAcademy.academyNumber like '"	+ shareService.getUserDetail().getSchoolAcademy().getAcademyNumber() + "' 去掉学院限制    贺子龙  2015-11-19
		List<User> list = new ArrayList<User>(userDAO.executeQuery(sql, 0, -1));
		// 遍历实验分室
		for (User user : list) {
			getStudentMap.put(user.getUsername(), user.getCname() + ";" + user.getUsername());
		}
		return getStudentMap;
	}

	/**
	 * @Description 根据条件获取排课的实验室
	 * @data 2019-01-10
	 * @author 张德冰
	 * */
	@Override
	public Map<Integer, String> findLabRoomMaps(String acno, Integer term, Integer week){
		Map<Integer, String> labRoomMap = new LinkedHashMap<Integer, String>(0);
		// 如果没有获取有效的实验分室列表-根据登录用户的所属学院
		StringBuffer sql = new StringBuffer("select distinct l from LabRoom l join l.timetableLabRelateds c join c.timetableAppointment.timetableAppointmentSameNumbers s where 1=1");
		sql.append(" and c.timetableAppointment.status = 1");
		sql.append(" and c.timetableAppointment.enabled is true");
		if(acno != null) {
			sql.append(" and l.labCenter.schoolAcademy.academyNumber like '"+acno+"'");
		}
		if(term != null) {
			sql.append(" and c.timetableAppointment.schoolTerm.id =" + term + " ");
		}
		if (week != 0 && week != -1) {
			sql.append(" and (s.startWeek <=" + week + " and s.endWeek >=" + week + ")");
		}

		List<LabRoom> list = labRoomDAO.executeQuery(sql.toString(), 0, -1);
		// 遍历实验分室
		for (LabRoom labRoom : list) {
			labRoomMap.put(labRoom.getId(),labRoom.getLabRoomName());
		}
		return labRoomMap;
	}
}