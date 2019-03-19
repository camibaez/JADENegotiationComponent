
package neco.onto.actions;

import jade.content.AgentAction;
import jade.core.AID;

/**
 * Accion de pedido de desregistro de un agente al Matchmaker.
 * @author Camilo Báez Aneiros
 */
public class ProposeUnregistrationAction implements AgentAction{
    protected AID proposer;

    public AID getProposer() {
        return proposer;
    }

    public void setProposer(AID proposer) {
        this.proposer = proposer;
    }
}
