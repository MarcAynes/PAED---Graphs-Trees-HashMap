package RTree;

import Model.Post;

public class RTreeNoOptimizado {
    private Post[] arrayPosts;
    private int cantidad;

    public RTreeNoOptimizado() {
        this.arrayPosts =  new Post[2];
    }

    public Post[] getArrayPosts() {
        return arrayPosts;
    }

    public void setArrayPosts(Post[] arrayPosts) {
        this.arrayPosts = arrayPosts;
    }

    public void insertarElemento (Post p) {
        if (cantidad == arrayPosts.length) {
            Post [] arrayPostsNew = new Post[cantidad*2];
            System.arraycopy(arrayPosts,0,arrayPostsNew,0,cantidad);
            arrayPosts = arrayPostsNew;
        }
        arrayPosts[cantidad] = p;
        cantidad++;
    }
    public Post[] buscarPotsDentroRadio (double latitud, double longitud, double radio) {
        Post[] resultado = new Post[cantidad];
        int contador = 0;
        double latitudMin = latitud - radio;
        double latitudMax = latitud + radio;
        double longitudMax = longitud + radio;
        double longitudMin = longitud - radio;

        if (latitudMin < -90) {
            latitudMin = -90;
        }
        if (latitudMax > 90) {
            latitudMax = 90;
        }
        if (longitudMin < -180) {
            longitudMin = -180;
        }
        if (longitudMax > 180) {
            longitudMax = 180;
        }

        for (int i = 0; i < arrayPosts.length;i++) {
            if (arrayPosts[i] != null) {
                double latitudPost = arrayPosts[i].getLocation()[0];
                double longitudPost = arrayPosts[i].getLocation()[1];
                if (latitudPost >= latitudMin && latitudPost <= latitudMax && longitudPost >= longitudMin && longitudPost <= longitudMax) {
                    resultado[contador] = arrayPosts[i];
                    contador++;
                }
            }
        }
        return resultado;
    }
    public void eliminarPost (Post post) {
        for (int i = 0; i < arrayPosts.length;i++) {
            if (arrayPosts[i] != null) {
                if (arrayPosts[i].getId() == post.getId()) {
                    arrayPosts[i] = null;
                    arrayPosts[i] = arrayPosts [cantidad-1];
                    arrayPosts[--cantidad] = null;
                    break;
                }
            }
        }
    }

}
