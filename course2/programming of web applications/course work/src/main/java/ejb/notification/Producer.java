package ejb.notification;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class Producer {
  public void produce(String exchange, String message) throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername("tina");
    factory.setPassword("19031999");
    factory.setVirtualHost("/");
    factory.setHost("188.166.39.53");
    factory.setPort(5672);

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(exchange, "fanout", true, false, null);
    channel.basicPublish(exchange, "", null, message.getBytes());

    channel.close();
    connection.close();
  }
}
