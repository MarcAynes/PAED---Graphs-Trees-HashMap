package RTree;

import Model.Post;

public class Main_2_LaVenganzaDelJamon {
    public static void main(String[] args) {
        double [] location = {13.12,45.90};
        Post p = new Post(1,location);
        double [] location_2 = {-3.14,69.90};
        Post p2 = new Post (2,location_2);
        double [] location_3 = {69.90,69.90};
        Post p3 = new Post (3,location_3);
        double [] location_4 = {45.90,-169.90};
        Post p4 = new Post (4,location_4);
        double [] location_5 = {87.90,-91.90};
        Post p5 = new Post (5,location_5);
        double [] location_6 = {-12.90,1.90};
        Post p6 = new Post (6,location_6);
        double [] location_7 = {86.90,15.90};
        Post p7 = new Post (7,location_7);
        double [] location_8 = {10.12,18.90};
        Post p8 = new Post (8,location_8);
        double [] location_9 = {-10.12,18.90};
        Post p9 = new Post (9,location_9);
        double [] location_10 = {1.23,1.90};
        Post p10 = new Post (10,location_10);
        double [] location_11 = {1.25,1.90};
        Post p11 = new Post (11,location_11);
        double [] location_12 = {89.87,60.87};
        Post p12 = new Post (12,location_12);
        double [] location_13 = {-90.0,-180.0};
        Post p13 = new Post (13,location_13);
        double [] location_14 = {90.0,180.0};
        Post p14 = new Post (14,location_14);
        double [] location_15 = {45.0,120.0};
        Post p15 = new Post (15,location_15);
        double [] location_16 = {46.15,121.0};
        Post p16 = new Post (16,location_16);
        RTree rTree = new RTree(2,3);
        rTree.insertarElemento(p);
        rTree.insertarElemento(p2);
        rTree.insertarElemento(p3);
        rTree.insertarElemento(p4);
        rTree.insertarElemento(p5);
        rTree.insertarElemento(p6);
        rTree.insertarElemento(p7);
        rTree.insertarElemento(p8);
        rTree.insertarElemento(p9);
        rTree.insertarElemento(p10);
        rTree.insertarElemento(p11);
        rTree.insertarElemento(p12);
        rTree.insertarElemento(p13);
        rTree.insertarElemento(p14);
        rTree.insertarElemento(p15);
        rTree.insertarElemento(p16);

        Post[] resultado = rTree.busquedaEnRtree(87.9, 69.9, 20.0);

        rTree.visualizacionRTree();

        rTree.eliminacionEnRtree(p10);

        System.out.println("Jaja si soy llo el shocu");

    }
}
