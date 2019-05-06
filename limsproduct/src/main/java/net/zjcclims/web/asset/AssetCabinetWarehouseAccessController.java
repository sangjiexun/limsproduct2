package net.zjcclims.web.asset;

import net.zjcclims.dao.CommonServerDAO;
import net.zjcclims.dao.SmartAgentDAO;
import net.zjcclims.dao.SmartAgentUserDAO;
import net.zjcclims.dao.UserDAO;
import net.zjcclims.domain.*;
import net.zjcclims.service.asset.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.device.LabRoomDeviceService;
import net.zjcclims.service.lab.LabRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller("AssetCabinetWarehouseAccessController")
@SessionAttributes("selected_academy")
@RequestMapping("/asset")
public class AssetCabinetWarehouseAccessController<JsonResult> {

	@Autowired AssetService assetService;
	@Autowired ShareService shareService;
	@Autowired AssetCabinetWarehouseAccessService assetCabinetWarehouseAccessService;
	@Autowired LabRoomService labRoomService;
	@Autowired AssetAppService assetAppService;
	@Autowired AssetReceiveService assetReceiveService;
	@Autowired SmartAgentService smartAgentService;
	@Autowired SmartAgentUserService smartAgentUserService;
	@Autowired SmartAgentDAO smartAgentDAO;
	@Autowired LabRoomDeviceService labRoomDeviceService;
	@Autowired CommonServerDAO commonServerDAO;
	@Autowired SmartAgentUserDAO smartAgentUserDAO;
	@Autowired UserDAO userDAO;
	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register // static // property // editors.
		binder.registerCustomEditor(java.util.Calendar.class,new org.skyway.spring.util.databinding.CustomCalendarEditor());
		binder.registerCustomEditor(byte[].class,new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
		binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
		binder.registerCustomEditor(java.math.BigDecimal.class,new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
		binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Integer.class, true));
		binder.registerCustomEditor(java.util.Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
		binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
		binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Long.class, true));
		binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Double.class, true));
	}
	/***********************************************************************************
	 * @功能：药品溶液管理--物资记录（库存调整列表）
	 * @author 郑昕茹
	 * @日期：2016-08-07
	 * **********************************************************************************/
	@RequestMapping("/listStoreAdjustRecords")
	public ModelAndView listStoreAdjustRecords(@RequestParam Integer id){
		ModelAndView mav = new ModelAndView();
		//根据id找到同种类型的物资记录的其中一条
		AssetCabinetWarehouseAccess access = assetCabinetWarehouseAccessService.findAssetCabinetWarehouseAccessByPrimaryKey(id);
		//找到属于同一类型，同一asset的物资记录
		List<AssetCabinetWarehouseAccessRecord> listAccessRecords = assetCabinetWarehouseAccessService.findAllAccesssInSameAssetAndType(access);
		mav.addObject("listAccessRecords", listAccessRecords);
		mav.setViewName("asset/listStoreAdjustRecords.jsp");
		mav.addObject("id", id);
		return mav;
	}
	/***********************************************************************************
	 * @功能：药品溶液管理--物资记录（药品库存调整操作）
	 * @author 郑昕茹
	 * @日期：2016-08-07
	 * **********************************************************************************/
	@RequestMapping("/newAdjustStore")
	public ModelAndView newAdjustStore(@RequestParam Integer id, @RequestParam Integer adjustId){
		ModelAndView mav = new ModelAndView();
		//根据主键找到存储在药品柜的信息
		AssetCabinetWarehouseAccessRecord accessRecord = assetCabinetWarehouseAccessService.findAssetCabinetWarehouseAccessRecordByPrimaryKey(id);
		//获得当前登录人
		User currUser = shareService.getUser();
		//获得所有users
		Map users = shareService.getUsersMap();
		if(accessRecord.getAsset().getSpecifications()!= null){
			String flag = (accessRecord.getAsset().getSpecifications()).replaceAll("[^a-z^A-Z]", ""); 
			mav.addObject("flag", flag);
		}
		mav.addObject("assetAdjustRecord", new AssetAdjustRecord());
		mav.addObject("accessRecord",accessRecord);
		mav.addObject("currUser", currUser);
		mav.addObject("users", users);
		mav.addObject("adjustId", adjustId);
		mav.setViewName("asset/newAdjustStore.jsp");
		return mav;
	}
	/***********************************************************************************
	 * @功能：药品溶液管理--物资记录（保存药品库存调整操作结果）
	 * @author 郑昕茹
	 * @日期：2016-08-07
	 * **********************************************************************************/
	@RequestMapping("/saveAdjustStore")
	public ModelAndView saveAdjustStore(@RequestParam Integer id,HttpServletRequest request,@ModelAttribute AssetAdjustRecord assetAdjustRecord, @RequestParam Integer adjustId){
		ModelAndView mav = new ModelAndView();
		//根据主键找到存储在药品柜的信息
		AssetCabinetWarehouseAccessRecord accessRecord = assetCabinetWarehouseAccessService.findAssetCabinetWarehouseAccessRecordByPrimaryKey(id);
		//调整数量
		String s = assetAdjustRecord.getQuantity().toString();
		
		double adjustQuantity = Double.parseDouble(s);
		//负责人
		User user = shareService.findUserByUsername(request.getParameter("chargePerson"));
		//调整方式
		String adjustWay = assetAdjustRecord.getType().toString();
		
		double beforeQuantity = accessRecord.getCabinetQuantity().doubleValue();//调整前的数量
		double d;//存取调整后的double结果
		//若该药品有规格
		if(accessRecord.getAsset().getSpecifications()== null){
			//添加
			if(adjustWay.equals("1")){
				d = accessRecord.getCabinetQuantity().doubleValue()+adjustQuantity;
				//double转decimal
				BigDecimal bg = new BigDecimal(new Double(d).toString());
				//蛇者改变后的库存数量
				accessRecord.setCabinetQuantity(bg);
			}//减少
			else{
				d = accessRecord.getCabinetQuantity().doubleValue()-adjustQuantity;
				BigDecimal bg = new BigDecimal(new Double(d).toString());
				accessRecord.setCabinetQuantity(bg);
			}
		}//若该药品无规格
		else{
			//获取要增加或减少的数量，将数量/规格
			adjustQuantity = adjustQuantity/Double.parseDouble((accessRecord.getAsset().getSpecifications()).replaceAll("[^0-9]", ""));
			//添加
			if(adjustWay.equals("1")){
				d = accessRecord.getCabinetQuantity().doubleValue()+adjustQuantity;
				BigDecimal bg = new BigDecimal(new Double(d).toString());
				accessRecord.setCabinetQuantity(bg);
			}
			//减少
			else{
				d = accessRecord.getCabinetQuantity().doubleValue()-adjustQuantity;
				BigDecimal bg = new BigDecimal(new Double(d).toString());
				accessRecord.setCabinetQuantity(bg);
			}
		}
		accessRecord=assetCabinetWarehouseAccessService.saveLocationMessage(accessRecord);
		//找到位置信息对应的在用物资记录
		AssetCabinetWarehouseAccess assetCabinetWarehouseAccess = accessRecord.getAssetCabinetWarehouseAccess();
		assetCabinetWarehouseAccess.setUser(user);//负责人
		double changeQuantity = d - beforeQuantity;//调整前后的数量差
		assetCabinetWarehouseAccess.setCabinetQuantity(new BigDecimal(assetCabinetWarehouseAccess.getCabinetQuantity().doubleValue()+changeQuantity));//设置改变后的库存数量
		assetCabinetWarehouseAccessService.saveAssetCabinetWarehouseAccess(assetCabinetWarehouseAccess);
		assetAdjustRecord.setAssetCabinetWarehouseAccessRecord(accessRecord);
		assetAdjustRecord.setAsset(accessRecord.getAsset());
		assetAdjustRecord.setDate(Calendar.getInstance());
		if(accessRecord.getAsset().getSpecifications() != null){
			assetAdjustRecord.setUnit(accessRecord.getAsset().getSpecifications().replaceAll("[^a-z^A-Z]", ""));
		}
		else{
			assetAdjustRecord.setUnit(accessRecord.getAsset().getUnit());
		}
		assetCabinetWarehouseAccessService.saveAssetAdjustRecord(assetAdjustRecord);
		mav.setViewName("redirect:/asset/listStoreAdjustRecords?id="+adjustId);
		return mav;
	}
	/***********************************************************************************
	 * @description 药品溶液管理--药品入库（申购入库列表）
	 * @author 郑昕茹
	 * @date 2016-08-10
	 * **********************************************************************************/
	@RequestMapping("/listAccessStocks")
	public ModelAndView listAccessStocks(@RequestParam Integer currpage,@ModelAttribute AssetApp assetApp,@RequestParam Integer stockStatus){
		ModelAndView mav = new ModelAndView();
		int pageSize = 30;//设置分页
		//获取所有通过审核的申购信息
		List<AssetApp> listAssetApps=assetAppService.findAssetAppsNeedStock(currpage, pageSize, assetApp, stockStatus);
		for(AssetApp app:listAssetApps){
			Set<AssetAppRecord> assetAppRecords = app.getAssetAppRecords();
			//判断该申购单对应的已通过审核的药品申购记录是否已全部成功入库
			boolean flag = true;
			//遍历所有的药品申购记录，判断是否有未入库的记录，若有说明该申购未入库
			Iterator<AssetAppRecord> assetAppRecordIterator = assetAppRecords.iterator();
			while(assetAppRecordIterator.hasNext()){
			AssetAppRecord a = assetAppRecordIterator.next();
			//只对通过审核的record进行判断
			if(a.getResult()!=null && a.getResult() == 1 &&a.getAsset().getCategory()!=null &&  a.getAsset().getCategory() == 0){
				//表示该记录未入库
				if(a.getStockStatus() == 0 || a.getStockStatus() == 2){
					flag = false;
					break;
				}
			}
			}
			//该药品申购全部入库
			if(flag == true){
				app.setStockStatus(1);
			}
			//该药品申购未全部入库
			else{
				app.setStockStatus(0);
			}
			assetAppService.saveAssetApp(app);
		}
		/**
		 * 1.遍历每个申购申购记录，得出记录数，即物资种类
		 * 2.在记录不为空时，将单价和数量相乘
		 * 3.累加得到总价值
		 * */
		//存储每条申购对应的申购记录的种类的种类
		int num[] = new int[listAssetApps.size()];
		//存储每条申购对应的申购记录的总价
		float totalPrice[] = new float[listAssetApps.size()];
		//计数现在在第几条申购
		int count = 0;
		for(AssetApp a:listAssetApps){
			num[count] = 0;
			totalPrice[count] = 0;
			//获取当前遍历到的这条申购相应的申购记录
			Set<AssetAppRecord> listAssetAppRecords=a.getAssetAppRecords();
			for(Iterator<AssetAppRecord> it = listAssetAppRecords.iterator(); it.hasNext();){
				AssetAppRecord assetAppRecord = it.next();
				if (assetAppRecord!=null) {
					num[count]++;
				}
				if (assetAppRecord!=null&&assetAppRecord.getAppPrice()!=null&&assetAppRecord.getAppQuantity()!=null) {
					BigDecimal quantity =new BigDecimal(assetAppRecord.getAppQuantity());
					float unitPrice=quantity.multiply(assetAppRecord.getAppPrice()).floatValue();
					totalPrice[count]+=unitPrice;
				}
			}
			count++;
		} 
		//获取总条数
		int totalRecords=assetAppService.findAssetApps(1,-1,1,assetApp,null,null,null).size();
		//翻页相关
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
		mav.addObject("num", num);//传值
		mav.addObject("listAssetApps", listAssetApps);//传值
		mav.addObject("pageModel", pageModel);
		mav.addObject("assetApp", assetApp);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("totalPrice", totalPrice); 
		mav.addObject("currpage", currpage);
		mav.addObject("pageSize", pageSize);
		mav.addObject("stockStatus", stockStatus);
		mav.setViewName("asset/listAccessStocks.jsp");
		return mav;
	}
	/***********************************************************************************
	 * @description：药品溶液管理--药品入库（新建药品入库）
	 * @author 郑昕茹
	 * @date：2016-08-11
	 * **********************************************************************************/
	@RequestMapping("/newAccessStock")
	public ModelAndView newAccessStock(@ModelAttribute("selected_academy") String acno){
		ModelAndView mav = new ModelAndView();
		AssetCabinetWarehouseAccess assetCabinetWarehouseAccess = new AssetCabinetWarehouseAccess(); 
		//User currUser = shareService.getUser();
		//获得所有users
		//Map users = shareService.getUsersMap();
		//找到再有的药品字典记录
		//List<Asset> listAssets = assetService.findAllReagentAssetNames();
		List<Asset> listAssets = assetService.findAllAssetNames();
		//获取该实验中心下的所有实验室运行大纲
		List<OperationItem> operationItems = assetAppService.findOperationOutlineByLabCenter(acno);
		Set<OperationOutline> operationOutlines = new HashSet<OperationOutline>();
		if(operationItems != null){
			for(OperationItem operationItem:operationItems){
				operationOutlines.add(operationItem.getOperationOutline());
			}
		}
		mav.addObject("operationOutlines", operationOutlines);
		mav.addObject("assetCabinetWarehouseAccess", assetCabinetWarehouseAccess); 
		mav.addObject("listAssets", listAssets);
		//mav.addObject("currUser", currUser);
		//mav.addObject("users", users);
		mav.setViewName("asset/newAccessStock.jsp");
		return mav;
	}
	/***********************************************************************************
	 * @description：药品溶液管理--药品入库（保存药品入库）
	 * @author 郑昕茹
	 * @date：2016-08-11
	 * **********************************************************************************/
	@RequestMapping("/saveAccessStock")
	public ModelAndView saveAccessStock(@ModelAttribute AssetCabinetWarehouseAccess assetCabinetWarehouseAccess){
		ModelAndView mav = new ModelAndView(); 
		if(assetCabinetWarehouseAccess.getOperationItem().getId()==null){
			assetCabinetWarehouseAccess.setOperationItem(null);
		}
		assetCabinetWarehouseAccess.setFlag(0);
		assetCabinetWarehouseAccess.setStatus(2);//入库中
		assetCabinetWarehouseAccess=assetCabinetWarehouseAccessService.saveAssetCabinetWarehouseAccess(assetCabinetWarehouseAccess); 
		mav.setViewName("redirect:/asset/setLocationMessage?id="+assetCabinetWarehouseAccess.getId()+"&isClickCompleteStock=0");
		return mav;
	}
	/***********************************************************************************
	 * @description：药品溶液管理--药品入库（设置药品位置具体信息）
	 * @author 郑昕茹
	 * @date：2016-08-11
	 * **********************************************************************************/
	@RequestMapping("/setLocationMessage")
	public ModelAndView setLocationMessage(@RequestParam Integer id,@ModelAttribute LabCenter labCenter, @ModelAttribute("selected_academy") String acno,@RequestParam Integer isClickCompleteStock){
		ModelAndView mav = new ModelAndView(); 
		//通过主键找到要设置药品位置具体信息的在用物资记录
		AssetCabinetWarehouseAccess assetCabinetWarehouseAccess = assetCabinetWarehouseAccessService.findAssetCabinetWarehouseAccessByPrimaryKey(id);
		//找到该物资对应的所有存放记录
		Set<AssetCabinetWarehouseAccessRecord> accessRecords = assetCabinetWarehouseAccess.getAssetCabinetWarehouseAccessRecords();
		//新建存放记录
		AssetCabinetWarehouseAccessRecord accessRecord = new AssetCabinetWarehouseAccessRecord();
		List<AssetCabinet> assetCabinets = assetCabinetWarehouseAccessService.findAllAssetCabinets();
		//找到所有的实验室
		Set<LabRoom> labRooms = new HashSet<LabRoom>();
		if(assetCabinets != null){
			for(AssetCabinet assetCabinet:assetCabinets){
				labRooms.add(assetCabinet.getLabRoom());
			}
		}
		//找到物资字典中的所有药品
		List<Asset> assets = assetService.findAllAssetNamesByCategory(0);
		mav.addObject("assetCabinetWarehouseAccess", assetCabinetWarehouseAccess); 
		mav.addObject("accessRecords", accessRecords);
		mav.addObject("accessRecord", accessRecord);
		mav.addObject("labRooms", labRooms);
		mav.addObject("assets", assets);
		mav.addObject("id", id);
		mav.addObject("isClickCompleteStock", isClickCompleteStock);
		mav.addObject("status", assetCabinetWarehouseAccess.getStatus());
		assetCabinetWarehouseAccessService.saveAssetCabinetWarehouseAccess(assetCabinetWarehouseAccess); 
		mav.setViewName("asset/setLocationMessage.jsp");
		return mav;
	}
	/***********************************************************************************
	 * @description：药品溶液管理--药品入库（查看药品位置具体信息）
	 * @author 郑昕茹
	 * @date：2016-08-18
	 * **********************************************************************************/
	@RequestMapping("/viewLocationMessage")
	public ModelAndView viewLocationMessage(@RequestParam Integer id,@ModelAttribute LabCenter labCenter, @ModelAttribute("selected_academy") String acno){
		ModelAndView mav = new ModelAndView(); 
		//通过主键找到要设置药品位置具体信息的在用物资记录
		AssetCabinetWarehouseAccess assetCabinetWarehouseAccess = assetCabinetWarehouseAccessService.findAssetCabinetWarehouseAccessByPrimaryKey(id);
		//找到该物资对应的所有存放记录
		Set<AssetCabinetWarehouseAccessRecord> accessRecords = assetCabinetWarehouseAccess.getAssetCabinetWarehouseAccessRecords();
		//新建存放记录
		AssetCabinetWarehouseAccessRecord accessRecord = new AssetCabinetWarehouseAccessRecord();
		//找到所有的实验室
		List<LabRoom> labRooms = labRoomService.findLabRoomByLabCenterid(null,1);
		//找到物资字典中的所有药品
		List<Asset> assets = assetService.findAllAssetNamesByCategory(0);
		mav.addObject("assetCabinetWarehouseAccess", assetCabinetWarehouseAccess); 
		mav.addObject("accessRecords", accessRecords);
		mav.addObject("accessRecord", accessRecord);
		mav.addObject("labRooms", labRooms);
		mav.addObject("assets", assets);
		mav.addObject("id", id);
		mav.addObject("status", assetCabinetWarehouseAccess.getStatus());
		assetCabinetWarehouseAccessService.saveAssetCabinetWarehouseAccess(assetCabinetWarehouseAccess); 
		mav.setViewName("asset/viewLocationMessage.jsp");
		return mav;
	}
	
	/***********************************************************************************
	 * @description：药品溶液管理--药品入库（联动查询实验室中的药品柜）
	 * @author 郑昕茹
	 * @date：2016-08-12
	 * **********************************************************************************/
	@ResponseBody
	@RequestMapping("/findAssetCabinetByLabRoomId")
	public Map<String, String> findAssetCabinetByLabRoomId(@RequestParam String labRoomId){
		Map<String, String> map = new HashMap<String, String>();
		String assetCabinetValue = assetCabinetWarehouseAccessService.findAssetCabinetByLabRoomId(labRoomId);
		map.put("assetCabinet", assetCabinetValue);
		return map;
	}
	/***********************************************************************************
	 * @description：药品溶液管理--药品入库（联动查询药品柜中的格子）
	 * @author 郑昕茹
	 * @date：2016-08-12
	 * **********************************************************************************/
	@ResponseBody
	@RequestMapping("/findWarehouseByCabinetId")
	public Map<String, String> findWarehouseByCabinetId(@RequestParam String cabinetId){
		Map<String, String> map = new HashMap<String, String>();
		String warehouseValue = assetCabinetWarehouseAccessService.findWarehouseByCabinetId(cabinetId);
		map.put("warehouse", warehouseValue);
		return map;
	}
	/***********************************************************************************
	 * @description：药品溶液管理--药品入库（保存药品位置信息）
	 * @author 郑昕茹
	 * @date：2016-08-12
	 * **********************************************************************************/
	@RequestMapping("/saveLoctaionMessage")
	public ModelAndView saveLoctaionMessage(@ModelAttribute AssetCabinetWarehouseAccessRecord assetRecord,@RequestParam Integer id){
		ModelAndView mav = new ModelAndView(); 
		//根据主键找到在用物资
		AssetCabinetWarehouseAccess assetCabinetWarehouseAccess = assetCabinetWarehouseAccessService.findAssetCabinetWarehouseAccessByPrimaryKey(id);
		//设置位置信息对应的在用物资
		assetRecord.setAssetCabinetWarehouseAccess(assetCabinetWarehouseAccess);
		//没有真正完成入库
		assetRecord.setStatus(0);
		//表示该字典入库
		assetCabinetWarehouseAccess.getAsset().setStatus(1);
		assetService.saveAsset(assetCabinetWarehouseAccess.getAsset());
		assetRecord.setId(null);
		//设置位置信息对应的药品
		assetRecord.setAsset(assetCabinetWarehouseAccess.getAsset());
		if(assetRecord.getAssetCabinetWarehouse() != null && assetRecord.getAssetCabinetWarehouse().getAssetCabinet() != null 
				&& assetRecord.getAssetCabinetWarehouse().getAssetCabinet().getLabRoom() != null && assetRecord.getAssetCabinetWarehouse().getAssetCabinet().getLabRoom().getId() == 0){
			assetRecord.setAssetCabinetWarehouse(null);
		}
		else{
			assetRecord.setPosition(null);
		}
		//保存位置信息
		assetRecord = assetCabinetWarehouseAccessService.saveLocationMessage(assetRecord);
		
		mav.setViewName("redirect:/asset/setLocationMessage?id="+id.toString()+"&isClickCompleteStock=0");
		return mav;
	}
	/***********************************************************************************
	 * @description：药品溶液管理--药品入库（删除药品位置信息）
	 * @author 郑昕茹
	 * @date：2016-08-13
	 * **********************************************************************************/
	@RequestMapping("/deleteLocationMessage")
	public ModelAndView deleteLocationMessage(@RequestParam Integer id){
		ModelAndView mav = new ModelAndView(); 
		//通过主键找到该条位置信息
		AssetCabinetWarehouseAccessRecord assetRecord = assetCabinetWarehouseAccessService.findAssetCabinetWarehouseAccessRecordByPrimaryKey(id);
		//找到该条位置信息对应的在用物资
		AssetCabinetWarehouseAccess assetCabinetWarehouseAccess = assetRecord.getAssetCabinetWarehouseAccess();
		assetCabinetWarehouseAccess.setStatus(0);
		assetCabinetWarehouseAccessService.saveAssetCabinetWarehouseAccess(assetCabinetWarehouseAccess);
		//找到该在用物资对应的所有位置信息
		Set<AssetCabinetWarehouseAccessRecord> assetRecords = assetCabinetWarehouseAccess.getAssetCabinetWarehouseAccessRecords();
		//遍历位置信息，将status都设为0，因为要删除一条信息，入库的总数量可能发生变化
		Iterator<AssetCabinetWarehouseAccessRecord> it = assetRecords.iterator();
		while(it.hasNext()){
			AssetCabinetWarehouseAccessRecord a = it.next();
			a.setStatus(0);
			assetCabinetWarehouseAccessService.saveLocationMessage(a);
		}
		//通过主键删除药品位置信息
		assetCabinetWarehouseAccessService.deleteLocationMessage(id);
		
		mav.setViewName("redirect:/asset/setLocationMessage?id="+assetCabinetWarehouseAccess.getId().toString()+"&isClickCompleteStock=0");
		return mav;
	}
	
	/***********************************************************************************
	 * @description：药品溶液管理--药品入库（完成入库，将所有位置的总数量与入库数量做判断，相同则入库成功，不同请核对）
	 * @author 郑昕茹
	 * @date：2016-08-13
	 * **********************************************************************************/
	@RequestMapping("/completeStock")
	public ModelAndView completeStock(@RequestParam Integer id){
		ModelAndView mav = new ModelAndView();
		//根据主键找到在用物资
		AssetCabinetWarehouseAccess assetCabinetWarehouseAccess = assetCabinetWarehouseAccessService.findAssetCabinetWarehouseAccessByPrimaryKey(id);
		boolean isMatch = assetCabinetWarehouseAccessService.judgeQuantitiesMatch(assetCabinetWarehouseAccess);
		//入库成功
		if(isMatch == true){
			//找到该要入库的在用物资对应的所有位置信息
			Set<AssetCabinetWarehouseAccessRecord> assetRecords = assetCabinetWarehouseAccess.getAssetCabinetWarehouseAccessRecords();	
			assetCabinetWarehouseAccess.setStatus(1);
			assetCabinetWarehouseAccessService.saveAssetCabinetWarehouseAccess(assetCabinetWarehouseAccess);
			Iterator<AssetCabinetWarehouseAccessRecord> it = assetRecords.iterator();
			while(it.hasNext()){
				AssetCabinetWarehouseAccessRecord assetRecord = it.next();
				assetRecord.setStatus(1);
				assetCabinetWarehouseAccessService.saveLocationMessage(assetRecord);
			}
			
		}
		else{
			//找到该要入库的在用物资对应的所有位置信息
			Set<AssetCabinetWarehouseAccessRecord> assetRecords = assetCabinetWarehouseAccess.getAssetCabinetWarehouseAccessRecords();
			assetCabinetWarehouseAccess.setStatus(2);
			assetCabinetWarehouseAccessService.saveAssetCabinetWarehouseAccess(assetCabinetWarehouseAccess);
			Iterator<AssetCabinetWarehouseAccessRecord> it = assetRecords.iterator();
			while(it.hasNext()){
				AssetCabinetWarehouseAccessRecord assetRecord = it.next();
				assetRecord.setStatus(2);
				assetCabinetWarehouseAccessService.saveLocationMessage(assetRecord);
			}
		}
		mav.setViewName("redirect:/asset/setLocationMessage?id="+id.toString()+"&isClickCompleteStock=1");
		return mav;
	}
	/***********************************************************************************
	 * @description：药品溶液管理--药品入库（查看申购后需要入库的药品）
	 * @author 郑昕茹
	 * @date：2016-08-15
	 * **********************************************************************************/
	@RequestMapping("/viewAppRecordNeedStocks")
	public ModelAndView viewAccessStocks(@RequestParam Integer id){
		ModelAndView mav = new ModelAndView();
		//根据主键找到药品申购记录
		AssetApp assetApp = assetAppService.findAssetAppByPrimaryKey(id);
		//找到该申购对应的所有申购记录
		Set<AssetAppRecord> assetAppRecords = assetApp.getAssetAppRecords();
		//计数申购记录的种类，即总条数
		int num = 0;
		float totalPrice = 0;//存储申购记录的总价
		List<AssetCabinetWarehouseAccess> accesss = new LinkedList<AssetCabinetWarehouseAccess>();
		//遍历所有的申购记录，获取其对应的在用物资记录
		Iterator<AssetAppRecord> it = assetAppRecords.iterator();
		while(it.hasNext()){
			AssetAppRecord assetAppRecord = it.next();
			if (assetAppRecord!=null) {
				num++;
			}
			if (assetAppRecord!=null&&assetAppRecord.getAppPrice()!=null&&assetAppRecord.getAppQuantity()!=null) {
				BigDecimal quantity =new BigDecimal(assetAppRecord.getAppQuantity());
				float unitPrice=quantity.multiply(assetAppRecord.getAppPrice()).floatValue();
				totalPrice+=unitPrice;
			}
			Set<AssetCabinetWarehouseAccess> warehouseAccesss = assetAppRecord.getAssetCabinetWarehouseAccesses();
			Iterator<AssetCabinetWarehouseAccess> accessIt = warehouseAccesss.iterator();
			Iterator<AssetCabinetWarehouseAccess> accessTest = warehouseAccesss.iterator();
			int count = 0;
			while(accessTest.hasNext()){
				accessTest.next();
				count++;
			}
			if(count == 0){
				accesss.add(new AssetCabinetWarehouseAccess());
			}else{
				//取第一条在用物资记录
				while(accessIt.hasNext()){
					AssetCabinetWarehouseAccess access = accessIt.next();
					accesss.add(access);
				}
			}
			
		}
		mav.addObject("assetApp", assetApp); 
		mav.addObject("accesss", accesss);
		mav.addObject("num", num);
		mav.addObject("totalPrice", totalPrice);
		mav.addObject("id", id);
		mav.addObject("assetAppRecords", assetAppRecords);
		mav.setViewName("asset/viewAppRecordNeedStocks.jsp");
		return mav;
	}
	/***********************************************************************************
	 * @description：药品溶液管理--药品入库（与申购相关的新建药品入库）
	 * @author 郑昕茹
	 * @date：2016-08-15
	 * **********************************************************************************/
	@RequestMapping("/newAccessStocks")
	public ModelAndView newAccessStocks(@RequestParam Integer id){
		ModelAndView mav = new ModelAndView();
		//根据主键找到物资申购记录
		AssetAppRecord assetAppRecord = assetAppService.findAssetAppRecordByPrimaryKey(id);
		AssetCabinetWarehouseAccess assetCabinetWarehouseAccess = new AssetCabinetWarehouseAccess();
		Calendar currTime = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		mav.addObject("currTime", sdf.format(currTime.getTime()));
		mav.addObject("assetAppRecord", assetAppRecord);
		mav.addObject("id", id);
		mav.addObject("assetCabinetWarehouseAccess", assetCabinetWarehouseAccess); 
		mav.setViewName("asset/newAccessStocks.jsp");
		return mav;
	}
	
	/***********************************************************************************
	 * @description：药品溶液管理--药品入库（与申购相关的保存药品入库）
	 * @author 郑昕茹
	 * @date：2016-08-15
	 * **********************************************************************************/
	@RequestMapping("/saveAccessStocks")
	public ModelAndView saveAccessStocks(@ModelAttribute AssetCabinetWarehouseAccess assetCabinetWarehouseAccess,@RequestParam Integer id){
		ModelAndView mav = new ModelAndView(); 
		//根据主键找到物资申购记录信息
		AssetAppRecord assetAppRecord = assetAppService.findAssetAppRecordByPrimaryKey(id);
		assetAppRecord.setStockStatus(2);//入库中
		assetAppService.saveAssetAppRecord(assetAppRecord);
		//设置在用物资记录对应的药品申购记录
		assetCabinetWarehouseAccess.setAssetAppRecord(assetAppRecord);
		assetCabinetWarehouseAccess.setAsset(assetAppRecord.getAsset());
		assetCabinetWarehouseAccess.setStatus(2);//入库中
		assetCabinetWarehouseAccess.setType(assetAppRecord.getAssetApp().getType());//
		assetCabinetWarehouseAccess.setFlag(1);//申购相关
		assetCabinetWarehouseAccess.setOperationItem(assetAppRecord.getAssetApp().getOperationItem());
		assetCabinetWarehouseAccess=assetCabinetWarehouseAccessService.saveAssetCabinetWarehouseAccess(assetCabinetWarehouseAccess); 
		mav.setViewName("redirect:/asset/setLocationMessages?id="+assetCabinetWarehouseAccess.getId()+"&isClickCompleteStock=0"+"&appId="+assetAppRecord.getAssetApp().getId());
		return mav;
	}
	
	/***********************************************************************************
	 * @description：药品溶液管理--药品入库（设置药品位置具体信息）
	 * @author 郑昕茹
	 * @date：2016-08-15
	 * **********************************************************************************/
	@RequestMapping("/setLocationMessages")
	public ModelAndView setLocationMessages(@RequestParam Integer id,@RequestParam Integer appId,@ModelAttribute LabCenter labCenter, @ModelAttribute("selected_academy") String acno,@RequestParam Integer isClickCompleteStock){
		ModelAndView mav = new ModelAndView(); 
		AssetAppRecord appRecord = assetAppService.findAssetAppRecordByPrimaryKey(appId);
		//通过主键找到要设置药品位置具体信息的在用物资记录
		AssetCabinetWarehouseAccess assetCabinetWarehouseAccess = assetCabinetWarehouseAccessService.findAssetCabinetWarehouseAccessByPrimaryKey(id);
		//找到该物资对应的所有存放记录
		if(assetCabinetWarehouseAccess != null)
		{
			Set<AssetCabinetWarehouseAccessRecord> accessRecords = assetCabinetWarehouseAccess.getAssetCabinetWarehouseAccessRecords();
			mav.addObject("accessRecords", accessRecords);
		}
		//新建存放记录
		AssetCabinetWarehouseAccessRecord accessRecord = new AssetCabinetWarehouseAccessRecord(); 
		List<AssetCabinet> assetCabinets = assetCabinetWarehouseAccessService.findAllAssetCabinets();
		//找到所有的实验室
				Set<LabRoom> labRooms = new HashSet<LabRoom>();
				if(assetCabinets != null){
					for(AssetCabinet assetCabinet:assetCabinets){
						labRooms.add(assetCabinet.getLabRoom());
					}
				}
		//找到物资字典中的所有药品
		List<Asset> assets = assetService.findAllAssetNamesByCategory(0);
		mav.addObject("assetCabinetWarehouseAccess", assetCabinetWarehouseAccess); 
		
		mav.addObject("accessRecord", accessRecord);
		mav.addObject("labRooms", labRooms);
		mav.addObject("assets", assets);
		mav.addObject("id", id);
		mav.addObject("appRecordId", appId);
		mav.addObject("isClickCompleteStock", isClickCompleteStock);
		mav.addObject("status", assetCabinetWarehouseAccess.getStatus());
		assetCabinetWarehouseAccessService.saveAssetCabinetWarehouseAccess(assetCabinetWarehouseAccess); 
		mav.setViewName("asset/setLocationMessages.jsp");
		return mav;
	}
	
	/***********************************************************************************
	 * @description：药品溶液管理--药品入库（查看药品位置具体信息）
	 * @author 郑昕茹
	 * @date：2016-08-15
	 * **********************************************************************************/
	@RequestMapping("/viewLocationMessages")
	public ModelAndView viewLocationMessages(@RequestParam Integer id,@RequestParam Integer appId,@ModelAttribute LabCenter labCenter, @ModelAttribute("selected_academy") String acno){
		ModelAndView mav = new ModelAndView(); 
		//通过主键找到要设置药品位置具体信息的在用物资记录
		AssetCabinetWarehouseAccess assetCabinetWarehouseAccess = assetCabinetWarehouseAccessService.findAssetCabinetWarehouseAccessByPrimaryKey(id);
		//找到该物资对应的所有存放记录
		Set<AssetCabinetWarehouseAccessRecord> accessRecords = assetCabinetWarehouseAccess.getAssetCabinetWarehouseAccessRecords();
		//新建存放记录
		AssetCabinetWarehouseAccessRecord accessRecord = new AssetCabinetWarehouseAccessRecord();
		//找到所有的实验室
		List<LabRoom> labRooms = labRoomService.findLabRoomByLabCenterid(null,1);
		//找到物资字典中的所有药品
		List<Asset> assets = assetService.findAllAssetNamesByCategory(0);
		mav.addObject("assetCabinetWarehouseAccess", assetCabinetWarehouseAccess); 
		mav.addObject("accessRecords", accessRecords);
		mav.addObject("accessRecord", accessRecord);
		mav.addObject("labRooms", labRooms);
		mav.addObject("assets", assets);
		mav.addObject("id", id);
		mav.addObject("appRecordId", appId);
		mav.addObject("status", assetCabinetWarehouseAccess.getStatus());
		assetCabinetWarehouseAccessService.saveAssetCabinetWarehouseAccess(assetCabinetWarehouseAccess); 
		mav.setViewName("asset/viewLocationMessages.jsp");
		return mav;
	}
	 
	/***********************************************************************************
	 * @description：药品溶液管理--药品入库（申购相关保存药品位置信息）
	 * @author 郑昕茹
	 * @date：2016-08-15
	 * **********************************************************************************/
	@RequestMapping("/saveLoctaionMessages")
	public ModelAndView saveLoctaionMessages(@ModelAttribute AssetCabinetWarehouseAccessRecord assetRecord,@RequestParam Integer id){
		ModelAndView mav = new ModelAndView(); 
		//根据主键找到在用物资
		AssetCabinetWarehouseAccess assetCabinetWarehouseAccess = assetCabinetWarehouseAccessService.findAssetCabinetWarehouseAccessByPrimaryKey(id);
		//找到对应的申购记录
		AssetAppRecord assetAppRecord = assetCabinetWarehouseAccess.getAssetAppRecord();
		assetAppRecord.setStockStatus(2);//入库中
		assetAppService.saveAssetAppRecord(assetAppRecord);
		//设置位置信息对应的在用物资
		assetRecord.setAssetCabinetWarehouseAccess(assetCabinetWarehouseAccess);
		//没有真正完成入库，入库中
		assetRecord.setStatus(2);
		assetCabinetWarehouseAccess.getAsset().setStatus(1);
		assetService.saveAsset(assetCabinetWarehouseAccess.getAsset());
		//设置位置信息对应的药品
		assetRecord.setAsset(assetCabinetWarehouseAccess.getAsset());
		assetRecord.setId(null);
		if(assetRecord.getAssetCabinetWarehouse() != null && assetRecord.getAssetCabinetWarehouse().getAssetCabinet() != null 
				&& assetRecord.getAssetCabinetWarehouse().getAssetCabinet().getLabRoom() != null && assetRecord.getAssetCabinetWarehouse().getAssetCabinet().getLabRoom().getId() == 0){
			assetRecord.setAssetCabinetWarehouse(null);
		}
		else{
			assetRecord.setPosition(null);
		}
		assetRecord = assetCabinetWarehouseAccessService.saveLocationMessage(assetRecord);
		mav.setViewName("redirect:/asset/setLocationMessages?id="+id.toString()+"&isClickCompleteStock=0"+"&appId="+id);
		return mav;
	}
	/***********************************************************************************
	 * @description：药品溶液管理--药品入库（完成入库，将所有位置的总数量与入库数量做判断，相同则入库成功，不同请核对）
	 * @author 郑昕茹
	 * @date：2016-08-15
	 * **********************************************************************************/
	@ResponseBody
	@RequestMapping("/checkQuantity")
	public String checkQuantity(@RequestParam Integer id){
		//根据主键找到在用物资
		AssetCabinetWarehouseAccess assetCabinetWarehouseAccess = assetCabinetWarehouseAccessService.findAssetCabinetWarehouseAccessByPrimaryKey(id);
		boolean isMatch = assetCabinetWarehouseAccessService.judgeQuantitiesMatch(assetCabinetWarehouseAccess);// 判断数量是否匹配
		//入库成功
		if(isMatch == true){
			//找到该要入库的在用物资对应的所有位置信息
			Set<AssetCabinetWarehouseAccessRecord> assetRecords = assetCabinetWarehouseAccess.getAssetCabinetWarehouseAccessRecords();	
			assetCabinetWarehouseAccess.setStatus(1);
			assetCabinetWarehouseAccess=assetCabinetWarehouseAccessService.saveAssetCabinetWarehouseAccess(assetCabinetWarehouseAccess);
			Iterator<AssetCabinetWarehouseAccessRecord> it = assetRecords.iterator();
			while(it.hasNext()){
				AssetCabinetWarehouseAccessRecord assetRecord = it.next();
				assetRecord.setStatus(1);
				assetCabinetWarehouseAccessService.saveLocationMessage(assetRecord);
			}
			//找到该在用物资对应的申购记录信息
			AssetAppRecord assetAppRecord = assetCabinetWarehouseAccess.getAssetAppRecord();
			assetAppRecord.setStockStatus(1);
			assetAppService.saveAssetAppRecord(assetAppRecord);
			//找到申购记录信息对应的申购信息
			AssetApp assetApp = assetAppRecord.getAssetApp();
			//找到该申购信息对应的所有申购记录
			Set<AssetAppRecord> assetAppRecords = assetApp.getAssetAppRecords();
			//判断该申购单对应的已通过审核的药品申购记录是否已全部成功入库
			boolean flag = true;
			//遍历所有的药品申购记录，判断是否有未入库的记录，若有说明该申购未入库
			Iterator<AssetAppRecord> assetAppRecordIterator = assetAppRecords.iterator();
			while(assetAppRecordIterator.hasNext()){
			AssetAppRecord a = assetAppRecordIterator.next();
			//只对通过审核的record进行判断
			if(a.getResult() == 1 &&a.getAsset().getCategory() != 1){
				//表示该记录未入库
				if(a.getStockStatus() == 0 || a.getStockStatus() == 2){
					flag = false;
					break;
				}
			}
			}
			//该药品申购全部入库
			if(flag == true){
				assetApp.setStockStatus(1);
			}
			//该药品申购未全部入库
			else{
				assetApp.setStockStatus(0);
			}
			assetAppService.saveAssetApp(assetApp);
			}
			else{
					//找到该要入库的在用物资对应的所有位置信息
					Set<AssetCabinetWarehouseAccessRecord> assetRecords = assetCabinetWarehouseAccess.getAssetCabinetWarehouseAccessRecords();
					assetCabinetWarehouseAccess.setStatus(2);//入库中
					assetCabinetWarehouseAccessService.saveAssetCabinetWarehouseAccess(assetCabinetWarehouseAccess);
					Iterator<AssetCabinetWarehouseAccessRecord> it = assetRecords.iterator();
					while(it.hasNext()){
					AssetCabinetWarehouseAccessRecord assetRecord = it.next();
					assetRecord.setStatus(2);
					assetCabinetWarehouseAccessService.saveLocationMessage(assetRecord);
				}
			}
		if(isMatch == true)return "match";
		else return "notMatch";
	}
	/***********************************************************************************
	 * @description：药品溶液管理--药品入库（删除申购对应的药品位置信息）
	 * @author 郑昕茹
	 * @date：2016-08-13
	 * **********************************************************************************/
	@RequestMapping("/deleteLocationMessages")
	public ModelAndView deleteLocationMessages(@RequestParam Integer id){
		ModelAndView mav = new ModelAndView(); 
		//通过主键找到该条位置信息
		AssetCabinetWarehouseAccessRecord assetRecord = assetCabinetWarehouseAccessService.findAssetCabinetWarehouseAccessRecordByPrimaryKey(id);
		//找到该条位置信息对应的在用物资
		AssetCabinetWarehouseAccess assetCabinetWarehouseAccess = assetRecord.getAssetCabinetWarehouseAccess();
		assetCabinetWarehouseAccess.setStatus(2);
		assetCabinetWarehouseAccessService.saveAssetCabinetWarehouseAccess(assetCabinetWarehouseAccess);
		//找到该在用物资对应的所有位置信息
		Set<AssetCabinetWarehouseAccessRecord> assetRecords = assetCabinetWarehouseAccess.getAssetCabinetWarehouseAccessRecords();
		//遍历位置信息，将status都设为0，因为要删除一条信息，入库的总数量可能发生变化
		Iterator<AssetCabinetWarehouseAccessRecord> it = assetRecords.iterator();
		while(it.hasNext()){
			AssetCabinetWarehouseAccessRecord a = it.next();
			a.setStatus(2);
			assetCabinetWarehouseAccessService.saveLocationMessage(a);
		}
		//通过主键删除药品位置信息
		assetCabinetWarehouseAccessService.deleteLocationMessage(id);
		
		mav.setViewName("redirect:/asset/setLocationMessages?id="+assetCabinetWarehouseAccess.getId().toString()+"&isClickCompleteStock=0"+"&appId="+id);
		return mav;
	}
	/***********************************************************************************
	 * @description：药品溶液管理--药品入库（完成入库，将所有位置的总数量与入库数量做判断，相同则入库成功，不同请核对）
	 * @author 郑昕茹
	 * @date：2016-08-15
	 * **********************************************************************************/
	@ResponseBody
	@RequestMapping("/checkNewStockQuantity")
	public String checkNewStockQuantity(@RequestParam Integer id){
		//根据主键找到在用物资
		AssetCabinetWarehouseAccess assetCabinetWarehouseAccess = assetCabinetWarehouseAccessService.findAssetCabinetWarehouseAccessByPrimaryKey(id);
		boolean isMatch = assetCabinetWarehouseAccessService.judgeQuantitiesMatch(assetCabinetWarehouseAccess);// 判断数量是否匹配
		//入库成功
		if(isMatch == true){
			//找到该要入库的在用物资对应的所有位置信息
			Set<AssetCabinetWarehouseAccessRecord> assetRecords = assetCabinetWarehouseAccess.getAssetCabinetWarehouseAccessRecords();	
			assetCabinetWarehouseAccess.setStatus(1);
			assetCabinetWarehouseAccess=assetCabinetWarehouseAccessService.saveAssetCabinetWarehouseAccess(assetCabinetWarehouseAccess);
			Iterator<AssetCabinetWarehouseAccessRecord> it = assetRecords.iterator();
			while(it.hasNext()){
				AssetCabinetWarehouseAccessRecord assetRecord = it.next();
				assetRecord.setStatus(1);
				assetCabinetWarehouseAccessService.saveLocationMessage(assetRecord);
			}   
			}
			else{
					//找到该要入库的在用物资对应的所有位置信息
					Set<AssetCabinetWarehouseAccessRecord> assetRecords = assetCabinetWarehouseAccess.getAssetCabinetWarehouseAccessRecords();
					assetCabinetWarehouseAccess.setStatus(2);//入库中
					assetCabinetWarehouseAccessService.saveAssetCabinetWarehouseAccess(assetCabinetWarehouseAccess);
					Iterator<AssetCabinetWarehouseAccessRecord> it = assetRecords.iterator();
					while(it.hasNext()){
					AssetCabinetWarehouseAccessRecord assetRecord = it.next();
					assetRecord.setStatus(2);
					assetCabinetWarehouseAccessService.saveLocationMessage(assetRecord);
				}
			}
		if(isMatch == true)return "match";
		else return "notMatch";
	}
	/***********************************************************************************
	 * @description 药品溶液管理--药品入库（新建的入库列表）
	 * @author 郑昕茹
	 * @date 2016-08-10
	 * **********************************************************************************/
	@RequestMapping("/listAccessStock")
	public ModelAndView listAccessStock(@RequestParam Integer currpage){
		ModelAndView mav = new ModelAndView();
		int pageSize = 30;//设置分页
		List<AssetCabinetWarehouseAccess>  assetCabinetWarehouseAccesss = assetCabinetWarehouseAccessService.findAllNewAccessStock(currpage, pageSize);
		//获取总条数
		int totalRecords= assetCabinetWarehouseAccessService.findAllNewAccessStock(1, -1).size();
		//翻页相关
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords); 
		mav.addObject("assetCabinetWarehouseAccesss", assetCabinetWarehouseAccesss);//传值
		mav.addObject("pageModel", pageModel); 
		mav.addObject("totalRecords", totalRecords); 
		mav.addObject("currpage", currpage);
		mav.addObject("pageSize", pageSize); 
		mav.setViewName("asset/listAccessStock.jsp");
		return mav;
	}
	
	/***********************************************************************************
	 * @description 药品溶液管理--物资记录（获得物资的库存调整列表）
	 * @author 郑昕茹
	 * @date 2016-08-17
	 * **********************************************************************************/
	@RequestMapping("/getAssetAdjustRecords")
	public ModelAndView getAssetAdjustRecords(@RequestParam Integer currpage, @RequestParam Integer id,@ModelAttribute  AssetAdjustRecord assetAdjustRecord){
		ModelAndView mav = new ModelAndView();
		int pageSize = 20;//设置分页
		AssetCabinetWarehouseAccess assetCabinetWarehouseAccess = assetCabinetWarehouseAccessService.findAssetCabinetWarehouseAccessByPrimaryKey(id);
		List<AssetAdjustRecord> listAssetAdjustRecords = assetCabinetWarehouseAccessService.findAllAdjustRecordsInSameAssetAndType(assetCabinetWarehouseAccess, currpage, pageSize);
		//获取总条数
		int totalRecords= assetCabinetWarehouseAccessService.findAllAdjustRecordsInSameAssetAndType(assetCabinetWarehouseAccess, currpage, pageSize).size();
		//翻页相关
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords); 
		mav.addObject("listAssetAdjustRecords", listAssetAdjustRecords);//传值 
		mav.addObject("pageModel", pageModel); 
		mav.addObject("totalRecords", totalRecords); 
		mav.addObject("currpage", currpage);
		mav.addObject("pageSize", pageSize); 
		mav.addObject("id", id);
		mav.addObject("assetAdjustRecord",assetAdjustRecord);
		mav.setViewName("asset/getAssetAdjustRecords.jsp");
		return mav;
	}
	
	/***********************************************************************************
	 * @功能：药品溶液管理--物资记录（每种物资的申领记录列表）
	 * @author 郑昕茹
	 * @日期：2016-08-03
	 * **********************************************************************************/
	@RequestMapping("/getAssetReceiveRecords")
	public ModelAndView getAssetReceiveRecords(@RequestParam int currpage,@RequestParam int id){
		ModelAndView mav = new ModelAndView();
		int pageSize = 20;
		AssetCabinetWarehouseAccess assetCabinetWarehouseAccess = assetCabinetWarehouseAccessService.findAssetCabinetWarehouseAccessByPrimaryKey(id);
		//获得所有药品申购信息 
		List<AssetReceiveRecord> listAssetReceiveRecords = assetCabinetWarehouseAccessService.findAllReceiveRecordsInSameAssetAndType(assetCabinetWarehouseAccess, currpage, pageSize);
		int totalRecords = assetCabinetWarehouseAccessService.findAllReceiveRecordsInSameAssetAndType(assetCabinetWarehouseAccess, 1, -1).size(); 
		//翻页相关
		Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords); 
		mav.addObject("pageSize",pageSize);
		mav.addObject("currpage",currpage);
		mav.addObject("totalRecords", totalRecords);
		mav.addObject("pageModel", pageModel);
		mav.addObject("id", id);
		mav.addObject("listAssetReceiveRecords", listAssetReceiveRecords); 
		mav.setViewName("asset/getAssetReceiveRecords.jsp");
		return mav;
	}
	
	/***********************************************************************************
	 * @功能：药品溶液管理--设置自动开箱时间 
	 * @author 郑昕茹
	 * @日期：2016-09-06
	 * **********************************************************************************/
	@RequestMapping("/listOpenTimes")
	public ModelAndView listOpenTimes(@ModelAttribute IotSharePowerOpentime iotSharePowerOpentime){
		ModelAndView mav = new ModelAndView();  
		List<IotSharePowerOpentime> listOpenTimes = assetCabinetWarehouseAccessService.findAllOpenTimes(iotSharePowerOpentime);
		mav.addObject("listOpenTimes", listOpenTimes); 
		mav.setViewName("asset/listOpenTimes.jsp");
		mav.addObject("iotSharePowerOpentime", iotSharePowerOpentime);
		mav.addObject("schoolTerms", shareService.findAllSchoolTerms());
		return mav;
	}
	/***********************************************************************************
	 * @功能：电源控制器列表 
	 * @author 周志辉
	 * @日期：2017-10-12
	 * **********************************************************************************/
	@RequestMapping("/listSmartAgents")
	public ModelAndView listSmartAgents(@ModelAttribute SmartAgent smartAgent){
		ModelAndView mav = new ModelAndView();  
		List<SmartAgent> listSmartAgent =smartAgentService.findAllSmartAgents(0,-1);
		mav.addObject("listSmartAgent", listSmartAgent); 
		mav.setViewName("asset/listSmartAgent.jsp");
		return mav;
	}
	/***********************************************************************************
	 * @功能：新增电源控制器
	 * @author 周志辉
	 * @日期：2017-10-12
	 * **********************************************************************************/
	@RequestMapping("/newSmartAgent")
	public ModelAndView newSmartAgent( ){
		ModelAndView mav = new ModelAndView();   
		mav.addObject("smartAgent", new SmartAgent()); 
		//mav.addObject("listLabRoomDevice",labRoomDeviceDAO.findAllLabRoomDevices());
		mav.addObject("server",commonServerDAO.findAllCommonServers());
		mav.addObject("flag",0);
		mav.setViewName("asset/editSmartAgent.jsp");
		return mav;
	}
	/***********************************************************************************
	 * @功能：保存电源控制器
	 * @author 周志辉
	 * @throws ParseException 
	 * @日期：2017-10-12
	 * **********************************************************************************/
	@RequestMapping("/saveSmartAgent")
	public ModelAndView saveSmartAgent(@ModelAttribute SmartAgent smartAgent,HttpServletRequest request) throws ParseException{
		ModelAndView mav = new ModelAndView();  
		smartAgentDAO.store(smartAgent);	
		mav.setViewName("redirect:/asset/listSmartAgents"); 
		return mav;
	}
	/***********************************************************************************
	 * @功能：编辑电源控制器
	 * @author 周志辉
	 * @throws ParseException 
	 * @日期：2017-10-12
	 * **********************************************************************************/
	@RequestMapping("/editSmartAgent")
	public ModelAndView editSmartAgent(@RequestParam String id){
		ModelAndView mav = new ModelAndView();   
		mav.addObject("smartAgent",smartAgentDAO.findSmartAgentByPrimaryKey(id));
		mav.addObject("server",commonServerDAO.findAllCommonServers());
		mav.addObject("flag",1);
		mav.setViewName("asset/editSmartAgent.jsp");
		return mav;
	}
	/***********************************************************************************
	 * @功能：删除电源控制器
	 * @author 周志辉
	 * @throws ParseException 
	 * @日期：2017-10-12
	 * **********************************************************************************/
	@RequestMapping("/deleteSmartAgent")
	public ModelAndView deleteSmartAgent(@RequestParam String id) throws ParseException{
		ModelAndView mav = new ModelAndView();  
		SmartAgent smartAgent=smartAgentDAO.findSmartAgentByPrimaryKey(id);
		smartAgentDAO.remove(smartAgent);
		mav.setViewName("redirect:/asset/listSmartAgents"); 
		return mav;
	}
	/***********************************************************************************
	 * @功能：删除电源控制器
	 * @author 周志辉
	 * @throws ParseException 
	 * @日期：2017-10-12
	 * **********************************************************************************/
	@RequestMapping("/authorize")
	public ModelAndView authorize(@RequestParam String id) throws ParseException{
		ModelAndView mav = new ModelAndView();  
		SmartAgent smartAgent=smartAgentDAO.findSmartAgentByPrimaryKey(id);
		//List<String> smartAgentUser=smartAgentService.findUserBySerialNo(id);
		List<SmartAgentUser> smartAgentUser=smartAgentService.findSmartAgentUserBySerialNo(id);
		mav.addObject("userList",smartAgentUser);
		mav.addObject("id",id);
		mav.setViewName("asset/listSmartAgentUser.jsp"); 
		return mav;
	}
	/****************************************************************************
	 * 功能：保存实验室管理员 作者：贺子龙 时间：2015-09-08
	 ****************************************************************************/
	@RequestMapping("/saveSmartAgentUser")
	public ModelAndView saveSmartAgentUser(@RequestParam String id,
			String[] array) {
		ModelAndView mav = new ModelAndView();
		for (String i : array) {
			// username对应的用户
			User u = userDAO.findUserByPrimaryKey(i);
			SmartAgentUser smartAgentUser=new SmartAgentUser();
			smartAgentUser.setCname(u.getCname());
			smartAgentUser.setUsername(u.getUsername());
			smartAgentUser.setSerialNo(id);
			smartAgentUserDAO.store(smartAgentUser);
		}
		mav.setViewName("redirect:/asset/authorize?id="+id);
		return mav;
	}
	/****************************************************************************
	 * 功能：删除实验室管理员 作者：贺子龙 时间：2015-09-15
	 ****************************************************************************/
	@RequestMapping("/deleteSmartAgentUser")
	public ModelAndView deleteSmartAgentUser(@RequestParam Integer id,String serialNo) {
		ModelAndView mav = new ModelAndView();
		// id对应的实验室物联管理员
		SmartAgentUser smartAgentUser = smartAgentUserDAO.findSmartAgentUserByPrimaryKey(id);
		smartAgentUserDAO.remove(smartAgentUser);
		mav.setViewName("redirect:/asset/authorize?id="+serialNo);
		return mav;
	}
	/****************************************************************************
	 * 功能：AJAX 根据姓名、工号查询当前登录人所在学院的用户 作者：周志辉 时间：2017-10-08
	 * 
	 * @throws UnsupportedEncodingException
	 ****************************************************************************/
	@SuppressWarnings("deprecation")
	@RequestMapping("/findUserByCnameAndUsername")
	public @ResponseBody
	String findUserByCnameAndUsername(@RequestParam String cname,
			String username,Integer page)
			throws UnsupportedEncodingException {
		if (cname != null) {

			// cname = java.net.URLDecoder.decode(cname, "UTF-8");// 转成utf-8；

		}
		User u = shareService.getUser();
		String academyNumber = "";
		if (u.getSchoolAcademy() != null) {
			academyNumber = u.getSchoolAcademy().getAcademyNumber();
		}
		User user = new User();
//		user.setCname(java.net.URLDecoder.decode(cname, "UTF-8"));
		// 页面传值转码
		if(cname != null && cname.equals(new String(cname.getBytes("iso8859-1"), "iso8859-1"))){
			cname = new String(cname.getBytes("iso8859-1"), "utf-8");
		}
		user.setCname(cname);
		user.setUsername(username);

		// 分页开始
		int totalRecords = labRoomService.findUserByUserAndSchoolAcademy(user,
				-1, academyNumber);
		int pageSize = 20;
		Map<String, Integer> pageModel = shareService.getPage(page, pageSize,
				totalRecords);
		// 根据分页信息查询出来的记录
		List<User> userList = labRoomService.findUserByUserAndSchoolAcademy(
				user,-1, academyNumber, page, pageSize);
		String s = "";
		for (User d : userList) {
			String academy = "";
			if (d.getSchoolAcademy() != null) {
				academy = d.getSchoolAcademy().getAcademyName();
			}
			s += "<tr>" + "<td><input type='checkbox' name='CK_name' value='"
					+ d.getUsername() + "'/></td>" + "<td>" + d.getCname()
					+ "</td>" + "<td>" + d.getUsername() + "</td>" + "<td>"
					+ academy + "</td>" +

					"</tr>";
		}
		s += "<tr><td colspan='6'>"
				+ "<a href='javascript:void(0)' onclick='firstPage(1);'>"
				+ "首页" + "</a>&nbsp;"
				+ "<a href='javascript:void(0)' onclick='previousPage("
				+ page
				+ ");'>"
				+ "上一页"
				+ "</a>&nbsp;"
				+ "<a href='javascript:void(0)' onclick='nextPage("
				+ page
				+ ","
				+ pageModel.get("totalPage")
				+ ");'>"
				+ "下一页"
				+ "</a>&nbsp;"
				+ "<a href='javascript:void(0)' onclick='lastPage("
				+ pageModel.get("totalPage")
				+ ");'>"
				+ "末页"
				+ "</a>&nbsp;"
				+ "当前第"
				+ page
				+ "页&nbsp; 共"
				+ pageModel.get("totalPage")
				+ "页  " + totalRecords + "条记录" + "</td></tr>";
		return htmlEncode(s);
	}
	/****************************************************************************
	 * 功能：处理ajax中文乱码 作者：贺子龙 时间：2015-09-08
	 ****************************************************************************/
	public static String htmlEncode(String str) {
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
	/***********************************************************************************
	 * @功能：药品溶液管理--新建开箱记录
	 * @author 郑昕茹
	 * @日期：2016-09-06
	 * **********************************************************************************/
	@RequestMapping("/newOpenTime")
	public ModelAndView newOpenTime( ){
		ModelAndView mav = new ModelAndView();   
		mav.addObject("iotSharePowerOpentime", new IotSharePowerOpentime()); 
		mav.addObject("schoolWeekdays", shareService.getWeekdays());
		mav.setViewName("asset/newOpenTime.jsp");
		mav.addObject("schoolTerms", shareService.findAllSchoolTerms());
		return mav;
	}
	
	/***********************************************************************************
	 * @功能：药品溶液管理--编辑开箱记录
	 * @author 郑昕茹
	 * @日期：2016-09-06
	 * **********************************************************************************/
	@RequestMapping("/editOpenTime")
	public ModelAndView editOpenTime(@RequestParam Integer id){
		ModelAndView mav = new ModelAndView();   
		mav.addObject("iotSharePowerOpentime", assetCabinetWarehouseAccessService.findIotSharePowerOpentimeByPrimaryKey(id)); 
		mav.addObject("schoolWeekdays", shareService.getWeekdays());
		mav.setViewName("asset/editOpenTime.jsp");
		mav.addObject("schoolTerms", shareService.findAllSchoolTerms());
		SimpleDateFormat sdf = new SimpleDateFormat( "HH:mm:ss" ); 
		String startTime = sdf.format(assetCabinetWarehouseAccessService.findIotSharePowerOpentimeByPrimaryKey(id).getStartTime().getTime());
		String endTime = sdf.format(assetCabinetWarehouseAccessService.findIotSharePowerOpentimeByPrimaryKey(id).getEndTime().getTime());
		mav.addObject("startTime", startTime);
		mav.addObject("endTime", endTime);
		return mav;
	}
	
	/***********************************************************************************
	 * @功能：药品溶液管理--新建开箱时间记录
	 * @author 郑昕茹
	 * @throws ParseException 
	 * @日期：2016-09-06
	 * **********************************************************************************/
	@RequestMapping("/saveOpenTime")
	public ModelAndView saveOpenTime(@ModelAttribute IotSharePowerOpentime iotSharePowerOpentime,HttpServletRequest request) throws ParseException{
		ModelAndView mav = new ModelAndView();  
		SimpleDateFormat sdformat = new SimpleDateFormat( "HH:mm:ss" ); 
		Date startDate = sdformat.parse(request.getParameter("startTime"));
		Date endDate = sdformat.parse(request.getParameter("endTime"));
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		iotSharePowerOpentime.setStartTime(start);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		iotSharePowerOpentime.setEndTime(end);
		assetCabinetWarehouseAccessService.saveIotSharePowerOpentime(iotSharePowerOpentime);
		
		mav.setViewName("redirect:/asset/listOpenTimes"); 
		return mav;
	}
	
	/***********************************************************************************
	 * @功能：药品溶液管理--删除开箱记录
	 * @author 郑昕茹
	 * @日期：2016-09-07
	 * **********************************************************************************/
	@RequestMapping("/deleteOpenTime")
	public ModelAndView deleteOpenTime(@RequestParam Integer id){
		ModelAndView mav = new ModelAndView();   
		assetCabinetWarehouseAccessService.deleteIotSharePowerOpentime(id);
		mav.setViewName("redirect:/asset/listOpenTimes"); 
		return mav;
	}
	
	/***********************************************************************************
	 * @description：药品溶液管理--设置自动开箱时间（根据学期找到该学期下还需要设置的周几）
	 * @author 郑昕茹
	 * @date：2016-09-06
	 * **********************************************************************************/
	@ResponseBody
	@RequestMapping("/findWeekdaysByTermId")
	public Map<String, String> findWeekdaysByTermId(@RequestParam Integer termId){
		Map<String, String> map = new HashMap<String, String>();
		String s = "<option value=\"\">请选择</option>";
		if(termId != null){
			List<IotSharePowerOpentime> IotSharePowerOpentimes = assetCabinetWarehouseAccessService.findOpenTimesByTermId(termId);
			Map<Integer,String> weekdays = shareService.getWeekdays();
			if(IotSharePowerOpentimes != null){
				for(IotSharePowerOpentime IotSharePowerOpentime:IotSharePowerOpentimes){
					weekdays.remove(IotSharePowerOpentime.getSchoolWeekday().getId()); 
				}
			}
				for (Map.Entry entry : weekdays.entrySet()) {  
					  
					s+="<option value=\""+entry.getKey()+"\">"+entry.getValue()+"</option>";
				  
				} 
		}
		map.put("operationItems", shareService.htmlEncode(s));
		return map;
	}
	
	/***********************************************************************************
	 * @功能：药品溶液管理--物资记录（修改项目列表）
	 * @author 郑昕茹
	 * @日期：2016-12-22
	 * **********************************************************************************/
	@RequestMapping("/listModifyBelongOperationItem")
	public ModelAndView listModifyBelongOperationItem(@RequestParam Integer id){
		ModelAndView mav = new ModelAndView();
		//根据id找到同种类型的物资记录的其中一条
		AssetCabinetWarehouseAccess access = assetCabinetWarehouseAccessService.findAssetCabinetWarehouseAccessByPrimaryKey(id);
		//找到属于同一类型，同一asset的物资记录
		List<AssetCabinetWarehouseAccess> listAccesss = assetCabinetWarehouseAccessService.findAccesssInSameAssetAndType(access);
		mav.addObject("listAccesss", listAccesss);
		mav.setViewName("asset/listModifyBelongOperationItem.jsp");
		mav.addObject("id", id);
		return mav;
	}
	
	
	/***********************************************************************************
	 * @功能：药品溶液管理--物资记录（药品修改所属项目操作）
	 * @author 郑昕茹
	 * @日期：2016-12-22
	 * **********************************************************************************/
	@RequestMapping("/modifyBelongOperationItem")
	public ModelAndView modifyBelongOperationItem(@RequestParam Integer id, @RequestParam Integer modifyId, @ModelAttribute("selected_academy") String acno){
		ModelAndView mav = new ModelAndView();
		//根据主键找到存储在药品柜的信息
		AssetCabinetWarehouseAccess access = assetCabinetWarehouseAccessService.findAssetCabinetWarehouseAccessByPrimaryKey(id);
		mav.addObject("access",access);
		mav.addObject("modifyId", modifyId);
		//获取该实验中心下的所有实验室运行大纲
		List<OperationItem> operationItems = assetAppService.findOperationOutlineByLabCenter(acno);
		Set<OperationOutline> operationOutlines = new HashSet<OperationOutline>();
		if(operationItems != null){
			for(OperationItem operationItem:operationItems){
				operationOutlines.add(operationItem.getOperationOutline());
			}
		}
		mav.addObject("operationOutlines", operationOutlines);
		mav.addObject("outLine", access.getType());
		if(access.getOperationItem() != null){
			//找到该实验项目所属实验大纲下的全部实验项目
			mav.addObject("items",access.getOperationItem().getOperationOutline().getOperationItems());
		}
		mav.addObject("operationOutlines", operationOutlines);
		mav.setViewName("asset/modifyBelongOperationItem.jsp");
		return mav;
	}
	
	
	/***********************************************************************************
	 * @功能：药品溶液管理--物资记录（保存药品库存调整操作结果）
	 * @author 郑昕茹
	 * @日期：2016-08-07
	 * **********************************************************************************/
	@RequestMapping("/saveMdifyBelongOperationItem")
	public ModelAndView saveMdifyBelongOperationItem(@RequestParam Integer id,HttpServletRequest request,@ModelAttribute AssetCabinetWarehouseAccess access, @RequestParam Integer modifyId){
		ModelAndView mav = new ModelAndView();
		AssetCabinetWarehouseAccess assetCabinetWarehouseAccess  = assetCabinetWarehouseAccessService.findAssetCabinetWarehouseAccessByPrimaryKey(id);
		assetCabinetWarehouseAccess.setType(access.getType());
		assetCabinetWarehouseAccess.setOperationItem(access.getOperationItem());
		assetCabinetWarehouseAccessService.saveAssetCabinetWarehouseAccess(assetCabinetWarehouseAccess);
		mav.setViewName("redirect:/asset/listModifyBelongOperationItem?id="+modifyId);
		return mav;
	}
}