package net.zjcclims.service.device;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import edu.emory.mathcs.backport.java.util.LinkedList;
import excelTools.ExcelUtils;
import excelTools.JsGridReportBase;
import excelTools.TableData;
import net.gvsun.lims.dto.common.PConfigDTO;
import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.util.HttpClientUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("LabRoomDeviceService")
public class LabRoomDeviceServiceImpl implements LabRoomDeviceService {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    CDictionaryDAO cDictionaryDAO;
    @Autowired
    LabRoomDeviceDAO labRoomDeviceDAO;
    @Autowired
    ViewUseOfLcDAO viewUseOfLcDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    LabRoomAgentDAO labRoomAgentDAO;
    @Autowired
    CommonDocumentDAO documentDAO;
    @Autowired
    CommonVideoDAO videoDAO;
    @Autowired
    LabRoomAdminDAO labRoomAdminDAO;
    @Autowired
    TimetableLabRelatedDAO timetableLabRelatedDAO;
    @Autowired
    ShareService shareService;
    @Autowired
    SchoolDeviceDAO schoolDeviceDAO;
    @Autowired
    LabRoomDAO labRoomDAO;
    @Autowired
    LabRoomService labRoomService;
    @Autowired
    LabRoomDeviceReservationDAO labRoomDeviceReservationDAO;
    @Autowired
    LabRoomDeviceTrainingDAO labRoomDeviceTrainingDAO;
    @Autowired
    LabRoomDeviceTrainingPeopleDAO labRoomDeviceTrainingPeopleDAO;
    @Autowired
    LabRoomDevicePermitUsersDAO labRoomDevicePermitUsersDAO;
    @Autowired
    LabCenterDAO labCenterDAO;
    @Autowired
    LabRoomDeviceLendingDAO labRoomDeviceLendingDAO;
    @Autowired
    LabRoomDeviceLendingResultDAO labRoomDeviceLendingResultDAO;
    @Autowired
    LabRoomDeviceRepairDAO labRoomDeviceRepairDAO;
    @Autowired
    LabRoomDeviceReservationResultDAO labRoomDeviceReservationResultDAO;
    @Autowired
    TimetableLabRelatedDeviceDAO timetableLabRelatedDeviceDAO;
    @Autowired
    TimetableTeacherRelatedDAO timetableTeacherRelatedDAO;
    @Autowired
    TimetableItemRelatedDAO timetableItemRelatedDAO;
    @Autowired
    ResearchProjectDAO researchProjectDAO;
    @Autowired
    LabRoomLendingDAO labRoomLendingDAO;
    @Autowired
    SchoolDeviceUseDAO schoolDeviceUseDAO;
    @Autowired
    SchoolDeviceService schoolDeviceService;
    @Autowired
    private MessageDAO messageDAO;
    @Autowired
    LabRoomTrainingPeopleDAO labRoomTrainingPeopleDAO;
    @Autowired
    LabRoomPermitUserDAO labRoomPermitUserDAO;
    @Autowired
    private AuditRefuseBackupDAO auditRefuseBackupDAO;
    @Autowired
    private RefuseItemBackupDAO refuseItemBackupDAO;
    /****************************************************************************
     * 功能：保存实验分室设备
     * 作者：李小龙
     ****************************************************************************/
    @Override
    public LabRoomDevice save(LabRoomDevice device) {
        return labRoomDeviceDAO.store(device);
    }

    /****************************************************************************
     * 功能：根据实验分室id查询实验分室的设备
     * 作者：李小龙
     * @author 陈乐为修改 （OperationItemDevice外键关联school_device）2018-3-21
     ****************************************************************************/
    @Override
    public List<LabRoomDevice> findLabRoomDeviceByRoomId(Integer id) {
        String sql = "select d from LabRoomDevice d where 1=1 and d.labRoom.id=" + id;
        return labRoomDeviceDAO.executeQuery(sql, 0, -1);
    }

