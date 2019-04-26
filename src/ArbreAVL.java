public class ArbreAVL {

    private Node root;

    public ArbreAVL(Node root){

        this.root = root;
        root.setAltura(1);
    }

    public void add(Node node){
        if (root.getNumero() != -1) {
            root.add(node, this);
        }else{
            root = node;
            root.setAltura(1);
        }
    }

    /*
        tipus = 1: PreOrdre
                2: InOrdre
                3: PostOrdre

     */

    public void visualitza(int tipus){
        root.visualitza(tipus);
    }

    public void setRoot(Node a){
        root = a;
    }


}
