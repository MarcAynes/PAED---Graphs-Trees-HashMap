public class Node {

    private int numero;

    //altura d'aquest node sent 0 el node root i altura + 1 conforme anem baixant per cada fill
    private int altura;
    private int profunditat;
    
    private int balanceFactor;

    private int alturaMaxDreta;
    private int alturaMinDreta;

    private int alturaMaxEsquerra;
    private int alturaMinEsquerra;

    private Node pare;

    private Node filldret;
    private Node fillEsquerra;

    public Node(int numero){
        this.numero = numero;
    }

    public int getNumero(){
        return this.numero;
    }

    public void add(Node node){

        if (node.getNumero() > numero) { //ex: numero actual 5 numero a inserir el 7
                                        // 7 al fill dret
            if (filldret != null) {

                filldret.add(node);
            }else{
                filldret = node;
                filldret.setPare(this);
                filldret.setProfunditat(this.profunditat+1);
            }
        }else {

            if (fillEsquerra != null) {
                fillEsquerra.add(node);
            }else{

                fillEsquerra = node;
                fillEsquerra.setPare(this);
                fillEsquerra.setProfunditat(this.profunditat+1);
            }
        }

        definirAltura();
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

        if (filldret != null){
            filldret.visualitza(tipus);
        }

        if (tipus == 3){
            printNode();
        }
    }

    public void printNode() {
        for (int i = 0; i < altura; i++){
            System.out.print("--");
        }
        System.out.print(">");

        if (pare == null) {
            System.out.println("Root Node: " + numero + ", Altura: " + altura);
        } else if (pare.numero < numero) {
            System.out.println("Right Node: " + numero + ", Altura: " + altura);
        } else if (pare.numero > numero){
            System.out.println("Left Node: " + numero + ", Altura: " + altura);
        }
    }

    //Mitjançant un PostOrder redefinim l'alçada de tots els nodes
    public void definirAltura() {
        if (fillEsquerra != null){
            fillEsquerra.definirAltura();
        }
        if (filldret != null){
            filldret.definirAltura();
        }

        if (filldret == null && fillEsquerra == null) {
            altura = 0;
        } else if (filldret == null) {
            altura = fillEsquerra.altura + 1;
        } else if (fillEsquerra == null) {
            altura = filldret.altura + 1;
        } else if (fillEsquerra.altura > filldret.altura) {
            altura = fillEsquerra.altura + 1;
        } else {
            altura = filldret.altura + 1;
        }

    }

}
