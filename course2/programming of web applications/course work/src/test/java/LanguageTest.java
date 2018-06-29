import ejb.data.Access;
import org.junit.Test;

import java.util.Date;

public class LanguageTest extends Access{
  @Test
  public void testSqlDate() throws InterruptedException {
    Date date = new Date();

    Thread.sleep(2000);
    System.out.println(new Date().getTime() - date.getTime());
  }
}
