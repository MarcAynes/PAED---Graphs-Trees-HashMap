package HashMap;

import Model.Post;

public class NodoPost {
    Post postActual;
    NodoPost anteriorPost;
    NodoPost siguientePost;


    public NodoPost(Post postActual) {
        this.postActual = postActual;
    }

    public Post getPostActual() {
        return postActual;
    }

    public void setPostActual(Post postActual) {
        this.postActual = postActual;
    }

    public NodoPost getAnteriorPost() {
        return anteriorPost;
    }

    public void setAnteriorPost(NodoPost anteriorPost) {
        this.anteriorPost = anteriorPost;
    }

    public NodoPost getSiguientePost() {
        return siguientePost;
    }

    public void setSiguientePost(NodoPost siguientePost) {
        this.siguientePost = siguientePost;
    }
}
