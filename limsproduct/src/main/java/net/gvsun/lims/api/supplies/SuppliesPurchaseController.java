//package net.gvsun.lims.api.supplies;
//import com.alibaba.fastjson.JSONObject;
//import net.gvsun.lims.dto.assets.AssetsApplyDTO;
//import net.gvsun.lims.service.supplies.SuppliesService;
//import net.zjcclims.service.common.ShareService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
///**
// * 申购api
// */
//@Controller("suppliesPurchaseController")
//@RequestMapping("/lims/api/supplies")
//public class SuppliesPurchaseController{
//    @Autowired
//    private SuppliesService suppliesService;
//    @Autowired
//    private ShareService shareService;
//    /**
//     * 获取父项目列表
//     * @param page 当前页数
//     * @param limit 当前页限制大小
//     * @return json字符串格式的父项目列表
//     */
//    @RequestMapping("/getSuppliesPurchase")
//    @ResponseBody
//    public String getSuppliesPurchase(Integer page, Integer limit){
//        JSONObject jsonObject = suppliesService.getSuppliesPurchase(page, limit);
//       System.out.println(jsonObject);
//        return shareService.htmlEncode(jsonObject.toJSONString());
//    }
//    /**
//     * Description 保存申购
//     * @param AssetsApplyDTO 参数封装DTO
//     * @return 成功-"success"，失败-"fail"
//     * @author
//     */
//    @RequestMapping("/saveSuppliesPurchase")
//    @ResponseBody
//    public String saveSuppliesPurchase(@RequestBody AssetsApplyDTO AssetsApplyDTO){
//        if(suppliesService.savesuppliesPurchaseDTO(AssetsApplyDTO)){
//            return "success";
//        }else{
//            return "fail";
//        }
//    }
//    /**
//     * Description 编辑申购
//     * @return 成功-"success"，失败-"fail"
//     * @author
//     */
//   @RequestMapping("/editsuppliesPurchase")
//    public ModelAndView suppliesPurchase(ModelAndView mav) {
//               Integer id = 1;
//        AssetsApplyDTO AssetsApplyDTO = suppliesService.getsuppliesPurchaseId(id);
//                    /*   mav.setViewName(this.CONSTRUCTION_URL + "editsuppliesPurchase.jsp");*/
//        return mav;
//    }
//    /**
//     * Description 删除申购
//     * @param id 项目id
//     * @return 成功-"success"，失败-"fail"
//     * @author
//     */
//    @RequestMapping("/deleteSuppliesPurchase")
//    @ResponseBody
//    public String deleteSuppliesPurchase(Integer id){
//        if(suppliesService.deleteSuppliesPurchase(id)){
//            return "success";
//        }else{
//            return "fail";
//        }
//    }
//}
