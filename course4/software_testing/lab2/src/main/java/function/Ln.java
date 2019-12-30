package function;

/**
 * Натуральный логарифм
 */
public class Ln {
  // Для тестов
  public static void main(String[] args) {
  }

  /**
   * Вычисляет x, необходимый для подстановки в формулу ряда
   * @param m - число, логарифм которого по основанию e мы хотим узнать
   * @return - число x
   */
  private static double countX(double m) {
    return (m - 1) / (1 + m);
  }

  /**
   * Вычисляет результат ln(m), где m = (1+x)/(1-x)
   *
   * @param m - число, логарифм которого по основанию e мы хотим узнать
   * @param eps - значение точности вычислений
   * @return - результат ln(x)
   */
  public static double count(double m, double eps) {
    double prevResult;
    double currentResult = 0;
    double n = 1;
    double x = countX(m);

    if (m == 0)
      return Double.NEGATIVE_INFINITY;

    if (m < 0) {
      return Double.NaN;
    }

    do {
      prevResult = currentResult;
      currentResult += 2 * Math.pow(x, n) / n;
      n += 2;
    } while (Math.abs(currentResult - prevResult) > eps);

    return currentResult;
  }
}
