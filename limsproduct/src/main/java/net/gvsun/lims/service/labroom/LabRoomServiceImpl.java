package net.gvsun.lims.service.labroom;

import api.net.gvsunlims.constant.ConstantInterface;
import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.common.JsonValueDTO;
import net.gvsun.lims.dto.user.AuthorityDTO;
import net.gvsun.lims.dto.user.UserDTO;
import net.gvsun.lims.service.user.UserService;
import net.zjcclims.dao.LabRoomDAO;
import net.zjcclims.dao.UserDAO;
import net.zjcclims.domain.Authority;
import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("labRoomService1")
public class LabRoomServiceImpl implements LabRoomService {
    @Autowired
    private LabRoomDAO labRoomDAO;

    /*************************************************************************************
     * Description:实验室管理-获取查询实验室列表-排课用
     *
     * @author： 魏誠
     * @date：2018-09-06
     *************************************************************************************/
    public List<JsonValueDTO> getLabRoomListBySelect(String academyNumber, String search,String soft) {
        String[] softs = soft.split(",");
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select c from LabRoom c " );
        if(!"".equals(soft)&&Objects.nonNull(softs) &&softs.length>0){
            sql.append("inner join c.softwareRoomRelateds related with (1=2 " );
            for(String i:softs){
                if(!"".equals(i)){
                    sql.append(" or related.software.id =" + i );
                }

            }
            sql.append(") " );
        }
        sql.append(" where c.isUsed=1 " );
        //查询条件
        if (academyNumber != null && !"".equals(academyNumber)) {
            sql.append(" and (c.schoolAcademy.academyNumber like '" + academyNumber +"' or c.isOpen=1) ");
        }
        if (search != null && !"".equals(search)) {
            sql.append(" and (c.labRoomName like '%" + search + "%' or");
            sql.append(" c.labRoomNumber like '%" + search + "%' )");
            //sql.append(" c.schoolAcademy.academyName like '%" + search + "%')");
        }
        sql.append(" order by c.labRoomName asc ");
        // 执行sb语句
        List<LabRoom> labRooms = labRoomDAO.executeQuery(sql.toString(),0,ConstantInterface.COMMON_SELECT_LIMIT);
        int total =labRooms.size();
        //封装VO
        List<JsonValueDTO> roomDTOs = new ArrayList<JsonValueDTO>();
        for(LabRoom labRoom:labRooms){
            JsonValueDTO jsonValueDTO = new JsonValueDTO();
            //设置学院
            jsonValueDTO.setId(labRoom.getId().toString());
            String academyName = Objects.nonNull(labRoom.getSchoolAcademy())?labRoom.getSchoolAcademy().getAcademyName():null;
            jsonValueDTO.setText(labRoom.getLabRoomName()+"(编号："+labRoom.getLabRoomNumber()+"；  学院："+academyName +")");
            roomDTOs.add(jsonValueDTO);
        }
        return roomDTOs;
    }


}