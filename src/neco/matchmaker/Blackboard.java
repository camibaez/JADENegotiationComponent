package neco.matchmaker;

import jade.core.AID;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase <code>Blackboard</code> representa el registro que lleva el agente Matchmaker
 * de los agentes negociadores que han sido registrados.
 * @author Camilo Báez Aneiros
 */
public class Blackboard {
    //TODO Sacar el alamacenamiento de estos valores para una base de datos o algo mas elegante y eficiente.
    protected List<AgentRegistration> consumersList;
    protected List<AgentRegistration> providersList;

    public Blackboard() {
        consumersList = new ArrayList<AgentRegistration>();
        providersList = new ArrayList<AgentRegistration>();
    }

    /**
     * Desregistra un agente a partir de su AID
     */
    public void unregisterAgent(AID aid) {
        //TODO Todo este metodo debe ser sustituido en el futuro por uno que diferencie 
        //si el agente a desregistrar es un consumidor o un proveedor. Optimizar esta busqueda que es
        //una locura. Extremadamente ineficiente!!!.
        for (AgentRegistration registration : consumersList) {
            if (registration.getAID().equals(aid)) {
                consumersList.remove(registration);
            }
            return;
        }
        for (AgentRegistration registration : providersList) {
            if (registration.getAID().equals(aid)) {
                providersList.remove(registration);
            }
            return;
        }
    }

    public List<AgentRegistration> getConsumersList() {
        return consumersList;
    }

    public void setConsumersList(List<AgentRegistration> consumersList) {
        this.consumersList = consumersList;
    }

    public List<AgentRegistration> getProvidersList() {
        return providersList;
    }

    public void setProvidersList(List<AgentRegistration> providersList) {
        this.providersList = providersList;
    }

    
    //ESTE METODO ES SOLO PARA UTLIDADES DE PRUEBA
    public void showRegistersState(){
        System.out.println("Consumers List: " + consumersList);
        System.out.println("Providers List: " + providersList);
    }
}
