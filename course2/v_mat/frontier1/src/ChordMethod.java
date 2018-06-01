import static java.lang.Math.*;

class ChordMethod {
  static void calculate(double e) throws Exception{
    double a = -1;
    double b = 10;
    double x;

    while(abs(b - a) > e) {
      x = a - f(a) * (b - a) / (f(b) - f(a));
      a = b;
      b = x;
    }
    System.out.println("Значение: " + getResult(a, e));
  }

  static void calculate2(double e) throws Exception{
    double a = 0;
    double b = 12;
    double x;

    do {
      x = b;
      b = b - f(b) * (b - a) / (f(b) - f(a));
    } while(abs(b - x) >= e);

    System.out.println("Значение: " + getResult(b, e));
  }

  private static double f(double x) throws Exception {
    return x - pow(E, -x)/sin(pow(E, x)) - log(1 + pow(1 - pow(E, 2*x), 0.5));
  }

  private static double getResult(double x, double e) {
    return round(x/e)*e;
  }
}
