module jobblett.json {
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jdk8;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires jobblett.core;

	exports jobblett.json;

	opens jobblett.json to com.fasterxml.jackson.databind;
}