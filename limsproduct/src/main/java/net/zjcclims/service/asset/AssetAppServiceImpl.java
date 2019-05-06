package net.zjcclims.service.asset;

import java.util.List;

import jxl.write.DateTime;

import net.zjcclims.dao.AssetAppAuditDAO;
import net.zjcclims.dao.AssetAppDAO;
import net.zjcclims.dao.AssetAppDateDAO;
import net.zjcclims.dao.AssetAppRecordDAO;
import net.zjcclims.dao.AssetDAO;
import net.zjcclims.dao.OperationItemDAO;
import net.zjcclims.dao.OperationOutlineDAO;
import net.zjcclims.domain.AssetApp;
import net.zjcclims.domain.AssetAppAudit;
import net.zjcclims.domain.AssetAppDate;
import net.zjcclims.domain.AssetAppRecord;
import net.zjcclims.domain.Authority;
import net.zjcclims.domain.OperationItem;
import net.zjcclims.domain.OperationOutline;
import net.zjcclims.domain.User;
import net.zjcclims.service.common.ShareService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;


@Service("AssetAppService")
public class AssetAppServiceImpl implements  AssetAppService {

	@Autowired ShareService shareService;
	@Autowired AssetDAO assetDAO;
	@Autowired AssetAppDAO assetAppDAO;
	@Autowired AssetAppRecordDAO assetAppRecordDAO;
	@Autowired AssetAppAuditDAO assetAppAuditDAO;
	@Autowired AssetAppDateDAO assetAppDateDAO;
	@Autowired OperationItemDAO operationItemDAO;
	@Autowired OperationOutlineDAO operationOutlineDAO;
	/***********************************************************************************
	 * @description 药品溶液管理-药品申购｛显示所有的药品审核信息｝
	 * @author 徐文
	 * @date 2016-08-08
	 * **********************************************************************************/
	public List<AssetApp> findAssetApps(Integer currpage,Integer pageSize,Integer assetAuditStatus,AssetApp assetApp, User user,String startDate,String endDate){
		String sql="select a from AssetApp a where 1=1";//获取所有的设备申购信息
		if(user != null && user.getUsername() != null && !user.getUsername().equals("")){
			sql +=  " and user.username ='" + user.getUsername()+"'";
		}
		
		/*User currUser = shareService.getUser();
		String judge = ",";
        for (Authority authority : currUser.getAuthorities()) {
            judge = judge + "," + authority.getId() + ",";
        }
        if(judge.indexOf(",11,") == -1 && judge.indexOf(",18,") != -1 && judge.indexOf(",20,") == -1 ){
        	sql  += " and a.userByFirstAuditUser.username ='"+currUser.getUsername()+"' and a.assetAuditStatus = 3";
        }
        if(judge.indexOf(",11,") == -1 && judge.indexOf(",18,") == -1 && judge.indexOf(",20,") != -1 ){
        	sql  += " and a.assetAuditStatus = 4";
        }
        if(judge.indexOf(",11,") == -1 && judge.indexOf(",18,") != -1 && judge.indexOf(",20,") != -1 ){
        	sql  += " and ( a.assetAuditStatus = 4"
        		 +	" or a.userByFirstAuditUser.username ='"+currUser.getUsername()+"' and a.assetAuditStatus = 3)";
        }*/
		//筛选
		if (assetAuditStatus!=9) {
			sql+="and a.assetAuditStatus like '"+assetAuditStatus+"' ";
		}
		//查询
		if(assetApp != null && assetApp.getAppNo() != null && !assetApp.getAppNo().equals("")){
			sql+=" and appNo like '%"+assetApp.getAppNo()+"%'"; 
		}
		if(startDate != null && endDate != null && !startDate.equals("")&&!endDate.equals("")){
			sql += " and appDate between '"+ startDate + "' and '"+endDate+"'";
		}
		if(assetApp != null && assetApp.getUser() != null && assetApp.getUser().getCname() != null && !assetApp.getUser().getCname().equals("")){
			sql +=  " and user.cname ='" + assetApp.getUser().getCname()+"'";
		}
		List<AssetApp> listAssetApps = assetAppDAO.executeQuery(sql, (currpage-1)*pageSize, pageSize);
		return listAssetApps;
	}
	
