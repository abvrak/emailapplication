module org.example.emailapplication {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    // Export the emailapp package
    exports emailapp;
    opens emailapp to javafx.fxml;
}