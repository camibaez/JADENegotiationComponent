/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neco.tradeagents.behaviours.negotiation;

import jade.content.onto.basic.Action;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.logging.Level;
import java.util.logging.Logger;
import neco.tradeagents.agents.TradeAgent;
import neco.offer.Offer;
import neco.onto.actions.ProposeOfferAction;
import neco.onto.concepts.OfferConcept;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import neco.tools.TestLoader;

/**
 * Este behaivour representa la accion de recivir una oferta.
 *
 * @author Camilo Báez Aneiros
 */
public class ReceiveOffer extends SimpleBehaviour {

    protected boolean negotiationTerminated;
    protected int exitState;

    public ReceiveOffer(Agent a) {
        super(a);
    }

    @Override
    public void action() {

        /* Se esperan tres tipos de mensajes:
         - ACCEPT_PROPOSAL => La contraparte acepto la oferta enviada
         - REJECT_PROPOSAL => La contraparte no quiere negocair mas (Negociacion abortada)
         - PROPOSE => La contraparte no quiso la oferta enviada y envio una contraoferta.
         */
        MessageTemplate mt = MessageTemplate.or(
                MessageTemplate.or(
                        MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL),
                        MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL)
                ),
                MessageTemplate.MatchPerformative(ACLMessage.PROPOSE)
        );
        ACLMessage msg = myAgent.receive(mt);

        ((TradeAgent) myAgent).informGuiController("Waiting An Offer...");
        if (msg == null) {
            block();
            return;
        }
        switch (msg.getPerformative()) {
            case ACLMessage.PROPOSE:
                processOffer(msg);
                break;
            case ACLMessage.REJECT_PROPOSAL:
                rejectResponseHandle(msg);
                break;
            case ACLMessage.ACCEPT_PROPOSAL:
                acceptResponseHandle(msg);
                break;
        }

    }

    protected void processOffer(ACLMessage msg) {
        try {
            Action action = (Action) myAgent.getContentManager().extractContent(msg);
            ProposeOfferAction propose = (ProposeOfferAction) action.getAction();
            OfferConcept offerConcept = propose.getOffer();
            Offer offer = new Offer(offerConcept);


            ((TradeAgent) myAgent).informGuiController(myAgent.getLocalName() + " Receiving offer from: " + msg.getSender().getLocalName());
            ((TradeAgent) myAgent).informGuiController("Received Offer:");
            ((TradeAgent) myAgent).informGuiController(offer.getDimensionsMap() + "");

            myAgent.doWait(TestLoader.WAIT_TIME);

            ((TradeAgent) myAgent).setActualOffer(offer);

            //Receiving Offer --> Evaluating Offer
            exitState = NegotiateBehaviour.OFFER_RECEIVED_TRANS;

        } catch (Codec.CodecException ex) {
            Logger.getLogger(ReceiveOffer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OntologyException ex) {
            Logger.getLogger(ReceiveOffer.class.getName()).log(Level.SEVERE, null, ex);
            ((TradeAgent) myAgent).informGuiController("Unexpected content in PROPOSE msg. Expected Offer object serialized");
        }

    }

    protected void rejectResponseHandle(ACLMessage msg) {

        ((TradeAgent) myAgent).informGuiController("The negotiatotor has aborted the negotiation");


        //Receiving Offer --> FInished Negotiation
        exitState = NegotiateBehaviour.ABORT_NEGOTIATION_TRANS;
        ((TradeAgent)myAgent).setNegotiationState(NegotiateBehaviour.NEGOTIATION_ABORTED_STATE);
        negotiationTerminated = true;
    }

    protected void acceptResponseHandle(ACLMessage msg) {
        //Receiving Offer --> Finished NEgotiation
        exitState = NegotiateBehaviour.OFFER_ACCEPTED_TRANS;
        ((TradeAgent)myAgent).setNegotiationState(NegotiateBehaviour.NEGOTIATION_SUCCESS_STATE);
        negotiationTerminated = true;
    }

    @Override
    public boolean done() {
        return (((TradeAgent) myAgent).getActualOffer() != null) || negotiationTerminated;
    }

    @Override
    public int onEnd() {
        return exitState;
    }

}
