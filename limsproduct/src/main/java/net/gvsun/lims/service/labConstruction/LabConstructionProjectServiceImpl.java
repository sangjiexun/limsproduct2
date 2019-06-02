package net.gvsun.lims.service.labConstruction;

import com.alibaba.fastjson.JSONObject;
import net.gvsun.lims.dto.labConstruction.GrandSonProjectDTO;
import net.gvsun.lims.dto.labConstruction.ParentProjectDTO;
import net.gvsun.lims.dto.labConstruction.SonProjectDTO;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.util.HttpClientUtil;
import net.zjcclims.web.common.PConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("LabConstructionProjectServiceNew")
public class LabConstructionProjectServiceImpl implements LabConstructionProjectService {

    @Autowired
    private ShareService shareService;
    @Autowired
    private LabConstructionParentProjectDAO labConstructionParentProjectDAO;
    @Autowired
    private LabConstructionSonProjectDAO labConstructionSonProjectDAO;
    @Autowired
    private LabConstructionGrandsonProjectDAO labConstructionGrandsonProjectDAO;
    @Autowired
    private LabConstructionProjectAuditNewDAO labConstructionProjectAuditNewDAO;
    @Autowired
    private LabConstructionProjectDocumentDAO labConstructionProjectDocumentDAO;
    @Autowired
    private PConfig pConfig;
    @Autowired
    private CommonDocumentDAO commonDocumentDAO;


    /**
     * 通过id获取父项目
     * @param id 主键
     * @return 父项目显示dto
     * @author 黄保钱 2019-2-24
     */
    @Override
    public ParentProjectDTO getParentProjectById(Integer id) {
        LabConstructionParentProject parent = labConstructionParentProjectDAO.findLabConstructionParentProjectById(id);
        ParentProjectDTO parentProjectDTO = new ParentProjectDTO();

        // 项目id
        parentProjectDTO.setId(id);
        // 项目名称
        parentProjectDTO.setProjectName(parent.getProjectName());
        // 项目预算
        parentProjectDTO.setBudget(parent.getBudget());

        // 创建人
        User createUser = shareService.findUserByUsername(parent.getCreateUser());
        // 创建人姓名
        parentProjectDTO.setCreateUser(createUser.getCname());
        // 创建人所属部门
        parentProjectDTO.setUnitName(createUser.getSchoolAcademy().getAcademyName());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 创建时间
        Calendar createTime = parent.getCreateTime();
        parentProjectDTO.setCreateTime(sdf.format(createTime.getTime()));

        // 提交状态
        parentProjectDTO.setSubmitted(parent.getSubmitted() ? 1 : 2);

        return parentProjectDTO;
    }

    /**
     * 获取父项目列表
     * @return 主显示DTO
     * @author 黄保钱 2019-2-24
     * @param page 当前页数
     * @param limit 当前的最大数据大小
     */
    @Override
    public JSONObject getParentProjects(HttpServletRequest request, ParentProjectDTO parentProjectDTO, Integer page, Integer limit) {
        StringBuffer hql = new StringBuffer("select c from LabConstructionParentProject c where 1=1");
        User user = shareService.getUserDetail();
        // 权限等级
        String authName = request.getSession().getAttribute("selected_role").toString();
        int type = shareService.getLevelByAuthName(authName);
        if (type == 5) {// 院级可操作权限
            hql = new StringBuffer("select c.labConstructionParentProject from LabConstructionSonProject c inner join c.schoolAcademies cs where 1=1");
            hql.append(" and cs.academyNumber='"+user.getSchoolAcademy().getAcademyNumber()+"'");
        }
        // 查询条件
        // 项目名称/创建人/创建人所属学院
        if (parentProjectDTO.getProjectName()!=null) {
            hql.append(" and (c.projectName like '%"+ parentProjectDTO.getProjectName() +"%')");
        }
        // 起止时间
        if (parentProjectDTO.getCreateTime()!=null) {
            String[] time = parentProjectDTO.getCreateTime().split("~");
            if (time[0]!=null && !time[0].equals("") && time[1]!=null && !time[1].equals("")) {
                hql.append(" and c.createTime between '"+time[0]+"' and '"+time[1]+"'");
            }else if (time[0]!=null && !time[0].equals("")) {
                hql.append(" and c.createTime >= '"+time[0]+"'");
            }else if (time[1]!=null && !time[1].equals("")) {
                hql.append(" and c.createTime <= '"+time[1]+"'");
            }
        }
        // 排序-倒序
        hql.append(" order by c.createTime desc");

        List<LabConstructionParentProject> parentProjects = labConstructionParentProjectDAO.executeQuery(hql.toString(),(page-1)*limit, limit);
        Set<LabConstructionParentProject> projectSet = labConstructionParentProjectDAO.findAllLabConstructionParentProjects((page-1)*limit, limit);
        List<ParentProjectDTO> parents = new ArrayList<>();
        int totalRecords = labConstructionParentProjectDAO.executeQuery(hql.toString(),0, -1).size();
        for (LabConstructionParentProject p: parentProjects) {
            ParentProjectDTO newParentProjectDTO = new ParentProjectDTO();
            // 项目id
            newParentProjectDTO.setId(p.getId());
            // 项目名称
            newParentProjectDTO.setProjectName(p.getProjectName());
            // 项目预算
            newParentProjectDTO.setBudget(p.getBudget());

            // 创建人
            User createUser = shareService.findUserByUsername(p.getCreateUser());
            // 创建人姓名
            newParentProjectDTO.setCreateUser(createUser.getCname());
            // 创建人所属部门
            newParentProjectDTO.setUnitName(createUser.getSchoolAcademy().getAcademyName());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // 创建时间
            Calendar createTime = p.getCreateTime();
            newParentProjectDTO.setCreateTime(sdf.format(createTime.getTime()));

            // 提交状态
            newParentProjectDTO.setSubmitted(p.getSubmitted() ? 1 : 2);

            parents.add(newParentProjectDTO);
        }
        return getProjectJSON(parents, totalRecords);
    }

