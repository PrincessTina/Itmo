import java.util.Scanner;

//ToDO: Пробел парсить самой
public class Main {
  public static void main(String[] args) {
    //for (int i = 0; i < 10000000; i++) {
     //if ((25 * i) % 495808 == 1) {
       //System.out.println(i);
       //break;
     //}

    System.out.println(Math.pow(51, 7) % 33);
    System.out.println(Math.pow(6, 3) % 33);
    }


  private void communicate() {
    Scanner scanner = new Scanner(System.in);

    StringBuilder a = new StringBuilder();
    while (true) {
      String string = scanner.nextLine();

      for (int i = 0; i < string.length(); i++) {
        int digit = (string.charAt(i) + 874) * 3 - 10;
        String b = Character.toString((char) ((digit + 10) / 3 - 874));
        a.append(digit);
        a.append("(");
        a.append(b);
        a.append(") ");
      }
      System.out.println(a.toString());
      System.out.println();
    }
  }
}