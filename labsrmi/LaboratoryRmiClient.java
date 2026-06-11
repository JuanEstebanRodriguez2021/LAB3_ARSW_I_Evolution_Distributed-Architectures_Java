package labsrmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class LaboratoryRmiClient {
    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 23000);
        LaboratoryService labservice = (LaboratoryService) registry.lookup("LabService");
        System.out.println("Equipos");

    }
}
