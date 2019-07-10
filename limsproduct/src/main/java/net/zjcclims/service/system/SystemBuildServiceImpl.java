package net.zjcclims.service.system;

import net.gvsun.lims.dto.common.PConfigDTO;
import net.zjcclims.dao.SystemBuildDAO;
import net.zjcclims.dao.SystemRoomDAO;
import net.zjcclims.domain.SystemBuild;
import net.zjcclims.domain.SystemRoom;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service("SystemBuildService")
public class SystemBuildServiceImpl implements SystemBuildService {
	
	@Autowired SystemBuildDAO systemBuildDAO;
	@Autowired SystemRoomDAO systemRoomDAO;
	@Autowired
	ShareService shareService;
	/*************************************************************************************
	 * @內容：根据校区id查询楼栋
	 * @作者：李小龙
	 *************************************************************************************/
	@Override
	public List<SystemBuild> findBuildByCampusId(String campusNumber) {
		// TODO Auto-generated method stub
		String sql="select b from SystemBuild b where b.systemCampus.campusNumber='"+campusNumber+"'";
		return systemBuildDAO.executeQuery(sql,0,-1);
	}
	/*************************************************************************************
	 * @內容：根据楼栋编号查询房间
	 * @作者：李小龙
	 *************************************************************************************/
	@Override
	public List<SystemRoom> findRoomByBuildNumber(String buildNumber) {
		// TODO Auto-generated method stub
		String sql="select m from SystemRoom m where m.systemBuild.buildNumber='"+buildNumber+"'";
		return systemRoomDAO.executeQuery(sql, 0,-1);
	}
	/*************************************************************************************
	 * @內容：根据学员编号查询楼栋
	 * @作者：李小龙
	 *************************************************************************************/
	@Override
	public List<SystemBuild> finSystemBuildByAcademy(String academyNumber) {
		if(academyNumber!=null&&!academyNumber.equals("")){
			String sql="select s from SystemBuild s where s.schoolAcademy.academyNumber="+academyNumber;
			return systemBuildDAO.executeQuery(sql, 0,-1);
		}else{
			return null;
		}
	}
	
	/*************************************************************************************
	 * @內容：查询所有楼栋
	 * @作者：裴继超
	 * @时间：2016年3月22日14:36:37
	 *************************************************************************************/
	@Override
	public List<SystemBuild> finAllSystemBuilds(){
		String sql = "select s from SystemBuild s where 1=1";
		sql+=" and s.enabled=1";
		return systemBuildDAO.executeQuery(sql, 0,-1);
	}
	
	
	/***************************************************************************************
	 * 保存楼栋
	 * 裴继超
	 * 2016年3月22日
	 *************************************************************************************/
	@Override
	public void saveSystemBuild(SystemBuild systemBuild){
		systemBuildDAO.store(systemBuild);
		systemBuildDAO.flush();
	}
	
	
	/*************************************************************************************
	 * @內容：根据id查询楼栋
	 * @作者：裴继超
	 * @时间：2016年3月22日14:36:37
	 *************************************************************************************/
	@Override
	public SystemBuild finSystemBuildById(String buildNumber){
		SystemBuild systemBuild = systemBuildDAO.findSystemBuildByBuildNumber(buildNumber);
		return systemBuild;
	}
	
	/**
	 * 根据是否存在坐标查楼宇
	 * 裴继超
	 * 2016年4月1日
	 */
	@Override
	public List<SystemBuild> findBuildingByXY(String campus_number){
		String sql = "select b from SystemBuild b where  b.xCoordinate != null and b.yCoordinate != null and b.systemCampus.campusNumber='"+campus_number+"'";
		List<SystemBuild> buildings = systemBuildDAO.executeQuery(sql, 0, -1);
		return buildings;
	}

	/**
	 * 根据id获取楼宇
	 * 裴继超
	 * 2016年4月5日
	 */
	@Override
	public SystemBuild findBuildingbyBuildNumber(String buildNumber){
		SystemBuild building = systemBuildDAO.findSystemBuildByPrimaryKey(buildNumber);
		return building;
	}

	/**
	 * @Description 根据查询条件获取楼宇
	 * @author 张德冰
	 * @data 2018-10-29
	 */
	@Override
	public List<SystemBuild> findSystemBuildByQuery(SystemBuild systemBuild, HttpServletRequest request, Integer currpage, Integer pageSize){
		//查询语句
		String sql="select s from SystemBuild s where 1=1";
		if(systemBuild.getBuildName() != null && !systemBuild.getBuildName().equals("")) {
			sql += " and s.buildName like '%" + systemBuild.getBuildName() +"%'";
		}
		if(systemBuild.getSystemCampus() != null && systemBuild.getSystemCampus().getCampusNumber() != null
				&& !systemBuild.getSystemCampus().getCampusNumber().equals("")){
			sql += " and s.systemCampus.campusNumber = " + systemBuild.getSystemCampus().getCampusNumber();
		}
		if(systemBuild.getSchoolAcademy() != null && systemBuild.getSchoolAcademy().getAcademyNumber() != null
				&& !systemBuild.getSchoolAcademy().getAcademyNumber().equals("") && !systemBuild.getSchoolAcademy().getAcademyNumber().equals("00")){
			sql += " and s.schoolAcademy.academyNumber = " + systemBuild.getSchoolAcademy().getAcademyNumber();
		}
		List<SystemBuild> builds = systemBuildDAO.executeQuery(sql, (currpage - 1) * pageSize, pageSize);
		return builds;
	}
	/**
	 * @Description 根据查询条件获取楼宇
	 * @author 廖文辉
	 * @data 2018-12-21
	 */
	public List<SystemBuild> findBuildByCampusIdAndAcno(String campusNumber,HttpServletRequest request){
		PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();

		//查询语句
		String sql="select b from SystemBuild b where b.systemCampus.campusNumber='"+campusNumber+"'";
		if(request.getSession().getAttribute("selected_role") == null){
			try {
				if(shareService.getUserDetail() != null) {
					sql += " and b.schoolAcademy.academyNumber='" + shareService.getUserDetail().getSchoolAcademy().getAcademyNumber() + "'";
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}else if(pConfigDTO.PROJECT_NAME.equals("tjpulims")) {
			// 获取当前系统权限
			String auth = request.getSession().getAttribute("selected_role").toString();
			// 根据权限等级筛选
			int authLevel = shareService.getLevelByAuthName(auth);
			if (authLevel == 5 || authLevel == 3 || authLevel == 4) {
				sql += " and b.schoolAcademy.academyNumber='" + shareService.getUserDetail().getSchoolAcademy().getAcademyNumber() + "'";
			}
		}
		return systemBuildDAO.executeQuery(sql,0,-1);
	}

}