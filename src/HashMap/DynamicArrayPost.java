package HashMap;

import Graph.PosicionUser;
import Model.Post;
import Model.User;
import RTree.PollasEnAlmibar;

public class DynamicArrayPost {
    private Post[] valores;
    private int cantidad;

    public DynamicArrayPost(){
        valores = new Post[2];
        cantidad = 0;
    }


    public void insertarElemento (Post elementoAInsertar) {
        if (cantidad == valores.length) {
            ampliarArray ();

        }
        valores [cantidad] = elementoAInsertar;
        cantidad++;

        PollasEnAlmibar pollasEnAlmibar = new PollasEnAlmibar();
        pollasEnAlmibar.quickSort(valores,new ComparatorTimestampsPost(),0,cantidad-1);
    }

    private void ampliarArray () {
        Post valoresNew [] = new Post [2*cantidad];
        System.arraycopy(valores,0,valoresNew,0,cantidad);
        valores = valoresNew;
    }

    public boolean eliminarElemento (int idPost) {
        for (int j = 0; j < cantidad;j++) {
            if (valores[j] != null && valores[j].getId() == idPost) {
                valores[j] = null;
                valores[j] = valores[cantidad-1];
                valores [cantidad-1] = null;
                cantidad--;

                PollasEnAlmibar pollasEnAlmibar = new PollasEnAlmibar();
                pollasEnAlmibar.quickSort(valores,new ComparatorTimestampsPost(),0,cantidad-1);

                return true;
            }
        }
        return false; //Usuario no encontrado!
    }

    public Post buscarUsuario() {
        Post pepe = valores [0];
        eliminarElemento(pepe.getId());

        return pepe;
    }


    public Post[] getValores() {
        return valores;
    }


    public void setValores(Post[] valores) {
        this.valores = valores;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
