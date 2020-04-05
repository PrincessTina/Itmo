package Structure;

import java.util.Map;
import java.util.HashMap;

/**
 * Алфавит символов, в котором каждому символу сопоставлена точка эллиптической кривой
 */
public class Alphabet {
  private static final Map<String, Point> alphabet = new HashMap<>();

  static {
    alphabet.put(" ", new Point(33, 355));
    alphabet.put("!", new Point(33, 396));
    alphabet.put("\"", new Point(34, 74));
    alphabet.put("#", new Point(34, 677));
    alphabet.put("$", new Point(36, 87));
    alphabet.put("%", new Point(36, 664));
    alphabet.put("&", new Point(39, 171));
    alphabet.put("'", new Point(39, 580));
    alphabet.put("(", new Point(43, 224));
    alphabet.put(")", new Point(43, 527));
    alphabet.put("*", new Point(44, 366));
    alphabet.put("+", new Point(44, 385));
    alphabet.put(",", new Point(45, 31));
    alphabet.put("-", new Point(45, 720));
    alphabet.put(".", new Point(47, 349));
    alphabet.put("/", new Point(47, 402));
    alphabet.put("0", new Point(48, 49));
    alphabet.put("1", new Point(48, 702));
    alphabet.put("2", new Point(49, 183));
    alphabet.put("3", new Point(49, 568));
    alphabet.put("4", new Point(53, 277));
    alphabet.put("5", new Point(53, 474));
    alphabet.put("6", new Point(56, 332));
    alphabet.put("7", new Point(56, 419));
    alphabet.put("8", new Point(58, 139));
    alphabet.put("9", new Point(58, 612));
    alphabet.put(":", new Point(59, 365));
    alphabet.put(";", new Point(59, 386));
    alphabet.put("<", new Point(61, 129));
    alphabet.put("=", new Point(61, 622));
    alphabet.put(">", new Point(62, 372));
    alphabet.put("?", new Point(62, 379));
    alphabet.put("@", new Point(66, 199));
    alphabet.put("A", new Point(66, 552));
    alphabet.put("B", new Point(67, 84));
    alphabet.put("C", new Point(67, 667));
    alphabet.put("D", new Point(69, 241));
    alphabet.put("E", new Point(69, 510));
    alphabet.put("F", new Point(70, 195));
    alphabet.put("G", new Point(70, 556));
    alphabet.put("H", new Point(72, 254));
    alphabet.put("I", new Point(72, 497));
    alphabet.put("J", new Point(73, 72));
    alphabet.put("K", new Point(73, 679));
    alphabet.put("L", new Point(74, 170));
    alphabet.put("M", new Point(74, 581));
    alphabet.put("N", new Point(75, 318));
    alphabet.put("O", new Point(75, 433));
    alphabet.put("P", new Point(78, 271));
    alphabet.put("Q", new Point(78, 480));
    alphabet.put("R", new Point(79, 111));
    alphabet.put("S", new Point(79, 640));
    alphabet.put("T", new Point(80, 318));
    alphabet.put("U", new Point(80, 433));
    alphabet.put("V", new Point(82, 270));
    alphabet.put("W", new Point(82, 481));
    alphabet.put("X", new Point(83, 373));
    alphabet.put("Y", new Point(83, 378));
    alphabet.put("Z", new Point(85, 35));
    alphabet.put("[", new Point(85, 716));
    alphabet.put("\\", new Point(86, 25));
    alphabet.put("]", new Point(86, 726));
    alphabet.put("^", new Point(90, 21));
    alphabet.put("_", new Point(90, 730));
    alphabet.put("`", new Point(93, 267));
    alphabet.put("a", new Point(93, 484));
    alphabet.put("b", new Point(98, 338));
    alphabet.put("c", new Point(98, 413));
    alphabet.put("d", new Point(99, 295));
    alphabet.put("e", new Point(99, 456));
    alphabet.put("f", new Point(100, 364));
    alphabet.put("g", new Point(100, 387));
    alphabet.put("h", new Point(102, 267));
    alphabet.put("i", new Point(102, 484));
    alphabet.put("j", new Point(105, 369));
    alphabet.put("k", new Point(105, 382));
    alphabet.put("l", new Point(106, 24));
    alphabet.put("m", new Point(106, 727));
    alphabet.put("n", new Point(108, 247));
    alphabet.put("o", new Point(108, 504));
    alphabet.put("p", new Point(109, 200));
    alphabet.put("q", new Point(109, 551));
    alphabet.put("r", new Point(110, 129));
    alphabet.put("s", new Point(110, 622));
    alphabet.put("t", new Point(114, 144));
    alphabet.put("u", new Point(114, 607));
    alphabet.put("v", new Point(115, 242));
    alphabet.put("w", new Point(115, 509));
    alphabet.put("x", new Point(116, 92));
    alphabet.put("y", new Point(116, 659));
    alphabet.put("z", new Point(120, 147));
    alphabet.put("{", new Point(120, 604));
    alphabet.put("|", new Point(125, 292));
    alphabet.put("}", new Point(125, 459));
    alphabet.put("~", new Point(126, 33));
    alphabet.put("А", new Point(189, 297));
    alphabet.put("Б", new Point(189, 454));
    alphabet.put("В", new Point(192, 32));
    alphabet.put("Г", new Point(192, 719));
    alphabet.put("Д", new Point(194, 205));
    alphabet.put("Е", new Point(194, 546));
    alphabet.put("Ж", new Point(197, 145));
    alphabet.put("З", new Point(197, 606));
    alphabet.put("И", new Point(198, 224));
    alphabet.put("Й", new Point(198, 527));
    alphabet.put("К", new Point(200, 30));
    alphabet.put("Л", new Point(200, 721));
    alphabet.put("М", new Point(203, 324));
    alphabet.put("Н", new Point(203, 427));
    alphabet.put("О", new Point(205, 372));
    alphabet.put("П", new Point(205, 379));
    alphabet.put("Р", new Point(206, 106));
    alphabet.put("С", new Point(206, 645));
    alphabet.put("Т", new Point(209, 82));
    alphabet.put("У", new Point(209, 669));
    alphabet.put("Ф", new Point(210, 31));
    alphabet.put("Х", new Point(210, 720));
    alphabet.put("Ц", new Point(215, 247));
    alphabet.put("Ч", new Point(215, 504));
    alphabet.put("Ш", new Point(218, 150));
    alphabet.put("Щ", new Point(218, 601));
    alphabet.put("Ъ", new Point(221, 138));
    alphabet.put("Ы", new Point(221, 613));
    alphabet.put("Ь", new Point(226, 9));
    alphabet.put("Э", new Point(226, 742));
    alphabet.put("Ю", new Point(227, 299));
    alphabet.put("Я", new Point(227, 452));
    alphabet.put("а", new Point(228, 271));
    alphabet.put("б", new Point(228, 480));
    alphabet.put("в", new Point(229, 151));
    alphabet.put("г", new Point(229, 600));
    alphabet.put("д", new Point(234, 164));
    alphabet.put("е", new Point(234, 587));
    alphabet.put("ж", new Point(235, 19));
    alphabet.put("з", new Point(235, 732));
    alphabet.put("и", new Point(236, 39));
    alphabet.put("й", new Point(236, 712));
    alphabet.put("к", new Point(237, 297));
    alphabet.put("л", new Point(237, 454));
    alphabet.put("м", new Point(238, 175));
    alphabet.put("н", new Point(238, 576));
    alphabet.put("о", new Point(240, 309));
    alphabet.put("п", new Point(240, 442));
    alphabet.put("р", new Point(243, 87));
    alphabet.put("с", new Point(243, 664));
    alphabet.put("т", new Point(247, 266));
    alphabet.put("у", new Point(247, 485));
    alphabet.put("ф", new Point(249, 183));
    alphabet.put("х", new Point(249, 568));
    alphabet.put("ц", new Point(250, 14));
    alphabet.put("ч", new Point(250, 737));
    alphabet.put("ш", new Point(251, 245));
    alphabet.put("щ", new Point(251, 506));
    alphabet.put("ъ", new Point(253, 211));
    alphabet.put("ы", new Point(253, 540));
    alphabet.put("ь", new Point(256, 121));
    alphabet.put("э", new Point(256, 630));
    alphabet.put("ю", new Point(257, 293));
    alphabet.put("я", new Point(257, 458));
  }

  /**
   * Поиск символа в алфавите по соответствующей ему точке
   *
   * @param point - точка, по которой идет поиск
   * @return возвращает найденный символ
   */
  public static String getSymbol(Point point) {
    StringBuilder symbol = new StringBuilder();

    alphabet.forEach((key, value) -> {
      if (value.equals(point)) {
        if (key.equals("И")) {
          symbol.append(key.toLowerCase()); // Исправление ошибки, связанной с UTF-8, для "И"
        } else {
          symbol.append(key);
        }
      }
    });

    return symbol.toString();
  }

  /**
   * Поиск точки в алфавите по соответствующему ей символу
   *
   * @param symbol - символ, по которому идет поиск
   * @return возвращает найденную точку
   */
  public static Point getPoint(String symbol) {
    return alphabet.get(symbol);
  }
}
