package text;

public class Door {
  private final String material = "Стальная";
  private boolean isClosed = false;
  private Captain captain;

  Door() {
    initialize();
  }

  private void initialize() {
    System.out.print(material + " дверь ");
  }

  void close() {
    System.out.print("закрылась");

    isClosed = true;
    pushCaptain();
  }

  private void pushCaptain() {
    if (isClosed && captain != null) {
      captain.leftAloneAgain();
      captain.weirdTalk();
      captain.turnPagesOfPoemsBook();
    }
  }

  public String getMaterial() {
    return material;
  }

  public boolean getClosed() {
    return isClosed;
  }

  public Captain getCaptain() {
    return captain;
  }

  void setCaptain(Captain captain) {
    this.captain = captain;
  }
}
