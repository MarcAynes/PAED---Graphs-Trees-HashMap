package TrieTree;

public class Root {
    public char[] abecedari = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                                'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                                's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public Node[] lletres = new Node[26];

    public Node pare;
    public int altura;

    public Root() {
        pare = null;
        altura = 1;

        for (int i = 0; i < lletres.length; i++) {
            lletres = null;
        }
    }

    public void add(char[] paraula){
        for(char aux:paraula){
            if  (aux == paraula[0]){


            }
        }
    }

    public char[] getAbecedari() {
        return abecedari;
    }

    public void setAbecedari(char[] abecedari) {
        this.abecedari = abecedari;
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

    public Node[] getLletres() {
        return lletres;
    }

    public void setLletres(Node[] lletres) {
        this.lletres = lletres;
    }
}
