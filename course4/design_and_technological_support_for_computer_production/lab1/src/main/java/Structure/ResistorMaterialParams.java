package Structure;

public class ResistorMaterialParams {
  private String materialName;
  public int pBottom;
  public int pTop;
  public int RBottom;
  public int RTop;
  public int W0;

  public ResistorMaterialParams(String materialName, int pBottom, int pTop, int RBottom, int RTop, int W0) {
    this.materialName = materialName;
    this.pBottom = pBottom;
    this.pTop = pTop;
    this.RBottom = RBottom;
    this.RTop = RTop;
    this.W0 = W0;
  }
}
