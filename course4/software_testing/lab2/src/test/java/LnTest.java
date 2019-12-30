import function.Ln;
import org.junit.Test;
import static org.junit.Assert.*;

public class LnTest {
  private final double EPS = 1E-7;
  private final double DELTA = 1E-6;

  @Test
  public void test1() {
    double x = -1;
    double expected = Math.log(x);
    double actual = Ln.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }

  @Test
  public void test2() {
    double x = 0;
    double expected = Math.log(x);
    double actual = Ln.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }

  @Test
  public void test3() {
    double x = 1;
    double expected = Math.log(x);
    double actual = Ln.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }

  @Test
  public void test4() {
    double x = 2;
    double expected = Math.log(x);
    double actual = Ln.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }

  @Test
  public void test5() {
    double x = 3;
    double expected = Math.log(x);
    double actual = Ln.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }

  @Test
  public void test6() {
    double x = 4;
    double expected = Math.log(x);
    double actual = Ln.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }

  @Test
  public void test7() {
    double x = 4.5;
    double expected = Math.log(x);
    double actual = Ln.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }
}