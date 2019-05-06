package net.gvsun.lims.api.supplies;
import com.alibaba.fastjson.JSONObject;
import net.gvsun.lims.dto.suppliesInStorage.suppliesInStorageDTO;
import net.gvsun.lims.service.supplies.SuppliesInStorageService;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
/**
 * 申购api
 */
@Controller("suppliesInStorageController")
@RequestMapping("/lims/api/supplies")
public class SuppliesInStorageController {
    @Autowired
    private  SuppliesInStorageService suppliesInStorageService;
    @Autowired
    private ShareService shareService;
    /**
     * 获取父项目列表
     * @return json字符串格式的父项目列表
     */
    @RequestMapping("/getSuppliesInStorage")
    @ResponseBody
    public String getSuppliesInStorage(){
        JSONObject jsonObject = suppliesInStorageService.getSuppliesInStorage();
        System.out.println(jsonObject);
        return shareService.htmlEncode(jsonObject.toJSONString());
    }
    /**
     * Description 保存申购
     * @param suppliesInStorageDTO 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author
     */
    @RequestMapping("/saveSuppliesInStorage")
    @ResponseBody
    public String saveSuppliesInStorage(@RequestBody suppliesInStorageDTO suppliesInStorageDTO){
        if(suppliesInStorageService.saveSuppliesInStorageDTO(suppliesInStorageDTO)){
            return "success";
        }else{
            return "fail";
        }
    }
    @RequestMapping("/editsuppliesInStorage")
    @ResponseBody
    public ModelAndView suppliesInStorageDTO(ModelAndView mav) {
               Integer id = 1;
        suppliesInStorageDTO suppliesInStorageDTO = suppliesInStorageService.getSuppliesInStorageId(id);
               /*         mav.setViewName(this.CONSTRUCTION_URL + "editsuppliesPurchase.jsp");*/
        return mav;
    }
    /**
     * Description 删除申购
     * @param id 项目id
     * @return 成功-"success"，失败-"fail"
     * @author
     */
    @RequestMapping("/deleteSuppliesInStorage")
    @ResponseBody
    public String deleteSuppliesInStorage(Integer id){
        if(suppliesInStorageService.deleteSuppliesInStorageDTO(id)){
            return "success";
        }else{
            return "fail";
        }
    }
}
