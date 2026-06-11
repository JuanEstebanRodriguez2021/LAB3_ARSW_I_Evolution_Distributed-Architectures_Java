package labsrmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LaboratoryRmiServer {
    public static void main(String[] args) throws Exception {
        LaboratoryService labservice = new LaboratoryServiceImpl();
        Registry registry = LocateRegistry.createRegistry(23000);
        registry.rebind("LabService", labservice);
        System.out.println("Labervice RMI publicado en puerto 23000...");
    }
}