package jms.notifications;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
/*import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP;*/

import javax.ejb.Stateful;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Stateful
public class NotificationsReceiver {
  int notificationId = 0;

  /**
   * Префиксы:
   * Q - очереди пользователей
   *
   * @param notificationType - Тип уведомления (Новости, ачивки)
   * @param login - Имя очереди завязано на login пользователя
   * @throws IOException
   * @throws TimeoutException
   */
  public int receive(String notificationType, String login) throws IOException, TimeoutException {
    if (notificationId != 0) {
      int result = notificationId;
      notificationId = 0;
      return result;
    }

    String queueName = "Q" + login;

    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername("tina");
    factory.setPassword("19031999");
    factory.setVirtualHost("/");
    factory.setHost("188.166.39.53");
    factory.setPort(5672);
    Connection conn = factory.newConnection();

    Channel channel = conn.createChannel();
    channel.exchangeDeclare(notificationType, "fanout", true);
    channel.queueDeclare(queueName, true, false, false, null);
    channel.queueBind(queueName, notificationType, "");

    /*channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        notificationId = Integer.parseInt(message);
      }
    });*/

    return notificationId;
  }
}
