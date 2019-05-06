package net.zjcclims.service.software;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zjcclims.dao.CommonDocumentDAO;
import net.zjcclims.dao.LabRoomDAO;
import net.zjcclims.dao.SoftwareDAO;
import net.zjcclims.dao.SoftwareItemRelatedDAO;
import net.zjcclims.dao.SoftwareReserveDAO;
import net.zjcclims.dao.SoftwareRoomRelatedDAO;
import net.zjcclims.domain.Authority;
import net.zjcclims.domain.CommonDocument;
import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.LabRoomDeviceRepair;
import net.zjcclims.domain.SchoolDevice;
import net.zjcclims.domain.Software;
import net.zjcclims.domain.SoftwareItemRelated;
import net.zjcclims.domain.SoftwareReserve;
import net.zjcclims.domain.SoftwareRoomRelated;
import net.zjcclims.domain.User;
import net.zjcclims.service.common.ShareService;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.inject.persist.Transactional;

import excelTools.ExcelUtils;
import excelTools.JsGridReportBase;
import excelTools.TableData;

@Service("SoftwareService")
public class SoftwareServiceImpl implements SoftwareService {
	@Autowired
	private SoftwareDAO softwareDAO;
	@Autowired
	private CommonDocumentDAO documentDAO;
	@Autowired
	private SoftwareItemRelatedDAO softwareItemRelatedDAO;
	@Autowired
	private SoftwareRoomRelatedDAO softwareRoomRelatedDAO;
	@Autowired
	private ShareService shareService;
	
	@Autowired
	private SoftwareReserveDAO softwareReserveDAO;
	@Autowired
	private LabRoomDAO labRoomDAO;

	/****************************************************************************
	 * Description：获取软件数量
	 * 
	 * @author： 邵志峰
	 * @date: 2017-06-02
	 ****************************************************************************/
	@Transactional
	public Integer countSoftwares() {
		return ((Long) softwareDAO.createQuerySingleResult("select count(o) from Software o").getSingleResult()).intValue();
	}

	/****************************************************************************
	 * Description：查找所有软件信息
	 * 
	 * @param：startResult 当前页数
	 * @param： maxRows 总页数
	 * @author: 邵志峰
	 * @date: 2017-06-02
	 ****************************************************************************/
	@Transactional
	public List<Software> findAllSoftwares(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<Software>(softwareDAO.findAllSoftwares(startResult, maxRows));
	}

	/****************************************************************************
	 * description：查询Software
	 * 
	 * @author：杨礼杰
	 * @date：2017-6-23
	 ****************************************************************************/
	public List<Software> findAllSoftwareByQuery(Integer currPage,
			Integer pageSize, Software software) {
		String sql = "select distinct w from Software w left join w.softwareRoomRelateds s where 1=1";
		String roomName = "";
		if (software != null && software.getLabRoom() != null) {
			roomName = software.getLabRoom();
			sql += " and s.labRoom.labRoomName like '%" + roomName + "%'";
		}

		//筛选无加密狗的
		//hql.append(" and w.dongle= 0");
		if (software != null && software.getName() != null
				&& !"".equals(software.getName())) {
			sql+=" and w.name like '%" + software.getName() + "%'";
		}
		
		/*String sql="Select l from LabRoom l where labRoomName like '%"+roomName+"%'";
		List<LabRoom> labRooms=labRoomDAO.executeQuery(sql);
		List<Integer> labRoomId=new ArrayList<Integer>();
		 StringBuilder ids = new StringBuilder();
		for(LabRoom lr:labRooms){
			labRoomId.add(lr.getId());
		}
		for(int i = 0; i < labRoomId.size(); i++) {
			ids.append(labRoomId.get(i));    
		}*/
		sql+=" order by w.id desc";
		List<Software> softwares = softwareDAO.executeQuery(sql,
				(currPage - 1) * pageSize, pageSize);
		return softwares;
	}

	/****************************************************************************
	 * Description:根据主键查找软件信息
	 * 
	 * @param： idKey 软件编号
	 * @author 邵志峰
	 * @date: 2017-06-02
	 ****************************************************************************/
	@Transactional
	public Software findSoftwareByPrimaryKey(Integer id) {
		return softwareDAO.findSoftwareByPrimaryKey(id);
	}

	/****************************************************************************
	 * Description:软件管理--保存
	 * 
	 * @author 杨礼杰
	 * @date: 2017-06-23
	 ****************************************************************************/
	public Software saveSoftware(Software software) {
		return softwareDAO.store(software);
	}

