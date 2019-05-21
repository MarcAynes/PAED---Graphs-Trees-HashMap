package HashMap;

import Model.Post;
import RTree.PollasEnAlmibar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

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
                    System.out.println(posts[y].getId());
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
                    PollasEnAlmibar pollasEnAlmibar = new PollasEnAlmibar();
                    pollasEnAlmibar.quickSort(posts, new ComparatorTimestampsPost(), 0, contador - 1);
                    System.out.println("\n\nPosición " + j + "--Hashtag:" + hashMap[j].getHashtag() + "\nLos ultimos 5 posts:");
                    for (int k = 0; k < contador; k++) {
                        System.out.println((k + 1) + ". " + posts[k].getId());
                    }
                }
            }
        }
    }


}
