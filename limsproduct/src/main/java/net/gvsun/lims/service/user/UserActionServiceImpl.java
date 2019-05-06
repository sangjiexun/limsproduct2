package net.gvsun.lims.service.user;

import api.net.gvsunlims.constant.ConstantInterface;
import edu.emory.mathcs.backport.java.util.Arrays;
import net.gvsun.lims.dto.common.BaseActionAuthDTO;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.web.common.PConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userActionService")
public class UserActionServiceImpl implements UserActionService {
    @Autowired
    private ShareService shareService;
    @Autowired
    PConfig pConfig;

    /*************************************************************************************
     * Description:用户权限管理-获取vo的用户行为权限
     *
     * @author： 魏誠
     * @date：2018-09-06
     *************************************************************************************/
    public BaseActionAuthDTO getBaseActionAuthDTO(String action, String author, String username) {
        BaseActionAuthDTO baseActionAuthDTO = new BaseActionAuthDTO();

        if (ConstantInterface.FUNCTION_MODEL_ACTION_TIMETABLE.equals(action) && ConstantInterface.AUTHORITY_TIMETABLE_BY_TEACHER.equals(pConfig.authTimetableType)) {
            //BY_TEACHER策略
            baseActionAuthDTO = this.getAuthTimetableTypeByTeacher(author, username);
        }else if(ConstantInterface.FUNCTION_MODEL_ACTION_TIMETABLE.equals(action) && ConstantInterface.AUTHORITY_TIMETABLE_BY_ACADEMY.equals(pConfig.authTimetableType)){
            //BY_ACADEMY策略
            baseActionAuthDTO = this.getAuthTimetableTypeByAcademy(author, username);
        }
        // 设备维修权限
        else if (ConstantInterface.FUNCTION_MODEL_ACTION_DEVICEREPAIR.equals(action)) {
            // 自己权限
            if ("noSubmit".equals(author)) {
                baseActionAuthDTO.setDeleteActionAuth(true);
                baseActionAuthDTO.setSearchActionAuth(true);
                baseActionAuthDTO.setUpdateActionAuth(true);
                baseActionAuthDTO.setPublicActionAuth(true);
            }
            // 审核权限
            else if (username.contains("audit")) {
                if(username.contains("sameAcademy")) {
                    String[] auths = author.split("_");
                    if(auths[0].equals(auths[2])) {
                        baseActionAuthDTO.setAuditActionAuth(true);
                    }
                }
            }
            // 确认权限
            else if (username.contains("confirmAction")) {
                List authNames = Arrays.asList(author.split(","));
                String[] selected_role = username.split("_");
                if (authNames.contains(selected_role[2]) && selected_role[3].equals("sameCenter"))
                    baseActionAuthDTO.setAddActionAuth(true);
            }
            // 审核拒绝权限
            else if ("fail".equals(author)) {
                baseActionAuthDTO.setSearchActionAuth(true);
            }
            // 查看权限
            else if (username.contains("viewList") && (
                    author.equals("ROLE_SUPERADMIN") ||
                            author.equals("ROLE_ASSETMANAGER") ||
                            author.equals("ROLE_ASSETMANAGEMENT") ||
                            author.equals("ROLE_DEPARTMENTHEADER") ||
                            (author.equals("ROLE_PREEXTEACHING") && !username.contains("noSameCenter")) ||
                            (author.equals("ROLE_EXCENTERDIRECTOR") && !username.contains("noSameCenter"))
            )) {
                baseActionAuthDTO.setSearchActionAuth(true);
            }
        }
        return baseActionAuthDTO;
    }

