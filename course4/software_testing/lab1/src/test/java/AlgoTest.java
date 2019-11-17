import algo.HashTable;
import org.junit.Test;

public class AlgoTest {
  public static void main(String[] args) {
    HashTable hashTable = new HashTable();
    hashTable.remove(2);
    hashTable.add(4);
    hashTable.add(5);
    hashTable.add(5);
    hashTable.remove(0);
    hashTable.remove(5);
    hashTable.add(5);
  }

  public void test() {}
}
