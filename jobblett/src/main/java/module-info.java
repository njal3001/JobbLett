module jobblett {
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires javafx.controls;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jdk8;
    requires com.fasterxml.jackson.datatype.jsr310;

    exports jobblett.core;
	exports jobblett.json;
	exports jobblett.ui;

	opens jobblett.ui to javafx.fxml;
	opens jobblett.json to com.fasterxml.jackson.databind;
	opens jobblett.core to com.fasterxml.jackson.databind;
}