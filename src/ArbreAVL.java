public class ArbreAVL {

    private Node root;



    public ArbreAVL(){

        root = new Node(-1);
        root.setAltura(0);
    }

    public ArbreAVL(Node root){

        this.root = root;
        root.setAltura(0);
    }

    public void add(Node node){

        if (root.getNumero() != -1) {
            root.add(node);
        }else{
            root = node;
            root.setAltura(0);
        }
    }


}
