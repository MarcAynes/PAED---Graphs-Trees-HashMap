package AVLTree;

import Model.Post;

import java.io.PrintWriter;

public class ArbreAVL {

    private Node root;

    public ArbreAVL(Node root){

        this.root = root;
        root.setAltura(1);
    }

    public void add(Node node){
        if (root.getNumero() != -1 ) {
            root.add(node, this);
        }else{
            root = node;
            root.setAltura(1);
        }
    }

    public void delete(Node node){
        root.delete(node,this);
    }

    /*
        tipus = 1: PreOrdre
                2: InOrdre
                3: PostOrdre

     */

    public void exporta(int tipus, PrintWriter pw){
        root.visualitza(tipus, pw);
    }

    public void visualitza(){
        root.visualitzaPerTerminal();
    }

    public Post cerca(int numero){
        return root.cerca(numero);
    }

    public void setRoot(Node a){
        root = a;
    }

    public Post[] returnPosts(){
        return root.returnPosts();
    }


}
