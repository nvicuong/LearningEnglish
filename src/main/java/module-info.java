module dictionaryapplication.dictionaryapplication {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens dictionaryapplication.dictionaryapplication to javafx.fxml;
    exports dictionaryapplication.dictionaryapplication;
}