package neco.tradeagents.behaviours;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.logging.Level;
import java.util.logging.Logger;
import neco.tradeagents.agents.TradeAgent;
import jade.content.AgentAction;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;

/**
 * Con este Behaviour un agente Consumidor, interesado en un servicio proveido
 * por un agente proveedor, le indicara a este que desea inciar una negociacion.
 *
 * @author Camilo Báez Aneiros
 */
public class ProposeNegotiationIntialization extends OneShotBehaviour {

    public ProposeNegotiationIntialization(Agent a) {
        super(a);
    }

    @Override
    public void action() {
        ACLMessage proposeMsg = new ACLMessage(ACLMessage.PROPOSE);
        proposeMsg.setOntology(((TradeAgent) myAgent).getOntology().getName());
        proposeMsg.setLanguage(((TradeAgent) myAgent).getCodec().getName());

        proposeMsg.setSender(myAgent.getAID());
        proposeMsg.addReceiver(((TradeAgent) myAgent).getNegotiationAgent());

        AgentAction action = new neco.onto.actions.ProposeNegotiationAction(myAgent.getAID());
        

        try {
            myAgent.getContentManager().fillContent(proposeMsg, new Action(myAgent.getAID(), action));
            myAgent.send(proposeMsg);

            ((TradeAgent) myAgent).informGuiController("Proposing negotiation intialization\n--------------");
            myAgent.addBehaviour(new NegotiationInitConfirmation(myAgent));
        } catch (Codec.CodecException ex) {
            Logger.getLogger(ProposeNegotiationIntialization.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OntologyException ex) {
            Logger.getLogger(ProposeNegotiationIntialization.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