	/***********************************************************************************
     * @功能：保存申购信息
     * @author 徐文
     * @日期：2016-08-08
     * **********************************************************************************/
    public AssetApp saveAssetApp(AssetApp assetApp){
        return assetAppDAO.store(assetApp);
    }
    /***********************************************************************************
	 * @description 药品溶液管理-药品申购｛通过主键找到药品申购信息｝
	 * @author 郑昕茹
	 * @date 2016-08-08
	 * **********************************************************************************/
	public AssetApp findAssetAppByPrimaryKey(Integer id){
		return assetAppDAO.findAssetAppByPrimaryKey(id);
	}
	/***********************************************************************************
	 * @description 药品溶液管理-药品申购｛通过主键删除药品申购信息｝
	 * @author 郑昕茹
	 * @date 2016-08-08
	 * **********************************************************************************/
	public boolean deleteAssetApp(AssetApp assetApp){
		if(assetApp != null){
			assetAppDAO.remove(assetApp);
			assetAppDAO.flush();
		} 
		return false;
	}
	/************************************************************************************
     * @功能：保存申购记录信息
     * @author 徐文
     * @日期：2016-08-09
     * **********************************************************************************/
	@Transactional
    public AssetAppRecord saveAssetAppRecord(AssetAppRecord assetAppRecord){
    	return assetAppRecordDAO.store(assetAppRecord);
    }
	/***********************************************************************************
     * @功能：保存申购审核信息
     * @author 徐文
     * @日期：2016-08-10
     * **********************************************************************************/
    public AssetAppAudit saveAssetAppAudit(AssetAppAudit assetAppAudit){
    	return assetAppAuditDAO.store(assetAppAudit);
    }
    
    /***********************************************************************************
  	 * @description 药品溶液管理-药品申购｛通过主键找到药品申购记录信息｝
  	 * @author 郑昕茹
  	 * @date 2016-08-15
  	 * **********************************************************************************/
  	public AssetAppRecord findAssetAppRecordByPrimaryKey(Integer id){
  		return assetAppRecordDAO.findAssetAppRecordByPrimaryKey(id);
  	}
  	
  	/***********************************************************************************
	 * @description 药品溶液管理-药品申购｛显示所有通过审核，需要入库的药品审核信息｝
	 * @author 郑昕茹
	 * @date 2016-08-15
	 * **********************************************************************************/
	public List<AssetApp> findAssetAppsNeedStock(Integer currpage,Integer pageSize,AssetApp assetApp, Integer stockStatus){
		String sql="select a from AssetApp a where 1=1 and assetAuditStatus = 1";//获取所有通过审核的设备申购信息
		//查询
		if(assetApp != null && assetApp.getAppNo() != null && !assetApp.getAppNo().equals("")){
			sql+=" and appNo like '%"+assetApp.getAppNo()+"%'"; 
		}
		//查询
		if(assetApp != null && assetApp.getAppNo() != null && !assetApp.getAppNo().equals("")){
				sql+=" and appNo like '%"+assetApp.getAppNo()+"%'"; 
		}
		if(stockStatus == 1 || stockStatus == 0){
			sql+=" and stockStatus like '%"+stockStatus.toString()+"%'"; 
		}
		List<AssetApp> listAssetApps = assetAppDAO.executeQuery(sql, (currpage-1)*pageSize, pageSize);
		return listAssetApps;
	}
	
	/***********************************************************************************
	 * @description 药品溶液管理-药品申购｛生成从0000开始到9999的数｝
	 * @author 郑昕茹
	 * @date 2016-08-17
	 * **********************************************************************************/
	public String getNumber(String lastNo){
		Integer i = Integer.parseInt(lastNo) + 1;
		int len = i.toString().length();
		String nowNo="";
		switch (len){
		case 1:
			nowNo="000"+i.toString();
		break;
		case 2:
			nowNo="00"+i.toString();
		break;
		case 3:
			nowNo="0"+i.toString();
		break;
		case 4:
			nowNo= i.toString();
		break;
		}
		return nowNo;
	}

