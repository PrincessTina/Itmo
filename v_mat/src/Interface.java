import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class Interface extends Application {
  public static void createInterface() {
    launch();
  }

  public void start(Stage stage) {
    Button randButton = new Button("Случайные числа");
    Button fileButton = new Button("Выбрать файл");
    Button solveButton = new Button("Решить");
    Button plus = new Button("+");
    Button minus = new Button("-");

    Text solveText = new Text();
    Text answerText = new Text();

    HBox buttonBox = new HBox(plus, minus);

    VBox right = new VBox(randButton, fileButton, solveButton);
    right.setStyle("-fx-border-width: 2px; -fx-border-style: hidden hidden hidden solid; -fx-border-color: darkcyan");

    VBox left = new VBox(buttonBox);
    left.setPrefSize(980, 370);

    HBox top = new HBox(left, right);

    HBox bottom = new HBox(answerText, solveText);
    bottom.setStyle("-fx-border-width: 2px; -fx-border-color: darkcyan; -fx-border-style: solid hidden hidden hidden");

    VBox mainVBox = new VBox(top, bottom);

    VBox.setMargin(randButton, new Insets(20, 20, 0, 20));
    VBox.setMargin(fileButton, new Insets(20, 20, 0, 20));
    VBox.setMargin(solveButton, new Insets(20, 20, 0, 20));

    HBox.setMargin(plus, new Insets(20, 0, 0, 20));
    HBox.setMargin(minus, new Insets(20, 0, 0, 20));

    Scene scene = new Scene(mainVBox, 1180, 740);
    stage.setScene(scene);
    stage.show();

    solveButton.setOnAction(event -> {
      Main.allocation();
      Method.calculation(solveText);
    });
  }
}
