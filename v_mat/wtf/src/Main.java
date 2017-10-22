import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main extends Application {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void start(Stage stage) throws ParseException {
        stage.setTitle("Line Chart Sample");
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");
        yAxis.setLabel("Events");

        final LineChart<Number,Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Events");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");


        XYChart.Series<Number,Number> series = new XYChart.Series<>();
        series.setName("Events this Year");
        series.getData().add(new XYChart.Data(23, 23));
        series.getData().add(new XYChart.Data(23, 14));
        series.getData().add(new XYChart.Data(23, 15));
        series.getData().add(new XYChart.Data(23, 24));
        series.getData().add(new XYChart.Data(23, 34));
        series.getData().add(new XYChart.Data(23, 36));
        series.getData().add(new XYChart.Data(23, 22));
        series.getData().add(new XYChart.Data(23, 45));
        series.getData().add(new XYChart.Data(23, 43));
        series.getData().add(new XYChart.Data(23, 17));
        series.getData().add(new XYChart.Data(23, 29));
        series.getData().add(new XYChart.Data(23, 25));


        Scene scene  = new Scene(lineChart,800,600);
        scene.getStylesheets().add(getClass().getResource("chart.css").toExternalForm());
        lineChart.getData().add(series);
        stage.setScene(scene);
        stage.show();

        /**
         * Browsing through the Data and applying ToolTip
         * as well as the class on hover
         */
        for (XYChart.Series<Number, Number> s : lineChart.getData()) {
            for (XYChart.Data<Number, Number> d : s.getData()) {
                Tooltip.install(d.getNode(), new Tooltip(
                    d.getXValue().toString() + "\n" +
                        "Number Of Events : " + d.getYValue()));

                //Adding class on hover
                d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));

                //Removing class on exit
                d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
