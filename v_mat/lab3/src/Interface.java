import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

import java.util.ArrayList;

import static java.lang.Math.*;

public class Interface extends Application {
  private XYChart.Series<Number, Number> graph_real = new XYChart.Series<>();
  private XYChart.Series<Number, Number> graph_hypo = new XYChart.Series<>();
  private XYChart.Series<Number, Number> graph_point = new XYChart.Series<>();
  private ArrayList<TextField> xArray = new ArrayList<>();
  private ArrayList<TextField> yArray = new ArrayList<>();
  private double x_array[]; //абсциссы
  private double y_array[]; //ординаты
  static double a_array[]; //коэффициенты в уравнении полинома
  private int level; //степень полинома
  private int count = 1; //количество столбцов в таблице по умолчанию
  private int func_num;
  private double a;
  private double b;
  private double k;
  private double c;
  private double point_x;
  private double point_y;

  static void createInterface() {
    launch();
  }

  public void start(Stage stage) {
    TextField degreeField = new TextField();
    degreeField.setPromptText("Степень полинома");
    degreeField.setVisible(false);
    degreeField.setPrefWidth(200);

    ComboBox<String> typeBox = new ComboBox<>();
    typeBox.getItems().addAll("Полином", "Логарифмическая", "Экспонента");
    typeBox.setPromptText("Тип функции");
    typeBox.setPrefWidth(200);

    Button plus = new Button("+");
    plus.setPrefSize(44, 40);
    Button minus = new Button("-");
    minus.setPrefSize(44, 40);
    Button clear = new Button("Очистить");
    clear.setPrefSize(200, 40);
    Button goButton = new Button("Поехали!");
    goButton.setPrefWidth(200);

    LineChart<Number, Number> canvas = new LineChart<>(new NumberAxis(), new NumberAxis());
    graph_real.setName("graph_real");
    graph_hypo.setName("graph_hypo");
    graph_point.setName("points");
    canvas.getData().add(graph_point);
    canvas.getData().add(graph_hypo);
    canvas.getData().add(graph_real);

    HBox operationsBox = new HBox(plus, minus, clear);
    HBox chooseFunctionBox = new HBox(typeBox, degreeField);
    HBox table = new HBox();
    table.setId("table");
    table.setPrefSize(878, 150);
    resizeTable(table);

    ScrollPane tableScroll = new ScrollPane(table);
    tableScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    tableScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    tableScroll.setPrefSize(880, 150);

    HBox.setMargin(typeBox, new Insets(10, 10, 0, 10));
    HBox.setMargin(degreeField, new Insets(10, 10, 0, 10));
    HBox.setMargin(plus, new Insets(20, 10, 0, 10));
    HBox.setMargin(minus, new Insets(20, 10, 0, 10));
    HBox.setMargin(clear, new Insets(20, 10, 0, 10));

    VBox.setMargin(goButton, new Insets(10, 10, 0, 350));
    VBox.setMargin(tableScroll, new Insets(10, 10, 0, 10));

    VBox mainBox = new VBox(chooseFunctionBox, operationsBox, tableScroll, goButton, canvas);

    Scene scene = new Scene(mainBox, 900, 700);
    scene.getStylesheets().add(Interface.class.getResource("main.css").toExternalForm());
    stage.setScene(scene);
    stage.show();

    //Events
    goButton.setOnAction(event -> {
      try {
        c = 0;
        graph_hypo.getData().clear();
        graph_real.getData().clear();
        graph_point.getData().clear();

        readData(typeBox, degreeField);

        switch (func_num) {
          case 0:
            setCoefficient();
            setMems();
            Method.calculation();
            buildGraph();
            findPoint();
            c = 0;
            setCoefficient();
            setMems();
            Method.calculation();
            buildNewGraph();
            break;
          case 1:
            setA();
            setB();
            buildGraph();
            findPoint();
            c = 0;
            setA();
            setB();
            buildNewGraph();
            break;
          case 2:
            initialState();
            setK();
            setA();
            buildGraph();
            findPoint();
            buildNewGraph();
            c = 0;
            initialState();
            setK();
            setA();
            buildNewGraph();
            break;
        }

      } catch (NumberFormatException ex) {
        callErrorWindow("Проверьте корректность введенных данных");
      } catch (NullPointerException ex) {
        callErrorWindow("Выберите, пожалуйста, тип функции");
      } catch (Exception ex) {
        if (ex.getMessage().equals("NULL")) {
          callErrorWindow("Из-за неточности/малого количества данных решение не может быть построено");
        } else if (ex.getMessage().equals("INFINITY")) {
          callErrorWindow("Из-за неточности введенных данных решение будет построено приблизительно");
          buildGraph();
        } else if (ex.getMessage().equals("LEVEL")) {
          callErrorWindow("Степень полинома должна быть неотрицательной");
        } else if (ex.getMessage().equals("ODZ")) {
          callErrorWindow("Введенные данные не удовлетворяют ОДЗ");
        } else if (ex.getMessage().equals("NOT_ENOUGH")) {
          callErrorWindow("Введите хотя бы еще одну точку");
        } else {
          callErrorWindow(ex.getMessage());
        }
      }
    });

    plus.setOnAction(event -> {
      count++;
      resizeTable(table);
    });

    minus.setOnAction(event -> {
      if (count > 1) {
        count--;
        resizeTable(table);
      }
    });

    clear.setOnAction(event -> {
      for (int i = 0; i < xArray.size(); i++) {
        xArray.get(i).setText("");
        yArray.get(i).setText("");
      }
    });

    typeBox.setOnHidden(event -> {
      try {
        if (typeBox.getValue().equals("Полином")) {
          degreeField.setVisible(true);
        } else {
          degreeField.setVisible(false);
        }
      } catch (NullPointerException ex) {
        System.out.println();
      }
    });
  }

