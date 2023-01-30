
package Modelo;

import java.util.LinkedList;

public class Carpeta {
    
    private String nombre;
    private Double tamaño;

    public Carpeta(String nombre, Double tamaño) {
        this.nombre = nombre;
        this.tamaño = tamaño;
    }

    public String getNombre() {
        return nombre;
    }

    public Double getTamaño() {
        return tamaño;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTamaño(Double tamaño) {
        this.tamaño = tamaño;
    }
    
}
