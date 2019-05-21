package Menu;

import AVLTree.ArbreAVL;
import AVLTree.Node;
import Graph.Graph;
import HashMap.HashMap;
import Model.Post;
import Model.User;
import RTree.RTree;
import TrieTree.ArbreTrie;
import TrieTree.Return;
import TrieTree.Root;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Menu {
    public int nombreDeParaulesHaRetornar;

    public Post[] posts;
    public User[] users;

    public ArbreAVL arbreAVL = null;
    public ArbreTrie arbreTrieIds = null;
    public ArbreTrie arbreTrieUsersNames = null;
    public HashMap hashMap = null;
    public RTree rTree = null;
    public Graph graph = null;

    public boolean estructuresBuides;

    public Menu() {
        int opcion;
        estructuresBuides = true;
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

    //TODO: Si em retorna el Trie un char[][] de length == 0, NO FER RES, ja que no hi ha cap paraula que coincideixi

        switch (opcio) {
            case 1:
                System.out.println("Importación de fichero");
                Importacio importacio = new Importacio();

                System.out.println("Importando estructuras...");

                posts = importacio.getPost();
                users = importacio.getUser();

                arbreTrieIds = new ArbreTrie();
                arbreTrieUsersNames = new ArbreTrie();
                hashMap = new HashMap();
                rTree = new RTree(3, 5);
                graph = new Graph();
                arbreAVL = new ArbreAVL(new AVLTree.Node(posts[0]));

                for (Post p : posts) {
                    //Importación de ids al Trie
                    arbreTrieIds.add(String.valueOf(p.getId()).toLowerCase().toCharArray());
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

                //Desactivamos el flag de que las estructuras estan vacías
                estructuresBuides = false;
                break;

            case 2:
                if (!estructuresBuides) {
                    System.out.println("Exportación de ficheros");

                }else {
                    System.out.println("Estructuras vacías, prueba de inserir o importar préviamente algo");
                }
                break;

            case 3:
                if (!estructuresBuides) {
                    System.out.println("Visualización de una estructura\n Que estructura desea visualizar?");
                    System.out.println("1. Trie\n" +
                            "2. R-Tree\n" +
                            "3. AVL Tree\n" +
                            "4. Tabla Hash\n" +
                            "5. Graph");
                    Scanner scStruct = new Scanner(System.in);
                    FileWriter fichero = null;
                    PrintWriter pw = null;
                    switch (scStruct.nextInt()) {
                        case 1:
                            try {
                                fichero = new FileWriter("files/trie.txt");
                                pw = new PrintWriter(fichero);
                                arbreTrieUsersNames.printarTrie(pw);
                                fichero.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            break;
                        case 3:
                            try {
                                fichero = new FileWriter("files/AVL.txt");
                                pw = new PrintWriter(fichero);
                                arbreAVL.visualitza(2, pw);
                                fichero.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                    }

                } else {
                    System.out.println("Estructuras vacías, prueba de inserir o importar préviamente algo");
                }
                break;

            case 4:
                int i = 0;
                String[][] matriz;

                System.out.println("Inserción de información");
                System.out.println("1. Nuevo Usuario\n" +
                        "2. Nuevo Post");

                Scanner scIn = new Scanner(System.in);

                //TODO: Hace falta controlar que cuando un post se inserte, que exista el usuario??

                //Antes de inserir un usuario o post, si alguna estructura no está inicializada, la inicializamos
                if (scIn.nextInt() == 1) {
                    System.out.println("Nombre de usuario:");
                    String name = scIn.next();
                    System.out.println("Fecha de creación");
                    long fecha = scIn.nextLong();
                    User user = new User(name, fecha);

                    System.out.println("Usuario que segurá [Y/N]:");
                    matriz = new String[users.length][];

                    while (scIn.next().equals("Y")) {
                        matriz[i][scIn.next().length()] = scIn.next();
                        System.out.println("Usuario que segurá [Y/N]:");
                    }

                    for (String[] follows : matriz) {
                        user.setTo_follow(follows);
                    }

                    if (arbreTrieUsersNames == null) {
                        arbreTrieUsersNames = new ArbreTrie();
                    } else if (graph == null) {
                        graph = new Graph();
                    }

                    //Inserción Trie of Usernames
                    arbreTrieUsersNames.add(user.getUsername().toCharArray());
                    /* TODO: Insercion de usuario que sigue???
                    for (String[] s : matriz) {
                        for (String sf : s) {
                            arbreTrieUsersNames.add(sf.toCharArray());
                        }
                    }*/

                    //Inserción Grafo
                    graph.insertarUsuario(user);

                } else {
                    Post post = new Post();

                    post.setId(posts.length + 1);
                    System.out.println("Publicación  de el post:");
                    post.setPublished_when(scIn.nextInt());
                    System.out.println("Publicado por:");
                    post.setPublished_by(scIn.next());
                    double[] localizacion = null;
                    System.out.println("Latitud publicación:");
                    localizacion[0] = scIn.nextDouble();
                    System.out.println("Longitud publicación:");
                    localizacion[1] = scIn.nextDouble();
                    post.setLocation(localizacion);

                    System.out.println("Hashtags de la publicación [Y/N]:");
                    matriz = new String[posts.length][];

                    while (scIn.next().equals("Y")) {
                        matriz[i][scIn.next().length()] = scIn.next();
                        System.out.println("Hashtags de la publicación [Y/N]:");
                    }

                    for (String[] hashtag : matriz) {
                        post.setHashtags(hashtag);
                    }

                    System.out.println("Ha dado alguien like? [Y/N]:");
                    matriz = new String[posts.length][];

                    while (scIn.next().equals("Y")) {
                        matriz[i][scIn.next().length()] = scIn.next();
                        System.out.println("Ha dado alguien like? [Y/N]:");
                    }

                    for (String[] user_like : matriz) {
                        post.setLiked_by(user_like);
                    }

                    if (arbreTrieIds == null) {
                        arbreTrieIds = new ArbreTrie();

                    } else if (hashMap == null) {
                        hashMap = new HashMap();

                    } else if (rTree == null) {
                        rTree = new RTree(3, 5);

                    } else if (arbreAVL == null) {
                        arbreAVL = new ArbreAVL(new AVLTree.Node(post));

                    } else {
                        //Inserción AVL Tree
                        arbreAVL.add(new Node(post));
                    }

                    //Inserción Trie of IDs
                    arbreTrieIds.add(String.valueOf(post.getId()).toCharArray());

                    //Inserción Hashmap
                    hashMap.agregarPost(post);

                    //Inserción RTree
                    rTree.insertarElemento(post);

                }
                break;

            case 5:
                if (!estructuresBuides) {
                    System.out.println("Borrar información\n Que tipo de información quieres inserir?");
                    System.out.println("1. Usuario\n" +
                            "2. Post");
                } else {
                    System.out.println("Estructuras vacías, prueba de inserir o importar préviamente algo");
                }
                break;

            case 6:
                if (!estructuresBuides) {
                    System.out.println("Búsqueda de información\n Que tipo de información quieres buscar?");
                    System.out.println("1. Usuario\n" +
                            "2. Post\n" +
                            "3. Según Hashtag\n" +
                            "4. Según Ubicación");

                    //TODO: Preguntar al Pernia si els 5 posts a buscar dins del hasmap son els 5 ultims inserits o publicats (timestamp)

                    //TODO: Hacer busqueda trie
                    Scanner sc = new Scanner(System.in);
                    switch (sc.nextInt()) {
                        case 1:
                            Return r = new Return();
                            r.setNombre(nombreDeParaulesHaRetornar);

                            arbreTrieUsersNames.search(sc.next().toCharArray(), nombreDeParaulesHaRetornar, r);
                            break;
                        case 2:
                            break;

                        case 3:
                            break;

                        case 4:
                            break;

                        default:
                            System.out.println("Opción incorrecta");
                            break;
                    }

                } else {
                    System.out.println("Estruturas vacías");
                }

            case 7:
                System.out.println("Limitar memória para autocompletar.\n" +
                        "Actualmente el límite se encuentre en [" + nombreDeParaulesHaRetornar + "] palabras\n" +
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

