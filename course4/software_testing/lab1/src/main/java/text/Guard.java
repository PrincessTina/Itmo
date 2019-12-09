package text;

public class Guard {
  private boolean isAllWorkDone = false;
  private Door captainDoor;
  private Captain captain = new Captain();
  private Violater violaters;

  public Guard() {
    initialize();
  }

  private void initialize() {
    System.out.print("Охранник");
  }

  private void grabNecks() {
    if (violaters.getViolate() && violaters.getCount() == 2) {
      System.out.print(" обхватил их обоих за шеи");
      violaters.incDonePunishments();
    }
  }

  private void bowedToCaptain() {
    if (captain.getSittingBack()) {
      System.out.print(" и, почтительно поклонившись спине капитана, ");
    }
  }

  private void bringOutFromBridge() {
    if (violaters.getViolate()) {
      System.out.print("выволок с мостика");
      violaters.incDonePunishments();
    }
  }

  private void ignoreOpposing() {
    if (violaters.getViolate() && violaters.getOpposition()) {
      System.out.print(", не обращая внимания на их сопротивление. ");
      violaters.incDonePunishments();
    }
  }

  public void protectCaptain() {
    violaters = new Violater(2, true);
    violaters.setPunishments(3);

    grabNecks();
    bowedToCaptain();
    bringOutFromBridge();
    ignoreOpposing();

    if (violaters.getViolate() && violaters.getPunished()) {
      isAllWorkDone = true;
    }

    pushEvents();
  }

  private void pushEvents() {
    if (isAllWorkDone) {
      captainDoor = new Door();

      captain.setAlone(true);
      captainDoor.setCaptain(captain);
      captainDoor.close();
    }
  }

  public boolean getAllWorkDone() {
    return isAllWorkDone;
  }

  public Door getCaptainDoor() {
    return captainDoor;
  }

  public Captain getCaptain() {
    return captain;
  }

  public Violater getViolaters() {
    return violaters;
  }
}
