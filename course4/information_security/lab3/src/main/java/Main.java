import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws java.io.UnsupportedEncodingException {
    Scanner in = new Scanner(System.in, "UTF-8");
    System.out.println("Source: ");

    String source = in.nextLine();
    String plainText = convertToBinary(new StringBuilder(source));
    String key = convertToBinary(new StringBuilder("keyword"));
    String encoded = encryption(plainText, key);
    String decoded = encryption(convertToBinary(new StringBuilder(encoded)), key);
    PrintStream ps = new PrintStream(System.out, true, "UTF-8");

    ps.println("Encoded: " + encoded);
    ps.println("Decoded: " + decoded);
    in.close();
  }

  private static String encryption(String plainText, String key) {
    LinkedList<String> register1 = new LinkedList<>();
    LinkedList<String> register2 = new LinkedList<>();
    StringBuilder encoded = new StringBuilder();
    final int registerLength = 81;
    int[] register1BitPositions = {80, 9, 4, 2, 0};
    int[] register2BitPositions = {80, 5, 3, 2, 1, 0};
    int lastGamma = 0;

    fillRegisters(register1, register2, registerLength, key);

    for (int i = 0; i < registerLength; i++) { // Прогоняем столько тактов, сколько длина регистра, вхолостую
      lastGamma = runTact(register1, register2, register1BitPositions, register2BitPositions, lastGamma);
    }

    for (int i = 0; i < plainText.length(); i++) {
      int gamma = runTact(register1, register2, register1BitPositions, register2BitPositions, lastGamma);
      int cipher = (Integer.parseInt("" + plainText.charAt(i)) + gamma) % 2;

      encoded.append(cipher);
      lastGamma = gamma;
    }

    return convertToText(encoded);
  }

  private static int runTact(LinkedList<String> register1, LinkedList<String> register2, int[] register1BitPositions,
                              int[] register2BitPositions, int lastGamma) {
    lastGamma = (lastGamma + getRegisterBit(register1) + getRegisterBit(register2)) % 2;

    moveRegisterToRight(register1, register1BitPositions);
    moveRegisterToRight(register2, register2BitPositions);

    return lastGamma;
  }

  private static void moveRegisterToRight(LinkedList<String> register, int[] bitPositions) {
    int newBit = 0;

    for (int bitPosition : bitPositions) {
      newBit += Integer.parseInt(register.get(bitPosition));
    }

    register.add(String.valueOf(newBit % 2));
    register.removeFirst();
  }

  private static int getRegisterBit(LinkedList<String> register) {
    return Integer.parseInt(String.valueOf(register.getFirst()));
  }

  private static String convertToBinary(StringBuilder text) {
    StringBuilder binary = new StringBuilder();

    text = fixCyrillic(text);

    for (int i = 0; i < text.length(); i++) {
      final char c = text.charAt(i);
      StringBuilder charBinary = new StringBuilder(Integer.toBinaryString(c));

      while (charBinary.length() < 8) {
        charBinary.insert(0, "0");
      }

      binary.append(charBinary);
    }

    return binary.toString();
  }

  private static String convertToText(StringBuilder binary) {
    StringBuilder text = new StringBuilder();

    for (String s : binary.toString().split("(?<=\\G.{8})")) {
      text.append((char) Integer.parseInt(s, 2));
    }

    text = fixCyrillic(text);

    return text.toString();
  }

  private static void fillRegisters(LinkedList<String> register1, LinkedList<String> register2, int registerLength, String key) {
    for (int i = 0; i < registerLength; i++) {
      final char c = key.charAt(i % key.length());
      register1.add(String.valueOf(c));
      register2.add(String.valueOf(c));
    }
  }

  private static StringBuilder fixCyrillic(StringBuilder source) {
    StringBuilder fixed = new StringBuilder(source);

    for (int i = 0; i < fixed.length(); i++) {
      char c = fixed.charAt(i);

      if (c == 'ё') {
        fixed.setCharAt(i, (char) 184);
      } else if (c == 'Ё') {
        fixed.setCharAt(i, (char) 168);
      } else if (c == 168) {
        fixed.setCharAt(i, 'Ё');
      } else if (c == 184) {
        fixed.setCharAt(i, 'ё');
      } else if (c > 255) {
        fixed.setCharAt(i, (char) (c - 848));
      } else if (c > 127) {
        fixed.setCharAt(i, (char) (c + 848));
      }
    }

    return fixed;
  }
}
