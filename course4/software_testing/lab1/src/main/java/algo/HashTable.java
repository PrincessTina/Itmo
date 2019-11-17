package algo;

/**
 * Хэш-таблица с открытой адресацией (закрытым шифрованием)
 */
public class HashTable {
  private final int SIZE = 29;
  private HashTableSlot structure[] = new HashTableSlot[SIZE];
  private int actualSize = 0;

  /**
   * Считает hash по ключу (значению) на основе простой хэш-функции
   * @param key - ключ (значение)
   * @return - возвращает хэш
   */
  private int hash(int key) {
    return key % SIZE;
  }

  /**
   * Находит свободную ячейку
   * Свободной считается ячейка, в которой никогда не было элемента, или из которой элемент был удален
   * @param hash - хэш
   * @return - возвращает номер свободной ячейки или -1
   */
  private int findFreeCell(int hash) {
    for (int i = 0; i < SIZE; i++) {
      final int cellNumber = (hash + i) % SIZE;

      if (structure[cellNumber] == null || structure[cellNumber].isDeleted) {
        return cellNumber;
      }
    }

    return -1;
  }

  /**
   * Находит ячейку с элементом с заданным значением
   * @param value - значение
   * @return - возвращает номер ячейки или -1
   */
  private int findCellWithValue(int value) {
    final int hash = hash(value);

    for (int i = 0; i < SIZE; i++) {
      final int cellNumber = (hash + i) % SIZE;

      if (structure[cellNumber] == null)
        return -1;

      if (structure[cellNumber].value == value && !structure[cellNumber].isDeleted)
        return cellNumber;
    }

    return -1;
  }

  /**
   * Добавляет значение в определенную хэшом ячейку
   * @param value - значение
   * @return - возвращает true / false в случае успеха / неудачи
   */
  public boolean add(int value) {
    final int hash = hash(value);
    final int cellNumber = findFreeCell(hash);

    if (cellNumber == -1)
      return false;

    structure[cellNumber] = new HashTableSlot(value);
    actualSize ++;

    return true;
  }

  /**
   * Удаляет значение из ячейки
   * @param value - значение
   * @return - возвращает true / false в случае успеха / неудачи
   */
  public boolean remove(int value) {
    final int cellNumber = findCellWithValue(value);

    if (cellNumber == -1)
      return false;

    structure[cellNumber].isDeleted = true;
    actualSize--;

    return true;
  }

  /**
   * Ищет заданное значение в хэш-таблице
   * @param value - значение
   * @return - возвращает true / false в случае если значение найдено / не найдено
   */
  public boolean contains(int value) {
    return findCellWithValue(value) != -1;
  }

  /**
   * Возвращает размер хэш-таблицы
   * @return - int от 0
   */
  public int size() {
    return actualSize;
  }

  /**
   * Определяет, пуста ли хэш-таблица
   * @return - возвращает true / false в случае если в хэш-таблице нет ни одного значения / иначе
   */
  public boolean isEmpty() {
    return actualSize == 0;
  }
}
