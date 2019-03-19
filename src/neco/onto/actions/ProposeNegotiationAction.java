
package neco.onto.actions;

import jade.content.AgentAction;
import jade.core.AID;

/**
 * Accion que representa la propuesta que hace un agente consumidor a un proveedor
 * para incializar la negociacion.
 * @author Camilo Báez Aneiros
 */
public class ProposeNegotiationAction implements AgentAction{
    protected AID proposer;
    
    public ProposeNegotiationAction(){
        
    }
    
    public ProposeNegotiationAction(AID proposer){
        this.proposer = proposer;
    }

    public AID getProposer() {
        return proposer;
    }

    public void setProposer(AID proposer) {
        this.proposer = proposer;
    }
}