  private void buildNewGraph() {
    switch (func_num) {
      case 0:
        for (double x : x_array) {
          if (x != point_x) {
            graph_real.getData().add(new XYChart.Data<>(x, f(x)));
          }
        }
        break;
      case 1:
        for (double x : x_array) {
          if (x != point_x) {
            graph_real.getData().add(new XYChart.Data<>(x, f(x)));
          }
        }
        break;
      case 2:
        for (double x : x_array) {
          if (x != point_x) {
            graph_real.getData().add(new XYChart.Data<>(x, f(x) + c));
          }
        }
        break;
      case 3:
        for (double x : x_array) {
          if (x != point_x) {
            graph_real.getData().add(new XYChart.Data<>(x, f(x) + c));
          }
        }
        break;
    }
  }

  private void resizeTable(HBox table) {
    table.getChildren().clear();

    //dangerous
    ArrayList<String> list = new ArrayList<>();

    for (int i = 0; i < xArray.size(); i++) {
      list.add(xArray.get(i).getText());
      list.add(yArray.get(i).getText());
    }

    xArray.clear();
    yArray.clear();

    Label space = new Label(" ");
    Label x = new Label("x");
    Label y = new Label("y");

    VBox.setMargin(space, new Insets(15, 0, 0, 10));
    VBox.setMargin(x, new Insets(7, 0, 0, 10));
    VBox.setMargin(y, new Insets(7, 0, 0, 10));

    table.getChildren().add(new VBox(space, x, y));

    for (int i = 0; i < count; i++) {
      Label number = new Label(String.valueOf(i + 1));
      TextField x_field = new TextField();
      xArray.add(x_field);
      TextField y_field = new TextField();
      yArray.add(y_field);

      if (list.size() >= 2 * i + 1) {
        x_field.setText(list.get(2 * i));
        y_field.setText(list.get(2 * i + 1));
      }

      x_field.setPrefSize(55, 26);
      y_field.setPrefSize(55, 26);
      x_field.setMinSize(55, 26);
      y_field.setMinSize(55, 26);

      VBox.setMargin(number, new Insets(15, 0, 0, 28));
      VBox.setMargin(x_field, new Insets(5, 0, 0, 5));
      VBox.setMargin(y_field, new Insets(5, 0, 0, 5));

      table.getChildren().add(new VBox(number, x_field, y_field));
    }
  }

  private void readData(ComboBox typeBox, TextField degreeField) throws Exception {
    x_array = new double[xArray.size()];
    y_array = new double[yArray.size()];

    for (int i = 0; i < xArray.size(); i++) {
      x_array[i] = Double.parseDouble(xArray.get(i).getText());
    }

    for (int i = 0; i < yArray.size(); i++) {
      y_array[i] = Double.parseDouble(yArray.get(i).getText());
    }

    if (typeBox.getValue().equals("Полином")) {
      level = Integer.parseInt(degreeField.getText());

      if (level < 0) {
        throw new Exception("LEVEL");
      }
      func_num = 0;
    } else if (typeBox.getValue().equals("Логарифмическая")) {
      for (double x : x_array) {
        if (x <= 0) {
          throw new Exception("ODZ");
        }
      }
      func_num = 1;
    } else if (typeBox.getValue().equals("Экспонента")) {
      if (x_array.length < 2) {
        throw new Exception("NOT_ENOUGH");
      }
      for (double y : y_array) {
        if (y <= 0) {
          throw new Exception("ODZ");
        }
      }
      func_num = 2;
    }
  }

