package Structure;

/**
 * Параметры материала резистивной пленки
 */
public class ResistorMaterialParams {
  public final String materialName;
  public final String p;
  public final String R;
  public final int pBottom;
  public final int pTop;
  public final int RBottom;
  public final int RTop;
  public final int W0;

  public ResistorMaterialParams(String materialName, int pBottom, int pTop, int RBottom, int RTop, int W0) {
    this.materialName = materialName;
    this.p = pBottom + " - " + pTop;
    this.R = RBottom + " - " + RTop;
    this.pBottom = pBottom;
    this.pTop = pTop;
    this.RBottom = RBottom;
    this.RTop = RTop;
    this.W0 = W0;
  }
}
