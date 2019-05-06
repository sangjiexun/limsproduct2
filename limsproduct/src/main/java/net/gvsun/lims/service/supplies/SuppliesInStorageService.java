package net.gvsun.lims.service.supplies;
import com.alibaba.fastjson.JSONObject;
import net.gvsun.lims.dto.suppliesInStorage.suppliesInStorageDTO;
public interface SuppliesInStorageService {
    /**
     * 通过id获取入库名目
     * @param id 主键
     * @return 入库名目dto
     * @author 陈文敏
     *
     **/
    public suppliesInStorageDTO getSuppliesInStorageId(Integer id);
   JSONObject getSuppliesInStorage();
    boolean saveSuppliesInStorageDTO(suppliesInStorageDTO suppliesInStorageDTO);
    boolean deleteSuppliesInStorageDTO(Integer id);
}