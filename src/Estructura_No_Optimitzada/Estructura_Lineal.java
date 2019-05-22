package Estructura_No_Optimitzada;

import Model.Post;
import Model.User;

import java.sql.Timestamp;
import java.util.Date;

public class Estructura_Lineal {
    public Post[] posts;
    public User[] users;
    public int users_count;
    public int posts_count;

    public Estructura_Lineal (Post[] posts, User[] users) {
        this.posts = posts;
        this.users = users;
        users_count = posts_count = 0;

    }

    //Inserción
    public void insertPost(Post p) {
        if (posts.length == posts_count) {
            Post [] arrayPostsNew = new Post[posts.length*2];
            System.arraycopy(posts,0, arrayPostsNew,0,posts.length);
            posts = arrayPostsNew;
        }

        posts[posts.length] = p;
        posts_count++;
    }

    public void insertUser(User u) {
        if (users.length == users_count) {
            User[] arrayUsersNew = new User[posts.length*2];
            System.arraycopy(users,0, arrayUsersNew,0, users.length);
            users = arrayUsersNew;
        }

        users[users.length] = u;
        users_count++;
    }


    //Eliminación
    public void eliminarPost (Post post) {
        for (int i = 0; i < posts.length;i++) {
            if (posts[i] != null) {
                if (posts[i].getId() == post.getId()) {
                    //Eliminamos el post que se quiere eliminar
                    posts[i] = null;
                    //Lo substituimos por el último de el array
                    posts[i] = posts[posts.length-1];
                    //Eliminamos el último de el array, ya que lo hemos desplazado
                    posts[posts.length-1] = null;
                    break;
                }
            }
        }
    }

    //TODO: Eliminar por user (i consecuentemente todos sus posts)

    //Búsqueda
    public void searchUser(String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                printUser(user);
                break;
            }
        }
    }

    public void searchPostById(int id) {
        for (Post p : posts) {
            if (p.getId() == id) {
                printPost(p);
                break;
            }
        }
    }

    public void searchLast5PostsByHashtag(String hashtag) {
        int i_post = 0;

        for (int i = posts.length - 1; i > 0; i--) {
            if (posts[i] != null) {
                if (posts[i].getHashtags().equals(hashtag)) {
                    printPost(posts[i]);
                    i_post++;

                    if (i_post == 5) {
                        break;
                    }
                }
            }
        }
    }

    public void searchPostByRatio(double latitud, double longitud, double radio) {
        int contador = 0;
        double latitudMin = latitud - radio;
        double latitudMax = latitud + radio;
        double longitudMax = longitud + radio;
        double longitudMin = longitud - radio;

        if (latitudMin < -90) {
            latitudMin = -90;
        }
        if (latitudMax > 90) {
            latitudMax = 90;
        }
        if (longitudMin < -180) {
            longitudMin = -180;
        }
        if (longitudMax > 180) {
            longitudMax = 180;
        }

        for (int i = 0; i < posts.length;i++) {
            if (posts[i] != null) {
                double latitudPost = posts[i].getLocation()[0];
                double longitudPost = posts[i].getLocation()[1];
                if (latitudPost >= latitudMin && latitudPost <= latitudMax && longitudPost >= longitudMin && longitudPost <= longitudMax) {
                    printPost(posts[contador]);
                    contador++;
                }
            }
        }
    }


    //Visualización
    public void array_visualization() {
        for (Post p : posts) {
            printPost(p);
        }

        for (User u : users) {
            printUser(u);
        }
    }

    //Utils
    private void printPost(Post p) {
        double[] localizacion = p.getLocation();

        Timestamp stamp = new Timestamp(p.getPublished_when());
        Date date = new Date(stamp.getTime());

        System.out.print("Post id: " + p.getId() +
                "\n Publicado por: " + p.getPublished_by() +
                "\n Fecha creación: " + date +
                "\n Localización (latitud, longitud): " + localizacion[0] + ", " + localizacion[1] +
                "\n Hashtags: ");

        for (String hashtag : p.getHashtags()) {
            System.out.print(hashtag + ", ");
        }
    }

    private void printUser(User u) {
        System.out.print("Username: " + u.getUsername()
        + "\nFecha creación: " + u.getCreation()
        + "\nUsuarios que sigue: ");

        for (String fll : u.getTo_follow()) {
            System.out.print(fll + ", ");
        }
    }

}
