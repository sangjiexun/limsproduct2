package net.zjcclims.service.system;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import excelTools.ExcelUtils;
import excelTools.JsGridReportBase;
import excelTools.TableData;
import net.luxunsh.util.EmptyUtil;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;

import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.vo.QueryParamsVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.Now;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SystemLogService")
public class SystemLogServiceImpl implements SystemLogService {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired private ShareService shareService;
	@Autowired private LabRoomService labRoomService;
	
	
	
	@Autowired private OperationItemDAO operationItemDAO;
	@Autowired private SystemLogDAO systemLogDAO;
	@Autowired private LabCenterDAO labCenterDAO;
	@Autowired private LabAnnexDAO labAnnexDAO;
	@Autowired private SchoolTermDAO schoolTermDAO;
	public SystemLogServiceImpl() {
	}

	/***********************************************************************************************
	 * 功能：保存项目卡片操作的日志
	 * 作者：贺子龙
	 * 日期：2015-12-19
	 * userIp：ip地址   tag：子模块标志位  0-我的实验项目  1-实验项目管理  2-实验项目导入  id：项目卡的id
	 ***********************************************************************************************/
	@Override
	public void saveOperationItemLog(String userIp, int tag, int action, int id){
		//获取当前登录用户
		User user=shareService.getUser();
		//用户详情：姓名+工号
		String userDetail=user.getCname()+"["+user.getUsername()+"]";
		//用户所在学院
		String userAcademy="";
		if (user.getSchoolAcademy()!=null&&!user.getSchoolAcademy().getAcademyNumber().equals("")) {
			userAcademy=user.getSchoolAcademy().getAcademyNumber();
		}
		//所属子模块
		String childModule="";
		switch (tag) 
		{
		    case 1: childModule="我的项目";break;
		    case 2: childModule="我的审核";break;
		    case 3: childModule="全部项目";break;
			case 4: childModule="新建大纲";break;
		}
		//操作动作
		String operationAction="";
		switch (action)//动作: 0 新建 1 编辑 2 查看 3 删除 4 提交 5 审核查看 6 保存 7 审核编辑后保存 8 导入 9 审核结果
		{
		    case 0: operationAction="新建";break;
		    case 1: operationAction="编辑";break;
		    case 2: operationAction="查看";break;
		    case 3: operationAction="删除";break;
		    case 4: operationAction="提交";break;
		    case 5: operationAction="审核查看";break;
		    case 6: operationAction="保存";break;
		    case 7: operationAction="审核编辑后保存";break;
		    case 8: operationAction="导入";break;
		    case 9: operationAction="审核结果";break;
		}
		//操作对象详情
		String objectiveDetail="";
		switch (id) 
		{
		    case 0: objectiveDetail=operationAction+"《"+childModule+"》"+"列表";break;//id=0 代表是查看列表页面
		    case -1:objectiveDetail=operationAction+"《"+childModule+"》";break;//id=-1 代表新建
		    default: {
		    	OperationItem operationItem=operationItemDAO.findOperationItemByPrimaryKey(id);
		    	if (operationItem!=null&&!operationItem.getLpName().equals("")) {
		    		objectiveDetail=operationAction+"——名称：《"+operationItem.getLpName()+"》";
		    		if(operationItem.getUserByLpCreateUser()!=null&&!operationItem.getUserByLpCreateUser().equals("")){
		    			objectiveDetail+="(创建者："+operationItem.getUserByLpCreateUser().getCname()+")";
		    		}
				}
		    }
		}
		//新建一个系统日志
		SystemLog log=new SystemLog();
		log.setUserDetail(userDetail);
		log.setUserAcademy(userAcademy);
		log.setUserIp(userIp);
		log.setCalendarTime(Calendar.getInstance());
		log.setSuperModule("课程实验");
		log.setChildModule(childModule);
		log.setObjectiveDetail(objectiveDetail);
		log.setOperationAction(action);
		systemLogDAO.store(log);
		systemLogDAO.flush();
	}
	/***********************************************************************************************
	 * 功能：查询系统日志
	 * 作者：贺子龙
	 * 日期：2015-12-19
	 ***********************************************************************************************/
	public List<SystemLog> findSystemLogs(SystemLog systemLog, String acno,int currpage,int pageSize,HttpServletRequest request){
		
		String academyNumber="";
        // 如果没有获取有效的实验分室列表-根据登录用户的所属学院
		SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
        if (academy!=null && academy.getAcademyNumber()!=null) {
    		//获取选择的实验中心
        	academyNumber = academy.getAcademyNumber();
        }else{
        	if(shareService.getUserDetail().getSchoolAcademy()!=null&&
					shareService.getUserDetail().getSchoolAcademy().getAcademyName()!=null) {
				academyNumber = shareService.getUserDetail().getSchoolAcademy().getAcademyNumber();
			}
        }
        String starttime= request.getParameter("starttime");
    	String endtime=	request.getParameter("endtime");
    	
		String sql="select s from SystemLog s where 1=1";
		// 系统时间向上推三个月
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date date = new Date(System.currentTimeMillis());
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -3);
		date = calendar.getTime();
		
