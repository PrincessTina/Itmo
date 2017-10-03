
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.*;

import java.io.FileInputStream;

public class Interface extends Application {
  static VBox answerBox = new VBox();
  static TextField answerField;
  private static Method thread = new Method();

  static void createInterface() {
    launch();
  }

  public void start(Stage stage) {
    try {
      TextField lowBound = new TextField();
      lowBound.setMaxSize(55, 26);
      lowBound.setFocusTraversable(false);
      TextField topBound = new TextField();
      topBound.setMaxSize(55, 26);
      topBound.setFocusTraversable(false);

      answerField = new TextField();
      answerField.setPromptText("Напишите сообщение ...");
      answerField.setPrefHeight(40);
      answerField.setDisable(true);

      Button solveButton = new Button("Решить!");
      solveButton.setPrefWidth(400);

      RadioButton func0 = new RadioButton();
      func0.setSelected(true);
      func0.setFocusTraversable(false);
      RadioButton func1 = new RadioButton();
      func1.setFocusTraversable(false);
      RadioButton func2 = new RadioButton();
      func2.setFocusTraversable(false);
      RadioButton func3 = new RadioButton();
      func3.setFocusTraversable(false);
      RadioButton func4 = new RadioButton();
      func4.setFocusTraversable(false);

      ToggleGroup radioGroup = new ToggleGroup();

      func0.setToggleGroup(radioGroup);
      func1.setToggleGroup(radioGroup);
      func2.setToggleGroup(radioGroup);
      func3.setToggleGroup(radioGroup);
      func4.setToggleGroup(radioGroup);

      ComboBox<Double> precisionBox = new ComboBox<>();
      precisionBox.getItems().addAll(1.0, 0.1, 0.01, 0.001, 0.0001, 0.00001, 0.000001, 0.0000001, 0.00000001);
      precisionBox.setPromptText("Точность");
      precisionBox.setPrefWidth(200);
      precisionBox.setId("comboBox");

      VBox.setMargin(func0, new Insets(10, 0, 0, 0));
      VBox.setMargin(topBound, new Insets(5, 0, 5, 50));
      VBox.setMargin(lowBound, new Insets(5, 0, 0, 5));
      VBox.setMargin(solveButton, new Insets(80, 0, 5, 70));

      VBox radioButtonBox = new VBox(func0, func1, func2, func3, func4);
      radioButtonBox.setSpacing(30);

      VBox imageBox = new VBox(new ImageView(new Image(new FileInputStream("func0.png"))),
          new ImageView(new Image(new FileInputStream("func1.png"))),
          new ImageView(new Image(new FileInputStream("func2.png"))),
          new ImageView(new Image(new FileInputStream("func3.png"))),
          new ImageView(new Image(new FileInputStream("func4.png"))));

      HBox.setMargin(precisionBox, new Insets(0, 10, 0, 90));

      HBox hBox = new HBox(new ImageView(new Image(new FileInputStream("integral.png"))), radioButtonBox, imageBox,
          precisionBox);
      hBox.setSpacing(5);

      VBox integralBox = new VBox(topBound, hBox, lowBound, solveButton);

      ScrollPane answerScroll = new ScrollPane(answerBox);
      answerScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
      answerScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
      answerScroll.setPrefSize(440, 500);
      answerScroll.setId("scroll");

      VBox messageBox = new VBox(answerScroll, answerField);
      messageBox.setId("border");
      HBox mainBox = new HBox(integralBox, messageBox);
      Scene scene = new Scene(mainBox, 983, 540);
      scene.getStylesheets().add(Interface.class.getResource("main.css").toExternalForm());
      stage.setScene(scene);
      stage.show();

      //Events
      solveButton.setOnAction(event -> {
        if (thread.isAlive()) {
          thread.stop();
          answerField.setDisable(true);
        }

        reset();
        setParams(precisionBox, radioGroup, lowBound, topBound);
      });

      answerField.setOnKeyReleased(event -> {
        if (event.getCode().equals(KeyCode.ENTER)) {
          Text text = new Text(answerField.getText());
          text.setAccessibleText(answerField.getText());
          text.setFill(Color.GREEN);
          VBox.setMargin(text, new Insets(10, 10, 0, 10));

          answerBox.getChildren().add(text);
          answerField.setText("");
          answerField.setDisable(true);
        }
      });
    } catch (Exception ex) {
      errorWindow(ex.getMessage());
    }
  }

  private void setParams(ComboBox<Double> precisionBox, ToggleGroup radioGroup, TextField lowBound, TextField topBound) {
    try {
      Method.precision = precisionBox.getValue();
      Method.a = Double.parseDouble(lowBound.getText());
      Method.b = Double.parseDouble(topBound.getText());

      for (int i = 0; i < radioGroup.getToggles().size(); i++) {
        if (radioGroup.getToggles().get(i).isSelected()) {
          Method.num = i;
          break;
        }
      }

      thread = new Method();
      thread.start();
    } catch (NullPointerException ex) {
      errorWindow("Выберите точность");
    } catch (NumberFormatException ex) {
      errorWindow("Проверьте корректность введенных данных");
    } catch (Exception ex) {
      errorWindow(ex.getMessage());
    }
  }

  private void reset() {
    answerBox.getChildren().clear();
  }

  private void errorWindow(String error) {
    Text text = new Text(error);
    text.setWrappingWidth(250);
    text.setTextAlignment(TextAlignment.CENTER);
    VBox.setMargin(text, new Insets(10, 10, 0, 10));

    Scene scene2 = new Scene(new VBox(text), 250, 60);
    Stage stage2 = new Stage(StageStyle.DECORATED);
    stage2.initModality(Modality.APPLICATION_MODAL);
    stage2.setScene(scene2);
    stage2.show();
  }
}
