package net.zjcclims.service.credit;

import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Service("CreditOptionService")
public class CreditOptionServiceImpl implements CreditOptionService {
	
	@Autowired
	private CreditOptionDAO creditOptionDAO;
	@Autowired
	private ShareService shareService;
	@Autowired
	private LabRoomDeviceReservationDAO labRoomDeviceReservationDAO;
	@Autowired 
	private LabReservationDAO labReservationDAO;
	@Autowired
	private LabRoomStationReservationDAO labRoomStationReservationDAO;
	@Autowired
	private LabRoomReservationCreditDAO labRoomReservationCreditDAO;
	@Autowired
	private LabRoomDeviceReservationCreditDAO labRoomDeviceReservationCreditDAO;
	@Autowired
	private LabRoomStationReservationCreditDAO labRoomStationReservationCreditDAO;
	/*************************************************************************************
	 * Description 开放预约-信誉积分设置-获取扣分项的总记录数
	 * 
	 * @author 黄阿娜
	 * @date 2017-07-13
	 *************************************************************************************/
	@Transactional
	public int getCreditOptionTotalRecords(){
		Set<CreditOption> creditoptions = creditOptionDAO.findAllCreditOptions();
		return creditoptions.size();
	}
	
	/*************************************************************************************
	 * Description 开放预约-信誉积分设置-根据扣分项名称查询扣分项
	 * 
	 * @author 黄阿娜
	 * @date 2017-07-13
	 *************************************************************************************/
	public List<CreditOption> creditOptions(CreditOption creditOption){
		String sql = "select a from CreditOption a where 1=1";
		if(creditOption != null && creditOption.getName() != null && !creditOption.getName().equals("")){
			sql+=" and a.name like '%"+creditOption.getName()+"%'";
		}
		return creditOptionDAO.executeQuery(sql, 0,-1);
	}
	
	/*************************************************************************************
	 * Description 开放预约-信誉积分设置-根据扣分项的id查找扣分项
	 * 
	 * @param id
	 * @author 黄阿娜
	 * @date 2017-07-13
	 *************************************************************************************/
	public CreditOption findCreditOptionById(Integer id)
	{
		CreditOption creditOption = creditOptionDAO.findCreditOptionById(id);
		return creditOption;		
	}

	/************************************************************ 
	 * Description 开放预约-信誉积分设置-删除扣分项
	 * 
	 * @author 黄阿娜
	 * @date 2017-07-13
	 ************************************************************/
	public void deleteCreditOption(CreditOption st){
		creditOptionDAO.remove(st);
		creditOptionDAO.flush();
	}
	
	/************************************************************ 
	 * Description 开放预约-信誉积分设置-保存扣分项
	 * 
	 * @author 黄阿娜
	 * @date 2017-07-13
	 ************************************************************/
	public void saveCreditOption(CreditOption st){
		if(st.getCreateDate()==null){
			st.setCreateDate(Calendar.getInstance());
		}
		User user = shareService.getUser();
		st.setUser(user);
		st.setEnabled(1);
		st.setStatus(0);
		creditOptionDAO.store(st);
		creditOptionDAO.flush();
	}
	