    /*************************************************************************************
     * Description:用户权限管理-排课模板-教师模板
     *
     * @author： 魏誠
     * @date：2018-09-06
     *************************************************************************************/
    public BaseActionAuthDTO getAuthTimetableTypeByTeacher(String author, String username) {
        BaseActionAuthDTO baseActionAuthDTO = new BaseActionAuthDTO();
        //学生权限
        if (ConstantInterface.AUTHORITY_ROLE_STUDENT.equals(author)) {
            baseActionAuthDTO.setAddActionAuth(false);
            baseActionAuthDTO.setDeleteActionAuth(false);
            baseActionAuthDTO.setSearchActionAuth(true);
            baseActionAuthDTO.setUpdateActionAuth(false);
            baseActionAuthDTO.setPublicActionAuth(false);
            baseActionAuthDTO.setSelectGroupActionAuth(true);
            baseActionAuthDTO.setCrossAcademyActionAuth(true);
            //教师权限
        } else if (ConstantInterface.AUTHORITY_ROLE_TEACHER.equals(author)) {
            baseActionAuthDTO.setSelectGroupActionAuth(false);
            //如果是教师，则当前登陆人匹配有权限
            if (shareService.getUser().getUsername().equals(username)) {
                baseActionAuthDTO.setDeleteActionAuth(true);
                baseActionAuthDTO.setAddActionAuth(true);
                baseActionAuthDTO.setUpdateActionAuth(true);
            } else {
                baseActionAuthDTO.setAddActionAuth(false);
                baseActionAuthDTO.setUpdateActionAuth(false);
                baseActionAuthDTO.setDeleteActionAuth(false);
            }
            baseActionAuthDTO.setSearchActionAuth(true);
            baseActionAuthDTO.setPublicActionAuth(false);
            baseActionAuthDTO.setCrossAcademyActionAuth(true);
            //中心主任
        } else if (ConstantInterface.AUTHORITY_ROLE_EXCENTERDIRECTOR.equals(author)) {
            baseActionAuthDTO.setAddActionAuth(true);
            baseActionAuthDTO.setSelectGroupActionAuth(false);
            baseActionAuthDTO.setDeleteActionAuth(true);
            baseActionAuthDTO.setSearchActionAuth(true);
            baseActionAuthDTO.setUpdateActionAuth(true);
            baseActionAuthDTO.setPublicActionAuth(true);
            baseActionAuthDTO.setCrossAcademyActionAuth(true);
            // 院系级系统管理员
        } else if (ConstantInterface.AUTHORITY_ROLE_ACADEMYLEVELM.equals(author)) {
            baseActionAuthDTO.setAddActionAuth(true);
            baseActionAuthDTO.setSelectGroupActionAuth(false);
            baseActionAuthDTO.setDeleteActionAuth(true);
            baseActionAuthDTO.setSearchActionAuth(true);
            baseActionAuthDTO.setUpdateActionAuth(true);
            baseActionAuthDTO.setPublicActionAuth(true);
            baseActionAuthDTO.setCrossAcademyActionAuth(true);
            // 超管
        } else if (ConstantInterface.AUTHORITY_ROLE_SUPERADMIN.equals(author)) {
            baseActionAuthDTO.setCrossAcademyActionAuth(true);
            // 系教学秘书
        }else if(ConstantInterface.AUTHORITY_ROLE_ASSISTANT.equals(author)) {
            baseActionAuthDTO.setAddActionAuth(true);
            baseActionAuthDTO.setSelectGroupActionAuth(false);
            baseActionAuthDTO.setDeleteActionAuth(true);
            baseActionAuthDTO.setSearchActionAuth(true);
            baseActionAuthDTO.setUpdateActionAuth(true);
            baseActionAuthDTO.setPublicActionAuth(false);
            baseActionAuthDTO.setCrossAcademyActionAuth(true);
        }
        // 实验室管理员
        else if(ConstantInterface.AUTHORITY_ROLE_LABMANAGER.equals(author)) {
            baseActionAuthDTO.setAddActionAuth(false);
            baseActionAuthDTO.setDeleteActionAuth(false);
            baseActionAuthDTO.setSearchActionAuth(true);
            baseActionAuthDTO.setUpdateActionAuth(false);
            baseActionAuthDTO.setPublicActionAuth(true);
            baseActionAuthDTO.setSelectGroupActionAuth(false);
            baseActionAuthDTO.setCrossAcademyActionAuth(true);
        }
        // 教研室主任
        else if(ConstantInterface.AUTHORITY_ROLE_DEPARTMENTHEADER.equals(author)) {
            baseActionAuthDTO.setAddActionAuth(false);
            baseActionAuthDTO.setDeleteActionAuth(false);
            baseActionAuthDTO.setSearchActionAuth(false);
            baseActionAuthDTO.setUpdateActionAuth(false);
            baseActionAuthDTO.setPublicActionAuth(false);
            baseActionAuthDTO.setSelectGroupActionAuth(false);
            baseActionAuthDTO.setCrossAcademyActionAuth(true);
        }else if("ROLE_CFO".equals(author)) {
            // 系主任
            baseActionAuthDTO.setAddActionAuth(false);
            baseActionAuthDTO.setDeleteActionAuth(false);
            baseActionAuthDTO.setSearchActionAuth(false);
            baseActionAuthDTO.setUpdateActionAuth(false);
            baseActionAuthDTO.setPublicActionAuth(true);
            baseActionAuthDTO.setSelectGroupActionAuth(false);
            baseActionAuthDTO.setCrossAcademyActionAuth(true);
        }
        return baseActionAuthDTO;
    }