	/***********************************************************************************
	 * @description 药品溶液管理-药品申购｛找到编号最大的申购编号的后四位｝
	 * @author 郑昕茹
	 * @date 2016-08-17
	 * **********************************************************************************/
	public String findAppNo(){
		String sql = "select a from AssetApp a where 1=1 order by appNo";
		List<AssetApp> assetApps = assetAppDAO.executeQuery(sql,0,-1);
		AssetApp assetApp=null;
		String no = "0000";
		if(assetApps.size()>0){
			assetApp = assetApps.get(assetApps.size()-1);
			no = assetApp.getAppNo().substring(assetApp.getAppNo().length()-4);
		}
		return no;
	}
	
	/***********************************************************************************
	 * @description 药品溶液管理-药品申购｛保存设置的提交时间｝
	 * @author 郑昕茹
	 * @date 2016-08-17
	 * **********************************************************************************/
    public AssetAppDate saveAssetAppDate(AssetAppDate assetAppDate){
        return assetAppDateDAO.store(assetAppDate);
    }
    
    /***********************************************************************************
   	 * @description 药品溶液管理-药品申购｛查找设置的提交时间｝
   	 * @author 郑昕茹
   	 * @date 2016-08-17
   	 * **********************************************************************************/
       public List<AssetAppDate> findAssetAppDate( ){
    	   String sql = "select a from AssetAppDate a where 1=1 ";
    	   return assetAppDateDAO.executeQuery(sql,0,-1);
       }
       
       /***********************************************************************************
   	 * @description 药品溶液管理-药品申购｛找到当前页面所有的申购人｝
   	 * @author 郑昕茹
   	 * @date 2016-08-20
   	 * **********************************************************************************/
   	public List<AssetApp> findAssetAppsGroupByUsers(Integer currpage,Integer pageSize,Integer assetAuditStatus,AssetApp assetApp, User user,String startDate,String endDate){
   		String sql="select a from AssetApp a where 1=1";//获取所有的设备申购信息
   		if(user != null && user.getUsername() != null && !user.getUsername().equals("")){
   			sql +=  " and user.username ='" + user.getUsername()+"'";
   		}
   		//筛选
   		if (assetAuditStatus!=9) {
   			sql+="and a.assetAuditStatus like '"+assetAuditStatus+"' ";
   		}
   		//查询
   		if(assetApp != null && assetApp.getAppNo() != null && !assetApp.getAppNo().equals("")){
   			sql+=" and appNo like '%"+assetApp.getAppNo()+"%'"; 
   		}
   		if(startDate != null && endDate != null && !startDate.equals("")&&!endDate.equals("")){
   			sql += " and appDate between '"+ startDate + "' and '"+endDate+"'";
   		}
   		if(assetApp != null && assetApp.getUser() != null && assetApp.getUser().getCname() != null && !assetApp.getUser().getCname().equals("")){
   			sql +=  " and user.cname ='" + assetApp.getUser().getCname()+"'";
   		}
   		sql += " group by user.username";
   		List<AssetApp> listAssetApps = assetAppDAO.executeQuery(sql, (currpage-1)*pageSize, pageSize);
   		return listAssetApps;
   	}
    /***********************************************************************************
   	 * @description 药品溶液管理-药品申购｛通过实验中心找到其下的实验室的所有实验大纲｝
   	 * @author 郑昕茹
   	 * @date 2016-08-30
   	 * **********************************************************************************/
   	public List<OperationItem> findOperationOutlineByLabCenter(String acno){
   		String sql ="select oi from OperationItem oi where 1=1";
   		if(acno!=null && !acno.equals("-1")) {
   			sql += " and oi.labCenter.schoolAcademy.academyNumber='"+ acno +"'";
		}
   		return operationItemDAO.executeQuery(sql,0,-1);
   	}
   	
