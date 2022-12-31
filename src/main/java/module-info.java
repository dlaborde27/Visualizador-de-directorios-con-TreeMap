module grupoestructuras.visualizadordedirectorios {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens grupoestructuras.visualizadordedirectorios to javafx.fxml;
    exports grupoestructuras.visualizadordedirectorios;
}
