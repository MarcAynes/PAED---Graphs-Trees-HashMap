package TrieTree;

public class ArbreTrie {
    Root root;

    public ArbreTrie(Root root) {

        this.root = root;
    }

    public ArbreTrie(){

        Root root = new Root();
        this.root = root;
    }

    public void add(char[] paraula){

        root.add(paraula);
    }

    public Return search(char[] paraula, int nombreDeParaulesAMostrar, Return retorna){

        retorna = root.search(paraula, nombreDeParaulesAMostrar, retorna);
        return retorna;
    }

    public void eliminarParaula(char[] paraula){
       root.eliminaNode(paraula);
    }

    public void printarTrie(){
        root.mostrar();
    }

}
