package Structure;

public class RTable {
  public int R;
  public double W;
  public double k;
  public double bExact;
  public double bW;
  public double b;
  public double lCalc;
  public double l;
  public int deltaR;
  public double deltaRFact;

  public RTable(int R, int deltaR, double W) {
    this.R = R;
    this.deltaR = deltaR;
    this.W = W;
    bExact = (deltaR == 20) ? 0.2 : (deltaR == 10) ? 0.3 : 0;
  }
}
