package jms.newsNotifications;

import org.junit.Test;

public class NewsProducerTest {
  private NewsProducer producer = new NewsProducer();

  @Test
  public void produceTest() {
    try {
      producer.produce();
    } catch (Exception e) {
      assert(false);
    }
  }
}
