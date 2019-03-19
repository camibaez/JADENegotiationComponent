
package neco.onto.actions;

import neco.onto.concepts.RegistrationConcept;
import jade.content.AgentAction;

/**
 * Accion de pedido de registro de un agente al Matchmaker
 * @author Camilo Báez Aneiros
 */
public class ProposeRegistrationAction implements AgentAction{
    protected RegistrationConcept registration;
    
    public ProposeRegistrationAction(){
        
    }
    

    public ProposeRegistrationAction(RegistrationConcept registration){
        this.registration = registration;
    }

    public RegistrationConcept getRegistration() {
        return registration;
    }

    public void setRegistration(RegistrationConcept registration) {
        this.registration = registration;
    }

    
}
