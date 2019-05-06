package net.gvsun.lims.service.school;

import api.net.gvsunlims.constant.ConstantInterface;
import net.gvsun.lims.dto.common.JsonValueDTO;
import net.zjcclims.dao.SchoolAcademyDAO;
import net.zjcclims.dao.SchoolCourseInfoDAO;
import net.zjcclims.domain.SchoolAcademy;
import net.zjcclims.domain.SchoolCourseInfo;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service("schoolAcademyService")
public class SchoolAcademyServiceImpl implements SchoolAcademyService {
    @Autowired
    private SchoolAcademyDAO schoolAcademyDAO;
    @Autowired
    private ShareService shareService;

    /*************************************************************************************
     * Description:课程管理-获取课程查询列表
     *
     * @author： 魏誠
     * @date：2018-10-26
     *************************************************************************************/
    public List<JsonValueDTO> apiSchoolAcademyListBySelect(String search,String acno,HttpServletRequest request) {
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select c from SchoolAcademy c where 1=1 " );
        //查询条件
        if (search != null && !"".equals(search)) {
            sql.append(" and (c.academyNumber like '%" + search + "%' or");
            sql.append(" c.academyName like '%" + search + "%' ) ");
        }
        String auth = request.getSession().getAttribute("selected_role").toString();
        int authLevel = shareService.getLevelByAuthName(auth);
        if(authLevel!=1){
            sql.append(" and c.academyNumber='"+acno+"'");
        }
        sql.append(" order by c.academyName asc limit " + ConstantInterface.COMMON_SELECT_LIMIT);
        // 执行sb语句
        List<SchoolAcademy> schoolAcademies = schoolAcademyDAO.executeQuery(sql.toString(),0,ConstantInterface.COMMON_SELECT_LIMIT);
        int total =schoolAcademies.size();
        //封装VO
        List<JsonValueDTO> schoolAcademyDTOs = new ArrayList<JsonValueDTO>();
        for(SchoolAcademy schoolAcademy:schoolAcademies){
            JsonValueDTO jsonValueDTO = new JsonValueDTO();
            //设置学院
            jsonValueDTO.setId(schoolAcademy.getAcademyNumber());
            jsonValueDTO.setText(schoolAcademy.getAcademyName()+"(编号："+schoolAcademy.getAcademyNumber()+")");
            schoolAcademyDTOs.add(jsonValueDTO);
        }
        return schoolAcademyDTOs;
    }
}