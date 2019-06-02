package net.gvsun.lims.api.labConstruction;

import com.alibaba.fastjson.JSONObject;
import net.gvsun.lims.dto.labConstruction.GrandSonProjectDTO;
import net.gvsun.lims.dto.labConstruction.ParentProjectDTO;
import net.gvsun.lims.dto.labConstruction.SonProjectDTO;
import net.gvsun.lims.service.labConstruction.LabConstructionProjectService;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 盐城新实验项目api
 */
@Controller("ApiLabConstructionController")
@RequestMapping("/lims/api/labConstruction")
public class ApiLabConstructionController {

    @Autowired
    private LabConstructionProjectService labConstructionProjectService;

    @Autowired
    private ShareService shareService;

    /**
     * 获取父项目列表
     * @param page 当前页数
     * @param limit 当前页限制大小
     * @return json字符串格式的父项目列表
     */
    @RequestMapping("/getParentProjects")
    @ResponseBody
    public String getParentProjects(HttpServletRequest request, String projectName, String createTime, Integer page, Integer limit){
        JSONObject jsonObject = labConstructionProjectService.getParentProjects(request, projectName, createTime, page, limit);

        return shareService.htmlEncode(jsonObject.toJSONString());
    }

    /**
     * 获取子项目列表
     * @param id 父项目id
     * @param page 当前页数
     * @param limit 当前页限制大小
     * @return json字符串格式的子项目列表
     */
    @RequestMapping("/getSonProjects")
    @ResponseBody
    public String getSonProjects(HttpServletRequest request, Integer id, String projectName, String projectImplementTime, String budgetBalanceTime, Integer page, Integer limit){
        JSONObject jsonObject = labConstructionProjectService.getSonProjects(request, id, page, limit, projectName, projectImplementTime, budgetBalanceTime);

        return shareService.htmlEncode(jsonObject.toJSONString());
    }

    /**
     * 获取孙项目列表
     * @param page 当前页数
     * @param id 子项目id
     * @param limit 当前页限制大小
     * @return json字符串格式的孙项目列表
     */
    @RequestMapping("/getGrandSonProjects")
    @ResponseBody
    public String getGrandSonProjects(Integer id, Integer page, Integer limit, Integer status){
        JSONObject jsonObject = labConstructionProjectService.getGrandSonProjects(id, page, limit, status);

        return shareService.htmlEncode(jsonObject.toJSONString());
    }

    /**
     * Description 保存父项目
     * @param parentProjectDTO 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author 黄保钱 2019-2-26
     */
    @RequestMapping("/saveParentProject")
    @ResponseBody
    public String saveParentProject(@RequestBody ParentProjectDTO parentProjectDTO){
        if(labConstructionProjectService.saveParentProject(parentProjectDTO)){
            return "success";
        }else{
            return "fail";
        }
    }

    /**
     * Description 保存子项目
     * @param sonProjectDTO 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author 黄保钱 2019-2-26
     */
    @RequestMapping("/saveSonProject")
    @ResponseBody
    public String saveSonProject(@RequestBody SonProjectDTO sonProjectDTO){
        return labConstructionProjectService.saveSonProject(sonProjectDTO);
    }

    /**
     * Description 保存孙项目
     * @param grandSonProjectDTO 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author 黄保钱 2019-2-26
     */
    @RequestMapping("/saveGrandSonProject")
    @ResponseBody
    public String saveGrandSonProject(@RequestBody GrandSonProjectDTO grandSonProjectDTO){
        return labConstructionProjectService.saveGrandSonProject(grandSonProjectDTO);
    }

    /**
     * Description 提交父项目
     * @param parentProjectDTO 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author 黄保钱 2019-2-26
     */
    @RequestMapping("/submitParentProject")
    @ResponseBody
    public String submitParentProject(@RequestBody ParentProjectDTO parentProjectDTO){
        if(labConstructionProjectService.submitParentProject(parentProjectDTO)){
            return "success";
        }else{
            return "fail";
        }
    }

    /**
     * Description 提交子项目
     * @param sonProjectDTO 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author 黄保钱 2019-2-26
     */
    @RequestMapping("/submitSonProject")
    @ResponseBody
    public String submitSonProject(@RequestBody SonProjectDTO sonProjectDTO){
        return labConstructionProjectService.submitSonProject(sonProjectDTO);
    }

    /**
     * Description 提交孙项目
     * @param grandSonProjectDTO 参数封装DTO
     * @return 成功-"success"，失败-"fail"
     * @author 黄保钱 2019-2-26
     */
    @RequestMapping("/submitGrandSonProject")
    @ResponseBody
    public String submitGrandSonProject(@RequestBody GrandSonProjectDTO grandSonProjectDTO){
        return labConstructionProjectService.submitGrandSonProject(grandSonProjectDTO);
    }

    /**
     * Description 删除父项目
     * @param id 项目id
     * @return 成功-"success"，失败-"fail"
     * @author 黄保钱 2019-2-26
     */
    @RequestMapping("/deleteParentProject")
    @ResponseBody
    public String deleteParentProject(Integer id){
        if(labConstructionProjectService.deleteParentProject(id)){
            return "success";
        }else{
            return "fail";
        }
    }

    /**
     * Description 删除子项目
     * @param id 项目id
     * @return 成功-"success"，失败-"fail"
     * @author 黄保钱 2019-2-26
     */
    @RequestMapping("/deleteSonProject")
    @ResponseBody
    public String deleteSonProject(Integer id){
        if(labConstructionProjectService.deleteSonProject(id)){
            return "success";
        }else{
            return "fail";
        }
    }

    /**
     * Description 删除孙项目
     * @param id 项目id
     * @return 成功-"success"，失败-"fail"
     * @author 黄保钱 2019-2-26
     */
    @RequestMapping("/deleteGrandSonProject")
    @ResponseBody
    public String deleteGrandSonProject(Integer id){
        if(labConstructionProjectService.deleteGrandSonProject(id)){
            return "success";
        }else{
            return "fail";
        }
    }

    /**
     * Description 保存审核结果
     * @param id 孙项目id
     * @param auditResult 审核结果
     * @param remark 审核备注
     * @return 保存审核成功-"success", 失败-"fail"
     * @author 黄保钱 2019-2-27
     */
    @RequestMapping("/saveProjectAudit")
    @ResponseBody
    public String saveProjectAudit(Integer id, int auditResult, String remark){
        if(labConstructionProjectService.saveProjectAudit(id, auditResult, remark)){
            return "success";
        }else{
            return "fail";
        }
    }

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
     * @return 保存文件成功-"success", 失败-"fail"
     * @author 黄保钱 2019-2-27
     */
    @RequestMapping("/uploadDocument")
    @ResponseBody
    public String uploadDocument(HttpServletRequest request, Integer grandSonProjectId, String fileType){
        JSONObject jsonObject = new JSONObject();
        if(labConstructionProjectService.uploadDocument(request, grandSonProjectId, fileType)){
            jsonObject.put("code", 0);
        }else{
            jsonObject.put("code", 1);
        }
        return shareService.htmlEncode(jsonObject.toJSONString());
    }

}
