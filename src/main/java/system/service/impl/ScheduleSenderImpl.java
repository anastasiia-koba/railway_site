package system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import system.service.api.ScheduleSender;

import javax.annotation.Resource;
import javax.jms.*;

@Slf4j
@Service
public class ScheduleSenderImpl implements ScheduleSender {

    private static final long TIME_TO_LIVE = 30_000; //messages older won't be delivered to destination

    @Resource(lookup = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "java:/jms/topic/rw")
    private Destination destination;

    @Override
    public void sendMessage(String jsonObj) {
        try {
            TopicConnection connection = (TopicConnection) connectionFactory.createConnection();
            TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(destination);

            TextMessage textMessage = session.createTextMessage("Hello MDB");
            producer.setTimeToLive(TIME_TO_LIVE);
            producer.send(textMessage);
            log.info("Send JMS: "+jsonObj);
            connection.close();
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
