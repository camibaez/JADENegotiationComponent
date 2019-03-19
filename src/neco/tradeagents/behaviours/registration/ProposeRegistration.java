
package neco.tradeagents.behaviours.registration;

import neco.tradeagents.agents.ConsumerAgent;
import neco.tradeagents.agents.TradeAgent;
import neco.tradeagents.behaviours.ProposeNegotiationIntialization;
import neco.onto.actions.ProposeRegistrationAction;
import neco.onto.concepts.RegistrationConcept;
import jade.content.AgentAction;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Camilo Báez Aneiros
 */
public class ProposeRegistration extends OneShotBehaviour{

    
    @Override
    public void action() {
        ACLMessage proposeMsg = new ACLMessage(ACLMessage.PROPOSE);
        proposeMsg.setOntology(((TradeAgent) myAgent).getOntology().getName());
        proposeMsg.setLanguage(((TradeAgent) myAgent).getCodec().getName());

        proposeMsg.setSender(myAgent.getAID());
        proposeMsg.addReceiver(((TradeAgent) myAgent).getMatchmaker());

        AID aid = (myAgent).getAID();
        
        
        RegistrationConcept registration = createRegistration();
        ProposeRegistrationAction action = new ProposeRegistrationAction();
        action.setRegistration(registration);
        try {
            myAgent.getContentManager().fillContent(proposeMsg, new Action(myAgent.getAID(), action));
            myAgent.send(proposeMsg);

            ((TradeAgent) myAgent).informGuiController("Proposing registration\n--------------");
            
            ((TradeAgent)myAgent).registrationDone();
            
        } catch (Codec.CodecException ex) {
            Logger.getLogger(ProposeNegotiationIntialization.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OntologyException ex) {
            Logger.getLogger(ProposeNegotiationIntialization.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected RegistrationConcept createRegistration(){
        AID aid = (myAgent).getAID();
        String agentType = myAgent.getClass().getName();
        String resourceType = ((TradeAgent)myAgent).getResourceType();
        return new RegistrationConcept(aid, agentType, resourceType);
    }

}
