/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neco.tradeagents.behaviours.negotiation;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import neco.tradeagents.agents.TradeAgent;
import neco.tradeagents.behaviours.ProposeOffer;
import neco.offer.Offer;
import neco.offer.Dimension;
import neco.onto.actions.ProposeOfferAction;
import neco.onto.concepts.OfferConcept;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import neco.tools.TestLoader;

/**
 * Este Behaviour es el encargado de generar una contra oferta al otro agente con
 * el que se esta negociando.
 * @author Camilo Báez Aneiros
 */
public class GenerateOffer extends OneShotBehaviour{

    public GenerateOffer(Agent agent){
        super(agent);
    }

    @Override
    public void action() {
        Offer offer = ((TradeAgent)myAgent).getActualOffer();
        Offer newOffer = ((TradeAgent)myAgent).getNegotiationPlan().generateCounterOffer();
        
        sendOffer(newOffer);
        
        ((TradeAgent)myAgent).getNegotiationPlan().setLastSentOffer(newOffer);
        ((TradeAgent)myAgent).setLastOffer(offer);
        ((TradeAgent)myAgent).setActualOffer(null);
    }
    
    
    private void sendOffer(Offer offer){
        ACLMessage offerMessage = new ACLMessage(ACLMessage.PROPOSE);
        offerMessage.setOntology(((TradeAgent) myAgent).getOntology().getName());
        offerMessage.setLanguage(((TradeAgent) myAgent).getCodec().getName());
        
        AID proposer = myAgent.getAID(), 
            receiver =  ((TradeAgent)myAgent).getNegotiationAgent();
        
        offerMessage.setSender(proposer);
        offerMessage.addReceiver(receiver);
        
        OfferConcept offerConcept = OfferConcept.wrap(offer);
        ProposeOfferAction propose = new ProposeOfferAction(proposer, receiver, offerConcept);
        Action action = new Action(proposer, propose);
        
        try {
            myAgent.getContentManager().fillContent(offerMessage, action);
        } catch (Codec.CodecException ex) {
            Logger.getLogger(GenerateOffer.class.getName()).log(Level.SEVERE, null, ex);
            ((TradeAgent)myAgent).informGuiController("ERROR DE CODEC PRODUCIDO CREANDO LA OFERTA");
            myAgent.doDelete();
        } catch (OntologyException ex) {
            Logger.getLogger(GenerateOffer.class.getName()).log(Level.SEVERE, null, ex);
            ((TradeAgent)myAgent).informGuiController("ERROR DE ONTOLOGIA PRODUCIDO CREANDO LA OFERTA");
            myAgent.doDelete();
        }
      

        ((TradeAgent)myAgent).informGuiController("Sending new counter Offer: ");
        ((TradeAgent)myAgent).informGuiController(offer.getDimensionsMap().toString());
        
        myAgent.doWait(TestLoader.WAIT_TIME);
        myAgent.send(offerMessage);
        
        ((TradeAgent)myAgent).informGuiController("Offer Sent");

    }

    @Override
    public int onEnd() {
        return NegotiateBehaviour.SENT_OFFER_TRANS;
    }
    
    
}
