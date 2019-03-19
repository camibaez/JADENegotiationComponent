
package neco.onto.actions;

import neco.onto.concepts.OfferConcept;
import jade.content.AgentAction;
import jade.core.AID;

/**
 * Accion que representa la propuesta de una oferta de un agente a otro.
 * @author Camilo Báez Aneiros
 */

public class ProposeOfferAction implements AgentAction{
    protected AID proposer;
    protected AID receiver;
    protected OfferConcept offer;
    
    public ProposeOfferAction(){
        
    }
    
    public ProposeOfferAction(AID proposer, AID receiver, OfferConcept offer){
        this.proposer = proposer;
        this.receiver = receiver;
        this.offer = offer;
    }
    

    public AID getProposer() {
        return proposer;
    }

    public void setProposer(AID proposer) {
        this.proposer = proposer;
    }

    public AID getReceiver() {
        return receiver;
    }

    public void setReceiver(AID receiver) {
        this.receiver = receiver;
    }

    public OfferConcept getOffer() {
        return offer;
    }

    public void setOffer(OfferConcept offer) {
        this.offer = offer;
    }
}
