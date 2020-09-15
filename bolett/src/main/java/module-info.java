module bolett {
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires javafx.controls;
    requires com.fasterxml.jackson.databind;

    exports bolett.core;
	exports bolett.json;

	opens bolett to javafx.fxml, com.fasterxml.jackson.databind;
}