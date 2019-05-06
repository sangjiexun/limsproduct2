package net.gvsun.lims.api.supplies;

import com.alibaba.fastjson.JSONObject;
import net.gvsun.lims.service.supplies.SuppliesRecordService;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("ApiSuppliesRecordController")
@RequestMapping("/lims/api/supplies")
public class ApiSuppliesRecordController {

    @Autowired
    private SuppliesRecordService suppliesRecordService;
    @Autowired
    private ShareService shareService;

    /**************************************************************************************
     * @Description 获取物资记录条目列表(按学院 ）
     * @param page 当前页数
     * @param limit 当前页限制大小
     * @param academy 学院
     * @param year 年份
     * @param month 月份
     * @return json字符串格式的物资记录条目列表
     * @author 伍菁 2019-03-07
     **************************************************************************************/
    @RequestMapping("/getSuppliesRecordListForAcademy")
    @ResponseBody
    public String getSuppliesRecordListForAcademy(Integer page, Integer limit,String academy,String year,String month) {
        JSONObject jsonObject = suppliesRecordService.getSuppliesRecordListForAcademy(page, limit,academy,year,month);
        return shareService.htmlEncode(jsonObject.toJSONString());
    }
    /**************************************************************************************
     * @Description 获取物资记录详情条目列表(按学院 ）
     * @param page 当前页数
     * @param limit 当前页限制大小
     * @param academy 学院
     * @param year 年份
     * @param month 月份
     * @return json字符串格式的物资记录条目列表
     * @author 伍菁 2019-03-07
     **************************************************************************************/
    @RequestMapping("/getSuppliesRecordListDetailsForAcademy")
    @ResponseBody
    public String getSuppliesRecordListDetailsForAcademy(Integer page, Integer limit,String academy,String year,String month) {
        JSONObject jsonObject = suppliesRecordService.getSuppliesRecordListDetailsForAcademy(page, limit,academy,year,month);
        System.out.println(jsonObject);
        return shareService.htmlEncode(jsonObject.toJSONString());
    }
    /**************************************************************************************
     * @Description 获取物资记录条目列表(按部门 ）
     * @param page 当前页数
     * @param limit 当前页限制大小
     * @param department 部门
     * @param year 年份
     * @param month 月份
     * @return json字符串格式的物资记录条目列表
     * @author 伍菁 2019-03-07
     **************************************************************************************/
    @RequestMapping("/getSuppliesRecordListForDepartment")
    @ResponseBody
    public String getSuppliesRecordListForDepartment(Integer page,Integer limit,String department,String year,String month) {
        JSONObject jsonObject = suppliesRecordService.getSuppliesRecordListForDepartment(page, limit,department,year,month);
        return shareService.htmlEncode(jsonObject.toJSONString());
    }
    /**************************************************************************************
     * @Description 获取物资记录详情条目列表(按部门 ）
     * @param page 当前页数
     * @param limit 当前页限制大小
     * @param department 部门
     * @param year 年份
     * @param month 月份
     * @return json字符串格式的物资记录条目列表
     * @author 伍菁 2019-03-07
     **************************************************************************************/
    @RequestMapping("/getSuppliesRecordListDetailsForDepartment")
    @ResponseBody
    public String getSuppliesRecordListDetailsForDepartment(Integer page,Integer limit,String department,String year,String month) {
        JSONObject jsonObject = suppliesRecordService.getSuppliesRecordListDetailsForDepartment(page, limit,department,year,month);
        return shareService.htmlEncode(jsonObject.toJSONString());
    }
    /**************************************************************************************
     * @Description 获取物资记录条目列表(按物资类别 ）
     * @param page 当前页数
     * @param limit 当前页限制大小
     * @param category 物资类别
     * @param year 年份
     * @param month 月份
     * @return json字符串格式的物资记录条目列表
     * @author 伍菁 2019-03-07
     **************************************************************************************/
    @RequestMapping("/getSuppliesRecordListForCategory")
    @ResponseBody
    public String getSuppliesRecordListForCategory(Integer page, Integer limit,String category,String year,String month) {
        JSONObject jsonObject = suppliesRecordService.getSuppliesRecordListForCategory(page,limit,category,year,month);
        return shareService.htmlEncode(jsonObject.toJSONString());
    }
    /**************************************************************************************
     * @Description 获取物资记录详情条目列表(按物资类别 ）
     * @param page 当前页数
     * @param limit 当前页限制大小
     * @param category 物资类别
     * @param year 年份
     * @param month 月份
     * @return json字符串格式的物资记录条目列表
     * @author 伍菁 2019-03-07
     **************************************************************************************/
    @RequestMapping("/getSuppliesRecordListDetailsForCategory")
    @ResponseBody
    public String getSuppliesRecordListDetailsForCategory(Integer page,Integer limit,String category,String year,String month) {
        JSONObject jsonObject = suppliesRecordService.getSuppliesRecordListDetailsForCategory(page, limit,category,year,month);
        return shareService.htmlEncode(jsonObject.toJSONString());
    }
}






