package excelTools;




import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.message.PromoteActProducerService;
import org.apache.activemq.command.ActiveMQDestination;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


public class PromoteActConsumer implements MessageListener{
//    @Value("#{properties['auditServerUrl']}")
//    private String  auditServerUrl;
    @Autowired private ShareService shareService;
// @JmsListener(containerFactory="jmsListenerContainerFactory",destination = "test3.topic")
    /**
    * @Description: 实验室消息监听者(消费者)
    * @Author: 徐明杭
    * @CreateDate: 2019/4/19 12:32
    */
    public void onMessage(Message message) {
        TextMessage tm = (TextMessage) message;
        try {
            ActiveMQDestination queues=(ActiveMQDestination) message.getJMSDestination();
            if(queues.getPhysicalName().equalsIgnoreCase("queue1")) {
                System.out.println("QueueMessageListener监听到了文本消息：\t"
                        + tm.getText());
            }
            //解析json
            com.alibaba.fastjson.JSONObject jsonObject= com.alibaba.fastjson.JSONObject.parseObject(tm.getText());
            //如果是实验室管理平台
            if (jsonObject.getString("project").equals("GvsunLims")){
                shareService.sendMessage(jsonObject.getString("mobile"),jsonObject.getString("mess"));
            }

           /* SendMsgToPortType sendMsg = new SendMsgTo_Service().getSendMsgToHttpPort();
            sendMsg.sendMsgTo(mobile, "50753a9a0f0cae0852a2c25d962960e4", mobile, code,mobile);*/
//           String out= new SendMsgResponse().getOut();

        /*   com.alibaba.fastjson.JSONObject jsonObject= com.alibaba.fastjson.JSONObject.parseObject(tm.getText());
          String mobile= jsonObject.getString("mobile");
            String code= jsonObject.getString("code");
            HttpClient httpclient=new HttpClient();
            Integer s=3736;
            GetMethod getMethod = new GetMethod(auditServerUrl + "/getFileById?fileId="+s.toString());
            getMethod.addRequestHeader("Authorization","");
            httpclient.executeMethod(getMethod);
            String userStr = getMethod.getResponseBodyAsString();
            JSONObject user1 = JSONObject.fromObject(userStr);*/
            //do something ...
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  /* @JmsListener(destination ="test.queue")
    public void receiveQueue(Message message) {
try {
    TextMessage textMessage=(TextMessage) message;
    System.out.println("接收到的消息为"+textMessage.getText());
}catch (JMSException e){
    e.printStackTrace();
}

    }*/
   /* @JmsListener(destination ="test.topic")
    public void receiveTopic(String consumer) {
        System.out.println(consumer+"消息已经消费了"+consumer);
    }*/

}
