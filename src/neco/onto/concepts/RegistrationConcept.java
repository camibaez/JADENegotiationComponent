package neco.onto.concepts;

import jade.content.Concept;
import jade.core.AID;
import neco.matchmaker.AgentRegistration;

/**
 * Concepto de registro.
 * @author Camilo Báez Aneiros
 * @see AgentRegistration
 */
public class RegistrationConcept implements Concept{
    /**
     * El tipo de agente del que se trata. Los posibles valores son:
     *  - Consumer
     *  - Provider
     */
    protected String agentType;
    
    protected String resourceType;
    /**
     * AID del agente.
     */
    protected AID aid;
   
    public RegistrationConcept(){
        
    }
    
    public RegistrationConcept(AID aid, String agentType, String resourceType){
        this.aid = aid;
        this.agentType = agentType;
        this.resourceType = resourceType;
    }
      
     public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
    

    public AID getAID() {
        return aid;
    }

    public void setAID(AID aid) {
        this.aid = aid;
    }

}
