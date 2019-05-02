/**
 * Interficie comparator
 * @autor Gerard Melgares Martinez  i Josep Roig Torres
 * @version 1.0.0
 */
package RTree;


public interface Comparator {
    public boolean compararp1top2(Rectangulo rec1, Rectangulo rec2);
    public boolean compararp2top1(Rectangulo rec1, Rectangulo rec2);
    public boolean compararp2top1IncludeEqual(Rectangulo rec1, Rectangulo rec2);
    public long retornarValor(Rectangulo post);
}
