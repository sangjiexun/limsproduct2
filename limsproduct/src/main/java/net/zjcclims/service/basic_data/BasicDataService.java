package net.zjcclims.service.basic_data;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*import net.zjcclims.domain.AssetManage;*/
import net.zjcclims.domain.SchoolDeviceChangeReport;
import net.zjcclims.domain.SchoolYear;

import org.springframework.transaction.annotation.Transactional;

public interface BasicDataService {
	/*************************************************************************************
	 * Description：报表导出txt{下载文件}
	 * 
	 * @author：岳茜
	 * @date：2016-8-2
	 *************************************************************************************/
	public void downLoad(File tempFile, String fileName,
			HttpServletResponse response);

	/*************************************************************************************
	 * Description：报表导出txt{生成文件}
	 * 
	 * @author：岳茜
	 * @date：2016-8-2
	 *************************************************************************************/
	public void contentToTxt(File tempFile, String content);

	/*************************************************************************************
	 * Description：基表模块-学年列表{获取学年的总记录数}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 *************************************************************************************/
	@Transactional
	public int getYearTotalRecords();

	/*************************************************************************************
	 * Description：基表模块-学年列表{查找所有的学年信息}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 *************************************************************************************/
	public Set<SchoolYear> findAllYears(int curr, int size);

	/************************************************************
	 * Description：基表模块-学年列表{获取所有学年}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 ************************************************************/
	public Map<Integer, String> findAllSchoolYearMap();

	/************************************************************
	 * Description：基表模块-学年列表{删除学年}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 ************************************************************/
	public void deleteYear(SchoolYear sy);

	/*************************************************************************************
	 * Description：基表模块-学年列表{根据学年的id查找学年}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 *************************************************************************************/
	public SchoolYear findYearById(Integer id);

	/************************************************************
	 * Description：基表模块-学年列表{保存学年}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 ************************************************************/
	public void saveYear(SchoolYear sy);

	/*************************************************************************************
	 * Description：基表模块-学年列表{根据学年的名称查找学年}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 *************************************************************************************/
	public Set<SchoolYear> findYearsByYearName(String yearName);

	/*********************************************************************************
	 * Description：基表模块-{查出教学科研仪器设备表里面的所有设备 school_device_report}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 ************************************************************************************/
	@SuppressWarnings("rawtypes")
	public List schoolDeviceReport(HttpServletRequest request, int page,
			int pageSize);

