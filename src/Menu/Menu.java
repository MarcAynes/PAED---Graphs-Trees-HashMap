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
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
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
        Scanner scOp = new Scanner(System.in);
        do {
            opcionesPosibles();
            System.out.println("Opción:");
            opcion = scOp.nextInt();
            funcionalidad(opcion);
        } while (opcion != 8);
    }

    public void opcionesPosibles() {
        System.out.println("\nMenu:\n" +
                "\t1. Importación de ficheros\n" +
                "\t2. Exportación de ficheros\n" +
                "\t3. Visualización de una estructura\n" +
                "\t4. Inserción de información\n" +
                "\t5. Borrar información\n" +
                "\t6. Búsqueda de información\n" +
                "\t7. Limitar memória para autocompletar\n" +
                "\t8. Salir");
    }

    public void funcionalidad(int opcio) {

        switch (opcio) {
            case 1: //Done
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

                    FileWriter fichero = null;
                    PrintWriter pw = null;

                    //Tries
                    System.out.println("1. Exportar ficheros de visualizacion");
                    System.out.println("2. Exportar ficheros de datos");
                    Scanner scEx = new Scanner(System.in);
                    int i = scEx.nextInt();
                    switch (i) {
                        case 1:
                            try {
                                fichero = new FileWriter("files/trie_posts.txt");
                                pw = new PrintWriter(fichero);
                                arbreTrieIds.exportarTrie(pw);
                                fichero.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            try {
                                fichero = new FileWriter("files/trie_users.txt");
                                pw = new PrintWriter(fichero);
                                arbreTrieUsersNames.exportarTrie(pw);
                                fichero.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            //AVL Tree
                            try {
                                fichero = new FileWriter("files/AVL.txt");
                                pw = new PrintWriter(fichero);
                                arbreAVL.exporta(2, pw);
                                fichero.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            //HashMap
                            hashMap.pasarHashMapAJSON();

                            //RTree
                            rTree.exportacionVisualizacionRTree();

                            break;

                        case 2:

                            //TODO: exportacion datos para futura informacion (usuarios y posts)
                            try {
                                fichero = new FileWriter("files/posts.json");
                                pw = new PrintWriter(fichero);
                                Post[] p = arbreAVL.returnPosts();

                                Gson gson = new Gson();
                                gson.toJson(p, fichero);

                            } catch (IOException e) {
                                System.out.println("error exportar posts");
                            }

                            //TODO: Falta exportació de usuarios amb el graph!

                            break;
                    }

                    //Graph
                    graph.guardarEnJSON();


                }else {
                    System.out.println("Estructuras vacías, prueba de inserir o importar préviamente algo");
                }
                break;

            case 3: //Done
                if (!estructuresBuides) {
                    System.out.println("Visualización de una estructura\n Que estructura desea visualizar?");
                    System.out.println("1. Trie\n" +
                            "2. R-Tree\n" +
                            "3. AVL Tree\n" +
                            "4. Tabla Hash\n" +
                            "5. Graph");
                    Scanner scStruct = new Scanner(System.in);

                    switch (scStruct.nextInt()) {
                        case 1:
                            arbreTrieUsersNames.printarTrie();
                            break;

                        case 2:
                            rTree.visualizacionRTreeTerminal();
                            break;

                        case 3:
                            //Visualización en preorden
                            arbreAVL.visualitza();
                            break;

                        case 4:
                            hashMap.hashMapVisualizacion();
                            break;

                        case 5:
                            graph.visualizacionGraph();
                            break;
                    }

                } else {
                    System.out.println("Estructuras vacías, prueba de inserir o importar préviamente algo");
                }
                break;

            case 4: //Done
                int i = 0;
                String[][] matriz;

                System.out.println("Inserción de información");
                System.out.println("1. Nuevo Usuario\n" +
                        "2. Nuevo Post");

                Scanner scIn = new Scanner(System.in);
                //Antes de inserir un usuario o post, si alguna estructura no está inicializada, la inicializamos
                if (scIn.nextInt() == 1) {
                    System.out.println("Nombre de usuario:");
                    String name = scIn.next();
                    System.out.println("Fecha de creación");
                    long fecha = scIn.nextLong();
                    User user = new User(name, fecha);

                    System.out.println("Usuario que seguirá [Y/N]:");
                    if (scIn.next().equals("Y")) {
                        if (users != null) {
                            matriz = new String[users.length][];
                        } else {
                            matriz = new String[1][];
                        }

                        while (scIn.next().equals("Y")) {
                            matriz[i][scIn.next().length()] = scIn.next();
                            System.out.println("Usuario que seguirá [Y/N]:");
                        }

                        for (String[] follows : matriz) {
                            user.setTo_follow(follows);
                        }
                    }

                    if (arbreTrieUsersNames == null) arbreTrieUsersNames = new ArbreTrie();

                    if (graph == null) graph = new Graph();

                    //No hacemos comprovación de que si existen los usuario a los cuales sigue

                    //Inserción Trie of Usernames
                    arbreTrieUsersNames.add(user.getUsername().toLowerCase().toCharArray());

                    //Inserción Grafo
                    graph.insertarUsuario(user);

                } else {
                    Post post = new Post();

                    if (posts != null) {
                        post.setId(posts.length + 1);
                    } else {
                        post.setId(1);
                    }

                    System.out.println("Fecha de publicación:");
                    post.setPublished_when(scIn.nextInt());
                    System.out.println("Publicado por:");
                    post.setPublished_by(scIn.next());

                    if (graph.buscarUsuario(post.getPublished_by()) != null) {
                        double[] localizacion = new double[2];
                        System.out.println("Latitud publicación:");
                        localizacion[0] = scIn.nextDouble();
                        System.out.println("Longitud publicación:");
                        localizacion[1] = scIn.nextDouble();
                        post.setLocation(localizacion);

                        System.out.println("Hashtags de la publicación [Y/N]:");
                        if (posts != null) {
                            matriz = new String[posts.length][];
                        } else {
                            matriz = new String[1][];
                        }

                        while (scIn.next().equals("Y")) {
                            matriz[i][scIn.next().length()] = scIn.next();
                            System.out.println("Hashtags de la publicación [Y/N]:");
                        }

                        if (matriz.length > 0) {
                            for (String[] hashtag : matriz) {
                                post.setHashtags(hashtag);
                            }
                        }

                        System.out.println("Ha dado alguien like? [Y/N]:");
                        if (posts != null) {
                            matriz = new String[posts.length][];
                        } else {
                            matriz = new String[1][];
                        }

                        while (scIn.next().equals("Y")) {
                            matriz[i][scIn.next().length()] = scIn.next();
                            System.out.println("Ha dado alguien like? [Y/N]:");
                        }

                        if (matriz.length > 0) {
                            for (String[] user_like : matriz) {
                                post.setLiked_by(user_like);
                            }
                        }

                        if (arbreTrieIds == null) {
                            arbreTrieIds = new ArbreTrie();

                        }
                        if (hashMap == null) {
                            hashMap = new HashMap();

                        }
                        if (rTree == null) {
                            //TODO: definir el millor cas pel RTree depenent dels limits de posts per area
                            rTree = new RTree(3, 5);

                        }
                        if (arbreAVL == null) {
                            arbreAVL = new ArbreAVL(new AVLTree.Node(post));

                        } else {
                            //Inserción AVL Tree
                            arbreAVL.add(new Node(post));
                        }

                        //Inserción Trie of IDs
                        arbreTrieIds.add(String.valueOf(post.getId()).toCharArray());

                        //Inserción Hashmap
                        if (post.getHashtags() != null) {
                            hashMap.agregarPost(post);
                        } else {
                            System.out.println("-----> No se puede inserir dentro del Hashmap ya que esta publicación no tiene hashtags");
                        }

                        //Inserción RTree
                        rTree.insertarElemento(post);

                    } else {
                        System.out.println("Error, este usuario no existe dentro del sistema");
                    }


                }
                break;

            case 5: //TODO: Acabar
                if (!estructuresBuides) {
                    Scanner scBr = new Scanner(System.in);

                    System.out.println("Borrar información\n Que tipo de información quieres Borrar?");
                    System.out.println("1. Usuario\n" +
                            "2. Post");

                    if (scBr.nextInt() == 1) {
                        //TODO: Si user s'esborra, borrar els seus post, tb likes?
                        System.out.println("Nombre de usuario a borrar: ");
                        String name = scBr.next();

                        graph.eliminarUsuario(name);
                        arbreTrieUsersNames.eliminarParaula(name.toLowerCase().toCharArray());

                    } else {
                        System.out.println("Id de el post a borrar: ");
                        int idPost = scBr.nextInt();
                        Post postABorrar = new Post();
                        postABorrar.setId(idPost);

                        //Eliminación AVL
                        arbreAVL.delete(new Node(postABorrar));

                        //Eliminación RTree
                        rTree.eliminacionEnRtree(postABorrar);

                        //Eliminación en Trie
                        arbreTrieIds.eliminarParaula(String.valueOf(idPost).toCharArray());

                        //Eliminación
                    }

                    scBr.close();
                } else {
                    System.out.println("Estructuras vacías, prueba de inserir o importar préviamente algo");
                }
                break;

            case 6: //TODO: Revisar
                if (!estructuresBuides) {
                    System.out.println("Búsqueda de información\n Que tipo de información quieres buscar?");
                    System.out.println("1. Usuario\n" +
                            "2. Post\n" +
                            "3. Según Hashtag\n" +
                            "4. Según Ubicación");

                    Scanner sc = new Scanner(System.in);
                    switch (sc.nextInt()) {
                        case 1: //TODO: Revisar
                            Return r = new Return();

                            String word = null;
                            int opcioT;
                            boolean getOut = false;

                            //TODO: Si em retorna el Trie un char[][] de length == 0, NO FER RES, ja que no hi ha cap paraula que coincideixi
                            word = sc.next();
                            do{
                                System.out.println("Posibles sugerencias");
                                r = arbreTrieUsersNames.search(word.toLowerCase().toCharArray(), nombreDeParaulesHaRetornar, r);

                                int j = 0;
                                char[][] palabras = r.getFrases();

                                //Si no ha encontrado ninguna palabra, el Trie ya avisa que no coincide ninguna con el valor buscado
                                if (palabras != null) {
                                    for (char[] palabra : palabras) {
                                        String mostrar = new String(palabra);
                                        System.out.println(j + ". " + mostrar);
                                        j++;
                                    }

                                    System.out.println(r.getFrases().length  + ". Ninguna de las sugeridas");
                                    opcioT = sc.nextInt();

                                    if (r.getFrases().length + 1 != opcioT) {
                                        String mostrar = new String(palabras[opcioT]);
                                        System.out.println("Cargar la información de el usuario [" + mostrar + "] [Y/N]");
                                        if (sc.next().equals("Y")) {
                                            getOut = true;

                                            User user = graph.buscarUsuario(mostrar);

                                            printUser(user);
                                            //TODO: falta obtener el numero de posts del usuario buscado, se podria hacer
                                            // con una variable incremental recorriendo el AVL
                                        }

                                    } else {
                                        System.out.println("Cargar la información de el usuario [" + word + "] [Y/N]");
                                        if (sc.next().equals("Y")) {
                                            getOut = true;

                                            User user = graph.buscarUsuario(word);

                                            printUser(user);
                                            //TODO: falta obtener el numero de posts del usuario buscado, se podria hacer
                                            // con una variable incremental recorriendo el AVL
                                        } else {
                                            word += sc.next();
                                        }
                                    }
                                } else {
                                    getOut = true;

                                }

                            } while (!getOut);
                            break;

                        case 2:
                            System.out.print("Id publicación (hay" + posts.length + "publicaciones): ");
                            try {
                                printPost(arbreAVL.cerca(sc.nextInt()));
                            } catch (NullPointerException e) {
                                System.out.println("No existe este post dentro de el AVL Tree");
                            }
                            break;

                        case 3:
                            System.out.print("Hashtag específico a buscar: ");
                            hashMap.buscarPostsConHashTAG(sc.next());
                            break;

                        case 4:
                            double latitud, longitud, radio = 0;
                            System.out.println("Latitud: ");
                            latitud = sc.nextDouble();
                            System.out.println("Longitud: ");
                            longitud = sc.nextDouble();
                            System.out.println("Radio máximo: ");
                            radio = sc.nextDouble();

                            Post[] postInRatio = rTree.busquedaEnRtree(latitud, longitud, radio);

                            int post_totales = 0;

                            for(int k = 0; k < postInRatio.length; k++) {
                                if (postInRatio[k] != null) post_totales++;
                            }

                            if (postInRatio.length > 0) {
                                System.out.println("Se ha encontrado " + post_totales + " posts dentro de un radio máximo [" + radio + "km]");
                                int i_p = 1;

                                for (Post p : postInRatio) {
                                    if (p != null) {
                                        System.out.println("\n[POST " + i_p + "]");
                                        i_p++;
                                        printPost(p);
                                        System.out.println("\n");
                                    }
                                }

                            } else {
                                System.out.println("No se ha encontrado ningún post!");
                            }

                            break;

                        default:
                            System.out.println("Opción incorrecta");
                            break;
                    }

                } else {
                    System.out.println("Estruturas vacías");
                }
                break;

            case 7: //Done
                Scanner scLimit = new Scanner(System.in);
                System.out.println("Limitar memória para autocompletar.\n" +
                        "Actualmente el límite se encuentre en [" + nombreDeParaulesHaRetornar + "] palabras\n" +
                        "Que nuevo límite de palabras quieres establecer?");
                nombreDeParaulesHaRetornar = scLimit.nextInt();
                System.out.println("Procesando petición...");
                System.out.println("El límite de palabras se ha actualizado a [" + nombreDeParaulesHaRetornar + "]");
                scLimit.close();
                break;

            case 8: //Done
                System.out.println("Hasta pronto!");
                break;

            default: //Done
                System.out.println("Opción incorrecta!");
                break;
        }
    }

    //Utils
    private void printPost(Post p) {
        double[] localizacion = p.getLocation();

        Timestamp stamp = new Timestamp(p.getPublished_when());
        Date date = new Date(stamp.getTime());

        System.out.print("Post id: " + p.getId() +
                "\n Publicado por: " + p.getPublished_by() +
                "\n Fecha creación: " + date +
                "\n Localización (latitud, longitud): " + localizacion[0] + ", " + localizacion[1] +
                "\n Hashtags: ");

        for (String hashtag : p.getHashtags()) {
            System.out.print(hashtag + ", ");
        }
    }

    private void printUser(User u) {
        System.out.print("Username: " + u.getUsername()
                + "\nFecha creación: " + u.getCreation()
                + "\nUsuarios que sigue: ");

        for (String fll : u.getTo_follow()) {
            System.out.print(fll + ", ");
        }
    }

}

