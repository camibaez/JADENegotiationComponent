package neco.tradeagents.behaviours.negotiation;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import neco.tradeagents.agents.TradeAgent;

/**
 * Con este behaviour un agente le indica a otro que ha decidido abortar la negocioacion.
 * @author Camilo Báez Aneiros
 */
public class AbortNegotiation extends OneShotBehaviour{

    public AbortNegotiation(Agent a) {
        super(a);
    }
    @Override
    public void action() {
        ((TradeAgent)myAgent).informGuiController("\n-O-O-O-O-O-O-O-O-O-O-O-O\nAborting negotiation\n-0-0-0-0-0-0-0-0-");

        
        ACLMessage abortMessage = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
        abortMessage.setSender(myAgent.getAID());
        abortMessage.addReceiver(((TradeAgent)myAgent).getNegotiationAgent());

        myAgent.send(abortMessage);
        
        ((TradeAgent)myAgent).setNegotiationState(NegotiateBehaviour.NEGOTIATION_ABORTED_STATE);
        
    }
}
