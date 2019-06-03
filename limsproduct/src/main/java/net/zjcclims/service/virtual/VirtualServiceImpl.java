package net.zjcclims.service.virtual;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabRoomAdminService;
import net.zjcclims.util.HttpClientUtil;
import net.zjcclims.vo.CourseSchedule;
import net.zjcclims.vo.virtual.VirtualImageReservationVO;
import net.zjcclims.web.common.PConfig;
import net.zjcclims.web.virtual.StartVirtualImageByCourseSchedules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("VirtualService")
@Transactional
public class VirtualServiceImpl implements VirtualService {

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static String path = "/upload/";
    private static String filenameTemp;
    @Autowired
    PConfig pConfig;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private LabRoomDAO labRoomDAO;
    @Autowired
    private ShareService shareService;
    @Autowired
    private VirtualImageDAO virtualImageDAO;
    @Autowired
    private VirtualImageReservationDAO virtualImageReservationDAO;
    @Autowired
    private LabRoomAdminDAO labRoomAdminDAO;
    @Autowired
    private MessageDAO messageDAO;
    @Autowired
    private LabRoomAdminService labRoomAdminService;
    @Value("${virtualCallBackUrl}")
    public String virtualCallBackUrl;

    /*************************************************************************************
     * Description:通过预约记录得到预约的镜像
     *
     * @author: 杨新蔚
     * @date: 2019/05/29
     *************************************************************************************/
    public VirtualImage getVirtualImageByVirtualImageReservationID(Integer virtualImageReservationID){
        String sql = "select v from VirtualImage v,VirtualImageReservation vir where v.id=vir.virtualImage and vir.id="+virtualImageReservationID;
        return virtualImageDAO.executeQuery(sql).size()>0?virtualImageDAO.executeQuery(sql).get(0):null;
    }



