package classrooms;

import java.io.*;
import java.net.*;
import java.util.*;
public class ClassroomClient {

    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Consultar salón");
        System.out.println("2. Reservar salón");
        System.out.println("3. Liberar salón");

        System.out.print("Seleccione una opción: ");
        int option = Integer.parseInt(scanner.nextLine());

        System.out.print("Ingrese el código del salón: ");
        String code = scanner.nextLine();

        String message = "";

        switch (option) {
            case 1:
                message = "CONSULTAR_SALON," + code;
                break;
            case 2:
                message = "RESERVAR_SALON," + code;
                break;
            case 3:
                message = "LIBERAR_SALON," + code;
                break;
            default:
                System.out.println("Opción inválida");
                return;
        }

        Socket socket = new Socket("127.0.0.1", 35000);

        PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        out.println(message);

        String result = in.readLine();

        System.out.println("Respuesta servidor: " + result);

        in.close();
        out.close();
        socket.close();

    }
}