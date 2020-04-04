package Main;

import Structure.Point;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static Main.Main.*;
import static java.lang.Math.pow;

/**
 * Вывод на стандартный поток вывода
 */
public class Log {
  private static final boolean isOutputVerbose = true; // Если стоит true, вывод будет с подробными расчетами
  private static PrintStream ps; // Необходим для вывода кириллицы в кодировке UTF-8
  private static StringBuilder result = new StringBuilder("\nРезультат:\n");
  private static int lambda;
  private static int lambdaSquare;
  private static int x3;
  private static int y3;

  static {
    try {
      ps = new PrintStream(System.out, true, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

  /**
   * Вывод постоянных параметров системы
   */
  public static void logInitialParams() {
    System.out.printf("G = %s, Pb = %s, n = %d\n", G.toString(), openKey.toString(), secretKey);
  }

  /**
   * Вывод формулы шифрования
   */
  public static void logEncodingFormula() {
    ps.println("\nШифрование:\nCm = {kG, Pm + kPb}");
  }

  /**
   * Подробный вывод расчетов шифрования одного символа
   *
   * @param symbol - символ, который необходимо зашифровать
   * @param point  - точка, соответствующая символу в алфавите
   * @param kg     - первая точка шифра
   * @param kPb    - результат умножения k на открытый ключ
   * @param PmkPb  - вторая точка шифра
   * @param k      - случайное число
   */
  public static void logEncodingTact(String symbol, Point point, Point kg, Point kPb, Point PmkPb, int k) {
    final String tactResult = String.format("{%s, %s};\n", kg.toString(), PmkPb.toString());

    result.append(tactResult);

    if (!isOutputVerbose) {
      return;
    }

    ps.printf("\nPm = '%s' %s, k = %d;\n", symbol, point.toString(), k);
    ps.printf("kG = %d * %s = %s;\n", k, G.toString(), kg.toString());
    ps.printf("Pm + kPb = %s + %d * %s = %s + %s = (x3 = %d, y3 = %d), где\n", point.toString(), k, openKey.toString(),
        point.toString(), kPb.toString(), PmkPb.x, PmkPb.y);
    ps.printf("l = ((y2 - y1) / (x2 - x1)) mod %d = ((%d - %d) / (%d - %d)) mod %d = %d/%d mod %d = %d;\n",
        module, kPb.y, point.y, kPb.x, point.x, module, kPb.y - point.y, kPb.x - point.x, module, lambda);
    ps.printf("x3 = (l^2 - x2 - x1) mod %d = (%d - %d - %d) mod %d = %d mod %d = %d;\n", module, lambdaSquare, kPb.x,
        point.x, module, lambdaSquare - kPb.x - point.x, module, x3);
    ps.printf("y3 = (l * (x1 - x3) - y1) mod %d = (%d * (%d - %d) - %d) mod %d = %d mod %d = %d;\n", module, lambda,
        point.x, x3, point.y, module, lambda * (point.x - x3) - point.y, module, y3);
    ps.print(tactResult);
  }

  /**
   * Вывод шифра открытого текста
   */
  public static void logEncodingResult() {
    ps.print(result);
    result = new StringBuilder("\nРезультат:\n");
  }

  /**
   * Вывод формулы расшифрования
   */
  public static void logDecodingFormula() {
    ps.println("\nРасшифрование:\nPm = (Pm + kPb) - nkG");
  }

  /**
   * Подробный вывод расчетов расшифрования одного шифра
   *
   * @param PmkPb    - вторая точка шифра
   * @param kg       - первая точка шифра
   * @param minusNkG - обратная точка для nkG
   * @param sum      - точка, являющаяся результатом расшифрования
   * @param symbol   - символ, соответствующий результатирующей точке в алфавите
   */
  public static void logDecodingTact(Point PmkPb, Point kg, Point minusNkG, Point sum, String symbol) {
    final Point nkG = new Point(minusNkG.x, -minusNkG.y);

    result.append(symbol);

    if (!isOutputVerbose) {
      return;
    }

    ps.printf("\nkG = %s, Pm + kPb = %s;\n", kg.toString(), PmkPb.toString());
    ps.printf("Pm = %s - %d * %s = %s - %s = %s + %s = (x3 = %d, y3 = %d) = '%s', где\n", PmkPb.toString(), secretKey,
        kg.toString(), PmkPb.toString(), nkG.toString(), PmkPb.toString(), minusNkG.toString(), sum.x, sum.y, symbol);
    ps.printf("l = ((y2 - y1) / (x2 - x1)) mod %d = ((%d - %d) / (%d - %d)) mod %d = %d/%d mod %d = %d;\n",
        module, minusNkG.y, PmkPb.y, minusNkG.x, PmkPb.x, module, minusNkG.y - PmkPb.y, minusNkG.x - PmkPb.x, module, lambda);
    ps.printf("x3 = (l^2 - x2 - x1) mod %d = (%d - %d - %d) mod %d = %d mod %d = %d;\n", module, lambdaSquare, minusNkG.x,
        PmkPb.x, module, lambdaSquare - minusNkG.x - PmkPb.x, module, x3);
    ps.printf("y3 = (l * (x1 - x3) - y1) mod %d = (%d * (%d - %d) - %d) mod %d = %d mod %d = %d;\n", module, lambda,
        PmkPb.x, x3, PmkPb.y, module, lambda * (PmkPb.x - x3) - PmkPb.y, module, y3);
    ps.printf("'%s' %s\n", symbol, sum.toString());
  }

  /**
   * Вывод расшифрованного открытого текста
   */
  public static void logDecodingResult() {
    ps.println(result);
    result = new StringBuilder("\nРезультат:\n");
  }

  /**
   * Установка параметров временного буфера из класса Point, полученных в результате вычислений
   *
   * @param lambdaFromPoint - lambda, необходимая для расчета x3
   * @param x3FromPoint     - x3, абсцисса результата вычислений
   * @param y3FromPoint     - y3, ордината результата вычислений
   */
  public static void setBufParams(int lambdaFromPoint, int x3FromPoint, int y3FromPoint) {
    lambda = lambdaFromPoint;
    lambdaSquare = (int) pow(lambda, 2);
    x3 = x3FromPoint;
    y3 = y3FromPoint;
  }
}