		if(starttime!=null && starttime.length()>0 && endtime!=null&& endtime.length()>0){
			sql+=" and s.calendarTime between '"+starttime +"' and '"+endtime+"' ";
		}else { // 缩短响应时间，默认显示过去三个月的日志
			sql += " and s.calendarTime between '"+ format.format(date) +"' and '"+ format.format(new Date()) +"'";
		}
		if (!academyNumber.equals("")) {
			sql+=" and s.userAcademy like '%"+academyNumber+"%'";
		}
		if (systemLog.getSuperModule()!=null&&!systemLog.getSuperModule().equals("")) {
			sql+=" and s.superModule like '%"+systemLog.getSuperModule()+"%'";
		}
		if (systemLog.getUserDetail()!=null&&!systemLog.getUserDetail().equals("")) {
			sql+=" and s.userDetail like '%"+systemLog.getUserDetail()+"%'";
		}
		if (systemLog.getOperationAction()!=null&&!systemLog.getOperationAction().equals("")) {
			sql+=" and s.operationAction like '%"+systemLog.getOperationAction()+"%'";
		}
		sql+=" order by s.calendarTime desc";
		//System.out.println(sql+"'''");
		return systemLogDAO.executeQuery(sql, (currpage-1)*pageSize, pageSize);
		
	}
	/**********************************
	 * 功能：查询systemLog表中的用户详细字段
	 * 作者：贺子龙
	 * 时间：2015-12-19
	 *********************************/
	public Map<String, String> getUserMap(String acno){
		String academyNumber="";
        // 如果没有获取有效的实验分室列表-根据登录用户的所属学院
		SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
        if (academy!=null && academy.getAcademyNumber()!=null) {
    		//获取选择的实验中心
        	academyNumber = academy.getAcademyNumber();
        }else{
        	if(shareService.getUserDetail().getSchoolAcademy()!=null&&shareService.getUserDetail().getSchoolAcademy().getAcademyNumber()!=null) {
				academyNumber = shareService.getUserDetail().getSchoolAcademy().getAcademyNumber();
			}
        }
		Map<String, String> map=new HashMap<String, String>();
		String sql="select s from SystemLog s where 1=1";
		if (!academyNumber.equals("")) {
			sql+=" and s.userAcademy like '%"+academyNumber+"%'";
		}
		List<SystemLog>   list=systemLogDAO.executeQuery(sql,0,-1);
		if(list.size()>0){
			for(SystemLog sl:list){
				map.put(sl.getUserDetail(),sl.getUserDetail());
			}
		}
		return map;
	}
	/**********************************
	 * 功能：删除系统日志
	 * 作者：贺子龙
	 * 时间：2015-12-22
	 *********************************/
	public void deleteSystemLog(String logIds){
		String[] ids = logIds.split(",");
		for (String string : ids) {
			SystemLog log=systemLogDAO.findSystemLogByPrimaryKey(Integer.parseInt(string));
			if(log != null)
			{
				systemLogDAO.remove(log);
				systemLogDAO.flush();
			}
		}
	}
	/***********************************************************************************************
	 * 功能：查询计划内实训室使用统计日志
	 * 作者：周志辉
	 * 日期：2017-9-22
	 ***********************************************************************************************/
	@Override
	public List<Object[]> findAllLabRoomUsePlan(int currpage, int pageSize,
			HttpServletRequest request) {
		// 建立查询
		StringBuffer queryHQL = new StringBuffer("select * from view_training_room_use_plan c where 1=1 ");

		// 按实验室名称查询
		if (!EmptyUtil.isStringEmpty(request.getParameter("roomName")) && !request.getParameter("roomName").equals("")) {
			queryHQL.append(" AND c.roomName like '%" + request.getParameter("roomName") + "%'");
		}
		// 按学期查询
		if(!EmptyUtil.isStringEmpty(request.getParameter("termId")) && !"-1".equals(request.getParameter("termId"))) {
			queryHQL.append(" AND c.termId = " + Integer.valueOf(request.getParameter("termId")));
		}
		// 执行查询
		javax.persistence.Query queryList = entityManager.createNativeQuery(queryHQL.toString());
		// 以下两行是分页设置
		queryList.setMaxResults(pageSize);
		queryList.setFirstResult((currpage-1)*pageSize);
		// 返回结果
		List<Object[]> queryHQLs = new ArrayList<Object[]>(queryList.getResultList());
		return queryHQLs;
	}
	/***********************************************************************************************
	 * 功能：查询计划内实训室使用统计日志
	 * 作者：周志辉
	 * 日期：2017-9-22
	 ***********************************************************************************************/
	@Override
	public Integer allLabRoomUsePlanCount(int currpage, int pageSize,
			HttpServletRequest request) {
		// 建立查询
		StringBuffer queryHQL = new StringBuffer("select count(*) from view_training_room_use_plan c where 1=1 ");

		// 按实验室名称查询
		if (!EmptyUtil.isStringEmpty(request.getParameter("roomName")) && !request.getParameter("roomName").equals("")) {
			queryHQL.append(" AND c.roomName like '%" + request.getParameter("roomName") + "%'");
		}
		// 按学期查询
		if(!EmptyUtil.isStringEmpty(request.getParameter("termId")) && !"-1".equals(request.getParameter("termId"))) {
			queryHQL.append(" AND c.termId = " + Integer.valueOf(request.getParameter("termId")));
		}
		javax.persistence.Query query = entityManager.createNativeQuery(queryHQL.toString());
        // 获取对象条数
        int count= ((BigInteger) query.getSingleResult()).intValue();
		return count;
	}
	/***********************************************************************************************
	 * 功能：实训室课时课次使用统计表个数
	 * 作者：周志辉
	 * 日期：2017-9-25
	 ***********************************************************************************************/
	@Override
	public Integer allLabRoomCourseCount(int currpage, int pageSize,
			HttpServletRequest request) {
			String sql= "select count(*) from _lab_room_course c where 1=1 ";
		// 按实验室名称查询
				if (!EmptyUtil.isStringEmpty(request.getParameter("roomName")) && !request.getParameter("roomName").equals("")) {
					sql+=" AND c.roomName like '%" + request.getParameter("roomName") + "%'";
				}
				javax.persistence.Query query = entityManager.createNativeQuery(sql);
		        // 获取对象条数
		        int count= ((BigInteger) query.getSingleResult()).intValue();
				return count;
	}
	/***********************************************************************************************
	 * 功能：实训室课时课次使用统计表
	 * 作者：周志辉
	 * 日期：2017-9-25
	 ***********************************************************************************************/
	@Override
	public List<Object[]> findLabRoomCourseCount(int currpage, int pageSize,
			HttpServletRequest request) {
		// 建立查询
		StringBuffer queryHQL = new StringBuffer("select * from _lab_room_course c where 1=1 ");

		// 按实验室名称查询
		if (!EmptyUtil.isStringEmpty(request.getParameter("roomName")) && !request.getParameter("roomName").equals("")) {
			queryHQL.append(" AND c.roomName like '%" + request.getParameter("roomName") + "%'");
		}
		// 执行查询
		javax.persistence.Query queryList = entityManager.createNativeQuery(queryHQL.toString());
		// 以下两行是分页设置
		queryList.setMaxResults(pageSize);
		queryList.setFirstResult((currpage-1)*pageSize);
		// 返回结果
		List<Object[]> queryHQLs = new ArrayList<Object[]>(queryList.getResultList());
		return queryHQLs;
	}
	/***********************************************************************************************
	 * 功能：年度使用绩效评价表个数
	 * 作者：周志辉
	 * 日期：2017-9-25
	 ***********************************************************************************************/
	@Override
	public Integer allUsePerformanceEvaluation(int currpage, int pageSize,
			HttpServletRequest request) {
		String sql= "select count(*) from view_use_performance_evaluation c where 1=1 ";
		// 按实验室名称查询
		if (!EmptyUtil.isStringEmpty(request.getParameter("roomName"))
				&& !request.getParameter("roomName").equals("")) {
			sql += " AND c.roomName like '%" + request.getParameter("roomName")
					+ "%'";
		}
		javax.persistence.Query query = entityManager.createNativeQuery(sql);
		// 获取对象条数
		int count = ((BigInteger) query.getSingleResult()).intValue();
		return count;
	}
	/***********************************************************************************************
	 * 功能：年度使用绩效评价表
	 * 作者：周志辉
	 * 日期：2017-9-25
	 ***********************************************************************************************/
	@Override
	public List<Object[]> findUsePerformanceEvaluation(int currpage,
			int pageSize, HttpServletRequest request) {
		// 建立查询
		StringBuffer queryHQL = new StringBuffer(
				"select * from view_use_performance_evaluation c where 1=1 ");

		// 按实验室名称查询
		if (!EmptyUtil.isStringEmpty(request.getParameter("roomName"))
				&& !request.getParameter("roomName").equals("")) {
			queryHQL.append(" AND c.roomName like '%"
					+ request.getParameter("roomName") + "%'");
		}
		// 执行查询
		javax.persistence.Query queryList = entityManager
				.createNativeQuery(queryHQL.toString());
		// 以下两行是分页设置
		queryList.setMaxResults(pageSize);
		queryList.setFirstResult((currpage - 1) * pageSize);
		// 返回结果
		List<Object[]> queryHQLs = new ArrayList<Object[]>(
				queryList.getResultList());
		return queryHQLs;
	}

	/**
	 * Description 查询计划外实训室使用统计日志
	 * @param paramsVO
	 * @return
	 * @author 陈乐为 2019年4月17日
	 */
	@Override
	public List<Object[]> findAllLabRoomUseUnplan(QueryParamsVO paramsVO) {
		// 建立查询
		StringBuffer queryHQL = new StringBuffer("select c.lab_name,c.lab_name,c.term_name,SUM(c.user_num),SUM(c.course_num),SUM(c.course_hour_num),SUM(c.user_hour_num),c.lab_id " +
				"from report_plan_lab_rate c where 1=1");
		if(paramsVO.getType() != 0) {
			queryHQL.append(" and c.flag = " + paramsVO.getType());
		}
		// 按实验室名称/地址/使用对象/用途查询
		String roomName = paramsVO.getQuery_params();
		if (!EmptyUtil.isStringEmpty(roomName) && !roomName.equals("")) {
			queryHQL.append(" AND (c.lab_name like '%" + roomName + "%' OR c.lab_address like '%" + roomName + "%' OR c.use_obj like '%" + roomName + "%')");
		}
		// 按学期查询
		int term_id = paramsVO.getTerm_id();
		if (term_id != -1) {
			queryHQL.append(" AND c.term_id = " + term_id );
		}
		// 按照中心统计
		if (paramsVO.getCenter_id() > 0) {
			queryHQL.append(" and c.center_id="+ paramsVO.getCenter_id());
			queryHQL.append(" group by c.center_id");
		}else if (paramsVO.getBase_id() > 0) {// 按照基地统计
			queryHQL.append(" and c.base_id="+ paramsVO.getBase_id());
			queryHQL.append(" group by c.base_id");
		}else {
			queryHQL.append(" GROUP BY c.lab_id");
		}
		queryHQL.append(" order by c.lab_name");

		// 执行查询
		javax.persistence.Query queryList = entityManager.createNativeQuery(queryHQL.toString());
		// 以下两行是分页设置
		queryList.setMaxResults(paramsVO.getPage_size());
		queryList.setFirstResult((paramsVO.getCurr_page() - 1) * paramsVO.getPage_size());
		// 查询结果
		List<Object[]> queryHQLs = new ArrayList<Object[]>(queryList.getResultList());
		// 计划外-管理员需要重新获取
		List<Object[]> list = new ArrayList<>();
		if (paramsVO.getCenter_id() > 0) {
			for(Object[] obj : queryHQLs) {
				LabCenter labCenter = labCenterDAO.findLabCenterByPrimaryKey(paramsVO.getCenter_id());
				obj[0] = labCenter.getCenterName();
				obj[1] = labCenter.getUserByCenterManager().getCname();
				list.add(obj);
			}
		}else if (paramsVO.getBase_id() > 0) {
			for(Object[] obj : queryHQLs) {
				LabAnnex labAnnex = labAnnexDAO.findLabAnnexByPrimaryKey(paramsVO.getBase_id());
				obj[0] = labAnnex.getLabName();
				obj[1] = "";//labAnnex.getLabCenter().getUserByCenterManager().getCname()
				list.add(obj);
			}
		}else {
			for(Object[] obj : queryHQLs) {
				LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(Integer.valueOf(obj[7].toString()));
				String labAdmin = "";
				for(LabRoomAdmin admin : labRoom.getLabRoomAdmins()) {
					if(admin.getTypeId()==1) {
						labAdmin += admin.getUser().getCname() + " ";
					}
				}
				obj[1] = labAdmin;
				list.add(obj);
			}
		}
		return list;
	}
	/**
	 * Description 查询计划外实训室使用统计个数
	 * @param paramsVO
	 * @return
	 * @author 陈乐为 2019年4月17日
	 */
	@Override
	public Integer allLabRoomUseUnplanCount(QueryParamsVO paramsVO) {
		// 建立查询
		StringBuffer queryHQL = new StringBuffer("select count(distinct c.lab_id) from report_plan_lab_rate c where 1=1");
		// 按照中心统计
		if (paramsVO.getCenter_id() > 0) {
			queryHQL = new StringBuffer("select count(distinct c.center_id) from report_plan_lab_rate c where 1=1");
			queryHQL.append(" and c.center_id="+ paramsVO.getCenter_id());
		}else if (paramsVO.getBase_id() > 0) {// 按照基地统计
			queryHQL = new StringBuffer("select count(distinct c.base_id) from report_plan_lab_rate c where 1=1");
			queryHQL.append(" and c.base_id="+ paramsVO.getCenter_id());
		}

		if(paramsVO.getType() != 0) {
			queryHQL.append(" and c.flag = " + paramsVO.getType());
		}
		// 按实验室名称/地址/使用对象/用途查询
		String roomName = paramsVO.getQuery_params();
		if (!EmptyUtil.isStringEmpty(roomName) && !roomName.equals("")) {
			queryHQL.append(" AND (c.lab_name like '%" + roomName + "%' OR c.lab_address like '%" + roomName + "%' OR c.use_obj like '%" + roomName + "%')");
		}
		// 按学期查询
		int term_id = paramsVO.getTerm_id();
		if (term_id != -1) {
			queryHQL.append(" AND c.term_id = " + term_id );
		}
//		queryHQL.append(" GROUP BY c.lab_id");
		javax.persistence.Query query = entityManager.createNativeQuery(queryHQL.toString());
		// 获取对象条数
		int count = ((BigInteger) query.getSingleResult()).intValue();
		return count;
	}

    /**
     * Description 开放项目相关报表-实验计划表{导出excel}
     * @param request
     * @param response
     * @throws Exception
     * @author Hezhaoyi 2019-5-17
     */
	@Override
	public void exportListExperimentalSchedule(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Map> list = new ArrayList<Map>();
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term") != null) {
			term = Integer.parseInt(request.getParameter("term"));
		}
        StringBuffer sql = new StringBuffer("select distinct o from OperationItem o order by o.id asc");
        Query query = entityManager.createQuery(sql.toString());
		// 当前页打印条件
        if (request.getParameter("currpage") != null && request.getParameter("pagesize") != null) {
            String currpage = request.getParameter("currpage");
            int pagesize = Integer.valueOf(request.getParameter("pagesize"));
            query.setMaxResults(pagesize);
            int firstResult = (Integer.valueOf(currpage)-1) * pagesize;
            query.setFirstResult(firstResult);
        }

        List<OperationItem> operationItemList = query.getResultList();
        int i = 1;
        for(OperationItem operationItem :operationItemList){
            Map map = new HashMap();
            map.put("serial number", i);//序号
            i++;
            map.put("itemName", operationItem.getLpName());//实验内容
            //器材-实验物资
            Set<ItemAssets> itemAssets = operationItem.getItemAssets();
            String Asset = "";
            if(itemAssets.size()!=0){
                for(ItemAssets itemAsset : itemAssets){
                    Asset = Asset +" "+ itemAsset.getAsset().getChName();
                }
            }
            map.put("itemAssets", Asset);//实验物资
            //器材-实验设备
            Set<OperationItemDevice> operationItemDevices = operationItem.getOperationItemDevices();
            String device = "";
            if(operationItemDevices.size()!=0){
                for(OperationItemDevice operationItemDevice : operationItemDevices){
                    device = device +" "+ operationItemDevice.getSchoolDevice().getDeviceName();
                }
            }
            map.put("itemDecvices", device);//实验设备
            if(operationItem.getCDictionaryByLpCategoryApp()!=null){
                map.put("itemCategory", operationItem.getCDictionaryByLpCategoryApp().getCName());//实验类型
            }
            if(operationItem.getPlanWeek()!=null){
                map.put("planTime", operationItem.getPlanWeek());//计划时间
            }
            list.add(map);
        }
		//实验室遍历
		SchoolTerm schoolTerm = schoolTermDAO.findSchoolTermById(term);
		String title = schoolTerm.getTermName()+"实验计划表";
		String[] hearders = new String[]{"序号", "实验内容", "实验物资", "实验设备",
				"实验类型", "计划时间", "备注"};//表头数组
		String[] fields = new String[]{"serial number", "itemName", "itemAssets", "itemDecvices", "itemCategory",
				"planTime", "notes"};
		TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportExcel(title, shareService.getUserDetail().getCname(), schoolTerm.getTermName(), td);
	}
}
