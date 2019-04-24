package RTree;

import Model.Post;
import Utiles.Haversine;

public class Nodo {
    private byte tipo; //Para saber dentro del nodo (0 rectangulo y 1 punto)
    private Object [] valores;
    private int cantidad;

    public Nodo(int max) {
        cantidad = 0;
        valores = new Object [max];
        tipo = 1;
    }

    public Nodo(int max, byte tipo){
        cantidad = 0;
        valores = new Object [max];
        this.tipo = tipo;
    }

    public Rectangulo [] insertarPost (Post postAInsertar) {
            if (cantidad == valores.length) {
                Rectangulo [] rectangulos = splitPost(postAInsertar);
                return rectangulos;
            } else {
                valores[cantidad] = postAInsertar;
                cantidad++;
                return null;
            }
    }


    public void busquedaSiguienteRectangulo (int altura, Post postAInsertar) {
        double incrementoMinimo = Double.MAX_VALUE;
        double areaMinima = Double.MAX_VALUE;
        double areaCalculada = 0;
        double incrementoCalculado = 0;
        int indice =0;
        for (int y = 0; y < cantidad;y++){
            incrementoCalculado = ((Rectangulo) valores[y]).calcularIncremento(postAInsertar);
            if (incrementoCalculado == 0) {
                indice = y;
                break;
            }
            if (incrementoCalculado < incrementoMinimo ) {
                indice = y;
                incrementoMinimo = incrementoCalculado;
                areaMinima = ((Rectangulo) valores[y]).calculoAreaActual();
            }
            else if (incrementoCalculado == incrementoMinimo) {
                areaCalculada = ((Rectangulo) valores[y]).calculoAreaActual();
                if (areaCalculada < areaMinima) {
                    indice = y;
                    areaMinima = areaCalculada;
                }
                //Es raro que tengan la misma area, asi que si tienen la misma aleatorio y ya
            }
        }
        ((Rectangulo) valores[indice]).bajarArbol(altura-1,postAInsertar,this);
    }

    public Rectangulo [] splitPost (Post postAInsertar) {
        int max = valores.length;
        Post[] auxiliar = new Post[max + 1];
        for (int i = 0; i < max; i++) {
            auxiliar[i] = (Post) valores[i];
        }
        auxiliar[max] = postAInsertar;

        //Ahora hacemos el split, una vez hecho esto
        int aux_1 = -1;
        int aux_2 = -1;
        double distanciaMax = -1;
        double posibleDistMax = 0;
        //Se buscan los posts mas distantes
        for (int j = 0; j <= max; j++) {
            for (int w = j; w <= max; w++) {
                posibleDistMax = Haversine.calculoHaversine(auxiliar[j].getLocation()[1], auxiliar[j].getLocation()[0], auxiliar[w].getLocation()[1], auxiliar[w].getLocation()[0]);
                if (posibleDistMax > distanciaMax) {
                    aux_1 = j;
                    aux_2 = w;
                    distanciaMax = posibleDistMax;
                }
            }
        }
        tipo = 0;
        valores = new Object[max];
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
            if ((auxiliar[i] != auxiliar[aux_1]) && (auxiliar[i] != auxiliar[aux_2])) {
                calcularAux = rectangulo_1.calcularIncremento(auxiliar[i]);
                calcularAux2 = rectangulo_2.calcularIncremento(auxiliar[i]);
                //Paso 1
                if (calcularAux < calcularAux2) {
                    boolean resultado = rectangulo_1.insertarPost(auxiliar[i]);
                    if (!resultado) {
                        rectangulo_2.insertarPost(auxiliar[i]);
                    }
                } else if (calcularAux > calcularAux2) {
                    boolean resultado = rectangulo_2.insertarPost(auxiliar[i]);
                    if (!resultado) {
                        rectangulo_1.insertarPost(auxiliar[i]);
                    }
                } else {
                    calcularAux = rectangulo_1.calculoAreaActual();
                    calcularAux2 = rectangulo_2.calculoAreaActual();
                    //Paso 2
                    if (calcularAux < calcularAux2) {
                        boolean resultado = rectangulo_1.insertarPost(auxiliar[i]);
                        if (!resultado) {
                            rectangulo_2.insertarPost(auxiliar[i]);
                        }
                    } else if (calcularAux > calcularAux2) {
                        boolean resultado = rectangulo_2.insertarPost(auxiliar[i]);
                        if (!resultado) {
                            rectangulo_1.insertarPost(auxiliar[i]);
                        }
                    } else {
                        //Paso 3
                        if (rectangulo_1.devolverCantidad() < rectangulo_2.devolverCantidad()) {
                            boolean resultado = rectangulo_1.insertarPost(auxiliar[i]);
                            if (!resultado) {
                                rectangulo_2.insertarPost(auxiliar[i]);
                            }
                        } else {
                            boolean resultado = rectangulo_2.insertarPost(auxiliar[i]);
                            if (!resultado) {
                                rectangulo_1.insertarPost(auxiliar[i]);
                            }
                        }
                    }

                }
            }
        }
        Rectangulo [] arrayRectangulos = new Rectangulo[2];
        arrayRectangulos[0] =rectangulo_1;
        arrayRectangulos[1] =rectangulo_2;
        return arrayRectangulos;
    }

        public byte getTipo() {
        return tipo;
    }

    public void agregarRectangulos (Rectangulo [] rectangulos,Rectangulo rectanguloAEliminar) {
        int j;
        for (j = 0; j < valores.length; j++) {
            if (valores[j] ==  rectanguloAEliminar) {
                valores[j] = null;
                break;
            }
        }
        valores[j] = rectangulos[0];
        valores[cantidad] = rectangulos[1];
        cantidad++;
    }

    public void agregarRectanguloIndividual(Rectangulo rectangulo) {
        //TODO: ¿Hay control cuando la cantidad es igual a lenght o no?
        if (rectangulo != null) {
            valores[cantidad] = rectangulo;
            cantidad++;
        }
    }


    public double calcularAreaRectangulos () {
        if (tipo == 0) {
            double area = 0;
            double auxiliar = 0;
            Rectangulo r = new Rectangulo(0);
            r.setLongMin(Double.MAX_VALUE);
            r.setLongMax(0);
            r.setLatMin(Double.MAX_VALUE);
            r.setLatMax(0);
            for (int i = 0; i < cantidad;i++) {
                auxiliar = ((Rectangulo)valores[i]).getLongMin();
                if (auxiliar < r.getLongMin()) {
                    r.setLongMin(auxiliar);
                }
                auxiliar = ((Rectangulo)valores[i]).getLongMax();
                if (auxiliar > r.getLongMax()) {
                    r.setLongMax(auxiliar);
                }
                auxiliar = ((Rectangulo)valores[i]).getLatMin();
                if (auxiliar < r.getLatMin()) {
                    r.setLatMin(auxiliar);
                }
                auxiliar = ((Rectangulo)valores[i]).getLatMax();
                if (auxiliar > r.getLatMax()) {
                    r.setLatMax(auxiliar);
                }
            }
            return r.calculoAreaActual();
        }
        else {
            return -1;
        }
    }

    public void eliminarUltimoValor () {
        valores[cantidad-1] = null;
        cantidad--;
    }

    public void setTipo(byte tipo) {
        this.tipo = tipo;
    }

    public Object[] getValores() {
        return valores;
    }

    public void setValores(Object[] valores) {
        this.valores = valores;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
