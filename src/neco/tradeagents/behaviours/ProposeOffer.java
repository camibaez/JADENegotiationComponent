package neco.tradeagents.behaviours;

import neco.tradeagents.behaviours.negotiation.NegotiateBehaviour;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.logging.Level;
import java.util.logging.Logger;
import neco.tradeagents.agents.TradeAgent;
import neco.offer.Offer;
import neco.onto.actions.ProposeOfferAction;
import neco.onto.concepts.OfferConcept;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;

/**
 * En este behaviour se genera la oferta inicial que envia el agente proveedor al
 * consumidor una vez que este ultimo le ha indicado que quiere negoaciar.
 * @author Camilo Báez Aneiros
 */
public class ProposeOffer extends OneShotBehaviour{

    
    public ProposeOffer(Agent agent){
        super(agent);
    }
    
    @Override
    public void action() {
        Offer offer = ((TradeAgent)myAgent).getNegotiationPlan().generateCounterOffer();

        ACLMessage offerMessage = new ACLMessage(ACLMessage.PROPOSE);
        offerMessage.setOntology(((TradeAgent) myAgent).getOntology().getName());
        offerMessage.setLanguage(((TradeAgent) myAgent).getCodec().getName());
        
        AID proposer = myAgent.getAID();
        AID receiver = ((TradeAgent)myAgent).getNegotiationAgent();
        OfferConcept offerConcept = OfferConcept.wrap(offer); 
        
        offerMessage.setSender(proposer);
        offerMessage.addReceiver(receiver);
        
        ProposeOfferAction action = new ProposeOfferAction(proposer, receiver, offerConcept);
        
        try {
            myAgent.getContentManager().fillContent(offerMessage, new Action(myAgent.getAID(), action));
            
            ((TradeAgent)myAgent).informGuiController("Proposing Offer: " +offer.getDimensionsMap()+ "\n-----------------");
             myAgent.doWait(3000);
             
            myAgent.send(offerMessage);
            myAgent.addBehaviour(new NegotiateBehaviour(myAgent));
            
        } catch (Codec.CodecException ex) {
            Logger.getLogger(ProposeOffer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OntologyException ex) {
            Logger.getLogger(ProposeOffer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }

}
