package net.zjcclims.service.device;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.zjcclims.dao.LabRoomDeviceReservationCreditDAO;
import net.zjcclims.dao.SoftwareRoomRelatedDAO;
import net.zjcclims.domain.LabRoomDeviceReservationCredit;
import net.zjcclims.domain.LabRoomStationReservationCredit;
import net.zjcclims.domain.SoftwareRoomRelated;
@Service("LabRoomDeviceReservationCreditService")
public class LabRoomDeviceReservationCreditServiceImpl implements
		LabRoomDeviceReservationCreditService {
	@Autowired  LabRoomDeviceReservationCreditDAO labRoomDeviceReservationCreditDAO;
	/****************************************************************************
	 * 功能：保存信誉登记
	 * 作者：周志辉
	 ****************************************************************************/
	@Override
	public LabRoomDeviceReservationCredit save(
			LabRoomDeviceReservationCredit labRoomDeviceReservationCredit) {
		// TODO Auto-generated method stub
		return labRoomDeviceReservationCreditDAO.store(labRoomDeviceReservationCredit);
	}
	/****************************************************************************
	 * 功能：查询信誉登记纪录
	 * 作者：周志辉
	 * @return 
	 ****************************************************************************/
	@Override
	public List<LabRoomDeviceReservationCredit > findCreditByReservationId(Integer reservationId) {
		String sql="Select l from LabRoomDeviceReservationCredit l where 1=1";
		sql+=" and l.labRoomDeviceReservation.id="+reservationId;
		return labRoomDeviceReservationCreditDAO.executeQuery(sql);
	}
	/****************************************************************************
	 * 功能：查询信誉登记纪录by username
	 * 作者：周志辉
	 * @return 
	 ****************************************************************************/
	@Override
	public List<LabRoomDeviceReservationCredit> findCreditByUsername(
			String username) {
		String sql="Select l from LabRoomDeviceReservationCredit l where 1=1";
		sql+=" and l.labRoomDeviceReservation.userByReserveUser.username like '"+username+"'";
		return labRoomDeviceReservationCreditDAO.executeQuery(sql);
	}

}
