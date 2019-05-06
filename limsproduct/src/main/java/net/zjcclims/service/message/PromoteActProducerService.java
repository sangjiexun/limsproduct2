package net.zjcclims.service.message;

import javax.jms.Destination;

public interface PromoteActProducerService {
    /**
    * @Description: 发送消息
    * @Author: 徐明杭
    * @CreateDate: 2019/4/17 14:39
    */
    void sendMessage(Destination destination, String message);
}
