package Modelo;

import java.io.File;
import java.util.Comparator;
import java.util.Stack;

public class Arbol<T> {

    Nodo<T> root;

    public Arbol(T contenido) {
        this.root = new Nodo<>(contenido);
    }
    
    public Arbol() {
        this.root = null;
    }

    public void add(T contenido) {
        this.root.añadirHijo(contenido);
    }

    public boolean esVacio() {
        return root == null;
    }
    

}
