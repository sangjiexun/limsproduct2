//package net.gvsun.lims.service.supplies;
//import com.alibaba.fastjson.JSONObject;
//import net.baidu.ueditor.PathFormat;
//import net.gvsun.lims.dto.assets.AssetsApplyDTO;
//import net.zjcclims.domain.User;
//import net.zjcclims.service.common.ShareService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//@Service("suppliesServiceNew")
//public class SuppliesServiceImpl implements SuppliesService {
//    @Autowired
//    private ShareService shareService;
//    private PathFormat sdf;
//
//    /**
//     * 通过id获取申购名目
//     *
//     * @param id 主键
//     * @return 申购名目dto
//     * @author chenwenmin
//     */
//    @Override
//    public AssetsApplyDTO getsuppliesPurchaseId(Integer id) {
//        AssetsApplyDTO AssetsApplyDTO = new AssetsApplyDTO();
//        //项目id
//        AssetsApplyDTO.setId(id);
//        AssetsApplyDTO.setGoodsCategory("申购名目分类" + id);
//        //申购人
//        User applicantUserName = shareService.getUserDetail();
//        AssetsApplyDTO.setAcademyNumber(applicantUserName.getSchoolAcademy().getAcademyName());
//        //申购开始时间
//        Calendar startData = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        AssetsApplyDTO.setStartData(sdf.format(startData.getTime()));
//        //申购结束时间
//        Calendar endData = Calendar.getInstance();
//        AssetsApplyDTO.setEndData(sdf.format(endData.getTime()));
//        //申购
//        return AssetsApplyDTO;
//    }
//    /**
//     * Description 通过申购表知道申购的ID
//    +     * @param page 当前页数
//    +     * @param limit 当前页最大数据量
//     */
//    public JSONObject getSuppliesList(Integer SuppliesListId) {
//        List<AssetsApplyDTO> suppliesLists=new ArrayList<>();
//        int totalRecords=5;
//        for(int j=0;j<totalRecords; j++){
//            AssetsApplyDTO AssetsApplyDTO =new AssetsApplyDTO();
//            AssetsApplyDTO.setStartData(sdf.format(AssetsApplyDTO.getDate()));
//        }
//return null;
//    }
//    @Override
//    public JSONObject getSuppliesPurchase(Integer page, Integer limit) {
//        List<AssetsApplyDTO> suppliesPurchase = new ArrayList<>();
//        int totalRecords = 10;
//        for (int i = 0; i < totalRecords; i++) {
//            AssetsApplyDTO AssetsApplyDTO = new AssetsApplyDTO();
//            // 项目id
//            AssetsApplyDTO.setId(i);
//            //申购人
//            User applicantUserName = shareService.getUserDetail();
//            AssetsApplyDTO.setAcademyNumber(applicantUserName.getSchoolAcademy().getAcademyName());
//            //申购开始时间
//            Calendar startData = Calendar.getInstance();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            AssetsApplyDTO.setStartData(sdf.format(startData.getTime()));
//            //申购结束时间
//            Calendar endData = Calendar.getInstance();
//            AssetsApplyDTO.setEndData(sdf.format(endData.getTime()));
//
//            suppliesPurchase.add(AssetsApplyDTO);
//        }
//        // 分页
//       /* suppliesPurchase = suppliesPurchase.subList((page-1)*limit, Math.min((page)*limit, suppliesPurchase.size()));*/
//        return getSuppliesJSON(suppliesPurchase, totalRecords);
//
//    }
//    /**
//     * 将项目数据封装为前台可接收的json格式数据
//     * @param Supplies 申购列表
//     * @param totalRecords 项目总数
//     * @return json格式数据
//     * @author
//     */
//    private JSONObject getSuppliesJSON(List<?> Supplies, int totalRecords){
//        JSONObject jsonObject = new JSONObject();
//      /*  jsonObject.put("data", projects);*/
//        jsonObject.put("count", totalRecords);
//        jsonObject.put("code", 0);
//        jsonObject.put("msg", "success");
//        return jsonObject;
//    }
//    /**
//     * Description 保存申购
//     * @return 保存成功-true，失败-false
//     */
//    @Override
//    public boolean savesuppliesPurchaseDTO(AssetsApplyDTO AssetsApplyDTO) {
//        return true;
//    }
//    @Override
//    public boolean deleteSuppliesPurchase(Integer id) {
//        return true;
//    }
//}