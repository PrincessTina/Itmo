import function.Sec;
import org.junit.Test;
import static org.junit.Assert.*;

public class FunctionTest {
  private double EPS = 1E-7;
  private double DELTA = 1E-6;

  @Test
  public void test1() {
    double x = Math.toRadians(-450);
    double expected = Double.POSITIVE_INFINITY;
    double actual = Sec.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }

  @Test
  public void test2() {
    double x = Math.toRadians(-360);
    double expected = 1 / Math.cos(x);
    double actual = Sec.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }

  @Test
  public void test3() {
    double x = Math.toRadians(-270);
    double expected = Double.POSITIVE_INFINITY;
    double actual = Sec.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }

  @Test
  public void test4() {
    double x = Math.toRadians(-180);
    double expected = 1 / Math.cos(x);
    double actual = Sec.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }

  @Test
  public void test5() {
    double x = Math.toRadians(-90);
    double expected = Double.POSITIVE_INFINITY;
    double actual = Sec.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }

  @Test
  public void test6() {
    double x = Math.toRadians(-60);
    double expected = 1 / Math.cos(x);
    double actual = Sec.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }

  @Test
  public void test7() {
    double x = Math.toRadians(-45);
    double expected = 1 / Math.cos(x);
    double actual = Sec.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }

  @Test
  public void test8() {
    double x = Math.toRadians(-30);
    double expected = 1 / Math.cos(x);
    double actual = Sec.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }

  @Test
  public void test9() {
    double x = Math.toRadians(-17);
    double expected = 1 / Math.cos(x);
    double actual = Sec.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }

  @Test
  public void test10() {
    double x = Math.toRadians(0);
    double expected = 1 / Math.cos(x);
    double actual = Sec.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }

  @Test
  public void test11() {
    double x = Math.toRadians(17);
    double expected = 1 / Math.cos(x);
    double actual = Sec.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }

  @Test
  public void test12() {
    double x = Math.toRadians(30);
    double expected = 1 / Math.cos(x);
    double actual = Sec.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }

  @Test
  public void test13() {
    double x = Math.toRadians(45);
    double expected = 1 / Math.cos(x);
    double actual = Sec.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }

  @Test
  public void test14() {
    double x = Math.toRadians(60);
    double expected = 1 / Math.cos(x);
    double actual = Sec.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }

  @Test
  public void test15() {
    double x = Math.toRadians(90);
    double expected = Double.POSITIVE_INFINITY;
    double actual = Sec.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }

  @Test
  public void test16() {
    double x = Math.toRadians(180);
    double expected = 1 / Math.cos(x);
    double actual = Sec.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }

  @Test
  public void test17() {
    double x = Math.toRadians(270);
    double expected = Double.POSITIVE_INFINITY;
    double actual = Sec.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }

  @Test
  public void test18() {
    double x = Math.toRadians(360);
    double expected = 1 / Math.cos(x);
    double actual = Sec.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }

  @Test
  public void test19() {
    double x = Math.toRadians(450);
    double expected = Double.POSITIVE_INFINITY;
    double actual = Sec.count(x, EPS);

    assertEquals(expected, actual, DELTA);
  }
}
