package net.gvsun.lims.service.supplies;

import com.alibaba.fastjson.JSONObject;
import net.gvsun.lims.dto.supplies.SuppliesRecordDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("SuppliesRecordService")
public class SuppliesRecordServiceImpl implements SuppliesRecordService{

    /**************************************************************************************
     * 将项目数据封装为前台可接收的json格式数据
     * @param projects 项目列表
     * @param totalRecords 项目总数
     * @param totalPage 总页数
     * @return json格式数据
     * @author 伍菁 2019-03-12
     **************************************************************************************/
    private JSONObject getProjectJSON_2(List<?> projects, int totalRecords,int totalPage){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", projects);
        jsonObject.put("count", totalRecords);
        jsonObject.put("totalPage",totalPage);
        jsonObject.put("code", 0);
        jsonObject.put("msg", "success");
        return jsonObject;
    }

    /**************************************************************************************
     * @Description 获取物资记录条目列表(按学院)
     * @param currPage 当前页数
     * @param limit 当前页限制大小
     * @param academy 学院
     * @param year 年份
     * @param month 月份
     * @return 物资记录DTO列表
     * @author 伍菁 2019-03-07
     **************************************************************************************/
    @Override
    public JSONObject getSuppliesRecordListForAcademy(Integer currPage, Integer limit,String academy,String year,String month){
        List<SuppliesRecordDTO> suppliesRecordDTOList = new ArrayList<>();
        int totalRecords = 6;
        for(int i =0;i<totalRecords;i++){
            SuppliesRecordDTO suppliesRecordDTO = new SuppliesRecordDTO();
            suppliesRecordDTO.setAcademy("学院"+(i+1));
            suppliesRecordDTO.setYear("2019");
            suppliesRecordDTO.setMonth("1");
            suppliesRecordDTO.setMonthAmount((float)(1000+i+1));
            suppliesRecordDTOList.add(suppliesRecordDTO);
        }
        //总页数
        int totalPage = 1;
        if (limit!=0){
            totalPage = (int)Math.ceil((double)totalRecords/limit);
        }
        //分页
        suppliesRecordDTOList = suppliesRecordDTOList.subList((currPage-1)*limit, Math.min((currPage)*limit,suppliesRecordDTOList.size()));
        return getProjectJSON_2(suppliesRecordDTOList,totalRecords,totalPage);
    }
    /**************************************************************************************
     * @Description 获取物资记录详情条目列表(按学院)
     * @param currPage 当前页数
     * @param limit 当前页限制大小
     * @param academy 学院
     * @param year 年份
     * @param month 月份
     * @return 物资记录DTO列表
     * @author 伍菁 2019-03-07
     **************************************************************************************/
    @Override
    public JSONObject getSuppliesRecordListDetailsForAcademy(Integer currPage, Integer limit,String academy,String year,String month){
        List<SuppliesRecordDTO> suppliesRecordDTOList = new ArrayList<>();
        int totalRecords = 3;
        for(int i =0;i<totalRecords;i++){
            SuppliesRecordDTO suppliesRecordDTO = new SuppliesRecordDTO();
            suppliesRecordDTO.setAcademy("学院"+(i+1));
            suppliesRecordDTO.setSuppliesCategory("物资类别"+(i+1));
            suppliesRecordDTO.setBatchNumber("申领批次编号"+(i+1));
            suppliesRecordDTO.setYear("2019");
            suppliesRecordDTO.setYearAmount((float)(9000+i+1));
            suppliesRecordDTO.setMonth("1");
            suppliesRecordDTO.setMonthAmount((float)(1000+i+1));
            suppliesRecordDTO.setTime("2019-01-22");
            suppliesRecordDTOList.add(suppliesRecordDTO);
        }
        //总页数
        int totalPage = 1;
        if (limit!=0){
            totalPage = (int)Math.ceil((double)totalRecords/limit);
        }
        //分页
        suppliesRecordDTOList = suppliesRecordDTOList.subList((currPage-1)*limit, Math.min((currPage)*limit,suppliesRecordDTOList.size()));
        return getProjectJSON_2(suppliesRecordDTOList,totalRecords,totalPage);
    }
    /**************************************************************************************
     * @Description 获取物资记录条目列表(按部门)
     * @param currPage 当前页数
     * @param limit 当前页限制大小
     * @param department 部门
     * @param year 年份
     * @param month 月份
     * @return 物资记录DTO列表
     * @author 伍菁 2019-03-07
     **************************************************************************************/
    @Override
    public JSONObject getSuppliesRecordListForDepartment(Integer currPage, Integer limit,String department,String year,String month){
        List<SuppliesRecordDTO> suppliesRecordDTOList = new ArrayList<>();
        int totalRecords = 5;
        for(int i =0;i<totalRecords;i++){
            SuppliesRecordDTO suppliesRecordDTO = new SuppliesRecordDTO();
            suppliesRecordDTO.setDepartment("部门"+(i+1));
            suppliesRecordDTO.setYear("2019");
            suppliesRecordDTO.setMonth("1");
            suppliesRecordDTO.setMonthAmount((float)(1000+i+1));
            suppliesRecordDTOList.add(suppliesRecordDTO);
        }
        //总页数
        int totalPage = 1;
        if (limit!=0){
            totalPage = (int)Math.ceil((double)totalRecords/limit);
        }
        //分页
        suppliesRecordDTOList = suppliesRecordDTOList.subList((currPage-1)*limit, Math.min((currPage)*limit,suppliesRecordDTOList.size()));
        return getProjectJSON_2(suppliesRecordDTOList,totalRecords,totalPage);
    }
    /**************************************************************************************
     * @Description 获取物资记录详情条目列表(按部门)
     * @param currPage 当前页数
     * @param limit 当前页限制大小
     * @param department 部门
     * @param year 年份
     * @param month 月份
     * @return 物资记录DTO列表
     * @author 伍菁 2019-03-07
     **************************************************************************************/
    @Override
    public JSONObject getSuppliesRecordListDetailsForDepartment(Integer currPage, Integer limit,String department,String year,String month){
        List<SuppliesRecordDTO> suppliesRecordDTOList = new ArrayList<>();
        int totalRecords = 3;
        for(int i =0;i<totalRecords;i++){
            SuppliesRecordDTO suppliesRecordDTO = new SuppliesRecordDTO();
            suppliesRecordDTO.setDepartment("部门"+(i+1));
            suppliesRecordDTO.setSuppliesCategory("物资类别"+(i+1));
            suppliesRecordDTO.setBatchNumber("申领批次编号"+(i+1));
            suppliesRecordDTO.setYear("2019");
            suppliesRecordDTO.setYearAmount((float)(9000+i+1));
            suppliesRecordDTO.setMonth("1");
            suppliesRecordDTO.setMonthAmount((float)(1000+i+1));
            suppliesRecordDTO.setTime("2019-01-18");
            suppliesRecordDTOList.add(suppliesRecordDTO);
        }
        //总页数
        int totalPage = 1;
        if (limit!=0){
            totalPage = (int)Math.ceil((double)totalRecords/limit);
        }
        //分页
        suppliesRecordDTOList = suppliesRecordDTOList.subList((currPage-1)*limit, Math.min((currPage)*limit,suppliesRecordDTOList.size()));
        return getProjectJSON_2(suppliesRecordDTOList,totalRecords,totalPage);
    }
    /**************************************************************************************
     * @Description 获取物资记录条目列表(按物资类别)
     * @param currPage 当前页数
     * @param limit 当前页限制大小
     * @param category 物资类别
     * @param year 年份
     * @param month 月份
     * @return 物资记录DTO列表
     * @author 伍菁 2019-03-07
     **************************************************************************************/
    @Override
    public JSONObject getSuppliesRecordListForCategory(Integer currPage,Integer limit,String category,String year,String month){
        List<SuppliesRecordDTO> suppliesRecordDTOList = new ArrayList<>();
        int totalRecords = 4;
        for(int i =0;i<totalRecords;i++){
            SuppliesRecordDTO suppliesRecordDTO = new SuppliesRecordDTO();
            suppliesRecordDTO.setSuppliesCategory("物资类别"+(i+1));
            suppliesRecordDTO.setYear("2019");
            suppliesRecordDTO.setMonth("1");
            suppliesRecordDTO.setMonthAmount((float)(1000+i));
            suppliesRecordDTOList.add(suppliesRecordDTO);
        }
        //总页数
        int totalPage = 1;
        if (limit!=0){
            totalPage = (int)Math.ceil((double)totalRecords/limit);
        }
        //分页
        suppliesRecordDTOList = suppliesRecordDTOList.subList((currPage-1)*limit, Math.min((currPage)*limit,suppliesRecordDTOList.size()));
        return getProjectJSON_2(suppliesRecordDTOList,totalRecords,totalPage);
    }
    /**************************************************************************************
     * @Description 获取物资记录详情条目列表(按物资类别)
     * @param currPage 当前页数
     * @param limit 当前页限制大小
     * @param category 物资类别
     * @param year 年份
     * @param month 月份
     * @return 物资记录DTO列表
     * @author 伍菁 2019-03-07
     **************************************************************************************/
    @Override
    public JSONObject getSuppliesRecordListDetailsForCategory(Integer currPage,Integer limit,String category,String year,String month){
        List<SuppliesRecordDTO> suppliesRecordDTOList = new ArrayList<>();
        int totalRecords = 3;
        for(int i =0;i<totalRecords;i++){
            SuppliesRecordDTO suppliesRecordDTO = new SuppliesRecordDTO();
            suppliesRecordDTO.setSuppliesCategory("物资类别"+(i+1));
            suppliesRecordDTO.setBatchNumber("申领批次编号"+(i+1));
            suppliesRecordDTO.setYear("2019");
            suppliesRecordDTO.setYearAmount((float)(9000+i+1));
            suppliesRecordDTO.setMonth("1");
            suppliesRecordDTO.setMonthAmount((float)(1000+i));
            suppliesRecordDTOList.add(suppliesRecordDTO);
        }
        //总页数
        int totalPage = 1;
        if (limit!=0){
            totalPage = (int)Math.ceil((double)totalRecords/limit);
        }
        //分页
        suppliesRecordDTOList = suppliesRecordDTOList.subList((currPage-1)*limit, Math.min((currPage)*limit,suppliesRecordDTOList.size()));
        return getProjectJSON_2(suppliesRecordDTOList,totalRecords,totalPage);
    }
}