    /**
     * 通过id获取子项目
     * @param id 主键
     * @return 子项目显示dto
     * @author 黄保钱 2019-2-24
     */
    @Override
    public SonProjectDTO getSonProjectById(Integer id) {
        LabConstructionSonProject sonProject = labConstructionSonProjectDAO.findLabConstructionSonProjectById(id);
        SonProjectDTO sonProjectDTO = new SonProjectDTO();

        // 项目id
        sonProjectDTO.setId(id);
        // 项目名称
        sonProjectDTO.setProjectName(sonProject.getProjectName());
        // 项目所属学院
        String academyName = "";
        for(SchoolAcademy sa: sonProject.getSchoolAcademies()) {
            academyName += sa.getAcademyName() + ",";
        }
        sonProjectDTO.setAcademyName(academyName.length() > 0 ? academyName.substring(0, academyName.length() - 1) : "");
        // 学院编号
        String academyNumber = "";
        for(SchoolAcademy sa: sonProject.getSchoolAcademies()) {
            academyNumber += sa.getAcademyNumber() + ",";
        }
        sonProjectDTO.setAcademyName(academyNumber.length() > 0 ? academyNumber.substring(0, academyNumber.length() - 1) : "");
        // 项目预算
        sonProjectDTO.setBudget(sonProject.getBudget());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 创建时间
        Calendar createTime = sonProject.getCreateTime();
        sonProjectDTO.setCreateTime(sdf.format(createTime.getTime()));
        // 预算结算时间
        Calendar budgetBalanceTime = sonProject.getBudgetBalanceTime();
        sonProjectDTO.setBudgetBalanceTime(sdf.format(budgetBalanceTime.getTime()));
        // 项目实施时间
        Calendar projectImplementTime = sonProject.getProjectImplementTime();
        sonProjectDTO.setProjectImplementTime(sdf.format(projectImplementTime.getTime()));

        // 创建人
        User createUser = shareService.findUserByUsername(sonProject.getCreateUser());
        // 创建人姓名
        sonProjectDTO.setCreateUser(createUser.getCname());
        // 创建人所属部门
        sonProjectDTO.setUnitName(createUser.getSchoolAcademy().getAcademyName());

        // 所属父项目名称
        sonProjectDTO.setParentProjectName(sonProject.getLabConstructionParentProject().getProjectName());
        // 所属父项目id
        sonProjectDTO.setParentProjectId(sonProject.getLabConstructionParentProject().getId());

        // 提交状态
        sonProjectDTO.setSubmitted(sonProject.getSubmitted() ? 1 : 2);

        return sonProjectDTO;
    }

    /**
     * 通过id获取孙项目
     * @param id 主键
     * @return 孙项目显示dto
     * @author 黄保钱 2019-2-24
     */
    @Override
    public GrandSonProjectDTO getGrandSonProjectById(Integer id) {
        LabConstructionGrandsonProject grandsonProject = labConstructionGrandsonProjectDAO.findLabConstructionGrandsonProjectById(id);
        GrandSonProjectDTO grandSonProjectDTO = new GrandSonProjectDTO();

        // 项目id
        grandSonProjectDTO.setId(id);
        // 项目名称
        grandSonProjectDTO.setProjectName(grandsonProject.getProjectName());

        // 项目进度
        if(grandsonProject.getLabConstructionProjectAuditNews() != null && grandsonProject.getLabConstructionProjectAuditNews().size() > 0) {
            grandSonProjectDTO.setProgress(grandsonProject.getLabConstructionProjectAuditNews().iterator().next().getRemark());
            // 项目流程线
            List<Object[]> objects = new ArrayList<>();
            for(LabConstructionProjectAuditNew l: grandsonProject.getLabConstructionProjectAuditNews()) {
                Object[] object = new Object[3];
                object[0] = l.getResult();
                object[1] = l.getRemark();
                objects.add(object);
            }
            grandSonProjectDTO.setProcessLine(objects);
        }
        // 项目状态
        grandSonProjectDTO.setProgressStage(grandsonProject.getStage());

        // 项目验收实际金额
        grandSonProjectDTO.setAcceptanceActualAmount(grandsonProject.getAcceptanceaActualAmount());
        // 项目预算
        grandSonProjectDTO.setBudget(grandsonProject.getBudget());
        // 项目结余
        BigDecimal restAmount = grandsonProject
                .getLabConstructionSonProject()
                .getLabConstructionParentProject()
                .getBudget()
                .subtract(grandsonProject.getAcceptanceaActualAmount())
                .subtract(grandsonProject.getTenderActualAmount());
        grandSonProjectDTO.setRestAmount(restAmount);
        // 项目招标纪要实际金额
        grandSonProjectDTO.setTenderActualAmount(grandsonProject.getTenderActualAmount());

        // 创建人
        User createUser = shareService.findUserByUsername(grandsonProject.getCreateUser());
        // 创建人姓名
        grandSonProjectDTO.setCreateUser(createUser.getCname());
        // 创建人所属部门
        grandSonProjectDTO.setUnitName(createUser.getSchoolAcademy().getAcademyName());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 创建时间
        Calendar createTime = grandsonProject.getCreateTime();
        grandSonProjectDTO.setCreateTime(sdf.format(createTime.getTime()));
        // 项目实施时间
        Calendar projectImplementTime = grandsonProject.getProjectImplementTime();
        grandSonProjectDTO.setProjectImplementTime(sdf.format(projectImplementTime.getTime()));

        // 所属子项目
        grandSonProjectDTO.setSonProjectName(grandsonProject.getLabConstructionSonProject().getProjectName());
        grandSonProjectDTO.setSonProjectId(grandsonProject.getLabConstructionSonProject().getId());
        // 所属父项目
        grandSonProjectDTO.setParentProjectName(grandsonProject.getLabConstructionSonProject().getLabConstructionParentProject().getProjectName());
        grandSonProjectDTO.setParentProjectId(grandsonProject.getLabConstructionSonProject().getLabConstructionParentProject().getId());

        // 文件
        // 相关文件
        Map<String, String> relatedDocuments = new HashMap<>();
        Map<String, String> reportDocuments = new HashMap<>();
        Map<String, String> purchaseDocuments = new HashMap<>();
        Map<String, String> relatedContracts = new HashMap<>();
        Map<String, String> acceptanceDocuments = new HashMap<>();
        for (LabConstructionProjectDocument document : grandsonProject.getLabConstructionProjectDocuments()) {
            CommonDocument cd = document.getCommonDocument();
            switch (document.getStage()){
                case 1:
                    relatedDocuments.put(cd.getDocumentName(), cd.getDocumentUrl());
                    break;
                case 2:
                    reportDocuments.put(cd.getDocumentName(), cd.getDocumentUrl());
                    break;
                case 3:
                    purchaseDocuments.put(cd.getDocumentName(), cd.getDocumentUrl());
                    break;
                case 4:
                    relatedContracts.put(cd.getDocumentName(), cd.getDocumentUrl());
                    break;
                case 5:
                    acceptanceDocuments.put(cd.getDocumentName(), cd.getDocumentUrl());
                    break;
                default:
            }
        }
        grandSonProjectDTO.setRelatedDocuments(relatedDocuments);
        // 相关合同文件
        grandSonProjectDTO.setRelatedContracts(relatedContracts);
        // 验收申请表文件
        grandSonProjectDTO.setAcceptanceDocuments(acceptanceDocuments);
        // 采购文件
        grandSonProjectDTO.setPurchaseDocuments(purchaseDocuments);
        // 论证报告文件
        grandSonProjectDTO.setReportDocuments(reportDocuments);

        // 提交状态
        grandSonProjectDTO.setSubmitted(grandsonProject.getSubmitted() ? 1 : 2);

        return grandSonProjectDTO;
    }

