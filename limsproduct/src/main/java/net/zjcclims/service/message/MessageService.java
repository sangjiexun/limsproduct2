package net.zjcclims.service.message;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import net.zjcclims.domain.Message;

/**
 * Spring service that handles CRUD requests for Message entities
 * 
 */
public interface MessageService {

	/**
	 */
	public Message findMessageByPrimaryKey(Integer id);

	/**
	 * Delete an existing Message entity
	 * 
	 */
	public void deleteMessage(Message message);

	/**
	 * Return a count of all Message entity
	 * 
	 */
	public Integer countMessages();

	/**
	 * Return all Message entity
	 * 
	 */
	public List<Message> findAllMessages(Integer startResult, Integer maxRows);

	/**
	 * Load an existing Message entity
	 * 
	 */
	public Set<Message> loadMessages();

	/**
	 * Save an existing Message entity
	 * 
	 */
	public void saveMessage(Message message_1);
	
	
	/*************************
	 * 功能：我的消息列表
	 * 作者：贺子龙
	 * 时间：2015-09-15 20:51:04
	 *************************/
	public List<Message> findMessageBySome(Message message,int currpage, int pageSize,HttpServletRequest request,int tage);
	/*************************
	 * 功能：我的消息个数
	 * 作者：贺子龙
	 * 时间：2015-09-16 10:31:58
	 *************************/
	public Integer countmessage(Message message,HttpServletRequest request,int tage);
	
	/*************************
	 * 功能：我的消息个数
	 * 作者：贺子龙
	 * 时间：2015-09-16 10:31:58
	 *************************/ 
	public Integer countmessage( );
	
	/*************************************************
	 * Description:保存信息列表
	 * @param:toUser 申请人
	 * @param:content 申请信息
	 * @author 邵志峰
	 * @date 2017-05-31
	 ***************************************************/
	@Transactional
	public void saveMessages(String toUser,String title,String content,int tage);
	
	public List<Message> findAllMessagesById(int operationItemId,String content,String username);
	/*************************
	 * 功能：分天，月，年获取某类型消息个数
	 * 作者：孙虎
	 *************************/
	public Integer countmessage(int tage,int flag);
}