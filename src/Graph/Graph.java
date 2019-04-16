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
        personasSeguidas = users.buscarIndiceUsuariosSeguidos((String[]) user.getTo_follow().toArray());
        vinculaciones.insertarVinculacion(indice,personasSeguidas);
    }

    public User buscarUsuario (String usernameABuscar) {
        //Devuelve null en caso de error
        return users.buscarUsuario(usernameABuscar);
    }

}
