package net.zjcclims.service.common;

import cn.com.pubinfo.service.SendMsgPortType;
import cn.com.pubinfo.service.SendMsg_Service;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import excelTools.MatrixToImageWriter;
import excelTools.People;
import net.gvsun.lims.dto.common.PConfigDTO;
import net.luxunsh.util.DateUtil;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.device.LabRoomDeviceService;
import net.zjcclims.service.lab.LabAnnexService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.service.lab.LabWorkerTrainingService;
import net.zjcclims.service.performance.PerformanceService;
import net.zjcclims.service.software.SoftwareService;
import net.zjcclims.util.CookieUtil;
import net.zjcclims.util.HttpClientUtil;
import net.zjcclims.web.common.PConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;



@Service("ShareService")
public class ShareServiceImpl implements ShareService {
	//读取属性文件中specialAcademy对应的值（此方法需要在web-content.xml中增加配置）
	@Value("${showDevice}")
	private String showDeviceURL;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private SchoolTermDAO schoolTermDAO;
	@Autowired
	private SchoolWeekDAO schoolWeekDAO;
	@Autowired
	private LabRoomLendingDAO labRoomLendingDAO;
	@Autowired
	private AuthorityDAO authorityDAO;
	@Autowired
	private SchoolAcademyDAO schoolAcademyDAO;
	@Autowired
	private SchoolCourseDAO schoolCourseDAO;
	@Autowired
	private TimetableAppointmentDAO timetableAppointmentDAO;
	@Autowired
	private SchoolCourseStudentDAO schoolCourseStudentDAO;
	@Autowired
	private TimetableAttendanceDAO timetableAttendanceDAO;
	@Autowired
	private CDictionaryDAO cDictionaryDAO;
	@Autowired
	private LabRoomService labRoomService;
	@Autowired
	private LabAnnexService labAnnexService;
	@Autowired
	private LabWorkerTrainingService labWorkerTrainingService;
	@Autowired
	private SoftwareService softwareService;
	@Autowired
	private PerformanceService performanceService;
	@Autowired
	private LabRoomDeviceService labRoomDeviceService;
	@Autowired
	private SystemTimeDAO systemTimeDAO;
	@Autowired
	private
	LabRoomDAO labRoomDAO;
	@Autowired
	private SchoolYearDAO schoolYearDAO;
	@Autowired
	private LabRoomTrainingPeopleDAO labRoomTrainingPeopleDAO;
	@Autowired
	private LabCenterDAO labCenterDAO;
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private OperationItemDAO operationItemDAO;

	@Autowired
	private  CommonDocumentDAO commonDocumentDAO;

	@Autowired
	private LabRoomDeviceDAO labRoomDeviceDAO;
	@Autowired
	private PConfig pConfig;
	@Autowired private MessageDAO messageDAO;
	@Autowired private SchoolClassesDAO schoolClassesDAO;
	/*
	 * Instantiates a new ShareServiceImpl.
	 */
	public ShareServiceImpl() {
	}
	
	/**
	 * 获取字典数据
	 * @author hly
	 *2015.08.03
	 */
	@Override
	public List<CDictionary> getCDictionaryData(String category) {
		StringBuffer hql = new StringBuffer("select c from CDictionary c where 1=1 ");
		if(category!=null && !"".equals(category))
		{
			hql.append(" and c.CCategory = '"+category+"' order by c.id");

			return cDictionaryDAO.executeQuery(hql.toString(), 0, -1);
		}
		
		return null;
	}
	/**
	 * 根据类别和编号获取字典数据
	 * @author hly
	 * 2015.08.04
	 */
	@Override
	public CDictionary getCDictionaryByCategory(String category, String number) {
		StringBuffer hql = new StringBuffer("select c from CDictionary c where 1=1 ");
		if(category!=null && !"".equals(category) && number!=null && !"".equals(number))
		{
			hql.append(" and c.CCategory='"+category+"' and c.CNumber='"+number+"'");
			
			List<CDictionary> cDictionaries = cDictionaryDAO.executeQuery(hql.toString(), 0, -1);
			
			if(cDictionaries.size() > 0)
			{
		        return cDictionaries.get(0);
			}
		}
		
		return null;
	}
	
	/*
	 * 分页显示
	 */
	