    /****************************************************************************
     * 功能：根据查询条件查询出所有的实验分室设备
     * 作者：李小龙
     ****************************************************************************/
    @Override
    public int findAllLabRoomDeviceNew(LabRoomDevice device, String acno, Integer isReservation) {
        String sql = "select count(d) from LabRoomDevice d where 1=1 ";
        //实验室
        if (device.getLabRoom() != null) {
            if (device.getLabRoom().getId() != null && !device.getLabRoom().getId().equals("")) {
                sql += " and d.labRoom.id=" + device.getLabRoom().getId();
            }
        }
        //设备
        if (device.getSchoolDevice() != null) {
            //设备编号
            if (device.getSchoolDevice().getDeviceNumber() != null && !device.getSchoolDevice().getDeviceNumber().equals("")) {
                sql += " and d.schoolDevice.deviceNumber like '%" + device.getSchoolDevice().getDeviceNumber() + "%'";
            }
            //设备名称
            if (device.getSchoolDevice().getDeviceName() != null && !device.getSchoolDevice().getDeviceName().equals("")) {
                sql += " and d.schoolDevice.deviceName like '%" + device.getSchoolDevice().getDeviceName() + "%'";
            }
            if (device.getUser() != null) {
                //设备管理员
                if (device.getUser().getUsername() != null && !device.getUser().getUsername().equals("")) {
                    sql += "and d.user.username  like '%" + device.getUser().getUsername() + "%'";
                }
            }
        }
        if (device != null && device.getCDictionaryByAllowAppointment() != null
                && device.getCDictionaryByAllowAppointment().getCNumber() != null
                && !device.getCDictionaryByAllowAppointment().getCNumber().equals("")) {
            if (device.getCDictionaryByAllowAppointment().getCNumber().equals("1")) {
                sql += " and d.CDictionaryByAllowAppointment.CNumber ='" + device.getCDictionaryByAllowAppointment().getCNumber() + "'";
            } else {
                sql += " and (d.CDictionaryByAllowAppointment.CNumber !='1' or d.CDictionaryByAllowAppointment is null)";
            }
        }
        if (isReservation != null && isReservation == 1) {
            sql += " and d.labRoom.labRoomActive=1";
        }
        //筛选可预约
        sql += " and d.CDictionaryByDeviceStatus.CNumber='1' and CDictionaryByAllowAppointment.CNumber='1'";

        return ((Long) labRoomDeviceDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
    }

    /****************************************************************************
     * 功能：根据查询条件查询出所有的实验分室设备并分页
     * 作者：李小龙
     ****************************************************************************/
    @Override
    public List<LabRoomDevice> findAllLabRoomDeviceNew(LabRoomDevice device, String acno,
                                                       Integer page, int pageSize, Integer isReservation) {
        String sql = "select d from LabRoomDevice d where 1=1 ";
        if (device.getLabRoom() != null) {
            if (device.getLabRoom().getId() != null && !device.getLabRoom().getId().equals("")) {
                sql += " and d.labRoom.id=" + device.getLabRoom().getId();
            }
        }
        //设备
        if (device.getSchoolDevice() != null) {
            //设备编号
            if (device.getSchoolDevice().getDeviceNumber() != null && !device.getSchoolDevice().getDeviceNumber().equals("")) {
                sql += " and d.schoolDevice.deviceNumber = '" + device.getSchoolDevice().getDeviceNumber() + "'";
            }
            //设备名称
            if (device.getSchoolDevice().getDeviceName() != null && !device.getSchoolDevice().getDeviceName().equals("")) {
                sql += " and d.schoolDevice.deviceName like '%" + device.getSchoolDevice().getDeviceName() + "%'";
            }
            if (device.getUser() != null) {
                //设备管理员
                if (device.getUser().getUsername() != null && !device.getUser().getUsername().equals("")) {
                    sql += " and d.user.username  like '%" + device.getUser().getUsername() + "%'";
                }
            }
        }
        if (device != null && device.getCDictionaryByAllowAppointment() != null
                && device.getCDictionaryByAllowAppointment().getCNumber() != null
                && !device.getCDictionaryByAllowAppointment().getCNumber().equals("")) {
            if (device.getCDictionaryByAllowAppointment().getCNumber().equals("1")) {
                sql += " and d.CDictionaryByAllowAppointment.CNumber ='" + device.getCDictionaryByAllowAppointment().getCNumber() + "'";
            } else {
                sql += " and (d.CDictionaryByAllowAppointment is null or (d.CDictionaryByAllowAppointment is not null and d.CDictionaryByAllowAppointment.CNumber = '2'))";
            }
        }

        if (isReservation != null && isReservation == 1) {
            sql += " and d.labRoom.labRoomActive = 1";

            // 开放学院筛选
            if(acno!=null&&!acno.equals("-1")) {
                sql += " and d in (select d from LabRoomDevice d join d.openSchoolAcademies openSAs where openSAs.academyNumber = '" +
                        acno +
                        "')";
            }
        }
        if(device.getLabRoom()!=null&&device.getLabRoom().getSchoolAcademy()!=null&&!acno.equals("-1")){
            sql +=" and d.labRoom.schoolAcademy.academyNumber='"+acno+"'";
        }
        sql += " order by d.schoolDevice.deviceName";
        System.out.println(sql);
        return labRoomDeviceDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
    }

    /****************************************************************************
     * 功能：删除实验室设备
     * 作者：李小龙
     ****************************************************************************/
    @Override
    public void deleteLabRoomDevice(LabRoomDevice d) {

        labRoomDeviceDAO.remove(d);
    }

    /****************************************************************************
     * 功能：根据实验分室设备主键查询对象
     * 作者：李小龙
     ****************************************************************************/
    @Override
    public LabRoomDevice findLabRoomDeviceByPrimaryKey(Integer id) {

        return labRoomDeviceDAO.findLabRoomDeviceByPrimaryKey(id);
    }

    /****************************************************************************
     * 功能：根据实验室id查询管理员
     * 作者：李小龙
     ****************************************************************************/
    @Override
    public List<LabRoomAdmin> findLabRoomAdminByRoomId(Integer id, Integer typeId) {

        String sql = "select a from LabRoomAdmin a where a.labRoom.id=" + id + " and a.typeId=" + typeId;

        return labRoomAdminDAO.executeQuery(sql, 0, -1);
    }

    /****************************************************************************
     * 功能：根据实验室查询实验室的排课
     * 作者：李小龙
     ****************************************************************************/
    @Override
    public List<TimetableLabRelated> findTimetableLabRelatedByRoomId(Integer id) {

        String sql = "select t from TimetableLabRelated t where t.labRoom.id=" + id;
        return timetableLabRelatedDAO.executeQuery(sql, 0, -1);
    }

    /****************************************************************************
     * 功能：查找实验室管理员
     * 作者：李鹏翔
     ****************************************************************************/
    public List<User> findAdminByLrid(Integer idKey) {
        List<User> us = new ArrayList<User>();
        String sql = "select l from LabRoomAdmin l where l.labRoom.id=" + idKey;
        sql += " and l.typeId = 1";
        List<LabRoomAdmin> lras = labRoomAdminDAO.executeQuery(sql, 0, -1);
        if (lras.size() > 0) {
            for (LabRoomAdmin lra : lras) {
                us.add(lra.getUser());
            }
        }
        return us;
    }


    /****************************************************************************
     * 功能：判断当前登录用户是否为实验室管理员
     * 作者：李小龙
     ****************************************************************************/
    @Override
    public Boolean getLabRoomAdmin(Integer roomId, String username) {
        String sql = "select a from LabRoomAdmin a where labRoom.id=" + roomId;
        List<LabRoomAdmin> list = labRoomAdminDAO.executeQuery(sql, 0, -1);
        Boolean flag = false;
        for (LabRoomAdmin a : list) {
            if (a.getUser().getUsername().equals(username)) {
                flag = true;
                break;
            }
        }
        return flag;
    }


    /****************************************************************************
     * 功能：上传设备图片
     * 作者：贺子龙
     * 时间：2015-09-28 14:50:23
     ****************************************************************************/
    @Override
    public void deviceImageUpload(HttpServletRequest request,
                                  HttpServletResponse response, Integer id, int type) {
        // TODO Auto-generated method stub
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        String sep = System.getProperty("file.separator");
        Map files = multipartRequest.getFileMap();
        Iterator fileNames = multipartRequest.getFileNames();
        boolean flag = false;
        String fileDir = request.getSession().getServletContext().getRealPath("/") + "upload" + sep + "device" + sep + id;
        //存放文件文件夹名称
        for (; fileNames.hasNext(); ) {
            String filename = (String) fileNames.next();
            CommonsMultipartFile file = (CommonsMultipartFile) files.get(filename);
            byte[] bytes = file.getBytes();
            if (bytes.length != 0) {
                // 说明申请有附件
                if (!flag) {
                    File dirPath = new File(fileDir);
                    if (!dirPath.exists()) {
                        flag = dirPath.mkdirs();
                    }
                }
                String fileTrueName = file.getOriginalFilename();
                //System.out.println("文件名称："+fileTrueName);
                File uploadedFile = new File(fileDir + sep + fileTrueName);
                //System.out.println("文件存放路径为："+fileDir + sep + fileTrueName);
                try {
                    FileCopyUtils.copy(bytes, uploadedFile);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                saveDeviceDocument(fileTrueName, id, type);
            }
        }
    }

    /****************************************************************************
     * 功能：保存设备的文档
     * 作者：贺子龙
     * 时间：2015-09-28 14:50:33
     ****************************************************************************/
    public void saveDeviceDocument(String fileTrueName, Integer id, int type) {
        // TODO Auto-generated method stub
        //id对应的设备
        LabRoomDevice device = labRoomDeviceDAO.findLabRoomDeviceByPrimaryKey(id);
        CommonDocument doc = new CommonDocument();
        doc.setDocumentName(fileTrueName);
        String imageUrl = "upload/device/" + id + "/" + fileTrueName;
        doc.setDocumentUrl(imageUrl);
        doc.setLabRoomDevice(device);
        doc.setType(type);//图片和文档

        documentDAO.store(doc);
    }

    /****************************************************************************
     * 功能：上传设备视频
     * 作者：贺子龙
     * 时间：2015-09-28 14:50:33
     ****************************************************************************/
    @Override
    public void deviceVideoUpload(HttpServletRequest request,
                                  HttpServletResponse response, Integer id) {
        // TODO Auto-generated method stub
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        String sep = System.getProperty("file.separator");
        Map files = multipartRequest.getFileMap();
        Iterator fileNames = multipartRequest.getFileNames();
        boolean flag = false;
        String fileDir = request.getSession().getServletContext().getRealPath("/") + "upload" + sep + "device" + sep + id;
        //存放文件文件夹名称
        for (; fileNames.hasNext(); ) {
            String filename = (String) fileNames.next();
            CommonsMultipartFile file = (CommonsMultipartFile) files.get(filename);
            byte[] bytes = file.getBytes();
            if (bytes.length != 0) {
                // 说明申请有附件
                if (!flag) {
                    File dirPath = new File(fileDir);
                    if (!dirPath.exists()) {
                        flag = dirPath.mkdirs();
                    }
                }
                String fileTrueName = file.getOriginalFilename();
                //System.out.println("文件名称："+fileTrueName);
                File uploadedFile = new File(fileDir + sep + fileTrueName);
                //System.out.println("文件存放路径为："+fileDir + sep + fileTrueName);
                try {
                    FileCopyUtils.copy(bytes, uploadedFile);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                saveDeviceVideo(fileTrueName, id);
            }
        }
    }

    /****************************************************************************
     * 功能：保存设备的视频
     * 作者：贺子龙
     * 时间：2015-09-28 14:50:33
     ****************************************************************************/
    public void saveDeviceVideo(String fileTrueName, Integer id) {
        // TODO Auto-generated method stub
        //id对应的设备
        LabRoomDevice device = labRoomDeviceDAO.findLabRoomDeviceByPrimaryKey(id);
        CommonVideo video = new CommonVideo();
        video.setVideoName(fileTrueName);
        String imageUrl = "upload/device/" + id + "/" + fileTrueName;
        video.setVideoUrl(imageUrl);
        video.setLabRoomDevice(device);

        videoDAO.store(video);
    }

    /****************************************************************************
     * 功能：根据设备id查询设备的预约记录
     * 作者：李小龙
     ****************************************************************************/
    @Override
    public List<LabRoomDeviceReservation> findReservationListByDeviceId(
            Integer equinemtid) {
        // TODO Auto-generated method stub
        String sql = "select r from LabRoomDeviceReservation r where  r.labRoomDevice.id=" + equinemtid;
        //显示的数据不包含审核拒绝
        sql += " and r.CDictionaryByCAuditResult <> 616";
        return labRoomDeviceReservationDAO.executeQuery(sql, 0, -1);
    }


    /****************************************************************************
     * 功能：根据设备预约信息和审核状态查询设备预约并分页
     * 作者：李小龙
     ****************************************************************************/
    @Override
    public List<LabRoomDeviceReservation> findAllLabRoomDeviceReservation(
            LabRoomDeviceReservation reservation, int cId,String acno, Integer page,
            int pageSize) {
        //获取当前登录人
        User user = shareService.getUser();
        //判断当前登陆人是否为实验室中心主任或者超级管理员
        String judge = ",";
        for (Authority authority : user.getAuthorities()) {
            judge = judge + "," + authority.getId() + ",";
        }
        boolean isEXCENTERDIRECTOR;
        if (judge.indexOf(",11,") > -1 || judge.indexOf(",4,") > -1) {
            isEXCENTERDIRECTOR = true;
        } else isEXCENTERDIRECTOR = false;

        String sql = "select distinct l from LabRoomDeviceReservation l left join l.labRoomDevice.labRoom.labRoomAdmins la where 1=1";
        if (cId != 0) {
            sql += " and l.CDictionaryByCAuditResult.CCategory='c_audit_result' and l.CDictionaryByCAuditResult.CNumber ='" + cId + "'";

        }
        if (reservation.getLabRoomDevice() != null && reservation.getLabRoomDevice().getSchoolDevice() != null) {
            if (reservation.getLabRoomDevice().getSchoolDevice().getDeviceName() != null && !reservation.getLabRoomDevice().getSchoolDevice().getDeviceName().equals("")) {
                sql += " and l.labRoomDevice.schoolDevice.deviceName like '%" + reservation.getLabRoomDevice().getSchoolDevice().getDeviceName() + "%'";
            }
            if (reservation.getLabRoomDevice() != null && reservation.getLabRoomDevice().getLabRoom() != null && reservation.getLabRoomDevice().getLabRoom().getId() != null && !reservation.getLabRoomDevice().getLabRoom().getId().equals("")) {
                sql += " and l.labRoomDevice.labRoom.id=" + reservation.getLabRoomDevice().getLabRoom().getId();
            }
        }
        if (reservation.getSchoolTerm() != null) {
            if (reservation.getSchoolTerm().getId() != null) {
                sql += " and l.schoolTerm.id=" + reservation.getSchoolTerm().getId();
            }
        }
        if (reservation.getUserByReserveUser() != null && reservation.getUserByReserveUser().getUsername() != null && !reservation.getUserByReserveUser().getUsername().equals("")) {
            sql += " and l.userByReserveUser.username ='" + reservation.getUserByReserveUser().getUsername() + "'";
        }
        //身份限制
        if (isEXCENTERDIRECTOR) {//是实验室中心主任或者超级管理员
            //do nothing
        } else {//不是上述两种身份
            sql += " and (l.userByReserveUser.username='" + shareService.getUser().getUsername() + "'";//申请者
            sql += " or l.userByTeacher.username='" + shareService.getUser().getUsername() + "'";//导师
            sql += " or l.labRoomDevice.user.username='" + shareService.getUser().getUsername() + "'";//设备管理员
            sql += " or (la.user.username='" + shareService.getUser().getUsername() + "' and la.typeId=1))";//实验室管理员
        }

        sql += " group by l.innerSame";
        sql += " order by l.time desc";
        return labRoomDeviceReservationDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
    }

    /****************************************************************************
     * 功能：根据设备预约信息和审核状态查询设备预约并分页
     * 作者：李小龙
     ****************************************************************************/
    @Override
    public List<LabRoomDeviceReservation> findAllLabRoomDeviceReservation(
            LabRoomDeviceReservation reservation, Integer cId, String acno, Integer page,
            int pageSize, int tag) {
        // TODO Auto-generated method stub
        String sql = "select l from LabRoomDeviceReservation l where 1=1";
        if (cId != null) {
            sql += " and l.CDictionaryByCAuditResult.CCategory='c_audit_result' and l.CDictionaryByCAuditResult.CNumber ='" + cId + "'";
        }

        if (reservation.getLabRoomDevice() != null && reservation.getLabRoomDevice().getSchoolDevice() != null) {
            if (reservation.getLabRoomDevice().getSchoolDevice().getDeviceName() != null && !reservation.getLabRoomDevice().getSchoolDevice().getDeviceName().equals("")) {
                sql += " and l.labRoomDevice.schoolDevice.deviceName like '%" + reservation.getLabRoomDevice().getSchoolDevice().getDeviceName() + "%'";
            }
            if (reservation.getLabRoomDevice() != null && reservation.getLabRoomDevice().getLabRoom() != null && reservation.getLabRoomDevice().getLabRoom().getId() != null && !reservation.getLabRoomDevice().getLabRoom().getId().equals("")) {
                sql += " and l.labRoomDevice.labRoom.id=" + reservation.getLabRoomDevice().getLabRoom().getId();
            }
        }
        if (reservation.getSchoolTerm() != null) {
            if (reservation.getSchoolTerm().getId() != null) {
                sql += " and l.schoolTerm.id=" + reservation.getSchoolTerm().getId();
            }
        }
        sql += " and (l.tag=1 or l.tag=2 or l.tag=3)";
        sql += " order by l.time desc";
        return labRoomDeviceReservationDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
    }

    /****************************************************************************
     * 功能：找出所有设备预约的实验室
     * 作者：贺子龙
     * 时间：2015-10-10
     ****************************************************************************/
    @Override
    public Map<Integer, String> findAllLabrooms(String acno) {

        Map<Integer, String> map = new HashMap<Integer, String>();
        String s = "select u from LabRoomDeviceReservation u where 1=1 ";
        List<LabRoomDeviceReservation> list = labRoomDeviceReservationDAO.executeQuery(s);
        if (list.size() > 0) {
            for (LabRoomDeviceReservation re : list) {
                map.put(re.getLabRoomDevice().getLabRoom().getId(), re.getLabRoomDevice().getLabRoom().getLabRoomName());
            }
        }
        return map;
    }

    /****************************************************************************
     * 功能：根据设备预约信息和审核状态查询设备预约
     * 作者：李小龙
     ****************************************************************************/
    @Override
    public List<LabRoomDeviceReservation> findAllLabRoomDeviceReservation(
            LabRoomDeviceReservation reservation, Integer cId, Integer centerId) {
        // TODO Auto-generated method stub
        //String sql="select l from LabRoomDeviceReservation l where l.CAuditResult.id="+cId;
        String sql = "select l from LabRoomDeviceReservation l where 1=1";
        if (cId != null) {
            //sql +=" and l.CDictionaryByCAuditResult.id="+cId;
            sql += " and l.CDictionaryByCAuditResult.CCategory='c_audit_result' and l.CDictionaryByCAuditResult.CNumber ='" + cId + "'";
        }
        if (reservation.getLabRoomDevice() != null && reservation.getLabRoomDevice().getSchoolDevice() != null) {
            if (reservation.getLabRoomDevice().getSchoolDevice().getDeviceName() != null && !reservation.getLabRoomDevice().getSchoolDevice().getDeviceName().equals("")) {
                sql += " and l.labRoomDevice.schoolDevice.deviceName like '%" + reservation.getLabRoomDevice().getSchoolDevice().getDeviceName() + "%'";
            }
            if (reservation.getLabRoomDevice() != null && reservation.getLabRoomDevice().getLabRoom() != null && reservation.getLabRoomDevice().getLabRoom().getId() != null && !reservation.getLabRoomDevice().getLabRoom().getId().equals("")) {
                sql += " and l.labRoomDevice.labRoom.id=" + reservation.getLabRoomDevice().getLabRoom().getId();
            }
        }
        if (reservation.getSchoolTerm() != null) {
            if (reservation.getSchoolTerm().getId() != null) {
                sql += " and l.schoolTerm.id=" + reservation.getSchoolTerm().getId();
            }
        }
		/*if(centerId!=null){
			sql+=" and l.labRoomDevice.labRoom.labCenter.id="+centerId;
		}*/
        sql += "order by l.time desc";
        return labRoomDeviceReservationDAO.executeQuery(sql, 0, -1);
    }

    /****************************************************************************
     * 功能：找出所有设备预约的申请人
     * 作者：贺子龙
     * 时间：2015-10-10
     ****************************************************************************/
    @Override
    public Map<String, String> findAllreserveUsers(Integer centerId) {

        Map<String, String> map = new HashMap<String, String>();
        String s = "select u from LabRoomDeviceReservation u where u.userByReserveUser.username is not null ";
        s += "group by u.userByReserveUser.username";
        List<LabRoomDeviceReservation> list = labRoomDeviceReservationDAO.executeQuery(s);
        if (list.size() > 0) {
            for (LabRoomDeviceReservation re : list) {
                map.put(re.getUserByReserveUser().getUsername(), re.getUserByReserveUser().getCname());
            }
        }
        return map;
    }

    /****************************************************************************
     * 功能：找出所有设备预约的指导老师
     * 作者：贺子龙
     * 时间：2015-10-10
     ****************************************************************************/
    @Override
    public Map<String, String> findAllTeachers(Integer centerId) {

        Map<String, String> map = new HashMap<String, String>();
        String s = "select u from LabRoomDeviceReservation u where u.userByTeacher.username is not null";
        s += "group by u.userByTeacher.username";
        List<LabRoomDeviceReservation> list = labRoomDeviceReservationDAO.executeQuery(s);
        if (list.size() > 0) {
            for (LabRoomDeviceReservation re : list) {
                map.put(re.getUserByTeacher().getUsername(), re.getUserByTeacher().getCname());
            }
        }
        return map;
    }


    /****************************************************************************
     * 功能：找出所有设备预约的设备管理员
     * 作者：贺子龙
     * 时间：2015-10-10
     ****************************************************************************/
    @Override
    public Map<String, String> findAllManageUsers(Integer centerId) {

        Map<String, String> map = new HashMap<String, String>();
        String s = "select u from LabRoomDeviceReservation u where u.labRoomDevice.user.username is not null";
        s += "group by u.labRoomDevice.user.username";
        List<LabRoomDeviceReservation> list = labRoomDeviceReservationDAO.executeQuery(s);
        if (list.size() > 0) {
            for (LabRoomDeviceReservation re : list) {
                map.put(re.getLabRoomDevice().getUser().getUsername(), re.getLabRoomDevice().getUser().getCname());
            }
        }
        return map;
    }

    /****************************************************************************
     * 功能：根据查询条件查询出所有的实验分室设备
     * 作者：贺子龙
     * 时间：2015-09-22 10:24:59
     ****************************************************************************/
    @Override
    public List<LabRoomDevice> findAllLabRoomDeviceNew(LabRoomDevice device, String acno) {
        String sql = "select d from LabRoomDevice d where 1=1";
        if (device.getLabRoom() != null) {
            if (device.getLabRoom().getId() != null && !device.getLabRoom().getId().equals("")) {
                sql += " and d.labRoom.id=" + device.getLabRoom().getId();
            }
           if(acno!=null && !"".equals(acno)){
                sql +=" and d.labRoom.labCenter.schoolAcademy.academyNumber='"+ acno +"'";
           }
        }
        //设备
        if (device.getSchoolDevice() != null) {
            //设备编号
            if (device.getSchoolDevice().getDeviceNumber() != null && !device.getSchoolDevice().getDeviceNumber().equals("")) {
                sql += "and d.schoolDevice.deviceNumber like '%" + device.getSchoolDevice().getDeviceNumber() + "%'";
            }
            //设备名称
            if (device.getSchoolDevice().getDeviceName() != null && !device.getSchoolDevice().getDeviceName().equals("")) {
                sql += "and d.schoolDevice.deviceName like '%" + device.getSchoolDevice().getDeviceName() + "%'";
            }
            if (device.getSchoolDevice().getUserByKeepUser() != null) {
                //设备管理员
                if (device.getSchoolDevice().getUserByKeepUser().getUsername() != null && !device.getSchoolDevice().getUserByKeepUser().getUsername().equals("")) {
                    sql += "and d.schoolDevice.userByKeepUser.username  like '%" + device.getSchoolDevice().getUserByKeepUser().getUsername() + "%'";
                }
            }
        }
		/*if(cid!=null){
			sql+=" and d.labRoom.labCenter.id="+cid;
		}*/
        return labRoomDeviceDAO.executeQuery(sql, 0, -1);
    }


    @Override
    public List<LabRoomDevice> findAllLabRoomDeviceNumbers(LabRoomDevice device, String acno) {
        String sql = "select d from LabRoomDevice d where 1=1";
		/*if(cid!=null){
			sql+=" and d.labRoom.labCenter.id="+cid;
		}*/
        if (device.getLabRoom() != null) {
            if (device.getLabRoom().getId() != null && !device.getLabRoom().getId().equals("")) {
                sql += " and d.labRoom.id=" + device.getLabRoom().getId();
            }
        }
        sql += " group by d.schoolDevice.deviceNumber ";
        return labRoomDeviceDAO.executeQuery(sql, 0, -1);
    }

    /****************************************************************************
     * 功能：根据设备id查询培训
     * 作者：李小龙
     ****************************************************************************/
    @Override
    public List<LabRoomDeviceTraining> findLabRoomDeviceTrainingByDeviceId(LabRoomDeviceTraining train,
                                                                           Integer deviceId) {
        // TODO Auto-generated method stub
        String sql = "select l from LabRoomDeviceTraining l where l.labRoomDevice.id=" + deviceId;
        if (train != null) {
            if (train.getSchoolTerm() != null) {
                sql += " and l.schoolTerm.id=" + train.getSchoolTerm().getId();
            }
        }
        sql += " order by l.time desc";
        return labRoomDeviceTrainingDAO.executeQuery(sql, 0, -1);
    }

    /****************************************************************************
     * 功能：根据培训id查询培训通过的人
     * 作者：李小龙
     ****************************************************************************/
    @Override
    public List<LabRoomDeviceTrainingPeople> findPassLabRoomDeviceTrainingPeopleByTrainId(
            Integer id) {
        // TODO Auto-generated method stub
        //String sql="select p from LabRoomDeviceTrainingPeople p where p.CDictionary.id=619 and p.labRoomDeviceTraining.id="+id;
        String sql = "select p from LabRoomDeviceTrainingPeople p where 1=1 and p.CDictionary.CCategory='c_training_result' and p.CDictionary.CNumber ='1' and p.labRoomDeviceTraining.id=" + id;
        return labRoomDeviceTrainingPeopleDAO.executeQuery(sql, 0, -1);
    }

    /****************************************************************************
     * 功能：根据培训查询培训名单
     * 作者：李小龙
     ****************************************************************************/
    @Override
    public List<LabRoomDeviceTrainingPeople> findTrainingPeoplesByTrainingId(
            int id) {
        // TODO Auto-generated method stub
        String sql = "select p from LabRoomDeviceTrainingPeople p where p.labRoomDeviceTraining.id=" + id;
        return labRoomDeviceTrainingPeopleDAO.executeQuery(sql, 0, -1);
    }

    /****************************************************************************
     * 功能：保存设备培训
     * 作者：李小龙
     ****************************************************************************/
    @Override
    public LabRoomDeviceTraining saveLabRoomDeviceTraining(
            LabRoomDeviceTraining train) {
        // TODO Auto-generated method stub
        return labRoomDeviceTrainingDAO.store(train);
    }

    /****************************************************************************
     * 功能：根据培训查询培训名单--已通过的学生
     * 作者：贺子龙
     ****************************************************************************/
    @Override
    public List<LabRoomDeviceTrainingPeople> findTrainingPassPeoplesByTrainingId(
            int id) {
        // TODO Auto-generated method stub
        String sql = "select p from LabRoomDeviceTrainingPeople p where p.labRoomDeviceTraining.id=" + id;
        //sql+=" and p.CDictionary.id=619";
        sql += " and p.CDictionary.CCategory='c_training_result' and p.CDictionary.CNumber ='1'";
        return labRoomDeviceTrainingPeopleDAO.executeQuery(sql, 0, -1);
    }

    /****************************************************************************
     * 功能：当前用户取消已经预约的培训
     * 作者：贺子龙
     ****************************************************************************/
    public void cancleTraining(int trainingId) {
        LabRoomDeviceTraining train = labRoomDeviceTrainingDAO.findLabRoomDeviceTrainingByPrimaryKey(trainingId);
        User user = shareService.getUser();
        // 找到对应id
        int labRoomDeviceTrainingPeopleId = 0;
        Set<LabRoomDeviceTrainingPeople> students = train.getLabRoomDeviceTrainingPeoples();
        for (LabRoomDeviceTrainingPeople labRoomDeviceTrainingPeople : students) {
            if (labRoomDeviceTrainingPeople.getUser().getUsername() == user.getUsername()) {
                students.remove(labRoomDeviceTrainingPeople);
                labRoomDeviceTrainingPeopleId = labRoomDeviceTrainingPeople.getId();
            }
        }
        train.setLabRoomDeviceTrainingPeoples(students);
        labRoomDeviceTrainingDAO.store(train);
        LabRoomDeviceTrainingPeople student = labRoomDeviceTrainingPeopleDAO.findLabRoomDeviceTrainingPeopleByPrimaryKey(labRoomDeviceTrainingPeopleId);
        if (student != null) {
            student.setLabRoomDeviceTraining(null);
            labRoomDeviceTrainingPeopleDAO.remove(student);
            labRoomDeviceTrainingPeopleDAO.flush();
        }
    }

    /****************************************************************************
     * 功能：将设备预约数据放到view_timetale
     * 作者：贺子龙
     ****************************************************************************/
    @Transactional
    public void callLabDeviceReservationFunction(){
        Query query1 = entityManager.createNativeQuery("{call put_dev_res_into_view()}");
        query1.executeUpdate();
    }
    /****************************************************************************
     * 功能：当天预约审核通过后门禁注册--设备
     * 作者：贺子龙
     ****************************************************************************/
    @Transactional
    public String refreshDeviceReservation(LabRoomDeviceReservation reservation) throws IOException{

        // 判断是否当天
        if (!shareService.isSameDate(reservation.getBegintime().getTime(),
                Calendar.getInstance().getTime())) {
            System.out.println("not today");
            return "not today";// 如果不是当天预约，直接返回。
        }

        int roomId = reservation.getLabRoomDevice().getLabRoom().getId();
        // 找到对应的电源控制器
        List<LabRoomAgent> doors = labRoomService.findLabRoomAgentAccessByRoomId(roomId);
        LabRoomAgent door = new LabRoomAgent();
        if (doors!=null && doors.size()>0) {
            door = doors.get(0);

        }else {
            return "no door";
        }

        String serverUrl="";//服务器地址
        if(door!=null && door.getCommonServer()!=null){
            serverUrl="http://"+door.getCommonServer().getServerIp()+":8081/services/ofthings/acldoor.asp?cmd=regcard&roomnumber="+roomId;
        }
        System.out.println(door.getCommonServer().getServerIp()+" is the ip of server");
        System.out.println("serverUrl:"+serverUrl);
        URL url=new URL(serverUrl);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;
        urlConn.setDoOutput(true);
        // 设置是否从httpUrlConnection读入，默认情况下是true;
        urlConn.setDoInput(true);
        // Post 请求不能使用缓存
        urlConn.setUseCaches(false);
        // 设定传送的内容类型是可序列化的java对象
        // (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
        urlConn.setRequestProperty("Content-type","application/x-java-serialized-object");
        // 设定请求的方法为"POST"，默认是GET
        urlConn.setRequestMethod("POST");
        // 连接，上面对urlConn的所有配置必须要在connect之前完成
        try{
            urlConn.connect();
        }catch(IOException e){
            return "error";
        }

        // 此处getOutputStream会隐含的进行connect (即：如同调用上面的connect()方法，
        // 所以在开发中不调用上述的connect()也可以)。
        OutputStream outStrm = urlConn.getOutputStream();

        // 调用HttpURLConnection连接对象的getInputStream()函数,
        // 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。
        InputStream inStrm = urlConn.getInputStream(); // <===注意，实际发送请求的代码段就在这里

        InputStreamReader  inStream = new InputStreamReader(inStrm,"UTF-8");
        String inputline="";
        String info="";//返回的参数
        BufferedReader buffer=new BufferedReader(inStream);

        while((inputline=buffer.readLine())!=null){
            info+=inputline;
        }
        //System.out.println("返回的参数为："+info);
        //设置超时时间
        HttpURLConnection urlCon = (HttpURLConnection)url.openConnection();
        urlCon.setConnectTimeout(30000);
        urlCon.setReadTimeout(30000);
        if(info.contains("true")){
            return "sucess";
        }else{
            return "error";
        }
    }

    /****************************************************************************
     * 功能：当天预约审核通过后电源控制器注册
     * 作者：贺子龙
     * @throws IOException
     ****************************************************************************/
    @Transactional
    public String refreshLabRoomDeviceReservation(LabRoomDeviceReservation reservation) throws IOException{

        // 判断是否当天
        if (!shareService.isSameDate(reservation.getBegintime().getTime(),
                Calendar.getInstance().getTime())) {
            System.out.println("not today");
            return "not today";// 如果不是当天预约，直接返回。
        }
        // 设备
        LabRoomDevice device = reservation.getLabRoomDevice();
        // 设备编号
        String deviceNumber = device.getSchoolDevice().getDeviceNumber();
        int roomId = device.getLabRoom().getId();
        // 找到对应的电源控制器
        LabRoomAgent guard = labRoomService.findGuardByRemark(deviceNumber, roomId);

        String serverUrl="";//服务器地址
        if(guard!=null && guard.getCommonServer()!=null){
            serverUrl="http://"+guard.getCommonServer().getServerIp()+":8081/services/ofthings/guard.asp?cmd=regcard&roomnumber="+roomId;
        }
        URL url=new URL(serverUrl);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;
        urlConn.setDoOutput(true);
        // 设置是否从httpUrlConnection读入，默认情况下是true;
        urlConn.setDoInput(true);
        // Post 请求不能使用缓存
        urlConn.setUseCaches(false);
        // 设定传送的内容类型是可序列化的java对象
        // (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
        urlConn.setRequestProperty("Content-type","application/x-java-serialized-object");
        // 设定请求的方法为"POST"，默认是GET
        urlConn.setRequestMethod("POST");
        // 连接，上面对urlConn的所有配置必须要在connect之前完成
        try{
            urlConn.connect();
        }catch(IOException e){
            return "error";
        }

        // 此处getOutputStream会隐含的进行connect (即：如同调用上面的connect()方法，
        // 所以在开发中不调用上述的connect()也可以)。
        OutputStream outStrm = urlConn.getOutputStream();

        // 调用HttpURLConnection连接对象的getInputStream()函数,
        // 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。
        InputStream inStrm = urlConn.getInputStream(); // <===注意，实际发送请求的代码段就在这里

        InputStreamReader  inStream = new InputStreamReader(inStrm,"UTF-8");
        String inputline="";
        String info="";//返回的参数
        BufferedReader buffer=new BufferedReader(inStream);

        while((inputline=buffer.readLine())!=null){
            info+=inputline;
        }
        //System.out.println("返回的参数为："+info);
        //设置超时时间
        HttpURLConnection urlCon = (HttpURLConnection)url.openConnection();
        urlCon.setConnectTimeout(30000);
        urlCon.setReadTimeout(30000);
        if(info.contains("true")){
            return "sucess";
        }else{
            return "error";
        }


    }

    /****************************************************************************
     *Description：判断设备是否进行了预约的初始化设置
     *
     *@author：孙虎
     *@date:2017-10-23
     ****************************************************************************/
    public Boolean isSettingForLabRoomDevice(Integer labRoomDeviceId) {
        LabRoomDevice device = labRoomDeviceDAO.findLabRoomDeviceByPrimaryKey(labRoomDeviceId);
        if (device.getCDictionaryByIsAudit() == null) {
            return false;
        } else {
            return true;
        }
    }

    /****************************************************************************
     * 功能：判断用户是否通过安全准入
     * 作者：李小龙
     ****************************************************************************/
    @Override
    public String securityAccess(String username, Integer id) {
        // TODO Auto-generated method stub
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();

        String str = "success";
        User user = shareService.getUserDetail();
        //id对应的设备
        LabRoomDevice device = labRoomDeviceDAO.findLabRoomDeviceByPrimaryKey(id);
        //判断是否初始化设置
        if (this.isSettingForLabRoomDevice(id) == false) {
            return "noSetting";
        }
        //由于更改系主任的筛选方式，需要判断是否系主任，以防产生脏数据
        boolean flag = false;
        Map<String, String> params = new HashMap<>();
        params.put("businessUid", device.getId().toString());
        params.put("businessType", pConfigDTO.PROJECT_NAME + "DeviceReservation" + device.getLabRoom().getLabCenter().getSchoolAcademy().getAcademyNumber());
        String s = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/getBusinessAuditConfigs", params);
        JSONObject jsonObject = JSONObject.parseObject(s);
        if("success".equals(jsonObject.getString("status"))) {
            Map auditConfigs = JSON.parseObject(jsonObject.getString("data"), Map.class);
            if (auditConfigs != null && auditConfigs.size() != 0) {
                for (int i = 0; i < auditConfigs.size(); i++) {
                    String[] text = ((String) auditConfigs.get(i + 1)).split(":");
                    if(text[0].equals("CFO")) {
                        flag = text[1].equals("on");
                    }
                }
            }
        }
        if (flag) {
            SchoolAcademy schoolAcademy = user.getSchoolAcademy();
            if (schoolAcademy != null) {
                List<User> dUsers = shareService.findDeansByAcademyNumber(schoolAcademy);
                if (dUsers == null) {
                    return "noDean";
                } else if (dUsers.size() == 0) {
                    return "noDean";
                }
            } else {
                return "noDean";
            }
        }
        //是否需要安全准入
        CDictionary a = device.getCDictionaryByAllowSecurityAccess();

        if (a != null && a.getId() == 621) {//需要安全准入
            // 判断当前登陆人是否在已经阅读的列表里
            String attentions = "";
            boolean isIn_lab = labRoomService.isInTheReaderList(device.getLabRoom().getId());
            boolean isIn_device = labRoomService.isInTheReaderListDevice(device.getSchoolDevice().getDeviceNumber());
            // 先判断设备有没有写注意事项
            if (!EmptyUtil.isStringEmpty(device.getApplicationAttentions())) {
                if (!isIn_device) {
                    attentions = device.getApplicationAttentions();
                    str = "<td>设备安全协议：<br>"+attentions+"</td>";
                }
            }else {
                // 设备没有填写注意事项
                // 再判断实验室有没有写注意事项
                if (!EmptyUtil.isStringEmpty(device.getLabRoom().getLabRoomAttentions()) && !isIn_lab) {
                    attentions = device.getLabRoom().getLabRoomAttentions();
                    str = "<td>实验室安全协议：<br>"+attentions+"</td>";
                }else {
                    // 没有填写注意事项  do nothing
                }
            }

            //培训形式
            CDictionary c = device.getCDictionaryByTrainType();

            if (c != null && c.getId() == 627) {//集中培训

                System.out.println("进入现场培训验证流程");
                //String sql="select p from LabRoomDeviceTrainingPeople p where p.labRoomDeviceTraining.labRoomDevice.id="
                //+id+" and p.user.username like '%"+username+"%'"+" and p.CDictionary.id=619";
                String sql = "select p from LabRoomDeviceTrainingPeople p where p.labRoomDeviceTraining.labRoomDevice.id="
                        + id + " and p.user.username like '%" + username + "%' ";
                sql += " and p.CDictionary.CCategory='c_training_result' and p.CDictionary.CNumber ='1'";
                List<LabRoomDeviceTrainingPeople> peoples = labRoomDeviceTrainingPeopleDAO.executeQuery(sql, 0, -1);
                if (peoples.size() == 0) {
                    str = "error";
                }
            }
            if (c != null && c.getId() == 628) {//单独培训
                boolean pass = false;
                Set<LabRoomDevicePermitUsers> students = device.getLabRoomDevicePermitUserses();
                for (LabRoomDevicePermitUsers ss : students) {
                    System.out.println(ss);
                    if (ss.getUser().getUsername() == username) {
                        pass = true;
                        break;
                    }
                }
                if (!pass) {
                    str = "errorType2";
                }
            }
            /*if (c != null && c.getId() == 686) {//网上答题
                if (this.isAccessTestForLabRoomDevice(username, id) == false) {
                    str = "errorType3";
                }
            }*/
        }
        // 获取当前用户
        User currUser = shareService.getUser();
        // 当前用户是否为实验室中心主任
        String judge = ",";
        for (Authority authority : currUser.getAuthorities()) {
            judge = judge + "," + authority.getId() + ",";
        }
        if (device.getUser() == null
                || device.getUser().getUsername() == null
                || device.getUser().getUsername().equals("")) {
            str = "noManager";
        } else {
            if (a != null && a.getId() == 621) {//需要安全准入
                // 中心主任或设备管理员不需要进行培训
                if (judge.indexOf(",4,") > -1 ||
                        (device.getUser() != null && device.getUser().getUsername().equals(username))) {
                    // do nothing
                } else {
                    if (findPermitUserByUsernameAndDeivce(username, id) == null) {
                        //str = "error";
                    }
                }
            }
        }

        return str;
    }


    /****************************************************************************
     * 功能：根据查询条件查询出所有的实验分室设备，添加所在实验室条件
     * 作者：李小龙
     ****************************************************************************/
    @Override
    public int findAllLabRoomDevice(LabRoomDevice device, String acno, int roomId) {
        String sql = "select count(*) from LabRoomDevice d where 1=1";
        //实验室
        if (device.getLabRoom() != null) {
            if (device.getLabRoom().getId() != null && !device.getLabRoom().getId().equals("")&&device.getLabRoom().getId()!=-1) {
                sql += "and d.labRoom.id=" + device.getLabRoom().getId();
            }
        } else {
            if (roomId != -1) {
                sql += " and d.labRoom.id=" + roomId;
            }
        }
        //设备
        if (device.getSchoolDevice() != null) {
            //设备编号
            if (device.getSchoolDevice().getDeviceNumber() != null && !device.getSchoolDevice().getDeviceNumber().equals("")) {
                sql += " and d.schoolDevice.deviceNumber like '%" + device.getSchoolDevice().getDeviceNumber() + "%'";
            }
            //设备名称
            if (device.getSchoolDevice().getDeviceName() != null && !device.getSchoolDevice().getDeviceName().equals("")) {
                sql += " and d.schoolDevice.deviceName like '%" + device.getSchoolDevice().getDeviceName() + "%'";
            }
            if (device.getUser() != null) {
                //设备管理员
                if (device.getUser().getUsername() != null && !device.getUser().getUsername().equals("")) {
                    sql += "and d.user.username  like '%" + device.getUser().getUsername() + "%'";
                }
            }
        }
        if (device != null && device.getCDictionaryByAllowAppointment() != null
                && device.getCDictionaryByAllowAppointment().getCNumber() != null
                && !device.getCDictionaryByAllowAppointment().getCNumber().equals("")) {
            if (device.getCDictionaryByAllowAppointment().getCNumber().equals("1")) {
                sql += " and d.CDictionaryByAllowAppointment.CNumber ='" + device.getCDictionaryByAllowAppointment().getCNumber() + "'";
            } else {
                sql += " and (d.CDictionaryByAllowAppointment.CNumber !='1' or d.CDictionaryByAllowAppointment is null)";
            }
        }
		/*if(cid!=null){
			sql+=" and d.labRoom.labCenter.id="+cid;
		}*/
        //sql+=" and d.labRoom.id="+roomId;

        return ((Long) labRoomDeviceDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
    }

    /****************************************************************************
     * 功能：根据user对象和学院编号查询所有学生
     * 作者：贺子龙
     * 时间：2015-11-05
     ****************************************************************************/
    @Override
    public int findStudentByCnameAndUsername(User user, String academyNumber) {
        String sql = "select count(*) from User u where 1=1";
		/*if(academyNumber!=null&&!academyNumber.equals("")){
			sql+=" and u.schoolAcademy.academyNumber like '"+academyNumber+"%'";
		}*/
        if (user != null) {
            if (user.getCname() != null && !user.getCname().equals("")) {
                sql += " and u.cname like '%" + user.getCname() + "%'";
            }
            if (user.getUsername() != null && !user.getUsername().equals("")) {
                sql += " and u.username like '%" + user.getUsername() + "%'";
            }
        }
        sql += " and u.userRole<>1";
        return ((Long) userDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
    }

    /****************************************************************************
     * 功能：根据user对象和学院编号查询所有学生
     * 作者：贺子龙
     * 时间：2015-11-05
     ****************************************************************************/
    @Override
    public List<User> findStudentByCnameAndUsername(User user, String academyNumber, Integer page, int pageSize) {
        String sql = "select u from User u where 1=1";
		/*if(academyNumber!=null&&!academyNumber.equals("")){
			sql+=" and u.schoolAcademy.academyNumber like '"+academyNumber+"%'";
		}*/
        if (user != null) {
            if (user.getCname() != null && !user.getCname().equals("")) {
                sql += " and u.cname like '%" + user.getCname() + "%'";
            }
            if (user.getUsername() != null && !user.getUsername().equals("")) {
                sql += " and u.username like '%" + user.getUsername() + "%'";
            }
        }
        sql += " and u.userRole<>1";
        sql += "ORDER BY CASE WHEN u.schoolAcademy.academyNumber like '" + academyNumber + "%' THEN 0 ELSE 1 END";
        sql += " ,u.username desc";
        return userDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
    }

    @Override
    public List<LabRoomDeviceReservation> findLabRoomDeviceReservationInfoByDeviceId(
            Integer labRoomDeviceId, Integer currpage, int pageSize) {
        // TODO Auto-generated method stub
        String sql = "select l from LabRoomDeviceReservation l where l.labRoomDevice.id = '" + labRoomDeviceId + "'";

        sql += " order by l.time desc";
        return labRoomDeviceReservationDAO.executeQuery(sql, (currpage - 1) * pageSize, pageSize);
    }

    @Override
    public int countLabRoomDeviceReservationInfoByDeviceId(
            Integer labRoomDeviceId) {
        // TODO Auto-generated method stub
        String sql = "select count(l) from LabRoomDeviceReservation l where l.labRoomDevice.id = '" + labRoomDeviceId + "'";
        int result = ((Long) labRoomDeviceReservationDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
        return result;
    }

    @Override
    public List<LabRoomDevicePermitUsers> findPermitUserByDeivceId(
            Integer labRoomDeviceId, int currpage, int pageSize) {
        // TODO Auto-generated method stub
        String sql = "select u from LabRoomDevicePermitUsers u where 1=1";
        sql += " and u.labRoomDevice.id =" + labRoomDeviceId;
        List<LabRoomDevicePermitUsers> users = labRoomDevicePermitUsersDAO.executeQuery(sql, (currpage - 1) * pageSize, pageSize);
        return users;
    }

    @Override
    public List<LabRoomDeviceTraining> findLabRoomDeviceTrainingListByDeviceId(
            Integer labRoomDeviceId, Integer currpage, int pageSize) {
        // TODO Auto-generated method stub
        String sql = "select l from LabRoomDeviceTraining l where l.labRoomDevice.id=" + labRoomDeviceId;
        sql += " order by l.time desc";
        return labRoomDeviceTrainingDAO.executeQuery(sql, (currpage - 1) * pageSize, pageSize);
    }

    @Override
    public int countLabRoomDeviceTrainingListByDeviceId(Integer labRoomDeviceId) {
        // TODO Auto-generated method stub
        String sql = "select count(l) from LabRoomDeviceTraining l where l.labRoomDevice.id=" + labRoomDeviceId;
        int result = ((Long) labRoomDeviceTrainingDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
        return result;
    }


    /****************************************************************************
     * 功能：根据查询条件查询出所有的实验分室设备并分页
     * 作者：李小龙
     ****************************************************************************/
    @Override
    public List<LabRoomDevice> findAllLabRoomDevice(LabRoomDevice device, String acno,
                                                    Integer page, int pageSize, int roomId) {
        String sql = "select d from LabRoomDevice d where 1=1";
        if (device.getLabRoom() != null) {
            if (device.getLabRoom().getId() != null && !device.getLabRoom().getId().equals("")&&device.getLabRoom().getId()!=-1) {
                sql += " and d.labRoom.id=" + device.getLabRoom().getId();
            }
        } else {
            if (roomId != -1) {
                sql += " and d.labRoom.id=" + roomId;
            }
        }
        //设备
        if (device.getSchoolDevice() != null) {
            //设备编号
            if (device.getSchoolDevice().getDeviceNumber() != null && !device.getSchoolDevice().getDeviceNumber().equals("")) {
                sql += "and d.schoolDevice.deviceNumber like '%" + device.getSchoolDevice().getDeviceNumber() + "%'";
            }
            //设备名称
            if (device.getSchoolDevice().getDeviceName() != null && !device.getSchoolDevice().getDeviceName().equals("")) {
                sql += "and d.schoolDevice.deviceName like '%" + device.getSchoolDevice().getDeviceName() + "%'";
            }
            if (device.getUser() != null) {
                //设备管理员
                if (device.getUser().getUsername() != null && !device.getUser().getUsername().equals("")) {
                    sql += "and d.user.username  like '%" + device.getUser().getUsername() + "%'";
                }
            }
        }
        if (device != null && device.getCDictionaryByAllowAppointment() != null
                && device.getCDictionaryByAllowAppointment().getCNumber() != null
                && !device.getCDictionaryByAllowAppointment().getCNumber().equals("")) {
            if (device.getCDictionaryByAllowAppointment().getCNumber().equals("1")) {
                sql += " and d.CDictionaryByAllowAppointment.CNumber ='" + device.getCDictionaryByAllowAppointment().getCNumber() + "'";
            } else {
                sql += " and (d.CDictionaryByAllowAppointment.CNumber !='1' or d.CDictionaryByAllowAppointment is null)";
            }
        }
		/*if(cid!=null){
			sql+=" and d.labRoom.labAnnex.labCenter.id="+cid;
		}*/
        sql += " order by d.schoolDevice.deviceNumber";
        //sql+=" and d.labRoom.id="+roomId;
        return labRoomDeviceDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
    }

    /****************************************************************************
     * 功能：根据设备id查询设备的预约记录
     * 作者：李小龙
     ****************************************************************************/
    @Override
    public List<LabRoomDeviceReservation> findLabRoomDeviceReservationByDeviceNumber(
            String deviceNumber) {
        // TODO Auto-generated method stub
        String sql = "select l from LabRoomDeviceReservation l where l.schoolDevice.deviceNumber=" + deviceNumber;
        return labRoomDeviceReservationDAO.executeQuery(sql, 0, -1);
    }


    /****************************************************************************
     * 功能：上传设备图片
     * 作者：李小龙
     ****************************************************************************/
    @Override
    public void deviceDocumentUpload(HttpServletRequest request,
                                     HttpServletResponse response, Integer id, int type) {
        String sep = System.getProperty("file.separator");
        String path = sep + "upload" + sep + "device" + sep + id;
        shareService.uploadFiles(request, path, "saveDeviceDocument", id);
    }

    /****************************************************************************
     * 功能：根据学期查询培训
     * 作者：李小龙
     ****************************************************************************/
    @Override
    public List<LabRoomDeviceTraining> findLabRoomDeviceTrainingByTermId(
            Integer termId, Integer id) {
        // TODO Auto-generated method stub
        String sql = "select t from LabRoomDeviceTraining t where t.schoolTerm.id=" + termId + " and t.labRoomDevice.id=" + id;
        sql += " order by t.time desc";
        return labRoomDeviceTrainingDAO.executeQuery(sql, 0, -1);
    }

    /****************************************************************************
     * 功能：根据实验室设备查询设备预约
     * 作者：贺子龙
     ****************************************************************************/
    @Override
    public List<LabRoomDeviceReservation> findLabRoomDeviceReservationByDeviceNumberAndCid(LabRoomDeviceReservation reservation, HttpServletRequest request,
                                                                                           String deviceNumber, int page, int pageSize, int status, String acno) {
        String sql = "select l from LabRoomDeviceReservation l where 1=1";
        if (deviceNumber != null && !deviceNumber.equals("")) {
            sql += " and l.labRoomDevice.schoolDevice.deviceNumber like '" + deviceNumber + "'";
        }
		/*if (cid!=null && cid!=0) {
			sql+=" and l.labRoomDevice.labRoom.labAnnex.labCenter.id ="+cid;
		}*/
        // 学期
        if (reservation.getSchoolTerm() != null) {
            if (reservation.getSchoolTerm().getId() != null) {
                sql += " and l.schoolTerm.id=" + reservation.getSchoolTerm().getId();
            }
        }
        //申请人
        if (reservation.getUserByReserveUser() != null && reservation.getUserByReserveUser().getUsername() != null &&
                !reservation.getUserByReserveUser().getUsername().equals("")) {
            sql += " and l.userByReserveUser.username='" + reservation.getUserByReserveUser().getUsername() + "'";
        }

        //指导老师
        if (reservation.getUserByTeacher() != null && reservation.getUserByTeacher().getUsername() != null &&
                !reservation.getUserByTeacher().getUsername().equals("")) {
            sql += " and l.userByTeacher.username = '" + reservation.getUserByTeacher().getUsername() + "'";
        }

        //设备管理员
        if (reservation.getLabRoomDevice() != null && reservation.getLabRoomDevice().getUser() != null
                && reservation.getLabRoomDevice().getUser().getUsername() != null &&
                !reservation.getLabRoomDevice().getUser().getUsername().equals("")) {
            sql += " and l.labRoomDevice.user.username='" + reservation.getLabRoomDevice().getUser().getUsername() + "'";
        }

        // 设备名称  实验室
        if (reservation.getLabRoomDevice() != null && reservation.getLabRoomDevice().getSchoolDevice() != null) {
            if (reservation.getLabRoomDevice().getSchoolDevice().getDeviceName() != null && !reservation.getLabRoomDevice().getSchoolDevice().getDeviceName().equals("")) {
                sql += " and l.labRoomDevice.schoolDevice.deviceName like '%" + reservation.getLabRoomDevice().getSchoolDevice().getDeviceName() + "%'";
            }
            if (reservation.getLabRoomDevice() != null && reservation.getLabRoomDevice().getLabRoom() != null && reservation.getLabRoomDevice().getLabRoom().getId() != null && !reservation.getLabRoomDevice().getLabRoom().getId().equals("")) {
                sql += " and l.labRoomDevice.labRoom.id=" + reservation.getLabRoomDevice().getLabRoom().getId();
            }
        }

        // 使用机时数
        if (reservation.getReserveHours() != null && !reservation.getReserveHours().equals(0)) {
            sql += " and l.reserveHours = " + reservation.getReserveHours();
        }

        // 起止时间
        if (request != null) {
            String begintime = request.getParameter("begintime");
            String endtime = request.getParameter("endtime");
            if (begintime != null && begintime.length() > 0 && endtime != null && endtime.length() > 0) {
                sql += " and l.begintime between '" + begintime + "' and '" + endtime + "' ";
            }
        }

        // 起止时间
        if (request != null) {
            String begintime = request.getParameter("begintime1");
            String endtime = request.getParameter("endtime1");
            if (begintime != null && begintime.length() > 0 && endtime != null && endtime.length() > 0) {
                sql += " and l.begintime between '" + begintime + "' and '" + endtime + "' ";
            }
        }

        // 审核状态
        if (status != 0) {
            sql += " and l.CDictionaryByCAuditResult.CCategory = 'c_audit_result' and l.CDictionaryByCAuditResult.CNumber = '" + status + "'";
        }
        if (reservation.getResearchProject() != null && reservation.getResearchProject().getId() != null && !reservation.getResearchProject().getId().equals("")) {
            sql += " and l.researchProject.id=" + reservation.getResearchProject().getId();
        }

        // 非排课占用
        sql += " and (l.timetableLabDevice is null or l.appointmentId is null)";
        sql += " group by l.innerSame";
        sql += " order by l.time desc";
        List<LabRoomDeviceReservation> reservationList = labRoomDeviceReservationDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
        // 设置使用机时数
        if (reservationList != null && reservationList.size() > 0) {
            for (LabRoomDeviceReservation labRoomDeviceReservation : reservationList) {
                BigDecimal reserveHours = this.getReserveHoursOfReservation(labRoomDeviceReservation);
                labRoomDeviceReservation.setReserveHours(reserveHours);
                labRoomDeviceReservationDAO.store(labRoomDeviceReservation);
            }
        }
        return reservationList;
    }

    /****************************************************************************
     * 功能：根据实验室设备查询设备预约--数量
     * 作者：贺子龙
     ****************************************************************************/
    public int countLabRoomDeviceReservationByDeviceNumberAndCid(LabRoomDeviceReservation reservation, HttpServletRequest request,
                                                                 String deviceNumber, int status, Integer cid) {
        String sql = "select count(l) from LabRoomDeviceReservation l where 1=1";
        if (deviceNumber != null && !deviceNumber.equals("")) {
            sql += " and l.labRoomDevice.schoolDevice.deviceNumber like '" + deviceNumber + "'";
        }
        if (cid != null && cid != 0) {
            sql += " and l.labRoomDevice.labRoom.labAnnex.labCenter.id =" + cid;
        }
        // 学期
        if (reservation.getSchoolTerm() != null) {
            if (reservation.getSchoolTerm().getId() != null) {
                sql += " and l.schoolTerm.id=" + reservation.getSchoolTerm().getId();
            }
        }
        //申请人
        if (reservation.getUserByReserveUser() != null && reservation.getUserByReserveUser().getUsername() != null &&
                !reservation.getUserByReserveUser().getUsername().equals("")) {
            sql += " and l.userByReserveUser.username=" + reservation.getUserByReserveUser().getUsername();
        }

        //指导老师
        if (reservation.getUserByReserveUser() != null && reservation.getUserByReserveUser().getUsername() != null &&
                !reservation.getUserByTeacher().getUsername().equals("")) {
            sql += " and l.userByTeacher.username=" + reservation.getUserByTeacher().getUsername();
        }

        //设备管理员
        if (reservation.getLabRoomDevice() != null && reservation.getLabRoomDevice().getUser() != null
                && reservation.getLabRoomDevice().getUser().getUsername() != null &&
                !reservation.getLabRoomDevice().getUser().getUsername().equals("")) {
            sql += " and l.labRoomDevice.user.username=" + reservation.getLabRoomDevice().getUser().getUsername();
        }

        // 设备名称  实验室
        if (reservation.getLabRoomDevice() != null && reservation.getLabRoomDevice().getSchoolDevice() != null) {
            if (reservation.getLabRoomDevice().getSchoolDevice().getDeviceName() != null && !reservation.getLabRoomDevice().getSchoolDevice().getDeviceName().equals("")) {
                sql += " and l.labRoomDevice.schoolDevice.deviceName like '%" + reservation.getLabRoomDevice().getSchoolDevice().getDeviceName() + "%'";
            }
            if (reservation.getLabRoomDevice() != null && reservation.getLabRoomDevice().getLabRoom() != null && reservation.getLabRoomDevice().getLabRoom().getId() != null && !reservation.getLabRoomDevice().getLabRoom().getId().equals("")) {
                sql += " and l.labRoomDevice.labRoom.id=" + reservation.getLabRoomDevice().getLabRoom().getId();
            }
        }

        // 使用机时数
        if (reservation.getReserveHours() != null && !reservation.getReserveHours().equals(0)) {
            sql += " and l.reserveHours = " + reservation.getReserveHours();
        }

        // 起止时间
        if (request != null) {
            String begintime = request.getParameter("begintime");
            String endtime = request.getParameter("endtime");
            if (begintime != null && begintime.length() > 0 && endtime != null && endtime.length() > 0) {
                sql += " and l.begintime between '" + begintime + "' and '" + endtime + "' ";
            }
        }

        // 审核状态
        if (status != 0) {
            //sql+=" and l.CAuditResult.id = "+status;
            sql += " and l.CDictionaryByCAuditResult.CCategory = 'c_audit_result' and l.CDictionaryByCAuditResult.CNumber = '" + status + "'";
        }

        // 非排课占用
        sql += " and (l.timetableLabDevice is null or l.appointmentId is null)";

        int count = ((Long) labRoomDeviceReservationDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
        return count;
    }

    /****************************************************************************
     * 功能：获取一个设备预约的使用机时数
     * 作者：贺子龙
     ****************************************************************************/
    public BigDecimal getReserveHoursOfReservation(LabRoomDeviceReservation labRoomDeviceReservation) {
        // 通过设备预约找到设备对应的实验中心id
        int centerId = labRoomDeviceReservation.getLabRoomDevice().getLabRoom().getLabCenter().getId();
        int reserveHours = 0;
        int reserveMinutes = 0;
        int startHour = labRoomDeviceReservation.getBegintime().get(Calendar.HOUR_OF_DAY);
        int endHour = labRoomDeviceReservation.getEndtime().get(Calendar.HOUR_OF_DAY);
        int startDay = labRoomDeviceReservation.getBegintime().get(Calendar.DAY_OF_YEAR);
        int endDay = labRoomDeviceReservation.getEndtime().get(Calendar.DAY_OF_YEAR);
        int startMinute = labRoomDeviceReservation.getBegintime().get(Calendar.MINUTE);
        int endMinute = labRoomDeviceReservation.getEndtime().get(Calendar.MINUTE);
        if (startDay == endDay) {// 不跨天
            reserveHours = endHour - startHour;
        } else {// 跨天
            if (centerId == 12) { // 纺织中心
                if (endHour >= 17) {
                    endHour = 17;
                }
                if (startHour <= 8) {
                    startHour = 8;
                }
                reserveHours += 17 - startHour;// 第一天
                reserveHours += (endDay - startDay - 1) * (17 - 8);// 中间天
                reserveHours += endHour - 8;// 最后一天

            } else { // 非纺织中心
                if (endHour >= 20) {
                    endHour = 20;
                }
                if (startHour <= 8) {
                    startHour = 8;
                }
                reserveHours += 20 - startHour;// 第一天
                reserveHours += (endDay - startDay - 1) * (20 - 8);// 中间天
                reserveHours += startHour - 8;// 最后一天
            }
        }
        reserveMinutes = endMinute - startMinute;
        double hourInMinute = reserveMinutes / 60.0;
        double hour = hourInMinute + reserveHours;
        BigDecimal bg = new BigDecimal(hour);
        return bg;
    }

    /****************************************************************************
     * 功能：保存新建的出借申请单
     * 作者：李鹏翔
     ****************************************************************************/
    public LabRoomDeviceLending saveDeviceLendApply(LabRoomDeviceLending lrdl) {
        /*if (lrdl.getBackTime() == null) {
            lrdl.setBackTime(Calendar.getInstance());
        }*/
        lrdl = labRoomDeviceLendingDAO.store(lrdl);
        labRoomDeviceLendingDAO.flush();
        return lrdl;
    }

    /****************************************************************************
     * 功能：查找所有设备借用单的记录总数
     * 作者：李鹏翔
     ****************************************************************************/
    public int getLabRoomLendsTotals(
            LabRoomDeviceLending lrdl, HttpServletRequest request) {
    	User user = shareService.getUser();
        String sql = "select count(l) from LabRoomDeviceLending l where (l.CDictionary.id is null or l.CDictionary.id=653 or l.CDictionary.id=743) and (l.stage=0 or l.stage is not 3)";

        String deviceName = request.getParameter("deviceName");
        if (deviceName != null && !deviceName.equals("")) {
            sql += " and (l.labRoomDevice.schoolDevice.deviceName like '%" + deviceName
                    + "%' or l.labRoomDevice.schoolDevice.deviceNumber like '%"+ deviceName +"%')";
        }
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        if (starttime != null && starttime.length() > 0 && endtime != null && endtime.length() > 0) {
            sql += " and l.lendingTime between '" + starttime + "' and '" + endtime + "' ";
        }

        String lendBatch = request.getParameter("lendBatch");
        if (lendBatch != null && !lendBatch.equals("")) {
            sql += " and (l.lendBatch like '%"+ lendBatch +"%')";
        }

        //根据当前登陆人身份判断可以看到哪些信息
        String auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        //是否是管理员或实训部主任
        if(auth.indexOf("ROLE_SUPERADMIN") != -1 || auth.indexOf("ROLE_PREEXTEACHING") != -1){

        }else{
            //是否是实验室管理员
            if(auth.indexOf("ROLE_LABMANAGER") != -1){
                sql += " and l.labRoomDevice.labRoom.id in (select r.labRoom.id from LabRoomAdmin r where r.user.username like '"
                        + user.getUsername() + "')";
            }else{
                sql += " and l.userByDepartmentHead.username ='"+user.getUsername()+"'";
            }
        }
        return ((Long) labRoomDeviceLendingDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
    }

    /****************************************************************************
     * 功能：查找所有设备领用单的记录总数
     * 作者：李鹏翔
     ****************************************************************************/
    public int getLabRoomKeepsTotals(
            LabRoomDeviceLending lrdl) {
//		String sql="select count(l) from LabRoomDeviceLending l where l.CLendingStatus.id is null and l.lendType='2'";
        String sql = "select count(l) from LabRoomDeviceLending l where l.CDictionary.id is null and l.lendType='2'";
        return ((Long) labRoomDeviceLendingDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
    }

    /****************************************************************************
     * 功能：查找所有设备借用单
     * 作者：李鹏翔
     ****************************************************************************/
    @Override
    public List<LabRoomDeviceLending> findAllLabRoomLends(
            LabRoomDeviceLending lrdl, Integer page, int pageSize, HttpServletRequest request) {
        User user = shareService.getUser();
        String sql = "select l from LabRoomDeviceLending l where (l.CDictionary.id is null or l.CDictionary.id=653 or l.CDictionary.id=743) and (l.stage=0 or l.stage is not 3)";

        String deviceName = request.getParameter("deviceName");
        if (deviceName != null && !deviceName.equals("")) {
        	sql += " and (l.labRoomDevice.schoolDevice.deviceName like '%" + deviceName 
            		+ "%' or l.labRoomDevice.schoolDevice.deviceNumber like '%"+ deviceName +"%')";
        }
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        if (starttime != null && starttime.length() > 0 && endtime != null && endtime.length() > 0) {
            sql += " and l.lendingTime between '" + starttime + "' and '" + endtime + "' ";
        }
        
        String lendBatch = request.getParameter("lendBatch");
        if (lendBatch != null && !lendBatch.equals("")) {
            sql += " and (l.lendBatch like '%"+ lendBatch +"%')";
        }
        
        //根据当前登陆人身份判断可以看到哪些信息
        String auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        //是否是管理员或实训部主任
        if(auth.indexOf("ROLE_SUPERADMIN") != -1 || auth.indexOf("ROLE_PREEXTEACHING") != -1){
        	
        }else{
        	//是否是实验室管理员
        	if(auth.indexOf("ROLE_LABMANAGER") != -1){
        		sql += " and l.labRoomDevice.labRoom.id in (select r.labRoom.id from LabRoomAdmin r where r.user.username like '"
        				+ user.getUsername() + "')";
        	}else{
        		sql += " and l.userByDepartmentHead.username ='"+user.getUsername()+"'";
        	}
        }
        sql += " order by l.lendingTime desc";
        return labRoomDeviceLendingDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
    }

    /****************************************************************************
     * 功能：查找所有设备领用单
     * 作者：李鹏翔
     ****************************************************************************/
    public List<LabRoomDeviceLending> findAllLabRoomKeeps(
            LabRoomDeviceLending lrdl, Integer page, int pageSize) {
//		String sql="select l from LabRoomDeviceLending l where l.CLendingStatus.id is null and l.lendType='2'";
        String sql = "select l from LabRoomDeviceLending l where l.CDictionary.id is null and l.lendType='2'";
        return labRoomDeviceLendingDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
    }

    /****************************************************************************
     * 功能：根据id查找设备借用单
     * 作者：李鹏翔
     ****************************************************************************/
    public LabRoomDeviceLending findDeviceLendingById(Integer idKey) {
        return labRoomDeviceLendingDAO.findLabRoomDeviceLendingByPrimaryKey(idKey);
    }

    /****************************************************************************
     * 功能：查找审核结果map
     * 作者：李鹏翔
     ****************************************************************************/
    public Map<Integer, String> getAuditResultMap() {
        //Set<CAuditResult> cars = cAuditResultDAO.findAllCAuditResults();
        List<CDictionary> cars = shareService.getCDictionaryData("c_audit_result");
        Map<Integer, String> map = new HashMap<Integer, String>();
		/*for(CAuditResult car : cars){
			map.put(car.getId(), car.getName());
		}*/
        for (CDictionary car : cars) {
            map.put(car.getId(), car.getCName());
        }
        return map;
    }

    /****************************************************************************
     * 功能：保存审核之后的借用单
     * 作者：李鹏翔
     ****************************************************************************/
    public void saveAuditDeviceLending(LabRoomDeviceLendingResult lrdlr) {
        labRoomDeviceLendingResultDAO.store(lrdlr);
        labRoomDeviceLendingResultDAO.flush();
    }

    /****************************************************************************
     * 功能：查找所有审核通过的设备借用审核单总数
     * 作者：李鹏翔
     ****************************************************************************/
    public int getPassLendingTotals(LabRoomDeviceLending lrdl, HttpServletRequest request) {
//		String sql="select count(l) from LabRoomDeviceLendingResult l where l.CAuditResult.id=2 and l.labRoomDeviceLending.CLendingStatus.id = 1 and l.labRoomDeviceLending.lendType='1'";
//		User user=shareService.getUser();
//		String sql="select count(l) from LabRoomDeviceLendingResult l where l.CDictionary.CCategory='c_audit_result' and l.CDictionary.CNumber ='2' and l.labRoomDeviceLending.CDictionary.CCategory='c_lending_status' and l.labRoomDeviceLending.CDictionary.CNumber= '1' and l.labRoomDeviceLending.lendType='1'";
//		sql+="and l.labRoomDeviceLending.userByLendingUser.username like '"+user.getUsername()+"'";
        User user = shareService.getUser();
        String sql = "select count(l) from LabRoomDeviceLending l where l.stage=3";
//		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") == -1){
//			sql+=" and l.userByLendingUser.username like '"+user.getUsername()+"'";
//		}

        String deviceName = request.getParameter("deviceName");
        if (deviceName != null && !deviceName.equals("")) {
            sql += " and (l.labRoomDevice.schoolDevice.deviceName like '%" + deviceName
                    + "%' or l.labRoomDevice.schoolDevice.deviceNumber like '%"+ deviceName +"%')";
        }

        String lendBatch = request.getParameter("lendBatch");
        if (lendBatch != null && !lendBatch.equals("")) {
            sql += " and (l.lendBatch like '%"+ lendBatch +"%')";
        }

        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        if (starttime != null && starttime.length() > 0 && endtime != null && endtime.length() > 0) {
            sql += " and l.lendingTime between '" + starttime + "' and '" + endtime + "' ";
        }
        return ((Long) labRoomDeviceLendingDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
    }

    /****************************************************************************
     * 功能：查找所有审核通过的设备领用审核单总数
     * 作者：李鹏翔
     ****************************************************************************/
    public int getPassKeepingTotals(LabRoomDeviceLendingResult lrdlr) {
//		String sql="select count(l) from LabRoomDeviceLendingResult l where l.CAuditResult.id=2 and l.labRoomDeviceLending.CLendingStatus.id = 1 and l.labRoomDeviceLending.lendType='2'";
        User user = shareService.getUser();
        String sql = "select count(l) from LabRoomDeviceLendingResult l where l.CDictionary.CCategory='c_audit_result' and l.CDictionary.CNumber ='2' and l.labRoomDeviceLending.CDictionary.CCategory='c_lending_status' and l.labRoomDeviceLending.CDictionary.CNumber= '1' and l.labRoomDeviceLending.lendType='2'";
        sql += " and l.labRoomDeviceLending.userByLendingUser.username like '" + user.getUsername() + "'";
        return ((Long) labRoomDeviceLendingResultDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
    }

    /****************************************************************************
     * 功能：查找所有审核通过的设备借用审核单
     * 作者：李鹏翔
     ****************************************************************************/
    public List<LabRoomDeviceLending> findAllPassLending(
            LabRoomDeviceLending lrdlr, Integer page, int pageSize, HttpServletRequest request) {
//		String sql="select l from LabRoomDeviceLendingResult l where l.CAuditResult.id=2 and l.labRoomDeviceLending.CLendingStatus.id = 1 and l.labRoomDeviceLending.lendType='1'";
        String sql = "select l from LabRoomDeviceLendingResult l where l.CDictionary.CCategory='c_audit_result' and l.CDictionary.CNumber ='2' and l.labRoomDeviceLending.CDictionary.CCategory='c_lending_status' and l.labRoomDeviceLending.CDictionary.CNumber= '1' and l.labRoomDeviceLending.lendType='1'";
        String deviceName = request.getParameter("deviceName");
        if (deviceName != null && !deviceName.equals("")) {
            sql += " and (l.labRoomDeviceLending.labRoomDevice.schoolDevice.deviceName like '%" + deviceName 
            		+ "%' or l.labRoomDeviceLending.labRoomDevice.schoolDevice.deviceNumber like '%"+ deviceName +"%')";
        }
        String lendBatch = request.getParameter("lendBatch");
        if (lendBatch != null && !lendBatch.equals("")) {
            sql += " and (l.lendBatch like '%"+ lendBatch +"%')";
        }
        return labRoomDeviceLendingDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
    }

    /****************************************************************************
     * 功能：查找所有审核通过的设备借用审核单
     * 作者：周志辉
     * 时间：2017-10-18
     ****************************************************************************/
    public List<LabRoomDeviceLending> findAllPassLending(Integer page, int pageSize, HttpServletRequest request) {
        String sql = "select l from LabRoomDeviceLending l where l.stage=3";
        String deviceName = request.getParameter("deviceName");
        if (deviceName != null && !deviceName.equals("")) {
        	sql += " and (l.labRoomDevice.schoolDevice.deviceName like '%" + deviceName 
                    + "%' or l.labRoomDevice.schoolDevice.deviceNumber like '%"+ deviceName +"%')";
        }
        String lendBatch = request.getParameter("lendBatch");
        if (lendBatch != null && !lendBatch.equals("")) {
            sql += " and (l.lendBatch like '%"+ lendBatch +"%')";
        }
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        if (starttime != null && starttime.length() > 0 && endtime != null && endtime.length() > 0) {
            sql += " and l.lendingTime between '" + starttime + "' and '" + endtime + "' ";
        }
        sql += " order by l.lendingTime desc";
        return labRoomDeviceLendingDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
    }

    /****************************************************************************
     * 功能：查找所有审核通过的设备领用审核单
     * 作者：李鹏翔
     ****************************************************************************/
    public List<LabRoomDeviceLendingResult> findAllPassKeeping(
            LabRoomDeviceLendingResult lrdlr, Integer page, int pageSize) {
//		String sql="select l from LabRoomDeviceLendingResult l where l.CAuditResult.id=2 and l.labRoomDeviceLending.CLendingStatus.id = 1 and l.labRoomDeviceLending.lendType='2'";
        String sql = "select l from LabRoomDeviceLendingResult l where l.CDictionary.CCategory='c_audit_result' and l.CDictionary.CNumber ='2' and l.labRoomDeviceLending.CDictionary.CCategory='c_lending_status' and l.labRoomDeviceLending.CDictionary.CNumber= '1' and l.labRoomDeviceLending.lendType='2'";
        return labRoomDeviceLendingResultDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
    }

    /****************************************************************************
     * 功能：查找所有审核被拒绝的设备借用审核单总数
     * 作者：李鹏翔
     ****************************************************************************/
    public int getRejectedLendingTotals(LabRoomDeviceLending lrdl, HttpServletRequest request) {
//		String sql="select count(l) from LabRoomDeviceLendingResult l where l.CAuditResult.id=3 and l.labRoomDeviceLending.CLendingStatus.id = 1 and l.labRoomDeviceLending.lendType='1'";
//		String sql="select count(l) from LabRoomDeviceLendingResult l where l.CDictionary.CCategory='c_audit_result' and l.CDictionary.CNumber ='3' and l.labRoomDeviceLending.CDictionary.CCategory='c_lending_status' and l.labRoomDeviceLending.CDictionary.CNumber= '1' and l.labRoomDeviceLending.lendType='1'";
        String sql = "select count(l) from LabRoomDeviceLending l where l.stage<0";
        User user = shareService.getUser();
//		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") == -1){
//			sql+=" and l.labRoomDeviceLending.userByLendingUser.username like '"+user.getUsername()+"'";
//		}

        String deviceName = request.getParameter("deviceName");
        if (deviceName != null && !deviceName.equals("")) {
            sql += " and (l.labRoomDevice.schoolDevice.deviceName like '%" + deviceName
                    + "%' or l.labRoomDevice.schoolDevice.deviceNumber like '%"+ deviceName +"%')";
        }

        String lendBatch = request.getParameter("lendBatch");
        if (lendBatch != null && !lendBatch.equals("")) {
            sql += " and (l.lendBatch like '%"+ lendBatch +"%')";
        }

        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        if (starttime != null && starttime.length() > 0 && endtime != null && endtime.length() > 0) {
            sql += " and l.lendingTime between '" + starttime + "' and '" + endtime + "' ";
        }
        return ((Long) labRoomDeviceLendingDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
    }

    /****************************************************************************
     * 功能：查找所有审核被拒绝的设备领用审核单总数
     * 作者：李鹏翔
     ****************************************************************************/
    public int getRejectedKeepingTotals(LabRoomDeviceLendingResult lrdlr) {
//		String sql="select count(l) from LabRoomDeviceLendingResult l where l.CAuditResult.id=3 and l.labRoomDeviceLending.CLendingStatus.id = 1 and l.labRoomDeviceLending.lendType='2'";
        String sql = "select count(l) from LabRoomDeviceLendingResult l where l.CDictionary.CCategory='c_audit_result' and l.CDictionary.CNumber ='3' and l.labRoomDeviceLending.CDictionary.CCategory='c_lending_status' and l.labRoomDeviceLending.CDictionary.CNumber= '1' and l.labRoomDeviceLending.lendType='2'";
        return ((Long) labRoomDeviceLendingResultDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
    }

    /****************************************************************************
     * 功能：查找所有审核通过的设备借用审核单
     * 作者：李鹏翔
     ****************************************************************************/
    public List<LabRoomDeviceLending> findAllRejectedLending(
            LabRoomDeviceLending lrdl, Integer page, int pageSize, HttpServletRequest request) {

        String sql = "select l from LabRoomDeviceLending l where l.CDictionary.CCategory='c_audit_result' and l.CDictionary.CNumber ='3' and l.lendType='1'";
        String deviceName = request.getParameter("deviceName");
        if (deviceName != null && !deviceName.equals("")) {
            sql += " and (l.labRoomDevice.schoolDevice.deviceName like '%" + deviceName
                    + "%' or l.labRoomDevice.schoolDevice.deviceNumber like '%"+ deviceName +"%')";
        }
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        if (starttime != null && starttime.length() > 0 && endtime != null && endtime.length() > 0) {
            sql += " and l.lendingTime between '" + starttime + "' and '" + endtime + "' ";
        }
        sql += " order by l.lendingTime desc";
        return labRoomDeviceLendingDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
    }

    /****************************************************************************
     * 功能：查找所有审核通过的设备领用审核单
     * 作者：李鹏翔
     ****************************************************************************/
    public List<LabRoomDeviceLendingResult> findAllRejectedKeeping(
            LabRoomDeviceLendingResult lrdlr, Integer page, int pageSize) {
//		String sql="select l from LabRoomDeviceLendingResult l where l.CAuditResult.id=3 and l.labRoomDeviceLending.CLendingStatus.id = 1 and l.labRoomDeviceLending.lendType='2'";
        String sql = "select l from LabRoomDeviceLendingResult l where l.CDictionary.CCategory='c_audit_result' and l.CDictionary.CNumber ='3' and l.labRoomDeviceLending.CDictionary.CCategory='c_lending_status' and l.labRoomDeviceLending.CDictionary.CNumber= '1' and l.labRoomDeviceLending.lendType='2'";
        return labRoomDeviceLendingResultDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
    }

    /****************************************************************************
     * 功能：查找所有已归还设备借用审核单总数
     * 作者：李鹏翔
     ****************************************************************************/
    public int getReturnedLendingTotals(LabRoomDeviceLendingResult lrdlr, HttpServletRequest request) {
//		String sql="select count(l) from LabRoomDeviceLendingResult l where l.CAuditResult.id=2 and l.labRoomDeviceLending.CLendingStatus.id = 2 and l.labRoomDeviceLending.lendType='1'";
        String sql = "select count(l) from LabRoomDeviceLendingResult l where l.CDictionary.CCategory='c_audit_result' and l.CDictionary.CNumber ='2' and l.labRoomDeviceLending.CDictionary.CCategory='c_lending_status' and l.labRoomDeviceLending.CDictionary.CNumber= '2' and l.labRoomDeviceLending.lendType='1'";
        User user = shareService.getUser();
//		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") == -1){
//			sql+=" and l.labRoomDeviceLending.userByLendingUser.username like '"+user.getUsername()+"'";
//		}
        String deviceName = request.getParameter("deviceName");
        if (deviceName != null && !deviceName.equals("")) {
        	sql += " and (l.labRoomDeviceLending.labRoomDevice.schoolDevice.deviceName like '%" + deviceName 
                    + "%' or l.labRoomDeviceLending.labRoomDevice.schoolDevice.deviceNumber like '%"+ deviceName +"%')";
        }
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        if (starttime != null && starttime.length() > 0 && endtime != null && endtime.length() > 0) {
            sql += " and l.labRoomDeviceLending.lendingTime between '" + starttime + "' and '" + endtime + "' ";
        }
        return ((Long) labRoomDeviceLendingResultDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
    }

    /****************************************************************************
     * 功能：查找所有已归还设备领用审核单总数
     * 作者：李鹏翔
     ****************************************************************************/
    public int getReturnedKeepingTotals(LabRoomDeviceLendingResult lrdlr) {
//		String sql="select count(l) from LabRoomDeviceLendingResult l where l.CAuditResult.id=2 and l.labRoomDeviceLending.CLendingStatus.id = 2 and l.labRoomDeviceLending.lendType='2'";
        String sql = "select count(l) from LabRoomDeviceLendingResult l where l.CDictionary.CCategory='c_audit_result' and l.CDictionary.CNumber ='2' and l.labRoomDeviceLending.CDictionary.CCategory='c_lending_status' and l.labRoomDeviceLending.CDictionary.CNumber= '2' and l.labRoomDeviceLending.lendType='2'";
        return ((Long) labRoomDeviceLendingResultDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
    }

    /****************************************************************************
     * 功能：查找所有已归还的设备借用审核单
     * 作者：李鹏翔
     ****************************************************************************/
    public List<LabRoomDeviceLendingResult> findAllReturnedLending(
            LabRoomDeviceLendingResult lrdlr, Integer page, int pageSize, HttpServletRequest request) {
//		String sql="select l from LabRoomDeviceLendingResult l where l.CAuditResult.id=2 and l.labRoomDeviceLending.CLendingStatus.id = 2 and l.labRoomDeviceLending.lendType='1'";
        String sql = "select l from LabRoomDeviceLendingResult l where l.CDictionary.CCategory='c_audit_result' and l.CDictionary.CNumber ='2' and l.labRoomDeviceLending.CDictionary.CCategory='c_lending_status' and l.labRoomDeviceLending.CDictionary.CNumber= '2' and l.labRoomDeviceLending.lendType='1'";
        User user = shareService.getUser();
//		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") == -1){
//			sql+=" and l.labRoomDeviceLending.userByLendingUser.username like '"+user.getUsername()+"'";
//		}
        String deviceName = request.getParameter("deviceName");
        if (deviceName != null && !deviceName.equals("")) {
        	sql += " and (l.labRoomDeviceLending.labRoomDevice.schoolDevice.deviceName like '%" + deviceName 
                    + "%' or l.labRoomDeviceLending.labRoomDevice.schoolDevice.deviceNumber like '%"+ deviceName +"%')";
        }
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        if (starttime != null && starttime.length() > 0 && endtime != null && endtime.length() > 0) {
            sql += " and l.labRoomDeviceLending.lendingTime between '" + starttime + "' and '" + endtime + "' ";
        }
        sql += " order by l.labRoomDeviceLending.lendingTime desc";
        return labRoomDeviceLendingResultDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
    }

    /****************************************************************************
     * 功能：查找所有已归还的设备领用审核单
     * 作者：李鹏翔
     ****************************************************************************/
    public List<LabRoomDeviceLendingResult> findAllReturnedKeeping(
            LabRoomDeviceLendingResult lrdlr, Integer page, int pageSize) {
//		String sql="select l from LabRoomDeviceLendingResult l where l.CAuditResult.id=2 and l.labRoomDeviceLending.CLendingStatus.id = 2 and l.labRoomDeviceLending.lendType='2'";
        String sql = "select l from LabRoomDeviceLendingResult l where l.CDictionary.CCategory='c_audit_result' and l.CDictionary.CNumber ='2' and l.labRoomDeviceLending.CDictionary.CCategory='c_lending_status' and l.labRoomDeviceLending.CDictionary.CNumber= '2' and l.labRoomDeviceLending.lendType='2'";
        return labRoomDeviceLendingResultDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
    }

    /****************************************************************************
     * 功能：归还借出的设备
     * 作者：李鹏翔
     ****************************************************************************/
    @Override
    public void returnDeviceLending(Integer idKey, String remark, String backtime) throws ParseException {
        LabRoomDeviceLending lrdl = labRoomDeviceLendingDAO.findLabRoomDeviceLendingById(idKey);
        LabRoomDevice lrd = lrdl.getLabRoomDevice();

        lrdl.setRemark(remark);
        
        CDictionary cds = shareService.getCDictionaryByCategory("c_lab_room_device_status", "1");
        lrd.setCDictionaryByDeviceStatus(cds);
        labRoomDeviceDAO.store(lrd);

        CDictionary cls = shareService.getCDictionaryByCategory("c_lending_status", "2");
        lrdl.setCDictionary(cls);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(backtime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        lrdl.setBackTime(calendar);
        labRoomDeviceLendingDAO.store(lrdl);
    }

    /****************************************************************************
     * 功能：查找所有维修列表总数
     * 作者：李鹏翔
     ****************************************************************************/
    public int getAllRepairTotals(LabRoomDeviceRepair lrdr) {
        String sql = "select count(l) from LabRoomDeviceRepair l  where 1=1";
        return ((Long) labRoomDeviceRepairDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
    }

    /****************************************************************************
     * 功能：查找所有维修列表
     * 作者：李鹏翔
     ****************************************************************************/
    public List<LabRoomDeviceRepair> findAllRepairs(
            LabRoomDeviceRepair lrdr, Integer page, int pageSize) {
        String sql = "select l from LabRoomDeviceRepair l  where 1=1";
        return labRoomDeviceRepairDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
    }

    /****************************************************************************
     * 功能：查找当前用户的设备报修或审核
     * 作者：李鹏翔
     ****************************************************************************/
    public List<LabRoomDeviceRepair> findAllRepairsByUser(String username) {
        String sql = "select l from LabRoomDeviceRepair l  where l.user.username = '" + username + "' or l.labRoomDevice.user.username = '" + username + "'";
        return labRoomDeviceRepairDAO.executeQuery(sql, 0, -1);
    }

    /****************************************************************************
     * 功能：保存设备报修
     * 作者：李鹏翔
     ****************************************************************************/
    public void saveNewDeviceRepair(LabRoomDeviceRepair lrdr) {
        if (lrdr.getCreateTime() == null) {
            lrdr.setCreateTime(Calendar.getInstance());
        }
        labRoomDeviceRepairDAO.store(lrdr);
        labRoomDeviceRepairDAO.flush();
    }

    /****************************************************************************
     * 功能：查找所有报修列表
     * 作者：李鹏翔
     ****************************************************************************/
    public List<LabRoomDeviceRepair> findApplyRepairs(
            LabRoomDeviceRepair lrdr, Integer page, int pageSize) {
//		String sql="select l from LabRoomDeviceRepair l  where l.CLabRoomDeviceRepairStatus = 1";
//		String sql="select l from LabRoomDeviceRepair l  where l.CDictionaryByStatusId.CCategory='c_lab_room_device_repair_status' and l.CDictionaryByStatusId.CNumber ='1'";
        String sql = "select l from LabRoomDeviceRepair l  where 1=1";
        return labRoomDeviceRepairDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
    }

    /****************************************************************************
     * 功能：查找所有已修复列表
     * 作者：李鹏翔
     ****************************************************************************/
    public List<LabRoomDeviceRepair> findPassRepairs(
            LabRoomDeviceRepair lrdr, Integer page, int pageSize) {
//		String sql="select l from LabRoomDeviceRepair l  where l.CLabRoomDeviceRepairStatus = 3";
        String sql = "select l from LabRoomDeviceRepair l  where l.CDictionaryByStatusId.CCategory='c_lab_room_device_repair_status' and l.CDictionaryByStatusId.CNumber ='3'";
        return labRoomDeviceRepairDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
    }

    /****************************************************************************
     * 功能：查找所有报修列表
     * 作者：李鹏翔
     ****************************************************************************/
    public List<LabRoomDeviceRepair> findRejectedRepairs(
            LabRoomDeviceRepair lrdr, Integer page, int pageSize) {
//		String sql="select l from LabRoomDeviceRepair l  where l.CLabRoomDeviceRepairStatus = 2";
        String sql = "select l from LabRoomDeviceRepair l  where l.CDictionaryByStatusId.CCategory='c_lab_room_device_repair_status' and l.CDictionaryByStatusId.CNumber ='2'";
        return labRoomDeviceRepairDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
    }

    /****************************************************************************
     * 功能：查找所有借用记录条数
     * 作者：李鹏翔
     ****************************************************************************/
    public int getAllLendTotals(HttpServletRequest request) {
        //String sql1 = "select count(l) from LabRoomDeviceLending l where l.CLendingStatus.id is null and l.lendType='1'";
        User user = shareService.getUser();
        String sql1 = "select count(l) from LabRoomDeviceLending l where l.CDictionary.id is null and l.lendType='1'";
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") == -1) {
            sql1 += " and l.userByLendingUser.username like '" + user.getUsername() + "'";
        }
        String sql2 = "select count(l) from LabRoomDeviceLendingResult l where l.labRoomDeviceLending.lendType='1'";
        String deviceName = request.getParameter("deviceName");
        if (deviceName != null && !deviceName.equals("")) {
            sql1 += " and l.labRoomDevice.schoolDevice.deviceName like '%" + deviceName + "%'";
            sql2 += " and l.labRoomDeviceLending.labRoomDevice.schoolDevice.deviceName like '%" + deviceName + "%'";
        }
        //申请单数目
        int t1 = ((Long) labRoomDeviceLendingDAO.createQuerySingleResult(sql1).getSingleResult()).intValue();
        //审核数目
        int t2 = ((Long) labRoomDeviceLendingResultDAO.createQuerySingleResult(sql2).getSingleResult()).intValue();
        return t1 + t2;
    }

    /****************************************************************************
     * 功能：查找所有借用申请和审核
     * 作者：李鹏翔
     ****************************************************************************/
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<SchoolDevice> findAllLendingList(int curr, int size, HttpServletRequest request) {
//		String deviceName=request.getParameter("deviceName");
//		String sql1 = "select l from LabRoomDeviceLending l where l.CLendingStatus.id is null and l.lendType='1' ";
        User user = shareService.getUser();
        String sql1 = "select l from LabRoomDeviceLending l where l.CDictionary.id is null and l.lendType='1'";
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") == -1) {
            sql1 += " and l.userByLendingUser.username like '" + user.getUsername() + "'";
        }
        String sql2 = "select l from LabRoomDeviceLendingResult l where l.labRoomDeviceLending.lendType='1'";
        String deviceName = request.getParameter("deviceName");
        if (deviceName != null && !deviceName.equals("")) {
            sql1 += " and l.labRoomDevice.schoolDevice.deviceName like '%" + deviceName + "%'";
            sql2 += " and l.labRoomDeviceLending.labRoomDevice.schoolDevice.deviceName like '%" + deviceName + "%'";
        }
        List<LabRoomDeviceLending> l1 = new LinkedList(labRoomDeviceLendingDAO.executeQuery(sql1, (curr - 1) * size, size));
        List<LabRoomDeviceLendingResult> l2 = new LinkedList(labRoomDeviceLendingResultDAO.executeQuery(sql2, (curr - 1) * size, size));
        //创建新集合存放两个集合的内同
        List<SchoolDevice> sds = new ArrayList();
        //将两个集合合并
        if (l2.size() > 0) {
            for (LabRoomDeviceLendingResult l : l2) {
                if (l.getLabRoomDeviceLending() != null) {
                    SchoolDevice sd = new SchoolDevice();
                    if (l.getLabRoomDeviceLending() != null) {
//						sd.setDevicePattern(l.getLabRoomDeviceLending()==null?null:l.getLabRoomDeviceLending().getUserByTeacher().getCname());
                        sd.setDeviceEnName(l.getLabRoomDeviceLending() == null ? null : l.getLabRoomDeviceLending().getUserByLendingUser().getCname());
                        sd.setDeviceFormat(l.getLabRoomDeviceLending().getContent());
                        sd.setCreatedAt(l.getLabRoomDeviceLending().getLendingTime());
                        sd.setDeviceCountry(l.getLabRoomDeviceLending() == null ? null : l.getLabRoomDeviceLending().getUserByLendingUser().getUsername());
						/*if(l.getLabRoomDeviceLending()!=null&&l.getLabRoomDeviceLending().getCLendingStatus()!=null){
						    sd.setAcademyMemo(l.getLabRoomDeviceLending().getCLendingStatus().getName());
						}*/
                        if (l.getLabRoomDeviceLending() != null && l.getLabRoomDeviceLending().getCDictionary() != null) {
                            sd.setAcademyMemo(l.getLabRoomDeviceLending().getCDictionary().getCName());
                        }
                        if (l.getLabRoomDeviceLending().getLabRoomDevice() != null) {
                            if (l.getLabRoomDeviceLending().getLabRoomDevice().getSchoolDevice() != null) {
                                sd.setDeviceName(l.getLabRoomDeviceLending().getLabRoomDevice().getSchoolDevice().getDeviceName());
                                sd.setDeviceNumber(l.getLabRoomDeviceLending().getLabRoomDevice().getSchoolDevice().getDeviceNumber());
                            }
                            if (l.getLabRoomDeviceLending().getLabRoomDevice().getUser() != null) {
                                sd.setProjectSource(l.getLabRoomDeviceLending().getLabRoomDevice().getUser().getUsername());
                            }
                        }
                    }
                    if (l.getUser() != null) {
                        sd.setDeviceUseDirection(l.getUser().getCname());
                    }
                    //sd.setDeviceAddress(l.getCAuditResult().getName());
                    sd.setDeviceAddress(l.getCDictionary().getCName());
                    sd.setDeviceSupplier(l.getRemark());
//					sd.setManufacturer(String.valueOf(l.getLabRoomDeviceLending().getLabRoomDevice().getLabRoom().getLabAnnex().getLabCenter().getId()));
                    sds.add(sd);
                }
            }
        }
        if (l1.size() > 0) {
            for (LabRoomDeviceLending ld : l1) {
                SchoolDevice sd = new SchoolDevice();
                sd.setDeviceNumber(ld.getLabRoomDevice().getSchoolDevice().getDeviceNumber());
                sd.setDeviceName(ld.getLabRoomDevice().getSchoolDevice().getDeviceName());
                sd.setDeviceEnName(ld.getUserByLendingUser().getCname());
//				sd.setDevicePattern(ld.getUserByTeacher().getCname());
                sd.setDeviceFormat(ld.getContent());
                sd.setCreatedAt(ld.getLendingTime());
                sd.setDeviceUseDirection("未审核");
                sd.setDeviceAddress("未审核");
                sd.setDeviceSupplier("未审核");
                sd.setAcademyMemo("未归还");
                sd.setDeviceCountry(ld.getUserByLendingUser().getUsername());
                if (ld.getLabRoomDevice().getUser() != null) {
                    sd.setProjectSource(ld.getLabRoomDevice().getUser().getUsername());
                }
//				sd.setManufacturer(String.valueOf(ld.getLabRoomDevice().getLabRoom().getLabAnnex().getLabCenter().getId()));
                sds.add(sd);
            }
        }
        return sds;
    }

    /****************************************************************************
     * 功能：下载设备文档（化工学院）
     * 作者：李小龙
     ****************************************************************************/
    @Override
    public void downloadFile(CommonDocument doc, HttpServletRequest request,
                             HttpServletResponse response) {
        try {
            String fileName = doc.getDocumentName();
            String root = System.getProperty("zjcclims.root");
            FileInputStream fis = new FileInputStream(root + doc.getDocumentUrl());
            response.setCharacterEncoding("utf-8");
            //解决上传中文文件时不能下载的问题
            response.setContentType("multipart/form-data;charset=UTF-8");
            if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");// firefox浏览器
            } else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                fileName = URLEncoder.encode(fileName, "UTF-8");// IE浏览器
            } else {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            }
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName.replaceAll(" ", ""));

            OutputStream fos = response.getOutputStream();
            byte[] buffer = new byte[8192];
            int count = 0;
            while ((count = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            fis.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /****************************************************************************
     * 功能：根据user对象和学院编号查询所有学生
     * 作者：贺子龙
     * 时间：2015-11-05
     ****************************************************************************/
    @Override
    public int findStudentByCnameAndUsername(User user, String academyNumber, Integer deviceId) {
        String sql = "select count(distinct u) from User u join u.authorities a where (a.id <> 4 and a.id <> 10)";
        sql += " and u.username not in (select p.user.username from LabRoomDevicePermitUsers p where p.labRoomDevice.id = " + deviceId + " )";
        LabRoomDevice device = findLabRoomDeviceByPrimaryKey(deviceId);
        if (device.getUser() != null && device.getUser().getUsername() != null) {
            sql += " and u.username <> '" + device.getUser().getUsername() + "'";
        }
        // 找到所有的实验室中心主任
        List<User> userList = shareService.findUsersByAuthorityId(4);
        for (User director : userList) {
            sql += " and u.username <> '" + director.getUsername() + "'";
        }
        if (academyNumber != null && !academyNumber.equals("")) {
            sql += " and u.schoolAcademy.academyNumber like '" + academyNumber + "%'";
        }
        if (user != null) {
            if (user.getCname() != null && !user.getCname().equals("")) {
                sql += " and u.cname like '%" + user.getCname() + "%'";
            }
            if (user.getUsername() != null && !user.getUsername().equals("")) {
                sql += " and u.username like '%" + user.getUsername() + "%'";
            }
        }
        // sql+=" and u.userRole<>1"; //可以有教师
        return ((Long) userDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
    }

    /****************************************************************************
     * 功能：根据user对象和学院编号查询所有学生
     * 作者：贺子龙
     * 时间：2015-11-05
     ****************************************************************************/
    @Override
    public List<User> findStudentByCnameAndUsername(User user, String academyNumber, Integer deviceId, Integer page, int pageSize) {
        String sql = "select distinct u from User u join u.authorities a where (a.id <> 4 and a.id <> 10)";
        sql += " and u.username not in (select p.user.username from LabRoomDevicePermitUsers p where p.labRoomDevice.id = " + deviceId + " )";
        LabRoomDevice device = findLabRoomDeviceByPrimaryKey(deviceId);
        if (device.getUser() != null && device.getUser().getUsername() != null) {
            sql += " and u.username <> '" + device.getUser().getUsername() + "'";
        }
        // 找到所有的实验室中心主任
        List<User> userList = shareService.findUsersByAuthorityId(4);
        for (User director : userList) {
            sql += " and u.username <> '" + director.getUsername() + "'";
        }

        if (academyNumber != null && !academyNumber.equals("")) {
            sql += " and u.schoolAcademy.academyNumber like '" + academyNumber + "%'";
        }
        if (user != null) {
            if (user.getCname() != null && !user.getCname().equals("")) {
                sql += " and u.cname like '%" + user.getCname() + "%'";
            }
            if (user.getUsername() != null && !user.getUsername().equals("")) {
                sql += " and u.username like '%" + user.getUsername() + "%'";
            }
        }
        //sql+=" and u.userRole<>1";  //可以有教师
        sql += "ORDER BY CASE WHEN u.schoolAcademy.academyNumber like '" + academyNumber + "%' THEN 0 ELSE 1 END";
        sql += " ,u.username desc";
        return userDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
    }


    /****************************************************************************
     * 功能：通过username和deviceId查询labRoomDevicePermitUser
     * 作者：贺子龙
     ****************************************************************************/
    public LabRoomDevicePermitUsers findPermitUserByUsernameAndDeivce(String username, int deviceId) {
        String sql = "select u from LabRoomDevicePermitUsers u where 1=1";
        if (username != null && !username.equals("")
                && deviceId != 0) {
            sql += " and u.user.username like '" + username + "'";
            sql += " and u.labRoomDevice.id =" + deviceId;
            List<LabRoomDevicePermitUsers> users = labRoomDevicePermitUsersDAO.executeQuery(sql, 0, -1);
            if (users.size() > 0) {
                return users.get(0);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /****************************************************************************
     * 功能：通过deviceId查询labRoomDevicePermitUser
     * 作者：贺子龙
     ****************************************************************************/
    public List<LabRoomDevicePermitUsers> findPermitUserByDeivceId(LabRoomDevicePermitUsers labRoomDevicePermitUsers, int deviceId, int page, int pageSize) {
        String sql = "select u from LabRoomDevicePermitUsers u where 1=1";
        if (deviceId != 0) {
            sql += " and u.labRoomDevice.id =" + deviceId;
            sql += " and u.flag <>1";//1--单独培训
            if (labRoomDevicePermitUsers.getUser() != null) {
                if (labRoomDevicePermitUsers.getUser().getCname() != null && !labRoomDevicePermitUsers.getUser().getCname().equals("")) {
                    sql += " and u.user.cname = '" + labRoomDevicePermitUsers.getUser().getCname() + "'";
                }
                if (labRoomDevicePermitUsers.getUser().getUsername() != null && !labRoomDevicePermitUsers.getUser().getUsername().equals("")) {
                    sql += " and u.user.username = '" + labRoomDevicePermitUsers.getUser().getUsername() + "'";
                }
            }
            List<LabRoomDevicePermitUsers> users = labRoomDevicePermitUsersDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
            if (users.size() > 0) {
                return users;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /****************************************************************************
     * 功能：删除labRoomDevicePermitUser
     * 作者：贺子龙
     ****************************************************************************/
    public void deletePermitUser(LabRoomDevicePermitUsers user) {
        labRoomDevicePermitUsersDAO.remove(user);
        labRoomDevicePermitUsersDAO.flush();
    }

    /****************************************************************************
     * 功能：通过主键查询labRoomDevicePermitUser
     * 作者：贺子龙
     ****************************************************************************/
    public LabRoomDevicePermitUsers findLabRoomDevicePermitUsersByPrimaryKey(int id) {
        LabRoomDevicePermitUsers user = labRoomDevicePermitUsersDAO.findLabRoomDevicePermitUsersByPrimaryKey(id);
        return user;
    }

    /****************************************************************************
     * 功能：更新某一设备下所有预约的审核结果
     * 作者：贺子龙
     * @throws ParseException
     ****************************************************************************/
    @SuppressWarnings("deprecation")
    public void updateReservationResult(int deviceId) throws ParseException {
        //id对应的设备
        //针对设置过预约审核时间的设备，删除已经过时了的预约
        LabRoomDevice device = labRoomDeviceDAO.findLabRoomDeviceByPrimaryKey(deviceId);

        //针对使用时间已过期的设备，予以审核取消
        if (device.getCDictionaryByAllowAppointment() != null && "1".equals(device.getCDictionaryByAllowAppointment().getCNumber()) &&
                device.getCDictionaryByIsAudit() != null && "1".equals(device.getCDictionaryByIsAudit().getCNumber())) {//需要审核
            Set<LabRoomDeviceReservation> reservations = device.getLabRoomDeviceReservations();
            for (LabRoomDeviceReservation labRoomDeviceReservation : reservations) {
                if (labRoomDeviceReservation.getCAuditResult() != null &&
                        labRoomDeviceReservation.getCAuditResult().getId() == 1) {//还没有审核结果
                    //获取当前时间
                    Calendar currTime = Calendar.getInstance();
                    //获取使用时间
                    Calendar beginTime = labRoomDeviceReservation.getBegintime();
                    if (currTime.after(beginTime)) {//当前时间已经超过使用时间
                        //设备预约拒绝
                        alterTimeAfterRefused(labRoomDeviceReservation, 2);
                    }
                }
            }
        }


        //判断设备允许预约、需要审核并且有预约审核时间限制
        if (device.getCDictionaryByAllowAppointment() != null && "1".equals(device.getCDictionaryByAllowAppointment().getCNumber()) &&
                device.getCDictionaryByIsAudit() != null && "1".equals(device.getCDictionaryByIsAudit().getCNumber()) &&
                device.getCDictionaryByTeacherAudit() != null && "1".equals(device.getCDictionaryByTeacherAudit().getCNumber()) &&//需要导师审核
                device.getIsAuditTimeLimit() != null && device.getIsAuditTimeLimit() == 1) {
            //获取审核限制时间
            int addHours = 48;//默认为48小时
            if (device.getAuditTimeLimit() != null && device.getAuditTimeLimit() != 0) {
                addHours = device.getAuditTimeLimit();
            }
            //获取当前时间
            Calendar currTime = Calendar.getInstance();

            Set<LabRoomDeviceReservation> reservations = device.getLabRoomDeviceReservations();
            for (LabRoomDeviceReservation labRoomDeviceReservation : reservations) {
                if (labRoomDeviceReservation.getCAuditResult() != null &&
                        labRoomDeviceReservation.getCAuditResult().getId() == 1) {//设备预约处于未审核状态
                    //获取该预约的创建时间
//            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Calendar reservationTime = labRoomDeviceReservation.getTime();
//                String dateStr = sdf.format(reservationTime.getTime());
//                System.out.println("before:"+dateStr);
                    Date date = reservationTime.getTime();
                    //加上审核限制时间
                    date.setHours(date.getHours() + addHours);
                    Calendar judgeTime = Calendar.getInstance();
                    judgeTime.setTime(date);
//                dateStr = sdf.format(reservationTime.getTime());
//                System.out.println("after:"+dateStr);
                    if (currTime.after(judgeTime)) {//当前时间已经超过限制时间
                        //设备预约拒绝
                        alterTimeAfterRefused(labRoomDeviceReservation, 1);

                	/*//创建一条审核意见
                	LabRoomDeviceReservationResult audit = new LabRoomDeviceReservationResult();
                	audit.setLabRoomDeviceReservation(labRoomDeviceReservation);
                	User user = new User();
                	int tag=0;
                	//根据对应的设备的审核流程情况，设置audit对应的用户
                	if (device.getCActiveByTeacherAudit()!=null&&device.getCActiveByTeacherAudit().getId()==1) {
                		//导师审核
						user = labRoomDeviceReservation.getUserByTeacher();
						tag=1;
					}else if (device.getCActiveByLabManagerAudit()!=null&&device.getCActiveByLabManagerAudit().getId()==1) {
						tag=2;
						//实验室管理员审核
						Set<LabRoomAdmin> admins=labRoomDeviceReservation.getLabRoomDevice().getLabRoom().getLabRoomAdmins();
						if (admins.size()>0) {
							for (LabRoomAdmin labRoomAdmin : admins) {
								if (labRoomAdmin.getUser().getUsername()!=null) {
									user=labRoomAdmin.getUser();break;
								}
							}
						}
					}else {
						//设备管理员审核
						tag=3;
						user = labRoomDeviceReservation.getLabRoomDevice().getUser();
					}
                	audit.setUser(user);
                	audit.setTag(tag);
                	//审核意见
            		String remark="";
            		remark+="未在规定时间内进行审核  审核人姓名："+user.getCname()+"      审核人工号："+user.getUsername();
            		audit.setRemark(remark);
            		CTrainingResult result=cTrainingResultDAO.findCTrainingResultByPrimaryKey(2);
            		audit.setCTrainingResult(result);
            		labRoomDeviceReservationResultDAO.store(audit);*/
                    }
                }
            }
        }
    }

    /****************************************************************************
     * 功能：设备预约审核拒绝后放开被预约掉的时间段，使其设备可以重新预约该时间段
     * 作者：贺子龙
     ****************************************************************************/
    public void alterTimeAfterRefused(LabRoomDeviceReservation reservation, int flag) throws ParseException {
        int resultId = -1;
        if (flag == 0) {//审核拒绝
            resultId = 3;
        } else if (flag == 1) {//审核取消--未在48小时内审核
            resultId = 4;
        } else if (flag == 3) {//排课冲突
            resultId = 6;
        } else {//审核取消--使用时间过期
            resultId = 5;
        }
        //CAuditResult r=cAuditResultDAO.findCAuditResultByPrimaryKey(resultId);
        CDictionary r = shareService.getCDictionaryByCategory("c_audit_result", String.valueOf(resultId));
        reservation.setCAuditResult(r);
        reservation.setStage(-1);
        reservation.setOriginalBegin(reservation.getBegintime());//在被赋值为1900-01-01之前，把本身的值保存在original中
        reservation.setOriginalEnd(reservation.getEndtime());
        String str = "1900-01-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(str);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        reservation.setBegintime(calendar);
        reservation.setEndtime(calendar);
        labRoomDeviceReservationDAO.store(reservation);
    }

    /****************************************************************************
     * 功能：根据设备编号查询实验室设备
     * 作者：贺子龙
     ****************************************************************************/
    public LabRoomDevice findLabRoomDeviceByDeviceNumber(String deviceNumber) {
        if (deviceNumber != null && !deviceNumber.equals("")) {
            String sql = "select d from LabRoomDevice d where 1=1";
            sql += " and d.schoolDevice.deviceNumber like '%" + deviceNumber + "%'";
            List<LabRoomDevice> devices = labRoomDeviceDAO.executeQuery(sql, 0, -1);
            if (devices.size() > 0) {
                return devices.get(0);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /****************************************************************************
     * 功能：找到当前用户的所有培训预约
     * 作者：贺子龙
     ****************************************************************************/
    public List<LabRoomDeviceTraining> findLabRoomDeviceTrainingByUser(Integer deviceId, Integer termId, int page, int pageSize) {
        // 获取当前用户
        User user = shareService.getUser();
        String sql = "";
        boolean isTeacher = true;
        if (shareService.getUserDetail().getUserRole().trim().equals("1")) {// 老师权限
            sql = "select t from LabRoomDeviceTraining t where 1=1 ";
        } else {
            sql = "select distinct t from LabRoomDeviceTraining t, LabRoomDeviceTrainingPeople lp where 1=1 ";
            sql += " and lp.labRoomDeviceTraining.id=t.id";
            isTeacher = false;
        }
        // 学期限制
        if (termId != null) {
            // do nothing
        } else {
            termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
        }
        sql += " and t.schoolTerm.id=" + termId;
        // 设备限制
        if (deviceId != null) {
            sql += " and t.labRoomDevice.id=" + deviceId;
        }
        // 用户限制
        if (isTeacher) {
            sql += " and t.user.username = '" + user.getUsername() + "'";
        } else {
            sql += " and lp.user.username = '" + user.getUsername() + "')";
        }
        sql += " order by t.time desc";
        return labRoomDeviceTrainingDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
    }

    /****************************************************************************
     * description：设备教学使用报表
     * author：郑昕茹
     ****************************************************************************/
    @SuppressWarnings("rawtypes")
    public List getListLabRoomDeviceUsageInAppointment(HttpServletRequest request, int page, int pageSize) {
        String sql = "select * from view_item_device_appointment v where 1=1";
        sql += " and v.week is not null";
        if (request.getParameter("deviceName") != null && !request.getParameter("deviceName").equals("")) {
            sql += " and device_name like '%" + request.getParameter("deviceName") + "%'";
        }
        if (request.getParameter("deviceNumber") != null && !request.getParameter("deviceNumber").equals("")) {
            sql += " and device_number like '%" + request.getParameter("deviceNumber") + "%'";
        }
        if (request.getParameter("courseName") != null && !request.getParameter("courseName").equals("")) {
            sql += " and courseName like '%" + request.getParameter("courseName") + "%'";
        }
        if (request.getParameter("itemName") != null && !request.getParameter("itemName").equals("")) {
            sql += " and itemName like '%" + request.getParameter("itemName") + "%'";
        }
        if (request.getParameter("teacherName") != null && !request.getParameter("teacherName").equals("")) {
            sql += " and cname like '%" + request.getParameter("teacherName") + "%'";
        }
        if (request.getParameter("term") != null && !request.getParameter("term").equals("")) {
            sql += " and termId = " + request.getParameter("term") + "";
        }
        Query query = entityManager.createNativeQuery(sql);
        if (pageSize != -1)
            query.setMaxResults(pageSize);
        query.setFirstResult(pageSize * (page - 1));
        // 获取list对象
        List<Object[]> list = query.getResultList();
        return list;
    }

    /****************************************************************************
     * @description：设备教学使用报表-找到所有被排课用到的设备
     * @author：郑昕茹
     ****************************************************************************/
    public List<TimetableLabRelatedDevice> getAllLabRoomDeviceUsageInAppointment() {
        String sql = "select l from TimetableLabRelatedDevice l where 1=1 ";
        sql += " group by labRoomDevice.id";
        return timetableLabRelatedDeviceDAO.executeQuery(sql, 0, -1);
    }

    /****************************************************************************
     * @description：设备教学使用报表-找到所有上课教师
     * @author：郑昕茹
     ****************************************************************************/
    public List<TimetableTeacherRelated> getAllTimetableRelatedTeachers() {
        String sql = "select l from TimetableTeacherRelated l where 1=1 ";
        sql += " group by user.username";
        return timetableTeacherRelatedDAO.executeQuery(sql, 0, -1);
    }

    /****************************************************************************
     * @description：设备教学使用报表-找到所有排课相关的实验项目
     * @author：郑昕茹
     ****************************************************************************/
    public List<TimetableItemRelated> getAllTimetableRelatedItems() {
        String sql = "select l from TimetableItemRelated l where 1=1 ";
        sql += " group by operationItem.id";
        return timetableItemRelatedDAO.executeQuery(sql, 0, -1);
    }

    /****************************************************************************
     * description：设备教学使用报表-找到所有的课程名称
     * author：郑昕茹
     ****************************************************************************/
    @SuppressWarnings("rawtypes")
    public List getAllCoursesInAppointment(HttpServletRequest request) {
        String sql = "select * from view_device_related_appointment v where 1=1";
        sql += " group by courseName";
        Query query = entityManager.createNativeQuery(sql);
        // 获取list对象
        List<Object[]> list = query.getResultList();
        return list;
    }

    /*************************************************************************************
     * @description：设备教学使用报表
     * @author：郑昕茹
     * @date：2016-10-25
     *************************************************************************************/
    @Override
    public void exportLabRoomDeviceUsageInAppointment(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Map> list = new ArrayList<Map>();
        List<Object[]> listLabRoomDeviceUsageInAppointments = this.getListLabRoomDeviceUsageInAppointment(request, 1, -1);
        //实验中心所在学院的实验室项目
        int i = 1;
        for (Object[] o : listLabRoomDeviceUsageInAppointments) {
            Map map = new HashMap();

            map.put("serial number", i);//序号
            i++;
            map.put("deviceName", o[2]);//设备名称
            map.put("courseName", o[12]);//课程名称
            map.put("itemName", o[10]);//项目
            map.put("weekday", o[4]);//星期
            map.put("classes", o[6]);//节次
            map.put("weeks", o[5]);//周次
            map.put("teachers", o[8]);//上课教师
            map.put("rooms", o[9]);//实验室
            map.put("times", o[14]);
            list.add(map);
        }
        String title = "设备教学使用表";
        String[] hearders = new String[]{"序号", "设备名称", "课程名称", "实验项目", "星期",
                "节次", "周次", "使用机时/小时", "上课教师", "实验室"};//表头数组
        String[] fields = new String[]{"serial number", "deviceName", "courseName", "itemName", "weekday",
                "classes", "weeks", "times", "teachers", "rooms"};
        TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders), fields);
        JsGridReportBase report = new JsGridReportBase(request, response);
        report.exportExcel(title, shareService.getUserDetail().getCname(), "", td);
    }

    /*************************************************************************************
     * @description：设备使用报表
     * @author：郑昕茹
     * @date：2016-10-25
     *************************************************************************************/
    @Override
    public void exportLabRoomDeviceUsage(HttpServletRequest request, HttpServletResponse response, LabRoomDeviceReservation reservation, String acno) throws Exception {
        List<Map> list = new ArrayList<Map>();
        List<LabRoomDeviceReservation> reservationList = this.findLabRoomDeviceReservationByDeviceNumberAndCid(reservation, request, null, 1, -1, 2, acno);
        //实验中心所在学院的实验室项目
        int i = 1;
        for (LabRoomDeviceReservation l : reservationList) {
            Map map = new HashMap();

            map.put("serial number", i);//序号
            i++;
            //预约设备
            if (l.getInnerDeviceName() == null) {
                map.put("deviceName", l.getLabRoomDevice().getSchoolDevice().getDeviceName() + "["
                        + l.getLabRoomDevice().getSchoolDevice().getDeviceNumber() + "]");
            } else {
                map.put("deviceName", l.getInnerDeviceName() + " 关联设备");
            }
            //申请人
            if (l.getUserByReserveUser() != null) {
                map.put("reserveUser", l.getUserByReserveUser().getCname());
            } else {
                map.put("reserveUser", "");
            }
            //课题组
            if (l.getResearchProject() != null) {
                map.put("researcgProject", l.getResearchProject().getName());
            } else {
                map.put("researcgProject", "");
            }
            //使用内容
            map.put("content", l.getContent());
            //使用机时
            map.put("reserveHours", l.getReserveHours().setScale(2, BigDecimal.ROUND_HALF_UP).toString());

            //日期
            if (l.getBegintime() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                map.put("date", sdf.format(l.getBegintime().getTime()));
            } else {
                map.put("date", "");
            }
            //使用时间
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String start = "", end = "";
            if (l.getBegintime() != null)
                start = sdf.format(l.getBegintime().getTime());
            if (l.getEndtime() != null) {
                end = sdf.format(l.getEndtime().getTime());
            }
            map.put("useTime", start + "-" + end);
            //设备管理员
            if (l.getLabRoomDevice().getUser() != null) {
                map.put("manager", l.getLabRoomDevice().getUser().getCname());
            } else {
                map.put("manager", "");
            }

            if (l.getLabRoomDevice() != null && l.getLabRoomDevice().getLabRoom() != null && l.getLabRoomDevice().getLabRoom().getLabRoomName() != null) {
                map.put("labRoomName", l.getLabRoomDevice().getLabRoom().getLabRoomName());
            } else {
                map.put("labRoomName", "");
            }
            //收费情况
            if (l.getLabRoomDevice() != null && l.getLabRoomDevice().getCDictionaryByDeviceCharge() != null) {
                String s = l.getLabRoomDevice().getCDictionaryByDeviceCharge().getCName() + "\n";
                if (l.getLabRoomDevice().getPrice() != null && l.getReserveHours() != null) {
                    s += "￥" + l.getLabRoomDevice().getPrice().multiply(l.getReserveHours()).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                } else {
                    s += "￥0";
                }
                map.put("fees", s);
            } else {
                map.put("fees", "");
            }
            list.add(map);
        }
        String title = "设备使用报表";
        String[] hearders = new String[]{"序号", "预约设备", "申请人", "课题组", "使用内容",
                "使用机时", "日期", "使用时间", "设备管理员", "实验室", "收费情况"};//表头数组
        String[] fields = new String[]{"serial number", "deviceName", "reserveUser", "researcgProject", "content",
                "reserveHours", "date", "useTime", "manager", "labRoomName", "fees"};
        TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders), fields);
        JsGridReportBase report = new JsGridReportBase(request, response);
        report.exportExcel(title, shareService.getUserDetail().getCname(), "", td);
    }

    /****************************************************************************
     * @description：课题组管理-查询到所有的课题组
     * @author：郑昕茹
     * @date:2016-11-06
     ****************************************************************************/
    public List<ResearchProject> findAllResearchProjects(ResearchProject researchProject, int currpage, int pageSize) {
        String sql = "select r from ResearchProject r where 1=1";
        if (researchProject != null && researchProject.getCode() != null && !researchProject.getCode().equals("")) {
            sql += " and code like '%" + researchProject.getCode() + "%'";
        }
        if (researchProject != null && researchProject.getName() != null && !researchProject.getName().equals("")) {
            sql += " and name like '%" + researchProject.getName() + "%'";
        }
        return researchProjectDAO.executeQuery(sql, pageSize * (currpage - 1), pageSize);
    }

    /****************************************************************************
     * @description：课题组管理-保存新建的课题组
     * @author：郑昕茹
     * @date:2016-11-06
     ****************************************************************************/
    public ResearchProject saveResearchProject(ResearchProject researchProject) {
        researchProject.setUserNum(researchProject.getUsers().size());
        return researchProjectDAO.store(researchProject);
    }

    /****************************************************************************
     * @description：课题组管理-删除课题组
     * @author：郑昕茹
     * @date:2016-11-06
     ****************************************************************************/
    public boolean deleteResearchProject(int id) {
        ResearchProject researchProject = researchProjectDAO.findResearchProjectByPrimaryKey(id);
        if (researchProject != null) {
            researchProjectDAO.remove(researchProject);
            researchProjectDAO.flush();
            return true;
        }
        return false;
    }

    /****************************************************************************
     * @description：课题组管理-根据主键找到课题组
     * @author：郑昕茹
     * @date:2016-11-06
     ****************************************************************************/
    public ResearchProject findResearchProjectByPrimaryKey(int id) {
        return researchProjectDAO.findResearchProjectByPrimaryKey(id);
    }

    /****************************************************************************
     * 功能：查出某一中心下的所有设备管理员
     * 作者：贺子龙
     ****************************************************************************/
    public Map<String, String> findDeviceManageCnamerByCid(String acno) {
        Map<String, String> userMap = new HashMap<String, String>();
        //创造一个空的labRoomDevice对象
        LabRoomDevice device = new LabRoomDevice();
        List<LabRoomDevice> devices = findAllLabRoomDeviceNew(device, "-1", 1, -1, 1);
        for (LabRoomDevice labRoomDevice : devices) {
            if (labRoomDevice.getUser() != null && labRoomDevice.getUser().getUsername() != null
                    && !labRoomDevice.getUser().getUsername().equals("")) {
                userMap.put(labRoomDevice.getUser().getUsername(),
                        labRoomDevice.getUser().getCname());
            }
        }
        return userMap;
    }

    /****************************************************************************
     * @description：课题组管理-找到不在该课题组下的所有用户
     * @author：郑昕茹
     * @date:2016-12-21
     ****************************************************************************/
    public List<User> findUserNotInThisResearch(User user, Integer researchId, int pageSize, int currpage) {
        String sql = "select u from User u where 1=1";
        sql += " and (u.researchProject is null or u.researchProject.id != " + researchId + ")";
        if (user != null && user.getUsername() != null) {
            sql += " and u.username like '%" + user.getUsername() + "%'";
        }
        if (user != null && user.getCname() != null) {
            sql += " and u.cname like '%" + user.getCname() + "%'";
        }
        return userDAO.executeQuery(sql, (currpage - 1) * pageSize, pageSize);
    }

    /****************************************************************************
     *Description：根据id查找实训室借用单
     *
     *@param:idDey 实训室借用单编号
     *@author：邵志峰
     *@date:2017-06-28
     ****************************************************************************/
    public LabRoomLending findLabRoomLendingById(Integer idKey) {
        return labRoomLendingDAO.findLabRoomLendingByPrimaryKey(idKey);
    }

    /****************************************************************************
     * 功能：查找所有可借用设备的条数
     * 作者：周志辉
     * 时间：2017-08-11
     ****************************************************************************/
    public int getAllLendableDevice(HttpServletRequest request) {
        String sql = "select count(u) from LabRoomDevice u where u.CDictionaryByDeviceStatus.id =592 and u.CDictionaryByAllowLending.id =621";
        String deviceName = request.getParameter("deviceName");
        if (deviceName != null && !deviceName.equals("")) {
            sql += " and (u.schoolDevice.deviceName like '%" + deviceName + "%' or u.labRoom.labRoomName like '%" + deviceName + "%')";
        }
        // 可视化页面直接跳转传参
        if(!"null".equals(request.getParameter("room_id")) && !"".equals(request.getParameter("room_id")) && request.getParameter("room_id")!=null) {
            sql += " and u.labRoom.id = "+ Integer.valueOf(request.getParameter("room_id"));
        }
        //申请单数目
        int t1 = ((Long) labRoomDeviceDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
        //审核数目
        return t1;
    }

    /****************************************************************************
     * 功能：查找所有可借用设备
     * 作者：周志辉
     * 时间：2017-08-11
     ****************************************************************************/
    public List<LabRoomDevice> findAllLendableDevice(LabRoomDevice labRoomDevice, String acno, int pageSize, int currpage, HttpServletRequest request) {
//		String sql = "select u from LabRoomDevice u where u.CDictionaryByAllowLending.id =621";
        String sql = "select u from LabRoomDevice u where (u.CDictionaryByDeviceStatus.CCategory like 'c_lab_room_device_status'and u.CDictionaryByDeviceStatus.CNumber='1' )" +
                "and (u.CDictionaryByAllowLending.CCategory like 'c_active' and u.CDictionaryByAllowLending.CNumber='1')";
//		if(labRoomDevice != null && labRoomDevice.getUsername() != null){
//			sql += " and u.username like '%"+labRoomDevice.getUsername()+"%'";
//		}
//		if(labRoomDevice != null && labRoomDevice.getCname()!= null){
//			sql += " and u.cname like '%"+labRoomDevice.getCname()+"%'";
//		}
        String deviceName = request.getParameter("deviceName");
        if (deviceName != null && !deviceName.equals("")) {
            //sql += " and u.schoolDevice.deviceName like '%" + deviceName + "%'";
            sql += " and (u.schoolDevice.deviceName like '%" + deviceName + "%' or u.labRoom.labRoomName like '%" + deviceName + "%')";
        }
        // 可视化页面直接跳转传参
        if(!"null".equals(request.getParameter("room_id")) && !"".equals(request.getParameter("room_id")) && request.getParameter("room_id")!=null) {
            sql += " and u.labRoom.id = "+ Integer.valueOf(request.getParameter("room_id"));
        }
        if(acno != null){
            sql += "and u.labRoom.labCenter.schoolAcademy.id = " + acno;
        }
        return labRoomDeviceDAO.executeQuery(sql, (currpage - 1) * pageSize, pageSize);
    }

    /****************************************************************************
     * 功能：根据主键查询物联硬件
     * 作者：周志辉
     ****************************************************************************/
    @Override
    public LabRoomAgent findLabRoomAgentByPrimaryKey(Integer id) {

        return labRoomAgentDAO.findLabRoomAgentByPrimaryKey(id);
    }

    /****************************************************************************
     * 找到当前中心下所有被预约设备（预约审核成功的）--对应的labRoom、manager等map信息
     * @author 贺子龙
     * 2016-07-18
     ***************************************************************************/
    public Map<String, String> findMapWithDeviceReservation(String acno, Integer term, String mapType, String termMulti) {
        Map<String, String> resultMap = new HashMap<String, String>();

        String selectString = "";
        // 判断需要返回map的对象类型
        if (mapType.equals("labRoom")) {// 需要返回labroom map
            selectString = "l.labRoom";
        } else if (mapType.equals("manager")) {
            selectString = "l.user";
        } else if (mapType.equals("deviceNumber")) {
            selectString = "l.schoolDevice";
        }

        String sql = "select distinct " + selectString + " from LabRoomDevice l, LabRoomDeviceReservation lr where 1=1";
        // 建立两个表的连接关系
        sql += " and lr.labRoomDevice.id = l.id";
        // 限制条件：审核通过的设备预约
        sql += " and lr.CDictionaryByCAuditResult.CNumber = '2'";
        // 限制中心
        //sql+=" and l.labRoom.labAnnex.labCenter.id = "+cid;
        // 限制学期
        if (term != null) {
            sql += " and lr.schoolTerm.id = " + term;
        }
        // 学期多选情况
        if (!EmptyUtil.isStringEmpty(termMulti)) {
            sql += " and lr.schoolTerm.id in (" + termMulti + ")";
        }
        if (mapType.equals("labRoom")) {// 需要返回labroom map
            List<LabRoom> rooms = labRoomDAO.executeQuery(sql, 0, -1);
            if (rooms != null && rooms.size() > 0) {
                for (LabRoom labRoom : rooms) {
                    resultMap.put(labRoom.getId() + "", labRoom.getLabRoomName());
                }
            }
        } else if (mapType.equals("manager")) {
            List<User> managers = userDAO.executeQuery(sql, 0, -1);
            if (managers != null && managers.size() > 0) {
                for (User user : managers) {
                    resultMap.put(user.getUsername(), user.getCname() + "[" + user.getUsername() + "]");
                }
            }
        } else if (mapType.equals("deviceNumber")) {
            List<SchoolDevice> devices = schoolDeviceDAO.executeQuery(sql, 0, -1);
            if (devices != null && devices.size() > 0) {
                for (SchoolDevice schoolDevice : devices) {
                    resultMap.put(schoolDevice.getDeviceNumber(), schoolDevice.getDeviceName() + "[" + schoolDevice.getDeviceNumber() + "]");
                }
            }
        }
        return resultMap;
    }

    /****************************************************************************
     * 功能：找到当前中心下所有被预约设备（预约审核成功的）--数量
     * @author 贺子龙
     ****************************************************************************/
    public int countLabRoomDeviceWithReservation(LabRoomDevice labRoomDevice, String acno, Integer term, String termMulti) {
        String sql = "select count(distinct l) from LabRoomDevice l, LabRoomDeviceReservation lr where 1=1";
        // 建立两个表的连接关系
        sql += " and lr.labRoomDevice.id = l.id";
        // 限制条件：审核通过的设备预约
        sql += " and lr.CDictionaryByCAuditResult.CNumber = '2'";
        // 限制中心
        //sql+=" and l.labRoom.labAnnex.labCenter.id = "+cid;
        // 限制学期
        if (term != null) {
            sql += " and lr.schoolTerm.id = " + term;
        }
        // 学期多选情况
        if (!EmptyUtil.isStringEmpty(termMulti)) {
            sql += " and lr.schoolTerm.id in (" + termMulti + ")";
        }
        // 限制实验室
        if (!EmptyUtil.isObjectEmpty(labRoomDevice.getLabRoom())
                && !EmptyUtil.isIntegerEmpty(labRoomDevice.getLabRoom().getId())) {
            sql += " and l.labRoom.id=" + labRoomDevice.getLabRoom().getId();
        }
        // 限制设备管理员
        if (!EmptyUtil.isObjectEmpty(labRoomDevice.getUser())
                && !EmptyUtil.isStringEmpty(labRoomDevice.getUser().getUsername())) {
            sql += " and l.user.username like '" + labRoomDevice.getUser().getUsername() + "'";
        }
        // 限制设备编号
        if (!EmptyUtil.isObjectEmpty(labRoomDevice.getSchoolDevice())
                && !EmptyUtil.isStringEmpty(labRoomDevice.getSchoolDevice().getDeviceNumber())) {
            sql += " and l.schoolDevice.deviceNumber like '" + labRoomDevice.getSchoolDevice().getDeviceNumber() + "'";
        }

        int count = ((Long) labRoomDeviceDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
        return count;
    }

    /****************************************************************************
     * 功能：找到当前中心下所有被预约设备（预约审核成功的）
     * 作者：贺子龙
     ****************************************************************************/
    public List<LabRoomDevice> findLabRoomDeviceWithReservation(LabRoomDevice labRoomDevice, String acno,
                                                                int currpage, int pageSize, Integer term, String termMulti) {

        String sql = "select distinct l from LabRoomDevice l, LabRoomDeviceReservation lr where 1=1";
        // 建立两个表的连接关系
        sql += " and lr.labRoomDevice.id = l.id";
        // 限制条件：审核通过的设备预约
        sql += " and lr.CDictionaryByCAuditResult.CNumber = '2'";
        // 限制中心
        //sql+=" and l.labRoom.labAnnex.labCenter.id = "+cid;
        // 限制学期
        if (term != null) {
            sql += " and lr.schoolTerm.id = " + term;
        }
        // 学期多选情况
        if (!EmptyUtil.isStringEmpty(termMulti)) {
            sql += " and lr.schoolTerm.id in (" + termMulti + ")";
        }
        // 限制实验室
        if (!EmptyUtil.isObjectEmpty(labRoomDevice.getLabRoom())
                && !EmptyUtil.isIntegerEmpty(labRoomDevice.getLabRoom().getId())) {
            sql += " and l.labRoom.id=" + labRoomDevice.getLabRoom().getId();
        }
        // 限制设备管理员
        if (!EmptyUtil.isObjectEmpty(labRoomDevice.getUser())
                && !EmptyUtil.isStringEmpty(labRoomDevice.getUser().getUsername())) {
            sql += " and l.user.username like '" + labRoomDevice.getUser().getUsername() + "'";
        }
        // 限制设备编号
        if (!EmptyUtil.isObjectEmpty(labRoomDevice.getSchoolDevice())
                && !EmptyUtil.isStringEmpty(labRoomDevice.getSchoolDevice().getDeviceNumber())) {
            sql += " and l.schoolDevice.deviceNumber like '" + labRoomDevice.getSchoolDevice().getDeviceNumber() + "'";
        }

        boolean isExport = false;
        if (pageSize == 1) {// 导出入口判断
            pageSize = -1;
            isExport = true;
        }

        List<LabRoomDevice> labRoomDevices = labRoomDeviceDAO.executeQuery(sql, (currpage - 1) * pageSize, pageSize);

			/* 计算实验室设备对应的学校设备的使用情况
			if (!isExport && pageSize==-1) {// 手动更新需要重新计算
				if (labRoomDevices!=null && labRoomDevices.size()>0) {
					for (LabRoomDevice device : labRoomDevices) {
						if (!EmptyUtil.isStringEmpty(termMulti)) {
							String[] termArray = termMulti.split(",");
							for (String termString : termArray) {
								// 计算对应设备的使用小时数，并保存。
								this.calculateHoursForSchoolDevice(device.getSchoolDevice().getDeviceNumber(), "("+termString+")");
							}
						}
					}
				}
			}else {// 其他情况需要翻页的时候去重新从子表中给主表赋值，但是导出不需要这一步。
				for (LabRoomDevice device : labRoomDevices) {
					schoolDeviceService.setUseToSchoolDevice(device.getSchoolDevice(), null, termMulti);
				}
			}*/

        return labRoomDevices;

    }

    /****************************************************************************
     * 得到设备的使用时间、使用次数、收取费用
     * @author 贺子龙
     * 2016-07-18
     ***************************************************************************/
    public void calculateHoursForSchoolDevice(String deviceNumber, String terms) {

        // LabRoomDeviceReservation和labRoomDevice联合查询，条件是设备编号和学期
        String sql = "select l from LabRoomDeviceReservation l where 1=1";
        sql += " and l.labRoomDevice.schoolDevice.deviceNumber like '" + deviceNumber + "'";
        sql += " and l.CDictionaryByCAuditResult.CNumber = '2'";


        if (!EmptyUtil.isStringEmpty(terms)) {
            sql += " and l.schoolTerm.id in " + terms;
        }

        List<LabRoomDeviceReservation> reservations = labRoomDeviceReservationDAO.executeQuery(sql, 0, -1);
        SchoolDevice schoolDevice = schoolDeviceDAO.findSchoolDeviceByPrimaryKey(deviceNumber);

        double useHours = 0.0;// 设备使用时长
        double price = 0.0;// 设备收取费用
        int useCount = 0;// 设备使用次数（预约成功的次数）
        if (reservations != null && reservations.size() > 0) {
            for (LabRoomDeviceReservation labRoomDeviceReservation : reservations) {

                if (labRoomDeviceReservation.getBegintime().after(Calendar.getInstance())) {
                    continue;
                }

                // 累计使用时长
                double useHour = this.getReserveHoursOfReservation(labRoomDeviceReservation).doubleValue();
                useHours += useHour;

                // 累计收取的费用
                // 判断收费类型(1计时 2计件 3计次 4记天)
                int chargeType = 0;
                if (!EmptyUtil.isObjectEmpty(labRoomDeviceReservation.getLabRoomDevice().getCDictionaryByDeviceCharge())) {
                    chargeType = labRoomDeviceReservation.getLabRoomDevice().getCDictionaryByDeviceCharge().getId();
                }
                // 如果没有计费类型，则无法统计收费情况

                if (chargeType != 0 && !EmptyUtil.isObjectEmpty(labRoomDeviceReservation.getLabRoomDevice().getPrice())) {
                    switch (chargeType) {
                        case 1:// 计时收费
                            price += useHour * labRoomDeviceReservation.getLabRoomDevice().getPrice().doubleValue();
                            break;
                        case 4:// 按天收费
                            price += (useHour / 24) * labRoomDeviceReservation.getLabRoomDevice().getPrice().doubleValue();
                        default:
                            price += labRoomDeviceReservation.getLabRoomDevice().getPrice().doubleValue();
                            break;
                    }
                }

                useCount++;
            }
        }

        // 使用时长
        BigDecimal userHourBD = new BigDecimal(useHours);
        // 收取费用
        BigDecimal priceBD = new BigDecimal(price);
        if (!EmptyUtil.isStringEmpty(terms)) {// 判空。
            int term = Integer.parseInt(terms.substring(1, terms.length() - 1));
            SchoolDeviceUse deviceUse = schoolDeviceService.findSchoolDeviceUseByNumberAndTerm(deviceNumber, term);
            if (EmptyUtil.isObjectEmpty(deviceUse)) {// 未有历史记录，则新建
                deviceUse = new SchoolDeviceUse();
                deviceUse.setSchoolDevice(schoolDevice);
                deviceUse.setTerm(term);
                deviceUse.setUseCount(useCount);
                deviceUse.setUseHours(userHourBD);
                deviceUse.setPrice(priceBD);
                schoolDeviceUseDAO.store(deviceUse);
            } else {// 有历史记录，则更新
                deviceUse.setUseCount(useCount);
                deviceUse.setUseHours(userHourBD);
                deviceUse.setPrice(priceBD);
                schoolDeviceUseDAO.store(deviceUse);
            }
        }
    }

    /****************************************************************************
     * @功能：用户所有设备借用申请数量
     * @作者：周志辉
     * @时间：2017-11-03
     ****************************************************************************/
    @Override
    public Integer getAllDeviceLendingApplyList(HttpServletRequest request) {
        User user = shareService.getUser();
        String sql = "select count(l)  from LabRoomDeviceLending l where 1=1";
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") == -1) {
            sql += " and l.userByLendingUser.username like '" + user.getUsername() + "'";
        }
        String deviceName = request.getParameter("deviceName");
        if (deviceName != null && !deviceName.equals("")) {
            sql += " and (l.labRoomDevice.schoolDevice.deviceName like '%" + deviceName 
            		+ "%' or l.labRoomDevice.schoolDevice.deviceNumber like '%"+ deviceName +"%')";
        }
        String lendBatch = request.getParameter("lendBatch");
        if (lendBatch != null && !lendBatch.equals("")) {
            sql += " and (l.lendBatch like '%"+ lendBatch +"%')";
        }
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        if (starttime != null && starttime.length() > 0 && endtime != null && endtime.length() > 0) {
            sql += " and l.lendingTime between '" + starttime + "' and '" + endtime + "' ";
        }
        return ((Long) labRoomDeviceLendingDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
    }

    /****************************************************************************
     * @功能：用户所有设备借用申请
     * @作者：周志辉
     * @时间：2017-11-03
     ****************************************************************************/
    @Override
    public List<LabRoomDeviceLending> getAllDeviceLendingApplyList(
            Integer page, int pageSize, HttpServletRequest request) {
        User user = shareService.getUser();
        String sql = "select l from LabRoomDeviceLending l where 1=1";
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") == -1) {
            sql += " and l.userByLendingUser.username like '" + user.getUsername() + "'";
        }
        String deviceName = request.getParameter("deviceName");
        if (deviceName != null && !deviceName.equals("")) {
        	sql += " and (l.labRoomDevice.schoolDevice.deviceName like '%" + deviceName 
            		+ "%' or l.labRoomDevice.schoolDevice.deviceNumber like '%"+ deviceName +"%')";
        }
        String lendBatch = request.getParameter("lendBatch");
        if (lendBatch != null && !lendBatch.equals("")) {
            sql += " and (l.lendBatch like '%"+ lendBatch +"%')";
        }
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        if (starttime != null && starttime.length() > 0 && endtime != null && endtime.length() > 0) {
            sql += " and l.lendingTime between '" + starttime + "' and '" + endtime + "' ";
        }
        sql += " order by l.lendBatch desc";
        return labRoomDeviceLendingDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
    }

    /****************************************************************************
     * @功能：机房设备适用记录
     * @作者：周志辉
     * @时间：2017-11-17
     ****************************************************************************/
    @Override
    public List<ViewUseOfLc> findAllallViewUseOfLcList(ViewUseOfLc viewUseOfLc, int pageSize, int currpage, HttpServletRequest request) {
        String sql = "select l from ViewUseOfLc l where 1=1";
        //机器名称
        if (viewUseOfLc.getMachinename() != null && !"".equals(viewUseOfLc.getMachinename())) {
            sql += " and l.machinename like '" + viewUseOfLc.getMachinename() + "'";
        }
        //实训室
        if (viewUseOfLc.getLabRoomName() != null && !"".equals(viewUseOfLc.getLabRoomName())) {
            sql += " and l.labRoomName like '" + viewUseOfLc.getLabRoomName() + "'";
        }
        //使用人
        if (viewUseOfLc.getUsername() != null && !"".equals(viewUseOfLc.getUsername())) {
            sql += " and l.username like '" + viewUseOfLc.getUsername() + "'";
        }
        //使用时间
        String begintime = request.getParameter("begintime");
        String endtime = request.getParameter("endtime");
        if (begintime != null && !"".equals(begintime)) {
            if (endtime != null && !"".equals(endtime)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM d HH:mm:ss 'CST' yyyy", Locale.ENGLISH);
                try {
                    String begintimes = sdf.format(sdf1.parse(begintime));
                    String endtimes = sdf.format(sdf1.parse(endtime));
                    sql += " and l.endtime between '" + begintimes + "' and '" + endtimes + "'";
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        //实训室管理员看自己所管理的是实训室
        Object selectedRole = request.getSession(false).getAttribute("selected_role");
        if(selectedRole != null && "ROLE_LABMANAGER".equals(selectedRole.toString())){
            String sql1 = "select r from LabRoomAdmin r where r.user.username = '"
                    + shareService.getUser().getUsername() + "'";
            List<LabRoomAdmin> labRoomAdmin = labRoomAdminDAO.executeQuery(sql1, 0, -1);
            if(labRoomAdmin.size() != 0){
                sql += (" and  (l.id in (select r.labRoom.id from LabRoomAdmin r where r.user.username = '"
                        + shareService.getUser().getUsername() + "'))");
            }
        }
        return viewUseOfLcDAO.executeQuery(sql, (currpage - 1) * pageSize, pageSize);
    }

    /****************************************************************************
     * @功能：所有机房电脑使用记录
     * @作者：张德冰
     * @时间：2017.03.09
     ****************************************************************************/
    @Override
    public List<ViewUseOfLc> findAllallViewUseOfLcListAll() {
        String sql = "select l from ViewUseOfLc l where 1=1";

        return viewUseOfLcDAO.executeQuery(sql, -1, -1);
    }

    /****************************************************************************
     * @功能：根据查询条件获取机房电脑使用记录
     * @作者：张德冰
     * @时间：2017.03.12
     ****************************************************************************/
    public List<ViewUseOfLc> findAllallViewUseOfLcListByLabRoomName(
            ViewUseOfLc viewUseOfLc, String labRoomName, HttpServletRequest request) {
        String sql = "select l from ViewUseOfLc l where 1=1";
        sql += " and l.labRoomName = '" + labRoomName + "'";
        //使用人
        if (viewUseOfLc.getUsername() != null && !"".equals(viewUseOfLc.getUsername())) {
            sql += " and l.username like '" + viewUseOfLc.getUsername() + "'";
        }
        //使用时间
        String begintime = request.getParameter("begintime");
        String endtime = request.getParameter("endtime");
        if (begintime != null && !"".equals(begintime)) {
            if (endtime != null && !"".equals(endtime)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM d HH:mm:ss 'CST' yyyy", Locale.ENGLISH);
                try {
                    String begintimes = sdf.format(sdf1.parse(begintime));
                    String endtimes = sdf.format(sdf1.parse(endtime));
                    sql += " and l.endtime between '" + begintimes + "' and '" + endtimes + "'";
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return viewUseOfLcDAO.executeQuery(sql, -1, -1);
    }

    /************************************************************
     * @功能：导出设备列表
     * @作者：张德冰
     * @时间：2018.03.13
     ************************************************************/
    public void exportLabRoomDevice(List<LabRoomDevice> labRoomDevices,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        //新建一个list集合
        List<Map> list = new ArrayList<Map>();
        for (LabRoomDevice labRoomDevice : labRoomDevices) {
            // 新建一个HashMap对象
            Map map = new HashMap();
            //实训室名称
            if (labRoomDevice.getLabRoom() != null && labRoomDevice.getLabRoom().getLabRoomName() != null) {
                map.put("labRoomName", labRoomDevice.getLabRoom().getLabRoomName());
            } else {
                map.put("labRoomName", "*");
            }
            //设备名称,编号
            if (labRoomDevice.getSchoolDevice() != null && labRoomDevice.getSchoolDevice().getDeviceName() != null) {
                map.put("deviceName", labRoomDevice.getSchoolDevice().getDeviceName());
                map.put("deviceNumber", labRoomDevice.getSchoolDevice().getDeviceNumber());
            } else {
                map.put("deviceName", "*");
                map.put("deviceNumber", "*");
            }
            //型号
            if (labRoomDevice.getSchoolDevice() != null && labRoomDevice.getSchoolDevice().getDevicePattern() != null) {
                map.put("pattern", labRoomDevice.getSchoolDevice().getDevicePattern());
            } else {
                map.put("pattern", "*");
            }
            //规格
            if (labRoomDevice.getSchoolDevice() != null && labRoomDevice.getSchoolDevice().getDeviceFormat() != null) {
                map.put("format", labRoomDevice.getSchoolDevice().getDeviceFormat());
            } else {
                map.put("format", "*");
            }
            //使用方向
            if (labRoomDevice.getSchoolDevice() != null && labRoomDevice.getSchoolDevice().getDeviceUseDirection() != null) {
                map.put("useDirection", labRoomDevice.getSchoolDevice().getDeviceUseDirection());
            } else {
                map.put("useDirection", "*");
            }
            //购买日期
            if (labRoomDevice.getSchoolDevice() != null && labRoomDevice.getSchoolDevice().getDeviceBuyDate() != null) {
                map.put("buyDate", labRoomDevice.getSchoolDevice().getDeviceBuyDate());
            } else {
                map.put("buyDate", "*");
            }
            //存放地点
            if (labRoomDevice.getSchoolDevice() != null && labRoomDevice.getSchoolDevice().getDeviceAddress() != null) {
                map.put("address", labRoomDevice.getSchoolDevice().getDeviceAddress());
            } else {
                map.put("address", "*");
            }
            //主要技术指标
            map.put("indicators", labRoomDevice.getIndicators());
            //国别
            if (labRoomDevice.getSchoolDevice() != null && labRoomDevice.getSchoolDevice().getDeviceCountry() != null) {
                map.put("country", labRoomDevice.getSchoolDevice().getDeviceCountry());
            } else {
                map.put("country", "*");
            }
            //价格
            if (labRoomDevice.getSchoolDevice() != null && labRoomDevice.getSchoolDevice().getDevicePrice() != null) {
                map.put("price", labRoomDevice.getSchoolDevice().getDevicePrice());
            } else {
                map.put("price", "*");
            }
            //设备入账日期
            if (labRoomDevice.getSchoolDevice() != null && labRoomDevice.getSchoolDevice().getDeviceAccountedDate() != null) {
                map.put("accountedDate", labRoomDevice.getSchoolDevice().getDeviceAccountedDate());
            } else {
                map.put("accountedDate", "*");
            }
            //设备供应商
            if (labRoomDevice.getSchoolDevice() != null && labRoomDevice.getSchoolDevice().getDeviceSupplier() != null) {
                map.put("supplier", labRoomDevice.getSchoolDevice().getDeviceSupplier());
            } else {
                map.put("supplier", "*");
            }
            //领用人
            if (labRoomDevice.getSchoolDevice() != null && labRoomDevice.getSchoolDevice().getUserByUserNumber() != null) {
                map.put("user", labRoomDevice.getSchoolDevice().getUserByUserNumber().getCname());
            } else {
                map.put("user", "*");
            }
            //保管人
            if (labRoomDevice.getSchoolDevice() != null && labRoomDevice.getSchoolDevice().getUserByKeepUser() != null) {
                map.put("keepUser", labRoomDevice.getSchoolDevice().getUserByKeepUser().getCname());
            } else {
                map.put("keepUser", "*");
            }
            //项目来源
            if (labRoomDevice.getSchoolDevice() != null && labRoomDevice.getSchoolDevice().getProjectSource() != null) {
                map.put("projectSource", labRoomDevice.getSchoolDevice().getProjectSource());
            } else {
                map.put("projectSource", "*");
            }
            //生产厂家
            if (labRoomDevice.getSchoolDevice() != null && labRoomDevice.getSchoolDevice().getManufacturer() != null) {
                map.put("manufacturer", labRoomDevice.getSchoolDevice().getManufacturer());
            } else {
                map.put("manufacturer", "*");
            }
            //设备管理员
            if (labRoomDevice.getUser() != null && labRoomDevice.getUser().getCname() != null) {
                map.put("userCname", labRoomDevice.getUser().getCname());
            } else {
                map.put("userCname", "*");
            }
            //序列号
            if (labRoomDevice.getUser() != null && labRoomDevice.getSchoolDevice().getSn() != null) {
                map.put("sn", labRoomDevice.getSchoolDevice().getSn());
            } else {
                map.put("sn", "*");
            }
            //资产类别
            if (labRoomDevice.getUser() != null && labRoomDevice.getSchoolDevice().getCategoryMain() != null) {
                map.put("categoryMain", labRoomDevice.getSchoolDevice().getCategoryMain());
            } else {
                map.put("categoryMain", "*");
            }
            //资产类型
            if (labRoomDevice.getUser() != null && labRoomDevice.getSchoolDevice().getCategoryType() != null) {
                map.put("categoryType", labRoomDevice.getSchoolDevice().getCategoryType());
            } else {
                map.put("categoryType", "*");
            }
            //状态
            if (labRoomDevice.getCDictionaryByDeviceStatus() != null && labRoomDevice.getCDictionaryByDeviceStatus().getCName() != null) {
                map.put("status", labRoomDevice.getCDictionaryByDeviceStatus().getCName());
            } else {
                map.put("status", "*");
            }
            //所在地点
            String str = labRoomDevice.getLabRoom().getSystemBuild().getSystemCampus().getCampusName() + labRoomDevice.getLabRoom().getSystemBuild().getBuildName();
            if(labRoomDevice.getLabRoom().getSystemRoom()!=null) {
                str += labRoomDevice.getLabRoom().getSystemRoom().getRoomName() + "(" + labRoomDevice.getLabRoom().getSystemRoom().getRoomNo() + ")";
            }

            map.put("place", str);

            list.add(map);
        }
        //给表设置名称
        String title = "实训室设备列表";
        //给表设置表头名
        String[] hearders = new String[]{"设备编号", "设备名称", "设备型号" , "设备规格", "使用方向" ,"购买日期", "存放地点","主要技术指标", "所在实训室", "所在地点","国别","价格","设备入账日期","设备供应商","领用人","保管人","设备管理员","项目来源","生产厂家", "序列号","资产类别","资产类型","状态"};
        //属性数组，写数据到excel时的顺序定位
        String[] fields = new String[]{"deviceNumber", "deviceName", "pattern", "format","useDirection","buyDate","address","indicators", "labRoomName", "place","country","price","accountedDate","supplier","user","keepUser","userCname","projectSource","manufacturer","sn","categoryMain","categoryType","status"};
        TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders), fields);
        JsGridReportBase report = new JsGridReportBase(request, response);
        report.exportToExcel(title, shareService.getUserDetail().getCname(), td);
    }

    /************************************************************
     * @功能：导入设备列表
     * @作者：张德冰
     * @时间：2018.03.15
     ************************************************************/
    public String[] importLabRoomDevice(String File) {
        Boolean isE2007 = false;
        int successNum = 0;
        int failNum = 0;
        String result = "";
        if (File.endsWith("xlsx")) {
            isE2007 = true;
        }
        //建立输入流
        try {
            //建立输入流
            InputStream input = new FileInputStream(File);
            Workbook wb = null;
            if (isE2007) {
                wb = new XSSFWorkbook(input);
            } else {
                wb = new HSSFWorkbook(input);
            }
            //获取第一个表单数据
            Sheet sheet = wb.getSheetAt(0);
            //获取第一个表单迭代器
            Iterator<Row> rows = sheet.rowIterator();
            Row rowContent = null;// 表头
            String deviceNumber = "";//设备编号
            String deviceName = "";//设备名称
            String devicePattern = "";//规格
            String indicators = "";//主要技术指标
            String labRoomName = "";//所在实训室
            String userCName = "";//设备管理员
            String status = "";//状态
            String place = "";//所在地点
            int a = 0;
            while (rows.hasNext()) {
                deviceNumber = "";//设备编号
                deviceName = "";//设备名称
                devicePattern = "";//规格
                indicators = "";//主要技术指标
                labRoomName = "";//所在实训室
                userCName = "";//设备管理员
                status = "";//状态
                place = "";//所在地点
                Row row = null;
                if (a == 0) {
                    rowContent = rows.next();
                    row = rows.next();
                    row = rows.next();
                    rowContent = sheet.getRow(3);
                    a = 1;
                }

                row = rows.next();
                //列数
                int column = 8;//sheet.getRow(0).getPhysicalNumberOfCells();
                //chName ="";//品名
                for (int k = 0; k < column; k++) {
                    if (row.getCell(k) != null) {
                        row.getCell(k).setCellType(Cell.CELL_TYPE_STRING);
                        String columnName = rowContent.getCell(k).getStringCellValue();
                        String content = row.getCell(k).getStringCellValue();
                        if (columnName.equals("设备编号")) {
                            deviceNumber = content;
                        }
                        if (columnName.equals("设备名称")) {
                            deviceName = content;
                        }
                        if (columnName.equals("规格")) {
                            devicePattern = content;
                        }
                        if (columnName.equals("主要技术指标")) {
                            indicators = content;
                        }
                        if (columnName.equals("所在实训室")) {
                            labRoomName = content;
                        }
                        if (columnName.equals("设备管理员")) {
                            userCName = content;
                        }
                        if (columnName.equals("状态")) {
                            status = content;
                        }
                        if (columnName.equals("所在地点")) {
                            place = content;
                        }
                    }
                }

                LabRoomDevice l = new LabRoomDevice();
                if (deviceNumber != null && !deviceNumber.equals("")) {
                    SchoolDevice schoolDevice = schoolDeviceService.findSchoolDeviceByPrimaryKey(deviceNumber);
                    if(schoolDevice != null) {
                        l.setSchoolDevice(schoolDevice);
                    }else{
                        failNum++;
                        result += "第" + a + "行的设备号" + deviceNumber + "不存在" + ";";
                        continue;
                    }
                }
                if (indicators != null && !indicators.equals("")) {
                    l.setIndicators(indicators);
                }
                if (labRoomName != null && !labRoomName.equals("")) {
                    LabRoom labRoom = labRoomService.findLabRoomByLabRoomName(labRoomName);
                    if(labRoom != null) {
                        l.setLabRoom(labRoom);
                    }else{
                        failNum++;
                        result += "第" + a + "行的实验室" + labRoomName + "不存在" + ";";
                        continue;
                    }
                }
                if (userCName != null && !userCName.equals("")) {
                    User user = shareService.findUserByCName(userCName);
                    if(user != null) {
                        l.setUser(user);
                    }else{
                        failNum++;
                        result += "第" + a + "行的用户" + userCName + "不存在" + ";";
                        continue;
                    }
                }
                if (status != null && !status.equals("")) {
                    CDictionary cDictionaryByDeviceStatus = shareService.findCDictionaryByCName(status);
                    if(cDictionaryByDeviceStatus != null) {
                        l.setCDictionaryByDeviceStatus(cDictionaryByDeviceStatus);
                    }else{
                        failNum++;
                        result += "第" + a + "行的状态" + status + "不正确" + ";";
                        continue;
                    }
                }
                labRoomDeviceDAO.store(l);
                successNum++;
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result += "文件未能打开";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result += "文件未能进行读取";
        }
        if(failNum == 0) {
            String[] returnStr = {"成功导入" + successNum + "条", "导入失败" + failNum + "条"};
            return returnStr;
        }else{
            String[] returnStr = {"成功导入" + successNum + "条", "导入失败" + failNum + "条", result};
            return returnStr;
        }

    }

    /****************************************************************************
     * @功能：查询实验室分室里的设备根据单价倒序
     * @作者：魏好
     * @时间：2018-1-12
     ****************************************************************************/
    public List<LabRoomDevice> getAllDeviceByLabRoomId(int labRoomId) {
        String sql = "select l from LabRoomDevice l where l.labRoom.id = " + labRoomId + " order by schoolDevice ";

        return labRoomDeviceDAO.executeQuery(sql);
    }

    /**
     * 查询所有已选设备信息count
     *
     * @param selectedDeviceStr
     * @return
     */
    @Override
    public Integer countSelectedDevice(String selectedDeviceStr) {
        Integer rs = 0;
        if (selectedDeviceStr != null && !"".equals(selectedDeviceStr)) {
            String sql = "select count(l) from LabRoomDevice l where l.id in (" + selectedDeviceStr + ")";
            rs = Integer.valueOf(labRoomDeviceDAO.executeQuerySingleResult(sql).toString());
        }


        return rs;
    }

    /**
     * 查询所有已选设备信息
     *
     * @param selectedDeviceStr
     * @param currpage
     * @param pageSize
     * @return
     */
    @Override
    public List<LabRoomDevice> getSelectedDevice(String selectedDeviceStr, Integer currpage, Integer pageSize) {
        List<LabRoomDevice> rs = new ArrayList<LabRoomDevice>();
        if (selectedDeviceStr != null && !"".equals(selectedDeviceStr)) {
            String sql = "select l from LabRoomDevice l where l.id in (" + selectedDeviceStr + ")";
            rs = labRoomDeviceDAO.executeQuery(sql, (currpage - 1) * pageSize, pageSize);
        }
        return rs;
    }
    
    /************************************************************
     * @功能：获取流水号
     * @作者：贺子龙
     * @时间：2018.04.17
     ************************************************************/
    @Override
    public String getDeviceLendingBatch(){
    	String lendingBatchString = "";
    	Calendar calendar = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	String dateStr = sdf.format(calendar.getTime());
    	String sql = "select count(distinct t.lendBatch) from LabRoomDeviceLending t where 1=1";
    	sql += " and t.lendingTime between '" + dateStr + "000000' and '" + dateStr + "235959' ";
    	int count = ((Long) labRoomDeviceLendingDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
    	lendingBatchString = dateStr + this.addZeroForNum(count+"", 3);
    	return lendingBatchString;
    }
    
    public String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        StringBuffer sb = null;
         while (strLen < strLength) {
               sb = new StringBuffer();
               sb.append("0").append(str);// 左补0
               str = sb.toString();
               strLen = str.length();
         }
        return str;
    }
    
    /************************************************************
     * @功能：根据流水号获取所有的借用
     * @作者：贺子龙
     * @时间：2018年4月24日
     ************************************************************/
    public List<LabRoomDeviceLending> getDeviceLendingByBatch(String lendBatch){
    	String sql = "select t from LabRoomDeviceLending t where 1=1 and t.lendBatch like '" + lendBatch + "'";
    	List<LabRoomDeviceLending> lendings = labRoomDeviceLendingDAO.executeQuery(sql);
    	return lendings;
    }
    
    /************************************************************
     * @功能：根据流水号获取所有的借用(已经审核拒绝的除外)
     * @作者：贺子龙
     * @时间：2018年4月24日
     ************************************************************/
    public List<LabRoomDeviceLending> getDeviceLendingByBatchWithoutReject(String lendBatch){
    	String sql = "select t from LabRoomDeviceLending t where 1=1 and t.lendBatch like '" + lendBatch + "'";
//    	// 不要把已经拒绝的放过来
//    	sql += " and t.CDictionary.CNumber !='2' " +
//    			"and t.CDictionary.CCategory='c_lending_status'";
    	List<LabRoomDeviceLending> lendings = labRoomDeviceLendingDAO.executeQuery(sql);
    	return lendings;
    }
    
    /************************************************************
     * @功能：根据结果和借用的id处理后续消息、状态等机制(系主任)
     * @作者：贺子龙
     * @时间：2018年4月27日
     ************************************************************/
    public void createAuditMessageByLendId(Integer id, String tag) throws NoSuchAlgorithmException, InterruptedException {
    	LabRoomDeviceLending lrdl = this.findDeviceLendingById(id);
        LabRoomDevice lrd = lrdl.getLabRoomDevice();
        if ("2".equals(tag)) {
            CDictionary cds = shareService.getCDictionaryByCategory("c_lab_room_device_status", "5");// 更新状态{借用中}
            lrd.setCDictionaryByDeviceStatus(cds);
            labRoomDeviceDAO.store(lrd);
            CDictionary cls = shareService.getCDictionaryByCategory("c_lending_status", "1");
            lrdl.setCDictionary(cls);
            lrdl.setStage(1);
            labRoomDeviceLendingDAO.store(lrdl);
        } else {
            CDictionary cls = shareService.getCDictionaryByCategory("c_lending_status", "2");
            CDictionary cds = shareService.getCDictionaryByCategory("c_lab_room_device_status", "1");
            lrd.setCDictionaryByDeviceStatus(cds);
            lrdl.setCDictionary(cls);
            lrdl.setStage(-1);
            labRoomDeviceLendingDAO.store(lrdl);
        }
    }
    
    /************************************************************
     * @功能：根据结果和借用的id处理后续消息、状态等机制(实训室管理员)
     * @作者：贺子龙
     * @时间：2018年4月27日
     ************************************************************/
    public void createAuditMessageByLendIdManager(Integer id, String tag) 
    		throws NoSuchAlgorithmException, InterruptedException{
    	LabRoomDeviceLending lrdl = this.findDeviceLendingById(id);
        LabRoomDevice lrd = lrdl.getLabRoomDevice();
        Set<LabRoomAdmin> admins = lrd.getLabRoom().getLabRoomAdmins();
        if ("2".equals(tag)) {
            CDictionary cds = shareService.getCDictionaryByCategory("c_lab_room_device_status", "5");
            lrd.setCDictionaryByDeviceStatus(cds);
            labRoomDeviceDAO.store(lrd);
            CDictionary cls = shareService.getCDictionaryByCategory("c_lending_status", "1");
            lrdl.setCDictionary(cls);
            lrdl.setStage(2);
            labRoomDeviceLendingDAO.store(lrdl);
        } else {
            CDictionary cls = shareService.getCDictionaryByCategory("c_lending_status", "2");
            CDictionary cds = shareService.getCDictionaryByCategory("c_lab_room_device_status", "1");
            lrd.setCDictionaryByDeviceStatus(cds);
            lrdl.setCDictionary(cls);
            lrdl.setStage(-2);
            labRoomDeviceLendingDAO.store(lrdl);
        }
    }
    
    /************************************************************
     * @功能：根据结果和借用的id处理后续消息、状态等机制(实训部主任)
     * @作者：贺子龙
     * @时间：2018年4月27日
     ************************************************************/
    public void createAuditMessageByLendIdHead(Integer id, String tag) 
    		throws NoSuchAlgorithmException, InterruptedException{
    	LabRoomDeviceLending lrdl = this.findDeviceLendingById(id);
        LabRoomDevice lrd = lrdl.getLabRoomDevice();
        if ("2".equals(tag)) {
            CDictionary cds = shareService.getCDictionaryByCategory("c_lab_room_device_status", "5");
            lrd.setCDictionaryByDeviceStatus(cds);
            labRoomDeviceDAO.store(lrd);
            CDictionary cls = shareService.getCDictionaryByCategory("c_lending_status", "1");
            lrdl.setCDictionary(cls);
            lrdl.setStage(3);
            labRoomDeviceLendingDAO.store(lrdl);
        } else {
            CDictionary cls = shareService.getCDictionaryByCategory("c_lending_status", "2");
            CDictionary cds = shareService.getCDictionaryByCategory("c_lab_room_device_status", "1");
            lrd.setCDictionaryByDeviceStatus(cds);
            lrdl.setCDictionary(cls);
            lrdl.setStage(-3);
            labRoomDeviceLendingDAO.store(lrdl);
        }
    }
    
    /************************************************************
     * @功能：根据设备名和借用预约条件查询设备列表
     * @作者：汪哲玮
     * @时间：2018.04.17
     ************************************************************/
	@Override
	public List<LabRoomDevice> getAllDeviceByLabRoomIdAndLabDevice(Integer id,
			String deviceName, int deviceLend, int deviceAppoint) {
		StringBuffer sql = new StringBuffer();
		sql.append("select l from LabRoomDevice l where l.labRoom.id = "+ id) ;
		if(deviceName!=null && !deviceName.equals("")){
		sql.append(" and l.schoolDevice.deviceName like '%"+deviceName+"%'");
		}
		if(deviceLend ==621){
		sql.append(" and l.CDictionaryByAllowLending.id ="+ deviceLend);
		}
		if(deviceAppoint==1){
		sql.append(" and l.CDictionaryByAllowAppointment.CNumber ='"+ deviceAppoint+"'");	
		}
		sql.append(" order by l.schoolDevice.devicePrice desc");

        return labRoomDeviceDAO.executeQuery(sql.toString());
	}
    /************************************************************
     * @功能：实验室与设备联动查询
     * @作者：廖文辉
     * @时间：2018.9.5
     ************************************************************/
    public String findLabRoomDevicesByLabRoom(String labRoom,HttpServletRequest request,String deviceNumber){
        String sql="select l from LabRoomDevice l where 1=1";
        if(labRoom != null && !"".equals(labRoom)) {
            sql += " and l.labRoom.id='" + labRoom + "'";
        }
        Map<String,String> labRoomDeviceMap=new HashMap<String,String>();
        List<LabRoomDevice> labRoomDevices = labRoomDeviceDAO.executeQuery(sql,0,-1);
        for(LabRoomDevice labRoomDevice : labRoomDevices){
            labRoomDeviceMap.put(labRoomDevice.getSchoolDevice().getDeviceNumber(),labRoomDevice.getSchoolDevice().getDeviceName()+"["+labRoomDevice.getSchoolDevice().getDeviceNumber()+"]");
        }
        String labRoomDevice="<option value='' selected>全部设备</option>";
        Iterator<Map.Entry<String,String>> it= labRoomDeviceMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String,String> entry=it.next();
            if(entry.getKey().equals(deviceNumber)) {
                labRoomDevice += "<option value='" + entry.getKey() + "' selected>" + entry.getValue() + "</option>";
            }else{
                labRoomDevice += "<option value='" + entry.getKey() + "' >" + entry.getValue() + "</option>";
            }

        }
        String labRoomDeviceValue=shareService.htmlEncode(labRoomDevice);
        return labRoomDeviceValue;
    }
    /************************************************************
     * @功能：查找labRoomDevice
     * @作者：廖文辉
     * @时间：2018.9.5
     ************************************************************/
    public List<LabRoomDevice> getLabRoomDevice(String acno){
        String sql ="select l from LabRoomDevice l where 1=1";
        if(acno!=null&&!acno.equals("-1")&&!"".equals(acno)) {
            sql += " and l.labRoom.schoolAcademy.academyNumber='" + acno + "'";
        }
        sql+=" group by l.schoolDevice.deviceNumber";
        List<LabRoomDevice>list=labRoomDeviceDAO.executeQuery(sql,0,-1);
        return list;
    }
    /****************************************************************************
     * @Description：实验室根据培训查询培训名单
     * @author：廖文辉
     * @date:2018-09-29
     ****************************************************************************/
    public List<LabRoomTrainingPeople> findLabRoomTrainingPeoplesByTrainingId(
            int id){
        String sql = "select p from LabRoomTrainingPeople p where p.LabRoomTraining.id=" + id;
        return labRoomTrainingPeopleDAO.executeQuery(sql, 0, -1);
    }

    /****************************************************************************
     * @Description：：根据培训id查询培训通过的人
     * @author：廖文辉
     * @date:2018-09-29
     ****************************************************************************/
    public List<LabRoomTrainingPeople> findPassLabRoomTrainingPeopleByTrainId(
            Integer id){
        String sql = "select p from LabRoomTrainingPeople p where 1=1 and p.CDictionary.CCategory='c_training_result' and p.CDictionary.CNumber ='1' and p.LabRoomTraining.id=" + id;
        return labRoomTrainingPeopleDAO.executeQuery(sql, 0, -1);
    }
    /****************************************************************************
     * @Description：：删除labRoomDevicePermitUser
     * @author：廖文辉
     * @date:2018-09-29
     ****************************************************************************/
    public void deleteLabRoomPermitUser(LabRoomPermitUser user){
        labRoomPermitUserDAO.remove(user);
        labRoomPermitUserDAO.flush();
    }

    /**
     * Description 设备借用消息发送提取
     * @param id 设备借用单id
     * @param tag 通过与否
     * @param stage 审核级别
     * @throws InterruptedException 消息发送异常
     * @throws NoSuchAlgorithmException 消息发送异常
     * @author 黄保钱 2019-1-2
     */
    @Override
    public void sendMessageForDeviceLending(Integer id, String tag, Integer stage) throws InterruptedException, NoSuchAlgorithmException {
        LabRoomDeviceLending lrdl = labRoomDeviceLendingDAO.findLabRoomDeviceLendingById(id);
        if ("2".equals(tag)) {
            if (1 == stage) {
                Set<LabRoomAdmin> admins = lrdl.getLabRoomDevice().getLabRoom().getLabRoomAdmins();
                Message message = new Message();
                for (LabRoomAdmin a : admins) {
                    if(a.getTypeId() == 1) {// 实验室管理员
                        //消息
                        message.setSendUser(shareService.getUserDetail().getCname());// 当前登录人
                        message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());// 当前登录人所在学院
                        message.setTitle(CommonConstantInterface.STR_LABROOMDEVICELENDING_TITLE+"审核");
                        message.setTage(2);
                        message.setContent("系主任审核通过，请<a onclick='changeMessage(this)' href='../device/auditLabRoomAdminDeviceLending?idKey=" + id + "'>审核</a>");
                        message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
                        message.setCreateTime(Calendar.getInstance());
                        shareService.sendMsg(a.getUser(), message);
                    }
                }
                message.setTitle(CommonConstantInterface.STR_LABROOMDEVICELENDING_TITLE+"申请");
                message.setContent("您借用" + lrdl.getLabRoomDevice().getSchoolDevice().getDeviceNumber() + lrdl.getLabRoomDevice().getSchoolDevice().getDeviceName() + "的申请已通过系主任审核");
                message.setTage(1);
                shareService.sendMsg(lrdl.getUserByLendingUser(), message);
            }
            if (2 == stage) {
                List<User> lrh = shareService.findAllLabRoomtHead();
                Message message = new Message();
                for (User ur : lrh) {
                    //消息
                    message.setSendUser(shareService.getUserDetail().getCname());// 当前登录人
                    message.setSendCparty(shareService.getUserDetail()
                            .getSchoolAcademy().getAcademyName());// 当前登录人所在学院
                    message.setTitle(CommonConstantInterface.STR_LABROOMDEVICELENDING_TITLE+"审核");
                    message.setTage(2);
                    message.setContent("实训室管理员审核通过，请<a onclick='changeMessage(this)' href='../device/auditLabRoomHeadDeviceLending?idKey=" + id + "'>审核</a>");
                    message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
                    message.setCreateTime(Calendar.getInstance());
                    shareService.sendMsg(ur, message);
                }
                message.setTitle(CommonConstantInterface.STR_LABROOMDEVICELENDING_TITLE+"申请");
                message.setContent("您借用" + lrdl.getLabRoomDevice().getSchoolDevice().getDeviceNumber() + lrdl.getLabRoomDevice().getSchoolDevice().getDeviceName() + "的申请已通过实训室管理员审核");
                message.setTage(1);
                shareService.sendMsg(lrdl.getUserByLendingUser(), message);
            }
            if(3 == stage){
                //给自己发送消息
                Message messageToStudent = new Message();
                messageToStudent.setSendUser(shareService.getUserDetail().getCname());// 当前登录人
                messageToStudent.setSendCparty(shareService.getUserDetail().getSchoolAcademy()
                        .getAcademyName());// 当前登录人所在学院
                messageToStudent.setTitle(CommonConstantInterface.STR_LABROOMDEVICELENDING_TITLE+"审核通过");
                messageToStudent.setTage(4);
                messageToStudent.setContent("<a onclick='changeMessage(this)' href='../device/auditDepartmentDeviceLending?idKey=" + id + "'>查看</a>");
                messageToStudent.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
                messageToStudent.setCreateTime(Calendar.getInstance());
                messageToStudent.setContent("您借用" + lrdl.getLabRoomDevice().getSchoolDevice().getDeviceNumber() + lrdl.getLabRoomDevice().getSchoolDevice().getDeviceName() + "的申请已通过审核");
                messageToStudent.setTage(4);
                shareService.sendMsg(lrdl.getUserByLendingUser(), messageToStudent);
            }
        } else {
            //给自己发送消息
            Message messageToStudent = new Message();
            messageToStudent.setSendUser(shareService.getUserDetail().getCname());// 当前登录人
            messageToStudent.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());// 当前登录人所在学院
            messageToStudent.setTitle(CommonConstantInterface.STR_LABROOMDEVICELENDING_TITLE+"审核不通过");
            messageToStudent.setTage(3);
            messageToStudent.setContent("<a onclick='changeMessage(this)' href='../device/auditDepartmentDeviceLending?idKey=" + id + "'>查看</a>");
            messageToStudent.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
            messageToStudent.setCreateTime(Calendar.getInstance());
            shareService.sendMsg(lrdl.getUserByLendingUser(), messageToStudent);
        }
    }

    /**
     * @Description 设备借用撤回备份
     * @author 张德冰
     * @data 2018-01-16
     */
    @Override
    public void revokeDeviceLend(LabRoomDeviceLending deviceLending){
        AuditRefuseBackup auditRefuseBackup = new AuditRefuseBackup();
        //储存审核信息
        String auditInfo = "";
        String auditContent = "";
        for(LabRoomDeviceLendingResult ldr:deviceLending.getLabRoomDeviceLendingResults()){
            auditInfo += ldr.getUser().getCname()+ldr.getCDictionary().getCName()+", ";
            auditContent += ldr.getRemark()+", ";

        }
        auditRefuseBackup.setAuditInfo(auditInfo);
        auditRefuseBackup.setAuditContent(auditContent);
        auditRefuseBackup = auditRefuseBackupDAO.store(auditRefuseBackup);
        //储存撤回的设备借用
        RefuseItemBackup refuseItemBackup = new RefuseItemBackup();
        //审核信息
        refuseItemBackup.setAuditRefuseBackup(auditRefuseBackup);
        //设备借用id
        refuseItemBackup.setBusinessId(deviceLending.getLabRoomDevice().getSchoolDevice().getDeviceNumber());
        //申请人
        refuseItemBackup.setCreator(deviceLending.getUserByLendingUser().getUsername());

        refuseItemBackup.setType("LabRoomDeviceLending");
        //设备名称
        refuseItemBackup.setLabRoomName(deviceLending.getLabRoomDevice().getSchoolDevice().getDeviceName());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String operationItems = "";
        operationItems += "借用时间： " + sdf.format(deviceLending.getLendingTime().getTime()) +
                "\n预计归还时间： " + sdf.format(deviceLending.getReturnTime().getTime()) +
                "\n借用原因： " + deviceLending.getContent();
        refuseItemBackup.setOperationItemName(operationItems);
        refuseItemBackupDAO.store(refuseItemBackup);
    }

    /**
     * @Description 查找设备借用作废
     * @author 张德冰
     * @data 2018-01-18
     */
    @Override
    public List<RefuseItemBackup> findRefuseItemBackupList(RefuseItemBackup refuseItemBackup,String type, Integer currpage, Integer pageSize){
        //查询语句
        StringBuilder sql = new StringBuilder("select distinct rib from RefuseItemBackup rib where 1=1");
        //设备借用作废
        sql.append("  and rib.type = '").append(type).append("'");
        //设备名称
        if(refuseItemBackup != null){
            if(refuseItemBackup.getLabRoomName() != null && !refuseItemBackup.getLabRoomName().equals("")) {
                sql.append(" and rib.labRoomName like '%").append(refuseItemBackup.getLabRoomName()).append("%' ");
            }
        }
        List<RefuseItemBackup> r = refuseItemBackupDAO.executeQuery(sql.toString(), (currpage-1)*pageSize, pageSize);
        return r;
    }

    /**
     * @Description 查找设备借用作废总记录
     * @author 张德冰
     * @data 2018-01-18
     */
    public Integer findRefuseItemBackupTotalRecords(RefuseItemBackup refuseItemBackup,String type){
        //查询语句
        StringBuilder sql = new StringBuilder("select count(distinct rib) from RefuseItemBackup rib where 1=1");
        //设备借用作废
        sql.append("  and rib.type = '").append(type).append("'");
        //设备名称
        if(refuseItemBackup != null){
            if(refuseItemBackup.getLabRoomName() != null && !refuseItemBackup.getLabRoomName().equals("")) {
                sql.append(" and rib.labRoomName like '%").append(refuseItemBackup.getLabRoomName()).append("%' ");
            }
        }
        Integer total = ((Long) refuseItemBackupDAO.executeQuerySingleResult(sql.toString())).intValue();
        return total;
    }

    /**
     * description 判断设备是否已经存在实验室中
     * @param device_number
     * @param lab_id
     * @return
     * @author 陈乐为 2019-3-26
     */
    public boolean ifLabRoomDeviceExist(String device_number, int lab_id) {
        String sql = "select count(ld) from LabRoomDevice ld where 1=1 ";
        sql += " and ld.labRoom.id="+lab_id;
        sql += " and ld.schoolDevice.deviceNumber='"+ device_number +"'";
        int total = ((Long) refuseItemBackupDAO.executeQuerySingleResult(sql)).intValue();
        if(total>0) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * Description 获取实验室的设备总价
     * @param lab_id
     * @return
     * @author 陈乐为 2019年4月29日
     */
    public BigDecimal getAgentPriceByLab(Integer lab_id) {
        BigDecimal price = new BigDecimal(0);
        StringBuffer hql = new StringBuffer("select sum(c.schoolDevice.devicePrice) from LabRoomDevice c where c.labRoom.id="+lab_id);
        price = (BigDecimal) labRoomDeviceDAO.createQuerySingleResult(hql.toString()).getResultList().get(0);

        return price;
    }


    /**
     * Description 保存系主任审核
     * @return
     * @author 顾延钊 2019年7月4日
     */
    @Override
    public void saveDeviceAudit(Integer idKey, Integer auditResult, String remark, String acno){
        LabRoomDeviceLending labRoomDeviceLending = findDeviceLendingById(idKey);
        LabRoomDevice labRoomDevice=labRoomDeviceLending.getLabRoomDevice();
        LabRoom labRoom=labRoomDevice.getLabRoom();
        //User createUser=shareService.getUserDetail();
        String businessType=pConfig.PROJECT_NAME+ "LabRoomDeviceLending" + labRoomDeviceLending.getLabRoomDevice().getLabRoom().getLabCenter().getSchoolAcademy().getAcademyNumber();
        String businessAppUid="";
        if (shareService.getSerialNumber(labRoomDeviceLending.getId().toString(),businessType)=="fail"){
            businessAppUid=labRoomDeviceLending.getId().toString();
        }else {
            businessAppUid=shareService.getSerialNumber(labRoomDeviceLending.getId().toString(),businessType);
        }
        //business=getBusinessLevels(business);
        String businessUid=labRoom.getId().toString();
        if(auditResult==1){
            User user=shareService.getUserDetail();
            System.out.println(user.getUsername());
            Map<String,String> params=new HashMap<>();
            params.put("businessType",businessType);
            params.put("businessAppUid",businessAppUid);
            params.put("businessUid","-1");
            params.put("result","pass");
            params.put("info",remark);
            params.put("username",user.getUsername());
            String s=HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/saveBusinessLevelAudit", params);
           /*JSONObject jsonObject5 = JSON.parseObject(s);
           JSONObject status = jsonObject5.getJSONObject("status");
           System.out.println(status);*/
           /*try {
               auditService.saveBusinessLevel(idKey.toString(),"-1","pass",remark,business,user.getUsername());
               *//*String s=auditService.getCurrAudit(idKey.toString(),business);
               String nextAuth=JSONObject.parseObject(s).getJSONArray("data").getJSONObject(0).getString("result");
               if ("pass".equals(nextAuth)){
                   labRoomDeviceLending.setStage(2);
               }else if (!"fail".equals(nextAuth)){
                   //List<User> users=findUsersBy
               }*//*
               //createAuditMessageByLendId(idKey, 2+"");
           }catch (Exception e){
               e.printStackTrace();
           }*/
        }else {
            User user=shareService.getUserDetail();
            Map<String,String> params=new HashMap<>();
            params.put("businessType",businessType);
            params.put("businessAppUid",businessAppUid);
            params.put("businessUid","-1");
            params.put("result","fail");
            params.put("info",remark);
            params.put("username",user.getUsername());
            HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/saveBusinessLevelAudit", params);

        }
        labRoomDeviceLendingDAO.store(labRoomDeviceLending);
    }
    /**
     * Description 提交设备借用申请
     * @return
     * @author 顾延钊 2019年7月4日
     */
    @Override
    public String submitDeviceLending(Integer id,String acno){
        LabRoomDeviceLending labRoomDeviceLending=labRoomDeviceLendingDAO.findLabRoomDeviceLendingById(id);
        String businessType=pConfig.PROJECT_NAME+ "LabRoomDeviceLending" + labRoomDeviceLending.getLabRoomDevice().getLabRoom().getLabCenter().getSchoolAcademy().getAcademyNumber();
        String businessAppUid=shareService.saveAuditSerialNumbers(labRoomDeviceLending.getId().toString(),businessType);
        /*String config="on,on,on";
        Map<String,String> params2=new HashMap<>();
        params2.put("businessUid",labRoomDeviceLending.getLabRoomDevice().getLabRoom().getId().toString());
        params2.put("config",config);
        params2.put("businessType",businessType);*/

        Map<String,String> params=new HashMap<>();
        params.put("businessUid","-1");
        params.put("businessType",businessType);
        params.put("businessAppUid",businessAppUid);
        String s = HttpClientUtil.doPost(pConfig.auditServerUrl+"audit/saveInitBusinessAuditStatus",params);
        return "success";

    }

}