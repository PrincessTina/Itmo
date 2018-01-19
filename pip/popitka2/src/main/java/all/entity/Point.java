package all.entity;

import javax.persistence.*;

@Entity
@Table(name = "point")
public class Point {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cl_seq")
  @SequenceGenerator(name="cl_seq", sequenceName="cl_seq", allocationSize=1)
  private int id;
  private double x;
  private double y;
  private double r;
  private String result;
  private int userid;

  public Point() {}

  public Point(int id, double x, double y, double r, String result, int userid) {
    this.id = id;
    this.x = x;
    this.y = y;
    this.r = r;
    this.result = result;
    this.userid = userid;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

  public double getR() {
    return r;
  }

  public void setR(double r) {
    this.r = r;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public int getUserid() {
    return userid;
  }

  public void setUserid(int userid) {
    this.userid = userid;
  }
}
