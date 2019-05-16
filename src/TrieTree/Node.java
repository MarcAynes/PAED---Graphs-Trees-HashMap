package TrieTree;

public class Node {
    public Node pare;
    public int altura;

    public char lletra;
    public int value;

    public Node[] lletres = new Node[36];


    public Node() {
        altura = 1;
        value = 0;

        //for (int i = 0; i < lletres.length; i++) {
          //  lletres = null;
        //}
    }

    public void addLetter(char[] lletra, int i, Node aiudame) {
        altura = altura < lletra.length - i ? lletra.length-i : altura;

        this.lletra = lletra[i-1];
        pare = aiudame;
        if (lletra.length == i){
            //cas trivial
            value++;

        }else{
            //cas no trivial
            if (lletra[i] - '0' > 9){
                //paraula
                if (lletres[lletra[i] - 'a' + 10] == null){
                    lletres[lletra[i] - 'a' + 10] = new Node();
                }
                lletres[lletra[i] - 'a' + 10].addLetter(lletra, i+1, this);
            }else{
                //numero
                if (lletres[lletra[i] - '0'] == null){
                    lletres[lletra[i] - '0'] = new Node();
                }
                lletres[lletra[i] - '0'].addLetter(lletra, i+1, this);
            }
        }

    }

    public void search(char[] paraula, int posicio) {

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