	/*********************************************************************************
	 * Description：基表模块-{导出excel，教学科研仪器设备表里面的所有设备 school_device_report}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 ************************************************************************************/
	public void exportReport(HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/*********************************************************************************
	 * Description：基表模块-{导出txt，教学科研仪器设备表里面的所有设备 school_device_report}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 ************************************************************************************/
	public void newExportTecEquipAVE(List<Object[]> listEquipments,
			File tempFile, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/********************************************************************************
	 * Description：基表模块-{查出教学科研仪器设备--count}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 *********************************************************************************/
	public int schoolDeviceReportCount(HttpServletRequest request);

	/*********************************************************************************
	 * Description：基表模块-{查出教学科研仪器设备表里面的贵重仪器设备 school_device_value 单价大于400000}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 ************************************************************************************/
	@SuppressWarnings("rawtypes")
	public List schoolDeviceValue(HttpServletRequest request, int page,
			int pageSize);

	/**************************************
	 * Description：基表模块-{查出教学科研仪器设备中的贵重仪器设备--count school_device_value}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 **************************************/
	public int schoolDeviceValueCount(HttpServletRequest request);
	
	/*********************************************************************************
     * Description：基表模块-{导出excel，教学科研仪器设备贵重仪器设备 school_device_value}
     * 
     * @author：魏来
     * @date：2016-8-24
     ************************************************************************************/
    public void exportSchoolDeviceValue(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
    
    /*********************************************************************************
	 * Description：基表模块-{导出txt，教学科研仪器设备贵重仪器设备表 school_device_value}
	 * 
	 * @author：魏来
	 * @date：2016-8-24
	 ************************************************************************************/
	public void newSchoolDeviceValueTecEquipAVE(List<Object[]> listEquipments,
			File tempFile, HttpServletRequest request,
			HttpServletResponse response) throws Exception; 

	/*********************************************************************************
	 * Description：基表模块-{教学科研仪器设备增减变动情况表 school_device_change}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 ************************************************************************************/
	public SchoolDeviceChangeReport schoolDeviceChange(HttpServletRequest request);
	/*********************************************************************************
	 * Description：基表模块-{查出school_device_change_report表里面的数据}
	 * 
	 * @author：岳茜
	 * @date：2016-9-6
	 ************************************************************************************/
	public List<SchoolDeviceChangeReport> schoolDeviceChangeReport(int yearcode);

	/********************************************************************************
	 * Description：基表模块-{查出教学科研仪器设备--count school_device_change}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 *********************************************************************************/
	public int schoolDeviceChangeCount(HttpServletRequest request);
	
	/*********************************************************************************
     * Description：基表模块-{导出excel，教学科研仪器设备 school_device_change}
     * 
     * @author：魏来
     * @date：2016-8-23
     ************************************************************************************/
	public void exportSchoolDeviceChangeReport(HttpServletRequest request,
			HttpServletResponse response,int yearcode) throws Exception;
    
    /*********************************************************************************
	 * Description：基表模块-{导出txt，教学科研仪器设备表 school_device_change}
	 * 
	 * @author：魏来
	 * @date：2016-8-23
	 ************************************************************************************/
	public void newSchoolDeviceChangeTecEquipAVE(List<SchoolDeviceChangeReport> listEquipments,
			File tempFile, HttpServletRequest request,
			HttpServletResponse response,int yearcode) throws Exception;

	/*********************************************************************************
     * Description：基表模块-{查出专任实验室人员列表 lab_team}
     * 
     * @author：魏来
     * @date：2016-8-2
     ************************************************************************************/
    @SuppressWarnings("rawtypes")
    public List labTeam(HttpServletRequest request, int page, int pageSize);
    
    /*********************************************************************************
     * Description：基表模块-{导出excel，专任实验室人员表 lab_team}
     * 
     * @author：魏来
     * @date：2016-8-2
     ************************************************************************************/
    public void exportLabTeam(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
    
    /*********************************************************************************
	 * Description：基表模块-{导出txt，专任实验室人员基表 lab_team}
	 * 
	 * @author：魏来
	 * @date：2016-8-5
	 ************************************************************************************/
	public void newLabTeamTecEquipAVE(List<Object[]> listEquipments,
			File tempFile, HttpServletRequest request,
			HttpServletResponse response) throws Exception; 
	
	/*********************************************************************************
     * Description：基表模块-{查出教学实验项目表 opeation_item_teaching}
     * 
     * @author：魏来
     * @date：2016-8-8
     ************************************************************************************/
    @SuppressWarnings("rawtypes")
    public List operationItemTeaching(HttpServletRequest request, int page, int pageSize);
    /***********************************************************************************
	 * Description：基表模块-{查出教学实验项目--count}
	 * 
	 * @author：岳茜
	 * @date：2016-8-1
	 ***********************************************************************************/
	public int operationItemTeachingCount(HttpServletRequest request);
    
    /*********************************************************************************
     * Description：基表模块-{导出excel，教学实验项目表 opeation_item_teaching}
     * 
     * @author：魏来
     * @date：2016-8-8
     ************************************************************************************/
    public void exportOperationItemTeaching(HttpServletRequest request,
			HttpServletResponse response,int yearCode) throws Exception;
    
    /*********************************************************************************
	 * Description：基表模块-{导出txt，教学实验项目表 opeation_item_teaching}
	 * 
	 * @author：魏来
	 * @date：2016-8-8
	 ************************************************************************************/
	public void newOperationItemTeachingTecEquipAVE(List<Object[]> listEquipments,
			File tempFile, HttpServletRequest request,
			HttpServletResponse response,int yearCode) throws Exception; 
	
	/*********************************************************************************
     * Description：基表模块-{实验室基本情况表 lab_basic}
     * 
     * @author：魏来
     * @date：2016-8-10
     ************************************************************************************/
    @SuppressWarnings("rawtypes")
    public List labBasic(HttpServletRequest request, int page, int pageSize);
    
    /*********************************************************************************
     * Description：基表模块-{导出excel，实验室基本情况表 lab_basic}
     * 
     * @author：魏来
     * @date：2016-8-10
     ************************************************************************************/
    public void exportLabBasic(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
    
    /*********************************************************************************
	 * Description：基表模块-{导出txt，实验室基本情况表 lab_basic}
	 * 
	 * @author：魏来
	 * @date：2016-8-8
	 ************************************************************************************/
	public void newLabBasicTecEquipAVE(List<Object[]> listEquipments,
			File tempFile, HttpServletRequest request,
			HttpServletResponse response) throws Exception; 
	
	/*********************************************************************************
     * Description：基表模块-{实验室经费情况表 lab_room_report_fund}
     * 
     * @author：魏来
     * @date：2016-8-10
     ************************************************************************************/
	 @SuppressWarnings("rawtypes")
	    public List labRoomReportFund(HttpServletRequest request, int page, int pageSize);
	 
	 /*********************************************************************************
	     * Description：基表模块-{导出excel，实验室经费情况表 lab_room_report_fund}
	     * 
	     * @author：魏来
	     * @date：2016-8-10
	     ************************************************************************************/
	    public void exportLabRoomReportFund(HttpServletRequest request,
				HttpServletResponse response) throws Exception;
	    
	    /*********************************************************************************
		 * Description：基表模块-{导出txt，实验室经费情况表 lab_room_report_fund}
		 * 
		 * @author：魏来
		 * @date：2016-8-8
		 ************************************************************************************/
		public void newLabRoomReportFundTecEquipAVE(List<Object[]> listEquipments,
				File tempFile, HttpServletRequest request,
				HttpServletResponse response) throws Exception; 
		 /*********************************************************************************
		 * Description：基表模块-{根据购买年（yearId）查找该年的设备变化量， view_school_device_change}
		 * 
		 * @author：岳茜
		 * @date：2016-8-30
		 ************************************************************************************/
		
		/*************************************************************************************
		 * Description：基表模块-资产管理{获取资产管理的总记录数}
		 * 
		 * @author：郑昕茹
		 * @date：2016-11-21
		 *************************************************************************************/ 
		public int getAllAssetManage();
		
		/*************************************************************************************
		 * Description：基表模块-资产管理{获取资产管理的分页记录数}
		 * 
		 * @author：郑昕茹
		 * @date：2016-11-21
		 *************************************************************************************/ 
		/*public List<AssetManage> getAssetManage(AssetManage assetManage, int currpage, int pageSize);*/
	     
		
		/*********************************************************************************
		 * Description：基表模块-{导出txt，学年}
		 * 
		 * @author：郑昕茹
		 * @date：2016-11-21
		 ************************************************************************************/
		public void newExportTxtYear(File tempFile, HttpServletRequest request,
				HttpServletResponse response) throws Exception;
		
		/*********************************************************************************
		 * Description：基表模块-{导出excel，教学科研仪器设备表里面的所有设备 school_device_report，本学年}
		 * 
		 * @author：岳茜
		 * @date：2016-8-1
		 ************************************************************************************/
		public void exportReportCurrYear(HttpServletRequest request,
				HttpServletResponse response,int yearcode) throws Exception;
		
		/*********************************************************************************
		 * Description：基表模块-{导出excel，教学科研仪器设备表贵重仪器设备 school_device_value}
		 * 
		 * @author：魏来
		 * @date：2016-8-24
		 ************************************************************************************/
		@SuppressWarnings("unchecked")
		public void exportCurrYearSchoolDeviceValue(HttpServletRequest request,
				HttpServletResponse response) throws Exception ;
		
		
		/*********************************************************************************
		 * Description：基表模块-{导出excel，教学科研仪器设备表里面的所有设备 school_device_report，本学年}
		 * 
		 * @author：岳茜
		 * @date：2016-8-1
		 ************************************************************************************/
		public void exportReportTxtCurrYear(HttpServletRequest request,
				HttpServletResponse response,int yearcode) throws Exception;
}