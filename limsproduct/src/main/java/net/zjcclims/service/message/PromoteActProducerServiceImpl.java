package net.zjcclims.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.jms.JMSException;


@Service("PromoteActProducerService")

public class PromoteActProducerServiceImpl implements PromoteActProducerService {
    @Autowired
    @Qualifier("jmsTemplate")
    private JmsTemplate jmsTemplate;

    public  void sendMessage(Destination destination,final String message) {

        jmsTemplate.send(destination, new MessageCreator() {
            public javax.jms.Message createMessage(javax.jms.Session session) throws JMSException {

                return session.createTextMessage(message);
            }
        });
    }

}
