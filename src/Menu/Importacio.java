package Menu;

import Model.Post;
import Model.User;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Importacio {
    public Post[] post;
    public User[] user;

    public Importacio() {
        Gson gson = new Gson();
        System.out.println("Vols importar la informació des dels datasets dins del projecte?");
        System.out.println("'Y' for yes, 'N' for no");
        Scanner scPath = new Scanner(System.in);

        try {
            if (scPath.next().equals("Y")) {
                System.out.println("1. Datasets Large");
                System.out.println("2. Datasets Medium");
                System.out.println("3. Datasets Small");

                switch (scPath.nextInt()) {
                    case 1:
                        post = gson.fromJson(new FileReader("datasets/large/posts.json"), Post[].class);
                        user = gson.fromJson(new FileReader("datasets/large/users.json"), User[].class);
                        break;
                    case 2:
                        post = gson.fromJson(new FileReader("datasets/medium/posts.json"), Post[].class);
                        user = gson.fromJson(new FileReader("datasets/medium/users.json"), User[].class);
                        break;
                    case 3:
                        post = gson.fromJson(new FileReader("datasets/small/posts.json"), Post[].class);
                        user = gson.fromJson(new FileReader("datasets/small/users.json"), User[].class);
                        break;
                }


            } else {
                System.out.println("Introduce la ruta del fichero de Posts:");
                post = gson.fromJson(new FileReader(scPath.next()), Post[].class);
                System.out.println("Introduce la ruta del fichero de Users:");
                user = gson.fromJson(new FileReader(scPath.next()), User[].class);


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //TODO: Inserir a totes les estructures

    public Post[] getPost() {
        return post;
    }

    public void setPost(Post[] post) {
        this.post = post;
    }

    public User[] getUser() {
        return user;
    }

    public void setUser(User[] user) {
        this.user = user;
    }
}