	/****************************************************************************
	 * Description:软件管理--删除
	 * 
	 * @author 杨礼杰
	 * @date: 2017-06-23
	 ****************************************************************************/
	public void deleteSoftware(Software software) {
		softwareDAO.remove(software);
	}
	/****************************************************************************
	 * Description:实训中心列表——添加软件
	 *
	 * @author 周志辉
	 * @date: 2017-08-01
	 ****************************************************************************/
	@Override
	public List<Software> findSoftwareByRoomId(Integer id) {
		// TODO Auto-generated method stub
		String sql="select s from Software s,LabRoom lr,SoftwareRoomRelated srr where s.id=srr.software.id and lr.id=srr.labRoom.id and lr.id="+id;		
		return softwareDAO.executeQuery(sql,0,-1);
	}
	/****************************************************************************
	 * Description:根据学院编号和Software对象查询软件数量
	 *
	 * @author 周志辉
	 * @date: 2017-08-01
	 ****************************************************************************/
	@Override
	public int findSoftwareByAcademyNumberAndSoftware(String academyNumber,
			Software software,Integer roomId) {
		// TODO Auto-generated method stub
		String sql="select count(*) from Software d where 1=1";
//		String sql="select count(*) from Software d where d.softwareRoomRelateds.labRoom.id <>"+roomId;
		if(software!=null){
			if(software.getName()!=null&&!software.getName().equals("")){
				sql+=" and d.name like '%"+software.getName()+"%'";
			}
			/*if(software.getEdition()!=null&&!software.getEdition().equals("")){//设备编号
				sql+=" and d.edition ="+software.getEdition();
			}*/
		}
		return ((Long) softwareDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	/****************************************************************************
	 * 功能：根据学院编号和Software对象查询软件并分页
	 * 作者：周志辉
	 * 时间：2017-08-01
	 ****************************************************************************/
	@Override
	public List<Software> findSoftwareByAcademyNumberAndSoftware(
			String academyNumber, Software software,Integer roomId, Integer page, int pageSize) {
		// TODO Auto-generated method stub
		String sql="select d from Software d where 1=1";
//		String sql="select d from Software d where d.softwareRoomRelateds.labRoom.id <>"+roomId;
		if(software!=null){
			if(software.getName()!=null&&!software.getName().equals("")){
				sql+=" and d.name like '%"+software.getName()+"%'";
			}
//			if(software.getEdition()!=null&&!software.getEdition().equals("")){
//				sql+=" and d.edition ="+software.getEdition();
//			}
		}
		return softwareDAO.executeQuery(sql, (page-1)*pageSize,pageSize);
	}
	
	/****************************************************************************
	 * 功能：根据id查询软件
	 * 作者：周志辉
	 * 时间：2017-08-01
	 ****************************************************************************/
	@Override
	public Software findSoftwareByPrimaryKey1(Integer id) {
		// TODO Auto-generated method stub
		return softwareDAO.findSoftwareByPrimaryKey(id);
	}
	
	/****************************************************************************
	 * Description 根據軟件&項目查詢關係記錄
	 * 
	 * @param softwareId 軟件主鍵
	 * @param itemId 項目主鍵
	 * @return 對象
	 * @author 陳樂為
	 * @date 2017年9月9日
	 ****************************************************************************/
	@Override
	public SoftwareItemRelated getItemRelatedByQuery(Integer softwareId, Integer itemId) {
		String sql = "select c from SoftwareItemRelated c where c.software.id="+softwareId;
		sql += " and c.operationItem.id="+itemId;
		List<SoftwareItemRelated> softwareItemRelateds = softwareItemRelatedDAO.executeQuery(sql);
		if(softwareItemRelateds != null && softwareItemRelateds.size() > 0) {
			SoftwareItemRelated softwareItemRelated = softwareItemRelateds.get(0);
			return softwareItemRelated;
		}else {
			return null;
		}
	}
	
	/****************************************************************************
	 * Description 根據軟件&實訓室查詢關係記錄
	 * 
	 * @param softwareId 軟件
	 * @param roomId 實訓室主鍵
	 * @return 對象
	 * @author 陳樂為
	 * @date 2017年9月9日
	 ****************************************************************************/
	@Override
	public SoftwareRoomRelated getRoomRelatedByQuery(Integer softwareId, Integer roomId) {
		String sql = "select r from SoftwareRoomRelated r where r.software.id="+softwareId;
		sql += " and r.labRoom.id="+roomId;
		List<SoftwareRoomRelated> softwareRoomRelateds = softwareRoomRelatedDAO.executeQuery(sql);
		if(softwareRoomRelateds != null && softwareRoomRelateds.size() > 0) {
			SoftwareRoomRelated softwareRoomRelated = softwareRoomRelateds.get(0);
			return softwareRoomRelated;
		}else {
			return null;
		}
	}
	/****************************************************************************
	 * 功能：上传附件
	 * 作者：周志辉
	 ****************************************************************************/
	@Override
	public void softwareDocumentUpload(HttpServletRequest request,
			HttpServletResponse response, Integer id,int type) {
		String sep = System.getProperty("file.separator"); 
		String path = sep+ "upload"+ sep+"software"+sep+id;
		if(type==1)
		{
			shareService.uploadFiles(request, path,"saveSoftwareInstallDocument",id);
		}else if(type==2){
			shareService.uploadFiles(request, path,"saveSoftwareUseDocument",id);
		}
		
	}
	/****************************************************************************
	 * 功能：保存实验室软件安装说明的文档
	 * 作者：周志辉
	 * 时间：2017-09-18
	 ****************************************************************************/
	@Override
	public void saveSoftwareDocument(String fileTrueName,
			Integer softwareId, Integer type) {
		
		 Software software=softwareDAO.findSoftwareByPrimaryKey(softwareId);
		 CommonDocument doc=new CommonDocument();
		 doc.setType(type);
		 doc.setDocumentName(fileTrueName);
		 String imageUrl="upload/software/"+softwareId+"/"+fileTrueName;
		 doc.setDocumentUrl(imageUrl);
		 doc.setSoftware(software);
		 documentDAO.store(doc);
	}
	/****************************************************************************
	 * 功能：下载软件文档
	 * 作者：周志辉
	 ****************************************************************************/
	@Override
	public void downloadFile(CommonDocument doc, HttpServletRequest request,
			HttpServletResponse response) {
		try{			
			String fileName=doc.getDocumentName();
			String root = System.getProperty("zjcclims.root");
			FileInputStream fis = new FileInputStream(root+doc.getDocumentUrl());
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
	
	
	/****************************************************************************
	 * Description:查看软件安装申请
	 *
	 * @author 张愉
	 * @date: 2017-09-30
	 ****************************************************************************/
	@Override
	public SoftwareReserve findSoftwareReserveById(Integer id) {
		// TODO Auto-generated method stub
		return softwareReserveDAO.findSoftwareReserveByPrimaryKey(id);
	}
	
	/****************************************************************************
	 * Description:查找实验室的软件
	 *
	 * @author 张愉
	 * @date: 2017-10-30
	 ****************************************************************************/
	@Override
	public List<SoftwareRoomRelated> findSoftwareRoomRelatedByRoomId(Integer roomId) {
		// TODO Auto-generated method stub
		String sql = "select r from SoftwareRoomRelated r where r.software.id="+roomId;
		List<SoftwareRoomRelated> softwareRoomRelateds = softwareRoomRelatedDAO.executeQuery(sql);
		return softwareRoomRelateds;
	}
	
	/****************************************************************************
	 * @功能：导出查询到的所有软件(分sheet导出)
	 * @作者：张愉
	 * @日期：2018-1-3
	 ****************************************************************************/
	@Override
	public void exportSoftList(List<Software> listSoftware,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//格式化时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				//新建一个mapList集合
				List<Map> mapList = new ArrayList<Map>();
				for(Software software : listSoftware){
					// 新建一个HashMap对象
					Map<String, String> map = new HashMap<>();
					// 软件编号
					if (null!=software.getCode()){
						map.put("softCode",software.getCode());
					}else{
						map.put("softCode","");
					}
					//软件名称
					if(software.getName()!= null){
						map.put("softName",software.getName());
					}else{
						map.put("softName", "");
					}
					
					//软件版本
					if(software.getEdition()!= null){
						map.put("edition", software.getEdition());
					}else{
						map.put("edition", "");
					}
					
					//所属实验室
					String s="";
					if(software.getSoftwareRoomRelateds()!=null){
						for(SoftwareRoomRelated swrr:software.getSoftwareRoomRelateds()){
								LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(swrr.getLabRoom().getId());
								String []array=labRoom.getLabRoomName().split(" ");
								if (array.length>0){
									for (String anArray : array) {
										s = s + anArray+" ";
										map.put("labRoom", s);
									}
								}
							}
					}else{
						map.put("labRoom", "");
					}
					
					//有无加密狗
					if(software.getDongle()!= null){
						if(software.getDongle()==1){
							map.put("dongle", "有");
						}
						if(software.getDongle()==0){
							map.put("dongle","无");
						}
					}else{
						map.put("dongle", "");
					}
					
					//点数
					if(software.getPoints()!= null){
						map.put("points", software.getPoints().toString());
					}else{
						map.put("points", "");
					}
					//供应商
					if(software.getSupplier()!= null){
						map.put("supplier",software.getSupplier());
					}else{
						map.put("supplier", "");
					}
					//供应商联系方式
					if(software.getSupplierTel()!= null){
						map.put("supplierTel",software.getSupplierTel());
					}else{
						map.put("supplierTel", "");
					}

					mapList.add(map);
					
				}
				
				//新建一个用来存放分sheet的List对象
				List<List<Map>> wrapList = new ArrayList<>();
				//定义一个sheet的最大条目容量
				int quantity = 60000;
				//定义起点坐标
				int count = 0;
				while (count < mapList.size()) {//判断equipments的容量能够分割成几个规定容量的List
					wrapList.add(new ArrayList(mapList.subList(count, (count + quantity) > mapList.size() ? mapList.size() : count + quantity)));
					count += quantity;
				}
				
				//给表设置名称
				String title = "教学软件管理列表";
				//给表设置表头名
				String[] hearders = new String[] {"软件编号","软件名称", "软件版本", "所属实验室", "有无加密狗","点数","供应商","供应商联系方式"};
				//属性数组，写数据到excel时的顺序定位
				String[] fields = new String[] {"softCode","softName", "edition","labRoom","dongle","points","supplier","supplierTel"};
				//新建一个TableData的集合
				List<TableData> tableDataList = new ArrayList<TableData>();
				for(List<Map> tempList : wrapList){//将所需导出的数据集合遍历并拼接表头信息
					TableData td = ExcelUtils.createTableData(tempList, ExcelUtils.createTableHeader(hearders), fields);
					tableDataList.add(td);
				}
				JsGridReportBase report = new JsGridReportBase(request, response);
				
				report.exportToExcelForSheets(title, shareService.getUserDetail().getCname(), tableDataList);
		
	}
	
	/**************************************************************************************
     * description：导入软件
     * @author：张愉
     * @date：2018-1-3
     **************************************************************************************/
	public void importSoft(String File){
		Boolean isE2007=false;
		if(File.endsWith("xlsx")){
			isE2007=true;
		}
		//建立输入流
		try {
			//建立输入流
			InputStream input = new FileInputStream(File);
			Workbook wb =null;
			if(isE2007){
				wb=new XSSFWorkbook(input);
			}else{
				wb=new HSSFWorkbook(input);
			}
			//获取第一个表单数据
			Sheet sheet= wb.getSheetAt(0);
			//获取第一个表单迭代器
			Iterator<Row>rows=sheet.rowIterator();
			Row rowContent=null;// 表头
			String softCode = ""; //软件编号
			String softName ="";//软件名称
			String edition="";//软件版本
			String labRoom="";//所属实验室
			String dongle ="";//有无加密狗
			String points="";//点数
			String supplier="";//供应商
			String supplierTel ="";//供应商联系方式
			int a=0;
			while(rows.hasNext()){
				 softCode = "";//软件编号
				 softName ="";//软件名称
				 edition="";//软件版本
				 labRoom="";//所属实验室
				 dongle ="";//有无加密狗
				 points="";//点数
				 supplier="";//供应商
				 supplierTel ="";//供应商联系方式
				if(a==0){
					rowContent=rows.next();
					a=1;
				}
				Row row =rows.next();
				int column=sheet.getRow(0).getPhysicalNumberOfCells();
				//chName ="";//品名
 				for(int k=0;k<column;k++){
					if(row.getCell(k)!=null){
						row.getCell(k).setCellType(Cell.CELL_TYPE_STRING);
						String columnName = rowContent.getCell(k).getStringCellValue();
						String content = row.getCell(k).getStringCellValue();
						if(columnName.equals("软件编号")){
							softCode = content;
						}
						if(columnName.equals("软件名称")){
							softName = content;
						}
						if(columnName.equals("软件版本")){
							edition = content;
						}
						if(columnName.equals("所属实验室")){
							labRoom = content;
						}
						if (columnName.equals("有无加密狗")) {
							dongle = content;
						}
						if (columnName.equals("点数")) {
							points = content;
						}
						if (columnName.equals("供应商")) {
							supplier = content;
						}
						if(columnName.equals("供应商联系方式")){
							supplierTel = content;
						}
					}
				}

						Software u = new Software();
						if(!softCode.equals("")){
							u.setCode(softCode);
						}
						if(!softName.equals("")){
							u.setName(softName);
						}
						if(!edition.equals("")){
							u.setEdition(edition);
						}
						if(!labRoom.equals("")){
							//将导入模板中实验室名称转换为id并拼入字段中
							Set<LabRoom> labRooms = (Set<LabRoom>) labRoomDAO.findLabRoomByLabRoomName(labRoom);
							//如果模板中的实验室不存在，则写空字符串
							if (labRooms!=null) {
								StringBuffer allLabRoom = new StringBuffer("");
								for (LabRoom tmp : labRooms) {
									allLabRoom.append(tmp.getId() + ",");
								}
								//删除最后一个字符
								allLabRoom.deleteCharAt(allLabRoom.length() - 1);
								u.setLabRoom(String.valueOf(allLabRoom));
							}else{
								u.setLabRoom("");
							}
						}
						if(!dongle.equals("")){
							if(dongle.equals("有")){
								u.setDongle(1);
							}
							if(dongle.equals("无")){
								u.setDongle(0);
							}
						}
						if(!points.equals("")) {
							u.setPoints(Integer.valueOf(points));
						}
						if(!supplier.equals("")){
							u.setSupplier(supplier);
						}
						if(!supplierTel.equals("")){
							u.setSupplierTel(supplierTel);
						}
					
						u = softwareDAO.store(u);
						softwareDAO.flush();
						if(!labRoom.equals("")) {
							Set<LabRoom> labRooms = (Set<LabRoom>) labRoomDAO.findLabRoomByLabRoomName(labRoom);
							for (LabRoom tmp : labRooms) {
								SoftwareRoomRelated softwareRoomRelated = new SoftwareRoomRelated();
								softwareRoomRelated.setSoftware(u);
								softwareRoomRelated.setLabRoom(tmp);
								softwareRoomRelatedDAO.store(softwareRoomRelated);
								softwareRoomRelatedDAO.flush();
							}
						}
//						if(!labRoom.equals("")){
//							SoftwareRoomRelated softwareRoomRelated=new SoftwareRoomRelated();
//							softwareRoomRelated.setSoftware(u);
//							softwareRoomRelated.setLabRoom(labRoomDAO.findLabRoomByPrimaryKey(Integer.valueOf(labRoom)));
//							softwareRoomRelatedDAO.store(softwareRoomRelated);
//							softwareRoomRelatedDAO.flush();
//						}
					}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	
	}
    /****************************************************************************
     * description：软件总记录
     *
     * @author：廖文辉
     * @date：2018-11-30
     ****************************************************************************/
    public List<Software> findSoftwareByQuery(Integer currPage,
                                                 Integer pageSize, Software software,HttpServletRequest request) {
        String sql = "select distinct w from Software w left join w.softwareRoomRelateds s where 1=1";
        String roomName = request.getParameter("labRoom");
        if(request.getParameter("labRoom")!=null&&!"".equals(roomName)){
            sql+=" and s.labRoom.labRoomName like '%" +roomName+"%'";
        }
        //筛选无加密狗的
        //hql.append(" and w.dongle= 0");
        if (software != null && software.getName() != null
                && !"".equals(software.getName())) {
            sql+=" and w.name like '%" + software.getName() + "%'";
        }

		/*String sql="Select l from LabRoom l where labRoomName like '%"+roomName+"%'";
		List<LabRoom> labRooms=labRoomDAO.executeQuery(sql);
		List<Integer> labRoomId=new ArrayList<Integer>();
		 StringBuilder ids = new StringBuilder();
		for(LabRoom lr:labRooms){
			labRoomId.add(lr.getId());
		}
		for(int i = 0; i < labRoomId.size(); i++) {
			ids.append(labRoomId.get(i));
		}*/
        sql+=" order by w.id desc";
        List<Software> softwares = softwareDAO.executeQuery(sql,
                (currPage - 1) * pageSize, pageSize);
        return softwares;
    }

	/******************************************************************
	 * Description:保存软件申请表
	 * @param：softwarereserve 软件申请表
	 * @author：邵志峰
	 * @date:2017-05-31
	 *****************************************************************/
	@org.springframework.transaction.annotation.Transactional
	public SoftwareReserve saveSoftwareReserve(SoftwareReserve softwarereserve) {

		softwarereserve = softwareReserveDAO.store(softwarereserve);

		softwareReserveDAO.flush();
		return softwarereserve;
	}

}