module dictionaryapplication.dictionaryapplication {
    requires javafx.controls;
    requires javafx.fxml;

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

    opens controller to javafx.fxml;
    exports controller;
    exports model;
    opens model to javafx.fxml;
}