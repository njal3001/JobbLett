module jobblett.restserver {
    requires java.ws.rs;
    requires jobblett.json;
    requires com.fasterxml.jackson.databind;
    requires jobblett.core;
    requires jersey.common;
    requires jersey.server;
    requires jobblett.restapi;
    requires jersey.media.json.jackson;
    requires hk2.api;
  requires java.desktop;
}
