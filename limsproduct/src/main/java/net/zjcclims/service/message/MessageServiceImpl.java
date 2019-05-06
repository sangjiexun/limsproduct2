package net.zjcclims.service.message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;


import net.zjcclims.dao.MessageDAO;


import net.zjcclims.domain.Authority;
import net.zjcclims.domain.LabRoomAdmin;
import net.zjcclims.domain.Message;
import net.zjcclims.domain.User;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.system.SystemService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

/**
 * Spring service that handles CRUD requests for Message entities
 * 
 */

@Service("MessageService")
@Transactional
public class MessageServiceImpl implements MessageService {

	/**
	 * DAO injected by Spring that manages Message entities
	 * 
	 */
	@Autowired
	private MessageDAO messageDAO;
	@Autowired
	private ShareService shareService;
	@Autowired
	private SystemService systemService;


	/**
	 * Instantiates a new MessageServiceImpl.
	 *
	 */
	public MessageServiceImpl() {
	}

	/**
	 */
	@Transactional
	public Message findMessageByPrimaryKey(Integer id) {
		return messageDAO.findMessageByPrimaryKey(id);
	}

	/**
	 * Delete an existing Message entity
	 * 
	 */
	@Transactional
	public void deleteMessage(Message message) {
		messageDAO.remove(message);
		messageDAO.flush();
	}

	/**
	 * Return a count of all Message entity
	 * 
	 */
	@Transactional
	public Integer countMessages() {
		return ((Long) messageDAO.createQuerySingleResult("select count(o) from Message o").getSingleResult()).intValue();
	}

	/**
	 * Return all Message entity
	 * 
	 */
	@Transactional
	public List<Message> findAllMessages(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<Message>(messageDAO.findAllMessages(startResult, maxRows));
	}

	/**
	 * Load an existing Message entity
	 * 
	 */
	@Transactional
	public Set<Message> loadMessages() {
		return messageDAO.findAllMessages();
	}

