module jobblett.fxui {
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires javafx.controls;
	requires jobblett.core;
	requires jobblett.json;

	exports jobblett.ui;

	opens jobblett.ui to javafx.fxml;
}