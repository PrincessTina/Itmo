package jms.notifications;

import classes.QueueNames;
import ejb.notification.Producer;
import ejb.notification.Receiver;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NotificationsTest {
  private Producer producer = new Producer();
  private Receiver receiver = new Receiver();

  @Test
  public void produceTest() throws InterruptedException, TimeoutException, IOException {
    String request = "Hello";
    String response;

    producer.produce(QueueNames.NEWS, request);
    response = receiver.receive(QueueNames.NEWS, "marco");

    System.out.println(response);

    assert (request.equals(response));
  }
}
