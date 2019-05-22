package TrieTree;

import java.io.PrintWriter;

public class Root implements trie{
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
            lletres[paraula[0] - 'a' + 10].addLetter(paraula, 1, this);
        }else{
            //lletra
            if (lletres[paraula[0] - '0'] == null){
                lletres[paraula[0] - '0'] = new Node();
            }
            lletres[paraula[0] - '0'].addLetter(paraula, 1, this);
        }

    }

    public Return search(char[] paraula, int nombreDeParaulesAMostrar, Return retorna){
        nombreDeParaulesAMostrar--;

        if (paraula[0] - '0' > 9 ){
            //numero
            if (lletres[paraula[0] - 'a' + 10] != null){
                retorna = lletres[paraula[0] - 'a' + 10].search(paraula, 0, nombreDeParaulesAMostrar, retorna);
            }else{
                System.out.println("no existeix cap paraula que comenci per la inserida");
            }

        }else{
            //lletra
            if (lletres[paraula[0] - '0'] != null){
                retorna = lletres[paraula[0] - '0'].search(paraula, 0, nombreDeParaulesAMostrar, retorna);
            }else{
                System.out.println("no existeix cap paraula que comenci per la inserida");
            }

        }
        return retorna;
    }

    public void eliminaNode(char[] paraula){
        if (paraula[0] - '0' > 9 ){
            //numero
            if (lletres[paraula[0] - 'a' + 10] != null){
                lletres[paraula[0] - 'a' + 10].elimina(paraula, 0);
            }else{
                System.out.println("no existeix cap paraula que comenci per ");
            }

        }else{
            //lletra
            if (lletres[paraula[0] - '0'] != null){
                lletres[paraula[0] - '0'].elimina(paraula, 0);
            }else{
                System.out.println("no existeix cap paraula que comenci per ");
            }

        }
    }

    public void mostrar(PrintWriter pw){
        for (int i = 0; 36 > i; i++) {
            if (lletres[i] != null) {
                lletres[i].mostrar("", pw);
            }
        }
    }

    public void mostrarPerTerminal(){
        for (int i = 0; 36 > i; i++) {
            if (lletres[i] != null) {
                lletres[i].mostrarPerTerminal("");
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

    @Override
    public void EliminaFill(Node fill) {
        for (int i = 0; 36 > i; i++) {
            if (lletres[i] != null && lletres[i].equals(fill)) {
                lletres[i] = null;
                break;
            }
        }
    }
}
