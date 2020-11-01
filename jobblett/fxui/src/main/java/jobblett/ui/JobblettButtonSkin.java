package jobblett.ui;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.control.skin.ButtonSkin;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class JobblettButtonSkin extends ButtonSkin {

    public JobblettButtonSkin(Button control) {
        super(control);
        control.setFont(Font.loadFont(JobblettButtonSkin.class.getResourceAsStream(App.FONT_FILE),16));
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
        control.setOnMouseReleased(e->fadeOut.playFromStart());

        final ScaleTransition pressedFade = new ScaleTransition(Duration.millis(100));
        pressedFade.setNode(control);
        pressedFade.setToX(0.975);
        pressedFade.setToY(0.975);
        control.setOnMousePressed(e -> pressedFade.playFromStart());
        

        String style = control.getStyle();
        style+="-fx-background-color:"+JobblettColors.ACCENT_COLOR+";";
        style+="-fx-effect:"+JobblettColors.SHADOW_CONFIG+";";
        control.setStyle(style);
        
    }

}