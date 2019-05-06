package net.gvsun.lims.service.school;

import api.net.gvsunlims.constant.ConstantInterface;
import net.gvsun.lims.dto.common.JsonValueDTO;
import net.zjcclims.dao.SchoolAcademyDAO;
import net.zjcclims.dao.SchoolTermDAO;
import net.zjcclims.domain.SchoolAcademy;
import net.zjcclims.domain.SchoolTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("SchoolTermService")
public class SchoolTermServiceImpl implements SchoolTermService {
    @Autowired
    private SchoolTermDAO schoolTermDAO;

    /*************************************************************************************
     * Description:课程管理-获取课程查询列表
     *
     * @author： 魏誠
     * @date：2018-10-26
     *************************************************************************************/
    public List<JsonValueDTO> apiSchoolTermListBySelect(String search) {
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select c from SchoolTerm c where 1=1 " );
        //查询条件
        if (search != null && !"".equals(search)) {
            sql.append(" and (c.termName like '%" + search + "%')");
        }
        sql.append(" order by c.id desc limit " + ConstantInterface.COMMON_SELECT_LIMIT);
        // 执行sb语句
        List<SchoolTerm> schoolTerms = schoolTermDAO.executeQuery(sql.toString(),0,ConstantInterface.COMMON_SELECT_LIMIT);
        int total =schoolTerms.size();
        //封装VO
        List<JsonValueDTO> schoolTermDTOs = new ArrayList<JsonValueDTO>();
        for(SchoolTerm schoolTerm:schoolTerms){
            JsonValueDTO jsonValueDTO = new JsonValueDTO();
            //设置学院
            jsonValueDTO.setId(schoolTerm.getId().toString());
            jsonValueDTO.setText(schoolTerm.getTermName());
            schoolTermDTOs.add(jsonValueDTO);
        }
        return schoolTermDTOs;
    }
}