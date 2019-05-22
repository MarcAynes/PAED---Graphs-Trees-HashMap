package HashMap;

import Model.Post;
import RTree.QuickSortPosts;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

public class HashMap {
    private ListPost [] hashMap;

    public HashMap() {
        this.hashMap = new ListPost[2069];
    }

    public void agregarPost (Post post) {
        if (post.getHashtags() != null) {
            for (String hashtag : post.getHashtags()) {
                int id = hashFunction(hashtag, 0);
                int contador = 1;
                if (hashMap[id] != null) {
                    while (!hashMap[id].agregarPost(post, hashtag)) {
                        id = hashFunction(hashtag, contador);
                        contador++;
                    }
                } else {
                    hashMap[id] = new ListPost();
                    hashMap[id].agregarPost(post, hashtag);
                }
            }
        }
    }

    public void eliminarPost(Post postAEliminar){
        int y = 0;
        while (y < postAEliminar.getHashtags().length) {
            if (postAEliminar.getHashtags()[y] != null) {
                int id = hashFunction(postAEliminar.getHashtags()[y], 0);
                int i = 0;
                int contador = 1;
                try {
                    while (!hashMap[id].getHashtag().equals(postAEliminar.getHashtags()[y])) {
                        id = hashFunction(postAEliminar.getHashtags()[y], contador);
                        contador++;
                    }

                } catch (NullPointerException ex) {
                    System.out.println("No existe este tag ;)");
                    break;
                }

                hashMap[id].eliminarPost(postAEliminar);
                y++;

            }
        }
    }

    public void buscarPostsConHashTAG (String hashtag) {
        int id = hashFunction(hashtag,0);
        int i  = 0;
        int contador = 1;
        try {
            while (!hashMap[id].getHashtag().equals(hashtag)) {
                id = hashFunction(hashtag, contador);
                contador++;
            }
            Post posts[] = hashMap[id].printarPosts();
            for (int y = 0; y < posts.length; y++) {
                if (posts[y] != null) {
                    printPost(posts[y]);
                }
            }
        } catch (NullPointerException ex){
            System.out.println("No existe este tag ;)");
        }

    }
    //Valor añadido es por si queremos hacer un reshash de todos los posts que tenemos
    private int hashFunction(String hashtag, int valorAnadido) {
        long id = 0;
        for (int i = 0; i < hashtag.length(); i++) {
            id = id*3 + hashtag.charAt(i);
        }
        id = (id + valorAnadido)%2069;
        return (int) id;
    }
    public void pasarHashMapAJSON () {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String s = gson.toJson(this);
        try {
            FileWriter fw = new FileWriter("files/hashmap.json");
            fw.write(s);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hashMapVisualizacion () {
        int contador= 0;
        for (int j = 0; j < 2069; j++) {
            if (hashMap[j] != null) {
                Post [] posts = new Post[5];
                contador = 0;
                for (int w = 0; w < hashMap[j].getPosts().length; w++) {
                    if (hashMap[j].getPosts()[w] != null) {
                        posts[contador] = hashMap[j].getPosts()[w];
                        contador++;
                    }
                }
                if (contador != 0) {
                    QuickSortPosts quickSortPosts = new QuickSortPosts();
                    quickSortPosts.quickSort(posts, new ComparatorTimestampsPost(), 0, contador - 1);
                    System.out.println("\n\nPosición " + j + "--Hashtag:" + hashMap[j].getHashtag() + "\nPosts: *******" +
                            "********************************************************************************************");
                    int pepe = 0;
                    for (int k = 0; k < contador; k++) {
                        System.out.println((pepe+1) + ". " );
                        printPost(posts[k]);
                        pepe++;
                    }
                    for (int g= 0; g < hashMap[j].getPostsBackup().getValores().length; g++) {
                        if (hashMap[j].getPostsBackup().getValores()[g] != null) {
                            System.out.println("\n" + (pepe + 1) + ". ");
                            printPost(hashMap[j].getPostsBackup().getValores()[g]);
                            pepe++;
                        }
                    }
                }
            }
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

        System.out.println("\n");
    }

}
