
package neco.tradeagents.behaviours;

import neco.tradeagents.behaviours.negotiation.NegotiateBehaviour;
import neco.tradeagents.agents.TradeAgent;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/** En este behaviour el agente espera la confirmacion de su contraparte de que
 * desea inciar la negociacion. Es un behaviour ciclico que se termina una vez
 * que halla recivido la repsuesta. La respuesta puede ser positiva o negativa
 * (ACCEPT_PROPOSAL, REJECT_PROPOSAL)
 *
 * @author Camilo Báez Aneiros
 */
public class NegotiationInitConfirmation extends SimpleBehaviour{
    protected boolean responseReceived;
    
    public NegotiationInitConfirmation(Agent a) {
        super(a);
    }
    
    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.or(
                MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL) , 
                MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL)
        );
        
        ACLMessage msg = myAgent.receive(mt);
        ((TradeAgent) myAgent).informGuiController("Waiting For Proposition Response");
        if (msg == null) {
            block();
            return;
        }
        
        if(msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL){
            ((TradeAgent) myAgent).informGuiController("Proposition Accepted");
             myAgent.addBehaviour(new NegotiateBehaviour(myAgent));
        }else{
            ((TradeAgent) myAgent).informGuiController("Proposition Rejeceted");
        }
        responseReceived = true; 
        
    }

    @Override
    public boolean done() {
        return responseReceived;
    }

}
