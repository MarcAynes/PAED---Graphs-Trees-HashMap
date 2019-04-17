package RTree;

import Model.Post;
import Utiles.Haversine;

public class RTree {
    private int min;
    private int max;
    private byte tipo; //Para saber si en el principio hay puntos o rectangulos (0 rectangulo i 1 punto)
    private int altura;
    private Object [] raiz;
    private int cantidad; //Cantidad en la raiz
    private int cantidadTotal;

    public void RTree (int min, int max) {
        tipo = 1;
        this.min = min;
        this.max = max;
        cantidad = 0;
        altura = 0;
        raiz = new Object[max];
    }

    public void insertarElemento (Post postAInsertar) {
        if (tipo == 1) {
            if (cantidad == max) {
                splitPost (postAInsertar);
                altura++;
                cantidad = 2; //SIEMPRE!!!!!! (Se quedan dos rectangulos siempre)
            }
            else {
                raiz[cantidad] = postAInsertar;
                cantidad++;
            }
        }
        else {
            double incrementoMinimo = Double.MAX_VALUE;
            double areaMinima = Double.MAX_VALUE;
            double areaCalculada = 0;
            double incrementoCalculado = 0;
            int indice =0;
            for (int y = 0; y < cantidad;y++){
                incrementoCalculado = ((Rectangulo) raiz[y]).calcularIncremento(postAInsertar);
                if (incrementoCalculado == 0) {
                    indice = y;
                    break;
                }
                if (incrementoCalculado < incrementoMinimo ) {
                    indice = y;
                    incrementoMinimo = incrementoCalculado;
                    areaMinima = ((Rectangulo) raiz[y]).calculoAreaActual();
                }
                else if (incrementoCalculado == incrementoMinimo) {
                    areaCalculada = ((Rectangulo) raiz[y]).calculoAreaActual();
                    if (areaCalculada < areaMinima) {
                        indice = y;
                        areaMinima = areaCalculada;
                    }
                    //Es raro que tengan la misma area, asi que si tienen la misma aleatorio y ya
                }
            }
            ((Rectangulo)raiz[indice]).bajarArbol(altura-1,postAInsertar,this);
        }
        cantidadTotal++;
    }

    public void splitPost (Post postAInsertar) {
        Post [] auxiliar = new Post[max+1];
        for (int i = 0; i < max; i++) {
            auxiliar[i] = (Post) raiz[i];
        }
        auxiliar[max] = postAInsertar;

        //Ahora hacemos el split, una vez hecho esto
        int aux_1 = -1;
        int aux_2 = -1;
        double distanciaMax = -1;
        double posibleDistMax = 0;
        //Se buscan los posts mas distantes
        for (int j = 0; j <= max;j++) {
            for (int w = j; w <= max; w++) {
                posibleDistMax = Haversine.calculoHaversine(auxiliar[j].getLocation()[1],auxiliar[j].getLocation()[0],auxiliar[w].getLocation()[1],auxiliar[w].getLocation()[0]);
                if (posibleDistMax > distanciaMax) {
                    aux_1 = j;
                    aux_2 = w;
                    distanciaMax = posibleDistMax;
                }
            }
        }
        tipo = 0;
        raiz = new Object[max];
        Rectangulo rectangulo_1 = new Rectangulo(max);
        Rectangulo rectangulo_2 = new Rectangulo(max);

        rectangulo_1.insertarPost(auxiliar[aux_1]);
        rectangulo_2.insertarPost(auxiliar[aux_2]);


        double calcularAux;
        double calcularAux2;

        /*Para los lectores retrasados del futuro:
            Paso 1: Insertar segun incremento de area (basicamente lo que se hace es comparar cual de las dos areas incrementa menos
                ,donde se incremente menos ahi irá el post). (*)
            Paso 2: Si los dos incrementos de las areas son iguales, se mira cuál de las dos areas es menor en este momento y una vez hecho
                esto, se inserta donde sea menor.(*)
            Paso 3: En caso de que las dos areas sean iguales, se pasará a mirar la cantidad, donde haya menos post alli se insertará el
                post.(*)

            * Cabe destacar que en caso que donde se ponga en un rectangulo que ya esta lleno, se intentará la inserción en el otro.
        */

        for (int i = 0; i < auxiliar.length; i++) {
            //Comprobamos que no sea ninguno de los dos polos introducidos ya
            if ((auxiliar[i] != auxiliar[aux_1])  && (auxiliar[i] != auxiliar[aux_2])) {
                calcularAux = rectangulo_1.calcularIncremento(auxiliar[i]);
                calcularAux2 = rectangulo_2.calcularIncremento(auxiliar[i]);
                //Paso 1
                if ( calcularAux < calcularAux2) {
                    boolean resultado = rectangulo_1.insertarPost(auxiliar[i]);
                    if (!resultado) {
                        rectangulo_2.insertarPost(auxiliar[i]);
                    }
                }
                else if (calcularAux > calcularAux2) {
                    boolean resultado = rectangulo_2.insertarPost(auxiliar[i]);
                    if (!resultado) {
                        rectangulo_1.insertarPost(auxiliar[i]);
                    }
                }
                else {
                    calcularAux = rectangulo_1.calculoAreaActual();
                    calcularAux2 = rectangulo_2.calculoAreaActual();
                    //Paso 2
                    if ( calcularAux < calcularAux2) {
                        boolean resultado = rectangulo_1.insertarPost(auxiliar[i]);
                        if (!resultado) {
                            rectangulo_2.insertarPost(auxiliar[i]);
                        }
                    }
                    else if (calcularAux > calcularAux2) {
                        boolean resultado = rectangulo_2.insertarPost(auxiliar[i]);
                        if (!resultado) {
                            rectangulo_1.insertarPost(auxiliar[i]);
                        }
                    }
                    else {
                        //Paso 3
                        if (rectangulo_1.devolverCantidad() < rectangulo_2.devolverCantidad()) {
                            boolean resultado = rectangulo_1.insertarPost(auxiliar[i]);
                            if (!resultado) {
                                rectangulo_2.insertarPost(auxiliar[i]);
                            }
                        }
                        else {
                            boolean resultado = rectangulo_2.insertarPost(auxiliar[i]);
                            if (!resultado) {
                                rectangulo_1.insertarPost(auxiliar[i]);
                            }
                        }
                    }

                }
            }
        }
        raiz [0] = rectangulo_1;
        raiz[1] = rectangulo_2;

    }

    public void agregarRectangulos (Rectangulo [] rectangulos,Rectangulo rectanguloAEliminar) {
        int j;
        for (j = 0; j < max; j++) {
            if (raiz[j] ==  rectanguloAEliminar) {
                raiz[j] = null;
                break;
            }
        }
        raiz[j] = rectangulos[0];
        raiz[cantidad] = rectangulos[1];
        cantidad++;
    }



    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public byte getTipo() {
        return tipo;
    }

    public void setTipo(byte tipo) {
        this.tipo = tipo;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public Object[] getRaiz() {
        return raiz;
    }

    public void setRaiz(Object[] raiz) {
        this.raiz = raiz;
    }

    public int getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(int cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }
}



