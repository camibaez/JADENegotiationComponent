package neco.matchmaker;

import neco.tradeagents.agents.ConsumerAgent;
import neco.tradeagents.agents.ProviderAgent;
import neco.onto.concepts.RegistrationConcept;
import jade.core.AID;

/**
 * Esta clase representa un registro de un agente (Consumer o Provider) en el
 * registro del Matchmaker.
 * 
 * @author Camilo Báez Aneiros
 */
public class AgentRegistration extends RegistrationConcept{
    //Posibles valores del atributo state
    public static final int FREE = 0;
    public static final int BUSSY = 1;
    
    //Posibles valores del atributo heredado agentType
    public static final String CONSUMER_AGENT = ConsumerAgent.class.getName(),
                               PROVIDER_AGENT = ProviderAgent.class.getName();
    
    //TODO Convertir este atributo a un enum y no tener sus posibles valores como constante en la clase
    /**
     * Estado en el que se encuentra el agente que representa este registro.
     * Sus posibles valores estan definidos como constantes en la clase AgentRegistration
     */
    protected int state = 0;

    public AgentRegistration(AID aid, String agentType, String resourceType) {
        this.agentType = agentType;
        this.resourceType = resourceType;
        this.aid = aid;
        this.state = FREE;
    }

    
    
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    
    @Override
   public String toString(){
       return aid.getLocalName() + " | " + resourceType;
   }
}
