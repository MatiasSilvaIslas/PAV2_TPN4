package frgp.utn.edu.com.tabcontrol.clases;

import java.io.Serializable;

public class Articulo implements Serializable {

    private int id;
    private String nombre;
    private int stock;
    private int idCategoria;

    public Articulo() {}

    public Articulo(int id, String nombre, int stock, int idCategoria) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.idCategoria = idCategoria;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
}
