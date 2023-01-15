package Modelo;

import java.util.LinkedList;

class Nodo<T> {

    T contenido;
    private LinkedList<Arbol<T>> listaDeHijos = new LinkedList<>();

    public Nodo(T contenido) {
        this.contenido = contenido;
    }

    public boolean esHoja() {
        return this.listaDeHijos.isEmpty();
    }

    public void añadirHijo(T hijo) {
        this.listaDeHijos.add(new Arbol<>(hijo));
    }
    
    
}
