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
    public ArbreTrie arbreTrie;
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
                int estructura;

                System.out.println("Importación de fichero");
                Importacio importacio = new Importacio();

                System.out.println("Importando estructuras...");

                estructura = entradaTerminal();

                for (Post p : posts) {

                }

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

