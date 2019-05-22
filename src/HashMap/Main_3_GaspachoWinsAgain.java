package HashMap;

import Model.Post;

public class Main_3_GaspachoWinsAgain {
    public static void main(String[] args) {
        Post post = new Post();
        String s []= {"hashtag1", "hashtag1","holabondia"};
        post.setPublished_when(123);
        post.setHashtags(s);
        post.setId(1);
        Post post2 = new Post();
        String s_2 [] = {"holabondia","adeu","estoesunhashtag"};
        post2.setPublished_when(69);
        post2.setHashtags(s_2);
        post2.setId(2);
        Post post3 = new Post();
        String s_3 [] = {"holaadeu", "hola"};
        post3.setPublished_when(777);
        post3.setHashtags(s_3);
        post3.setId(3);
        String s_4 [] = {"rtreewinsagain", "amijitos"};
        Post post4 = new Post();
        post4.setPublished_when(12);
        post4.setHashtags(s_4);
        post4.setId(4);
        String s_5 [] = {"vinagre", "tomate","PAED"};
        Post post5 = new Post();
        post5.setHashtags(s_5);
        post5.setPublished_when(121);
        post5.setId(5);
        Post post6 = new Post();
        String s_6 [] = {"tomate", "PAED"};
        post6.setPublished_when(1);
        post6.setHashtags(s_6);
        post6.setId(6);
        Post post7 = new Post();
        post7.setPublished_when(8);
        String s_7 [] = {"estoesotrohashtag"};
        post7.setHashtags(s_7);
        post7.setId(7);

        Post post8 = new Post();
        String s_8 [] = {"tomate"};
        post8.setHashtags(s_8);
        post8.setId(8);
        post8.setPublished_when(999);
        HashMap hashMap = new HashMap();
        hashMap.agregarPost(post);
        hashMap.agregarPost(post2);
        hashMap.agregarPost(post3);
        hashMap.agregarPost(post4);
        hashMap.agregarPost(post5);
        hashMap.agregarPost(post6);
        hashMap.agregarPost(post7);
        hashMap.agregarPost(post8);
        System.out.println("Haha si soy llo el chocu");

        hashMap.buscarPostsConHashTAG("pollasEnVinagre");

        System.out.println("POSTS QUE CONTIENE EL HASHTAG BUSCADO");
        hashMap.buscarPostsConHashTAG("pollasEnVinagre");
        hashMap.pasarHashMapAJSON();
        hashMap.eliminarPost(post3);

        System.out.println("Haha si soy llo el chocu");

        hashMap.hashMapVisualizacion();
    }
}
