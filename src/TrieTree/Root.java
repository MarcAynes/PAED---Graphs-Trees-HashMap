package TrieTree;

public class Root {
        //lletraes minuscula + numeros
    public Node[] lletres = new Node[36];

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

        if (paraula[0] - '0' > 9 ){
            //numero
            if (lletres[paraula[0] - '0'] == null){
                lletres[paraula[0] - '0'] = new Node();
            }
            lletres[paraula[0] - '0'].addLetter(paraula, 1);
        }else{
            //lletra
            if (lletres[paraula[0] - 'a'] == null){
                lletres[paraula[0] - 'a'] = new Node();
            }
            lletres[paraula[0] - 'a'].addLetter(paraula, 1);
        }


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