    /**
     * 获取父项目列表(子项目列表用)
     * @return 父项目显示DTO列表
     * @author 黄保钱 2019-2-24
     */
    @Override
    public List<ParentProjectDTO> getParentProjectsForSonProject() {
        Set<LabConstructionParentProject> parentProjects = labConstructionParentProjectDAO.findAllLabConstructionParentProjects(0, -1);
        List<ParentProjectDTO> parents = new ArrayList<>();
        int totalRecords = labConstructionParentProjectDAO.findAllLabConstructionParentProjects(0, -1).size();
        for (LabConstructionParentProject parentProject: parentProjects) {
            ParentProjectDTO parentProjectDTO = new ParentProjectDTO();
            // 项目id
            parentProjectDTO.setId(parentProject.getId());
            // 项目名称
            parentProjectDTO.setProjectName(parentProject.getProjectName());
            // 项目预算
            parentProjectDTO.setBudget(parentProject.getBudget());

            parents.add(parentProjectDTO);
        }
        return parents;
    }

    /**
     * 获取父项目列表(孙项目列表用)
     * @return 父项目显示DTO列表
     * @author 黄保钱 2019-2-24
     */
    @Override
    public List<ParentProjectDTO> getParentProjectsForGrandSonProject() {
        Set<LabConstructionParentProject> parentProjects = labConstructionParentProjectDAO.findAllLabConstructionParentProjects(0, -1);
        List<ParentProjectDTO> parents = new ArrayList<>();
        int totalRecords = labConstructionParentProjectDAO.findAllLabConstructionParentProjects(0, -1).size();;
        for (LabConstructionParentProject parentProject: parentProjects) {
            ParentProjectDTO parentProjectDTO = new ParentProjectDTO();
            // 项目id
            parentProjectDTO.setId(parentProject.getId());
            // 项目名称
            parentProjectDTO.setProjectName(parentProject.getProjectName());
            // 项目预算
            parentProjectDTO.setBudget(parentProject.getBudget());

            // 子项目
            List<SonProjectDTO> sonProjects = new ArrayList<>();
            int totalRecordsForSonProject = 5;
            for (LabConstructionSonProject sonProject: parentProject.getLabConstructionSonProjects()) {
                SonProjectDTO sonProjectDTO = new SonProjectDTO();
                // 子项目id
                sonProjectDTO.setId(sonProject.getId());
                // 项目名称
                sonProjectDTO.setProjectName(sonProject.getProjectName());
                // 项目所属学院
                String academyName = "";
                for(SchoolAcademy sa: sonProject.getSchoolAcademies()) {
                    academyName += sa.getAcademyName() + ",";
                }
                sonProjectDTO.setAcademyName(academyName.length() > 0 ? academyName.substring(0, academyName.length() - 1) : "");
                // 项目预算
                sonProjectDTO.setBudget(sonProject.getBudget());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                // 预算结算时间
                Calendar budgetBalanceTime = sonProject.getBudgetBalanceTime();
                sonProjectDTO.setBudgetBalanceTime(sdf.format(budgetBalanceTime.getTime()));
                // 项目实施时间
                Calendar projectImplementTime = sonProject.getProjectImplementTime();
                sonProjectDTO.setProjectImplementTime(sdf.format(projectImplementTime.getTime()));
                sonProjects.add(sonProjectDTO);
            }

            parentProjectDTO.setSonProjects(sonProjects);
            parents.add(parentProjectDTO);
        }
        return parents;
    }

