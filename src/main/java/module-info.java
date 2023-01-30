module grupoestructuras.visualizadordedirectorios {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires org.apache.commons.io;
    
    opens grupoestructuras.visualizadordedirectorios to javafx.fxml;
    exports grupoestructuras.visualizadordedirectorios;
}
