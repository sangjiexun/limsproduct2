package net.zjcclims.service.device;

import java.util.List;

import net.zjcclims.domain.LabRoomDeviceReservationCredit;
import net.zjcclims.domain.LabRoomStationReservationCredit;
import net.zjcclims.domain.SoftwareRoomRelated;

public interface LabRoomDeviceReservationCreditService {
	/****************************************************************************
	 * 功能：保存信誉登记
	 * 作者：周志辉
	 ****************************************************************************/
	public LabRoomDeviceReservationCredit save(LabRoomDeviceReservationCredit labRoomDeviceReservationCredit);
	/****************************************************************************
	 * 功能：查询信誉登记纪录by reservationId
	 * 作者：周志辉
	 * @return 
	 ****************************************************************************/
	public List<LabRoomDeviceReservationCredit > findCreditByReservationId(Integer reservationId);
	/****************************************************************************
	 * 功能：查询信誉登记纪录by username
	 * 作者：周志辉
	 * @return 
	 ****************************************************************************/
	public List<LabRoomDeviceReservationCredit > findCreditByUsername(String username);
}
