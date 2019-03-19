/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neco.tradeagents.behaviours.negotiation;

import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import neco.tradeagents.agents.TradeAgent;

/**
 * Este Behaviour represetna el proceso de negociacion. Esta implementado como una
 * maquina de estados.
 * El estado inicial es ReceiveOfferBehaviour, a traves del cual el agente se mantiene
 * esperando a que le llegue una oferta para posteriormente pasar al estado de 
 * EvaluateOfferBehaviour, en el cual se evalua dicha oferta. En dependencia del
 * resultado de la evaluacion se decidira si terminar la negociacion (ya sea porque se 
 * encontro una buena oferta o por otras causas) o si generar una contraoferta, en 
 * cuyo caso se pasaria al estado de GenerateOfferBehaviour. Una vez que la contraoferta
 * haya sido generada y enviada se pasa denuevo al estado inicial.
 * 
 * @see ReceiveOffer
 * @see EvaluateOffer
 * @see GenerateOffer
 * @see AbortNegotiation
 * @see SuccessNegotiation
 * @author Camilo Báez Aneiros
 */
public class NegotiateBehaviour extends FSMBehaviour{
    public static final String INITIAL_STATE = "Initial",
            RECEIVING_STATE = "Receiving",
            EVALUATING_STATE = "Evaluating",
            OFFERING_STATE = "Offering",
            SENDING_OFFER_STATE = "SedingOffer",
            NEGOTIATION_ABORTED_STATE = "AbortNegotiation",
            NEGOTIATION_SUCCESS_STATE = "NegotiationSuccess",
            NEGOTIATION_FINISHED_STATE = "NegotiationDone";
    
    
    public static final int OFFER_RECEIVED_TRANS = 1;
    public static final int BAD_OFFER_TRANS = 2;
    public static final int GOOD_OFFER_TRANS = 3;
    public static final int ABORT_NEGOTIATION_TRANS = 4;
    public static final int SENT_OFFER_TRANS = 5;
    public static final int OFFER_ACCEPTED_TRANS = 6;
    
    public NegotiateBehaviour(Agent agent){
        super(agent);
        
        registerFirstState(new OneShotBehaviour(agent){
            public void action() {
                ((TradeAgent)myAgent).informGuiController("Intializing Negotiation");
            }
        }, INITIAL_STATE);
        
        registerLastState(new FinishedNegotiation(agent), NEGOTIATION_FINISHED_STATE);
        
        registerState(new ReceiveOffer(agent), RECEIVING_STATE);
        registerState(new EvaluateOffer(agent), EVALUATING_STATE);
        registerState(new GenerateOffer(agent), OFFERING_STATE);
        registerState(new AbortNegotiation(agent), NEGOTIATION_ABORTED_STATE);
        registerState(new SuccessNegotiation(agent), NEGOTIATION_SUCCESS_STATE);
       
        
        
        //Transitions Registering
        registerDefaultTransition(INITIAL_STATE, RECEIVING_STATE);
        
        // Reciving Offer --> Evaluating Offer  when a offer is received
        registerTransition(RECEIVING_STATE, EVALUATING_STATE, OFFER_RECEIVED_TRANS);
        
        // Receiving Offer --> FInished Negotiation when an negotiation aborted message is received
        registerTransition(RECEIVING_STATE, NEGOTIATION_FINISHED_STATE, ABORT_NEGOTIATION_TRANS);
        
        // Receiving Offer --> Finished NEgotiation when teh agent is informed that the offer was accepted by the other negotiatior
        registerTransition(RECEIVING_STATE, NEGOTIATION_FINISHED_STATE, OFFER_ACCEPTED_TRANS);
        
        //Evaluating Offer --> Negotiation Success  when is a good offer
        registerTransition(EVALUATING_STATE, NEGOTIATION_SUCCESS_STATE, GOOD_OFFER_TRANS);
        
        //Informing Succes Negotiation --> Finished negotiaton   once the negotiation agent is iformed that the deal is closed
        registerDefaultTransition(NEGOTIATION_SUCCESS_STATE, NEGOTIATION_FINISHED_STATE);
        
        //Evaluating Offer --> Abort NEgotiation  when the agent has decided to abort the negotiation
        registerTransition(EVALUATING_STATE, NEGOTIATION_ABORTED_STATE, ABORT_NEGOTIATION_TRANS);
        
        //Abort Negotiation --> Negotiation Finished a defaul transition once the negociation has been aborted
        registerDefaultTransition(NEGOTIATION_ABORTED_STATE, NEGOTIATION_FINISHED_STATE);
        
        //Evaluating Offer --> Re Offering when was a bad offer and create a counteroffer
        registerTransition(EVALUATING_STATE, OFFERING_STATE, BAD_OFFER_TRANS);
        
        //Re Offering (Creating Counter Offer) --> Receiving  when the counter offer was sent
        registerTransition(OFFERING_STATE, RECEIVING_STATE, SENT_OFFER_TRANS, new String[]{RECEIVING_STATE, EVALUATING_STATE, OFFERING_STATE});
        
        
        
    }
    
    
}
