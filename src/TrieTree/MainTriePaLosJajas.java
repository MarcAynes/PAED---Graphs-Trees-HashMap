package TrieTree;

import java.util.Scanner;

public class MainTriePaLosJajas {
    public static void main(String[] args) {

        Root root = new Root();
        ArbreTrie arbreTrie = new ArbreTrie(root);

        Scanner sc = new Scanner(System.in);
        Node aux = new Node();

        for (;;) {
            char[] paraula = sc.next().toCharArray();
            aux.addWord(paraula);
        }

    }
}
