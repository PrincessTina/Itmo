import org.junit.Test;

import java.sql.Date;
import java.util.Calendar;

public class LanguageTest {
  @Test
  public void testSqlDate() {
    Date date = new Date(Calendar.getInstance().getTime().getTime());
    Date date2 = new Date(new java.util.Date().getDate());
    System.out.println(date);
  }
}