	@Override
	@Transactional
	public Map<String, Integer> getPage(int currpage, int pageSize, int totalRecords) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		PageModel pageModel = new PageModel();
		pageModel.setTotalRecords(totalRecords);
		pageModel.setPageSize(pageSize);
		pageModel.setCurrpage(currpage);
		map.put("totalPage", pageModel.getTotalPage());
		map.put("nextPage", pageModel.getNextPage());
		map.put("previousPage", pageModel.getPreiviousPage());
		map.put("firstPage", pageModel.getFisrtPage());
		map.put("lastPage", pageModel.getLastPage());
		map.put("currpage", pageModel.getCurrpage());
		map.put("totalRecords", totalRecords);
		map.put("pageSize", pageSize);
		return map;
	}

	/*
	 * 获取登录人的所有信息
	 */
	/* (non-Javadoc)
	 * @see net.zjcclims.service.ShareService#getUser()
	 */
	/* (non-Javadoc)
	 * @see net.zjcclims.service.common.ShareService#getUser()
	 */
	@Override
	public User getUser() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null
				&& (!AnonymousAuthenticationToken.class.isAssignableFrom(auth
						.getClass()))) {
			User user = userDAO.findUserByUsername(SecurityContextHolder
					.getContext().getAuthentication().getName());
			return user;
		} else {
			return null;
		}
	}

	/***********************************************************************************************
	 * 根据路径删除指定的目录或文件，无论存在与否
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public boolean deleteDocument(String documentUrl) {
		boolean flag=false;
		File file = new File(documentUrl);
		if(!file.exists()){
			return flag;
		}else{
			//判断是否为文件
			if(file.isFile()){
				//如果为文件则调用删除文件的方法
				return deleteFile(documentUrl);
			}else{
				//如果为文件夹（目录）则调用删除文件夹的方法
				return deleteMenu(documentUrl);
			}
		}

	}
	/***********************************************************************************************
	 * 根据学院编号查询实验项目
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public List<OperationItem> findOperationItemBySchoolAcademy(
			String academyNumber) {
		String sql="select o from OperationItem o where o.labCenter.schoolAcademy.academyNumber like '%"+academyNumber+"%' ";
		return operationItemDAO.executeQuery(sql, 0,-1);
	}
	/***********************************************************************************************
	 * 根据路径删除指定的目录（文件夹）以及目录下的文件
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public boolean deleteMenu(String documentUrl) {
		boolean flag=false;
		File file=new File(documentUrl);
		//接下来删除文件夹及其目录下的文件
		return flag;
	}
	/***********************************************************************************************
	 * 将日期类型转化为String
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public String format(Calendar appDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(appDate.getTime());
	}
	/*
	 * 处理中文乱码 作者：彭文玉
	 */
	/* (non-Javadoc)
	 * @see net.zjcclims.service.ShareService#htmlEncode(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see net.zjcclims.service.common.ShareService#htmlEncode(java.lang.String)
	 */
	@Override
	public String htmlEncode(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			int c = (int) str.charAt(i);
			if (c > 127 && c != 160) {
				sb.append("&#").append(c).append(";");
			} else {
				sb.append((char) c);
			}
		}
		return sb.toString();
	}

	/*
	 * 获得当前页数
	 */
	/* (non-Javadoc)
	 * @see net.zjcclims.service.ShareService#getCurrpage(javax.servlet.http.HttpServletRequest)
	 */
	/* (non-Javadoc)
	 * @see net.zjcclims.service.common.ShareService#getCurrpage(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public int getCurrpage(HttpServletRequest request) {
		String url = request.getHeader("Referer");
		int page = 0;
		String ul = url.substring(url.length() - 1);
		String equalSign = url.substring(url.length() - 2, url.length() - 1);
		if (equalSign.equals("=")) {

			page = Integer.parseInt(ul);
		} else {
			page = 1;
		}
		return page;
	}

	/*
	 * 获取所有的周次；
	 */
	/* (non-Javadoc)
	 * @see net.zjcclims.service.ShareService#getWeeksMap(int)
	 */
	/* (non-Javadoc)
	 * @see net.zjcclims.service.common.ShareService#getWeeksMap(int)
	 */
	@Override
	public Map getWeeksMap(int termId) {
		// 新建一个HashMap对象；
		Map weeksMap = new HashMap();
		// 创建一条通过周次和星期分组的语句；
		StringBuffer sb1 = new StringBuffer(
				"select s from SchoolWeek s where 1=1 and s.schoolTerm.id='"
						+ termId + "'group by s.week");
		// 执行sb1得到需要的周次；
		List<SchoolWeek> weeks = schoolWeekDAO.executeQuery(sb1.toString());
		// 循环weeks；
		for (SchoolWeek schoolWeek : weeks) {
			// 将schoolWeek的week映射给week；
			weeksMap.put(schoolWeek.getWeek(), schoolWeek.getWeek());
		}
		return weeksMap;
	}

	/*
	 * 获取所有的星期； 作者：彭文玉
	 */
	/* (non-Javadoc)
	 * @see net.zjcclims.service.ShareService#getWeekdays()
	 */
	/* (non-Javadoc)
	 * @see net.zjcclims.service.common.ShareService#getWeekdays()
	 */
	@Override
	public Map getWeekdays() {
		// 新建一个HashMap对象；
		Map weekdayMap = new HashMap();
		// 循环7map星期几；
		for (int i = 1; i < 8; i++) {
			switch (i) {
			// 如果i等于1，则map1为星期一；
			case 1:
				weekdayMap.put(1, "星期一");
				break;
			// 如果i等于2，则map2为星期二；
			case 2:
				weekdayMap.put(2, "星期二");
				break;
			// 如果i等于3，则map3为星期二；
			case 3:
				weekdayMap.put(3, "星期三");
				break;
			// 如果i等于2，则map2为星期二；
			case 4:
				weekdayMap.put(4, "星期四");
				break;
			// 如果i等于5，则map5为星期二；
			case 5:
				weekdayMap.put(5, "星期五");
				break;
			// 如果i等于6，则map6为星期六；
			case 6:
				weekdayMap.put(6, "星期六");
				break;
			// 如果i等于7，则map7为星期天；
			case 7:
				weekdayMap.put(7, "星期日");
				break;
			}
		}
		return weekdayMap;
	}

	/***********************************************************************************************
	 * @throws Exception
	 * @功能：遍历两个日期之间是否有周末
	 * @作者：贺子龙
	 * @日期：2017-04-14
	 ***********************************************************************************************/
	public boolean isInWeedend(Date date1, Date date2) throws Exception{
		boolean isWeekend = false;
		// 遍历得到每一天
		List<Date> dates = this.dateSplit(date1, date2);
		if (!dates.isEmpty()) {
			for (Date date : dates) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				// 判断是否为周末
				isWeekend = this.isWeekend(cal);
				if (isWeekend) {
					break;
				}
			}
		}
		return isWeekend;
	}
	/**
	 * Description 纺织学院设备预约{判断设备预约时间是否被占用}
	 * @param labRoomLimitTimes 实验室限制时间
	 * @param startDate 预约起始时间
	 * @param endDate 预约使用截止时间
	 * @param deviceId 预约设备id
	 * @return
	 * @author 陈乐为 2018-9-30
	 */
	@Override
	public boolean isLimitedByAppointment(List<LabRoomLimitTime> labRoomLimitTimes,Calendar startDate,Calendar endDate,Integer deviceId) {
		boolean flag = true;
		/**
		 * 1.判断实验室禁用情况
		 * 2.判断排课时设备对外开放情况
		 * 2.1如果预约起止时间落在开放区间内flag为true
		 */
		// 获取room禁用时间
		LabRoomDevice labRoomDevice = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(deviceId);
		// 判断实验室禁用情况
		flag = this.isLimitedByTime(labRoomLimitTimes, startDate, endDate);
		// 纺织学院{排课占用期间，实验室设备对外开放预约}
		for(LabRoomDeviceReservation reservation : labRoomDevice.getLabRoomDeviceReservations()) {
			if (reservation.getAppointmentId() != null) {// 排课（排除正常预约时间）
				// 设备对外开放时间
				Calendar start = reservation.getOriginalBegin();
				Calendar end = reservation.getOriginalEnd();

				if (endDate.before(end) || endDate.equals(end) || startDate.after(start) || startDate.equals(start)) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
    /***********************************************************************************************
     * @throws ParseException
     * @功能：通用模塊service層定義-判断指定实验室的禁用时间段内，根据日历表的起始时间和结束时间判断是否可用
     * @作者：魏诚
     * @日期：2016-03-08
     ***********************************************************************************************/
    public boolean isLimitedByTimeNew(Integer id,Calendar startDate,Calendar endDate) throws ParseException {

        boolean flag = true;
        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 获取room禁用时间
        LabRoomDevice labRoomDevice = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(id);
        LabRoom labRoom = labRoomDevice.getLabRoom();
        Integer roomID = labRoomDevice.getLabRoom().getId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");
        //纺织学院特殊配置
        /*String academy = labCenterDAO.findLabCenterByPrimaryKey(cid).getSchoolAcademy().getAcademyNumber();*/
        BigDecimal startHour = new BigDecimal(-1);
        BigDecimal endHour = new BigDecimal(25);
        /*if (academy.equals("0201")) {// 纺织学院
            startHour = new BigDecimal(8);
            endHour = new BigDecimal(17);
        } else{
            startHour = new BigDecimal(8);
            endHour = new BigDecimal(20);
        }*/
        startHour = new BigDecimal(8);
        endHour = new BigDecimal(20);
        if(labRoom.getStartHour() != null && labRoom.getEndHour() != null){
            startHour = labRoom.getStartHour();
            endHour = labRoom.getEndHour();
        }
        long sTime = this.getBelongsSchoolTerm(Calendar.getInstance()).getTermStart().getTime().getTime();
        Integer startweek = 1;
        Integer endweek = 21;
        Calendar cal = Calendar.getInstance();
        int currentWeekday = cal.get(Calendar.DAY_OF_WEEK) - 1;
        int startWeekday = this.getBelongsSchoolTerm(Calendar.getInstance()).getTermStart().get(Calendar.DAY_OF_WEEK) - 1;
        if(labRoom.getStartHourInweekend().compareTo(startHour) == -1){
            startHour = labRoom.getStartHourInweekend();
            for(int j = 1; j <=5 ; j++){

                for (int i = startweek; i <= endweek; i++) {
                    Integer weekday = j;
                    String s ="";
                    String e ="";
                    long dlT = (i - 1) * 7 * 24 * 3600 * 1000l + (weekday) * 24 * 3600 * 1000l;
                    String date = sdf.format(new Date(sTime + dlT));
                    s = startHour.toString();
                    if(labRoom.getStartHour() != null)
                    {
                        e = labRoom.getStartHour().toString();
                    }else{
                        e = "8";
                    }
                    if(s.length() == 1){
                        s = "0" + s;
                        s += ":00:00";
                    }
                    if(s.length() == 2){
                        s += ":00:00";
                    }
                    if(e.length() == 1){
                        e = "0" + e;
                        e += ":00:00";
                    }
                    if(e.length() == 2){
                        e += ":00:00";
                    }
                    //1.25
                    if(s.length() == 4){
                        s="0"+this.getDateString(Double.valueOf(s));
                    }
                    //11.25
                    if(s.length() == 5){
                        s=this.getDateString(Double.valueOf(s));
                    }
                    //1.25
                    if(e.length() == 4){
                        e="0"+this.getDateString(Double.valueOf(e));
                    }
                    //11.25
                    if(e.length() == 5){
                        e=this.getDateString(Double.valueOf(e));
                    }
                    String start_date = date + " " + s;
                    String end_date = date + " " + e;
                    Calendar start = Calendar.getInstance();
                    Calendar end = Calendar.getInstance();
                    Date dateParse = sdfTime.parse(start_date);
                    start.setTime(dateParse);
                    dateParse = sdfTime.parse(end_date);
                    end.setTime(dateParse);
                    if(!(endDate.before(start) || endDate.equals(start) || startDate.after(end) || startDate.equals(end)) ){
                        flag = false;
                        break;
                    }
                }
            }
        }else{
            for(int j = 6; j <=7 ; j++){

                for (int i = startweek; i <= endweek; i++) {
                    Integer weekday = j;
                    long dlT = (i - 1) * 7 * 24 * 3600 * 1000l + (weekday) * 24 * 3600 * 1000l;
                    String date = sdf.format(new Date(sTime + dlT));
                    String s = startHour.toString();
                    String e = labRoom.getStartHourInweekend().toString();
                    if (!s.contains(".")) {// 设置成了小数
                        if(s.length() == 1){
                            s = "0" + s;
                            s += ":00:00";
                        }
                        if(s.length() == 2){
                            s += ":00:00";
                        }
                    }
                    if (s.contains(".")) {
                        String[] start_array = s.replace(".", ",").split(",");
                        int hour = Integer.parseInt(start_array[0]);
                        if (hour > 9) {// 两位数
                            s = "" + hour;
                        }else{
                            s = "0" + hour;
                        }
                        int part = Integer.parseInt(start_array[1]);
                        BigDecimal min = new BigDecimal(60 * part / 100);
                        min.setScale(0, BigDecimal.ROUND_HALF_UP);
                        if (min.compareTo(new BigDecimal(9)) == 1) {// 分钟是两位数
                            s += ":" + min + ":00";
                        }else{
                            s += ":0" + min + ":00";
                        }
                    }
                    if (!e.contains(".")) {
                        if(e.length() == 1){
                            e = "0" + e;
                            e += ":00:00";
                        }
                        if(e.length() == 2){
                            e += ":00:00";
                        }
                    }
                    if (e.contains(".")) {
                        String[] end_array = e.replace(".", ",").split(",");
                        int hour = Integer.parseInt(end_array[0]);
                        if (hour > 9) {// 两位数
                            e = "" + hour;
                        }else{
                            e = "0" + hour;
                        }
                        int part = Integer.parseInt(end_array[1]);
                        BigDecimal min = new BigDecimal(60 * part / 100);
                        min.setScale(0, BigDecimal.ROUND_HALF_UP);
                        if (min.compareTo(new BigDecimal(9)) == 1) {// 分钟是两位数
                            e += ":" + min + ":00";
                        }else{
                            e += ":0" + min + ":00";
                        }
                    }
                    String start_date = date + " " + s;
                    String end_date = date + " " + e;
                    Calendar start = Calendar.getInstance();
                    Calendar end = Calendar.getInstance();
                    Date dateParse = sdfTime.parse(start_date);
                    start.setTime(dateParse);
                    dateParse = sdfTime.parse(end_date);
                    end.setTime(dateParse);
                    if(!(endDate.before(start) || endDate.equals(start) || startDate.after(end) || startDate.equals(end)) ){
                        flag = false;
                        break;
                    }
                }
            }
        }
        if(labRoom.getEndHourInweekend().compareTo(endHour) == 1){
            endHour = labRoom.getEndHourInweekend();
            for(int j = 1; j <=5 ; j++){
                for (int i = startweek; i <= endweek; i++) {
                    Integer weekday = j;
                    String s = "";
                    String e = "";
                    long dlT = (i - 1) * 7 * 24 * 3600 * 1000l + (weekday) * 24 * 3600 * 1000l;
                    String date = sdf.format(new Date(sTime + dlT));
                    if(labRoom.getEndHour() != null)
                    {
                        s = labRoom.getEndHour().toString();
                    }
                    else{
                        //纺织学院特殊配置
                        /*if (academy.equals("0201")) {// 纺织学院
                            s = "17";
                        } else{
                            e = "20";
                        }*/
                        e = "20";
                    }

                    e = endHour.toString();
                    if(s.length() == 1){
                        s = "0" + s;
                        s += ":00:00";
                    }
                    if(s.length() == 2){
                        s += ":00:00";
                    }
                    if (s.contains(".")) {
                        String[] start_array = s.replace(".", ",").split(",");
                        int hour = Integer.parseInt(start_array[0]);
                        if (hour > 9) {// 两位数
                            s = "" + hour;
                        }else{
                            s = "0" + hour;
                        }
                        int part = Integer.parseInt(start_array[1]);
                        BigDecimal min = new BigDecimal(60 * part / 100);
                        min.setScale(0, BigDecimal.ROUND_HALF_UP);
                        if (min.compareTo(new BigDecimal(9)) == 1) {// 分钟是两位数
                            s += ":" + min + ":00";
                        }else{
                            s += ":0" + min + ":00";
                        }
                    }
                    if(e.length() == 1){
                        e = "0" + e;
                        e += ":00:00";
                    }
                    if(e.length() == 2){
                        e += ":00:00";
                    }
                    if (e.contains(".")) {
                        String[] end_array = e.replace(".", ",").split(",");
                        int hour = Integer.parseInt(end_array[0]);
                        if (hour > 9) {// 两位数
                            e = "" + hour;
                        }else{
                            e = "0" + hour;
                        }
                        int part = Integer.parseInt(end_array[1]);
                        BigDecimal min = new BigDecimal(60 * part / 100);
                        min.setScale(0, BigDecimal.ROUND_HALF_UP);
                        if (min.compareTo(new BigDecimal(9)) == 1) {// 分钟是两位数
                            e += ":" + min + ":00";
                        }else{
                            e += ":0" + min + ":00";
                        }
                    }
                    String start_date = date + " " + s;
                    String end_date = date + " " + e;
                    Calendar start = Calendar.getInstance();
                    Calendar end = Calendar.getInstance();
                    Date dateParse = sdfTime.parse(start_date);
                    start.setTime(dateParse);
                    dateParse = sdfTime.parse(end_date);
                    end.setTime(dateParse);
                    if(!(endDate.before(start) || endDate.equals(start) || startDate.after(end) || startDate.equals(end)) ){
                        flag = false;
                        break;
                    }
                }
            }
        }else{
            for(int j = 6; j <=7 ; j++){
                for (int i = startweek; i <= endweek; i++) {
                    Integer weekday = j;
                    long dlT = (i - 1) * 7 * 24 * 3600 * 1000l + (weekday) * 24 * 3600 * 1000l;
                    String date = sdf.format(new Date(sTime + dlT));
                    String s = labRoom.getEndHourInweekend().toString();
                    String e = endHour.toString();
                    if(s.length() == 1){
                        s = "0" + s;
                        s += ":00:00";
                    }
                    if(s.length() == 2){
                        s += ":00:00";
                    }
                    if (s.contains(".")) {
                        String[] start_array = s.replace(".", ",").split(",");
                        int hour = Integer.parseInt(start_array[0]);
                        if (hour > 9) {// 两位数
                            s = "" + hour;
                        }else{
                            s = "0" + hour;
                        }
                        int part = Integer.parseInt(start_array[1]);
                        BigDecimal min = new BigDecimal(60 * part / 100);
                        min.setScale(0, BigDecimal.ROUND_HALF_UP);
                        if (min.compareTo(new BigDecimal(9)) == 1) {// 分钟是两位数
                            s += ":" + min + ":00";
                        }else{
                            s += ":0" + min + ":00";
                        }
                    }
                    if(e.length() == 1){
                        e = "0" + e;
                        e += ":00:00";
                    }
                    if(e.length() == 2){
                        e += ":00:00";
                    }
                    if (e.contains(".")) {
                        String[] end_array = e.replace(".", ",").split(",");
                        int hour = Integer.parseInt(end_array[0]);
                        if (hour > 9) {// 两位数
                            e = "" + hour;
                        }else{
                            e = "0" + hour;
                        }
                        int part = Integer.parseInt(end_array[1]);
                        BigDecimal min = new BigDecimal(60 * part / 100);
                        min.setScale(0, BigDecimal.ROUND_HALF_UP);
                        if (min.compareTo(new BigDecimal(9)) == 1) {// 分钟是两位数
                            e += ":" + min + ":00";
                        }else{
                            e += ":0" + min + ":00";
                        }
                    }
                    String start_date = date + " " + s;
                    String end_date = date + " " + e;
                    Calendar start = Calendar.getInstance();
                    Calendar end = Calendar.getInstance();
                    Date dateParse = sdfTime.parse(start_date);
                    start.setTime(dateParse);
                    dateParse = sdfTime.parse(end_date);
                    end.setTime(dateParse);
                    if(!(endDate.before(start) || endDate.equals(start) || startDate.after(end) || startDate.equals(end)) ){
                        flag = false;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    /***********************************************************************************************
     * @功能：判断两个日期是否为同一天
     * @作者：贺子龙
     * @日期：2016-08-07
     ***********************************************************************************************/
    public boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    /**
     *
     * @author 陆少凯
     * Jan 19, 20184:26:54 PM
     * @param time 需要被保存的时间（double）
     * @return
     */
    public String getDateString(Double time){
        Integer hour=(int)Math.floor(time);
        Integer minute=(int)((time-hour)*60);
        return hour.toString()+":"+minute.toString()+":00";
    }
	/***********************************************************************************************
	 * @throws Exception
	 * @功能：使用java对给定的两个日期之间的日期进行遍历
	 * @作者：贺子龙
	 * @日期：2017-04-14
	 ***********************************************************************************************/
	private List<Date> dateSplit(Date startDate, Date endDate)
			throws Exception {
		if (!startDate.before(endDate))
			throw new Exception("开始时间应该在结束时间之后");
		Long spi = endDate.getTime() - startDate.getTime();
		Long step = spi / (24 * 60 * 60 * 1000);// 相隔天数

		List<Date> dateList = new ArrayList<Date>();
		dateList.add(endDate);
		for (int i = 1; i <= step; i++) {
			dateList.add(new Date(dateList.get(i - 1).getTime()
					- (24 * 60 * 60 * 1000)));// 比上一天减一
		}
		return dateList;
	}
	/***********************************************************************************************
	 * @功能：判断是否是周末
	 * @作者：贺子龙
	 * @日期：2017-04-14
	 ************************************************************************************************/
	private boolean isWeekend(Calendar cal){
		int week=cal.get(Calendar.DAY_OF_WEEK)-1;
		if(week ==6 || week==0){//0代表周日，6代表周六
			return true;
		}
		return false;
	}

	/*
	 * 获取所有的用户列表； 作者：彭文玉
	 */
	/* (non-Javadoc)
	 * @see net.zjcclims.service.ShareService#getUsersMap()
	 */
	/* (non-Javadoc)
	 * @see net.zjcclims.service.common.ShareService#getUsersMap()
	 */
	@Override
	public Map getUsersMap() {
		// 获取所有的用户；
		String sql = "select username,cname from user where 1=1 and user_role = 1 and user_status=1 and enabled=1";
		Query query = entityManager.createNativeQuery(sql);
		List<Object[]> users = query.getResultList();
//		Set<User> users = userDAO.findUserByUserRole("1");
		// 新建一个HashMap对象；
		Map<String,String> userMap = new HashMap<String, String>();
		// 循环users；
		for (Object[] user : users) {
			// 将user的Cname映射成id；
			userMap.put(user[0].toString(), user[1].toString());
		}
		return userMap;
	}
	/*
	 * 得到所有的学期 作者：彭文玉
	 */
	/* (non-Javadoc)
	 * @see net.zjcclims.service.ShareService#getTermsMap()
	 */
	/* (non-Javadoc)
	 * @see net.zjcclims.service.common.ShareService#getTermsMap()
	 */
	@Override
	public Map getTermsMap() {
		// 获取所有的学期；
		Set<SchoolTerm> terms = schoolTermDAO.findAllSchoolTerms();
		// 新建一个Map集合；
		Map termsMap = new HashMap();
		// 循环map terms对象；
		for (SchoolTerm term : terms) {
			termsMap.put(term.getId(), term.getTermName());
		}
		return termsMap;
	}

	/*
	 * 获取所有的周次 作者：彭文玉
	 */
	/* (non-Javadoc)
	 * @see net.zjcclims.service.ShareService#getWeekMap()
	 */
	/* (non-Javadoc)
	 * @see net.zjcclims.service.common.ShareService#getWeekMap()
	 */
	@Override
	public Map getWeekMap() {
		// 新建一个week的map集合；
		Map weeksMap = new HashMap();
		for (int i = 1; i < 26; i++) {
			// 声明并赋值weekName；
			String weekName = "第" + String.valueOf(i) + "周";
			weeksMap.put(i, weekName);
		}
		return weeksMap;
	}

	/*
	 * 得到本周的周次 作者：彭文玉
	 */
	/* (non-Javadoc)
	 * @see net.zjcclims.service.ShareService#findNewWeek()
	 */
	/* (non-Javadoc)
	 * @see net.zjcclims.service.common.ShareService#findNewWeek()
	 */
	@Override
	public int findNewWeek() {
		int week;
		// 声明变量now并给它赋值成当前时间；
		Calendar now = Calendar.getInstance();
		// 查找今天所属的周次
		Set<SchoolWeek> schoolWeeks = schoolWeekDAO.findSchoolWeekByDate(now);
		// 新建SchoolWeek的list集合
		List<SchoolWeek> schoolWeeks1 = new ArrayList<SchoolWeek>();
		// 将schoolWeeks添加到schoolWeeks1
		schoolWeeks1.addAll(schoolWeeks);
		// 如果schoolWeeks大于0；
		if (schoolWeeks1.size() > 0) {
			// 获取schoolWeek的第一个
			SchoolWeek schoolWeek = schoolWeeks1.get(0);
			week = schoolWeek.getWeek();
		}// 否则为0
		else {
			week = 0;
		}
		return week;
	}

	/*
	 * 密码加密md5； 鲁静 2013.08.26
	 */
	/* (non-Javadoc)
	 * @see net.zjcclims.service.ShareService#createMD5(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see net.zjcclims.service.common.ShareService#createMD5(java.lang.String)
	 */
	@Override
	public String createMD5(String s) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] byteArray = s.getBytes("ISO-8859-1");

			byte[] md5Bytes = md5.digest(byteArray);
			StringBuffer hexValue = new StringBuffer();

			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16)
					hexValue.append("0");
				hexValue.append(Integer.toHexString(val));
			}
			return hexValue.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 获取当前日期 鲁静 2013.09.04
	 */
	/* (non-Javadoc)
	 * @see net.zjcclims.service.ShareService#getDate()
	 */
	/* (non-Javadoc)
	 * @see net.zjcclims.service.common.ShareService#getDate()
	 */
	@Override
	public String getDate() {
		String m, d, getDate;
		Calendar date = Calendar.getInstance();
		int year = date.get(Calendar.YEAR);
		int month = date.get(Calendar.MONTH) + 1;
		if (month < 10) {
			m = "0" + String.valueOf(month);
		} else {
			m = String.valueOf(month);
		}
		int day = date.get(Calendar.DATE);
		if (day < 10) {
			d = "0" + String.valueOf(day);
		} else {
			d = String.valueOf(day);
		}
		getDate = String.valueOf(year) + "-" + m + "-" + d;

		return getDate;
	}

	/* (non-Javadoc)
	 * @see net.zjcclims.service.common.ShareService#getUserDetail()
	 */
	public User getUserDetail() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		return userDAO.findUserByUsername(userDetails.getUsername());
		
	}
	/***********************************************************************************************
	 * 获取上传文件的保存路径
	 * 作者：李小龙 
	 * 日期：2014-07-27
	 ***********************************************************************************************/
	@Override
	public String getUpdateFilePath(HttpServletRequest request) {
		// TODO Auto-generated method stub
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	    /**日期格式**/    
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
	    /** 构建文件保存的目录* */
	    String PathDir = "/upload/"+ dateformat.format(new Date());
	    /** 得到文件保存目录的真实路径* */
	    String RealPathDir = request.getSession().getServletContext().getRealPath(PathDir);
	    //System.out.println("文件保存目录的真实路径:"+logoRealPathDir);
	    /** 根据真实路径创建目录* */
	    File SaveFile = new File(RealPathDir);
	    if (!SaveFile.exists()){
	    	SaveFile.mkdirs();
	    }
	    /** 页面控件的文件流* */
	    System.out.println("准备获取文件---");
	    MultipartFile multipartFile = multipartRequest.getFile("file");
	    /** 获取文件的后缀* */
	    System.out.println("上传的文件名称"+multipartFile.getOriginalFilename());
	    /**判断文件不为空*/
	    if(!multipartFile.isEmpty()){
	    	String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
		    /** 使用UUID生成文件名称* */
	    	String logImageName = UUID.randomUUID().toString() + suffix;// 构建文件名称
		    /** 拼成完整的文件保存路径加文件* */
		    String fileName = RealPathDir + File.separator + logImageName;
		    File file = new File(fileName);
		    try {
		        multipartFile.transferTo(file);
		    } catch (IllegalStateException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    /** 上传到服务器的文件的绝对路径* */	       
		    String saveUrl=PathDir+"/"+logImageName;
		    return saveUrl;
	    }
	    return "";
	}
	/***********************************************************************************************
	 * 查询和当前登录人同一学院的用户
	 * 作者：李小龙 
	 ***********************************************************************************************/
	@Override
	public List<User> findTheSameCollegeUser(String academyNumber) {
		// TODO Auto-generated method stub
		String sql="select u from User u where u.schoolAcademy.academyNumber like'"+academyNumber+"%'";
		return userDAO.executeQuery(sql,0,-1);
	}
	/***********************************************************************************************
	 * 判断时间所属的学期
	 * 作者：李小龙 
	 ***********************************************************************************************/
	@Override
	public SchoolTerm getBelongsSchoolTerm(Calendar time) {
		// TODO Auto-generated method stub
		//遍历所有的学期
		Set<SchoolTerm> terms=schoolTermDAO.findAllSchoolTerms();
		SchoolTerm term=new SchoolTerm();
		for (SchoolTerm schoolTerm : terms) {
			Calendar start=schoolTerm.getTermStart();
			Calendar end=schoolTerm.getTermEnd();
			if(time.after(start)&&time.before(end)){
				term=schoolTerm;
				break;
			}
			if(time.after(end)){
				term=schoolTerm;
			}
		}
		if(term.getId() == null && terms.size() > 0){
			term = terms.iterator().next();
		}
		return term;
	}
	/***********************************************************************************************
	 * 获取指定学期和学院的可用课程
	 * 作者：魏晨 
	 ***********************************************************************************************/
	@Override
	public List<SchoolCourse> getSchoolCourseList(int termId) {
		String sql = "select c from SchoolCourse c where c.schoolTerm.id =" + termId + " and schoolAcademy.academyNumber like '" + this.getUserDetail().getSchoolAcademy().getAcademyNumber() + "' group by c.courseNo";
		List <SchoolCourse> schoolCourses = schoolCourseDAO.executeQuery(sql, 0,-1);
		return schoolCourses;
	}

	/***********************************************************************************************
	* 查询和当前登录人同一学院的老师
	* 作者：李小龙 
	***********************************************************************************************/
	@Override
	public List<User> findTheSameCollegeTeacher(String academyNumber) {
		// TODO Auto-generated method stub
		String sql="select u from User u where u.schoolAcademy.academyNumber='"+academyNumber+"' and u.userRole=1";
		return userDAO.executeQuery(sql,0,-1);
	}
	/***********************************************************************************************
	 * 根据权限id查询用户
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public List<User> findUsersByAuthorityId(int i) {
		// TODO Auto-generated method stub
		String sql="select u from User u join u.authorities a where a.id= "+i;
		return userDAO.executeQuery(sql,0,-1);
	}

	/***********************************************************************************************
	 * 根据权限id查询用户
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public List<User> findUsersByAuthorityName(String authorityName){
		// TODO Auto-generated method stub
		String sql="select u from User u join u.authorities a where a.authorityName like '"+authorityName+"'";
		return userDAO.executeQuery(sql,0,-1);
	}
	/***********************************************************************************************
	 * 找到某学院的所有系主任和系教学秘书
	 * 作者：孙虎
	 ***********************************************************************************************/
	@Override
	public List<User> findDeansByAcademyNumber(SchoolAcademy schoolAcademy) {
		// TODO Auto-generated method stub
		String sql="select distinct u from User u join u.authorities a where (a.id=9 or a.id=17) and u.schoolAcademy.id= '"+schoolAcademy.getAcademyNumber()+"'";
		return userDAO.executeQuery(sql,0,-1);
	}
	/***********************************************************************************************
	 * 查询所有的学期并倒序排列
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public List<SchoolTerm> findAllSchoolTerms() {
		// TODO Auto-generated method stub
		String sql="select s from SchoolTerm s where 1=1 order by s.id desc";
		return schoolTermDAO.executeQuery(sql, 0, -1);
	}
	/***********************************************************************************************
	 * 查询所有的十二个学院
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public List<SchoolAcademy> findAllSchoolAcademys() {
		// TODO Auto-generated method stub
		String sql="select s from SchoolAcademy s where 1=1";//where s.academyNumber like '02__'  2015.10.02贺子龙
		List<SchoolAcademy> s=schoolAcademyDAO.executeQuery(sql, 0,-1);
		return s;
	}

	/***********************************************************************************************
	 * 根据排课记录登录用户判断是否用户仅为老师权限，且是否为当前用户，或者为超级管理员或实验中心主任
	 * 作者：魏诚
	 ***********************************************************************************************/
	@Override
	public boolean isOrNotAuthority(TimetableAppointment timetableAppointment) {
		/**
		 * 判断是否只是老师角色
	    **/		
		User user = this.getUserDetail();
		String judge = ",";
		for(Authority authority:user.getAuthorities()){
			judge = judge + "," + authority.getId() + "," ;
		}
		//如果权限仅为教师，不为实验中心主任或超级管理员，则增加过滤
		if(judge.indexOf(",11,")>-1||judge.indexOf(",4,")>-1||judge.indexOf(",7,")>-1){
			return true;
		}else if(judge.indexOf(",2,")!=-1){
			/*for(TimetableTeacherRelated timetableTeacherRelated:timetableAppointment.getTimetableTeacherRelateds()){
				if(user.getUsername().equals(timetableTeacherRelated.getUser().getUsername())){
					return true;
				}
			}*/
			if(timetableAppointment.getTimetableStyle()==5||timetableAppointment.getTimetableStyle()==6){
				if(user.getUsername().equals(timetableAppointment.getTimetableSelfCourse().getUser().getUsername())){
					return true;
				}
			}else if(timetableAppointment.getTimetableStyle()==1||timetableAppointment.getTimetableStyle()==2||timetableAppointment.getTimetableStyle()==3||timetableAppointment.getTimetableStyle()==4){
				if(user.getUsername().equals(timetableAppointment.getSchoolCourse().getUserByTeacher().getUsername())){
					return true;
				}
			}
		}
		return false;
	}
	/***********************************************************************************************
	 * 获取所有的部门
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public Set<SchoolAcademy> getAllSchoolAcademy() {
		
		return schoolAcademyDAO.findAllSchoolAcademys();
	}
	/***********************************************************************************************
	 * 构建文件夹
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public File createDirdown() {
		//上传之后注意修改服务器文件夹权限
		String root = System.getProperty("zjcclims.root");
		//操作手册的保存路径
		//File.separator windows是\，unix是/
		String url=root+"pages"+File.separator+"instructions";
		File sendPath = new File(url);
		//构建文件夹
		if(!sendPath.exists()){
			sendPath.mkdirs();
		}
		return sendPath;
	}
	/***********************************************************************************************
	 * 下载文件
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public void downloadFile(String fileName, HttpServletRequest request,
			HttpServletResponse response) {
		try{			
				File sendPath = createDirdown();
				FileInputStream fis = new FileInputStream(sendPath+File.separator+fileName);
				response.setCharacterEncoding("utf-8");
				//解决上传中文文件时不能下载的问题
				response.setContentType("multipart/form-data;charset=UTF-8");
				if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
					fileName = new String(fileName.getBytes("UTF-8"),"ISO8859-1");// firefox浏览器
				} else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
					fileName = URLEncoder.encode(fileName, "UTF-8");// IE浏览器
				} else {
					fileName = URLEncoder.encode(fileName, "UTF-8");
				}
				response.setHeader("Content-Disposition", "attachment;fileName="+fileName.replaceAll(" ", ""));
				
				OutputStream fos = response.getOutputStream();
				byte[] buffer = new byte[8192];
				int count = 0;
				while((count = fis.read(buffer))>0){
					fos.write(buffer,0,count);   
				}
				fis.close();
				fos.close();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try {
					response.getOutputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
	}
	/***********************************************************************************************
	 * 查询排课所属学期的周次数量
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public int getTermNumber(TimetableAppointment t) {
		int weeks=0;
		if(t!=null){
			int style=t.getTimetableStyle();
			if(style==5||style==6){
				weeks=t.getTimetableSelfCourse().getSchoolTerm().getSchoolWeeks().size()/7;
			}else{
				weeks=t.getSchoolCourse().getSchoolTerm().getSchoolWeeks().size()/7;
			}
		}
		return weeks;
	}
	/***********************************************************************************************
	 * 判断当前时间是否在指定时间（周次、星期）之后
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public boolean getTimeMark(Integer week, Integer weekday) {
		boolean flag=false;
		Calendar now=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String time=sdf.format(now.getTime());
		
		String sql="select w from SchoolWeek w where w.date like '"+time+"' ";
		List<SchoolWeek> weeks=schoolWeekDAO.executeQuery(sql, 0,-1);
		if(weeks.size()>0){
			
			int currentWeek=weeks.get(0).getWeek();
			int currentWeekDay=weeks.get(0).getWeekday();
			if(currentWeek>week){
				flag=true;
			}
			if(currentWeek==week){
				if(currentWeekDay>=weekday){
					flag=true;
				}
			}
		}
		return flag;
	}
	/***********************************************************************************************
	 * 根据对象生成二维码，并返回二维码保存路径
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public String getDimensionalCode(LabRoomDevice d) throws Exception {
		
		String url="";
		//获取系统路径
		String root = System.getProperty("zjcclims.root");
		//二维码的保存路径
		//File.separator windows是\，unix是/
		String path="upload"+"/"+"dimensionalCode";
		String text =showDeviceURL+d.getId();
		int width = 300;
		int height = 300;
		//二维码的图片格式
		String format = "gif";
		Hashtable hints = new Hashtable();
		//内容所使用编码
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(text,BarcodeFormat.QR_CODE, width, height, hints);
		File sendPath = new File(root+path);
		//构建文件夹
		if(!sendPath.exists()){
			sendPath.mkdirs();
		}
		//生成二维码
		File outputFile = new File(root+path+File.separator+d.getSchoolDevice().getDeviceNumber()+".gif");
		MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
		url=path+"/"+d.getSchoolDevice().getDeviceNumber()+".gif";
		
System.out.println("二维码路径："+url);
		return url;
	}
	/***********************************************************************************************
	 * 获取已经排过课的课程
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public Set<SchoolCourse> findSchoolCourse() {
		Set<SchoolCourse> courses=new HashSet<SchoolCourse>();
		String sql="select t from TimetableAppointment t where t.status=1";
		List<TimetableAppointment> timetableList=timetableAppointmentDAO.executeQuery(sql, 0,-1);
		for (TimetableAppointment t : timetableList) {
			if (t.getSchoolCourse()!=null) {
				courses.add(t.getSchoolCourse());
			}
			
		}
		return courses;
	}
	/***********************************************************************************************
	 * 获取当前时间所属的周次星期信息
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public int getBelongsSchoolWeek(Calendar time) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String date=sdf.format(time.getTime());
		String sql="select w from SchoolWeek w where w.date='"+date+"' ";
		List<SchoolWeek> weeks=schoolWeekDAO.executeQuery(sql, 0,-1);
		if(weeks.size()>0){
			return weeks.get(0).getWeek();
		}else{//如果是暑假和寒假期间则显示第一周的数据
			return 1;
		}
		
	}
	/***********************************************************************************************
	 * 获取当前时间所属的周次星期信息
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public int getBelongsSchoolWeekday(Calendar time) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String date=sdf.format(time.getTime());
		String sql="select w from SchoolWeek w where w.date='"+date+"' ";
		List<SchoolWeek> weeks=schoolWeekDAO.executeQuery(sql, 0,-1);
		if(weeks.size()>0){
			return weeks.get(0).getWeekday();
		}else{//如果是暑假和寒假期间则显示第一周的数据
			return 1;
		}
		
	}
	/***********************************************************************************************
	 * 根据课程获取教师
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public Set<User> findSchoolCourseTeachers(Set<SchoolCourse> schoolCourses) {
		Set<User> users=new HashSet<User>();
		for (SchoolCourse course : schoolCourses) {
			if(course!=null&&course.getUserByTeacher()!=null){
				users.add(course.getUserByTeacher());
			}
		}
		return users;
	}
	/***********************************************************************************************
	 * 根据选课组编号获取学生名单
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public List<SchoolCourseStudent> findStudentByCourseNo(String courseNo) {
		SchoolCourse schoolCourse=schoolCourseDAO.findSchoolCourseByPrimaryKey(courseNo);
		Set<SchoolCourseDetail> courseDetails=schoolCourse.getSchoolCourseDetails();
		String detailNo="";
		if(courseDetails.size()>0){
			SchoolCourseDetail detail=courseDetails.iterator().next();
			detailNo=detail.getCourseDetailNo();
		}
		if(detailNo!=null&&!detailNo.equals("")){
			String sql="select  s from SchoolCourseStudent s where s.schoolCourseDetail.courseDetailNo='"+detailNo+"' ";
			return schoolCourseStudentDAO.executeQuery(sql, 0,-1);
		}else{
			return null;
		}
		
	}
	/***********************************************************************************************
	 * 根据学生名单和周次统计学生实到次数
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public List<People> findPeopleByStudentsAndWeek(List<SchoolCourseStudent> studentList, Integer week,String courseNo) {
		List<People> people=new ArrayList<People>();
		for (SchoolCourseStudent student : studentList) {
			String username=student.getUserByStudentNumber().getUsername();
			String cname=student.getUserByStudentNumber().getCname();
			
			//统计实到人数
			String peopleSql="select t from TimetableAttendance t where (t.attendanceMachine=1 or t.actualAttendance=1)";
			if(!week.equals(0)){//不等于0即查询的周次不是全部
				peopleSql+=" and t.week="+week;
			}
			peopleSql+=" and t.courseNo='"+courseNo+"' and t.userByUserNumber.username='"+username+"' ";
//	System.out.println("-------peopleSql-------------"+peopleSql);
			List<TimetableAttendance> atds=timetableAttendanceDAO.executeQuery(peopleSql, 0,-1);
			int time=atds.size();//实到人数
			if(time!=0){
				People p=new People();
				p.setUsername(username);
				p.setCname(cname);
				p.setTime(time);
				
				people.add(p);
			}
		}
		return people;
	}
	/***********************************************************************************************
	 * 根据选课组编号和周次统计缺勤次数
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public List<People> findAbsenceByStudentsAndWeek(Integer week,String courseNo) {
		List<People> people=new ArrayList<People>();
		String peopleSql="select t from TimetableAttendance t where t.attendanceMachine=0 and t.actualAttendance=0";
		if(!week.equals(0)){//不等于0即查询的周次不是全部
			peopleSql+=" and t.week="+week;
		}
		peopleSql+=" and t.courseNo='"+courseNo+"' ";
		List<TimetableAttendance> atds=timetableAttendanceDAO.executeQuery(peopleSql, 0,-1);
		Set<User> students=new HashSet<User>();
		for (TimetableAttendance t : atds) {
			students.add(t.getUserByUserNumber());
		}
		for (User user : students) {
			String sql="select t from TimetableAttendance t where t.attendanceMachine=0 and t.actualAttendance=0";
			if(!week.equals(0)){//不等于0即查询的周次不是全部
				sql+=" and t.week="+week;
			}
			sql+=" and t.courseNo='"+courseNo+"' and t.userByUserNumber.username='"+user.getUsername()+"' ";
			List<TimetableAttendance> atts=timetableAttendanceDAO.executeQuery(sql, 0,-1);
			int time=atts.size();//缺勤次数
			if(time!=0){
				People p=new People();
				p.setUsername(user.getUsername());
				p.setCname(user.getCname());
				p.setTime(time);
				
				people.add(p);
			}
		}
		return people;
	}

	/**
	 * 判断指定用户是否具有指定权限
	 * @param username 用户工号
	 * @param authStr 权限标识
	 * @return boolean
	 * @author hely
	 * 2015.08.25
	 */
	@Override
	public boolean checkAuthority(String username, String authStr) {
		User user = userDAO.findUserByPrimaryKey(username);
		if(user != null && !"".equals(authStr))
		{
			for(Authority a : user.getAuthorities())
			{
				if(authStr.equals(a.getAuthorityName()))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	/***********************************************************************************************
	 * 获取所有教师数据
	 * 作者：贺子龙
	 ***********************************************************************************************/
	@Override
	public List<User> findAllTeacheres(){
		String s = "select u from User u where u.userRole=1";
		List<User> users = userDAO.executeQuery(s, 0, -1);
		return users;
	}
	/***********************************************************************************************
	 * 获取所有系主任数据
	 * 作者：周志辉
	 ***********************************************************************************************/
	@Override
	public List<User> findAllDepartmentHead(User user){
		String sql="select distinct u from User u join u.authorities a where  a.id=17 and u.schoolAcademy.academyNumber like '"+user.getSchoolAcademy().getAcademyNumber()+"'";
//		if (Id<=7||Id==13||Id==18) {//本学院
//			if(academyNumber!=null&&!academyNumber.equals("")){
//				sql+=" and u.schoolAcademy.academyNumber='"+academyNumber+"'";
//			}
//		}else {//校级
//		}

		List<User> users = userDAO.executeQuery(sql, 0, -1);
		return users;
	}
	/***********************************************************************************************
	 * 获取所有实训部主任数据
	 * 作者：周志辉
	 ***********************************************************************************************/
	@Override
	public List<User> findAllLabRoomtHead(){
		String sql="select distinct u from User u join u.authorities a where  a.authorityName='PREEXTEACHING'";
//		if (Id<=7||Id==13||Id==18) {//本学院
//			if(academyNumber!=null&&!academyNumber.equals("")){
//				sql+=" and u.schoolAcademy.academyNumber='"+academyNumber+"'";
//			}
//		}else {//校级
//		}

		List<User> users = userDAO.executeQuery(sql, 0, -1);
		return users;
	}
	/***********************************************************************************************
	 * @功能：获取ip地址
	 * @作者：魏诚
	 ***********************************************************************************************/
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	/***********************************************************************************************
	 * @功能：根据学号判断是否为本科生
	 * @作者：贺子龙
	 ***********************************************************************************************/
	public boolean isUndergraduate(String username){
		boolean isUndergraduate=true;
		if (!username.equals("")&&username.length()>3) {
			int grade=Integer.parseInt(username.substring(0, 2));//用户年级
			String flag=username.substring(2, 3);//学号第三位为标志位
			if (grade>=11) {//11级以后的学生
				if (flag.equals("0")) {
					isUndergraduate=false;
				}
			}else {//11级以前的学生
				if (!flag.equals("B")) {
					isUndergraduate=false;
				}
			}
		}
		return isUndergraduate;
	}
	/***********************************************************************************************
	 * @throws ParseException 
	 * @功能：根据年、月数字获取一个时间段
	 * @作者：贺子龙
	 ***********************************************************************************************/
	public Calendar[] getTimePeriod(int year, int month) throws ParseException{
		Calendar[] timePeriod={Calendar.getInstance(),Calendar.getInstance()};
		int day=31;
		if (month==2) {
			if(year%4==0&&year%100!=0||year%400==0) {//是闰年
				day=29;
			}else {
				day=28;
			}
		}else {
			if (month==4||month==6||month==9||month==11) {
				day=30;
			}
		}
		String str1=year+"-"+month+"-"+"01";
		String str2=year+"-"+month+"-"+day;
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Date date1 =sdf.parse(str1);
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date1);
		Date date2 =sdf.parse(str2);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);
		timePeriod[0]=calendar1;
		timePeriod[1]=calendar2;
//		System.out.println(getBelongsSchoolTerm(timePeriod[0]).getId()+"[[");
		return timePeriod;

	}
	/***********************************************************************************************
	 * 判断月份所属的学期
	 * 作者：贺子龙
	 ***********************************************************************************************/
	public SchoolTerm getSchoolTermByMonth(int year, int month) throws ParseException{
		SchoolTerm term=getBelongsSchoolTerm(Calendar.getInstance());//默认取当前学期
		int day=31;
		if (month==2) {
			if(year%4==0&&year%100!=0||year%400==0) {//是闰年
				day=29;
			}else {
				day=28;
			}
		}else {
			if (month==4||month==6||month==9||month==11) {
				day=30;
			}
		}
		for (int i = 1; i <= day; i++) {
			
			String str=year+"-"+month+"-"+i;
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
			Date date =sdf.parse(str);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			//遍历所有的学期
			Set<SchoolTerm> terms=schoolTermDAO.findAllSchoolTerms();
			int count=0;
			for (SchoolTerm schoolTerm : terms) {
				Calendar start=schoolTerm.getTermStart();
				Calendar end=schoolTerm.getTermEnd();
				if(calendar.after(start)&&calendar.before(end)){//输入月份的任意一天落在一个学期，那这个月就属于它所落入的学期。
					count++;
					term=schoolTerm;break;//减少循环次数
				}
			}
			if (count!=0) {
				break;//减少循环次数
			}
		}
//		System.out.println(term.getId()+"----");
		return term;
	}
	/***********************************************************************************************
	 * 判断该月份的起始和终止周次
	 * 作者：贺子龙
	 ***********************************************************************************************/
	public int[] getSchoolWeekByMonth(int year, int month) throws ParseException{
		int[] weekId={0,0};
		if (year!=0&&month!=0) {
			int day=31;
			if (month==2) {
				if(year%4==0&&year%100!=0||year%400==0) {//是闰年
					day=29;
				}else {
					day=28;
				}
			}else {
				if (month==4||month==6||month==9||month==11) {
					day=30;
				}
			}
			String str1=year+"-"+month+"-"+"01";
			String str2=year+"-"+month+"-"+day;
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
			Date date1 =sdf.parse(str1);
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(date1);
			SchoolWeek startWeek=getSchoolWeekByDate(calendar1);
			if (startWeek!=null&&startWeek.getWeek()!=0) {
				weekId[0]=startWeek.getWeek();
			}
			Date date2 =sdf.parse(str2);
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(date2);
			SchoolWeek endWeek=getSchoolWeekByDate(calendar2);
			if (endWeek!=null&&endWeek.getWeek()!=0) {
				weekId[1]=endWeek.getWeek();
			}
		}
		return weekId;
	}
	/***********************************************************************************************
	 * 判断该月份的起始和终止周次
	 * 作者：贺子龙
	 ***********************************************************************************************/
	public SchoolWeek getSchoolWeekByDate(Calendar time) throws ParseException{
		String sql="select w from SchoolWeek w where 1=1";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(time.getTime());
		
		sql+=" and w.date between '"+dateStr +"' and '"+dateStr+"' "; 
		List<SchoolWeek> weeks=schoolWeekDAO.executeQuery(sql, 0,-1);
		if (weeks.size()>0) {
			return weeks.get(0);
		}
		return null;
	}
	
	/***********************************************************************************************
	 * @功能：替换特殊字符
	 * @作者：贺子龙
	 * @日期：2016-03-07
	 ***********************************************************************************************/
	public String replaceString(String oldString){
		//将传来的特殊字符还原
		//1. +  URL 中+号表示空格 %2B
		//2. 空格 URL中的空格可以用+号或者编码 %20
		//3. /  分隔目录和子目录 %2F 
		//4. ?  分隔实际的 URL 和参数 %3F 
		//5. % 指定特殊字符 %25 
		//6. # 表示书签 %23 
		//7. & URL 中指定的参数间的分隔符 %26 
		//8. = URL 中指定参数的值 %3D
		oldString=oldString.replace("[Geng1Shang]", "+");
		oldString=oldString.replace("[Geng2Shang]", " ");
		oldString=oldString.replace("[Geng3Shang]", "/");
		oldString=oldString.replace("[Geng4Shang]", "?");
		oldString=oldString.replace("[Geng5Shang]", "%");
		oldString=oldString.replace("[Geng6Shang]", "#");
		oldString=oldString.replace("[Geng7Shang]", "&");
		oldString=oldString.replace("[Geng8Shang]", "=");
		oldString=oldString.replace("[Geng9Shang]", ".");
		String newString=oldString;
		return newString;
	}
	
	/***********************************************************************************************
	 * @功能：通用模塊service層定義-根据对象生成二维码，并返回二维码保存路径
	 * @作者：李小龙
	 ***********************************************************************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String getDimensionalCode(LabRoomDevice d, String serverName) throws Exception {
		PConfigDTO pConfigDTO = this.getCurrentDataSourceConfiguration();
		String url = "";
		// 获取系统路径
		String root = System.getProperty("zjcclims.root");
		// 二维码的保存路径
		// File.separator windows是\，unix是/
		String path = "upload" + "/" + "dimensionalCode";
		String text = "http://"+serverName + "/" + pConfigDTO.PROJECT_NAME + "/cmsshow/showDevice?id=" + d.getId();
		int width = 300;
		int height = 300;
		// 二维码的图片格式
		String format = "gif";
		Hashtable hints = new Hashtable();
		// 内容所使用编码
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
		File sendPath = new File(root + path);
		// 构建文件夹
		if (!sendPath.exists()) {
			sendPath.mkdirs();
		}
		// 生成二维码
		File outputFile = new File(root + path + File.separator + d.getSchoolDevice().getDeviceNumber() + ".gif");
		MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
		url = path + "/" + d.getSchoolDevice().getDeviceNumber() + ".gif";

		System.out.println("二维码路径：" + url);
		return url;
	}


	/***********************************************************************************************
	 * @功能：通用模塊service層定義-根据对象生成二维码，并返回二维码保存路径
	 * @作者：李小龙
	 ***********************************************************************************************/
	public String getDimensionalCodeForAsset(Asset a, String serverName) throws Exception{String url = "";
		// 获取系统路径
		String root = System.getProperty("zjcclims.root");
		// 二维码的保存路径
		// File.separator windows是\，unix是/
		String path = "upload" + "/" + "dimensionalCodeForAsset";
		String text = a.getId().toString()+" "+a.getChName();
		int width = 300;
		int height = 300;
		// 二维码的图片格式
		String format = "gif";
		Hashtable hints = new Hashtable();
		// 内容所使用编码
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
		File sendPath = new File(root + path);
		// 构建文件夹
		if (!sendPath.exists()) {
			sendPath.mkdirs();
		}
		// 生成二维码
		File outputFile = new File(root + path + File.separator + a.getId() + ".gif");
		MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
		url = path + "/" + a.getId() + ".gif";

		System.out.println("二维码路径：" + url);
		return url;
	}

	/***********************************************************************************************
	 * 功能：将文件上传到指定路径并返回文件保存路径
	 * 作者：黄崔俊
	 * 时间：2015-12-14 14:02:30
	 ***********************************************************************************************/
	@Override
	public String fileUpload(HttpServletRequest request,String folderName, String filePath) {
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		
		/*SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
	    // 构建文件保存的目录
	    String PathDir = "/upload/"+ dateformat.format(new Date());*/
		/** 得到文件保存目录的真实(绝对)路径* */
		String RealPathDir = request.getSession().getServletContext().getRealPath(filePath);
		//根据真实路径创建文件夹
		File SaveFile = new File(RealPathDir);
		if (!SaveFile.exists()){
			SaveFile.mkdirs();
		}
		/** 页面控件的文件流* */
		MultipartFile multipartFile = multipartRequest.getFile(folderName);
		/**判断文件不为空*/
		if(!multipartFile.isEmpty()){
			//截取上传文件的名称，获取文件的后缀
			String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
			// 使用UUID生成文件名称,中文一般会报错(产生一个号称全球唯一的ID)
			String name = UUID.randomUUID().toString() + suffix;// 构建文件名称
			// 拼成完整的文件保存路径加文件
			String fileName = RealPathDir + File.separator + name;
			File file = new File(fileName);
			try {
				//转储文件
				multipartFile.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			/** 上传到服务器的文件的绝对路径* */	       
			String saveUrl=filePath+File.separator+name;
			return saveUrl;
		}
		return "";
	}
	/***********************************************************************************************
	 * 删除指定路径的文件
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public boolean deleteFile(String documentUrl) {
		boolean flag=false;
		File file=new File(documentUrl);
		if(file.exists()&&file.isFile()){
			file.delete();
			flag=true;
		}
		return flag;
	}

	@Override
	public User changeUserPassword(User user, String password) {
		// TODO Auto-generated method stub
		user.setPassword(password);
		return userDAO.store(user);
	}
	
	/**
	  *@comment：获取form上传的文件
	  *@param request
	  *@return：
	  *@author：叶明盾
	  *@date：2015-10-14 下午3:52:37
	 */
	public String getUpdateFileSavePath(HttpServletRequest request) {

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		/** 日期格式 **/
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
		/** 构建文件保存的目录* */
		String logoPathDir = "/upload/" + dateformat.format(new Date());
		/** 得到文件保存目录的真实路径* */
		String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir);
		/** 根据真实路径创建目录* */
		File logoSaveFile = new File(logoRealPathDir);
		if (!logoSaveFile.exists())
			logoSaveFile.mkdirs();
		/** 页面控件的文件流* */

		MultipartFile multipartFile = multipartRequest.getFile("file");
		if (multipartFile == null) {// 如果没有文件，则返回
			return null;
		}
		/** 获取文件的后缀* */
		// System.out.println(multipartFile.getOriginalFilename());
		/** 判断文件不为空 */
		if (!multipartFile.isEmpty()) {
			String suffix = multipartFile.getOriginalFilename().substring(
					multipartFile.getOriginalFilename().lastIndexOf("."));
			/** 使用UUID生成文件名称* */
			String logImageName = UUID.randomUUID().toString() + suffix;// 构建文件名称
			/** 拼成完整的文件保存路径加文件* */
			String fileName = logoRealPathDir + File.separator + logImageName;
			File file = new File(fileName);
			try {
				multipartFile.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			/** 上传到服务器的文件的绝对路径* */
			String saveUrl = logoPathDir + "/" + logImageName;
			return saveUrl;
		}
		return null;
	}
	
	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		User newUser = userDAO.store(user);
		return newUser;
	}

	/***********************************************************************************************
	 * @功能：上传文件
	 * @作者：魏诚
	 * @日期：2014-07-27
	 ***********************************************************************************************/
	@SuppressWarnings({ "rawtypes" })
	@Override
	public void uploadFiles(HttpServletRequest request, String path, String type, int id) {
		// TODO Auto-generated method stub
		String sep = System.getProperty("file.separator");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		/** 构建文件保存的目录* */
		// String PathDir = "/upload/"+ dateformat.format(new Date());
		/** 得到文件保存目录的真实路径* */
		String RealPathDir = request.getSession().getServletContext().getRealPath("/") + sep + path;
		// System.out.println("文件保存目录的真实路径:"+logoRealPathDir);
		/** 根据真实路径创建目录* */
		File SaveFile = new File(RealPathDir);
		if (!SaveFile.exists()) {
			SaveFile.mkdirs();
		}
		Iterator fileNames = multipartRequest.getFileNames();
		for (; fileNames.hasNext();) {
			String filename = (String) fileNames.next();
			MultipartFile file = multipartRequest.getFile(filename);
			String fileTrueName = file.getOriginalFilename();
			if (!file.isEmpty()) {
				try {
					file.transferTo(new File(RealPathDir + sep + fileTrueName));
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (type.equals("saveLabRoomVideo")) {
					labRoomService.saveLabRoomVideo(fileTrueName, id);
				} else if (type.equals("saveLabRoomDocument")) {
					labRoomService.saveLabRoomDocument(fileTrueName, id, 2);
				} else if (type.equals("saveDocument")) {
					labAnnexService.saveDocument(fileTrueName, id);
				} else if (type.equals("saveVideo")) {
					labAnnexService.saveVideo(fileTrueName, id);
				} else if (type.equals("saveDeviceDocument")) {
					labRoomDeviceService.saveDeviceDocument(fileTrueName, id, 2);
				} else if (type.equals("saveDeviceImage")) {
					labRoomDeviceService.saveDeviceDocument(fileTrueName, id, 1);
				}else if (type.equals("saveLabWorkerTraininDocument")) {
					labWorkerTrainingService.saveLabWorkerTrainingDocument(fileTrueName, id, 1);
				}else if (type.equals("saveLabRoomDeviceRepairDocument")) {
					labWorkerTrainingService.saveLabRoomDeviceRepairDocument(fileTrueName, id, 1);
				}else if (type.equals("saveSoftwareInstallDocument")) {
					softwareService.saveSoftwareDocument(fileTrueName, id,1);
				}else if (type.equals("saveSoftwareUseDocument")) {
					softwareService.saveSoftwareDocument(fileTrueName, id,2);
				}else if (type.equals("savePerformanceDocument")){
					performanceService.saveLabRoomDocument(fileTrueName,id,2);
				}


			}
		}
		MultipartFile multipartFile = multipartRequest.getFile("file");

		/** 判断文件不为空 */
		// 存放文件文件夹名称
		for (; fileNames.hasNext();) {
			String filename = (String) fileNames.next();
			MultipartFile file = multipartRequest.getFile(filename);
			String suffix = multipartFile.getOriginalFilename().substring(
					multipartFile.getOriginalFilename().lastIndexOf("."));
			/** 使用UUID生成文件名称* */
			String logImageName = UUID.randomUUID().toString() + suffix;// 构建文件名称
			/** 拼成完整的文件保存路径加文件* */
			String fileName = RealPathDir + File.separator + logImageName;
			File thisFile = new File(RealPathDir);
			if (!thisFile.exists()) {
				thisFile.mkdirs();
			} else {
				try {
					file.transferTo(new File(fileName));
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	/***********************************************************************************************
	 * @功能：通用模塊service層定義-判断指定实验室的禁用时间段内，根据日历表的起始时间和结束时间判断是否可用
	 * @作者：魏诚
	 * @日期：2016-03-08
	 ***********************************************************************************************/
	@Override
	public boolean isLimitedByTime(List<LabRoomLimitTime> labRoomLimitTimes,Calendar startDate,Calendar endDate) {

		boolean flag = true;
		SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//如果是禁用时间则返回limit
		for(LabRoomLimitTime labRoomLimitTime:labRoomLimitTimes ){
			//设置日期起始变量
			Calendar currCalendar = startDate;
			//遍历日历插件上的起始时间结束时间，计量单位按日
			//for (; currCalendar.compareTo(endDate) <= 0;) {  
				int cCurrentWeek = this.getBelongsSchoolWeek(startDate);

				
				//当前遍历禁用日期星期，匹配当前的日历时间的星期
				int weekday =currCalendar.get(Calendar.DAY_OF_WEEK)-1;
				
				if(weekday==0){  
					weekday = 7;;  
				}
				
				if(weekday==labRoomLimitTime.getWeekday()){
					//当前遍历的周次匹配禁用日期的周次
					if(labRoomLimitTime.getStartweek()<=cCurrentWeek&&labRoomLimitTime.getEndweek()>=cCurrentWeek){
						//当前选取时间对于当前节次
						String sql = "select c from SystemTime c where c.systemCampus.id=2 and (c.startDate>'"+ sdfTime.format(currCalendar.getTime()).split(" ")[1] +
								"' and c.startDate<'"+ sdfTime.format(endDate.getTime()).split(" ")[1] + 
								"' or c.endDate>'"+ sdfTime.format(currCalendar.getTime()).split(" ")[1] +
								"' and c.endDate<'"+ sdfTime.format(endDate.getTime()).split(" ")[1] + "')";
						System.out.println("sql:" + sql);
						List<SystemTime> systemTimes = systemTimeDAO.executeQuery(sql, 0,-1);
						//对在日历所选的日期所获取的周次进行遍历
			            //如果禁止日期对应在周次内，同时星期对应的日期相同，节次相同则判断为limit
						for(SystemTime systemTime:systemTimes){
							if(labRoomLimitTime.getStartclass()<=systemTime.getSection()&&labRoomLimitTime.getEndclass()>=systemTime.getSection()){
								return false;
							}
						}
						
					}
				//}
				//累计加一天
				//currCalendar.add(Calendar.DAY_OF_YEAR, 1);  
			}
		}
		
		return flag;
	}
	
	/***********************************************************************************************
	 * @功能：通用模塊service層定義-根据周次星期信息获取当前时间
	 * @作者：魏诚
	 * @日期：2016-03-05
	 ***********************************************************************************************/
	@Override
	public String getDateByWeekdayClassWeek(int weekday,int classes,int week,int term,int flag) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "select w from SchoolWeek w where w.weekday=" + weekday + " and w.week=" +week + " and w.schoolTerm.id=" +term;
		List<SchoolWeek> weeks = schoolWeekDAO.executeQuery(sql, 0, -1);
		  
		SystemTime systemTime = systemTimeDAO.findSystemTimeById(classes);
		
		if (weeks.size() > 0&&systemTime!=null) {
			//如果为起始时间flag=0，如果为结束时间flag=1
			if(flag==0){
				return sdf.format(weeks.get(0).getDate().getTime())+" "+ sdfTime.format(systemTime.getStartDate().getTime()).split(" ")[1]  ;
			}else{
				return sdf.format(weeks.get(0).getDate().getTime())+" "+ sdfTime.format(systemTime.getEndDate().getTime()).split(" ")[1]  ;
			}
		} else {
			return "";
		}

	}
	/***********************************************************************************************
	 * @功能：通用username找到user
	 * @作者：郑昕茹
	 * @日期：2016-08-07
	 ***********************************************************************************************/
	public User findUserByUsername(String username){
		return userDAO.findUserByUsername(username);
	}
	
	/*************************************************************************************
	 * Description：排课模块-ajax获取可用周次{判断当前的实验室是否属于不需要判冲的特殊实验室}
	 * 
	 * @author： 贺子龙
	 * @date：2016-07-27
	 *************************************************************************************/
	public boolean isSpecialLabRoom(int labRoomId){
		boolean isSpecialLabRoom = false;
		// 通过主键查找对应的实验室记录
		LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(labRoomId);
		// 判断labRoom的isSpecial属性
		if (!EmptyUtil.isIntegerEmpty(labRoom.getIsSpecial()) 
				&& labRoom.getIsSpecial().equals(1)) {// 是特殊实验室
			isSpecialLabRoom = true;
		}
		
		return isSpecialLabRoom;
	}
	
	/***********************************************************************************************
	 * Description:查找当前学期及以后的学期
	 * @author：邵志峰
	 * @date：2017-5-26
	 ***********************************************************************************************/
    public List<SchoolCourse> getMyCourse(){
    	String teacherId=getUser().getUsername();//查找当前用户
    	
    	StringBuffer sqlStr=new StringBuffer("");
    	sqlStr.append("select s from SchoolCourse s where s.userByTeacher.username='"+teacherId+"'");
    	if(getCurTerm()!=null){
    		sqlStr.append(" and s.schoolTerm.id="+getCurTerm().getId());
    	}
    	/*sqlStr+=" group by s.schoolCourseInfo.courseNumber";*/
    	return schoolCourseDAO.executeQuery(sqlStr.toString());
    }
    
    /***********************************************************************************************
	 * Descrption:当前学期
	 * 
	 * @author：邵志峰
	 * @date：2017-5-26
	 ***********************************************************************************************/
    public SchoolTerm getCurTerm(){
    	String sb = "select t from SchoolTerm t where now() between t.termStart and t.termEnd or now() < t.termStart order by t.termStart asc";
		return schoolTermDAO.executeQuery(sb).size()>0? schoolTermDAO.executeQuery(sb).get(0) : null;		
    }
    
	 /******************************************************************
		 * Description:保存实训室借用申请表
		 * 
		 * @param：softwarereserve 软件申请表
		 * @author：邵志峰
		 * @date:2017-05-31 
		 *****************************************************************/
		@Transactional
		public int saveLabRoomLending(LabRoomLending labRoomLending) {
		
			labRoomLending = labRoomLendingDAO.store(labRoomLending);
			
			labRoomLendingDAO.flush();
			return labRoomLending.getId();
		}

	@Override
	public SchoolYear getBelongsSchoolYear(Calendar time) {
		// TODO Auto-generated method stub
		// 建立查询
		String sql = "select t from SchoolYear t order by t.yearStart asc";
		// 遍历所有的学期，并按学期开始时间，正序排列
		List<SchoolYear> years=schoolYearDAO.executeQuery(sql);
		SchoolYear year=new SchoolYear();
		for (SchoolYear schoolYear : years) {
			// 获取学期起始时间
			Calendar start=schoolYear.getYearStart();
			// 获取学期结束时间
			Calendar end=schoolYear.getYearEnd();

			if(time.after(start)&&time.before(end)){// 当前日期正好属于某一个学期
				year=schoolYear;
				break;
			}else if (time.before(start)) {// 寒暑假，已经建立新学期时，按新学期
				year=schoolYear;
				break;
			}else if(time.after(end)){// 寒暑假，没有建新学期时，按老学期
				year=schoolYear;
			}
		}
		return year;
	}
	/************************************************************ 
	 * @功能:获取节次
	 * @作者：戴昊宇
	 * @日期：2017-09-27
	 ************************************************************/
	public int getCurClass(Calendar time){
		//查找节次,节次表id大于29的是被修改过的特殊节次时间
		StringBuffer sb = new StringBuffer(
				"select c from SystemTime c where '"+time+"' between time(c.startDate) and time(c.endDate)");
		
		List<SystemTime> systemTime = systemTimeDAO.executeQuery(sb.toString());
		if(systemTime.size()>0){
			return systemTime.get(0).getSection();
		}		
		return 0;	
	}
	
	/***********************************************************************************************
	 * Descrption:实验室借用-使用类型
	 * 
	 * @author：张愉
	 * @date：2017-10-29
	 ***********************************************************************************************/
    public CDictionary getCDictionaryforLabResrevationuse(int id){
    	String sb = "select t from CDictionary t where 1=1";
    	sb+=" and t.id="+id;
		return cDictionaryDAO.executeQuery(sb).size()>0? cDictionaryDAO.executeQuery(sb).get(0) : null;		
    }

	/**
	 * Description 消息保存接口
	 * @param receiveUser
	 * @param message
	 * @author 周志辉 2017-11-08
	 */
	public void sendMsg(User receiveUser, Message message) {
		PConfigDTO pConfigDTO = this.getCurrentDataSourceConfiguration();
		message.setUsername(receiveUser.getUsername());
		messageDAO.store(message);
		messageDAO.flush();
		if (receiveUser.getTelephone() != null && (pConfig.PROJECT_NAME.equals("zjcclims") || pConfig.PROJECT_NAME.equals("limsproduct"))) {// 暂时给浙江建设/鲁班楼发送短信
//			try {
//				String mess = receiveUser.getCname() + "您好，您有" + message.getTitle() + "，请登录平台处理或查阅！";
//				String result = this.sendMessage(receiveUser.getTelephone(), mess);
//			} catch (InterruptedException | NoSuchAlgorithmException e) {
//				e.printStackTrace();
//			}
		}
	}
    
    /***********************************************************************************************
	 * Descrption:短信发送接口
     * @throws NoSuchAlgorithmException 
     * @throws InterruptedException 
	 * 
	 * @author：周志辉
	 * @date：2017-11-08
	 ***********************************************************************************************/
	@Override
	public synchronized String  sendMessage(String tel, String content) throws NoSuchAlgorithmException, InterruptedException {
		if(tel != null && !"".equals(tel)){
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
			int date=Integer.parseInt(df.format(new Date()));
			int result1=57100053&date;
			String str1=String.valueOf(result1);
			String str=str1.substring(str1.length()-6,str1.length());
			MessageDigest md;
			md=MessageDigest.getInstance("MD5");
			byte[] str_byte = str.getBytes();
			byte[] md5_result_byte = md.digest(str_byte);  
			String md5_result = bytesToHex(md5_result_byte).toLowerCase();
			SendMsgPortType sendMsg = new SendMsg_Service().getSendMsgHttpPort();
			String result=sendMsg.sendMsg("0571053",md5_result, tel, content);
			return result;
		}
		return "no phoneNumber";
	}
	public static String bytesToHex(byte[] bytes) {  
        StringBuffer md5str = new StringBuffer();  
        //把数组每一字节换成16进制连成md5字符串  
        int digital;  
        for (int i = 0; i < bytes.length; i++) {  
//             System.out.println("，   "+bytes[i]);  
             digital = bytes[i];  
            if(digital < 0) {  //16及以上的数转16进制是两位  
                digital += 256;  
            }  
            if(digital < 16){  
                md5str.append("0");//如果是0~16之间的数的话则变成0X  
            }  
            md5str.append(Integer.toHexString(digital));  
        }  
        return md5str.toString().toUpperCase();  
    } 
	
	/**************************************
 	  * 功能：根据名字查询设备管理员
 	  * 作者：张德冰
 	  * 日期：2018.03.15
 	  **************************************/
     public User findUserByCName(String userCName){
    	String sql="select u from User u where 1=1 and u.cname ='"+userCName+"'";
 		List<User> list = userDAO.executeQuery(sql);
 		if(list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
     }
     
     /**************************************
   	  * 功能：根据状态查询id
   	  * 作者：张德冰
   	  * 日期：2018.03.15
   	  **************************************/
      public CDictionary findCDictionaryByCName(String status){
    	String sql="select c from CDictionary c where 1=1 and c.CName ='"+status+"'";
   		List<CDictionary> list = cDictionaryDAO.executeQuery(sql);
   		return list.get(0);
      }
      
      /**************************************
       * 功能：根据用户名查询培训记录数量
       * 作者：张德冰
       * 日期：2018.03.19
       **************************************/
      public int counttrainings(String username){
    	  String sql="select l from LabRoomTrainingPeople l where 1=1 and l.user.username ='"+username+"'";
    	  List<LabRoomTrainingPeople> l = labRoomTrainingPeopleDAO.executeQuery(sql);
    	  return l.size();
      }
      

      /**************************************
       * 功能：根据用户名查询培训记录
       * 作者：张德冰
       * 日期：2018.03.21
       **************************************/
      public List<LabRoomTrainingPeople> findTrainingRecordByUsername(
			String username){
    	  String sql="select l from LabRoomTrainingPeople l where 1=1 and l.user.username ='"+username+"'";
    	  List<LabRoomTrainingPeople> l = labRoomTrainingPeopleDAO.executeQuery(sql);
    	  return l;
      }
      
      /***********************************************************************************************
  	 * 获取当前时间所属的节次
  	 * 作者：戴昊宇
  	 ***********************************************************************************************/
  	@Override
  	public int getBelongsClass(Calendar time) {
  		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
  		String date=sdf.format(time.getTime());
  		String sql="select w from SystemTime w where w.startDate<='"+date+"'"+" and w.endDate>='"+date+"'";
  		List<SystemTime> classes=systemTimeDAO.executeQuery(sql, 0,-1);
  		if(classes.size()>0 && classes.get(0) != null && classes.get(0).getSection() != null){
  			return classes.get(0).getSection();
  		}else
  			return 1;
  		}
	/************************************************************************
	 *@Description:获取所有实验中心主任lab_center表的manager
	 *@Author:孙虎
	 *@Date:2018/5/30
	 ************************************************************************/
    @Override
    public List<User> getLabCenterManagers() {
		List<LabCenter> labCenters = new ArrayList<LabCenter>(labCenterDAO.findAllLabCenters());
		List<User> labManagers = new ArrayList<User>();
		for (LabCenter labCenter:labCenters) {
			if(labCenter.getUserByCenterManager() != null){
				labManagers.add(labCenter.getUserByCenterManager());
			}
		}
		return labManagers;
    }
	/************************************************************************
	 *@Description:获取某个学院的下的某个身份的全体成员
	 *@Author:孙虎
	 *@Date:2018/5/30
	 ************************************************************************/
    @Override
    public List<User> findAuthByAuthNameAndAcademy(String authName, String academyNumber) {
		List<User> returnUsers = new ArrayList<User>();
		List<String> username = entityManager.createNativeQuery("SELECT" +
				" username" +
				" from user where academy_number = '"+academyNumber+"' and username in" +
				" (select user_id from user_authority where authority_id =" +
				" (select id from authority where authority_name ='"+authName+"'))"
		).getResultList();
    	if(username.size()!=0){
    		StringBuilder uBuilder = new StringBuilder();
    		for (String s:username){
    			uBuilder.append(s);
    			uBuilder.append(",");
			}
			String u = uBuilder.substring(0, uBuilder.length()-1);
			returnUsers = userDAO.executeQuery("select u from User u where u.username in('" + u + "')");
		}
        return returnUsers;
    }

	/***********************************************************************************************
	 * 功能：下载文件
	 * 作者：李德
	 * 时间：2015-04-27
	 ***********************************************************************************************/
	@Override
	public void downloadFileByDocumentId(int idkey, HttpServletRequest request,
										 HttpServletResponse response) {
		try{
			//File sendPath = createDirdown();
			//FileInputStream fis = new FileInputStream(sendPath+File.separator+fileName);
			//根据idkey查询附件信息
			CommonDocument doc=commonDocumentDAO.findCommonDocumentById(idkey);
			String fileName = doc.getDocumentName();
			//系统路径
			String root = System.getProperty("zjcclims.root");
			//文件路径
			String fileDir = root +doc.getDocumentUrl();
			System.out.println("fileDir="+fileDir);

			FileInputStream fis = new FileInputStream(fileDir);
			response.setCharacterEncoding("utf-8");

			//解决上传中文文件时不能下载的问题
			response.setContentType("multipart/form-data;charset=UTF-8");
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
				fileName = new String(fileName.getBytes("UTF-8"),"ISO8859-1");// firefox浏览器
			} else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
				fileName = URLEncoder.encode(fileName, "UTF-8");// IE浏览器
			} else {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}
			response.setHeader("Content-Disposition", "attachment;fileName="+fileName.replaceAll(" ", ""));

			OutputStream fos = response.getOutputStream();
			byte[] buffer = new byte[8192];
			int count = 0;
			while((count = fis.read(buffer))>0){
				fos.write(buffer,0,count);
			}
			fis.close();
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				response.getOutputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/***********************************************************************************************
	 * 找到某学院的所有教研室主任
	 * 作者：黄浩
	 ***********************************************************************************************/
	@Override
	public List<User> findDepartmentHeaderByAcademyNumber(SchoolAcademy schoolAcademy) {
		// TODO Auto-generated method stub
		String sql="select u from User u join u.authorities a where a.id=18 and u.schoolAcademy.academyNumber = '"+ schoolAcademy.getAcademyNumber() +"'";
 		return userDAO.executeQuery(sql,0,-1);
	}

	/**
	 * Description 获取所有实验室
	 * @return
	 * @author 陈乐为 2018-7-24
	 */
	@Override
	public List<LabRoom> findAllLabRooms() {
		// TODO Auto-generated method stub
		String sql="select l from LabRoom l where 1=1 and l.labCategory=1 order by l.id desc";
		return labRoomDAO.executeQuery(sql, 0, -1);
	}

	/**
	 * Description 根据主键查找学期
	 * @param idKey
	 * @return
	 * @author 陈乐为 2018-8-26
	 */
	@Override
	public SchoolTerm findSchoolTermByPrimaryKey(int idKey) {
		return schoolTermDAO.findSchoolTermByPrimaryKey(idKey);
	}
	/***********************************************************************************************
	 * @功能：将String转换成Calendar
	 * @作者：赵晶
	 * @日期：2017-12-25
	 ***********************************************************************************************/
	public Calendar toCalendar(String dateStr) {
		// 声明一个Date类型的对象
		Date date = null;
		// 声明格式化日期的对象
		SimpleDateFormat format = null;
		Calendar calendar=null;
		if (dateStr != null) {
			// 创建日期的格式化类型
			format = new SimpleDateFormat("yyyy-MM-dd");
			// 创建一个Calendar类型的对象
			calendar = Calendar.getInstance();
			// forma.parse()方法会抛出异常
			try {
				//解析日期字符串，生成Date对象
				date = format.parse(dateStr);
				// 使用Date对象设置此Calendar对象的时间
				calendar.setTime(date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return calendar;
	}
	/***********************************************************************************************
	 * @功能：将String转换成Calendar(精确到时分秒)
	 * @作者：赵晶
	 * @日期：2018-1-9
	 ***********************************************************************************************/
	public Calendar toCalendarDetil(String dateStr) {
		// 声明一个Date类型的对象
		Date date = null;
		// 声明格式化日期的对象
		SimpleDateFormat format = null;
		Calendar calendar=null;
		if (dateStr != null) {
			// 创建日期的格式化类型
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 创建一个Calendar类型的对象
			calendar = Calendar.getInstance();
			// forma.parse()方法会抛出异常
			try {
				//解析日期字符串，生成Date对象
				date = format.parse(dateStr);
				// 使用Date对象设置此Calendar对象的时间
				calendar.setTime(date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return calendar;
	}

	/**
	 * Description 字典表数据判断
	 * @param idKey
	 * @return
	 * @author 陈乐为 2018-8-30
	 */
	@Override
	public boolean checkCDictionary(Integer idKey, String cNumber, String cCategory) {
		CDictionary cDictionary = cDictionaryDAO.findCDictionaryByPrimaryKey(idKey);
		if(cDictionary.getCNumber().equals(cNumber) && cDictionary.getCCategory().equals(cCategory)) {
			return true;
		}else return false;
	}

	public JSONObject useAuditAPI(String type, String... param){
		return new JSONObject();
	}

	/**
	 * @Description 根据权限和实验中心获取所有人
	 * @author 张德冰
	 * @date 2018.09.12
	 */
	@Override
	public List<User> findUserByCidAndAuthorities(Integer cid, Integer authoritieId){
		String  sql = "select u from User u join u.authorities a where 1=1";
		//获取学院
		if(cid != null) {
			LabCenter lc = labCenterDAO.findLabCenterByPrimaryKey(cid);
			String san = lc.getSchoolAcademy().getAcademyNumber();
			sql += " and u.schoolAcademy.academyNumber = '"+san+"'";
		}
		if(authoritieId != null){
			sql += " and a.id="+authoritieId;
		}
		List<User> user = userDAO.executeQuery(sql,0,-1);
		return user;
	}
	/**
	 * 获取学期数据
	 * @author 廖文辉
	 * 2018.09.21
	 */
	public List<SchoolTerm> findSchoolTerm(){
		StringBuffer hql = new StringBuffer("select t from SchoolTerm t where 1=1 ");
		hql.append(" group by t.yearCode");
		/*hql.append(" order by t.termStart desc");*/
		return schoolTermDAO.executeQuery(hql.toString(), 0, -1);
	}

	/**
	 * Description 根据主键查询学院对象
	 * @param number
	 * @return
	 * @author 陈乐为 2018-9-21
	 */
	public SchoolAcademy findSchoolAcademyByPrimaryKey(String number) {
		return schoolAcademyDAO.findSchoolAcademyByPrimaryKey(number);
	}

	/**
	 * Description 获取当前系统权限等级
	 * @return
	 * @author 陈乐为 2018-9-29
	 */
	public int getLevelByAuthName(String author) {
		// 获取权限英文
		author = author.split("_")[1];
		// 根据名称查找对象
		Authority authority = authorityDAO.findAuthorityByName(author);
		// 返回权限等级
		return authority.getType();
	}

	/**
	 * Description lab_center查询
	 * @param hql
	 * @param currpage
	 * @param pagesize
	 * @return
	 * @author 陈乐为 2018-9-29
	 */
	public List<LabCenter> executeLabCenter(StringBuffer hql, int currpage, int pagesize) {
		return labCenterDAO.executeQuery(hql.toString(), (currpage-1)*pagesize, pagesize);
	}

	public int executeLabCenterCount(StringBuffer hql) {
		return ((Long) labCenterDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}
	/**
	 * Description 获取实验室类别字段表数据(除去实验基地）
	 * @param category 类别
	 * @return
	 * @author 廖文辉 2018-10-26
	 */
	public List<CDictionary> getLabType(String category){
		StringBuffer hql = new StringBuffer("select c from CDictionary c where 1=1 ");
		if(category!=null && !"".equals(category))
		{
			hql.append(" and c.CCategory = '"+category+"' and c.CNumber<>4 order by c.id");
			return cDictionaryDAO.executeQuery(hql.toString(), 0, -1);
		}

		return null;
	}
	/**
	 * Description 获取学院列表
	 * @return
	 * @author 廖文辉 2018-11-01
	 */
	public List<SchoolAcademy> getSchoolAcademy(){
		StringBuffer hql = new StringBuffer("select l from SchoolAcademy l where 1=1 ");
		return schoolAcademyDAO.executeQuery(hql.toString(), 0, -1);
	}
	/**
	 * Description 获取当前周的日期
	 * @return
	 * @author 廖文辉 2018-11-02
	 */
	public List<SchoolWeek> getDate(int week){
		SchoolTerm schoolTerm =getBelongsSchoolTerm(Calendar.getInstance());
		String sql =" select s from SchoolWeek s where 1=1";
		sql+=" and s.week="+week;
		sql+=" and s.schoolTerm.id="+schoolTerm.getId();
		List<SchoolWeek> schoolWeeks=schoolWeekDAO.executeQuery(sql,0,-1);
		return schoolWeeks;
	}

	/**
	 * Description 根據學院和權限查找用戶
	 * @param cid
	 * @param authName
	 * @return
	 * @author 陳樂為 2017年10月2日
	 */
	@Override
	public List<User> findUserByQuery(int cid, String authName) {
		String sql = "select c from User c join c.authorities a where a.authorityName= '"+ authName +"'";
		String academyNumber = "";
		if(cid != 0) {
			if (cid != -1) {
				academyNumber = labCenterDAO.findLabCenterByPrimaryKey(cid).getSchoolAcademy().getAcademyNumber();
			} else {
				academyNumber = this.getUserDetail().getSchoolAcademy().getAcademyNumber();
			}
			sql += " and c.schoolAcademy.academyNumber=" + academyNumber;
		}
		return userDAO.executeQuery(sql,0,-1);
	}

	/**
	 * Description 根据学院、权限输出用户
	 * @param auth 权限英文名
	 * @param acno 学院编号
	 * @return
	 * @author 陈乐为 2018-11-29
	 */
	@Override
	public List<User> findUsersByQuery(String auth, String acno){
		// TODO Auto-generated method stub
		String sql="select u from User u join u.authorities a where 1=1";
		if(!EmptyUtil.isStringEmpty(auth)) {
			sql += " and a.authorityName = '"+ auth +"'";
		}
		if(!EmptyUtil.isStringEmpty(acno) && !acno.equals("-1")) {
			sql += " and u.schoolAcademy.academyNumber = '"+ acno +"'";
		}
		return userDAO.executeQuery(sql,0,-1);
	}

	/**
	 * Description 项目可以添加的实验室设备
	 * @param labId
	 * @param itemId
	 * @return
	 * @author 陈乐为 2018-11-30
	 */
	@Override
	public List<LabRoomDevice> findLabRoomDeviceByQuery(Integer labId, Integer itemId) {
		String sql = "select d from LabRoomDevice d where 1=1 and d.labRoom.id=" + labId;
		return labRoomDeviceDAO.executeQuery(sql, 0, -1);
	}

	/**
	 * Description 根据工号查找用户对象
	 * @param username
	 * @return
	 * @author 陈乐为 2018-12-7
	 */
	@Override
	public User getUserByPrimaryKey(String username) {
		return userDAO.findUserByPrimaryKey(username);
	}
	/**
	 * Description 取某一个硬件
	 * @param category
	 * @param cnumber
	 * @return
	 * @author 廖文辉 2018-12-11
	 */
	public List<CDictionary> getOnlyCDictionaryData(String category,String cnumber){
		StringBuffer hql = new StringBuffer("select c from CDictionary c where 1=1 ");
		if(category!=null && !"".equals(category)) {
			hql.append(" and c.CCategory = '" + category + "'");
		}
		if(cnumber!=null &&!"".equals(cnumber)){
			hql.append(" and c.CNumber = '"+cnumber+"' order by c.id");
		}
			return cDictionaryDAO.executeQuery(hql.toString(), 0, -1);
	}

	/**
	 * Description 楼宇联动实验室
	 * @param build_number
	 * @return
	 * @author 陈乐为 2018-12-17
	 */
	@Override
	public String linkLabByBuild(String build_number) {
		StringBuffer hql = new StringBuffer("select c from LabRoom c where c.systemBuild.buildNumber='"+ build_number +"'");
		hql.append(" and labCategory=1");
		List<LabRoom> labRooms = labRoomDAO.executeQuery(hql.toString(),0,-1);
		String rooms="<option value=''>请选择</option>";
		for(LabRoom labRoom : labRooms) {
			rooms += "<option value=" + labRoom.getId() + ">" + labRoom.getLabRoomName()+ "</option>";
		}

		return rooms;
	}

	/**
	 * Description 获取未来时间段
	 * @return
	 * @author 陈乐为 2018-12-26
	 */
	@Override
	public List<SystemTime> getNotObsoleteTime() {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
		StringBuffer hql = new StringBuffer("select c from SystemTime c where c.startDate > '"+df.format(new Date())+"' order by c.startDate");
		List<SystemTime> systemTimes = systemTimeDAO.executeQuery(hql.toString());
		return systemTimes;
	}

	/**
	 * Description 中心联动实验分室
	 * @param center_id
	 * @return
	 * @author 陈乐为 2019-1-2
	 */
	@Override
	public String linkLabByCenter(Integer center_id) {
		StringBuffer hql = new StringBuffer("select c from LabRoom c where c.labCenter.id="+ center_id);
		hql.append(" and labCategory=1");
		List<LabRoom> labRooms = labRoomDAO.executeQuery(hql.toString(),0,-1);
		String rooms="<option value=''>请选择</option>";
		for(LabRoom labRoom : labRooms) {
			rooms += "<option value=" + labRoom.getId() + ">" + labRoom.getLabRoomName()+ "</option>";
		}

		return rooms;
	}

	/**
	 * Description 获取学院下所有实验分室
	 * @param acno
	 * @labCategory 标志位（1实验分室，2工作室，3会议室）
	 * @return
	 */
	@Override
	public List<LabRoom> getLabByAcademy(String acno) {
		// 选框-当前学院的实验分室
		String sql = "select c from LabRoom c where 1=1 and c.labCategory=1";
		sql += " and c.schoolAcademy.academyNumber = '"+ acno +"'";
		// 实验室
		List<LabRoom> labRoomList = labRoomDAO.executeQuery(sql,0,-1);

		return labRoomList;
	}

	/**
	 * Description 判断设置是否需要审核
	 * @param businessType
	 * @return
	 * @author 陈乐为 2019-1-8
	 */
	@Override
	public boolean getAuditOrNot(String businessType) {
		PConfigDTO pConfigDTO = this.getCurrentDataSourceConfiguration();
		String status = "";
		Map<String, String> params = new HashMap<>();
		params.put("projectName", pConfigDTO.getPROJECT_NAME());
		params.put("businessConfigItem", businessType);
		String str = HttpClientUtil.doPost(pConfigDTO.getAuditServerUrl() + "/configuration/getBusinessConfiguration", params);
		JSONObject jsonObject = JSONObject.parseObject(str);
		if("success".equals(jsonObject.getString("status"))) {
			status = jsonObject.getString("data");
			if (status.equals("\"yes\"")) {
				return true;
			} else return false;
		}else{
			return false;
		}
	}

	/**
	 * Description 根据权限名查找对象
	 * @param authName
	 * @return
	 */
	@Override
	public Authority getAuthorityByName(String authName) {
		Authority authority = authorityDAO.findAuthorityByName(authName);
		return authority;
	}

    /***********************************************************************************************
     * @功能：判断时间字符串所属的节次
     * @作者：贺子龙
     * @日期：2016-05-18
     ***********************************************************************************************/
    public int getSystemTimeByString(String timeString){
        int systemTimeId = 0;
        /*LabCenter center = labCenterDAO.findLabCenterByPrimaryKey(cid);
        String campusNumber = center.getSystemCampus().getCampusNumber();*/

        Calendar time = DateUtil.strToCal(timeString, "HH:mm");
        // 建立查询
        String sql = "select s from SystemTime s where 1=1 ";
        //sql+=" and s.systemCampus.campusNumber like '"+campusNumber+"'";// 确定校区
        sql+=" order by s.startDate asc";
        // 遍历所有的学期，并按学期开始时间，正序排列
        List<SystemTime> timeList = systemTimeDAO.executeQuery(sql);
        SystemTime theSystemTime=new SystemTime();
        for (SystemTime systemTime : timeList) {
            // 获取学期起始时间
            Calendar start=systemTime.getStartDate();
            // 获取学期结束时间
            Calendar end=systemTime.getEndDate();

            if(time.after(start)&&time.before(end)){// 当前日期正好属于某一个学期
                theSystemTime = systemTime;
                break;
            }else if (time.before(start)) {// 寒暑假，没有已经建立新学期时，按新学期
                theSystemTime = systemTime;
                break;
            }else if(time.after(end)){// 寒暑假，没有建新学期时，按老学期
                theSystemTime = systemTime;
            }
        }

        /*if (campusNumber.equals("1")) {
            systemTimeId = theSystemTime.getId()-13;
        }else {
            systemTimeId = theSystemTime.getId();
        }*/
		systemTimeId = theSystemTime.getSection();

        return systemTimeId;
    }

    /***********************************************************************************************
     * @功能：判断时间字符串所属的周次
     * @作者：贺子龙
     * @日期：2016-05-18
     ***********************************************************************************************/
    public SchoolWeek getSchoolWeekByString(String timeString){
        String sql = "select s from SchoolWeek s where 1=1";
        if (timeString!=null && !timeString.equals("")) {
            sql+=" and s.date between '"+timeString+"' and '"+timeString+"'";
        }
        List<SchoolWeek> weekList = schoolWeekDAO.executeQuery(sql);
        if(weekList!=null && weekList.size()>0) {
            return weekList.get(0);
        }else {
            return null;
        }
    }
	/***********************************************************************************************
	 * @功能：根据权限id查询当前学院的用户
	 * @作者：徐明杭
	 * @时间: 2019-3-20
	 ***********************************************************************************************/
	@Override
	public List<User> findUsersByAuthorityNameAndAcno(String authorityName,String acno){
		// TODO Auto-generated method stub
		String sql="select u from User u join u.authorities a where a.authorityName like '"+authorityName+"'";
		sql += " and u.schoolAcademy="+acno;
		return userDAO.executeQuery(sql,0,-1);
	}

	/**
	 * Description 获取额外配置项
	 * @param businessType
	 * @return
	 * @author 黄保钱 2019-5-11
	 */
	@Override
	public boolean getExtendItem(String businessType) {
//		StringBuffer hql = new StringBuffer("select * from audit_setting c where c.type='"+ businessType +"'");
//		Query query = entityManager.createNativeQuery(hql.toString());
//		List<Object[]> queryHQLs = new ArrayList<Object[]>(query.getResultList());
//		Object[] obj = queryHQLs.get(0);
//		if(obj[3].equals(true)) {
		PConfigDTO pConfigDTO = this.getCurrentDataSourceConfiguration();
		String status = "";
		Map<String, String> params = new HashMap<>();
		params.put("projectName", pConfigDTO.PROJECT_NAME);
		params.put("businessConfigItemExtend", businessType);
		String str = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "/configuration/getBusinessConfigurationExtend", params);
		JSONObject jsonObject = JSONObject.parseObject(str);
		if("success".equals(jsonObject.getString("status"))) {
			status = jsonObject.getString("data");
			if (status.equals("\"1\"")) {
				return true;
			} else return false;
		}else{
			return false;
		}
	}

	/**
	 * Description 保存业务流水单
	 * @param businessAppUid
	 * @param businessType
	 * @return
	 * @throws Exception
	 * @author 陈乐为 2019年5月27日
	 */
	@Override
	public String saveAuditSerialNumbers(@RequestParam String businessAppUid, String businessType) {
		String uuid = UUID.randomUUID().toString();
		Query query = entityManager.createNativeQuery("call proc_audit_serial_number('"+uuid+"','"+businessAppUid+"','"+businessType+"',true)");
		// 获取list对象
		List<Object> list = query.getResultList();
		String str = "noSerial";
		for (Object obj : list) {
			str = obj.toString();
			break;
		}
		return str;
	}

	/**
	 * Description 查询业务的当前流水单
	 * @param businessAppUid
	 * @param businessType
	 * @return
	 * @author 陈乐为 2019年5月27日
	 */
	public String getSerialNumber(@RequestParam String businessAppUid, String businessType) {
		String sql = "select uuid from audit_serial_number where business_id='"+businessAppUid+"' and business_type='"+businessType+"' and enable=1";
		Query query = entityManager.createNativeQuery(sql);
		List<Object> list = query.getResultList();
		String str = "fail";
		for (Object obj : list) {
			str = obj.toString();
			break;
		}
		return str;
	}
    /**
     * Description 根据当前流水单反查预约id
     * @param uuid
     * @param businessType
     * @return
     * @author Hezhaoyi 2019年6月14日
     */
    public String getReservationIdBySerialNumber(@RequestParam String uuid, String businessType) {
        String sql = "select business_id from audit_serial_number where uuid='"+uuid+"' and business_type='"+businessType+"' and enable=1";
        Query query = entityManager.createNativeQuery(sql);
        List<Object> list = query.getResultList();
        String str = "fail";
        for (Object obj : list) {
            str = obj.toString();
            break;
        }
        return str;
    }

	/**
	 * Description 删除流水单
	 * @param uuid
	 * @author 陈乐为 2019年5月28日
	 */
	public void deleteSerialNumber(@RequestParam String uuid) {
		StringBuffer hql1 = new StringBuffer("delete from audit_serial_number where uuid='"+uuid+"'");
		entityManager.createNativeQuery(hql1.toString()).executeUpdate();
	}

	/**
	 * Description 判断当前登录人是否有远程开门权限
	 * @param lab_id
	 * @return
	 * @author 陈乐为 2019年6月17日
	 */
	public boolean getAuthToOpenDoor(Integer lab_id) {
		User user = this.getUserDetail();
		String sql = "select * from lab_room_admin where lab_room_id="+lab_id;
		Query query = entityManager.createNativeQuery(sql);
		List<Object[]> objects = query.getResultList();

		Boolean str = false;
		for (Object[] obj : objects) {
			if (obj[2]!=null && obj[2].toString().equals(user.getUsername())) {
				if (obj[3]!=null && (obj[3].toString().equals("1") || obj[3].toString().equals("2"))) {//实验室&物联管理员
					str = true;
					break;
				} else if (obj[3]!=null && obj[3].toString().equals("3")) {//授权人员
					if (obj[4]!=null && obj[5]!=null && obj[4].toString()!=null && obj[5].toString()!=null) {//timetableGroup.getTimetableBatch().getStartDate().getTime().before(new Date())
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
						try {
							Date startDate = sdf.parse(obj[4].toString());
							Date endDate = sdf.parse(obj[5].toString());
							if (startDate.before(new Date()) && endDate.after(new Date())) {
								Date startTime = formatter.parse(obj[6].toString());
								Date endTime = formatter.parse(obj[7].toString());
								String nowStr = formatter.format(new Date());
								Date nowTime = formatter.parse(nowStr);
								if (startTime.before(nowTime) && endTime.after(nowTime)) {
									str = true;
									break;
								}
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return str;
	}

	/**
	 * Description 判断业务日期是否为当天
	 * @param app_date
	 * @return
	 * @author 陈乐为 2019-6-20
	 */
	public boolean theSameDay(Date app_date) {
		Boolean bln = false;
		Calendar calendar = Calendar.getInstance();
		// 把当前时间的时、分、秒、毫秒置成零，则为当前日期
		calendar.set(Calendar.MILLISECOND,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		// 如果当前日期和预约日期相同即同一天，则向物联发送刷新权限请求
		if(calendar.getTime().equals(app_date)) {
			bln = true;
		}
		return bln;
	}

	/**
	 * Description 从班级中获取可用学年
	 * @return
	 * @author 陈乐为
	 */
	public List<SchoolClasses> getYearCode() {
		String sql = "select st from SchoolClasses st group by st.classGrade order by st.classGrade";

		return schoolClassesDAO.executeQuery(sql);
	}

	/**
	 * Description 获取当前数据源配置文件
	 * @param
	 * @return
	 * @author lay
	 */
	@Override
	public PConfigDTO getCurrentDataSourceConfiguration() {
		PConfigDTO pConfigDTO = new PConfigDTO();
		Cookie cookie = CookieUtil.getCookieByName("dataResource");
		if (cookie!=null) {
			try {
				String data = cookie.getValue();
				Properties p = new Properties();
				//非实时动态获取l
				//p.load(new InputStreamReader(this.class.getClassLoader().getResourceAsStream(fiePath), "UTF-8"));
				//下面为动态获取
				String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
				InputStream is = new FileInputStream(path + File.separator + "/config"+"/"+data+"/basic-config.properties");
				p.load(is);
				pConfigDTO.setPROJECT_NAME(p.getProperty("project.name"));
				pConfigDTO.setAnnexManage(p.getProperty("annexManage"));
				pConfigDTO.setSoftManage(p.getProperty("softManage"));
				pConfigDTO.setBaseManage(p.getProperty("baseManage"));
				pConfigDTO.setJobReservation(p.getProperty("jobReservation"));
				pConfigDTO.setDeviceLend(p.getProperty("deviceLend"));
				pConfigDTO.setStationNum(p.getProperty("stationNum"));
				pConfigDTO.setAuditServerUrl(p.getProperty("auditServerUrl"));
				pConfigDTO.setSchoolCode(p.getProperty("school.code"));
				pConfigDTO.setSiteEnName(p.getProperty("siteEnName"));
				pConfigDTO.setSiteSecret(p.getProperty("siteSecret"));
				pConfigDTO.setApiGateWayHost(p.getProperty("apiGateWayHost"));
				pConfigDTO.setZuulServerUrl(p.getProperty("zuulServerUrl"));
				pConfigDTO.setAuthTimetableType(p.getProperty("authTimetableType"));
				pConfigDTO.setProfessorIntroductionUrl(p.getProperty("professorIntroductionUrl"));
				pConfigDTO.setCmsSiteUrl(p.getProperty("cmsSiteUrl"));
				pConfigDTO.setCmsUrl(p.getProperty("cmsUrl"));
				pConfigDTO.setBackToCms(p.getProperty("backToCms"));
				pConfigDTO.setCmsAccess(p.getProperty("cmsAccess"));
				pConfigDTO.setCms(p.getProperty("cms"));
				pConfigDTO.setSchoolName(p.getProperty("school.name"));
				pConfigDTO.setUserOperation(p.getProperty("userOperation"));
				pConfigDTO.setShowroom(p.getProperty("showroom"));
				pConfigDTO.setYuntai(p.getProperty("yuntai"));
				pConfigDTO.setLabAddAdim(p.getProperty("labAddAdim"));
				pConfigDTO.setEduDirect(p.getProperty("eduDirect"));
				pConfigDTO.setEduAjust(p.getProperty("eduAjust"));
				pConfigDTO.setEduBatch(p.getProperty("eduBatch"));
				pConfigDTO.setEduNoBatch(p.getProperty("eduNoBatch"));
				pConfigDTO.setSelfBatch(p.getProperty("selfBatch"));
				pConfigDTO.setSelfNoBatch(p.getProperty("selfNoBatch"));
				pConfigDTO.setNoREC(p.getProperty("noREC"));
				pConfigDTO.setDirectTimetable(p.getProperty("directTimetable"));
				pConfigDTO.setOperationItemName(p.getProperty("operationItemName"));
				pConfigDTO.setRefuseTitle(p.getProperty("refuseTitle"));
				pConfigDTO.setSelfRefuseTitle(p.getProperty("selfRefuseTitle"));
				pConfigDTO.setNewServer(p.getProperty("newServer"));
				pConfigDTO.setVirtual(p.getProperty("virtual"));
				pConfigDTO.setLimsUrl(p.getProperty("limsUrl"));
				pConfigDTO.setAdvanceCancelTime(p.getProperty("advanceCancelTime"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			pConfigDTO.setPROJECT_NAME(pConfig.PROJECT_NAME);
			pConfigDTO.setAnnexManage(pConfig.annexManage);
			pConfigDTO.setSoftManage(pConfig.softManage);
			pConfigDTO.setBaseManage(pConfig.baseManage);
			pConfigDTO.setJobReservation(pConfig.jobReservation);
			pConfigDTO.setDeviceLend(pConfig.deviceLend);
			pConfigDTO.setStationNum(pConfig.stationNum);
			pConfigDTO.setAuditServerUrl(pConfig.auditServerUrl);
			pConfigDTO.setSchoolCode(pConfig.schoolCode);
			pConfigDTO.setSiteEnName(pConfig.siteEnName);
			pConfigDTO.setSiteSecret(pConfig.siteSecret);
			pConfigDTO.setApiGateWayHost(pConfig.apiGateWayHost);
			pConfigDTO.setZuulServerUrl(pConfig.zuulServerUrl);
			pConfigDTO.setAuthTimetableType(pConfig.authTimetableType);
			pConfigDTO.setProfessorIntroductionUrl(pConfig.professorIntroductionUrl);
			pConfigDTO.setCmsSiteUrl(pConfig.cmsSiteUrl);
			pConfigDTO.setCmsUrl(pConfig.cmsUrl);
			pConfigDTO.setBackToCms(pConfig.backToCms);
			pConfigDTO.setCmsAccess(pConfig.cmsAccess);
			pConfigDTO.setCms(pConfig.cms);
			pConfigDTO.setSchoolName(pConfig.schoolName);
			pConfigDTO.setUserOperation(pConfig.userOperation);
			pConfigDTO.setShowroom(pConfig.showroom);
			pConfigDTO.setYuntai(pConfig.yuntai);
			pConfigDTO.setLabAddAdim(pConfig.labAddAdim);
			pConfigDTO.setEduDirect(pConfig.eduDirect);
			pConfigDTO.setEduAjust(pConfig.eduAjust);
			pConfigDTO.setEduBatch(pConfig.eduBatch);
			pConfigDTO.setEduNoBatch(pConfig.eduNoBatch);
			pConfigDTO.setSelfBatch(pConfig.selfBatch);
			pConfigDTO.setSelfNoBatch(pConfig.selfNoBatch);
			pConfigDTO.setNoREC(pConfig.noREC);
			pConfigDTO.setDirectTimetable(pConfig.directTimetable);
			pConfigDTO.setOperationItemName(pConfig.operationItemName);
			pConfigDTO.setRefuseTitle(pConfig.refuseTitle);
			pConfigDTO.setSelfRefuseTitle(pConfig.selfRefuseTitle);
			pConfigDTO.setNewServer(pConfig.newServer);
			pConfigDTO.setVirtual(pConfig.virtual);
			pConfigDTO.setLimsUrl(pConfig.limsUrl);
			pConfigDTO.setAdvanceCancelTime(pConfig.advanceCancelTime);
		}
		return pConfigDTO;
	}
}