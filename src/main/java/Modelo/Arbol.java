package Modelo;


public class Arbol<T> {

    public Nodo<T> root;

    public Arbol(T contenido) {
        this.root = new Nodo<>(contenido);
    }
    
    public Arbol() {
        this.root = null;
    }

    public void add(T contenido) {
        this.root.añadirHijo(contenido);
    }

}
