import algo.HashTable;
import org.junit.Test;
import static org.junit.Assert.*;

public class AlgoTest {
  private HashTable hashTable = new HashTable();

  @Test
  public void testSizeOfEmptyTable() {
    assertEquals(0, hashTable.size());
  }

  @Test
  public void testEmptinessOfEmptyTable() {
    assertEquals(true, hashTable.isEmpty());
  }

  @Test
  public void testContainedInEmptyTable() {
    int value = (int) (Math.random() * 100);
    assertEquals(false, hashTable.contains(value));
  }

  @Test
  public void testRemoveFromEmptyTable() {
    int value = (int) (Math.random() * 100);
    assertEquals(false, hashTable.remove(value));
  }

  @Test
  public void testAddInEmptyTable() {
    int value = (int) (Math.random() * 100);
    assertEquals(true, hashTable.add(value));
  }

  @Test
  public void testSizeOfNonEmptyTable() {
    int value = (int) (Math.random() * 100);
    hashTable.add(value);

    assertEquals(1, hashTable.size());
  }

  @Test
  public void testEmptinessOfNonEmptyTable() {
    int value = (int) (Math.random() * 100);
    hashTable.add(value);

    assertEquals(false, hashTable.isEmpty());
  }

  @Test
  public void testContainedInNonEmptyTable() {
    int value = (int) (Math.random() * 100);
    hashTable.add(value);

    assertEquals(true, hashTable.contains(value));
  }

  @Test
  public void testContainedNonexistentInNonEmptyTable() {
    int value = (int) (Math.random() * 100);
    int nonExistentValue = value - 1;

    hashTable.add(value);

    assertEquals(false, hashTable.contains(nonExistentValue));
  }

  @Test
  public void testRemoveNonexistentFromNonEmptyTable() {
    int value = (int) (Math.random() * 100);
    int nonExistentValue = value - 1;

    hashTable.add(value);

    assertEquals(false, hashTable.remove(nonExistentValue));
  }

  @Test
  public void testRemoveFromNonEmptyTable() {
    int value = (int) (Math.random() * 100);
    hashTable.add(value);

    assertEquals(true, hashTable.remove(value));
  }

  @Test
  public void testSizeOfTableAfterRemoving() {
    int value = (int) (Math.random() * 100);

    hashTable.add(value);
    hashTable.remove(value);

    assertEquals(0, hashTable.size());
  }

  @Test
  public void testEmptinessOfTableAfterRemoving() {
    int value = (int) (Math.random() * 100);

    hashTable.add(value);
    hashTable.remove(value);

    assertEquals(true, hashTable.isEmpty());
  }

  @Test
  public void testAddSeveralInEmptyTable() {
    for (int i = 2; i < 5; i++) {
      for (int j = 0; j < i; j++) {
        assertEquals(true, hashTable.add(i));
      }
    }

    assertEquals(9, hashTable.size());
    assertEquals(false, hashTable.isEmpty());
  }

  @Test
  public void testRemoveAllFromTable() {
    testAddSeveralInEmptyTable();

    for (int i = 2; i < 5; i++) {
      while (hashTable.contains(i)) {
        assertEquals(true, hashTable.remove(i));
      }
    }

    assertEquals(0, hashTable.size());
    assertEquals(true, hashTable.isEmpty());
  }
}