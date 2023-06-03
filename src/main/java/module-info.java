module ru.ac.uniyar.simplexmethod {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens ru.ac.uniyar.application to javafx.fxml;
    exports ru.ac.uniyar.application;
    exports ru.ac.uniyar.method;
    opens ru.ac.uniyar.method to javafx.fxml;
}