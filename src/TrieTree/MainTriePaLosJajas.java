package TrieTree;

import java.util.Scanner;

public class MainTriePaLosJajas {
    public static void main(String[] args) {

        Root root = new Root();
        ArbreTrie arbreTrie = new ArbreTrie(root);

        Scanner sc = new Scanner(System.in);
        Node aux = new Node();

        for (;;) {
            char[] paraula = sc.next().toLowerCase().toCharArray();

            if (paraula[0] == '-'){
                break;
            }

            //modificacio de la paraula en cas que no estigui en el abecedari
            for (int j =0; j < paraula.length; j++){
                paraula[j] = paraula[j] == 'ç' ? 'c': paraula[j];
                paraula[j] = paraula[j] == 'ñ' ? 'n': paraula[j];
            }
            root.add(paraula);
        }

        for (;;){
            char[] paraula = sc.next().toLowerCase().toCharArray();

            if (paraula[0] == '-'){
                break;
            }

            for (int j =0; j < paraula.length; j++){
                paraula[j] = paraula[j] == 'ç' ? 'c': paraula[j];
                paraula[j] = paraula[j] == 'ñ' ? 'n': paraula[j];
            }
            root.search(paraula);
        }

        for (;;){
            char[] paraula = sc.next().toLowerCase().toCharArray();

            if (paraula[0] == '-'){
                break;
            }

            for (int j =0; j < paraula.length; j++){
                paraula[j] = paraula[j] == 'ç' ? 'c': paraula[j];
                paraula[j] = paraula[j] == 'ñ' ? 'n': paraula[j];
            }
            root.eliminaNode(paraula);
        }

        for (;;){
            char[] paraula = sc.next().toLowerCase().toCharArray();

            if (paraula[0] == '-'){
                break;
            }

            for (int j =0; j < paraula.length; j++){
                paraula[j] = paraula[j] == 'ç' ? 'c': paraula[j];
                paraula[j] = paraula[j] == 'ñ' ? 'n': paraula[j];
            }
            root.search(paraula);
        }


    }
}
