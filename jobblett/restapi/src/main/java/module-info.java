module jobblett.restapi {
    exports jobblett.restapi;
    requires javax.inject;
    requires java.ws.rs;
    requires slf4j.api;
    requires transitive jobblett.core;
    requires jobblett.json;
    opens jobblett.restapi;
}
