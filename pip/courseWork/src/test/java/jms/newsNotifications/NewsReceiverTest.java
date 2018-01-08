package jms.newsNotifications;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NewsReceiverTest {
  private NewsReceiver receiver = new NewsReceiver();

  @Test
  public void receiveTest() {
    try {
      receiver.receive();
    } catch (Exception e) {
      assert(false);
    }
  }
}
