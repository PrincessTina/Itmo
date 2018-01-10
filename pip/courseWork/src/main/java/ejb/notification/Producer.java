package ejb.notification;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Stateless
@LocalBean
public class Producer {
  public void produce(String exchange, String message) throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername("s225074");
    factory.setPassword("3HJiPRAH");
    factory.setVirtualHost("/");
    factory.setHost("helios.cs.ifmo.ru");
    factory.setPort(2222);

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(exchange, "fanout", true, false, null);
    channel.basicPublish(exchange, "", null, message.getBytes());

    channel.close();
    connection.close();
  }
}
