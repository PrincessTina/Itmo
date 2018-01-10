package jms.notifications;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NotificationsTest {
  private NotificationsProducer producer = new NotificationsProducer();
  private NotificationsReceiver receiver = new NotificationsReceiver();

  @Test
  public void produceTest() throws InterruptedException, TimeoutException, IOException {
    int expectedNewsId = 123;

    producer.produce(NotificationTypes.NEWS, expectedNewsId);
    int receivedNewsId = receiver.receive(NotificationTypes.NEWS, "tina");

    assert(expectedNewsId == receivedNewsId);
  }
}
