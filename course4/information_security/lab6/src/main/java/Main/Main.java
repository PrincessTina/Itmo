package Main;

import Structure.Point;

import static java.lang.Math.*;

public class Main {
  public static int a = -1;
  static int module = 751;
  static int secretKey = 58;
  static Point G = new Point(0, 1);
  static Point openKey = new Point(406, 397);

  public static void main(String[] args) {
    Point[][] encoded = {
        {new Point(16, 416), new Point(93, 484)},
        {new Point(489, 468), new Point(531, 397)},
        {new Point(188, 93), new Point(654, 102)},
        {new Point(489, 468), new Point(218, 150)},
        {new Point(16, 416), new Point(530, 729)},
        {new Point(425, 663), new Point(295, 219)},
        {new Point(725, 195), new Point(742, 299)},
        {new Point(188, 93), new Point(367, 360)},
        {new Point(188, 93), new Point(235, 732)},
        {new Point(618, 206), new Point(251, 245)},
        {new Point(425, 663), new Point(688, 10)}
    };

    String message = "феерический";

    decode(encoded);
  }

  /**
   * Находит математически корректный остатот от деления
   * - целого числа
   * - дроби
   * - отрицательного числа
   * @param numerator - числитель
   * @param denominator - знаменатель (если не дробь, ставить в 1)
   * @return возвращает остаток от деления
   */
  public static int mod(int numerator, int denominator) {
    long mod;
    boolean sign = (double) numerator / denominator < 0;

    numerator = abs(numerator);
    denominator = abs(denominator);
    module = abs(module);

    if (denominator == 1) {
      mod = numerator % module;
    } else {
      mod = (numerator * pow_mod(denominator, module - 2)) % module;
    }

    if (sign) {
      mod = (mod == 0) ? mod : module - mod;
    }

    return (int) mod;
  }

  private static Point[] encode() {
    int k = 3;
    Point point = new Point(67, 667);
    Point[] encoded = new Point[2];

    encoded[0] = G.multiply(k);
    encoded[1] = point.add(openKey.multiply(k));

    System.out.println("{(" + encoded[0].x + ", " + encoded[0].y + "), (" + encoded[1].x + ", " + encoded[1].y + ")}");
    return encoded;
  }

  private static void decode(Point[][] encoded) {
    //Point decoded = encoded[1].subtract(encoded[0].multiply(secretKey));
    Point[] decoded = new Point[encoded.length];

    for (int i = 0; i < decoded.length; i++) {
      decoded[i] = encoded[i][1].subtract(encoded[i][0].multiply(secretKey));
      System.out.print("(" + decoded[i].x + ", " + decoded[i].y + ") ");
    }
  }

  /**
   * Возведение в степень по модулю
   * Код взят с https://brestprog.by/topics/modulo/
   * @param base - то, что возводим в степень
   * @param currentModule - текущий модуль (начинается с module - 2)
   * @return возвращает результат возведения числа в степень по модулю
   */
  private static long pow_mod(long base, long currentModule) {
    if (currentModule == 1) {
      return base;
    }

    if (currentModule % 2 == 0) {
      long t = pow_mod(base, currentModule / 2);
      return t * t % module;
    } else {
      return pow_mod(base, currentModule - 1) * base % module;
    }
  }
}
