
package neco.tradeagents.behaviours.negotiation;

import neco.mobility.behaviours.MigrateHomeHost;
import neco.tradeagents.agents.TradeAgent;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import neco.tradeagents.behaviours.registration.ProposeRegistration;

/**
 *
 * @author Camilo Báez Aneiros
 */
public class FinishedNegotiation extends OneShotBehaviour{

    FinishedNegotiation(Agent agent) {
        super(agent);
    }

    @Override
    public void action() {
        ((TradeAgent)myAgent).informGuiController("Negotiation Finisehd");
        
        String negotiationState = ((TradeAgent)myAgent).getNegotiationState();
        if(negotiationState.equals(NegotiateBehaviour.NEGOTIATION_ABORTED_STATE)){
            ((TradeAgent)myAgent).informGuiController("Looking for another negotiation");
            myAgent.addBehaviour(new ProposeRegistration());
        }
        if(negotiationState.equals(NegotiateBehaviour.NEGOTIATION_SUCCESS_STATE)){
            ((TradeAgent)myAgent).informGuiController("Success!!! Coming Back");
            myAgent.addBehaviour(new MigrateHomeHost());
        }
        
        ( (TradeAgent) myAgent).cleanState();
        
    }

}
