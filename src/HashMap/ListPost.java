package HashMap;

import Model.Post;


public class ListPost {
    String hashtag;
    Post [] posts;
    int siguiente;

    public ListPost() {
        siguiente =0;
        posts = new Post[5];
        hashtag = null;
    }
    public boolean agregarPost (Post post,String hashtag) {
        if (this.hashtag != null) {
            if (this.hashtag.equals(hashtag)) {
                posts[siguiente++] = post;
                if (siguiente == 5) {
                    siguiente = 0;
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

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }
}
