
package sample;

import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

public class Controller {
    public Slider speedSlider;
    double speed = 1;

    public Controller() {
    }

    public void testing(MouseEvent mouseEvent) {
        this.speed = this.speedSlider.getValue();
    }
}
