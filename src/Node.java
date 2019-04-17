public class Node {

    private int numero;

    //altura d'aquest node sent 0 el node root i altura + 1 conforme anem baixant per cada fill
    private int altura;
    
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
                filldret.setAltura(this.altura+1);
            }
        }else {

            if (fillEsquerra != null) {
                fillEsquerra.add(node);
            }else{

                fillEsquerra = node;
                fillEsquerra.setPare(this);
                fillEsquerra.setAltura(this.altura+1);
            }
        }
    }

    public void setAltura(int altura){

        this.altura = altura;
    }

    public void setPare(Node node){

        this.pare = node;
    }


    /*
        tipus = 1: PreOrdre
                2: InOrdre
                3: PostOrdre

     */
    public void visualitza(int tipus){

        if  (tipus == 1){
            System.out.println(numero);
        }

        if (fillEsquerra != null){

            fillEsquerra.visualitza(tipus);
        }

        if (tipus == 2){
            System.out.println(numero);
        }

        if (filldret != null){

            filldret.visualitza(numero);
        }

        if (tipus == 3){

            System.out.println(numero);
        }

    }


}
