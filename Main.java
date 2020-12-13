
package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.FuzzyRuleSet;

public class Main extends Application {
    private int width = 960;
    private int height = 720;

    public Main() {
    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("sample.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        final Controller controller = (Controller)fxmlLoader.getController();
        primaryStage.setTitle("Fuzzy");
        primaryStage.setScene(new Scene(root, (double)this.width, (double)this.height));
        Scene scene = primaryStage.getScene();
        Canvas canvas = (Canvas)scene.lookup("#canv");
        final Canvas canvas2 = (Canvas)scene.lookup("#canv2");
        final Canvas canvas3 = (Canvas)scene.lookup("#canv3");
        final Label front = (Label)scene.lookup("#front");
        final Label right = (Label)scene.lookup("#right");
        final Label left = (Label)scene.lookup("#left");
        Slider speed = (Slider)scene.lookup("#speed");
        GraphicsContext context = canvas.getGraphicsContext2D();
        final Maze maze = new Maze(4, this.width, this.height);
        FIS fis = FIS.load((String)this.getParameters().getRaw().get(0), true);
        final FuzzyRuleSet fuzzyRuleSet = fis.getFuzzyRuleSet();
        context.clearRect(0.0D, 0.0D, (double)this.width, (double)this.height);
        maze.visualizeGrid(context, 720, 720);
        (new AnimationTimer() {
            public void handle(long currentNanoTime) {
                maze.visualizeVehicle(canvas2, 720, 720);
                maze.visualizeUtils(canvas3, front, right, left, 720, 720);

                for(int i = 0; (double)i < controller.speed; ++i) {
                    maze.simulate(fuzzyRuleSet);
                }

            }
        }).start();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
