package net.gvsun.lims.service.software;

import api.net.gvsunlims.constant.ConstantInterface;
import net.gvsun.lims.dto.common.JsonValueDTO;
import net.zjcclims.dao.LabRoomDAO;
import net.zjcclims.dao.SoftwareDAO;
import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.Software;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("softwareService1")
public class SoftwareServiceImpl implements SoftwareService {
    @Autowired
    private LabRoomDAO labRoomDAO;
    @Autowired
    private SoftwareDAO softwareDAO;

    /*************************************************************************************
     * Description:用户管理-获取用户查询列表-排课用
     *
     * @author： 魏誠
     * @date：2018-09-06
     *************************************************************************************/
    public List<JsonValueDTO> getSoftwareListBySelect(String academyNumber, String search) {
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select c from Software c where 1=1 " );
        //查询条件
        if (academyNumber != null && !"".equals(academyNumber)) {
            sql.append(" and c.schoolAcademy.academyNumber like '" + academyNumber +"' ");
        }
        if (search != null && !"".equals(search)) {
            sql.append(" and (c.name like '%" + search + "%' or");
            sql.append(" c.code like '%" + search + "%' )");
            //sql.append(" c.schoolAcademy.academyName like '%" + search + "%')");
        }
        sql.append(" order by c.name asc ");
        // 执行sb语句
        List<Software> softwares = softwareDAO.executeQuery(sql.toString(),0,ConstantInterface.COMMON_SELECT_LIMIT);
        int total =softwares.size();
        //封装VO
        List<JsonValueDTO> softwareDTOs = new ArrayList<JsonValueDTO>();
        for(Software software:softwares){
            JsonValueDTO jsonValueDTO = new JsonValueDTO();
            //设置学院
            jsonValueDTO.setId(software.getId().toString());
            String academyName = software.getName();
            jsonValueDTO.setText(software.getName()+"(编号："+software.getCode()+"；  学院："+academyName +")");
            softwareDTOs.add(jsonValueDTO);
        }
        return softwareDTOs;
    }


}