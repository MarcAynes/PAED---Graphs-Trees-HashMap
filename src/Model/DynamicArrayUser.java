package Model;

import Graph.PosicionUser;

public class DynamicArrayUser {
    private PosicionUser[] valores;
    private int cantidad;

    public DynamicArrayUser(){
        valores = new PosicionUser[2];
        cantidad = 0;
    }

    public void insertarUsuariosJSON (User [] arrayJSONUsers) {
        for (int pepe = 0; pepe < arrayJSONUsers.length; pepe++) {
            insertarElemento(arrayJSONUsers[pepe]);
        }
    }

    public int insertarElemento (User elementoAInsertar) {
        if (cantidad == valores.length) {
            ampliarArray ();
            PosicionUser posicionUser = new PosicionUser(elementoAInsertar,cantidad);
            valores [cantidad] = posicionUser;
            cantidad++;
            return (cantidad-1);
        }
        else {
            for (int i = 0; i < valores.length;i++) {
                if (valores[i] == null) {
                    PosicionUser posicionUser = new PosicionUser(elementoAInsertar,i);
                    valores[i] = posicionUser;
                    cantidad++;
                    return i;
                }
            }
        }
        return -1;
    }

    private void ampliarArray () {
        PosicionUser valoresNew [] = new PosicionUser [2*cantidad];
        System.arraycopy(valores,0,valoresNew,0,cantidad);
        valores = valoresNew;
    }

    public int eliminarElemento (String usernameAEliminar) {
        for (int j = 0; j < valores.length;j++) {
            if (valores[j] != null && valores[j].getUsuario().getUsername().equals(usernameAEliminar)) {
                valores[j] = null;
                cantidad--;
                return j;
            }
        }
        return -1; //Usuario no encontrado!
    }

    public int [] buscarIndiceUsuariosSeguidos (String [] usernameABuscar) {
        int [] resultado = new int [usernameABuscar.length];
        int contadore = 0;
        for (int y = 0; y < valores.length; y++) {
            for (int j = 0; j < usernameABuscar.length;j++) {
                if (valores[y].getUsuario().getUsername().equals (usernameABuscar[j])) {
                    resultado[contadore] = y;
                    contadore++;
                }
                if (contadore == usernameABuscar.length) {
                    return resultado;
                }
            }
        }
        return resultado;
    }

    public User buscarUsuario (String usernameABuscar) {
        int size = valores.length;
        for (int i = 0; i < size;i++) {
            if (valores[i] != null && usernameABuscar.equals(valores[i].getUsuario().getUsername())) {
                return valores[i].getUsuario();
            }
        }
        return null;
    }


    public PosicionUser[] getValores() {
        return valores;
    }


    public void setValores(PosicionUser[] valores) {
        this.valores = valores;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
