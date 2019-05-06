package net.zjcclims.web.instrument;


import com.alibaba.fastjson.JSON;
import net.zjcclims.service.instrument.TimeTableAppointmentForInstrumentService;
import net.zjcclims.vo.RestResult;
import net.zjcclims.vo.TimeTableAppointmentForInstrumentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller("ApiForInstrument")
public class ApiForInstrument {
    private String fail = "fail";
    private String success = "success";
    @Autowired
    TimeTableAppointmentForInstrumentService timeTableAppointmentForInstrumentService;
    /************************************************************************
     *@Description:获取大仪所需实验室预约数据
     *@Author:吴奇臻
     *@Date:2018/03/11
     ************************************************************************/
    @RequestMapping(value = "/instrument/getTimeTableAppointmentForInstrument")
    @ResponseBody
    public RestResult getTimeTableAppointmentForInstrument(@RequestParam String id){
        RestResult result = new RestResult(success);
        try {
            List<TimeTableAppointmentForInstrumentVO> timeTableAppointmentForInstrumentVOList=new ArrayList<>();
            timeTableAppointmentForInstrumentVOList=timeTableAppointmentForInstrumentService.getAllAppointmentForInstrumentByLabId(Integer.parseInt(id));
            result.setData(timeTableAppointmentForInstrumentVOList);
        } catch (Exception e) {
            result.setStatus(fail);
            e.printStackTrace();
        }
        return result;
    }
}
