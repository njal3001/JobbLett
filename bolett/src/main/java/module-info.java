module bolett {
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires javafx.controls;
    requires com.fasterxml.jackson.databind;

    exports bolett.core;
	exports bolett.json;
	exports bolett.ui;

	opens bolett.ui to javafx.fxml;
	opens bolett to javafx.fxml, com.fasterxml.jackson.databind;
}