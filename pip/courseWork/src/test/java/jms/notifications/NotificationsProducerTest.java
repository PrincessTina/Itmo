package jms.notifications;

import org.junit.Test;

public class NotificationsProducerTest {
  private NotificationsProducer producer = new NotificationsProducer();

  @Test
  public void produceTest() {
    try {
      producer.produce(NotificationTypes.NEWS, 1);
    } catch (Exception e) {
      assert(false);
    }
  }
}
