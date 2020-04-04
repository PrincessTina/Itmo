package Structure;

import static java.lang.Math.pow;
import static Main.Log.setBufParams;
import static Main.Main.mod;
import static Main.Main.a;

/**
 * Точка эллиптической кривой
 */
public class Point {
  public int x;
  public int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
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
    final int numerator = point.y - y;
    final int denominator = point.x - x;
    final int lambda = mod(numerator, denominator);
    final int x3 = mod((int) pow(lambda, 2) - x - point.x, 1);
    final int y3 = mod(lambda * (x - x3) - y, 1);

    setBufParams(lambda, x3, y3);

    return new Point(x3, y3);
  }

  /**
   * Сравнивает точки
   *
   * @param point - точка, с которой необходимо выполнить сравнение
   * @return возвращает true, если координаты точек равны, иначе - false
   */
  public boolean equals(Point point) {
    return x == point.x && y == point.y;
  }

  /**
   * @return возвращает строковое представление точки
   */
  public String toString() {
    return "(" + x + ", " + y + ")";
  }

  /**
   * Удвоение точки эллиптической кривой
   *
   * @return возвращает точку, являющуюся произведением данной точки на 2
   */
  private Point redouble() {
    final int numerator = 3 * (int) pow(x, 2) + a;
    final int denominator = 2 * y;
    final int lambda = mod(numerator, denominator);
    final int x3 = mod((int) pow(lambda, 2) - 2 * x, 1);
    final int y3 = mod(lambda * (x - x3) - y, 1);

    return new Point(x3, y3);
  }
}
