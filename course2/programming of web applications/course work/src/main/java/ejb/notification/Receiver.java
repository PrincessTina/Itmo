package ejb.notification;

import com.rabbitmq.client.*;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class Receiver {
  private static String response = null;

  /**
   * Q - очередь пользователей
   *
   * @param exchange - Тип уведомления
   * @param login - Имя очереди
   * @return null or response message
   */
  public String receive(String exchange, String login) throws IOException, TimeoutException, InterruptedException {
    String queueName = "Q" + login;
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername("tina");
    factory.setPassword("19031999");
    factory.setVirtualHost("/");
    factory.setHost("188.166.39.53");
    factory.setPort(5672);

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(exchange, "fanout", true, false, null);
    channel.queueDeclare(queueName, true, false, false, null);
    channel.queueBind(queueName, exchange, "");

    channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        response = new String(body, "UTF-8");
      }
    });

    Thread.sleep(1000);

    return response;
  }
}
