package classes;

public class Paint {
  private double x;
  private int y;
  private double r;
  private String result;

  public Paint(double x, int y, double r, String result) {
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

  public int getY() {
    return y;
  }

  public void setY(int y) {
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
