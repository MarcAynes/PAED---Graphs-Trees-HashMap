package Graph;

import Model.User;

public class PosicionUser {
    private User usuario;
    private int posicion;

    public PosicionUser (User user, int posicion) {
        this.usuario = user;
        this.posicion = posicion;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
}
