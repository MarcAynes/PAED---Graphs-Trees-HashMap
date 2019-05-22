package HashMap;

import Model.Post;
import RTree.QuickSortPosts;

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

    public int buscarPosicionHashtag(String hashtag) {
        for (int i = 0; i < arrayHashtags.length;i++) {
            if (arrayHashtags[i] != null) {
                if (arrayHashtags[i].getHashtag().equals(hashtag)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public Post[] buscar5postConHashtag (String hashtag) {
        int id = buscarPosicionHashtag(hashtag);
        Post [] resultat = new Post[5];
        if (id != -1) {
            int i  =0;
            Post [] posts = arrayHashtags[id].getArrayPosts();
            QuickSortPosts quickSortPosts = new QuickSortPosts();
            quickSortPosts.quickSort(posts,new ComparatorTimestampsPost(),0,arrayHashtags[id].getCantidad()-1);
            while (i < 5 && i  < arrayHashtags[id].getCantidad()) {
                resultat[i] = posts[i];
                i++;
            }
        }
        return resultat;
    }
}
