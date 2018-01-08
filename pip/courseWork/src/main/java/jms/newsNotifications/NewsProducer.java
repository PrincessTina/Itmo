package jms.newsNotifications;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NewsProducer {
  public void produce() throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername("tina");
    factory.setPassword("19031999");
    factory.setVirtualHost("/");
    factory.setHost("188.166.39.53");
    factory.setPort(5672);

    Connection conn = factory.newConnection();
    Channel channel = conn.createChannel();
    String exchangeName = "myExchange";
    String queueName = "myQueue";
    String routingKey = "testRoute";

    channel.exchangeDeclare(exchangeName, "direct", true);
    channel.queueDeclare(queueName, true, false, false, null);
    channel.queueBind(queueName, exchangeName, routingKey);

    byte[] messageBodyBytes = "Hello, world!".getBytes();
    channel.basicPublish(exchangeName, routingKey, null, messageBodyBytes);

    channel.close();
    conn.close();
  }
}
