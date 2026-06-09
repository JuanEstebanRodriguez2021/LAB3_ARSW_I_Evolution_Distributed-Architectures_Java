package classrooms;

import java.io.*;
import java.net.*;
import java.util.*;
public class ClassroomServer {

    public static void main(String[] args) throws Exception{

        ClassroomRepository classroomRepository = new ClassroomRepository();
        ServerSocket serverSocket = new ServerSocket(35000);
        System.out.println("Servidor de salones TCP escuchando en puerto 35000");

        while (true){
            Socket socket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String request = in.readLine();
            String response = classroomSolicitation(request, classroomRepository);
            out.println(response);

            in.close();
            out.close();
            socket.close();

        }
    }
    private static String classroomSolicitation(String request, ClassroomRepository classroomRepository){

        try {
            String[] parts = request.split(",");

            if (parts.length != 2) {
                return "ERROR_OPERACION_INVALIDA";
            }

            String operation = parts[0];
            String idClassroom = parts[1];

            Classroom classroom = classroomRepository.searchClassroom(idClassroom);

            if (classroom == null) {
                return "ERROR_SALON_NO_EXISTE";
            }

            switch (operation) {

                case "CONSULTAR_SALON":
                    return classroom.isReserved() ? "SALON_RESERVADO" : "SALON_DISPONIBLE";

                case "RESERVAR_SALON":
                    if (classroom.isReserved()) {
                        return "SALON_RESERVADO";
                    }
                    classroom.reserve();
                    return "RESERVA_EXITOSA";

                case "LIBERAR_SALON":
                    if (!classroom.isReserved()) {
                        return "SALON_DISPONIBLE";
                    }
                    classroom.release();
                    return "LIBERACION_EXITOSA";

                default:
                    return "ERROR_OPERACION_INVALIDA";
            }

        } catch (Exception e) {
            return "ERROR_OPERACION_INVALIDA";
        }
    }

}