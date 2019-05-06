package net.gvsun.lims.api.deviceRepair;

import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.service.DeviceRepair.DeviceRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller("apiDeviceRepairController")
@SessionAttributes("selected_academy")
@RequestMapping("/lims/api/deviceRepair")
public class apiDeviceRepairController {
    @Autowired
    private DeviceRepairService deviceRepairService;

    /**
     * Description 设备维修信息显示
     * @param offset 起始位置
     * @param limit 限制每页显示个数
     * @param search 查询条件
     * @param sort 排序条件
     * @param order 正序或倒序
     * @param request 请求
     * @return 设备维修信息
     * @author 黄保钱 2018-9-29
     */
    @ResponseBody
    @RequestMapping("/getDeviceRepairList")
    public BaseDTO getDeviceRepairList(String labRoom,Integer deviceId, int offset, int limit, String search, String sort, String order, HttpServletRequest request){
        //获取查询设备列表
        return deviceRepairService.getStandardDeviceListBySelect(labRoom,deviceId, search, offset, limit, sort, order,request);
    }

    /**
     * 获取设备维修申请单列表
     * @param auditStage 审核状态
     * @param offset 起始位置
     * @param limit 限制每页显示个数
     * @param search 查询条件
     * @param sort 排序条件
     * @param order 正序或倒序
     * @param request 请求
     * @return 申请单显示列表
     * @author 黄保钱 2018-10-9
     */
    @ResponseBody
    @RequestMapping("/getDeviceRepairApplyList")
    public BaseDTO getDeviceRepairApplyList(int auditStage, int offset, int limit, String search, String sort, String order, HttpServletRequest request,
                                            @ModelAttribute("selected_academy") String acno){
        if(auditStage == 0) {
            // 获取我的设备维修申请列表
            return deviceRepairService.getMyDeviceRepairListBySelect(search, offset, limit, sort, order, request);
        }
        else if(auditStage == 4){
            // 获取我的确认维修申请列表
            return deviceRepairService.getDeviceRepairConfirmListBySelect(search, offset, limit, sort, order, request);
        }
        else if(auditStage == 5){
            // 获取我的确认维修申请列表
            return deviceRepairService.getDeviceRepairViewListBySelect(search, offset, limit, sort, order, request);
        }
        else{
            // 获取我的设备维修审核列表
            return deviceRepairService.getDeviceRepairCheckListBySelect(auditStage, search, offset, limit, sort, order, request, acno);
        }
    }
}
