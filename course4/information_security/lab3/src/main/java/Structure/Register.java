package Structure;

import java.util.LinkedList;

/**
 * Линейный регистр сдвига
 */
public class Register {
  private LinkedList<String> buffer = new LinkedList<>();
  private int[] bitPositions;
  private int length;

  public Register(int length, int[] bitPositions, String value) {
    this.length = length;
    this.bitPositions = bitPositions;
    fill(value);
  }

  /**
   * Сдвиг регистра вправо на 1
   * Биты в регистре расположены в порядке с последнего по 0
   */
  public void moveToRight() {
    int newBit = 0;

    for (int bitPosition : bitPositions) {
      newBit += Integer.parseInt(buffer.get(bitPosition));
    }

    buffer.add(String.valueOf(newBit % 2));
    buffer.removeFirst();
  }

  /**
   * Возврат бита регистра, необходимого для алгоритма вычисления гаммы
   *
   * @return возвращает нулевой бит
   */
  public int getBit() {
    return Integer.parseInt(String.valueOf(buffer.getFirst()));
  }

  /**
   * Заполнение регистра ключом
   *
   * @param value - ключ
   */
  private void fill(String value) {
    for (int i = 0; i < length; i++) {
      buffer.add(String.valueOf(value.charAt(i % value.length())));
    }
  }
}
