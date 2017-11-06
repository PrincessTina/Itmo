import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

import java.util.ArrayList;

public class Interface extends Application {
  private int errorCode;
  static ArrayList<Double> point_x = new ArrayList<>();
  static ArrayList<Double> point_y = new ArrayList<>();
  private XYChart.Series<Number, Number> graph = new XYChart.Series<>();

  static void createInterface() {
    launch();
  }

  public void start(Stage stage) {
    ComboBox<String> typeBox = new ComboBox<>();
    typeBox.getItems().addAll("12x - 3x^2 + 8x^3", "-5y + 8", "x^2 - 2y", "x^3 + 3x^2 - 10x + 2y - 0.04y^3", "10");
    typeBox.setPromptText("Тип функции");
    typeBox.setPrefWidth(200);

    ComboBox<Double> precisionBox = new ComboBox<>();
    precisionBox.getItems().addAll(1.0, 0.1, 0.01, 0.001, 0.0001, 0.00001, 0.000001, 0.0000001, 0.00000001);
    precisionBox.setPromptText("Точность");
    precisionBox.setPrefWidth(200);

    Label cut = new Label("На отрезке [");
    Label brace = new Label("]");
    Label y_start = new Label("y0 (x0) = ");

    TextField x0_field = new TextField();
    x0_field.setPrefSize(55, 26);

    TextField last_field = new TextField();
    last_field.setPrefSize(55, 26);

    TextField y0_field = new TextField();
    y0_field.setPrefSize(55, 26);

    Button goButton = new Button("Поехали!");
    goButton.setPrefWidth(200);

    LineChart<Number, Number> canvas = new LineChart<>(new NumberAxis(), new NumberAxis());
    graph.setName("graph");
    canvas.getData().add(graph);

    HBox chooseFunctionBox = new HBox(typeBox, precisionBox);
    HBox cutBox = new HBox(cut, x0_field, last_field, brace);
    HBox startBox = new HBox(y_start, y0_field);

    HBox.setMargin(typeBox, new Insets(10, 10, 0, 10));
    HBox.setMargin(precisionBox, new Insets(10, 10, 0, 10));
    HBox.setMargin(cut, new Insets(20, 0, 0, 10));
    HBox.setMargin(x0_field, new Insets(20, 0, 0, 5));
    HBox.setMargin(y_start, new Insets(25, 0, 0, 10));
    HBox.setMargin(y0_field, new Insets(20, 0, 0, 3));
    HBox.setMargin(last_field, new Insets(20, 5, 0, 5));
    HBox.setMargin(brace, new Insets(20, 0, 0, 0));

    VBox.setMargin(goButton, new Insets(10, 10, 0, 350));

    VBox mainBox = new VBox(chooseFunctionBox, cutBox, startBox, goButton, canvas);

    Scene scene = new Scene(mainBox, 900, 600);
    scene.getStylesheets().add(Interface.class.getResource("main.css").toExternalForm());
    stage.setScene(scene);
    stage.show();

    //Events
    goButton.setOnAction(event -> {
      try {
        graph.getData().clear();
        readData(typeBox, precisionBox, x0_field, last_field, y0_field);
        Method.calculation();
        buildGraph();

      } catch (NumberFormatException ex) {
        callErrorWindow("Проверьте, пожалуйста, корректность введенных данных");
      } catch (NullPointerException ex) {
        switch (errorCode) {
          case 0:
            callErrorWindow("Выберите, пожалуйста, тип функции");
            break;
          default: callErrorWindow("Выберите, пожалуйста, точность");
        }
      } catch (Exception ex) {
        if (ex.getMessage().equals("NaN")) {
          callErrorWindow("Введенные данные не удовлетворяют ОДЗ");
        } else {
          callErrorWindow("Error: " + ex.getMessage());
        }
      }
    });
  }

  private void buildGraph() {
    for (int i = 0; i < point_x.size(); i++) {
      graph.getData().add(new XYChart.Data<>(point_x.get(i), point_y.get(i)));
    }

    //Для всплывающих подсказок
    graph.getData().forEach(data -> {
      Node node = data.getNode();
      Tooltip tooltip = new Tooltip("(" + data.getXValue() + " ; " + data.getYValue() + ")");
      Tooltip.install(node, tooltip);
    });
  }

  private void readData(ComboBox<String> typeBox, ComboBox<Double> precisionBox, TextField x0_field,
                        TextField last_filed, TextField y0_field) throws Exception {
    int func_num = 0;
    double precision;
    double x0;
    double y0;
    double last;

    errorCode = 0;
    if (typeBox.getValue().equals("12x - 3x^2 + 8x^3")) {
      func_num = 0;
    } else if (typeBox.getValue().equals("-5y + 8")) {
      func_num = 1;
    } else if (typeBox.getValue().equals("x^2 - 2y")) {
      func_num = 2;
    } else if (typeBox.getValue().equals("x^3 + 3x^2 - 10x + 2y - 0.04y^3")) {
      func_num = 3;
    } else if (typeBox.getValue().equals("10")) {
      func_num = 4;
    }

    errorCode = 1;
    precision = precisionBox.getValue();
    x0 = Double.parseDouble(x0_field.getText());
    last = Double.parseDouble(last_filed.getText());
    y0 = Double.parseDouble(y0_field.getText());

    Method.x0 = x0;
    Method.y0 = y0;
    Method.last = last;
    Method.precision = precision;
    Method.number = func_num;

    if (x0 > last) {
      x0_field.setText(String.valueOf(last));
      last_filed.setText(String.valueOf(x0));
    }
  }

  private void callErrorWindow(String message) {
    Text text = new Text(message);
    text.setWrappingWidth(250);
    text.setTextAlignment(TextAlignment.CENTER);
    VBox.setMargin(text, new Insets(10, 10, 0, 10));

    Scene scene2 = new Scene(new VBox(text), 270, 100);
    Stage stage2 = new Stage(StageStyle.DECORATED);
    stage2.initModality(Modality.APPLICATION_MODAL);
    stage2.setScene(scene2);
    stage2.show();
  }
}

