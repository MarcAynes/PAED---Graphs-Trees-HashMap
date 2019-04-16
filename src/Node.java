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

        if (node.getNumero() < numero) {

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


}
