package algo;

public class HashTable {
  private final int SIZE = 29;
  private HashTableSlot structure[] = new HashTableSlot[SIZE];
  private int actualSize = 0;

  private int hash(int key) {
    return key % SIZE;
  }

  private int findFreeCell(int hash) {
    for (int i = 0; i < SIZE; i++) {
      int cellNumber = (hash + i) % SIZE;

      if (structure[cellNumber] == null || structure[cellNumber].isDeleted) {
        return cellNumber;
      }
    }

    return -1;
  }

  private int findCellWithValue(int value) {
    final int hash = hash(value);

    for (int i = 0; i < SIZE; i++) {
      int cellNumber = (hash + i) % SIZE;

      if (structure[cellNumber] == null)
        return -1;

      if (structure[cellNumber].value == value)
        return cellNumber;
    }

    return -1;
  }

  public boolean add(int value) {
    final int hash = hash(value);
    final int cellNumber = findFreeCell(hash);

    if (cellNumber == -1)
      return false;

    structure[cellNumber] = new HashTableSlot(value);
    actualSize ++;

    return true;
  }

  public boolean remove(int value) {
    final int cellNumber = findCellWithValue(value);

    if (cellNumber == -1)
      return false;

    structure[cellNumber].isDeleted = true;
    actualSize--;

    return true;
  }

  public boolean contains(int value) {
    return findCellWithValue(value) != -1;
  }

  public int size() {
    return actualSize;
  }

  public boolean isEmpty() {
    return actualSize == 0;
  }
}
