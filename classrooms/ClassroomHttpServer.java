package classrooms;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class ClassroomHttpServer {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        ClassroomRepository repository = new ClassroomRepository();

        server.createContext("/rooms", new RoomHandler(repository));
        server.createContext("/rooms/reserve", new ReserveHandler(repository));
        server.createContext("/rooms/release", new ReleaseHandler(repository));
        server.setExecutor(null);
        server.start();

        System.out.println("Servidor escuchando en http://localhost:8081/rooms?id=E303");
    }

    static class RoomHandler implements HttpHandler {

        private ClassroomRepository repository;

        public RoomHandler(ClassroomRepository repository) {
            this.repository = repository;
        }

        @Override
        public void handle(HttpExchange exchange) {

            try {

                String query = exchange.getRequestURI().getQuery();
                String id = query.split("=")[1];
                Classroom classroom = repository.searchClassroom(id);
                String response;

                if (classroom == null) {
                    response = "<html><body><h1>SALON_NO_EXISTE</h1></body></html>";
                } else {
                    String status = classroom.isReserved()
                            ? "SALON_RESERVADO"
                            : "SALON_DISPONIBLE";

                    response = "<html><body><h1>" + status + "</h1></body></html>";
                }

                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class ReserveHandler implements HttpHandler {

        private ClassroomRepository repository;

        public ReserveHandler(ClassroomRepository repository) {
            this.repository = repository;
        }

        @Override
        public void handle(HttpExchange exchange) {

            try {

                String query = exchange.getRequestURI().getQuery();
                String id = query.split("=")[1];
                Classroom classroom = repository.searchClassroom(id);
                String response;

                if (classroom == null) {
                    response = "<html><body><h1>SALON_NO_EXISTE</h1></body></html>";
                } else if (classroom.isReserved()) {
                    response = "<html><body><h1>SALON_RESERVADO</h1></body></html>";
                } else {
                    classroom.reserve();
                    response = "<html><body><h1>RESERVA_EXITOSA</h1></body></html>";
                }

                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class ReleaseHandler implements HttpHandler {

        private ClassroomRepository repository;

        public ReleaseHandler(ClassroomRepository repository) {
            this.repository = repository;
        }

        @Override
        public void handle(HttpExchange exchange) {

            try {

                String query = exchange.getRequestURI().getQuery();
                String id = query.split("=")[1];
                Classroom classroom = repository.searchClassroom(id);
                String response;

                if (classroom == null) {
                    response = "<html><body><h1>SALON_NO_EXISTE</h1></body></html>";
                } else if (!classroom.isReserved()) {
                    response = "<html><body><h1>SALON_DISPONIBLE</h1></body></html>";
                } else {
                    classroom.release();
                    response = "<html><body><h1>LIBERACION_EXITOSA</h1></body></html>";
                }

                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}