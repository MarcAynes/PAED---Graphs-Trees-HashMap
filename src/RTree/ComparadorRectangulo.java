package RTree;

public class ComparadorRectangulo implements Comparator{

    @Override
    public boolean compararp1top2(Object rec1, Object rec2) {
        if (((Rectangulo) rec1).getIncremento() > ((Rectangulo) rec2).getIncremento()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean compararp2top1(Object rec1, Object rec2) {
        if (((Rectangulo)rec2).getIncremento() > ((Rectangulo) rec1).getIncremento()) {
            return true;
        }
        return false;
    }

}
