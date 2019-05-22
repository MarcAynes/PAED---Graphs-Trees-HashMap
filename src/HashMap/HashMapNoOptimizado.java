package HashMap;

import Model.Post;

public class HashMapNoOptimizado {
    private Hashtag[] arrayHashtags;

    private int cantidad;

    public HashMapNoOptimizado () {
        arrayHashtags = new Hashtag[2069];
        cantidad = 0;
    }

    public void agregarPost (Post post) {
        for (int y  =0; y < post.getHashtags().length;y++) {
            if (post.getHashtags()[y] != null) {
                int id = buscarPosicionHashtag (post.getHashtags()[y]);
                if (id != -1) {
                    arrayHashtags[id].insertarElemento(post,post.getHashtags()[y]);
                }
                else {
                    arrayHashtags [cantidad] = new Hashtag();
                    arrayHashtags [cantidad].insertarElemento(post,post.getHashtags()[y]);
                    cantidad++;
                }
            }
        }
    }

    private int buscarPosicionHashtag(String hashtag) {
        for (int i = 0; i < arrayHashtags.length;i++) {
            if (arrayHashtags[i] != null) {
                if (arrayHashtags[i].getHashtag().equals(hashtag)) {
                    return i;
                }
            }
        }
        return -1;
    }
}
