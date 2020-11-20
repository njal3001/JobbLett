package jobblett.ui;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.control.skin.ButtonSkin;
import javafx.util.Duration;

public class ButtonAnimationSkin extends ButtonSkin {

  /**
   * Defines the effects for button transition when they are hovered, pressed and released.
   *
   * @param button the button we are adding the effects to
   */
  public ButtonAnimationSkin(Button button) {
    super(button);
    final ScaleTransition fadeIn = new ScaleTransition(Duration.millis(200));
    fadeIn.setNode(button);
    fadeIn.setToX(1.05);
    fadeIn.setToY(1.05);
    button.setOnMouseEntered(e -> fadeIn.playFromStart());

    final ScaleTransition fadeOut = new ScaleTransition(Duration.millis(100));
    fadeOut.setNode(button);
    fadeOut.setToX(1);
    fadeOut.setToY(1);
    button.setOnMouseExited(e -> fadeOut.playFromStart());
    button.setOnMouseReleased(e -> fadeOut.playFromStart());

    final ScaleTransition pressedFade = new ScaleTransition(Duration.millis(100));
    pressedFade.setNode(button);
    pressedFade.setToX(0.975);
    pressedFade.setToY(0.975);
    button.setOnMousePressed(e -> pressedFade.playFromStart());
  }

}
