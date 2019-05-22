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

    public DynamicMatrix (int n_usuarios) {
        int multiplicador = 1;
        int i = 1;
        while (multiplicador <= n_usuarios) {
            multiplicador =  i* 8;
            i++;
        }
        matriz = new byte [multiplicador] [i-1];
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

    public void eliminarVinculacion (int indice) {
        matriz[indice] = null;
        for(int i= 0; i < matriz.length; i++) {
            matriz[i][indice/8] = (byte) (matriz[i][indice/8] & ~(1 << (indice%8)));
        }
    }

    public Integer[] devolverVinculacionesUsuario (int idUser) {
        int hola= 0 ;
        int google = 0;
        Integer [] resultado = new Integer[2000];
        for (int u  =0; u < matriz[idUser].length; u++) {
            for (int h = 0; h < 8; h++) {
                if ((matriz[idUser][hola] & 1) == 1) {
                    resultado[google] = h + (hola*8);
                    google++;
                }
                matriz[idUser][hola] = (byte) (matriz[idUser][hola] >> 1);
            }
            hola++;
        }
        return resultado;
    }

    public byte[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(byte[][] matriz) {
        this.matriz = matriz;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
