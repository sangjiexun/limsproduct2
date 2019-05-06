package net.gvsun.lims.service.labConstruction;

import com.alibaba.fastjson.JSONObject;
import net.gvsun.lims.dto.labConstruction.GrandSonProjectDTO;
import net.gvsun.lims.dto.labConstruction.ParentProjectDTO;
import net.gvsun.lims.dto.labConstruction.SonProjectDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface LabConstructionProjectService {

    /**
     * 通过id获取父项目
     * @param id 主键
     * @return 父项目显示dto
     * @author 黄保钱 2019-2-24
     */
    ParentProjectDTO getParentProjectById(Integer id);

    /**
     * 获取父项目列表
     * @return 主显示DTO
     * @author 黄保钱 2019-2-24
     * @param page 当前页数
     * @param limit 每页个数
     */
    JSONObject getParentProjects(Integer page, Integer limit);

    /**
     * 通过id获取父项目
     * @param id 主键
     * @return 父项目显示dto
     * @author 黄保钱 2019-2-24
     */
    SonProjectDTO getSonProjectById(Integer id);

    /**
     * 通过id获取孙项目
     * @param id 主键
     * @return 孙项目显示dto
     * @author 黄保钱 2019-2-24
     */
    GrandSonProjectDTO getGrandSonProjectById(Integer id);

    /**
     * 获取父项目列表(子项目列表用)
     * @return 父项目显示DTO列表
     * @author 黄保钱 2019-2-24
     */
    List<ParentProjectDTO> getParentProjectsForSonProject();

    /**
     * 获取父项目列表(孙项目列表用)
     * @return 父项目显示DTO列表
     * @author 黄保钱 2019-2-24
     */
    List<ParentProjectDTO> getParentProjectsForGrandSonProject();

    /**
     * Description 通过父项目id获取该父项目的子项目json格式数据
     * @param parentProjectId 父项目id
     * @param page 当前页数
     * @param limit 当前页最大数据量
     * @return 子项目json格式数据
     */
    JSONObject getSonProjects(Integer parentProjectId, Integer page, Integer limit);

    /**
     * Description 通过子项目id获取该子项目的孙项目json格式数据
     * @param sonProjectId 子项目id
     * @param page 当前页数
     * @param limit 当前页最大数据量
     * @param status 状态值（-1 -> 全部，0 -> 未审核，1 -> 审核中，2 -> 已经审核）
     * @return 孙项目json格式数据
     */
    JSONObject getGrandSonProjects(Integer sonProjectId, Integer page, Integer limit, Integer status);

    /**
     * Description 保存父项目
     * @param parentProjectDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     */
    boolean saveParentProject(ParentProjectDTO parentProjectDTO);

    /**
     * Description 保存子项目
     * @param sonProjectDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     */
    boolean saveSonProject(SonProjectDTO sonProjectDTO);

    /**
     * Description 保存孙项目
     * @param grandSonProjectDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     */
    boolean saveGrandSonProject(GrandSonProjectDTO grandSonProjectDTO);

    /**
     * Description 提交父项目
     * @param parentProjectDTO 保存的数据封装DTO
     * @return 成功-true，失败-false
     */
    boolean submitParentProject(ParentProjectDTO parentProjectDTO);

    /**
     * Description 提交子项目
     * @param sonProjectDTO 保存的数据封装DTO
     * @return 成功-true，失败-false
     */
    boolean submitSonProject(SonProjectDTO sonProjectDTO);

    /**
     * Description 提交孙项目
     * @param grandSonProjectDTO 保存的数据封装DTO
     * @return 成功-true，失败-false
     */
    boolean submitGrandSonProject(GrandSonProjectDTO grandSonProjectDTO);

    /**
     * Description 删除父项目
     * @param id 项目id
     * @return 成功-true，失败-false
     */
    boolean deleteParentProject(Integer id);

    /**
     * Description 删除子项目
     * @param id 项目id
     * @return 成功-true，失败-false
     */
    boolean deleteSonProject(Integer id);

    /**
     * Description 删除孙项目
     * @param id 项目id
     * @return 成功-true，失败-false
     */
    boolean deleteGrandSonProject(Integer id);

    /**
     * Description 保存审核结果
     * @param id 孙项目id
     * @param auditResult 审核结果
     * @param remark 审核备注
     * @return 保存审核成功-true, 失败-false
     * @author 黄保钱 2019-2-27
     */
    boolean saveProjectAudit(Integer id, int auditResult, String remark);

    /**
     * Description 上传文件
     * @param grandSonProjectId 孙项目id
     * @param request 请求
     * @param fileType 文件类型
     *                 （"relatedDocument" - 相关文件）
     *                 （"reportDocument" - 论证报告）
     *                 （"purchaseDocument" - 采购文件）
     *                 （"relatedContract" - 相关合同）
     *                 （"acceptanceDocument" - 验收申请表）
     * @return 保存文件成功-true, 失败-false
     * @author 黄保钱 2019-2-27
     */
    boolean uploadDocument(HttpServletRequest request, Integer grandSonProjectId, String fileType);

    /**
     * 获取当前审核状态
     * @param businessId 业务id
     * @return 当前审核状态
     * @author 黄保钱 2019-3-14
     */
    String getCurState(String businessId);

    /**
     * 保存当前审核结果
     * @param businessId 业务id
     * @param audit 审核结果
     * @param remark 备注
     * @param repeat 当前阶段
     * @return 下一级审核状态
     * @author 黄保钱 2019-3-14
     */
    String saveCurState(String businessId, int audit, String remark, int repeat);

    /**
     * 保存孙项目文件
     * @param fileTrueName 文件名
     * @param id 孙项目id
     * @param type 类型
     * @author 黄保钱 2019-3-15
     */
    void saveLabConstructionProjectDocument(String fileTrueName, Integer id,Integer type);

    /**
     * Description 通过孙项目json分类的数量
     * @param status 状态值（0 -> 未审核，1 -> 审核中，2 -> 已经审核）
     * @return 总数
     */
    String getGrandSonProjectsCount(Integer status);
}