    /*************************************************************************************
     * Description:得到所有虚拟实验室数量
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    @Override
    public int getAllVirtualLabRoomCount(LabRoom labRoom) {
        String sql = "select count(l) from LabRoom l where l.isSimulation=1";
        return ((Long) labRoomDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
    }

    /*************************************************************************************
     * Description:得到所有虚拟实验室
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    @Override
    public List<LabRoom> getAllVirtualLabRoom(LabRoom labRoom, int currpage, int pageSize) {
        String sql = "select l from LabRoom l where l.isSimulation=1";
        return labRoomDAO.executeQuery(sql, (currpage - 1) * pageSize, pageSize);
    }

    /*************************************************************************************
     * Description:取得delete接口url(添加参数)
     *
     * @author: 贺照易
     * @date: 2018/12/29
     *************************************************************************************/
    public String getdeleteUrl(String url, String ImageId) {
        String token = "chinamcloud";
        long timestamp = System.currentTimeMillis();
        Random ra = new Random();
        int nonce = ra.nextInt(10) + 1;
        ArrayList<String> list = new ArrayList<String>();
        String str = "";
        list.add(token);
        list.add(timestamp + "");
        list.add(nonce + "");
        Collections.sort(list);
        str = list.get(0) + list.get(1) + list.get(2);
        System.out.println("str=" + str);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes());
            String signature = getFormattedText(messageDigest.digest());
            System.out.println("signature=" + signature);
            String realUrl = url + "?signature=" + signature + "&nonce=" + nonce + "&timestamp=" + timestamp + "&soft_id=" + ImageId;
            System.out.println("realUrl=" + realUrl);
            return realUrl;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*************************************************************************************
     * Description:取得接口url(添加参数)
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public String getUrl(String url) {
        String token = "chinamcloud";
        long timestamp = System.currentTimeMillis();
        Random ra = new Random();
        int nonce = ra.nextInt(10) + 1;
        ArrayList<String> list = new ArrayList<String>();
        String str = "";
        list.add(token);
        list.add(timestamp + "");
        list.add(nonce + "");
        Collections.sort(list);
        str = list.get(0) + list.get(1) + list.get(2);
        System.out.println("str=" + str);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes());
            String signature = getFormattedText(messageDigest.digest());
            System.out.println("signature=" + signature);
            String realUrl = url + "?signature=" + signature + "&nonce=" + nonce + "&timestamp=" + timestamp;
            System.out.println("realUrl=" + realUrl);
            return realUrl;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    /*************************************************************************************
     * Description:提交请求
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public String loadJson(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL urlObject = new URL(url);
            URLConnection uc = urlObject.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(), "UTF-8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine + "\r\n");
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    /*************************************************************************************
     * Description:post方式调用接口
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public String post(String urlStr, Map<String, String> params) {
        URL connect;
        StringBuffer data = new StringBuffer();
        try {
            connect = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) connect.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //conn.setRequestProperty("Cookie", cookiesAll.toString()); 设置cookie  若需要登录操作
            OutputStreamWriter paramout = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            String paramsStr = "";
            for (String param : params.keySet()) {
                paramsStr += "&" + param + "=" + params.get(param);
            }
            if (!paramsStr.isEmpty()) {
                paramsStr = paramsStr.substring(1);
            }
            paramout.write(paramsStr);
            paramout.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }

            paramout.close();
            reader.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data.toString();
    }

    /*************************************************************************************
     * Description:delete方式调用接口
     *
     * @author: 贺照易
     * @date: 2018/12/21
     *************************************************************************************/
    public String sendDelete(String urlStr, Map<String, String> params) {
        URL url = null;
        StringBuffer data = new StringBuffer();    //hzy返回值
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        HttpURLConnection httpCon = null;
        try {
            httpCon = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        httpCon.setDoOutput(true);
        httpCon.setRequestProperty(
                "Content-Type", "application/x-www-form-urlencoded");
        try {
            httpCon.setRequestMethod("DELETE");
        } catch (ProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            httpCon.connect();
            //修改将同步返回数据作为返回值
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    httpCon.getInputStream(), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }

            reader.close();
            //hzy返回
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /*return urlStr;*/
        return data.toString();    //返回

    }

    /*************************************************************************************
     * Description:get方式调用接口
     *
     * @author: 贺照易
     * @date: 2018/12/22
     *************************************************************************************/
    public String get(String urlStr, Map<String, String> params) {
        InputStream is = null;
        BufferedReader reader = null;
        String resultStr = "";
        try {
            String paramsStr = "";
            for (String param : params.keySet()) {
                paramsStr += "&" + param + "=" + params.get(param);
            }
            if (!paramsStr.isEmpty()) {
                paramsStr = paramsStr.substring(1);
                urlStr += "&" + paramsStr;
            }
            URL url = new URL(urlStr);
            HttpURLConnection httpCon = (HttpURLConnection) url
                    .openConnection();
            httpCon.setRequestMethod("GET");
            is = httpCon.getInputStream();

            /*reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));*/
            reader = new BufferedReader(new InputStreamReader(httpCon.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            resultStr = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (reader != null) reader.close();
                if (is != null) is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultStr;
    }

    /*************************************************************************************
     * Description:创建文件
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public boolean creatTxtFile(String name, HttpServletRequest request) throws IOException {
        //得到文件保存目录的真实路径
        String RealPathDir = request.getSession().getServletContext().getRealPath(path);
        //System.out.println("文件保存目录的真实路径:"+logoRealPathDir);
        //根据真实路径创建目录
        File saveFile = new File(RealPathDir);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        boolean flag = false;
        filenameTemp = saveFile + File.separator + name + ".ica";
        File filename = new File(filenameTemp);
        if (!filename.exists()) {
            filename.createNewFile();
            flag = true;
        }
        return flag;
    }

    /*************************************************************************************
     * Description:写入文件内容
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public String writeTxtFile(String newStr) throws IOException {
        // 先读取原有文件内容，然后进行写入操作
        boolean flag = false;
        String filein = newStr + "\r\n";
        String temp = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            // 文件路径
            File file = new File(filenameTemp);
            // 将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();

            // 保存该文件原有的内容
            for (int j = 1; (temp = br.readLine()) != null; j++) {
                buf = buf.append(temp);
                // System.getProperty("line.separator")
                // 行与行之间的分隔符 相当于“\n”
                buf = buf.append(System.getProperty("line.separator"));
            }
            buf.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            flag = true;
        } catch (IOException e1) {
            // TODO 自动生成 catch 块
            throw e1;
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return filenameTemp;
    }

    /*************************************************************************************
     * Description:下载文件
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public HttpServletResponse download(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            //response.setContentType("multipart/form-data;charset=UTF-8");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }


    /*************************************************************************************
     * Description:删除文件
     *
     * @author: 杨新蔚
     * @date: 2018/12/19
     *************************************************************************************/
    public void delFile(String filename) {
        File file = new File(filename);
        if (file.exists() && file.isFile())
            file.delete();
    }

    /*************************************************************************************
     * Description:保存虚拟实验室数据
     *
     * @author: 杨新蔚
     * @date: 2018/12/17
     *************************************************************************************/
    public LabRoom saveVirtualLabRoom(LabRoom lr) {
        String sql = "select l from LabRoom l where l.isSimulation=1 and l.labRoomNumber=" + lr.getLabRoomNumber();
        List<LabRoom> labRooms = labRoomDAO.executeQuery(sql);
        LabRoom labRoom = null;
        if (labRooms != null && labRooms.size() > 0) {
            labRoom = labRooms.get(0);
            labRoom.setLabRoomNumber(lr.getLabRoomNumber());
            labRoom.setLabRoomName(lr.getLabRoomName());
            labRoom.setLabRoomIntroduction(lr.getLabRoomIntroduction());
            labRoom = labRoomDAO.store(labRoom);
        } else {
            lr.setIsSimulation(1);
            labRoom = labRoomDAO.store(lr);
        }
        return labRoom;
    }


    /*************************************************************************************
     * Description:得到所有虚拟镜像数量
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    @Override
    public int getAllVirtualImageCount(VirtualImage virtualImage) {
        String sql = "select count(v) from VirtualImage v where 1=1 and v.enable=1";
        return ((Long) virtualImageDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
    }

    /*************************************************************************************
     * Description:得到所有虚拟镜像数量
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    @Override
    public List<VirtualImage> getAllVirtualImage(VirtualImage virtualImage, int currpage, int pageSize) {
        String sql = "select v from VirtualImage v where 1=1 and v.enable=1";
        return virtualImageDAO.executeQuery(sql, (currpage - 1) * pageSize, pageSize);
    }

    /*************************************************************************************
     * Description:得到可预约的虚拟镜像(已经关联到实验室)
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    @Override
    public List<VirtualImage> getVirtualImageNotNull(VirtualImage virtualImage, int currpage, int pageSize) {
        String sql = "select v from VirtualImage v where v.labRoom.id is not null";
        return virtualImageDAO.executeQuery(sql, (currpage - 1) * pageSize, pageSize);
    }


    public void saveVirtualImage(VirtualImage vi) {
        //String sql="delete VirtualImage vi where 1=1";
        //virtualImageDAO.executeQuery(sql);
        VirtualImage virtualImage = virtualImageDAO.findVirtualImageByPrimaryKey(vi.getId());
        if (virtualImage == null) {
            virtualImageDAO.store(vi);
        } else {
            virtualImage.setName(vi.getName());
            virtualImage.setHardwareSet(vi.getHardwareSet());
            virtualImage.setImageCode(vi.getImageCode());
            virtualImage.setProvider(vi.getProvider());
            virtualImage.setSetNote(vi.getSetNote());
            virtualImage.setEnable(vi.getEnable());
            virtualImageDAO.store(virtualImage);
        }
    }

    /*************************************************************************************
     * Description:得到预约记录数量
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public int getAllVirtualImageReservationCount(VirtualImageReservation virtualImageReservation) {
        String sql = "select count(l) from VirtualImageReservation l where 1=1";
        return ((Long) virtualImageReservationDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
    }

    /*************************************************************************************
     * Description:得到预约记录
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public List<VirtualImageReservation> getAllVirtualImageReservation(VirtualImageReservation virtualImageReservation, int currpage, int pageSize) {
        String sql = "select l from VirtualImageReservation l where 1=1";
        return virtualImageReservationDAO.executeQuery(sql, (currpage - 1) * pageSize, pageSize);
    }

    public int getVirtualImageReservationCount(VirtualImageReservation virtualImageReservation) {
        String sql = "select count(l) from VirtualImageReservation l where l.labRoom is not null";
        return ((Long) virtualImageReservationDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
    }


    public List<VirtualImageReservation> getVirtualImageReservation(VirtualImageReservation virtualImageReservation, int currpage, int pageSize) {
        String sql = "select l from VirtualImageReservation l where l.labRoom is not null";
        return virtualImageReservationDAO.executeQuery(sql, (currpage - 1) * pageSize, pageSize);
    }

    /*************************************************************************************
     * Description:调用接口更新虚拟实验室
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public void updateVirtualLabRoom() {
        String url = "http://10.2.39.41/v1/lab";
        String realUrl = getUrl(url);
        String json = loadJson(realUrl);
        String realJson = "[" + json + "]";
        //String realJson = "[{\"code\": 200, \"msg\": \"\\u6210\\u529f\", \"data\": [ { \"id\": 78, \"type_code\": \"Ad\", \"lab_code\": \"Ad\", \"lab_name\": \"\\u4e13\\u5c5e\\u6a21\\u5f0f\", \"lab_use\": 12, \"lab_url\": \"\", \"desktop_num\": 1, \"icon_file\": \"\", \"lab_note\": \"\\u4e13\\u5c5e\\u6a21\\u5f0f\", \"sort_order\": 1000, \"lab_soft\": [{ \"set_id\": 1, \"set_name\": \"nova\", \"set_code\": null, \"hardware_set\": \"instancetype-cybwlogpus4\", \"image_code\": \"image-qgtPoIjE\", \"provider\": null, \"set_note\": null, \"desktop_num\": 40 }] }, { \"id\": 79, \"type_code\": \"Ae\", \"lab_code\": \"Ae\", \"lab_name\": \"\\u8bfe\\u7a0b\\u6a21\\u5f0f\", \"lab_use\": 12, \"lab_url\": \"\", \"desktop_num\": 13, \"icon_file\": \"\", \"lab_note\": \"\\u8bfe\\u7a0b\\u6a21\\u5f0f\", \"sort_order\": 1000, \"lab_soft\": [] }, { \"id\": 81, \"type_code\": \"Aa\", \"lab_code\": \"Aa1\", \"lab_name\": \"\\u57fa\\u7840\\u975e\\u7f16\\u5305\", \"lab_use\": 11, \"lab_url\": \"\", \"desktop_num\": 1, \"icon_file\": \"\", \"lab_note\": \"\\u5305\\u542b\\u7d22\\u8d1dNOVA10\\uff0cAE\\uff0cEDIUS\\u5728\\u5185\\u7684\\u57fa\\u7840\\u975e\\u7f16\\u5de5\\u5177\\u96c6\\u3002\", \"sort_order\": 1000, \"lab_soft\": [] }, { \"id\": 85, \"type_code\": \"Ag\", \"lab_code\": \"Ag\", \"lab_name\": \"\\u9884\\u7ea6\\u6a21\\u5f0f\", \"lab_use\": 12, \"lab_url\": \"\", \"desktop_num\": 2, \"icon_file\": \"\", \"lab_note\": \"\\u9884\\u7ea6\\u6a21\\u5f0f\", \"sort_order\": 1000, \"lab_soft\": [] } ]}]";
        List<LabRoom> labRoomList = new ArrayList<>();
        System.out.println(realJson);
        try {
            JSONArray jsonArray = JSONArray.fromObject(realJson);
            for (int k = 0; k < jsonArray.size(); k++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(k);
                JSONArray jsonArray1 = jsonObject1.getJSONArray("data");
                for (int i = 0; i < jsonArray1.size(); i++) {
                    JSONObject jsonObject = jsonArray1.getJSONObject(i);
                    LabRoom labRoom = new LabRoom();
                    Set<VirtualImage> virtualImageSet = new HashSet<>();
                    // 赋值
                    labRoom.setLabRoomNumber(jsonObject.getString("id"));
                    labRoom.setLabRoomName(jsonObject.getString("lab_name"));
                    labRoom.setLabRoomIntroduction(jsonObject.getString("lab_note"));
                    // 软件子集合
                    labRoom = saveVirtualLabRoom(labRoom);
                    JSONArray jac = jsonObject.getJSONArray("lab_soft");
                    for (int j = 0; j < jac.size(); j++) {
                        JSONObject b = (JSONObject) jac.get(j);
                        VirtualImage vi = new VirtualImage();
                        // 赋值
                        vi.setId(b.get("set_id").toString());
                        vi.setName(b.get("set_name").toString());
                        //vi.set(b.get("set_code").toString());
                        vi.setHardwareSet(b.get("hardware_set").toString());
                        vi.setImageCode(b.get("image_code").toString());
                        vi.setProvider(b.get("provider").toString());
                        vi.setSetNote(b.get("set_note").toString());
                        vi.setLabRoom(labRoom);
                        virtualImageDAO.store(vi);
                    }
                }
                //mav.addObject("listVirtualLabRoom", lrList);
                // 保存虚拟实验室数据
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*************************************************************************************
     * Description:调用接口更新虚拟镜像
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public void updateVirtualImage() {
        String url = "http://10.2.39.41/v1/soft";
        String realUrl = getUrl(url);
        String json = loadJson(realUrl);
        String realJson = "[" + json + "]";
//		String realJson = "[{ \"code\": 200, \"msg\": \"\\u6210\\u529f\", \"data\": [ { \"set_id\": 1, \"set_name\": \"nova\", \"set_code\": null, \"hardware_set\": \"instancetype-cybwlogpus4\", \"image_code\": \"image-qgtPoIjE\", \"provider\": null, \"set_note\": null, \"desktop_num\": 40 } ]}]";
        System.out.println(realJson);
        try {
            JSONArray jsonArray = JSONArray.fromObject(realJson);
            for (int k = 0; k < jsonArray.size(); k++) {
                List<VirtualImage> virtualImageList = new ArrayList<VirtualImage>();
                JSONObject jsonObject1 = jsonArray.getJSONObject(k);
                String code = jsonObject1.getString("code");
                String data = jsonObject1.getString("msg");
                JSONArray jac = jsonObject1.getJSONArray("data");
                String imageIds = "(";
                for (int j = 0; j < jac.size(); j++) {
                    JSONObject b = (JSONObject) jac.get(j);
                    VirtualImage vi = new VirtualImage();
                    // 赋值
                    vi.setId(b.get("set_id").toString());
                    vi.setName(b.get("set_name").toString());
                    //vi.setSet_code(b.get("set_code").toString());
                    vi.setHardwareSet(b.get("hardware_set").toString());
                    vi.setImageCode(b.get("image_code").toString());
                    vi.setProvider(b.get("provider").toString());
                    vi.setSetNote(b.get("set_note").toString());
                    vi.setEnable(1);
                    saveVirtualImage(vi);
                    imageIds += vi.getId().toString() + ",";
                }
                imageIds = imageIds.substring(0, imageIds.length() - 1);
                imageIds += ")";
                StringBuffer hql2 = new StringBuffer("delete from virtual_image  where id not in " + imageIds);
                entityManager.createNativeQuery(hql2.toString()).executeUpdate();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*************************************************************************************
     * Description:直连Citrix更新虚拟镜像
     *
     * @author: 杨新蔚
     * @date: 2019/05/28
     *************************************************************************************/
    public String updateImageCitrix(HttpServletRequest request){
        Map<String,String> headers=new HashMap<>();
        headers.put("Content-Type","application/x-www-form-urlencoded");
        headers.put("X-Citrix-IsUsingHTTPS","No");
        try {
            //登录前接口获取cookie、csrftoken
            String getAuthMethods = HttpClientUtil.postWithoutCookie("http://10.2.47.30/Citrix/GVSUNWeb/Authentication/GetAuthMethods", null, headers);
            String login = HttpClientUtil.postWithCookie("http://10.2.47.30/Citrix/GVSUNWeb/ExplicitAuth/Login", null, headers);
            //登录接口调用
            Map<String,String> params=new HashMap<>();
            //学生域账号，user.getDomainAccount，临时用固定账号测试
            params.put("username","cmop\\user1");
            params.put("password","abc@123");
            params.put("saveCredentials","false");
            params.put("loginBtn","login");
            params.put("StateContext","");
            String loginAttempt = HttpClientUtil.postWithCookie("http://10.2.47.30/Citrix/GVSUNWeb/ExplicitAuth/LoginAttempt", params, headers);
            String list = HttpClientUtil.postWithCookie("http://10.2.47.30/Citrix/GVSUNWeb/Resources/List", null, headers);
            System.out.println(list);
            JSONObject jsonObject = JSONObject.fromObject(list);
            JSONArray jac = jsonObject.getJSONArray("resources");
            for (int i=0;i<jac.size();i++){
               JSONObject jsonObject1= (JSONObject)jac.get(i);
               if (jsonObject1.has("isdesktop")&&"true".equals(jsonObject1.getString("isdesktop"))){
                   VirtualImage vi = new VirtualImage();
                   // 赋值
                   // 临时保存citrix本地存储id
                   vi.setId(jsonObject1.get("id").toString());
                   vi.setName(jsonObject1.get("name").toString());
                   // 临时保存citrix镜像桌面启动url
                   vi.setHardwareSet(jsonObject1.get("launchurl").toString());
                   vi.setEnable(1);
                   saveVirtualImage(vi);
               }
            }
            //更新gft镜像，使用其他账号
            //登录前接口获取cookie、csrftoken
            String getAuthMethodsGFT = HttpClientUtil.postWithoutCookie("http://10.2.47.30/Citrix/GVSUNWeb/Authentication/GetAuthMethods", null, headers);
            String loginGFT = HttpClientUtil.postWithCookie("http://10.2.47.30/Citrix/GVSUNWeb/ExplicitAuth/Login", null, headers);
            //登录接口调用
            Map<String,String> paramsGFT=new HashMap<>();
            //学生域账号，user.getDomainAccount，临时用固定账号测试
            paramsGFT.put("username","cmop\\gft1");
            paramsGFT.put("password","abc@123");
            paramsGFT.put("saveCredentials","false");
            paramsGFT.put("loginBtn","login");
            paramsGFT.put("StateContext","");
            String loginAttemptGFT = HttpClientUtil.postWithCookie("http://10.2.47.30/Citrix/GVSUNWeb/ExplicitAuth/LoginAttempt", paramsGFT, headers);
            String listGFT = HttpClientUtil.postWithCookie("http://10.2.47.30/Citrix/GVSUNWeb/Resources/List", null, headers);
            System.out.println(listGFT);
            JSONObject jsonObjectGFT = JSONObject.fromObject(listGFT);
            JSONArray jacGFT = jsonObjectGFT.getJSONArray("resources");
            for (int i=0;i<jacGFT.size();i++){
                JSONObject jsonObject1= (JSONObject)jacGFT.get(i);
                if (jsonObject1.has("isdesktop")&&"true".equals(jsonObject1.getString("isdesktop"))){
                    VirtualImage vi = new VirtualImage();
                    // 赋值
                    // 临时保存citrix本地存储id
                    vi.setId(jsonObject1.get("id").toString());
                    vi.setName(jsonObject1.get("name").toString());
                    // 临时保存citrix镜像桌面启动url
                    vi.setHardwareSet(jsonObject1.get("launchurl").toString());
                    vi.setEnable(1);
                    saveVirtualImage(vi);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        //String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/saveInitBusinessAuditStatus", params);
        return "success";
    }


    /*************************************************************************************
     * Description:查看虚拟实验室下的镜像-调用查看实验室接口
     *
     * @author: 贺照易
     * @date: 2018/12/22
     *************************************************************************************/
    public List<Map> getLabRoomVirtualImage(String labNum) {
        //调用接口查询虚拟实验室
        String url = "http://10.2.39.41/v1/lab/" + labNum;
        Map<String, String> map = new HashMap();
        map.put("id", labNum);
        String realUrl = getUrl(url);
        String json = get(realUrl, map);   //get方式调用接口
        /*String realJson = "[" + json + "]";*/
        System.out.println(json);
        List<Map> list = new ArrayList<Map>();
        try {
            JSONObject jsonObject1 = JSONObject.fromObject(json);
            JSONObject jsonObject = jsonObject1.getJSONObject("data");
            JSONArray jac = jsonObject.getJSONArray("lab_soft");
            for (int j = 0; j < jac.size(); j++) {
                Map VirtualImagemap = new HashMap();
                JSONObject b = (JSONObject) jac.get(j);
                VirtualImagemap.put("id", b.get("set_id").toString());
                VirtualImagemap.put("name", b.get("set_name").toString());
                VirtualImagemap.put("hardwareSet", b.get("hardware_set").toString());
                VirtualImagemap.put("imageCode", b.get("image_code").toString());
                VirtualImagemap.put("provider", b.get("provider").toString());
                VirtualImagemap.put("setNote", b.get("set_note").toString());
                list.add(VirtualImagemap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /*************************************************************************************
     * Description:查看虚拟实验室下的镜像数量-调用查看实验室接口
     *
     * @author: 贺照易
     * @date: 2018/12/29
     *************************************************************************************/
    @Override
    public int getLabRoomAllVirtualImageCount(String labNum) {
        //调用接口查询虚拟实验室
        String url = "http://10.2.39.41/v1/lab/" + labNum;
        Map<String, String> map = new HashMap();
        map.put("id", labNum);
        String realUrl = getUrl(url);
        String json = get(realUrl, map);   //get方式调用接口
        /*String realJson = "[" + json + "]";*/
        System.out.println(json);
        List<Map> list = new ArrayList<Map>();
        try {
            JSONObject jsonObject1 = JSONObject.fromObject(json);
            JSONObject jsonObject = jsonObject1.getJSONObject("data");
            JSONArray jac = jsonObject.getJSONArray("lab_soft");
            for (int j = 0; j < jac.size(); j++) {
                Map VirtualImagemap = new HashMap();
                JSONObject b = (JSONObject) jac.get(j);
                VirtualImagemap.put("id", b.get("set_id").toString());
                VirtualImagemap.put("name", b.get("set_name").toString());
                VirtualImagemap.put("hardwareSet", b.get("hardware_set").toString());
                VirtualImagemap.put("imageCode", b.get("image_code").toString());
                VirtualImagemap.put("provider", b.get("provider").toString());
                VirtualImagemap.put("setNote", b.get("set_note").toString());
                list.add(VirtualImagemap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list.size();
    }

    /*************************************************************************************
     * Description:得到所有待添加的虚拟镜像-调用查询虚拟镜像列表接口
     *
     * @author: 贺照易
     * @date: 2018/12/29
     *************************************************************************************/
    @Override
    public List<Map> getAlladdVirtualImage() {
        String url = "http://10.2.39.41/v1/soft";
        String realUrl = getUrl(url);
        String json = loadJson(realUrl);
        String realJson = "[" + json + "]";
//		String realJson = "[{ \"code\": 200, \"msg\": \"\\u6210\\u529f\", \"data\": [ ]}]";
        System.out.println(realJson);
        List<Map> AllImageList = new ArrayList<Map>();
        try {
            JSONArray jsonArray = JSONArray.fromObject(realJson);
            for (int k = 0; k < jsonArray.size(); k++) {
                List<VirtualImage> virtualImageList = new ArrayList<VirtualImage>();
                JSONObject jsonObject1 = jsonArray.getJSONObject(k);
                String code = jsonObject1.getString("code");
                String data = jsonObject1.getString("msg");
                JSONArray jac = jsonObject1.getJSONArray("data");
                for (int j = 0; j < jac.size(); j++) {
                    Map AllImagemap = new HashMap();
                    JSONObject b = (JSONObject) jac.get(j);
                    // 赋值
                    AllImagemap.put("id", b.get("set_id").toString());
                    AllImagemap.put("name", b.get("set_name").toString());
                    AllImageList.add(AllImagemap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AllImageList;
    }


    /*************************************************************************************
     * Description:添加镜像至虚拟实验室-调用添加镜像接口
     *
     * @author: 贺照易
     * @date: 2018/12/20
     *************************************************************************************/
    public String addVirtualImage(String roomNum, String ImageId) throws ParseException {
        String status = "";
        try {
            //调用添加镜像接口
            String url = "http://10.2.39.41/v1/lab/" + roomNum + "/soft/";
            Map<String, String> map = new HashMap();
            map.put("soft_id", ImageId);
            String realUrl = getUrl(url);
            String json = post(realUrl, map);
            String realJson = "[" + json + "]";
            System.out.println(realJson);
            status = "interfaceSuccess";
            JSONObject jsonObject = JSONObject.fromObject(json);
            String code = jsonObject.getString("code");
            String msg = jsonObject.getString("msg");
            if ("成功".equals(msg)) {
                status = "success";
            }
        } catch (Exception e) {
            status = "fail";
        } finally {
            return status;
        }
    }

    /*************************************************************************************
     * Description:从虚拟实验室移除镜像-调用移除镜像接口
     *
     * @author: 贺照易
     * @date: 2018/12/21
     *************************************************************************************/
    public String deleteVirtualImage(String roomNum, String ImageId) throws ParseException {
        String status = "";
        try {
            //调用移除镜像接口
            String url = "http://10.2.39.41/v1/lab/" + roomNum + "/soft";
            Map<String, String> map = new HashMap();
            map.put("soft_id", ImageId);
            String realUrl = getdeleteUrl(url, ImageId);
            String json = sendDelete(realUrl, map);
            String realJson = "[" + json + "]";
            System.out.println(realJson);
            status = "interfaceSuccess";
            status = "success";
            JSONObject jsonObject = JSONObject.fromObject(json);
            String code = jsonObject.getString("code");
            String msg = jsonObject.getString("msg");
            if ("成功".equals(msg)) {
                status = "Success";
            }
        } catch (Exception e) {
            status = "fail";
        } finally {
            return status;
        }
    }

    /*************************************************************************************
     * Description:保存虚拟镜像预约-调用预约接口
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public String saveVirtualImageReservation(HttpServletRequest request) throws ParseException {
        VirtualImage virtualImage = virtualImageDAO.findVirtualImageByPrimaryKey(request.getParameter("VirtualImage"));
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = sdf.parse(startTime);
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(date1);
        Date date2 = sdf.parse(endTime);
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(date2);
        VirtualImageReservation virtualImageReservation = new VirtualImageReservation();
        virtualImageReservation.setVirtualImage(virtualImage.getId());
        virtualImageReservation.setVirtualImageName(virtualImage.getName());
        virtualImageReservation.setStartTime(calendarStart);
        virtualImageReservation.setEndTime(calendarEnd);
        virtualImageReservation.setCreateTime(Calendar.getInstance());
        virtualImageReservation.setUser(shareService.getUserDetail());
        virtualImageReservation.setRemarks(request.getParameter("remarks"));
        try {
            String url = "http://10.2.39.41/v1/courseDesk";
            Map<String, String> map = new HashMap();
            map.put("num", "1");
            map.put("soft_id", virtualImage.getId().toString());
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //Calendar start= virtualImageReservation.getStartTime();
            Calendar calendarStartNew = Calendar.getInstance();
            calendarStartNew.setTime(date1);
            calendarStartNew.add(Calendar.MINUTE, -5);
            //String startTime = sdf.format(calendarStart.getTime());
            String startTimeNew = sdf.format(calendarStartNew.getTime());
            //String endTime = sdf.format(virtualImageReservation.getEndTime().getTime());
            map.put("start_time", startTimeNew);
            map.put("end_time", endTime);
            String realUrl = getUrl(url);
            String json = post(realUrl, map);
            System.out.println(json);
            JSONObject jsonObjects = JSONObject.fromObject(json);
            String code = jsonObjects.getString("code");
            String msg = jsonObjects.getString("msg");
            String data = jsonObjects.getString("data");
            if ("课表桌面预约成功".equals(msg)) {
                JSONArray jsonArrays = JSONArray.fromObject(data);
                for (int k = 0; k < jsonArrays.size(); k++) {
                    JSONObject jsonObject9 = jsonArrays.getJSONObject(k);
                    String uid = jsonObject9.getString("id");
                    if (!"TODO".equals(uid)) {
                        virtualImageReservation.setMachineId(Integer.parseInt(uid));
                        virtualImageReservation.setAuditStage(6);
                    }
                }
            }
        } catch (Exception e) {
        } finally {
            virtualImageReservationDAO.store(virtualImageReservation);
        }


        //暂时引掉预约部分
		/*User user = shareService.getUser();
		//消息
		Message message = new Message();
		message.setSendUser(user.getCname());
		message.setSendCparty(user.getSchoolAcademy().getAcademyName());
		message.setCond(0);
		message.setTitle("虚拟镜像增加");
		*//*String content = "<a onclick='changeMessage(this)' href='../labRoomLending/checkButton?id=" + labReservation.getId() + "&tage=0&state=" + auditNumber + "&page=1'>审核</a>";
		message.setContent(content);*//*
		message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
		message.setCreateTime(Calendar.getInstance());
		//message.setTage(2);

		String businessType = "VirtualImageReservation";
		//demo
		Map<String, String> params = new HashMap<>();
		params.put("businessUid", "-1");
		params.put("businessType", pConfig.PROJECT_NAME + businessType);
		params.put("businessAppUid", virtualImageReservation.getId().toString());
		String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/saveInitBusinessAuditStatus", params);
		com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(s);
		String status = jsonObject.getString("status");
		if (!"success".equals(status)) {
			return "fail";
		}
		Map<String, String> params2 = new HashMap<>();
		params2.put("businessType", pConfig.PROJECT_NAME + businessType);
		params2.put("businessAppUid", virtualImageReservation.getId().toString());
		String s2 = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", params2);
		com.alibaba.fastjson.JSONObject jsonObject2 = JSON.parseObject(s2);
		String status2 = jsonObject2.getString("status");
		com.alibaba.fastjson.JSONArray jsonArray = jsonObject2.getJSONArray("data");
		com.alibaba.fastjson.JSONObject jsonObject3 = jsonArray.getJSONObject(0);
		Integer auditNumber = jsonObject3.getIntValue("level");
		String firstAuthName = jsonObject3.getString("result");
		if (auditNumber == 1 && !request.getSession().getAttribute("selected_role").equals("ROLE_STUDENT") && "TEACHER".equals(firstAuthName)) {
			Map<String, String> params3 = new HashMap<>();
			params3.put("businessType", pConfig.PROJECT_NAME + businessType);
			params3.put("businessAppUid", virtualImageReservation.getId().toString());
			params3.put("businessUid", virtualImage.getId().toString());
			params3.put("result", "pass");
			params3.put("info", "不是学生不需要导师审核");
			params3.put("username", "username");
			String s3 = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/saveBusinessLevelAudit", params3);
			com.alibaba.fastjson.JSONObject jsonObject4 = JSON.parseObject(s3);
			String status3 = jsonObject4.getString("status");
			if (status3.equals("fail")) {
				return status3;
			}
		}
		Map<String, String> params4 = new HashMap<>();
		params4.put("businessType", pConfig.PROJECT_NAME + businessType);
		params4.put("businessAppUid", virtualImageReservation.getId().toString());
		String s4 = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", params4);
		com.alibaba.fastjson.JSONObject jsonObject5 = JSON.parseObject(s4);
		String status4 = jsonObject5.getString("status");
		com.alibaba.fastjson.JSONArray jsonArray4 = jsonObject5.getJSONArray("data");
		com.alibaba.fastjson.JSONObject jsonObject6 = jsonArray4.getJSONObject(0);
		auditNumber = jsonObject6.getIntValue("level");
		firstAuthName = jsonObject6.getString("result");*/


        // 审核通过推数据
	/*	if(auditNumber == -1){
			TimetableAppointment timetableAppointment = new TimetableAppointment();
			timetableAppointment.setTimetableStyle(ConstantInterface.TIMETABLE_STYLE_LAB_RESERVATION);
			timetableAppointment.setCreatedDate(Calendar.getInstance());
			timetableAppointment.setUpdatedDate(Calendar.getInstance());
//            timetableAppointment.setCreatedBy(labReservation.getUser().getCname());
			timetableAppointment.setCreatedBy(virtualImageReservation.getUser().getUsername());
			timetableAppointment.setStatus(ConstantInterface.TIMETABLE_STATUS_PUBLIC);
			timetableAppointment.setEnabled(true);
			timetableAppointment.setWeekday(labReservation.getLabReservationTimeTables().iterator().next().getSchoolWeekday().getId());
			timetableAppointment.setGroups(-1);
			timetableAppointment.setGroupCount(labReservation.getNumber());
			timetableAppointment.setLabhours(-1);
			timetableAppointment.setConsumablesCosts(new BigDecimal(-1));
			timetableAppointment.setDeviceOrLab(2);
			timetableAppointment.setSchoolTerm(labReservation.getLabReservationTimeTables().iterator().next().getSchoolTerm());
			timetableAppointment = timetableAppointmentDAO.store(timetableAppointment);
			TimetableLabRelated tlr = new TimetableLabRelated();
			tlr.setLabRoom(labRoom);
			tlr.setTimetableAppointment(timetableAppointment);
			tlr = timetableLabRelatedDAO.store(tlr);
			Set<TimetableLabRelated> timetableLabRelateds = new HashSet<>();
			timetableLabRelateds.add(tlr);
			SimpleDateFormat compareSDF = new SimpleDateFormat("yyyyMMddHHmmss");
			Set<TimetableAppointmentSameNumber> timetableAppointmentSameNumbers = new HashSet<>();
			Integer week = 0;
			List<Integer> sections = new ArrayList<>();
			List<List<Integer>> results = new ArrayList<>();
			for(LabReservationTimeTable lrtt: labReservation.getLabReservationTimeTables()){
				week = Integer.parseInt(lrtt.getCDictionary().getCNumber());
				Integer section = labRoomLendingService.getSystemTimeByStartAndEnd(lrtt.getStartTime(), lrtt.getEndTime()).getSection();
				sections.add(section);
			}
			Collections.sort(sections);
			List<Integer> temp = new ArrayList<>();
			boolean flag = false;
			if(sections.size() == 1){
				temp.add(sections.get(0));
				results.add(temp);
			}else {
				for (int i = 0; i < sections.size() - 1; i++) {
					if (flag) {
						results.add(temp);
						temp = new ArrayList<>();
						flag = false;
					}
					temp.add(sections.get(i));
					if(sections.get(i+1) - sections.get(i) != 1){
						flag = true;
					}
				}
				if (flag) {
					results.add(temp);
					temp = new ArrayList<>();
					temp.add(sections.get(sections.size() - 1));
					results.add(temp);
				}else{
					temp.add(sections.get(sections.size() - 1));
					results.add(temp);
				}
			}
			for(List<Integer> integerList: results){
				TimetableAppointmentSameNumber tasn = new TimetableAppointmentSameNumber();
				tasn.setStartWeek(week);
				tasn.setEndWeek(week);
				tasn.setStartClass(integerList.get(0));
				tasn.setEndClass(integerList.get(integerList.size() - 1));
				tasn.setTimetableAppointment(timetableAppointment);
				tasn = timetableAppointmentSameNumberDAO.store(tasn);
				timetableAppointmentSameNumberDAO.flush();
				timetableAppointmentSameNumbers.add(tasn);
			}
			Set<TimetableTeacherRelated> timetableTeacherRelateds = new HashSet<>();
			TimetableTeacherRelated timetableTeacherRelated = new TimetableTeacherRelated();
			timetableTeacherRelated.setTimetableAppointment(timetableAppointment);
			timetableTeacherRelated.setUser(labReservation.getUser());
			timetableTeacherRelated = timetableTeacherRelatedDAO.store(timetableTeacherRelated);
			timetableTeacherRelateds.add(timetableTeacherRelated);
			timetableAppointment.setTimetableTeacherRelateds(timetableTeacherRelateds);
			timetableAppointment.setTimetableAppointmentSameNumbers(timetableAppointmentSameNumbers);
			timetableAppointment.setTimetableLabRelateds(timetableLabRelateds);
			timetableAppointmentDAO.flush();
			labReservation.setTimetableAppointment(timetableAppointment);
			labReservationDAO.store(labReservation);
		}*/
        //第一级审核人
	/*	switch (firstAuthName) {
			case "TEACHER":
				//virtualImageReservation.setTeacher(teacher);暂未处理
				sendMsg(null, message);
				break;
			case "CFO":
				List<User> deans = shareService.findDeansByAcademyNumber(user.getSchoolAcademy());
				for (User user2 : deans) {
					sendMsg(user2, message);
				}
				break;
			case "LABMANAGER":
				List<LabRoomAdmin> labRoomAdmins = labRoomAdminService.findAllLabRoomAdminsByLabRoomId(virtualImage.getLabRoom().getId());
				for (LabRoomAdmin labRoomAdmin : labRoomAdmins) {
					User user2 = labRoomAdmin.getUser();
					sendMsg(user2, message);
				}
				break;
			case "EXCENTERDIRECTOR":
				*//*sendMsg(virtualImage.getLabRoom().getLabCenter().getUserByCenterManager(), message);*//*
				break;
			case "PREEXTEACHING":
				List<User> labRoomMasters = shareService.findUsersByAuthorityName("PREEXTEACHING");
				for (User user2 : labRoomMasters) {
					sendMsg(user2, message);
				}
				break;
			case "pass":
				virtualImageReservation.setAuditStage(6);
				break;
			case "fail":
				virtualImageReservation.setAuditStage(0);
				break;
			default:
				List<User> auditUsers = shareService.findUsersByAuthorityName(firstAuthName);
				for (User user2 : auditUsers) {
					sendMsg(user2, message);
				}
		}
		message.setTitle("虚拟镜像预约成功");
		*//*content = "<a onclick='changeMessage(this)' href='../labRoomLending/checkButton?id=" + labReservation.getId() + "&tage=0&state=" + auditNumber + "&page=1'>查看</a>";
		message.setContent(content);*//*
         *//*message.setTage(1);*//*
		message.setUsername(user.getUsername());
		messageDAO.store(message);
		messageDAO.flush();*/
        return "success";
    }

    /*************************************************************************************
     * Description:保存虚拟镜像预约-Citrix直连
     *
     * @author: 杨新蔚
     * @date: 2019/05/28
     *************************************************************************************/
    public String saveVirtualImageReservationCitrix(HttpServletRequest request) throws ParseException{
    VirtualImage virtualImage = virtualImageDAO.findVirtualImageByPrimaryKey(request.getParameter("VirtualImage"));
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = sdf.parse(startTime);
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(date1);
        Calendar calendarEnd = Calendar.getInstance();
        if (EmptyUtil.isStringEmpty(endTime)){
            calendarEnd.setTime(date1);
            calendarEnd.add(Calendar.HOUR,3);
        }else{
            Date date2 = sdf.parse(endTime);
            calendarEnd.setTime(date2);
        }
        VirtualImageReservation virtualImageReservation = new VirtualImageReservation();
        virtualImageReservation.setVirtualImage(virtualImage.getId());
        virtualImageReservation.setVirtualImageName(virtualImage.getName());
        virtualImageReservation.setStartTime(calendarStart);
        virtualImageReservation.setEndTime(calendarEnd);
        virtualImageReservation.setCreateTime(Calendar.getInstance());
        virtualImageReservation.setUser(shareService.getUserDetail());
        virtualImageReservation.setRemarks(request.getParameter("remarks"));
        virtualImageReservation.setAuditStage(6);
        virtualImageReservation.setImageAccount(request.getParameter("imageAccount"));
        virtualImageReservation.setIsDownloadIca(0);
        virtualImageReservationDAO.store(virtualImageReservation);
        return "success";
    }

    /*************************************************************************************
     * Description:预约审核方法
     *
     * @author: 杨新蔚
     * @date: 2019/1/6
     *************************************************************************************/

    public List<VirtualImageReservationVO> findAllVirtualImageReservation(VirtualImageReservation virtualImageReservation, Integer page, int pageSize, int tage, int isaudit) {
        String sql = "select v from VirtualImageReservation v where 1=1 ";
        //暂未加查询
	/*if(virtualImageReservation.getLabRoom()!= null && labReservation.getLabRoom().getLabRoomName() != null){
			sql +=" and (l.labRoom.labRoomName like '%"+labReservation.getLabRoom().getLabRoomName()+"%'";
			sql +=" or l.labRoom.labRoomNumber like '%"+labReservation.getLabRoom().getLabRoomName()+"%')";
	}*/
        SimpleGrantedAuthority sga = (SimpleGrantedAuthority) SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next();
        //新加需求，是我的审核还是我的预约，isAudit  1审核2预约
        if (isaudit == 1) {
            //实训部主任、实训教训秘书	权限可以看到所有
            if (sga.getAuthority().indexOf("ROLE_PREEXTEACHING") != -1 || sga.getAuthority().indexOf("ROLE_PRESECTEACHING") != -1) {

            } else {
                // qun 判断 登陆者是不是超级管理员，实验教务 是的话下边权限不能进入
                int qun = 0;
                // shareService.getUser().getAuthorities().toString().indexOf(str)
                // 实验室超级管理员，实验教务,选择实验室中心的所有
                if (sga.getAuthority().indexOf("ROLE_SUPERADMIN") != -1
                        || sga.getAuthority()
                        .indexOf("ROLE_EXPERIMENTALTEACHING") != -1) {
                    qun++;
				/*String num = "";
				for (LabRoom iterable_element : labRoomDAO.findAllLabRooms()) {
					num += iterable_element.getId() + ",";
				}
				if (num != "") {
					sql += " and v.virtualImage.labRoom.id in (" + num.substring(0, num.length() - 1) + " ) ";
				}*/
                }
                // shi 判断登陆者 不是超级管理员，实验教务， 是不是实验室中心主任， 是的话下边权限不能进入
                int shi = 0;
                // 实验室中心主任，看到该中心下 自己实验室下边的实验室预约
                if (qun == 0
                        && (sga.getAuthority()
                        .indexOf("ROLE_EXCENTERDIRECTOR") != -1 || sga.getAuthority()
                        .indexOf("ROLE_DEPARTMENTHEADER") != -1 || sga.getAuthority()
                        .indexOf("ROLE_COLLEGELEADER") != -1 || sga.getAuthority()
                        .indexOf("ROLE_ASSETMANAGEMENT") != -1
                )) {
                    //ROLE_DEPARTMENTHEAD,ROLE_COLLEGELEADER,ROLE_ASSETMANAGEMENT为贺子龙  2015-11-28  新增
                    shi++;
                    String wq = "";
                    for (LabCenter iter : shareService.getUser().getLabCentersForCenterManager()) {
                        for (LabRoom ite : iter.getLabRooms()) {
                            wq += ite.getId() + ",";
                        }
                    }
//				if (wq != "") {
//					sql += " and v.virtualImage.labRoom.id in (" + wq.substring(0, wq.length() - 1) + " ) ";
//				}
                }
                // guan 判断登陆者 不是超级管理员，实验教务 不是实验室中心主任，是不是实验室管理员 是的话下边权限不能进入
                int guan = 0;
                // 实验室管理员
			/*if (qun == 0
					&& shi == 0
					&& sga.getAuthority()
					.indexOf("ROLE_LABMANAGER") != -1) {
				guan++;
				String sql1 = "select r from LabRoomAdmin r where r.user.username like '"
						+ shareService.getUser().getUsername() + "'";
				List<LabRoomAdmin> labRoomAdmin = labRoomAdminDAO.executeQuery(sql1, 0, 3);
				if (labRoomAdmin.size() > 0) {
					sql += " and v.virtualImage.labRoom.id in (select r.labRoom.id from LabRoomAdmin r where r.user.username like '"
							+ shareService.getUser().getUsername() + "')";
				}
			}*/
                // 判断登陆者 不是超级管理员，实验教务, 不是实验室中心主任，是不是实验室管理员 是的话下边权限不能进入
                // System.out.println("---qun--"+qun+"---shi---"+shi+"---guan---"+guan);

                // 老师和学生sga.getAuthority().indexOf("ROLE_STUDENT")
                // != -1 ||
                // sga.getAuthority().indexOf("ROLE_TEACHER")
                // != -1
                // xi 判断登陆者 不是超级管理员，实验教务 不是实验室中心主任，不是实验室管理员 ,是不是系主任，是的话下边权限不能进入
                int xi = 0;
                if (qun == 0 && shi == 0 && guan == 0 && (sga.getAuthority()
                        .indexOf("ROLE_CFO") != -1 || SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
                        .indexOf("ROLE_ASSISTANT") != -1)) {
                    xi++;
                    if (shareService.getUser().getSchoolAcademy() != null) {
                        sql += " and v.user.schoolAcademy.academyNumber='" + shareService.getUser().getSchoolAcademy().getAcademyNumber() + "' ";
                    } else {
                        sql += " and v.id = -1";
                    }
                }
                // 判断登陆者 不是超级管理员，实验教务 不是实验室中心主任，不是实验室管理员 ,不是系主任
			/*if (qun == 0 && shi == 0 && guan == 0 && xi ==0 && !sga.getAuthority().contains("ROLE_TEACHER")) {
				sql += " and l.id = -1";
			}else if(sga.getAuthority().contains("ROLE_TEACHER")){
				sql += " and l.teacher = '" + shareService.getUserDetail().getUsername() + "'";
			}*/
            }
        } else {
            sql += " and v.user.username='" + shareService.getUser().getUsername() + "' ";
        }

        // 1审核通过
        if (tage == 1) {
            sql += " and v.auditResults=1";
        }
        // 2审核中
        if (tage == 2) {
            sql += " and v.auditResults=2";
        }
        // 3未审核
        if (tage == 3) {
            sql += " and v.auditResults=3";
        }
        // 4审核拒绝
        if (tage == 4) {
            sql += " and v.auditResults=4";
        }
        sql += " order by v.id desc";
        List<VirtualImageReservation> virtualImageReservations = virtualImageReservationDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
        List<VirtualImageReservationVO> virtualImageReservationVOS =new ArrayList<>();
        for (VirtualImageReservation v:virtualImageReservations){
            VirtualImage virtualImage = getVirtualImageByVirtualImageReservationID(v.getId());
            VirtualImageReservationVO virtualImageReservationVO=new VirtualImageReservationVO();
            virtualImageReservationVO.setVirtualImageReservationID(v.getId());
            virtualImageReservationVO.setVirtualImageName(virtualImage!=null?virtualImage.getName():"");
            virtualImageReservationVO.setStartTime(v.getStartTime());
            Date date =v.getStartTime().getTime();
            Calendar c=Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.MINUTE, 15);
            virtualImageReservationVO.setStartFifteenTime(c);
            virtualImageReservationVO.setEndTime(v.getEndTime());
            virtualImageReservationVO.setRemarks(v.getRemarks());
            virtualImageReservationVO.setUserName(v.getUser().getCname());
            virtualImageReservationVO.setAuditStage(v.getAuditStage());
            virtualImageReservationVO.setImageAccount(v.getImageAccount());
            virtualImageReservationVOS.add(virtualImageReservationVO);
        }

        return virtualImageReservationVOS;

    }

    /**
     * Description 获取实际审核状态
     *
     * @param state 现在的状态
     * @return 实际审核状态
     * @author 黄保钱 2018-08-24
     */
    @Override
    public Integer getAuditNumber(VirtualImage virtualImage, Integer state) {
        if (state == null || state <= 0) return state;
        Integer auditNumber;
		/*if (labRoom.getCDictionaryByIsAudit() != null
				&& "是".equals(labRoom.getCDictionaryByIsAudit().getCName())) {*/
        //demo
        String[] RSWITCH = {"on", "off"};
        Map<String, String> params = new HashMap<>();
        params.put("businessUid", virtualImage.getId().toString());
        params.put("businessType", pConfig.PROJECT_NAME + "VirtualImageReservation");
        String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessAuditConfigs", params);
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(s);
        String status = jsonObject.getString("status");
        Map auditConfigs = JSON.parseObject(jsonObject.getString("data"), Map.class);
        boolean[] auditArray = new boolean[auditConfigs.size()];
        if (auditConfigs != null && auditConfigs.size() != 0) {
            for (int i = 0; i < auditConfigs.size(); i++) {
                String[] text = ((String) auditConfigs.get(i + 1)).split(":");
                auditArray[i] = text[1].equals(RSWITCH[0]);
            }
        }
        for (auditNumber = state - 1; auditNumber < auditConfigs.size(); auditNumber++) {
            if (auditArray[auditNumber]) {
                break;
            }
        }
        auditNumber++;
		/*} else {
			auditNumber = 6;
		}*/
        return auditNumber;
    }


    /*************************************************************************************
     * Description:虚拟预约审核
     *
     * @author: 杨新蔚
     * @date: 2019/1/6
     *************************************************************************************/
	/*String interstatus="";
		try {
		//调用预约接口
		String url = "http://10.2.39.41/v1/courseDesk";
		Map<String,String> map=new HashMap();
		map.put("num","1");
		map.put("soft_id",virtualImage.getId().toString());
		map.put("start_time",startTime);
		map.put("end_time",endTime);
		String realUrl = getUrl(url);
		String json =post(realUrl,map);
		interstatus="interfaceSuccess";
		System.out.println(json);
		JSONObject jsonObjects = JSONObject.fromObject(json);
		String code = jsonObjects.getString("code");
		String msg = jsonObjects.getString("msg");
		String data=jsonObjects.getString("data");
		if("课表桌面预约成功".equals(msg)){
			interstatus="dataSuccess";
			JSONArray jsonArrays = JSONArray.fromObject(data);
			for (int k = 0; k < jsonArray.size(); k++) {
				JSONObject jsonObject1 = jsonArrays.getJSONObject(k);
				String id = jsonObject1.getString("id");
				if(!"TODO".equals(id)){
					interstatus = "success";
				}
			}
		}
	}catch (Exception e){
		interstatus="fail";
	}finally {
		return interstatus;
	}*/


    /*************************************************************************************
     * Description:调用登录接口-下载ica文件
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public String virtualLogin(Integer id, HttpServletRequest request, HttpServletResponse response) {
        String filename = "";
        VirtualImageReservation virtualImageReservation = virtualImageReservationDAO.findVirtualImageReservationByPrimaryKey(id);
        String nextUrl = "http://10.2.39.41/v1/desktop/" + virtualImageReservation.getMachineId() + "/login";
        String realUrl = getUrl(nextUrl) + "&show";
        String json = loadJson(realUrl);
        long timestamp = System.currentTimeMillis();
        try {
            creatTxtFile(timestamp + "", request);
            filename = writeTxtFile(json);
            download(filename, response);
            delFile(filename);
			/*String fileName="C:\\citrix\\"+timestamp+".ica";
			final Runtime runtime = Runtime.getRuntime();
			Process process = null;
			final String cmd = "rundll32 url.dll FileProtocolHandler file://"+fileName;
			try {
				process = runtime.exec(cmd);
			} catch (final Exception e) {
				System.out.println("Error exec!");
				result="runError";
			}*/
            //将预约状态变为已使用
            //virtualImageReservation=virtualLabSoftwareReservationDAO.findVirtualLabSoftwareReservationById(reservationId);
            //virtualImageReservation.setIsUsed(1);
            //virtualLabSoftwareReservationDAO.store(vlsr);
        } catch (Exception e) {

        } finally {
            return "success";
        }
    }

    /*************************************************************************************
     * Description:调用登录接口（直连）-下载ica文件
     *
     * @author: 杨新蔚
     * @date: 2019/05/28
     *************************************************************************************/
    public String virtualLoginCitrix(Integer id, HttpServletRequest request, HttpServletResponse response) {
        Map<String,String> headers=new HashMap<>();
        headers.put("Content-Type","application/x-www-form-urlencoded");
        headers.put("X-Citrix-IsUsingHTTPS","No");
        try {
            //登录前接口获取cookie、csrftoken
            String post = HttpClientUtil.postWithoutCookie("http://10.2.47.30/Citrix/GVSUNWeb/Authentication/GetAuthMethods", null, headers);
            String post1 = HttpClientUtil.postWithCookie("http://10.2.47.30/Citrix/GVSUNWeb/ExplicitAuth/Login", null, headers);
            VirtualImageReservation virtualImageReservation = virtualImageReservationDAO.findVirtualImageReservationByPrimaryKey(id);
            //登录接口调用
            Map<String, String> params = new HashMap<>();
            //学生域账号，user.getDomainAccount，临时用固定账号测试
            if(!EmptyUtil.isStringEmpty(virtualImageReservation.getImageAccount())){
                params.put("username",virtualImageReservation.getImageAccount());
            }else {
                params.put("username","cmop\\user1");
            }
            params.put("password", "abc@123");
            params.put("saveCredentials", "false");
            params.put("loginBtn", "login");
            params.put("StateContext", "");
            String post2 = HttpClientUtil.postWithCookie("http://10.2.47.30/Citrix/GVSUNWeb/ExplicitAuth/LoginAttempt", params, headers);
            String post3 = HttpClientUtil.postWithCookie("http://10.2.47.30/Citrix/GVSUNWeb/Resources/List", null, headers);
            String icaString=getVirtualImageByVirtualImageReservationID(virtualImageReservation.getId()).getHardwareSet();
            Map<String, String> paramsGet = new HashMap<>();
            //下载ica所需参数
            paramsGet.put("launchId",getVirtualImageByVirtualImageReservationID(virtualImageReservation.getId()).getId());
            paramsGet.put("displayNameDesktopTitle", "Desktop");
            String json = HttpClientUtil.getWithCookie("http://10.2.47.30/Citrix/GVSUNWeb/"+icaString, paramsGet, headers);
            //写入ica文件
            String filename = "";
            long timestamp = System.currentTimeMillis();
            creatTxtFile(timestamp + "", request);
            filename = writeTxtFile(json);
            download(filename, response);
            delFile(filename);
            virtualImageReservation.setIsDownloadIca(1);
            virtualImageReservationDAO.store(virtualImageReservation);
        }catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
		return "success";
    }



    /*************************************************************************************
     * Description:查看虚拟镜像报表
     *
     * @author: 杨新蔚
     * @date: 2018/12/20
     *************************************************************************************/
    public List<Map<String, String>> virtualHistory() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = null;
        String url = "http://10.2.39.41/v1/history";
        String realUrl = getUrl(url);
        String json = loadJson(realUrl);
        JSONObject jsonObject = JSONObject.fromObject(json);
        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");
        String data = jsonObject.getString("data");
        JSONArray jsonArray = JSONArray.fromObject(data);
        for (int k = 0; k < jsonArray.size(); k++) {
            map = new HashMap<>();
            JSONObject jsonObject1 = jsonArray.getJSONObject(k);
            map.put("name", jsonObject1.getString("host_disp_name"));
            map.put("ip", jsonObject1.getString("host_ip"));
            map.put("time", jsonObject1.getString("duration"));
            list.add(map);
        }
        return list;
    }

    private void sendMsg(User receiveUser, Message message) {
        message.setUsername(receiveUser.getUsername());
        messageDAO.store(message);
        messageDAO.flush();
        if (receiveUser.getTelephone() != null) {
            try {
                String result = shareService.sendMessage(receiveUser.getTelephone(), message.getTitle());
            } catch (InterruptedException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }

    /*************************************************************************************
     * Description:虚拟镜像预约检查
     *
     * @author: 杨新蔚
     * @date: 2019/1/11
     *************************************************************************************/
    public String checkImage(HttpServletRequest request) {
        String state = "success";
        try {
            String endTime = request.getParameter("endTime");
            String startTime = request.getParameter("startTime");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = sdf.parse(request.getParameter("startTime"));
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(start);
            //开始时间减少10分钟用于判断冲突
            startCalendar.add(Calendar.MINUTE, -10);
            String startTenTime = sdf.format(startCalendar.getTime());
            String sql = "select v from VirtualImageReservation v where v.virtualImage='" + request.getParameter("VirtualImage")+"'";
            sql += " and ((v.startTime >='" + startTenTime + "' and v.startTime <='" + endTime + "')";
            //sql+=" or (v.endTime >='"+ startTime+"' and v.endTime <='"+endTime+"')";
            sql += " or (v.startTime <='" + startTenTime + "' and v.endTime >='" + startTenTime + "'))";
            List<VirtualImageReservation> virtualImageReservationList1 = virtualImageReservationDAO.executeQuery(sql, 0, -1);
            if (virtualImageReservationList1 != null && virtualImageReservationList1.size() > 0) {
                state = "used";
            }
            String sqll = "select v from VirtualImageReservation v where v.user.username='" + shareService.getUser().getUsername() + "'";
            sqll += " and ((v.startTime >='" + startTime + "' and v.startTime <='" + endTime + "')";
            sqll += " or (v.startTime <='" + startTime + "' and v.endTime >='" + startTime + "'))";
            List<VirtualImageReservation> virtualImageReservationList2 = virtualImageReservationDAO.executeQuery(sqll, 0, -1);
            if (virtualImageReservationList2 != null && virtualImageReservationList2.size() > 0) {
                state = "booked";
            }
        } catch (ParseException e) {
            state = "fail";
        } finally {
            return state;
        }
    }

  /*************************************************************************************
     * Description:虚拟镜像预约检查（直连）
     *
     * @author: 杨新蔚
     * @date: 2019/6/3
     *************************************************************************************/
    public String checkImageCitrix(HttpServletRequest request) {
        String state = "success";
        try {
            //String endTime = request.getParameter("endTime");
            String startTime = request.getParameter("startTime");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = sdf.parse(request.getParameter("startTime"));
            Date end = sdf.parse(request.getParameter("startTime"));
            Calendar startCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();
            startCalendar.setTime(start);
            endCalendar.setTime(end);
            //开始时间减少10分钟用于判断冲突
            startCalendar.add(Calendar.MINUTE, -15);
            endCalendar.add(Calendar.HOUR, 3);
            String startFifteenTime = sdf.format(startCalendar.getTime());
            String endTime = sdf.format(endCalendar.getTime());
            //判断此人时间内是否有其他预约;
            String sqll = "select v from VirtualImageReservation v where v.user.username='" + shareService.getUser().getUsername() + "'";
            sqll += " and ((v.startTime >='" + startTime + "' and v.startTime <='" + endTime + "')";
            sqll += " or (v.startTime <='" + startTime + "' and v.endTime >='" + startTime + "'))";
            List<VirtualImageReservation> virtualImageReservationList2 = virtualImageReservationDAO.executeQuery(sqll, 0, -1);
            if (virtualImageReservationList2 != null && virtualImageReservationList2.size() > 0) {
                state = "booked";
            }
            //特殊镜像，只有5个账号可同时用
            if ("Controller.Win7 GFT $S8-15".equals(request.getParameter("VirtualImage"))){
                String[] accounts={"cmop\\gft1","cmop\\gft2","cmop\\gft2","cmop\\gft2","cmop\\gft2"};
                for (String account:accounts){
                    String sql = "select v from VirtualImageReservation v where v.virtualImage='" + request.getParameter("VirtualImage")+"'";
                    sql+=" and v.imageAccount ='"+account+"' and ((v.isDownloadIca=1 and v.endTime >='"+startFifteenTime+"')";
                    sql+=" or (v.isDownloadIca=0 and v.startTime >='" + startFifteenTime + "' and v.startTime <='" + endTime + "')";
                    sql += " or (v.isDownloadIca=0 and v.startTime <='" + startFifteenTime + "' and v.endTime >='" + startFifteenTime + "'))";
                    List<VirtualImageReservation> virtualImageReservationList1 = virtualImageReservationDAO.executeQuery(sql, 0, -1);
                    if (virtualImageReservationList1 != null && virtualImageReservationList1.size() > 0) {
                        state = "used";
                    }else{
                        state = account;
                        break;
                    }
                }
            }
        } catch (ParseException e) {
            state = "fail";
        } finally {
            return state;
        }
    }

    /*************************************************************************************
     * Description: 保存该门课程下的所有已经启动的桌面ID（机器ID）
     *
     * @param courseID
     * @param virtualImageID
     * @param desktopIDs
     * @author 陈敬2019年3月13日
     *************************************************************************************/
    @Override
    public void saveDesktopIDs(String courseID, String virtualImageID, String[] desktopIDs, String courseStartTime, String courseEndTime) {
        String formatSQL = "INSERT INTO virtual_image_reservation(virtual_image, machine_id, course_id, start_time, end_time,username) VALUE(%s, %s, '%s', '%s', '%s' ,null)";
        for (String desktopID : desktopIDs) {
            Query nativeQuery = entityManager.createNativeQuery(String.format(formatSQL, virtualImageID, desktopID, courseID, courseStartTime, courseEndTime));
            nativeQuery.executeUpdate();
        }
    }

    /*************************************************************************************
     * Description: 获取今天的课程启动计划
     *
     * @return 课程计划列表
     * @author 陈敬2019年3月13日
     *************************************************************************************/
    @Override
    public List<CourseSchedule> getTodayCourseSchedules() {
        String sql = "SELECT\n" +
                "    join_table.id,\n" +
                "    join_table.course_no,\n" +
                "    join_table.virtual_image_id,\n" +
                "    join_table.term,\n" +
                "    timetable_appointment_same_number.start_week AS start_week,\n" +
                "    timetable_appointment_same_number.end_week AS end_week,\n" +
                "    join_table.weekday,\n" +
                "    timetable_appointment_same_number.start_class AS start_class,\n" +
                "    timetable_appointment_same_number.end_class AS end_class,\n" +
                "    CURDATE( ) \n" +
                "FROM\n" +
                "    timetable_appointment_same_number\n" +
                "LEFT JOIN (\n" +
                "    SELECT\n" +
                "        timetable_appointment.id AS id,\n" +
                "        timetable_appointment.course_no AS course_no,\n" +
                "        timetable_appointment.virtual_image_id AS virtual_image_id,\n" +
                "        timetable_appointment.weekday AS weekday,\n" +
                "        timetable_appointment.term AS term\n" +
                "    FROM\n" +
                "        timetable_appointment\n" +
                "        LEFT JOIN school_course ON timetable_appointment.course_no = school_course.course_no \n" +
                "    WHERE\n" +
                "        timetable_appointment.course_no IS NOT NULL \n" +
                "        AND virtual_image_id IS NOT NULL \n" +
                "        AND LENGTH(TRIM(virtual_image_id)) <> 0 \n" +
                ") join_table ON timetable_appointment_same_number.appointment_id = join_table.id \n" +
                "WHERE\n" +
                "    join_table.course_no IS NOT NULL \n" +
                "    AND timetable_appointment_same_number.start_week <= ( SELECT school_week.`week` FROM school_week WHERE school_week.date = CURDATE( ) )\n" +
                "    AND timetable_appointment_same_number.end_week >= ( SELECT school_week.`week` FROM school_week WHERE school_week.date = CURDATE( ) )\n" +
                "    AND join_table.weekday = ( SELECT school_week.`weekday` FROM school_week WHERE school_week.date = CURDATE( ) )\n" +
                "    AND term = (SELECT id FROM school_term WHERE school_term.term_start <= CURDATE() AND CURDATE() <= school_term.term_end)\n" +
                "    ORDER BY course_no ASC, id ASC;";
        Query nativeQuery = entityManager.createNativeQuery(sql);
        List<Object[]> rows = nativeQuery.getResultList();

        List<CourseSchedule> courseScheduleList = new ArrayList<>();
        for (Object[] row : rows) {
            CourseSchedule courseSchedule = new CourseSchedule();
            courseSchedule.setTimetableAppointmentID(Integer.parseInt(row[0].toString()));
            courseSchedule.setCourseNumber(row[1].toString());
            courseSchedule.setVirtualImageID(Integer.parseInt(row[2].toString()));
            courseSchedule.setTerm(Integer.parseInt(row[3].toString()));
            courseSchedule.setStartWeek(Integer.parseInt(row[4].toString()));
            courseSchedule.setEndWeek(Integer.parseInt(row[5].toString()));
            courseSchedule.setWeekday(Integer.parseInt(row[6].toString()));
            courseSchedule.setStartClass(Integer.parseInt(row[7].toString()));
            courseSchedule.setEndClass(Integer.parseInt(row[8].toString()));

            //根据startClass和endClass查询上课的具体时间
            sql = "SELECT start_date FROM system_time WHERE system_time.section = " + courseSchedule.getStartClass();
            nativeQuery = entityManager.createNativeQuery(sql);
            Time courseStartTime = (Time) nativeQuery.getSingleResult();
            sql = "SELECT end_date FROM system_time WHERE system_time.section = " + courseSchedule.getEndClass();
            nativeQuery = entityManager.createNativeQuery(sql);
            Time courseEndTime = (Time) nativeQuery.getSingleResult();
            if (courseStartTime != null && courseEndTime != null) {
                courseSchedule.setStartTime(courseStartTime);
                courseSchedule.setEndTime(courseEndTime);
            } else
                break;

            //通过表reqport_schedule查询人数
            String formatSQL = "SELECT COUNT(username) FROM report_schedule WHERE course_no = '%s' AND class_date = CURDATE() AND start_class = %s AND end_class = %s";
            sql = String.format(formatSQL, courseSchedule.getCourseNumber(), courseSchedule.getStartClass(), courseSchedule.getEndClass());
            nativeQuery = entityManager.createNativeQuery(sql);
            Object numbers = nativeQuery.getSingleResult();
            Integer desktopNumber = Integer.parseInt(numbers.toString());
            courseSchedule.setDesktopNumber(desktopNumber);

            courseScheduleList.add(courseSchedule);
        }
        return courseScheduleList;
    }

    /************************************************************************************
     * Description: 生成回调地址
     *
     * @return 回调地址列表
     * @author 陈敬2019年3月13日
     ************************************************************************************/
    public String generateCallbackRUL() {
        String callbackURL = virtualCallBackUrl;
        //String realURL = getUrl(callbackURL);
        //realURL += "&virtualImageID=" + virtualImageID + "&courseStartTime=" + stringCourseStartTime + "&courseEndTime=" + stringCourseEndTime;
        return callbackURL;
    }

    /*************************************************************************************
     * Description: 根据课程计划自动启动虚拟镜像
     *
     * @param courseScheduleList
     * @author 陈敬2019年3月13日
     *************************************************************************************/
    @Override
    public void startVirtualImageByCourseSchedules(List<CourseSchedule> courseScheduleList) {
        if (courseScheduleList != null && courseScheduleList.size() != 0) {
            String URL = "http://10.2.39.41/v1/courseDeskTiming";
            String realURL = getUrl(URL);
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            Calendar today = Calendar.getInstance();
            today.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            for (CourseSchedule courseSchedule : courseScheduleList) {
                Map<String, String> requestMap = new HashMap<>();
                requestMap.put("num", courseSchedule.getDesktopNumber().toString());
                //requestMap.put("num", "4");
                requestMap.put("soft_id", courseSchedule.getVirtualImageID().toString());
                //requestMap.put("soft_id", "12");


                int hour = Integer.parseInt(courseSchedule.getStartTime().toString().substring(0, 2));
                int minute = Integer.parseInt(courseSchedule.getStartTime().toString().substring(3, 5));
                int second = Integer.parseInt(courseSchedule.getStartTime().toString().substring(6, 8));
                long imageStartTime = today.getTimeInMillis() + hour * 3600000 + minute * 60000 + second * 1000 - 10 * 60 * 1000;
                long courseStartTime = today.getTimeInMillis() + hour * 3600000 + minute * 60000 + second * 1000;
                String stringCourseStartTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(courseStartTime));
                String stringStartTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(imageStartTime));
                requestMap.put("create_time", stringStartTime);
                //requestMap.put("create_time", "2019-03-25 14:00:00");

                hour = Integer.parseInt(courseSchedule.getEndTime().toString().substring(0, 2));
                minute = Integer.parseInt(courseSchedule.getEndTime().toString().substring(3, 5));
                second = Integer.parseInt(courseSchedule.getEndTime().toString().substring(6, 8));
                long imageEndTime = today.getTimeInMillis() + hour * 3600000 + minute * 60000 + second * 1000;
                String stringEndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(imageEndTime));
                requestMap.put("del_time", stringEndTime);
                //requestMap.put("del_time", "2019-03-21 14:10:00");

                requestMap.put("course_id", courseSchedule.getCourseNumber());
                //requestMap.put("course_id", "ceshi");
                String callbackRUL = generateCallbackRUL();
                requestMap.put("callback", callbackRUL);
                String json = post(realURL, requestMap);
                System.out.println(json);
            }
        }
    }

    /*************************************************************************************
     * Description: 获取今天的虚拟镜像
     *
     * @author 陈敬2019年3月15日
     *************************************************************************************/
    @Override
    public List<Object[]> getTodayVirtualImage() {
        String sql = "SELECT course_id, virtual_image, start_time, end_time, id FROM virtual_image_reservation WHERE DATE_FORMAT(start_time,'%Y-%m-%d') = CURDATE() GROUP BY course_id, virtual_image, start_time, end_time";
        //String sql = "SELECT course_id, machine_id, start_time, end_time FROM virtual_image_reservation WHERE 1=1";
        Query nativeQuery = entityManager.createNativeQuery(sql);
        List<Object[]> virtualImageList = nativeQuery.getResultList();
        return virtualImageList;
    }


    /*************************************************************************************
     * Description: 获取未使用的虚拟桌面
     *
     * @param username
     * @param courseID
     * @param startDate
     * @param endDate
     * @return 未使用的虚拟桌面ID，-1表示该学生已经分配过虚拟桌面了，-2表示虚拟桌面用尽了
     * @author 陈敬2019年3月15日
     *************************************************************************************/
    public String getNotUsedDesktopByID(String username, String courseID, String virtualImageID, String startDate, String endDate) {
        String formatSQL = "SELECT id, machine_id, virtual_image FROM virtual_image_reservation WHERE username = '%s' AND virtual_image = %s AND course_id = '%s' AND start_time = '%s' AND end_time = '%s'";
        Query nativeQuery = entityManager.createNativeQuery(String.format(formatSQL, username, virtualImageID, courseID, startDate, endDate));
        List<Object[]> rows = nativeQuery.getResultList();
        if(rows.size() == 0) {  //该学生并没有使用过虚拟桌面，可以给其分配
            formatSQL = "SELECT id, machine_id, virtual_image FROM virtual_image_reservation WHERE username IS NULL AND virtual_image = %s AND course_id = '%s' AND start_time = '%s' AND end_time = '%s' LIMIT 1";
            nativeQuery = entityManager.createNativeQuery(String.format(formatSQL, virtualImageID, courseID, startDate, endDate));
            rows = nativeQuery.getResultList();
            if(rows.size() == 0) {
                return "-2";
            }
            String virtualImageReservationID = rows.get(0)[0].toString();
            //把刚才的虚拟桌面设置为占用
            formatSQL = "UPDATE virtual_image_reservation SET username = '%s'  WHERE id = %s";
            nativeQuery = entityManager.createNativeQuery(String.format(formatSQL, username, virtualImageReservationID));
            nativeQuery.executeUpdate();
            return virtualImageReservationID;
        }
        else {
            return rows.get(0)[0].toString();
        }
    }

    /*************************************************************************************
     * Description: 按当天课表自动启动虚拟镜像
     *
     * @author 陈敬2019年3月13日
     *************************************************************************************/
    //@PostConstruct
    public void autoStartVirtualImageByCourseSchedules() {
        System.out.println("======================跟随项目启动虚拟镜像预约计划===================");
        StartVirtualImageByCourseSchedules startVirtualImageByCourseSchedules = new StartVirtualImageByCourseSchedules(this);
        startVirtualImageByCourseSchedules.run();
    }
}
