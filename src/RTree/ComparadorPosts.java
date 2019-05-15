package RTree;

import Model.Post;

public class ComparadorPosts implements Comparator{
    @Override
    public boolean compararp1top2(Object rec1, Object rec2) {
        if (((Post) rec1).getIncremento() > ((Post) rec2).getIncremento()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean compararp2top1(Object rec1, Object rec2) {
        if (((Post)rec2).getIncremento() > ((Post) rec1).getIncremento()) {
            return true;
        }
        return false;
    }
}
