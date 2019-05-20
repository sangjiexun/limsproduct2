package net.zjcclims.web.system;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.system.SystemLogService;

import net.zjcclims.service.virtual.VirtualService;
import net.zjcclims.vo.QueryParamsVO;
import org.python.antlr.op.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import net.gvsun.lims.vo.OpenProjectRelatedReports.*;

/**
 * Spring MVC controller that handles CRUD requests for SystemLog entities
 * 
 */

@Controller("SystemLogController")
@SessionAttributes("selected_academy")
public class SystemLogController {

	@Autowired private SystemLogService systemLogService;
	@Autowired private ShareService shareService;
	//@Autowired SchoolTermService schoolTermService;
	@Autowired private VirtualService virtualService;
	@Autowired private LabCenterDAO labCenterDAO;
	@Autowired private LabAnnexDAO labAnnexDAO;
	@Autowired private AssetDAO assetDAO;
	@Autowired private UserDAO userDAO;
	@Autowired private AssetCabinetDAO assetCabinetDAO;
	@Autowired private OperationItemDAO operationItemDAO;
	@Autowired private TimetableAppointmentDAO timetableAppointmentDAO;
	@Autowired private SchoolWeekDAO schoolWeekDAO;
	@Autowired private AssetReceiveDAO assetReceiveDAO;
	@Autowired private LabRoomDAO labRoomDAO;
    @PersistenceContext
    private EntityManager entityManager;

	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register static property editors.
		binder.registerCustomEditor(java.util.Calendar.class, new org.skyway.spring.util.databinding.CustomCalendarEditor());
		binder.registerCustomEditor(byte[].class, new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
		binder.registerCustomEditor(boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(false));
		binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
		binder.registerCustomEditor(java.math.BigDecimal.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
		binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Integer.class, true));
		binder.registerCustomEditor(java.util.Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
		binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
		binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Long.class, true));
		binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Double.class, true));
	}
	
	
	/***********************************************
	 * 功能：列出系统日志
	 * 作者：贺子龙
	 * 日期：2015-12-21
	 **********************************************/
	@RequestMapping("/log/listOperationLog")
	public ModelAndView listOperationLog(@RequestParam int currpage,@ModelAttribute SystemLog systemLog,
			@ModelAttribute("selected_academy") String acno,HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		int pageSize = CommonConstantInterface.INT_PAGESIZE;
		int totalRecords=systemLogService.findSystemLogs(systemLog,acno,1,-1,request).size();
		List<SystemLog>  operationLogs=systemLogService.findSystemLogs(systemLog,acno,currpage,pageSize,request);
		Map<String, String> userDetailMap=systemLogService.getUserMap(acno);
		mav.addObject("userDetailMap", userDetailMap);
		mav.addObject("operationLogs", operationLogs);
		mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
		mav.addObject("pageSize", pageSize);
		mav.addObject("currpage", currpage);
		mav.setViewName("reports/systemLog/listOperationLog.jsp");
		return mav;
	}
	
	/***********************************************
	 * 功能：删除系统日志
	 * 作者：贺子龙
	 * 日期：2015-12-22
	 **********************************************/
	@RequestMapping("/log/deleteOperationLog")
	public ModelAndView deleteOperationLog(@RequestParam String logIds){
		ModelAndView mav = new ModelAndView();
		systemLogService.deleteSystemLog(logIds);
		mav.setViewName("redirect:/log/listOperationLog?currpage=1");
		return mav;
	}
	/***********************************************
	 * 功能：计划内实训室使用统计
	 * 作者：周志辉
	 * 日期：2017-09-19
	 **********************************************/
	@RequestMapping("/log/listLabRoomUsePlan")
	public ModelAndView listLabRoomUsePlan(@RequestParam int currpage,HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.addObject("roomName",request.getParameter("roomName"));
		mav.addObject("yearCode",request.getParameter("yearCode"));
		mav.addObject("termCode",request.getParameter("termId"));
		
		//学期
		List<SchoolTerm> terms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerms", terms);
		
		// 页面设置
		int pageSize = 20;
		// 课程列表
		int totalRecords = systemLogService.allLabRoomUsePlanCount(currpage, pageSize, request);
		List<Object[]> details = systemLogService.findAllLabRoomUsePlan(currpage,
				pageSize, request);
		Map<String, Integer> pageModel = shareService.getPage(
				currpage, pageSize, totalRecords);
		mav.addObject("pageModel", pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("details",details);
		mav.setViewName("reports/systemLog/listLabRoomUsePlan.jsp");
		return mav;
	}
	/***********************************************
	 * 功能：实训室课时课次使用统计表
	 * 作者：周志辉
	 * 日期：2017-09-25
	 **********************************************/
	@RequestMapping("/log/listLabRoomCourseCount")
	public ModelAndView listLabRoomCourseCount(@RequestParam int currpage,HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.addObject("roomName",request.getParameter("roomName"));
		mav.addObject("roomAdmin",request.getParameter("roomAdmin"));
		mav.addObject("yearCode",request.getParameter("yearCode"));
		mav.addObject("termCode",request.getParameter("termCode"));
		mav.addObject("termId",request.getParameter("termId"));
		mav.addObject("courseCount",request.getParameter("courseCount"));
		mav.addObject("labId",request.getParameter("labId"));
		mav.addObject("courseHour",request.getParameter("courseHour"));
		
		// 页面设置
		int pageSize = 20;
		// 课程列表
		int totalRecords = systemLogService.allLabRoomCourseCount(currpage, pageSize, request);
		List<Object[]> details = systemLogService.findLabRoomCourseCount(currpage,
				pageSize, request);
		Map<String, Integer> pageModel = shareService.getPage(
				currpage, pageSize, totalRecords);
		mav.addObject("pageModel", pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("details",details);
		mav.setViewName("reports/systemLog/listLabRoomCourseCount.jsp");
		return mav;
	}
	/***********************************************
	 * 功能：年度使用绩效评价表
	 * 作者：周志辉
	 * 日期：2017-09-25
	 **********************************************/
	@RequestMapping("/log/listUsePerformanceEvaluation")
	public ModelAndView listUsePerformanceEvaluation(@RequestParam int currpage,HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.addObject("roomName",request.getParameter("roomName"));
		mav.addObject("labType",request.getParameter("labType"));
		mav.addObject("constructionAcademy",request.getParameter("constructionAcademy"));
		mav.addObject("constructionYear",request.getParameter("constructionYear"));
		mav.addObject("termId",request.getParameter("termId"));
		mav.addObject("courseCount",request.getParameter("courseCount"));
		mav.addObject("labId",request.getParameter("labId"));
		mav.addObject("courseHour",request.getParameter("courseHour"));
		
		// 页面设置
		int pageSize = 20;
		// 课程列表
		int totalRecords = systemLogService.allUsePerformanceEvaluation(currpage, pageSize, request);
		List<Object[]> details = systemLogService.findUsePerformanceEvaluation(currpage,
				pageSize, request);
		Map<String, Integer> pageModel = shareService.getPage(
				currpage, pageSize, totalRecords);
		mav.addObject("pageModel", pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("details",details);
		mav.setViewName("reports/systemLog/listUsePerformanceEvaluation.jsp");
		return mav;
	}

	/**
	 * Description 计划外实验室使用统计-列表
	 * @param currpage
	 * @param request
	 * @return
	 * @author 陈乐为 2019年4月17日
	 */
	@RequestMapping("/log/listLabRoomUseUnplan")
	public ModelAndView listLabRoomUseUnplan(@RequestParam int currpage, int type,HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.addObject("roomName",request.getParameter("roomName"));
		int term_id = term_id = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if(request.getParameter("termId") != null && !request.getParameter("termId").equals("")) {
			term_id = Integer.valueOf(request.getParameter("termId"));
		}
		mav.addObject("term", term_id);
		int base_id = 0;
		int center_id = 0;
		if (request.getParameter("base_id")!=null) {
			base_id = Integer.valueOf(request.getParameter("base_id"));
		}
		if (request.getParameter("center_id")!=null) {
			center_id = Integer.valueOf(request.getParameter("center_id"));
		}

		mav.addObject("center_id", center_id);
		mav.addObject("base_id", base_id);

		// 页面设置
		int pageSize = 20;
		// 参数封装
		QueryParamsVO paramsVO = new QueryParamsVO();
		paramsVO.setTerm_id(term_id);
		paramsVO.setQuery_params(request.getParameter("roomName"));
		paramsVO.setType(type);
		paramsVO.setCurr_page(currpage);
		paramsVO.setPage_size(pageSize);
		paramsVO.setBase_id(base_id);
		paramsVO.setCenter_id(center_id);
		// 课程列表
		int totalRecords = systemLogService.allLabRoomUseUnplanCount(paramsVO);
		//学期
		List<SchoolTerm> terms = shareService.findAllSchoolTerms();
		mav.addObject("schoolTerms", terms);
		List<Object[]> details = systemLogService.findAllLabRoomUseUnplan(paramsVO);
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
		mav.addObject("pageModel", pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("details", details);
		mav.addObject("type", type);
		mav.addObject("centers", labCenterDAO.findAllLabCenters());
		mav.addObject("bases", labAnnexDAO.findAllLabAnnexs());

		mav.setViewName("reports/systemLog/listLabRoomUseUnplan.jsp");
		return mav;
	}

	@RequestMapping("reportPlanLabRateInfo")
	public ModelAndView reportPlanLabRateInfo(QueryParamsVO queryParamsVO) {
		ModelAndView mav = new ModelAndView();

		return mav;
	}

	/*************************************************************************************
	 * Description:虚拟镜像使用报表
	 *
	 * @author: 杨新蔚
	 * @date: 2018/12/18
	 *************************************************************************************/
	@RequestMapping(value="/log/listVirtualUse",produces = "application/json;charset=utf-8")
	public ModelAndView listVirtualUse(@RequestParam int currpage,HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		List<Map<String,String>> list=virtualService.virtualHistory();
		mav.addObject("historyList",list);
		// 页面设置
		int pageSize = 20;
		// 课程列表
		int totalRecords =0;
		if(list!=null&list.size()>0){
			totalRecords=list.size();
		}
		Map<String, Integer> pageModel = shareService.getPage(
				currpage, pageSize, totalRecords);
		mav.addObject("pageModel", pageModel);
		mav.addObject("totalRecords", totalRecords);
		mav.setViewName("reports/systemLog/listVirtualUse.jsp");
		return mav;
	}

	/*************************************************************************************
	 * Description:开放项目相关报表--实验计划表
	 *
	 * @author: Hezhaoyi
	 * @date: 2019-5-15
	 *************************************************************************************/
	@RequestMapping(value="/log/listExperimentalSchedule")
	public ModelAndView listExperimentalSchedule(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();

		//每页20条记录
		int pagesize = 20;
		String currpage = request.getParameter("currpage");

        StringBuffer sql = new StringBuffer("select distinct o from OperationItem o order by o.id asc");

        Query query = entityManager.createQuery(sql.toString());
        int totalRecords = query.getResultList().size();
        query.setMaxResults(pagesize);
        int firstResult = (Integer.valueOf(currpage)-1) * pagesize;
        query.setFirstResult(firstResult);
        List<OperationItem> operationItemList = query.getResultList();
        List<ExperimentalScheduleVO> experimentalScheduleVOs = new ArrayList<ExperimentalScheduleVO>();
        int i = 1;
        for(OperationItem operationItem :operationItemList){
            ExperimentalScheduleVO experimentalScheduleVO = new ExperimentalScheduleVO();
            experimentalScheduleVO.setId(i);
            //实验名称
            experimentalScheduleVO.setItemName(operationItem.getLpName());
            //器材-实验物资
            Set<ItemAssets> itemAssets = operationItem.getItemAssets();
            String Asset = "";
            if(itemAssets.size()!=0){
                for(ItemAssets itemAsset : itemAssets){
                    Asset = Asset + itemAsset.getAsset().getChName();
                }
            }
            experimentalScheduleVO.setItemAssets(Asset);
            //器材-实验设备
            Set<OperationItemDevice> operationItemDevices = operationItem.getOperationItemDevices();
            String device = "";
            if(operationItemDevices.size()!=0){
                for(OperationItemDevice operationItemDevice : operationItemDevices){
                    device = device + operationItemDevice.getSchoolDevice().getDeviceName();
                }
            }
            experimentalScheduleVO.setItemDecvices(device);
            //实验类型
            if(operationItem.getCDictionaryByLpCategoryApp()!=null){
                experimentalScheduleVO.setItemCategory(operationItem.getCDictionaryByLpCategoryApp().getCName());
            }
            //计划时间
            if(operationItem.getPlanWeek()!=null){
                experimentalScheduleVO.setPlanTime(operationItem.getPlanWeek());
            }
            experimentalScheduleVOs.add(experimentalScheduleVO);
            i++;
        }
        Map<String, Integer> pageModel = shareService.getPage(Integer.valueOf(currpage), pagesize, totalRecords);
        //总记录数
        mav.addObject("pagesize",pagesize);
        mav.addObject("currpage",currpage);
        mav.addObject("totalRecords",totalRecords);
        mav.addObject("pageModel",pageModel);
        mav.addObject("experimentalScheduleVOs",experimentalScheduleVOs);

		mav.setViewName("reports/systemLog/listExperimentalSchedule.jsp");
		return mav;
	}

    /*************************************************************************************
     * Description:开放项目相关报表--仪器借出登记表/实验开出情况-实验室列表
     *
     * @author: Hezhaoyi
     * @date: 2019-5-15
     *************************************************************************************/
    @RequestMapping(value="/log/listLabRoom")
    public ModelAndView listLabRoom(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //每页20条记录
        int pagesize = 20;
        String currpage = request.getParameter("currpage");
        //页面标记-1仪器借出一级页面2实验开出情况一级页面
        String type = request.getParameter("type");

        StringBuffer sql = new StringBuffer("select distinct l from LabRoom l order by l.id asc");
        Query query = entityManager.createQuery(sql.toString());
        int totalRecords = query.getResultList().size();
        query.setMaxResults(pagesize);
        int firstResult = (Integer.valueOf(currpage)-1) * pagesize;
        query.setFirstResult(firstResult);
        List<LabRoom> labRoomList = query.getResultList();

        Map<String, Integer> pageModel = shareService.getPage(Integer.valueOf(currpage), pagesize, totalRecords);
        //总记录数
        mav.addObject("totalRecords",totalRecords);
        mav.addObject("pageModel",pageModel);
        mav.addObject("labRoomList",labRoomList);
        mav.addObject("type",type);

        mav.setViewName("reports/systemLog/listLabRoom.jsp");
        return mav;
    }

    /*************************************************************************************
     * Description:开放项目相关报表--仪器借出登记表
     *
     * @author: Hezhaoyi
     * @date: 2019-5-15
     *************************************************************************************/
    @RequestMapping(value="/log/listInstrumentLendingegistration")
    public ModelAndView listInstrumentLendingegistration(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        //每页20条记录
        int pagesize = 20;
        String currpage = request.getParameter("currpage");
        int labRoomId = Integer.valueOf(request.getParameter("labRoomId"));

        StringBuffer sql = new StringBuffer("SELECT lrdl FROM LabRoomDeviceLending lrdl,LabRoomDevice lrd");
        sql.append(" WHERE lrdl.labRoomDevice.id = lrd.id AND lrd.labRoom.id = "+labRoomId);
        sql.append(" order by lrdl.id asc");
        Query query = entityManager.createQuery(sql.toString());
        int totalRecords = query.getResultList().size();
        query.setMaxResults(pagesize);
        int firstResult = (Integer.valueOf(currpage)-1) * pagesize;
        query.setFirstResult(firstResult);
        List<LabRoomDeviceLending> labRoomDeviceLendingList = query.getResultList();
		List<InstrumentLendingegistrationVO> instrumentLendingegistrationVOs = new ArrayList<InstrumentLendingegistrationVO>();
		for(LabRoomDeviceLending labRoomDeviceLending :labRoomDeviceLendingList){
            InstrumentLendingegistrationVO instrumentLendingegistrationVO = new InstrumentLendingegistrationVO();
            instrumentLendingegistrationVO.setLendingTime(sdf.format(labRoomDeviceLending.getLendingTime().getTime()));
            instrumentLendingegistrationVO.setDeviceName(labRoomDeviceLending.getLabRoomDevice().getSchoolDevice().getDeviceName());
            instrumentLendingegistrationVO.setNumber("1");
            instrumentLendingegistrationVO.setLendingUser(labRoomDeviceLending.getUserByLendingUser().getCname());
            if(labRoomDeviceLending.getBackTime()!=null){
                instrumentLendingegistrationVO.setBackTime(sdf.format(labRoomDeviceLending.getBackTime().getTime()));
            }
            if(labRoomDeviceLending.getCDictionary()!=null){
				instrumentLendingegistrationVO.setBackStatus(labRoomDeviceLending.getCDictionary().getCName());
			}
            instrumentLendingegistrationVOs.add(instrumentLendingegistrationVO);
        }
        mav.addObject("labRoomId",labRoomId);
        Map<String, Integer> pageModel = shareService.getPage(Integer.valueOf(currpage), pagesize, totalRecords);
        //总记录数
        mav.addObject("totalRecords",totalRecords);
        mav.addObject("pageModel",pageModel);
        mav.addObject("instrumentLendingegistrationVOs",instrumentLendingegistrationVOs);

        mav.setViewName("reports/systemLog/listInstrumentLendingegistration.jsp");
        return mav;
    }

    /*************************************************************************************
     * Description:开放项目相关报表--低值易耗品领用登记单
     *
     * @author: Hezhaoyi
     * @date: 2019-5-15
     *************************************************************************************/
    @RequestMapping(value="/log/listReceiptOfLowValueConsumables")
    public ModelAndView listReceiptOfLowValueConsumables(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //每页20条记录
        int pagesize = 20;
        String currpage = request.getParameter("currpage");

        StringBuffer sql = new StringBuffer("SELECT arr FROM AssetReceiveRecord arr ");
        sql.append(" WHERE arr.assetReceive.status = 4 AND arr.asset.category = 8 order by arr.id asc");

        Query query = entityManager.createQuery(sql.toString());
        int totalRecords = query.getResultList().size();
        query.setMaxResults(pagesize);
        int firstResult = (Integer.valueOf(currpage)-1) * pagesize;
        query.setFirstResult(firstResult);
        List<AssetReceiveRecord> assetReceiveRecordList = query.getResultList();
        List<OutOfStockRecordsVO> outOfStockRecordsVOs = new ArrayList<OutOfStockRecordsVO>();
        for(AssetReceiveRecord assetReceiveRecord : assetReceiveRecordList){
            OutOfStockRecordsVO outOfStockRecordsVO = new OutOfStockRecordsVO();
            outOfStockRecordsVO.setTime(sdf.format(assetReceiveRecord.getAssetReceive().getReceiveDate().getTime()));
            outOfStockRecordsVO.setUsage(assetReceiveRecord.getAssetReceive().getAssetUsage());
            outOfStockRecordsVO.setNameAndSpecifications("名称："+assetReceiveRecord.getAsset().getChName()+ " 规格："+assetReceiveRecord.getAsset().getSpecifications());
            outOfStockRecordsVO.setLendingNum(assetReceiveRecord.getQuantity().toString());
            if(assetReceiveRecord.getReturnQuantity()!=null){
                outOfStockRecordsVO.setReturnNum(assetReceiveRecord.getReturnQuantity().toString());
            }
            outOfStockRecordsVO.setLendingUser(assetReceiveRecord.getAssetReceive().getUser().getCname());
            outOfStockRecordsVOs.add(outOfStockRecordsVO);
        }

        mav.addObject("outOfStockRecordsVOs",outOfStockRecordsVOs);

        Map<String, Integer> pageModel = shareService.getPage(Integer.valueOf(currpage), pagesize, totalRecords);
        //总记录数
        mav.addObject("totalRecords",totalRecords);
        mav.addObject("pageModel",pageModel);

        mav.setViewName("reports/systemLog/listReceiptOfLowValueConsumables.jsp");
        return mav;
    }

    /**
     * Description 开放项目相关报表--药品出库表药品柜列表
     * @param request
     * @return
     * @author Hezhaoyi 2019-5-16
     */
    @RequestMapping(value="/log/listDrugCabinet")
    public ModelAndView listDrugCabinet(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        //每页20条记录
        int pagesize = 20;
        String currpage = request.getParameter("currpage");

        StringBuffer sql = new StringBuffer("select distinct o from AssetCabinet o");

        Query query = entityManager.createQuery(sql.toString());
        int totalRecords = query.getResultList().size();
        query.setMaxResults(pagesize);
        int firstResult = (Integer.valueOf(currpage)-1) * pagesize;
        query.setFirstResult(firstResult);
        List<AssetCabinet> assetCabinetList = query.getResultList();

        Map<String, Integer> pageModel = shareService.getPage(Integer.valueOf(currpage), pagesize, totalRecords);
        mav.addObject("assetCabinetList",assetCabinetList);
        //总记录数
        mav.addObject("totalRecords",totalRecords);
        mav.addObject("pageModel",pageModel);

        mav.setViewName("reports/systemLog/listDrugCabinet.jsp");
        return mav;

    }
    /*************************************************************************************
     * Description:开放项目相关报表--药品出库登记表
     *
     * @author: Hezhaoyi
     * @date: 2019-5-15
     *************************************************************************************/
    @RequestMapping(value="/log/listDrugDepotRegistrationForm")
    public ModelAndView listDrugDepotRegistrationForm(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //每页20条记录
        int pagesize = 20;
        String currpage = request.getParameter("currpage");
        //药品柜编号
        String cabinetId = request.getParameter("cabinetId");

        StringBuffer sql = new StringBuffer("SELECT acar FROM AssetCabinetAccessRecord acar ");
        sql.append(" WHERE acar.type = 'Receive' AND acar.cabinetId =" + cabinetId );
        sql.append(" order by acar.id asc");

        Query query = entityManager.createQuery(sql.toString());
        int totalRecords = query.getResultList().size();
        query.setMaxResults(pagesize);
        int firstResult = (Integer.valueOf(currpage)-1) * pagesize;
        query.setFirstResult(firstResult);
        List<AssetCabinetAccessRecord> assetCabinetAccessRecordList = query.getResultList();
        List<DrugDepotRegistrationFormVO> drugDepotRegistrationFormVOs = new ArrayList<DrugDepotRegistrationFormVO>();
        for(AssetCabinetAccessRecord assetCabinetAccessRecord : assetCabinetAccessRecordList){
            DrugDepotRegistrationFormVO drugDepotRegistrationFormVO = new DrugDepotRegistrationFormVO();
            drugDepotRegistrationFormVO.setTime(sdf.format(assetCabinetAccessRecord.getCreateDate()));
            Asset asset = assetDAO.findAssetByPrimaryKey(assetCabinetAccessRecord.getAssetId());
            drugDepotRegistrationFormVO.setDrugName(asset.getChName());
            drugDepotRegistrationFormVO.setSpecification(asset.getSpecifications());
            drugDepotRegistrationFormVO.setUnit(asset.getUnit());
            drugDepotRegistrationFormVO.setNumber(assetCabinetAccessRecord.getQuantity());
            User user = userDAO.findUserByUsername(assetCabinetAccessRecord.getUsername());
            drugDepotRegistrationFormVO.setUser(user.getCname());
            drugDepotRegistrationFormVOs.add(drugDepotRegistrationFormVO);
        }

        mav.addObject("drugDepotRegistrationFormVOs",drugDepotRegistrationFormVOs);
        mav.addObject("cabinetId",cabinetId);
        Map<String, Integer> pageModel = shareService.getPage(Integer.valueOf(currpage), pagesize, totalRecords);
        //总记录数
        mav.addObject("totalRecords",totalRecords);
        mav.addObject("pageModel",pageModel);

        mav.setViewName("reports/systemLog/listDrugDepotRegistrationForm.jsp");
        return mav;
    }


    /*************************************************************************************
     * Description:开放项目相关报表--耗材领用记录单-耗材物资列表
     *
     * @author: Hezhaoyi
     * @date: 2019-5-15
     *************************************************************************************/
    @RequestMapping(value="/log/listAsset")
    public ModelAndView listAsset(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        //每页20条记录
        int pagesize = 20;
        String currpage = request.getParameter("currpage");

        StringBuffer sql = new StringBuffer("select distinct o from Asset o");

        Query query = entityManager.createQuery(sql.toString());
        int totalRecords = query.getResultList().size();
        query.setMaxResults(pagesize);
        int firstResult = (Integer.valueOf(currpage)-1) * pagesize;
        query.setFirstResult(firstResult);
        List<Asset> assetList = query.getResultList();

        Map<String, Integer> pageModel = shareService.getPage(Integer.valueOf(currpage), pagesize, totalRecords);
        mav.addObject("assetList",assetList);
        //总记录数
        mav.addObject("totalRecords",totalRecords);
        mav.addObject("pageModel",pageModel);

        mav.setViewName("reports/systemLog/listAsset.jsp");
        return mav;
    }

    /*************************************************************************************
     * Description:开放项目相关报表--耗材领用记录单
     *
     * @author: Hezhaoyi
     * @date: 2019-5-15
     *************************************************************************************/
    @RequestMapping(value="/log/listConsumablesAcquisitionRecordSheet")
    public ModelAndView listConsumablesAcquisitionRecordSheet(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //每页20条记录
        int pagesize = 20;
        String currpage = request.getParameter("currpage");
        String assetId = request.getParameter("assetId");
        //物资
        Asset asset = assetDAO.findAssetByPrimaryKey(Integer.valueOf(assetId));

        StringBuffer sql = new StringBuffer("select arr from AssetReceiveRecord arr ,AssetReceive ar");
        sql.append(" where ar.id = arr.assetReceive.id");
        sql.append(" and ar.status = 4 and arr.asset.id = "+ assetId);
        sql.append(" order by arr.id asc");

        Query query = entityManager.createQuery(sql.toString());
        int totalRecords = query.getResultList().size();
        query.setMaxResults(pagesize);
        int firstResult = (Integer.valueOf(currpage)-1) * pagesize;
        query.setFirstResult(firstResult);
        List<AssetReceiveRecord> assetReceiveRecordList = query.getResultList();
        List<OutOfStockRecordsVO> outOfStockRecordsVOs = new ArrayList<OutOfStockRecordsVO>();
        String nameAndSpecifications = "名称："+asset.getChName()+" 规格："+asset.getSpecifications()+" 单位："+asset.getUnit();
        mav.addObject("nameAndSpecifications",nameAndSpecifications);
        for(AssetReceiveRecord assetReceiveRecord : assetReceiveRecordList){
            OutOfStockRecordsVO outOfStockRecordsVO = new OutOfStockRecordsVO();
            outOfStockRecordsVO.setTime(sdf.format(assetReceiveRecord.getAssetReceive().getReceiveDate().getTime()));
            outOfStockRecordsVO.setLendingNum(assetReceiveRecord.getQuantity().toString());
            outOfStockRecordsVO.setUsage(assetReceiveRecord.getAssetReceive().getAssetUsage());
            outOfStockRecordsVO.setLendingUser(assetReceiveRecord.getAssetReceive().getUser().getCname());
            StringBuffer sql1 = new StringBuffer("SELECT acar FROM AssetCabinetAccessRecord acar WHERE acar.appId=" + assetReceiveRecord.getAssetReceive().getId());
            List<AssetCabinetAccessRecord> assetCabinetAccessRecords = entityManager.createQuery(sql1.toString()).getResultList();
            if(assetCabinetAccessRecords.size()!=0){
                AssetCabinetAccessRecord assetCabinetAccessRecord = assetCabinetAccessRecords.get(0);
                outOfStockRecordsVO.setRemainQuantity(assetCabinetAccessRecord.getRemainQuantity());
            }
            outOfStockRecordsVOs.add(outOfStockRecordsVO);
        }

        mav.addObject("outOfStockRecordsVOs",outOfStockRecordsVOs);
        mav.addObject("assetId",assetId);

        Map<String, Integer> pageModel = shareService.getPage(Integer.valueOf(currpage), pagesize, totalRecords);
        //总记录数
        mav.addObject("totalRecords",totalRecords);
        mav.addObject("pageModel",pageModel);

        mav.setViewName("reports/systemLog/listConsumablesAcquisitionRecordSheet.jsp");
        return mav;
    }

    /*************************************************************************************
     * Description:开放项目相关报表--实验通知单-实验列表
     *
     * @author: Hezhaoyi
     * @date: 2019-5-16
     *************************************************************************************/
    @RequestMapping(value="/log/listItem")
    public ModelAndView listItem(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();

        //每页20条记录
        int pagesize = 20;
        String currpage = request.getParameter("currpage");
        String type = request.getParameter("type");


        StringBuffer sql = new StringBuffer("select distinct o from OperationItem o");

        //实验通知单演示性实验查询条件
        if(Integer.valueOf(type) == 6){
            sql.append(" where o.CDictionaryByLpCategoryApp.id = 776");
        }
        //教学记录单分组实验查询条件
        if(Integer.valueOf(type) == 7){
            sql.append(" where o.CDictionaryByLpCategoryApp.id = 777");
        }

        Query query = entityManager.createQuery(sql.toString());
        int totalRecords = query.getResultList().size();
        query.setMaxResults(pagesize);
        int firstResult = (Integer.valueOf(currpage)-1) * pagesize;
        query.setFirstResult(firstResult);
        List<OperationItem> operationItemList = query.getResultList();
        mav.addObject("operationItemList",operationItemList);

        Map<String, Integer> pageModel = shareService.getPage(Integer.valueOf(currpage), pagesize, totalRecords);
        //标记区分  6实验通知单 7教学记录单
        mav.addObject("type",type);
        //总记录数
        mav.addObject("totalRecords",totalRecords);
        mav.addObject("pageModel",pageModel);

        mav.setViewName("reports/systemLog/listItem.jsp");
        return mav;
    }

    /*************************************************************************************
     * Description:开放项目相关报表--实验通知单-课次列表
     *
     * @author: Hezhaoyi
     * @date: 2019-5-15
     *************************************************************************************/
    @RequestMapping(value="/log/listItemClasses")
    public ModelAndView listItemClasses(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        //每页20条记录
        int pagesize = 20;
        String currpage = request.getParameter("currpage");
        int itemId = Integer.valueOf(request.getParameter("itemId"));
        OperationItem operationItem = operationItemDAO.findOperationItemById(itemId);
        //根据项目获取项目排课完成的课次列表
        int startWeek = 0;
        int endWeek = 0;
        int startClass = 0;
        int endClass = 0;
        int weekday = 0;
        List<Object> sectionList = new ArrayList();
        StringBuffer sql = new StringBuffer("SELECT i FROM ItemPlan i WHERE i.operationItem.id="+itemId);
        List<ItemPlan> itemPlanList = entityManager.createQuery(sql.toString()).getResultList();
        if(itemPlanList.size()!=0){
            ItemPlan itemPlan = itemPlanList.get(0);
            TimetableSelfCourse timetableSelfCourse = itemPlan.getTimetableSelfCourse();
            Set<TimetableAppointment> timetableAppointments = timetableAppointmentDAO.findTimetableAppointmentByCourseCode(timetableSelfCourse.getCourseCode());
            //根据TimetableAppointment获取起止周次节次星期
            for(TimetableAppointment timetableAppointment:timetableAppointments){
                Set<TimetableAppointmentSameNumber> timetableAppSameNumbers = timetableAppointment.getTimetableAppointmentSameNumbers();
                if(timetableAppSameNumbers.size()!=0){
                    for(TimetableAppointmentSameNumber timetableAppointmentSameNumber : timetableAppSameNumbers){
                        startWeek = timetableAppointmentSameNumber.getStartWeek();
                        endWeek = timetableAppointmentSameNumber.getEndWeek();
                        startClass = timetableAppointmentSameNumber.getStartClass();
                        endClass = timetableAppointmentSameNumber.getEndClass();
                        weekday = timetableAppointment.getWeekday();

                        if(startWeek!=0){
                            if(startWeek<endWeek){
                                if(startClass<endClass){
                                    Object[] object = new Object[5];
                                    object[0] = operationItem;
                                    object[1] = startWeek;
                                    object[2] = weekday;
                                    object[3] = startClass;
                                    object[4] = timetableAppointment.getId();
                                    sectionList.add(object);
                                    startClass++;
                                }else {
                                    Object[] object = new Object[5];
                                    object[0] = operationItem;
                                    object[1] = startWeek;
                                    object[2] = weekday;
                                    object[3] = endClass;
                                    object[4] = timetableAppointment.getId();
                                    sectionList.add(object);
                                }
                                startWeek++;
                            }else {
                                if(startClass<endClass){
                                    Object[] object = new Object[5];
                                    object[0] = operationItem;
                                    object[1] = startWeek;
                                    object[2] = weekday;
                                    object[3] = startClass;
                                    object[4] = timetableAppointment.getId();
                                    sectionList.add(object);
                                    startClass++;
                                }else {
                                    Object[] object = new Object[5];
                                    object[0] = operationItem;
                                    object[1]= startWeek;
                                    object[2] = weekday;
                                    object[3] = endClass;
                                    object[4] = timetableAppointment.getId();
                                    sectionList.add(object);
                                }
                            }
                        }
                    }
                }
            }
        }
        mav.addObject("sectionList",sectionList);
        int totalRecords = sectionList.size();
		Map<String, Integer> pageModel = shareService.getPage(Integer.valueOf(currpage), pagesize, totalRecords);
		//总记录数
		mav.addObject("totalRecords",totalRecords);
		mav.addObject("pageModel",pageModel);

        mav.setViewName("reports/systemLog/listItemClasses.jsp");
        return mav;
    }

    /*************************************************************************************
     * Description:开放项目相关报表--实验通知单
     *
     * @author: Hezhaoyi
     * @date: 2019-5-15
     *************************************************************************************/
    @RequestMapping(value="/log/listLaboratoryNotice")
    public ModelAndView listLaboratoryNotice(HttpServletRequest request){

        ModelAndView mav = new ModelAndView();

        LaboratoryNoticeVO laboratoryNoticeVO = systemLogService.listLaboratoryNotice(request);

        mav.addObject("laboratoryNoticeVO",laboratoryNoticeVO);
        mav.setViewName("reports/systemLog/listLaboratoryNotice.jsp");
        return mav;
    }

    /*************************************************************************************
     * Description:开放项目相关报表--分组实验通知、教学记录单
     *
     * @author: Hezhaoyi
     * @date: 2019-5-15
     *************************************************************************************/
    @RequestMapping(value="/log/listTeachingRecordSheet")
    public ModelAndView listTeachingRecordSheet(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();

        LaboratoryNoticeVO laboratoryNoticeVO = systemLogService.listTeachingRecordSheet(request);
        mav.addObject("laboratoryNoticeVO",laboratoryNoticeVO);

        mav.setViewName("reports/systemLog/listTeachingRecordSheet.jsp");
        return mav;
    }

    /*************************************************************************************
     * Description:开放项目相关报表--实验开出情况统计表
     *
     * @author: Hezhaoyi
     * @date: 2019-5-15
     *************************************************************************************/
    @RequestMapping(value="/log/listStatisticalTableOfExperiments")
    public ModelAndView listStatisticalTableOfExperiments(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        int labRoomId = Integer.valueOf(request.getParameter("labRoomId"));
        LabRoom labRoom = labRoomDAO.findLabRoomById(labRoomId);

        LaboratoryNoticeVO laboratoryNoticeVO = new LaboratoryNoticeVO();
        //查询对应年级的实验开出情况
        List<Object[]> InformationList = new ArrayList<>();
        //高一年级
        Object[] objectOne = new Object[7];
        //演示实验
        int oneCategory1 = entityManager.createQuery("select l from OperationItem l where l.CDictionaryByOpenGrade.id = "+779+" and l.CDictionaryByLpCategoryApp.id = " + 776 +" and l.labRoom.id =" +labRoomId).getResultList().size();
        objectOne[0] = "高一年级";
        objectOne[1] = oneCategory1;
        objectOne[2] = oneCategory1;
        if(oneCategory1!=0){
            objectOne[3] = 100;
        }
        else {
            objectOne[3] = 0;
        }
        //分组实验
        int oneCategory2 = entityManager.createQuery("select l from OperationItem l where l.CDictionaryByOpenGrade.id = "+779+" and l.CDictionaryByLpCategoryApp.id = " + 777 +" and l.labRoom.id =" +labRoomId).getResultList().size();
        objectOne[4] = oneCategory2;
        objectOne[5] = oneCategory2;
        if(oneCategory2!=0){
            objectOne[6] = 100;
        }
        else {
            objectOne[6] = 0;
        }
        InformationList.add(objectOne);
        //高二年级
        Object[] objectTwo = new Object[7];
        //演示实验
        int TwoCategory1 = entityManager.createQuery("select l from OperationItem l where l.CDictionaryByOpenGrade.id = "+780+" and l.CDictionaryByLpCategoryApp.id = " + 776 +" and l.labRoom.id =" +labRoomId).getResultList().size();
        objectTwo[0] = "高二年级";
        objectTwo[1] = TwoCategory1;
        objectTwo[2] = TwoCategory1;
        if(TwoCategory1!=0){
            objectTwo[3] = 100;
        }
        else {
            objectTwo[3] = 0;
        }
        //分组实验
        int TwoCategory2 = entityManager.createQuery("select l from OperationItem l where l.CDictionaryByOpenGrade.id = "+780+" and l.CDictionaryByLpCategoryApp.id = " + 777 +" and l.labRoom.id =" +labRoomId).getResultList().size();
        objectTwo[4] = TwoCategory2;
        objectTwo[5] = TwoCategory2;
        if(TwoCategory2!=0){
            objectTwo[6] = 100;
        }
        else {
            objectTwo[6] = 0;
        }
        InformationList.add(objectTwo);
        //高三年级
        Object[] objectThree = new Object[7];
        //演示实验
        int threeCategory1 = entityManager.createQuery("select l from OperationItem l where l.CDictionaryByOpenGrade.id = "+781+" and l.CDictionaryByLpCategoryApp.id = " + 776 +" and l.labRoom.id =" +labRoomId).getResultList().size();
        objectThree[0] = "高三年级";
        objectThree[1] = threeCategory1;
        objectThree[2] = threeCategory1;
        if(threeCategory1!=0){
            objectThree[3] = 100;
        }
        else {
            objectThree[3] = 0;
        }
        //分组实验
        int threeCategory2 = entityManager.createQuery("select l from OperationItem l where l.CDictionaryByOpenGrade.id = "+781+" and l.CDictionaryByLpCategoryApp.id = " + 777 +" and l.labRoom.id =" +labRoomId).getResultList().size();
        objectThree[4] = threeCategory2;
        objectThree[5] = threeCategory2;
        if(threeCategory2!=0){
            objectThree[6] = 100;
        }
        else {
            objectThree[6] = 0;
        }
        InformationList.add(objectThree);
        laboratoryNoticeVO.setInformationList(InformationList);
        mav.addObject("labRoomName",labRoom.getLabRoomName());
        mav.addObject("laboratoryNoticeVO",laboratoryNoticeVO);
        mav.setViewName("reports/systemLog/listStatisticalTableOfExperiments.jsp");
        return mav;
    }

    /**
     * Description 开放项目相关报表-实验计划表{导出excel}
     * @param request
     * @param response
     * @throws Exception
     * @author Hezhaoyi 2019-5-17
     */
    @RequestMapping("/log/exportListExperimentalSchedule")
    public void exportListExperimentalSchedule(HttpServletRequest request, HttpServletResponse response)throws Exception{
        systemLogService.exportListExperimentalSchedule(request, response);
    }

    /**
     * Description 开放项目相关报表-仪器借出登记表{导出excel}
     * @param request
     * @param response
     * @throws Exception
     * @author Hezhaoyi 2019-5-17
     */
    @RequestMapping("/log/exportListInstrumentLendingegistration")
    public void exportListInstrumentLendingegistration(HttpServletRequest request, HttpServletResponse response)throws Exception{
        systemLogService.exportListInstrumentLendingegistration(request, response);
    }


    /* Description 开放项目相关报表-低值易耗品领用登记表{导出excel}
     * @param request
     * @param response
     * @throws Exception
     * @author Hezhaoyi 2019-5-17
     */
    @RequestMapping("/log/exportListReceiptOfLowValueConsumables")
    public void exportListReceiptOfLowValueConsumables(HttpServletRequest request, HttpServletResponse response)throws Exception{
        systemLogService.exportListReceiptOfLowValueConsumables(request, response);
    }

    /* Description 开放项目相关报表-药品出库登记表{导出excel}
     * @param request
     * @param response
     * @throws Exception
     * @author Hezhaoyi 2019-5-17
     */
    @RequestMapping("/log/exportListDrugDepotRegistrationForm")
    public void exportListDrugDepotRegistrationForm(HttpServletRequest request, HttpServletResponse response)throws Exception{
        systemLogService.exportListDrugDepotRegistrationForm(request, response);
    }

    /* Description 开放项目相关报表-耗材领用登记表{导出excel}
     * @param request
     * @param response
     * @throws Exception
     * @author Hezhaoyi 2019-5-17
     */
    @RequestMapping("/log/exportListConsumablesAcquisitionRecordSheet")
    public void exportListConsumablesAcquisitionRecordSheet(HttpServletRequest request, HttpServletResponse response)throws Exception{
        systemLogService.exportListConsumablesAcquisitionRecordSheet(request, response);
    }

}