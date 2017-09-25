import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

import java.io.*;
import java.util.*;

import static java.lang.Math.pow;

public class Interface extends Application {
  static VBox solveBox = new VBox();
  static VBox answerBox = new VBox();
  private static VBox matrix = new VBox();
  private static VBox memsColumn = new VBox();
  private static int n = 2;
  private static HashMap<Integer, TextField> fieldMap = new HashMap<>();
  private static HashMap<Integer, HBox> strokeMap = new HashMap<>();
  private static HashMap<Integer, TextField> memsMap = new HashMap<>();

  static void createInterface() {
    launch();
  }

  public void start(Stage stage) {
    Button randButton = new Button("Случайные числа");
    Button fileButton = new Button("Выбрать файл");
    Button solveButton = new Button("Решить");
    Button resetButton = new Button("Сбросить");
    randButton.setId("button");
    fileButton.setId("button");
    solveButton.setId("button");
    resetButton.setId("button");
    Button plus = new Button("+");
    Button minus = new Button("-");
    plus.setId("sign");
    minus.setId("sign");
    plus.setPrefWidth(55);
    minus.setPrefWidth(55);

    VBox right = new VBox(randButton, fileButton, resetButton, solveButton);
    right.setId("r");

    resizeMatrix();

    ScrollPane solvePane = new ScrollPane(solveBox);
    solvePane.setPrefSize(590, 370);
    solvePane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    solvePane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    solvePane.setId("sP");

    ScrollPane answerPane = new ScrollPane(answerBox);
    answerPane.setPrefSize(590, 370);
    answerPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    answerPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    answerPane.setId("aP");

    ScrollPane leftPane = new ScrollPane(new VBox(new HBox(plus, minus), new HBox(matrix, memsColumn)));
    leftPane.setPrefSize(980, 370);
    leftPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    leftPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

    VBox mainVBox = new VBox(new HBox(leftPane, right), new HBox(answerPane, solvePane));

    VBox.setMargin(randButton, new Insets(20, 20, 0, 20));
    VBox.setMargin(fileButton, new Insets(20, 20, 0, 20));
    VBox.setMargin(solveButton, new Insets(20, 20, 0, 20));
    VBox.setMargin(resetButton, new Insets(20, 20, 0, 20));

    HBox.setMargin(plus, new Insets(20, 0, 0, 20));
    HBox.setMargin(minus, new Insets(20, 0, 0, 20));

    Scene scene = new Scene(mainVBox, 1180, 740);
    scene.getStylesheets().add(Interface.class.getResource("main.css").toExternalForm());
    stage.setScene(scene);
    stage.show();

    //Events
    solveButton.setOnAction(event -> {
      solveBox.getChildren().clear();
      answerBox.getChildren().clear();

      try {
        allocation();
        Method.calculation();
      } catch (Exception ex) {
        Text text = new Text("Проверьте корректность введенных данных");
        text.setWrappingWidth(250);
        text.setTextAlignment(TextAlignment.CENTER);
        VBox.setMargin(text, new Insets(10, 10, 0, 10));

        Scene scene2 = new Scene(new VBox(text), 250, 100);
        Stage stage2 = new Stage(StageStyle.DECORATED);
        stage2.initModality(Modality.APPLICATION_MODAL);
        stage2.setScene(scene2);
        stage2.show();
      }
    });

    plus.setOnAction(event -> {
      n++;
      resizeMatrix();
    });

    minus.setOnAction(event -> {
      if (n > 2) {
        n--;
        resizeMatrix();
      }
    });

    fileButton.setOnAction(event -> readFromFile());

    resetButton.setOnAction(event -> reset());

    randButton.setOnAction(event -> randomFilling());
  }

  private void readFromFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Document");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));
    File file = fileChooser.showOpenDialog(new Stage());

    try (FileReader fileReader = new FileReader(file)) {
      int c;
      int s = 0;
      StringBuilder builder = new StringBuilder();

      while ((c = fileReader.read()) != -1) {
        builder.append((char) c);
      }

      String[] array = builder.toString().split(";");
      n = Integer.parseInt(array[0]);
      resizeMatrix();

      for (String coef : array[1].split(",")) {
        fieldMap.get(s).setText(coef);
        s++;
      }
      s = 0;

      for (String coef : array[2].split(",")) {
        memsMap.get(s).setText(coef);
        s++;
      }
    } catch (Exception ex) {
      System.out.println("error");
    }
  }

  private void randomFilling() {
    Random random = new Random();

    for (int s = 0; s < memsMap.size(); s++) {
      memsMap.get(s).setText(String.format(Locale.ENGLISH, "%.2f",
          100 * random.nextFloat() * pow(-1, random.nextInt(2))));
    }

    for (int s = 0; s < fieldMap.size(); s++) {
      fieldMap.get(s).setText(String.format(Locale.ENGLISH, "%.2f",
          100 * random.nextFloat() * pow(-1, random.nextInt(2))));
    }
  }

  private void reset() {
    for (int s = 0; s < memsMap.size(); s++) {
      memsMap.get(s).clear();
    }

    for (int s = 0; s < fieldMap.size(); s++) {
      fieldMap.get(s).clear();
    }
  }

  static void allocation() throws Exception {
    double coefficient[][] = new double[n][n];
    double mems[] = new double[n];
    int k;
    int i;

    for (int s = 0; s < memsMap.size(); s++) {
      mems[s] = Double.parseDouble(memsMap.get(s).getText());
    }

    for (int s = 0; s < fieldMap.size(); s++) {
      k = s / n;
      i = s % n;
      coefficient[k][i] = Double.parseDouble(fieldMap.get(s).getText());
    }

    Method.coefficient = coefficient;
    Method.mems = mems;
  }

  private void resizeMatrix() {
    int k;
    int i;
    int s;

    matrix.getChildren().clear();
    memsColumn.getChildren().clear();
    strokeMap.clear();
    fieldMap.clear();
    memsMap.clear();

    while (strokeMap.size() != n) {
      s = strokeMap.size();

      HBox box = new HBox();
      matrix.getChildren().add(box);
      strokeMap.put(s, box);
    }

    while (memsColumn.getChildren().size() != n) {
      TextField field = new TextField();
      field.setPromptText("=");
      field.setPrefSize(55, 26);
      VBox.setMargin(field, new Insets(10, 0, 0, 10));

      memsColumn.getChildren().add(field);
      memsMap.put(memsColumn.getChildren().size() - 1, field);
    }

    while (fieldMap.size() != pow(n, 2)) {
      s = fieldMap.size();
      k = s / n;
      i = s % n;

      TextField field = new TextField();
      field.setPromptText("x" + (i + 1));
      field.setPrefSize(55, 26);
      HBox.setMargin(field, new Insets(10, 0, 0, 10));

      strokeMap.get(k).getChildren().add(field);
      fieldMap.put(s, field);
    }
  }
}
