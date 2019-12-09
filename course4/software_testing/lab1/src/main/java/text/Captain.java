package text;

public class Captain {
  private boolean isAlone = false;
  private boolean isBookInPlace = true;
  private boolean isSittingBack = true;

  public void leftAloneAgain() throws IllegalStateException {
    if (isAlone) {
      System.out.print(", и капитан снова остался один. ");
    } else {
      throw new IllegalStateException();
    }
  }

  public void weirdTalk() throws IllegalStateException {
    if (isAlone) {
      System.out.print("Он задумчиво промурлыкал что-то");
    } else {
      throw new IllegalStateException();
    }
  }

  public void turnPagesOfPoemsBook() throws IllegalStateException {
    if (isAlone && isBookInPlace) {
      System.out.print(" и полистал свою записную книжку со стихами.");
    } else {
      throw new IllegalStateException();
    }
  }

  public boolean getAlone() {
    return isAlone;
  }

  public boolean getBookPlace() {
    return isBookInPlace;
  }

  public boolean getSittingBack() {
    return isSittingBack;
  }

  public void setAlone(boolean alone) {
    this.isAlone = alone && !this.isAlone;
  }
}