	/************************************************************ 
	 * Description 开放预约-信誉积分设置-提交扣分项
	 * 
	 * @author 黄阿娜
	 * @date 2017-07-13
	 ************************************************************/
	public void submitCreditOption(CreditOption st){
		st.setStatus(1);
		creditOptionDAO.store(st);
		creditOptionDAO.flush();
	}
	/************************************************************ 
	 * Description 查询所有扣分项
	 * 
	 * @author 周志辉
	 * @date 2017-08-10
	 ************************************************************/
	@Override
	public List<CreditOption> findAllCreditOptionByQuery() {
		StringBuffer hql = new StringBuffer("select i from CreditOption i  where 1=1");	
		return creditOptionDAO.executeQuery(hql.toString());
	}
	/************************************************************ 
	 * Description 查询所有可用的扣分项
	 * 
	 * @author 黄浩
	 * @date 2018年5月30日
	 ************************************************************/
	@Override
	public List<CreditOption> findAllUseCreditOptionByQuery() {
		StringBuffer hql = new StringBuffer("select i from CreditOption i  where 1=1 and i.enabled=1");	
		return creditOptionDAO.executeQuery(hql.toString());
	}
	/*************************************************************************************
	 * Description 根据申请人获取已通过的预约
	 * @author 黄浩
	 * @date 2018年5月30日
	 *************************************************************************************/
	@Override
	public List<LabRoomDeviceReservation> findLabDeviceReservstionByReserveUserAndStatus(String username){
		String hql = "select l from LabRoomDeviceReservation l where l.userByReserveUser.username like '"+username+"' " +
				"and l.CDictionaryByCAuditResult.id = 615 and l.schoolTerm.id="+shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId() ;
		return labRoomDeviceReservationDAO.executeQuery(hql, 0,-1);
	}
	/*************************************************************************************
	 * Description 根据申请人获取已通过的预约
	 * @author 黄浩
	 * @date 2018年5月30日
	 *************************************************************************************/
	@Override
	public List<LabReservation> findLabRoomReservstionByReserveUserAndStatus(String username){
		SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		String hql = "select l from LabReservation l, SchoolTerm st" +
				" where l.user.username like '"+username+"' " +
				" and l.auditResults=1 " +
				" and st.termStart <= l.lendingTime " +
				" and st.termEnd >= l.lendingTime" +
				" and st.id="+schoolTerm.getId();
		return labReservationDAO.executeQuery(hql, 0,-1);
	}
	/*************************************************************************************
	 * Description 根据申请人获取已通过的预约
	 * @author 黄浩
	 * @date 2018年5月30日
	 *************************************************************************************/
	@Override
	public List<LabRoomStationReservation> findLabRoomStationReservationByReserveUserAndStatus(String username){
		String hql = "select l from LabRoomStationReservation l where l.user.username like '"+username+"' and l.result=1" +
				" and l.schoolTerm.id="+shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId() ;
		return labRoomStationReservationDAO.executeQuery(hql, 0,-1);
	}
	/*************************************************************************************
	 * Description 保存扣分记录
	 * @author 黄浩
	 * @date 2018年5月30日
	 *************************************************************************************/
	@Override
	public void saveDeductingRecord(String reservationsType,String reditOption,String username){
		//预约的id
		String reservationId = "";
		//扣分项的id
		String[] reditOptionId = reditOption.split(",");
		for(int i=0;i<reditOptionId.length;i++){
			//判断预约的类型
			if(reservationsType.contains("labReservation")){
				//获取预约id
				reservationId = reservationsType.substring(reservationsType.indexOf("+")+1);
				//操作LabRoomReservationCredit
				LabRoomReservationCredit labRoomReservationCredit = new LabRoomReservationCredit();
				labRoomReservationCredit.setLabReservation(labReservationDAO.findLabReservationById(Integer.valueOf(reservationId)));
				labRoomReservationCredit.setCreditOption(creditOptionDAO.findCreditOptionById(Integer.valueOf(reditOptionId[i])));
				labRoomReservationCredit.setUsername(username);
				labRoomReservationCreditDAO.store(labRoomReservationCredit);
			}
			if(reservationsType.contains("labRoomDeviceReservation")){
				//获取预约id
				reservationId = reservationsType.substring(reservationsType.indexOf("+")+1);
				//操作LabRoomDeviceReservationCredit
				LabRoomDeviceReservationCredit labRoomDeviceReservationCredit = new LabRoomDeviceReservationCredit();
				labRoomDeviceReservationCredit.setLabRoomDeviceReservation(labRoomDeviceReservationDAO.findLabRoomDeviceReservationById(Integer.valueOf(reservationId)));
				labRoomDeviceReservationCredit.setCreditOption(creditOptionDAO.findCreditOptionById(Integer.valueOf(reditOptionId[i])));
				labRoomDeviceReservationCredit.setUsername(username);
				labRoomDeviceReservationCreditDAO.store(labRoomDeviceReservationCredit);
			}
			if(reservationsType.contains("labRoomStationReservation")){
				//获取预约id
				reservationId = reservationsType.substring(reservationsType.indexOf("+")+1);
				//操作LabRoomStationReservationCredit
				LabRoomStationReservationCredit labRoomStationReservationCredit = new LabRoomStationReservationCredit();
				labRoomStationReservationCredit.setLabRoomStationReservation(labRoomStationReservationDAO.findLabRoomStationReservationById(Integer.valueOf(reservationId)));
				labRoomStationReservationCredit.setCreditOption(creditOptionDAO.findCreditOptionById(Integer.valueOf(reditOptionId[i])));
				labRoomStationReservationCredit.setUsername(username);
				labRoomStationReservationCreditDAO.store(labRoomStationReservationCredit);
			}
		}
	}
	/*************************************************************************************
	 * Description 计算总扣分
	 * @author 黄浩
	 * @date 2018年5月30日
	 *************************************************************************************/
	@Override
	public Integer deductingRecordMark(String reditOption){
		Integer sumDeduction = 0;
		//扣分项的id
		String[] reditOptionId = reditOption.split(",");
		for(int i=0;i<reditOptionId.length;i++){
			sumDeduction += creditOptionDAO.findCreditOptionById(Integer.valueOf(reditOptionId[i])).getDeduction();
		}
		return sumDeduction;
	}
}