package HashMap;

import Model.Post;

public class HashMap {
    private ListPost [] hashMap;

    public HashMap() {
        this.hashMap = new ListPost[2069];
    }
    public void agregarPost (Post post) {
        for(String hashtag : post.getHashtags()) {
            int id = hashFunction(hashtag, 0);
            int contador = 0;
            while (!hashMap[id].agregarPost(post,hashtag)) {
                id = hashFunction(hashtag,contador);
            }
        }
    }

    //Valor añadido es por si queremos hacer un reshash de todos los posts que tenemos
    private int hashFunction(String hashtag, int valorAnadido) {
        long id = 0;
        for (int i = 0; i < hashtag.length(); i++) {
            id = id + (int)Math.pow(31,hashtag.length()-1-i)* hashtag.charAt(i);
        }
        id = (id + valorAnadido)%2069;
        return (int) id;
    }
}
