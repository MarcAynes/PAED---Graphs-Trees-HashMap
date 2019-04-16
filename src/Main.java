import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Node node = new Node(40);
        ArbreAVL arbre = new ArbreAVL(node);

        for (int i = 0; i < 10; i++){
            Random rn = new Random();
            Node aux = new Node(rn.nextInt(100));
            arbre.add(aux);
        }
        System.out.println("aiudame");

    }
}
