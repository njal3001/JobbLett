module jobblett.core {
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jdk8;
    requires com.fasterxml.jackson.datatype.jsr310;
	requires guava;

	exports jobblett.core;
	exports jobblett.json;

	opens jobblett.json to com.fasterxml.jackson.databind;
	opens jobblett.core to com.fasterxml.jackson.databind;
}