package AVLTree;

import Model.Post;

public class Node {
    private int numero;
    private Post post;

    //profunditat d'aquest node sent 0 el node root i altura + 1 conforme anem baixant per cada fill
    private int altura;
    private int profunditat;

    private int balanceFactor;

    private int alturaMaxDreta;
    private int alturaMinDreta;

    private int alturaMaxEsquerra;
    private int alturaMinEsquerra;

    private Node pare;

    private Node fillDret;
    private Node fillEsquerra;

    public Node(Post post){

        this.post = post;
        this.numero = post.getId();
        pare = null;
        altura = 1;
    }

    public Node(int i){
        this.numero =i;
        pare = null;
        altura = 1;
    }

    public int getNumero(){
        return this.numero;
    }

    public void add (Node node, ArbreAVL arbre) {
        //Si el node actual a inserir es mes gran que el node actual
        if (node.getNumero() > numero) { //ex: numero actual 5 numero a inserir el 7
            //7 al fill dret

            //Si el node actual te un fill dret
            if (fillDret != null) {
                fillDret.add(node, arbre);

                if (fillEsquerra != null) {

                    if (fillEsquerra.getAltura() < fillDret.getAltura()) {
                        altura = fillDret.getAltura() + 1;

                    } else {
                        altura = fillEsquerra.getAltura()+1;
                    }

                } else {
                    altura = fillDret.getAltura() + 1;
                }

                //Si el node actual no te fill dret, l'afegim
            } else {
                fillDret = node;
                fillDret.setPare(this);
                fillDret.setProfunditat(1);

                //Actualitzacio de l'altura d'aquest node
                if (fillEsquerra != null) {
                    if (fillEsquerra.getAltura() > fillDret.getAltura()) {
                        altura = fillEsquerra.getAltura() + 1;

                    } else {
                        altura = fillDret.getAltura() + 1;
                    }

                } else {
                    altura = fillDret.getAltura() + 1;
                }

            }

            //Si el node actual a inserir es mes petit que el node actual
        } else {

            //Si el node actual te un fill esquerra
            if (fillEsquerra != null) {

                fillEsquerra.add(node, arbre);

                if (fillDret != null){

                    if (fillEsquerra.getAltura() < fillDret.getAltura()){

                        altura = fillDret.getAltura()+1;
                    }else {
                        altura = fillEsquerra.getAltura()+1;
                    }
                }else{

                    altura = fillEsquerra.getAltura()+1;
                }

                //Si el node actual no te fill esquerra, l'afegim
            } else {
                fillEsquerra = node;
                fillEsquerra.setPare(this);
                fillEsquerra.setProfunditat(this.profunditat+1);

                if (fillDret != null){
                    if  (fillEsquerra.getAltura() > fillDret.getAltura() ){
                        altura = fillEsquerra.getAltura()+1;

                    }else{
                        altura = fillDret.getAltura()+1;
                    }

                }else{
                    altura = fillEsquerra.getAltura()+1;
                }

            }
        }

        rotacions(arbre);
        //definirAltura();
    }

    public void delete (Node node, ArbreAVL arbre) {
        if (node.getNumero() == numero){
            //eliminar aquest node
            if (fillDret == null && fillEsquerra == null){
                //eliminar fulla cas trivial
                if (pare != null) {
                    if (pare.getFillDret().equals(this)) {
                        pare.setFilldret(null);
                    } else {
                        pare.setFillEsquerra(null);
                    }
                } else {
                    arbre.setRoot(new Node(-1));
                }
            } else {
                if(fillEsquerra == null || fillDret == null){
                    Node aux;
                    if (fillDret == null){
                        aux = fillEsquerra;
                    } else {
                        aux = fillDret;
                    }
                    aux.setPare(pare);
                    if (pare != null) {
                        if (pare.getFillDret().equals(this)) {
                            pare.setFilldret(aux);
                        } else {
                            pare.setFillEsquerra(aux);
                        }
                    } else {
                        arbre.setRoot(aux);
                    }
                } else {
                    Node aux = fillDret.cercaSubst(arbre);
                    aux.setPare(pare);
                    aux.setFillEsquerra(fillEsquerra);
                    aux.setFilldret(fillDret);
                    if (pare != null) {
                        if (pare.getFillDret().equals(this)) {
                            pare.setFilldret(aux);
                        } else {
                            pare.setFillEsquerra(aux);
                        }
                    } else {
                        arbre.setRoot(aux);
                    }
                    aux.definirAltura();
                    aux.rotacions(arbre);
                    fillEsquerra = null;
                    fillDret = null;
                }
            }
            if (pare != null) {
                pare.definirAltura();
                pare.rotacions(arbre);
                pare = null;
            }

        } else {
            if(node.getNumero() <= numero && fillEsquerra != null){
                fillEsquerra.delete(node, arbre);

            }else if(fillDret != null){
                fillDret.delete(node, arbre);
            }

            if (getFillEsquerra().getAltura() > getFillDret().getAltura()) {
                altura = getFillEsquerra().getAltura() + 1;
            } else {
                altura = getFillDret().getAltura() + 1;
            }
            rotacions(arbre);

        }
    }

