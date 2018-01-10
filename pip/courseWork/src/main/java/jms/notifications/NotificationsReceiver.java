package jms.notifications;

import com.rabbitmq.client.*;
/*import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP;*/

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Stateless
public class NotificationsReceiver {

  /**
   * Префиксы:
   * Q - очереди пользователей
   *
   * @param notificationType - Тип уведомления (Новости, ачивки)
   * @param login - Имя очереди завязано на login пользователя
   * @throws IOException
   * @throws TimeoutException
   */
  public int receive(String notificationType, String login) throws IOException, TimeoutException, InterruptedException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername("tina");
    factory.setPassword("19031999");
    factory.setVirtualHost("/");
    factory.setHost("188.166.39.53");
    factory.setPort(5672);
    Connection conn = factory.newConnection();

    String queueName = "Q" + login;

    Channel channel = conn.createChannel();
    channel.exchangeDeclare(notificationType, "fanout", true, false, null);
    channel.queueDeclare(queueName, true, false, false, null);
    channel.queueBind(queueName, notificationType, "");

    // Должен быть массивом, чтобы можно было прокинуть в коллбек. Не верите - попробуйте сделать просто int (C) Java Buddha
    final Integer[] notificationId = {0};

    channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        notificationId[0] = Integer.parseInt(message);
      }
    });

    // Даю время коллбеку handleDelivery считать сообщение и положить его в notificationId
    Thread.sleep(10);

    return notificationId[0];
  }
}
