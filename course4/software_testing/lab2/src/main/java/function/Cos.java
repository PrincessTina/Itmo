package function;

public class Cos {
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
   * Вычисляет результат cos(x)
   * @param x - значение угла в радианах
   * @param eps - значение точности вычислений
   * @return - результат cos(x)
   */
  public static double count(double x, double eps) {
    double prevResult;
    double currentResult = 0;
    int n = 0;

    do {
      prevResult = currentResult;
      currentResult += Math.pow(x, 2 * n) * Math.pow(-1, n) / fac(2 * n);
      n++;
    } while (Math.abs(currentResult - prevResult) > eps);

    return currentResult;
  }
}