    /*************************************************************************************
     * Description:用户权限管理-排课模板-教师模板
     *
     * @author： 魏誠
     * @date：2018-09-06
     *************************************************************************************/
    public BaseActionAuthDTO getAuthTimetableTypeByAcademy(String author, String username) {
        BaseActionAuthDTO baseActionAuthDTO = new BaseActionAuthDTO();
        //学生权限
        if (ConstantInterface.AUTHORITY_ROLE_STUDENT.equals(author)) {
            baseActionAuthDTO.setAddActionAuth(false);
            baseActionAuthDTO.setDeleteActionAuth(false);
            baseActionAuthDTO.setSearchActionAuth(true);
            baseActionAuthDTO.setUpdateActionAuth(false);
            baseActionAuthDTO.setPublicActionAuth(false);
            baseActionAuthDTO.setSelectGroupActionAuth(true);
            baseActionAuthDTO.setCrossAcademyActionAuth(false);
            //教师权限
        } else if (ConstantInterface.AUTHORITY_ROLE_TEACHER.equals(author)) {
            baseActionAuthDTO.setSelectGroupActionAuth(false);
            //如果是教师，则当前登陆人匹配有权限
            if (shareService.getUser().getUsername().equals(username)) {
                baseActionAuthDTO.setDeleteActionAuth(true);
                baseActionAuthDTO.setAddActionAuth(true);
                baseActionAuthDTO.setUpdateActionAuth(true);
            } else {
                baseActionAuthDTO.setAddActionAuth(false);
                baseActionAuthDTO.setUpdateActionAuth(false);
                baseActionAuthDTO.setDeleteActionAuth(false);
            }
            baseActionAuthDTO.setSearchActionAuth(true);
            baseActionAuthDTO.setPublicActionAuth(false);
            baseActionAuthDTO.setCrossAcademyActionAuth(false);
            //中心主任
        } else if (ConstantInterface.AUTHORITY_ROLE_ACADEMYLEVELM.equals(author)) {
            baseActionAuthDTO.setAddActionAuth(true);
            baseActionAuthDTO.setSelectGroupActionAuth(false);
            baseActionAuthDTO.setDeleteActionAuth(true);
            baseActionAuthDTO.setSearchActionAuth(true);
            baseActionAuthDTO.setUpdateActionAuth(true);
            baseActionAuthDTO.setPublicActionAuth(true);
            baseActionAuthDTO.setCrossAcademyActionAuth(false);
        } else if (ConstantInterface.AUTHORITY_ROLE_EXCENTERDIRECTOR.equals(author)) {
            baseActionAuthDTO.setAddActionAuth(true);
            baseActionAuthDTO.setSelectGroupActionAuth(false);
            baseActionAuthDTO.setDeleteActionAuth(true);
            baseActionAuthDTO.setSearchActionAuth(true);
            baseActionAuthDTO.setUpdateActionAuth(true);
            baseActionAuthDTO.setPublicActionAuth(true);
            baseActionAuthDTO.setCrossAcademyActionAuth(false);
        } else if (ConstantInterface.AUTHORITY_ROLE_SUPERADMIN.equals(author)) {
            baseActionAuthDTO.setCrossAcademyActionAuth(true);
            // 系教学秘书
        }else if(ConstantInterface.AUTHORITY_ROLE_ASSISTANT.equals(author)) {
            baseActionAuthDTO.setAddActionAuth(true);
            baseActionAuthDTO.setSelectGroupActionAuth(false);
            baseActionAuthDTO.setDeleteActionAuth(true);
            baseActionAuthDTO.setSearchActionAuth(true);
            baseActionAuthDTO.setUpdateActionAuth(true);
            baseActionAuthDTO.setPublicActionAuth(false);
            baseActionAuthDTO.setCrossAcademyActionAuth(true);
        }
        return baseActionAuthDTO;
    }

}