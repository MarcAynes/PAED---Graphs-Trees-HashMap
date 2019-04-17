package Utiles;

public class Haversine {
    //Calculo de distancias
    public static double calculoHaversine (double longitudRef, double latitudRef, double longitud_2, double latitud_2) {
        double dlatitud = latitudRef-latitud_2;
        double dlongitud = longitudRef-longitud_2;
        return ((2 * 6371* Math.asin(Math.sqrt(Math.pow(Math.sin(Math.toRadians(dlatitud/2)),(double)2)+Math.cos(Math.toRadians(latitud_2))*Math.cos(Math.toRadians(latitudRef))*Math.pow(Math.sin(Math.toRadians(dlongitud/2)),2)))));
    }
}
