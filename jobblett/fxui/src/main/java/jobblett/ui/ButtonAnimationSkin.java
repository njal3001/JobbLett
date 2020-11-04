package jobblett.ui;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.control.skin.ButtonSkin;
import javafx.util.Duration;

public class ButtonAnimationSkin extends ButtonSkin {

  /**
   * TODO.
   *
   * @param control TODO
   */
  public ButtonAnimationSkin(Button control) {
    super(control);
    final ScaleTransition fadeIn = new ScaleTransition(Duration.millis(200));
    fadeIn.setNode(control);
    fadeIn.setToX(1.05);
    fadeIn.setToY(1.05);
    control.setOnMouseEntered(e -> fadeIn.playFromStart());

    final ScaleTransition fadeOut = new ScaleTransition(Duration.millis(100));
    fadeOut.setNode(control);
    fadeOut.setToX(1);
    fadeOut.setToY(1);
    control.setOnMouseExited(e -> fadeOut.playFromStart());
    control.setOnMouseReleased(e -> fadeOut.playFromStart());

    final ScaleTransition pressedFade = new ScaleTransition(Duration.millis(100));
    pressedFade.setNode(control);
    pressedFade.setToX(0.975);
    pressedFade.setToY(0.975);
    control.setOnMousePressed(e -> pressedFade.playFromStart());
  }

}
