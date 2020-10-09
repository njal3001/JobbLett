module jobblett.core {
	requires guava;

	exports jobblett.core;

	opens jobblett.core to com.fasterxml.jackson.databind;
}