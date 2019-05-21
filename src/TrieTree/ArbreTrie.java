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

    public void search(char[] paraula){

        root.search(paraula);
    }

    public void eliminarParaula(char[] paraula){
        root.search(paraula);

    }

    public void printarTrie(){
        root.mostrar();
    }

}