   	/***********************************************************************************
   	 * @description 药品溶液管理-药品申购｛通过主键找到实验大纲｝
   	 * @author 郑昕茹
   	 * @date 2016-08-30
   	 * **********************************************************************************/
   	public OperationOutline findOperationOutlineByPrimaryKey(Integer id){
   		return operationOutlineDAO.findOperationOutlineByPrimaryKey(id);
   	}
   	
    /***********************************************************************************
  	 * @description 药品溶液管理-药品申购｛删除药品申购信息｝
  	 * @author 郑昕茹
  	 * @date 2016-10-11
  	 * **********************************************************************************/
  	public boolean deleteAssetAppRecord(Integer id){
  		AssetAppRecord assetAppRecord = assetAppRecordDAO.findAssetAppRecordByPrimaryKey(id);
  		if(assetAppRecord != null){
  			assetAppRecordDAO.remove(assetAppRecord);
  			assetAppRecordDAO.flush();
  			return true;
  		}
  		return false;
  	}
  	
  	/***********************************************************************************
	 * @description 药品溶液管理-申购审核｛显示所有的药品审核信息｝
	 * @author 郑昕茹
	 * @date 2017-03-20
	 * **********************************************************************************/
	public List<AssetApp> findAuditAssetApps(Integer currpage, Integer pageSize, Integer assetAuditStatus, AssetApp assetApp,
											 User user, String startDate, String endDate, HttpServletRequest request){
		String sql="select a from AssetApp a where 1=1";//获取所有的设备申购信息
		if(user != null && user.getUsername() != null && !user.getUsername().equals("")){
			sql +=  " and user.username ='" + user.getUsername()+"'";
		}
		
		User currUser = shareService.getUser();
		String judge = ",";
        for (Authority authority : currUser.getAuthorities()) {
            judge = judge + "," + authority.getId() + ",";
        }
		/**
		 * 审核状态值（个人理解也是醉）：1审核通过；2审核拒绝；3一级待审核；4待提交；5二级待审核
		 * 状态值9为页面参数，表示全部
		 * 1.超管：所有一级待审核记录
		 * 2.教研室主任：待审核记录（状态值为3）
		 * 3.创新成果管理员：二级待审核记录
		 */
		String currRole = request.getSession().getAttribute("selected_role").toString();
		if(currRole.indexOf("ROLE_SUPERADMIN") != -1 && assetAuditStatus != 9) {
			sql += " and a.assetAuditStatus = " + assetAuditStatus;
		}else if(currRole.indexOf("ROLE_DEPARTMENTHEADER") != -1 && assetAuditStatus != 5) {
			if(assetAuditStatus != 9) {
				sql += " and a.assetAuditStatus = " + assetAuditStatus + " and a.userByFirstAuditUser.username ='"+currUser.getUsername()+"'";
			}else {
				sql += " and a.userByFirstAuditUser.username ='"+currUser.getUsername()+"'";
			}
		}else if(currRole.indexOf("ROLE_CABINETADMIN") != -1) {
			if(assetAuditStatus != 9 && assetAuditStatus != 3) {
				sql += " and a.assetAuditStatus = " + assetAuditStatus;
			}else if(assetAuditStatus == 3) {
				sql += " and a.assetAuditStatus = 5";
			}
		}

		//查询
		if(assetApp != null && assetApp.getAppNo() != null && !assetApp.getAppNo().equals("")){
			sql+=" and appNo like '%"+assetApp.getAppNo()+"%'"; 
		}
		if(startDate != null && endDate != null && !startDate.equals("")&&!endDate.equals("")){
			sql += " and appDate between '"+ startDate + "' and '"+endDate+"'";
		}
		if(assetApp != null && assetApp.getUser() != null && assetApp.getUser().getCname() != null && !assetApp.getUser().getCname().equals("")){
			sql +=  " and user.cname ='" + assetApp.getUser().getCname()+"'";
		}
		List<AssetApp> listAssetApps = assetAppDAO.executeQuery(sql, (currpage-1)*pageSize, pageSize);
		return listAssetApps;
	}
}