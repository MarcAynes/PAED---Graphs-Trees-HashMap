package Graph;

import Model.DynamicArrayUser;
import Model.User;

import java.util.ArrayList;
import java.util.List;

public class MainPruebaPaLosJajas {
    public static void main(String[] args) {
        User [] users = new User[10];
        String [] strings_0 =  {"ErMergui"};
        users[0] = new User ("ErPepe",10, strings_0);
        String [] strings_1 =  {"ErPepe"};
        users[1] = new User ("ErMergui",69,strings_1);
        String [] strings_2 =  {"El de las 3000","ErPepe"};
        users[2] = new User ("Dani",100,strings_2);
        String [] strings_3 =  {"El paio cura","ErMergui"};
        users[3] = new User ("El compi de compus imaginario", 12,strings_3);
        String [] strings_4 =  {"Willy Wonka y la fabrica de ANDs","ErMergui"};
        users[4] = new User ("La rana gustavo, version pirata", 89,strings_4);
        String [] strings_5 =  {"Willy Wonka y la fabrica de ANDs","Sinmerk, el robot del futuro"};
        users[5] = new User ("El de las 3000",99,strings_5);
        String [] strings_6 =  {"El paio cura","Sinmerk, el robot del futuro"};
        users[6] = new User ("Willy Wonka y la fabrica de ANDs", 87,strings_6);
        String [] strings_7 =  {"El paio cura","Gomaespuma"};
        users[7] = new User ("Gomaespuma", 77,strings_7);
        String [] strings_8 =  {"El de las 3000","ErPepe","Sinmerk, el robot del futuro"};
        users[8] = new User ("Sinmerk, el robot del futuro",56,strings_8);
        String [] strings_9 =  {"Dani","El compi de compus imaginario","Sinmerk, el robot del futuro","El de las 3000"};
        users[9] = new User("El paio cura", 44,strings_9);

        Graph graph = new Graph();
        graph.insertarJSON(users);
        //graph.hacerJSONUsuarios();
        graph.visualizacionGraph();


    }
}
