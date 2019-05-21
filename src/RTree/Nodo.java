package RTree;

import Model.Post;
import Utiles.Haversine;
import com.google.gson.annotations.Expose;



import static RTree.RTree.min;

public class Nodo {
    @Expose
    private byte tipo; //Para saber dentro del nodo (0 rectangulo y 1 punto)
    @Expose
    private Object [] valores;
    @Expose
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

    public Rectangulo [] insertarPost (Post postAInsertar, Rectangulo yoSoyTuRectangulo) {
        if (cantidad == valores.length) {
            Rectangulo [] rectangulos = splitPost(postAInsertar);
            return rectangulos;
        } else {
            valores[cantidad] = postAInsertar;
            cantidad++;
            yoSoyTuRectangulo.actualizarValoresConPost(postAInsertar);
            return null;
        }
    }


    public Rectangulo[] busquedaSiguienteRectangulo (int altura, Post postAInsertar) {
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
        Rectangulo[] rectangulosGenerados = ((Rectangulo) valores[indice]).bajarArbol(altura-1,postAInsertar,this);

        // Caso ha habido split en hijo y nodo debe de splitearse para almacenar rectangulos
        if(rectangulosGenerados.length != 1){
            //Creamos los dos rectangulos que in crementaran la altura del arbol
            Nodo hijoIzquierdo = new Nodo(valores.length, (byte) 0);
            Nodo hijoDerecho = new Nodo(valores.length, (byte) 0);
            tipo = 0;
            Rectangulo rectanguloIzquierdo = new Rectangulo(hijoIzquierdo);
            Rectangulo rectanguloDerecho = new Rectangulo(hijoDerecho);


            Rectangulo[] unionRectangulosASplitear = new Rectangulo[valores.length + 1];
                /* Para la gente con perdida de orina:
                    Lo que hacemos aqui es coger los rectangulos nuevos que han nacido del split y añadirlos a un array auxiliar
                    Cabe destacar que el rectangulo origen del split se elimina (basicamente no se añade)
                 */
            int contador = 0;
            for (int y = 0; y < valores.length; y++) {
                if (y != indice) {
                    unionRectangulosASplitear[contador] = (Rectangulo) valores[y];
                    contador++;
                }
            }
            //Rectangulos nacidos del split
            unionRectangulosASplitear[valores.length - 1] = rectangulosGenerados[0];
            unionRectangulosASplitear[valores.length] = rectangulosGenerados[1];

            double areaAux;
            double areaMaxActual = -69;
            int indice1 = 0;
            int indice2 = 0;
                /*
                    Aqui simplemente lo que hacemos es buscar los dos rectangulos de area maxima para separarlos
                 */
            for (int p = 0; p < (valores.length + 1); p++) {
                for (int w = p + 1; w < (valores.length + 1); w++) {
                    areaAux = calculoAumentoArea(unionRectangulosASplitear[p], unionRectangulosASplitear[w]);
                    if (areaMaxActual < areaAux) {
                        indice1 = p;
                        indice2 = w;
                    }
                }
            }

            //Creamos la base del split
            hijoIzquierdo.agregarRectanguloIndividual(unionRectangulosASplitear[indice1]);
            rectanguloIzquierdo.setAll(unionRectangulosASplitear[indice1]);
            hijoDerecho.agregarRectanguloIndividual(unionRectangulosASplitear[indice2]);
            rectanguloDerecho.setAll(unionRectangulosASplitear[indice2]);

            Rectangulo[] rectangulosSolucion = new Rectangulo[2];
            rectangulosSolucion[0] = rectanguloIzquierdo;
            rectangulosSolucion[1] = rectanguloDerecho;

            Rectangulo rectNoQuiero1 = unionRectangulosASplitear[indice1];
            Rectangulo rectNoQuiero2 = unionRectangulosASplitear[indice2];

            for (int i = 0; i < unionRectangulosASplitear.length; i++) {
                unionRectangulosASplitear[i].setIncremento(rectNoQuiero1.calcularIncrementoConRectangulo(unionRectangulosASplitear[i]));
            }

            QuickSort q = new QuickSort();
            unionRectangulosASplitear = q.quickSort(unionRectangulosASplitear,new ComparadorRectangulo(),0,unionRectangulosASplitear.length-1);

            int contadorAux = 0;
            /*
             * Para saber donde poner cada rectangulo donde le pertenece, haremos varias cosas:
             *  1) Calcularemos el incremento
             *  2) En caso que los incrementos de area sean iguales nos cogeremos el area anterior que sea mejor
             *  3) Se añade el rectangulo alli donde se cumplan las minimas condiciones segun los dos criterios anteriores
             */
            for (int i = 0; i < unionRectangulosASplitear.length; i++) {
                if ((unionRectangulosASplitear[i] != rectNoQuiero1) && (unionRectangulosASplitear[i] != rectNoQuiero2)) {
                    if (unionRectangulosASplitear.length - contadorAux -3 + hijoDerecho.getCantidad() < min) {
                        hijoDerecho.agregarRectanguloIndividual(unionRectangulosASplitear[i]);
                        rectanguloDerecho.actualizarValores(unionRectangulosASplitear[i]);
                    } else if (unionRectangulosASplitear.length - contadorAux -3 + hijoIzquierdo.getCantidad() < min) {
                        hijoIzquierdo.agregarRectanguloIndividual(unionRectangulosASplitear[i]);
                        rectanguloIzquierdo.actualizarValores(unionRectangulosASplitear[i]);
                    }
                    else {

                        double areaIzqSinConjunto = rectanguloIzquierdo.calculoAreaActual();
                        double areaDerSinConjunto = rectanguloDerecho.calculoAreaActual();
                        double incrementoIzq = rectanguloIzquierdo.calcularIncrementoConRectangulo(unionRectangulosASplitear[i]);
                        double incrementoDer = rectanguloDerecho.calcularIncrementoConRectangulo(unionRectangulosASplitear[i]);
                        double areaConjuntaIzq = incrementoIzq - areaIzqSinConjunto;
                        double areaConjuntoDer = incrementoDer - areaDerSinConjunto;

                        boolean procesoCorrecto;
                        if (areaConjuntaIzq < areaConjuntoDer) {
                            procesoCorrecto = hijoIzquierdo.agregarRectanguloIndividual(unionRectangulosASplitear[i]);
                            if (!procesoCorrecto) {
                                hijoDerecho.agregarRectanguloIndividual(unionRectangulosASplitear[i]);
                                rectanguloDerecho.actualizarValores(unionRectangulosASplitear[i]);
                            } else {
                                rectanguloIzquierdo.actualizarValores(unionRectangulosASplitear[i]);
                            }
                        } else {
                            if (areaConjuntaIzq > areaConjuntoDer) {
                                procesoCorrecto = hijoDerecho.agregarRectanguloIndividual(unionRectangulosASplitear[i]);
                                if (!procesoCorrecto) {
                                    hijoIzquierdo.agregarRectanguloIndividual(unionRectangulosASplitear[i]);
                                    rectanguloIzquierdo.actualizarValores(unionRectangulosASplitear[i]);
                                } else {
                                    rectanguloDerecho.actualizarValores(unionRectangulosASplitear[i]);
                                }
                            } else {
                                if (areaIzqSinConjunto < areaDerSinConjunto) {
                                    procesoCorrecto = hijoIzquierdo.agregarRectanguloIndividual(unionRectangulosASplitear[i]);
                                    if (!procesoCorrecto) {
                                        hijoDerecho.agregarRectanguloIndividual(unionRectangulosASplitear[i]);
                                        rectanguloDerecho.actualizarValores(unionRectangulosASplitear[i]);
                                    } else {
                                        rectanguloIzquierdo.actualizarValores(unionRectangulosASplitear[i]);
                                    }
                                } else {
                                    if (areaIzqSinConjunto > areaDerSinConjunto) {
                                        procesoCorrecto = hijoDerecho.agregarRectanguloIndividual(unionRectangulosASplitear[i]);
                                        if (!procesoCorrecto) {
                                            hijoIzquierdo.agregarRectanguloIndividual(unionRectangulosASplitear[i]);
                                            rectanguloIzquierdo.actualizarValores(unionRectangulosASplitear[i]);
                                        } else {
                                            rectanguloDerecho.actualizarValores(unionRectangulosASplitear[i]);
                                        }
                                    } else {
                                        if (hijoIzquierdo.getCantidad() <= hijoDerecho.getCantidad()) {
                                            hijoIzquierdo.agregarRectanguloIndividual(unionRectangulosASplitear[i]);
                                            rectanguloIzquierdo.actualizarValores(unionRectangulosASplitear[i]);
                                        } else {
                                            hijoDerecho.agregarRectanguloIndividual(unionRectangulosASplitear[i]);
                                            rectanguloDerecho.actualizarValores(unionRectangulosASplitear[i]);

                                        }

                                    }
                                }
                            }
                        }
                    }
                    contadorAux++;
                }
            }

            return rectangulosSolucion;
        }
        else {
            return rectangulosGenerados;
        }

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


        Post postNoQuiero1 = auxiliar[aux_1];
        Post postNoQuiero2 = auxiliar[aux_2];

        for (int i = 0; i < auxiliar.length; i++) {
            auxiliar[i].setIncremento(rectangulo_1.calcularIncremento(auxiliar[i]));
        }

        QuickSortPosts q = new QuickSortPosts();
        auxiliar = q.quickSort(auxiliar,new ComparadorPosts(),0,auxiliar.length-1);


        double calcularAux;
        double calcularAux2;


        int contadorAux = 0;

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
            if ((auxiliar[i] != postNoQuiero1) && (auxiliar[i] != postNoQuiero2)) {
                if (auxiliar.length - contadorAux -3  + rectangulo_1.getHijo().getCantidad() < min) {
                    rectangulo_1.insertarPost(auxiliar[i]);
                } else if (auxiliar.length - contadorAux -3  + rectangulo_2.getHijo().getCantidad() < min) {
                    rectangulo_2.insertarPost(auxiliar[i]);

                } else {
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
                contadorAux++;
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

    public boolean agregarRectanguloIndividual(Rectangulo rectangulo) {
        // ¿Hay control cuando la cantidad es igual a lenght o no?
        if (rectangulo != null) {
            if(cantidad != valores.length){
                valores[cantidad] = rectangulo;
                cantidad++;
                return true;
            }
        }
        return false;
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

    public double calculoAumentoArea (Rectangulo r1, Rectangulo r2) {
        Rectangulo rectanguloAuxiliar = new Rectangulo(0);

        rectanguloAuxiliar.setAll(r1);
        if (r1.getLongMin() > r2.getLongMin()) {
            rectanguloAuxiliar.setLongMin(r2.getLongMin());
        }
        if (r1.getLongMax() < r2.getLongMax()) {
            rectanguloAuxiliar.setLongMax(r2.getLongMax());
        }
        if (r1.getLatMin() > r2.getLatMin()) {
            rectanguloAuxiliar.setLatMin(r2.getLatMin());
        }
        if (r1.getLatMax() < r2.getLatMax()) {
            rectanguloAuxiliar.setLatMax(r2.getLatMax());
        }

        return rectanguloAuxiliar.calculoAreaActual();
    }


    public int bajarNodoBusqueda (double latitudMax, double longitudMax, double latitudMin, double longitudMin, int contador, Post[] arrayResultado) {
        if (tipo == 0) {
            int i = 0;

            while (i < valores.length) {
                if (valores[i] != null) {
                    Rectangulo r = (Rectangulo) valores[i];
                    if (r.getLongMin() <= longitudMax && r.getLongMax() >= longitudMin && r.getLatMin() <= latitudMax && r.getLatMax() >= latitudMin) {
                        //Seguimos bajando payo
                        int nuevoContador = ((Rectangulo) valores[i]).getHijo().bajarNodoBusqueda(latitudMax, longitudMax, latitudMin, longitudMin, contador, arrayResultado);
                        contador = nuevoContador;
                    }
                }
                i++;
            }

        }
        else {
            for(Object mochilo: valores) {
                if (mochilo != null) {
                    Post post = (Post) mochilo;
                    if (!post.isEliminado() && post.getLocation()[0] >= latitudMin && post.getLocation()[0] <= latitudMax && post.getLocation()[1] >= longitudMin && post.getLocation()[1] <= longitudMax) {
                        arrayResultado[contador++] = post;
                    }
                }
            }
        }
        return contador;
    }

    public boolean bajarNodoEliminacion (Post post) {
        if (tipo == 0) {
            int i = 0;
            boolean eliminado = false;
            double lat = post.getLocation()[0];
            double lon = post.getLocation()[1];
            while (i < valores.length) {
                if (valores[i] != null) {
                    if (((Rectangulo) valores [i]).getLongMin() <= lon && ((Rectangulo) valores[i]).getLongMax() >= lon
                            && ((Rectangulo) valores[i]).getLatMin() <= lat && ((Rectangulo) valores[i]).getLatMax() >= lat){
                        eliminado = ((Rectangulo) valores[i]).getHijo().bajarNodoEliminacion(post);
                        if (eliminado) {
                            int contador= 0;
                            int contador_2=0;
                            if (((Rectangulo) valores [i]).getHijo().tipo == 0) {
                                for (int j = 0; j < ((Rectangulo) valores[i]).getHijo().valores.length; j++) {
                                    if (((Rectangulo) valores[i]).getHijo().valores[j] != null) {
                                        Rectangulo rec1 = (Rectangulo) valores[j];
                                        if (rec1.isEliminado()) {
                                            contador++;
                                        }
                                        contador_2++;
                                    }
                                }
                                if (contador == contador_2) {
                                    ((Rectangulo)valores[i]).setEliminado(true);
                                }
                            }
                            else {
                                for (int j = 0; j < ((Rectangulo) valores[i]).getHijo().valores.length;j++) {
                                    if (((Rectangulo) valores[i]).getHijo().valores[j] != null) {
                                        Post post1 = ((Post) ((Rectangulo) valores[i]).getHijo().valores[j]);
                                        if (post1.isEliminado()) {
                                            contador++;
                                        }
                                        contador_2++;
                                    }
                                }
                                if (contador == contador_2) {
                                    ((Rectangulo)valores[i]).setEliminado(true);
                                }
                            }
                            break;
                        }
                    }
                }
                i++;
            }
            return eliminado;

        }
        else {
            for(Object mochilo: valores) {
                if (mochilo != null) {
                    Post p = (Post) mochilo;
                    if (p.getId() == post.getId()) {
                        p.setEliminado(true);
                        return true;
                    }
                }
            }
            return false;
        }
    }
    public void visualzacionRTreeTerminal (int altura) {
        if (tipo ==  0) {
            Rectangulo rectangulo;
            int i = 0;
            for (Object obj: valores) {
                if (obj != null) {
                    if (!((Rectangulo) obj).isEliminado()) {
                        for (int y = 0; y < altura; y++) {
                            System.out.print("\t");
                        }
                        rectangulo = (Rectangulo) obj;
                        System.out.println("[Rectangulo " + i + "] --> Altura:" + altura);
                        rectangulo.getHijo().visualzacionRTreeTerminal(altura + 1);
                        i++;
                    }
                }

            }
        }
        else {
            int i = 0;
            Post post;
            System.out.print("\n");
            for (Object obj: valores) {
                if (obj != null) {
                    post = (Post) obj;
                    if (!post.isEliminado()) {
                        for (int y = 0; y < altura; y++) {
                            System.out.print("\t");
                        }
                        System.out.println("[Post " + i + "] ---> Altura:" + altura);
                        for (int y = 0; y < altura; y++) {
                            System.out.print("\t");
                        }
                        System.out.println("Info del post: ID:" + post.getId());
                        i++;
                    }
                }
            }
            System.out.print("\n");

        }
    }


}