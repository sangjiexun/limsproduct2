package net.gvsun.lims.service.supplies;

import com.alibaba.fastjson.JSONObject;

public interface SuppliesRecordService {
    /**************************************************************************************
     * @Description 获取物资记录条目列表(按学院）
     * @param page 当前页数
     * @param limit 当前页限制大小
     * @param academy 学院
     * @param year 年份
     * @param month 月份
     * @return 物资记录DTO列表
     * @author 伍菁 2019-03-07
     **************************************************************************************/
    JSONObject getSuppliesRecordListForAcademy(Integer page,Integer limit,String academy,String year,String month);
    /**************************************************************************************
     * @Description 获取物资记录详情条目列表(按学院）
     * @param page 当前页数
     * @param limit 当前页限制大小
     * @param academy 学院
     * @param year 年份
     * @param month 月份
     * @return 物资记录DTO列表
     * @author 伍菁 2019-03-07
     **************************************************************************************/
    JSONObject getSuppliesRecordListDetailsForAcademy(Integer page,Integer limit,String academy,String year,String month);
    /**************************************************************************************
     * @Description 获取物资记录条目列表(按部门）
     * @param page 当前页数
     * @param limit 当前页限制大小
     * @param department 部门
     * @param year 年份
     * @param month 月份
     * @return 物资记录DTO列表
     * @author 伍菁 2019-03-07
     **************************************************************************************/
    JSONObject getSuppliesRecordListForDepartment(Integer page,Integer limit,String department,String year,String month);
    /**************************************************************************************
     * @Description 获取物资记录详情条目列表(按部门）
     * @param page 当前页数
     * @param limit 当前页限制大小
     * @param department 部门
     * @param year 年份
     * @param month 月份
     * @return 物资记录DTO列表
     * @author 伍菁 2019-03-07
     **************************************************************************************/
    JSONObject getSuppliesRecordListDetailsForDepartment(Integer page,Integer limit,String department,String year,String month);
    /**************************************************************************************
     * @Description 获取物资记录条目列表(按物资类别）
     * @param page 当前页数
     * @param limit 当前页限制大小
     * @param category 物资类别
     * @param year 年份
     * @param month 月份
     * @return 物资记录DTO列表
     * @author 伍菁 2019-03-07
     **************************************************************************************/
    JSONObject getSuppliesRecordListForCategory(Integer page,Integer limit,String category,String year,String month);
    /**************************************************************************************
     * @Description 获取物资记录详情条目列表(按物资类别）
     * @param page 当前页数
     * @param limit 当前页限制大小
     * @param category 物资类别
     * @param year 年份
     * @param month 月份
     * @return 物资记录DTO列表
     * @author 伍菁 2019-03-07
     **************************************************************************************/
    JSONObject getSuppliesRecordListDetailsForCategory(Integer page,Integer limit,String category,String year,String month);

}
