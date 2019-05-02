package RTree;

public class ComparadorRectangulo implements Comparator{

    @Override
    public boolean compararp1top2(Rectangulo rec1, Rectangulo rec2) {
        if (rec1.getIncremento() > rec2.getIncremento()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean compararp2top1(Rectangulo rec1, Rectangulo rec2) {
        if (rec2.getIncremento() > rec1.getIncremento()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean compararp2top1IncludeEqual(Rectangulo rec1, Rectangulo rec2) {
        return false;
    }

    @Override
    public long retornarValor(Rectangulo post) {
        return 0;
    }
}
