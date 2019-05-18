package TrieTree;

public class Root {
        //lletraes minuscula + numeros
    public Node[] lletres = new Node[36];

    public Node pare;
    public int altura;

    public Root() {
        pare = null;
        altura = 1;

        //for (int i = 0; i < lletres.length; i++) {
        //  lletres = null;
        //}
    }

    public void add(char[] paraula){

        if (paraula[0] - '0' > 9 ){
            //numero
            if (lletres[paraula[0] - 'a' + 10] == null){
                lletres[paraula[0] - 'a' + 10] = new Node();
            }
            lletres[paraula[0] - 'a' + 10].addLetter(paraula, 1, null);
        }else{
            //lletra
            if (lletres[paraula[0] - '0'] == null){
                lletres[paraula[0] - '0'] = new Node();
            }
            lletres[paraula[0] - '0'].addLetter(paraula, 1, null);
        }

    }

    public void search(char[] paraula){
        if (paraula[0] - '0' > 9 ){
            //numero
            if (lletres[paraula[0] - 'a' + 10] != null){
                lletres[paraula[0] - 'a' + 10].search(paraula, 0, 0);
            }else{
                System.out.println("no existeix cap paraula que comenci per " + paraula.toString());
            }

        }else{
            //lletra
            if (lletres[paraula[0] - '0'] != null){
                lletres[paraula[0] - '0'].search(paraula, 0, 0);
            }else{
                System.out.println("no existeix cap paraula que comenci per " + paraula.toString());
            }

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