    /**
     * Description 通过父项目id获取该父项目的子项目json格式数据
     * @param parentProjectId 父项目id
     * @param page 当前页数
     * @param limit 当前页最大数据量
     * @return 子项目json格式数据
     */
    public JSONObject getSonProjects(Integer parentProjectId, Integer page, Integer limit){
        List<LabConstructionSonProject> sonProjectSet = labConstructionSonProjectDAO
                .executeQuery("select son from LabConstructionSonProject son where son.labConstructionParentProject.id=" + parentProjectId +" order by son.createTime desc", (page-1)*limit, limit);
        List<SonProjectDTO> sonProjects = new ArrayList<>();
        int totalRecords = labConstructionSonProjectDAO
                .executeQuery("select son from LabConstructionSonProject son where son.labConstructionParentProject.id=" + parentProjectId).size();

        for (LabConstructionSonProject sonProject: sonProjectSet) {
            SonProjectDTO sonProjectDTO = new SonProjectDTO();
            // 项目id
            sonProjectDTO.setId(sonProject.getId());
            // 项目名称
            sonProjectDTO.setProjectName(sonProject.getProjectName());
            // 项目所属学院
            String academyName = "";
            for(SchoolAcademy sa: sonProject.getSchoolAcademies()) {
                academyName += sa.getAcademyName() + ",";
            }
            sonProjectDTO.setAcademyName(academyName.length() > 0 ? academyName.substring(0, academyName.length() - 1) : "");
            // 项目预算
            sonProjectDTO.setBudget(sonProject.getBudget());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // 预算结算时间
            Calendar budgetBalanceTime = sonProject.getBudgetBalanceTime();
            sonProjectDTO.setBudgetBalanceTime(sdf.format(budgetBalanceTime.getTime()));
            // 项目实施时间
            Calendar projectImplementTime = sonProject.getProjectImplementTime();
            sonProjectDTO.setProjectImplementTime(sdf.format(projectImplementTime.getTime()));

            // 提交状态
            sonProjectDTO.setSubmitted(sonProject.getSubmitted() ? 1 : 2);

            sonProjects.add(sonProjectDTO);
        }

        return getProjectJSON(sonProjects, totalRecords);
    }

    /**
     * Description 通过子项目id获取该子项目的孙项目json格式数据
     * @param sonProjectId 子项目id
     * @param page 当前页数
     * @param limit 当前页最大数据量
     * @param status 状态值（-1 -> 全部，0 -> 未审核，1 -> 审核中，2 -> 已经审核）
     * @return 孙项目json格式数据
     */
    public JSONObject getGrandSonProjects(Integer sonProjectId, Integer page, Integer limit, Integer status){
        String sqlSearch = "select grandson from LabConstructionGrandsonProject grandson where 1=1";
        String sqlCondition = "";
        sqlCondition += " and grandson.labConstructionSonProject.id= " + sonProjectId;
        switch (status) {
            case -1:
                break;
            case 0:
                sqlCondition += " and grandson.stage=1 and grandson.status=3 ";
                break;
            case 1:
                sqlCondition += " and ((grandson.stage!=1 and grandson.status!=3) or (grandson.stage!=5 and grandson.status!=1))";
                break;
            case 2:
                sqlCondition += " and grandson.stage=5 and grandson.status=1";
                break;
            default:
        }
        sqlCondition += " order by grandson.createTime desc";
        sqlSearch += sqlCondition;
        List<LabConstructionGrandsonProject> grandsonProjectList = labConstructionGrandsonProjectDAO
                .executeQuery(sqlSearch, (page-1)*limit, limit);

        String sqlCount = "select count(grandson) from LabConstructionGrandsonProject grandson where 1=1 ";
        sqlCount += sqlCondition;
        List<GrandSonProjectDTO> grandSonProjects = new ArrayList<>();
        BigInteger totalRecords = BigInteger.valueOf((Long) labConstructionGrandsonProjectDAO
                        .executeQuerySingleResult(sqlCount));

        for (LabConstructionGrandsonProject grandsonProject: grandsonProjectList) {
            GrandSonProjectDTO grandSonProjectDTO = new GrandSonProjectDTO();

            // 项目id
            grandSonProjectDTO.setId(grandsonProject.getId());
            // 项目名称
            grandSonProjectDTO.setProjectName(grandsonProject.getProjectName());
            // 子项目id
            grandSonProjectDTO.setSonProjectId(grandsonProject.getLabConstructionSonProject().getId());

            grandSonProjectDTO.setProgressStage(grandsonProject.getStage());
            // 项目进度状态
            grandSonProjectDTO.setProgressState(grandsonProject.getStatus());
            // 项目预算
            grandSonProjectDTO.setBudget(grandsonProject.getBudget());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // 项目实施时间
            Calendar projectImplementTime = grandsonProject.getProjectImplementTime();
            grandSonProjectDTO.setProjectImplementTime(sdf.format(projectImplementTime.getTime()));

            String document = "";
            for(LabConstructionProjectDocument projectDocument: grandsonProject.getLabConstructionProjectDocuments()){
                document += projectDocument.getCommonDocument().getDocumentName() + ",";
            }
            // 相关文件
            grandSonProjectDTO.setDocuments(document.length() > 0 ? document.substring(0, document.length() - 1) : "");

            // 提交状态
            grandSonProjectDTO.setSubmitted(grandsonProject.getSubmitted() ? 1 : 2);

            grandSonProjects.add(grandSonProjectDTO);
        }

        return getProjectJSON(grandSonProjects, totalRecords.intValue());

    }

    /**
     * 将项目数据封装为前台可接收的json格式数据
     * @param projects 项目列表
     * @param totalRecords 项目总数
     * @return json格式数据
     * @author 黄保钱 2019-2-26
     */
    private JSONObject getProjectJSON(List<?> projects, int totalRecords){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", projects);
        jsonObject.put("count", totalRecords);
        jsonObject.put("code", 0);
        jsonObject.put("msg", "success");
        return jsonObject;
    }

    /**
     * Description 保存父项目
     * @return 保存成功-true，失败-false
     */
    @Override
    public boolean saveParentProject(ParentProjectDTO parentProjectDTO) {
        LabConstructionParentProject parentProject;
        if(parentProjectDTO.getId() == null){
            parentProject = new LabConstructionParentProject();
        }else{
            parentProject = labConstructionParentProjectDAO.findLabConstructionParentProjectById(parentProjectDTO.getId());
        }

        // 项目名称
        parentProject.setProjectName(parentProjectDTO.getProjectName());
        // 项目预算
        parentProject.setBudget(parentProjectDTO.getBudget());

        // 创建人
        parentProject.setCreateUser(shareService.getUserDetail().getUsername());
        // 创建时间
        parentProject.setCreateTime(Calendar.getInstance());
        // 是否提交
        parentProject.setSubmitted(false);

        parentProject = labConstructionParentProjectDAO.store(parentProject);

        return parentProject.getId() != null && parentProject.getId() > 0;
    }

