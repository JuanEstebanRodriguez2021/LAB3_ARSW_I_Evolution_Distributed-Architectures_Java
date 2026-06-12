package labsrmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class LaboratoryServiceImpl extends UnicastRemoteObject implements LaboratoryService{

    private Map<String, Equipment> equipments = new HashMap<>();

    public LaboratoryServiceImpl() throws RemoteException{

        equipments.put("PC1", new Equipment("PC1", "ComputadorHP", "B0"));
        equipments.put("CF1", new Equipment("CF1", "Cortafrios", "Redes"));
        equipments.put("SW1", new Equipment("SW1", "Switches", "LabC1"));
    }


    public List<String> consultarEquipos() throws RemoteException{

        List<String> result = new ArrayList<>();

        for (Equipment equipment: equipments.values()){
            result.add(equipment.toString());
        }
        return result;
    }

    public String consultarEquipo(String codigo) throws RemoteException{

        Equipment equipment = equipments.get(codigo);

        if (equipment == null){
            return "EQUIPO_NO_ENCONTRADO";
        }

        return equipment.toString();
    }

    public boolean reservarEquipo(String codigo) throws RemoteException{

        Equipment equipment = equipments.get(codigo);

        if (equipment == null || equipment.isReserved()){
            return false;
        }

        equipment.reserve();
        return true;
    }

    public boolean liberarEquipo(String codigo) throws RemoteException{

        Equipment equipment = equipments.get(codigo);

        if (equipment == null || !equipment.isReserved()){
            return false;
        }

        equipment.release();
        return true;

    }
}