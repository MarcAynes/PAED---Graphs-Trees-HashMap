package TrieTree;

public class Node implements trie {
    public Object pare;
    public int altura;

    private static final int nombreDeParaulesHaRetornar = 5;

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
    public int search(char[] paraula, int posicio, int nombre) {
        if (paraula.length > posicio + 1) {
            if (paraula[posicio + 1] - '0' > 9) {
                //lletra
                if (lletres[paraula[posicio + 1] - 'a' + 10] != null) {
                    nombre = lletres[paraula[posicio + 1] - 'a' + 10].search(paraula, posicio + 1, 1);
                } else {
                    System.out.println("no existeix cap paraula que comenci per la inserida");
                }

            } else {
                //numero
                if (lletres[paraula[posicio + 1] - '0'] != null) {
                    nombre = lletres[paraula[posicio + 1] - '0'].search(paraula, posicio + 1, 1);
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
                paraulaAux[paraulaAux.length - 1] = ' ';
                nombre++;
                String printa = new String(paraulaAux);
                System.out.println(printa);
            }

            for (int i = 0; 36 > i && nombre <= nombreDeParaulesHaRetornar; i++) {
                if (lletres[i] != null) {
                    paraulaAux[paraula.length] = lletres[i].getLletra();
                    nombre = lletres[i].search(paraulaAux, posicio + 1, nombre);
                }
            }
        }
        return nombre;
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

    public void mostrar(String distancia){
        System.out.printf("%s%c", distancia, lletra);
        if (value > 0){
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
                lletres[i].mostrar(distancia);
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
