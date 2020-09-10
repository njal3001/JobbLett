module bolett {
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires javafx.controls;
	
	exports bolett;

	opens bolett to javafx.fxml;
}