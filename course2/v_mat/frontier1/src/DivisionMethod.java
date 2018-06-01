import java.util.Date;

import static java.lang.Math.*;

 class DivisionMethod {
  static void calculate(double e) throws Exception{
    double a = 0;
    double b = 1;
    double c = 0;
    double lastDate = new Date().getTime();
    boolean isNotFound = false;

    while (signum(f(b)) == signum(f(a))) {
      a--;
      b++;

      if (new Date().getTime() - lastDate > 5000) {
        isNotFound = true;
        break;
      }
    }

    if(isNotFound) {
      lastDate = new Date().getTime();
      while (signum(f(b)) == signum(f(a))) {
        a++;
        b--;

        if (new Date().getTime() - lastDate > 5000) {
          break;
        }
      }
    }

    while(abs(b - a) > e) {
      c = (a + b)/2;
      if (f(a)*f(c) > 0) {
        a = c;
      } else {
        b = c;
      }
    }
    System.out.println("Значение: " + getResult(c, e));
  }

  private static double f(double x) {
    return pow(x, 2) - 42*x + 441;
    //return pow(x, 3) - 18*x - 83;
  }

  private static double getResult(double x, double e) {
    return round(x/e)*e;
  }
}
