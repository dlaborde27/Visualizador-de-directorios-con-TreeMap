
package grupoestructuras.visualizadordedirectorios;

import Modelo.Arbol;
import Modelo.BuilderArbol;
import Modelo.Carpeta;
import Modelo.Nodo;
import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;


public class PrincipalController implements Initializable {

    @FXML
    private AnchorPane ventana2;
    @FXML
    private AnchorPane barra;
    @FXML
    private Button directory;
    @FXML
    private AnchorPane center;
    @FXML
    private AnchorPane base;
    @FXML
    private Button save;
    @FXML
    private TextField rutaDeCarpeta;
    @FXML
    private Button visualizador;
    Arbol<Carpeta> treeMap;
    private double xOffset = 0;
    private double yOffset = 0;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}    

    private void quitButton(ActionEvent event) {
        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void moveWindow(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    private void getVector(MouseEvent event) {
        Stage stage = (Stage) barra.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    @FXML
    private void seleccionarCarpeta(ActionEvent event) {
        DirectoryChooser selectorDeCarpeta = new DirectoryChooser();
        selectorDeCarpeta.setInitialDirectory(new File("C:\\"));
        File carpetaElegida = selectorDeCarpeta.showDialog(null);
        establecerRutaEnTextField(carpetaElegida);
        contruirTreeMap(carpetaElegida);
        mostarTreeMapPorConsola();
        center.getChildren().clear();
        save.setDisable(true);
    }
    
    private void contruirTreeMap(File carpetaElegida){
        BuilderArbol b = new BuilderArbol();
        this.treeMap = b.construirArbol(carpetaElegida);
    }
    
    private void mostarTreeMapPorConsola(){
        System.out.println("---------- TreeMap ---------");
        iterar(this.treeMap, 0);
    }
    
    private void establecerRutaEnTextField(File carpetaElegida){
        rutaDeCarpeta.setText(carpetaElegida.getAbsolutePath());
        visualizador.setDisable(false);
    }
    
    private void iterar(Arbol<Carpeta> treeMap, int num) {
        Nodo<Carpeta> nodoPadre = treeMap.root;
        for(Arbol<Carpeta> h : nodoPadre.getHijos()){
            Carpeta carpetaEnNodo = h.root.contenido;
            if(h.root.getHijos().isEmpty()){
                System.out.println(identar(num) + "Archivo: " + carpetaEnNodo.getNombre() + "| size: " + carpetaEnNodo.getTamaño());
            }else{
                System.out.println(identar(num) + "Carpeta: " + carpetaEnNodo.getNombre() + "| size: " + carpetaEnNodo.getTamaño());
                iterar(h, num + 2);
            }
        }
    }
    
    private String identar(int num) {
        String identacion = "";
        if (num > 0) {
            for (int i = 0; i < num; i++) {
                identacion += "-";
            }
        }
        return identacion;
    }
    
    @FXML
    private void visualizeButtonAction(ActionEvent event) {
        VBox container = new VBox();
        Pane SizeTotal = new Pane();

        HBox graphics = new HBox();
        graphics.setMaxWidth(960);
        graphics.setMaxHeight(650);

        Rectangle graphicSizeTotal = new Rectangle();
        graphicSizeTotal.setWidth(960);
        
        graphicSizeTotal.setHeight(25);
        graphicSizeTotal.setFill(Color.CORAL);
        graphicSizeTotal.setStroke(Color.WHITE);

        Label extensionSize = new Label();
        setTamañoDeLabel(extensionSize, treeMap.root.contenido);

        SizeTotal.getChildren().addAll(graphicSizeTotal, extensionSize);
        container.getChildren().addAll(SizeTotal, graphics);
        Painting(this.treeMap, graphics, 960.0, 650.0, "h");
        center.getChildren().addAll(container);
        save.setDisable(false);
    }

    @FXML
    private void saveButtonAction(ActionEvent event) {
        /*boolean result = false;
        try {
            String nombre = "captura";
            String fecha = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            WritableImage snapshot = center.snapshot(null, null);

            java.awt.image.BufferedImage bufferedImage;
            bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);
            File file1 = new File(nombre + fecha + ".png");
            result = javax.imageio.ImageIO.write(bufferedImage, "png", file1);
        } catch (IOException ex) {
            Logger.getLogger(SecondController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (result) {
            Alert dialog = new Alert(AlertType.INFORMATION);
            dialog.setTitle("Confirmación");
            dialog.setHeaderText(null);
            dialog.setContentText("Captura guardada con éxito!");
            dialog.initStyle(StageStyle.TRANSPARENT);
            dialog.showAndWait();

        } else {

        }*/
    }
    
    public void setTamañoDeLabel(Label lb, Carpeta carpeta) {
        lb.setStyle("-fx-font-weight: bold; -fx-font-size: 15");
        lb.setText("Carpeta : "+ carpeta.getNombre() +" (" + carpeta.getTamaño() + " KB" + ")");
    }

    public Color getRandomColor() {
        Random rd = new Random();
        float r = rd.nextFloat();
        float g = rd.nextFloat();
        float b = rd.nextFloat();
        Color randomColor = new Color(r, g, b, 1);
        return randomColor;
    }

    public double getSize(File dir) {
        double size = 0.0;
        File[] files = dir.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                size += f.length();
            } else {
                File[] fileB = f.listFiles();
                for (File fl : fileB) {
                    size += fl.length();
                }
            }
        }
        return size;
    }
    
    public void Painting(Arbol<Carpeta>arbol, Pane pane, double width, double height, String orientacion) {
        Nodo<Carpeta> nodoArbol = arbol.root;
        List<Arbol<Carpeta>> selected = nodoArbol.getHijos();
        double tamano = nodoArbol.contenido.getTamaño();
        
        for (Arbol<Carpeta> archivo : selected) {
            
            Boolean esCarpeta = archivo.root.esHoja();
            Boolean esHorizontal = orientacion.equals("h");
            
            if (!esCarpeta && esHorizontal) {
                double valor = width * (archivo.root.contenido.getTamaño() / tamano);
                agregarRectangulo(valor, height, pane, archivo.root.contenido);

            } else if (!esCarpeta && !esHorizontal) {
                double valor = height * (archivo.root.contenido.getTamaño() / tamano);

                agregarRectangulo(width, valor, pane, archivo.root.contenido);

            } else if (esCarpeta && esHorizontal) {

                VBox box = new VBox();
                box.setMaxWidth(width * (archivo.root.contenido.getTamaño() / tamano));
                box.setMaxHeight(height);
                Pane panel = new Pane();

                Painting(archivo, box, box.getMaxWidth(), box.getMaxHeight(), "v");
                panel.getChildren().addAll(box);
                pane.getChildren().addAll(panel);

            } else {
                HBox box = new HBox();
                Pane panel = new Pane();
                //Label lb = new Label(archivo.root.contenido.getNombre() + " " + archivo.root.contenido.getTamaño());
                //lb.setStyle("-fx-background-color: transparent");

                box.setMaxWidth(width);
                box.setMaxHeight(height * (archivo.root.contenido.getTamaño() / tamano));
                Painting(archivo, box, box.getMaxWidth(), box.getMaxHeight(), "h");
                panel.getChildren().addAll(box);
                pane.getChildren().addAll(panel);
            }
        }
    }

    public void agregarAletar(Carpeta carpeta) {
        Alert a = new Alert(AlertType.INFORMATION);
        a.setTitle("Informacion");
        a.setContentText("Nombre del archivo: " + carpeta.getNombre() + "\n" + "Peso en megabytes: " + carpeta.getTamaño());
        a.setHeaderText(null);
        a.showAndWait();

    }

    public void agregarRectangulo(Double v1, Double v2, Pane panel, Carpeta carpeta) {
        Rectangle shape = new Rectangle(v1, v2);
        shape.setFill(getRandomColor());
        shape.setStrokeType(StrokeType.INSIDE);
        shape.setStroke(Color.WHITE);
        VBox temp = new VBox();
        temp.getChildren().addAll(shape);
        panel.getChildren().add(temp);
        shape.setOnMouseClicked(event -> {
            agregarAletar(carpeta);
        });
    }
    
}
