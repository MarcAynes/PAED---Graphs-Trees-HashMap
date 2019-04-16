package Model;

import Graph.PosicionUser;

public class DynamicMatrix {
    //De filas a columnas (De izquierda a derecha)

    private byte [][] matriz;
    private int cantidad;


    //TODO: Inserción de JSON

    public DynamicMatrix() {
        matriz = new byte [8][1];
    }


    public void insertarVinculacion (int posicion, int [] personasSeguidas) {
        if (cantidad == matriz.length) {
            ampliarMatriz ();
        }
        for (int j = 0; j < matriz[posicion].length;j++) {
            matriz[posicion][j] = 0;
        }
        for (int i  = 0; i < personasSeguidas.length; i++) {
            matriz[posicion][personasSeguidas[i]/8] = (byte) (matriz[posicion][personasSeguidas[i]/8]| (1 << (personasSeguidas[i]%8)));
        }
        cantidad++;
    }


    private void ampliarMatriz() {
        byte [][] matrizNueva = new byte [2*cantidad][matriz[0].length+1];
        for(int y = 0; y < matriz.length; y++) {
            matrizNueva [y] = matriz [y];
        }
        matriz = matrizNueva;
    }



}
