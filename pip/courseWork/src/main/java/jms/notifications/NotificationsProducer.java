package jms.notifications;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import javax.ejb.Stateless;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Stateless
public class NotificationsProducer {
  public void produce(String notificationType, int id) throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername("tina");
    factory.setPassword("19031999");
    factory.setVirtualHost("/");
    factory.setHost("188.166.39.53");
    factory.setPort(5672);

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    channel.exchangeDeclare(notificationType, "fanout", true, false, null);

    channel.basicPublish(notificationType, "", null, String.valueOf(id).getBytes());

    channel.close();
    connection.close();
  }
}
