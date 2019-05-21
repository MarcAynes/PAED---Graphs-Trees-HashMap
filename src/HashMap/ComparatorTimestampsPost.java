package HashMap;

import Model.Post;
import RTree.Comparator;

public class ComparatorTimestampsPost implements Comparator {
    @Override
    public boolean compararp2top1(Object rec1, Object rec2) {
        if (((Post)rec1).getPublished_when() > ((Post)rec2).getPublished_when()) {
           return true;
        }
        return false;
    }

    @Override
    public boolean compararp1top2(Object rec1, Object rec2) {
        if (((Post)rec1).getPublished_when() < ((Post)rec2).getPublished_when()) {
            return true;
        }
        return false;
    }
}