  private void buildGraph() {
    switch (func_num) {
      case 0:
        for (double x : x_array) {
          graph_hypo.getData().add(new XYChart.Data<>(x, f(x)));
        }
        break;
      case 1:
        for (double x : x_array) {
          graph_hypo.getData().add(new XYChart.Data<>(x, f(x)));
        }
        break;
      case 2:
        for (double x : x_array) {
          graph_hypo.getData().add(new XYChart.Data<>(x, f(x) + c));
        }
        break;
      case 3:
        for (double x : x_array) {
          graph_hypo.getData().add(new XYChart.Data<>(x, f(x) + c));
        }
        break;
    }

    for (int i = 0; i < x_array.length; i++) {
      graph_point.getData().add(new XYChart.Data<>(x_array[i], y_array[i] + c));
    }

    //Для всплывающих подсказок
    graph_point.getData().forEach(data -> {
      Node node = data.getNode();
      Tooltip tooltip = new Tooltip("(" + data.getXValue() + " ; " + data.getYValue() + ")");
      Tooltip.install(node, tooltip);
    });
  }

  private void findPoint() {
    point_x = 0;
    point_y = 0;
    double y_hypo;
    double y_real;

    for (int i = 0; i < x_array.length; i++) {
      y_hypo = f(x_array[i]);
      y_real = y_array[i];
      if (abs(y_hypo - y_real) > point_x) {
        point_x = x_array[i];
        point_y = y_array[i];
      }
    }
  }

  private void initialState() {
    double min = y_array[0];

    for (double y : y_array) {
      if (y < min) {
        min = y;
      }
    }

    if (min != round(min)) {
      min = round(min);
    } else {
      min -= 0.2;
    }


    for (int i = 0; i < y_array.length; i++) {
      y_array[i] -= min;
    }

    c = min;
  }

  private double f(double x) {
    double y = 0;

    switch (func_num) {
      case 0:
        for (int i = 0; i <= level; i++) {
          y += a_array[i] * pow(x, i);
        }
        break;
      case 1:
        y = a * log(x) + b;
        break;
      case 2:
        y = a * pow(E, k * x);
        break;
    }

    return y;
  }

  private void setCoefficient() {
    double coefficient[][] = new double[level + 1][level + 1];

    for (int i = 0; i <= level; i++) {
      for (int j = 0; j <= level; j++) {
        coefficient[i][j] = getSumOfX(i + j);
      }
    }
    Method.coefficient = coefficient;
  }

  private void setMems() {
    double mems[] = new double[level + 1];

    for (int i = 0; i <= level; i++) {
      mems[i] = getProduct(i);
    }
    Method.mems = mems;
  }

  private void setA() {
    switch (func_num) {
      case 1:
        a = getProduct(1) * x_array.length - getSumOfX(1) * getSumOfY();
        a /= getSumOfX(2) * x_array.length - pow(getSumOfX(1), 2);
        break;
      case 2:
        a = pow(E, (getSumOfY() - k * getSumOfX(1)) / x_array.length);
        break;
    }
  }

  private void setK() {
    k = getProduct(1) * x_array.length - getSumOfX(1) * getSumOfY();
    k /= getSumOfX(2) * x_array.length - pow(getSumOfX(1), 2);
  }

  private void setB() {
    b = (getSumOfY() - a * getSumOfX(1)) / x_array.length;
  }

  private double getSumOfY() {
    double sum = 0;

    switch (func_num) {
      case 1:
        for (double y : y_array) {
          if (y != point_y) {
            sum += y;
          }
        }
        break;
      case 2:
        for (double y : y_array) {
          if (y != point_y) {
            sum += log(y);
          }
        }
        break;
    }

    return sum;
  }

  private double getSumOfX(int degree) {
    double sum = 0;

    switch (func_num) {
      case 0:
        for (double x : x_array) {
          if (x != point_x) {
            sum += pow(x, degree);
          }
        }
        break;
      case 1:
        for (double x : x_array) {
          if (point_x != x) {
            sum += pow(log(x), degree);
          }
        }
        break;
      case 2:
        for (double x : x_array) {
          if (x != point_x) {
            sum += pow(x, degree);
          }
        }
        break;
    }

    return sum;
  }

  private double getProduct(int degree) {
    double sum = 0;

    switch (func_num) {
      case 0:
        for (int i = 0; i < x_array.length; i++) {
          if (x_array[i] != point_x) {
            sum += y_array[i] * pow(x_array[i], degree);
          }
        }
        break;
      case 1:
        for (int i = 0; i < x_array.length; i++) {
          if (x_array[i] != point_x) {
            sum += y_array[i] * log(x_array[i]);
          }
        }
        break;
      case 2:
        for (int i = 0; i < x_array.length; i++) {
          if (x_array[i] != point_x) {
            sum += x_array[i] * log(y_array[i]);
          }
        }
        break;
    }

    return sum;
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
