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
            if (cantidad == valores.length) {

            } else {
                valores[cantidad] = postAInsertar;
                cantidad++;
            }
    }


    public void busquedaSiguienteRectangulo (int altura, Post postAInsertar) {
        double incrementoMinimo = Double.MAX_VALUE;
        double areaMinima = Double.MAX_VALUE;
        double areaCalculada = 0;
        double incrementoCalculado = 0;
        int indice =0;
        for (int y = 0; y < cantidad;y++){
            incrementoCalculado = ((Rectangulo) valores[y]).calcularIncremento(postAInsertar);
            if (incrementoCalculado == 0) {
                indice = y;
                break;
            }
            if (incrementoCalculado < incrementoMinimo ) {
                indice = y;
                incrementoMinimo = incrementoCalculado;
                areaMinima = ((Rectangulo) valores[y]).calculoAreaActual();
            }
            else if (incrementoCalculado == incrementoMinimo) {
                areaCalculada = ((Rectangulo) valores[y]).calculoAreaActual();
                if (areaCalculada < areaMinima) {
                    indice = y;
                    areaMinima = areaCalculada;
                }
                //Es raro que tengan la misma area, asi que si tienen la misma aleatorio y ya
            }
        }
        ((Rectangulo) valores[indice]).bajarArbol(altura-1,postAInsertar);
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
