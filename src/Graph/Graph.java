package Graph;

import Model.DynamicArrayUser;
import Model.DynamicMatrix;
import Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

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
        if (user.getTo_follow() != null) {
            personasSeguidas = users.buscarIndiceUsuariosSeguidos(user.getTo_follow());
            vinculaciones.insertarVinculacion(indice, personasSeguidas);
        }

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
                //Usuario a mostrar
                printUser(users.getValores()[i].getUsuario());
                contador = 0;
                for (int j = 0; j < vinculaciones.getMatriz()[i].length; j++) {
                    byte b = vinculaciones.getMatriz()[i][j];
                    //Usuarios a los que sigue
                    for (byte w = 0; w < 8; w++) {
                        byte aux = (byte) (b & 0x01);
                        if (aux == 1) {
                            System.out.print(" " + users.getValores()[contador].getUsuario().getUsername() + ", ");
                        }
                        contador++;
                        b = (byte) (b >> 1);
                    }

                }
                System.out.println("\n");
            }
        }
    }

    private void printUser(User u) {
        System.out.print("Username: " + u.getUsername()
                + "\nFecha creación: " + u.getCreation()
                + "\nUsuarios que sigue: ");
    }

    // Va en orden 0 a 8 para CADA BYTE
    //japapappaa
    public void guardarEnJSON () {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String s = gson.toJson(this);
        try {
            FileWriter fw = new FileWriter("files/graph.json");
            fw.write(s);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
