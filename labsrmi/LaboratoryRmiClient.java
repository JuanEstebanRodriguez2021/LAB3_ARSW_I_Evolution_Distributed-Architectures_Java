package labsrmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class LaboratoryRmiClient {
    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 23000);
        LaboratoryService labservice = (LaboratoryService) registry.lookup("LabService");
        System.out.println("Equipos");

        List<String> equipments = labservice.consultarEquipos();

        for (String equipment : equipments) {
            System.out.println(equipment);
        }

        System.out.println();
        System.out.println(labservice.consultarEquipo("PC1"));
        boolean reserved = labservice.reservarEquipo("PC1");
        System.out.println("Reservation completed: " + reserved);
        boolean released = labservice.liberarEquipo("PC1");
        System.out.println("Release completed: " + released);
    }
}
