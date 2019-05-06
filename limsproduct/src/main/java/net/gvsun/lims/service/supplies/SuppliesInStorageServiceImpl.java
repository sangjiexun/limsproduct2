package net.gvsun.lims.service.supplies;
import com.alibaba.fastjson.JSONObject;
import net.gvsun.lims.dto.suppliesInStorage.suppliesInStorageDTO;
import net.zjcclims.domain.User;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
@Service("SuppliesInStorageService")
public class SuppliesInStorageServiceImpl implements SuppliesInStorageService {
    @Autowired
    private ShareService shareService;


    /**
     * 通过id获取申购名目
     *
     * @param id 主键
     * @return 申购名目dto
     * @author chenwenmin
     */
    @Override
    public suppliesInStorageDTO getSuppliesInStorageId(Integer id) {
        suppliesInStorageDTO suppliesInStorageDTO = new suppliesInStorageDTO();
        //入库id
        suppliesInStorageDTO.setId(id);
        //申请人
        User applicantUserName = shareService.getUserDetail();
        suppliesInStorageDTO.setAcademyNumber(applicantUserName.getSchoolAcademy().getAcademyName());
        //入库日期
        Calendar InStoragedate = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        suppliesInStorageDTO.setDate(sdf.format(InStoragedate.getTime()));
        //入库编号
        suppliesInStorageDTO.setAcademyNumber("20190305");
        //售出单位
        suppliesInStorageDTO.setSellerFactory("庚商");
        //物品分类
        suppliesInStorageDTO.setGoodsCategory("化学试剂");
        //物品名称
        suppliesInStorageDTO.setGoodsName("油漆");
        //总价
        suppliesInStorageDTO.setMoney("1");
        //单位
        suppliesInStorageDTO.setUnit("瓶");
        //单价
        suppliesInStorageDTO.setItemPrice("20");
        //数量
        suppliesInStorageDTO.setQuantity("33");
        //发票号
        suppliesInStorageDTO.setInvoiceNumber("00009999");
        //备注
        //获取方式
        suppliesInStorageDTO.setStorageWay("申购入库");
        //所属部门
        suppliesInStorageDTO.setDepartment("软件开发");
        return suppliesInStorageDTO;
    }
    /**
     * Description 通过申购表知道申购的ID
    +     * @param page 当前页数
    +     * @param limit 当前页最大数据量
     */
    @Override
    public JSONObject getSuppliesInStorage() {
        List<suppliesInStorageDTO> suppliesInStorage = new ArrayList<>();
        int totalRecords = 10;
        for (int i = 0; i < totalRecords; i++) {
            suppliesInStorageDTO suppliesInStorageDTO = new suppliesInStorageDTO();
            // 项目id
            suppliesInStorageDTO.setId(i);
            //入库人
            User applicantUserName = shareService.getUserDetail();
            suppliesInStorageDTO.setAcademyNumber(applicantUserName.getSchoolAcademy().getAcademyName());
            //入库时间
            Calendar startData = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            suppliesInStorageDTO.setDate(sdf.format(startData.getTime()));
            suppliesInStorageDTO.setStorageWay("新建入库");
            suppliesInStorageDTO.setInvoiceNumber("2000000");
            suppliesInStorageDTO.setGoodsName("化学");
            suppliesInStorageDTO.setItemPrice("3333");
            suppliesInStorageDTO.setDepartment("化学系");
            suppliesInStorageDTO.setGoodsCategory("化学试剂");
            suppliesInStorageDTO.setQuantity("999");
            suppliesInStorageDTO.setMoney("444");
            suppliesInStorageDTO.setPurchaseNumber("4444");
            suppliesInStorageDTO.setUnit("瓶");
            suppliesInStorageDTO.setRemarks("备注");
            suppliesInStorage.add(suppliesInStorageDTO);
        }
        // 分页
       /* suppliesInStorage = suppliesInStorage.subList((page-1)*limit, Math.min((page)*limit, suppliesInStorage.size()));*/
        return getSuppliesInStorageJSON(suppliesInStorage, totalRecords);
    }
    /**
     * 将项目数据封装为前台可接收的json格式数据
     * @param SuppliesInStorage 申购列表
     * @param totalRecords 项目总数
     * @return json格式数据
     * @author
     */
    private JSONObject getSuppliesInStorageJSON(List<?> SuppliesInStorage, int totalRecords){
        JSONObject jsonObject = new JSONObject();
      /*  jsonObject.put("data", projects);*/
        jsonObject.put("count", totalRecords);
        jsonObject.put("code", 0);
        jsonObject.put("msg", "success");
        return jsonObject;
    }
    /**
     * Description 保存申购
     * @return 保存成功-true，失败-false
     */
    @Override
    public boolean saveSuppliesInStorageDTO(suppliesInStorageDTO suppliesInStorageDTO) {
        return true;
    }
    /**
     * Description 删除申购记录
     * @return 保存成功-true，失败-false
     */
    @Override
    public boolean deleteSuppliesInStorageDTO(Integer id) {
        return true;
    }
}