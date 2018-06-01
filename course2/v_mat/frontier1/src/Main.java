import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    try {
      System.out.println("Введите, пожалуйста, точность");
      ChordMethod.calculate(Double.parseDouble(new Scanner(System.in).nextLine()));
      //DivisionMethod.calculate(Double.parseDouble(new Scanner(System.in).nextLine()));
     // NewtonMethod.calculate(Double.parseDouble(new Scanner(System.in).nextLine()));
    } catch (NumberFormatException ex) {
      System.out.println("Введите, пожалуйста, в следующий раз адекватное значение точности");
    } catch (Exception ex) {
      System.out.println("Неопознанная ошибка в методе подсчета");
    }
  }
}
