package HashMap;

import Model.Post;

public class Main_3_GaspachoWinsAgain {
    public static void main(String[] args) {
        Post post = new Post();
        String s []= {"pollasEnVinagre", "pollasEnAlmibar","pollasConTomate"};
        post.setHashtags(s);
        post.setId(1);
        Post post2 = new Post();
        String s_2 [] = {"pollasEnVinagre","cocinadePollas","pollasConAlmibarIncluido"};
        post2.setHashtags(s_2);
        post2.setId(2);
        Post post3 = new Post();
        String s_3 [] = {"donramon", "donramonyperchita"};
        post3.setHashtags(s_3);
        post3.setId(3);
        String s_4 [] = {"PEPEGUINSAJAIN", "AMIJOS"};
        Post post4 = new Post();
        post4.setHashtags(s_4);
        post4.setId(4);
        String s_5 [] = {"pinjiunos", "sonpinjuinos","sonomokop"};
        Post post5 = new Post();
        post5.setHashtags(s_5);
        post5.setId(5);
        Post post6 = new Post();
        String s_6 [] = {"pokochu", "pokomonos"};
        post6.setHashtags(s_6);
        post6.setId(6);
        Post post7 = new Post();
        String s_7 [] = {"tortillas","pollasEnVinagre"};
        post7.setHashtags(s_7);
        post7.setId(7);
        HashMap hashMap = new HashMap();
        hashMap.agregarPost(post);
        hashMap.agregarPost(post2);
        hashMap.agregarPost(post3);
        hashMap.agregarPost(post4);
        hashMap.agregarPost(post5);
        hashMap.agregarPost(post6);
        hashMap.agregarPost(post7);
        System.out.println("Haha si soy llo el chocu");

        System.out.println("POSTS QUE CONTIENE EL HASHTAG BUSCADO");
        hashMap.buscarPostsConHashTAG("pollasEnVinagre");
        hashMap.pasarHashMapAJSON();
    }
}
