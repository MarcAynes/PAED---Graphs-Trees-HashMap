package Graph;

import Model.DynamicArrayUser;
import Model.DynamicMatrix;
import Model.User;

public class Graph {
    private DynamicArrayUser users;
    private DynamicMatrix vinculaciones;

    public Graph() {
        users = new DynamicArrayUser();
        vinculaciones = new DynamicMatrix();
    }

    public void insertarUsuario (User user) {
        int indice = users.insertarElemento(user);
        int [] personasSeguidas;
        personasSeguidas = users.buscarIndiceUsuariosSeguidos(user.getTo_follow());
        vinculaciones.insertarVinculacion(indice,personasSeguidas);
    }

    public User buscarUsuario (String usernameABuscar) {
        //Devuelve null en caso de error
        return users.buscarUsuario(usernameABuscar);
    }

    public void eliminarUsuario (String userAEliminar) {
        int indice = users.eliminarElemento(userAEliminar);
        vinculaciones.eliminarVinculacion (indice);
    }

    public void insertarJSON (User [] usersJSON) {
        users.insertarUsuariosJSON(usersJSON);
        vinculaciones = new DynamicMatrix(usersJSON.length); //Porque si no pasa el problema que teniamos de no tener preparada la matriz
        for (int i = 0; i < usersJSON.length;i++) {
            int[] personasSeguidas;
            personasSeguidas = users.buscarIndiceUsuariosSeguidos(usersJSON[i].getTo_follow());
            vinculaciones.insertarVinculacion(i, personasSeguidas);
        }
    }

    public void visualizacionGraph () {
        int contador = 0;
        for (int i = 0; i < users.getValores().length; i++) {
            if (users.getValores()[i] != null) {
                System.out.println("\nUser: " + users.getValores()[i].getUsuario().getUsername());
                contador = 0;
                for (int j = 0; j < vinculaciones.getMatriz()[i].length; j++) {
                    byte b = vinculaciones.getMatriz()[i][j];
                    for (byte w = 0; w < 8; w++) {
                        byte aux = (byte) (b & 0x01);
                        if (aux == 1) {
                            System.out.println("\t  " + users.getValores()[contador].getUsuario().getUsername());
                        }
                        contador++;
                        b = (byte) (b >> 1);
                    }

                }
            }
        }
    }

}
