package text;

/**
 * Класс Охранник
 * Охраняет каюту капитана
 */
public class Guard {
  private boolean isAllWorkDone = false;
  private Door captainDoor;
  private Captain captain = new Captain();
  private Violater violaters;

  public Guard() {
    initialize();
  }

  /**
   * Инициализируется, выводя себя
   */
  private void initialize() {
    System.out.print("Охранник");
  }

  /**
   * Метод, который отвечает за кусочек текста, где охранник обхватывает шеи нарушителей
   * Первое наказание для нарушителей
   */
  private void grabNecks() {
    if (violaters.getViolate() && violaters.getCount() == 2) {
      System.out.print(" обхватил их обоих за шеи");
      violaters.incDonePunishments();
    }
  }

  /**
   * Охранник кланяется спине капитана, если тот повернут спиной (всегда)
   */
  private void bowedToCaptain() {
    if (captain.getSittingBack()) {
      System.out.print(" и, почтительно поклонившись спине капитана, ");
    }
  }

  /**
   * Метод, который отвечает за кусочек текста, где охранник убирает нарушителей с мостика
   * Второе наказание для нарушителей
   */
  private void bringOutFromBridge() {
    if (violaters.getViolate()) {
      System.out.print("выволок с мостика");
      violaters.incDonePunishments();
    }
  }

  /**
   * Метод, который отвечает за кусочек текста, где охранник не обращает внимание на сопротивление нарушителей
   * Третье наказание для нарушителей
   */
  private void ignoreOpposing() {
    if (violaters.getViolate() && violaters.getOpposition()) {
      System.out.print(", не обращая внимания на их сопротивление. ");
      violaters.incDonePunishments();
    }
  }

  /**
   * Защищает капитана, отгоняя от него нарушителей, если они есть
   */
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

  /**
   * Передает управление двери каюты капитана
   */
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
