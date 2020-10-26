module jobblett.fxui {
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires javafx.controls;
	requires jobblett.core;
	requires jobblett.json;
	requires com.fasterxml.jackson.databind;
	requires java.net.http;

	exports jobblett.ui;

	opens jobblett.ui to javafx.fxml;
}