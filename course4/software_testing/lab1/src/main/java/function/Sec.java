package function;

/**
 * Sec(x) = 1 / Cos(x)
 * Область значения - любые числа
 * В точках PI / 2 +- PI * k, где k - любое целое число, принимает значение бесконечности
 */
public class Sec {
  // Для тестов
  public static void main(String[] args) {}

  /**
   * Считает факториал числа n
   * @param n - число
   * @return - факториал
   */
  private static double fac(int n) {
    return n == 0 ? 1 : n * fac(n - 1);
  }

  /**
   * Вычисляет результат sec(x)
   * @param x - значение угла в радианах
   * @param eps - значение точности вычислений
   * @return - результат sec(x)
   */
  public static double count(double x, double eps) {
    double prevResult;
    double currentResult = 0;
    int n = 0;

    if ((Math.abs(x) - Math.PI / 2) % Math.PI == 0)
      return Double.POSITIVE_INFINITY;

    do {
      prevResult = currentResult;
      currentResult += Math.pow(x, 2 * n) * Math.pow(-1, n) / fac(2 * n);
      n++;
    } while (Math.abs(currentResult - prevResult) > eps);

    return 1 / currentResult;
  }
}
