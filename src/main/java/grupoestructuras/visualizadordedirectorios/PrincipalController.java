
package grupoestructuras.visualizadordedirectorios;

import Modelo.Arbol;
import Modelo.Carpeta;
import java.io.File;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
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
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


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
    LinkedList<Carpeta> treeMap;
    private double xOffset = 0;
    private double yOffset = 0;
    private final double valorDeEquivalenciaDeUnKiloByteAByte = 1024.00;
    
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
        if (carpetaElegida == null) {
            System.out.println("No se ha seleccionado ninguna carpeta");
        } else {
            establecerRutaEnTextField(carpetaElegida);
            Carpeta carpeta = new Carpeta(carpetaElegida.getName());
            carpeta.setTamaño(redondeo(recorrerCarpeta(carpetaElegida.listFiles(), 0, carpeta), 2));
            treeMap = new LinkedList<>();
            treeMap.add(carpeta);
            System.out.println("---------- TreeMap ---------");
            iterar(treeMap, 0);
        }
        center.getChildren().clear();
        save.setDisable(true);
    }
    
    private void establecerRutaEnTextField(File carpetaElegida){
        rutaDeCarpeta.setText(carpetaElegida.getAbsolutePath());
        visualizador.setDisable(false);
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
        setLabelSize(extensionSize, treeMap.getFirst().getTamaño());

        SizeTotal.getChildren().addAll(graphicSizeTotal, extensionSize);
        container.getChildren().addAll(SizeTotal, graphics);
        Painting(treeMap.getFirst(), graphics, 960.0, 650.0, "h");
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
    
    
    
    public double redondeo(double numeroCompleto, int decimales) {
        return new BigDecimal(numeroCompleto).setScale(decimales, RoundingMode.HALF_EVEN).doubleValue();
    }
    
    private void iterar(LinkedList<Carpeta> treeMap, int num) {
        Iterator<Carpeta> it = treeMap.iterator();
        while (it.hasNext()) {
            Carpeta next = it.next();
            if (!next.getCarpetas().isEmpty()) {
                System.out.println(identar(num) + "Carpeta: " + next.getNombre() + "| size: " + next.getTamaño());
                iterar(next.getCarpetas(), num + 2);
            } else {
                System.out.println(identar(num) + "Archivo: " + next.getNombre() + "| size: " + next.getTamaño());
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
    
    public double recorrerCarpeta(File[] contenidoDeCarpeta, double total, Carpeta carpetaPrincipal) {
        for (File file : contenidoDeCarpeta) {
            if (file.isDirectory()) {
                double tamaño = 0.00;
                Carpeta carpeta = new Carpeta(file.getName());
                double size = redondeo(recorrerCarpeta(file.listFiles(), tamaño, carpeta), 2);
                carpeta.setTamaño(size);
                carpetaPrincipal.getCarpetas().add(carpeta);
                total += size;
            } else {
                double pesoDelArchivoEnBytes = file.length();
                double conversion = conversionDeBytesAkiloBytes(pesoDelArchivoEnBytes);
                double redondeoDeConversion = redondeo(conversion, 2);
                total += redondeoDeConversion;
                Carpeta carpeta = new Carpeta(file.getName(), redondeoDeConversion);
                carpetaPrincipal.getCarpetas().add(carpeta);
            }
        }
        return total;
    }
    
    private double conversionDeBytesAkiloBytes(double bytes){
        return bytes / valorDeEquivalenciaDeUnKiloByteAByte;
    }

    public void setLabelSize(Label lb, double amount) {
        lb.setStyle("-fx-font-weight: bold; -fx-font-size: 15");
        DecimalFormat two = new DecimalFormat("0.00");
        if (amount < 1024) {
            lb.setText("(" + amount + " KB" + ")");
        } else if (amount > 1024 && amount < 1024 * 1024) {
            lb.setText("(" + two.format(amount / 1024) + " MB" + ")");
        } else if (amount > 1024 * 1024 && amount < 1024 * 1024) {
            lb.setText("(" + two.format(amount / 1024 * 1024) + " GB" + ")");
        } else {
            lb.setText("(" + two.format(amount / 1024 * 1024 * 1024) + " TB" + ")");
        }
    }

    public Color getRandomColor() {
        Random rd = new Random();
        float r = rd.nextFloat();
        float g = rd.nextFloat();
        float b = rd.nextFloat();
        Color randomColor = new Color(r, g, b, 1);
        return randomColor;
    }

    /*public double getSize(File dir) {
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
    }*/
    
    public void Painting(Carpeta directory, Pane pane, double width, double height, String type) {
        LinkedList<Carpeta> selected = directory.getCarpetas();
        double size = directory.getTamaño(); 
        selected.forEach((elementoDeCarpeta) -> {
            if (!elementoDeCarpeta.esCarpeta() && type.equals("h")) {
                double fact1 = width * (elementoDeCarpeta.getTamaño() / size);
                double fact2 = height;
                Rectangle shape = new Rectangle(fact1, fact2);
                shape.setFill(getRandomColor());
                shape.setStrokeType(StrokeType.INSIDE);
                shape.setStroke(Color.WHITE);
                VBox temp = new VBox();
                
                temp.getChildren().addAll(shape);
                pane.getChildren().add(temp);
                shape.setOnMouseClicked(event -> {
                       
                       Alert alert=new Alert(AlertType.INFORMATION);
                       alert.setTitle("Informacion");
                       alert.setContentText("Nombre del archivo: "+elementoDeCarpeta.getNombre()+"\n"+"Peso en megabytes: "+elementoDeCarpeta.getTamaño());
                       alert.setHeaderText(null);
                       alert.showAndWait();

                   });
                
            } else if (!elementoDeCarpeta.esCarpeta() && type.equals("v")) {
                double fact1 = width;
                double fact2 = height * (elementoDeCarpeta.getTamaño() / size);
                Rectangle shape = new Rectangle(fact1, fact2);
                shape.setFill(getRandomColor());
                shape.setStrokeType(StrokeType.INSIDE);
                shape.setStroke(Color.WHITE);
                HBox temp = new HBox();
                
                temp.getChildren().addAll(shape);
                pane.getChildren().add(temp);
                
                shape.setOnMouseClicked(event -> {
                       
                       Alert alert=new Alert(AlertType.INFORMATION);
                       alert.setTitle("Informacion");
                       alert.setContentText("Nombre del archivo: "+elementoDeCarpeta.getNombre()+"\n"+"Peso en megabytes: "+elementoDeCarpeta.getTamaño());
                       alert.setHeaderText(null);
                       alert.showAndWait();

                   });
            } else if (elementoDeCarpeta.esCarpeta() && type.equals("h")) {
                double size2 = elementoDeCarpeta.getTamaño();
                VBox box = new VBox();
                box.setMaxWidth(width * (size2 / size)); 
                box.setMaxHeight(height);
                Pane panel = new Pane();
                
                
                Label lb = new Label(elementoDeCarpeta.getNombre()+elementoDeCarpeta.getTamaño());
                lb.setStyle("-fx-background-color: transparent");
                
                
                
                Painting(elementoDeCarpeta, box, box.getMaxWidth(), box.getMaxHeight(), "v");
                panel.getChildren().addAll(box,lb);
                pane.getChildren().addAll(panel);
                
                
            } else if (elementoDeCarpeta.esCarpeta() && type.equals("v")) {
                double size2 = elementoDeCarpeta.getTamaño();
                HBox box = new HBox();
                Pane panel = new Pane();
                
                
                Label lb = new Label(elementoDeCarpeta.getNombre()+ " " +elementoDeCarpeta.getTamaño());
                lb.setStyle("-fx-background-color: transparent");
                
                box.setMaxWidth(width);
                box.setMaxHeight(height * (size2 / size));
                Painting(elementoDeCarpeta, box, box.getMaxWidth(), box.getMaxHeight(), "h");
                panel.getChildren().addAll(box,lb);
                pane.getChildren().addAll(panel);
            }
        });
    }

    
    
    
}
