import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Node root = new Node(sc.nextInt());
        ArbreAVL arbre = new ArbreAVL(root);


        for (;;){
            int i = sc.nextInt();
            if (i == -1) {
                break;
            }

            Node aux = new Node(i);
            arbre.add(aux);
            arbre.visualitza(2);


        }

         for (;;) {
             int i = sc.nextInt();
             Node aux = new Node(i);
            arbre.delete(aux);
            arbre.visualitza(2);
         }
    }
}
