module hellofx {
    requires javafx.controls;
    requires javafx.fxml;

    opens win.floss.amazeing to javafx.fxml;
    exports win.floss.amazeing.controllers;
    exports win.floss.amazeing.models;
}
