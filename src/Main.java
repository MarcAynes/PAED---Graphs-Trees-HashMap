import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Node root = new Node(40);
        ArbreAVL arbre = new ArbreAVL(root);

        for (int i = 0; i < 10; i++){
            Random rn = new Random();
            Node aux = new Node(rn.nextInt(100));
            arbre.add(aux);
        }

        arbre.visualitza(2);
    }
}
