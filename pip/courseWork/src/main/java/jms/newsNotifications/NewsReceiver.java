package jms.newsNotifications;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NewsReceiver {
  public void receive() throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername("tina");
    factory.setPassword("19031999");
    factory.setVirtualHost("/");
    factory.setHost("188.166.39.53");
    factory.setPort(5672);

    Connection conn = factory.newConnection();
    final Channel channel = conn.createChannel();
    String exchangeName = "myExchange";
    String queueName = "myQueue";
    String routingKey = "testRoute";

    channel.exchangeDeclare(exchangeName, "direct", true);
    channel.queueDeclare(queueName, true, false, false, null);
    channel.queueBind(queueName, exchangeName, routingKey);

    channel.basicConsume(queueName, false, new DefaultConsumer(channel) {
          @Override
          public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
            String routingKey = envelope.getRoutingKey();
            String contentType = properties.getContentType();
            long deliveryTag = envelope.getDeliveryTag();

            // Get message
            String message = new String(body);

            // (process the message components here ...)
            channel.basicAck(deliveryTag, false);
          }
        }
    );

    channel.close();
    conn.close();
  }
}
