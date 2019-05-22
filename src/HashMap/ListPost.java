package HashMap;

import Model.Post;


public class ListPost {
    String hashtag;
    Post [] posts;
    int siguiente;
    int minimo;
    long timetagMinimo;
    DynamicArrayPost postsBackup;

    public ListPost() {
        siguiente = 0;
        minimo = 0;
        timetagMinimo = 0;
        posts = new Post[5];
        postsBackup = new DynamicArrayPost();
        hashtag = null;
    }
    public boolean agregarPost (Post post,String hashtag) {
        if (this.hashtag != null) {
            if (this.hashtag.equals(hashtag)) {
                if (siguiente == 5) {
                    if(post.getPublished_when() > timetagMinimo){
                        postsBackup.insertarElemento(posts[minimo]);
                        posts[minimo] = post;
                        comprobarMinimo();
                    }
                    else {
                        postsBackup.insertarElemento(post);
                    }
                }
                else {
                    posts[siguiente++] = post;
                    comprobarMinimo();

                }
                return true;
            } else {
                return false;
            }
        }
        else {
            this.hashtag = hashtag;
            posts[siguiente++] = post;
            if (siguiente == 5) {
                siguiente = 0;
            }
            return true;
        }
    }

    private void comprobarMinimo() {
        if(posts[0] != null){
            timetagMinimo = posts[0].getPublished_when();
            minimo = 0;

            for (int i = 1; i < siguiente; i++){
                if(posts[i].getPublished_when() < timetagMinimo){
                    timetagMinimo = posts[i].getPublished_when();
                    minimo = i;
                }
            }
        }
        else {
            minimo = 0;
            timetagMinimo = 0;
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

    public boolean eliminarPost (Post postAEliminar) {
        for(int i = 0; i < siguiente; i++){
            if (posts[i].getId() == postAEliminar.getId()) {
                if (siguiente < 5) {
                    posts[i] = null;
                    posts[i] = posts [siguiente-1];
                    posts[--siguiente] = null;

                }
                else {
                    if(postsBackup.getCantidad() != 0){
                        posts[i] = postsBackup.buscarUsuario();

                    }
                    else {
                        posts[i] = null;
                        posts[i] = posts [siguiente-1];
                        posts[--siguiente] = null;

                    }
                }
                comprobarMinimo();
                return true;
            }
        }

        return postsBackup.eliminarElemento(postAEliminar.getId());

    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public Post[] getPosts() {
        return posts;
    }

    public void setPosts(Post[] posts) {
        this.posts = posts;
    }

    public DynamicArrayPost getPostsBackup() {
        return postsBackup;
    }

    public void setPostsBackup(DynamicArrayPost postsBackup) {
        this.postsBackup = postsBackup;
    }
}
