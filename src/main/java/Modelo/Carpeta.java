
package Modelo;

import java.util.LinkedList;


public class Carpeta {
    
    private String nombre;
    private Double tamaño;
    private LinkedList<Carpeta> carpetas;

    public Carpeta(String nombre, Double tamaño) {
        this.nombre = nombre;
        this.tamaño = tamaño;
        this.carpetas = new LinkedList<>();
    }

    public Carpeta(String nombre) {
        this.nombre = nombre;
        this.tamaño = 0.0;
        this.carpetas = new LinkedList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public Double getTamaño() {
        return tamaño;
    }

    public LinkedList<Carpeta> getCarpetas() {
        return carpetas;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTamaño(Double tamaño) {
        this.tamaño = tamaño;
    }

    public void setCarpetas(LinkedList<Carpeta> carpetas) {
        this.carpetas = carpetas;
    }

    @Override
    public String toString() {
        return "Carpeta{" + "nombre=" + nombre + ", tama\u00f1o=" + tamaño + ", carpetas=" + carpetas + '}';
    }
    
    public boolean esCarpeta() {
        return !this.getCarpetas().isEmpty();
    }
    
}