    private Node cercaSubst(ArbreAVL arbre){

        if (fillEsquerra == null){

            if (fillDret != null){
                pare.setFillEsquerra(fillDret);
                fillDret.setPare(pare);
            }else{
                if (pare.getFillDret().equals(this)){
                    pare.setFilldret(null);
                }else{
                    pare.setFillEsquerra(null);
                }
            }

            pare.definirAltura();
            pare.rotacions(arbre);

            return this;
        }else{
            Node auxi = fillEsquerra.cercaSubst(arbre);
            if (fillDret == null) {
                altura = fillEsquerra.getAltura() + 1;
            } else if (fillEsquerra.getAltura() > fillDret.getAltura()) {
                altura = fillEsquerra.getAltura() + 1;
            } else {
                altura = fillDret.getAltura() + 1;
            }
            rotacions(arbre);
            return auxi;
        }

    }

    public void setProfunditat(int profunditat){

        this.profunditat = profunditat;
    }

    public void setPare(Node node){
        this.pare = node;
    }

    /*  tipus = 1: PreOrdre
                2: InOrdre
                3: PostOrdre
     */
    public void visualitza(int tipus){
        if  (tipus == 1){
            printNode();
        }

        if (fillEsquerra != null){
            fillEsquerra.visualitza(tipus);
        }

        if (tipus == 2) {
            printNode();
        }

        if (fillDret != null){
            fillDret.visualitza(tipus);
        }

        if (tipus == 3){
            printNode();
        }
    }

    public void printNode() {
        for (int i = 0; i < altura; i++){
            System.out.print("|---");
        }
        System.out.print(">");

        if (pare == null) {
            System.out.println("Root AVLTree.Node: " + numero + ", Altura: " + altura);
        } else if (pare.numero < numero) {
            System.out.println("Right AVLTree.Node: " + numero + ", Altura: " + altura);
        } else if (pare.numero >= numero){
            System.out.println("Left AVLTree.Node: " + numero + ", Altura: " + altura);
        }
    }

    //Mitjançant un PostOrder redefinim l'alçada de tots els nodes
    public void definirAltura() {
        if (fillEsquerra != null){
            fillEsquerra.definirAltura();
        }
        if (fillDret != null){
            fillDret.definirAltura();
        }

        if (fillDret == null && fillEsquerra == null) {
            altura = 1;
        } else if (fillDret == null) {
            altura = fillEsquerra.getAltura() + 1;
        } else if (fillEsquerra == null) {
            altura = fillDret.getAltura() + 1;
        } else if (fillEsquerra.getAltura() > fillDret.getAltura()) {
            altura = fillEsquerra.getAltura() + 1;
        } else {
            altura = fillDret.getAltura() + 1;
        }

    }

    public int getAltura(){
        return altura;
    }

    public Node getFillEsquerra(){
        if (fillEsquerra != null){
            return fillEsquerra;

        } else {
            Node node = new Node(0);
            node.setAltura(0);
            return node;
        }
    }

    public Node getFillDret(){
        if (fillDret != null){
            return fillDret;

        } else {
            Node node = new Node(0);
            node.setAltura(0);
            return node;
        }

    }

    public void setFillEsquerra(Node fill){

        fillEsquerra = fill;
    }

