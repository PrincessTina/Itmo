package Main;

import static java.lang.Math.*;
import static Structure.Alphabet.alphabet;

import Structure.Point;

import java.io.PrintStream;

public class Main {
  public static final int a = -1;
  static final int secretKey = 58;
  static final Point openKey = new Point(286, 136);
  static final Point G = new Point(0, 1);
  static int module = 751;

  public static void main(String[] args) {
    final String message = "низменный";
    final int[] k = {12, 5, 7, 17, 18, 2, 12, 10, 11};
    final Point[][] encoded = {
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

    decode(encoded);
    encode(message, k);
  }

  /**
   * Находит математически корректный остатот от деления
   * - целого числа
   * - дроби
   * - отрицательного числа
   *
   * @param numerator   - числитель
   * @param denominator - знаменатель (если не дробь, ставить в 1)
   * @return возвращает остаток от деления
   */
  public static int mod(int numerator, int denominator) {
    final boolean sign = (double) numerator / denominator < 0;
    long mod;

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

  private static void encode(String message, int[] k) {
    Point[][] encoded = new Point[message.length()][2];

    for (int i = 0; i < message.length(); i++) {
      final Point point = lookForPointInAlphabet(String.valueOf(message.charAt(i)));

      encoded[i][0] = G.multiply(k[i]);
      encoded[i][1] = point.add(openKey.multiply(k[i]));

      System.out.println("{" + encoded[i][0].toString() + ", " + encoded[i][1].toString() + "};");
    }
  }

  private static void decode(Point[][] encoded) {
    StringBuilder message = new StringBuilder();
    Point[] decoded = new Point[encoded.length];

    for (int i = 0; i < decoded.length; i++) {
      decoded[i] = encoded[i][1].subtract(encoded[i][0].multiply(secretKey));
      message.append(lookForSymbolInAlphabet(decoded[i]));

      System.out.print(decoded[i].toString() + " ");
    }

    try {
      PrintStream ps = new PrintStream(System.out, true, "UTF-8");
      ps.println("\n" + message.toString());
    } catch (Exception ignored) {
    }
  }

  /**
   * Поиск точки в алфавите по соответствующему ей символу
   *
   * @param symbol - символ, по которому идет поиск
   * @return возвращает найденную точку
   */
  private static Point lookForPointInAlphabet(String symbol) {
    return alphabet.get(symbol);
  }

  /**
   * Поиск символа в алфавите по соответствующей ему точке
   *
   * @param point - точка, по которой идет поиск
   * @return возвращает найденный символ
   */
  private static String lookForSymbolInAlphabet(Point point) {
    StringBuilder symbol = new StringBuilder();

    alphabet.forEach((key, value) -> {
      if (value.equals(point)) {
        if (key.equals("И")) {
          symbol.append(key.toLowerCase()); // Исправление ошибки, связанной с UTF-8, для "И"
        } else {
          symbol.append(key);
        }
      }
    });

    return symbol.toString();
  }

  /**
   * Возведение в степень по модулю
   * Код взят с https://brestprog.by/topics/modulo/
   *
   * @param base          - то, что возводим в степень
   * @param currentModule - текущий модуль (начинается с module - 2)
   * @return возвращает результат возведения числа в степень по модулю
   */
  private static long pow_mod(long base, long currentModule) {
    if (currentModule == 1) {
      return base;
    }

    if (currentModule % 2 == 0) {
      final long t = pow_mod(base, currentModule / 2);
      return t * t % module;
    } else {
      return pow_mod(base, currentModule - 1) * base % module;
    }
  }
}
