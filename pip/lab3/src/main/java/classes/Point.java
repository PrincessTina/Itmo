package classes;

public class Point {
  private double x;
  private double y;
  private double r;
  private String result;

  public Point(double x, double y, double r, String result) {
    this.x = x;
    this.y = y;
    this.r = r;
    this.result = result;
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