    public void setFilldret(Node fill){

        fillDret = fill;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    private void rotacions(ArbreAVL arbre){
        //Comprovem si hi ha desaquilibri en el node actual
        if (Math.abs(getFillEsquerra().getAltura() - getFillDret().getAltura()) >= 2) {
            //rotacions cas esquerra -> rotacions cap a la dreta
            //left left case = right rotation
            //
            if (getFillEsquerra().getAltura() > getFillDret().getAltura()) {

                if (fillEsquerra.getFillDret().getAltura() <= fillEsquerra.getFillEsquerra().getAltura()){
                    leftleft(arbre);

                }else{
                    leftright(arbre);
                }

            } else {

                //El desaquilibri es troba en le fill dret
                if (fillDret.getFillEsquerra().getAltura() <= fillDret.getFillDret().getAltura()){
                    rightright(arbre);

                }else{
                    rightleft(arbre);
                }

            }

            pare.definirAltura();

        }

    }

    private void leftleft(ArbreAVL arbre){
        //ens guardem el fill esquerra el cual ara sera el pare en un node auxiliar
        Node auxiliar = fillEsquerra;

        //posem com a fill esquerra el node mes semblant per la esquerra (el fill dret del nostre fill esquerre)
        if (fillEsquerra.getFillDret().getAltura() == 0){
            fillEsquerra = null;
        } else {
            fillEsquerra = fillEsquerra.getFillDret();
            fillEsquerra.setPare(this);
        }

        //posem el node actual com a fill dret del auxiliar (el que abans era el nostre fill esquerra)
        //si abans era el nostre fill esquerra vol dir que erem un nombre menor al seu
        //per tant aquest fill al "pujar" tinddra un nombre menor al pare i aquest passara a ser un fill dret
        auxiliar.setFilldret(this);
        isRoot(auxiliar, arbre);

    }

    private void leftright(ArbreAVL arbre){
        //left right rotation
        Node auxiliar = fillEsquerra;
        fillEsquerra = fillEsquerra.getFillDret();  //intercambient el fill esquerra per el fill dret del fill esquerra
        fillEsquerra.setPare(this);

        if (fillEsquerra.getFillEsquerra().getAltura() == 0){
            auxiliar.setFilldret(null);
        }else{
            auxiliar.setFilldret(fillEsquerra.getFillDret());       //el fill dret del auxiliar es el fill esquerra del node que acabem d'intercanviar
            auxiliar.getFillDret().setPare(auxiliar);               //i el pare del auxiliar ara sera el fill esquerra del anteriorment intercanviat,
        }                                                           //el que ara es el nostre fill esquerra

        fillEsquerra.setFillEsquerra(auxiliar);
        auxiliar.setPare(fillEsquerra);

        auxiliar = fillEsquerra;

        if (fillEsquerra.getFillDret().getAltura() == 0){
            fillEsquerra = null;
        } else {
            fillEsquerra = fillEsquerra.getFillDret();
            fillEsquerra.setPare(this);
        }

        auxiliar.setFilldret(this);
        isRoot(auxiliar, arbre);

    }

    private void rightright(ArbreAVL arbre) {
        //Mirem si es un right right case o right left case

        //Rotacions cas dret -> rotacions cap a l'esquerra
        //right right case = left rotation

        //Ens guardem el fill dret el qual ara sera el pare en un node auxiliar
        Node auxiliar = fillDret;

        //Posem com a fill dret el node mes semblant per la dreta (el fill esquerra del nostre fill dret)
        if (fillDret.getFillEsquerra().getAltura() == 0) {
            fillDret = null;

        } else {
            fillDret = fillDret.getFillEsquerra();
            fillDret.setPare(this);
        }

        auxiliar.setFillEsquerra(this);

        isRoot(auxiliar, arbre);

    }

    private void rightleft(ArbreAVL arbre) {
        Node auxiliar = fillDret;
        fillDret = fillDret.getFillEsquerra();
        fillDret.setPare(this);

        if (fillDret.getFillEsquerra().getAltura() == 0) {
            auxiliar.setFillEsquerra(null);
        } else {
            auxiliar.setFillEsquerra(fillDret.getFillDret());
            auxiliar.setPare(this);

        }

        fillDret.setFilldret(auxiliar);
        auxiliar.setPare(fillDret);

        auxiliar = fillDret;

        if (fillDret.getFillEsquerra().getAltura() == 0){
            fillDret = null;
        } else {
            fillDret = fillDret.getFillEsquerra();
            fillDret.setPare(this);
        }

        auxiliar.setFillEsquerra(this);
        isRoot(auxiliar, arbre);
    }

    /**
     * Comprovem que el auxiliar que s'ha rotat i queda amunt és root o no és root. Finalment fem l'assignació del node
     * rootat com a pare el auxiliar.
     * @param auxiliar
     * @param arbre
     */
    private void isRoot(Node auxiliar, ArbreAVL arbre) {
        //Si el node no era root
        if (pare != null) {
            //Posem el nou fill dret o esquerra del node pare
            if (pare.getFillDret().equals(this)){
                pare.setFilldret(auxiliar);
            }else{
                pare.setFillEsquerra(auxiliar);
            }

            auxiliar.setPare(pare);

        } else {
            //Si el node es root hem de modificar la classe arbre
            arbre.setRoot(auxiliar);
            auxiliar.setPare(null);
        }

        pare = auxiliar;

        //definim les noves altures
        auxiliar.definirAltura();

    }


}