	/**
	 * Save an existing Message entity
	 * 
	 */
	@Transactional
	public void saveMessage(Message message) {
		Message existingMessage = messageDAO.findMessageByPrimaryKey(message.getId());

		if (existingMessage != null) {
			if (existingMessage != message) {
				existingMessage.setId(message.getId());
				existingMessage.setCond(message.getCond());
				existingMessage.setTitle(message.getTitle());
				existingMessage.setCreateTime(message.getCreateTime());
				existingMessage.setContent(message.getContent());
				existingMessage.setMessageState(message.getMessageState());
				existingMessage.setSendUser(message.getSendUser());
				existingMessage.setSendCparty(message.getSendCparty());
				existingMessage.setAuthId(message.getAuthId());
				existingMessage.setReceiveCpartyid(message.getReceiveCpartyid());
			}
			message = messageDAO.store(existingMessage);
		} else {
			message = messageDAO.store(message);
		}
		messageDAO.flush();
	}
	/*************************
	 * 功能：我的消息列表
	 * 作者：贺子龙
	 * 时间：2015-09-15 20:51:04
	 *************************/
	@Override
	public List<Message> findMessageBySome(Message message,int currpage, int pageSize,HttpServletRequest request,int tage) {
	List<Message> list =new ArrayList<Message>();
	String str="";
	String sql="";
	Set<Authority> authorities = shareService.getUserDetail().getAuthorities();
	List<Authority> authoritiesList = new ArrayList<Authority>(authorities);
	if (authoritiesList.size()>0) {//找到当前登录用户的所有权限
		
		for (int i = 0; i < authoritiesList.size(); i++) {
			sql+="  m.authId="+authoritiesList.get(i).getId()+" or";
		}
	}
	
		
	
	String a="select m from Message m where 1=1";
	//a=a.substring(0, a.length()-2);//-2  是为了去掉最后一个or
//	a+="and m.messageState=0";
	a+=" and m.username= '"+shareService.getUserDetail().getUsername()+"'";//看是不是有当前登陆人的消息，根据username判断
	if (message.getReceiveCpartyid()!=null) {//不判断会报错
		
		a+=" and m.receiveCpartyid ="+ shareService.getUserDetail().getSchoolAcademy().getAcademyNumber();
	}
			
	if(message.getSendUser()!=null&&message.getSendUser()!=""){
		a+="and m.sendUser like'%"+message.getSendUser()+"%'";
	}
	if(message.getTitle()!=null&&message.getTitle()!=""){
		a+="and m.title like'%"+message.getTitle()+"%'";
	}
	String starttime= request.getParameter("starttime");
	String endtime=	request.getParameter("endtime");
	
	  if(starttime!=null && starttime.length()>0 && endtime!=null&& endtime.length()>0){
        	//SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		 a += " and m.createTime between '"+starttime +"' and '"+endtime+"' "; 	
        }
	if(tage == 1){
		a+=" and m.tage in(0,1,3,4,5)";
	}else if(tage == 2){
		a+=" and m.tage = 2 ";
	}
	  a+="order by m.createTime desc, m.messageState asc, m.id desc";
	  list=messageDAO.executeQuery(a,currpage*pageSize,pageSize);
	  return list;
	}
	/*************************
	 * 功能：我的消息个数
	 * 作者：贺子龙
	 * 时间：2015-09-16 10:31:58
	 *************************/
	@Transactional
	@Override
	public Integer countmessage(Message message,HttpServletRequest request,int tage) {
		String a="select count(m) from Message m where 1=1";
		a+=" and m.username= '"+shareService.getUserDetail().getUsername()+"'";//看是不是有当前登陆人的消息，根据username判断
		if (message.getReceiveCpartyid()!=null) {//不判断会报错

			a+=" and m.receiveCpartyid ="+ shareService.getUserDetail().getSchoolAcademy().getAcademyNumber();
		}

		if(message.getSendUser()!=null&&message.getSendUser()!=""){
			a+="and m.sendUser like'%"+message.getSendUser()+"%'";
		}
		if(message.getTitle()!=null&&message.getTitle()!=""){
			a+="and m.title like'%"+message.getTitle()+"%'";
		}
		String starttime= request.getParameter("starttime");
		String endtime=	request.getParameter("endtime");

		if(starttime!=null && starttime.length()>0 && endtime!=null&& endtime.length()>0){
			a += " and m.createTime between '"+starttime +"' and '"+endtime+"' ";
		}
		if(tage == 1){
			a+=" and m.tage in(0,1,3,4,5)";
		}else if(tage == 2){
			a+=" and m.tage = 2 ";
		}
		 return ((Long) messageDAO.createQuerySingleResult(a.toString()).getSingleResult()).intValue();

	}
	/*************************
	 * 功能：分天，月，年获取某类型消息个数
	 * 作者：孙虎
	 *************************/
	@Override
	public Integer countmessage(int tage,int flag) {
		//我要获取当前的日期
        Date date = new Date();
        //设置要获取到什么样的时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //获取String类型的时间
        String createdate = format.format(date);
        // 获取前月的第一天  
        Calendar  cale = Calendar.getInstance();  
        cale.add(Calendar.MONTH, 0);  
        cale.set(Calendar.DAY_OF_MONTH, 1);  
        String firstday = format.format(cale.getTime());  
        // 获取前月的最后一天  
        cale = Calendar.getInstance();  
        cale.add(Calendar.MONTH, 1);  
        cale.set(Calendar.DAY_OF_MONTH, 0);  
        String lastday = format.format(cale.getTime());
        
		StringBuffer a = new StringBuffer("select count(m) from Message m where 1=1 ");
		if(flag == 1){//当天
			a.append(" and m.createTime = '"+createdate+"' ");
		}else if(flag == 2){
			a.append(" and m.createTime between '"+firstday+"' and '"+lastday+"' ");
		}else if(flag == 3){
			a.append(" and m.createTime like '"+cale.get(Calendar.YEAR)+"%' ");
		}
		a.append(" and m.tage ="+tage);
		a.append(" and m.username ='"+shareService.getUserDetail().getUsername()+"'");
		return ((Long) messageDAO.createQuerySingleResult(a.toString()).getSingleResult()).intValue();
	}
	/*************************
	 * 功能：我的消息个数
	 * 作者：贺子龙
	 * 时间：2015-09-16 10:31:58
	 *************************/
	@Transactional
	public Integer countmessage( ) { 
		String a="select count(m) from Message m where 1=1"; 
		a+="and m.messageState=0";
		a+="and m.username= '"+shareService.getUserDetail().getUsername()+"'";//看是不是有当前登陆人的消息，根据username判断
		a+=" and (m.tage = 0 or m.tage = 1 or m.tage = 2 or m.tage = 3 or m.tage = 4) ";
		return ((Long) messageDAO.createQuerySingleResult(a.toString()).getSingleResult()).intValue();

	}
	
	/*************************************************
	 * Description:保存信息列表
	 * @param:toUser 申请人
	 * @param:content 申请信息
	 * @author 邵志峰
	 * @date 2017-05-31
	 ***************************************************/
	@Transactional
	public void saveMessages(String toUser,String title,String content,int tage) {
		
		
		Message message=new Message();
		message.setUsername(toUser);
		message.setSendUser(shareService.getUser().getCname());//发给预约人
		message.setTitle(title);
		message.setContent(content);//消息内容
		message.setMessageState(0);//设置未读
		//获取当前时间
		Calendar date=Calendar.getInstance();
		message.setCreateTime(date);
		message = messageDAO.store(message);
		messageDAO.flush();
		
	}
	
	public List<Message> findAllMessagesById(int operationItemId,String content,String username){
		String sql="select l from Message l where 1=1";
		sql+=" and l.username='"+username+"'";
		sql+=" and l.content like '%"+content+"%"+"operationItemId%"+operationItemId+"%'";
		return messageDAO.executeQuery(sql,0,-1);
	}
	
}
