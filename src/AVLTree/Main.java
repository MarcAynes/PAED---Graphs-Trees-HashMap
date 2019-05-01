package AVLTree;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Node root =  new Node(20);
        ArbreAVL arbre = new ArbreAVL(root);
        arbre.add(new Node(17));
        arbre.add(new Node(30));
        arbre.add(new Node(15));
        arbre.add(new Node(19));
        arbre.add(new Node(29));
        arbre.add(new Node(50));
        arbre.add(new Node(14));
        arbre.add(new Node(18));
        arbre.add(new Node(35));
        arbre.add(new Node(51));
        arbre.visualitza(2);

        Scanner sc = new Scanner(System.in);

        for (;;) {
            int i = sc.nextInt();
            Node aux = new Node(i);
            arbre.delete(aux);
            arbre.visualitza(2);
        }
    }
}
