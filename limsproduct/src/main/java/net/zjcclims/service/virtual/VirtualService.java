package net.zjcclims.service.virtual;

import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.VirtualImage;
import net.zjcclims.domain.VirtualImageReservation;
import net.zjcclims.vo.CourseSchedule;
import net.zjcclims.web.virtual.StartVirtualImageByCourseSchedules;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface VirtualService {
    /*************************************************************************************
     * Description:得到所有虚拟实验室
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public int getAllVirtualLabRoomCount(LabRoom labRoom);

    /*************************************************************************************
     * Description:得到所有虚拟实验室
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public List<LabRoom> getAllVirtualLabRoom(LabRoom labRoom, int currpage, int pageSize);

    /*************************************************************************************
     * Description:取得接口url(添加参数)
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public String getUrl(String url);

    /*************************************************************************************
     * Description:提交请求
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public String loadJson(String url);

    /*************************************************************************************
     * Description:post方式调用接口
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public String post(String urlStr, Map<String, String> params);

    /*************************************************************************************
     * Description:delete方式调用接口
     *
     * @author: 贺照易
     * @date: 2018/12/21
     *************************************************************************************/
    public String sendDelete(String urlStr, Map<String, String> params);

    /*************************************************************************************
     * Description:得到所有虚拟镜像数量
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public int getAllVirtualImageCount(VirtualImage virtualImage);

    /*************************************************************************************
     * Description:得到所有虚拟镜像
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public List<VirtualImage> getAllVirtualImage(VirtualImage virtualImage, int currpage, int pageSize);

    /*************************************************************************************
     * Description:得到可预约的虚拟镜像(已经关联到实验室)
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public List<VirtualImage> getVirtualImageNotNull(VirtualImage virtualImage, int currpage, int pageSize);

    /*************************************************************************************
     * Description:调用接口更新虚拟实验室
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public void updateVirtualLabRoom();

    /*************************************************************************************
     * Description:调用接口更新虚拟镜像
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public void updateVirtualImage();

    /*************************************************************************************
     * Description:直连Citrix更新虚拟镜像
     *
     * @author: 杨新蔚
     * @date: 2019/05/28
     *************************************************************************************/
    public String updateImageCitrix(HttpServletRequest request);

    /*************************************************************************************
     * Description:调用接口查看虚拟实验室下的镜像
     *
     * @author: 贺照易
     * @date: 2018/12/22
     *************************************************************************************/
    public List<Map> getLabRoomVirtualImage(String labNum);

    /*************************************************************************************
     * Description:调用接口查看虚拟实验室下的镜像数量
     *
     * @author: 贺照易
     * @date: 2018/12/29
     *************************************************************************************/
    public int getLabRoomAllVirtualImageCount(String labNum);

    /*************************************************************************************
     * Description:得到所有待添加的虚拟镜像数量
     *
     * @author: 贺照易
     * @date: 2018/12/23
     *************************************************************************************/
    public List<Map> getAlladdVirtualImage();

    /*************************************************************************************
     * Description:添加镜像至虚拟实验室-调用添加镜像接口
     *
     * @author: 贺照易
     * @date: 2018/12/20
     *************************************************************************************/
    public String addVirtualImage(String roomNum, String ImageId) throws ParseException;

    /*************************************************************************************
     * Description:从虚拟实验室移除镜像-调用移除镜像接口
     *
     * @author: 贺照易
     * @date: 2018/12/21
     *************************************************************************************/
    public String deleteVirtualImage(String roomNum, String ImageId) throws ParseException;

    /*************************************************************************************
     * Description:得到预约记录数量
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public int getAllVirtualImageReservationCount(VirtualImageReservation virtualImageReservation);

    /*************************************************************************************
     * Description:得到预约记录
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public List<VirtualImageReservation> getAllVirtualImageReservation(VirtualImageReservation virtualImageReservation, int currpage, int pageSize);

    /*************************************************************************************
     * Description:保存虚拟镜像预约-调用预约接口
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public String saveVirtualImageReservation(HttpServletRequest request) throws ParseException;

    /*************************************************************************************
     * Description:保存虚拟镜像预约-Citrix直连
     *
     * @author: 杨新蔚
     * @date: 2019/05/28
     *************************************************************************************/
    public String saveVirtualImageReservationCitrix(HttpServletRequest request) throws ParseException;

    /*************************************************************************************
     * Description:预约审核方法
     *
     * @author: 杨新蔚
     * @date: 2019/1/6
     *************************************************************************************/

    public List<VirtualImageReservation> findAllVirtualImageReservation(VirtualImageReservation virtualImageReservation, Integer page, int pageSize, int tage, int isaudit);

    /**
     * Description 获取实际审核状态
     *
     * @param state 现在的状态
     * @return 实际审核状态
     * @author 黄保钱 2018-08-24
     */
    public Integer getAuditNumber(VirtualImage virtualImage, Integer state);


    /*************************************************************************************
     * Description:调用登录接口-下载ica文件
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public String virtualLogin(Integer id, HttpServletRequest request, HttpServletResponse response);

    /*************************************************************************************
     * Description:调用登录接口(直连)-下载ica文件
     *
     * @author: 杨新蔚
     * @date: 2019/05/28
     *************************************************************************************/
    public String virtualLoginCitrix(Integer id, HttpServletRequest request, HttpServletResponse response);

    /*************************************************************************************
     * Description:查看虚拟镜像报表
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public List<Map<String, String>> virtualHistory();

    /*************************************************************************************
     * Description:虚拟镜像预约检查
     *
     * @author: 杨新蔚
     * @date: 2019/1/11
     *************************************************************************************/
    public String checkImage(HttpServletRequest request);

    /*************************************************************************************
     * Description: 保存该门课程下的所有已经启动的桌面ID（机器ID）
     *
     * @param courseID
     * @param virtualImageID
     * @param desktopIDs
     * @author 陈敬2019年3月13日
     *************************************************************************************/
    public void saveDesktopIDs(String courseID, String virtualImageID, String[] desktopIDs, String courseStartTime, String courseEndTime);

    /*************************************************************************************
     * Description: 获取未使用的虚拟桌面
     *
     * @param username
     * @param courseID
     * @param startDate
     * @param endDate
     * @return 未使用的虚拟桌面ID
     * @author 陈敬2019年3月15日
     *************************************************************************************/
    public String getNotUsedDesktopByID(String username, String courseID, String virtualImageID, String startDate, String endDate);

    /*************************************************************************************
     * Description: 按当天课表自动启动虚拟镜像
     *
     * @author 陈敬2019年3月13日
     *************************************************************************************/
    public void autoStartVirtualImageByCourseSchedules();

    /*************************************************************************************
     * Description: 获取今天的课程启动计划
     *
     * @return List
     * @author 陈敬2019年3月13日
     *************************************************************************************/
    public List<CourseSchedule> getTodayCourseSchedules();

    /************************************************************************************
     * Description: 生成回调地址
     *
     * @return 回调地址
     * @author 陈敬2019年3月13日
     ************************************************************************************/
    public String generateCallbackRUL() throws UnsupportedEncodingException;

    /*************************************************************************************
     * Description: 根据课程计划自动启动虚拟镜像
     *
     * @param courseScheduleList
     * @author 陈敬2019年3月13日
     *************************************************************************************/
    public void startVirtualImageByCourseSchedules(List<CourseSchedule> courseScheduleList) throws UnsupportedEncodingException;

    /*************************************************************************************
     * Description: 获取今天的虚拟镜像
     *
     * @author 陈敬2019年3月15日
     *************************************************************************************/
    public List<Object[]> getTodayVirtualImage();
}
