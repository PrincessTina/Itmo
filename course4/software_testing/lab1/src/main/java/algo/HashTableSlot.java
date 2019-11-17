package algo;

/**
 * Ячейка хэш-таблицы
 */
class HashTableSlot {
  int value = 0;
  boolean isDeleted = false;

  HashTableSlot(int value) {
    this.value = value;
    this.isDeleted = false;
  }
}
