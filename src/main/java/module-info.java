module dictionaryapplication.dictionaryapplication {
    requires javafx.controls;
    requires javafx.fxml;

    requires google.cloud.core;
    requires google.cloud.firestore;
    requires com.google.auth;
    requires com.google.auth.oauth2;
    requires com.google.api.apicommon;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires async.http.client;
    requires voicerss.tts;
//    requires AudioPlayer;
    requires java.desktop;
    requires java.management;
    requires java.instrument;
    requires atlantafx.base;
    requires bcrypt;
    requires com.google.gson;

    opens controller to javafx.fxml;
    exports controller;
    exports model;
    opens model to javafx.fxml;
}