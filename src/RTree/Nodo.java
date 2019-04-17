package RTree;

import Model.Post;

public class Nodo {
    private byte tipo; //Para saber dentro del nodo (0 rectangulo y 1 punto)
    private Object [] valores;
    private int cantidad;

    public Nodo(int max) {
        cantidad = 0;
        valores = new Object [max];
        tipo = 1;
    }

    public void insertarPost (Post postAInsertar) {
        if (tipo == 1) {
            if (cantidad == valores.length) {

            } else {
                valores[cantidad] = postAInsertar;
                cantidad++;
            }
        }
        else {

        }
    }


    public byte getTipo() {
        return tipo;
    }

    public void setTipo(byte tipo) {
        this.tipo = tipo;
    }

    public Object[] getValores() {
        return valores;
    }

    public void setValores(Object[] valores) {
        this.valores = valores;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
