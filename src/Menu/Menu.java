package Menu;

import java.util.Scanner;

public class Menu {

    public Menu() {
        int opcion;

        System.out.println("Bienvenid@!");
        do {
            opcionesPosibles();
            opcion = seleccionarOpcion();
            funcionalidad(opcion);

        } while (opcion != 8);
    }

    public void opcionesPosibles() {
        System.out.println("Menu:\n" +
                "\t1. Importación de fichero\n" +
                "\t2. Exportación de ficheros\n" +
                "\t3. Visualización de una estructura\n" +
                "\t4. Inserción de información\n" +
                "\t5. Borrar información\n" +
                "\t6. Búsqueda de información\n" +
                "\t7. Limitar memória para autocompletar\n" +
                "\t8. Salir");
    }

    public int seleccionarOpcion() {
        System.out.println("Opción:");
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public void funcionalidad(int opcio) {
        switch (opcio) {
            case 1:
                System.out.println("Importación de fichero");
                break;

            case 2:
                System.out.println("Exportación de ficheros");
                break;

            case 3:
                System.out.println("Visualización de una estructura");
                break;

            case 4:
                System.out.println("Inserción de información");
                break;

            case 5:
                System.out.println("Borrar información");
                break;

            case 6:
                System.out.println("Búsqueda de información");
                break;

            case 7:
                System.out.println("Limitar memória para autocompletar");
                break;

            case 8:
                System.out.println("Hasta pronto!");
                break;

            default:
                System.out.println("Opción incorrecta!");
                 break;
        }
    }

}