    /**
     * Description 保存子项目
     * @param sonProjectDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     */
    @Override
    public String saveSonProject(SonProjectDTO sonProjectDTO) {
        // 判断预算是否超额
        LabConstructionParentProject parentProject = labConstructionParentProjectDAO.findLabConstructionParentProjectById(sonProjectDTO.getParentProjectId());
        BigDecimal budget = new BigDecimal(0);
        for (LabConstructionSonProject sProject : parentProject.getLabConstructionSonProjects()) {// 遍历所有子项目，计算预算总额
            budget = budget.add(sProject.getBudget());
        }
        budget = budget.add(sonProjectDTO.getBudget());
        if (budget.compareTo(parentProject.getBudget()) == 1) {// 超预算
            return "over";
        }

        LabConstructionSonProject sonProject;
        if(sonProjectDTO.getId() == null) {
            sonProject = new LabConstructionSonProject();
        }else{
            sonProject = labConstructionSonProjectDAO.findLabConstructionSonProjectById(sonProjectDTO.getId());
        }

        // 项目预算
        sonProject.setBudget(sonProjectDTO.getBudget());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 预算结算时间
            Calendar budgetBalanceTime = Calendar.getInstance();
            budgetBalanceTime.setTime(sdf.parse(sonProjectDTO.getBudgetBalanceTime()));
            sonProject.setBudgetBalanceTime(budgetBalanceTime);
            // 项目实施时间
            Calendar projectImplementTime = Calendar.getInstance();
            projectImplementTime.setTime(sdf.parse(sonProjectDTO.getProjectImplementTime()));
            sonProject.setProjectImplementTime(projectImplementTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 创建时间
        sonProject.setCreateTime(Calendar.getInstance());
        // 创建人
        sonProject.setCreateUser(shareService.getUserDetail().getUsername());
        // 项目名称
        sonProject.setProjectName(sonProjectDTO.getProjectName());
        // 指定学院
        Set<SchoolAcademy> schoolAcademies = new HashSet<>();
        for(String academyNumber: sonProjectDTO.getAcademyNumber().split(",")) {
            schoolAcademies.add(shareService.findSchoolAcademyByPrimaryKey(academyNumber));
        }
        sonProject.setSchoolAcademies(schoolAcademies);
        // 父项目
        sonProject.setLabConstructionParentProject(labConstructionParentProjectDAO.findLabConstructionParentProjectById(sonProjectDTO.getParentProjectId()));
        // 是否提交
        sonProject.setSubmitted(false);

        sonProject = labConstructionSonProjectDAO.store(sonProject);

        if (sonProject.getId() != null && sonProject.getId() > 0) {
            return "success";
        }else {
            return "fail";
        }
    }

    /**
     * Description 保存孙项目
     * @param grandSonProjectDTO 保存的数据封装DTO
     * @return 保存成功-true，失败-false
     */
    @Override
    public String saveGrandSonProject(GrandSonProjectDTO grandSonProjectDTO) {
        // 判断预算是否超额
        LabConstructionSonProject sonProject = labConstructionSonProjectDAO.findLabConstructionSonProjectById(grandSonProjectDTO.getSonProjectId());
        BigDecimal budget = new BigDecimal(0);
        for (LabConstructionGrandsonProject gProject : sonProject.getLabConstructionGrandsonProjects()) {// 遍历所有孙项目，计算预算总额
            budget = budget.add(gProject.getBudget());
        }
        budget = budget.add(grandSonProjectDTO.getBudget());
        if (budget.compareTo(sonProject.getBudget()) == 1) {// 超预算
            return "over";
        }

        LabConstructionGrandsonProject grandsonProject;
        if(grandSonProjectDTO.getId() == null) {
            grandsonProject = new LabConstructionGrandsonProject();
        }else{
            grandsonProject = labConstructionGrandsonProjectDAO.findLabConstructionGrandsonProjectById(grandSonProjectDTO.getId());
        }

        grandsonProject.setAcceptanceaActualAmount(grandSonProjectDTO.getAcceptanceActualAmount());
        grandsonProject.setBudget(grandSonProjectDTO.getBudget());
        grandsonProject.setCreateTime(Calendar.getInstance());
        grandsonProject.setCreateUser(shareService.getUserDetail().getUsername());
        grandsonProject.setLabConstructionSonProject(sonProject);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Calendar projectImplementTime = Calendar.getInstance();
            projectImplementTime.setTime(sdf.parse(grandSonProjectDTO.getProjectImplementTime()));
            grandsonProject.setProjectImplementTime(projectImplementTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        grandsonProject.setProjectName(grandSonProjectDTO.getProjectName());
        grandsonProject.setStage(grandSonProjectDTO.getProgressStage() == null ? 1 : grandSonProjectDTO.getProgressStage());
        grandsonProject.setStatus(grandSonProjectDTO.getProgressState() == null ? 0 : grandSonProjectDTO.getProgressState());
        grandsonProject.setTenderActualAmount(grandSonProjectDTO.getTenderActualAmount());
        grandsonProject.setSubmitted(false);

        grandsonProject = labConstructionGrandsonProjectDAO.store(grandsonProject);

        saveCurState(grandsonProject.getId().toString(), 0, null, 0);

        if (grandsonProject.getId() != null && grandsonProject.getId() > 0) {
            return "success";
        }else {
            return "fail";
        }
    }

    /**
     * Description 提交父项目
     * @param parentProjectDTO 保存的数据封装DTO
     * @return 成功-true，失败-false
     */
    @Override
    public boolean submitParentProject(ParentProjectDTO parentProjectDTO) {
        LabConstructionParentProject parentProject;
        if(parentProjectDTO.getId() == null){
            parentProject = new LabConstructionParentProject();
        }else{
            parentProject = labConstructionParentProjectDAO.findLabConstructionParentProjectById(parentProjectDTO.getId());
        }

        // 项目名称
        parentProject.setProjectName(parentProjectDTO.getProjectName());
        // 项目预算
        parentProject.setBudget(parentProjectDTO.getBudget());

        // 创建人
        parentProject.setCreateUser(shareService.getUserDetail().getUsername());
        // 创建时间
        parentProject.setCreateTime(Calendar.getInstance());
        // 是否提交
        parentProject.setSubmitted(true);

        parentProject = labConstructionParentProjectDAO.store(parentProject);
        return parentProject.getId() != null && parentProject.getId() > 0;
    }

    /**
     * Description 提交子项目
     * @param sonProjectDTO 保存的数据封装DTO
     * @return 成功-true，失败-false
     */
    @Override
    public String submitSonProject(SonProjectDTO sonProjectDTO) {
        // 判断预算是否超额
        LabConstructionParentProject parentProject = labConstructionParentProjectDAO.findLabConstructionParentProjectById(sonProjectDTO.getParentProjectId());
        BigDecimal budget = new BigDecimal(0);
        for (LabConstructionSonProject sProject : parentProject.getLabConstructionSonProjects()) {// 遍历所有子项目，计算预算总额
            budget = budget.add(sProject.getBudget());
        }
        budget = budget.add(sonProjectDTO.getBudget());
        if (budget.compareTo(parentProject.getBudget()) == 1) {// 超预算
            return "over";
        }

        LabConstructionSonProject sonProject;
        if(sonProjectDTO.getId() == null) {
            sonProject = new LabConstructionSonProject();
        }else{
            sonProject = labConstructionSonProjectDAO.findLabConstructionSonProjectById(sonProjectDTO.getId());
        }

        // 项目预算
        sonProject.setBudget(sonProjectDTO.getBudget());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 预算结算时间
            Calendar budgetBalanceTime = Calendar.getInstance();
            budgetBalanceTime.setTime(sdf.parse(sonProjectDTO.getBudgetBalanceTime()));
            sonProject.setBudgetBalanceTime(budgetBalanceTime);
            // 项目实施时间
            Calendar projectImplementTime = Calendar.getInstance();
            projectImplementTime.setTime(sdf.parse(sonProjectDTO.getProjectImplementTime()));
            sonProject.setProjectImplementTime(projectImplementTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 创建时间
        sonProject.setCreateTime(Calendar.getInstance());
        // 创建人
        sonProject.setCreateUser(shareService.getUserDetail().getUsername());
        // 项目名称
        sonProject.setProjectName(sonProjectDTO.getProjectName());
        // 指定学院
        Set<SchoolAcademy> schoolAcademies = new HashSet<>();
        for(String academyNumber: sonProjectDTO.getAcademyNumber().split(",")) {
            schoolAcademies.add(shareService.findSchoolAcademyByPrimaryKey(academyNumber));
        }
        sonProject.setSchoolAcademies(schoolAcademies);
        // 父项目
        sonProject.setLabConstructionParentProject(labConstructionParentProjectDAO.findLabConstructionParentProjectById(sonProjectDTO.getParentProjectId()));
        // 是否提交
        sonProject.setSubmitted(true);

        sonProject = labConstructionSonProjectDAO.store(sonProject);

        if (sonProject.getId() != null && sonProject.getId() > 0) {
            return "success";
        }else {
            return "fail";
        }
    }

    /**
     * Description 提交孙项目
     * @param grandSonProjectDTO 保存的数据封装DTO
     * @return 成功-true，失败-false
     */
    @Override
    public String submitGrandSonProject(GrandSonProjectDTO grandSonProjectDTO) {
        // 判断预算是否超额
        LabConstructionSonProject sonProject = labConstructionSonProjectDAO.findLabConstructionSonProjectById(grandSonProjectDTO.getSonProjectId());
        BigDecimal budget = new BigDecimal(0);
        for (LabConstructionGrandsonProject gProject : sonProject.getLabConstructionGrandsonProjects()) {// 遍历所有孙项目，计算预算总额
            budget = budget.add(gProject.getBudget());
        }
        budget = budget.add(grandSonProjectDTO.getBudget());
        if (budget.compareTo(sonProject.getBudget()) == 1) {// 超预算
            return "over";
        }

        LabConstructionGrandsonProject grandsonProject;
        if(grandSonProjectDTO.getId() == null) {
            grandsonProject = new LabConstructionGrandsonProject();
        }else{
            grandsonProject = labConstructionGrandsonProjectDAO.findLabConstructionGrandsonProjectById(grandSonProjectDTO.getId());
        }

        grandsonProject.setAcceptanceaActualAmount(grandSonProjectDTO.getAcceptanceActualAmount());
        grandsonProject.setBudget(grandSonProjectDTO.getBudget());
        grandsonProject.setCreateTime(Calendar.getInstance());
        grandsonProject.setCreateUser(shareService.getUserDetail().getUsername());
        grandsonProject.setLabConstructionSonProject(labConstructionSonProjectDAO.findLabConstructionSonProjectById(grandSonProjectDTO.getSonProjectId()));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Calendar projectImplementTime = Calendar.getInstance();
            projectImplementTime.setTime(sdf.parse(grandSonProjectDTO.getProjectImplementTime()));
            grandsonProject.setProjectImplementTime(projectImplementTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        grandsonProject.setProjectName(grandSonProjectDTO.getProjectName());
        grandsonProject.setStage(grandSonProjectDTO.getProgressStage() == null ? 1 : grandSonProjectDTO.getProgressStage());
        grandsonProject.setStatus(grandSonProjectDTO.getProgressState() == null ? 0 : grandSonProjectDTO.getProgressState());
        grandsonProject.setTenderActualAmount(grandSonProjectDTO.getTenderActualAmount());
        grandsonProject.setSubmitted(true);

        grandsonProject = labConstructionGrandsonProjectDAO.store(grandsonProject);

        String s = saveCurState(grandsonProject.getId().toString(), 0, null, 0);
        if("success".equals(JSONObject.parseObject(s).getString("status"))) {
            // 上传信息流程线保存
            LabConstructionProjectAuditNew auditNext = new LabConstructionProjectAuditNew();
            auditNext.setStage(grandsonProject.getStage());
            auditNext.setResult(0);
            auditNext.setLabConstructionGrandsonProject(grandsonProject);
            String uploadName = shareService.findUserByUsername(grandsonProject.getCreateUser()).getCname();
            String content = "【" + uploadName + "】正在准备上传文件";
            auditNext.setRemark(content);
            labConstructionProjectAuditNewDAO.store(auditNext);
        }

        if (grandsonProject.getId() != null && grandsonProject.getId() > 0) {
            return "success";
        }else {
            return "fail";
        }
    }

    /**
     * Description 删除父项目
     * @param id 项目id
     * @return 成功-true，失败-false
     */
    @Override
    public boolean deleteParentProject(Integer id) {
        try {
            LabConstructionParentProject parentProject = labConstructionParentProjectDAO.findLabConstructionParentProjectById(id);
            labConstructionParentProjectDAO.remove(parentProject);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Description 删除子项目
     * @param id 项目id
     * @return 成功-true，失败-false
     */
    @Override
    public boolean deleteSonProject(Integer id) {
        try {
            LabConstructionSonProject sonProject = labConstructionSonProjectDAO.findLabConstructionSonProjectById(id);
            labConstructionParentProjectDAO.remove(sonProject);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Description 删除孙项目
     * @param id 项目id
     * @return 成功-true，失败-false
     */
    @Override
    public boolean deleteGrandSonProject(Integer id) {
        try {
            LabConstructionGrandsonProject grandsonProject = labConstructionGrandsonProjectDAO.findLabConstructionGrandsonProjectById(id);
            labConstructionParentProjectDAO.remove(grandsonProject);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Description 保存审核结果
     * @param id 孙项目id
     * @param auditResult 审核结果
     * @param remark 审核备注
     * @return 保存审核成功-true, 失败-false
     * @author 黄保钱 2019-2-27
     */
    @Override
    public boolean saveProjectAudit(Integer id, int auditResult, String remark){
        LabConstructionGrandsonProject grandsonProject = labConstructionGrandsonProjectDAO.findLabConstructionGrandsonProjectById(id);
        boolean allFinish = false;
        // 审核微服务推送
        try{
            String nextAuth = null;
            try{
                nextAuth = this.saveCurState(id.toString(), auditResult, remark, grandsonProject.getStage());
            }catch (NullPointerException | NoSuchElementException ne){
                ne.printStackTrace();
            }
            if(nextAuth == null) {
                nextAuth = this.getCurState(id.toString());
            }
            switch (nextAuth){
                case "pass":
                case "fail":
                    // 当前状态置为审核通过/拒绝
                    grandsonProject.setStatus(auditResult);
                    labConstructionGrandsonProjectDAO.store(grandsonProject);
                    allFinish = true;
                    break;
                case "repeat":
                    // 当前状态置为上传中
                    grandsonProject.setStatus(0);
                    // 未提交
                    grandsonProject.setSubmitted(false);
                    // 失效所有这个阶段的文件
                    for(LabConstructionProjectDocument document: grandsonProject.getLabConstructionProjectDocuments()){
                        if(document.getStage().equals(grandsonProject.getStage())){
                            document.setEnable(false);
                            labConstructionProjectDocumentDAO.store(document);
                        }
                    }
                    labConstructionGrandsonProjectDAO.store(grandsonProject);
                default:
                    // 当前状态置为下一级的上传中
                    grandsonProject.setStatus(0);
                    grandsonProject.setStage(grandsonProject.getStage() + 1);
                    // 提交状态置为未提交
                    grandsonProject.setSubmitted(false);
                    labConstructionGrandsonProjectDAO.store(grandsonProject);
            }
        }catch (NullPointerException ne){
            ne.printStackTrace();
        }
        // 审核信息流程线保存
        LabConstructionProjectAuditNew audit = new LabConstructionProjectAuditNew();
        audit.setStage(grandsonProject.getStage());
        audit.setResult(auditResult);
        audit.setLabConstructionGrandsonProject(grandsonProject);
        String NowDate = (new SimpleDateFormat("yyyy-MM-dd")).format(Calendar.getInstance().getTime());
        String content = "【" + shareService.getUserDetail().getCname() + "】文件审核" + (auditResult == 1 ? "通过，进入下一阶段" : ("不通过,原因是：" + remark));
        audit.setRemark(NowDate + content);
        labConstructionProjectAuditNewDAO.store(audit);
        // 上传信息流程线保存
        LabConstructionProjectAuditNew auditNext = new LabConstructionProjectAuditNew();
        auditNext.setStage(auditResult == 1 ? grandsonProject.getStage() + 1 : grandsonProject.getStage());
        auditNext.setResult(0);
        auditNext.setLabConstructionGrandsonProject(grandsonProject);
        String uploadName = shareService.findUserByUsername(grandsonProject.getCreateUser()).getCname();
        content = auditResult == 1 ?
                allFinish ? "完成所有阶段" : "【" + uploadName + "】正在准备上传文件" :
                "等待【" + uploadName + "】再次上传文件";
        auditNext.setRemark(content);
        labConstructionProjectAuditNewDAO.store(auditNext);
        return true;
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
     * @return 保存文件成功-true, 失败-false
     * @author 黄保钱 2019-2-27
     */
    @Override
    public boolean uploadDocument(HttpServletRequest request, Integer grandSonProjectId, String fileType) {
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            // 获取当前系统文件目录分隔符
            String sep = "/";
            /** 构建文件保存的目录* */
            String PathDir = sep + "upload" + sep + "labConstructionGrandsonProject" + sep + grandSonProjectId;
            /** 得到文件保存目录的真实路径* */
            String RealPathDir = request.getSession().getServletContext().getRealPath("/") + sep + PathDir;
            /** 根据真实路径创建目录* */
            File SaveFile = new File(RealPathDir);
            if (!SaveFile.exists()){
                if(!SaveFile.mkdirs()){
                    throw new RuntimeException("无法创建项目上传文件目录");
                }
            }
            Iterator fileNames = multipartRequest.getFileNames();
            MultipartFile multipartFile = multipartRequest.getFile("file");
            for(; fileNames.hasNext(); ){
                // 文件系统保存文件
                String filename = (String) fileNames.next();
                MultipartFile file = multipartRequest.getFile(filename);
                /** 拼成完整的文件保存路径加文件* */
                String fileName = RealPathDir + File.separator + multipartFile.getOriginalFilename();
                File thisFile = new File(RealPathDir);
                if (!thisFile.exists()) {
                    thisFile.mkdirs();
                } else {
                    try {
                        file.transferTo(new File(fileName));
                    } catch (IllegalStateException | IOException e) {
                        e.printStackTrace();
                        return false;
                    }
                }

                // 保存数据库
                CommonDocument commonDocument = new CommonDocument();
                commonDocument.setDocumentName(multipartFile.getOriginalFilename());
                commonDocument.setDocumentUrl(PathDir + sep + multipartFile.getOriginalFilename());
                commonDocument = commonDocumentDAO.store(commonDocument);

                LabConstructionProjectDocument document = new LabConstructionProjectDocument();
                LabConstructionGrandsonProject grandsonProject =
                        labConstructionGrandsonProjectDAO.findLabConstructionGrandsonProjectById(grandSonProjectId);
                document.setLabConstructionGrandsonProject(grandsonProject);
                document.setCommonDocument(commonDocument);
                Map<String, Integer> map = new HashMap<>();
                map.put("relatedDocument", 1);
                map.put("reportDocument", 2);
                map.put("purchaseDocument", 3);
                map.put("relatedContract", 4);
                map.put("acceptanceDocument", 5);
                document.setStage(map.get(fileType));
                document.setEnable(true);
                labConstructionProjectDocumentDAO.store(document);
                grandsonProject.setStatus(3);
                grandsonProject = labConstructionGrandsonProjectDAO.store(grandsonProject);
                // 上传信息流程线保存
                LabConstructionProjectAuditNew auditNext = new LabConstructionProjectAuditNew();
                auditNext.setStage(grandsonProject.getStage());
                auditNext.setResult(3);
                auditNext.setLabConstructionGrandsonProject(grandsonProject);
                String uploadName = shareService.getUserDetail().getCname();
                String NowDate = (new SimpleDateFormat("yyyy-MM-dd")).format(Calendar.getInstance().getTime());
                String content = NowDate + "【" + uploadName + "】文件上传成功，正在审核...";
                auditNext.setRemark(content);
                labConstructionProjectAuditNewDAO.store(auditNext);
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取当前审核状态
     * @param businessId 业务id
     * @return 当前审核状态
     * @author 黄保钱 2019-3-14
     */
    @Override
    public String getCurState(String businessId){
        Map<String, String> params = new HashMap<>();
        String businessType = "OperationItemNewAudit";
        params.put("businessAppUid", businessId);
        params.put("businessType", pConfig.PROJECT_NAME + businessType);
        String curStr = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", params);
        return JSONObject.parseObject(curStr).getJSONArray("data").getJSONObject(0).getString("result");
    }

    /**
     * 保存当前审核结果
     * @param businessId 业务id
     * @param audit 审核结果
     * @param remark 备注
     * @param repeat 当前阶段
     * @return 下一级审核状态
     * @author 黄保钱 2019-3-14
     */
    @Override
    public String saveCurState(String businessId, int audit, String remark, int repeat){
        Map<String, String> params = new HashMap<>();
        String businessType = "OperationItemNewAudit";
        params.put("businessType", pConfig.PROJECT_NAME + businessType);
        params.put("businessAppUid", businessId);
        params.put("businessUid", "-1");
        params.put("result", audit == 1 ? "pass" : "fail");
        params.put("info", remark);
        params.put("username", shareService.getUserDetail().getUsername());
        String curStr;
        if(repeat == 2) {
            curStr = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/saveBusinessLevelAudit", params);
        }else if(repeat == 0){
            curStr = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/saveInitBusinessAuditStatus", params);
        }else{
            curStr = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/saveBusinessLevelAuditForRepeatable", params);
        }
        if(repeat != 0) {
            return (String) JSONObject.parseObject(curStr).getJSONObject("data").entrySet().iterator().next().getValue();
        }
        return curStr;
    }

    /**
     * 保存孙项目文件
     * @param fileTrueName 文件名
     * @param id 孙项目id
     * @param type 类型
     * @author 黄保钱 2019-3-15
     */
    public void saveLabConstructionProjectDocument(String fileTrueName, Integer id,Integer type){
    }

    /**
     * Description 通过孙项目json分类的数量
     * @param status 状态值（0 -> 未审核，1 -> 审核中，2 -> 已经审核）
     * @return 总数
     */
    @Override
    public String getGrandSonProjectsCount(Integer status){

        String sqlCondition = "";
        switch (status) {
            case 0:
                sqlCondition += " and grandson.stage=1 and grandson.status=3 ";
                break;
            case 1:
                sqlCondition += " and ((grandson.stage!=1 and grandson.status!=3) or (grandson.stage!=5 and grandson.status!=1))";
                break;
            case 2:
                sqlCondition += " and grandson.stage=5 and grandson.status=1 ";
                break;
            default:
        }
        String sqlCount = "select count(grandson) from LabConstructionGrandsonProject grandson where 1=1 ";
        sqlCount += sqlCondition;
        BigInteger totalRecords = BigInteger.valueOf((Long) labConstructionGrandsonProjectDAO
                .executeQuerySingleResult(sqlCount));
        return totalRecords.compareTo(BigInteger.valueOf(100)) < 0 ? totalRecords.toString() : "99+";
    }
}
