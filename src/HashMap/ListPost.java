package HashMap;

import Model.Post;


public class ListPost {
    Post [] posts;
    int siguiente;
    public ListPost() {
        siguiente =0;
        posts = new Post[5];
    }
    public void agregarPost (Post post) {
        posts[siguiente++] = post;
        if (siguiente == 5) {
            siguiente= 0;
        }
    }
    public Post [] printarPosts (){
        Post [] postsAux = new Post[5];
        int contadorAux = siguiente-1;
        if (!(contadorAux == -1 && posts[4] == null)) {
            for (int i = 0; i < 5; i++) {
                if (contadorAux == -1 ) {
                    contadorAux = 4;
                }
                postsAux[i] = posts[contadorAux--];
            }
        }

        return postsAux;
    }
}
