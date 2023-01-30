
package Modelo;

import java.io.File;
import org.apache.commons.io.FileUtils;


public class BuilderArbol {
    
    Arbol<Carpeta> arbol;
    
    public BuilderArbol(){}
    
    public Arbol<Carpeta> construirArbol(File carpetaBase){
        this.arbol = new Arbol<>(contruirCarpeta(carpetaBase));
        File[] archivosEnCarpeta = carpetaBase.listFiles();
        recorridoDentroDeCarpeta(archivosEnCarpeta,arbol);
        return this.arbol;
    }
    
    private Carpeta contruirCarpeta(File carpetaEnFile){
        String nombreCarpeta = carpetaEnFile.getName();
        Double pesoCarpetaEnBytes = Double.valueOf(FileUtils.sizeOf(carpetaEnFile));
        return new Carpeta(nombreCarpeta, Conversor.conversionDeBytesAkiloBytes(pesoCarpetaEnBytes));
    }
    
    private void recorridoDentroDeCarpeta(File[] archivosEnCarpeta, Arbol<Carpeta> arbolParaActualizacion){
        for(File f : archivosEnCarpeta){
            Carpeta carpeta = contruirCarpeta(f);
            Nodo<Carpeta> nodoPadre = arbolParaActualizacion.root;
            nodoPadre.añadirHijo(carpeta);
            if(f.isDirectory()){
                recorridoDentroDeCarpeta(f.listFiles(), nodoPadre.obtenerUltimoHijo());
            }
        }
    }
    
}
