package TrieTree;

public class Node {
    public Node pare;
    public int altura;

    public char lletra;
    public int value;

    public Node[] lletres = new Node[36];


    public Node() {
        altura = 1;

        for (int i = 0; i < lletres.length; i++) {
            lletres = null;
        }
    }

    public void addLetter(char[] lletra, int i) {
        altura = altura < lletra.length - i ? lletra.length-i : altura;

        this.lletra = lletra[i];
        if (lletra.length == i+1){
            //cas trivial
            value++;

        }else{
            //cas no trivial
            if (lletra[i+1] - '0' > 9){
                //paraula
                if (lletres[lletra[i+1] - 'a'] == null){
                    lletres[lletra[i+1] - 'a'] = new Node();
                }
                lletres[lletra[i+1] - 'a'].addLetter(lletra, i+1);
            }else{
                //numero
                if (lletres[lletra[i+1] - '0'] == null){
                    lletres[lletra[i+1] - '0'] = new Node();
                }
                lletres[lletra[i+1] - '0'].addLetter(lletra, i+1);
            }
        }

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
