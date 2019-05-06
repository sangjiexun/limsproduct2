package net.zjcclims.service.credit;


import java.util.List;
import java.util.Set;

import org.apache.commons.collections.ListUtils;
import org.springframework.transaction.annotation.Transactional;

import net.zjcclims.domain.CreditOption;
import net.zjcclims.domain.LabReservation;
import net.zjcclims.domain.LabRoomDeviceReservation;
import net.zjcclims.domain.LabRoomStationReservation;
import net.zjcclims.domain.OperationItem;

public interface CreditOptionService {
	/*************************************************************************************
	 * Description 开放预约-信誉积分设置-获取扣分项的总记录数
	 * 
	 * @author 黄阿娜
	 * @date 2017-07-13
	 *************************************************************************************/
	public int getCreditOptionTotalRecords();
	
	/*************************************************************************************
	 * Description 开放预约-信誉积分设置-根据扣分项名称查询扣分项
	 * 
	 * @author 黄阿娜
	 * @date 2017-07-13
	 *************************************************************************************/
	public List<CreditOption> creditOptions(CreditOption creditOption);
	
	/*************************************************************************************
	 * Description 开放预约-信誉积分设置-根据扣分项的id查找扣分项
	 * 
	 * @param id
	 * @author 黄阿娜
	 * @date 2017-07-13
	 *************************************************************************************/
	public CreditOption findCreditOptionById(Integer id);
	/************************************************************ 
	 * Description 开放预约-信誉积分设置-删除扣分项
	 * 
	 * @author 黄阿娜
	 * @date 2017-07-13
	 ************************************************************/
	public void deleteCreditOption(CreditOption st);
	/************************************************************ 
	 * Description 开放预约-信誉积分设置-保存扣分项
	 * 
	 * @author 黄阿娜
	 * @date 2017-07-13
	 ************************************************************/
	public void saveCreditOption(CreditOption st);
	/************************************************************ 
	 * Description 开放预约-信誉积分设置-提交扣分项
	 * 
	 * @author 黄阿娜
	 * @date 2017-07-13
	 ************************************************************/
	public void submitCreditOption(CreditOption st);
	
	/************************************************************ 
	 * Description 查询所有扣分项
	 * 
	 * @author 周志辉
	 * @date 2017-08-10
	 ************************************************************/
	public List<CreditOption> findAllCreditOptionByQuery();
	/************************************************************ 
	 * Description 查询所有可用的扣分项
	 * 
	 * @author 黄浩
	 * @date 2018年5月30日
	 ************************************************************/
	public List<CreditOption> findAllUseCreditOptionByQuery();
	/*************************************************************************************
	 * Description 根据申请人获取已通过的预约
	 * @author 黄浩
	 * @date 2018年5月30日
	 *************************************************************************************/
	public List<LabRoomDeviceReservation> findLabDeviceReservstionByReserveUserAndStatus(String username);
	/*************************************************************************************
	 * Description 根据申请人获取已通过的预约
	 * @author 黄浩
	 * @date 2018年5月30日
	 *************************************************************************************/
	public List<LabReservation> findLabRoomReservstionByReserveUserAndStatus(String username);
	/*************************************************************************************
	 * Description 根据申请人获取已通过的预约
	 * @author 黄浩
	 * @date 2018年5月30日
	 *************************************************************************************/
	public List<LabRoomStationReservation> findLabRoomStationReservationByReserveUserAndStatus(String username);
	/*************************************************************************************
	 * Description 保存扣分记录
	 * @author 黄浩
	 * @date 2018年5月30日
	 *************************************************************************************/
	public void saveDeductingRecord(String reservationsType,String reditOption,String username);
	/*************************************************************************************
	 * Description 计算总扣分
	 * @author 黄浩
	 * @date 2018年5月30日
	 *************************************************************************************/
	public Integer deductingRecordMark(String reditOption);
}
