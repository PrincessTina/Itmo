package beans;

import classes.Paint;
import java.util.ArrayList;

import java.io.Serializable;
import java.util.Arrays;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "paint")
@SessionScoped
public class PaintBean implements Serializable {
  private double x;
  private int y;
  private double r;
  private String result;
  private static final ArrayList<Paint> paints = new ArrayList<>(Arrays.asList(
      new Paint(1.0, -4, 3.0, "better"),
      new Paint(2.0, 2, 3.0, "worse"),
      new Paint(3.0, 0, 3.0, "bad"),
      new Paint(4.0, 12, 3.0, "bad"),
      new Paint(5.0, -9, 3.0, "good")
  ));

  public void add() {
    paints.add(new Paint(this.x, this.y, this.r, this.result));
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

  public void setResult(String  result) {
    this.result = result;
  }

  public ArrayList<Paint> getPaints() {
    return paints;
  }
}
