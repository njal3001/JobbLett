module bolett {
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires javafx.controls;

	exports bolett.core;
	exports bolett.ui;

	opens bolett.ui to javafx.fxml;
}