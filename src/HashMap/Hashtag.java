package HashMap;

import Model.Post;

public class Hashtag {
    private String hashtag;
    private Post[] arrayPosts;
    private int cantidad;

    public Hashtag() {
        hashtag = "";
        this.arrayPosts =  new Post[2];
        cantidad = 0;
    }

    public Post[] getArrayPosts() {
        return arrayPosts;
    }

    public void setArrayPosts(Post[] arrayPosts) {
        this.arrayPosts = arrayPosts;
    }

    public void insertarElemento (Post p, String hashtag) {
        if (cantidad == arrayPosts.length) {
            Post [] arrayPostsNew = new Post[cantidad*2];
            System.arraycopy(arrayPosts,0,arrayPostsNew,0,cantidad);
            arrayPosts = arrayPostsNew;
        }
        arrayPosts[cantidad] = p;
        this.hashtag = hashtag;
        cantidad++;
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

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
