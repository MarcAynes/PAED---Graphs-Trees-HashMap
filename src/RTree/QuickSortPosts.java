package RTree;

import Model.Post;

public class QuickSortPosts {

    /**
     * Quicksort per ordenar un arraylist de posts
     * @param p arraylist de posts
     * @param c comprador que gastem en el quicksort
     * @param i posicio inicial
     * @param j posicio final
     * @return
     */
    public Post [] quickSort (Post[] p, Comparator c, int i, int j) {
        int s;
        int t;
        int array_aux_ij [] = new int [2];
        int array_aux_st [] = new int [2];

        if (i >= j) {
            return p;
        }
        else{
            array_aux_ij[0] = i;
            array_aux_ij[1] = j;
            array_aux_st = particio(p,array_aux_ij,c);
            s = array_aux_st[0];
            t = array_aux_st[1];
            p = quickSort(p,c,i,t);
            p = quickSort(p,c,s,j);
        }
        return p;
    }

    /**
     * Metode que ens ordena una petita de l'array
     * @param p arraylist que volem ordenar
     * @param array_aux_ij posicio inicial i final que estem actualment, en forma de array
     * @param c comparador que gastem en el quicksort
     * @return El nostre array amb la petita part ordenada
     */
    private int [] particio (Post [] p, int array_aux_ij[], Comparator c) {
        int mig;
        Post pivot;
        Post tmp;
        int s;
        int t;
        s = array_aux_ij[0];
        t = array_aux_ij[1];
        mig = (array_aux_ij[0] + array_aux_ij[1])/2;
        pivot = p[mig];
        while (s <= t) {
            while (c.compararp2top1(p[s],pivot)) {
                s = s + 1;
            }
            while (c.compararp1top2(p[t],pivot)) {
                t = t - 1;
            }
            if (s < t) {
                tmp = p[s];
                p[s] = p[t];
                p[t] = tmp;
                s = s + 1;
                t = t - 1;
            }
            else {
                if (s == t) {
                    s = s + 1;
                    t = t - 1;
                }
            }
        }
        int [] array_aux_st = new int[2];
        array_aux_st[0]= s;
        array_aux_st[1]=t;

        return array_aux_st;
    }
}
