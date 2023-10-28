module com.example.hangmanjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.almasb.fxgl.all;

    opens com.example.hangmanjavafx to javafx.fxml;
    exports com.example.hangmanjavafx;
}