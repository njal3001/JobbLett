package jobblett.ui;

import javafx.scene.control.ListCell;
import javafx.scene.control.skin.ListCellSkin;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class JobblettCellSkin<T> extends ListCellSkin<T> {
    public JobblettCellSkin(ListCell<T> listCell) {
        super(listCell);
        listCell.setFont(Font.loadFont(JobblettCellSkin.class.getResourceAsStream(App.FONT_FILE),16));
        listCell.setTextFill(Color.BLACK);
    }
}
