module jobblett.core {
  requires guava;
  requires java.desktop;
  requires javafx.base;

  exports jobblett.core;

  opens jobblett.core to com.fasterxml.jackson.databind;
}
