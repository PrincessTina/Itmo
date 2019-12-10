import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import text.Captain;
import text.Door;
import text.Guard;
import text.Violater;

import java.io.*;

public class TextTest {
  @Before
  public void testFull() {
    Guard guard = new Guard();
    guard.protectCaptain();

    try {
      PrintStream out = new PrintStream(new FileOutputStream("logs.txt"));
      System.setOut(out);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testNullOnViolatersBeforeStart() {
    Guard guard = new Guard();
    Violater violaters = guard.getViolaters();

    assertNull(violaters);
  }

  @Test
  public void testParamsViolatersAfterStart() {
    Guard guard = new Guard();
    guard.protectCaptain();
    Violater violaters = guard.getViolaters();

    assertEquals(violaters.getCount(), 2); // "обоих"
    assertEquals(violaters.getPunishments(), 3); // всего предусмотрено 3 наказания
    assertEquals(violaters.getPunished(), true); // уже наказаны
    assertEquals(violaters.getViolate(), true); // уже виновны
    assertEquals(violaters.getOpposition(), true); // оказывают сопротивление
  }

  @Test
  public void testOtherPunishmentsNumOnViolaters() {
    Guard guard = new Guard();
    guard.protectCaptain();
    Violater violaters = guard.getViolaters();

    violaters.setPunishments(4); // 4 > 3
    assertEquals(violaters.getPunished(), false);

    violaters.setPunishments(2); // 2 < 3
    assertEquals(violaters.getPunished(), false);
  }

  @Test
  public void testParamsCaptainBeforeStart() {
    Guard guard = new Guard();
    Captain captain = guard.getCaptain();

    assertEquals(captain.getAlone(), false); // еще не один
    assertEquals(captain.getSittingBack(), true);
    assertEquals(captain.getBookPlace(), true); // книга на месте
  }

  @Test
  public void testParamsCaptainAfterStart() {
    Guard guard = new Guard();
    guard.protectCaptain();
    Captain captain = guard.getCaptain();

    assertEquals(captain.getAlone(), true); // уже один
    assertEquals(captain.getSittingBack(), true); // по-прежнему сидит спиной
    assertEquals(captain.getBookPlace(), true); // книга на месте
  }

  @Test
  public void testNullOnDoorBeforeStart() {
    Guard guard = new Guard();
    Door door = guard.getCaptainDoor();

    assertNull(door);
  }

  @Test
  public void testParamsDoorAfterStart() {
    Guard guard = new Guard();
    guard.protectCaptain();
    Door door = guard.getCaptainDoor();

    assertEquals(door.getMaterial(), "Стальная");
    assertEquals(door.getClosed(), true); // уже закрыта
    assertEquals(door.getCaptain(), guard.getCaptain());
  }

  @Test
  public void testGuardWorkBeforeStart() {
    Guard guard = new Guard();
    assertEquals(guard.getAllWorkDone(), false);
  }

  @Test
  public void testGuardWorkAfterStart() {
    Guard guard = new Guard();
    guard.protectCaptain();

    assertEquals(guard.getAllWorkDone(), true);
  }

  @Test
  public void testFirstAloneCaptain() {
    Captain captain = new Captain();
    captain.setAlone(true);

    captain.leftAloneAgain();
    captain.weirdTalk();
    captain.turnPagesOfPoemsBook();
  }

  @Test(expected = IllegalStateException.class)
  public void testNotAloneCaptain() {
    Captain captain = new Captain();

    captain.leftAloneAgain();
    captain.weirdTalk();
    captain.turnPagesOfPoemsBook();
  }

  @Test(expected = IllegalStateException.class)
  public void testSecondAloneCaptain() {
    Captain captain = new Captain();
    captain.setAlone(true);
    captain.setAlone(true);

    captain.leftAloneAgain();
    captain.weirdTalk();
    captain.turnPagesOfPoemsBook();
  }
}
