package classes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Point {
  @Id
  @GeneratedValue
  private int id;

  private int sessionId;

  private double x;
  private double y;
  private double r;
  private String result;

  public Point(int id, int sessionId, double x, double y, double r, String result) {
    this.id = id;
    this.sessionId = sessionId;
    this.x = x;
    this.y = y;
    this.r = r;
    this.result = result;
  }

  public Point(int sessionId, double x, double y, double r, String result) {
    this.sessionId = sessionId;
    this.x = x;
    this.y = y;
    this.r = r;
    this.result = result;
  }

  public Point(double x, double y, double r, String result) {
    this.x = x;
    this.y = y;
    this.r = r;
    this.result = result;
  }

  public Point() {

  }

  public int getSessionId() {
    return sessionId;
  }

  public void setSessionId(int sessionId) {
    this.sessionId = sessionId;
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
}
