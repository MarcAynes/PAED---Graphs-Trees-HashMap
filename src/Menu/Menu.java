package Menu;

import AVLTree.ArbreAVL;
import Graph.Graph;
import HashMap.HashMap;
import Model.Post;
import Model.User;
import RTree.RTree;
import TrieTree.ArbreTrie;
import TrieTree.Node;
import TrieTree.Root;

import java.util.Scanner;

public class Menu {
    public int nombreDeParaulesHaRetornar;

    public Post[] posts;
    public User[] users;

    public ArbreAVL arbreAVL;
    public ArbreTrie arbreTrieIds;
    public ArbreTrie arbreTrieUsersNames;
    public HashMap hashMap;
    public RTree rTree;
    public Graph graph;

    public Node nodeTrie;

    public Menu() {
        int opcion;
        nombreDeParaulesHaRetornar = 5;

        System.out.println("Bienvenid@!");
        do {
            opcionesPosibles();
            System.out.println("Opción:");
            opcion = entradaTerminal();
            funcionalidad(opcion);

        } while (opcion != 8);



    }

    public void opcionesPosibles() {
        System.out.println("Menu:\n" +
                "\t1. Importación de ficheros\n" +
                "\t2. Exportación de ficheros\n" +
                "\t3. Visualización de una estructura\n" +
                "\t4. Inserción de información\n" +
                "\t5. Borrar información\n" +
                "\t6. Búsqueda de información\n" +
                "\t7. Limitar memória para autocompletar\n" +
                "\t8. Salir");
    }

    public int entradaTerminal() {
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public void funcionalidad(int opcio) {


        switch (opcio) {
            case 1:
                System.out.println("Importación de fichero");
                Importacio importacio = new Importacio();

                System.out.println("Importando estructuras...");

                arbreTrieIds = new ArbreTrie();
                arbreTrieUsersNames = new ArbreTrie();
                hashMap = new HashMap();
                rTree = new RTree(3, 5);
                graph = new Graph();
                arbreAVL = new ArbreAVL(new AVLTree.Node(posts[0]));

                for (Post p : posts) {
                    //Importación de ids al Trie
                    arbreTrieIds.add(String.valueOf(p.getId()).toCharArray());
                    //Importación post al HashMap
                    hashMap.agregarPost(p);
                    if (p != posts[0]) {
                        //Importación post al AVL
                        arbreAVL.add(new AVLTree.Node(p));
                    }
                    //Importación post al RTree
                    rTree.insertarElemento(p);
                }

                for (User u : users) {
                    //Importación de los usernames al Trie
                    arbreTrieUsersNames.add(u.getUsername().toCharArray());
                }

                //Importación de los usuarios al Grafo
                graph.insertarJSON(users);

                break;

            case 2:
                System.out.println("Exportación de ficheros");
                break;

            case 3:
                System.out.println("Visualización de una estructura");
                break;

            case 4:
                System.out.println("Inserción de información");
                break;

            case 5:
                System.out.println("Borrar información");
                break;

            case 6:
                System.out.println("Búsqueda de información");
                break;

            case 7:
                System.out.println("Limitar memória para autocompletar.\n" +
                        "Actualamente el límite se encuentre en [" + nombreDeParaulesHaRetornar + "] palabras\n" +
                        "Que nuevo límite de palabras quieres establecer?");
                nombreDeParaulesHaRetornar = entradaTerminal();
                System.out.println("Procesando petición...");
                System.out.println("El límite de palabras se ha actualizado a [" + nombreDeParaulesHaRetornar + "");
                break;

            case 8:
                System.out.println("Hasta pronto!");
                break;

            default:
                System.out.println("Opción incorrecta!");
                break;
        }
    }


}

