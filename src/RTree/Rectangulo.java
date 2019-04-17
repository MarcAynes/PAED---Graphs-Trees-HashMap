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


    public void bajarArbol (int altura, Post postAInsertar) {
        //Aqui lo unico que hacemos es mirar si hemos llegado al final o no, si hemos llegado al final, significa
        //Que ya podemos insertar post, en caso contrario significara que tenemos que seguir escogiendo rectangulos
        if (altura  == 0) {
            hijo.insertarPost(postAInsertar);
        }
        else {
            hijo.busquedaSiguienteRectangulo(altura,postAInsertar);
        }
    }

}
