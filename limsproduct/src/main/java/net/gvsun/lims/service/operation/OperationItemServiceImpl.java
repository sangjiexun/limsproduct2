package net.gvsun.lims.service.operation;

import api.net.gvsunlims.constant.ConstantInterface;
import net.gvsun.lims.dto.common.JsonValueDTO;
import net.gvsun.lims.service.labroom.LabRoomService;
import net.zjcclims.dao.LabRoomDAO;
import net.zjcclims.dao.OperationItemDAO;
import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.OperationItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("operationItemService")
public class OperationItemServiceImpl implements OperationItemService {
    @Autowired
    private OperationItemDAO operationItemDAO;

    /*************************************************************************************
     * Description:用户管理-获取用户查询列表
     *
     * @author： 魏誠
     * @date：2018-09-06
     *************************************************************************************/
    public List<JsonValueDTO> getOperationItemListBySelect(String academyNumber,String courseNumber, String search) {
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select c from OperationItem c where 1=1 " );
        //查询条件
        if (academyNumber != null && !"".equals(academyNumber)) {
            sql.append(" and c.labCenter.schoolAcademy.academyNumber like '" + academyNumber +"' ");
        }
        if (courseNumber != null && !"".equals(courseNumber)) {
            sql.append(" and c.schoolCourseInfo.courseNumber like '" + courseNumber +"' ");
        }
        if (search != null && !"".equals(search)) {
            sql.append(" and (c.lpName like '%" + search + "%' or");
            sql.append(" c.labCenter.schoolAcademy.academyName like '%" + search + "%' )");
            //sql.append(" c.schoolAcademy.academyName like '%" + search + "%')");
        }
        sql.append(" order by c.lpName asc ");
        // 执行sb语句
        List<OperationItem> items = operationItemDAO.executeQuery(sql.toString(),0,ConstantInterface.COMMON_SELECT_LIMIT);
        int total =items.size();
        //封装VO
        List<JsonValueDTO> itemDTOs = new ArrayList<JsonValueDTO>();
        for(OperationItem operationItem:items){
            JsonValueDTO jsonValueDTO = new JsonValueDTO();
            //设置主键
            jsonValueDTO.setId(operationItem.getId().toString());
            //设置学院
            String academyName =null;
            if(Objects.nonNull(operationItem.getLabCenter())&&Objects.nonNull(operationItem.getLabCenter().getSchoolAcademy())){
                academyName = operationItem.getLabCenter().getSchoolAcademy().getAcademyName();
            }
            //设置课程
            String courseName =null;
            if(Objects.nonNull(operationItem.getSchoolCourseInfo())){
                courseName = operationItem.getSchoolCourseInfo().getCourseName();
                courseNumber = operationItem.getSchoolCourseInfo().getCourseNumber();
            }
            jsonValueDTO.setText(operationItem.getLpName()+"(课程"+courseNumber+"："+courseName+"；  学院："+academyName +")");
            itemDTOs.add(jsonValueDTO);
        }
        return itemDTOs;
    }
}