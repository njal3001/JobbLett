module bolett {
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires javafx.controls;
    requires com.fasterxml.jackson.databind;

    exports bolett.core;
	exports bolett.json;

	opens bolett.ui to javafx.fxml;
	opens bolett.json to com.fasterxml.jackson.databind;
}