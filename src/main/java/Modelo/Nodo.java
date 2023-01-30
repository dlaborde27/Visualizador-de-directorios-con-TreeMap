package Modelo;

import java.util.LinkedList;
import java.util.List;

public class Nodo<T> {
    public T contenido;
    private List<Arbol<T>> listaDeHijos = new LinkedList<>();
    

    public Nodo(T contenido) {
        this.contenido = contenido;
    }

    public void añadirHijo(T hijo) {
        this.listaDeHijos.add(new Arbol<>(hijo));
    }
    
    public Arbol<T> obtenerUltimoHijo(){
        int indiceDeUltimoHijo = this.listaDeHijos.size()-1;
        return this.listaDeHijos.get(indiceDeUltimoHijo);
    }
    
    public List<Arbol<T>> getHijos(){
        return this.listaDeHijos;
    }
    
    public boolean esHoja(){
        return this.listaDeHijos.isEmpty();
    }
    
}
