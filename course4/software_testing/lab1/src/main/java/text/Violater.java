package text;

/**
 * Класс Нарушитель
 */
public class Violater {
  private int count;
  private int punishments = 0;
  private int donePunishments = 0;
  private boolean isViolate = false;
  private boolean opposition = false;

  Violater(int count, boolean isViolate) {
    this.count = count;
    this.isViolate = isViolate;
    opposition = true;
  }

  /**
   * Инкрементирует количество принятых мер против нарушителей
   */
  void incDonePunishments() {
    donePunishments++;
  }

  public int getCount() {
    return count;
  }

  public int getPunishments() {
    return punishments;
  }

  public boolean getPunished() {
    return punishments == donePunishments;
  }

  public boolean getViolate() {
    return isViolate;
  }

  public boolean getOpposition() {
    return opposition;
  }

  public void setPunishments(int punishments) {
    this.punishments = punishments;
  }
}
