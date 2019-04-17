package RTree;

import Model.Post;

public class Rectangulo {
    private Nodo hijo;
    private double latMax;
    private double latMin;
    private double longMax;
    private double longMin;

    public Rectangulo(int max) {
        this.latMax = -91;
        this.latMin = 91;
        this.longMax = -181;
        this.longMin = 181;
        hijo = new Nodo (max);
    }


    public boolean insertarPost (Post postAInsertar) {
        if (hijo.getCantidad() == hijo.getValores().length) {
            return false;
        }
        else {
            double lat = postAInsertar.getLocation()[0];
            double lon = postAInsertar.getLocation()[1];

            if (lat > latMax) {
                latMax = lat;
            }
            if (lat < latMin) {
                latMin = lat;
            }
            if (lon > longMax) {
                longMax = lon;
            }
            if (lon < longMin) {
                longMin = lon;
            }

            hijo.insertarPost(postAInsertar);
            return true;
        }
    }



    public double calcularIncremento (Post post) {
        double lat = post.getLocation()[0];
        double lon = post.getLocation()[1];

        double latMaxAux = latMax;
        double latMinAux = latMin;
        double longMaxAux = longMax;
        double longMinAux = longMin;


        if (lat > latMaxAux ) {
            latMaxAux = lat;
        }
        if(lat < latMinAux) {
            latMinAux = lat;
        }
        if (lon > longMaxAux) {
            longMaxAux = lon;
        }
        if (lon < longMinAux) {
            longMinAux = lon;
        }

        return (((latMaxAux-latMinAux) * (longMaxAux - longMinAux)) - ((latMax-latMin) * (longMax - longMin)));
    }

    public double calculoAreaActual () {
        return (latMax-latMin) * (longMax - longMin);
    }

    public int devolverCantidad () {
        return hijo.getCantidad();
    }


    public void bajarArbol (int altura, Post postAInsertar, Object objectPadre) {
        //Aqui lo unico que hacemos es mirar si hemos llegado al final o no, si hemos llegado al final, significa
        //Que ya podemos insertar post, en caso contrario significara que tenemos que seguir escogiendo rectangulos
        if (altura  == 0) {
            Rectangulo [] rectangulos = hijo.insertarPost(postAInsertar);
            //Si hay split de puntos que genera rectangulos, entonces se devolveran los dois nuevos rectangulos(si no null)
            if (rectangulos != null ) {
                if (objectPadre instanceof Nodo) {
                    if (((Nodo)objectPadre).getCantidad() == ((Nodo)objectPadre).getValores().length) {
                        //TODO: INSERCION CHUNGA, PELIGRO NO APTO PARA CARDIACOS
                    } else {
                        ((Nodo)objectPadre).agregarRectangulos(rectangulos,this);
                    }
                }
                else {
                    if (((RTree)objectPadre).getCantidad() == ((RTree)objectPadre).getMax()) {
                        //TODO: Insercion chunga, no apta para multimedias
                    }
                    else {
                        ((RTree)objectPadre).agregarRectangulos(rectangulos,this);
                    }
                }
            }
        }
        else {
            hijo.busquedaSiguienteRectangulo(altura,postAInsertar);
        }
    }

}
