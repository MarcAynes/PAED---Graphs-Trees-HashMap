package TrieTree;

public class Node {
    public Node fillDret;
    public Node fillEsquerra;
    public Node pare;
    public int altura;

    public char lletra;
    public int value;

    public Node[] lletres = new Node[26];


    public Node() {
        altura = 1;
    }

    public void addWord(char[] paraula) {
        lletra = paraula[0];


    }

    public void recorregut() {

    }


    public Node getFillDret() {
        return fillDret;
    }

    public void setFillDret(Node fillDret) {
        this.fillDret = fillDret;
    }

    public Node getFillEsquerra() {
        return fillEsquerra;
    }

    public void setFillEsquerra(Node fillEsquerra) {
        this.fillEsquerra = fillEsquerra;
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
