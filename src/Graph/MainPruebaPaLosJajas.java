package Graph;

import Model.DynamicArrayUser;
import Model.User;

public class MainPruebaPaLosJajas {
    public static void main(String[] args) {
        DynamicArrayUser dynamicArrayUser = new DynamicArrayUser();
        dynamicArrayUser.insertarElemento(new User("pepe",69));
        dynamicArrayUser.insertarElemento(new User("dani",0));
        dynamicArrayUser.insertarElemento(new User("donramon",690));
        dynamicArrayUser.insertarElemento(new User("elmarc",69));
        dynamicArrayUser.insertarElemento(new User("erMergui",666));
        dynamicArrayUser.insertarElemento(new User("LosFruitis",92));
        dynamicArrayUser.insertarElemento(new User("CurroElPajaro",90));
        dynamicArrayUser.insertarElemento(new User("ElCompiDeCompus",314));
        dynamicArrayUser.insertarElemento(new User("Teletubbies",6969));
        dynamicArrayUser.eliminarElemento("CurroElPajaro");
        dynamicArrayUser.insertarElemento(new User("ElGutavo",690));
        dynamicArrayUser.insertarElemento(new User("ElPernia",699));
        System.out.println("aiuda");
    }
}
