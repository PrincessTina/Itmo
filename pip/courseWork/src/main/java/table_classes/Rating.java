package table_classes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Rating {
  @Id
  @GeneratedValue
  private int id;
  private int object_id;
  private int one;
  private int two;
  private int three;
  private int four;
  private int five;

  public Rating() {
  }

  public Rating(int object_id, int one, int two, int three, int four, int five) {
    this.object_id = object_id;
    this.one = one;
    this.two = two;
    this.three = three;
    this.four = four;
    this.five = five;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getObject_id() {
    return object_id;
  }

  public void setObject_id(int object_id) {
    this.object_id = object_id;
  }

  public int getOne() {
    return one;
  }

  public void setOne(int one) {
    this.one = one;
  }

  public int getTwo() {
    return two;
  }

  public void setTwo(int two) {
    this.two = two;
  }

  public int getThree() {
    return three;
  }

  public void setThree(int three) {
    this.three = three;
  }

  public int getFour() {
    return four;
  }

  public void setFour(int four) {
    this.four = four;
  }

  public int getFive() {
    return five;
  }

  public void setFive(int five) {
    this.five = five;
  }
}
