/**
 * 
 */


package net.zjcclims.web.sms;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.zjcclims.service.common.ShareService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.pubinfo.service.SendMsgPortType;
import cn.com.pubinfo.service.SendMsg_Service;
import cn.com.pubtoinfo.service.SendMsgToPortType;
import cn.com.pubtoinfo.service.SendMsgTo_Service;

/****************************************************************************
 * 功能：短信对接
 * 作者：贺子龙
 * 时间：2017-11-02
 ****************************************************************************/
@RequestMapping("/sms")
@Controller("SMSController")
public class SMSController<JsonResult> {
	@Autowired
	private ShareService shareService;

	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register static property editors.
		binder.registerCustomEditor(java.util.Calendar.class, new org.skyway.spring.util.databinding.CustomCalendarEditor());
		binder.registerCustomEditor(byte[].class, new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
		binder.registerCustomEditor(boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(false));
		binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
		binder.registerCustomEditor(java.math.BigDecimal.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
		binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Integer.class, true));
		binder.registerCustomEditor(java.util.Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
		binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
		binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Long.class, true));
		binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Double.class, true));
	}
	
	/****************************************************************************
	 * 功能：发送短信
	 * 作者：周志辉
	 * 时间：2017-11-08
	 * @throws NoSuchAlgorithmException 
	 * @throws InterruptedException 
	 ****************************************************************************/
	@RequestMapping("/sendMessage")
	public ModelAndView sendMessage() throws NoSuchAlgorithmException, InterruptedException{
		ModelAndView mav = new ModelAndView();
		String tel="18621586371";
		String content="你好";
		String result=shareService.sendMessage(tel, content);
		mav.addObject("result",result);
		mav.setViewName("/sendMsgTest.jsp");	
		return mav;
	}
	/****************************************************************************
	 * 功能：发送短信
	 * 作者：贺子龙
	 * 时间：2017-11-02
	 ****************************************************************************/
	@RequestMapping("/sendMessageTo")
	public ModelAndView sendMessageTo(){
		ModelAndView mav = new ModelAndView();
		
		SendMsgToPortType sendMsg = new SendMsgTo_Service().getSendMsgToHttpPort();
		sendMsg.sendMsgTo("0571053", "50753a9a0f0cae0852a2c25d962960e4", "13167220653", "测试短信","18621586371");
		mav.setViewName("/../**.jsp");
		
		return mav;
	}
	
	
}
