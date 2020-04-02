package Structure;

import static java.lang.Math.pow;
import static Main.Main.mod;
import static Main.Main.a;

public class Point {
  public int x;
  public int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Point() {
  }

  /**
   * Умножение точки эллиптической кривой
   *
   * @param k - коэффициент умножения
   * @return возвращает точку, являющуюся произведением данной точки на k
   */
  public Point multiply(int k) {
    int i = 2;
    Point point3 = this;

    do {
      point3 = point3.redouble();
      i *= 2;
    } while (i <= k);

    i /= 2;

    while (i < k) {
      point3 = add(point3);
      i++;
    }

    return point3;
  }

  /**
   * Вычитание точек эллиптической кривой
   *
   * @param point - точка, которую необходимо вычесть
   * @return возвращает точку, являющуюся результатом разности точек
   */
  public Point subtract(Point point) {
    point.y = -point.y;
    return add(point);
  }

  /**
   * Сложение точек эллиптической кривой
   *
   * @param point - точка, которую необходимо добавить
   * @return возвращает точку, являющуюся суммой точек
   */
  public Point add(Point point) {
    int numerator = point.y - y;
    int denominator = point.x - x;
    int lambda = mod(numerator, denominator);
    Point point3 = new Point();

    point3.x = mod((int) pow(lambda, 2) - x - point.x, 1);
    point3.y = mod(lambda * (x - point3.x) - y, 1);

    return point3;
  }

  /**
   * Удвоение точки эллиптической кривой
   *
   * @return возвращает точку, являющуюся произведением данной точки на 2
   */
  private Point redouble() {
    int numerator = 3 * (int) pow(x, 2) + a;
    int denominator = 2 * y;
    int lambda = mod(numerator, denominator);
    Point point3 = new Point();

    point3.x = mod((int) pow(lambda, 2) - 2 * x, 1);
    point3.y = mod(lambda * (x - point3.x) - y, 1);

    return point3;
  }
}
