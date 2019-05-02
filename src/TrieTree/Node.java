package TrieTree;

public class Node {
    public Node pare;
    public int altura;

    public char lletra;
    public int value;

    public Node[] lletres = new Node[26];


    public Node() {
        altura = 1;

        for (int i = 0; i < lletres.length; i++) {
            lletres = null;
        }
    }

    public void addLetter(char[] lletra, int i) {

        this.lletra = lletra[i];

    }

    public void recorregut() {

    }




    public Node getPare() {
        return pare;
    }

    public void setPare(Node pare) {
        this.pare = pare;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public char getLletra() {
        return lletra;
    }

    public void setLletra(char lletra) {
        this.lletra = lletra;
    }
}
