package RTree;

import Model.Post;
import Utiles.Haversine;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.*;

public class RTree {
    @Expose
    public static int min;
    @Expose
    private int max;
    @Expose
    private byte tipo; //Para saber si en el principio hay puntos o rectangulos (0 rectangulo i 1 punto)
    @Expose
    private int altura;
    @Expose
    private Object [] raiz;
    @Expose
    private int cantidad; //Cantidad en la raiz
    @Expose
    private int cantidadTotal;

    public RTree (int minAux, int max) {
        tipo = 1;
        min = minAux;
        this.max = max;
        cantidad = 0;
        altura = 0;
        raiz = new Object[max];
    }

    public void insertarElemento (Post postAInsertar) {
        if (tipo == 1) {
            // Split inicial
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
            // Miramos post donde se encuentra mas cercano a los rectangulos actuales
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
            Rectangulo [] rectangulos = ((Rectangulo)raiz[indice]).bajarArbol(altura-1,postAInsertar,this);

            if (rectangulos.length != 1) {
                //Creamos los dos rectangulos que in crementaran la altura del arbol
                Nodo hijoIzquierdo = new Nodo(max,(byte) 0);
                Nodo hijoDerecho = new Nodo(max,(byte) 0);
                tipo = 0;
                Rectangulo rectanguloIzquierdo = new Rectangulo(hijoIzquierdo);
                Rectangulo rectanguloDerecho = new Rectangulo(hijoDerecho);


                Rectangulo [] unionRectangulosASplitear = new Rectangulo[max+1];
                /* Para la gente con perdida de orina:
                    Lo que hacemos aqui es coger los rectangulos nuevos que han nacido del split y añadirlos a un array auxiliar
                    Cabe destacar que el rectangulo origen del split se elimina (basicamente no se añade)
                 */
                int contador = 0;
                for (int y = 0; y < raiz.length; y++) {
                    if (y != indice) {
                        unionRectangulosASplitear[contador] = (Rectangulo) raiz[y];
                        contador++;
                    }
                }
                //Rectangulos nacidos del split
                unionRectangulosASplitear[max - 1] = rectangulos [0];
                unionRectangulosASplitear[max] = rectangulos[1];

                double areaAux;
                double areaMaxActual = -69;
                int indice1 = 0;
                int indice2 = 0;
                /*
                    Aqui simplemente lo que hacemos es buscar los dos rectangulos de area maxima para separarlos
                 */
                for (int p=0; p < (max + 1) ;p++) {
                    for(int w = p+1; w < (max+1);w++) {
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
                raiz = new Object[max];
                raiz[0] = rectanguloIzquierdo;
                raiz[1] = rectanguloDerecho;
                cantidad = 2;

                Rectangulo rectNoQuiero1 = unionRectangulosASplitear[indice1];
                Rectangulo rectNoQuiero2 = unionRectangulosASplitear[indice2];

                for (int i = 0; i < unionRectangulosASplitear.length; i++) {
                    unionRectangulosASplitear[i].setIncremento(rectNoQuiero1.calcularIncrementoConRectangulo(unionRectangulosASplitear[i]));
                }

                int contadorAux = 0;
                /*
                 * Para saber donde poner cada rectangulo donde le pertenece, haremos varias cosas:
                 *  1) Calcularemos el incremento
                 *  2) En caso que los incrementos de area sean iguales nos cogeremos el area anterior que sea mejor
                 *  3) Se añade el rectangulo alli donde se cumplan las minimas condiciones segun los dos criterios anteriores
                 */
                for (int i = 0; i < unionRectangulosASplitear.length; i++) {
                    if ((unionRectangulosASplitear[i] != rectNoQuiero1) && (unionRectangulosASplitear[i] != rectNoQuiero2)) {

                        if (unionRectangulosASplitear.length - contadorAux - 3  + hijoDerecho.getCantidad() < min) {
                            hijoDerecho.agregarRectanguloIndividual(unionRectangulosASplitear[i]);
                            rectanguloDerecho.actualizarValores(unionRectangulosASplitear[i]);
                        } else if (unionRectangulosASplitear.length - contadorAux - 3  + hijoIzquierdo.getCantidad() < min) {
                            hijoIzquierdo.agregarRectanguloIndividual(unionRectangulosASplitear[i]);
                            rectanguloIzquierdo.actualizarValores(unionRectangulosASplitear[i]);
                        } else {
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
                altura++;
            }
            else {
                ((Rectangulo)raiz[indice]).actualizarValores(rectangulos[0]);
            }

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
            for (int w = j+1; w <= max; w++) {
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


        Post postNoQuiero1 = auxiliar[aux_1];
        Post postNoQuiero2 = auxiliar[aux_2];

        for (int i = 0; i < auxiliar.length; i++) {
            auxiliar[i].setIncremento(rectangulo_1.calcularIncremento(auxiliar[i]));
        }


        PollasEnAlmibar q = new PollasEnAlmibar();
        auxiliar = q.quickSort(auxiliar,new ComparadorPosts(),0,auxiliar.length-1);



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
        int contadorAux = 0;
        for (int i = 0; i < auxiliar.length; i++) {
            //Comprobamos que no sea ninguno de los dos polos introducidos ya
            if ((auxiliar[i] != postNoQuiero1) && (auxiliar[i] != postNoQuiero2)) {
                if (auxiliar.length - contadorAux -3 + rectangulo_1.getHijo().getCantidad() < min) {
                    rectangulo_1.insertarPost(auxiliar[i]);
                } else if (auxiliar.length - contadorAux -3 + rectangulo_2.getHijo().getCantidad() < min) {
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



    //Privados

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


    public void visualizacionRTree () {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

        //convert the Java object to json
        String jsonString = gson.toJson(this);
        //Write JSON String to file
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("results/prueba.json");
            fileWriter.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }






    //Getters y setters

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

    public void eliminarPost (){

    }

    public void hacerJSONRtree () {

    }

    public Post [] busquedaEnRtree (double latitud, double longitud, double radio) {
        Post[] resultado = new Post[cantidadTotal];
        int contador = 0;
        double latitudMin = latitud - radio;
        double latitudMax = latitud + radio;
        double longitudMax = longitud + radio;
        double longitudMin = longitud - radio;

        if (latitudMin < -90) {
            latitudMin = -90;
        }
        if (latitudMax > 90) {
            latitudMax = 90;
        }
        if (longitudMin < -180) {
            longitudMin = -180;
        }
        if (longitudMax > 180) {
            longitudMax = 180;
        }

        if (tipo == 0) {
            //Si soy rectangulo

            for(Object pepe: raiz) {
                contador = ((Rectangulo) pepe).getHijo().bajarNodoBusqueda(latitudMax, longitudMax, latitudMin, longitudMin, contador, resultado);

            }

        }
        else {
            //Si soy un punto
            for(Object pincho : raiz) {
                if (pincho != null) {
                    Post post = (Post) pincho;
                    if (!post.isEliminado() && post.getLocation()[0] >= latitudMin && post.getLocation()[0] <= latitudMax && post.getLocation()[1] >= longitudMin && post.getLocation()[1] <= longitudMax) {
                        resultado[contador++] = post;
                    }
                }
            }

        }
        return resultado;
    }
}



