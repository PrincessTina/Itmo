import static java.lang.Math.*;

class NewtonMethod {
  static void calculate(double e) throws Exception{
    double a = 1;
    double x1;
    double x2;

    do {
      x1 = a;
      x2 = x1 - f(x1)/ff(x1);
      a = x2;
    } while(abs(x2 - x1) > e);
    System.out.println("Значение: " + getResult(x2, e));
  }

  private static double ff(double x) {
    return (f(x + 0.0001) - f(x))/0.0001;
  }

  private static double f(double x) {
    //return pow(x, 2) - 42*x + 441;
    return pow(x, 3) - 18*x - 83;
  }

  private static double getResult(double x, double e) {
    return round(x/e)*e;
  }
}
