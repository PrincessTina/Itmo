package jms.notifications;

import org.junit.Test;

public class NotificationsReceiverTest {
  private NotificationsReceiver receiver = new NotificationsReceiver();

  @Test
  public void receiveTest() {
    try {
      //receiver.receive(NotificationTypes.NEWS, "tina");
    } catch (Exception e) {
      assert(false);
    }
  }
}
