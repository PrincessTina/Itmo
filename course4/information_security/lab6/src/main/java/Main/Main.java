package Main;

import static Structure.Alphabet.*;
import static java.lang.Math.abs;
import static Main.Log.*;

import Structure.Point;

public class Main {
  public static final int a = -1;
  static final int secretKey = 58;
  static final Point openKey = new Point(286, 136);
  static final Point G = new Point(0, 1);
  static int module = 751;

  public static void main(String[] args) {
    final String plainText = "низменный";
    final int[] k = {12, 5, 7, 17, 18, 2, 12, 10, 11};
    final Point[][] cipherText = {
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

    logInitialParams();
    encode(plainText, k);
    decode(cipherText);
  }

  /**
   * Поиск математически корректного остатка от деления
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

  /**
   * Шифрование
   * Выводит результат на стандартный поток вывода
   *
   * @param plainText - открытый текст
   * @param k         - массив случайных чисел для символов plainText
   */
  private static void encode(String plainText, int[] k) {
    Point[][] cipherText = new Point[plainText.length()][2];

    logEncodingFormula();

    for (int i = 0; i < plainText.length(); i++) {
      final String symbol = String.valueOf(plainText.charAt(i));
      final Point point = getPoint(symbol);
      final Point temp;

      cipherText[i][0] = G.multiply(k[i]);
      temp = openKey.multiply(k[i]);
      cipherText[i][1] = point.add(temp);

      logEncodingTact(symbol, point, cipherText[i][0], temp, cipherText[i][1], k[i]);
    }

    logEncodingResult();
  }

  /**
   * Расшифрование
   * Выводит результат на стандартный поток вывода
   *
   * @param cipherText - шифртекст
   */
  private static void decode(Point[][] cipherText) {
    Point[] plainText = new Point[cipherText.length];

    logDecodingFormula();

    for (int i = 0; i < plainText.length; i++) {
      final Point temp = cipherText[i][0].multiply(secretKey);
      String symbol;

      plainText[i] = cipherText[i][1].subtract(temp);
      symbol = getSymbol(plainText[i]);

      logDecodingTact(cipherText[i][1], cipherText[i][0], temp, plainText[i], symbol);
    }

    logDecodingResult();
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
