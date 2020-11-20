module jobblett.fxui {
  requires javafx.fxml;
  requires transitive javafx.graphics;
  requires transitive javafx.controls;
  requires transitive jobblett.core;
  requires jobblett.json;
  requires com.fasterxml.jackson.databind;
  requires java.net.http;
  requires java.desktop;

  exports jobblett.ui;

  opens jobblett.ui to javafx.fxml;
}
