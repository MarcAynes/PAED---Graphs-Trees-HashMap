package TrieTree;

import java.io.PrintWriter;

public class Node implements trie {
    public Object pare;
    public int altura;



    public char lletra;
    public int value;

    public Node[] lletres = new Node[36];


    public Node() {
        altura = 1;
        value = 0;

        for (int i = 0; i < lletres.length; i++) {
            lletres[i] = null;
        }
    }

    public void addLetter(char[] lletra, int i, Object aiudame) {
        altura = altura < lletra.length - i ? lletra.length - i : altura;

        this.lletra = lletra[i - 1];
        pare = aiudame;
        if (lletra.length == i) {
            //cas trivial
            value++;

        } else {
            //cas no trivial
            if (lletra[i] - '0' > 9) {
                //paraula
                if (lletres[lletra[i] - 'a' + 10] == null) {
                    lletres[lletra[i] - 'a' + 10] = new Node();
                }
                lletres[lletra[i] - 'a' + 10].addLetter(lletra, i + 1, this);
            } else {
                //numero
                if (lletres[lletra[i] - '0'] == null) {
                    lletres[lletra[i] - '0'] = new Node();
                }
                lletres[lletra[i] - '0'].addLetter(lletra, i + 1, this);
            }
        }

    }

    //paraula: array que ens han introduit
    //nombre: nombre de paraules que hem retornat
    //posicio: posicioactual de la array
    public Return search(char[] paraula, int posicio, int nombreDeParaulesHaRetornar, Return retorna) {
        if (paraula.length > posicio + 1) {
            if (paraula[posicio + 1] - '0' > 9) {
                //lletra
                if (lletres[paraula[posicio + 1] - 'a' + 10] != null) {
                    retorna = lletres[paraula[posicio + 1] - 'a' + 10].search(paraula, posicio + 1,  nombreDeParaulesHaRetornar, retorna);
                } else {
                    System.out.println("no existeix cap paraula que comenci per la inserida");
                }

            } else {
                //numero
                if (lletres[paraula[posicio + 1] - '0'] != null) {
                    retorna = lletres[paraula[posicio + 1] - '0'].search(paraula, posicio + 1,  nombreDeParaulesHaRetornar, retorna);
                } else {
                    System.out.println("no existeix cap paraula que comenci per la inserida");
                }

            }
        } else {
            char[] paraulaAux = new char[paraula.length + 1];
            for (int a = 0; a < paraula.length; a++) {
                paraulaAux[a] = paraula[a];
            }
            if (value >= 1) {
                retorna.nombre++;
                if (retorna.frases != null) {
                    char[][] p = retorna.frases;
                    retorna.frases = new char[p.length + 1][];
                    for (int a = 0; a < retorna.frases.length-1; a++) {
                        retorna.frases[a] = p[a];
                    }
                    retorna.frases[retorna.frases.length-1] = paraula;
                }else{
                    retorna.frases = new char[1][];
                    retorna.frases[0] = paraula;
                }


            }

            for (int i = 0; 36 > i && retorna.nombre <= nombreDeParaulesHaRetornar; i++) {
                if (lletres[i] != null) {
                    paraulaAux[paraula.length] = lletres[i].getLletra();
                    retorna = lletres[i].search(paraulaAux.clone(), posicio + 1, nombreDeParaulesHaRetornar, retorna);
                }
            }
        }
        return retorna;
    }

    public Boolean elimina(char[] paraula, int posicio) {
        //ens posicionem a la ultima lletra de la paraula recursivament
        boolean owisim = true;
        if (paraula.length > posicio + 1) {
            if (paraula[posicio + 1] - '0' > 9) {
                //lletra
                if (lletres[paraula[posicio + 1] - 'a' + 10] != null) {
                    owisim = lletres[paraula[posicio + 1] - 'a' + 10].elimina(paraula, posicio + 1);
                }else{
                    System.out.println("no existeix cap paraula que comenci per la inserida");
                    owisim = false;
                }
            } else {
                //numero
                if (lletres[paraula[posicio + 1] - '0'] != null) {
                    owisim = lletres[paraula[posicio + 1] - '0'].elimina(paraula, posicio + 1);
                }else {
                    System.out.println("no existeix cap paraula que comenci per la inserida");
                    owisim = false;
                }
            }
        }
        if  (owisim) {
            //si som a la ultima lletra de la paraula posem el valor a 0
            if (paraula.length == posicio + 1) {
                value = 0;
            }

            boolean owo = false;
            for (int i = 0; 36 > i; i++) {
                if (lletres[i] != null) {
                    owo = true;
                    break;
                }
            }
            if (!owo) {
                if (pare instanceof trie) {
                    ((trie) pare).EliminaFill(this);
                }
            }
        }
        return owisim;

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

    public void mostrar(String distancia, PrintWriter pw){
        pw.printf("%s%c", distancia, lletra);
        System.out.printf("%s%c", distancia, lletra);
        if (value > 0){
            pw.printf("\n");
            System.out.printf("\n");
        }
        int quantitat = 0;
        for (int i = 0; 36 > i; i++) {
            if (lletres[i] != null) {
                quantitat++;
            }
        }

        int lenghth = distancia.length();
        distancia = "";
        for (int i = 0 ; lenghth >= i && quantitat > 1; i++){
            distancia += "-";
        }

        for (int i = 0; 36 > i; i++) {
            if (lletres[i] != null) {
                lletres[i].mostrar(distancia, pw);
            }
        }

    }

    public Object getPare() {
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
